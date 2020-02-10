package com.tibco.cep.query.stream.group;

import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;

/*
* Author: Ashwin Jayaprakash Date: Jun 5, 2008 Time: 3:14:37 PM
*/
public interface GroupChanges {
    AppendOnlyQueue<Tuple> getAdditions();

    AppendOnlyQueue<Tuple> getDeletions();
}
