package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * HunterJoystickDriver
 */
public class HunterJoystickDriver implements Driver {

    private Joystick joystick;

    public HunterJoystickDriver(Joystick joystick) {
        this.joystick = joystick;
    }

    public void drive(DifferentialDrive differentialDrive) {
        double speed = getSpeed();
        double rotation = getRotation();
        if (getSlowMode()) {
            speed *= .6;
            rotation *= .7;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);
    }

    private double getSpeed() {
        return joystick.getY();
    }

    private double getRotation() {
        return joystick.getZ() * .8;
    }

    private boolean getSlowMode() {
        if (joystick.getRawButton(10)) {
            return true;
        }
        return false;
    }

    public boolean getAutoOverride() {
        return joystick.getTrigger();
    }

    public boolean getDropKeyPressed() {
        return false;
    }

}