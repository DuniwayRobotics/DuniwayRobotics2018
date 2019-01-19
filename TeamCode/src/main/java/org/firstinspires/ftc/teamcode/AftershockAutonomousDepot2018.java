package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "Aftershock", name = "Aftershock Autonomous Depot 2018")
public class AftershockAutonomousDepot2018 extends LinearOpMode {

    //Robot hardware
    private AftershockHardware2018 robot = new AftershockHardware2018();
    private ElapsedTime runtime = new ElapsedTime();

    private static final double COUNTS_PER_INCH = 68.376;

    @Override
    public void runOpMode(){
        //Init the hardware map
        robot.init(hardwareMap);

        //Init encoders
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //--------DogeCV--------

        //Create a GoldAlignDetector
        GoldAlignDetector detector = new GoldAlignDetector();
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

        robot.armLeft.setPosition(0);
        robot.armRight.setPosition(0.3);

        //Start rotating to find gold
        robot.rightDrive.setPower(0.12);
        robot.leftDrive.setPower(-0.12);

        runtime.reset();

        while(!(detector.getAligned() && !detector.getConstrained()) && opModeIsActive()) {
            //Some diagnostic data
            telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral
            telemetry.addData("X Pos", detector.getXPosition()); // Gold X pos.
            telemetry.addData("Time", runtime.milliseconds());

            //Not aligned, keep going
            telemetry.addLine("NOOOOO");
            telemetry.update();
        }

        double turnTime = runtime.seconds();

        //Aligned!
        telemetry.addLine("YASSSS");
        telemetry.update();

        //Figure out location
        if(turnTime < 3){
            encoderDrive(0.3, 24, 24, 6);
            encoderDrive(0.2, 38, -30, 5);
            encoderDrive(0.3, -30, -30, 2);
        }else if(turnTime < 4.5){
            encoderDrive(0.3, 52, 52, 6);
            encoderDrive(0.2, 38, -38, 5);
        }else{
            encoderDrive(0.3, 28, 28, 6);
            encoderDrive(0.2, -30, 38, 5);
            encoderDrive(0.3, -30, -30, 2);
        }
        robot.marker.setPosition(0.9);

        //Disable and stop detectors
        detector.disable();
    }

    private void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn on RUN_TO_POSITION
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
            while (opModeIsActive() &&
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
