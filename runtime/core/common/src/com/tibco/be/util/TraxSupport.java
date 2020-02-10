package com.tibco.be.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;

import org.xml.sax.InputSource;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.PlatformUtil;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.transform.trax.DefaultTransformerFactory;
import com.tibco.xml.transform.trax.TransformerEx;
import com.tibco.xml.trax.XiNodeBucket;
import com.tibco.xml.xquery.helpers.DefaultExprFocus;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Aug 26, 2004
 * Time: 2:11:16 PM
 * To change this template use Options | File Templates.
 */
public class TraxSupport {
	public static boolean useXPath2;
	private static ThreadLocal<XPATH_VERSION> sessionUseXpath2 = new ThreadLocal<XPATH_VERSION>();
    public static DefaultTransformerFactory factory = null;
    static {
        // Select the TIBCOxml XSLT engine.
        //This should be in the BootStrap also.
        try {
            System.setProperty("java.property.javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
            System.setProperty("java.property.javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            useXPath2 = Boolean.parseBoolean(System.getProperty("com.tibco.xml.bw6engine", "false")); 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static boolean isXPath2() {
		if (sessionUseXpath2.get() == null) {
			RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
			if (isProjectXPathVersion(XPATH_VERSION.XPATH_20, rsp)) {
				sessionUseXpath2.set(XPATH_VERSION.XPATH_20);
			} else {
				sessionUseXpath2.set(null);
			}
		}
		return useXPath2 || sessionUseXpath2.get() != null;
	}
	
	public static boolean isXPathVersion(XPATH_VERSION version ,RuleServiceProvider rsp) {
		return useXPath2 || isProjectXPathVersion(version,rsp);
	}
    
    
    public static boolean isXPath2DesignTime(String projectName) {
    	return useXPath2 || isDesigntimeXPathVersion(projectName, XPATH_VERSION.XPATH_20);
    }
    
    public static boolean isDesigntimeXPathVersion(String projectName, XPATH_VERSION version) {
		try {
			if(PlatformUtil.INSTANCE.isStudioPlatform()) {
				StudioProjectConfiguration cfg = StudioProjectConfigurationCache.getInstance().get(projectName);
				return cfg != null && cfg.getXpathVersion().compareTo(version) == 0;
			}
			
			
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean isProjectXPathVersion(XPATH_VERSION version,RuleServiceProvider rsp) {
		try {
			
			XPATH_VERSION xv = rsp.getProject().getProjectConfiguration().getXpathVersion();
			return xv.compareTo(version) == 0;
			
		} catch (Exception e) {
			return false;
		}
	}
    
    public static TemplatesArgumentPair makeTAP(String xsltTemplate, Class recvParam, Class dvm, List<String> parms) throws Exception {
    	Source templateSource = makeTransformerSource(xsltTemplate);
    	DefaultTransformerFactory transformerFactory = XiSupport.getTransformerFactory();
    	if ("true".equalsIgnoreCase(System.getProperty("xpath.disable.customfunctions", "false"))) {
    		// don't add custom functions in xpath
    	} else {
    		transformerFactory.setExternalFunctions(new BECustomFunctionResolver(FunctionsCatalog.getINSTANCE()).getAsFunctionGroup());
    	}
        Templates templates = null;
        if (isXPath2()) {
        	// do not create templates here -- they are not used and it takes a non-trivial amount of time
        } else {
        	templates = transformerFactory.newTemplates(templateSource);
        }
        List<String> paramNames = getParamNamesForTemplateSource(templateSource);

        String instanceVarName = null;
        if (parms.size() == 1) {
        	String nm = parms.get(0);
        	if (nm.charAt(0)=='$') {
        		instanceVarName = nm.substring(1);
        	}
        }
        return new TemplatesArgumentPair(templates, paramNames, recvParam, dvm, xsltTemplate, instanceVarName);
    }

    /**
     * Construct a Transformer Source from an XSLT String
     * @param xsltTemplate - A string representing an XSLT Template
     * @return returns a Source. @see javax.xml.transform.Source
     * @throws Exception
     */
    public static Source makeTransformerSource(String xsltTemplate) throws Exception {
        XiNode xsltNode = getXiNode(xsltTemplate);
        Source transformSource = new XiNodeBucket(xsltNode);
        return transformSource;
    }

    /**
     *  Perform the XSLT Transformation given a Template, and set of input Nodes
     * @param src       The XSLT Template as a source
     * @param inputNodes An Array containing input nodes representing the parameters for the templates
     * @param handler The Result Handler
     * @throws Exception
     */

    public static void doTransform(Source src, XiNode[] inputNodes, XmlContentHandler handler) throws Exception {

        Templates templates = XiSupport.getTransformerFactory().newTemplates(src);
        doTransform(templates, inputNodes, handler);
    }

    /**
     * Perform an XSLT Transformation passing the templates and VariableList
     * @param templates
     * @param inputNodes
     * @param handler
     * @throws Exception
     */
    public static void doTransform(Templates templates, XiNode[] inputNodes, XmlContentHandler handler) throws Exception {

        TransformerEx transformer = (TransformerEx)templates.newTransformer();

        if (inputNodes != null) {
            for (int i=0; i < inputNodes.length; i++) {
                XiNode inputNode = inputNodes[i];
                if(inputNodes[i] == null)
                    continue;
                ExpandedName name = inputNode.getName();
                transformer.setArgument(name, inputNode);
            }
        }

        XiNode focusNode = XiSupport.getXiFactory().createDocument();

        transformer.transform(new DefaultExprFocus(focusNode), handler,null);

    }

    public static void doTransform(Templates templates, Source src, Result result) throws Exception {
        TransformerEx transformer = (TransformerEx)templates.newTransformer();
        transformer.transform(src, result);
    }

    private static XiNode getXiNode(String xsltTemplate) throws Exception {

        StringReader sr = new StringReader(xsltTemplate);
        InputSource is = new InputSource(sr);

        XiNode doc = XiSupport.getParser().parse(is);
        doc.normalize();

//        XmlCursor4XiNode cursor = new XmlCursor4XiNode();
//        XmlContentParser p = new XmlContentParser(cursor);
//        p.parse(is);
//
//        return cursor.getNode();
        return doc;               
    }


    static ExpandedName XSLPARAM_NAME = ExpandedName.makeName("http://www.w3.org/1999/XSL/Transform", "param" );
    static ExpandedName STYLESHEET_NAME = ExpandedName.makeName("http://www.w3.org/1999/XSL/Transform", "stylesheet" );
    static ExpandedName NAME=ExpandedName.makeName("name");

    public static List<String> getParamNamesForTemplateSource(Source transformSource) throws Exception {


        if (!(transformSource instanceof XiNodeBucket)) Collections.emptyList();
        XiNode sheetdoc = ((XiNodeBucket)transformSource).getNode();

        if (sheetdoc == null ) return Collections.emptyList();

        XiNode sheetnode = XiChild.getChild(sheetdoc, STYLESHEET_NAME);
//        XiNode sheetnode = sheetdoc.getFirstChild();
        if (sheetnode == null) Collections.emptyList();

        HashSet parameters = new HashSet();
        Iterator itr = XiChild.getIterator(sheetnode, XSLPARAM_NAME);

        while (itr.hasNext()) {
            XiNode paramNode = (XiNode) itr.next();
            String paramName = paramNode.getAttributeStringValue(NAME);
            parameters.add(paramName);
        }

        ArrayList ret = new ArrayList(parameters.size());
        ret.addAll(parameters);
        return ret;

    }

    public static void destroy() {
    	//System.out.println("Destroying TRAX...");
    	//cache = new ConcurrentHashMap(); //NOT USED
    	//factory = new DefaultTransformerFactory();
    	//xiFactory = XiFactoryFactory.newInstance();
    }
}