package com.tibco.cep.vcs.svn;

import com.tibco.cep.vcs.VcsConnectionFactory;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 5:00 PM
 */
public class SvnConnectionFactory
        extends VcsConnectionFactory {


    @Override
    public SvnConnection create(
            String url)
            throws Exception {

        return new SvnConnection(url);
    }

}
