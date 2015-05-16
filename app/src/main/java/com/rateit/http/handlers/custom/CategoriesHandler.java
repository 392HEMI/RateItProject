/*
package com.rateit.http.handlers.custom;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.rateit.RateItActivity;
import com.rateit.adapters.CategoryAdapter;
import com.rateit.http.HttpClient;
import com.rateit.http.SelectType;
import com.rateit.http.handlers.IJsonResponseHandler;
import com.rateit.models.Category;
import com.rateit.MainActivity;
import com.rateit.api.*;
import com.rateit.models.Type;

public class CategoriesHandler implements IJsonResponseHandler<Category> {
	private MainActivity _activity;
	private ListView _listView;
	private Integer _categoryId;
	private HttpClient httpClient;

	public CategoriesHandler(MainActivity activity, ListView listView, Integer categoryId)
	{
		_activity = activity;
		_listView = listView;
		_categoryId = categoryId;
		httpClient = activity.getHttpClient();
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
	public void onSuccess(int statusCode, Category[] model) {
		// если категории есть
		if (model.length > 0)
		{
			attachAdapter(_listView, model);
			return;
		}
		// иначе пытаемся запросить типы
		_activity.getTypes(_categoryId, false);
	}
	
	@Override
	public void onSuccess(int statusCode, Category model) {
	}
	
	@Override
	public void onFailure(int statusCode, Throwable error, String content) {

	}
	
    private void attachAdapter(final ListView listView, final Category[] categories)
    {
    	CategoryAdapter adapter = new CategoryAdapter(_activity, categories);
    	listView.setAdapter(adapter);
    	listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category category = (Category) parent.getItemAtPosition(position);
				_activity.getCategories(category.id);
			}
		});
    }
}
*/
