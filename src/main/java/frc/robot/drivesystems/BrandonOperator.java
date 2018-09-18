package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class BrandonOperator implements Operator {

    private ControlSet controlSet;

    public BrandonOperator(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
        manipulatorsSubsystem.setLiftSpeed(getLiftSpeed());
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
    }

    private double getLiftSpeed() {
        boolean leftBumper = controlSet.getOperatorController().getBumper(Hand.kLeft);
        boolean rightBumper = controlSet.getOperatorController().getBumper(Hand.kRight);
        if (leftBumper && !rightBumper) {
            return -1.0;
        } else if (rightBumper && !leftBumper) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    private double getIntakeSpeed() {
        double leftTrigger = controlSet.getOperatorController().getTriggerAxis(Hand.kLeft);
        double rightTrigger = controlSet.getOperatorController().getTriggerAxis(Hand.kRight);
        if (leftTrigger == 1 && rightTrigger != 1) {
            return -1.0;
        } else if (rightTrigger == 1 && leftTrigger != 1) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

}