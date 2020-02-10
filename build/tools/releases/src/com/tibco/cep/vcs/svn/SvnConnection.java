package com.tibco.cep.vcs.svn;

import com.tibco.cep.vcs.VcsConnection;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 4:28 PM
 */
public class SvnConnection
        implements VcsConnection {

    private SVNClientManager svnClientManager;
    private SVNURL svnUrl;


    SvnConnection(
            String url)
            throws Exception {

        if (url.startsWith("http")) {
            DAVRepositoryFactory.setup();
        } else if (url.startsWith("svn")) {
            SVNRepositoryFactoryImpl.setup();
        } else if (url.startsWith("file:")) {
            FSRepositoryFactory.setup();
        } else {
            throw new IllegalArgumentException();
        }

//        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userid, password);
//              this.repository = SVNRepositoryFactory.create( SVNURL.parseURIDecoded( URL ) );
//              repository.setAuthenticationManager( authManager );
        final ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        this.svnClientManager = SVNClientManager.newInstance(options); //, authManager);

        this.svnUrl = SVNURL.parseURIEncoded(url);
    }


    @Override
    public void close() throws Exception {
        this.svnClientManager.dispose();
    }


    public SVNClientManager getClientManager() {
        return this.svnClientManager;
    }


    public SVNURL getSvnUrl() {
        return this.svnUrl;
    }

}
