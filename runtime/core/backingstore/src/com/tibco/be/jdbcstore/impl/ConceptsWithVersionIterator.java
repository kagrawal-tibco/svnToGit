package com.tibco.be.jdbcstore.impl;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.be.jdbcstore.HistoryTableTuple;
import com.tibco.be.jdbcstore.HistoryTuple;
import com.tibco.be.jdbcstore.ReverseRefTuple;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

class ConceptsWithVersionIterator implements Iterator {

    @SuppressWarnings("unused")
    private final static int DB_FIELD_OFFSET = 1;
    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBEventMap.class);

    ResultSet _primaryResultSet;
    boolean _isStateMachine=false;
    List _resultList;
    DBAdapter _adapter;
    DBConceptMap _entityMap;
    Object[] _attributes;
    Map _secondaryAttributeMap;

    ConceptsWithVersionIterator (List resultList, DBAdapter adapter, DBConceptMap entityMap) {
        this._resultList = resultList;
        if (_resultList != null && _resultList.size() > 0) {
            _primaryResultSet = ((ResultMap) resultList.get(0))._rs;
        }
        _adapter = adapter;
        _entityMap = entityMap;
    }

    ConceptsWithVersionIterator (ResultSet resultSet) {
        this._primaryResultSet=resultSet;
    }

    ConceptsWithVersionIterator (ResultSet resultSet, boolean isStateMachine) {
        this._primaryResultSet=resultSet;
        this._isStateMachine=isStateMachine;
    }

    public boolean hasNext() {
        try {
            boolean hasNext=_primaryResultSet.next();
            if (!hasNext) {
                close();
                _adapter.releaseConnection();
            }
            return hasNext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object next() {
        try {
            if (_primaryResultSet != null) {
                int version=_primaryResultSet.getInt(1);
                getAllCurrentAttributes();
                if (_isStateMachine) {
                    StateMachineConcept sm= DBHelper.createStateMachine(_entityMap, _adapter.getCurrentConnection(), _attributes);
                    ((ConceptImpl) sm).setVersion(version);
                    return sm;
                }
                else {
                    com.tibco.cep.runtime.model.element.Concept cept= DBHelper.createConcept(_entityMap, _adapter.getCurrentConnection(), _attributes, _secondaryAttributeMap);
                    ((ConceptImpl) cept).setVersion(version);
                    return cept;
                }
            } else {
                throw new Exception("Result set already closed");
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Concept iterator failed: %s", e, e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            _attributes = null;
            _secondaryAttributeMap = null;
        }
    }

    public void remove() {
        return;
    }

    public void close() {
        try {
            if (_resultList != null && _resultList.size() > 0) {
                int length = _resultList.size();
                for (int i=0; i<length; i++) {
                    ResultMap rm = (ResultMap) _resultList.get(i);
                    rm._rs.close();
                    rm._stmt.close();
                    rm._stmt = null;
                    rm._rs = null;
                }
                logger.log(Level.DEBUG, "Completed & Closed resources for concept: %s size:%s", this._entityMap._tableName, _resultList.size());
            }
        } catch (Exception e) {
            logger.log(Level.WARN, "Concept iterator close failed: %s", e, e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            _resultList = null;
        }
    }

    private void getAllCurrentAttributes() throws Exception {
        _attributes = new Object[_entityMap.getPrimaryFieldCount() + DBConceptMap.CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT];
        _secondaryAttributeMap = new HashMap();

        int fieldCount = _entityMap.getFieldCount();
        for (int i=0; i<fieldCount; i++) {
            DBEntityMap.DBFieldMap fmap = _entityMap.getFieldMap(i);
            if (fmap.secondaryTableName == null) {
                //_attributes[fmap.tableFieldIndex + DBConceptMap.CONCEPT_PRIMARY_SYSTEM_FIELD_COUNT] =
                //    DBHelper.getColumnData(_primaryResultSet, 7 + fmap.tableFieldIndex, fmap.tableFieldType);
                _attributes[fmap.dataObjectFieldIndex] =
                    DBHelper.getColumnData(_primaryResultSet, 2 + fmap.tableFieldIndex, fmap.tableFieldMappingType);
            }
        }
        long id = ((Long) DBHelper.getColumnData(_primaryResultSet, 2, DBEntityMap.FTYPE_LONG)).longValue();
        for (int i=1; i<_resultList.size(); i++) {
            ResultMap rm = (ResultMap) _resultList.get(i);
            getSecondaryAttributeValues(rm, id);
        }
    }

    private void getSecondaryAttributeValues(ResultMap rm, long pid) throws Exception {
        DBEntityMap.DBFieldMap fMap = rm._fMap;
        if (rm._noMoreData) {
            return;
        }
        if (rm._hasCurrentValue == false) {
            if(!rm._rs.next()) {
                rm._noMoreData = true;
                return;
            }
            rm._currentPid = ((Long) DBHelper.getColumnData(rm._rs, 1, DBEntityMap.FTYPE_LONG)).longValue();
            rm._hasCurrentValue = true;
            if (fMap.isArray || fMap.isReverseRef) {
                rm._valueList = new ArrayList();
            }
        }
        if (rm._currentPid != pid) {
        	// This logic is added to handle the case where a secondary table contains records 
        	// which have no matching pid in the primary table
        	if (DBHelper._skipUnreferencedSecondaryTableEntries == true) {
        		while (rm._currentPid < pid) {
        			logger.log(Level.WARN, "Skipping %s(pid:%d) for primary table %s", rm._fMap.secondaryTableName, rm._currentPid, _entityMap.getTableName());
		            if (rm._rs.next()) {
		                rm._currentPid = ((Long) DBHelper.getColumnData(rm._rs, 1, DBEntityMap.FTYPE_LONG)).longValue();
		            }
		            else {
		                rm._noMoreData = true;
		                return;
		            }
        		}
        		if (rm._currentPid != pid) {
        			return;
        		}
        	} else {
        		return;
        	}
        }
        if (fMap.hasHistory) {
            rm._historyTableTuple = new HistoryTableTuple(0, null);
            rm._historyList = new ArrayList();
        }
        while (rm._currentPid == pid) {
            if (fMap.isArray) {
                int index = rm._rs.getInt(2);
                if ((rm._currentArrayIndex >= 0) && (rm._currentArrayIndex != index) && fMap.hasHistory) {
                    if ((rm._historyTableTuple != null) && (rm._historyList != null)) {
                        int historySize = rm._historyList.size();
                        rm._historyTableTuple.howMany = historySize;
                        rm._historyTableTuple.historyTable = (HistoryTuple[]) rm._historyList.toArray(new HistoryTuple[historySize]);
                        rm._valueList.add(rm._historyTableTuple);
                    }
                    rm._historyTableTuple = new HistoryTableTuple(0, null);
                    rm._historyList = new ArrayList();
                }
                if (rm._currentArrayIndex != index) {
                    rm._currentArrayIndex = index;
                }
            }
            int valuePos = -1;
            Timestamp ts = null;
            //int howMany;
            if (fMap.isArray && fMap.hasHistory) {
                //howMany = rm._rs.getInt(3);
                ts = rm._rs.getTimestamp(4);
                valuePos = 5;
                //sb.append("T.valPid$, T.howMany, T.timeidx, ");
            }
            else if (fMap.isArray && !fMap.hasHistory) {
                //sb.append("T.valPid$, ");
                valuePos = 3;
            }
            else if (!fMap.isArray && fMap.hasHistory) {
                //howMany = rm._rs.getInt(2);
                ts = rm._rs.getTimestamp(3);
                valuePos = 4;
                //sb.append("T.howMany, T.timeidx, ");
            }
            else if (fMap.isReverseRef) {
                String prop = rm._rs.getString(2);
                long id = rm._rs.getLong(3);
                rm._valueList.add(new ReverseRefTuple(prop, id));
            }
            else {
                logger.log(Level.INFO, "%s should not be a secondary attribute", fMap.classFieldName);
                //return null;
            }
            if (valuePos != -1) {
                Object value = DBHelper.getColumnData(rm._rs, valuePos, fMap.tableFieldMappingType);
                if (DBHelper._skipNullValue == false || value != null) {
                    if (fMap.isArray && fMap.hasHistory) {
                        HistoryTuple ht = new HistoryTuple(ts, value);
                        rm._historyList.add(ht);
                    }
                    else if (fMap.isArray && !fMap.hasHistory) {
                        rm._valueList.add(value);
                    }
                    else if (!fMap.isArray && fMap.hasHistory) {
                        HistoryTuple ht = new HistoryTuple(ts, value);
                        rm._historyList.add(ht);
                    }
                }
            }
            if (rm._rs.next()) {
                rm._currentPid = ((Long) DBHelper.getColumnData(rm._rs, 1, DBEntityMap.FTYPE_LONG)).longValue();
            }
            else {
                rm._noMoreData = true;
                break;
            }
        }
        if (fMap.isArray && fMap.hasHistory) {
            int historySize = rm._historyList.size();
            rm._historyTableTuple.howMany = historySize;
            rm._historyTableTuple.historyTable = (HistoryTuple[]) rm._historyList.toArray(new HistoryTuple[historySize]);
            rm._valueList.add(rm._historyTableTuple);
            int arraySize = rm._valueList.size();
            _secondaryAttributeMap.put(rm._fieldName, rm._valueList.toArray(new HistoryTableTuple[arraySize]));
            rm._historyTableTuple = null;
            rm._historyList = null;
            rm._currentArrayIndex = -1;
            if (!rm._noMoreData) {
                rm._valueList = new ArrayList();
            }
        }
        else if (fMap.isArray && !fMap.hasHistory) {
            _secondaryAttributeMap.put(rm._fieldName, rm._valueList.toArray());
            rm._currentArrayIndex = -1;
            if (!rm._noMoreData) {
                rm._valueList = new ArrayList();
            }
        }
        else if (!fMap.isArray && fMap.hasHistory) {
            int historySize = rm._historyList.size();
            rm._historyTableTuple.howMany = historySize;
            rm._historyTableTuple.historyTable = (HistoryTuple[]) rm._historyList.toArray(new HistoryTuple[historySize]);
            _secondaryAttributeMap.put(rm._fieldName, rm._historyTableTuple);
            rm._historyTableTuple = null;
            rm._historyList = null;
        }
        else if (fMap.isReverseRef) {
            _secondaryAttributeMap.put(rm._fieldName, (ReverseRefTuple[]) rm._valueList.toArray(new ReverseRefTuple[rm._valueList.size()]));
            if (!rm._noMoreData) {
                rm._valueList = new ArrayList();
            }
        }
    }
}
