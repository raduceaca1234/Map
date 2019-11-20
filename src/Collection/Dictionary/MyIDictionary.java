package Collection.Dictionary;

import java.util.Collection;
import java.util.Set;

public interface MyIDictionary<K,V> {
    V getValue (K key);
    void update(K key,V value);
    String toString();
    int size();
    boolean isDefined(K key);
    boolean hasValue(V value);
    void remove(K key);
    Collection<V> values();
    Set<K> keySet();
}
