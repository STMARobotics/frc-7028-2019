package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class HunterDriver implements Driver {

    private ControlSet controlSet;
    private boolean slowMode = false;

    public HunterDriver(ControlSet controlSet) {
        this.controlSet = controlSet;
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
        double leftTrigger = controlSet.getDriverController().getTriggerAxis(Hand.kLeft);
        double rightTrigger = controlSet.getDriverController().getTriggerAxis(Hand.kRight);
        if (rightTrigger > leftTrigger) {
            return rightTrigger;
        } else if (leftTrigger > rightTrigger) {
            return -leftTrigger;
        } else {
            return 0;
        }
    }

    public double getRotation() {
        return controlSet.getDriverController().getX(Hand.kLeft);
    }

    public boolean getSlowMode() {
        if (controlSet.getDriverController().getAButtonPressed()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }
}