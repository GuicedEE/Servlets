package com.guicedee.guicedservlets.tests;

import com.google.inject.servlet.ServletModule;
import com.guicedee.guicedservlets.GuicedServletKeys;
import com.guicedee.guicedservlets.services.*;
import com.guicedee.guicedservlets.servlets.services.IGuiceSiteBinder;
import com.guicedee.guicedservlets.tests.mocks.MockHTTPSession;
import com.guicedee.guicedservlets.tests.mocks.MockRequest;
import com.guicedee.guicedservlets.tests.mocks.MockResponse;
import com.guicedee.guicedservlets.tests.mocks.MockServletContext;

public class ServletTestBindings
	extends ServletModule
		implements IGuiceSiteBinder<ServletTestBindings>
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
