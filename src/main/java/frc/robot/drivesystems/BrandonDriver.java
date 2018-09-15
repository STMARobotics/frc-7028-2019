package frc.robot.drivesystems;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class BrandonDriver implements Driver {

    private Robot robot = new Robot();

    private XboxController controller;

    public BrandonDriver() {
        this.controller = getController();
    }

    public void drive(DifferentialDrive differentialDrive) {
        differentialDrive.arcadeDrive(getSpeed(), getRotation(), true);
    }

    private double getSpeed() {
        return controller.getY(Hand.kLeft);
    }

    private double getRotation() {
        return controller.getX(Hand.kRight);
    }

    private XboxController getController() {
        return robot.getDriverController();
    }

}