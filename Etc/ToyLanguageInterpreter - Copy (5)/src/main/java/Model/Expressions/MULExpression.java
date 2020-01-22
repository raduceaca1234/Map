package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class MULExpression implements Expression {
    private Expression exp1;
    private Expression exp2;

    public MULExpression(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> tbl, IHeap<Value> heap) throws MyException {
        return (IntValue) new ArithmeticExpression(
                '-',
                new ArithmeticExpression(
                        '*',
                        exp1,
                        exp2
                ),
                new ArithmeticExpression(
                        '+',
                        exp1,
                        exp2
                )
        ).evaluate(tbl, heap);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}