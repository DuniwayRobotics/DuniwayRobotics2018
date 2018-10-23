/*
Modern Robotics Range Sensor Example
Created 9/8/2016 by Colton Mehlhoff of Modern Robotics using FTC SDK 2.x Beta
Reuse permitted with credit where credit is due

Configuration:
I2cDevice on an Interface Module named "range" at the default address of 0x28 (0x14 7-bit)

This program can be run without a battery and Power Destitution Module.

For more information, visit modernroboticsedu.com.
Support is available by emailing support@modernroboticsinc.com.
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Range", group = "MRI")
public class TestbotAutonomous extends OpMode {

    TestbotHardware robot   = new TestbotHardware();   // Use a Pushbot's hardware

    byte red = 0;
    byte green = 0;
    byte blue = 0;

    int colorNumber;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        double distance = robot.rangeSensor.getDistance(DistanceUnit.CM);

        if (distance > 10) {
            robot.dc_2.setPower(1);
        } else {
            robot.dc_2.setPower(0);
        }

        if (robot.touch.isPressed()) {
            robot.beacon.green();
        } else {
            robot.beacon.red();
        }

        telemetry.addData("distance:", distance);
        telemetry.update();
    }

    @Override
    public void stop() {

    }

}