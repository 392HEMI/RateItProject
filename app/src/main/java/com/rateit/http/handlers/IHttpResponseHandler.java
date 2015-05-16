package com.rateit.http.handlers;

public interface IHttpResponseHandler {
	public void onStart();
	public void onFinish();
	
	public void onSuccess(int statusCode, String response);
	public void onFailure(int statusCode, Throwable error, String content);
}