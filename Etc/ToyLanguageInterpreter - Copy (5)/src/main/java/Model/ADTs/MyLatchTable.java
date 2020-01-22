package Model.ADTs;

import java.util.HashMap;
import java.util.Map;

public class MyLatchTable<K,V> implements ILatchTable<K,V> {
    private HashMap<K,V> latchTable;
    private int addr;
    @Override
    public String toString() {
        String s="{";
        for(Map.Entry entry: latchTable.entrySet()){
            s+=entry.getKey() + "->" + entry.getValue() + "\n";
        }
        return s+"}";
    }

    public MyLatchTable() {
        latchTable=new HashMap<>();
        addr = 0;
    }

    @Override
    public void put(K key, V value) {
        latchTable.put(key,value);
    }

    @Override
    public void setLatchTable(Map<K, V> newTable) {
        latchTable= (HashMap<K, V>) newTable;
    }

    @Override
    public Map<K, V> getLatchTable() {
        return latchTable;
    }

    @Override
    public boolean isDefined(K key) {
        return latchTable.containsKey(key);
    }

    @Override
    public int getFreeAddress() {

        return addr++;
    }
}