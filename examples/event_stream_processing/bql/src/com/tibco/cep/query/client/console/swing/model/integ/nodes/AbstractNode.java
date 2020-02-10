package com.tibco.cep.query.client.console.swing.model.integ.nodes;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.rule.Symbol;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author ksubrama
 */
public abstract class AbstractNode extends DefaultMutableTreeNode {
    protected static final String NAMESPACE = "Namespace";
    protected static final String NAME = "Name";
    protected static final String ALIAS = "Alias";
    protected static final String URI = "URI";
    protected static final String SIGNATURE = "Signature";
    protected static final String DESTINATION = "Destination";
    protected static final String TTL = "TTL";
    protected static final String OTHER_PROPS = "Properties";
    protected static final String PATH = "Path";
    protected static final String PARENT_PROPERTIES = "Parent Properties";
    protected static final String PRIORITY = "Priority";
    protected Map<String, Object> properties = new TreeMap<String, Object>();

    protected AbstractNode(Entity entity) {
        super(entity != null?entity.getFullPath() : "", false);
    }
    
    protected abstract void initProperties();

    public Object[][] getProperties() {
        Object value = properties.remove(OTHER_PROPS);
        int size = properties.size();
        if(value != null) {
            size++;
        }
        Object[][] data = new Object[size][2];
        int i = 0;
        for(String key : properties.keySet()) {
            data[i][0] = key;
            data[i++][1] = properties.get(key);
        }
        if(value != null) {
            data[i][0] = OTHER_PROPS;
            data[i][1] = value;
            properties.put(OTHER_PROPS, value);
        }
        return data;
    }

    protected String getSignature(String name, String returnType, List<Symbol> symbolsList) {
        StringBuilder str = new StringBuilder((returnType == null? "void" : returnType) +
                " " + name + "(");
        for(int i = 0; i < symbolsList.size(); i++) {
            if(i != 0) {
                str.append(",");
            }
            Symbol symbol = symbolsList.get(i);
            str.append(symbol.getType()).append(" ").append(symbol.getName());
        }
        return str.append(")").toString();
    }

    protected String getNonNullValue(String value) {
        return value != null? value : "";
    }
}
