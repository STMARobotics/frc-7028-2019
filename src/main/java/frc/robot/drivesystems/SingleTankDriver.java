package drivesystems;

public interface SingleTankDriver {
    public double getLeftSpeed();
    public double getRightSpeed();
    public boolean getUseSquaresDriveTrain();
    public double getLiftSpeed();
    public double getIntakeSpeed();
    public void run();
}