package frc.robot.commands.vision;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Value;

public class CombinedTarget extends Command {

    private final DriveTrainSubsystem driveTrain;
    private final Limelight limelight;

    // Tuning Values
    private static final double KP_AIM = 0.02;
    //private static final double KP_DISTANCE_HEIGHT = 0.2;
    private static final double KP_DISTANCE_AREA = 0.2;

    // Spin
    private double MIN_POWER = 0.09; // About the minimum amount of power required to move

    // Forewards/Backwards
    private double areaTarget = 2.5; // Percent of screen

    private boolean finished = false;

    private int noFrame = 0;
    private int maxNoFrames = 20;

    public CombinedTarget(DriveTrainSubsystem driveTrain, Limelight limelight) {
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        requires(driveTrain);
    }

    public CombinedTarget setTarget(double targetArea) {
        this.areaTarget = targetArea;
        return this;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        driveTrain.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void execute() {

        if (limelight.getIsTargetFound()) {
            noFrame = 0;
        } else {
            System.out.println("NO TARGET");
            if (noFrame++ > maxNoFrames) {
                finished = true;
            }
            driveTrain.getDiffDrive().arcadeDrive(0, 0);
            return;
        }

        double turnAdjust = getXAdjust();
        double foreAdjust = getYAdjustArea();

        if (Math.abs(turnAdjust) < 0.05) {
            turnAdjust = 0.0;
        } else {
            turnAdjust += Math.signum(turnAdjust) * MIN_POWER;
        }

        if (Math.abs(foreAdjust) < 0.05) {
            foreAdjust = 0.0;
        } else {
            foreAdjust += Math.signum(foreAdjust) * MIN_POWER;
        }
        finished = turnAdjust == 0 && foreAdjust == 0;

        /**
         * ^ ^ ^ | Drive Straight and turn right | | | v ++ +-
         * 
         * ^ ^ | ^ Drive Straight and turn left | | v | ++ -+
         * 
         * | | ^ | Drive Back and turn right v v | v -- +-
         * 
         * | | | ^ Drive back and turn left v v v | -- -+
         */

        driveTrain.getDiffDrive().arcadeDrive(foreAdjust, turnAdjust, false);

    }

    private double getXAdjust() {
        // Get current degrees from center
        double xOffDeg = limelight.getTargetX();

        return KP_AIM * xOffDeg;
    }

    private double getYAdjustArea() {

        // (areaTarget - Robot...areaPercent) is distanceError
        // above is desiredDistance(areaTarget) - currentDistance(Robot...areaPercent)

        // Driving Adjust is KpDistance * distanceError (above)

        if (!limelight.getIsTargetFound()) {
            return 0d;
        }

        return (KP_DISTANCE_AREA * (areaTarget - limelight.getValue(Value.AREA_PERCENT)));
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() {
        double distance = limelight.getDistanceApprox();
        System.out.println("We should be about " + distance);
        driveTrain.getDiffDrive().arcadeDrive(0, 0);
        driveTrain.setNeutralMode(NeutralMode.Brake);
    }
}