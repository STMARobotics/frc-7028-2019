package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Globals;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Target;
import frc.robot.vision.Limelight.Value;

public class LocateTarget extends Command {

    private DriveTrainSubsystem driveTrain = Globals.getDrivetrain();
    private Limelight limelight = Globals.getLimelight();

    private boolean lookingForTarget;

    public LocateTarget() {
        requires(driveTrain);
    }

    public LocateTarget(DriveTrainSubsystem driveTrain, Limelight limelight){
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        requires(driveTrain);
    }

    @Override
    protected void initialize() {
        lookingForTarget = true;
    }

    //Tuning Values
    double turnRate = 0.4; //Percent speed to spin.

    @Override
    protected void execute() {
        
        driveTrain.getDiffDrive().arcadeDrive(0, turnRate);;

        if(limelight.getValue(Value.targetFound) != 0){
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