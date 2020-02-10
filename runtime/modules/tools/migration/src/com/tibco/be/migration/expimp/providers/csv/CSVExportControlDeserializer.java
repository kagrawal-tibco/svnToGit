package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.CSVReader;
import com.tibco.be.migration.CSVReaderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 3:41:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVExportControlDeserializer {
    public static String EXPORT_CONTROL_FILE_NAME = "export-control";
    private String inputUrl;

    public CSVExportControlDeserializer(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public ExportControlData deserialize() throws Exception {
        CSVReader reader = new CSVReaderImpl(this.inputUrl, EXPORT_CONTROL_FILE_NAME);
        for( String[] o = reader.nextRow(); o != null ; o = reader.nextRow() ) {            
            ExportControlData ecd = new ExportControlData();
            if (null != o[0]) {
                ecd.setProjectName(o[0]);
            }
            if (null != o[1]) {
                ecd.setProjectConfigVersion(o[1]);
            }
            if (null != o[2]) {
                ecd.setDataVersion(o[2]);
            }
            if (null != o[3]) {
                ecd.setLastInternalId(Long.valueOf(o[3]).longValue());
            }
            if (null != o[4]) {
                ecd.setNumInstances(Integer.valueOf(o[4]).intValue());
            }
            if (null != o[5]) {
                ecd.setNumEvents(Integer.valueOf(o[5]).intValue());
            }
            if (null != o[6]) {
                ecd.setNumErrors(Integer.valueOf(o[6]).intValue());
            }
            if (null != o[7]) {
                ecd.setNumWarnings(Integer.valueOf(o[7]).intValue());
            }
            return ecd;
        }


        return null;
    }

}
