package com.tibco.cep.studio.core.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TerrFunctionMigrator extends FunctionMigrator {
    // Replacement String and pattern to find "Analytics.Engine"
    private static final String EngineReplace = "Analytics.TERR.Engine";
    private static final Pattern EngineFindPattern = Pattern.compile("Analytics\\s*\\.\\s*Engine");

    // Replacement String and pattern to find "Analytics.DataFrame"
    private static final String DataFrameReplace = "Analytics.TERR.DataFrame";
    private static final Pattern DataFrameFindPattern = Pattern.compile("Analytics\\s*\\.\\s*DataFrame");

    // Replacement String and pattern to find "Analytics.DataList"
    private static final String DataListReplace = "Analytics.TERR.DataList";
    private static final Pattern DataListFindPattern = Pattern.compile("Analytics\\s*\\.\\s*DataList");

    private static final Map<String, Pattern> ReplaceFindPatterns = new HashMap<String, Pattern>();

    static {
        ReplaceFindPatterns.put(EngineReplace, EngineFindPattern);
        ReplaceFindPatterns.put(DataFrameReplace, DataFrameFindPattern);
        ReplaceFindPatterns.put(DataListReplace, DataListFindPattern);
    }

    @Override
    public int getPriority() {
        // low priority
        return 101;
    }

    /*
     * Finds the Patterns in StringBuilder and replaces with the Replacement Strings
     * String
     */
    @Override
    protected void createReplaceEdits(StringBuilder sb, File file) {
        for (Entry<String, Pattern> entry : ReplaceFindPatterns.entrySet()) {
            Matcher matcher = entry.getValue().matcher(sb);
            if (matcher.find()) {
                int startIndex, endIndex;
                int fromIndex = 0;
                int lenofReplaceString = entry.getKey().length();
                while (matcher.find(fromIndex)) {
                    startIndex = matcher.start();
                    endIndex = matcher.end();
                    sb.replace(startIndex, endIndex, entry.getKey());
                    fromIndex = startIndex + lenofReplaceString;
                }
                writeFile(sb, file);
            }
        }
    }
}
