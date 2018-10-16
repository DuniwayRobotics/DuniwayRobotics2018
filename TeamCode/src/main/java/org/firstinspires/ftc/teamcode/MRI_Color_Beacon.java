/*
Modern Robotics Color Beacon Example
Created 12/7/2016 by Colton Mehlhoff of Modern Robotics using FTC SDK 2.35
Reuse permitted with credit where credit is due

Configuration:
I2CDevice "cb" (MRI Color Beacon with default I2C address 0x4c)

MRIColorBeacon class must be in the same folder as this program. Download from http://modernroboticsinc.com/color-beacon

To change color sensor I2C Addresses, go to http://modernroboticsedu.com/mod/lesson/view.php?id=96
Support is available by emailing support@modernroboticsinc.com
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Color Beacon", group="MRI")
//@Disabled
public class MRI_Color_Beacon extends LinearOpMode {

    MRIColorBeacon beacon = new MRIColorBeacon();

    byte red = 0;     //red value to sent to sensor
    byte green = 0;   //green ...
    byte blue = 0;

    int colorNumber;  //number representing a preset color on the Color Beacon

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        beacon.init(hardwareMap, "cb");  //initializes the I2CDevice. Second parameter is the name of the sensor in the configuration file.

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.b)
                red++;
            if(gamepad1.a)
                green++;
            if(gamepad1.x)
                blue++;
            if(gamepad1.b || gamepad1.a || gamepad1.x)
                beacon.rgb(red, green, blue);            //Set beacon to illuminate the current red, green, and blue values

            if(gamepad1.y){
                colorNumber++;                      //increase color number
                beacon.colorNumber(colorNumber);    //sets color number between 0 and 7. If value is >, modulus is used
                sleep(100);
            }

            telemetry.addData("rgb", (red & 0xFF) + " " + (green & 0xFF) + " " + (blue & 0xFF));
            telemetry.addData("Color", beacon.getColor());  //getColor() returns a text string with the current color of the beacon
            telemetry.update();
        }
    }
}
