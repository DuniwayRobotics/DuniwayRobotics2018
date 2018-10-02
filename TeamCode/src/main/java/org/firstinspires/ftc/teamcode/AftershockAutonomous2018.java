package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "Aftershock", name = "Aftershock Autonomous 2018")
public class AftershockAutonomous2018 extends LinearOpMode{

    //Set up elapsed time object
    ElapsedTime elapsed = new ElapsedTime();

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

    }

    void EncoderDrive(int speed, double left, double right, int timeout){

        //Check if opmode is active
        if(opModeIsActive()){
            while(opModeIsActive()&& elapsed.seconds() < timeout){

            }
        }
    }
}
