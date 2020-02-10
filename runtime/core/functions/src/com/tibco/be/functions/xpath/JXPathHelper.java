package com.tibco.be.functions.xpath;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import com.tibco.cep.mapper.xml.xdata.XMLDateTypesParser;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.jxpath.JXPathFactory;
import com.tibco.xml.data.primitive.NodeSequence;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Element;

public class JXPathHelper {

	public static final QName OBJECT_TYPE = XPathConstants.NODE;
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(XMLDateTypesParser.DATE_TIME_FORMAT);
	private static XPath xpath;

	public static boolean evalXPathAsBoolean(String xpathString, String[] vars, Object[] objs) {
		return (Boolean) evalXPath(xpathString, vars, objs, XPathConstants.BOOLEAN);
	}
	
	public static String evalXPathAsString(String xpathString, String[] vars, Object[] objs) {
		return (String) evalXPath(xpathString, vars, objs, XPathConstants.STRING);
	}
	
	public static int evalXPathAsInt(String xpathString, String[] vars, Object[] objs) {
		return ((Double)evalXPath(xpathString, vars, objs, XPathConstants.NUMBER)).intValue();
	}
	
	public static double evalXPathAsDouble(String xpathString, String[] vars, Object[] objs) {
		return (Double) evalXPath(xpathString, vars, objs, XPathConstants.NUMBER);
	}
	
	public static Calendar evalXPathAsDateTime(String xpathString, String[] vars, Object[] objs) {
		Calendar calendar = new GregorianCalendar();
		String dateTime = ((String)evalXPath(xpathString, vars, objs, XPathConstants.STRING));
		if (dateTime == null) {
			return calendar; // ? 
		}
		Date date;
		try {
			date = FORMAT.parse(dateTime); // always in this format?
			calendar.setTime(date);
		} catch (ParseException e) {
			try {
				// try parsing with default format
				date = DateFormat.getDateInstance().parse(dateTime);
				calendar.setTime(date);
				return calendar;
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return calendar;
	}
	
	public static long evalXPathAsLong(String xpathString, String[] vars, Object[] objs) {
		return ((Double)evalXPath(xpathString, vars, objs, XPathConstants.NUMBER)).longValue();
	}
	
	public static Object evalXPathAsObject(String xpathString, String[] vars, Object[] objs) {
		return evalXPath(xpathString, vars, objs, OBJECT_TYPE);
	}
	
	public static List<Object> evalXPathAsList(String xpathString, String[] vars, Object[] objs) {
		return (List<Object>) evalXPath(xpathString, vars, objs, XPathConstants.NODESET);
	}

	public static Object copyOf(Object sourceElement) {
		if (sourceElement instanceof Concept) {
			ConceptImpl c = (ConceptImpl) ((Concept) sourceElement).duplicateThis();
			c.setParentReference(null); // if the concept is contained, remove previous parent
			return c;
		} else if (sourceElement instanceof XiNode) {
			return ((XiNode) sourceElement).copy();
		}
		return null;
	}
	
	private static Object evalXPath(String xpathString, String[] vars, Object[] objs, QName xpathType) {
		try {
			xpath = getXPath();
			XPathExpression expr = null;
			expr = xpath.compile(xpathString);
			JXPathBEVariableResolver variableResolver = new JXPathBEVariableResolver();
			for (int i=0; i<vars.length; i++) {
				variableResolver.addVariable(new QName(vars[i]), objs[i]);
			}
			// add global variables
			GlobalVariables gvars = null;
			for(int i=0;i< vars.length;i++){
				if(vars[i].equals("globalVariables")){
					gvars = (GlobalVariables) objs[i];
					break;
				}
			}
			if(gvars == null) {
				RuleSession session = RuleSessionManager.getCurrentRuleSession();
				gvars = session.getRuleServiceProvider().getGlobalVariables();
			}
			variableResolver.addVariable(new QName("globalVariables"), gvars);
			
			Object obj = expr.evaluate(variableResolver, xpathType);
			return castAsType(obj, xpathType);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static Object castAsType(Object obj, QName xpathType) throws XmlAtomicValueCastException {
		if (XPathConstants.BOOLEAN.equals(xpathType)) {
			return castAsBoolean(obj);
		}
		if (XPathConstants.NUMBER.equals(xpathType)) {
			return castAsDouble(obj);
		}
		if (XPathConstants.STRING.equals(xpathType)) {
			return castAsString(obj);
		}
		if (XPathConstants.NODESET.equals(xpathType)) {
			return castAsList(obj);
		}
		if (XPathConstants.NODE.equals(xpathType)) {
			return obj;
		}
		return obj;
	}

	private static String castAsString(Object obj) {
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Element) {
			return ((Element) obj).getTypedValue().getAtom(0).castAsString();
		}
		return null;
	}

	private static Double castAsDouble(Object obj) throws XmlAtomicValueCastException {
		if (obj instanceof Double) {
			return (Double) obj;
		}
		if (obj instanceof Element) {
			return ((Element) obj).getTypedValue().getAtom(0).castAsDouble();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static List<? extends Object> castAsList(Object obj) throws XmlAtomicValueCastException {
		if (obj instanceof List) {
			return (List<Object>) obj;
		}
		if (obj instanceof PropertyArray) {
			return Arrays.asList(((PropertyArray) obj).toArray());
		}
		if (obj instanceof NodeSequence) {
			int size = ((NodeSequence) obj).size();
			List<XmlItem> items = new ArrayList<XmlItem>();
			for (int i = 0; i < size; i++) {
				items.add(((NodeSequence) obj).getItem(i));
			}
			return items;
		}
		return Collections.EMPTY_LIST;
	}
	
	private static Boolean castAsBoolean(Object obj) throws XmlAtomicValueCastException {


		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if (obj instanceof Element) {
			return ((Element) obj).getTypedValue().getAtom(0).castAsBoolean();
		}

        //return null;
		return obj != null;
	}

	private static XPath getXPath() {
		if (xpath == null) {
			xpath = new JXPathFactory().newXPath();
		}
		return xpath;
	}

}
