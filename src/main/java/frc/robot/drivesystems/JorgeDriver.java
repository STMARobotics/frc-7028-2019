package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;

public class JorgeDriver implements Driver {

    private Robot robot = new Robot();

    private XboxController controller;

    public JorgeDriver() {
        this.controller = getController();
    }

    @Override
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