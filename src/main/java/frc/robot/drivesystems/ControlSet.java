package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class ControlSet {

    private SendableChooser<XboxController> driverChooser;
    private SendableChooser<XboxController> operatorChooser;
    
    public ControlSet(SendableChooser<XboxController> driverChooser, SendableChooser<XboxController> operatorChooser) {
        this.driverChooser = driverChooser;
        this.operatorChooser = operatorChooser;
    }

    public XboxController getOperatorController() {
        return operatorChooser.getSelected();
    }

    public XboxController getDriverController() {
        return driverChooser.getSelected();
    }

}