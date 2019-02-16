package frc.robot.drivesystems.operator;

import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public interface Operator {
    public void operate(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem);
}