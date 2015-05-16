/*
package com.rateit.http.handlers.custom;

import java.util.ArrayList;
import java.util.UUID;

import android.util.Log;

import com.rateit.androidapplication.ObjectActivity;
import com.rateit.androidapplication.http.handlers.IJsonResponseHandler;
import com.rateit.androidapplication.models.Comment;

public class GetCommentsPageHandler implements IJsonResponseHandler<Comment> {
	private ObjectActivity activity;
	private UUID userID;
	
	public GetCommentsPageHandler(ObjectActivity _activity)
	{
		activity = _activity;
		userID = activity.GetRateItApplication().getUser().getID();
	}
	
	@Override
	public void onStart() {
	}
	
	@Override
	public void onFinish() {
		
	}

	@Override
	public void onSuccess(int statusCode, Comment[] comments) {
		int count = comments.length;
		ArrayList<Comment> coms = new ArrayList<Comment>(count);
		
		for (Comment comment : comments)
			if (!comment.User.ID.equals(userID))
				coms.add(comment);
		
		activity.addComments(coms, true, true);
	}
	
	@Override
	public void onSuccess(int statusCode, Comment response) {
	}

	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		// TODO Auto-generated method stub
	}

}
*/
