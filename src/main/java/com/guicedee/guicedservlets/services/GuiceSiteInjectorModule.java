/*
 * Copyright (C) 2017 GedMarc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.guicedee.guicedservlets.services;

import com.google.inject.servlet.ServletModule;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import com.guicedee.guicedservlets.GuicedServletKeys;
import com.guicedee.guicedservlets.servlets.services.scopes.CallScope;
import com.guicedee.client.CallScopeProperties;
import lombok.extern.java.Log;

/**
 * Exposes the site injection modules for consumption
 *
 * @author GedMarc
 * @since 12 Dec 2016
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Log
public class GuiceSiteInjectorModule extends ServletModule implements IGuiceModule<GuiceSiteInjectorModule>
{
	/**
	 * Default Constructor
	 */
	public GuiceSiteInjectorModule()
	{
		//Nothing needed
	}
	
	/**
	 * Default Sort Order 150
	 *
	 * @return 150
	 */
	@Override
	public Integer sortOrder()
	{
		return 150;
	}
	
	/**
	 * Runs the binders
	 */
	@Override
	public void configureServlets()
	{

		bind(GuicedServletKeys.getHttpSessionKey())
				.toProvider(HttpSessionProvider.class)
				.in(CallScope.class);
		
		bind(GuicedServletKeys.getServletRequestKey())
				.toProvider(HttpServletRequestProvider.class)
				.in(CallScope.class);
		
		bind(GuicedServletKeys.getHttpServletRequestKey())
				.toProvider(HttpServletRequestProvider.class)
				.in(CallScope.class);
		
		bind(GuicedServletKeys.getServletResponseKey())
				.toProvider(HttpServletResponseProvider.class)
				.in(CallScope.class);
		
		bind(GuicedServletKeys.getHttpServletResponseKey())
				.toProvider(HttpServletResponseProvider.class)
				.in(CallScope.class);
		
		bind(GuicedServletKeys.getServletContextKey())
				.toProvider(HttpServletContextProvider.class)
				.in(CallScope.class);
		
		bind(CallScopeProperties.class).in(CallScope.class);
		
		/*Set<IGuiceSiteBinder> loader = IGuiceContext.getLoader(IGuiceSiteBinder.class, true, ServiceLoader.load(IGuiceSiteBinder.class));
		for (IGuiceSiteBinder siteBinder : loader)
		{
			install((com.google.inject.Module) siteBinder);
			GuiceSiteInjectorModule.log.log(Level.CONFIG,
			                                "Loading IGuiceSiteBinder - {0}",
			                                siteBinder
					                                .getClass()
					                                .getCanonicalName());
		}*/
	}
}
