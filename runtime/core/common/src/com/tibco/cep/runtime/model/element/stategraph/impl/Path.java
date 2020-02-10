package com.tibco.cep.runtime.model.element.stategraph.impl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.model.element.stategraph.FinalStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StateVertex;

/**
 * @author ishaan
 * @version Dec 6, 2005, 8:54:42 PM
 */
public class Path {
    public LinkedHashMap /*<StateVertex, List<MutableStateVertex>>*/states;
    public BitSet signature;

    public Path(int numStates) {
        states = new LinkedHashMap(numStates);
        signature = new BitSet(numStates);
    }

    public Path(Path p) {
        states = (LinkedHashMap) p.states.clone();
        signature = (BitSet) p.signature.clone();
    }


    public boolean get(int index) {
        return signature.get(index);
    }

    public void setEnd(FinalStateVertex end) {
        states.put(end, null);
        signature.set(end.getIndex());
    }

    public void add(StateVertex prev, StateVertex state) {
        List l = (List) states.get(prev);
        if(l == null) {
            l = new ArrayList();
            states.put(prev, l);
        }

        l.add(state);
        signature.set(state.getIndex());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Set entries = states.entrySet();
        Iterator it = entries.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            StateVertex prev = (StateVertex) entry.getKey();
            sb.append(prev.getName());
            if(it.hasNext()) {
                sb.append("->");
            }
            // todo concurrent
        }

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Path)) {
            return false;
        }

        final Path path = (Path) o;

        return signature.equals(path.signature);
    }

    public int hashCode() {
        return signature.hashCode();
    }
}
