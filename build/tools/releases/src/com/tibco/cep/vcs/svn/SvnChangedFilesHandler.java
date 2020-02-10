package com.tibco.cep.vcs.svn;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/24/11
 * Time: 8:02 PM
 */

class SvnChangedFilesHandler implements ISVNLogEntryHandler {

    private final SortedMap<String, SortedSet<SVNLogEntry>> pathToEntries;


    public SvnChangedFilesHandler(
            SortedMap<String, SortedSet<SVNLogEntry>> pathToEntries) {
        this.pathToEntries = pathToEntries;
    }


    public void handleLogEntry(
            SVNLogEntry logEntry)
            throws SVNException {

        for (Object o : logEntry.getChangedPaths().keySet()) {
            final String changedPath = (String) o;
            SortedSet<SVNLogEntry> entries = this.pathToEntries.get(changedPath);
            if (null == entries) {
                entries = new TreeSet<SVNLogEntry>(new SvnLogEntryDateComparator());
                this.pathToEntries.put(changedPath, entries);
            }
            entries.add(logEntry);
        }
    }
}
