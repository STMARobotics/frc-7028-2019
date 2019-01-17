package frc.robot.motion;

import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;

/**
 * Repesents a path created by PathFinder for use in Talon SRX Motion Profiling.
 * <p>Use {@link #loadFromPathWeaver(String)} factory method to load a path created with PathWeaver.</p>
 */
public class Path {

    private final Trajectory leftTrajectory;
    private final Trajectory rightTrajectory;

    /**
     * Constructor
     * @param leftTrajectory trajectory for the left wheels
     * @param rightTrajectory trajectory for the right wheels
     */
    public Path(Trajectory leftTrajectory, Trajectory rightTrajectory) {
        this.leftTrajectory = leftTrajectory;
        this.rightTrajectory = rightTrajectory;
    }

    public Trajectory getLeftTrajectory() {
        return leftTrajectory;
    }

    public Trajectory getRightTrajectory() {
        return rightTrajectory;
    }

    /**
     * Factory method to load a path from PathWeaver CSV files
     * 
     * @param pathName name of the path
     * @param forward true to run the path forward, false to run it in reverse
     * @return path
     */
    public static Path loadFromPathWeaver(String pathName) {
        // Note: Pathweaver's "right" path is actually for the left side of robot, and vice versa
        return new Path(
            readPathFile(pathName, "right"),
            readPathFile(pathName, "left"));
    }

    static Trajectory readPathFile(String pathName, String side) {
        return PathfinderFRC.getTrajectory(pathName + "." + side);
    }

}
