/*
package com.rateit.http.handlers.custom;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.Comment;

public class SetUserCommentHandler implements IJsonResponseHandler<Comment> {
	private ObjectActivity activity;
	public SetUserCommentHandler(ObjectActivity _activity)
	{
		activity = _activity;
	}
	
	@Override
	public void onStart()
	{
		activity.lock();
	}

	@Override
	public void onFinish()
	{
		activity.unlock();
	}
	
	@Override
	public void onSuccess(int statusCode, Comment[] response)
	{
		
	}
	
	@Override
	public void onSuccess(int statusCode, Comment response)
	{
		activity.setUserComment(response, true);
		activity.unlock();
	}
	
	
	@Override
	public void onFailure(int statusCode, Throwable error, String content)
	{
	}
}
*/
