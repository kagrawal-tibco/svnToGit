package com.tibco.cep.kernel.core.rete;

import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.ObjectTable;
import com.tibco.cep.kernel.core.base.tuple.ObjectTableNoKey;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.TupleRowNoKey;
import com.tibco.cep.kernel.core.base.tuple.TupleTableNoKey;
import com.tibco.cep.kernel.helper.ForceConditionFailureException;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:17:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoinNode extends Node {
    /**
     * IF YOU MODIFY THIS CLASS IN ANY WAY, PLEASE ENSURE THE FOLLOWING LINE NUMBERS ARE VALID.
     * THESE ARE THE LINE NUMBERS CORRESPONDING TO LINES CONTAINING if(_eval) {...
     **/
    public static final int debugLineNumber0 = 388;
    public static final int debugLineNumber1 = 479;

    protected Identifier[] m_leftIdentifiers;
    protected Identifier[] m_rightIdentifiers;
    protected Condition    m_condition;

    protected int        numTotalIdentifier;
    protected int        numLeftIdentifier;
    protected int        numRightIdentifier;

    protected int[] m_joinIndexForLeft;
    protected int[] m_joinIndexForRight;

    protected JoinTable leftTable;
    protected JoinTable rightTable;

    protected int[] m_leftNamedInstance = null;
    protected int[] m_rightNamedInstance = null;

    protected JoinNode(Rule rule, ReteNetwork network, boolean isConcurrent) {
        super(rule, null, network);
    }

    public JoinNode(Rule rule, ReteNetwork network, Identifier[] leftIdentifiers, Identifier[] rightIdentifiers, Condition condition, boolean isConcurrent) throws SetupException {
        super(rule, null, network);
        m_leftIdentifiers  = leftIdentifiers;
        m_rightIdentifiers = rightIdentifiers;
        m_condition = condition;
        setNamedInstanceIndex();

        if (m_leftIdentifiers.length == 1) leftTable = new ObjectTableNoKey(m_leftIdentifiers);
        else leftTable = new TupleTableNoKey(m_leftIdentifiers);

        if(m_rightIdentifiers.length == 1) rightTable = new ObjectTableNoKey(m_rightIdentifiers);
        else rightTable = new TupleTableNoKey(m_rightIdentifiers);

        setJoinIdentifiers();
    }

    public Identifier[] getLeftIdentifiers() {
        return m_leftIdentifiers;
    }

    public Identifier[] getRightIdentifiers() {
        return m_rightIdentifiers;
    }

    protected void setNamedInstanceIndex() {
        if(m_leftIdentifiers.length > 1) {
            int[] leftIdx = new int[m_leftIdentifiers.length];
            int leftNumIdx = 0;
            for(int i = 0; i < m_leftIdentifiers.length; i++) {
                if(NamedInstance.class.isAssignableFrom(m_leftIdentifiers[i].getType())) {
                    leftIdx[leftNumIdx] = i;
                    leftNumIdx++;
                }
            }
            if(leftNumIdx > 0) {
                m_leftNamedInstance = new int[leftNumIdx];
                System.arraycopy(leftIdx, 0, m_leftNamedInstance, 0, leftNumIdx);
            }
        }

        if(m_rightIdentifiers.length > 1) {
            int[] rightIdx = new int[m_rightIdentifiers.length];
            int rightNumIdx = 0;
            for(int j = 0; j < m_rightIdentifiers.length; j++) {
                if(NamedInstance.class.isAssignableFrom(m_rightIdentifiers[j].getType())) {
                    rightIdx[rightNumIdx] = j;
                    rightNumIdx++;
                }
            }
            if(rightNumIdx > 0) {
                m_rightNamedInstance = new int[rightNumIdx];
                System.arraycopy(rightIdx, 0, m_rightNamedInstance, 0, rightNumIdx);
            }
        }
    }

    protected void setJoinIdentifiers() {
        numLeftIdentifier  = m_leftIdentifiers.length;
        numRightIdentifier = m_rightIdentifiers.length;
        numTotalIdentifier = m_leftIdentifiers.length + m_rightIdentifiers.length;
        m_identifiers = new Identifier[numTotalIdentifier];

        m_joinIndexForLeft  = new int[numLeftIdentifier];
        m_joinIndexForRight = new int[numRightIdentifier];
        for(int i = 0; i < numLeftIdentifier; i++) {
            m_joinIndexForLeft[i] = -1;
        }
        for(int i = 0; i < numRightIdentifier; i++) {
            m_joinIndexForRight[i] = -1;
        }
        int numConditionIdrs = 0;
        if(m_condition != null) {
            Identifier[] condIdr = m_condition.getIdentifiers();
            numConditionIdrs = condIdr.length;
            for(int i = 0; i < numConditionIdrs; i++) {
                int idx = IdentifierUtil.getIndex(m_leftIdentifiers,condIdr[i]);
                if (idx != -1) {
                    m_joinIndexForLeft[idx] = i;
                    m_identifiers[i] = m_leftIdentifiers[idx];
                }
                else {
                    idx = IdentifierUtil.getIndex(m_rightIdentifiers,condIdr[i]);
                    if(idx != -1) {
                        m_joinIndexForRight[idx] = i;
                        m_identifiers[i] = m_rightIdentifiers[idx];
                    }
                    else {
                        throw new RuntimeException("Program Error: Identifier position can't be null");
                    }
                }
            }
        }
        //set the remaining out identifier
        int outIndex = numConditionIdrs;
        for(int i = 0; i < numLeftIdentifier; i++) {
            if (m_joinIndexForLeft[i] == -1) {
                m_joinIndexForLeft[i] = outIndex;
                m_identifiers[outIndex] = m_leftIdentifiers[i];
                outIndex++;
            }
        }
        for(int i = 0; i < numRightIdentifier; i++) {
            if (m_joinIndexForRight[i] == -1) {
                m_joinIndexForRight[i] = outIndex;
                m_identifiers[outIndex] = m_rightIdentifiers[i];
                outIndex++;
            }
        }
        if(outIndex != numTotalIdentifier) {
            throw new RuntimeException("Program Error: outIndex should be equal to total number of identifier");
        }
    }

    void assertObjects(Handle[] handles, Object[] objs, boolean right) {
        Object[] joinedObjects = new Object[numTotalIdentifier];
        Handle[] joinedHandles = new Handle[numTotalIdentifier];
        if (right) {
            assertFromRight(handles, objs, joinedObjects, joinedHandles);
        }
        else {
            assertFromLeft(handles, objs, joinedObjects, joinedHandles);
        }
    }

    MatchedList findMatch(MatchedList resultList, boolean right) {
        if(right) { //right table
            return this.matchFromRight(resultList);
        }
        else {      //left table
            return this.matchFromLeft(resultList);
        }
    }

    protected void joinLeftObjects(Handle[] handles, Object[] leftObjs, Object[] joinedObjects, Handle[] joinedHandles) {
        for(int i = 0; i < numLeftIdentifier; i++) {
            joinedObjects[m_joinIndexForLeft[i]] = leftObjs[i];
            joinedHandles[m_joinIndexForLeft[i]] = handles[i];
        }
    }

    //returns false if one of the getObject calls returned null
    protected boolean joinLeftObjects(Handle[] handles, Object[] joinedObjects, Handle[] joinedHandles) {
        for(int i = 0; i < numLeftIdentifier; i++) {
            Object obj = handles[i].getObject();
            if(obj == null) return false;
            joinedObjects[m_joinIndexForLeft[i]] = obj;
            joinedHandles[m_joinIndexForLeft[i]] = handles[i];
        }
        return true;
    }

    protected Object[] joinLeftObjects__(Object[] leftObjs) {
        Object[] ret = new Object[numTotalIdentifier];
        for(int i = 0; i < numLeftIdentifier; i++) {
            ret[m_joinIndexForLeft[i]] = leftObjs[i];
        }
        return ret;
    }

    //returns null if handle.getObject() returns null at any point
    protected Object[] joinLeftObjects__(Object[] rightObjs, Handle handle, Map objectMap) {
        Object[] ret =  new Object[numTotalIdentifier];
        for(int i = 0; i < numRightIdentifier; i++)
            ret[m_joinIndexForRight[i]] = rightObjs[i];
        Object obj;
        if(handle instanceof AbstractElementHandle) {
            Long key = new Long(((AbstractElementHandle)handle).getElementId());
            obj = objectMap.get(key);
            if(obj == null) {
                obj = handle.getObject();
                if(obj == null) return null;
                objectMap.put(key, obj);
            }
        }
        else if(handle instanceof AbstractEventHandle) {
            Long key = new Long(((AbstractEventHandle)handle).getEventId());
            obj = objectMap.get(key);
            if(obj == null) {
                obj = handle.getObject();
                if(obj == null) return null;
                objectMap.put(key, obj);
            }
        }
        else {
            obj = handle.getObject();
            if(obj == null) return null;
        }
        ret[m_joinIndexForLeft[0]] = obj;
        return ret;
    }

    //returns null if handle.getObject() returns null at any point
    protected Object[] joinLeftObjects__(Object[] leftObjs, Handle[] row, Map objectMap) {
        Object[] ret =  new Object[numTotalIdentifier];
        for(int i = 0; i < numRightIdentifier; i++)
            ret[m_joinIndexForRight[i]] = leftObjs[i];

        for(int k=0; k < row.length; k++) {
            if(row[k] instanceof AbstractElementHandle) {
                Long key = new Long(((AbstractElementHandle)row[k]).getElementId());
                Object obj = objectMap.get(key);
                if(obj == null) {
                    obj = row[k].getObject();
                    if(obj == null) return null;
                    objectMap.put(key, obj);
                }
                ret[m_joinIndexForLeft[k]] = obj;
            }
            else if(row[k] instanceof AbstractEventHandle) {
                Long key = new Long(((AbstractEventHandle)row[k]).getEventId());
                Object obj = objectMap.get(key);
                if(obj == null) {
                    if(obj == null) return null;
                    obj = row[k].getObject();
                    objectMap.put(key, obj);
                }
                ret[m_joinIndexForLeft[k]] = obj;
            }
            else {
                Object obj = row[k].getObject();
                if(obj == null) return null;
                ret[m_joinIndexForLeft[k]] = obj;
            }
        }
        return ret;
    }

    protected void joinRightObjects(Handle[] handles, Object[] rightObjs, Object[] joinedObjects, Handle[] joinedHandles) {
        for(int i = 0; i < numRightIdentifier; i++) {
            joinedObjects[m_joinIndexForRight[i]] = rightObjs[i];
            joinedHandles[m_joinIndexForRight[i]] = handles[i];
        }
    }

    
    //returns false if handle.getObject() returns null at any time
    protected boolean joinRightObjects(Handle[] handles, Object[] joinedObjects, Handle[] joinedHandles) {
        for(int i = 0; i < numRightIdentifier; i++) {
            Object obj = handles[i].getObject();
            if(obj == null) return false;
            joinedObjects[m_joinIndexForRight[i]] = obj;
            joinedHandles[m_joinIndexForRight[i]] = handles[i];
        }
        return true;
    }

    protected Object[] joinRightObjects__(Object[] rightObjs) {
        Object[] ret = new Object[numTotalIdentifier];
        for(int i = 0; i < numRightIdentifier; i++) {
            ret[m_joinIndexForRight[i]] = rightObjs[i];
        }
        return ret;
    }

    //returns null if handle.getObject() returns null at any point
    protected Object[] joinRightObjects__(Object[] leftObjs, Handle handle, Map objectMap) {
        Object[] ret =  new Object[numTotalIdentifier];
        for(int i = 0; i < numLeftIdentifier; i++)
            ret[m_joinIndexForLeft[i]] = leftObjs[i];

        Object obj;
        if(handle instanceof AbstractElementHandle) {
            Long key = new Long(((AbstractElementHandle)handle).getElementId());
            obj = objectMap.get(key);
            if(obj == null) {
                obj = handle.getObject();
                if(obj == null) return null;
                objectMap.put(key, obj);
            }
        }
        else if(handle instanceof AbstractEventHandle) {
            Long key = new Long(((AbstractEventHandle)handle).getEventId());
            obj = objectMap.get(key);
            if(obj == null) {
                obj = handle.getObject();
                if(obj == null) return null;
                objectMap.put(key, obj);
            }
        }
        else {
            obj = handle.getObject();
            if(obj == null) return null;
        }
        ret[m_joinIndexForRight[0]] = obj;
        return ret;
    }

    //returns null if handle.getObject() returns null at any point
    protected Object[] joinRightObjects__(Object[] leftObjs, Handle[] row, Map objectMap) {
        Object[] ret =  new Object[numTotalIdentifier];
        for(int i = 0; i < numLeftIdentifier; i++)
            ret[m_joinIndexForLeft[i]] = leftObjs[i];

        for(int k=0; k < row.length; k++) {
            if(row[k] instanceof AbstractElementHandle) {
                Long key = new Long(((AbstractElementHandle)row[k]).getElementId());
                Object obj = objectMap.get(key);
                if(obj == null) {
                    obj = row[k].getObject();
                    if(obj == null) return null;
                    objectMap.put(key, obj);
                }
                ret[m_joinIndexForRight[k]] = obj;
            }
            else if(row[k] instanceof AbstractEventHandle) {
                Long key = new Long(((AbstractEventHandle)row[k]).getEventId());
                Object obj = objectMap.get(key);
                if(obj == null) {
                    obj = row[k].getObject();
                    if(obj == null) return null;
                    objectMap.put(key, obj);
                }
                ret[m_joinIndexForRight[k]] = obj;
            }
            else {
                Object obj = row[k].getObject();
                if(obj == null) return null;
                ret[m_joinIndexForRight[k]] = obj;
            }
        }
        return ret;
    }

    public void assertFromLeft(Handle[] handles, Object[] leftObjs, Object[] joinedObjects, Handle[] joinedHandles) {
        joinLeftObjects(handles, leftObjs, joinedObjects,  joinedHandles);
        if(leftTable instanceof ObjectTable) {
            if(!((ObjectTableNoKey)leftTable).add(handles[0]))
                m_logger.log(Level.WARN,"Object <" + leftObjs[0] + "> already added to the left ObjectTable of JoinNode" + Format.BRK + this.toString());
        }
        else {
            TupleRow row = new TupleRowNoKey(handles);
            ((TupleTableNoKey)leftTable).add(row);
        }
        if(m_wm.isLoadingObjects() && loadStopHere) return;  //if it is in loadObject mode, return

        if(m_wm.m_reteListener != null)
            m_wm.m_reteListener.joinConditionStart(this, true);

        int numSuccess = 0;
        int numFailed = 0;
        rightTable.lock();
        try {
            Iterator ite = rightTable.iterator();
            while(ite.hasNext()) {
                //check condition
                try {
                    //joinRightObjects returns false if handle.getObject() returns null
                    if(!joinRightObjects((Handle[])ite.next(), joinedObjects, joinedHandles)) {
                        numFailed++;
                        continue;
                    }
                    Object[] _objects = joinedObjects;
                    boolean _eval = (m_condition == null || m_condition.eval(_objects));
                    if(_eval) {
                        numSuccess++;
                        propagateObjects(joinedHandles, _objects);
                    }
                    else {
                        numFailed++;
                    }
                }
                catch(ForceConditionFailureException fcfe) {
                    numFailed++;
                    m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " + m_condition + " with " + Format.objsToStr(leftObjs));
                    continue;
                }
                catch(RuntimeException ex) {
                    numFailed++;
                    if (m_condition != null) {
                        String errMsg =ResourceManager.formatString("rule.condition.exception", getWorkingMemory(), m_condition.getRule().getName(), m_condition, Format.objsToStr(leftObjs));
                        m_logger.log(Level.ERROR,errMsg, ex);
                    }
                    else {
                        m_logger.log(Level.ERROR,ex.getLocalizedMessage(), ex);
                    }
                    continue;
                }
            }
        } finally {
            rightTable.unlock();
        }

        if(m_wm.m_reteListener != null)
            m_wm.m_reteListener.joinConditionEnd(numSuccess, numFailed);
    }

    protected MatchedList matchFromLeft(MatchedList resultList) {
        Map ObjectMap = resultList.getObjectMaps();
        MatchedList newResultList = new MatchedList(resultList.length(), ObjectMap);
        for(int i = 0; i < resultList.length(); i++) {
            Object[] leftObjs = resultList.getRow(i);
            if(leftObjs == null)
                continue;
            if(rightTable instanceof ObjectTable) {
                Handle[] handles = ((ObjectTableNoKey)rightTable).getAllHandles();
                for(int j = 0; j < handles.length; j++) {
                    if(handles[j] == null) continue;
                    try {
                        Object[] joined = this.joinRightObjects__(leftObjs, handles[j], ObjectMap);
						//joinRightObjects__ returns false if handle.getObject() returns null
                        if(joined != null && (m_condition == null || m_condition.eval(joined)))
                            newResultList.addRow(joined);
                    }
                    catch(Exception ex) {}  //eat}
                }
            }
            else {  //join Table
                Handle[][] rows = ((TupleTableNoKey)rightTable).getAllRows();
                for(int j = 0; j < rows.length; j++) {
                    if(rows[j] == null) continue;
                    try {
                        Object[] joined = this.joinRightObjects__(leftObjs, rows[j], ObjectMap);
						//joinRightObjects__ returns false if handle.getObject() returns null
                        if(joined != null && (m_condition == null || m_condition.eval(joined)))
                            newResultList.addRow(joined);
                    }
                    catch(Exception ex) {}  //eat}
                }
            }
        }
        if(newResultList.length() == 0)
            return null;
        return this.m_nextNodeLink.findMatch(newResultList);
    }


    public void assertFromRight(Handle[] handles, Object[] rightObjs, Object[] joinedObjects, Handle[] joinedHandles) {
        joinRightObjects(handles, rightObjs, joinedObjects, joinedHandles);
        if(rightTable instanceof ObjectTable) {
            if(!((ObjectTableNoKey)rightTable).add(handles[0]))
                m_logger.log(Level.WARN,"Object <" + rightObjs[0] + "> already added to the right ObjectTable of JoinNode" + Format.BRK + this.toString());
        }
        else {
            TupleRow row = new TupleRowNoKey(handles);
            ((TupleTableNoKey)rightTable).add(row);
        }
        if(m_wm.isLoadingObjects() && loadStopHere) return;  //if it is in loadObject mode, return

        if(m_wm.m_reteListener != null)
            m_wm.m_reteListener.joinConditionStart(this, false);

        int numSuccess = 0;
        int numFailed = 0;
        leftTable.lock();
        try {
            Iterator ite = leftTable.iterator();
            while(ite.hasNext()) {
                //check condition
                try {
                    //if joinLeftObjects calls getObject on a handle and gets null
                    //it will return false
                    if(!joinLeftObjects((Handle[])ite.next(), joinedObjects, joinedHandles)) {
                        numFailed++;
                        continue;
                    }
                    Object[] _objects = joinedObjects;
                    boolean _eval = (m_condition == null || m_condition.eval(_objects));
                    if(_eval) {
                        numSuccess++;
                        propagateObjects(joinedHandles, _objects);
                    }
                    else {
                        numFailed++;
                    }
                }
                catch(ForceConditionFailureException fcfe) {
                    numFailed++;
                    m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " + m_condition + " with " + Format.objsToStr(rightObjs));
                    continue;
                }
                catch(RuntimeException ex) {
                    numFailed++;
                    if (m_condition != null) {
                        String errMsg =ResourceManager.formatString("rule.condition.exception", getWorkingMemory(), m_condition.getRule().getName(), m_condition, Format.objsToStr(rightObjs));
                        m_logger.log(Level.ERROR,errMsg, ex);
                    }
                    else {
                        m_logger.log(Level.ERROR,ex.getLocalizedMessage(), ex);
                    }
                    continue;
                }
            }
        } finally {
            leftTable.unlock();
        }
        if(m_wm.m_reteListener != null)
            m_wm.m_reteListener.joinConditionEnd(numSuccess, numFailed);
    }

    protected MatchedList matchFromRight(MatchedList resultList) {
        Map ObjectMap = resultList.getObjectMaps();
        MatchedList newResultList = new MatchedList(resultList.length(), ObjectMap);
        for(int i = 0; i < resultList.length(); i++) {
            Object[] rightObjs = resultList.getRow(i);
            if(rightObjs == null)
                continue;
            if(leftTable instanceof ObjectTable) {
                Handle[] handles = ((ObjectTableNoKey)leftTable).getAllHandles();
                for(int j = 0; j < handles.length; j++) {
                    if(handles[j] == null) continue;
                    try {
                        Object[] joined = this.joinLeftObjects__(rightObjs, handles[j], ObjectMap);
						//joinLeftObjects__ returns false if handle.getObject() returns null
                        if(joined != null && (m_condition == null || m_condition.eval(joined)))
                            newResultList.addRow(joined);
                    }
                    catch(Exception ex) {}  //eat}
                }
            }
            else {  //join Table
                Handle[][] rows = ((TupleTableNoKey)leftTable).getAllRows();
                for(int j = 0; j < rows.length; j++) {
                    if(rows[j] == null) continue;
                    try {
                        Object[] joined = this.joinLeftObjects__(rightObjs, rows[j], ObjectMap);
						//joinLeftObjects__ returns false if handle.getObject() returns null
                        if(joined != null && (m_condition == null || m_condition.eval(joined)))
                            newResultList.addRow(joined);
                    }
                    catch(Exception ex) {}  //eat}
                }
            }
        }
        if(newResultList.length() == 0)
            return null;
        return this.m_nextNodeLink.findMatch(newResultList);
    }



    public String toString() {
        String joinIndexForLeft  = new String();
        String joinIndexForRight = new String();

        for(int i = 0; i < m_joinIndexForLeft.length; i++) {
            joinIndexForLeft += m_joinIndexForLeft[i] + " ";
        }

        for(int i = 0;  i < m_joinIndexForRight.length; i++) {
            joinIndexForRight += m_joinIndexForRight[i] + " ";
        }

        String linkTo = new String();
        if(m_nextNodeLink.getLinkToNode() instanceof JoinNode) {
            if(m_nextNodeLink.isLinkToRight()) {
                linkTo = m_nextNodeLink.getLinkToNode().getNodeId() + "R";
            }
            else {
                linkTo = m_nextNodeLink.getLinkToNode().getNodeId() + "L";
            }
        }
        else {
            linkTo = ""+ m_nextNodeLink.getLinkToNode().getNodeId();
        }

        String tableIds = "" + leftTable.getTableId() + "L," + rightTable.getTableId() + "R";

        return "\t[JoinNode("+m_nodeId+") link("+linkTo+") stopLoad(" + loadStopHere + ") tables(" + tableIds + "): " + Format.BRK +
                "\t\tLeft Identifier      = " + IdentifierUtil.toString(m_leftIdentifiers) + ";" + Format.BRK +
                "\t\tRight Identifier     = " + IdentifierUtil.toString(m_rightIdentifiers) + ";" + Format.BRK +
                "\t\tOut Identifier       = " + IdentifierUtil.toString(m_identifiers) + ";" + Format.BRK +
                "\t\tCondition Identifier = " + (m_condition==null?"":IdentifierUtil.toString(m_condition.getIdentifiers()) )+ ";" + Format.BRK +
                "\t\tJoin Left Index      = " + joinIndexForLeft + ";" + Format.BRK +
                "\t\tJoin Right Index     = " + joinIndexForRight + ";" + Format.BRK +
                "\t\tCondition            = " + m_condition + "]";
    }

    public Condition getCondition() {
        return m_condition;
    }
}
