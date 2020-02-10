package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author rajesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HTMLTag {

    private String CUSTOM_ATTRIB_PREFIX = "c_";

    private String tagName;
    private ArrayList subTags = null;
    private HashMap mapAttributes = null;
    private HashMap mapStyle = null;
    private String content;
    private ArrayList singleAttributes = null;
    private String comment = null;
    /**
     * 
     */
    public HTMLTag(String tagName) {
        this.tagName = tagName;
    }
    
    public void addAttributes(Map attributes)
    {
        if (attributes == null)
            return;
        if (mapAttributes == null)
        {
            mapAttributes = new HashMap();
        }
        
        mapAttributes.putAll(attributes);
    }
    
    public void addAttribute(String name, String value)
    {
        if (mapAttributes == null)
        {
            mapAttributes = new HashMap();
        }
        mapAttributes.put(name, value);
    }
    
    public void addStyle(String name, String value)
    {
        if (mapStyle == null)
        {
            mapStyle = new HashMap();
        }
        mapStyle.put(name, value);
    }

    public HTMLTag addTag(HTMLTag tag)
    {
        if (subTags == null)
        {
            subTags = new ArrayList();
        }
        subTags.add(tag);
        return tag;
    }
    
    public HTMLTag addTag(String tagName)
    {
        return addTag(new HTMLTag(tagName));
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        toStringTagStart(buffer);
        toStringTagChilds(buffer);
        toStringTagContent(buffer);
        toStringTagEnd(buffer);
        return buffer.toString();
    }
    
    private String toStyle() {
        if (mapStyle == null) return "";
        StringBuffer buffer = new StringBuffer();
        Iterator iterator = mapStyle.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            buffer.append(entry.getKey() + ":" + entry.getValue() + ";");
        }
        return buffer.toString();
    }
    
    public void toStringTagContent(StringBuffer buffer)
    {
        if (content!=null)
        {
            buffer.append(content);
        }
    }

    public void toStringTagChilds(StringBuffer buffer)
    {
        if (subTags == null) return;
        for (Iterator itSubTags = subTags.iterator(); itSubTags.hasNext();) {
            HTMLTag subTag = (HTMLTag) itSubTags.next();
            buffer.append(subTag);
        }
    }

    public void toStringTagEnd(StringBuffer buffer)
    {
        TableTreeUtils.outTagEnd(buffer, tagName);
        if (comment != null)
            buffer.append("<!--END: " + comment + "-->");
    }
    
    public void toStringTagStart(StringBuffer buffer)
    {
        if (comment != null)
            buffer.append("<!--START: " + comment + "-->");
        TableTreeUtils.outTagStart(buffer, tagName);
        TableTreeUtils.outAttributes(mapAttributes, buffer);
        TableTreeUtils.outAttributes(singleAttributes, buffer);
        
        if (mapStyle != null)
        {
            TableTreeUtils.outAttribute(buffer, "style", toStyle());
        }
        
        TableTreeUtils.outTagStartEnd(buffer);

        return ;
    }

    /**
     * @param string
     */
    public void setContent(String string) {
        content = string;
    }
    
    public void addTableGeneralProps()
    {
        addAttribute("cellspacing", "0");
        addAttribute("cellpadding", "0");
        addAttribute("border", "0");
    }
    
    public void setStyleClass(String styleClass)
    {
        addAttribute("class", styleClass);
    }

    /**
     * @param string
     */
    public void setId(String id) {
        addAttribute("id", id);
    }
    
    /**
     * @param bVisible
     */
    public void setVisible(boolean bVisible) {
		if (bVisible)
		{
			addStyle("visibility", "visible");
		}
		else
		{
			addStyle("visibility", "hidden");
		}
    }
    
    public void addAttribute(String att)
    {
        if (singleAttributes == null)
        {
            singleAttributes = new ArrayList();
        }
        singleAttributes.add(att);
    }

    /**
     * @param string
     */
    public void addCustomAttribute(String attrib, String value) {
        addAttribute(CUSTOM_ATTRIB_PREFIX + attrib, value);
    }

    /**
     * @param string
     */
    public void setComment(String string) {
        comment = string;
    }

}
