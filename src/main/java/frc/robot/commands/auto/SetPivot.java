package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

public class SetPivot extends Command{

    private PivotPosition position;
    private ManipulatorsSubsystem manipulatorSubsystem;
    public SetPivot(ManipulatorsSubsystem manipulatorSubsystem, PivotPosition position){
        this.position = position;
        this.manipulatorSubsystem = manipulatorSubsystem;
        requires(manipulatorSubsystem);
    }

    @Override
    protected void initialize() {
        manipulatorSubsystem.setPivotPosition(position);
    }


    @Override
    protected boolean isFinished() {
        return true;
    }

    
    
}