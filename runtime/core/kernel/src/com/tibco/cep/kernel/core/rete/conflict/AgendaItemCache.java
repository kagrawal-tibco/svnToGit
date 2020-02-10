package com.tibco.cep.kernel.core.rete.conflict;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 23, 2006
 * Time: 12:16:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class AgendaItemCache {
    final static int MAX_NUM_HANDLES = 10;
    final static int MAX_CACHE_SIZE = Integer.parseInt(System.getProperty("com.tibco.cep.kernel.reteAgenda.cacheSize", "100"));

    Cache[] caches;

    public AgendaItemCache() {
        caches = new Cache[MAX_NUM_HANDLES];
        caches[1] = new Cache1();
        caches[2] = new Cache2();
        caches[3] = new Cache3();
        //caches[4] = new Cache4();
        for(int i = 4; i < MAX_NUM_HANDLES; i++) {
            caches[i] = new Cache(i);
        }
    }

    public AgendaItem get(Rule rule, Handle[] handles) {
        if(handles.length < MAX_NUM_HANDLES) {
            return caches[handles.length].get(rule, handles);
        }
        else {
            return AgendaItem.newAgendaItem(rule, handles);
        }
    }

    static class Cache1 extends Cache {
        Cache1() {
            super(1);
        }

        AgendaItem get(Rule rule, Handle[] handles) {
            if(size == 0) {
                if(created < MAX_CACHE_SIZE) {
                    created++;
                    return Entry.newEntry(this, rule, handles);
                }
                else
                    return AgendaItem.newAgendaItem(rule, handles);
            }
            Entry ret = head.nextEntry;
            head.nextEntry = head.nextEntry.nextEntry;
            ret.rule    = rule;
            ret.objects[0] = handles[0].getObject();
            ret.handles[0] = handles[0];
            ((BaseHandle)handles[0]).agendaCount++;
            ret.nextEntry = null;
            size--;
            
            if(ret.objects[0] == null) {
                ret.recycle();
                return null;
            } else {
                return ret;
            }
        }

        void put(Entry e) {
            if(!e.decrementedAgendaCount)
                ((BaseHandle)e.handles[0]).agendaCount--;
            e.handles[0] = null;
            e.objects[0] = null;
            e.nextEntry = head.nextEntry;
            head.nextEntry = e;
            size++;
        }
    }

    static class Cache2 extends Cache {
        Cache2() {
            super(2);
        }

        AgendaItem get(Rule rule, Handle[] handles) {
            if(size == 0) {
                if(created < MAX_CACHE_SIZE) {
                    created++;
                    return Entry.newEntry(this, rule, handles);
                }
                else
                    return AgendaItem.newAgendaItem(rule, handles);
            }
            Entry ret = head.nextEntry;
            head.nextEntry = head.nextEntry.nextEntry;
            ret.rule    = rule;
            ret.objects[0] = handles[0].getObject();
            ret.handles[0] = handles[0];
            ((BaseHandle)handles[0]).agendaCount++;
            ret.objects[1] = handles[1].getObject();
            ret.handles[1] = handles[1];
            ((BaseHandle)handles[1]).agendaCount++;
            ret.nextEntry = null;
            size--;
            
            if(handles[0] == null || handles[1] == null) {
                ret.recycle();
                return null;
            }
            return ret;
        }

        void put(Entry e) {
            if(!e.decrementedAgendaCount) {
                ((BaseHandle)e.handles[0]).agendaCount--;
                ((BaseHandle)e.handles[1]).agendaCount--;
            }                        
            e.handles[0] = null;
            e.objects[0] = null;
            e.handles[1] = null;
            e.objects[1] = null;
            e.nextEntry = head.nextEntry;
            head.nextEntry = e;
            size++;
        }
    }

    static class Cache3 extends Cache {
        Cache3() {
            super(3);
        }

        AgendaItem get(Rule rule, Handle[] handles) {
            if(size == 0) {
                if(created < MAX_CACHE_SIZE) {
                    created++;
                    return Entry.newEntry(this, rule, handles);
                }
                else
                    return AgendaItem.newAgendaItem(rule, handles);
            }
            Entry ret = head.nextEntry;
            head.nextEntry = head.nextEntry.nextEntry;
            ret.rule    = rule;
            ret.objects[0] = handles[0].getObject();
            ret.handles[0] = handles[0];
            ((BaseHandle)handles[0]).agendaCount++;
            ret.objects[1] = handles[1].getObject();
            ret.handles[1] = handles[1];
            ((BaseHandle)handles[1]).agendaCount++;
            ret.objects[2] = handles[2].getObject();
            ret.handles[2] = handles[2];
            ((BaseHandle)handles[2]).agendaCount++;
            ret.nextEntry = null;
            size--;
            
            if(ret.objects[0] == null || ret.objects[1] == null || ret.objects[2] == null) {
                ret.recycle();
                return null;
            } else {
                return ret;
            }
        }

        void put(Entry e) {
            if(!e.decrementedAgendaCount) {
                ((BaseHandle)e.handles[0]).agendaCount--;
                ((BaseHandle)e.handles[1]).agendaCount--;
                ((BaseHandle)e.handles[2]).agendaCount--;
            }
            e.handles[0] = null;
            e.objects[0] = null;
            e.handles[1] = null;
            e.objects[1] = null;
            e.handles[2] = null;
            e.objects[2] = null;
            e.nextEntry = head.nextEntry;
            head.nextEntry = e;
            size++;
        }
    }
/*
    static class Cache4 extends Cache {
        Cache4() {
            super(4);
        }

        AgendaItem get(Rule rule, Handle[] handles) {
            if(size == 0) {
                if(created < MAX_CACHE_SIZE) {
                    created++;
                    return new Entry(this, rule, handles);
                }
                else
                    return new AgendaItem(rule, handles);
            }
            Entry ret = head.nextEntry;
            head.nextEntry = head.nextEntry.nextEntry;
            ret.rule    = rule;
            ret.objects[0] = handles[0].getObject();
            ret.handles[0] = handles[0];
            ((BaseHandle)handles[0]).agendaCount++;
            ret.objects[1] = handles[1].getObject();
            ret.handles[1] = handles[1];
            ((BaseHandle)handles[1]).agendaCount++;
            ret.objects[2] = handles[2].getObject();
            ret.handles[2] = handles[2];
            ((BaseHandle)handles[2]).agendaCount++;
            ret.objects[3] = handles[3].getObject();
            ret.handles[3] = handles[3];
            ((BaseHandle)handles[3]).agendaCount++;
            ret.nextEntry = null;
            size--;
            return ret;
        }

        void put(Entry e) {
            ((BaseHandle)e.handles[0]).agendaCount--;
            e.handles[0] = null;
            e.objects[0] = null;
            ((BaseHandle)e.handles[1]).agendaCount--;
            e.handles[1] = null;
            e.objects[1] = null;
            ((BaseHandle)e.handles[2]).agendaCount--;
            e.handles[2] = null;
            e.objects[2] = null;
            ((BaseHandle)e.handles[3]).agendaCount--;
            e.handles[3] = null;
            e.objects[3] = null;
            e.nextEntry = head.nextEntry;
            head.nextEntry = e;
            size++;
        }
    }        */

    static class Cache {
        Entry head;
        int   handleNum;
        int   size;
        int   created;

        Cache(int numHandle) {
            head = Entry.newEntry(this, null, new Handle[]{});
            size = 0;
            handleNum = numHandle;
            created = 0;
        }

        AgendaItem get(Rule rule, Handle[] handles) {
            if(size == 0) {
                if(created < MAX_CACHE_SIZE) {
                    created++;
                    return Entry.newEntry(this, rule, handles);
                }
                else
                    return AgendaItem.newAgendaItem(rule, handles);
            }
            Entry ret = head.nextEntry;
            head.nextEntry = head.nextEntry.nextEntry;
            ret.rule    = rule;
            boolean returnNull = false;
            for(int i=0; i < handleNum; i++) {
                ret.objects[i] = handles[i].getObject();
                ret.handles[i] = handles[i];
                ((BaseHandle)handles[i]).agendaCount++;
                if(ret.objects[i] == null) {
                    returnNull = true;
                }
            }
            ret.nextEntry = null;
            size--;
            
            if(returnNull) {
                ret.recycle();
                return null;
            } else {
                return ret;
            }
        }

        void put(Entry e) {
            if(e.decrementedAgendaCount) {
                for(int k = 0; k < e.handles.length; k++) {
                    e.handles[k] = null;
                    e.objects[k] = null;
                }
            }
            else {
                for(int k = 0; k < e.handles.length; k++) {
                    ((BaseHandle)e.handles[k]).agendaCount--;
                    e.handles[k] = null;
                    e.objects[k] = null;
                }
            }
            e.nextEntry = head.nextEntry;
            head.nextEntry = e;
            size++;
        }
    }

    public static class Entry extends AgendaItem {
        Entry nextEntry;
        Cache cache;
        
        protected Entry(){}
        
        static Entry newEntry(Cache c, Rule rule, Handle[] handles) {
            Entry ret = new Entry();
            if(!ret.constructorBody(c, rule, handles)) return null;
            return ret;
        }
        
        boolean constructorBody(Cache c, Rule rule, Handle[] handles) {
            if(!super.constructorBody(rule, handles)) return false;
            cache = c;
            return true;
        }

        public void recycle() {
            cache.put(this);
        }
    }
}
