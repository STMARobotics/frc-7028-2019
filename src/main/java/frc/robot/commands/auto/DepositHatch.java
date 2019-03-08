package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.vision.*;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;
import frc.robot.vision.Limelight;

public class DepositHatch extends CommandGroup{

    public DepositHatch(DriveTrainSubsystem dT, GyroSubsystem gyro, Limelight limelight, ManipulatorsSubsystem manipSubsys){
        addSequential(new SetPivot(manipSubsys, PivotPosition.LOCK_HATCH));
        addSequential(new CombinedTarget(dT, limelight));
        //addSequential(new HitWall(dT, gyro, limelight, 0.2), commandDelay);
        addSequential(new DriveForwardCommand(dT, 0.5, 6, gyro, 1));
        addSequential(new SetPivot(manipSubsys, PivotPosition.UNLOCK_HATCH));
        addSequential(new WaitCommand(.25d));
        addSequential(new DriveBackwardCommand(dT, 0.5, 6, gyro));
    }

    @Override
    protected void initialize() {
        System.out.println("Starting Deposit");
    }

    
}
