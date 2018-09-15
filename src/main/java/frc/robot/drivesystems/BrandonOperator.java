package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

public class BrandonOperator implements Operator {

    private Robot robot = new Robot();

    private XboxController controller;

    public BrandonOperator() {
        this.controller = getController();
    }

    public void operate(Manipulators manipulators) {
        manipulators.setLiftSpeed(getLiftSpeed());
        manipulators.setIntakeSpeed(getIntakeSpeed());
    }

    private double getLiftSpeed() {
        boolean leftBumper = controller.getBumper(Hand.kLeft);
        boolean rightBumper = controller.getBumper(Hand.kRight);
        if (leftBumper && !rightBumper) {
            return -1.0;
        } else if (rightBumper && !leftBumper) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    private double getIntakeSpeed() {
        double leftTrigger = controller.getTriggerAxis(Hand.kLeft);
        double rightTrigger = controller.getTriggerAxis(Hand.kRight);
        if (leftTrigger == 1 && rightTrigger != 1) {
            return -1.0;
        } else if (rightTrigger == 1 && leftTrigger != 1) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    private XboxController getController() {
        return robot.getOperatorController();
    }

}