package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class JorgeXboxDriver implements Driver {

    private XboxController controller;
    private boolean slowMode = false;

    public JorgeXboxDriver(XboxController controller) {
        this.controller = controller;
    }

    @Override
    public void drive(DifferentialDrive differentialDrive) {
        double speed = -getSpeed();
        double rotation = getRotation();
        if (getSlowMode()) {
            speed = speed * .6;
            rotation = rotation * .8;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);

    }

    private double getSpeed() {
        return controller.getY(Hand.kLeft);
    }

    private double getRotation() {
        return controller.getX(Hand.kRight) * .7;
    }

    private boolean getSlowMode() {
        if (controller.getBButton()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

    public boolean getAutoOverride() {
        return controller.getAButton();
    }

}