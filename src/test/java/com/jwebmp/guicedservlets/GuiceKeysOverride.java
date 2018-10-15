package com.jwebmp.guicedservlets;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.jwebmp.guicedinjection.interfaces.IGuicePreStartup;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GuiceKeysOverride
		implements IGuicePreStartup<GuiceKeysOverride>
{
	@Override
	public void onStartup()
	{
		GuicedServletKeys.setHttpServletRequestKey(Key.get(HttpServletRequest.class, Names.named("TEST")));
		GuicedServletKeys.setHttpServletResponseKey(Key.get(HttpServletResponse.class, Names.named("TEST")));
		GuicedServletKeys.setHttpSessionKey(Key.get(HttpSession.class, Names.named("TEST")));
		GuicedServletKeys.setServletContextKey(Key.get(ServletContext.class, Names.named("TEST")));
		GuicedServletKeys.setServletRequestKey(Key.get(ServletRequest.class, Names.named("TEST")));
		GuicedServletKeys.setServletResponseKey(Key.get(ServletResponse.class, Names.named("TEST")));
	}

	/**
	 * Sort order for startup, Default 100.
	 *
	 * @return the sort order never null
	 */
	@Override
	public Integer sortOrder()
	{
		return 101;
	}
}
