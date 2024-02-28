package com.guicedee.guicedservlets.tests;

import com.google.inject.servlet.*;
import com.guicedee.guicedinjection.interfaces.*;
import com.guicedee.guicedservlets.services.*;
import com.guicedee.guicedservlets.tests.mocks.*;

public class ServletTestBindings extends ServletModule implements IGuiceModule<ServletTestBindings>
{
	@Override
	protected void configureServlets()
	{
		HttpServletContextProvider.setOverride(new MockServletContext());
		HttpSessionProvider.setOverride(new MockHTTPSession());
		HttpServletRequestProvider.setOverride(new MockRequest());
		HttpServletResponseProvider.setOverride(new MockResponse());
	}
	
}
