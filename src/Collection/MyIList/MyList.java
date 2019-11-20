package Collection.MyIList;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private List<T> list;

    public MyList(){
        list= new ArrayList<>();
    }

    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void add(T obj){
        list.add(obj);
    }

    public void clear(){
        list.clear();
    }

    public T get(int i){
        return list.get(i);
    }

    public String toString(){
        return list.toString();
    }
}
