package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class JorgeOperator implements Operator {

    private ControlSet controlSet;

    public JorgeOperator(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
    }

}