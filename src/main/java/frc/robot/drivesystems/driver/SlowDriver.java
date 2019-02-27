package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class SlowDriver implements Driver {

    private XboxController controller;
    private boolean slowMode = false;

    public SlowDriver(XboxController controller) {
        this.controller = controller;
    }

    @Override
    public void drive(DifferentialDrive differentialDrive) {
        double speed = -getSpeed()*.6;
        double rotation = getRotation()*.8;
        if (getSlowMode()) {
            speed = speed * .3;
            rotation = rotation * .4;
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
