package com.rateit.http.handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.rateit.RateItApplication;
import com.rateit.http.SelectType;

public class ApiQueryJsonResponseHandler<T> extends JsonResponseHandler<T>
{
    public ApiQueryJsonResponseHandler(RateItApplication application, Class<T> _cls, SelectType selectType, IJsonResponseHandler<T> _handler)
    {
        super(application, _cls, selectType, _handler);
    }

    public ApiQueryJsonResponseHandler(RateItApplication application, Class<T> _cls, SelectType selectType, IJsonResponseHandler<T> _handler, boolean responseArray)
    {
        super(application, _cls, selectType, _handler, responseArray);
    }

    @Override
    public void onSuccess(int statusCode, JSONObject object)
    {
        try
        {
            JSONArray arr = object.getJSONArray("result");
            super.onSuccess(statusCode, arr);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
