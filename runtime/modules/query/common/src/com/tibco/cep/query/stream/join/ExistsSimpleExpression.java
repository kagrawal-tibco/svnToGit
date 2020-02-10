package com.tibco.cep.query.stream.join;

import com.tibco.cep.query.stream.expression.AbstractExpression;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 5:27:10 PM
 */

public class ExistsSimpleExpression extends AbstractExpression {
    protected final AbstractExistsPetey existsPetey;

    public ExistsSimpleExpression(AbstractExistsPetey existsPetey) {
        super(existsPetey.populateAndGetAliasAndTypes());

        this.existsPetey = existsPetey;
    }

    public AbstractExistsPetey getExistsPetey() {
        return existsPetey;
    }
}
