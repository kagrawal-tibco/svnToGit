package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.FieldList;
import com.tibco.cep.query.model.GroupClause;
import com.tibco.cep.query.model.GroupPolicy;
import com.tibco.cep.query.model.HavingClause;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContextId;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.validation.validators.GroupClauseValidator;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 22, 2007 Time: 2:39:14 PM To
 * change this template use File | Settings | File Templates.
 */
public class GroupClauseImpl extends AbstractQueryContext implements GroupClause {
    FieldList fieldList;

    HavingClause havingClause;

    GroupPolicy groupPolicy;

    public GroupClauseImpl(SelectContext selectContext, CommonTree tree) {
        super(selectContext, tree);
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return ModelContext.CTX_TYPE_GROUP_CLAUSE;
    }

    /**
     * Returns the name of the named context
     * 
     * @return String
     */
    public NamedContextId getContextId() {
        return GroupClause.CTX_ID;
    }

    public FieldList getFieldList() {
        return fieldList;
    }

    public GroupPolicy getGroupPolicy() {
        return groupPolicy;
    }

    public HavingClause getHavingClause() {
        return havingClause;
    }

    public void setFieldList(FieldList fieldList) {
        this.fieldList = fieldList;
    }

    public void setGroupPolicy(GroupPolicy groupPolicy) {
        this.groupPolicy = groupPolicy;
    }

    public void setHavingClause(HavingClause havingClause) {
        this.havingClause = havingClause;
    }

    public void validate() throws Exception {
        new GroupClauseValidator().validate(this);
    }
}
