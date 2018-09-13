package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class JorgeBoth {

    private ControlSet controlSet;

    public JorgeBoth(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public double getSpeed() {
        return controlSet.getDriverController().getY(Hand.kLeft);
    }

    public double getRotation() {
        return controlSet.getDriverController().getX(Hand.kRight);
    }

    public boolean getUseSquaresDriveTrain() {
        return true;
    }

    public double getLiftSpeed() {
        double leftTrigger = controlSet.getOperatorController().getTriggerAxis(Hand.kLeft);
        double rightTrigger = controlSet.getOperatorController().getTriggerAxis(Hand.kRight);
        if (leftTrigger > rightTrigger) {
            return leftTrigger;
        } else if (rightTrigger > leftTrigger) {
            return rightTrigger;
        } else {
            return 0.0;
        }
    }

    public double getIntakeSpeed() {
        boolean input = controlSet.getOperatorController().getXButton();
        boolean output = controlSet.getOperatorController().getAButton();
        if (input && !output) {
            return -1;
        } else if (output && !input) {
            return 1;
        } else {
            return 0.0;
        }
    }

    public void run() {

    }

}