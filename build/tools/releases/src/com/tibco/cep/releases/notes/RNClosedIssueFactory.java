package com.tibco.cep.releases.notes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RNClosedIssueFactory {

    private static final Pattern TEXT_PATTERN = Pattern.compile(
            "\\s*([A-Z]+)-(\\d+)(?:\\r|\\n|\\s)+((?:\\r|\\n|.)*?)\\s*");


    public RNClosedIssueFactory() {
    }


    public RNClosedIssue make(
            String rnKey,
            String fullText) {

        if (null != fullText) {
            final Matcher matcher = TEXT_PATTERN.matcher(fullText);
            if (matcher.matches()) {
                return new RNClosedIssue(rnKey, matcher.group(1), Integer.valueOf(matcher.group(2)), matcher.group(3));
            } else {
                return new RNClosedIssue(rnKey, "", 0, fullText);
            }
        }

        return new RNClosedIssue(rnKey, "", 0, "");
    }

}