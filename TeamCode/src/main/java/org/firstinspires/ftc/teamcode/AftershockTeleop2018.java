package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Aftershock Teleop 2018", group = "Aftershock")
public class AftershockTeleop2018 extends LinearOpMode {
    //Robot hardware
    private AftershockHardware2018 robot = new AftershockHardware2018();

    @Override
    public void runOpMode(){
        //Init hardware
        robot.init(hardwareMap);

        robot.marker.setPosition(0);

        waitForStart();

        while (opModeIsActive()){
            robot.leftDrive.setPower(gamepad1.left_stick_y);
            robot.rightDrive.setPower(-gamepad1.right_stick_y);

            //Move arm up/down
            if (gamepad1.dpad_up){
                robot.armLift.setPower(-0.2);
            }else if (gamepad1.dpad_down){
                robot.armLift.setPower(0.2);
            }else{
                robot.armLift.setPower(0);
            }

            if (gamepad1.a){
                robot.armLeft.setPosition(0.3);
                robot.armRight.setPosition(0);
            }else if (gamepad1.b){
                robot.armLeft.setPosition(0);
                robot.armRight.setPosition(0.3);
            }

            if (gamepad1.x){
                robot.marker.setPosition(1);
            }else if (gamepad1.y){
                robot.marker.setPosition(0);
            }
        }
    }
}
