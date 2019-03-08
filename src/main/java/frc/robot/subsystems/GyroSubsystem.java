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

    /**
     * Resets the gyro Z. This is used to orient the gyro to the field.
     * THIS SHOULD ONLY BE CALLED WHEN THE ROBOT IS KNOWN TO BE POINTING DOWN FIELD.
     */
    public void reset(){
        gyro.reset();
    }

    public boolean getIsRotating() {
        return gyro.isRotating();
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
