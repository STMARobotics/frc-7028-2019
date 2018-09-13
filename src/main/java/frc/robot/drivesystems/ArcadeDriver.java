package frc.robot.drivesystems;

public interface ArcadeDriver {
    public double getSpeed();
    public double getRotation();
    public boolean getUseSquares();
    public void run();
}