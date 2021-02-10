package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.guicedservlets.services.mocks.MockHTTPSession;
import com.guicedee.guicedservlets.services.scopes.CallScope;
import jakarta.servlet.http.HttpSession;

@CallScope
public class HttpSessionProvider implements Provider<HttpSession> {

    @Override
    public HttpSession get() {
        try
        {
            return GuiceContext.get(HttpSession.class);
        }catch (Throwable T)
        {
            return new MockHTTPSession();
        }
    }
}
