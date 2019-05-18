package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.auto.PointCommand;

public class JorgeXboxDriver implements Driver {

    private XboxController controller;
    private boolean slowMode = false;
    private PointCommand pointCommand;

    public JorgeXboxDriver(XboxController controller, PointCommand pointCommand) {
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

        if(controller.getPOV() != -1){
            pointCommand.setTarget(controller.getPOV());
            pointCommand.start();
        }

    }

    private double getSpeed() {
        return controller.getY(Hand.kLeft);
    }

    private double getRotation() {
        return controller.getX(Hand.kRight) * .78;
    }

    private boolean getSlowMode() {
        if (controller.getBButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

    @Override
    public boolean getAutoOverride() {
        return controller.getAButton();
    }

    @Override
    public boolean getVisionPressed() {
        return controller.getYButtonPressed();
    }

    @Override
    public boolean getVisionReleased() {
        return controller.getYButtonReleased();
    }

}
