package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class LowerLiftCommand extends Command {

    private ManipulatorsSubsystem manipulatorsSubsystem;
    private Timer timer = new Timer();

    public LowerLiftCommand(ManipulatorsSubsystem manipulatorsSubsystem) {
        requires(manipulatorsSubsystem);
        this.manipulatorsSubsystem = manipulatorsSubsystem;
    }

    protected void intitialize() {
        timer.start();
    }

    protected void execute() {
        manipulatorsSubsystem.setLiftSpeed(-1);
    }

    protected boolean isFinished() {
        return timer.get() >= 3;
    }

    protected void end() {
        manipulatorsSubsystem.setLiftSpeed(0);
    }

    public void interrupted() {

    }

}