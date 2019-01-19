package frc.robot;

import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.vision.Limelight;

public class Globals{

    private static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(Controls.driverChooser);
    private static ManipulatorsSubsystem manipulatorsSubsystem = new ManipulatorsSubsystem();
    private static GyroSubsystem gyroSubsystem = new GyroSubsystem();
    private static Limelight limelight = new Limelight();


    private static boolean isUnitTesting = false;

    public static void StartUnitTest(){
        isUnitTesting = true;
    }

    public static DriveTrainSubsystem getDrivetrain(){
        if(isUnitTesting)
            return null;
        
        return driveTrainSubsystem;
    }

    public static ManipulatorsSubsystem getManipulator(){
        if(isUnitTesting)
            return null;

        return manipulatorsSubsystem;
    }

    public static GyroSubsystem getGyro(){
        if(isUnitTesting)
            return null;
        
        return gyroSubsystem;
    }

    public static Limelight getLimelight(){
        if(isUnitTesting)
            return null;
        
        return limelight;
    }

}