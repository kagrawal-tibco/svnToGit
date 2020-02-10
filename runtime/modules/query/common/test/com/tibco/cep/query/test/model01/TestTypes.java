package com.tibco.cep.query.test.model01;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestTypes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class[]types = {				
				void.class,
				boolean.class,
				boolean[].class,
				char.class,
				char[].class,
				byte.class,
				byte[].class,
				short.class,
				short[].class,
				int.class,
				int[].class,
				long.class,
				long[].class,
				float.class,
				float[].class,
				double.class,
				double[].class,
				java.lang.String.class,
				java.lang.String[].class,
				java.lang.Character.class,
				java.lang.Character[].class,
				java.lang.Byte.class,
				java.lang.Byte[].class,
				java.lang.Integer.class,
				java.lang.Integer[].class,
				java.lang.Short.class,
				java.lang.Short[].class,
				java.lang.Long.class,
				java.lang.Long[].class,
				java.lang.Float.class,
				java.lang.Float[].class,
				java.lang.Double.class,
				java.lang.Double[].class,
				java.util.Calendar.class,
				java.util.Calendar[].class};
		List<Class> tlist = Arrays.asList(types);
		for (Iterator iter = tlist.iterator(); iter.hasNext();) {
			Class type = (Class) iter.next();
			StringBuilder sb = new StringBuilder();
			sb.append(type.getCanonicalName()).append(" = ");
			sb.append(type.getName()).append(" , ");
			sb.append(com.tibco.cep.query.utils.TypeHelper.getBoxedClass(type).getName());
			System.out.println(sb.toString());
			
		}
	}

}
