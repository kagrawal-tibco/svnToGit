/**
 * 
 */
package com.tibco.cep.diagramming.utils;

/**
 * @author aathalye
 *
 */
public enum ActivityTypes {
	
	ACTIVITY_RF(0),
	ACTIVITY_TABLE(1),
	ACTIVITY_EVENT(2),
	ACTIVITY_EVENT_SEND(3),
	ACTIVITY_EVENT_RECEIVE(4),
	ACTIVITY_JAVA(5),
	ACTIVITY_CQ(6),
	ACTIVITY_SQ(7),
	ACTIVITY_MANUAL(8),
	ACTIVITY_WS(9),
	ACTIVITY_RULE(10),
	ACTIVITY_ONMESSAGE_EVENT(11),
	ACTIVITY_TIMER_EVENT(12),
	ACTIVITY_NONE(13),
	ACTIVITY_SUBPROCESS(14),
	ACTIVITY_POOL(15),
	ACTIVITY_LANE(16);
	
	
	private Integer typeId;
	
	private ActivityTypes(int typeId) {
		this.typeId = typeId;
	}
	
	private static final ActivityTypes[] VALUES_ARRAY =
        new ActivityTypes[] {
		ACTIVITY_RF,
		ACTIVITY_TABLE,
		ACTIVITY_EVENT,
		ACTIVITY_EVENT_SEND,
		ACTIVITY_EVENT_RECEIVE,
		ACTIVITY_JAVA,
		ACTIVITY_CQ,
		ACTIVITY_SQ,
		ACTIVITY_MANUAL,
		ACTIVITY_WS,
		ACTIVITY_RULE,
		ACTIVITY_ONMESSAGE_EVENT,
		ACTIVITY_TIMER_EVENT,
		ACTIVITY_NONE,
		ACTIVITY_SUBPROCESS,
		ACTIVITY_POOL,
		ACTIVITY_LANE
        };

	public static ActivityTypes getById(Integer typeId) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActivityTypes result = VALUES_ARRAY[i];
			if (result.getTypeId().equals(typeId)) {
				return result;
			}
		}
		return null;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String toString() {
		return Integer.toString(typeId);
	}
}
