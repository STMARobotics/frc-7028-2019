package frc.robot.vision;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Notifier;

public class Limelight {

    private NetworkTable m_table;
    private String m_tableName;
    private boolean isConnected = false;
    private double _hearBeatPeriod = 0.1;

    private double _latency = 0.0;

    public Limelight(){
        m_tableName = "limelight";
        m_table = NetworkTableInstance.getDefault().getTable(m_tableName);
        _hearBeat.startPeriodic(_hearBeatPeriod);

        //add an event listener on the latency value, every time that gets changed we'll update our values
        m_table.addEntryListener("tl", (table, key, entry, value, flags) -> {
            UpdateValues(table, value.getDouble());
            System.out.println("Latency Updated: " + _latency);
        } , EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    private void UpdateValues(NetworkTable table, double latency)
    {
        _latency = latency;
        //TODO Update all fields from the table param here
    }

    Notifier _hearBeat = new Notifier(new LimelightThread());

    class LimelightThread implements Runnable{

		@Override
		public void run() {
            resetPilelineLatency();
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            if(getPipelinelatency() == 0.0){
                isConnected = false;
            } else {
                isConnected = true;
            }
		}

    }

    public boolean isConnected(){
        return isConnected;
    }

    /**
     * Checks  "tv". Returns true if a target is found.
     * @return TargetFound Boolean
     */
    public boolean isTargetFound(){
        NetworkTableEntry tv = m_table.getEntry("tv");
        double v = tv.getDouble(0);
        if (v == 0.0f){
            return false;
        } else {
            return true; 
        }
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