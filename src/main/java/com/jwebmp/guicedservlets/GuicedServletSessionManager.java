package com.jwebmp.guicedservlets;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class GuicedServletSessionManager
		implements HttpSessionBindingListener, HttpSessionListener
{
	/**
	 * A session mapping
	 */
	private static final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

	/**
	 * Returns a map of all the currently allocated sessions
	 *
	 * @return
	 */
	public static Map<String, HttpSession> getSessionMap()
	{
		return sessionMap;
	}

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
