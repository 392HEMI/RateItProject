package com.rateit.http.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rateit.RateItActivity;
import com.rateit.RateItApplication;
import com.rateit.http.SelectType;

public class JsonResponseHandler<T> extends JsonHttpResponseHandler {
	
	private Class<T> cls;
    private SelectType selectType;
	private IJsonResponseHandler<T> handler;
	private RateItApplication application;
	
	public JsonResponseHandler(RateItApplication _application, Class<T> _cls, SelectType _selectType, IJsonResponseHandler<T> _handler)
	{
		application = _application;
		cls = _cls;
        selectType = _selectType;
		handler = _handler;
	}
	
	public JsonResponseHandler(RateItApplication _application, Class<T> _cls, SelectType _selectType, IJsonResponseHandler<T> _handler, boolean _responseArray)
	{
		application = _application;
		cls = _cls;
        selectType = _selectType;
		handler = _handler;
	}

	private void setFieldValue(Field field, Object object, JSONObject json) throws JSONException
	{
		Class<?> fieldType = field.getType();
		String fieldName = field.getName();
		try
		{
			if (fieldType.equals(boolean.class))
			{
				boolean value = json.getBoolean(fieldName);
				field.setBoolean(object, value);
			} else if (fieldType.equals(Boolean.class))
			{
				Boolean value = null;
				if (!json.isNull(fieldName))
					value = json.getBoolean(fieldName);
				field.set(object, value);
			}
			else if (fieldType.equals(int.class))
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " int)");
				int value = json.getInt(fieldName);
				field.setInt(object, value);
			} else if (fieldType.equals(Integer.class)) {
				Log.e("JsonResponseHandler", "set field value (" + field.getName() + " Integer)");
				Integer value = null;
				if (!json.isNull(fieldName))
					value = json.getInt(fieldName);
				field.set(object, value);
			} else if (fieldType.equals(long.class))
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " long)");
				long value = json.getLong(fieldName);
				field.setLong(object, value);
			} else if (fieldType.equals(Long.class)) {
				Log.e("JsonResponseHandler", "set field value (" + field.getName() + " Long)");
				Long value = null;
				if (json.isNull(fieldName))
					value = json.getLong(fieldName);
				field.set(object, value);
			} else if (fieldType.equals(double.class))
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " double)");
				double value = json.getDouble(fieldName);
				field.setDouble(object, value);
			} else if (fieldType.equals(Double.class))
			{
				Log.e("JsonResponseHandler", "set field value (" + field.getName() + " Double)");
				Double value = null;
				if (json.isNull(fieldName))
					value = json.getDouble(fieldName);
				field.set(object, value);
			} else if (fieldType.equals(String.class))
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " string)");
				String value = json.getString(fieldName);
				field.set(object, value);
			} else if (fieldType.equals(UUID.class))
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " uuid)");
				String stringValue = json.getString(fieldName);
				UUID value = UUID.fromString(stringValue);
				field.set(object, value);
			} else if (fieldType.isArray())
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " array)");
				Object value = null;
				if (!json.isNull(fieldName))
					value = parseArray(fieldType.getComponentType(), json.getJSONArray(fieldName));
				else
					value = java.lang.reflect.Array.newInstance(fieldType.getComponentType(), 0);
				field.set(object, value);
			}
			else
			{
                Log.e("JsonResponseHandler", "set field value (" + field.getName() + " object)");
				Object value = null;
				if (!json.isNull(fieldName))
					value = parseObject(json.getJSONObject(fieldName), field.getType());
				field.set(object, value);
			}
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
	}
	
	private <Q> Q[] parseArray(Class<Q> cls, JSONArray array)
	{
        Log.e("JsonResponseHandler", "parsing json array");
		int count = array.length();
		Q[] result = (Q[])java.lang.reflect.Array.newInstance(cls, count);
		Q obj;
		JSONObject object;
		for (int i = 0; i < count; i++)
		{
			try
			{
				object = array.getJSONObject(i);
				obj = parseObject(object, cls);
				result[i] = obj;
			} 
			catch (JSONException e)
			{
				Log.e("error", "error while parsing object " + e.getMessage());
			}
		}
		return result;
	}

	private <Q> Q parseObject(JSONObject object, Class<Q> cls) throws JSONException
	{
        Log.e("JsonResponseHandler", "parsing json object");
		Q result = null;
		try
		{
			Constructor<Q> constr = null;
			Constructor<Q>[] constructors = (Constructor<Q>[])cls.getConstructors();
			for (Constructor<?> c : constructors)
				if (c.getParameterTypes().length == 0)
				{
					constr = (Constructor<Q>)c;
					break;
				}
			if (constr == null)
				throw new IllegalAccessException();
			result = constr.newInstance();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}

		if (result == null)
			return null;
		for (Field f : cls.getFields()) {
			setFieldValue(f, result, object);
		}
		return result;
	}

	private T parseResponse(JSONObject object) throws JSONException
	{
		return parseObject(object, cls);
	}
	
	@Override
	public void onStart()
	{
		handler.onStart();
	}

	@Override
	public void onSuccess(int statusCode, JSONArray array)
	{
        if (selectType == SelectType.Single)
        {
            T result = null;
            if (array.length() > 0)
            {
                try
                {
                    JSONObject obj = array.getJSONObject(0);
                    result = parseObject(obj, cls);
                }
                catch (JSONException e)
                {
					Log.e("error", "error while parsing object " + e.getMessage());
                }
            }
            handler.onSuccess(statusCode, result);
        }
        else
        {
            T[] collection = parseArray(cls, array);
            handler.onSuccess(statusCode, collection);
        }
	}
	
	@Override
	public void onSuccess(int statusCode, JSONObject object)
    {
		Log.e("obj", object.toString());
		T result = null;
        try
        {
			if (!(object.has("result") && object.isNull("result")))
				result = parseObject(object, cls);
            handler.onSuccess(statusCode, result);
        }
        catch (JSONException e)
        {
        }
    }
	
	@Override
	public void onFailure(int statusCode, Throwable e, String response)
	{
    	if (statusCode == 401)
    	{
    		application.Autorize(true);
    		return;
    	}
    	handler.onFailure(statusCode, e, response);
	}
	
	@Override
	public void onFinish()
	{
		handler.onFinish();
	}
}
