package com.tibco.cep.vcs;

import com.tibco.cep.vcs.svn.SvnChangedSourceFilesProvider;
import com.tibco.cep.vcs.svn.SvnConnection;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/30/11
 * Time: 2:51 PM
 */
public class ChangedSourceFilesProviderFactory {

    public ChangedSourceFilesProvider makeChangedFilesProvider(
            VcsConnection vcsConnection)
            throws Exception {

        if (vcsConnection instanceof SvnConnection) {
            return new SvnChangedSourceFilesProvider((SvnConnection) vcsConnection);
        }

        throw new Exception();
    }


}
