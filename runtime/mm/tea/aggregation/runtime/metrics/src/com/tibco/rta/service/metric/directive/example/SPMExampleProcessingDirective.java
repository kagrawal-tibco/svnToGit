package com.tibco.rta.service.metric.directive.example;

import com.tibco.rta.Fact;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.service.metric.directive.MetricProcessingDirective;

public class SPMExampleProcessingDirective implements MetricProcessingDirective {


	public static final int STOP =   0;
	public static final int START =  1;
	public static final int CREATE = 3;
	public static final int DELETE = 4;

	
	
	/**
	 * Use CASES for AMX:
	 * 
	 * "ServiceInstance" and such hierarchies:
	 *  
	 * 	normal fact
	 * 		visitParents: no
	 *  	processNode: yes.
	 *  	deleteChildNodes: no
	 *  
	 *  asset fact:
	 *  	visit parents: no
	 *  	processNode: no
	 *  	deleteChildNodes: YES if DELETE
	 *  
	 * "InferredStatus"
	 *  normal fact
	 *  	visitParents: no
	 *  	processNode: no
	 *  	deleteChildNodes: no
	 *  asset fact
	 *  	visitParents: YES if DELETE
	 *  	processNode: 
	 *  		yes --> START/STOP NODES/SERVICE_INST
	 *  		no --> DELETE/CREATE
	 *  	deleteChildNodes: YES if DELETE
	 * 
	 * 
	 * 
	 */

	@Override
	public boolean visitParentNodes(DimensionHierarchy dh, Fact fact) {
		int asset_status = -1;
		String asset_name  = (String) fact.getAttribute(Fact.ASSET_NAME);
		if (asset_name != null) {
			asset_status = (Integer) fact.getAttribute(Fact.ASSET_STATUS);
		}
		if (asset_name == null) {
			// this means it is a normal fact.
			return false;
		}
		
		if (asset_status == CREATE) {
			return false;
		} else if (asset_status == START) {
			return false;
		} else if (asset_status == STOP) {
			if (dh.getName().equals("InferredStatus")) {
				return true;
			} else {
				return false;
			}
		} else if (asset_status == DELETE) {
			if (dh.getName().equals("InferredStatus")) {
				return true;
			} else {
				return false;
			}
			
		}
		return false;
	}

	@Override
	public boolean deleteChildNodes(DimensionHierarchy dh, Fact fact) {
		int asset_status = -1;
		String asset_name  = (String) fact.getAttribute(Fact.ASSET_NAME);
		if (asset_name != null) {
			asset_status = (Integer) fact.getAttribute(Fact.ASSET_STATUS);
		}
		if (asset_name == null) {
			// this means it is a normal fact.
			return false;
		}
		
		if (asset_status == CREATE) {
			return false;
		} else if (asset_status == START) {
			return false;
		} else if (asset_status == STOP) {
			return false;
		} else if (asset_status == DELETE) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean processNode(DimensionHierarchy dh, Fact fact) {
		int asset_status = -1;
		String asset_name  = (String) fact.getAttribute(Fact.ASSET_NAME);
		if (asset_name != null) {
			asset_status = (Integer) fact.getAttribute(Fact.ASSET_STATUS);
		}
		if (asset_name == null) {
			// this means it is a normal fact.
			return true;
		}
		
		if (asset_status == CREATE) {
			return false;
		} else if (asset_status == START) {
			if (dh.getName().equals("InferredStatus")) {
				return true;
			} else {
				return false;
			}
		} else if (asset_status == STOP) {
			if (dh.getName().equals("InferredStatus")) {
				return true;
			} else {
				return false;
			}
		} else if (asset_status == DELETE) {
			return false;
		}
		return false;		
	}

	@Override
	public boolean allowNullDimensionValues(DimensionHierarchy dh, Fact fact) {
		if (dh.getName().equals("InferredStatus")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldApplyAssetStatusToCurrentTimeWindowOnly(
            DimensionHierarchy dh) {
		if (dh.getName().equals("InferredStatus")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isDeleteFact(Fact fact) {
		int asset_status = -1;
		String asset_name  = (String) fact.getAttribute(Fact.ASSET_NAME);
		if (asset_name != null) {
			asset_status = (Integer) fact.getAttribute(Fact.ASSET_STATUS);
		}
		if (asset_status == DELETE) {
			return true;
		}
		return false;
	}
}
