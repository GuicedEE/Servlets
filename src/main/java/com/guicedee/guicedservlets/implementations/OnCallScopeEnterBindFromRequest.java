package com.guicedee.guicedservlets.implementations;

import com.google.inject.Inject;
import com.google.inject.OutOfScopeException;
import com.google.inject.servlet.ServletScopes;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.services.*;
import com.guicedee.guicedservlets.services.scopes.CallScoper;
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
	public void onScopeEnter(CallScoper scoper)
	{
		ServletContext servletContext = GuiceContext.get(ServletContext.class);// servletContextProvider.get();
		scoper.seed(ServletContext.class, servletContext);
		try
		{
			ServletScopes.transferRequest();
			scoper.seed(ServletRequest.class, GuiceContext.get(ServletRequest.class));
			scoper.seed(HttpServletRequest.class, GuiceContext.get(HttpServletRequest.class));
			scoper.seed(ServletResponse.class, GuiceContext.get(ServletResponse.class));
			scoper.seed(HttpServletResponse.class, GuiceContext.get(HttpServletResponse.class));
			//scoper.seed(HttpSession.class, GuiceContext.get(HttpSession.class));
		}catch (OutOfScopeException oe)
		{
			log.log(Level.FINE,"Call Scope started without a sesion",oe);
		}
	}
}
