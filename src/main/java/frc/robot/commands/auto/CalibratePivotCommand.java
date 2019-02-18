package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ManipulatorsSubsystem;

/**
 * CalibratePivotCommand
 * Only use in auto init
 */
public class CalibratePivotCommand extends Command {

    private ManipulatorsSubsystem manipulatorsSubsystem;

    public CalibratePivotCommand(ManipulatorsSubsystem manipulatorsSubsystem) {
        this.manipulatorsSubsystem = manipulatorsSubsystem;
        requires(manipulatorsSubsystem);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        manipulatorsSubsystem.setPivotSpeed(.2);
    }

    @Override
    protected void end() {
        manipulatorsSubsystem.setPivotSpeed(0);
    }

    @Override
    protected boolean isFinished() {
        return manipulatorsSubsystem.isPivotAtBottomLimit();
    }
    
}