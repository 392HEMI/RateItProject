package com.rateit.api;

import org.json.JSONObject;

/**
 * Created by alexander on 17.04.15.
 */
public abstract class Selector extends Operand<JSONObject> {

    @Override
    public String getOperandType()
    {
        return "selector";
    }

    @Override
    public abstract JSONObject serialize();
}