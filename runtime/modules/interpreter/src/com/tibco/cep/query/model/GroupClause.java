package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Validatable;

/**
 * Created by IntelliJ IDEA. User: nprade Date: Oct 22, 2007 Time: 2:30:27 PM To
 * change this template use File | Settings | File Templates.
 */
public interface GroupClause extends QueryContext, NamedContext, Validatable {

    NamedContextId CTX_ID = new NamedContextId() {
        public String toString() {
            return "GROUP_CLAUSE";
        }
    };

    FieldList getFieldList();

    HavingClause getHavingClause();

    GroupPolicy getGroupPolicy();

    void setFieldList(FieldList fieldList);

    void setHavingClause(HavingClause havingClause);

    void setGroupPolicy(GroupPolicy groupPolicy);
}
