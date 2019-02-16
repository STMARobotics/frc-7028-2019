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
            speed = speed * .5;
            rotation = rotation * .5;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);
    }

    private double getSpeed() {
        return controller.getY(Hand.kLeft);
    }

    private double getRotation() {
        return controller.getX(Hand.kRight) * .8;
    }

    private boolean getSlowMode() {
        if (controller.getBButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

    public boolean getAutoOverride() {
        return controller.getAButtonPressed();
    }
    
    public boolean getDropKeyPressed() {
        return controller.getXButton();
    }

}