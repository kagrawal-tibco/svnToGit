package com.tibco.cep.releases.be;

import com.tibco.cep.releases.bom.Assemblies;
import com.tibco.cep.releases.bom.Assembly;
import com.tibco.cep.releases.bom.FileSet;
import com.tibco.cep.releases.bom.FileSetType;
import com.tibco.cep.releases.impl.InstalledFileChangesImpl;
import com.tibco.cep.vcs.VcsBranchContext;

import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;


public class BeSpChangesProvider
        extends BeChangesProvider {


    public BeSpChangesProvider() {

        this(new BeVcsBranchContext());
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public BeSpChangesProvider(
            String branchPath) {

        //noinspection NullableProblems
        this(null, branchPath);
    }


    @SuppressWarnings({"SameParameterValue"})
    public BeSpChangesProvider(
            String connectionUrl,
            String branchPath) {

        this(new BeVcsBranchContext(connectionUrl, branchPath));
    }


    public BeSpChangesProvider(
            VcsBranchContext vcsBranchContext) {

        super(vcsBranchContext);
    }


    private void addAssemblyPathMapping(
            SortedMap<String, SortedSet<String>> map,
            Assembly assembly,
            String path) {

        final String assemblyName = (null == assembly) ? UNMAPPED_PATHS_NAME : assembly.getName();

        SortedSet<String> paths = map.get(assemblyName);
        if (null == paths) {
            paths = new TreeSet<String>();
            map.put(assemblyName, paths);
        }

        paths.add(path);
    }


    @Override
    protected void addAssemblyPathMappings(
            InstalledFileChangesImpl assemblyNameToPath,
            Assemblies knownAssemblies,
            String changedPath,
            SortedSet<FileSet> changedFileSets) {

        if (changedFileSets.isEmpty()) {
            //noinspection NullableProblems
            this.addAssemblyPathMapping(assemblyNameToPath, null, changedPath);

        } else {
            for (final FileSet fileSet : changedFileSets) {
                final boolean isDir = (FileSetType.DIR == fileSet.getType());
                final String pathForDir = isDir ? fileSet.getInstalledGaPathFromSourcePath(changedPath) : null;
                for (final String installedPath : fileSet.getInstalledGaPaths()) {
                    for (final Assembly assembly : knownAssemblies.getByInstalledGaPath(installedPath)) {
                        this.addAssemblyPathMapping(assemblyNameToPath, assembly,
                                (isDir ? pathForDir : installedPath));
                    }
                }
            }
        }

    }


    public static void main(
            String[] args)
            throws Exception {

        if (args.length < 1) {
            System.err.println("Please provide the start revision number, and optionally the end revision number.");
            System.exit(-1);
        }

        final long start = Long.parseLong(args[0]);
        final long end = (args.length < 2) ? -1 : Long.parseLong(args[1]);
        System.err.println("Finding SP file(s) updated between " + start + " and " + end + ".");

        boolean first = true;
        for (final Map.Entry<String, SortedSet<String>> nameToPaths :
                new BeSpChangesProvider().getChanges(start, end).entrySet()) {
            if (first) {
                first = false;
            } else {
                System.out.println();
            }
            System.out.println(nameToPaths.getKey());
            for (final String path : nameToPaths.getValue()) {
                System.out.println("    " + path);
            }
        }
    }


}
