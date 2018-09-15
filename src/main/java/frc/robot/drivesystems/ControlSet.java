package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;

public class ControlSet {

    private XboxController controllerOne;
    private XboxController controllerTwo;
    
    public ControlSet(XboxController controllerOne, XboxController controllerTwo) {
        this.controllerOne = controllerOne;
        this.controllerTwo = controllerTwo;
    }

    public XboxController getControllerOne() {
        return controllerOne;
    }

    public XboxController getControllerTwo() {
        return controllerTwo;
    }

}