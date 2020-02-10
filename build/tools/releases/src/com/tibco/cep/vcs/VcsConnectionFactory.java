package com.tibco.cep.vcs;

import com.tibco.cep.vcs.svn.SvnConnectionFactory;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 4:37 PM
 */
public class VcsConnectionFactory {

    public VcsConnection create(
            String url)
            throws Exception {

        if (url.startsWith("svn:")
                || url.startsWith("svn+")
                || url.startsWith("http:")
                || url.startsWith("https:")
                || url.startsWith("file:")) {
            return new SvnConnectionFactory().create(url);
        }

        throw new IllegalArgumentException();
    }


}
