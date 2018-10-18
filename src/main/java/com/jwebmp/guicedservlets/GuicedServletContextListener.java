package com.jwebmp.guicedservlets;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.jwebmp.guicedinjection.GuiceContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Loads Guice Context into the servlet container as a listener
 */
@WebListener
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

	/**
	 * Method contextDestroyed ...
	 *
	 * @param servletContextEvent
	 * 		of type ServletContextEvent
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		super.contextDestroyed(servletContextEvent);
		GuiceContext.destroy();
	}

	/**
	 * Method getInjector returns the injector of this GuicedServletContextListener object.
	 *
	 * @return the injector (type Injector) of this GuicedServletContextListener object.
	 */
	@Override
	protected Injector getInjector()
	{
		return GuiceContext.inject();
	}
}
