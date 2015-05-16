package com.rateit.http.handlers;

import com.loopj.android.http.BinaryHttpResponseHandler;

public class HttpFileResponseHandler extends BinaryHttpResponseHandler {
	
	private IFileResponseHandler handler;
	
	public HttpFileResponseHandler(IFileResponseHandler _handler, String[] allowedTypes)
	{
		super(allowedTypes);
		handler = _handler;
	}
	
    @Override
    public void onSuccess(byte[] imageData) {
    	handler.Success(imageData);
    }

    @Override
    public void onFailure(Throwable e) {
    }
}
