package com.guicedee.guicedservlets.mocks;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class MockPrincipal
		implements Principal
{
	// Variables --------------------------------------------------------------
	/**
	 * The principal name.
	 */
	private String name;
	/**
	 * The principal roles.
	 */
	private Set<String> roles;
	// Constructors -----------------------------------------------------------

	/**
	 * Constructs a new MockPrincipal instance.
	 */
	public MockPrincipal()
	{
	}

	/**
	 * Constructs a new MockPrincipal instance for the given name.
	 *
	 * @param name
	 * 		the name of the principal
	 */
	public MockPrincipal(String name)
	{
		setName(name);
	}

	/**
	 * Constructs a new MockPrincipal instance for the given name and roles.
	 *
	 * @param name
	 * 		the name of the principal
	 * @param roles
	 * 		the principal roles
	 */
	public MockPrincipal(String name, Set<String> roles)
	{
		setName(name);
		setRoles(roles);
	}

	/**
	 * Constructs a new MockPrincipal instance for the given name and roles.
	 *
	 * @param name
	 * 		the name of the principal
	 * @param roles
	 * 		the principal roles
	 */
	public MockPrincipal(String name, String... roles)
	{
		setName(name);
		addRoles(roles);
	}
	// Public methods ---------------------------------------------------------

	/**
	 * Add the roles of this principal.
	 *
	 * @param roles
	 * 		set the roles of this principal.
	 */
	public void addRoles(String... roles)
	{
		for (String role : roles)
		{
			getRoles().add(role);
		}
	}

	/**
	 * Returns the roles of this principal.
	 *
	 * @return the roles of this principal.
	 */
	public Set<String> getRoles()
	{
		if (roles == null)
		{
			roles = new HashSet<String>();
		}
		return roles;
	}

	/**
	 * Sets the roles of this principal.
	 *
	 * @param roles
	 * 		set the roles of this principal.
	 */
	public void setRoles(Set<String> roles)
	{
		this.roles = roles;
	}

	/**
	 * Returns the name of this principal.
	 *
	 * @return the name of this principal.
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * Set the name of this principal.
	 *
	 * @param name
	 * 		the name of the princpal
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
