package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.MutationObservable;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableLinkedHashMap;
import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableDomain;
import com.tibco.cep.designtime.model.rule.mutable.MutableScopeContainer;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbol;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbols;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * Ordered map of DefaultMutableSymbol name to Symbol.
 */
public class DefaultMutableSymbols extends DefaultMutableLinkedHashMap implements MutableSymbols {


    protected static final ExpandedName XNAME_DOMAIN = ExpandedName.makeName("domain");
    protected static final ExpandedName XNAME_ENTITY_PATH = ExpandedName.makeName("entity");
    protected static final ExpandedName XNAME_ENTITY_PATH_LEGACY = ExpandedName.makeName("type");
    protected static final ExpandedName XNAME_IDENTIFIER = ExpandedName.makeName("identifier");
    protected static final ExpandedName XNAME_ENTITY_TYPE = ExpandedName.makeName("entityType");


    public DefaultMutableSymbols(Map m) {
        super(m);
    }


    public DefaultMutableSymbols(Symbols original) {
        this();
        for (Object o: original.getSymbolsList()) {
            final Symbol s = (Symbol) o;
            final String name =  s.getName();
            this.put(new DefaultMutableSymbol(this, name, s.getTypeWithExtension(), (MutableDomain) s.getDomain()));
        }
    }



    public DefaultMutableSymbols() {
        super();
    }


    public DefaultMutableSymbols(int initialCapacity) {
        super(initialCapacity);
    }


    public DefaultMutableSymbols(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }


    public DefaultMutableSymbols(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }


    public boolean containsType(String typePath) {
        if (null == typePath) {
            return false;
        }
        for (Iterator it = this.values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            if (typePath.equals(symbol.getType())) {
                return true;
            }
        }
        return false;
    }


    public String getAvailableIdentifier(String baseName) {
        String name = baseName;
        for (long i = 0; this.containsKey(name); i++) {
            name = baseName + "_" + i;
        }
        return name;
    }


    public String getType(String identifier) {
        final Symbol symbol = (Symbol) this.get(identifier);
        if (null != symbol) {
            return symbol.getType();
        }
        return null;
    }


    public LinkedHashSet getTypes() {
        final LinkedHashSet set = new LinkedHashSet();
        for (Iterator it = this.values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            set.add(symbol.getType());
        }
        return set;
    }


    public List getSymbolsList() {
        final ArrayList list = new ArrayList(this.size());
        for (Iterator it = this.values().iterator(); it.hasNext();) {
            list.add((Symbol) it.next());
        }
        return list;
    }


    public Symbol getSymbol(String idName) {
        return (MutableSymbol) this.get(idName);
    }


    public boolean renameSymbol(
            String oldName,
            String newName) {

        final MutableSymbol symbol = (MutableSymbol) this.get(oldName);
        if (null == symbol) {
            return false;
        }
        final List<MutableSymbol> copy = new ArrayList<MutableSymbol>(this.values());
        symbol.setName(newName);

        this.clearWithoutNotification();
        for (MutableSymbol s : copy) {
            this.putWithoutNotification(s.getName(), s);
        }
        return true;
    }



    public void load(XiNode symbolsNode, ExpandedName nameSymbol, MutableScopeContainer parent, boolean disableNotifications) throws ModelException {
        if (null != symbolsNode) {
            for (Iterator it = XiChild.getIterator(symbolsNode, nameSymbol); it.hasNext();) {
                this.loadSymbol((XiNode) it.next(), parent, disableNotifications);
            }
        }
    }


    protected void loadSymbol(XiNode symbolNode, MutableScopeContainer parent, boolean disableNotifications) throws ModelException {
        if (null != symbolNode) {
            final String identifier = symbolNode.getAttributeStringValue(XNAME_IDENTIFIER);
            String entityPath = symbolNode.getAttributeStringValue(XNAME_ENTITY_PATH);
            if (null == entityPath) {
                entityPath = symbolNode.getAttributeStringValue(XNAME_ENTITY_PATH_LEGACY);
            }
            final String entityType = symbolNode.getAttributeStringValue(XNAME_ENTITY_TYPE);
            if (!((null == entityType) || "".equals(entityType) || entityPath.endsWith("." + entityType))) {
                entityPath += "." + entityType;
            }
            final MutationObservable mutationObservable = this.getMutationObservable();
            final boolean mustSwitchMutationObservable = disableNotifications && !mutationObservable.isSuspended();
            try {
                mutationObservable.suspend();

                final MutableSymbol symbol = new DefaultMutableSymbol(this, identifier, entityPath);

                final XiNode domainNode = XiChild.getChild(symbolNode, XNAME_DOMAIN);
                final MutableDomain domain = (MutableDomain) symbol.getDomain();
                domain.load(domainNode);

                this.put(symbol);
            }
            finally {
                if (mustSwitchMutationObservable) {
                    mutationObservable.resume();
                }
            }
        }//if
    }


