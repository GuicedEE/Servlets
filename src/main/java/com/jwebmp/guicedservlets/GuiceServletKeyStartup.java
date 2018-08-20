package com.jwebmp.guicedservlets;

import com.google.inject.Key;
import com.jwebmp.guicedinjection.interfaces.IGuicePreStartup;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GuiceServletKeyStartup
		implements IGuicePreStartup
{
	@Override
	public void onStartup()
	{
		GuicedServletKeys.setHttpServletRequestKey(Key.get(HttpServletRequest.class));
		GuicedServletKeys.setHttpServletResponseKey(Key.get(HttpServletResponse.class));
		GuicedServletKeys.setHttpSessionKey(Key.get(HttpSession.class));
		GuicedServletKeys.setServletContextKey(Key.get(ServletContext.class));
		GuicedServletKeys.setServletRequestKey(Key.get(ServletRequest.class));
		GuicedServletKeys.setServletResponseKey(Key.get(ServletResponse.class));
	}
}
