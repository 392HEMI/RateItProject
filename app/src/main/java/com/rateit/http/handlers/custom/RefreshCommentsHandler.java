/*
package com.rateit.http.handlers.custom;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.Comment;

public class RefreshCommentsHandler implements IJsonResponseHandler<Comment> {
	private ObjectActivity activity;
	
	public RefreshCommentsHandler(ObjectActivity _activity)
	{
		activity = _activity;
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int statusCode, Comment[] array) {
		activity.setComments(array, true);
	}

	@Override
	public void onSuccess(int statusCode, Comment object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(int statusCode, Throwable e, String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish() {
		
	}

}
*/
