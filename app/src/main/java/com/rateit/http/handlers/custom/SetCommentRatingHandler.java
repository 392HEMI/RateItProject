/*
package com.rateit.http.handlers.custom;

import android.util.Log;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.SetCommentRatingResponse;

public class SetCommentRatingHandler implements IJsonResponseHandler<SetCommentRatingResponse> {
	private ObjectActivity activity;
	
	public SetCommentRatingHandler(ObjectActivity _activity)
	{
		activity = _activity;
	}
	
	@Override
	public void onStart() {
		activity.lock();
	}
	
	@Override
	public void onFinish() {
	}

	@Override
	public void onSuccess(int statusCode, SetCommentRatingResponse[] response) {
	}
	
	@Override
	public void onSuccess(int statusCode, SetCommentRatingResponse response) {
		if (response.Status == 1)
			activity.refreshComments();
		activity.unlock();
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		Log.i("SetCommentRating", error.getMessage());
	}
	
}
*/
