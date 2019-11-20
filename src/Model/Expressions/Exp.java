package Model.Expressions;

import Collection.Dictionary.MyIDictionary;
import Exceptions.MyException;
import Model.Values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl) throws MyException;
}
