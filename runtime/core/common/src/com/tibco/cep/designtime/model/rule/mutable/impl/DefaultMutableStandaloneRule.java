package com.tibco.cep.designtime.model.rule.mutable.impl;

import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableStandaloneRule;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jan 26, 2009
* Time: 7:24:10 PM
*/
public class DefaultMutableStandaloneRule
        extends DefaultMutableRuleSet
        implements MutableStandaloneRule {


    public DefaultMutableStandaloneRule(
            DefaultMutableOntology ontology,
            DefaultMutableFolder folder,
            String name)
            throws ModelException {
        super(ontology, folder, name);
        super.createRule(name, false, false);
    }


    /**
     * Start methods used by default implementation
     */
    public void addDefaultRule(DefaultMutableRule rule)
            throws ModelException {
        if (this.getRules().size() == 0) {
            super.addDefaultRule(rule);
        }
    }


    protected void addRule(
            String name,
            DefaultMutableRule r)
            throws ModelException {
        if (this.getRules().size() == 0) {
            super.addRule(name, r);
        }
    }


    public static DefaultMutableStandaloneRule createDefaultStandaloneRuleFromNode(
            XiNode root)
            throws ModelException {

        final String name = root.getAttributeStringValue(NAME_NAME);
        final String folderPath = root.getAttributeStringValue(FOLDER_NAME);
        final DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null,
                String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        final DefaultMutableFolder folder = DefaultMutableOntology.createFolder(rootFolder, folderPath, false);

        final DefaultMutableStandaloneRule ruleSet = new DefaultMutableStandaloneRule(null, folder, name);
        populateRuleSetFromNode(root, ruleSet);
        return ruleSet;
    }


    public MutableRule createRule(
            String name,
            boolean renameOnConflict,
            boolean isFunction)
            throws ModelException {
        if (this.getRules().size() == 0) {
            return super.createRule(name, renameOnConflict, isFunction);
        } else {
            return null;
        }
    }


    public MutableRule createRule(
            String name,
            boolean renameOnConflict,
            int ruleType)
            throws ModelException {
        if (this.getRules().size() == 0) {
            return super.createRule(name, renameOnConflict, ruleType);
        } else {
            return null;
        }
    }


    public XiNode toXiNode(
            XiFactory factory) {
        return this.toXiNode(factory, "standaloneRule");
    }



}
