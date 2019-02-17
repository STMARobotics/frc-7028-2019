package frc.robot.commands.vision;

import frc.robot.commands.auto.DriveForwardCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.vision.Limelight;

/**
 * HitWall
 */
public class HitWall extends DriveForwardCommand{

    private Limelight limelight;
    public HitWall(DriveTrainSubsystem driveTrain, GyroSubsystem gyro, Limelight limelight, double speed){
        super(driveTrain, speed, limelight.getDistanceApprox(), gyro);
        this.limelight = limelight;
    }

    @Override
    protected void initialize() {
        this.distance = limelight.getDistanceApprox();
        super.initialize();
    }
    
}
