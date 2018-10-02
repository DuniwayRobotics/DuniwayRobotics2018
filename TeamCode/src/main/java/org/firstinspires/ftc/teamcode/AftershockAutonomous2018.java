package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "Aftershock", name = "Aftershock Autonomous 2018")
public class AftershockAutonomous2018 extends LinearOpMode{

    TestBotHardware robot = new TestBotHardware();

    //Set up elapsed time object
    private ElapsedTime elapsed = new ElapsedTime();

    //Definitions for encoder count variables
    static final double COUNTS_PER_MOTOR_REV = 7;
    static final double DRIVE_GEAR_REDUCTION = 2.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        encoderSetup();

        encoderDrive(DRIVE_SPEED, 1, 1, 2);
    }

    private void encoderSetup(){
        robot.DC_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DC_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.DC_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.DC_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void encoderDrive(double speed, double left, double right, int timeout){

        //Target variables
        int leftTarget;
        int rightTarget;

        //Check if opmode is active
        if(opModeIsActive()){

            //Set motors to run to a specified position
            robot.DC_1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.DC_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Set motor speed
            robot.DC_1.setPower(Math.abs(speed));
            robot.DC_2.setPower(Math.abs(speed));

            //Motor target positions
            leftTarget = robot.DC_1.getCurrentPosition() + (int) (left * COUNTS_PER_INCH);
            rightTarget = robot.DC_2.getCurrentPosition() + (int) (right * COUNTS_PER_INCH);

            //Set target positions
            robot.DC_1.setTargetPosition(leftTarget);
            robot.DC_1.setTargetPosition(rightTarget);

            //Reset elapsed time
            elapsed.reset();

            while(opModeIsActive()&& elapsed.seconds() < timeout && robot.DC_1.isBusy() || robot.DC_2.isBusy()){
                telemetry.addLine("Running");
                telemetry.update();
            }
        }

        //Turn off motors
        robot.DC_1.setPower(0);
        robot.DC_2.setPower(0);

        //Disable RUN_TO_POSITION mode
        robot.DC_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.DC_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
