package com.tibco.cep.vcs.svn;

import com.tibco.cep.util.Filter;
import com.tibco.cep.vcs.ChangedSourceFilesProvider;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;

import java.util.*;


public class SvnChangedSourceFilesProvider
        implements ChangedSourceFilesProvider {


    private static final TypeFilter ADDED_FILES_FILTER = new TypeFilter() {
        public boolean accepts(SVNLogEntryPath first, SVNLogEntryPath last) {
            return ('A' == first.getType()) && ('D' != last.getType());
        }
    };

    private static final TypeFilter DELETED_FILES_FILTER = new TypeFilter() {
        public boolean accepts(SVNLogEntryPath first, SVNLogEntryPath last) {
            return ('A' != first.getType()) && ('D' == last.getType());
        }
    };

    private static final TypeFilter MODIFIED_FILES_FILTER = new TypeFilter() {
        public boolean accepts(SVNLogEntryPath first, SVNLogEntryPath last) {
            return ('A' != first.getType()) && ('D' != last.getType());
        }
    };

    private static final TypeFilter REMAINING_CHANGED_FILES_FILTER = new TypeFilter() {
        public boolean accepts(SVNLogEntryPath first, SVNLogEntryPath last) {
            return ('D' != last.getType());
        }
    };


    private final SvnConnection connection;


    public SvnChangedSourceFilesProvider(
            SvnConnection connection) {

        this.connection = connection;

    }

    private void fetchChangedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd,
            ISVNLogEntryHandler handler)
            throws SVNException {

        final SVNLogClient svnLogClient = this.connection.getClientManager().getLogClient();
        svnLogClient.doLog(
                this.connection.getSvnUrl(),
                new String[]{branchPath},
                SVNRevision.HEAD,
                SVNRevision.create(revisionStart),
                ((revisionEnd <= 0) ? SVNRevision.HEAD : SVNRevision.create(revisionEnd)),
                true,
                true,
                0,
                handler);
    }


    public List<String> getAddedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd)
            throws Exception {

        return this.getChangedFilePaths(branchPath, revisionStart, revisionEnd, ADDED_FILES_FILTER);

    }


    public List<String> getAllChangedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd)
            throws SVNException {

        //noinspection NullableProblems
        return this.getChangedFilePaths(branchPath, revisionStart, revisionEnd, null);
    }


    private List<String> getChangedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd,
            TypeFilter typeFilter)
            throws SVNException {

        final TreeMap<String, SortedSet<SVNLogEntry>> pathToEntries = new TreeMap<String, SortedSet<SVNLogEntry>>();

        final ISVNLogEntryHandler handler = new SvnChangedFilesHandler(pathToEntries);

        this.fetchChangedFilePaths(branchPath, revisionStart, revisionEnd, handler);

        final List<String> results = new ArrayList<String>();

        for (final Map.Entry<String, SortedSet<SVNLogEntry>> e : pathToEntries.entrySet()) {
            final String path = e.getKey();
            final SortedSet<SVNLogEntry> entries = e.getValue();
            if ((null == typeFilter)
                    || typeFilter.accepts(
                    (SVNLogEntryPath) entries.first().getChangedPaths().get(path),
                    (SVNLogEntryPath) entries.last().getChangedPaths().get(path))) {
                results.add(path);
            }
        }

        return results;
    }


    public List<String> getDeletedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd)
            throws Exception {

        return this.getChangedFilePaths(branchPath, revisionStart, revisionEnd, DELETED_FILES_FILTER);
    }


    public List<String> getModifiedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd)
            throws Exception {

        return this.getChangedFilePaths(branchPath, revisionStart, revisionEnd, MODIFIED_FILES_FILTER);
    }


    public List<String> getRemainingChangedFilePaths(
            String branchPath,
            long revisionStart,
            long revisionEnd)
            throws Exception {

        return this.getChangedFilePaths(branchPath, revisionStart, revisionEnd, REMAINING_CHANGED_FILES_FILTER);

    }


    private static abstract class TypeFilter implements Filter<SVNLogEntryPath[]> {

        public boolean accepts(SVNLogEntryPath[] firstAndLast) {
            return this.accepts(firstAndLast[0], firstAndLast[1]);
        }

        public abstract boolean accepts(SVNLogEntryPath first, SVNLogEntryPath last);

    }

}
