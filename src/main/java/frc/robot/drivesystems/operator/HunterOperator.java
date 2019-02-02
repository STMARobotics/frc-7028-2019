package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class HunterOperator implements Operator {

    private XboxController controller;

    public HunterOperator(XboxController controller) {
        this.controller = controller;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
    }
}