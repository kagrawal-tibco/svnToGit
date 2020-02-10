package com.tibco.cep.runtime.service.tester.model;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 12:29:04 PM
 *
 * The operation performed on the object.
 * <p>
 * An object is asserted, modified, or retracted from Working memory.
 * </p>
 */
public enum ReteChangeType {

    ASSERT,
	MODIFY,
	RETRACT,
    SCHEDULEDTIMEEVENT,
    RULEEXECUTION,
	RULEFIRED;
}
