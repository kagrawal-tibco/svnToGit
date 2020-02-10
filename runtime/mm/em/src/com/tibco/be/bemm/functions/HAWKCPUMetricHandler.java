package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.util.Map;

import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.TabularData;

public class HAWKCPUMetricHandler extends HAWKMetricTypeHandler {

	private static final DataElement[] IDX_KEY = new DataElement[] { new DataElement("Processor", "_Total") };

	@Override
	protected void hawkInit() throws IOException {
		super.hawkInit();
		// by default we will get all the information using the below arguments
		String agentID = "COM.TIBCO.hawk.hma.System";
		String methodName = "getCpuInfo";
		DataElement[] args = null;

		switch (operatingSystem) {
			case WINDOWS:
				agentID = "COM.TIBCO.hawk.hma.Performance";
				methodName = "Processor";
				//the windows method needs time interval to be in seconds and not msecs
				args = new DataElement[] { new DataElement("Processor", ""), new DataElement("TimeInterval", delay / 1000) };
				break;
            case HPUX:
            case LINUX:
            case SOLARIS:
            case AIX:
                break;
			case UNKNOWN:
			default:
				agentID = null;
				methodName = null;
				args = null;
				break;
		}
		if (agentID != null) {
			microAgentID = findMicroAgentId(agentID);
			if (microAgentID != null){
				methodInvoker = new HAWKMethodInvoker(logger, agentManager, microAgentID, methodName, args, delay);
			}
		}
	}

	protected void parseData(Object data, Map<String, Object> parsedData) {
		if (data instanceof TabularData) {
			TabularData tabularData = (TabularData)data;
			DataElement[][] allDataElements = tabularData.getAllDataElements();
			int rowCnt = allDataElements.length;
			switch (operatingSystem) {
				case AIX:
					parsedData.put("cpucnt", rowCnt);
					parsedData.put("cputime", -1L);
					parsedData.put("uptime", -1L);
					parsedData.put("usage", getValue(allDataElements[0], "% User Time"));
					break;
				case HPUX:
				case LINUX:
				case SOLARIS:
					parsedData.put("cpucnt", rowCnt);
					parsedData.put("cputime", -1L);
					parsedData.put("uptime", -1L);
					Double userTime = 0.0;
					for (int i = 0; i < allDataElements.length; i++) {
						Number value = (Number) getValue(allDataElements[i], "% User Time");
						userTime = userTime + value.doubleValue();
					}
					userTime = userTime / rowCnt;
					parsedData.put("usage", limit(userTime, 2));
					break;
				case WINDOWS:
					DataElement[] dataRow = tabularData.getRow(IDX_KEY);
					if (dataRow != null) {
						parsedData.put("cpucnt", rowCnt - 1);
						parsedData.put("cputime", -1L);
						parsedData.put("uptime", -1L);
						Double cpuUsageInPercent = (Double) getValue(dataRow, "% User Time");
						parsedData.put("usage", limit(cpuUsageInPercent, 2));
					}
				default:
					break;
			}
		}
	}

	public static void main(String[] args) {
		internalMain(args,HAWKCPUMetricHandler.class,"CPU");
	}
}
