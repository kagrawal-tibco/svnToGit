package com.tibco.cep.releases.be;

import com.tibco.cep.vcs.VcsBranchContext;
import org.testng.annotations.Test;

/**
 * User: nprade
 * Date: 7/14/11
 * Time: 11:37 AM
 */
public class BeBuildRevisionFinderTest {

    @Test
    public void MainTest()
            throws Exception {

        final VcsBranchContext vcsBranchContext = new BeVcsBranchContext("http://svn.tibco.com:80/be", "/branches/4.0");

        final BeBuildRevisionFinder revisionFinder = new BeBuildRevisionFinder(vcsBranchContext);

        assert(revisionFinder.findBuildRevision(39000) == 38927);
        assert(revisionFinder.findBuildRevision(42000) == 41998);
        assert(revisionFinder.findBuildRevision(43000) == 42890);
        assert(revisionFinder.findBuildRevision(43200) == 43129);
    }

}
