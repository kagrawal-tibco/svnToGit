package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.ObjectTable;
import com.tibco.cep.kernel.core.base.tuple.TupleTable;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:15:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassNode implements TypeInfo {
    ClassNode       m_super;
    ReteNetwork     m_rete;
    Class           m_class;
    ObjectTable[]   m_objectTables; //This table for delete (the one in ClassNodeLink for modify)
    TupleTable[]    m_tupleTables;  //This table for delete (the one in ClassNodeLink for modify)

    boolean         hasRule;
    ArrayList       subNodes;
    ClassNodeLink[] classLinks;
    HashMap<Integer, ClassNodeLink> ruleClassLinkMap = new HashMap<Integer, ClassNodeLink> ();
    protected EntitySharingLevel localSharingLevel = EntitySharingLevel.DEFAULT;
    protected EntitySharingLevel recursiveSharingLevel = EntitySharingLevel.DEFAULT;

    public int[][]  identifierIndexTable;   //given a rule as the first index, find the identifier index of this type in the rule

    public ClassNode(ReteNetwork reteNetwork, Class cl) {
        m_class        = cl;
        m_super        = null;
        m_rete         = reteNetwork;
        m_objectTables = new ObjectTable[0];
        m_tupleTables  = new TupleTable[0];

        classLinks = new ClassNodeLink[0];
        subNodes   = new ArrayList();
        hasRule    = true;
        setupIdentifierIndexTable();
    }

    public WorkingMemory getWorkingMemory() {
        return m_rete.getWorkingMemory();
    }

    public EntitySharingLevel getLocalSharingLevel() {
        return localSharingLevel;
    }
    public void setLocalSharingLevel(EntitySharingLevel lev) {
        localSharingLevel = lev;
    }
    public EntitySharingLevel getRecursiveSharingLevel() {
        return recursiveSharingLevel;
    }
    public void setRecursiveSharingLevel(EntitySharingLevel lev) {
        recursiveSharingLevel = lev;
    }

    public void setSuper(ClassNode superNode) {
        if(m_super != null)
            throw new RuntimeException("ProgramError: m_super already been set");
        else if(superNode == null)
            throw new RuntimeException("ProgramError: superNode is null");
        if(m_super == null && superNode != null) {
            m_super = superNode;
            superNode.subNodes.add(this);
        }
    }

    private void setupIdentifierIndexTable() {
        Iterator ite  = m_rete.rules.iterator();
        while(ite.hasNext()) {
            addRule((Rule) ite.next());
        }
    }

    protected void updateLastRuleId(int id) {
        if(identifierIndexTable == null) {
            identifierIndexTable = new int[id + 1][];
        }
        else if (id >= identifierIndexTable.length) {
            int[][] oldTable = identifierIndexTable;
            identifierIndexTable = new int[id + 1][];
            System.arraycopy(oldTable, 0, identifierIndexTable, 0, oldTable.length);
        }
    }

    protected void addRule(Rule rule) {
        setIdentifierIndex(rule);
        for(int i = 0; i < subNodes.size(); i++) {
            ((ClassNode)subNodes.get(i)).addRule(rule);
        }
    }

    private void setIdentifierIndex(Rule rule) {
        if(identifierIndexTable == null) {
            identifierIndexTable = new int[rule.getId() + 1][];
        }
        else if (rule.getId() >= identifierIndexTable.length) {
            int[][] oldTable = identifierIndexTable;
            identifierIndexTable = new int[rule.getId() + 1][];
            System.arraycopy(oldTable, 0, identifierIndexTable, 0, oldTable.length);
        }
        identifierIndexTable[rule.getId()] = analyzeDependency(rule);
    }

    private int[] analyzeDependency(Rule rule) {
        Identifier[] idrs = rule.getIdentifiers();
        int count = 0;
        int[] tempArr = new int[idrs.length];
        for(int i = 0; i < idrs.length; i++) {
            if(idrs[i].getType().isAssignableFrom(m_class)) {
                tempArr[count] = i;
                count++;
            }
        }
        if(count == 0) return null;
        int[] retArr = new int[count];
        for(int j = 0; j < count; j++) {
            retArr[j] = tempArr[j];
        }
        return retArr;
    }

    protected void removeRule(Rule rule) {
        if(identifierIndexTable == null) {
            identifierIndexTable = new int[rule.getId() + 1][];
        }
        else if ((rule).getId() >= identifierIndexTable.length) {
            int[][] oldTable = identifierIndexTable;
            identifierIndexTable = new int[(rule).getId() + 1][];
            System.arraycopy(oldTable, 0, identifierIndexTable, 0, oldTable.length);
        }
        identifierIndexTable[(rule).getId()] = null;
        for(int i = 0; i < subNodes.size(); i++) {
            ((ClassNode)subNodes.get(i)).removeRule(rule);
        }
    }

    protected boolean isNoAssociatedRule() {
        if(m_super == null) {
            if(classLinks == null || classLinks.length == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if(m_super.isNoAssociatedRule()) {
                if(classLinks == null || classLinks.length == 0)  {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
    }

    protected void addLink(ClassNodeLink link) {
        ruleClassLinkMap.put(link.m_rule.getId(), link);
        if(classLinks == null) {
            classLinks = new ClassNodeLink[1];
            classLinks[0] = link;
        }
        else {
            ClassNodeLink[] newLinks = new ClassNodeLink[classLinks.length + 1];
            System.arraycopy(classLinks, 0, newLinks, 0, classLinks.length);;
            newLinks[classLinks.length] = link;
            classLinks = newLinks;
        }
    }

    protected void removeLink(ClassNodeLink link) {
        if(classLinks == null) return;
        ruleClassLinkMap.remove(link.m_rule.getId());
        int i = 0;
        while(i < classLinks.length) {
            if(classLinks[i].equals(link)) {
                ClassNodeLink[] newLinks = new ClassNodeLink[classLinks.length-1];
                System.arraycopy(classLinks, 0, newLinks, 0, i);
                if(i < (classLinks.length -1))
                    System.arraycopy(classLinks, i+1, newLinks, i, classLinks.length - i -1);
                classLinks = newLinks;
            }
            else {
                i++;
            }
        }
    }


    private void addObjectTable(ObjectTable objTable) {
        if(m_objectTables == null) {
            m_objectTables = new ObjectTable[1];
            m_objectTables[0] = objTable;
        }
        else {
            ObjectTable[] newTables = new ObjectTable[m_objectTables.length + 1];
            System.arraycopy(m_objectTables, 0, newTables, 0, m_objectTables.length);;
            newTables[m_objectTables.length] = objTable;
            m_objectTables = newTables;
        }
    }

    private void removeObjectTable(ObjectTable objTable) {
        if(m_objectTables == null) return;
        int i = 0;
        while(i < m_objectTables.length) {
            if(m_objectTables[i].equals(objTable)) {
                ObjectTable[] newTables = new ObjectTable[m_objectTables.length-1];
                System.arraycopy(m_objectTables, 0, newTables, 0, i);
                if(i < (m_objectTables.length -1))
                    System.arraycopy(m_objectTables, i+1, newTables, i, m_objectTables.length - i -1);
                m_objectTables = newTables;
            }
            else {
                i++;
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

    private void removeTupleTable(TupleTable tupleTable) {
        if(m_tupleTables == null) return;
        int i = 0;
        while( i < m_tupleTables.length) {
            if(m_tupleTables[i].equals(tupleTable)) {
                TupleTable[] newTables = new TupleTable[m_tupleTables.length-1];
                System.arraycopy(m_tupleTables, 0, newTables, 0, i);
                if(i < (m_tupleTables.length -1))
                    System.arraycopy(m_tupleTables, i+1, newTables, i, m_tupleTables.length - i -1);
                m_tupleTables = newTables;
            }
            else {
                i++;
            }
        }
    }

    public int getNumLinks() {
        return classLinks.length;
    }

    protected void addJoinTable(JoinTable table) {
        if(table instanceof ObjectTable) {
            Identifier[] idrs = table.getIdentifiers();
            if(idrs[0].getType().equals(m_class)) {
                addObjectTable((ObjectTable)table);
            }
        }
        else if(table instanceof TupleTable) {
            Identifier[] idrs = table.getIdentifiers();
            for(int i = 0; i < idrs.length; i++) {
                if(idrs[i].getType().equals(m_class)) {
                    addTupleTable((TupleTable)table);
                }
            }
        }
        else {
            throw new RuntimeException("Program Error:  unknown type of JoinTable");
        }
    }

    protected void removeJoinTable(JoinTable table) {
        if(table instanceof ObjectTable) {
            removeObjectTable((ObjectTable)table);
        }
        else if(table instanceof TupleTable) {
            removeTupleTable((TupleTable)table);
        }
        else {
            throw new RuntimeException("Program Error:  unknown type of JoinTable");
        }
    }

//    private Handle[] handles = new Handle[1];
//    private Object[] objects = new Object[1];

    public void assertObject(Handle objHandle) {
        Handle[] handles = new Handle[1];
        Object[] objects = new Object[1];
        handles[0] = objHandle;
        objects[0] = objHandle.getObject();
        if(objects[0] == null) return;
        assertObject_(handles, objects);
    }

    private void assertObject_(Handle[] objHandles, Object[] objects) {
        if(hasRule) {
            for(int i = classLinks.length-1; i >= 0 ; i--) {
                classLinks[i].propagateObjects(objHandles, objects);
            }
            if(m_super != null) {
                m_super.assertObject_(objHandles, objects);
            }
        }
    }

    protected void evaluateRules(Handle objHandle, int[] ruleIds) {
        Handle[] handles = new Handle[1];
        Object[] objects = new Object[1];

        handles[0] = objHandle;
        objects[0] = objHandle.getObject();
        if(objects[0] == null) return;
        evaluateRules_(handles, objects, ruleIds);
    }

    protected void modifyObject(Handle objHandle, int[] overrideDirtyBitMap) {
        Handle[] handles = new Handle[1];
        Object[] objects = new Object[1];

        handles[0] = objHandle;
        objects[0] = objHandle.getObject();
        if(objects[0] == null) return;
        modifyObject_(handles, objects, overrideDirtyBitMap);
    }

    protected void evaluateRules_(Handle[] objHandles, Object[] objects, int [] ruleIds) {
        if(hasRule) {
            evaluateRules_(objHandles, objects, ruleIds, true);
            evaluateRules_(objHandles, objects, ruleIds, false);
        }
    }
    
    protected void evaluateRules_(Handle[] objHandles, Object[] objects, int[] ruleIds, boolean remove) {
        ClassNodeLink [] linksToBeEvaluated = new ClassNodeLink[ruleIds.length];

        for (int j=0; j < ruleIds.length; j++) {
            linksToBeEvaluated[j] = ruleClassLinkMap.get(ruleIds[j]);
        }
        if (linksToBeEvaluated.length > 0) {
            int [] dirtyBits = ((Mutable) objects[0]).getDirtyBitArray();
            for (int i=0; i < linksToBeEvaluated.length; i++) {
                if (linksToBeEvaluated[i] != null) {
                    if(!linksToBeEvaluated[i].m_identifier.hasDependency((Mutable)objects[0], dirtyBits)) {
                        //System.err.println("Evaluating Rule " + linksToBeEvaluated[i].m_rule.getName() + "-" + linksToBeEvaluated[i].m_rule.getDescription() + ", obj = " + objects[0] + " dirtyBits =" + dirtyBits);
                        if(remove) {
                            linksToBeEvaluated[i].removeObject(objHandles);
                        } else {
                            linksToBeEvaluated[i].propagateObjects(objHandles, objects);
                        }
//                        } else {
////                            System.err.println("Skipping Rule " + linksToBeEvaluated[i].m_rule.getName() + ", obj = " + objects[0] + " dirtyBits =" + dirtyBits);
//                        }
                    }
                }
            }
        }
        
        if(m_super != null)
            m_super.evaluateRules_(objHandles, objects, ruleIds, remove);
    }

    private void modifyObject_(Handle[] objHandles, Object[] objects, int[] overrideDirtyBitMap) {
        if(hasRule) {
            modifyObject_(objHandles, objects, overrideDirtyBitMap, true);
            modifyObject_(objHandles, objects, overrideDirtyBitMap, false);
        }
    }
    
    private void modifyObject_(Handle[] objHandles, Object[] objects, int[] overrideDirtyBitMap, boolean remove) {
            if(objects[0] instanceof Mutable) {
                for(int i = 0; i < classLinks.length; i++) {
//                    System.err.println("Analyzing Rule Dependency" + classLinks[i].m_rule.getName() + "-" + classLinks[i].m_rule.getDescription() + ", obj = " + objects[0] + " identifier =" + classLinks[i].m_identifier.getClass());
                    if(classLinks[i].m_identifier.hasDependency((Mutable)objects[0], overrideDirtyBitMap)) {
//                        System.err.println("Evaluating Rule " + classLinks[i].m_rule.getName() + "-" + classLinks[i].m_rule.getDescription() + ", obj = " + objects[0] + " dirtyBits =" + overrideDirtyBitMap);
                        if(remove) {
                            classLinks[i].removeObject(objHandles);
                        } else {
                            classLinks[i].propagateObjects(objHandles, objects);
                        }
                    }
                }
            }
            else {
                for(int i = 0; i < classLinks.length; i++) {
                    if(remove) {
                        classLinks[i].removeObject(objHandles);
                    } else {
                        classLinks[i].propagateObjects(objHandles, objects);
                    }
                }
            }
        
            if(m_super != null)
                m_super.modifyObject_(objHandles, objects, overrideDirtyBitMap, remove);
    }

    protected void removeFromObjectTable(Handle objHandle) {
        if(hasRule) {
            for(int i = 0; i < m_objectTables.length; i++) {
                ObjectTable table = m_objectTables[i];
                table.remove(objHandle);
            }
            if(m_super != null)
                m_super.removeFromObjectTable(objHandle);
        }
    }

    public String toString() {
        String links = new String();
        for(int i = classLinks.length -1; i >= 0; i-- ) {
            NodeLink link = classLinks[i];
            links += "\t" + link.toString();
            if(i != classLinks.length-1) {
                links += Format.BRK;
            }
        }
        String table = "";
        for(int i = 0; i < m_tupleTables.length; i++) {
            table += m_tupleTables[i].getTableId();
            table += " ";
        }
        for(int i = 0; i < m_objectTables.length; i++) {
            table += m_objectTables[i].getTableId();
            if(i != (m_objectTables.length-1)) {
                table += " ";
            }
        }
        String strSuper = null;
        if( m_super != null) {
            strSuper = m_super.m_class.getName();
        }

        String ret = "[ClassNode Class(" + m_class.getName() + ") Table(" + table + ") Super(" + strSuper+ ")";
        if(links.length() > 0) {
            ret += Format.BRK + links + "]" + Format.BRK;
        }
        else {
            ret += "]" + Format.BRK;
        }
        return ret;
    }


    public Class getType() {
        return this.m_class;
    }

    public boolean hasRule() {
        return hasRule;
    }

    public void fastReset() {


        m_objectTables = new ObjectTable[0];
        m_tupleTables  = new TupleTable[0];


        classLinks = new ClassNodeLink[0];
        subNodes   = new ArrayList();
        hasRule = false;
    }
}
