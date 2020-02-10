package com.tibco.cep.runtime.model.element.stategraph.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineCorrelation;
import com.tibco.cep.runtime.model.event.TimeEvent;

/**
 * @author ishaan
 * @version Dec 14, 2005, 11:51:14 AM
 */
public abstract class StateMachineCorrelationImpl extends PropertyImpl implements StateMachineCorrelation {
    private Concept m_concept;
    private TransitionCorrelation[][] m_correlationLists;
    private boolean[] m_correlationStatuses;

    public StateMachineCorrelationImpl(Concept owner, int numTransitions) {
        super();
        m_concept = owner;
        m_correlationLists = new TransitionCorrelationImpl[numTransitions][];
        m_correlationStatuses = new boolean[numTransitions];
        Arrays.fill(m_correlationStatuses, false);
    }

    // for restore only
    public StateMachineCorrelationImpl(Concept owner) {
        super();
        m_concept = owner;
    }

    public TransitionCorrelation[] getCorrelations(int transitionID) {
        return m_correlationLists[transitionID];
    }

    public void addCorrelation(int transitionID, Object[] objects) {
        List ids = new ArrayList(1);
        List timeEvents = new ArrayList(1);
        sortObjects(objects, ids, timeEvents);

        if(m_correlationLists[transitionID] == null) {
            m_correlationLists[transitionID] = new TransitionCorrelationImpl[1];
            m_correlationLists[transitionID][0] = new TransitionCorrelationImpl(objects, m_concept);
        }

        else {
            TransitionCorrelationImpl[] newArr;
            TransitionCorrelationImpl corr = new TransitionCorrelationImpl(objects, m_concept);
            newArr = new TransitionCorrelationImpl[m_correlationLists[transitionID].length + 1];
            System.arraycopy(m_correlationLists[transitionID], 0, newArr, 0, m_correlationLists[transitionID].length);
            newArr[m_correlationLists[transitionID].length] = corr;
            m_correlationLists[transitionID] = newArr;
        }

        m_correlationStatuses[transitionID] = true;
        setConceptModified();
    }

    private void sortObjects(Object[] objects, List ids, List timeEvents) {
        for(int i = 0; i < objects.length; i++) {
            if(m_concept.equals(objects[i])) {
                continue; // don't count owner
            }

            if(objects[i] instanceof TimeEvent) {
                timeEvents.add(objects[i]);
            }
            else if(objects[i] instanceof Entity) {
                Entity entity = (Entity) objects[i];
                ids.add(new Long(entity.getId()));
            }
        }
    }

    public boolean isCorrelated(int transitionID) {
        return m_correlationStatuses[transitionID];
    }

    public void setCorrelated(int transitionID, boolean correlated) {
        m_correlationStatuses[transitionID] = correlated;
        setConceptModified();
    }

    public int getCorrelationCount(int transitionID) {
        return (m_correlationLists[transitionID] != null) ? m_correlationLists[transitionID].length : 0;
    }

    public TransitionCorrelation getCorrelation(int transitionID, int correlationID) {
        return m_correlationLists[transitionID][correlationID];
    }

    public long[] getConceptIDs(int transitionID, int correlationID) {
        return m_correlationLists[transitionID][correlationID].getConceptIDs();
    }

    public long[] getEventIDs(int transitionID, int correlationID) {
        return m_correlationLists[transitionID][correlationID].getEventIDs();
    }

    public TimeEvent[] getTimeEvents(int transitionID, int correlationID) {
        return m_correlationLists[transitionID][correlationID].getTimeEvents();
    }

    public int getHistoryPolicy() {
        return 0; // doesn't matter
    }

    public int getHistorySize() {
        return -1; // doesn't matter
    }

    public Concept getParent() {
        return m_concept;
    }

    public void write(DataOutputStream os) throws IOException {
        // number of transitions
        os.writeInt(m_correlationStatuses.length);
        for(int i = 0; i < m_correlationStatuses.length; i++) {
            // correlation status
            os.writeBoolean(m_correlationStatuses[i]);

            // no correlations
            if(m_correlationLists[i] == null) {
                os.writeInt(0);
                continue;
            }

            // num correlations
            os.writeInt(m_correlationLists[i].length);
            for(int j = 0; j < m_correlationLists[i].length; j++) {
                // correlation time
                os.writeLong(m_correlationLists[i][j].getTime());
                // Concept ids
                writeIDArray(os, m_correlationLists[i][j].getConceptIDs());
                // Events ids
                writeIDArray(os, m_correlationLists[i][j].getEventIDs());
                // TimeEvents
                writeTimeEvents(os, m_correlationLists[i][j].getTimeEvents());
            }
        }
    }


    // todo write other TimeEvent stuff
    private void writeTimeEvents(DataOutputStream os, TimeEvent[] events) throws IOException {
        // num TimeEvents
        os.writeInt(events.length);
        for(int i = 0; i < events.length; i++) {
            // TimeEvent class name
            os.writeUTF(events[i].getClass().getName());
//nick(delete)            TimeEventImpl event = (TimeEventImpl) events[i];
            // TimeEvent
//nick(delete)            event.write(os);
            // TimeEvent scheduled time
            os.writeLong(events[i].getScheduledTimeMillis());
            // TimeEvent closure
            os.writeUTF(events[i].getClosure());
        }
    }

