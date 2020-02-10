package com.tibco.cep.query.functions.advanced.cluster.datagrid;

import com.tangosol.coherence.dslquery.QueryPlus;

import java.io.PrintWriter;

/*
* Author: Ashwin Jayaprakash / Date: 3/15/11 / Time: 11:04 PM
*/
public class QueryPlusPlus extends QueryPlus{
    @Override
    public boolean evalLine(String line, PrintWriter out) {
        return super.evalLine(line, out);
    }
}
