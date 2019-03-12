package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.drivesystems.driver.Driver;

/**
 * Runs a command until the driver vision button is released.
 * @see Driver.getVisionReleased()
 */
public class CommandTillVisionReleased extends Command{

    private final Command command;
    private final Driver driver;

    public CommandTillVisionReleased(Command command, Driver driver) {
        this.command = command;
        this.driver = driver;
    }

    @Override
    protected void initialize() {
        command.start();
    }

    @Override
    protected boolean isFinished() {
        return driver.getVisionReleased();
    }

    @Override
    protected void end() {
        command.cancel();
    }
   
}
