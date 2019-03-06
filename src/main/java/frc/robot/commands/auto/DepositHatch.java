package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.vision.*;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;
import frc.robot.vision.Limelight;

public class DepositHatch extends CommandGroup{


    //Delays for after hitting the wall and droping the arm
    private double commandDelay = 250;

    public DepositHatch(DriveTrainSubsystem dT, GyroSubsystem gyro, Limelight limelight, ManipulatorsSubsystem manipSubsys, double endTurn){
        addSequential(new SetPivot(manipSubsys, PivotPosition.LOCK_HATCH));
        addSequential(new CombinedTarget(dT, limelight));
        addSequential(new HitWall(dT, gyro, limelight, 0.2), commandDelay);
        addSequential(new SetPivot(manipSubsys, PivotPosition.UNLOCK_HATCH), commandDelay);
        addSequential(new DriveForwardCommand(dT, 0.2, -6, gyro, commandDelay));
        addSequential(new PointCommand(dT, gyro, endTurn));


    }

    
}