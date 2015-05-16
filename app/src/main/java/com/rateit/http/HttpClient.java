package com.rateit.http;


import java.io.UnsupportedEncodingException;

import com.loopj.android.http.*;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Entity;
import android.util.Log;

import com.rateit.AccountActivity;
import com.rateit.RateItApplication;
import com.rateit.api.SelectQuery;
import com.rateit.http.handlers.*;
import com.rateit.http.handlers.custom.*;

import com.rateit.models.Category;
import com.rateit.models.ValidationUserResult;

public final class HttpClient {
	private Context context;
	private final String API2_ADDRESS = "http://50.112.149.206:2158/api/";
	private final String API_ADDRESS = "http://50.112.149.206:2158/api/call/";
	private final String AUTH_ADDRESS = "http://50.112.149.206:2158/api/account/";
	private static AsyncHttpClient client;
	
	private RateItApplication application;

	private void downloadFile(String url, String[] allowedTypes, IFileResponseHandler handler)
	{
		client.get(url, new HttpFileResponseHandler(handler, allowedTypes));
	}

	public void DownloadImage(String url, String name, String directory, IFileDownloadCompleteHandler handler)
	{
		String[] allowedTypes = new String[] { "image/png" };
		ImageResponseHandler ih = new ImageResponseHandler(context, name, directory, handler);
		downloadFile(url, allowedTypes, ih);
	}

	private String getAPIActionUrl(String action, String params)
	{
		return API_ADDRESS + action + "/" + params;
	}

	private void post(String url, JSONObject object, ResponseHandlerInterface responseHandler)
	{
		ByteArrayEntity entity = null;
		try
		{
			String request = object.toString();
			entity = new ByteArrayEntity(request.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		client.post(context, url, entity, "application/json", responseHandler);
	}
	
	public void get(String action, String urlParams, IHttpResponseHandler responseHandler)
	{
		String url = getAPIActionUrl(action, urlParams);
		HttpResponseHandler handler = new HttpResponseHandler(application, responseHandler);
		client.get(url, handler);
	}
	
	public void get(String action, String urlParams, RequestParams requestParams, IHttpResponseHandler responseHandler)
	{
		String url = getAPIActionUrl(action, urlParams);
		HttpResponseHandler handler = new HttpResponseHandler(application, responseHandler);
		client.get(url, requestParams, handler);
	}
	
	public <T> void get(Class<T> cls, String action, String urlParams, SelectType selectType, IJsonResponseHandler<T> responseHandler)
	{
		String url = getAPIActionUrl(action, urlParams);
		JsonResponseHandler<T> handler = new ApiJsonResponseHandler<T>(application, cls, selectType, responseHandler);
		client.get(url, handler);
	}
	
	public <T> void get(Class<T> cls, String action, String urlParams, RequestParams requestParams, SelectType selectType, IJsonResponseHandler<T> responseHandler)
	{
		String url = getAPIActionUrl(action, urlParams);
		JsonResponseHandler<T> handler = new ApiJsonResponseHandler<T>(application, cls, selectType, responseHandler);
	}

	public <T> void post(Class<T> cls, String controller, String action, String params, JSONObject object, SelectType selectType, IJsonResponseHandler<T> responseHandler)
	{
		String url = API2_ADDRESS + controller + "/" + action;
		JsonResponseHandler<T> handler = new ApiJsonResponseHandler<T>(application, cls, selectType, responseHandler);
		post(url, object, handler);
	}
	
	public <T> void post(Class<T> cls, String action, String params, JSONObject object, SelectType selectType, IJsonResponseHandler<T> responseHandler)
    {
        String url = getAPIActionUrl(action, params);
        JsonResponseHandler<T> handler = new ApiJsonResponseHandler<T>(application, cls, selectType, responseHandler);
        post(url, object, handler);
    }

	public void Autorize(AccountActivity activity, String username, String password)
	{
        String url = AUTH_ADDRESS + "Autorize";
		JSONObject obj = new JSONObject();
		try
		{
			obj.put("Username", username);
			obj.put("Password", password);
		}
		catch (JSONException e)
		{
		}
		
		Class<ValidationUserResult> cls = ValidationUserResult.class;
		IJsonResponseHandler<ValidationUserResult> handler = new LoginHandler(activity);
		ApiJsonResponseHandler<ValidationUserResult> responseHandler =
				new ApiJsonResponseHandler<ValidationUserResult>(application, cls, SelectType.Single, handler);
		post(url, obj, responseHandler);
	}
	
	public void SignOut(IJsonResponseHandler<ValidationUserResult> responseHandler) {
		String url = AUTH_ADDRESS + "SignOut";

	}

	public <T> void ApiQuery(Class<T> cls, SelectQuery query, SelectType selectType, IJsonResponseHandler<T> handler)
	{
		JSONObject obj = query.serialize();
		RequestParams requestParams = new RequestParams();
		requestParams.put("json", obj.toString());
		JsonResponseHandler<T> jsonHandler = new ApiQueryJsonResponseHandler<T>(application, cls, selectType, handler);
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(API_ADDRESS + "Execute", requestParams, jsonHandler);
	}

	public HttpClient(Context _context, RateItApplication _application)
	{
		client = new AsyncHttpClient();
		context = _context;
		application = _application;
		
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		client.setCookieStore(myCookieStore);
	}
}