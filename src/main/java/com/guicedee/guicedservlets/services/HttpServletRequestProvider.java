package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.services.mocks.MockRequest;
import com.guicedee.guicedservlets.services.scopes.CallScope;
import jakarta.servlet.http.HttpServletRequest;

@CallScope
public class HttpServletRequestProvider implements Provider<HttpServletRequest> {

    @Override
    public HttpServletRequest get() {
        try
        {
            return GuiceContext.get(HttpServletRequest.class);
        }catch (Throwable T)
        {
            return new MockRequest();
        }
    }
}
