package com.jwebmp.guicedservlets;

import com.jwebmp.guicedinjection.interfaces.IPackageContentsScanner;

import java.util.HashSet;
import java.util.Set;

public class GuicedServletsPackageInclusions
		implements IPackageContentsScanner
{
	@Override
	public Set<String> searchFor()
	{
		Set<String> strings = new HashSet<>();
		strings.add("com.jwebmp.guicedservlets");
		return strings;
	}
}
