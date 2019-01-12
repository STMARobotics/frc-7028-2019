package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * GyroSubsystem
 */
public class GyroSubsystem extends Subsystem {

    private static final AHRS gyro = new AHRS(SPI.Port.kMXP);

    public static double getGyroPosition() {
        return gyro.getAngle();
    }

    public static double getGyroYaw(){
        return gyro.getYaw();
    }

    public static void stopThing(){
        gyro.reset();
    }

    public static boolean getIsRotating() {
        return gyro.isRotating();
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}