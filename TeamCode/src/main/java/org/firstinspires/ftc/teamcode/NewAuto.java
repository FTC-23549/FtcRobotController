package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="New Auto", group="Robot")
public class NewAuto extends LinearOpMode {
    private boolean DEBUG = true;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;
    //private DcMotor armMotor = null;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("entering runOpMode", 1);
        waitForStart();
        telemetry.addData("ran waitForStart", 1);
        rightMotor = hardwareMap.get(DcMotor.class, "right_drive");
        telemetry.addData("right motor done", 1);
        leftMotor = hardwareMap.get(DcMotor.class, "left_drive");
        telemetry.addData("left motor done", 1);
        // armMotor = hardwareMap.get(DcMotor.class,"arm");
        curr(0, 0);
        telemetry.addData("currX & currY set to 20", 1);
        moveX(1000);
        telemetry.addData("moveX 5 ran", 1);
        telemetry.update();



    }

    private double currX;
    private double currY;

    // when set it collects current x and y which is soon to be determined
    public void curr(double currX, double currY)
    {
        if (DEBUG) {
            telemetry.addData("in current method", 1);
            telemetry.update();
        }
        this.currX = currX;
        this.currY = currY;
        if (DEBUG) {
            telemetry.addData("leaving current method", 1);
            telemetry.update();
        }
    }

    boolean isMovable = true;


    // when set it collects new coords wanting to input
    // now thinking abt this
    // it feels rlly pointless and might take out later
    // can just set vars in methods when needed
    // public void newCoords(int newX, int newY)
    // {
    //     newX += currX;
    //     newY += currY;

    // }

    // sets the new current x pos when called
    public void currX(int newX)
    {
        int leftCurrPos = (int)(currX += newX);
        int rightCurrPos = (int)(currX += newX);
    }

    // sets the new current x pos when called
    public void currY(int newY)
    {
        int leftCurrPos = (int) (currY += newY);
        int rightCurrPos = (int)(currY += newY);
    }

    // moves either forwards or backwards based on the current pos of the robot
    // compared to the new input of coords requested to move
    public void moveX(int newX)
    {
        if (DEBUG) {
            telemetry.addData("in move method", 1);
            telemetry.update();
            sleep(500);
        }
        if(isMovable)
        {
            if (DEBUG) {
                telemetry.addData("in isMovable loop", isMovable);
                telemetry.update();
                sleep(500);
            }
            if (newX < currX)
            {
                if (DEBUG) {
                    telemetry.addData("new X is less than currX", newX);
                    telemetry.update();
                    sleep(500);
                }
                for(int i = 0; i<= 50; i++) {
                    moveBackwardsX(newX);
                    telemetry.addData("moved ", i);
                    telemetry.update();
                }
            }

            else if (newX > currX)
            {
                if (DEBUG) {
                    telemetry.addData("new X is greater than currX", newX);
                    telemetry.update();
                    sleep(500);
                }
                for(int i = 0; i<= 3; i++) {
                    moveForwardX(newX);
                    telemetry.addData("moved ", i);
                    telemetry.update();
                }
            }

            else
            {
                if (DEBUG) {
                    telemetry.addData("newX = currX", newX);
                    telemetry.update();
                    sleep(500);
                }
                dontMove();
            }
        }
        else
        {
            if (DEBUG) {
                telemetry.addData("not movable", newX);
                telemetry.update();
                sleep(500);
            }
            dontMove();

        }
    }

    public void dontMove() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    private void moveForwardX(int newX) {
        if (DEBUG) {
            telemetry.addData("in moveForwardX", newX);
            telemetry.update();
            sleep(500);
        }
        leftMotor.setTargetPosition((int) Math.abs(newX -currX));
        rightMotor.setTargetPosition((int) Math.abs(newX -currX));

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        int leftCurrPos = (int)(currX += newX);
        int rightCurrPos = (int)(currX += newX);
        rightMotor.setPower(1);
        leftMotor.setPower(1);
        while (
                (leftMotor.isBusy() || rightMotor.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Currently at", " at %7d :%7d",
                    leftMotor.getCurrentPosition(), rightMotor.getCurrentPosition());
            telemetry.update();
        }
        //   rightMotor.setPower(0);
        //leftMotor.setPower(0);
        isMovable = false;
    }

    private void moveBackwardsX(int newX) {
        leftMotor.setTargetPosition((int) Math.abs(newX -currX));
        rightMotor.setTargetPosition((int) Math.abs(newX -currX));

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        int leftCurrPos = (int) (currX -= newX);
        int rightCurrPos = (int) (currX -= newX);
        isMovable = false;
    }

    // moves either forwards or backwards based on the current pos of the robot
    // compared to the new input of coords requested to move
    public void moveY(int newY)
    {
        if(isMovable)
        {
            if (newY < currY)
            {
                moveBackwardY(newY);
            }

            else if (newY > currY)
            {
                moveForwardY(newY);
            }

            else
            {
                stop();
            }
        }
        else
        {
            stop();
        }

    }

    private void moveForwardY(int newY) {
        leftMotor.setTargetPosition((int) Math.abs(newY -currY));
        rightMotor.setTargetPosition((int) Math.abs(newY -currY));

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        isMovable = false;
    }

    private void moveBackwardY(int newY) {
        leftMotor.setTargetPosition((int) Math.abs(newY -currY));
        rightMotor.setTargetPosition((int) Math.abs(newY -currY));

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        isMovable = false;
    }

    // sets the right turn (soon to be decided the correct amount for it)
    // same will be made for left turn
    // see if we can get degree based sensors on robot for easy turning
    public void rightTurn(int turn)
    {
        if(!isMovable)
        {
            leftMotor.setDirection(DcMotor.Direction.REVERSE);
            rightMotor.setDirection(DcMotor.Direction.REVERSE);

            leftMotor.setTargetPosition(turn);
            rightMotor.setTargetPosition(turn);
        }

    }

    public void leftTurn(int turn)
    {
        if(!isMovable)
        {
            leftMotor.setDirection(DcMotor.Direction.FORWARD);
            rightMotor.setDirection(DcMotor.Direction.FORWARD);

            leftMotor.setTargetPosition(turn);
            rightMotor.setTargetPosition(turn);
        }

    }
}

