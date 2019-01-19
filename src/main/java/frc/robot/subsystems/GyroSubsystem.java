package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * GyroSubsystem
 */
public class GyroSubsystem extends Subsystem {

    private static final AHRS gyro = new AHRS(SPI.Port.kMXP);

    public double getGyroPosition() {
        return gyro.getAngle();
    }

    public double getGyroYaw(){
        return gyro.getYaw();
    }

    public void stopThing(){
        gyro.reset();
    }

    public boolean getIsRotating() {
        return gyro.isRotating();
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}