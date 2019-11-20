package Repository;

import Model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> list;

    public Repository() {
        list = new ArrayList<>();
    }

    public void setList(List<PrgState> list) {
        this.list = list;
    }

    public void add(PrgState state){ list.add(state);}

    public PrgState getCrtPrg(){
        return list.get(0);
    }
}
