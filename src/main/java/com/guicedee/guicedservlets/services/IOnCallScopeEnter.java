package com.guicedee.guicedservlets.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;

@FunctionalInterface
public interface IOnCallScopeEnter<J extends IOnCallScopeExit<J>> extends IDefaultService<J> {
    void onScopeEnter();
}