    public boolean move(String name, int index) {
        // 1) Check the index
        int maxIndex = this.size() - 1;
        if ((index < 0) || (index > maxIndex)) {
            return false;
        }

        // 2) Remove the Symbol from this.
        final Symbol symbol = (Symbol) super.remove(name);
        if (null == symbol) {
            return false;
        }

        // 3) If moving to the end, simple.
        final MutationObservable mutationObservable = this.getMutationObservable();
        if (index == maxIndex) {
            super.put(name, symbol);
            mutationObservable.changeAndNotify();
            return true;
        }

        // 4) Move all the Symbols starting at the index to a temporary tail Map.
        final LinkedHashMap tailMap = new LinkedHashMap();
        if (index == 0) {
            tailMap.putAll(this);
            super.clear();
        } else {
            Iterator it = this.entrySet().iterator();
            for (int i = 0; i < index; i++) {
                it.next();
            }
            while (it.hasNext()) {
                final Map.Entry entry = (Map.Entry) it.next();
                tailMap.put(entry.getKey(), entry.getValue());
            }
            for (it = tailMap.keySet().iterator(); it.hasNext();) {
                super.remove(it.next());
            }
        }

        // 5) Insert the symbol
        super.put(name, symbol);

        // 6) Append the tail Map.
        for (Iterator it = tailMap.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            super.put(entry.getKey(), entry.getValue());
        }

        mutationObservable.changeAndNotify();
        return true;
    }


    public Object put(Object key, Object value) {
        if ((key instanceof String) && (value instanceof String)) {
            return this.put((String) key, (String) value); // Takes care of reflection issues.
        }
        return null;
    }


    public Symbol put(String name, String type) {
        return this.put(new DefaultMutableSymbol(this, name, type));
    }


    public MutableSymbol put(MutableSymbol symbol) {
        if (null == symbol) {
            return null;
        }
        final String name = symbol.getName();
        if (null == name) {
            return null;
        }
        super.put(name, symbol);
        return symbol;
    }


    public void putAll(Map map) {
        for (Iterator it = map.values().iterator(); it.hasNext();) {
            final Object value = it.next();
            if ((null != value) && (value instanceof MutableSymbol)) {
                this.put((MutableSymbol) value);
            }//if
        }//for
    }


    public Object remove(Object object) {
        MutableSymbol symbol = null;
        if (object instanceof String) {
            symbol = (MutableSymbol) super.remove(object);
        }
        if (object instanceof MutableSymbol) {
            symbol = (MutableSymbol) super.remove(((Symbol) object).getName());
        }
        return symbol;
    }


    public XiNode toXiNode(XiFactory factory, ExpandedName name, ExpandedName symbolName) {
        final XiNode root = factory.createElement(name);

        for (Iterator it = this.values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
            final XiNode symbolNode = root.appendElement(symbolName);
            final String entityPath = symbol.getType();
            final String entityTypeExtension = symbol.getTypeExtension();
            final String entityType = (entityTypeExtension.length() < 1) ? "" : entityTypeExtension.substring(1);

            symbolNode.setAttributeStringValue(XNAME_IDENTIFIER, symbol.getName());
            symbolNode.setAttributeStringValue(XNAME_ENTITY_PATH, entityPath);
            symbolNode.setAttributeStringValue(XNAME_ENTITY_TYPE, entityType);
            final Domain domain = symbol.getDomain();
            if (null != domain) {
                symbolNode.appendChild(domain.toXiNode(factory, XNAME_DOMAIN));
            }
        }
        return root;
    }


}


