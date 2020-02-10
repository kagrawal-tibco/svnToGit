package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.util.Map;

import COM.TIBCO.hawk.talon.CompositeData;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.TabularData;

public class HAWKMemoryMetricHandler extends HAWKMetricTypeHandler {

	private static final int ONE_KB = 1024;

	private static final int ONE_MB = 1048576;

	private static final DataElement[] IDX_KEY = new DataElement[]{new DataElement("Memory","Memory")};

	@Override
	protected void hawkInit() throws IOException {
		super.hawkInit();
		// by default we will get all the information using the below arguments
		String agentID = null;
		String methodName = null;
		DataElement[] args = null;

		switch (operatingSystem) {
			case WINDOWS:
				agentID = "COM.TIBCO.hawk.hma.Performance";
				methodName = "Memory";
				//the windows method needs time interval to be in seconds and not msecs
				args = new DataElement[] { new DataElement("TimeInterval", delay / 1000) };
				break;
			case LINUX:
				agentID = "COM.TIBCO.hawk.hma.System";
				methodName = "getSystemInfo";
				break;
            default:
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
			case LINUX:
				if (data instanceof CompositeData) {
					DataElement[] dataElements = ((CompositeData) data).getDataElements();
					parsedData.put("initSize", -1D);
					parsedData.put("usedSize", -1D);
					parsedData.put("committedSize", -1D);
					parsedData.put("maxSize", -1D);
					Double freeMemory = ((Number) getValue(dataElements, "Free Memory")).doubleValue();
					Double freeMemoryInMBytes = freeMemory / ONE_KB; //all values are reported in KB
					parsedData.put("availableSize", limit(freeMemoryInMBytes, 2));
				}
				break;
			case WINDOWS:
				if (data instanceof TabularData) {
					DataElement[] dataRow = ((TabularData) data).getRow(IDX_KEY);
					if (dataRow != null) {
						parsedData.put("initSize", -1D);
						parsedData.put("usedSize", -1D);
						parsedData.put("committedSize", -1D);
						parsedData.put("maxSize", -1D);
						Double availableBytes = (Double) getValue(dataRow, "Available Bytes");
						Double availableBytesInMB = availableBytes / ONE_MB; //all values are reported in B
						parsedData.put("availableSize", limit(availableBytesInMB, 2));
					}
				}
			default:
				break;
		}
	}

	public static void main(String[] args) {
		internalMain(args,HAWKMemoryMetricHandler.class,"Memory");
	}


}
