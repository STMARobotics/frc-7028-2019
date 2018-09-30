package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class JorgeDriver implements Driver {

    private ControlSet controlSet;
    private boolean slowMode = false;

    public JorgeDriver(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    @Override
    public void drive(DifferentialDrive differentialDrive) {
        double speed = -getSpeed();
        double rotation = getRotation();
        if (getSlowMode()) {
            speed = speed / 1.5;
            rotation = rotation / 1.5;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);
    }

    private double getSpeed() {
        return controlSet.getDriverController().getY(Hand.kLeft);
    }

    private double getRotation() {
        return controlSet.getDriverController().getX(Hand.kRight);
    }

    private boolean getSlowMode() {
        if (controlSet.getDriverController().getBButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

}