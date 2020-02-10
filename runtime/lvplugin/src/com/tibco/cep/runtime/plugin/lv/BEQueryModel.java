package com.tibco.cep.runtime.plugin.lv;

import com.streambase.liveview.client.LiveViewException;
import com.streambase.liveview.client.LiveViewExceptionType;
import com.streambase.liveview.client.LiveViewQueryType;
import com.streambase.liveview.server.query.ParsedExpr;
import com.streambase.liveview.server.query.QueryModelBase;
import com.streambase.liveview.server.table.CatalogedTable;
import com.tibco.cep.query.service.QuerySession;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 10/7/14
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEQueryModel extends QueryModelBase {

    /**
     * @param args
     */

    private BETable beTable;
    private BETableProvider beProvider;
    String actualQuery;
    private LiveViewQueryType queryType;
    private BETupleCopier beTupleCopier;

    public BEQueryModel (CatalogedTable catalogedTable, BETable beTable, String queryString, LiveViewQueryType type) {
        super(catalogedTable, queryString, type);
        this.actualQuery = queryString;
        this.beTable = beTable;
        this.queryType = type;
    }


    @Override
    public List<String> getDirection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ParsedExpr> getGroupBy() {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public int getLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<ParsedExpr> getOrderBy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPredicate() {
        return actualQuery;
    }

    @Override
    public String getProjection() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTable() {
        return beTable.getMappedName();
    }

    @Override
    public long getTemporalPredicateComponent() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getTimestampField() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getWindowEndExpr() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getWindowStartExpr() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasGroupBy() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasLimit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasOrderBy() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasPredicate() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasTemporalPredicate() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasTimeWindowedPredicate() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void validate(CatalogedTable arg0, boolean arg1)
            throws LiveViewException {
        try {
            String queryName = UUID.randomUUID().toString();
            QuerySession rs = (QuerySession) (beProvider.provider).getRuleRuntime().getRuleSessions()[0];
            rs.createQuery(queryName,actualQuery);

            setKeyFields(beTable.getKeyFields());
            confirmKeyFieldsPresent();
            beTupleCopier = new BETupleCopier(getFullSchema(), beTable.getSpaceDef());
        }
        catch (Exception e){

            throw LiveViewExceptionType.QUERY_SYNTAX.error();

        }

    }


    public BETupleCopier getBETupleCopier() {
        return beTupleCopier;
    }

//	public String getQueryStatement() {
//		return actualQuery;
//	}



}

