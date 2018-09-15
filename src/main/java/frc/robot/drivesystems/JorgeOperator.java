package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

public class JorgeOperator implements Operator {

    private Robot robot = new Robot();

    private XboxController controller;

    public JorgeOperator() {
        this.controller = getController();
    }

    public void operate(Manipulators manipulators) {
        manipulators.setLiftSpeed(getLiftSpeed());
        manipulators.setIntakeSpeed(getIntakeSpeed());
    }

    private double getLiftSpeed() {
        double leftTrigger = controller.getTriggerAxis(Hand.kLeft);
        double rightTrigger = controller.getTriggerAxis(Hand.kRight);
        if (leftTrigger > rightTrigger) {
            return leftTrigger;
        } else if (rightTrigger > leftTrigger) {
            return rightTrigger;
        } else {
            return 0.0;
        }
    }

    private double getIntakeSpeed() {
        boolean input = controller.getXButton();
        boolean output = controller.getAButton();
        if (input && !output) {
            return -1;
        } else if (output && !input) {
            return 1;
        } else {
            return 0.0;
        }
    }

    private XboxController getController() {
        return robot.getOperatorController();
    }

}