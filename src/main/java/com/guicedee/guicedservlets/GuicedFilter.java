package com.guicedee.guicedservlets;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;

import com.guicedee.guicedservlets.services.scopes.CallScoper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Enables web scopes for guiced items
 */
@WebFilter(urlPatterns = "/*",
		asyncSupported = true)
public class GuicedFilter
		extends GuiceFilter
{
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
		scope.enter();
		super.doFilter(servletRequest, servletResponse, filterChain);
		scope.exit();
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
}
