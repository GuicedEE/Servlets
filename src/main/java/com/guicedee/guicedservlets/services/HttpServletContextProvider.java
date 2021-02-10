package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.services.mocks.MockHTTPSession;
import com.guicedee.guicedservlets.services.mocks.MockServletContext;
import com.guicedee.guicedservlets.services.scopes.CallScope;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@CallScope
public class HttpServletContextProvider implements Provider<ServletContext> {

    @Override
    public ServletContext get() {
        try
        {
            return GuiceContext.get(ServletContext.class);
        }catch (Throwable T)
        {
            return new MockServletContext();
        }
    }
}
