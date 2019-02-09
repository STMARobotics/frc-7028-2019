package frc.robot.commands.auto;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.motion.Path;
import frc.robot.subsystems.DriveTrainSubsystem;
import jaci.pathfinder.Trajectory;

/**
 * Executes a path using Talon SRX Motion Profiling.
 */
public class PathCommand extends Command {

    private final DriveTrainSubsystem driveTrainSubsystem;

    private final BufferedTrajectoryPointStream leftPointStream = new BufferedTrajectoryPointStream();

    private final BufferedTrajectoryPointStream rightPointStream = new BufferedTrajectoryPointStream();

    private final Path path;

    private boolean isForward;


    /**
     * Constructs path to drive forwards
     * @param path path to execute
     * @param driveTrainSubsystem drive train
     */
    public PathCommand(Path path, DriveTrainSubsystem driveTrainSubsystem) {
        this(path, driveTrainSubsystem, true);
    }

    /**
     * Constructor
     * @param path path to execute
     * @param driveTrainSubsystem drive train
     * @param isForward execute path forwards
     */
    public PathCommand(Path path, DriveTrainSubsystem driveTrainSubsystem, boolean isForward) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.path = path;
        this.isForward = isForward;
    }

    @Override
    protected void initialize() {
        driveTrainSubsystem.setUseDifferentialDrive(false);

        // Load the trajectory points into buffers
        loadBuffer(path.getLeftTrajectory(), isForward ? leftPointStream : rightPointStream);
        loadBuffer(path.getRightTrajectory(), isForward ? rightPointStream : leftPointStream);

        WPI_TalonSRX leftTalon = driveTrainSubsystem.getLeftTalonSRX();
        WPI_TalonSRX rightTalon = driveTrainSubsystem.getRightTalonSRX();
        leftTalon.configMotionProfileTrajectoryPeriod(0);
        rightTalon.configMotionProfileTrajectoryPeriod(0);

        // TODO Is encoder position reset needed?
        leftTalon.setSelectedSensorPosition(0);
        rightTalon.setSelectedSensorPosition(0);

        // Start executing the trajectory
        leftTalon.startMotionProfile(leftPointStream, 10, ControlMode.MotionProfile);
        rightTalon.startMotionProfile(rightPointStream, 10, ControlMode.MotionProfile);
    }

    /**
     * Loads a PathFinder Trajectory into a Talon BufferedTrajectoryPointStream
     * @param trajectory trajectory to buffer
     * @param stream buffer to load
     */
    private void loadBuffer(Trajectory trajectory, BufferedTrajectoryPointStream stream) {
        stream.Clear();

        var segments = trajectory.segments;
        int direction = isForward ? 1 : -1;
        for (int i = 0; i < segments.length; i++) {
            var segment = segments[i];
            var point = new TrajectoryPoint();

            point.timeDur = (int)(segment.dt * 1000);
            point.position = DriveTrainSubsystem.insToSteps(segment.position) * direction;
            point.velocity = DriveTrainSubsystem.insPerSecToStepsPerDecisec(segment.velocity) * direction;
            
            point.auxiliaryPos = 0;
            point.auxiliaryVel = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.zeroPos = 0 == i;
            point.isLastPoint = segments.length - 1 == i;
            point.arbFeedFwd = 0;
            stream.Write(point);
        }
    }

    @Override
    protected boolean isFinished() {
        return driveTrainSubsystem.getRightTalonSRX().isMotionProfileFinished() 
            && driveTrainSubsystem.getLeftTalonSRX().isMotionProfileFinished();
    }

    @Override
    protected void interrupted() {
        driveTrainSubsystem.getLeftTalonSRX().set(0);
        driveTrainSubsystem.getRightTalonSRX().set(0);
    }

    @Override
    protected void end() {
        driveTrainSubsystem.getLeftTalonSRX().clearMotionProfileTrajectories();
        driveTrainSubsystem.getRightTalonSRX().clearMotionProfileTrajectories();
        driveTrainSubsystem.setUseDifferentialDrive(true);
    }
}
