package com.tibco.be.util;

import java.lang.reflect.Method;

import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Condition;

/**
 * Created by IntelliJ IDEA. User: pdhar Date: Mar 24, 2009 Time: 12:53:24 PM To
 * change this template use File | Settings | File Templates.
 */
public class JNISignature {

	static {
		com.tibco.cep.Bootstrap.ensureBootstrapped();
	}

	public static String generateMethodSignature(Method method) {
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		Class<?>[] params = method.getParameterTypes();
		for (int ii = 0; ii < params.length; ii++) {
			Class<?> param = params[ii];
			buf.append(jniTypeStr(param));
		}
		buf.append(")");
		buf.append(jniTypeStr(method.getReturnType()));
		return buf.toString();

	}

	private static String jniTypeStr(Class<?> type) {
		int dims = getArrayDimensions(type);
		Class<?> ctype = type.getComponentType();
		String sig;
		if (type == Boolean.TYPE || type == boolean.class) {
			sig = "Z";
		} else if (type == Byte.TYPE || type == byte.class) {
			sig = "B";
		} else if (type == Character.TYPE || type == char.class) {
			sig = "C";
		} else if (type == Short.TYPE || type == short.class) {
			sig = "S";
		} else if (type == Integer.TYPE || type == int.class) {
			sig = "I";
		} else if (type == Long.TYPE || type == long.class) {
			sig = "J";
		} else if (type == Float.TYPE || type == float.class) {
			sig = "F";
		} else if (type == Double.TYPE || type == double.class) {
			sig = "D";
		} else if (type == Void.TYPE || type == void.class) {
			sig = "V";
		} else {
			if (type.isArray()) {
				if(ctype.isPrimitive()){
					sig = getPrimitiveSignature(ctype);
				} else {
					sig = 'L' + ctype.getName().replace('.', '/') + ";";
				}
			} else {
				sig = 'L' + type.getName().replace('.', '/') + ";";
			}
		}

		return repeatString("[", dims) + sig;
	}
	
	private static String getPrimitiveSignature(Class<?> type) {
		String sig = "";
		if (type == Boolean.TYPE || type == boolean.class) {
			sig = "Z";
		} else if (type == Byte.TYPE || type == byte.class) {
			sig = "B";
		} else if (type == Character.TYPE || type == char.class) {
			sig = "C";
		} else if (type == Short.TYPE || type == short.class) {
			sig = "S";
		} else if (type == Integer.TYPE || type == int.class) {
			sig = "I";
		} else if (type == Long.TYPE || type == long.class) {
			sig = "J";
		} else if (type == Float.TYPE || type == float.class) {
			sig = "F";
		} else if (type == Double.TYPE || type == double.class) {
			sig = "D";
		} else if (type == Void.TYPE || type == void.class) {
			sig = "V";
		}
		return sig;
	}

	private static String repeatString(String s, int dims) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < dims; x++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static Class<?> getArrayClass(Class<?> c) {
		if (c.getComponentType().isArray())
			return getArrayClass(c.getComponentType());
		else
			return c.getComponentType();
	}

	public static int getArrayDimensions(Class<?> array) {
		if (!array.isArray()) {
			return 0;
		}
		if (array.getComponentType().isArray())
			return 1 + getArrayDimensions(array.getComponentType());
		else
			return 1;
	}

	public static int getArrayDimensions(Object array) {
		return getArrayDimensions(array.getClass());
	}

	public static void main(String[] args) {
		try {
			Method execute = Action.class.getMethod("execute", Object[].class);
			System.out.println(generateMethodSignature(execute));

			Method eval = Condition.class.getMethod("eval", Object[].class);
			System.out.println(generateMethodSignature(eval));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
