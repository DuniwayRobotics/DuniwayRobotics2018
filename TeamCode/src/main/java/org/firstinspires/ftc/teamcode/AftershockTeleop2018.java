package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Aftershock Teleop 2018", group = "Aftershock")
public class AftershockTeleop2018 extends LinearOpMode {
    //Robot hardware
    AftershockHardware2018 robot = new AftershockHardware2018();

    @Override
    public void runOpMode(){
        //Init hardware
        robot.init(hardwareMap);

        while (opModeIsActive()){
            //Move arm up/down
            if (gamepad1.dpad_up && !robot.limitSwitch.getState()){
                robot.arm_lift.setPower(0.2);
            }else if (gamepad1.dpad_down){
                robot.arm_lift.setPower(-0.2);
            }
        }
    }
}
