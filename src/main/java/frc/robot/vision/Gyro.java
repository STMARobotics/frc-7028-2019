package frc.robot.vision;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class Gyro {

    private final static AHRS gyro = new AHRS(SPI.Port.kMXP);

    static{
        gyro.reset();
    }

    public static double getGyroPosition(){
        return gyro.getAngle();
    }

}