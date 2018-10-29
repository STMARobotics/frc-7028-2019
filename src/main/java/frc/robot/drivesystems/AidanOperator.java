package frc.robot.drivesystems;

import frc.robot.subsystems.ManipulatorsSubsystem;

public class AidanOperator implements Operator {

    private ControlSet controlSet;

    public AidanOperator(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
        manipulatorsSubsystem.setLiftSpeed(getLiftSpeed());
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
    }

    public double getLiftSpeed() {
        return 0;
    }

    public double getIntakeSpeed() {
        boolean input = controlSet.getOperatorController().getBButton();
        boolean output = controlSet.getOperatorController().getAButton();
        if (input && !output) {
            return -1;
        } else if (output && !input) {
            return 1;
        } else {
            return 0;
        }
    }
}