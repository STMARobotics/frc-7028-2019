package frc.robot.drivesystems;

public interface TankDriver {
    public double getLeftSpeed();
    public double getRightSpeed();
    public boolean getUseSquares();
    public void run();
}