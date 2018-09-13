package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;

public class ControlSet {

    private XboxController driverController;
    private XboxController operatorController;
    
    public ControlSet(XboxController driverController, XboxController operatorController) {
        this.driverController = driverController;
        this.operatorController = operatorController;
    }

    public XboxController getDriverController() {
        return driverController;
    }

    public XboxController getOperatorController() {
        return operatorController;
    }

}