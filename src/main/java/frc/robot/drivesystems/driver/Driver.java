package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public interface Driver {
    public void drive(DifferentialDrive differentialDrive);
    public boolean getAutoOverride();
    public boolean getVisionPressed();
    public boolean getVisionReleased();
}
