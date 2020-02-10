package com.tibco.cep.designtime.model.rule.mutable.impl;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 5, 2008
 * Time: 1:31:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleFunctionSymbol extends DefaultMutableSymbol {

    String direction = "IN";
    String resourceType="NULL";
    boolean domainOverridden = false;
    String graphicalPath="";
    String primitiveType="NULL";

    static XiFactory gFactory = XiFactoryFactory.newInstance();
    protected static final ExpandedName XNAME_ARGUMENT = ExpandedName.makeName("argument");
    private static final String DIRECTION = "direction";
    private static final String DOMAIN_OVERRIDDEN = "domainOverridden";
    private static final String PATH = "path";
    private static final String ALIAS = "alias";
    private static final String TYPE = "type";
    private static final String GRAPHICAL_PATH = "graphicalPath";
    private static final String RESOURCE_TYPE = "resourceType";

    //@#V2 attributes
    protected static final ExpandedName XNAME_ENTITY_PATH = ExpandedName.makeName("entity");
    protected static final ExpandedName XNAME_ENTITY_PATH_LEGACY = ExpandedName.makeName("type");
    protected static final ExpandedName XNAME_IDENTIFIER = ExpandedName.makeName("identifier");
    protected static final ExpandedName XNAME_ENTITY_TYPE = ExpandedName.makeName("entityType");

    public RuleFunctionSymbol(DefaultMutableSymbols parent, String idname, String typePath) {
        super(parent, idname, typePath);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isDomainOverridden() {
        return domainOverridden;
    }

    public void setDomainOverridden(boolean domainOverridden) {
        this.domainOverridden = domainOverridden;
    }

    public String getGraphicalPath() {
        return graphicalPath;
    }

    public void setGraphicalPath(String graphicalPath) {
        this.graphicalPath = graphicalPath;
    }

    public String getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(String primitiveType) {
        this.primitiveType = primitiveType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }


    public XiNode toXiNode() {

        XiNode argument = gFactory.createElement(XNAME_ARGUMENT);
        XiAttribute.setStringValue(argument, DIRECTION, this.direction);
        XiAttribute.setStringValue(argument, DOMAIN_OVERRIDDEN, Boolean.toString(this.domainOverridden));
        XiAttribute.setStringValue(argument, PATH, this.getType());
        XiAttribute.setStringValue(argument, ALIAS, this.getName());
        XiAttribute.setStringValue(argument, TYPE, this.getPrimitiveType());
        XiAttribute.setStringValue(argument, GRAPHICAL_PATH, this.getGraphicalPath());
        XiAttribute.setStringValue(argument, RESOURCE_TYPE, this.getResourceType());

        return argument;
    }

    public static RuleFunctionSymbol createRuleFunctionSymbol(XiNode argument, DefaultMutableSymbols parent, boolean bMigrateV2) {

        if (bMigrateV2) return createRuleFunctionSymbolV2(argument, parent);

        String name = XiAttribute.getStringValue(argument, ALIAS);
        String path = XiAttribute.getStringValue(argument, PATH);
        RuleFunctionSymbol symbol = new RuleFunctionSymbol (parent, name, path);

        symbol.setDirection(XiAttribute.getStringValue(argument, DIRECTION));
        symbol.setDomainOverridden(Boolean.valueOf(XiAttribute.getStringValue(argument, DOMAIN_OVERRIDDEN)));
        symbol.setPrimitiveType(XiAttribute.getStringValue(argument, TYPE));
        symbol.setGraphicalPath(XiAttribute.getStringValue(argument, GRAPHICAL_PATH));
        symbol.setResourceType(XiAttribute.getStringValue(argument, RESOURCE_TYPE));

        return symbol;
    }

    public static RuleFunctionSymbol createRuleFunctionSymbolV2(XiNode symbolNode, DefaultMutableSymbols parent)
    {
        if (null != symbolNode) {
            final String name = symbolNode.getAttributeStringValue(XNAME_IDENTIFIER);
            String path = symbolNode.getAttributeStringValue(XNAME_ENTITY_PATH);
            if (null == path) {
                path = symbolNode.getAttributeStringValue(XNAME_ENTITY_PATH_LEGACY);
            }
            final String entityType = symbolNode.getAttributeStringValue(XNAME_ENTITY_TYPE);

            RuleFunctionSymbol symbol = new RuleFunctionSymbol (parent, name, path);

//            if (!((null == entityType) || "".equals(entityType) || path.endsWith("." + entityType))) {
//                path += "." + entityType;
//            }

            symbol.setResourceType(entityType);
            if ("concept".equalsIgnoreCase(entityType)) {
                symbol.setDirection("BOTH");
                symbol.setPrimitiveType("NULL");
                symbol.setResourceType("CONCEPT");
            }
            else if ("event".equalsIgnoreCase(entityType))
            {
                symbol.setDirection("IN");
                symbol.setPrimitiveType("NULL");
                symbol.setResourceType("EVENT");
            }
            else {
                symbol.setDirection("IN");
                symbol.setPrimitiveType(path);
                symbol.setResourceType("NULL");
            }

            return symbol;
        }
        return null;
    }

}