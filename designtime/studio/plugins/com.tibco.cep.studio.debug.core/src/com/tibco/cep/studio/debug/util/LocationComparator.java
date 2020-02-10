package com.tibco.cep.studio.debug.util;

import java.util.Comparator;

import com.sun.jdi.Location;
/**
 * Compares two line numbers provided by the JDI Location
 * @author pdhar
 *
 */
public class LocationComparator implements Comparator<Location> {

	@Override
	public int compare(Location loc1, Location loc2) {

		
		if (!loc1.method().equals(loc2.method()))
			return loc1.method().compareTo(loc2.method());

		if (loc1.codeIndex() < 0 || loc2.codeIndex() < 0)
			throw new InternalError(
					"Negative Code Index found");

		if (loc1.codeIndex() < loc2.codeIndex())
			return -1;
		else if (loc1.codeIndex() > loc2.codeIndex())
			return 1;
		else
			return 0;
	}

}
