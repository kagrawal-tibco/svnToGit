package com.tibco.cep.designtime.model.element.stategraph.mutable.impl;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.stategraph.mutable.MutableStateMachineRuleSet;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbol;
import com.tibco.cep.designtime.model.rule.mutable.MutableSymbols;
import com.tibco.cep.designtime.model.rule.mutable.impl.DefaultMutableRuleSet;

public class DefaultMutableStateMachineRuleSet
        extends DefaultMutableRuleSet
        implements MutableStateMachineRuleSet {

    protected static final String DEFAULT_SCOPE_ITEM_NAME = "defaultName";

    protected Entity entity;
    protected String conceptPath;


    public DefaultMutableStateMachineRuleSet(
            Entity entity,
            String conceptPath,
            DefaultMutableOntology ontology,
            DefaultMutableFolder folder,
            String name) {
        super(ontology, folder, name);
        this.entity = entity;
        this.conceptPath = conceptPath;
    }


    public MutableRule createRule(
            String name,
            boolean renameOnConflict,
            boolean isFunction)
            throws ModelException {

        final MutableRule r = super.createRule(name, renameOnConflict, isFunction);
        if (null != this.conceptPath) {
            final String idName = ModelNameUtil.getSMContextName(
                    this.entity.getOntology().getEntity(this.conceptPath));
            r.addDeclaration(idName, this.conceptPath);
        } else {
            r.addDeclaration(DEFAULT_SCOPE_ITEM_NAME, this.entity.getFullPath());
        }
        return r;
    }


    public String getConceptPath() {
        return this.conceptPath;
    }


    public Folder getFolder() {
        return this.entity.getFolder();
    }


    public String getFullPath() {
        return this.entity.getFullPath();
    }


    public String getName() {
        return this.entity.getName();
    }


    public void setConceptPath(
            String conceptPath) {
        
        final String oldName = (null == conceptPath) ? DEFAULT_SCOPE_ITEM_NAME
                : ModelNameUtil.getSMContextName(this.conceptPath);

        String name = null;
        if ((null != conceptPath) && !"".equals(conceptPath)) {
            name = ModelNameUtil.getSMContextName(conceptPath);
        }
        if ((null == name) || "".equals(name)) {
            name = DEFAULT_SCOPE_ITEM_NAME;
        }
//        final boolean isReallyNewName = !name.equals(oldName);

        for (Object ruleObj : this.getRules()) {
            final Rule rule = (Rule) ruleObj;
            final MutableSymbols decls = (MutableSymbols) rule.getDeclarations();
            if (decls.renameSymbol(DEFAULT_SCOPE_ITEM_NAME, name)) {
                ((MutableSymbol) decls.getSymbol(name)).setType(conceptPath);
            }
            if (null == this.conceptPath) {
                for (Object symbolObj : decls.getSymbolsList()) {
                    final MutableSymbol symbol = (MutableSymbol) symbolObj;
                    final String oldPath = symbol.getType(); 
                    if ((null == oldPath) || ("".equals(oldPath))) {
                        symbol.setType(conceptPath);
                    }
                }
            } else {
                for (Object symbolObj : decls.getSymbolsList()) {
                    final MutableSymbol symbol = (MutableSymbol) symbolObj;
                    if (this.conceptPath.equals(symbol.getType())) {
                        symbol.setType(conceptPath);
                    }
                }
            }
        }

        this.conceptPath = conceptPath;
    }

    
    public void setOntology(MutableOntology ontology) {
        for (Object ruleObj : this.getRules()) {
            ((MutableRule) ruleObj).setOntology(ontology);
        }
        this.m_ontology = (DefaultMutableOntology) ontology;
        this.setConceptPath(this.conceptPath);
    }


}//class
