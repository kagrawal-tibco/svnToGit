package com.tibco.cep.designtime.model.element.stategraph;


import java.util.List;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 2:33:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateComposite extends State {


    /**
     * Return a List of all the entities contained in this composite state.
     *
     * @return A List of all the entities contained in this composite state.
     */
    List getEntities();


    /**
     * Get a region (set of concurrent sub-states) by index.  Each element of the List returned will
     * be a StateComposite.
     *
     * @return A List of regions (where each element of the List is a StateComposite).
     */
    StateComposite getRegion(
            int index) throws ModelException;


    /**
     * Get the list of regions (concurrent sub-states).  Each element of the List returned will
     * be a StateComposite.
     *
     * @return A List of regions (where each element of the List is a StateComposite).
     */
    List getRegions();


    /**
     * Get the timeout (in milliseconds) for this concurrent set of states.
     *
     * @return The new number of milliseconds before this concurrent set of states
     *         will automatically exit.
     */
    int getTimeout();


    /**
     * Return true if this composite state is a concurrent state, otherwise return false.
     *
     * @return true if this composite state is a concurrent state, otherwise return false.
     */
    boolean isConcurrentState();


    /**
     * Is this Composite State a Region?
     *
     * @return true if this Composite State a Region, otherwise false.
     */
    boolean isRegion();


    StateVertex getState(String name);
    
    /**
     * returs the timeout action code block offsets
     * @since 4.0
     * @return
     */
    CodeBlock getTimeoutCodeBlock();
}
