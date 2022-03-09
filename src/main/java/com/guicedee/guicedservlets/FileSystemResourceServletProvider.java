package com.guicedee.guicedservlets;

import com.google.inject.*;

import java.io.*;

public class FileSystemResourceServletProvider implements Provider<FileSystemResourceServlet>
{
	private File folder;
	
	public FileSystemResourceServletProvider(File folder)
	{
		this.folder = folder;
	}
	
	@Override
	public FileSystemResourceServlet get()
	{
		return new FileSystemResourceServlet().setFolder(folder);
	}
}
