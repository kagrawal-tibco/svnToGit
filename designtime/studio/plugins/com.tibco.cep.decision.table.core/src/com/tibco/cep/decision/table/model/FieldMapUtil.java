package com.tibco.cep.decision.table.model;

import java.util.Vector;

public class FieldMapUtil 
{
	public static boolean containsKey(Vector<FieldMap> v, String key)
	{
		for (int i=0; i<v.size(); i++)
		{
			FieldMap map = (FieldMap) v.get(i);
			if (map!=null && map.getKey().equals(key))
				return true;
		}
		return false;
	}
	
	public static void put(Vector<FieldMap> v, String key, String value)
	{
		FieldMap map = new FieldMap(key, value);
		v.add(map);
	}
	
	public static String get(Vector<FieldMap> v, String key)
	{
		for (int i=0; i<v.size(); i++)
		{
			FieldMap map = (FieldMap) v.get(i);
			if (map!=null && map.getKey().equals(key))
				return map.getValue();
		}
		return null;
	}
}
