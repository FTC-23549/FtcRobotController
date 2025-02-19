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
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import java.util.Iterator;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name="SailorBot Auto", group="Robot")
public class DM extends LinearOpMode {
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;
    //private DcMotor armMotor = null;



    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        rightMotor = hardwareMap.get(DcMotor.class,"right_drive");
        leftMotor = hardwareMap.get(DcMotor.class,"left_drive");

        directMove();
    }



    @Override
    public void waitForStart() {
        super.waitForStart();
    }




    private void tellMe(String caption, int currentPosition) {
        telemetry.addData(caption, currentPosition);
        telemetry.update();
        sleep(2000);
    }



    /*
    Gets all the device names from the hardware map and displays them on the driver hub
     */
    public void showAttachedDevices() {
        Iterator<HardwareDevice> hwDevList = hardwareMap.iterator();
        for (Iterator<HardwareDevice> it = hwDevList; it.hasNext(); ) {
            HardwareDevice hd = it.next();
            telemetry.addData("Found", hd.getDeviceName());
        }
        telemetry.update();
        sleep(5000);
    }

    private void directMove() {

        int TARGET_POSITION = 5;
        double MOTOR_POWER = 0.1;
        //int MOTOR_MAX_TICK = 538;
        telemetry.addData("left motor",  " position %7d power %7f target %7d",
                leftMotor.getCurrentPosition(),
                leftMotor.getPower(),
                leftMotor.getTargetPosition());
        telemetry.addData("right motor",  " position %7d power %7f target %7d",
                rightMotor.getCurrentPosition(),
                rightMotor.getPower(),
                rightMotor.getTargetPosition());
        telemetry.update();
        sleep(1000);

        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setTargetPosition(TARGET_POSITION);


        telemetry.addData("right motor",  " position %7d power %7d target %7d",
                rightMotor.getCurrentPosition(),
                rightMotor.getPower(),
                rightMotor.getTargetPosition());
        telemetry.update();
        sleep(1000);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setTargetPosition(TARGET_POSITION);

        telemetry.addData("left motor",  " position %7d power %7f target %7d",
                leftMotor.getCurrentPosition(),
                leftMotor.getPower(),
                leftMotor.getTargetPosition());
        telemetry.update();
        sleep(1000);

        telemetry.addData("moving to", rightMotor.getTargetPosition());
        telemetry.update();
        sleep(1000);

        leftMotor.setPower(MOTOR_POWER);
        rightMotor.setPower(MOTOR_POWER);


        while ( //opModeIsActive
                (leftMotor.isBusy() || rightMotor.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Running to", TARGET_POSITION);
            telemetry.addData("Currently at", " at %7d :%7d",
                    leftMotor.getCurrentPosition(), rightMotor.getCurrentPosition());
            telemetry.update();
            telemetry.update();
//            sleep(100);
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}