package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class NotExpression implements Expression {
    private Expression expr;

    @Override
    public String toString() {
        return "!" + expr.toString();
    }

    public NotExpression(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value result = expr.evaluate(table,heap);
        if(!((BoolValue) result).getValue())
            return new BoolValue(true);
        return new BoolValue(false);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return expr.typeCheck(typeEnv);
    }
}