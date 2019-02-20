package frc.robot.vision;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private static final String LED_MODE = "ledMode";
    private static final String CAM_MODE = "camMode";

    private int badFrameThreshold = 5;
    private int badFrameCount = 0;

    private boolean compensateForApproachAngle = false;

    private NetworkTable table;
    private String tableName;
    private boolean isConnected = false;

    private double latency = 0.0;
    private double targetArea = 0.0;
    private double targetX = 0.0;
    private double targetY = 0.0;
    private boolean targetAcquired = false;

    private double[] cornerX;
    private double[] cornerY;

    // TODO SHUFFLE add distance kFactors to shuffleboard
    private double kFactorPercent = 0.0;
    private double kFactorDistance = 0.0;

    public Limelight(boolean compensateForApproachAngle) {
        this(compensateForApproachAngle, true);
    }

    public Limelight(boolean compensateForApproachAngle, boolean initializeNetworkTables) {
        this.compensateForApproachAngle = compensateForApproachAngle;

        if (initializeNetworkTables) {
            initializeNetworkTables();
        }
    }

    private void initializeNetworkTables() {
        tableName = "limelight";
        table = NetworkTableInstance.getDefault().getTable(tableName);

        // add an event listener on the latency value, every time that gets changed
        // we'll update our values
        table.addEntryListener("tl", (table, key, entry, value, flags) -> {
            updateValues(table, value.getDouble());
            // System.out.println("Latency Updated: " + _latency);
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    public void init() {
        table.getEntry(LED_MODE).setDouble(0.0); // set to current pipeline mode
        table.getEntry(CAM_MODE).setDouble(0.0); // set to vision mode
    }

    public void disable() {
        table.getEntry(LED_MODE).setDouble(1.0); // force off
        table.getEntry(CAM_MODE).setDouble(1.0); // driver camera (stops vision processing)
    }

    private void updateValues(NetworkTable table, double latency) {
        var targetV = table.getEntry("tv").getDouble(0.0);
        if ((targetV == 0.0) && (badFrameCount++ < badFrameThreshold)) {
            // we haven't hit the threshold yet ignore this frame and hope we reacquire next
            // frame
            return;
        }

        this.latency = latency;
        isConnected = targetV != 0.0;

        targetArea = getValue(Value.AREA_PERCENT);
        targetX = getValue(Value.X_OFF_DEG);
        targetY = getValue(Value.Y_OFF_DEG);
        targetAcquired = targetV != 0.0;

        badFrameCount = 0;

        if (!targetAcquired)
            return;

        cornerX = table.getEntry("tcornx").getDoubleArray(new double[8]);
        cornerY = table.getEntry("tcorny").getDoubleArray(new double[8]);

        if (compensateForApproachAngle) {
            targetX = getAdjustedX(targetX, cornerX, cornerY);
        }
    }

    public double getAdjustedX(double inputX, double[] xValues, double[] yValues) {
        // trust the arrays to be the same length from NT, if not lets get out of here
        if (xValues.length != yValues.length)
            return inputX;

        double leftY = 0.0;
        double rightY = 0.0;

        for (int i = 0; i < yValues.length; i++) {
            if (xValues[i] < inputX) {
                // left side of center
                if (leftY < yValues[i]) {
                    // if cur value is larger set it as the max
                    leftY = yValues[i];
                }
            } else {
                // right side of center
                if (rightY < yValues[i]) {
                    // if cur value is larger set it as the max
                    rightY = yValues[i];
                }
            }
        }

        // if we didn't match both left and right get out of here
        if (leftY <= 0 || rightY <= 0) {
            return inputX;
        }

        // apply a linear adjustment pixels to degrees, could add a multiplier here to
        // soften or make more aggressive
        return inputX - (rightY - leftY);
    }

    public double getTargetArea() {
        return targetArea;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    /**
     * Checks "tv". Returns true if a target is found.
     * 
     * @return TargetFound Boolean
     */
    public boolean getIsTargetFound() {
        return targetAcquired;
    }

    public enum Value {
        X_OFF_DEG("tx"),
        Y_OFF_DEG("ty"),
        AREA_PERCENT("ta"),
        SKEW("ts"),
        TARGET_FOUND("tv");

        private String begin;

        private Value(String begin) {
            this.begin = begin;
        }

        public String getBegin() {
            return begin;
        }
    }

    private enum Target {
        TARGET(""), TARGET_O("0"), TARGET_1("1"), TARGET_2("2");

        private String end;

        private Target(String end) {
            this.end = end;
        }

        public String getEnd() {
            return end;
        }
    }

    /**
     * Gets a value of a target from the network table
     * 
     * @param target Looking target. Usually target, if you want a specific channel
     *               use target0-2
     */
    public double getValue(Value value, Target target) {
        try {
            NetworkTableEntry entry = table.getEntry(value.getBegin() + target.getEnd());
            double Entryvalue = entry.getDouble(0.0);
            return Entryvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets a value of the current found target from the network table
     * 
     * @param value Type of value you want
     * @see Value
     */
    public double getValue(Value value) {
        try {
            NetworkTableEntry entry = table.getEntry(value.getBegin());
            double Entryvalue = entry.getDouble(0.0);
            return Entryvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getPipelinelatency() {
        NetworkTableEntry tl = table.getEntry("tl");
        double l = tl.getDouble(0.0);
        return l;
    }

    private void resetPilelineLatency() {
        table.getEntry("tl").setValue(0.0);
    }

    public double getDistanceApprox() {
        return kFactorPercent * kFactorDistance / getValue(Value.AREA_PERCENT);
    }

}
