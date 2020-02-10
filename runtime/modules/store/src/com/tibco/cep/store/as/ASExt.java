/**
 * 
 */
package com.tibco.cep.store.as;

import java.util.Arrays;
import java.util.Properties;

import com.tibco.cep.store.StoreExt;
import com.tibco.datagrid.DataGrid;
import com.tibco.datagrid.LogHandler;

/**
 * @author vpatil
 *
 */
public class ASExt extends StoreExt {
	private static final int MIN_LOG_FILE_SIZE_BYTES = 102400;

	private Properties properties;

	@Override
	public String getVersion() {
		return DataGrid.getVersionInformation();
	}

	@Override
	public void setLogLevel(String logLevel) throws Exception {
		DataGridLogLevel dgLogLevel = DataGridLogLevel.valueOf(logLevel);
		if (dgLogLevel == null) throw new IllegalArgumentException(String.format("Invalid log level specified. Valid Log levels are %s", DataGridLogLevel.getLogLevelNames()));
		DataGrid.setLogLevel(dgLogLevel.getValue());
	}

	@Override
	public void setLogFiles(String filePrefix, long maxFileSize, int maxFiles) throws Exception {
		if (maxFileSize < MIN_LOG_FILE_SIZE_BYTES) throw new IllegalArgumentException("Low log file size specified. Value expected should be > " + MIN_LOG_FILE_SIZE_BYTES + "byes (100kb)");
		DataGrid.setLogFiles(filePrefix, maxFileSize, maxFiles, properties);
	}

	@Override
	public void setLogHandler(Object logHandler) throws Exception {
		if (!(logHandler instanceof LogHandler)) throw new IllegalArgumentException("Invalid log handler type. Expect type should be an instance of LogHandler.");
		DataGrid.setLogHandler((LogHandler)logHandler, properties);
	}
	
	enum DataGridLogLevel {
		DEBUG("DEBUG", DataGrid.TIB_LOG_LEVEL_DEBUG),
		INFO("INFO", DataGrid.TIB_LOG_LEVEL_INFO),
		OFF("OFF", DataGrid.TIB_LOG_LEVEL_OFF),
		SEVERE("SEVERE", DataGrid.TIB_LOG_LEVEL_SEVERE),
		VERBOSE("VERBOSE", DataGrid.TIB_LOG_LEVEL_VERBOSE),
		WARN("WARN", DataGrid.TIB_LOG_LEVEL_WARN);
		
		private String key;
		private String value;
		
		private DataGridLogLevel(String key, String value) {
			this.key = key;
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
		
		public String getKey() {
			return this.key;
		}
		
		public static String getLogLevelNames() {
			String[] logLevelNames = new String[DataGridLogLevel.values().length]; int i=0;
			for (DataGridLogLevel dgLogLevel : DataGridLogLevel.values()) {
				logLevelNames[i++] = dgLogLevel.getKey();
			}
			return Arrays.toString(logLevelNames);
		}
	}
}
