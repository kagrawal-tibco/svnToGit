package com.tibco.cep.query.benchmark.query.selectcardswipewinavg;

import com.tibco.cep.query.benchmark.query.FileWriterChunkProcessor;

import java.io.Writer;

/*
* Author: Ashwin Jayaprakash Date: Jun 19, 2008 Time: 2:03:23 PM
*/
public class ChunkProcessorImpl extends FileWriterChunkProcessor<Object[]> {
    /**
     * {@value}
     */
    public static final String SYSTEM_PROPERTY_NAME_OUTPUT_DIR =
            "benchmark.query.selectcardswipewinavg.output.dir";

    /**
     * {@value}
     */
    public static final String FILE_NAME = "selectcardswipewinavg_lane_output";

    protected String[] getFileColumnHeaders() {
        return new String[]{"groupName", "count", "minCreatedTime", "maxCreatedTime", "currTime",
                "minEventId", "maxEventId"};
    }

    protected String getOutputDir() {
        return System.getProperty(SYSTEM_PROPERTY_NAME_OUTPUT_DIR);
    }

    protected String getOutputFileName() {
        return FILE_NAME;
    }

    protected void writeRow(int lane, Writer writer, Object[] row) throws Exception {
        for (int i = 0; i < row.length; i++) {
            if (i > 0) {
                writer.write(FILE_COLUMN_SEPARATOR);
            }
            writer.write(row[i].toString());
        }

        writer.write(FILE_ROW_SEPARATOR);
    }
}