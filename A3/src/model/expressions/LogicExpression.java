package model.expressions;

import exceptions.GeneralException;
import model.adt.IADTDictionary;
import model.types.IType;
import model.types.BoolType;
import model.values.IValue;
import model.values.BoolValue;

public class LogicExpression implements IExpression {
    private final IExpression expression1;

    private final IExpression expression2;

    String operator;

    public LogicExpression(IExpression expression1, IExpression expression2, String operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    public IValue evaluate(IADTDictionary<String, IValue> symbolTable) throws GeneralException {
        IValue value1 = expression1.evaluate(symbolTable);
        IValue value2 = expression2.evaluate(symbolTable);

        if (!value1.getType().equals(new BoolType())) {
            throw new GeneralException("Logic Error: The first operand is not a boolean.");
        }
        if (!value2.getType().equals(new BoolType())) {
            throw new GeneralException("Logic Error: The second operand is not a boolean.");
        }

        BoolValue bool1 = (BoolValue) value1;
        BoolValue bool2 = (BoolValue) value2;

        switch (operator) {
            case "and":
                return new BoolValue(bool1.getValue() && bool2.getValue());
            case "or":
                return new BoolValue(bool1.getValue() || bool2.getValue());
            default:
                throw new GeneralException("Logic Error: Invalid boolean operator.");
        }
    }

    @Override
    public IType getType(IADTDictionary<String, IType> typeTable) throws GeneralException {
        IType type1 = expression1.getType(typeTable);
        IType type2 = expression2.getType(typeTable);

        if (!type1.equals(new BoolType())) {
            throw new GeneralException("Logic Error: The first operand is not a boolean.");
        }
        if (!type2.equals(new BoolType())) {
            throw new GeneralException("Logic Error: The second operand is not a boolean.");
        }

        return new BoolType();
    }

    public String toString() {
        return expression1.toString() + " " + operator + "  " + expression2.toString();
    }
}
