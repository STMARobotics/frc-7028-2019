package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class RaiseLiftCommand extends Command {

    ManipulatorsSubsystem manipulatorsSubsystem;
    double time;
    Timer timer = new Timer();

    public RaiseLiftCommand(ManipulatorsSubsystem manipulatorsSubsystem, double time) {
        requires(manipulatorsSubsystem);
        this.manipulatorsSubsystem = manipulatorsSubsystem;
        this.time = time;
    }

    @Override
    protected void initialize() {
        timer.start();
    }

    @Override
    protected void execute() {
        manipulatorsSubsystem.setLiftSpeed(1);
    }

    @Override
    protected boolean isFinished() {
        return timer.get() >= time;
    }

    protected void end() {
        manipulatorsSubsystem.setLiftSpeed(0);
    }

    protected void interrupted() {

    }
}