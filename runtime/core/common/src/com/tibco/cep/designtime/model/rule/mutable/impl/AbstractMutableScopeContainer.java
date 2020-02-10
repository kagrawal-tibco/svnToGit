package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.MutationContext;
import com.tibco.cep.designtime.model.MutationObserver;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutationObserver;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.designtime.model.rule.mutable.MutableScopeContainer;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbol;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


/**
 * Anything that needs a scope should use this.
 */
public abstract class AbstractMutableScopeContainer extends AbstractMutableEntity implements MutableScopeContainer {


    public static final String MISSING_DECLARATION_ENTITY = "AbstractCompilable.errors.missingEntity";
    protected static final String NAME_SCOPE = "declarations";
    protected static final ExpandedName XNAME_SCOPE = ExpandedName.makeName(NAME_SCOPE);
    protected static final ExpandedName XNAME_SYMBOL = ExpandedName.makeName("declaration");
    /**
     * Only there for backwards compatibility. Please use XNAME_SYMBOL instead.
     */
    protected static final ExpandedName XNAME_SYMBOL_OLD = ExpandedName.makeName("declarations");

    protected DefaultMutableSymbols m_decls;
    protected MutationObserver scopeMutationObserver;


    public AbstractMutableScopeContainer(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
        this.m_decls = new DefaultMutableSymbols();
        this.scopeMutationObserver = new ScopeMutationObserver(this);
        this.scopeMutationObserver.startObserving(this.m_decls);
    }


    /**
     * This method is used by our .NET libs.
     */

    public List getSymbolsList() {
        return m_decls.getSymbolsList();
    }


    public void setScope(Symbols declarations) {
//        for (Iterator it = m_decls.values().iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            this.removeFromParticipant(symbol.getType());
//        }

        setScopeInternal(declarations);

//        for (Iterator it = m_decls.values().iterator(); it.hasNext();) {
//            final Symbol symbol = (Symbol) it.next();
//            this.addToParticipant(symbol.getType());
//        }

        this.notifyListeners();
        this.notifyOntologyOnChange();
    }

    protected void setScopeInternal(Symbols declarations) {
        this.scopeMutationObserver.stopObserving(this.m_decls);
        this.m_decls = new DefaultMutableSymbols(declarations);
        this.scopeMutationObserver.startObserving(this.m_decls);
    }

    public Symbols getScope() {
        return m_decls;
    }


    protected void loadScope(XiNode declarationsContainerNode, boolean disableNotifications) throws ModelException {
        if (null != declarationsContainerNode) {
            this.loadScope(declarationsContainerNode, XNAME_SCOPE, XNAME_SYMBOL_OLD, disableNotifications);
            this.loadScope(declarationsContainerNode, XNAME_SCOPE, XNAME_SYMBOL, disableNotifications);
        }
    }


    protected void loadScope(XiNode declarationsContainerNode, ExpandedName nameScope, ExpandedName nameSymbol, boolean disableNotifications) throws ModelException {
        final XiNode declarationsNode = XiChild.getChild(declarationsContainerNode, nameScope);
        this.m_decls.load(declarationsNode, nameSymbol, this, disableNotifications);
    }


    public void participantPathChanged(String oldPath, String newPath) {
        if (null != this.m_decls) {
            for (Iterator it = this.m_decls.values().iterator(); it.hasNext();) {
                final MutableSymbol symbol = (MutableSymbol) it.next();
                if (oldPath.equals(symbol.getType())) {
                    symbol.setType(newPath + symbol.getTypeExtension());
                }
            }//for
            this.notifyListeners();
            this.notifyOntologyOnChange();
        }//if
    }


//    public abstract void addToParticipant(String participantPath);
//
//
//    public abstract void removeFromParticipant(String participantPath);


    protected XiNode scopeToXiNode(XiFactory factory) {
        return this.m_decls.toXiNode(factory, XNAME_SCOPE, XNAME_SYMBOL);
    }


    protected XiNode scopeToXiNode(XiFactory factory, ExpandedName scopeNodeName, ExpandedName scopeItemNodeName) {
        return this.m_decls.toXiNode(factory, scopeNodeName, scopeItemNodeName);
    }


    /**
     * Receives and handles notifications from the scope when it changes.
     */
    protected class ScopeMutationObserver extends AbstractMutationObserver {


        private AbstractMutableScopeContainer parent;


        public ScopeMutationObserver(AbstractMutableScopeContainer scopeContainer) {
            super();
            this.parent = scopeContainer;
        }


        protected void onSymbolAdded(MutableSymbol symbol) {
            if (null == symbol) {
                return;
            }
//            this.parent.addToParticipant(symbol.getType());
            this.parent.notifyListeners();
            this.parent.notifyOntologyOnChange();
        }


        protected void onSymbolChanged(String oldType, MutableSymbol symbol) {
            if ((null == symbol) || ModelUtils.IsEmptyString(oldType)) {
                return;
            }
            final String newType = symbol.getType();
            if (oldType.equals(newType)) {
                return; // Only interested in type changes.
            }
//            this.parent.removeFromParticipant(oldType);
//            this.parent.addToParticipant(symbol.getType());
            this.parent.notifyListeners();
            this.parent.notifyOntologyOnChange();
        }


        protected void onSymbolRemoved(MutableSymbol symbol) {
            if (null != symbol) {
//                this.parent.removeFromParticipant(symbol.getType());
                this.parent.notifyListeners();
                this.parent.notifyOntologyOnChange();
            }
        }


        protected void onSymbolRenamed(String oldName, MutableSymbol symbol) {
            if ((null == symbol) || ModelUtils.IsEmptyString(oldName)) {
                return;
            }
            this.parent.notifyListeners();
            this.parent.notifyOntologyOnChange();
        }


        public void update(Observable observable, Object object) {
            if (object instanceof MutationContext) {
                final MutationContext context = (MutationContext) object;
                final Object mutated = context.getMutatedObject();
                if (mutated instanceof MutableSymbol) {
                    final MutableSymbol symbol = (MutableSymbol) mutated;
                    switch (context.getMutationType()) {
                        case MutationContext.MODIFY :
                            this.onSymbolChanged((String) context.getChanges().get("type"), symbol);
                            return;
                        case MutationContext.RENAME :
                            this.onSymbolRenamed((String) context.getChanges().get("name"), symbol);
                            return;
                        case MutationContext.ADD :
                            this.onSymbolAdded(symbol);
                            return;
                        case MutationContext.DELETE :
                            this.onSymbolRemoved(symbol);
                            return;
                    }
                }//if
            }//if
            this.parent.notifyListeners();
            this.parent.notifyOntologyOnChange();
        }


    }


}
