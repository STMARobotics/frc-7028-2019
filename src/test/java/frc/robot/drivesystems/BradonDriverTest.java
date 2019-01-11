package frc.robot.drivesystems;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Unit tests for BrandonDriver
 */
@RunWith(MockitoJUnitRunner.class)
public class BradonDriverTest {

    @Mock
    private ControlSet mockControlSet;

    @Mock
    private XboxController mockDriverController;

    @Mock
    private DifferentialDrive mockDifferentialDrive;

    @InjectMocks
    private BrandonDriver brandonDriver;

    @Before
    public void setUp() {
        when(mockControlSet.getDriverController()).thenReturn(mockDriverController);
    }

    /**
     * Verifies drive speed
     */
    @Test    public void testDriveSpeed() {
        when(mockDriverController.getY(Hand.kLeft)).thenReturn(.123d);

        brandonDriver.drive(mockDifferentialDrive);

        verify(mockDifferentialDrive).arcadeDrive(eq(-.123d), anyDouble(), anyBoolean());
    }

    /**
     * Verifies rotation speed
     */
    @Test
    public void testDriveRotation() {
        when(mockDriverController.getX(Hand.kRight)).thenReturn(.321d);

        brandonDriver.drive(mockDifferentialDrive);

        verify(mockDifferentialDrive).arcadeDrive(anyDouble(), eq(.321d), anyBoolean());
    }

    /**
     * Verifies drive speed when Slow-mode is active
     */
    @Test
    public void testDriveSpeedSlow() {
        when(mockDriverController.getBButtonPressed()).thenReturn(true);
        when(mockDriverController.getY(Hand.kLeft)).thenReturn(.9d);

        brandonDriver.drive(mockDifferentialDrive);

        verify(mockDifferentialDrive).arcadeDrive(eq(-.6d), anyDouble(), anyBoolean());
    }

    /**
     * Verifies rotation speed when Slow-mode is active
     */
    @Test
    public void testDriveRotationSlow() {
        when(mockDriverController.getBButtonPressed()).thenReturn(true);
        when(mockDriverController.getX(Hand.kRight)).thenReturn(.75d);

        brandonDriver.drive(mockDifferentialDrive);

        verify(mockDifferentialDrive).arcadeDrive(anyDouble(), eq(.5d), anyBoolean());
    }

    /**
     * Verifies that Slow-mode toggles for each button press
     */
    @Test
    public void testSlowToggle() {
        // B-Button will be checked 3 times. It will be pressed, unpressed, then pressed
        when(mockDriverController.getBButtonPressed()).thenReturn(true, false, true);
        when(mockDriverController.getX(Hand.kRight)).thenReturn(.75d);

        // Call drive 3 times
        brandonDriver.drive(mockDifferentialDrive);
        brandonDriver.drive(mockDifferentialDrive);
        brandonDriver.drive(mockDifferentialDrive);

        // Check that Slow-mode was active for the first 2 times, then toggled off the 3rd time
        InOrder inOrder = inOrder(mockDifferentialDrive);
        inOrder.verify(mockDifferentialDrive, times(2)).arcadeDrive(anyDouble(), eq(.5d), anyBoolean());
        inOrder.verify(mockDifferentialDrive).arcadeDrive(anyDouble(), eq(.75d), anyBoolean());
    }

}
