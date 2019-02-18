package frc.robot.vision;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private int _badFrameThreshold = 5;
    private int _badFrameCount = 0;

    private boolean _compensateForApproachAngle = false;

    private NetworkTable m_table;
    private String m_tableName;
    private boolean isConnected = false;
    
    private double _latency = 0.0;
    private double _targetArea = 0.0;
    private double _targetX = 0.0;
    private double _targetY = 0.0;
    private boolean _targetAcquired = false;

    private double[] _cornerX;
    private double[] _cornerY;

    public Limelight(boolean compensateForApproachAngle)
    {
        this(compensateForApproachAngle, true);
    }

    public Limelight(boolean compensateForApproachAngle, boolean initializeNetworkTables){
        _compensateForApproachAngle = compensateForApproachAngle;

        if (!initializeNetworkTables)
            return;
            
        m_tableName = "limelight";
        m_table = NetworkTableInstance.getDefault().getTable(m_tableName);
        
        //add an event listener on the latency value, every time that gets changed we'll update our values
        m_table.addEntryListener("tl", (table, key, entry, value, flags) -> {
            UpdateValues(table, value.getDouble());
            //System.out.println("Latency Updated: " + _latency);
        } , EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    private static final String _ledMode = "ledMode";
    private static final String _camMode = "camMode";

    public void Init()
    {
        m_table.getEntry(_ledMode).setDouble(0.0); //set to current pipeline mode
        m_table.getEntry(_camMode).setDouble(0.0); //set to vision mode
    }

    public void Disable()
    {
        m_table.getEntry(_ledMode).setDouble(1.0); //force off
        m_table.getEntry(_camMode).setDouble(1.0); //driver camera (stops vision processing)
    }

    private void UpdateValues(NetworkTable table, double latency)
    {
        if (latency == 0)
        {
            if (_badFrameCount++ < _badFrameThreshold)
            {
                return; //we haven't hit the threshold yet ignore this frame and hope we reacquire next frame
            }
        }

        _latency = latency;
        isConnected = latency != 0.0;
        
        _targetArea = getValue(Value.areaPercent);
        _targetX = getValue(Value.xOffDeg);
        _targetY = getValue(Value.yOffDeg);
        _targetAcquired = table.getEntry("tv").getDouble(0.0) != 0.0;
        _cornerX = table.getEntry("tcornx").getDoubleArray(new double[8]);
        _cornerY = table.getEntry("tcorny").getDoubleArray(new double[8]);

        if(_compensateForApproachAngle)
        {
            _targetX = getAdjustedX(_targetX, _cornerY[0], _cornerY[7]);
        }

        //System.out.println("Target x,y area:" + _targetX + "," + _targetY + " " + _targetArea);
    }

    public double getAdjustedX(double inputX, double leftCornerY, double rightCornerY)
    {
        return inputX - (rightCornerY - leftCornerY);
    }

    public double getTargetArea()
    {
        return _targetArea;
    }

    public double getTargetX()
    {
        return _targetX;
    }

    public double getTargetY()
    {
        return _targetY;
    }

    public boolean getIsConnected(){
        return isConnected;
    }

    /**
     * Checks  "tv". Returns true if a target is found.
     * @return TargetFound Boolean
     */
    public boolean getIsTargetFound(){
        return _targetAcquired;
    }

    public enum Value {
        xOffDeg("tx"), yOffDeg("ty"), areaPercent("ta"), skew("ts"), targetFound("tv");

        private String begin;
        private Value(String begin){
            this.begin = begin;
        }
        public String getBegin(){
            return begin;
        }
    }
    public enum Target {

        target(""), target0("0"), target1("1"), target2("2");

        private String end;
        private Target(String end){
            this.end = end;
        }
        public String getEnd(){
            return end;
        }
    }
    
    /**
     * Gets a value of a target from the network table
     * @param target Looking target. Usually target, if you want a specific channel use target0-2
     */
    public double getValue(Value value, Target target){
        try{
        NetworkTableEntry entry = m_table.getEntry(value.getBegin()+target.getEnd());
        double Entryvalue = entry.getDouble(0.0);
        return Entryvalue;
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets a value of the current found target from the network table
     * @param value Type of value you want
     * @see Value
     */
    public double getValue(Value value){
        try{
        NetworkTableEntry entry = m_table.getEntry(value.getBegin());
        double Entryvalue = entry.getDouble(0.0);
        return Entryvalue;
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public double getPipelinelatency(){
        NetworkTableEntry tl = m_table.getEntry("tl");
        double l = tl.getDouble(0.0);
        return l;
    }

    private void resetPilelineLatency(){
        m_table.getEntry("tl").setValue(0.0);
    }

    //TODO SHUFFLE add distance kFactors to shuffleboard
    private double kFactorPercent = 0.0, KFactorDistance = 0.0;
    public double getDistanceApprox(){
        return kFactorPercent * KFactorDistance / getValue(Value.areaPercent);
    }

}