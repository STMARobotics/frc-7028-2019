package frc.robot.vision;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private NetworkTable m_table;
    private String m_tableName;
    private boolean isConnected = false;
    
    private double _latency = 0.0;
    private double _targetArea = 0.0;
    private double _targetX = 0.0;
    private double _targetY = 0.0;
    private boolean _targetAcquired = false;

    public Limelight(){
        m_tableName = "limelight";
        m_table = NetworkTableInstance.getDefault().getTable(m_tableName);
        
        //add an event listener on the latency value, every time that gets changed we'll update our values
        m_table.addEntryListener("tl", (table, key, entry, value, flags) -> {
            UpdateValues(table, value.getDouble());
            System.out.println("Latency Updated: " + _latency);
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
        _latency = latency;
        isConnected = latency != 0.0;
        
        _targetArea = getValue(Value.areaPercent);
        _targetX = getValue(Value.xOffDeg);
        _targetY = getValue(Value.yOffDeg);
        _targetAcquired = table.getEntry("tv").getDouble(0.0) != 0.0;

        System.out.println("Target x,y area:" + _targetX + "," + _targetY + " " + _targetArea);
    }

    public double TargetArea()
    {
        return _targetArea;
    }

    public double TargetX()
    {
        return _targetX;
    }

    public double TargetY()
    {
        return _targetY;
    }

    public boolean isConnected(){
        return isConnected;
    }

    /**
     * Checks  "tv". Returns true if a target is found.
     * @return TargetFound Boolean
     */
    public boolean isTargetFound(){
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
     * Checks "tx?" Returns the current offset from target (-27 to 27 degrees)
     * @param target Looking target. Usually target, if you want a specific channel use target0-2
     * @return X offset in degress
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
    
}