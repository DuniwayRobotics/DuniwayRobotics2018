/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.Dogeforia;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


@TeleOp(name="Gold Test", group="Aftershock")
public class GoldAlignExample extends OpMode {
    //Gold align detector
    private GoldAlignDetector detector;

    //Robot hardware
    private TestbotHardware robot = new TestbotHardware();

    //Vuforia licence key
    private static final String VUFORIA_KEY = "AeD6c6X/////AAABmZ3ExKW40UAfroKSDQ2JrLxH4Mlxt6oFF+x0qihPTikBy3IoT+Qj/R4aFwnmv5ERnhX5w58J8AWMCC1pceL0E5GTfwJP/8cjdY831tmQlwioOssywwG7E8MbZys8uzNdBkTOJvnG8vEenOM+8zKR5cqDhG4AOi3v6ydSz8OHGlJf9IcMwQrh9vKP/qAYAWQmZXMIN5KUhipe9VS1zmDEq3h/nqmR/fZcHDwesqJOA2vsfqro/QAfTQa1abnJoT5D1VnLxrmMVlmYP+0KNhxDfIIRGNQGPLTfjYGyFoQpxpNi5Li3XqKajdhruIkluDZ1PXfiRy6SODW2SsJk5aznfDvAqJnXkK2jT2miYSOhhsb1";

    //Parameters for the Dogeforia object
    private Dogeforia.Parameters parameters = new Dogeforia.Parameters();

    //A variable for the Dogeforia object
    private Dogeforia vuforia;
    @Override
    public void init() {
        //Init the hardware map
        robot.init(hardwareMap);

        //Create a GoldAlignDetector that can also be used for Vuforia
        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), 0, false);
        detector.useDefaults();

        //Set some parameters for detector
        detector.alignSize = 100;
        detector.alignPosOffset = 0;
        detector.downscale = 0.4;

        //Set the detector to use the max area
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005;

        //Ratio scoring settings
        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;

        //Enable detector
        detector.enable();

        //Set license key
        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        //Define a Dogeforia object
        vuforia = new Dogeforia(parameters);

        //Stup and start the Dogeforia object
        vuforia.setDogeCVDetector(detector);
        vuforia.enableDogeCV();
        vuforia.showDebug();
        vuforia.start();
    }

    @Override
    public void start() {
        //Start rotating to find gold
        robot.DC_1.setPower(0.12);
        robot.DC_2.setPower(-0.39);
    }

    @Override
    public void loop() {
        //Some diagnostic data
        telemetry.addData("IsAligned" , detector.getAligned()); // Is the bot aligned with the gold mineral
        telemetry.addData("X Pos" , detector.getXPosition()); // Gold X pos.


        if(!detector.getAligned()){
            //Not aligned, keep going
            telemetry.addLine("NOOOOO");
            telemetry.update();
        }else{
            //Aligned!
            telemetry.addLine("YASSSS");
            telemetry.update();

            //Drive forward
            robot.DC_1.setPower(0.3);
            robot.DC_2.setPower(0.3);
        }
    }

    @Override
    public void stop() {
        //Disable and stop detectors
        detector.disable();
        vuforia.stop();
    }

}
