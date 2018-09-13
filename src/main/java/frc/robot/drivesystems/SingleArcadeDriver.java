package frc.robot.drivesystems;

public interface SingleArcadeDriver {
    public double getSpeed();
    public double getRotation();
    public boolean getUseSquaresDriveTrain();
    public double getLiftSpeed();
    public double getIntakeSpeed();
    public void run();
}