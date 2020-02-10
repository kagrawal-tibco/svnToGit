package com.tibco.cep.kernel.core.rete.conflict;

import java.util.Iterator;
import java.util.LinkedList;

import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 4:16:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleConflictResolver implements ConflictResolver {

    LinkedList list;

    public SimpleConflictResolver() {
        list = new LinkedList();
    }

    public AgendaItem[] agendaToArray() {
        return (AgendaItem[]) list.toArray(new AgendaItem[0]);
    }


    public int size() {
        return list.size();
    }

    public void reset() {
        list.clear();
    }

    synchronized public AgendaItem put(Rule rule, Handle[] handles) {
        AgendaItem ai = AgendaItem.newAgendaItem(rule, handles);
        if(ai != null) {
            list.addLast(ai);
        }
        return ai;
    }

    synchronized public boolean isEmpty() {
        return list.size() == 0;
    }

    synchronized public AgendaItem getNext() {
        if(list.size() == 0) return null;
        AgendaItem item = (AgendaItem) list.removeFirst();
        item.decrAgendaCount();
        return item;
    }

    synchronized public void objectRemoved(Handle objHandle) {
        Iterator ite = list.iterator();
        while(ite.hasNext()) {
            AgendaItem agendaItem = (AgendaItem) ite.next();
            if(agendaItem.contains(objHandle)) {
                AgendaItemRecycler.recycle(agendaItem);
                ite.remove();
            }
        }
    }

    synchronized public void objectModified(Handle objHandle, int[] overrideDirtyBitMap) {
        Iterator ite = list.iterator();
        while(ite.hasNext()) {
            AgendaItem agendaItem = (AgendaItem) ite.next();
            if(agendaItem.contains(objHandle)) {
                AgendaItemRecycler.recycle(agendaItem);
                ite.remove();
            }
        }
    }

    public void printAgenda(StringBuffer buffer) {
        Iterator ite = list.iterator();
        while(ite.hasNext()) {
            AgendaItem item = (AgendaItem) ite.next();
            buffer.append(" ");
            item.printAgendaItem(buffer);
        }
    }
}
