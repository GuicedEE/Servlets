package com.jwebmp.guicedservlets;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A listener for current sessions
 */
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
	 * @return A map of string and http sessions
	 */
	public static Map<String, HttpSession> getSessionMap()
	{
		return sessionMap;
	}

	/**
	 * Method valueBound ...
	 *
	 * @param event
	 * 		of type HttpSessionBindingEvent
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event)
	{
		sessionMap.put(event.getSession()
		                    .getId(), event.getSession());
	}

	/**
	 * Method valueUnbound ...
	 *
	 * @param event
	 * 		of type HttpSessionBindingEvent
	 */
	@Override
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		sessionMap.remove(event.getSession()
		                       .getId());
	}

	/**
	 * Method sessionCreated ...
	 *
	 * @param se
	 * 		of type HttpSessionEvent
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
		sessionMap.put(se.getSession()
		                 .getId(), se.getSession());
	}

	/**
	 * Method sessionDestroyed ...
	 *
	 * @param se
	 * 		of type HttpSessionEvent
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		sessionMap.remove(se.getSession()
		                    .getId());
	}
}
