/*
package com.rateit.http.handlers.custom;

import android.widget.Toast;

import com.rateit.androidapplication.RateItAndroidApplication;
import com.rateit.androidapplication.SettingsActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.SignOutResponse;

public class SignOutHandler implements IJsonResponseHandler<SignOutResponse> {
	private SettingsActivity activity;
	
	public SignOutHandler(SettingsActivity _activity)
	{
		activity = _activity;
	}
	
	
	@Override
	public void onStart() {
		activity.lock();
	}
	
	@Override
	public void onFinish()
	{
		activity.unlock();
	}

	public void onSuccess(int statusCode, SignOutResponse[] response) {
	}
	
	@Override
	public void onSuccess(int statusCode, SignOutResponse response) {
		if (!response.Status)
		{
			Toast.makeText(activity, "Вы вышли", Toast.LENGTH_LONG).show();
		}
		
		RateItAndroidApplication application = (RateItAndroidApplication)activity.getApplication();
		application.Autorize(true);
		activity.unlock();
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		Toast.makeText(activity, "Невозможно соединиться со службой", Toast.LENGTH_LONG).show();
	}
}
*/
