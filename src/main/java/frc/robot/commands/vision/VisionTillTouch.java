package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * VisionTillTouch
 */
public class VisionTillTouch extends Command{

    private Command command;
    private XboxController stick;

    public VisionTillTouch(Command command, XboxController controller) {
        this.command = command;
        this.stick = controller;
    }

    @Override
    protected void initialize() {
        command.start();
    }

    @Override
    protected boolean isFinished() {
        return stick.getRawButton(3);
    }

    @Override
    protected void end() {
        command.cancel();
    }

    
}