package com.tibco.cep.designtime.model.rule;


import java.util.List;

import com.tibco.cep.designtime.model.Entity;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 1:05:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleSet extends Entity {


    /**
     * Returns an Collection of all Rule objects in this RuleSet.
     *
     * @return A Collection of Rules.
     */
    List getRules();


    /**
     * Returns a Rule by name.
     *
     * @param name The name to search for.
     * @return A RulesetEntry, if it's found, or null otherwise.
     */
    RulesetEntry getRule(String name);
}
