package com.tibco.cep.dashboard.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

/******************************************************************************
 * Debug
 ******************************************************************************/
public class Debug {
	
	/*************************************************************************
	 * Convert an array to a string.
	 * 
	 * @param array
	 *            the array.
	 * @return String output string.
	 **************************************************************************/
	public static String toString(Object[] array) {
		StringBuffer buf = new StringBuffer();

		buf.append("[");
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				buf.append(", ");
			if (array[i] == null)
				buf.append("null");
			else
				format(array[i], buf, "");
		}
		buf.append("]");

		return buf.toString();
	}

	public static String mapToString(Map<?,?> map) {
		StringBuffer buf = new StringBuffer();
		boolean isFirst = true;

		buf.append("(");
		for (Iterator<?> i = map.keySet().iterator(); i.hasNext();) {
			Object key = i.next();
			Object value = map.get(key);
			if (isFirst)
				isFirst = false;
			else
				buf.append(", ");
			buf.append(key).append("=");
			if (value != null)
				buf.append(value);
			else
				buf.append("null");
		}
		buf.append(")");
		return buf.toString();
	}

	public static String toString(Object o) {
		StringBuffer sb = new StringBuffer();
		format(o, sb, "");
		return sb.toString();
	}

	/*************************************************************************
	 * Get the current stack trace.
	 * 
	 * @return String The stack trace.
	 **************************************************************************/
	public static String getStackTrace() {
		try {
			throw new Exception("getStackTrace");
		} catch (Exception e) {
			return getStackTrace(e);
		}
	}

	/*************************************************************************
	 * Convert the stack trace of an exception to a string..
	 * 
	 * @param e
	 *            The exception.
	 * @return String The stack trace.
	 **************************************************************************/
	public static String getStackTrace(Throwable e) {
		StringWriter swriter = null;
		PrintWriter pwriter = null;
		String stack = "";

		try {
			swriter = new StringWriter();
			pwriter = new PrintWriter(swriter);
			// e.fillInStackTrace();
			e.printStackTrace(pwriter);
			pwriter.flush();
			stack = swriter.toString();
		} catch (Exception e1) {
		} finally {
			try {
				pwriter.close();
				swriter.close();
			} catch (Exception e2) {
			}
		}

		return stack;
	}

	private static void format(Object o, StringBuffer sb, String indent) {
		if (o == null) {
			sb.append("null");
		} else if (o instanceof Map) {
			Map<?,?> map = (Map<?,?>) o;
			Iterator<?> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object v = map.get(key);
				sb.append(indent).append(key).append("=");
				format(v, sb, indent + "  ");
			}
		} else if (o instanceof List) {
			Iterator<?> liter = ((List<?>) o).iterator();
			while (liter.hasNext()) {
				format(liter.next(), sb, indent + "  ");
			}
		} else if (o instanceof Node) {
			sb.append(XMLUtil.toString((Node) o));
		} else {
			// Assume string.
			sb.append(indent).append(o.toString());
		}
	}

	public static final long markTime() {
		return System.currentTimeMillis();
	}
}
