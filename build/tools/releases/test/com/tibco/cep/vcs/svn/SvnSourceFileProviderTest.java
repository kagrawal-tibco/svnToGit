package com.tibco.cep.vcs.svn;

import com.tibco.cep.releases.be.BeVcsBranchContext;
import com.tibco.cep.vcs.VcsBranchContext;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * User: nprade
 * Date: 7/14/11
 * Time: 12:15 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class SvnSourceFileProviderTest {


    @Test
    public void MainTest()
            throws Exception {

        final VcsBranchContext vcsBranchContext = new BeVcsBranchContext("http://svn.tibco.com:80/be", "/branches/4.0");

        final SvnSourceFileProvider svnSourceFileProvider = new SvnSourceFileProvider(vcsBranchContext);

        final String url = "http://svn.tibco.com:80/be/branches/4.0/build/logs/rev-epe.log";

        assert (Arrays.equals(svnSourceFileProvider.getSourceFile(url, 39000), "38831\n".getBytes()));
        assert (Arrays.equals(svnSourceFileProvider.getSourceFile(url, 42000), "39334\n".getBytes()));
        assert (Arrays.equals(svnSourceFileProvider.getSourceFile(url, 43000), "42471\n".getBytes()));
        assert (Arrays.equals(svnSourceFileProvider.getSourceFile(url, 43200), "43130\n".getBytes()));
    }

}
