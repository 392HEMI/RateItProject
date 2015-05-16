package com.rateit.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexander on 21.04.15.
 */
public class SimpleSelector extends Selector {
    private Operand leftOperand;
    private Operand rightOperand;
    private SelectorAction action;

    public SimpleSelector(Operand leftOperand, Operand rightOperand, SelectorAction action)
    {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.action = action;
    }

    public Operand getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(Operand leftOperand) {
        this.leftOperand = leftOperand;
    }

    public Operand getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(Operand rightOperand) {
        this.rightOperand = rightOperand;
    }

    public SelectorAction getAction() {
        return action;
    }

    public void setAction(SelectorAction action) {
        this.action = action;
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("lop", leftOperand.serialize());
            obj.put("rop", rightOperand.serialize());
            obj.put("action", action.toString());
        }
        catch (JSONException e)
        {
        }
        return obj;
    }
}
