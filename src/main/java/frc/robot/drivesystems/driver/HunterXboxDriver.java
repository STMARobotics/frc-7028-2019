package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class HunterXboxDriver implements Driver {

    private XboxController controller;
    private boolean slowMode = false;
    private boolean autoOverride = false;

    public HunterXboxDriver(XboxController controller) {
        this.controller = controller;
    }

    public void drive(DifferentialDrive differentialDrive) {
        double speed = getSpeed();
        double rotation = getRotation();
        if (getSlowMode()) {
            speed = speed / 1.5;
            rotation = rotation / 1.5;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);
    }

    public double getSpeed() {
        double leftTrigger = controller.getTriggerAxis(Hand.kLeft);
        double rightTrigger = controller.getTriggerAxis(Hand.kRight);
        if (rightTrigger > leftTrigger) {
            return rightTrigger;
        } else if (leftTrigger > rightTrigger) {
            return -leftTrigger;
        } else {
            return 0;
        }
    }

    public double getRotation() {
        return controller.getX(Hand.kLeft);
    }

    public boolean getSlowMode() {
        if (controller.getAButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

    public boolean getAutoOverride() {
        return controller.getAButtonPressed();
    }
}