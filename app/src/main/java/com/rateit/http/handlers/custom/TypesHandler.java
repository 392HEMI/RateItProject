/*
package com.rateit.http.handlers.custom;

import com.rateit.MainActivity;
import com.rateit.http.handlers.IJsonResponseHandler;
import com.rateit.models.Type;

import com.rateit.adapters.TypeAdapter;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TypesHandler implements IJsonResponseHandler<Type> {
	private MainActivity _activity;
	private ListView _listView;
	private int _typeID;

	public TypesHandler(MainActivity activity, ListView listView, int typeID)
	{
		_activity = activity;
		_listView = listView;
		_typeID = typeID;
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
	public void onSuccess(int statusCode, Type[] model)
	{
		if (model.length > 0) {
			attachAdapter(_listView, model);
			return;
		}
	}

	@Override
	public void onSuccess(int statusCode, Type model) {
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
	}
	
    private void attachAdapter(final ListView listView, Type[] types)
    {
    	int count = types.length;
    	TypeAdapter adapter = new TypeAdapter(_activity, types);		
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> types, View view, int position, long id) {
				Type type = (Type)types.getItemAtPosition(position);
				_activity.invokeGetObjects(type.id, true);
			}
		});
    }
}*/
