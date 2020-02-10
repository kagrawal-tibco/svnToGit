package com.tibco.cep.query.api.impl.local;

import com.tibco.cep.query.api.QueryStatement;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/*
* Author: Ashwin Jayaprakash Date: May 6, 2008 Time: 11:40:50 AM
*/

/**
 * This is a helper class that just swallows the {@link #batchEndMarker} and skips to the next row
 * if it's available. The user never sees {@link #isBatchEnd()} being <code>true</code>.
 */
public class StreamedSinkCloak extends StreamedSinkAdapter {
    public StreamedSinkCloak(ReteQuery reteQuery, QueryStatement statement,
                             QueryResultSetManager resultSetManager) {
        super(reteQuery, statement, resultSetManager);
    }

    @Override
    public boolean next() {
        boolean b = super.next();

        if (b && isBatchEnd()) {
            b = super.next();
        }

        return b;
    }
}
