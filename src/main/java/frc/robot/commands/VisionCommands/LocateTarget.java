package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.vision.Limelight.Target;
import frc.robot.vision.Limelight.Value;

public class LocateTarget extends Command {

    private boolean lookingForTarget;

    public LocateTarget() {
        requires(Robot.driveTrainSubsystem);
    }

    @Override
    protected void initialize() {
        lookingForTarget = true;
    }

    //Tuning Values
    double turnRate = 0.4; //Percent speed to spin.

    @Override
    protected void execute() {
        
        Robot.driveTrainSubsystem.getDriveTrain().arcadeDrive(0, turnRate);;

        if(Robot.limelight.getValue(Value.targetFound) != 0){
            lookingForTarget = false;
        }

    }

    @Override
    protected boolean isFinished() {
        return lookingForTarget;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}