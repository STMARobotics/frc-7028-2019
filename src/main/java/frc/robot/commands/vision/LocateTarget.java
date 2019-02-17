package frc.robot.commands.vision;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;

public class LocateTarget extends Command {

    //Subsystems
    private DriveTrainSubsystem driveTrain;
    private Limelight limelight;

    //Tuning Values
    double turnRate = 0.4; //Percent speed to spin.

    public LocateTarget(DriveTrainSubsystem driveTrain, Limelight limelight){
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        requires(driveTrain);
    }

    @Override
    protected void execute() {
        driveTrain.getDiffDrive().arcadeDrive(0, turnRate);;
    }

    @Override
    protected boolean isFinished() {
        return limelight.isTargetFound();
    }
}
