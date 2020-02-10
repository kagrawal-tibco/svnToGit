package com.tibco.cep.dashboard.psvr.alerts;

import com.tibco.cep.dashboard.psvr.mal.model.MALVisualAlertAction;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

public final class VisualAlertActionable implements AlertActionable {
	
	private MALVisualAlertAction visualAlertAction;
	
	public VisualAlertActionable(MALVisualAlertAction visualAlertAction) {
		this.visualAlertAction = visualAlertAction;
	}

	@Override
	public AlertResult execute(PresentationContext ctx) throws ExecException {
		VisualAlertResult result = new VisualAlertResult();
		if (visualAlertAction.getEnabled() == true){
			result.setValue(VisualAlertResult.COLOR_FORMAT_KEY,visualAlertAction.getFillColor());
			result.setValue(VisualAlertResult.FONTCOLOR_FORMAT_KEY,visualAlertAction.getFontColor());
			result.setValue(VisualAlertResult.FONTSTYLE_FORMAT_KEY,visualAlertAction.getFontStyle().toString());
			result.setValue(VisualAlertResult.TOOLTIP_FORMAT_KEY,visualAlertAction.getTooltipFormat());
			result.setValue(VisualAlertResult.TRANSFORMATION_FORMAT_KEY,visualAlertAction.getDisplayFormat());
		}
		return result;
	}

}
