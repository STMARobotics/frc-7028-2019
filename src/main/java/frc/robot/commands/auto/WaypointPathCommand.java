package frc.robot.commands.auto;

import frc.robot.motion.Path;
import frc.robot.subsystems.DriveTrainSubsystem;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.modifiers.TankModifier;

public class WaypointPathCommand extends PathCommand {

    private static final double wheelbase = 25.5625d;

    public WaypointPathCommand(DriveTrainSubsystem driveTrainSubsystem, Config config, Waypoint... waypoints) {
        super(generatePath(waypoints, config), driveTrainSubsystem);
    }

    private static Path generatePath(Waypoint[] waypoints, Config config) {
        Trajectory trajectory = Pathfinder.generate(waypoints, config);
        TankModifier modifier = new TankModifier(trajectory);
        modifier.modify(wheelbase);
        Trajectory leftTrajectory = modifier.getLeftTrajectory();
        Trajectory rightTrajectory = modifier.getRightTrajectory();
        return new Path(leftTrajectory, rightTrajectory);
    }

}