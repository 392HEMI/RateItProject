package com.rateit.api;

import org.json.JSONObject;

/**
 * Created by alexander on 17.04.15.
 */
public interface IQueryRule<T> {
    public T serialize();
}
