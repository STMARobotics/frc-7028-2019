package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class BrandonDriver implements Driver {

    private ControlSet controlSet;

    public BrandonDriver(ControlSet controlSet) {
        this.controlSet = controlSet;
    }

    public void drive(DifferentialDrive differentialDrive) {
        differentialDrive.arcadeDrive(getSpeed(), getRotation(), true);
    }

    private double getSpeed() {
        return controlSet.getDriverController().getY(Hand.kLeft);
    }

    private double getRotation() {
        return controlSet.getDriverController().getX(Hand.kRight);
    }

}