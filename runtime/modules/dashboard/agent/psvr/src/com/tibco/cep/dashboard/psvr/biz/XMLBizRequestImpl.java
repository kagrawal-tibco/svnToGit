package com.tibco.cep.dashboard.psvr.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.common.utils.XMLUtil;

/**
 * @author RGupta
 *
 */
public class XMLBizRequestImpl implements BizRequest {

    protected Map<String,Set<String>> parameters;

    public XMLBizRequestImpl(String requestXML){
        parameters = new HashMap<String, Set<String>>();
        if (StringUtil.isEmptyOrBlank(requestXML) == false){
			try {
				Node requestXMLAsNode = XMLUtil.parse(requestXML);
				load(requestXMLAsNode);
			} catch (Exception e) {
				throw new RuntimeException("Failed to parse the request XML :\n" + requestXML, e);
			}
        }
    }

    private void load(Node node){
        Iterator<Node> parametersIter = XMLUtil.getAllNodes(node,"parameter");
        while (parametersIter.hasNext()) {
            Node parameterNode = (Node) parametersIter.next();
            String name = XMLUtil.getString(parameterNode,"@name");
            String value = "";
            List<Node> validChildren = getValidChildNodes(parameterNode);
            if (validChildren.size() != 0){
                Node valueNode = (Node) validChildren.get(0);
                if (valueNode.getNodeType() == Node.TEXT_NODE){
                    value = valueNode.getNodeValue();
                }
                else if (valueNode.getNodeType() == Node.CDATA_SECTION_NODE){
                    value = valueNode.getNodeValue();
                }
                else {
                    value = XMLUtil.toCompactString(valueNode);
                }
            }
            addParameter(name,value);
        }
    }

    private List<Node> getValidChildNodes(Node parentNode){
        List<Node> validChildren = new ArrayList<Node>();
        NodeList children = parentNode.getChildNodes();
        int noofChildren = children.getLength();
        for (int i = 0; i < noofChildren; i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.CDATA_SECTION_NODE){
                validChildren.add(child);
            }
            else if (child.getNodeType() == Node.TEXT_NODE){
                String childValue = child.getNodeValue();
                if (childValue.trim().length() != 0){
                    validChildren.add(child);
                }
            }
            else{
                validChildren.add(child);
            }
        }
        return validChildren;
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.psvr.biz.IXMLRequest#addParameter(java.lang.String, java.lang.String)
	 */
    public void addParameter(String name,String value){
        Set<String> values = parameters.get(name);
        if (values == null){
        	values = new LinkedHashSet<String>();
            parameters.put(name,values);
        }
        values.add(value);
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.psvr.biz.IXMLRequest#getParameterNames()
	 */
    public Iterator<String> getParameterNames(){
    	return parameters.keySet().iterator();
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.psvr.biz.IXMLRequest#getParameter(java.lang.String)
	 */
    public String getParameter(String name){
    	Set<String> values = parameters.get(name);
        if (values == null || values.isEmpty() == true){
            return null;
        }
        return values.iterator().next();
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.psvr.biz.IXMLRequest#getParameterValues(java.lang.String)
	 */
    public String[] getParameterValues(String name){
    	Set<String> values = parameters.get(name);
        if (values == null || values.isEmpty() == true){
            return null;
        }
        return (String[]) values.toArray(new String[values.size()]);
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.psvr.biz.IXMLRequest#toString()
	 */
    public String toString(){
    	StringBuilder sb = new StringBuilder("XMLRequest[");
    	Iterator<String> keys = parameters.keySet().iterator();
    	while (keys.hasNext()) {
			String key = (String) keys.next();
			Set<String> values = parameters.get(key);
			if (key.equalsIgnoreCase("password") == false){
				sb.append("name="+key);
				sb.append(",values="+values);
				if (keys.hasNext() == true){
					sb.append(",");
				}
			}
		}
    	sb.append("]");
    	return sb.toString();
    }

    /* (non-Javadoc)
	 * @see com.tibco.cep.dashboard.psvr.biz.IXMLRequest#toXML()
	 */
    public String toXML(){
    	StringBuilder sb = new StringBuilder("<request>");
    	for (String parameterName : parameters.keySet()) {
    		sb.append("<parameter name=\"" + parameterName + "\">");
    		Iterator<String> values = parameters.get(parameterName).iterator();
    		while (values.hasNext()) {
				String value = (String) values.next();
				sb.append(value);
				if (values.hasNext() == true){
					sb.append(",");
				}
			}
			sb.append("</parameter>");
		}
    	sb.append("</request>");
    	return sb.toString();
    }

 }