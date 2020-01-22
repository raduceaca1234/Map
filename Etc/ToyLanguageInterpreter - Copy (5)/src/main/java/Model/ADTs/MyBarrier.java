package Model.ADTs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyBarrier<T> implements IBarrier<T> {
    private int addr;
    private ConcurrentHashMap<Integer,T> barrierTable;

    public MyBarrier() {
        barrierTable = new ConcurrentHashMap<>();
    }

    @Override
    public int getFreeAddress() {
        return addr++;
    }

    @Override
    public Map getBarrierTable() {
        return barrierTable;
    }

    @Override
    public void put(Integer addr, Object value) {
        barrierTable.put(addr,(T) value);
    }

    @Override
    public void setBarrierTable(Map newBt) {
        barrierTable = (ConcurrentHashMap<Integer, T>) newBt;
    }
    public String toString()
    {
        String s ="{";
        for (int key : barrierTable.keySet())
        {
            s+=key + "->" + barrierTable.get(key).toString() + "\n";
        }
        return s+"}";
    }
}

