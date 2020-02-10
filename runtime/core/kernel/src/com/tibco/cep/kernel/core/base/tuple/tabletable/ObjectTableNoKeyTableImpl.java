package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: Oct 7, 2009
* Time: 6:20:50 PM
* To change this template use File | Settings | File Templates.
*/
public class ObjectTableNoKeyTableImpl extends ObjectTableTableImpl implements ObjectTableNoKeyTable
{
    public ObjectTableNoKeyTableImpl() {
        super();
    }

    public boolean add(Handle objHandle) {
        int i = JoinTable.indexFor(objHandle.hashCode(), table.length);
        for (ObjectEntry e = table[i]; e != null; e = e.next) {
            if (e.objHandle == objHandle) {
                return false;
            }
        }
        //synchronized(this) {
        table[i] = new ObjectEntry(objHandle, table[i]);
        if (numEntry++ >= threshold)
            resizeTable(2 * table.length);
        //}
        return true;
    }

    public boolean remove(Handle objHandle) {
        int i = JoinTable.indexFor(objHandle.hashCode(), table.length);
        ObjectEntry prev = table[i];
        ObjectEntry e = prev;

        while (e != null) {
            ObjectEntry next = e.next;
            if (e.objHandle == objHandle) {
                //synchronized(this) {
                numEntry--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                //}
                return true;
            }
            prev = e;
            e = next;
        }
        return false;
    }

    public Handle[] getAllHandles() {  //this function is called another thread, need to sync the object table when read
        Handle[] ret = new Handle[numEntry];
        int i = 0;
        //synchronized(this) {
            TableIterator ite = iterator();
            while(ite.hasNext()) {
                ret[i] = ((Handle[])ite.next())[0];
                i++;
            }
        //}
        return ret;
    }

    public String contentHashForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> ObjectTable id=" + container.getTableId() + " Identifiers= " + idrStr + Format.BRK;
        ret += "\tObjectTable length=" + table.length + " numEntry=" + numEntry;
        ret += " <<<" + Format.BRK;
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                ObjectEntry cursor = table[i];
                ret += "\tObjectTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle hashCode:" + cursor.objHandle.hashCode() + ", " + ((BaseHandle)cursor.objHandle).printInfo() + "] ";
                    cursor = cursor.next;
                }
                ret += Format.BRK;
            }
        }
        ret += Format.BRK;
        return ret;
    }

    public String contentListForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> ObjectTable id=" + container.getTableId() + " length=" + table.length +
                " numEntry=" + numEntry + " Identifiers= " + idrStr + " <<<" + Format.BRK;
        ret += "\t Objects :" + Format.BRK + "\t\t";
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                ObjectEntry cursor = table[i];
                while(cursor != null) {
                    ret += "[Handle hashCode:" + cursor.objHandle.hashCode() + ", " + ((BaseHandle)cursor.objHandle).printInfo() + "] ";
                    cursor = cursor.next;
                }
            }
        }            ret += Format.BRK;
        return ret;
    }
}