package com.tibco.rta.service.metric.directive;

import com.tibco.rta.Fact;
import com.tibco.rta.model.DimensionHierarchy;

public class BeTeaProcessingDirective implements MetricProcessingDirective {

    public static final BeTeaProcessingDirective INSTANCE = new BeTeaProcessingDirective();
    
    public static final int CREATE = 1;
    public static final int STOP =   3;
	public static final int START =  2;
	public static final int DELETE = 4;
    
    public BeTeaProcessingDirective() {}

	@Override
	public boolean visitParentNodes(DimensionHierarchy dh, Fact fact) {
		return false;
	}

	@Override
	public boolean deleteChildNodes(DimensionHierarchy dh, Fact fact) {
		
		int asset_status = -1;
		String asset_name  = (String) fact.getAttribute(Fact.ASSET_NAME);
		if (asset_name != null) {
			asset_status = (Integer) fact.getAttribute(Fact.ASSET_STATUS);
		}
		if (asset_status == DELETE) { //oob, treat asset_staus == 4 as deletes.
			return true;
		}
		else if(asset_status == STOP && ("instancehealthhierarchy".equals(dh.getName()) ||"rulehealthhierarchy".equals(dh.getName()) || "eventhealthhierarchy".equals(dh.getName()) || "inferencehealthhierarchy".equals(dh.getName()) ))
			return true;
		else
			return false;
	}

	@Override
	public boolean processNode(DimensionHierarchy dh, Fact fact) {
		return true;
	}

	@Override
	public boolean allowNullDimensionValues(DimensionHierarchy dh, Fact fact) {
		return false;
	}

	@Override
	public boolean shouldApplyAssetStatusToCurrentTimeWindowOnly(DimensionHierarchy dh) {
		return false;
	}
	
	@Override
	public boolean isDeleteFact(Fact fact) {
		int asset_status = -1;
		String asset_name  = (String) fact.getAttribute(Fact.ASSET_NAME);
		if (asset_name != null) {
			asset_status = (Integer) fact.getAttribute(Fact.ASSET_STATUS);
		}
		if (asset_status == 4) { //oob, treat asset_staus == 4 as deletes.
			return true;
		}
		return false;
	}
}