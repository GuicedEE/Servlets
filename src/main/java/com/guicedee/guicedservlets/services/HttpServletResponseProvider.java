package com.guicedee.guicedservlets.services;

import com.google.inject.Provider;
import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedservlets.servlets.services.scopes.CallScope;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;

@CallScope
public class HttpServletResponseProvider implements Provider<HttpServletResponse> {
    
    @Setter
    private static HttpServletResponse override;
    
    @Override
    public HttpServletResponse get() {
        try {
            return IGuiceContext.get(HttpServletResponse.class);
        } catch (Throwable T) {
            if(override != null)
                return override;
            throw new IllegalStateException("HttpServletResponse is not bound to anything");
        }
    }
}
