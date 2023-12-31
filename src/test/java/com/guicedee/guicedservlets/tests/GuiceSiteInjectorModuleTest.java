package com.guicedee.guicedservlets.tests;

import com.google.inject.Key;
import com.google.inject.servlet.RequestParameters;
import com.google.inject.servlet.ServletScopes;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.GuicedServletKeys;
import com.guicedee.guicedservlets.services.scopes.CallScoper;
import com.guicedee.guicedservlets.tests.mocks.MockHTTPSession;
import com.guicedee.guicedservlets.tests.mocks.MockRequest;
import com.guicedee.guicedservlets.tests.mocks.MockResponse;
import com.guicedee.guicedservlets.tests.mocks.MockServletContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Convert2Diamond")
@Log
public class GuiceSiteInjectorModuleTest
{
	private static final Key<HttpServletRequest> HTTP_REQ_KEY = Key.get(HttpServletRequest.class);
	private static final Key<HttpServletResponse> HTTP_RESP_KEY = Key.get(HttpServletResponse.class);
	private static final Key<Map<String, String[]>> REQ_PARAMS_KEY = new Key<Map<String, String[]>>(RequestParameters.class)
	{
	};
	
	@Test
	void testFakeRequestScope()
	{
		GuiceContext.inject();
		
		ServletScopes.scopeRequest(new HashMap<>(Map.of(
										Key.get(ServletContext.class), new MockServletContext(),
										Key.get(ServletRequest.class), new MockRequest(),
										Key.get(HttpServletRequest.class), new MockRequest(),
										Key.get(ServletResponse.class), new MockResponse(),
										Key.get(HttpServletResponse.class), new MockResponse(),
										Key.get(HttpSession.class), new MockHTTPSession()
						)))
						.open();
		
		RequestScopedObject obj = GuiceContext.get(RequestScopedObject.class);
		BasicServlet servlet = GuiceContext.get(BasicServlet.class);
		
		HttpServletRequest req = GuiceContext.get(HttpServletRequest.class);
		CallScoper callScoper = GuiceContext.get(CallScoper.class);
		callScoper.enter();
		
		HttpServletRequest req2 = GuiceContext.get(HttpServletRequest.class);
		HttpServletResponse resp2 = GuiceContext.get(HttpServletResponse.class);
		HttpServletResponse resp = GuiceContext.get(GuicedServletKeys.getHttpServletResponseKey());
		callScoper.exit();
		
		System.out.println(obj);
	}
	
}
