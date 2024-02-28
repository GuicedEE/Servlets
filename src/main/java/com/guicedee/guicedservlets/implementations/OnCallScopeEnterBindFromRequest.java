package com.guicedee.guicedservlets.implementations;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.OutOfScopeException;
import com.google.inject.Scope;
import com.google.inject.servlet.ServletScopes;
import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedservlets.services.*;
import com.guicedee.guicedservlets.services.scopes.CallScoper;
import com.guicedee.guicedservlets.servlets.services.IOnCallScopeEnter;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class OnCallScopeEnterBindFromRequest implements IOnCallScopeEnter<OnCallScopeEnterBindFromRequest>
{
	@Override
	public void onScopeEnter(Scope scoper)
	{
		ServletContext servletContext = IGuiceContext.get(ServletContext.class);// servletContextProvider.get();
		scoper.scope(Key.get(ServletContext.class), () -> servletContext);
		try
		{
			ServletScopes.transferRequest();
			scoper.scope(Key.get(ServletRequest.class), () -> IGuiceContext.get(ServletRequest.class));
			scoper.scope(Key.get(HttpServletRequest.class), () -> IGuiceContext.get(HttpServletRequest.class));
			scoper.scope(Key.get(ServletResponse.class), () -> IGuiceContext.get(ServletResponse.class));
			scoper.scope(Key.get(HttpServletResponse.class), () -> IGuiceContext.get(HttpServletResponse.class));
			//scoper.seed(HttpSession.class, GuiceContext.get(HttpSession.class));
		} catch (OutOfScopeException oe)
		{
			log.log(Level.FINE, "Call Scope started without a sesion", oe);
		}
	}
}
