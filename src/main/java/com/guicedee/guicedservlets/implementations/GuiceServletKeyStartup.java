package com.guicedee.guicedservlets.implementations;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import com.guicedee.guicedservlets.GuicedServletKeys;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

@Log
public class GuiceServletKeyStartup
		implements IGuicePreStartup<GuiceServletKeyStartup>
{

	@Override
	public void onStartup()
	{
		try
		{
			GuicedServletKeys.setHttpServletRequestKey(Key.get(HttpServletRequest.class, Names.named("Servlet")));
			GuicedServletKeys.setHttpServletResponseKey(Key.get(HttpServletResponse.class, Names.named("Servlet")));
			GuicedServletKeys.setHttpSessionKey(Key.get(HttpSession.class, Names.named("Servlet")));
			GuicedServletKeys.setServletContextKey(Key.get(ServletContext.class, Names.named("Servlet")));
			GuicedServletKeys.setServletRequestKey(Key.get(ServletRequest.class, Names.named("Servlet")));
			GuicedServletKeys.setServletResponseKey(Key.get(ServletResponse.class, Names.named("Servlet")));

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
