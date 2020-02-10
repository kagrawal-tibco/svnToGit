package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.util.Map;

import COM.TIBCO.hawk.talon.CompositeData;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.TabularData;

public class HAWKSwapMetricHandler extends HAWKMetricTypeHandler {

	private static final DataElement[] IDX_KEY = new DataElement[]{new DataElement("Paging File","_Total")};

	@Override
	protected void hawkInit() throws IOException {
		super.hawkInit();
		// by default we will get all the information using the below arguments
		String agentID = "COM.TIBCO.hawk.hma.System";
		String methodName = "getSwapInfo";
		DataElement[] args = null;

        switch (operatingSystem) {
            case WINDOWS:
                agentID = "COM.TIBCO.hawk.hma.Performance";
                methodName = "Paging File";
                //the windows method needs time intervale to be in seconds and not msecs
                args = new DataElement[]{new DataElement("Paging File", ""), new DataElement("TimeInterval", delay / 1000)};
                break;
            case HPUX:
            case LINUX:
            case SOLARIS:
                break;
            case AIX:
            case UNKNOWN:
            default:
                agentID = null;
                methodName = null;
                break;
        }
		if (agentID != null) {
			microAgentID = findMicroAgentId(agentID);
			if (microAgentID != null){
				methodInvoker = new HAWKMethodInvoker(logger, agentManager, microAgentID, methodName, args, delay);
			}
		}
	}

	@Override
	protected void parseData(Object data, Map<String, Object> parsedData) {
		switch (operatingSystem) {
			case HPUX:
			case LINUX:
			case SOLARIS:
				if (data instanceof CompositeData) {
					DataElement[] dataElements = ((CompositeData) data).getDataElements();
					parsedData.put("available", limit((Double)getValue(dataElements, "% Free"), 2));
					parsedData.put("used", limit((Double)getValue(dataElements, "% Used"), 2));
				}
				break;
			case WINDOWS:
				if (data instanceof TabularData) {
					DataElement[] dataRow = ((TabularData) data).getRow(IDX_KEY);
					if (dataRow != null) {
						Double usagePercent = (Double) getValue(dataRow, "% Usage");
						parsedData.put("available", limit(100-usagePercent, 2));
						parsedData.put("used", limit(usagePercent, 2));
					}
				}
				break;
			default:
				break;
		}
	}

	public static void main(String[] args) {
		internalMain(args,HAWKSwapMetricHandler.class,"Swap");
	}


}