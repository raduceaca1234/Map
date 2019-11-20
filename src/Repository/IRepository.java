package Repository;

import Model.PrgState;
import Model.Stament.IStmt;

public interface IRepository {
    PrgState getCrtPrg();
    void add(PrgState prg);
}
