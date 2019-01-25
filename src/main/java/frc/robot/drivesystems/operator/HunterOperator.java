package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class HunterOperator implements Operator {

    private SendableChooser<XboxController> operatorControllerChooser;

    public HunterOperator(SendableChooser<XboxController> operatorControllerChooser) {
        this.operatorControllerChooser = operatorControllerChooser;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
    }
}