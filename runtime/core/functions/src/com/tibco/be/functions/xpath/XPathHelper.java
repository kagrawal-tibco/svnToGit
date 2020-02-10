package com.tibco.be.functions.xpath;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.genxdm.typed.variant.ItemIterable;
import org.xml.sax.InputSource;

import com.tibco.be.model.functions.MapperElementType;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;


@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "XPath",
        synopsis = "This category provides list of xpath functions to operate on the event payload")

public class XPathHelper {
    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    static TypeRenderer java2xsd_dt_conv= registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);
    static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    static XPath2Helper xpath2helper = new XPath2Helper();
    
    @com.tibco.be.model.functions.BEFunction(
        name = "evalAsBoolean",
        synopsis = "Evaluate an XPath Expression and return a boolean.",
        signature = "boolean evalAsBoolean (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Formed using XPathBuilder.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the value identified by path param exists, false otherwise."),
        version = "1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XPATH,
        		inputelement=""),
        description = "Evaluate the xpath expression on the objects as specified in XPathbuilder.",
        cautions = "Using more than one object is join in the rule's engine.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean evalAsBoolean(String key, String path, VariableList varlist)  {
    	if(TraxSupport.isXPath2()) {
    		boolean b = xpath2helper.evalAsBoolean(key, varlist, false);
    		return b;
    	}

        XmlSequence value = executeXPath(key, path, varlist);
        try {
            if(!value.isEmpty()) {
                XmlItem item = value.getItem(0);
                if(item != null && item.getTypedValue() != null) {
                    return item.getTypedValue().getAtom(0).castAsBoolean();
                }
            }
            return false;
//            throw new RuntimeException("XPath.evalAsBoolean return element is null, unable to cast empty element to boolean");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean evalAsBoolean2(String path, VariableList varlist)  {
        return evalAsBoolean(path, path, varlist);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "evalAsString",
        synopsis = "Evaluate an XPath Expression and return a String.",
        signature = "String evalAsString (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Formed using XPathBuilder.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns the value identified by path param as String if exists, null otherwise."),
        version = "1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XPATH,
        		inputelement=""),
        description = "Evaluate the xpath expression on the objects as specified in XPathbuilder.",
        cautions = "Using more than one object is join in the rule's engine.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String evalAsString(String key, String path, VariableList varlist) {
    	if(TraxSupport.isXPath2()) {
    		return xpath2helper.evalAsString(key, varlist, false);
    	}

        XmlSequence value = executeXPath(key, path, varlist);
        try {
            String s2 = (String)getData(value, String.class);
            return s2;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
//        throw new RuntimeException("XPath.evalAsString return element is null, unable to cast empty element to String");
    }


    public static String evalAsString2(String path, VariableList varlist) {
        return evalAsString(path, path, varlist);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "evalAsDouble",
        synopsis = "Evaluate an XPath Expression and return a double.",
        signature = "double evalAsDouble (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Formed using XPathBuilder.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "Returns the value identified by path param as double if exists, 0.0 otherwise."),
        version = "1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XPATH,
        		inputelement=""),
        description = "Evaluate the xpath expression on the objects as specified in XPathbuilder.",
        cautions = "Using more than one object is join in the rule's engine.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double evalAsDouble(String key, String path, VariableList varlist) {
    	if(TraxSupport.isXPath2()) {
    		return xpath2helper.evalAsDouble(key, varlist, false);
    	}

        XmlSequence value = executeXPath(key, path, varlist);
        try {
            if(!value.isEmpty()) {
                XmlItem item = value.getItem(0);
                if(item != null && item.getTypedValue() != null) {
                    return item.getTypedValue().getAtom(0).castAsDouble();
                }
            }
            return 0.0;
//            throw new RuntimeException("XPath.evalAsDouble return element is null, unable to cast empty element to double");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static double evalAsDouble2(String path, VariableList varlist) {
        return evalAsDouble(path, path, varlist);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "evalAsDateTime",
        synopsis = "Evaluate an XPath Expression and return a DateTime.",
        signature = "DateTime evalAsDateTime (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Formed using XPathBuilder.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "Returns the value identified by path param as DateTime if exists, null otherwise."),
        version = "1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XPATH,
        		inputelement=""),
        description = "Evaluate the xpath expression on the objects as specified in XPathbuilder.",
        cautions = "Using more than one object is join in the rule's engine.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar evalAsDateTime(String key, String path, VariableList varlist) {
    	if(TraxSupport.isXPath2()) {
    		return xpath2helper.evalAsDateTime(key, varlist, false);
    	}

        XmlSequence value = executeXPath(key, path, varlist);
        try {
            if(!value.isEmpty()) {
                XmlItem item = value.getItem(0);
                if(item != null && item.getTypedValue() != null) {
                    return (Calendar) xsd2java_dt_conv.convertSimpleType(value.getItem(0).getTypedValue());
                }
            }
            return null;
//            throw new RuntimeException("XPath.evalAsDateTime return element is null, unable to cast empty element to DateTime");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Calendar evalAsDateTime2(String path, VariableList varlist) {
        return evalAsDateTime(path, path, varlist);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "evalAsLong",
        synopsis = "Evaluate an XPath Expression and return a long.",
        signature = "long evalAsLong (String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Formed using XPathBuilder.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "Returns the value identified by path param as long if exists, 0L otherwise."),
        version = "1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XPATH,
        		inputelement=""),
        description = "Evaluate the xpath expression on the objects as specified in XPathbuilder.",
        cautions = "Using more than one object is join in the rule's engine.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long evalAsLong(String key, String path, VariableList varlist) {
    	if(TraxSupport.isXPath2()) {
    		return xpath2helper.evalAsLong(key, varlist, false);
    	}

        XmlSequence value = executeXPath(key, path, varlist);
        try {
            if(!value.isEmpty()) {
                XmlItem item = value.getItem(0);
                if(item != null && item.getTypedValue() != null) {
                    return value.getItem(0).getTypedValue().getAtom(0).castAsLong();
                }
            }
            return 0L;
//            throw new RuntimeException("XPath.evalAsLong return element is null, unable to cast empty element to long");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static long evalAsLong2(String path, VariableList varlist) {
        return evalAsLong(path, path, varlist);
    }



    @com.tibco.be.model.functions.BEFunction(
        name = "evalAsInt",
        synopsis = "Evaluate an XPath Expression",
        signature = "int evalAsInt (String path) and return an int.",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Formed using XPathBuilder.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Returns the value identified by path param as int if exists, 0 otherwise."),
        version = "1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XPATH,
        		inputelement=""),
        description = "Evaluate the xpath expression on the objects as specified in XPathbuilder.",
        cautions = "Using more than one object is join in the rule's engine.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int evalAsInt(String key, String path, VariableList varlist) {
    	if(TraxSupport.isXPath2()) {
    		return xpath2helper.evalAsInt(key, varlist, false);
    	}

        XmlSequence value = executeXPath(key, path, varlist);
        try {
            if(!value.isEmpty()) {
                XmlItem item = value.getItem(0);
                if(item != null && item.getTypedValue() != null) {
                    return value.getItem(0).getTypedValue().getAtom(0).castAsInt();
                }
            }
            return 0;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static int evalAsInt2(String path, VariableList varlist) {
        return evalAsInt(path, path, varlist);
    }


    public static XmlSequence executeXPath(String key, String path, VariableList varlist) {
        try {

            XQueryExprContext expr = XQueryExprContext.buildXQuery(key, path);
            XmlSequence seq = expr.execute(varlist);
            return seq;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    public static Object getData(XmlSequence value, Class returnType) throws Exception {
        if(!value.isEmpty()) {

            XmlItem item = value.getItem(0);

            if(item != null) {

                XmlTypedValue xmlval = item.getTypedValue();

                if ((xmlval != null) && (xmlval.size() > 0)) {

                    XmlAtomicValue atom = xmlval.getAtom(0);

                    if (String.class == returnType) {
                        return atom.castAsString();
                    }
                    else if (Boolean.class == returnType) {
                        return new Boolean(atom.castAsBoolean());
                    }
                    else if (Double.class == returnType) {
                        return new Double(atom.castAsDouble());
                    }
                    else if (Long.class == returnType) {
                        return new Long(atom.castAsLong());
                    }
                    else if (Integer.class == returnType) {
                        return new Integer(atom.castAsInt());
                    }
                    else if (Calendar.class == returnType) {
                        return (Calendar) xsd2java_dt_conv.convertSimpleType(atom);
                    }
                }
            }
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "execute",
        synopsis = "Evaluate an XPath Expression against the input XML String.  See Language Reference for examples.",
        signature = "String execute (String xpath, String xml, String prefixes)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xpath", type = "String", desc = "The XPath expression.  $var is required in front of the root element, for example: $1count($var/order/orderlines)$1."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xml", type = "String", desc = "The XML string for the xpath to be executed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "prefixes", type = "String", desc = "The prefixes and namespaces in comma delimiter format, for example: $1xns1=http://www.tibco.com/be,xns2=http://www.tibco.com/support$1.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result of the evaluation."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Evaluate an XPath Expression against the input XML String.",
        cautions = "RuntimeException for an undefined property.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String execute(String xpath, String xml, String prefixes) {
        try {
			// There is a bug with GenXDM's XPath 2 execution while using descendant-or-self::node() (that is, '//' such as $var//entry[1])
        	// Since the GenXDM team refuses to fix the issue, the workaround is to do the following:
        	// 1) first, attempt to execute using xpath 1.0 libraries.  If that fails (possibly due to function out of scope issues from using an XPath 2.0 function)
        	// 2) attempt to use genxdm xpath 2.0 libraries (which can fail with '//' operator)
        	// 3) as a last resort, replace '//' with '/*/' to allow execution to complete.  
        	// NOTE, option 3) this has an issue, where '//' finds all descendants of the current node, '/*/' only finds all element children -- so this might not
        	// include the full result set expected -- perhaps it is better to throw an exception at this point, or run the xpath multiple times with '/*/*/' etc
        	// to aggregate the results
        	try {
        		XQueryExprContext expr = XQueryExprContext.buildXQuery("var", xpath, prefixes);
        		XiNode doc = XiSupport.getParser().parse(new InputSource(new StringReader(xml)));
        		XmlSequence seq = expr.execute("var", doc);
        		return XPathHelper.getStringValue(seq);
			} catch (RuntimeException e) {
				if(TraxSupport.isXPath2()) {
					try {
						ItemIterable<XiNode, XmlAtomicValue> ret = xpath2helper.execute(xpath, xml, prefixes);
						return XPath2Helper.getStringValue(ret);
					} catch (RuntimeException ex) {
	        			int idx = xpath.indexOf("//");
	        			if (idx != -1) {
        					// if an exception occurs (possibly due to XPath 2.0 functions being used), then hack a separate '//' to '/*/' conversion
        					String xpath2 = xpath.replaceAll("//", "/*/");
        					ItemIterable<XiNode, XmlAtomicValue> ret = xpath2helper.execute(xpath2, xml, prefixes);
        					return XPath2Helper.getStringValue(ret);
	        			} else {
	        				// rethrow the error
	        				throw ex;
	        			}
	        		}
				}
				throw e;
			}
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "executeWithEvent",
        synopsis = "Evaluate an XPath Expression against the input event.",
        signature = "String executeWithEvent (String xpath, Event event, String prefixes)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xpath", type = "String", desc = "The XPath expression.  $var is required in front of the root element, for example: $1count($var/order/orderlines)$1."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "Event", desc = "The Event object on which the XPath expression executes."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "prefixes", type = "String", desc = "The prefixes and namespaces in comma delimiter format, for example: $1xns1=http://www.tibco.com/be,xns2=http://www.tibco.com/support$1.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The result of the evaluation."),
        version = "2.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Evaluate an XPath Expression against the input event.",
        cautions = "RuntimeException for an undefined property.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String executeWithEvent(String xpath, SimpleEvent event, String prefixes) {
        try {
        	XiNode doc = XiSupport.getXiFactory().createDocument();
        	XiNode ele = doc.appendElement(event.getExpandedName());
        	event.toXiNode(ele);

        	if(TraxSupport.isXPath2()) {
        		ItemIterable<XiNode, XmlAtomicValue> ret = xpath2helper.executeWithContext(xpath, doc, prefixes);
        		return XPath2Helper.getStringValue(ret);
        	}
        	
            XQueryExprContext expr = XQueryExprContext.buildXQuery("var", xpath, prefixes);
            XmlSequence seq = expr.execute("var", doc);
            return XPathHelper.getStringValue(seq);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeXPathWithEvent",
        synopsis = "Evaluate an XPath Expression against the payload of the input event.",
        signature = "String[] executeXPathWithEvent (String xpath, Event event, String prefixes)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xpath", type = "String", desc = "The XPath expression.  The root element of the XML document is accessible with <code>$var</code>, regardless of its actual name and namespace."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "Event", desc = "The Event containing the payload on which the XPath expression executes."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "prefixes", type = "String", desc = "The prefixes and namespaces used in the XPath expression, separated with commas. For example: $1myns=MyNameSpace,xsd=http://www.w3.org/2001/XMLSchema$1.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "The result of the evaluation. Each node is returned as a separate string in the array."),
        version = "4.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Evaluate an XPath Expression against the input event.",
        cautions = "RuntimeException for an undefined property.",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String[] executeXPathWithEvent(String xpath,
                                                 SimpleEvent event,
                                                 String prefixes) {
        List<String> xPathResult = new ArrayList<String>();
        try {
            EventPayload eventPayload = event.getPayload();

            if (eventPayload instanceof XiNodePayload) {
                XiNodePayload payload = (XiNodePayload)eventPayload;
                
            	if(TraxSupport.isXPath2()) {
            		// need to build up array with this call
            		ItemIterable<XiNode, XmlAtomicValue> ret = xpath2helper.executeWithContext(xpath, payload.getNode(), prefixes);
            		return XPath2Helper.getStringArrayValue(ret);
            	}

                XQueryExprContext expr =
                        XQueryExprContext.buildXQuery("var", xpath, prefixes);
                XmlSequence seq = expr.execute("var", payload.getNode());
                xPathResult = new ArrayList<String>(seq.size());

                for (int loop = 0, length = seq.size(); loop < length; loop++) {
                    XmlItem xmlNode = seq.getItem(loop);
                    String serializedNode = serializeNode(xmlNode);
                    if (serializedNode != null) {
                        xPathResult.add(serializedNode);
                    }
                }
            }
            return xPathResult.toArray(new String[xPathResult.size()]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   public static String getStringValue(XmlSequence seq) throws Exception {
        if(seq.size() > 1) {
            StringWriter writer = new StringWriter();
            DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(writer,"UTF-8");
            handler.setOmitXmlDeclaration(Boolean.TRUE);
            seq.serialize(handler);
            writer.flush();
            return writer.toString();
        }
        else if (seq.size() == 1) {
            XmlItem item = seq.getItem(0);
            if(item instanceof com.tibco.xml.datamodel.nodes.Element) {
//                int count = XiChild.getChildCount((XiNode) item);
//                if(count == 0) {
//                    return item.getTypedValue().toString();
//                }
//                else {
                    StringWriter writer = new StringWriter();
                    DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(writer,"UTF-8");
                    handler.setOmitXmlDeclaration(Boolean.TRUE);
                    seq.serialize(handler);
                    writer.flush();
                    return writer.toString();
//                }
            }
            else {
                return item.getTypedValue().toString();
            }
        }
        return null;
    }

    public static String serializeNode(XmlItem xmlItem) throws Exception {
        StringWriter writer = new StringWriter();
        DefaultXmlContentSerializer handler =
                new DefaultXmlContentSerializer(writer,"UTF-8");
        handler.setOmitXmlDeclaration(Boolean.TRUE);
        xmlItem.serialize(handler);
        writer.flush();
        return writer.toString();
    }
}
