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

import com.jwebmp.guicedinjection.interfaces.DefaultBinder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Assists to bind into the injection module from multiple JAR or enterprise archives
 *
 * @author GedMarc
 * @since 12 Dec 2016
 */
public abstract class GuiceSiteBinder
		implements Comparator<GuiceSiteBinder>, DefaultBinder<GuiceSiteInjectorModule>, Serializable, Comparable<GuiceSiteBinder>
{

	private static final long serialVersionUID = 1L;
	/**
	 * Default order 100
	 */
	private Integer sortOrder = 100;

	/**
	 * Blank constructor
	 */
	public GuiceSiteBinder()
	{
		//Nothing needed to be done
	}

	/**
	 * Sets the default sort order
	 *
	 * @param DefaultSortOrder
	 * 		the default sort order
	 */
	public void setSortOrder(int DefaultSortOrder)
	{
		sortOrder = DefaultSortOrder;
	}

	/**
	 * Compares the items across
	 *
	 * @param o1
	 * 		the comparing site
	 * @param o2
	 * 		the comparing to
	 *
	 * @return the comparison
	 */
	@Override
	public int compare(GuiceSiteBinder o1, GuiceSiteBinder o2)
	{
		if (o1 == null || o2 == null)
		{
			return -1;
		}
		return o1.sortOrder()
		         .compareTo(o2.sortOrder());
	}

	/**
	 * The default sort order number is 100
	 *
	 * @return the default
	 */
	public Integer sortOrder()
	{
		return sortOrder;
	}

	@Override
	public int compareTo(@NotNull GuiceSiteBinder o)
	{
		int sort = sortOrder.compareTo(o.sortOrder);
		if (sort == 0)
		{
			return -1;
		}
		return sort;
	}
}
