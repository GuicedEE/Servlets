package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.services.scopes.CallScope;
import jakarta.servlet.ServletContext;
import lombok.Setter;

@CallScope
public class HttpServletContextProvider implements Provider<ServletContext>
{
	@Setter
	private static ServletContext override;
	
	@Override
	public ServletContext get()
	{
		try
		{
			return GuiceContext.get(ServletContext.class);
		} catch (Throwable T)
		{
			if (override == null)
				throw new IllegalStateException("Servlet Context is not bound to anything");
			else
				return override;
		}
	}
}
