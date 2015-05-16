package com.rateit.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by alexander on 21.04.15.
 */
public class FieldOperand extends Operand<JSONObject> {
    private String name;
    private Type type;

    public String getName()
    {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type value) {
        type = value;
    }

    public FieldOperand(Type type, String name)
    {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getOperandType() {
        return "field";
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("type", getOperandType());
            obj.put("name", name);
        }
        catch (JSONException e)
        {
        }
        return obj;
    }
}
