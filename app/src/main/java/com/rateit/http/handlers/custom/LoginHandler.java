package com.rateit.http.handlers.custom;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.rateit.AccountActivity;
import com.rateit.MainActivity;
import com.rateit.RateItApplication;
import com.rateit.User;
import com.rateit.http.handlers.IJsonResponseHandler;
import com.rateit.models.ValidationUserResult;

public class LoginHandler implements IJsonResponseHandler<ValidationUserResult> {
	private AccountActivity activity;
	private RateItApplication application;
	
	public LoginHandler(AccountActivity _activity)
	{
		activity = _activity;
		application = (RateItApplication)activity.getApplication();
	}
	
	@Override
	public void onStart() {
		activity.lock();
	}
	
	@Override
	public void onFinish() {
		activity.unlock();
	}
	
	@Override
	public void onSuccess(int statusCode, ValidationUserResult[] array) {
		
	}
	
	@Override
	public void onSuccess(int statusCode, ValidationUserResult result) {
		if (result == null)
			Toast.makeText(activity, "Не удалось подключиться к службе", Toast.LENGTH_LONG).show();
		else {
			switch (result.state) {
				case 1:
					User user = new User(result.user.id, result.user.username);
					application.setUser(user);
					Intent intent = new Intent(activity, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					activity.startActivity(intent);
					break;
				case 2:
					Toast.makeText(activity, "Неверный логин или пароль", Toast.LENGTH_LONG).show();
					break;
				case 3:
					Toast.makeText(activity, "Данный пользователь не активирован", Toast.LENGTH_LONG).show();
					break;
				default:
				case 0:
					Toast.makeText(activity, "Данный пользователь удален", Toast.LENGTH_LONG).show();
					break;
			}
		}
		activity.unlock();
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		Toast.makeText(activity, "Не удалось подключиться к службе", Toast.LENGTH_LONG).show();
		activity.unlock();
	}
}
