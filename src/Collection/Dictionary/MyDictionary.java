package Collection.Dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V> {
    private Map<K,V> dictionary;

    public MyDictionary(){
        dictionary=new HashMap<>();
    }

    public V getValue(K key){
        return dictionary.get(key);
    }

    public void update(K key,V value){
        if(isDefined(key))dictionary.replace(key,value);
        else dictionary.put(key,value);
    }
    public String toString(){return dictionary.toString();}
    public int size(){return dictionary.size();}
    public boolean isDefined(K key){
        return dictionary.containsKey(key);
    }
    public boolean hasValue(V value){return dictionary.containsValue(value);}
    public void remove(K key){dictionary.remove(key);}
    public Collection<V> values(){return  dictionary.values();}
    public Set<K> keySet(){return dictionary.keySet();}
}
