/*
package com.rateit.http.handlers.custom;

import android.util.Log;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.Comment;

public class GetUserCommentHandler implements IJsonResponseHandler<Comment> {
	private ObjectActivity activity;
	public GetUserCommentHandler(ObjectActivity _activity)
	{
		activity = _activity;
	}
	
	@Override
	public void onStart() {
	}

	@Override
	public void onSuccess(int statusCode, Comment[] array) {
	}

	@Override
	public void onSuccess(int statusCode, Comment object) {
		activity.setUserComment(object, true);
	}

	@Override
	public void onFailure(int statusCode, Throwable e, String response) {
	}

	@Override
	public void onFinish() {
	}
}
*/
