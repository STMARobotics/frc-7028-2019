package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class JoystickDriver implements Driver {

    private Joystick joystick;
    private static final double PRECISION_SPEED_MOD = .6d;
    private static final double PRECISION_ROTATION_MOD = .7d;
    private static final double ROTATION_MOD = .8d;

    public JoystickDriver(Joystick joystick) {
        this.joystick = joystick;
    }

    public void drive(DifferentialDrive differentialDrive) {
        double speed = getSpeed();
        double rotation = getRotation();
        if (joystick.getTrigger()) {
            speed *= PRECISION_SPEED_MOD;
            rotation *= PRECISION_ROTATION_MOD;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);
    }

    private double getSpeed() {
        return -joystick.getY();
    }

    private double getRotation() {
        return joystick.getZ() * ROTATION_MOD;
    }

}