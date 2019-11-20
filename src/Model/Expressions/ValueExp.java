package Model.Expressions;

import Collection.Dictionary.MyIDictionary;
import Exceptions.MyException;
import Model.Values.Value;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value v){
        e=v;
    }

    public Value eval(MyIDictionary<String,Value> tbl)throws MyException {
        return e;
    }
}
