package com.guicedee.guicedservlets;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import com.guicedee.client.*;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

import java.util.Collections;

/**
 * Loads Guice Context into the servlet container as a listener
 */
@WebListener
public class GuicedServletContextListener extends GuiceServletContextListener
{
	/**
	 * Disables the cookies, great for new apps
	 */
	public static boolean disableCookies = true;
	
	/**
	 * Initializes Guice Context post Startup Beans
	 *
	 * @param servletContextEvent The injected servlet context event from an EE server
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		if (disableCookies)
		{
			servletContextEvent
					.getServletContext()
					.setSessionTrackingModes(Collections.emptySet());
		}
		super.contextInitialized(servletContextEvent);
		getInjector();
	}
	
	/**
	 * Method contextDestroyed ...
	 *
	 * @param servletContextEvent of type ServletContextEvent
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		super.contextDestroyed(servletContextEvent);
		IGuiceContext.getContext().destroy();
	}
	
	/**
	 * Method getInjector returns the injector of this GuicedServletContextListener object.
	 *
	 * @return the injector (type Injector) of this GuicedServletContextListener object.
	 */
	@Override
	protected Injector getInjector()
	{
		return IGuiceContext
				       .getContext()
				       .inject();
	}
}
