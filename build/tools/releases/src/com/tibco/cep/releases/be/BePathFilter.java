package com.tibco.cep.releases.be;

import com.tibco.cep.util.Filter;

import java.util.regex.Pattern;

public class BePathFilter
        implements Filter<String> {

    private final Pattern includes;
    private final Pattern excludes;


    public BePathFilter(
            String basePath) {

        final String quotedPath = Pattern.quote(basePath + (basePath.endsWith("/") ? "" : "/"));

        this.includes = Pattern.compile(
                new StringBuffer(quotedPath).append("[^/]+/[^/].*").toString()); // Must be a file, not in the root.

        this.excludes = Pattern.compile(
                new StringBuffer(quotedPath).append("(?:")
                        .append("(?:.*Version\\.(?:tag|java))")                         // Version markers.
                        .append("|(?:build(?:/.*)?)")                                   // Build files.
                        .append("|(?:dashboard/beviews/src(?:/.*)?)")                   // Compiled into SWF.
                        .append("|(?:docs(?:/.*)?)")                                    // Internal docs.
                        .append("|(?:em/ui/com.tibco.cep.ui.monitor/src/data(?:/.*)?)") // Sample data.
                        .append("|(?:hotfix(?:/.*)?)")                                  // All the read-me files.
                        .append("|(?:studio/features(?:/.*)?)")                         // Studio features.
                        .append("|(?:studio/plugins/com\\.tibco(?:\\.\\w+)*)")          // Root folder for each plugin.
                        .append("|(?:studio/plugins/com\\.tibco\\.cep\\.decision\\.table\\.application/.*)") // Gone.
                        .append("|(?:studio/setup/plugins/com\\.tibco\\.cep\\.diagramming\\.dep.*)") // TS licensing.
                        .append("|(?:studio/sites(?:/.*)?)")                            // Site SDK.
                        .append("|(?:tools/releases(?:/.*)?)")                          // Tools for releases.
                        .append("|(?:tools/test(?:/.*)?)")                              // Tools for testing.
                        .append(")").toString());
    }


    public boolean accepts(
            String path) {

        return (null != path)
                && this.includes.matcher(path).matches()
                && !this.excludes.matcher(path).matches();
    }


}
