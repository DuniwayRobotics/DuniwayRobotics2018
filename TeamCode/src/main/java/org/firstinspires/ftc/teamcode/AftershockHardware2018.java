package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AftershockHardware2018 {
    private HardwareMap hwMap;

    //--------MOTORS--------
    //Arm lifter
    DcMotor armLift = null;
    //Drive
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;

    //--------SERVOS--------
    //Grabbers
    Servo armLeft = null;
    Servo armRight = null;
    //Marker flipper
    Servo marker = null;

    void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        //--------MOTORS--------
        armLift = hwMap.get(DcMotor.class, "arm_lift");
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive = hwMap.get(DcMotor.class, "right_drive");

        //--------SERVOS--------
        armLeft = hwMap.get(Servo.class, "arm_left");
        armLeft.setPosition(0);
        armRight = hwMap.get(Servo.class, "arm_right");
        armRight.setPosition(0);
        marker = hwMap.get(Servo.class, "marker_placer");
        marker.setPosition(0);
    }
}
