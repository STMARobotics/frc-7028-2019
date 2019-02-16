package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.vision.CombinedTarget;
import frc.robot.commands.vision.VisionTillTouch;

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
        return controller.getY(Hand.kLeft) * .7;
        // double forward = controller.getTriggerAxis(Hand.kRight);
        // double backward = controller.getTriggerAxis(Hand.kLeft);
        // if (forward > backward) {
        //     return forward;
        // } else if (backward > forward) {
        //     return -backward;
        // }
        // return 0;
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

}