package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

/**
 * Drops the arm and retracts the rack to calibrate them.
 * <p>Only use in auto init</p>
 */
public class CalibratePivotAndArmCommand extends Command {

    private final ManipulatorsSubsystem manipulatorsSubsystem;
    private final ClimbSubsystem climbSubsystem;

    public CalibratePivotAndArmCommand(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem) {
        this.manipulatorsSubsystem = manipulatorsSubsystem;
        this.climbSubsystem = climbSubsystem;
        requires(manipulatorsSubsystem);
        requires(climbSubsystem);
    }

    @Override
    protected void initialize() {
        System.out.println("Starting Arm and Rack Calbration");
    }

    @Override
    protected void execute() {
        manipulatorsSubsystem.setVelocity(110);;
        climbSubsystem.setRackSpeed(-1);
    }

    @Override
    protected void end() {
        manipulatorsSubsystem.setPivotPosition(PivotPosition.LOCK_HATCH);
        climbSubsystem.setRackSpeed(0);
        System.out.println("Arm and Rack Calibrated");
    }

    @Override
    protected boolean isFinished() {
        return manipulatorsSubsystem.isPivotAtBottomLimit() && climbSubsystem.isRackRetracted();
    }
    
}
