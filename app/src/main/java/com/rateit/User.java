package com.rateit;

import java.util.UUID;

public class User {
	private UUID id;
	private String name;
	
	public UUID getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public User(UUID _id, String _name)
	{
		id = _id;
		name = _name;
	}
}
