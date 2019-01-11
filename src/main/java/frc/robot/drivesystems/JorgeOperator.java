package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class JorgeOperator implements Operator {

    private SendableChooser<XboxController> operatorControllerChooser;

    public JorgeOperator(SendableChooser<XboxController> operatorControllerChooser) {
        this.operatorControllerChooser = operatorControllerChooser;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
    }

}