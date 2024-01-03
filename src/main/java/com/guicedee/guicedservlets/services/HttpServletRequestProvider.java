package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.servlets.services.scopes.CallScope;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

@CallScope
public class HttpServletRequestProvider implements Provider<HttpServletRequest>
{
	
	@Setter
	private static HttpServletRequest override;
	
	@Override
	public HttpServletRequest get()
	{
		try
		{
			return GuiceContext.get(HttpServletRequest.class);
		} catch (Throwable T)
		{
			if (override != null)
				return override;
			throw new IllegalStateException("HttpServletRequest is not bound to anything");
		}
	}
}
