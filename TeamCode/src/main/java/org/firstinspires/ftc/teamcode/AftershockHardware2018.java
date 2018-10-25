package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AftershockHardware2018 {
    HardwareMap hwMap;

    //--------MOTORS--------
    //Arm lifter
    DcMotor arm_lift = null;

    //--------SERVOS--------
    //Grabbers
    Servo armLeft = null;
    Servo armRight = null;

    //--------SENSORS--------
    //Arm raising limit switch
    DigitalChannel limitSwitch = null;

    void init(HardwareMap ahwMap){
        //--------MOTORS--------
        arm_lift = hwMap.get(DcMotor.class, "arm_lift");

        //--------SERVOS--------
        armLeft = hwMap.get(Servo.class, "arm_left");
        armRight = hwMap.get(Servo.class, "arm_right");

        //--------SENSORS--------
        limitSwitch = hwMap.get(DigitalChannel.class, "limit_switch");
    }
}
