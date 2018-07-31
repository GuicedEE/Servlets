package com.jwebmp.guicedservlets;

import javax.servlet.http.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuicedServletSessionManager implements HttpSessionBindingListener, HttpSessionListener
{
	private static final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

	@Override
	public void valueBound(HttpSessionBindingEvent event)
	{
		sessionMap.put(event.getSession()
		                    .getId(), event.getSession());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		sessionMap.remove(event.getSession()
		                       .getId());
	}

	/**
	 * Returns a map of all the currently allocated sessions
	 * @return
	 */
	public static Map<String, HttpSession> getSessionMap()
	{
		return sessionMap;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
		sessionMap.put(se.getSession()
		                    .getId(), se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		sessionMap.remove(se.getSession()
		                       .getId());
	}
}
