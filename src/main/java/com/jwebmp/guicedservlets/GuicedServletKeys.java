package com.jwebmp.guicedservlets;

import com.google.inject.Key;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Keys holder for Guice Servlet items - Exists purely to override in test cases
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class GuicedServletKeys
{
	/**
	 * The key to get the servlet context
	 */
	private static Key<ServletContext> ServletContextKey;
	/**
	 * The key to get the servlet request
	 */
	private static Key<HttpServletRequest> HttpServletRequestKey;
	/**
	 * The key to get the servlet response
	 */
	private static Key<HttpServletResponse> HttpServletResponseKey;
	/**
	 * The key to get the session
	 */
	private static Key<HttpSession> HttpSessionKey;
	/**
	 * The key to get the servlet request
	 */
	private static Key<ServletRequest> ServletRequestKey;
	/**
	 * The key to get the servlet response
	 */
	private static Key<ServletResponse> ServletResponseKey;

	/**
	 * Does nothing but instantiate
	 */
	public GuicedServletKeys()
	{
		//No config required
	}

	/**
	 * Method getServletContextKey returns the ServletContextKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet context
	 *
	 * @return the ServletContextKey (type Key<ServletContext>) of this GuicedServletKeys object.
	 */
	public static Key<ServletContext> getServletContextKey()
	{
		return GuicedServletKeys.ServletContextKey;
	}

	/**
	 * Method setServletContextKey sets the ServletContextKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet context
	 *
	 * @param servletContextKey
	 * 		the ServletContextKey of this GuicedServletKeys object.
	 */
	public static void setServletContextKey(Key<ServletContext> servletContextKey)
	{
		GuicedServletKeys.ServletContextKey = servletContextKey;
	}

	/**
	 * Method getHttpServletRequestKey returns the HttpServletRequestKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet request
	 *
	 * @return the HttpServletRequestKey (type Key<HttpServletRequest>) of this GuicedServletKeys object.
	 */
	public static Key<HttpServletRequest> getHttpServletRequestKey()
	{
		return GuicedServletKeys.HttpServletRequestKey;
	}

	/**
	 * Method setHttpServletRequestKey sets the HttpServletRequestKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet request
	 *
	 * @param httpServletRequestKey
	 * 		the HttpServletRequestKey of this GuicedServletKeys object.
	 */
	public static void setHttpServletRequestKey(Key<HttpServletRequest> httpServletRequestKey)
	{
		GuicedServletKeys.HttpServletRequestKey = httpServletRequestKey;
	}

	/**
	 * Method getHttpServletResponseKey returns the HttpServletResponseKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet response
	 *
	 * @return the HttpServletResponseKey (type Key<HttpServletResponse>) of this GuicedServletKeys object.
	 */
	public static Key<HttpServletResponse> getHttpServletResponseKey()
	{
		return GuicedServletKeys.HttpServletResponseKey;
	}

	/**
	 * Method setHttpServletResponseKey sets the HttpServletResponseKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet response
	 *
	 * @param httpServletResponseKey
	 * 		the HttpServletResponseKey of this GuicedServletKeys object.
	 */
	public static void setHttpServletResponseKey(Key<HttpServletResponse> httpServletResponseKey)
	{
		GuicedServletKeys.HttpServletResponseKey = httpServletResponseKey;
	}

	/**
	 * Method getHttpSessionKey returns the HttpSessionKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the session
	 *
	 * @return the HttpSessionKey (type Key<HttpSession>) of this GuicedServletKeys object.
	 */
	public static Key<HttpSession> getHttpSessionKey()
	{
		return GuicedServletKeys.HttpSessionKey;
	}

	/**
	 * Method setHttpSessionKey sets the HttpSessionKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the session
	 *
	 * @param httpSessionKey
	 * 		the HttpSessionKey of this GuicedServletKeys object.
	 */
	public static void setHttpSessionKey(Key<HttpSession> httpSessionKey)
	{
		GuicedServletKeys.HttpSessionKey = httpSessionKey;
	}

	/**
	 * Method getServletRequestKey returns the ServletRequestKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet request
	 *
	 * @return the ServletRequestKey (type Key<ServletRequest>) of this GuicedServletKeys object.
	 */
	public static Key<ServletRequest> getServletRequestKey()
	{
		return GuicedServletKeys.ServletRequestKey;
	}

	/**
	 * Method setServletRequestKey sets the ServletRequestKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet request
	 *
	 * @param servletRequestKey
	 * 		the ServletRequestKey of this GuicedServletKeys object.
	 */
	public static void setServletRequestKey(Key<ServletRequest> servletRequestKey)
	{
		GuicedServletKeys.ServletRequestKey = servletRequestKey;
	}

	/**
	 * Method getServletResponseKey returns the ServletResponseKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet response
	 *
	 * @return the ServletResponseKey (type Key<ServletResponse>) of this GuicedServletKeys object.
	 */
	public static Key<ServletResponse> getServletResponseKey()
	{
		return GuicedServletKeys.ServletResponseKey;
	}

	/**
	 * Method setServletResponseKey sets the ServletResponseKey of this GuicedServletKeys object.
	 * <p>
	 * The key to get the servlet response
	 *
	 * @param servletResponseKey
	 * 		the ServletResponseKey of this GuicedServletKeys object.
	 */
	public static void setServletResponseKey(Key<ServletResponse> servletResponseKey)
	{
		GuicedServletKeys.ServletResponseKey = servletResponseKey;
	}
}
