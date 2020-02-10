package com.tibco.be.migration.expimp.providers.csv;

import java.util.HashMap;

import com.tibco.be.migration.CSVReader;
import com.tibco.be.migration.CSVReaderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 26, 2008
 * Time: 11:16:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class CSVScoreCardIndexDeserializer {
    public static String SCORECARD_INDEX_FILE_NAME = "scorecardIds";
    private String inputUrl;


    public CSVScoreCardIndexDeserializer(String inputUrl) {
        this.inputUrl = inputUrl;
    }

    public HashMap deserialize() throws Exception {
        HashMap indexMap = new HashMap();
        CSVReader reader = new CSVReaderImpl(this.inputUrl, SCORECARD_INDEX_FILE_NAME);
        for( String[] o = reader.nextRow(); o != null ; o = reader.nextRow() ) {            

            ScoreCardIndex si = new ScoreCardIndex();
            if (null != o[0]) {
                si.setScoreCardName((String) o[0]);
            }
            if (null != o[1]) {
                si.setScoreCardIndex(Integer.valueOf((String) o[1]).intValue());
            }
            indexMap.put(si.getScoreCardName(),si);
        }
        return indexMap;
    }
}
