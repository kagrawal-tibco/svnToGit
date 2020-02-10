package com.tibco.jxpath;

import java.util.EnumSet;

public enum AxisName {

    Ancestor("ancestor"),
    AncestorOrSelf("ancestor-or-self"),
    Attribute("attribute"),
    Child("child"),
    Descendant("descendant"),
    DescendantOrSelf("descendant-or-self"),
    Following("following"),
    FollowingSibling("following-sibling"),
    Namespace("namespace"),
    Parent("parent"),
    Preceding("preceding"),
    PrecedingSibling("preceding-sibling"),
    Self("self");

    private String xpathName;

    AxisName(String xpathName) {
        this.xpathName = xpathName;
    }

    public String getXPathName() {
        return this.xpathName;
    }

    public static AxisName axisNamefromXPath(String xpathName)
    {
        EnumSet<AxisName> axisNames = EnumSet.allOf(AxisName.class);
        if ("@".equalsIgnoreCase(xpathName)) return AxisName.Attribute;
        for (AxisName ane : axisNames) {
           if (ane.getXPathName().equalsIgnoreCase(xpathName)) {
               return ane;
           }
        }
        return null;
    }

}
