package drivesystems;

import edu.wpi.first.wpilibj.XboxController;

public class ControlSet {

    private XBoxController driverController;
    private XBoxController operatorController;
    
    public ControlSet(XBoxController driverController, XBoxController operatorController) {
        this.driverController = driverController;
        this.operatorController = operatorController;
    }

    public XBoxController getDriverController() {
        return driverController;
    }

    public XBoxController getOperatorController() {
        return operatorController;
    }

}