package com.tibco.cep.vcs.svn;

import org.tmatesoft.svn.core.SVNLogEntry;

import java.util.Comparator;

/**
 * User: nprade
 * Date: 6/24/11
 * Time: 6:19 PM
 */
class SvnLogEntryDateComparator
        implements Comparator<SVNLogEntry> {

    public int compare(
            SVNLogEntry o1,
            SVNLogEntry o2) {

        if (o1.getDate().after(o2.getDate())) {
            return 1;

        } else if (o1.getDate().before(o2.getDate())) {
            return -1;

        } else {
            return 0;
        }
    }

}
