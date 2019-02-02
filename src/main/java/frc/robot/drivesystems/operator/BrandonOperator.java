package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class BrandonOperator implements Operator {

    private XboxController controller;

    public BrandonOperator(XboxController controller) {
        this.controller = controller;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
        manipulatorsSubsystem.setPivotSpeed(getPivotSpeed());
    }

    private double getIntakeSpeed() {
        return -controller.getY(Hand.kLeft);
    }

    private double getPivotSpeed() {
        return -controller.getY(Hand.kRight);
    }

}