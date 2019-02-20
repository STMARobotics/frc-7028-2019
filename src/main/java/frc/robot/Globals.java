package frc.robot;

import frc.robot.vision.Limelight;

public class Globals{

    private static Limelight limelight;


    private static boolean isUnitTesting = false;
    
    public static void setup() {
        limelight = new Limelight(true);
    }

    public static void startUnitTest(){
        isUnitTesting = true;
    }

    public static Limelight getLimelight(){
        if(isUnitTesting)
            return null;
        
        return limelight;
    }

}