package com.tibco.cep.query.stream.group;

import com.tibco.cep.query.stream.expression.TupleValueExtractor;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 12:07:15 PM
 */

public class GroupItemInfo {
    protected final TupleValueExtractor groupColumnExtractor;

    public GroupItemInfo(TupleValueExtractor groupColumnExtractor) {
        this.groupColumnExtractor = groupColumnExtractor;
    }

    public TupleValueExtractor getGroupColumnExtractor() {
        return groupColumnExtractor;
    }
}
