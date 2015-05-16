package com.rateit.http.handlers.custom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;

import com.rateit.http.handlers.IFileResponseHandler;

public class ImageResponseHandler implements IFileResponseHandler {
	
	private String name;
	private String directory;
	private IFileDownloadCompleteHandler complete;
	
	private Context context;
	
	public ImageResponseHandler(Context _context, String _name, String _directory, IFileDownloadCompleteHandler _handler)
	{
		name = _name;
		directory = _directory;
		complete = _handler;
		context = _context;
	}

	@Override
	public void Success(byte[] imageData) {
		ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
		String path = "media";
		File dir = cw.getDir(path, Context.MODE_PRIVATE);
		path = "/images/" + directory;
		File image = new File(dir.getAbsolutePath() + path, name);
		image.mkdirs();
		try
		{
			if (!dir.exists())
				dir.createNewFile();
			if (image.exists())
			{
				image.delete();
				image.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(image);
			stream.write(imageData);
			stream.flush();
			stream.close();
		}
		catch (IOException e)
		{
			
		}
		complete.Complete(image);
	}
}
