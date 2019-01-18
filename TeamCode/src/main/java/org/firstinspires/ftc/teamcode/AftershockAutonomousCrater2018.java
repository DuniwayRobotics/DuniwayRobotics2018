package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "Aftershock", name = "Aftershock Autonomous Crater 2018")
public class AftershockAutonomousCrater2018 extends LinearOpMode {
    //Gold align detector
    private GoldAlignDetector detector;

    //Robot hardware
    private AftershockHardware2018 robot = new AftershockHardware2018();
    private ElapsedTime runtime = new ElapsedTime();

    private static final double COUNTS_PER_INCH = 68.376;

    private boolean active = false;

    @Override
    public void runOpMode() {
        //Init the hardware map
        robot.init(hardwareMap);

        //Init encoders
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        active = true;

        //--------DogeCV--------

        //Create a GoldAlignDetector that can also be used for Vuforia
        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();

        //Set some parameters for detector
        detector.alignSize = 100;
        detector.alignPosOffset = 0;
        detector.constrainPosOffset = 333;
        detector.downscale = 0.4;

        //Set the detector to use the max area
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005;

        //Ratio scoring settings
        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;

        detector.enable();

        waitForStart();

        //Start rotating to find gold
        robot.rightDrive.setPower(0.12);
        robot.leftDrive.setPower(-0.12);

        while(!(detector.getAligned() && !detector.getConstrained())){
            //Some diagnostic data
            telemetry.addData("IsAligned" , detector.getAligned()); // Is the bot aligned with the gold mineral
            telemetry.addData("X Pos" , detector.getXPosition()); // Gold X pos.

            //Not aligned, keep going
            telemetry.addLine("NOOOOO");
            telemetry.update();
        }
        //Aligned!
        telemetry.addLine("YASSSS");
        telemetry.update();

        //Drive forward
        //robot.rightDrive.setPower(0.3);
        //robot.leftDrive.setPower(0.3);
        encoderDrive(0.3, 36, 36, 4);


        //Disable and stop detectors
        detector.disable();
    }

    private void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (active) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (active &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftDrive.getCurrentPosition(),
                        robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
