/*
 * Copyright (C) 2017 Marc Magon
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
package com.jwebmp.guicedservlets;

import com.google.inject.*;
import com.google.inject.Module;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.servlet.ServletModule;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeListener;
import com.jwebmp.guicedinjection.GuiceContext;
import com.jwebmp.guicedinjection.interfaces.IGuiceModule;
import com.jwebmp.guicedservlets.services.IGuiceSiteBinder;
import com.jwebmp.logger.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * Version Identifier
	 */
	private static final long serialVersionUID = 1L;

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
	 * @param urlPattern
	 * 		The url pattern
	 * @param morePatterns
	 * 		any additional patterns
	 *
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSite(String urlPattern, String... morePatterns)
	{
		return serve(urlPattern, morePatterns);
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPatterns
	 * 		The url pattern
	 *
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSite(Iterable<String> urlPatterns)
	{
		return serve(urlPatterns);
	}

	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regex
	 * 		The regex to apply oto serve
	 * @param regexes
	 * 		The regexes to apply
	 *
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSiteRegex(String regex, String... regexes)
	{
		return serveRegex(regex, regexes);
	}

	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regexes
	 * 		The regexes to apply
	 *
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveSiteRegex(Iterable<String> regexes)
	{
		return serveRegex(regexes);
	}

	/**
	 * Runs the binders
	 */
	@Override
	public void configureServlets()
	{
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
	 * @param scopeAnnotation
	 * 		The annotation
	 * @param scope
	 * 		The scope to apply
	 */
	@Override
	public void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope)
	{
		super.bindScope(scopeAnnotation, scope);
	}

	/**
	 * Binds to a given guice key
	 *
	 * @param <T>
	 * 		The type to bind
	 * @param key
	 * 		The key to bind
	 *
	 * @return The key builder
	 */
	@Override
	public <T> LinkedBindingBuilder<T> bind(Key<T> key)
	{
		return super.bind(key);
	}

	/**
	 * Binds to a given type literal
	 *
	 * @param <T>
	 * 		The type to bind
	 * @param typeLiteral
	 * 		The type literal to bind - create abstract instance of type
	 *
	 * @return The key builder
	 */
	@Override
	public <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral)
	{
		return super.bind(typeLiteral);
	}

	/**
	 * Binds to a given class
	 *
	 * @param <T>
	 * 		The type to apply
	 * @param clazz
	 * 		The class to bind
	 *
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
	 * @param module
	 * 		The module to install with this one
	 */
	@Override
	public void install(Module module)
	{
		super.install(module);
	}

	/**
	 * Bind listener
	 *
	 * @param typeMatcher
	 * 		The type literal to match
	 * @param listener
	 * 		The type listener for listening
	 */
	@Override
	public void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener)
	{
		super.bindListener(typeMatcher, listener);
	}

	/**
	 * Bind listener
	 *
	 * @param bindingMatcher
	 * 		The type to listen for
	 * @param listener
	 * 		The listener
	 */
	@Override
	public void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listener)
	{
		super.bindListener(bindingMatcher, listener);
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPatterns
	 * 		The url pattern to apply
	 *
	 * @return The filter key builder
	 */
	public FilterKeyBindingBuilder filter$(Iterable<String> urlPatterns)
	{
		return super.filter(urlPatterns);
	}

	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regex
	 * 		The regex
	 * @param regexes
	 * 		Any additional regexes
	 *
	 * @return The filter key builder
	 */
	public FilterKeyBindingBuilder filterRegex$(String regex, String... regexes)
	{
		return super.filterRegex(regex, regexes);
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param regexes
	 * 		The regexes to apply
	 *
	 * @return The filer key builder
	 */
	public FilterKeyBindingBuilder filterRegex$(Iterable<String> regexes)
	{
		return super.filterRegex(regexes);
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPattern
	 * 		The url pattern
	 * @param morePatterns
	 * 		The url pattern
	 *
	 * @return the key builder
	 */
	public ServletKeyBindingBuilder serve$(String urlPattern, String... morePatterns)
	{
		return super.serve(urlPattern, morePatterns);
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPatterns
	 * 		The url pattern to apply
	 *
	 * @return The system serving at the generated address
	 */
	public ServletKeyBindingBuilder serve$(Iterable<String> urlPatterns)
	{
		return super.serve(urlPatterns);
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param regex
	 * 		Any regex that should be applied
	 * @param regexes
	 * 		The regexes additional
	 *
	 * @return The key builder
	 */
	public ServletKeyBindingBuilder serveRegex$(String regex, String... regexes)
	{
		return super.serveRegex(regex, regexes);
	}

	/**
	 * regexes - Any Java-style regular expressions. Since: 4.1
	 *
	 * @param regexes
	 * 		Any regexes to be served
	 *
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
	public javax.servlet.ServletContext getServletContext$()
	{
		return super.getServletContext();
	}

	/**
	 * urlPatterns - Any Servlet-style patterns. examples: /*, /html/*, *.html, etc. Since: 4.1
	 *
	 * @param urlPattern
	 * 		The url pattern
	 * @param morePatterns
	 * 		The more patterns
	 *
	 * @return The filter key builder
	 */
	public FilterKeyBindingBuilder filter$(String urlPattern, String... morePatterns)
	{
		return super.filter(urlPattern, morePatterns);
	}

	/**
	 * Binds a method intercepter
	 *
	 * @param classMatcher
	 * 		The given class matcher
	 * @param methodMatcher
	 * 		The given method matcher
	 * @param interceptors
	 * 		The given intercept
	 */
	public void bindInterceptor$(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, org.aopalliance.intercept.MethodInterceptor... interceptors)
	{
		super.bindInterceptor(classMatcher, methodMatcher, interceptors);
	}

}
