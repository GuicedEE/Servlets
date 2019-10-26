package com.guicedee.guicedservlets;

import com.guicedee.guicedservlets.mocks.MockHTTPSession;
import com.guicedee.guicedservlets.mocks.MockRequest;
import com.guicedee.guicedservlets.mocks.MockResponse;
import com.guicedee.guicedservlets.mocks.MockServletContext;
import com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
import com.guicedee.guicedservlets.services.IGuiceSiteBinder;

public class ServletTestBindings
		implements IGuiceSiteBinder<GuiceSiteInjectorModule>
{
	@Override
	public void onBind(GuiceSiteInjectorModule module)
	{
		module.serveRegex$("/sitetest")
		      .with(BasicServlet.class);

		module.bind(GuicedServletKeys.getHttpSessionKey())
		      .to(MockHTTPSession.class);
		module.bind(GuicedServletKeys.getServletRequestKey())
		      .to(MockRequest.class);

		module.bind(GuicedServletKeys.getHttpServletRequestKey())
		      .to(MockRequest.class);

		module.bind(GuicedServletKeys.getServletResponseKey())
		      .to(MockResponse.class);
		module.bind(GuicedServletKeys.getHttpServletResponseKey())
		      .to(MockResponse.class);

		module.bind(GuicedServletKeys.getServletContextKey())
		      .to(MockServletContext.class);
	}
}
