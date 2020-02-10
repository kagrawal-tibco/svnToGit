package com.tibco.cep.kernel.core.rete;

import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.kernel.core.base.tuple.ObjectTable;
import com.tibco.cep.kernel.core.base.tuple.ObjectTableWithKey;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.TupleRowWithKey;
import com.tibco.cep.kernel.core.base.tuple.TupleTableWithKey;
import com.tibco.cep.kernel.helper.ForceConditionFailureException;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.EquivalentCondition;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 4:23:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class EquivalentJoinNode extends JoinNode {
    /**
     * IF YOU MODIFY THIS CLASS IN ANY WAY, PLEASE ENSURE THE FOLLOWING LINE NUMBERS ARE VALID.
     * THESE ARE THE LINE NUMBERS CORRESPONDING TO LINES CONTAINING if(_eval) {...
     **/
    public static int debugLineNumber0 = 105;
    public static int debugLineNumber1 = 211;

    Identifier[] m_conditionLeftIdrs;
    Identifier[] m_conditionRightIdrs;

    public EquivalentJoinNode(Rule rule, ReteNetwork network, Identifier[] leftIdentifiers, Identifier[] rightIdentifiers, Condition condition, boolean isConcurrent) throws SetupException {
        super(rule, network, isConcurrent);
        m_leftIdentifiers  = leftIdentifiers;
        m_rightIdentifiers = rightIdentifiers;
        m_condition = condition;
        setNamedInstanceIndex();

        if (m_leftIdentifiers.length == 1) leftTable = new ObjectTableWithKey(m_leftIdentifiers);
        else leftTable = new TupleTableWithKey(m_leftIdentifiers);

        if(m_rightIdentifiers.length == 1) rightTable = new ObjectTableWithKey(m_rightIdentifiers);
        else rightTable = new TupleTableWithKey(m_rightIdentifiers);
        
        setJoinIdentifiers();

        if(!(condition instanceof EquivalentCondition)) {
            throw new RuntimeException("Program Error: Condition passed in is not a EquivalentCondition");
        }
        m_conditionLeftIdrs  = ((EquivalentCondition)condition).getLeftIdentifiers();
        m_conditionRightIdrs = ((EquivalentCondition)condition).getRightIdentifiers();
    }

    @Override
    public void assertFromLeft(Handle[] handles, Object[] leftObjs, Object[] joinedObjects, Handle[] joinedHandles) {
        joinLeftObjects(handles, leftObjs, joinedObjects, joinedHandles);
        int hashCode;
        try {
            hashCode = ((EquivalentCondition)m_condition).leftExpHashcode(joinedObjects);
        }
        catch(ForceConditionFailureException fcfe) {
            m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " +  m_condition + " with " + Format.objsToStr(leftObjs));
            return;
        }
        catch(RuntimeException ex) {
            String infoMsg =ResourceManager.formatString("rule.condition.index.exception", m_condition.getRule().getName(), m_condition, Format.objsToStr(leftObjs));
            m_logger.log(Level.INFO,infoMsg, ex);
            return;
        }
        if(leftTable instanceof ObjectTable) {
            if(!((ObjectTableWithKey)leftTable).add(handles[0], hashCode))
                m_logger.log(Level.WARN,"Object <" + leftObjs[0] + "> already added to the left ObjectTable of JoinNode" + Format.BRK + this.toString());
        }
        else {
            TupleRow row = new TupleRowWithKey(handles, hashCode);
            ((TupleTableWithKey)leftTable).add(row);
        }
        if(m_wm.isLoadingObjects() && loadStopHere) return;  //if it is in loadObject mode, return

        if(m_wm.m_reteListener != null)
            m_wm.m_reteListener.joinConditionStart(this, true);

        int numSuccess = 0;
        int numFailed = 0;

        rightTable.lock();
        try {
            Iterator ite = rightTable.keyIterator(hashCode);
            while(ite.hasNext()) {
                //check condition
                try {
                    //joinRightObjects returns false if handle.getObject() returns null
                    if(!joinRightObjects((Handle[])ite.next(),joinedObjects, joinedHandles)) {
                        numFailed++;
                        continue;
                    }
                    Object[] _objects = joinedObjects;
                    boolean _eval = m_condition.eval(_objects);
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
                   m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " +  m_condition + " with " + Format.objsToStr(leftObjs));
                   continue;
                }
                catch(RuntimeException ex) {
                    numFailed++;
                    String errMsg =ResourceManager.formatString("rule.condition.exception", m_condition.getRule().getName(), m_condition, Format.objsToStr(leftObjs));
                     m_logger.log(Level.ERROR,errMsg, ex);
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
            int hashCode = 0;
            try {
                hashCode = ((EquivalentCondition)m_condition).leftExpHashcode(joinLeftObjects__(leftObjs));
            }
            catch(Exception ex) {} //eat

            if(rightTable instanceof ObjectTable) {
                Handle[] handles = ((ObjectTableWithKey)rightTable).getAllHandles(hashCode);
                for(int j = 0; j < handles.length; j++) {
                    if(handles[j] == null) continue;
                    try {
                        Object[] joined = this.joinRightObjects__(leftObjs, handles[j], ObjectMap);
						//joinRightObjects returns null if handle.getObject() returns null
                        if(joined != null && m_condition.eval(joined))
                            newResultList.addRow(joined);
                    }
                    catch(Exception ex) {}  //eat}
                }
            }
            else {  //join Table
                Handle[][] rows = ((TupleTableWithKey)rightTable).getAllRows(hashCode);
                for(int j = 0; j < rows.length; j++) {
                    if(rows[j] == null) continue;
                    try {
                        Object[] joined = this.joinRightObjects__(leftObjs, rows[j], ObjectMap);
						//joinRightObjects returns null if handle.getObject() returns null
                        if(joined != null && m_condition.eval(joined))
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

    @Override
    public void assertFromRight(Handle[] handles, Object[] rightObjs, Object[] joinedObjects, Handle[] joinedHandles) {
        joinRightObjects(handles, rightObjs, joinedObjects, joinedHandles);
        int hashCode;
        try {
            hashCode = ((EquivalentCondition)m_condition).rightExpHashcode(joinedObjects);
        }
        catch(ForceConditionFailureException fcfe) {
            m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " +  m_condition + " with " + Format.objsToStr(rightObjs));
            return;
        }
        catch(RuntimeException ex) {
            String infoMsg =ResourceManager.formatString("rule.condition.index.exception", m_condition.getRule().getName(), m_condition, Format.objsToStr(rightObjs));
            m_logger.log(Level.INFO,infoMsg, ex);
            return;
        }
        if(rightTable instanceof ObjectTable) {
            if(!((ObjectTableWithKey)rightTable).add(handles[0], hashCode))
                m_logger.log(Level.WARN,"Object <" + rightObjs[0] + "> already added to the right ObjectTable of JoinNode" + Format.BRK + this.toString());
        }
        else {
            TupleRow row = new TupleRowWithKey(handles, hashCode);
            ((TupleTableWithKey)rightTable).add(row);
        }
        if(m_wm.isLoadingObjects() && loadStopHere) return;  //if it is in loadObject mode, return

        if(m_wm.m_reteListener != null)
            m_wm.m_reteListener.joinConditionStart(this, false);

        int numSuccess = 0;
        int numFailed = 0;
        
        leftTable.lock();
        try {
            Iterator ite = leftTable.keyIterator(hashCode);
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
                    boolean _eval = m_condition.eval(_objects);
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
                     m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " +  m_condition + " with " + Format.objsToStr(rightObjs));
                    continue;
                }
                catch(RuntimeException ex) {
                    numFailed++;
                    String errMsg =ResourceManager.formatString("rule.condition.exception", m_condition.getRule().getName(), m_condition, Format.objsToStr(rightObjs));
                     m_logger.log(Level.ERROR,errMsg, ex);
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
            int hashCode = 0;
            try {
                hashCode = ((EquivalentCondition)m_condition).rightExpHashcode(joinRightObjects__(rightObjs));
            }
            catch(Exception ex) {} //eat

            if(leftTable instanceof ObjectTable) {
                Handle[] handles = ((ObjectTableWithKey)leftTable).getAllHandles(hashCode);
                for(int j = 0; j < handles.length; j++) {
                    if(handles[j] == null) continue;
                    try {
                        Object[] joined = this.joinLeftObjects__(rightObjs, handles[j], ObjectMap);
						//joinLeftObjects returns null if handle.getObject() returns null
                        if(joined!= null && m_condition.eval(joined))
                            newResultList.addRow(joined);
                    }
                    catch(Exception ex) {}  //eat}
                }
            }
            else {  //join Table
                Handle[][] rows = ((TupleTableWithKey)leftTable).getAllRows(hashCode);
                for(int j = 0; j < rows.length; j++) {
                    if(rows[j] == null) continue;
                    try {
                        Object[] joined = this.joinLeftObjects__(rightObjs, rows[j], ObjectMap);
						//joinLeftObjects returns null if handle.getObject() returns null
                        if(joined != null && m_condition.eval(joined))
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

        return "\t[EquivalentJoinNode("+m_nodeId+") link("+linkTo+") stopLoad(" + loadStopHere + ") tables(" + tableIds + "): " + Format.BRK +
                "\t\tLeft Identifier      = " + IdentifierUtil.toString(m_leftIdentifiers) + ";" + Format.BRK +
                "\t\tRight Identifier     = " + IdentifierUtil.toString(m_rightIdentifiers) + ";" + Format.BRK +
                "\t\tOut Identifier       = " + IdentifierUtil.toString(m_identifiers) + ";" + Format.BRK +
                "\t\tCondition Identifier = " + IdentifierUtil.toString(m_condition.getIdentifiers())+ ";" + Format.BRK +
                "\t\tLeft Exp Identifier  = " + IdentifierUtil.toString(m_conditionLeftIdrs)+ ";" + Format.BRK +
                "\t\tRight Exp Identifier = " + IdentifierUtil.toString(m_conditionRightIdrs)+ ";" + Format.BRK +
                "\t\tJoin Left Index      = " + joinIndexForLeft + ";" + Format.BRK +
                "\t\tJoin Right Index     = " + joinIndexForRight + ";" + Format.BRK +
                "\t\tCondition            = " + m_condition + "]";
    }
}
