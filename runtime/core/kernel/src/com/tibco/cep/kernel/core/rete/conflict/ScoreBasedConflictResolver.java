package com.tibco.cep.kernel.core.rete.conflict;

import java.util.ArrayList;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.rete.ClassNode;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * User: nleong
 * Creation Date: Sep 14, 2009
 * Creation Time: 10:17:44 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public class ScoreBasedConflictResolver implements ConflictResolver {
    AgendaItem[] list;
    int     max;
    int     size;
    int     cursor;
    AgendaItemCache cache;

    public ScoreBasedConflictResolver() {
        list    = new AgendaItem[Rule.MAX_PRIORITY+1];
        size    = 0;
        max     = 0;
        cursor  = Rule.MAX_PRIORITY;
        cache   = new AgendaItemCache();
    }

    public int size() {
        return size;
    }

    public void reset() {
        max     = 0;
        cursor  = Rule.MAX_PRIORITY;
    }

    public AgendaItem[] agendaToArray() {
        ArrayList agenda = new ArrayList();
        for(int i = cursor; i <= max; i++) {
            AgendaItem e = list[i];
            while(e != null) {
                agenda.add(e);
                e = e.next;
            }
        }
        return (AgendaItem[]) agenda.toArray(new AgendaItem[0]);
    }

    public AgendaItem put(Rule rule, Handle[] handles) {
        AgendaItem agendaItem = cache.get(rule, handles);

        //null is returned if one of the objects in the objects array of the agenda item would have been null
        //due to getObject() returning null;
        if(agendaItem == null) return null;

        RuleFunction function = rule.getRank();
        if(function != null) {
            try {
                agendaItem.rank = ((Double)function.invoke(agendaItem.objects)).doubleValue();
            } catch (Exception ex) {
                agendaItem.rank = 0;

                StringBuffer buffer = new StringBuffer(ex.getMessage());
                buffer.append(" - Can't calculate the rank for ");
                agendaItem.printAgendaItem(buffer);
                buffer.append(", set rank to 0.");

                Logger logger = LogManagerFactory.getLogManager()
                    .getLogger(ScoreBasedConflictResolver.class);
                logger.log(Level.ERROR, buffer.toString(), ex);
            }
        }
        else
            agendaItem.rank = 0;
        //else - no calculation

        int priority = rule.getPriority();

        //put into the right place++
        AgendaItem curr = list[priority];
        AgendaItem prev = curr;
        boolean found = false;
        while(curr != null) {
            if(curr.rank <= agendaItem.rank) {
                //found
                if(curr == prev) {
                    list[priority] = agendaItem;
                    agendaItem.next = curr;
                }
                else {
                    prev.next = agendaItem;
                    agendaItem.next = curr;
                }
                found = true;
                break;
            }
            else {
                prev = curr;
                curr = curr.next;
            }
        }
        if(!found) {
            if(curr == prev) {
                agendaItem.next = list[priority];
                list[priority] = agendaItem;
            }
            else {
                agendaItem.next = curr;
                prev.next = agendaItem;
            }
        }

        //put into the right place--
        if(priority < cursor) {
            cursor = priority;
        }
        if(priority > max) {
            max = priority;
        }
        size++;

        return agendaItem;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public AgendaItem getNext() {
        if(list[cursor] != null) {
            AgendaItem ret = list[cursor];
            list[cursor] = ret.next;
            size--;
            ret.decrAgendaCount();
            return ret;
        }
        while(cursor < max) {
            cursor++;
            if(list[cursor] != null) {
                AgendaItem ret = list[cursor];
                list[cursor] = ret.next;
                size--;
                ret.decrAgendaCount();
                return ret;
            }
        }
        return null;
    }

    public void objectRemoved(Handle objHandle) {
        if(((BaseHandle)objHandle).agendaCount == 0) return;
        int curr = cursor;
        while(curr <= max ) {
            AgendaItem e    = list[curr];
            AgendaItem prev = e;
            while(e != null) {
                if(e.checkForRemove(objHandle, ((ClassNode)((BaseHandle)objHandle).getTypeInfo()).identifierIndexTable)) {
                    size--;
                    if(e == prev) {
                        list[curr] = e.next;
                        prev       = e.next;
                    }
                    else {
                        prev.next  = e.next;
                    }
                    AgendaItemRecycler.recycle(e);
                    e = e.next;

                    //check agendaCount
                    if(((BaseHandle)objHandle).agendaCount == 0) return;
                }
                else {
                    prev = e;
                    e = e.next;
                }
            }
            curr++;
        }
    }

    int[] virtualCount = new int[] {0};

    public void objectModified(Handle objHandle, int[] overrideDirtyBitMap) {
        if (((BaseHandle)objHandle).agendaCount == 0) return;
        virtualCount[0] = ((BaseHandle)objHandle).agendaCount;
        int curr = cursor;
        while(curr <= max ) {
            AgendaItem e    = list[curr];
            AgendaItem prev = e;
            while(e != null) {
                int checkForModify = e.checkForModify(objHandle, virtualCount, ((ClassNode)((BaseHandle)objHandle).getTypeInfo()).identifierIndexTable, overrideDirtyBitMap);
                if(checkForModify == AgendaItem.CHK_FOR_MODIFY_REMOVE_AGENDAITEM) {
                    size--;
                    if(e == prev) {
                        list[curr] = e.next;
                        prev       = e.next;
                    }
                    else {
                        prev.next  = e.next;
                    }

                    AgendaItemRecycler.recycle(e);
                    e = e.next;

                    //check agenda count
                    if(virtualCount[0] == 0) return;
                }
                else if (checkForModify == AgendaItem.CHK_FOR_MODIFY_FOUND) {
                    //check agenda count
                    if(virtualCount[0] == 0) return;

                    prev = e;
                    e = e.next;
                }
                else {  //AgendaItem.CHK_FOR_MODIFY_NOT_FOUND
                    prev = e;
                    e = e.next;
                }
            }
            curr++;
        }
    }

    public void printAgenda(StringBuffer buffer) {
        for(int i = cursor; i <= max; i++) {
            AgendaItem e = list[i];
            while(e != null) {
                buffer.append(" ");
                e.printAgendaItem(buffer);
                e = e.next;
            }
        }
    }

    String printQueue() {
        String ret = "";
        ret += "List :";
        for(int i = cursor; i <= max; i++) {
            AgendaItem e = list[i];
            while(e != null) {
                ret += e.objects[0] + " ";
                e = e.next;
            }
        }
        return ret;
    }
}

