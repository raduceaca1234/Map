package Model.ADTs;

import java.util.Map;

public interface IBarrier<T> {
    int getFreeAddress();
    Map<Integer,T> getBarrierTable();
    void setBarrierTable(Map<Integer,T> newBt);
    void put(Integer addr, T value);
    String toString();
}