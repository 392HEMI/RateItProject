package com.rateit.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexander on 04.05.15.
 */
public class QueryField implements IQueryRule<JSONObject> {
    private String name;

    public QueryField(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("name", name);
        }
        catch (JSONException e)
        {
        }
        return obj;
    }
}
