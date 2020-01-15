package com.guicedee.guicedservlets.implementations;

import com.google.inject.Key;
import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import com.guicedee.guicedservlets.GuicedServletKeys;
import com.guicedee.logger.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public class GuiceServletKeyStartup
		implements IGuicePreStartup<GuiceServletKeyStartup>
{
	private static final Logger log = LogFactory.getLog("GuiceServletKeys");

	@Override
	public void onStartup()
	{
		try
		{
			GuicedServletKeys.setHttpServletRequestKey(Key.get(HttpServletRequest.class));
			GuicedServletKeys.setHttpServletResponseKey(Key.get(HttpServletResponse.class));
			GuicedServletKeys.setHttpSessionKey(Key.get(HttpSession.class));
			GuicedServletKeys.setServletContextKey(Key.get(ServletContext.class));
			GuicedServletKeys.setServletRequestKey(Key.get(ServletRequest.class));
			GuicedServletKeys.setServletResponseKey(Key.get(ServletResponse.class));

			log.fine("Bound HttpServletResponse Key");
			log.fine("Bound HttpServletRequest Key");
			log.fine("Bound HttpSession Key");
			log.fine("Bound ServletContext Key");
			log.fine("Bound ServletRequest Key");
			log.fine("Bound ServletResponse Key");
		}catch(Throwable T)
		{
			log.warning("Unable to start servlet structure");
		}
	}
}
