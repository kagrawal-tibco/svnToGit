package com.tibco.cep.kernel.core.rete;

import java.util.HashMap;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.ObjectTable;
import com.tibco.cep.kernel.core.base.tuple.TupleTable;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:19:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassNodeLink extends NodeLink {

    protected Identifier m_identifier;
    protected Rule       m_rule;
    protected ClassNode  m_classNode;

    private ObjectTable  m_objectTable;
    private TupleTable[] m_tupleTables;   //This table for modify (the one in ClassNode for delete)

    private ReteWM m_wm;
    boolean loadStopHere = false;

    protected ClassNodeLink(ClassNode classNode, Node childNode, Rule rule, Identifier identifier) throws SetupException {
        super(childNode, false);
        if (childNode instanceof JoinNode) {
            throw new SetupException(ResourceManager.getString("rete.nodelink.childIsJoinNode"));
        }
        m_rule             = rule;
        m_identifier       = identifier;
        m_classNode        = classNode;
        m_tupleTables      = new TupleTable[0];
        m_objectTable      = null;
        m_wm               = (ReteWM) classNode.getWorkingMemory();
    }

    public void propagateObjects(Handle[] handles, Object[] objects) {
        if(m_wm.isLoadingObjects() && loadStopHere) return;  //if it is in loadObject mode, return
        super.propagateObjects(handles, objects);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public MatchedList findMatch(Object object) {
//        if(!m_rule.isActive()) throw new RuntimeException("Rule <" + m_rule.getName() + "> is not active");
        MatchedList s = new MatchedList(new Object[] {object}, new HashMap());
        return super.findMatch(s);
    }

    public String toString() {
        String nextNode;
        if(m_child instanceof JoinNode) {
            if (m_childRight) {
                nextNode = m_child.m_nodeId + "R";
            }
            else {
                nextNode = m_child.m_nodeId + "L";
            }
        }
        else {
            nextNode = Integer.toString(m_child.m_nodeId);
        }
        String mask = null;
        if(m_identifier.getDependencyBitArray() != null) {
            mask = new String();
            for(int i = 0; i < m_identifier.getDependencyBitArray().length; i++) {
                mask += Integer.toBinaryString(m_identifier.getDependencyBitArray()[i]);
                if(i != m_identifier.getDependencyBitArray().length-1) {
                    mask += " ";
                }
            }
        }

        String table = "";
        if(m_objectTable != null) {
            table += m_objectTable.getTableId();
        }
        else {
            table += "null";
        }
        for(int i = 0; i < m_tupleTables.length; i++) {
            if(m_objectTable == null)  throw new RuntimeException("ProgramError: ClassNodeLink contains TupleTable but no ObjectTable");
            table += " ";
            table += m_tupleTables[i].getTableId();
        }
        return "link(" + nextNode + ")\tmask(" + mask +")\tstopLoad(" + loadStopHere + ")\ttable(" + table + ")";
    }

    protected void addJoinTable(JoinTable table) {
        if(table instanceof ObjectTable) {
            if(m_objectTable == null)
                m_objectTable = (ObjectTable) table;
            else
                throw new RuntimeException("ProgramError: ClassNodeLink ObjectTable already exist");
        }
        else {
            Identifier[] idrs = table.getIdentifiers();
            for(int i = 0; i < idrs.length; i++) {
                if(idrs[i].equals(this.m_identifier)) {
                    addTupleTable((TupleTable) table);
                }
            }
        }
    }

    protected void recycleAllTables() {
        if(m_objectTable != null) {
            m_classNode.removeJoinTable(m_objectTable);
            m_objectTable.recycleTable();
            for(int i = 0; i < m_tupleTables.length; i++) {
                JoinTable table = (JoinTable) m_tupleTables[i];
                m_classNode.removeJoinTable(table);
                table.recycleTable();
            }
            m_objectTable = null;
            m_tupleTables = new TupleTable[0];
        }
        else if(m_tupleTables.length > 0) {
            throw new RuntimeException("ProgramError: ClassNodeLink contains TupleTable but no ObjectTable");
        }
    }

    protected void removeObject(Handle[] objHandle) {
        //remove the object from all table first
        if(m_objectTable != null) {
            if(m_objectTable.remove(objHandle[0])) {
                for(int i = 0; i < m_tupleTables.length; i++) {
                    ((BaseHandle)objHandle[0]).removeFromTable(m_tupleTables[i].getTableId());
                }
            }
        }
    }

    private void addTupleTable(TupleTable tupleTable) {
        if(m_tupleTables == null) {
            m_tupleTables = new TupleTable[1];
            m_tupleTables[0] = tupleTable;
        }
        else {
            TupleTable[] newTables = new TupleTable[m_tupleTables.length + 1];
            System.arraycopy(m_tupleTables, 0, newTables, 0, m_tupleTables.length);;
            newTables[m_tupleTables.length] = tupleTable;
            m_tupleTables = newTables;
        }
    }
}
