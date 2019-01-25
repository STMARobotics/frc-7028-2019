package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class HunterDriver implements Driver {

    private SendableChooser<XboxController> driverControllerChooser;
    private boolean slowMode = false;

    public HunterDriver(SendableChooser<XboxController> driverControllerChooser) {
        this.driverControllerChooser = driverControllerChooser;
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
        double leftTrigger = driverControllerChooser.getSelected().getTriggerAxis(Hand.kLeft);
        double rightTrigger = driverControllerChooser.getSelected().getTriggerAxis(Hand.kRight);
        if (rightTrigger > leftTrigger) {
            return rightTrigger;
        } else if (leftTrigger > rightTrigger) {
            return -leftTrigger;
        } else {
            return 0;
        }
    }

    public double getRotation() {
        return driverControllerChooser.getSelected().getX(Hand.kLeft);
    }

    public boolean getSlowMode() {
        if (driverControllerChooser.getSelected().getAButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }
}