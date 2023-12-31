package com.guicedee.guicedservlets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import com.guicedee.guicedservlets.services.scopes.CallScoper;

@FunctionalInterface
public interface IOnCallScopeEnter<J extends IOnCallScopeEnter<J>> extends IDefaultService<J> {
    void onScopeEnter(CallScoper scoper);
}
