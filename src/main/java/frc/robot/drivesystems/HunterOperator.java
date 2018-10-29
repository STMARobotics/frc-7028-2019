package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class HunterOperator implements Operator {

    private ControlSet controlSet;

    public HunterOperator(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
        manipulatorsSubsystem.setLiftSpeed(getLiftSpeed());
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
    }

    public double getLiftSpeed() {
        double rightTrigger = controlSet.getOperatorController().getTriggerAxis(Hand.kRight);
        double leftTrigger = controlSet.getOperatorController().getTriggerAxis(Hand.kLeft);
        if (rightTrigger > leftTrigger) {
            return rightTrigger;
        } else if (leftTrigger > rightTrigger) {
            return -leftTrigger;
        } else {
            return 0;
        }
    }

    public double getIntakeSpeed() {
        return controlSet.getOperatorController().getY(Hand.kRight);
    }
}