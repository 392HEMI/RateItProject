/*
package com.rateit.http.handlers.custom;

import android.util.Log;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.ObjectModel;

public class ObjectHandler implements IJsonResponseHandler<ObjectModel> {
	
	private ObjectActivity _activity;
	public ObjectHandler(ObjectActivity activity)
	{
		_activity = activity;
	}
	
	@Override
	public void onStart() {
		_activity.lock();
	}
	
	@Override
	public void onFinish() {
		_activity.unlock();
	}

	@Override
	public void onSuccess(int statusCode, ObjectModel[] model) {
	}
	
	@Override
	public void onSuccess(int statusCode, ObjectModel model) {
        Log.i("object", model == null ? "null" : "not null");
		_activity.setModel(model);
		_activity.InitializeComponent();
		_activity.unlock();
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		_activity.onBackPressed();
	}
}
*/
