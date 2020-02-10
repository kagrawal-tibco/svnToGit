package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.helper.ForceConditionFailureException;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:18:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterNode extends Node {
    /**
     * IF YOU MODIFY THIS CLASS IN ANY WAY, PLEASE ENSURE THE FOLLOWING LINE NUMBERS ARE VALID.
     * THESE ARE THE LINE NUMBERS CORRESPONDING TO LINES CONTAINING if(_eval) {...
     **/
    public static final int debugLineNumber0 = 107;
    public static final int debugLineNumber1 = 116;

    protected Condition  m_condition;
    protected int[]      m_convertIndex;
//    private Handle[]     convertedHandles;  //not thread-safe
//    private Object[]     convertedObjects;  //not thread-safe


    public FilterNode(Rule rule, ReteNetwork network, Identifier[] identifier, Condition condition) {
        super(rule, identifier, network);
        m_condition = condition;
        setConvertIndex();
    }

    void setConvertIndex() {
        if(m_condition == null) {
            return;
        }
        Identifier[] condIdr = m_condition.getIdentifiers();
        if(condIdr == null) {
            condIdr = new Identifier[0];
        }
        else {
            m_convertIndex = new int[condIdr.length];
        }
        for(int i = 0; i < condIdr.length; i++) {
            int idx = IdentifierUtil.getIndex(m_identifiers,condIdr[i]);
            if(idx != -1) {
                m_convertIndex[i] = idx;
            }
            else {
                throw new RuntimeException("Program Error: Identifier position can't be null");
            }
        }
        //see if this is inorder
        boolean inorder = true;
        for(int i = 0; i < m_convertIndex.length; i++) {
            if (m_convertIndex[i] != i) {
                inorder = false;
                break;
            }
        }
        if(inorder) {
            m_convertIndex = null;
        }
//        else {
//            convertedHandles = new Handle[m_convertIndex.length];
//            convertedObjects = new Object[m_convertIndex.length];
//        }
    }

    protected Object[] convert(Object[] orgObjects) {
        Object[] newObjects = new Object[orgObjects.length];
        for(int i=0; i < m_convertIndex.length; i++) {
            newObjects[i] = orgObjects[m_convertIndex[i]];
        }
        return newObjects;
    }

    protected void convert(Handle[] handles, Object[] objects) {
//        for(int i=0; i < m_convertIndex.length; i++) {
//            convertedObjects[i] = objects[m_convertIndex[i]];
//            convertedHandles[i] = handles[m_convertIndex[i]];
//        }
    }

    void assertObjects(Handle[] handles, Object[] objects, boolean right) {
        if(m_wm.isLoadingObjects() && loadStopHere) return;  //if it is in loadObject mode, return
        if (m_condition == null){
            propagateObjects(handles, objects);
            return;
        }
        else {
            boolean evalSuccess = false;
            if(m_wm.m_reteListener != null) {
                m_wm.m_reteListener.filterConditionStart(this);
            }
            try {
                if(m_convertIndex == null) {
                    Object[] _objects = objects;
                    boolean _eval = m_condition.eval(_objects);
                    if(_eval) {
                        evalSuccess = true;
                        propagateObjects(handles, objects);
                    }
                }
                else {
                    convert(handles, objects);
                    Handle [] convertedHandles = new Handle[m_convertIndex.length];
                    Object [] convertedObjects = new Object[m_convertIndex.length];

                    for(int i=0; i < m_convertIndex.length; i++) {
                        convertedObjects[i] = objects[m_convertIndex[i]];
                        convertedHandles[i] = handles[m_convertIndex[i]];
                    }

                    Object[] _objects = convertedObjects;
                    boolean _eval = m_condition.eval(_objects);
                    if(_eval) {
                        evalSuccess = true;
                        propagateObjects(handles, objects);
                    }
                }
            }
            catch(ForceConditionFailureException fcfe) {
                m_logger.log(Level.DEBUG,"Forced condition failure in " + getWorkingMemory().getName() + " : " + m_condition.getRule().getName() + " : " + m_condition + " with " + Format.objsToStr(objects));
            }
            catch(RuntimeException ex) {
                String errMsg =ResourceManager.formatString("rule.condition.exception", m_condition.getRule().getName(), m_condition, Format.objsToStr(objects));
                m_logger.log(Level.ERROR,errMsg, ex);
            }
            if(m_wm.m_reteListener != null)
                m_wm.m_reteListener.filterConditionEnd(evalSuccess);
        }
    }

    MatchedList findMatch(MatchedList resultList, boolean right) {
        if (m_condition != null){
            if(m_convertIndex == null) {
                for(int i=0; i < resultList.length(); i++) {
                    Object[] objs = resultList.getRow(i);
                    if(objs != null) {
                        try {
                            if(!m_condition.eval(objs))
                                resultList.nullRow(i);  //remove if not match the condition
                        }
                        catch(Exception ex) {   //eat this
                            resultList.nullRow(i);
                        }
                    }
                }
            }
            else {
                for(int i=0; i < resultList.length(); i++) {
                    Object[] objs = resultList.getRow(i);
                    if(objs != null) {
                        Object[] cvtObjs = convert(objs);
                        try {
                            if(m_condition.eval(cvtObjs))
                                resultList.chgRow(i, cvtObjs);  //reset if match
                            else
                                resultList.nullRow(i);    //remove if not match
                        }
                        catch(Exception ex) {   //eat this
                            resultList.nullRow(i);
                        }
                    }
                }
            }
            if(resultList.numRow() == 0)
                return null;
        }
        return m_nextNodeLink.findMatch(resultList);
    }

    public String toString() {
        String cond  = new String();

        if (m_condition != null) {
            for(int i = 0; i < m_condition.getIdentifiers().length; i++) {
                cond += m_condition.getIdentifiers()[i] + " ";
            }
        }

        String linkTo = new String();
        if(m_nextNodeLink.m_child instanceof JoinNode) {
            if(m_nextNodeLink.m_childRight) {
                linkTo = m_nextNodeLink.m_child.m_nodeId + "R";
            }
            else {
                linkTo = m_nextNodeLink.m_child.m_nodeId + "L";
            }
        }
        else {
            linkTo = ""+ m_nextNodeLink.m_child.m_nodeId;
        }


        return "\t[FilterNode id("+m_nodeId+") stopLoad(" + loadStopHere +") link("+linkTo+"): " + Format.BRK +
                "\t\tIdentifier           = " + IdentifierUtil.toString(this.m_identifiers) + " ;" + Format.BRK +
                "\t\tCondition Identifier = " + cond + ";" + Format.BRK +
                "\t\tCondition            = " + m_condition + "]";
    }

    public Condition getCondition() {
        return m_condition;
    }
}
