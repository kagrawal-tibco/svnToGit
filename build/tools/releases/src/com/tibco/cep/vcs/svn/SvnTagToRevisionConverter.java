package com.tibco.cep.vcs.svn;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;

import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 7/6/11
 * Time: 7:23 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class SvnTagToRevisionConverter {


    private static final SVNRevision START_REVISION = SVNRevision.create(0);


    private SvnConnection connection;


    public SvnTagToRevisionConverter(
            SvnConnection connection) {

        this.connection = connection;
    }


    public long getRevision(
            String tagPath)
            throws SVNException {

		final TreeSet<Long> revisions = new TreeSet<Long>();

        final SVNLogClient svnLogClient = this.connection.getClientManager().getLogClient();
		svnLogClient.doLog(
                this.connection.getSvnUrl(),
                new String[]{tagPath},
                START_REVISION,
                START_REVISION,
                SVNRevision.HEAD,
                true,
                true,
                0,
                new ISVNLogEntryHandler() {
                    @Override
                    public void handleLogEntry(
                            SVNLogEntry entry)
                            throws SVNException {
                        revisions.add(entry.getRevision());
                    }
                });

		return revisions.last();
	}


}
