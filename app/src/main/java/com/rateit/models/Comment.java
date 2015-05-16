package com.rateit.models;

import com.rateit.models.User;

import java.util.UUID;

public class Comment
{
	public int ID;
	public UUID UserID;
	public String UserAvatar;
	public String UserName;
	public String UserSurname;
	public String Text;
	public int LikesCount;
	public Boolean IsLike;

	public Comment()
	{
		
	}
}