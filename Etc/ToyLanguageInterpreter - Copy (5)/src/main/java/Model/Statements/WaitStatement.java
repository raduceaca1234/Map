package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class WaitStatement implements IStatement {
    private Expression exp;

    public WaitStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        int val = ((IntValue)exp.evaluate((MyDictionary<String, Value>) state.getSymbolTable(), (MyHeap<Value>) state.getHeap())).getValue();

        if(val > 0)
        {
            state.getExecutionStack().push(
                    new CompoundStatement(
                            new PrintStatement(
                                    new ValueExpression(
                                            new IntValue(val)
                                    )
                            ),
                            new WaitStatement(
                                    new ValueExpression(
                                            new IntValue(val-1)
                                    )
                            )
                    )
            );
        } else {
            // CIOCAN
        }

        return null;
    }


    @Override
    public MyDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}