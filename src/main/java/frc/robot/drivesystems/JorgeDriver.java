package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class JorgeDriver implements Driver {

    private SendableChooser<XboxController> driverControllerChooser;
    private boolean slowMode = false;

    public JorgeDriver(SendableChooser<XboxController> driverControllerChooser) {
        this.driverControllerChooser = driverControllerChooser;
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
        return driverControllerChooser.getSelected().getY(Hand.kLeft);
    }

    private double getRotation() {
        return driverControllerChooser.getSelected().getX(Hand.kRight);
    }

    private boolean getSlowMode() {
        if (driverControllerChooser.getSelected().getBButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

}