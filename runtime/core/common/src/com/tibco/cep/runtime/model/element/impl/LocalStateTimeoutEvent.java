package com.tibco.cep.runtime.model.element.impl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 14, 2010
 * Time: 6:39:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocalStateTimeoutEvent extends AbstractStateTimeoutEvent
{
	public final static int LOCALSTATETIMEOUTEVENT_TYPEID = 1004;
	
    public LocalStateTimeoutEvent(long _id) { super(_id); }
    public LocalStateTimeoutEvent(long _id, String _extId) { super(_id, _extId); }
	
    public LocalStateTimeoutEvent(long _id, long sm_id, String property_name, long next, int count) {
        super(_id, sm_id, property_name, next, count);
    }
}
