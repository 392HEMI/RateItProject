package com.rateit.http.handlers;

public interface IJsonResponseHandler<T> {
	public void onStart();
	public void onSuccess(int statusCode, T[] array);
	public void onSuccess(int statusCode, T object);
	public void onFailure(int statusCode, Throwable e, String response);
	public void onFinish();
}