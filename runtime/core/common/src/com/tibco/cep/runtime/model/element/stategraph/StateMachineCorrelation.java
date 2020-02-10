package com.tibco.cep.runtime.model.element.stategraph;

import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.event.TimeEvent;

/**
 * @author ishaan
 * @version Dec 14, 2005, 11:42:55 AM
 */
public interface StateMachineCorrelation extends Property {
    public TransitionCorrelation[] getCorrelations(int transitionID);
    public void addCorrelation(int transitionID, Object[] objects);
    public boolean isCorrelated(int transitionID);
    public void setCorrelated(int transitionID, boolean correlated);
    public int getCorrelationCount(int transitionID);
    public TransitionCorrelation getCorrelation(int transitionID, int correlationID);
    public long[] getConceptIDs(int transitionID, int correlationID);
    public long[] getEventIDs(int transitionID, int correlationID);
    public TimeEvent[] getTimeEvents(int transitionID, int correlationID);

    public interface TransitionCorrelation {
        public long getTime();
        public long[] getConceptIDs();
        public long[] getEventIDs();
        public TimeEvent[] getTimeEvents();
    }
}
