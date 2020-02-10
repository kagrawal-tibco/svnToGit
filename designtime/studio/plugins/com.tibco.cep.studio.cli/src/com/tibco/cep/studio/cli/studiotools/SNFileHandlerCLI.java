package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class SNFileHandlerCLI extends AbstractCLI {

	public static final String OPERATION_CATEGORY_SHAREDNOTHING = "-sharednothing";
	
    private final static String OPERATION_MIGRATE_FILES = "migrate-files";
    private final static String FLAG_ROOT_FOLDER = "-p";
    private final static String FLAG_CLUSTER_NAME = "-c";
    private final static String FLAG_ENGINE_NAME = "-n";
    private final static String FLAG_SHAREDNOTHING_HELP = "-h";   

    @Override
	public String getOperationCategory() {
		return OPERATION_CATEGORY_SHAREDNOTHING;
	}
    
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || 
           (args.length == 1 && args[0].trim().endsWith("help"))) {
            StudioCommandLineInterpreter.printPrologue(new String[]{});
            System.out.println(new SNFileHandlerCLI().getHelp());
            System.exit(-1);
        }
        String newArgs[] = new String[args.length+2];
        newArgs[0] = SNFileHandlerCLI.OPERATION_CATEGORY_SHAREDNOTHING;
        newArgs[1] = OPERATION_MIGRATE_FILES;
        System.arraycopy(args, 0, newArgs, 2, args.length);
        StudioCommandLineInterpreter.executeCommandLine(newArgs);
    }
    
    @Override
    public String[] getFlags() {
        return new String[]{ FLAG_SHAREDNOTHING_HELP, FLAG_ROOT_FOLDER, FLAG_CLUSTER_NAME, FLAG_ENGINE_NAME };
    }

    @Override
    public String getOperationFlag() {
        return OPERATION_MIGRATE_FILES;
    }

    @Override
    public String getOperationName() {
        return "Migrate SharedNothing Files";
    }

    @Override
    public String getUsageFlags() {
        return "[" + FLAG_SHAREDNOTHING_HELP + "] " +
               "[" + FLAG_ROOT_FOLDER + " <datastore path>] " +
               "[" + FLAG_CLUSTER_NAME + " <cluster name>] " +
               "[" + FLAG_ENGINE_NAME + " <engine name>] ";
    }

    @Override
    public String getHelp() {
        String helpMsg = "Usage: sharednothing " + getUsageFlags() + "\n" +
                "where, \n" +
                "   -h prints this usage \n" +
                "   -p datastore path \n" +
                "   -c cluster name \n" +
                "   -n engine name \n";
        return helpMsg;
    }

    @Override
    public boolean runOperation(Map<String, String> argsMap) throws Exception {
        if (checkIfExcludeOperation(argsMap)) {
            return false;
        }
        
        if (argsMap.containsKey(FLAG_SHAREDNOTHING_HELP)) {
            return false;
        }
        
        if (!argsMap.containsKey(FLAG_ROOT_FOLDER) ||
            !argsMap.containsKey(FLAG_CLUSTER_NAME) ||
            !argsMap.containsKey(FLAG_ENGINE_NAME)) {
            return false;
        }
          
        String rootFolder = argsMap.get(FLAG_ROOT_FOLDER);
        String clusterName = argsMap.get(FLAG_CLUSTER_NAME);
        String engineName = argsMap.get(FLAG_ENGINE_NAME);
        
        if (rootFolder == null || rootFolder.trim().equals("")) {
            return false;
        }
                
        if (clusterName == null || clusterName.trim().equals("")) {
            return false;
        }
                
        if (engineName == null || engineName.trim().equals("")) {
            return false;
        }

        String hostName = null;
        String hostNameProperty = System.getProperty("be.engine.cluster.as.hostaware.hostname", "localhost");
        if (hostNameProperty.trim() != "") {
            if (hostNameProperty.equalsIgnoreCase("localhost")) {
                hostNameProperty = getHostname();
            }
            hostName = normalize(hostNameProperty);
        }
        
        if (hostName == null || hostName.trim().equals("")) {
            return false;
        }
        
        return migrate(rootFolder + File.separator + clusterName, engineName, hostName);
    }

    public static boolean migrate(String rootFolder, String engineName, String hostName) throws Exception {

        File dataStoreHandle = new File(rootFolder);
        
        if (dataStoreHandle.exists() && dataStoreHandle.isDirectory()) {
            if (!dataStoreHandle.canWrite()) {
                throw new RuntimeException("Directory '" + rootFolder + "' does not have write permissions.");
            }
            System.out.println("Migrating '" + rootFolder + "' shared-nothing files for engine[ " + engineName + " ] host [ " + hostName + " ]");
            File[] spaceFolders = dataStoreHandle.listFiles();
            int filesMigrated = 0;
            int foldersMigrated = 0;
            for (int i = 0; i < spaceFolders.length; i++) {
                File spaceFolder = spaceFolders[i];
                if (spaceFolder.isDirectory()) {
                    System.out.println("\n  Space folder '" + spaceFolder.getAbsolutePath() + "' found");
                    File[] engineFolders = spaceFolder.listFiles();
                    for (int j = 0; j < engineFolders.length; j++) {
                        File engineFolder = engineFolders[j];
                        if (engineFolder.isDirectory() && engineFolder.getName().startsWith(engineName)) {
                            File renamedEngineFolder = null;
                            boolean renamed = false;
                            File[] engineFiles = engineFolder.listFiles();
                            System.out.println("    Engine folder '" + engineFolder.getAbsolutePath() + "' for engine [ " + engineName + " ] has " + engineFiles.length + " files.");
                            String renamedFolderName = hostName + "." + engineFolder.getName();
                            renamedEngineFolder = new File(spaceFolder.getAbsolutePath() + File.separator + renamedFolderName);
                            if (renamedEngineFolder.exists() == false) {
                                for (int k = 0; k < engineFiles.length; k++) {
                                    File engineFile = engineFiles[k];
                                    if (engineFile.isFile() && engineFile.getName().startsWith(engineName)) {
                                        String renamedFileName = hostName + "." + engineFile.getName();
                                        System.out.println("      Renaming engine file '" + engineFile.getName() + "' for engine [ " + engineName + " ]" + " to '" + renamedFileName + "'");
                                        if (renameFile(engineFile, new File(engineFolder.getAbsolutePath() + File.separator + renamedFileName))) {
                                            renamed = true;
                                            filesMigrated++;
                                        } else {
                                            // Renaming file failed... something wrong!
                                            String msg = "\nMigration failed: Error during renaming engine file '" + engineFile.getName() + "' (check if locked).";
                                            System.err.println(msg);
                                            return true; //throw new RuntimeException(msg);
                                        }
                                    } else if (engineFile.isFile() && engineFile.getName().startsWith(hostName + "." + engineName)) {
                                        System.out.println("      Skipping previously named engine file '" + engineFile.getName() + "' for engine [ " + engineName + " ]");
                                        renamed = true;
                                    } else {
                                        System.out.println("      Skipping unknown file '" + engineFile.getName() + "' for engine [ " + engineName + " ]");
                                    }
                                }
                                if (renamed) {
                                    System.out.println("      Renaming engine folder '" + engineFolder.getName() + "' for engine [ " + engineName + " ]" + " to '" + renamedFolderName + "'");
                                    if (renameFile(engineFolder, renamedEngineFolder)) {
                                        foldersMigrated++;
                                    } else {
                                        // Renaming folder failed... something wrong!
                                        String msg = "\nMigration failed: Error during renaming engine folder '" + engineFolder.getName() + "' (check if locked).";
                                        System.err.println(msg);
                                        return true; //throw new RuntimeException(msg);
                                    }
                                }
                                if (renamed && renamedEngineFolder.isDirectory()) {
                                    engineFiles = renamedEngineFolder.listFiles();
                                    for (int k = 0; k < engineFiles.length; k++) {
                                        File engineFile = engineFiles[k];
                                        System.out.println("        Final renamed engine file(s): '" + engineFile.getAbsolutePath() + "'");
                                    }
                                }
                            } else {
                                // Renamed(to-be) folder exists... something wrong!
                                String msg = "\nMigration failed: Target engine folder '" + renamedEngineFolder.getAbsolutePath() + "' exists already.";
                                System.err.println(msg);
                                return true; //throw new RuntimeException(msg);
                            }
                        } else {
                            // Folders for other engines... no need to log anything.
                            //System.out.println("  Engine folder '" + engineFolder.getAbsolutePath() + "' has no files to migrate for engine [ " + engineName + " ]");
                        }
                    }
                }
            }
            
            if (filesMigrated > 0 || foldersMigrated > 0) {
                System.out.println("\nMigration completed with total of " + filesMigrated + " shared-nothing files and " + foldersMigrated + " folders.\n");
            } else {
                System.out.println("\nMigration completed without migrating any shared-nothing files or folders.\n");
            }
        } else {
            String msg = "\nMigration failed: Path '" + rootFolder + "' is not a directory.";
            System.err.println(msg);
            return true; //throw new RuntimeException(msg);
        }
        
        return true;
    }

    private static boolean renameFile(File engineFile, File newFile) {
        return engineFile.renameTo(newFile);
    }

    private static String getHostname() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        String hostName = addr.getHostName();
        return hostName;
    }

    private static String normalize(String input) {
        String output;
        output = input.replaceAll("\\.", "_");
        return output;
    }    
}
