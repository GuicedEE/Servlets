package com.guicedee.guicedservlets;

import com.guicedee.guicedservlets.services.scopes.*;

import java.io.*;

@CallScope
public class CallScopeProperties implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	private boolean webCall;
	
	/**
	 * If this call scope is from a web request call
	 *
	 * @return
	 */
	public boolean isWebCall()
	{
		return webCall;
	}
	
	/**
	 * If this call scope is from a web request call
	 *
	 * @param webCall
	 * @return
	 */
	public CallScopeProperties setWebCall(boolean webCall)
	{
		this.webCall = webCall;
		return this;
	}
}
