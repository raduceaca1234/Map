package Collection.MyIList;

public interface MyIList<T> {
    int size();
    boolean isEmpty();
    void add(T obj);
    void clear();
    T get(int index);
    String toString();
}
