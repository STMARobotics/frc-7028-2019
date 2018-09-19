package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class OperateIntakeCommand extends Command {

    private double speed;
    private double time;
    private ManipulatorsSubsystem manipulatorsSubsystem;
    private Timer timer = new Timer();

    public OperateIntakeCommand(ManipulatorsSubsystem manipulatorsSubsystem, double speed, double time) {
        requires(manipulatorsSubsystem);
        this.speed = speed;
        this.time = time;
        this.manipulatorsSubsystem = manipulatorsSubsystem;
    }

    public void initialize() {
        timer.start();
    }

    public void execute() {
        manipulatorsSubsystem.setIntakeSpeed(speed);
    }

    public boolean isFinished() {
        return timer.get() >= time;
    }

    public void end() {
        manipulatorsSubsystem.setIntakeSpeed(0);
        timer.stop();
    }

}