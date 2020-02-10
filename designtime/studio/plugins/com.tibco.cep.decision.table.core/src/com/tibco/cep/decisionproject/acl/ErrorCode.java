package com.tibco.cep.decisionproject.acl;

public class ErrorCode {
	public static final int DP_VRFIMPL_PROPERTY_MODIFY_DENY = 100;
	public static final int DP_VRFIMPL_ADD_DENY = 101;
	public static final int DP_DOMAIN_MODEL_DOMAIN_MODIFY_DENY = 102;
	public static final int DP_CONCEPT_MODIFY_DENY = 103;
	public static final int DP_CONCEPT_PROPERTY_MODIFY_DENY = 104;
	public static final int DP_EVENT_MODIFY_DENY = 105;
	public static final int DP_EVENT_PROPERTY_MODIFY_DENY = 106;
	public static final int DP_RULESET_MODIFY_DENY = 107;
	public static final int DP_RULE_MODIFY_DENY = 108;
	public static final int DP_RF_MODIFY_DENY = 109;	
	
	public static final int DP_MODIFY_DENY = 0x300;
	public static final int DP_READ_DENY = 0x302;
	
	 
	
	public static final int DP_VRFIMPL_PROPERTY_READ_DENY = 200;	
	public static final int DP_CONCEPT_READ_DENY = 201;
	public static final int DP_CONCEPT_PROPERTY_READ_DENY = 202;
	public static final int DP_EVENT_READ_DENY = 203;
	public static final int DP_EVENT_PROPERTY_READ_DENY = 204;
	public static final int DP_RULESET_READ_DENY = 205;
	public static final int DP_RULE_READ_DENY = 206;
	public static final int DP_RF_READ_DENY = 206;
	
	public static final int createErrorCode(String action) {
		if ("read".equals(action)) {
			return DP_READ_DENY;
		}
		if ("modify".equals(action)) {
			return DP_MODIFY_DENY;
		}
		return Integer.MIN_VALUE;
	}
}
