package com.rateit.http.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rateit.RateItApplication;


public final class HttpResponseHandler extends AsyncHttpResponseHandler {
	private IHttpResponseHandler handler;
	private RateItApplication application;
	
	public HttpResponseHandler(RateItApplication _application, IHttpResponseHandler _handler)
	{
		handler = _handler;
		application = _application;
	}

	@Override
    public void onStart() {
        handler.onStart();
    }
	
	public void onFinish()
	{
		handler.onFinish();
	}

    @Override
    public void onSuccess(int statusCode, String response) {
    	handler.onSuccess(statusCode, response);
    }

    @Override
    public void onFailure(int statusCode, Throwable error, String content) {
    	if (statusCode == 401)
    	{
    		application.Autorize(true);
    		return;
    	}
    	handler.onFailure(statusCode, error, content);
    }
}
