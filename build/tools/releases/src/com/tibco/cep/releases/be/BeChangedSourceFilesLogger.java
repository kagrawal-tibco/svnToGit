package com.tibco.cep.releases.be;

import com.tibco.cep.vcs.ChangedSourceFilesProvider;
import com.tibco.cep.vcs.ChangedSourceFilesProviderFactory;
import com.tibco.cep.vcs.VcsBranchContext;
import com.tibco.cep.vcs.VcsConnection;


public class BeChangedSourceFilesLogger {


    private static final String DEFAULT_BRANCH_PATH = null;
    private static final String DEFAULT_CONNECTION_URL = null;


    private final ChangedSourceFilesProviderFactory changedSourceFilesProviderFactory;
    private final VcsBranchContext vcsBranchContext;


    public BeChangedSourceFilesLogger() {

        this(DEFAULT_BRANCH_PATH);
    }


    @SuppressWarnings({"SameParameterValue"})
    public BeChangedSourceFilesLogger(
            String branchPath) {

        this(DEFAULT_CONNECTION_URL, branchPath);
    }


    @SuppressWarnings({"SameParameterValue"})
    public BeChangedSourceFilesLogger(
            String connectionUrl,
            String branchPath) {

        this(new BeVcsBranchContext(connectionUrl, branchPath));
    }


    public BeChangedSourceFilesLogger(
            VcsBranchContext vcsBranchContext) {

        this.changedSourceFilesProviderFactory = new ChangedSourceFilesProviderFactory();
        this.vcsBranchContext = vcsBranchContext;
    }


    public void log(
            long revisionStart)
            throws Exception {

        this.log(revisionStart, -1);
    }


    public void log(
            long revisionStart,
            long revisionStop)
            throws Exception {

        final String branchPath = this.vcsBranchContext.getBranchPath();
        final BePathFilter filter = new BePathFilter(branchPath);

        final VcsConnection connection = this.vcsBranchContext.makeConnection();
        try {
            final ChangedSourceFilesProvider changedSourceFilesProvider =
                    this.changedSourceFilesProviderFactory.makeChangedFilesProvider(connection);

            for (final String path :
                    changedSourceFilesProvider.getAllChangedFilePaths(branchPath, revisionStart, revisionStop)) {
                if (filter.accepts(path)) {
                    System.out.println(path);
                }
            }

        } finally {
            connection.close();
        }
    }


    public static void main(
            String[] args)
            throws Exception {

        if (args.length < 1) {
            System.out.println("Please provide the start revision number, and optionally the end revision number.");
            System.exit(1);
        }

        final long start = Long.parseLong(args[0]);
        final long end = (args.length < 2) ? -1 : Long.parseLong(args[1]);
        System.out.println("Relevant changed paths between " + start + " and " + end + ":\n");

        final BeChangedSourceFilesLogger logger = new BeChangedSourceFilesLogger();
        logger.log(start, end);
    }


}
