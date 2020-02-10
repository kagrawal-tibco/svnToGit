package com.tibco.cep.kernel.core.base.tuple;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Sep 16, 2004
 * Time: 7:40:10 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class JoinTable {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final int MAXIMUM_CAPACITY = 1 << 30;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    protected final Identifier[] idrs;
    //never changed from the default so I commented it out -- Alan
    //protected final float        loadFactor;
    protected final short        id;


    public JoinTable(Identifier[] identifiers) throws SetupException {
        //loadFactor = DEFAULT_LOAD_FACTOR;
        idrs       = identifiers;
        id         = JoinTableCollectionProvider.getInstance().getJoinTableCollection().addTable(this);
    }
    
    public JoinTable(Identifier[] identifiers, short Id) {
        //loadFactor = DEFAULT_LOAD_FACTOR;
        idrs       = identifiers;
        id         = Id;
    }

    abstract public void lock();

    abstract public void unlock();

    synchronized public void recycleTable() {
        clearAllElements();
        JoinTableCollectionProvider.getInstance().getJoinTableCollection().removeTable(this);
    }

    public short getTableId() {
        return id;
    }
    
//    public float getLoadFactor() {
//        return loadFactor;
//    }

    public Identifier[] getIdentifiers() {
        return idrs;
    }

    public abstract int size();

    public abstract boolean isEmpty();

    public static int indexFor(int key, int length) {
        int h = key;
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h & (length-1);
    }

    abstract public void initTableImpl(WorkingMemoryImpl wmImpl); //init the ST, MT, CacheOnly or Mixed table
    
    abstract public void reset();  //this is for reset the working memory, just remove all the entry

    abstract protected void clearAllElements();  //this is for remove rule

    abstract public TableIterator iterator();

    abstract public TableIterator keyIterator(int key);

    abstract public String contentListForm();

    abstract public String contentHashForm();

//    static public JoinTable[] allTables  = new JoinTable[0];
//    static public LinkedList  recycleIds = new LinkedList();

//    synchronized static protected short addTable(JoinTable _table) throws SetupException {
//
//
//        if(recycleIds.size() > 0) {
//            Integer id = (Integer) recycleIds.removeFirst();
//            if(allTables[id.intValue()] !=null)
//                throw new SetupException("Table Id [" + id + "] already assigned");
//            allTables[id.intValue()] = _table;
//            return id.shortValue();
//        }
//        else {
//            int id = allTables.length;
//            if(id == Short.MAX_VALUE){
//                throw new SetupException(ResourceManager.getString("joinTable.rearchMaxId"));
//            }
//            JoinTable[] newTable = new JoinTable[id + 1];
//            System.arraycopy(allTables, 0, newTable, 0, id);
//            newTable[id] = _table;
//            allTables = newTable;
//            return (short) id;
//        }
//    }
//
//    synchronized static protected void removeTable(JoinTable _table) {
//        int tId = _table.id;
//        if(allTables[tId] != null) {
//            allTables[tId] = null;
//            recycleIds.add(new Integer(tId));
//        }
//    }

    static public JoinTable getTable(short id) {
        return JoinTableCollectionProvider.getInstance().getJoinTableCollection().getJoinTable(id);
       //return allTables[id];
    }

    static public String memoryDump(boolean hashForm) {
        String ret = "";
        JoinTable[] allTables = JoinTableCollectionProvider.getInstance().getJoinTableCollection().toArray();
        for(int i=0; i< allTables.length; i++) {
            JoinTable t = allTables[i];
            if(t == null || t.size() ==0) continue;
            if (hashForm) {
                ret += t.contentHashForm();
            }
            else {
                ret += t.contentListForm();
            }
        }
        return ret;
    }

    protected static EntitySharingLevel getRecursiveSharingLevel(WorkingMemoryImpl wmImpl, Class entityClz) {
        TypeInfo ti = wmImpl.getTypeInfo(entityClz);
        if(ti != null) {
            return ti.getRecursiveSharingLevel();
        } else {
            return EntitySharingLevel.DEFAULT;
        }
    }
}
