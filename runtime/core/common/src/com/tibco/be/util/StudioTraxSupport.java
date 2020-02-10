package com.tibco.be.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.XmlCursor4XiNode;
import com.tibco.xml.transform.trax.DefaultTransformerFactory;
import com.tibco.xml.transform.trax.TransformerEx;
import com.tibco.xml.xquery.helpers.DefaultExprFocus;

public class StudioTraxSupport extends TraxSupport
{
	public static TemplatesArgumentPair getTemplates(String key, String xsltTemplate) throws Exception {
    	return makeTAP(xsltTemplate);
    }
	
    protected static TemplatesArgumentPair makeTAP(String xsltTemplate) throws Exception {
        return makeTAP2(xsltTemplate, null, null, new ArrayList<String>()); 
     }

    public static TemplatesArgumentPair makeTAP2(String xsltTemplate, Class recvParam, Class dvm, List<String> parms) throws Exception {
    	Source templateSource = makeTransformerSource(xsltTemplate);
    	DefaultTransformerFactory transformerFactory = XiSupport.getTransformerFactory();
    	if ("true".equalsIgnoreCase(System.getProperty("xpath.disable.customfunctions", "false"))) {
    		// don't add custom functions in xpath
    	} else {
    		transformerFactory.setExternalFunctions(new BECustomFunctionResolver(FunctionsCatalog.getINSTANCE()).getAsFunctionGroup());
    	}
        Templates templates = null;
//        if (useXPath2) {
//        	// do not create templates here -- they are not used and it takes a non-trivial amount of time
//        } else {
        	templates = transformerFactory.newTemplates(templateSource);
//        }
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
     * Transform using an XSLT String as a template with XiNode[] as input
     * @param xsltTemplate - The XSLT String
     * @param inputNodes - Input Nodes
     * @return XiNode
     * @throws Exception
     */
    public static XiNode doTransform(String xsltTemplate, XiNode[] inputNodes) throws Exception
    {
        TemplatesArgumentPair tp = getTemplates(xsltTemplate, xsltTemplate);

        TransformerEx transformer = (TransformerEx)tp.getTemplates().newTransformer();

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
        XmlCursor4XiNode cursor = new XmlCursor4XiNode();
        transformer.transform(new DefaultExprFocus(focusNode), cursor, false, null);

        return cursor.getNode();
    }
}
