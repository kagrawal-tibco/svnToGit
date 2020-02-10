package com.tibco.cep.designtime.model.rule.mutable.impl;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelError;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableRuleParticipant;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.mutable.MutableCompilable;


/**
 * @author ishaan
 * @version Jan 19, 2005, 6:29:17 PM
 */
public abstract class AbstractMutableCompilable extends AbstractMutableScopeContainer implements MutableCompilable {


    protected int m_compilationStatus;
    protected String m_returnType;

    public static final String MISSING_DECLARATION_ENTITY = "AbstractCompilable.errors.missingEntity";


    public AbstractMutableCompilable(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
        m_compilationStatus = -1;
    }


    public List getModelErrors() {
        List errors = super.getModelErrors();
        if (this.m_decls == null) {
            return errors;
        }

        BEModelBundle bundle = BEModelBundle.getBundle();

        for (Iterator it = this.m_decls.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            final String id = (String) entry.getKey();
            final Symbol symbol = (Symbol) entry.getValue();
            final String path = symbol.getType();

            if (path.charAt(0) != Folder.FOLDER_SEPARATOR_CHAR) {
                continue;
            }

            final Entity entity = m_ontology.getEntity(path);
            if (entity instanceof AbstractMutableRuleParticipant) {
                continue;
            }

            final ModelError me = new ModelError(this, bundle.formatString(MISSING_DECLARATION_ENTITY, path, id));
            errors.add(me);
        }

        return errors;
    }// end getModelErrors



    public String getReturnType() {
        String type = m_returnType;
        if (type == null) return null;
        int indexOfDot = type.indexOf(".");
        if (indexOfDot == -1) return type;
        return type.substring(0, indexOfDot);
    }

    public void setReturnType(String retType) {
//        removeFromParticipant(m_returnType);
        m_returnType = retType;
//        addToParticipant(retType);
        setCompilationStatus(-1);
        notifyListeners();
        notifyOntologyOnChange();
    }


    public int getCompilationStatus() {
        return m_compilationStatus;
    }


    public void setCompilationStatus(int status) {
        m_compilationStatus = status;
    }


}
