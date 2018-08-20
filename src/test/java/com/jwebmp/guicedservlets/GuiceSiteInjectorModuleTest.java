package com.jwebmp.guicedservlets;

import com.google.inject.Key;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.ServletScopes;
import com.jwebmp.guicedinjection.GuiceContext;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.jwebmp.guicedservlets.GuicedServletKeys.*;

class GuiceSiteInjectorModuleTest
{
	private static final Key<HttpServletRequest> HTTP_REQ_KEY = Key.get(HttpServletRequest.class);
	private static final Key<HttpServletResponse> HTTP_RESP_KEY = Key.get(HttpServletResponse.class);
	private static final Key<Map<String, String[]>> REQ_PARAMS_KEY =
			new Key<Map<String, String[]>>(RequestParameters.class) {};

	/**
	 * Returns a FilterChain that does nothing.
	 */
	public static FilterChain newNoOpFilterChain()
	{
		return new FilterChain()
		{
			@Override
			public void doFilter(ServletRequest request, ServletResponse response)
			{
			}
		};
	}

	@Test
	void testFakeRequestScope()
	{
		GuiceContext.inject();
		ServletScopes.scopeRequest(new HashMap<>())
		             .open();

		RequestScopedObject obj = GuiceContext.getInstance(RequestScopedObject.class);
		BasicServlet servlet = GuiceContext.getInstance(BasicServlet.class);

		HttpServletResponse resp = GuiceContext.getInstance(HttpServletResponseKey);

		System.out.println(obj);
	}

}