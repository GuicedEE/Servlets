package com.guicedee.guicedservlets;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;

import com.guicedee.guicedinjection.*;
import com.guicedee.guicedservlets.services.scopes.CallScoper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

import static com.guicedee.guicedservlets.GuicedServletKeys.*;

/**
 * Enables web scopes for guiced items
 */
@WebFilter(urlPatterns = "/*",
		asyncSupported = true)
public class GuicedFilter
		extends GuiceFilter
{
	private static boolean killSessionOnRequestClosed = true;
	
	@Inject
	@Named("callScope")
	CallScoper scope;

	/**
	 * Constructor GuicedFilter creates a new GuicedFilter instance.
	 */
	public GuicedFilter()
	{
		super();
	}

	/**
	 * Method doFilter ...
	 *
	 * @param servletRequest
	 * 		of type ServletRequest
	 * @param servletResponse
	 * 		of type ServletResponse
	 * @param filterChain
	 * 		of type FilterChain
	 *
	 * @throws IOException
	 * 		when
	 * @throws ServletException
	 * 		when
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		try {
			scope.enter();
			CallScopeProperties properties = GuiceContext.get(CallScopeProperties.class);
			properties.setWebCall(true);
		}catch (java.lang.IllegalStateException T)
		{
			//already in scope
		}
		try {
			super.doFilter(servletRequest, servletResponse, filterChain);
		}finally {
			scope.exit();
		}
		if (killSessionOnRequestClosed)
		{
			GuicedServletSessionManager.getSessionMap().forEach((key,value) ->{
				value.invalidate();
			});
		}
	}

	/**
	 * Method init ...
	 *
	 * @param filterConfig
	 * 		of type FilterConfig
	 *
	 * @throws ServletException
	 * 		when
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		super.init(filterConfig);
	}

	/**
	 * Method destroy ...
	 */
	@Override
	public void destroy()
	{
		super.destroy();
	}
	
	public static boolean isKillSessionOnRequestClosed()
	{
		return killSessionOnRequestClosed;
	}
	
	public static void setKillSessionOnRequestClosed(boolean killSessionOnRequestClosed)
	{
		GuicedFilter.killSessionOnRequestClosed = killSessionOnRequestClosed;
	}
}
