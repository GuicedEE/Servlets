package com.jwebmp.guicedservlets;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.jwebmp.guicedinjection.GuiceContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads Guice Context into the servlet container as a listener
 */
public class GuicedServletContextListener
		extends GuiceServletContextListener
{

	/**
	 * Initializes Guice Context post Startup Beans
	 *
	 * @param servletContextEvent
	 * 		The injected servlet context event from an EE server
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		super.contextInitialized(servletContextEvent);
		getInjector();
	}

	@Override
	protected Injector getInjector()
	{
		return GuiceContext.inject();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		super.contextDestroyed(servletContextEvent);
	}
}
