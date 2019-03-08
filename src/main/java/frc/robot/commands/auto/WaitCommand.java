package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WaitCommand extends Command {

    private final Timer timer = new Timer();

    private final double waitSeconds;

    public WaitCommand(double waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    protected void initialize() {
        timer.start();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return timer.get() >= waitSeconds;
    }

    protected void end() {
        timer.stop();
    }

    protected void interrupted() {

    }

}
