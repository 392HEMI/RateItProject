package com.rateit.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexander on 17.04.15.
 */
public class OrderRule implements IQueryRule<JSONObject> {
    private String field;
    private OrderType orderType;

    private static String getOrderTypeStr(OrderType orderType)
    {
        if (orderType == OrderType.Ascending)
            return "ASC";
        return "DESC";
    }

    public  OrderRule(String field, OrderType orderType)
    {
        this.field = field;
        this.orderType = orderType;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String value)
    {
        field = value;
    }

    public OrderType getOrderType()
    {
        return orderType;
    }

    public void setOrderType(OrderType value)
    {
        orderType = value;
    }

    @Override
    public JSONObject serialize() {
        String orderTypeStr = getOrderTypeStr(orderType);

        JSONObject obj = new JSONObject();
        try {
            obj.put("field", field);
            obj.put("type", orderTypeStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
