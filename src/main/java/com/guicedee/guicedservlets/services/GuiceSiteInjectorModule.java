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

import com.google.inject.Module;
import com.google.inject.*;
import com.google.inject.binder.*;
import com.google.inject.matcher.*;
import com.google.inject.name.*;
import com.google.inject.servlet.*;
import com.google.inject.spi.*;
import com.guicedee.guicedinjection.*;
import com.guicedee.guicedinjection.interfaces.*;
import com.guicedee.guicedservlets.*;
import com.guicedee.guicedservlets.services.scopes.*;
import com.guicedee.logger.*;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

/**
 * Exposes the site injection modules for consumption
 *
 * @author GedMarc
 * @since 12 Dec 2016
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class GuiceSiteInjectorModule
		extends ServletModule
		implements IGuiceModule<GuiceSiteInjectorModule>
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("GuiceSiteInjectorModule");
	
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
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPattern   The url pattern
	 * @param morePatterns any additional patterns
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSite(String urlPattern, String... morePatterns)
	{
		return serve(urlPattern, morePatterns);
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPatterns The url pattern
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSite(Iterable<String> urlPatterns)
	{
		return serve(urlPatterns);
	}
	
	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regex   The regex to apply oto serve
	 * @param regexes The regexes to apply
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSiteRegex(String regex, String... regexes)
	{
		return serveRegex(regex, regexes);
	}
	
	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regexes The regexes to apply
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSiteRegex(Iterable<String> regexes)
	{
		return serveRegex(regexes);
	}
	
	@Override
	public com.google.inject.Binder binder()
	{
		return super.binder();
	}
	
	/**
	 * Runs the binders
	 */
	@Override
	public void configureServlets()
	{
		bindScope(CallScope.class, callScope);
		
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
		
		runBinders();
	}
	
	/**
	 * Runs the binders for the system
	 */
	@SuppressWarnings("unchecked")
	void runBinders()
	{
		Set<IGuiceSiteBinder> loader = GuiceContext.instance()
		                                           .getLoader(IGuiceSiteBinder.class, true, ServiceLoader.load(IGuiceSiteBinder.class));
		for (IGuiceSiteBinder siteBinder : loader)
		{
			GuiceSiteInjectorModule.log.log(Level.CONFIG, "Loading IGuiceSiteBinder - {0}", siteBinder.getClass()
			                                                                                          .getCanonicalName());
			siteBinder.onBind(this);
		}
	}
	
	/**
	 * Binds to a given scope
	 *
	 * @param scopeAnnotation The annotation
	 * @param scope           The scope to apply
	 */
	@Override
	public void bindScope(Class<? extends Annotation> scopeAnnotation, com.google.inject.Scope scope)
	{
		super.bindScope(scopeAnnotation, scope);
	}
	
	/**
	 * Binds to a given guice key
	 *
	 * @param <T> The type to bind
	 * @param key The key to bind
	 * @return The key builder
	 */
	@Override
	public <T> LinkedBindingBuilder<T> bind(com.google.inject.Key<T> key)
	{
		return super.bind(key);
	}
	
	/**
	 * Binds to a given type literal
	 *
	 * @param <T>         The type to bind
	 * @param typeLiteral The type literal to bind - create abstract instance of type
	 * @return The key builder
	 */
	@Override
	public <T> AnnotatedBindingBuilder<T> bind(com.google.inject.TypeLiteral<T> typeLiteral)
	{
		return super.bind(typeLiteral);
	}
	
	/**
	 * Binds to a given class
	 *
	 * @param <T>   The type to apply
	 * @param clazz The class to bind
	 * @return the key builder
	 */
	@Override
	public <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz)
	{
		return super.bind(clazz);
	}
	
	/**
	 * Binds to a given constant
	 *
	 * @return The key builder
	 */
	@Override
	public AnnotatedConstantBindingBuilder bindConstant()
	{
		return super.bindConstant();
	}
	
	/**
	 * Installs a given module
	 *
	 * @param module The module to install with this one
	 */
	@Override
	public void install(Module module)
	{
		try
		{
			super.install(module);
		}
		catch (Throwable T)
		{
			log.warning("Unable to install servlet guice module - " +
			            module.getClass()
			                  .getCanonicalName());
		}
	}
	
	/**
	 * Bind listener
	 *
	 * @param typeMatcher The type literal to match
	 * @param listener    The type listener for listening
	 */
	@Override
	public void bindListener(Matcher<? super com.google.inject.TypeLiteral<?>> typeMatcher, TypeListener listener)
	{
		super.bindListener(typeMatcher, listener);
	}
	
	/**
	 * Bind listener
	 *
	 * @param bindingMatcher The type to listen for
	 * @param listener       The listener
	 */
	@Override
	public void bindListener(Matcher<? super com.google.inject.Binding<?>> bindingMatcher, ProvisionListener... listener)
	{
		super.bindListener(bindingMatcher, listener);
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPatterns The url pattern to apply
	 * @return The filter key builder
	 */
	public FilterKeyBindingBuilder filter$(Iterable<String> urlPatterns)
	{
		return super.filter(urlPatterns);
	}
	
	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regex   The regex
	 * @param regexes Any additional regexes
	 * @return The filter key builder
	 */
	public FilterKeyBindingBuilder filterRegex$(String regex, String... regexes)
	{
		return super.filterRegex(regex, regexes);
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param regexes The regexes to apply
	 * @return The filer key builder
	 */
	public FilterKeyBindingBuilder filterRegex$(Iterable<String> regexes)
	{
		return super.filterRegex(regexes);
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPattern   The url pattern
	 * @param morePatterns The url pattern
	 * @return the key builder
	 */
	public ServletKeyBindingBuilder serve$(String urlPattern, String... morePatterns)
	{
		return super.serve(urlPattern, morePatterns);
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPatterns The url pattern to apply
	 * @return The system serving at the generated address
	 */
	public ServletKeyBindingBuilder serve$(Iterable<String> urlPatterns)
	{
		return super.serve(urlPatterns);
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param regex   Any regex that should be applied
	 * @param regexes The regexes additional
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveRegex$(String regex, String... regexes)
	{
		return super.serveRegex(regex, regexes);
	}
	
	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regexes Any regexes to be served
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveRegex$(Iterable<String> regexes)
	{
		return super.serveRegex(regexes);
	}
	
	/**
	 * This method only works if you are using the GuiceServletContextListener to create your injector. Otherwise, it returns null.
	 *
	 * @return The given servlet context
	 */
	public jakarta.servlet.ServletContext getServletContext$()
	{
		return super.getServletContext();
	}
	
	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPattern   The url pattern
	 * @param morePatterns The more patterns
	 * @return The filter key builder
	 */
	public FilterKeyBindingBuilder filter$(String urlPattern, String... morePatterns)
	{
		return super.filter(urlPattern, morePatterns);
	}
	
	/**
	 * Binds a method intercepter
	 *
	 * @param classMatcher  The given class matcher
	 * @param methodMatcher The given method matcher
	 * @param interceptors  The given intercept
	 */
	public void bindInterceptor$(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, org.aopalliance.intercept.MethodInterceptor... interceptors)
	{
		super.bindInterceptor(classMatcher, methodMatcher, interceptors);
	}
	
	private final CallScoper callScope = new CallScoper();
	
	@Provides
	@Named("callScope")
	CallScoper provideCallScope()
	{
		return callScope;
	}
}
