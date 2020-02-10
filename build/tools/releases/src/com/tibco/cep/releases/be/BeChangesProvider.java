package com.tibco.cep.releases.be;

import com.tibco.cep.releases.InstalledFilesChanges;
import com.tibco.cep.releases.bom.*;
import com.tibco.cep.releases.impl.InstalledFileChangesImpl;
import com.tibco.cep.vcs.ChangedSourceFilesProvider;
import com.tibco.cep.vcs.ChangedSourceFilesProviderFactory;
import com.tibco.cep.vcs.VcsBranchContext;
import com.tibco.cep.vcs.VcsConnection;

import java.util.*;


/**
 * User: nprade
 * Date: 1/27/12
 * Time: 1:53 PM
 */
public abstract class BeChangesProvider {


    public static final String UNMAPPED_PATHS_NAME = "?";

    private final VcsBranchContext vcsBranchContext;
    private final ChangedSourceFilesProviderFactory changedSourceFilesProviderFactory;


    public BeChangesProvider(
            VcsBranchContext vcsBranchContext) {
        this.changedSourceFilesProviderFactory = new ChangedSourceFilesProviderFactory();
        this.vcsBranchContext = vcsBranchContext;
    }


    protected abstract void addAssemblyPathMappings(
            InstalledFileChangesImpl assemblyNameToPath,
            Assemblies knownAssemblies,
            String changedPath,
            SortedSet<FileSet> changedFileSets);


    private SortedMap<String, SortedSet<FileSet>> getChangedFileSets(
            long revisionStart,
            long revisionStop,
            Bom bom)
            throws Exception {

        final String branchPath = this.vcsBranchContext.getBranchPath();
        final int branchPathLength = branchPath.length();
        final BePathFilter bePathFilter = new BePathFilter(branchPath);
        final FileSets knownFileSets = bom.getFileSets();
        final SortedMap<String, SortedSet<FileSet>> changedPathToFileSets = new TreeMap<String, SortedSet<FileSet>>();

        for (String path : this.getChangedVcsPaths(revisionStart, revisionStop)) {
            if (bePathFilter.accepts(path)) {
                final String shortPath = path.substring(branchPathLength);
                SortedSet<FileSet> fileSets = changedPathToFileSets.get(path);
                if (null == fileSets) {
                    fileSets = new TreeSet<FileSet>();
                    changedPathToFileSets.put(shortPath, fileSets);
                }
                fileSets.addAll(knownFileSets.getBySourcePath(shortPath));
            }
        }

        return changedPathToFileSets;
    }


    private List<String> getChangedVcsPaths(
            long revisionStart,
            long revisionStop)
            throws Exception {

        final String branchPath = this.vcsBranchContext.getBranchPath();
        final VcsConnection connection = this.vcsBranchContext.makeConnection();
        try {
            final ChangedSourceFilesProvider changedSourceFilesProvider =
                    this.changedSourceFilesProviderFactory.makeChangedFilesProvider(connection);

            return changedSourceFilesProvider.getAllChangedFilePaths(branchPath, revisionStart, revisionStop);

        } finally {
            connection.close();
        }
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public InstalledFilesChanges getChanges(
            long revisionStart)
            throws Exception {

        return this.getChanges(revisionStart, -1);
    }


    public InstalledFilesChanges getChanges(
            long revisionStart,
            long revisionStop)
            throws Exception {

        final Bom bom = BeBomProvider.getDefaultBom();
        final Assemblies knownAssemblies = bom.getAssemblies();
        final InstalledFileChangesImpl assemblyNameToPath = new InstalledFileChangesImpl();

        for (final Map.Entry<String, SortedSet<FileSet>> entry :
                this.getChangedFileSets(revisionStart, revisionStop, bom).entrySet()) {

            this.addAssemblyPathMappings(assemblyNameToPath, knownAssemblies, entry.getKey(), entry.getValue());
        }

        return assemblyNameToPath;
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public VcsBranchContext getVcsBranchContext() {
        return this.vcsBranchContext;
    }


}
