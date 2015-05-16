package com.rateit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rateit.R;
import com.rateit.models.Type;

public class TypeAdapter extends BaseAdapter {
	  Context ctx;
	  LayoutInflater lInflater;
	  Type[] objects;

	  public TypeAdapter(Context context, Type[] types) {
	    ctx = context;
	    objects = types;
	    lInflater = (LayoutInflater) ctx
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }

	  @Override
	  public int getCount() {
	    return objects.length;
	  }

	  @Override
	  public Object getItem(int position) {
	    return objects[position];
	  }

	  @Override
	  public long getItemId(int position) {
	    return position;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  View view = convertView;
		  if (view == null) {
			  view = lInflater.inflate(R.layout.list_menu_item_layout, parent, false);
		  }
		  Type type = (Type)getItem(position);
		  TextView label = (TextView)view.findViewById(R.id.title);
		  label.setText(type.title);
		  return view;
	  }
}
