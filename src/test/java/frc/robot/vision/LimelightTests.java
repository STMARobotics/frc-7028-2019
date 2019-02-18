package frc.robot.vision;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for Limelight class
 */
public class LimelightTests {

    private Limelight GetLimelight()
    {
        return new Limelight(true, false);
    }
    @Test
    public void testGetAdjustX_LeftSide() {
        //arrange
        double leftCornerY = 70.0;
        double rightCornerY = 65.0;
        double inputX = 0;

        var limeLight = GetLimelight();

        //act
        var actual = limeLight.getAdjustedX(inputX, leftCornerY, rightCornerY);

        assertEquals(5.0, actual, 0.0);
    }

    @Test
    public void testGetAdjustX_RightSide() {
        //arrange
        double leftCornerY = 70.0;
        double rightCornerY = 73.5;
        double inputX = 0;

        var limeLight = GetLimelight();

        //act
        var actual = limeLight.getAdjustedX(inputX, leftCornerY, rightCornerY);

        assertEquals(-3.5, actual, 0.0);
    }
}