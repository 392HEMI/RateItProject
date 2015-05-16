package com.rateit.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexander on 17.04.15.
 */
public class SelectQuery implements IQueryRule<JSONObject> {
    private String entity;
    private QueryField[] fields;
    private Selector selector;
    private OrderRule orderRule;
    private int limit;
    private int offset;

    public SelectQuery(String entity, QueryField[] fields, Selector selector, OrderRule orderRule, int limit, int offset)
    {
        this.entity = entity;
        this.fields = fields;
        this.selector = selector;
        this.orderRule = orderRule;
        this.limit = limit;
        this.offset = offset;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String value) {
        entity = value;
    }

    public  QueryField[] getFields()
    {
        return fields;
    }

    public void setFields(QueryField[] value)
    {
        fields = value;
    }

    public Selector getSelector()
    {
        return selector;
    }

    public void setSelector(Selector value)
    {
        selector = value;
    }

    public OrderRule getOrderRule()
    {
        return orderRule;
    }

    public void setOrderRule(OrderRule value)
    {
        orderRule = value;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int value)
    {
        limit = value;
    }

    public int getOffset()
    {
        return offset;
    }

    public  void setOffset(int value)
    {
        offset = value;
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        try {
            if (fields != null || fields.length > 0) {
                JSONArray fieldsArray = new JSONArray();
                JSONObject fieldJson;

                for (QueryField field : fields) {
                    fieldJson = field.serialize();
                    fieldsArray.put(fieldJson);
                }
                obj.put("fields", fieldsArray);
            }

            if (selector != null) {
                JSONObject selectorJson = selector.serialize();
                obj.put("selector", selectorJson);
            }
            if (orderRule != null) {
                JSONObject orderRuleJson = orderRule.serialize();
                obj.put("order", orderRule.serialize());
            }

            obj.put("entity", entity);
            obj.put("limit", limit);
            obj.put("offset", offset);
        }
        catch (JSONException e)
        {

        }
        return obj;
    }
}
