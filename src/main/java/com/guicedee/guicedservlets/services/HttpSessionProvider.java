package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.client.*;
import com.guicedee.guicedservlets.servlets.services.scopes.CallScope;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@CallScope
public class HttpSessionProvider implements Provider<HttpSession>
{
	
	@Setter
	private static HttpSession override;
	
	@Override
	public HttpSession get()
	{
		try
		{
			return IGuiceContext.get(HttpSession.class);
		} catch (Throwable T)
		{
			if (override != null)
			{
				return override;
			}
			throw new IllegalStateException("HttpSession is not bound to anything");
		}
	}
}
