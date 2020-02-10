package com.tibco.cep.kernel.core.rete.conflict;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.model.entity.Mutable;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;
/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 2:53:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaItem implements ExecutionContext {
    public Rule rule;
    public Object[] objects;
    public Handle[] handles;
    AgendaItem next;
    boolean decrementedAgendaCount = false;
    double rank = 0;

    AgendaItem(int numHandles) {
        objects = new Object[numHandles];
        handles = new Handle[numHandles];
    }
    
    protected AgendaItem() {}

    //return false if error
    protected boolean constructorBody(Rule rul, Handle[] objHandles) {
        rule    = rul;
        objects = new Object[objHandles.length];
        handles = new Handle[objHandles.length];

        for(int i=0; i < objHandles.length; i++) {
            objects[i] = objHandles[i].getObject();
            if(objects[i] == null) return false;
            handles[i] = objHandles[i];
            ((BaseHandle)handles[i]).agendaCount++;
        }
        return true;
    }
    
    //returns null if handle.getObject returned null
    public static AgendaItem newAgendaItem(Rule rul, Handle[] objHandles) {
        AgendaItem ret = new AgendaItem();
        if(!ret.constructorBody(rul, objHandles)) return null;
        return ret;
    }

    void decrAgendaCount() {
        if(!decrementedAgendaCount) {
            decrementedAgendaCount = true;
            for(int k = 0; k < handles.length; k++) {
                ((BaseHandle)handles[k]).agendaCount--;
            }
        }
    }

    void recycle() {
        if(!decrementedAgendaCount) {
            for(int k = 0; k < handles.length; k++) {
                ((BaseHandle)handles[k]).agendaCount--;
            }
        }
    }

    public boolean contains(Handle objHandle) {
        for(int i = 0; i < handles.length; i++) {
            if(handles[i] == objHandle) {
                return true;
            }
        }
        return false;
    }

    boolean checkForRemove(Handle objHandle, int[][] classIndicesTable) {
        int[] indices = classIndicesTable[rule.getId()];
        if(indices != null) {
            for(int i = 0; i < indices.length; i++) {
                if(handles[indices[i]] == objHandle) {
                    return true;
                }
            }
        }
        return false;
    }

    private void decrVirtualCount(Handle objHandle, int[] virtualCount) {
        for(int i = 0; i < handles.length; i++) {
            if(handles[i] == objHandle) {
                virtualCount[0]--;
            }
        }
    }

    private void decrVirtualCount(Handle objHandle, int[] virtualCount, int[] indices) {
        for(int i = 0; i < indices.length; i++) {
            if(handles[indices[i]] == objHandle) {
                virtualCount[0]--;
            }
        }
    }


    static int CHK_FOR_MODIFY_REMOVE_AGENDAITEM = 0;  //found and have dependency, need to remove this agenda item
    static int CHK_FOR_MODIFY_FOUND      = 1;         //found but no dependency
    static int CHK_FOR_MODIFY_NOT_FOUND  = 2;         //not found anything

    int checkForModify(Handle objHandle, int[] virtualCount, int[][] classIndicesTable, int[] overrideDirtyBitMap) {
        int[] indices = null;
        if (classIndicesTable != null)
            indices = classIndicesTable[rule.getId()];
        if(indices != null) {
            Object modifiedObj = objHandle.getObject();
            if(modifiedObj != null && modifiedObj instanceof Mutable) {
                boolean needDecrVirtualCount = false;
                for(int i = 0; i < indices.length; i++) {
                    if(handles[indices[i]] == objHandle) {
                        if(rule.getIdentifiers()[indices[i]].hasDependency((Mutable)modifiedObj, overrideDirtyBitMap)) {
                            decrVirtualCount(objHandle, virtualCount, indices);
                            return CHK_FOR_MODIFY_REMOVE_AGENDAITEM;
                        }
                        else {
                            needDecrVirtualCount = true;
                        }
                    }
                }
                if(needDecrVirtualCount) {
                    decrVirtualCount(objHandle, virtualCount, indices);
                    return CHK_FOR_MODIFY_FOUND;
                }
            }
            else {
                for(int i = 0; i < indices.length; i++) {
                    if(handles[indices[i]] == objHandle) {
                        decrVirtualCount(objHandle, virtualCount);
                        return CHK_FOR_MODIFY_REMOVE_AGENDAITEM;
                    }
                }
            }
        }
        return CHK_FOR_MODIFY_NOT_FOUND;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("[AgendaItem: Rule="+ rule.getClass().getName() + " Objects=");
        for(int i = 0; i < objects.length; i++) {
            buf.append(objects[i]);
            if(i != objects.length -1) {
                buf.append(", ");
            }
        }
        buf.append("]");
        return buf.toString();
    }

    public void printAgendaItem(StringBuffer buf) {
        buf.append("[Rule="+ rule.getClass().getName() + " Objects=");
        for(int i = 0; i < objects.length; i++) {
            buf.append(objects[i]);
            if(i != objects.length -1) {
                buf.append(", ");
            }
        }
        buf.append("]");
    }

    public Object getCause() {
        return this.rule;
    }

    public Object getParams() {
        return this.objects;
    }

    public String[] info() {
        String value;
        String[] ret = new String[objects.length + 1];
        value = rule.getName();
        if(value.startsWith("be.gen"))
            value = value.substring("be.gen".length() + 1);

        ret[0] = "Rule=" + value;
        for(int i=1; i < ret.length; i++) {
            value = String.valueOf(objects[i-1]);
            ret[i] = value.startsWith("be.gen")? value.substring("be.gen".length() + 1): value;
        }
        return ret;
    }
}
