package com.jwebmp.guicedservlets;

import com.jwebmp.logger.LogFactory;

import java.util.logging.Logger;

/**
 * Binds Caching Annotations for the Servlets Binder
 */
@SuppressWarnings("unused")
public class GuicedServletsBinder
		extends GuiceSiteBinder
{
	private static final Logger log = LogFactory.getLog("GuicedServletsBinder");

	@Override
	public void onBind(GuiceSiteInjectorModule module)
	{
	}

}
