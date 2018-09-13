package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class JorgeDriver implements ArcadeDriver {

    private ControlSet controlSet;

    public JorgeDriver(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public double getSpeed() {
        return controlSet.getDriverController().getY(Hand.kLeft);
    }

    public double getRotation() {
        return controlSet.getDriverController().getX(Hand.kRight);
    }

    public boolean getUseSquares() {
        return true;
    }

    public void run() {
        
    }

}