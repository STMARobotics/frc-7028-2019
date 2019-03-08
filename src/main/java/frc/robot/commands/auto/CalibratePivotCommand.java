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
        System.out.println("Starting Arm Calbration");
    }

    @Override
    protected void execute() {
        System.out.println("Dropping the arm for calibration");
        manipulatorsSubsystem.setPivotSpeed(.2);
    }

    @Override
    protected void end() {
        manipulatorsSubsystem.setPivotSpeed(0);
        System.out.println("Arm Calibrated");
    }

    @Override
    protected boolean isFinished() {
        System.out.println("The bottom limit is " + manipulatorsSubsystem.isPivotAtBottomLimit());
        return manipulatorsSubsystem.isPivotAtBottomLimit();
    }
    
}