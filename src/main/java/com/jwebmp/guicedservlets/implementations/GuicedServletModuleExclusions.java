package com.jwebmp.guicedservlets.implementations;

import com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions;
import com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class GuicedServletModuleExclusions
		implements IGuiceScanModuleExclusions<GuicedServletModuleExclusions>,
				           IGuiceScanJarExclusions<GuicedServletModuleExclusions>
{
	@Override
	public @NotNull Set<String> excludeJars()
	{
		Set<String> strings = new HashSet<>();
		strings.add("guice-servlet-*");
		strings.add("guiced-servlets-*");

		strings.add("javax.servlet-api-*");

		return strings;
	}

	@Override
	public @NotNull Set<String> excludeModules()
	{
		Set<String> strings = new HashSet<>();
		strings.add("com.jwebmp.guicedservlets");


		strings.add("com.google.guice.extensions.servlet");
		strings.add("com.jwebmp.guicedinjection");
		strings.add("com.jwebmp.logmaster");
		strings.add("com.google.guice");
		strings.add("java.logging");
		strings.add("javax.servlet.api");
		strings.add("aopalliance");
		strings.add("java.validation");


		return strings;
	}
}
