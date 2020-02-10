package com.tibco.be.jdbcstore.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.tibco.be.jdbcstore.HistoryTableTuple;

public class ResultMap {
    public String _fieldName;
    public PreparedStatement _stmt;
    public ResultSet _rs;
    public DBEntityMap.DBFieldMap _fMap;
    public boolean _isPrimary = false;
    public boolean _hasCurrentValue = false;
    public boolean _noMoreData = false;
    public long _currentPid = -1L;
    public List _valueList;
    public HistoryTableTuple _historyTableTuple;
    public List _historyList;
    public int _currentArrayIndex = -1;

    public ResultMap(String fieldName, DBEntityMap.DBFieldMap fMap, PreparedStatement stmt, ResultSet rs) {
        this._fieldName = fieldName;
        this._stmt = stmt;
        this._rs = rs;
        this._fMap = fMap;
    }
}
