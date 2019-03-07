package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

public class SetPivot extends Command{

    public SetPivot(ManipulatorsSubsystem manipulatorSubsystem, PivotPosition position){
        manipulatorSubsystem.setPivotPosition(position);    
    }


    @Override
    protected boolean isFinished() {
        return true;
    }

    
    
}