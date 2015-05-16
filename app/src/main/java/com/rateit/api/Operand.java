package com.rateit.api;

import org.json.JSONObject;

/**
 * Created by alexander on 21.04.15.
 */
public abstract class Operand<T> implements IQueryRule<T> {
    public abstract String getOperandType();

    @Override
    public abstract T serialize();
}
