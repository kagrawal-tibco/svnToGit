package com.tibco.cep.releases.be;

import com.tibco.cep.vcs.VcsBranchContext;
import com.tibco.cep.vcs.svn.SvnSourceFileProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: nprade
 * Date: 7/14/11
 * Time: 11:37 AM
 */
public class BeBuildRevisionFinder {


    private static final String BUILD_REVISION_PATH = "/build/version.properties";
    private static final Pattern REVISION_PATTERN = Pattern.compile("^\\s*env.BE_REVISION\\s*=\\s*(\\d+)\\s*$",
            Pattern.MULTILINE);


    private final VcsBranchContext vcsBranchContext;


    @SuppressWarnings({"UnusedDeclaration"})
    public BeBuildRevisionFinder() {

        this(new BeVcsBranchContext());
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public BeBuildRevisionFinder(
            VcsBranchContext vcsBranchContext) {

        this.vcsBranchContext = vcsBranchContext;
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public long findBuildRevision()
            throws Exception {

        return this.findBuildRevision(-1);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public long findBuildRevision(
            long revision)
            throws Exception {

        final SvnSourceFileProvider sourceFileProvider = new SvnSourceFileProvider(this.vcsBranchContext);
        final byte[] revisionAsBytes = sourceFileProvider.getSourceFile(this.getRevisionFileUrl(), revision);

        final Matcher matcher = REVISION_PATTERN.matcher(new String(revisionAsBytes));
        if (!matcher.find()) {
            throw new Exception("Revision not found");
        }

        return Long.parseLong(matcher.group(1));
    }


    private String getRevisionFileUrl() {
        return this.vcsBranchContext.getConnectionUrl()
                + this.vcsBranchContext.getBranchPath()
                + BUILD_REVISION_PATH;
    }


    public static void main(
            String[] args)
            throws Exception {

        final String url = (args.length < 1) ? null : args[0];
        final String branch = (args.length < 2) ? null : args[1];
        final long revision = (args.length < 3) ? -1 : Long.parseLong(args[2]);

        final VcsBranchContext vcsBranchContext = (null == url)
                ? new BeVcsBranchContext()
                : (null == branch) ? new BeVcsBranchContext(url) : new BeVcsBranchContext(url, branch);

        System.err.println("Finding BE build revision for connection <" + vcsBranchContext.getConnectionUrl()
                + ">, branch '" + vcsBranchContext.getBranchPath()
                + "', revision " + ((revision < 0) ? "LAST" : "" + revision)
                + ".");

        final BeBuildRevisionFinder revisionFinder = new BeBuildRevisionFinder(vcsBranchContext);
        System.out.println(revisionFinder.findBuildRevision(revision));
    }


}
