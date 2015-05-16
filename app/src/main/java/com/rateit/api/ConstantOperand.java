package com.rateit.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by alexander on 22.04.15.
 */
public class ConstantOperand extends Operand<JSONObject> {

    private String value;
    private Type type;

    public ConstantOperand(Type type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type value) {
        type = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getOperandType() {
        return "value";
    }

    @Override
    public JSONObject serialize() {
        char prefix;
        if (type == int.class || type == Integer.class)
            prefix = '#';
        else if (type == boolean.class || type == Boolean.class)
            prefix = '&';
        else if (type == double.class || type == Double.class)
            prefix = '.';
        else
            prefix = '$';
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("type", getOperandType());
            obj.put("value", prefix + value);
        }
        catch (JSONException e)
        {
        }
        return obj;
    }
}