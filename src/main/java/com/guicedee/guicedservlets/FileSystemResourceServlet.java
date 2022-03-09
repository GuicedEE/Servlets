package com.guicedee.guicedservlets;

import com.google.inject.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.nio.file.*;

@Singleton
public class FileSystemResourceServlet extends StaticResourceServlet
{
	private File folder;
	
	public FileSystemResourceServlet setFolder(File folder)
	{
		this.folder = folder;
		return this;
	}
	
	@Override
	public void init() throws ServletException
	{
	}
	
	@Override
	protected StaticResource getStaticResource(HttpServletRequest request) throws IllegalArgumentException
	{
		String pathInfo = request.getPathInfo();
		
		if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo))
		{
			pathInfo = "/index.html";
		}
		
		String name = null;
		try
		{
			name = URLDecoder.decode(pathInfo.substring(1), StandardCharsets.UTF_8.name());
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		File file = new File(folder, Paths.get(name)
		                                  .getFileName()
		                                  .toString());
		if (!file.exists())
		{
			file = new File(folder, Paths.get("index.html")
			                             .getFileName()
			                             .toString());
		}
		
		File finalFile = file;
		return !file.exists() ? null : new StaticResource()
		{
			@Override
			public long getLastModified()
			{
				return finalFile.lastModified();
			}
			
			@Override
			public InputStream getInputStream() throws IOException
			{
				return new FileInputStream(finalFile);
			}
			
			@Override
			public String getFileName()
			{
				return finalFile.getName();
			}
			
			@Override
			public long getContentLength()
			{
				return finalFile.length();
			}
		};
	}
	
}