
/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guicedee.guicedservlets.tests.mocks;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import java.io.Serializable;
import java.util.*;

/**
 * Mock implementation of the {@link jakarta.servlet.http.HttpSession} interface.
 *
 * <p>As of Spring 4.0, this set of mocks is designed on a Servlet 3.0 baseline.
 *
 * <p>Used for testing the web framework; also useful for testing application
 * controllers.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Mark Fisher
 * @author Sam Brannen
 * @since 1.0.2
 */
@SuppressWarnings("deprecation")
public class MockHTTPSession
		implements HttpSession
{

	public static final String SESSION_COOKIE_NAME = "JSESSION";

	private static int nextId = 1;
	private final long creationTime = System.currentTimeMillis();
	private final ServletContext servletContext;
	private final Map<String, Object> attributes = new LinkedHashMap<>();
	private String id;
	private int maxInactiveInterval;
	private long lastAccessedTime = System.currentTimeMillis();

	private boolean invalid = false;

	private boolean isNew = true;

	public MockHTTPSession()
	{
		this(null);
	}

	/**
	 * Create a new MockHttpSession.
	 *
	 * @param servletContext
	 * 		the ServletContext that the session runs in
	 */
	public MockHTTPSession(ServletContext servletContext)
	{
		this(servletContext, null);
	}

	/**
	 * Create a new MockHttpSession.
	 *
	 * @param servletContext
	 * 		the ServletContext that the session runs in
	 * @param id
	 * 		a unique identifier for this session
	 */
	public MockHTTPSession(ServletContext servletContext, String id)
	{
		this.servletContext = servletContext;
		this.id = (id != null ? id : Integer.toString(MockHTTPSession.nextId++));
	}

	@Override
	public long getCreationTime()
	{
		assertIsValid();
		return creationTime;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public long getLastAccessedTime()
	{
		assertIsValid();
		return lastAccessedTime;
	}

	@Override
	public ServletContext getServletContext()
	{
		return servletContext;
	}

	/**
	 * Convenience method for asserting that this session has not been
	 * {@linkplain #invalidate() invalidated}.
	 *
	 * @throws IllegalStateException
	 * 		if this session has been invalidated
	 */
	private void assertIsValid()
	{
		if (isInvalid())
		{
			throw new IllegalStateException("The session has already been invalidated");
		}
	}

	public boolean isInvalid()
	{
		return invalid;
	}

	/**
	 * As of Servlet 3.1 the id of a session can be changed.
	 *
	 * @return the new session id.
	 *
	 * @since 4.0.3
	 */
	public String changeSessionId()
	{
		id = Integer.toString(MockHTTPSession.nextId++);
		return id;
	}

	public void access()
	{
		lastAccessedTime = System.currentTimeMillis();
		isNew = false;
	}

	/**
	 * Serialize the attributes of this session into an object that can be
	 * turned into a byte array with standard Java serialization.
	 *
	 * @return a representation of this session's serialized state
	 */
	public Serializable serializeState()
	{
		HashMap<String, Object> state = new HashMap<>();
		for (Iterator<Map.Entry<String, Object>> it = attributes.entrySet()
		                                                        .iterator(); it.hasNext(); )
		{
			Map.Entry<String, Object> entry = it.next();
			String name = entry.getKey();
			Object value = entry.getValue();
			it.remove();
			if (value instanceof Serializable)
			{
				state.put(name, value);
			}
			else
			{
				// Not serializable... Servlet containers usually automatically
				// unbind the attribute in this case.
				if (value instanceof HttpSessionBindingListener)
				{
					((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
				}
			}
		}
		return state;
	}

	/**
	 * Deserialize the attributes of this session from a state object created by
	 * {@link #serializeState()}.
	 *
	 * @param state
	 * 		a representation of this session's serialized state
	 */
	@SuppressWarnings("unchecked")
	public void deserializeState(Serializable state)
	{
		attributes.putAll((Map<String, Object>) state);
	}

	@Override
	public void setMaxInactiveInterval(int interval)
	{
		maxInactiveInterval = interval;
	}

	@Override
	public int getMaxInactiveInterval()
	{
		return maxInactiveInterval;
	}

	@Override
	public Object getAttribute(String name)
	{
		assertIsValid();
		return attributes.get(name);
	}


	public Object getValue(String name)
	{
		return getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames()
	{
		assertIsValid();
		return Collections.enumeration(new LinkedHashSet<String>(attributes.keySet()));
	}

	public String[] getValueNames()
	{
		assertIsValid();
		return attributes.keySet()
		                 .toArray(new String[attributes.size()]);
	}

	@Override
	public void setAttribute(String name, Object value)
	{
		assertIsValid();
		if (value != null)
		{
			attributes.put(name, value);
			if (value instanceof HttpSessionBindingListener)
			{
				((HttpSessionBindingListener) value).valueBound(new HttpSessionBindingEvent(this, name, value));
			}
		}
		else
		{
			removeAttribute(name);
		}
	}

	public void putValue(String name, Object value)
	{
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name)
	{
		assertIsValid();
		Object value = attributes.remove(name);
		if (value instanceof HttpSessionBindingListener)
		{
			((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
		}
	}

	public void removeValue(String name)
	{
		removeAttribute(name);
	}

	/**
	 * Clear all of this session's attributes.
	 */
	public void clearAttributes()
	{
		for (Iterator<Map.Entry<String, Object>> it = attributes.entrySet()
		                                                        .iterator(); it.hasNext(); )
		{
			Map.Entry<String, Object> entry = it.next();
			String name = entry.getKey();
			Object value = entry.getValue();
			it.remove();
			if (value instanceof HttpSessionBindingListener)
			{
				((HttpSessionBindingListener) value).valueUnbound(new HttpSessionBindingEvent(this, name, value));
			}
		}
	}

	/**
	 * Invalidates this session then unbinds any objects bound to it.
	 *
	 * @throws IllegalStateException
	 * 		if this method is called on an already invalidated session
	 */
	@Override
	public void invalidate()
	{
		assertIsValid();
		invalid = true;
		clearAttributes();
	}

	public void setNew(boolean value)
	{
		isNew = value;
	}

	@Override
	public boolean isNew()
	{
		assertIsValid();
		return isNew;
	}

}