    private void writeIDArray(DataOutputStream os, long[] ids) throws IOException {
        // num object ids
        os.writeInt(ids.length);
        for(int i = 0; i < ids.length; i++) {
            // object id
            os.writeLong(ids[i]);
        }
    }

    public void read(DataInputStream is) throws IOException {
        int numTransitions = is.readInt();
        m_correlationStatuses = new boolean[numTransitions];
        m_correlationLists = new TransitionCorrelationImpl[numTransitions][];
        for(int i = 0; i < numTransitions; i++) {
            // correlation status
            m_correlationStatuses[i] = is.readBoolean();

            // num correlations
            int numCorrelations = is.readInt();
            if(numCorrelations == 0) {
                continue; // leave transition correlation bucket as null
            }

            m_correlationLists[i] = new TransitionCorrelationImpl[numCorrelations];
            for(int j = 0; j < numCorrelations; j++) {
                // correlation time
                long time = is.readLong();
                // concept ids
                long[] conceptIDs = readIDs(is);
                // event ids
                long[] eventIDs = readIDs(is);
                // TimeEvents
                TimeEvent[] timeEvents = readTimeEvents(is);
                m_correlationLists[i][j] = new TransitionCorrelationImpl(conceptIDs, eventIDs, timeEvents, time);
            }
        }
    }

    // todo read other TimeEvent stuff
    private TimeEvent[] readTimeEvents(DataInputStream is) throws IOException {
//nick(delete)
        /*
        BEClassLoader classLoader = (BEClassLoader) EngineContext.getInstance().getClassLoader();

        // num TimeEvents
        int numTimeEvents = is.readInt();
        TimeEvent[] timeEvents = new TimeEvent[numTimeEvents];
        for(int i = 0; i < timeEvents.length; i++) {
            try {
                // read class name
                String className = is.readUTF();
                // read scheduled time
                long scheduledTime = is.readLong();
                // read closure
                String closure = is.readUTF();
                // instantiate the TimeEvent
                Class timeEventClass = classLoader.loadClass(className);
                Constructor cons = timeEventClass.getConstructor(new Class[] {java.io.DataInputStream.class});
                timeEvents[i] = (TimeEvent) cons.newInstance(new Object[] {is});
                timeEvents[i].setScheduledTime(scheduledTime);
                timeEvents[i].setClosure(closure);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return timeEvents;
        */
        return null;
    }

    private long[] readIDs(DataInputStream is) throws IOException {
        int numObjects = is.readInt();
        long[] objectIDs = new long[numObjects];
        for(int k = 0; k < numObjects; k++) {
            // object id
            objectIDs[k] = is.readLong();
        }

        return objectIDs;
    }

    private static class TransitionCorrelationImpl implements TransitionCorrelation {
        long[] conceptIDs;
        long[] eventIDs;
        TimeEvent[] timeEvents;
        long time;

        TransitionCorrelationImpl(Object[] objects, Concept owner) {
            sortObjects(objects, owner);
            this.time = System.currentTimeMillis();
        }

        public long[] getConceptIDs() {
            return conceptIDs;
        }

        public long[] getEventIDs() {
            return eventIDs;
        }

        public TimeEvent[] getTimeEvents() {
            return timeEvents;
        }

        public long getTime() {
            return time;
        }

        void sortObjects(Object[] objects, Concept owner) {
            List concepts = new ArrayList(1);
            List events = new ArrayList(1);
            List timeEvents = new ArrayList(1);
            for(int i = 0; i < objects.length; i++) {
                if(owner.equals(objects[i])) {
                    continue;
                }
                else if(objects[i] instanceof Concept) {
                    concepts.add(objects[i]);
                }
                else if(objects[i] instanceof TimeEvent) {
                    timeEvents.add(objects[i]);
                }
                else if(objects[i] instanceof Event) {
                    events.add(objects[i]);
                }
            }

            conceptIDs = new long[concepts.size()];
            Iterator it = concepts.iterator();
            for(int i = 0; it.hasNext(); i++) {
                Concept concept = (Concept) it.next();
                conceptIDs[i] = concept.getId();
            }

            eventIDs = new long[events.size()];
            it = events.iterator();
            for(int i = 0; it.hasNext(); i++) {
                Event event = (Event) it.next();
                eventIDs[i] = event.getId();
            }

            this.timeEvents = (TimeEvent[]) timeEvents.toArray(new TimeEvent[0]);
        }

        // for restore only
        TransitionCorrelationImpl(long[] conceptIDs, long[] eventIDs, TimeEvent[] timeEvents, long time) {
            this.conceptIDs = conceptIDs;
            this.eventIDs = eventIDs;
            this.timeEvents = timeEvents;
            this.time = time;
        }
    }
}