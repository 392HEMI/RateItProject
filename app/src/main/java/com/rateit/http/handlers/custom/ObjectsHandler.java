/*
package com.rateit.http.handlers.custom;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.adapters.ObjectsAdapter;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.GeneralObject;
import com.rateit.androidapplication.models.ObjectsModel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ObjectsHandler implements IJsonResponseHandler<ObjectsModel> {
	private Activity _activity;
	private ListView _listView;
	
	public ObjectsHandler(Activity activity, ListView listView)
	{
		_activity = activity;
		_listView = listView;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSuccess(int statusCode, ObjectsModel[] model) {
	}	
	
	@Override
	public void onSuccess(int statusCode, ObjectsModel model) {
		attachAdapter(_listView, model.objects);
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		// TODO Auto-generated method stub
	}
	
    private void attachAdapter(final ListView listView, GeneralObject[] objects)
    {
    	ObjectsAdapter adapter = new ObjectsAdapter(_activity, objects);
    	listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				GeneralObject obj = (GeneralObject)parent.getItemAtPosition(position);
				Intent intent = new Intent(_activity.getApplicationContext(), ObjectActivity.class);
				intent.putExtra("objectID", obj.id);
				_activity.startActivity(intent);
			}
		});
    	listView.setAdapter(adapter);
    }
}
*/
