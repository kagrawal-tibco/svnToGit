package com.tibco.be.rms.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.functions.java.util.MapHelper;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.rms.repo.ManagedEMFProject;
import com.tibco.be.ws.scs.IArtifactFilter;
import com.tibco.be.ws.scs.impl.filter.IFilterContext;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Jan 20, 2010
 * Time: 11:10:10 AM
 * <!--
 * Add Description of the class here
 * -->
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.ArtifactsManagement",
        synopsis = "RMS Artifact Manangement functions",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS.ArtifactsManagement", value=true))

public class ArtifactsHelper {

    /**
     *
     * @param filePath
     * @return
     */
    public static String getArtifactPath(String filePath) {
        //This file should exist
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File path " + filePath + " does not exist");
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File path " + filePath + " should not be a directory");
        }
        String artifactPath;
        try {

            String artifactName = null;
            String folder = null;
            String NAME_ATTR = "name=\"";
            String FOLDER_ATTR = "folder=\"";

            byte[] bytes;
            long fromPos = 0;
            long toPos = 1024;
            int bytesRead = 0;
            while ((bytes = readFileContents(filePath, fromPos, toPos)) != null) {
                String line = new String(bytes);
                //Update number of bytes read
                bytesRead = bytesRead + bytes.length;
                //Match pattern
                int index = line.indexOf(NAME_ATTR);
                int folderIndex = line.indexOf(FOLDER_ATTR);
                if (index != -1) {
                    //From this index look for next quote
                    artifactName = line.substring(index + NAME_ATTR.length(), line.indexOf("\"", index + NAME_ATTR.length()));
                }
                if (folderIndex != -1) {
                    folder = line.substring(folderIndex + FOLDER_ATTR.length(), line.indexOf("\"", folderIndex + FOLDER_ATTR.length()));
                }
                if (folder != null && artifactName != null) {
                    artifactPath = folder + artifactName;
                    return artifactPath;
                }
            }
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getClassNameForArtifact",
        synopsis = "Get fully qualified class name of the generated artifact.",
        signature = "String getClassNameForArtifact(String artifactPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Name of the managed project."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The fully qualified name of the BE resource."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtension", type = "String", desc = "File Extension of the artifact.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "A", desc = "String representing the fully qualified name of the java class."),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get fully qualified class name of the generated artifact.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String getClassNameForArtifact(String projectName,
                                                 String artifactPath,
                                                 String artifactExtension) {
        if (ArtifactsType.DECISIONTABLE.getLiteral().intern() == artifactExtension.intern()) {
            /**
             * Get Table and VRF instance
             */
            //Get the appropriate project
            ManagedEMFProject managedProject = (ManagedEMFProject)MapHelper.getObject("MANAGED_PROJECTS", projectName);
            if (managedProject != null) {
                //Get table instance
                Table table = managedProject.getDecisionTable(artifactPath);
                if (table == null) {
                    return null;
                }
                //Get VRF implemented
                String implementedVRFPath = table.getImplements();
                com.tibco.cep.designtime.core.model.rule.RuleFunction vrf =
                        (com.tibco.cep.designtime.core.model.rule.RuleFunction)managedProject.getRuleElement(ArtifactsType.RULEFUNCTION, implementedVRFPath);
                RuleFunction adaptedVRF = CoreAdapterFactory.INSTANCE.createAdapter(vrf, managedProject.getOntology());
                if (vrf == null) {
                    return null;
                }
                return ModelNameUtil.vrfImplClassFSName(adaptedVRF, table.getName());
            }
        }
        return ModelNameUtil.modelPathToGeneratedClassName(artifactPath);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getArtifactCodeSourceDir",
        synopsis = "Get source directory for the artifact type.",
        signature = "String getArtifactCodeSourceDir(String artifactExtension)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtension", type = "String", desc = "Extension of the artifact.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "A", desc = "String representing the source directory"),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get source directory for the artifact type.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String getArtifactCodeSourceDir(String artifactExtension) {
        if (artifactExtension == null) {
            throw new IllegalArgumentException("Artifact Extension cannot be null");
        }
        ArtifactsType artifactsType = ArtifactsType.get(artifactExtension);

        switch (artifactsType) {
            case RULE:
                return "ruleSrc";
            case RULEFUNCTION:
            case DECISIONTABLE:
                return "ruleFunctionSrc";
            case CONCEPT:
                return "conceptSrc";
            case SCORECARD:
                return "scorecardSrc";
            case EVENT:
                return "eventSrc";
            case TIMEEVENT:
                return "timeEventSrc";
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "loadProject",
        synopsis = "Load a managed project's contents.",
        signature = "Object loadProject(String projectPath, String projectContainerDirectory)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "The absolute path of the managed project."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectContainerDirectory", type = "String", desc = "The directory containing the managed project's artifacts.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "The", desc = "loaded project"),
        version = "4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Load a managed project's contents.",
        cautions = "This is an expensive operation hence should be used only at startup.",
        fndomain = {ACTION},
        example = ""
    )
    public static Object loadProject(String projectPath, String projectContainerDirectory) {
        if (projectPath == null) {
            throw new IllegalArgumentException("Project Path for loading project cannot be null");
        }
        ManagedEMFProject managedEMFProject = null;
        try {
            managedEMFProject = new ManagedEMFProject(projectPath, projectContainerDirectory);
            managedEMFProject.load();
        } catch (Exception e) {
            //Silently ignore
        }
        return managedEMFProject;
    }

    
    /**
     *
     * @param filePath
     * @param fromPos
     * @param toPos -> -1 means read all contents
     * @return
     * @throws IOException
     */
    public static byte[] readFileContents(String filePath,
                                           long fromPos,
                                           long toPos) throws IOException {
        FileChannel fc = new RandomAccessFile(filePath, "r").getChannel();
        if (toPos > fc.size()) {
            toPos = fc.size();
        }
        if (toPos == -1) {
            toPos = fc.size();
        }
        MappedByteBuffer mb =
                    fc.map(FileChannel.MapMode.READ_ONLY, fromPos, toPos);
        ByteBuffer buf = mb.asReadOnlyBuffer();
        byte[] bytes = new byte[buf.capacity()];
        buf.get(bytes, 0, bytes.length);
        fc.close();
        return bytes;
    }

    @com.tibco.be.model.functions.BEFunction(
		name = "listFilesRecursively",
		signature = "String[] listFilesRecursively(String dirPath, boolean showHidden)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "dirPath", type = "String", desc = "Path to the directory."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "showHidden", type = "boolean", desc = "")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "List all files in this folder recursively. Do not list directories.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)    
    public static String[] listFilesRecursively(String dirPath, boolean showHidden) {
        if (dirPath == null) {
            throw new IllegalArgumentException("Directory Path cannot be null");
        }
        final List<String> allFiles = new ArrayList<String>();

        File rootDir = new File(dirPath);
        if (rootDir.isDirectory()) {
            //TODO convert to filter
            listFilesRecursive(allFiles, rootDir, false, null);
        } else {
            //If it is a file, just add it to list
            try {
                allFiles.add(rootDir.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return allFiles.toArray(new String[allFiles.size()]);
    }

    /**
     * List all files in this folder recursively. Do not list directories
     * @param dirPath
     * @param filterContext
     * @param artifactFilters
     * @return
     */
    public static <F extends IFilterContext, I extends IArtifactFilter> String[] listFilesRecursive(String dirPath, F filterContext, I... artifactFilters) {
        if (dirPath == null) {
            throw new IllegalArgumentException("Directory Path cannot be null");
        }
        final List<String> allFiles = new ArrayList<String>();

        File rootDir = new File(dirPath);
        if (rootDir.isDirectory()) {
            listFilesRecursive(allFiles, rootDir, true, filterContext, artifactFilters);
        } else {
            //If it is a file, just add it to list
            try {
                allFiles.add(rootDir.getCanonicalPath() + "@" + rootDir.lastModified());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return allFiles.toArray(new String[allFiles.size()]);
    }


    /**
     *
     * @param list
     * @param rootDir
     * @param filterContext
     * @param artifactFilters
     * @param <F>
     * @param <I>
     */
    @SuppressWarnings("unchecked")
    private static <F extends IFilterContext, I extends IArtifactFilter> void listFilesRecursive(final List<String> list,
                                                                                                 File rootDir,
                                                                                                 boolean addLastUpdated,
                                                                                                 final F filterContext,
                                                                                                 final I... artifactFilters) {
        String[] children = rootDir.list(new FilenameFilter() {
            public boolean accept(File dir, String file) {
                //All directories
                File f = new File(dir, file);
                if (!f.isDirectory()) {
                    boolean filter = false;
                    for (I artifactFilter : artifactFilters) {
                        try {
                            filter = filter | artifactFilter.shouldFilter(filterContext, f.getCanonicalPath());
                            if (filter) {
                                break;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        if (!filter) {
                            String pathToAdd = f.getCanonicalPath();
                            if (addLastUpdated) pathToAdd += "@" + f.lastModified();
                            list.add(pathToAdd);
                            return false;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                return true;
            }
        });
        if (children == null) {
            return;
        }
        for (String child : children) {
            //Assuming it is a directory
            listFilesRecursive(list, new File(rootDir, child), addLastUpdated, filterContext, artifactFilters);
        }
    }
}
