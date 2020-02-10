package com.tibco.be.maven.plugin.docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo( name = "buildBaseImage")
public class BuildBEBaseImage extends AbstractMojo {

	@Parameter
	private LinkedHashMap<String, String> baseImageConfig;
	
	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
	private Map<String,String> globalValidAddons = new HashMap<String,String>();
	private Map<String,String> globalValidEdition = new HashMap<String,String>();
	private Map<String,String> globalValidAddOnEdition = new HashMap<String,String>();
	private Map<String,String> globalValidASMap = new HashMap<String,String>();
	private Map<String,String> globalValidASMapMax = new HashMap<String,String>();
	
	private String BE_HOME = "",BE_PRODUCT_IMAGE_VERSION="",ARG_INSTALLER_LOCATION="",ARG_EDITION="enterprise",ARG_AS_VERSION="",ARG_VERSION="",ARG_ADDONS="",ARG_IMAGE_VERSION="",ARG_HF="",ARG_AS_HF="",ARG_DOCKERFILE="";
	private String ARG_PLATFORM = "";
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException  {
		try {
			String beHomeEntry = beProjectDetails.get("beHome");
			initializeMaps();
			if(isArgumentNull(baseImageConfig.get("generateBaseImage")) || isArgumentNull(baseImageConfig.get("useBEHome"))){
				printUsage();
				throw new Exception("Missing mandatory arguments");
			}
			if(!Boolean.parseBoolean(baseImageConfig.get("generateBaseImage")))
				return;
			if (Boolean.parseBoolean(baseImageConfig.get("useBEHome"))) {
				readOptimizedImageArguments();
				if (!validateVersion()) {
					System.out.println("ERROR: Invalid value for be version: " + ARG_VERSION + ". Make sure you provide the fully qualified version.Ex- 5.4.0");
					throw new Exception();
				}
				if(isArgumentNull(BE_HOME))
					BE_HOME = beHomeEntry;
//				printOptimizedImageArgumentInfo();
				
				String dockerExecDirectory = BE_HOME + "/docker/frominstall";
				List<String> createBaseImage = new ArrayList<>();
				if (System.getProperty("os.name").toLowerCase().contains("win")) {
					createBaseImage.add(dockerExecDirectory + "/build_be_image_frominstallation.bat");
				} else {
					createBaseImage.add(dockerExecDirectory + "/build_be_image_frominstallation.sh");
				}
				createBaseImage.add("-v"); createBaseImage.add(ARG_VERSION);
				createBaseImage.add("-i"); createBaseImage.add(BE_PRODUCT_IMAGE_VERSION);
				if (!isArgumentNull(ARG_DOCKERFILE)) {
					createBaseImage.add("-d"); createBaseImage.add(ARG_DOCKERFILE);
				}
				createBaseImage.add("-h"); createBaseImage.add(BE_HOME);
				
				ProcessBuilder createOptimizedImageProcess = new ProcessBuilder(createBaseImage);
				createOptimizedImageProcess.directory(new File(dockerExecDirectory));
				Process createOptimizedImagePro = createOptimizedImageProcess.start();
				printLines(" stdout:", createOptimizedImagePro.getInputStream());
				printLines(" stderr:", createOptimizedImagePro.getErrorStream());
				createOptimizedImagePro.waitFor();
				if (createOptimizedImagePro.exitValue() > 0) {
					System.out.println(" exitValue() " + createOptimizedImagePro.exitValue());
					throw new Exception("Error while building BaseImage");
				}
			} else {
				readArguments();
				if (!new File(ARG_INSTALLER_LOCATION).isDirectory()) {
					System.out.println("ERROR: The directory - " + ARG_INSTALLER_LOCATION + " is not a valid directory.Enter a valid directory and try again.");
				}
				if (!validateArguments()) {
					throw new Exception("Invalid Arguments");
				}
				String dockerExecDirectory = beHomeEntry + "/docker/bin";
				System.out.println("INFO: Building docker image for TIBCO BusinessEvents Version: " + ARG_VERSION + " and Image Version: " + ARG_IMAGE_VERSION);
				List<String> createBaseImage = new ArrayList<>();
				if (System.getProperty("os.name").toLowerCase().contains("win")) {
					createBaseImage.add(dockerExecDirectory + "/build_be_image.bat");
				} else {
					createBaseImage.add(dockerExecDirectory + "/build_be_image.sh");
				}
				createBaseImage.add("-l"); createBaseImage.add(ARG_INSTALLER_LOCATION);
				createBaseImage.add("-v"); createBaseImage.add(ARG_VERSION);
				createBaseImage.add("-i"); createBaseImage.add(ARG_IMAGE_VERSION);
				if (!isArgumentNull(ARG_ADDONS)) {
					createBaseImage.add("-a");
					createBaseImage.add(ARG_ADDONS.contains("\"") ? ARG_ADDONS.replaceAll(" ", "") : ("\"" + ARG_ADDONS.replaceAll(" ", "") + "\"") );
				}
				if (!isArgumentNull(ARG_HF)) {
					createBaseImage.add("--hf"); createBaseImage.add(ARG_HF);
				}
				if (!isArgumentNull(ARG_AS_HF)) {
					createBaseImage.add("--as-hf"); createBaseImage.add(ARG_AS_HF);
				}
				if (!isArgumentNull(ARG_DOCKERFILE)) {
					createBaseImage.add("-d"); createBaseImage.add(ARG_DOCKERFILE);
				}
				createBaseImage.add("-p"); createBaseImage.add(ARG_PLATFORM);
				
				runBaseImageBuildProcess(createBaseImage, dockerExecDirectory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException("Error while creating base image");
		}
	}
	private boolean validateArguments() {
		if(!globalValidEdition.containsKey(ARG_EDITION)){
			System.out.println("ERROR: Invalid value for Edition: " + ARG_EDITION + " Should be either of standard/enterprise.");
			return false;
		}
		boolean validAdon=true;
		if (!isArgumentNull(ARG_ADDONS)) {
			String[] addOnArray = ARG_ADDONS.split(",");
			for(String addOn : addOnArray){
				if(!globalValidAddons.containsKey(addOn)){
					System.out.println("ERROR: Invalid value for addon ::" +addOn+" .Should be either of process/views/decisionmanager/eventstreamprocessing/datamodeling.");
					validAdon = false;
					break;
				}
			}
		}
		if (!validAdon)
			return false;
		if (!validateVersion()) {
			System.out.println("ERROR: Invalid value for be version: " + ARG_VERSION + ". Make sure you provide the fully qualified version. Example 5.4.0");
			return false;
		}
		if (!validateActiveSpace() || !validateBaseProduct() || !validateAddOns() || !validateHf())
			return false;
		return true;
	}
	private boolean validateHf(){
		if(isArgumentNull(ARG_HF))
			return true;
		String local_hf=ARG_HF;
		if(ARG_HF!=null){
			if(ARG_HF.length()==0 || ARG_HF.length()>3){
				System.out.println("ERROR: Invalid value for base HF :"+ARG_HF);
				return false;
			}
		}
		if(ARG_HF.length()==1)
			local_hf = "00"+local_hf;
		if(ARG_HF.length()==2)
			local_hf = "0"+local_hf;
		int COUNT=0;
		File[] list = new File(ARG_INSTALLER_LOCATION).listFiles();
		for(File file : list){
			if(file.getName().matches(".*businessevents-hf*"+"_"+ARG_VERSION+"_HF-"+local_hf+".*.zip")){
				COUNT++;
			}
		}
		if(COUNT < 1){
			System.out.println("ERROR: No package found for hotfix :" + ARG_HF +"with version: " +ARG_VERSION +"in the installer location.");
			return false;
		}
		if(COUNT > 1){
			System.out.println("ERROR: More than one hf products are present in the installer location.There should be only one.");
			return false;
		}
		return true;
	}
	private boolean validateAddOns() {
		boolean addOnVerification = true;
		if(!isArgumentNull(ARG_ADDONS)){
			String[] addOnArray = ARG_ADDONS.split(",");
			for(String addOn : addOnArray){
				int COUNT=0;
				File[] list = new File(ARG_INSTALLER_LOCATION).listFiles();
				for(File file : list){
					if(file.getName().matches(".*"+globalValidAddons.get(addOn)+"_"+ARG_VERSION+".*.zip")){
						COUNT++;
					}
				}
				if(COUNT < 1){
					System.out.println("ERROR: No package found for Addon:" + addOn + " with version: " + ARG_VERSION + " in the installer location.");
					addOnVerification=false;
					break;
				}
				if(COUNT > 1){
					System.out.println("ERROR: More than one Addon:" + addOn + " are present in the installer location. There should be only one.");
					addOnVerification=false;
					break;
				}
			}
		}
		return addOnVerification;
	}
	
	private boolean validateBaseProduct() {
		int BE_COUNT=0;
		File[] list = new File(ARG_INSTALLER_LOCATION).listFiles();
		String regex = "^.*" + globalValidEdition.get(ARG_EDITION) + "_" + ARG_VERSION + "_linux.*\\.zip$";
		if ("win".equals(ARG_PLATFORM)) {
			regex = "^.*" + globalValidEdition.get(ARG_EDITION) + "_" + ARG_VERSION + "_win.*\\.zip$";
		}
		for (File file : list) {
			if (file.getName().matches(regex)){
				BE_COUNT++;
			}
		}
		if (BE_COUNT < 1) {
			System.out.println("ERROR: No package found with version: " + ARG_VERSION + " and platform: " + ARG_PLATFORM + " in the installer location.");
			return false;
		}
		if(BE_COUNT > 1){
			System.out.println("ERROR: More than one base products are present in the installer location.There should be only one.");
			return false;
		}
		return true;
	}
	private boolean validateActiveSpace() {
		String as_file_name = null;
		if (!globalValidASMap.containsKey(ARG_VERSION)) {
			System.out.println("ERROR: No AS version found for base BE version " + ARG_VERSION);
			return false;
		}
		int AS_COUNT=0;
		File[] list = new File(ARG_INSTALLER_LOCATION).listFiles();
		String regex = "^.*activespaces.*[0-9]\\.[0-9]\\.[0-9]_linux.*\\.zip$";
		if ("win".equals(ARG_PLATFORM)) {
			regex = "^.*activespaces.*[0-9]\\.[0-9]\\.[0-9]_win.*\\.zip$";
		}
		for (File file : list) {
			if (file.getName().matches(regex)) {
				AS_COUNT++;
				as_file_name = file.getName();
			}
		}
		if (AS_COUNT > 1) {
			System.out.println("ERROR: Multiple AS package found for base BE version " + ARG_VERSION + " in the directory : " + ARG_INSTALLER_LOCATION);
			return false;
		} else {
			if (AS_COUNT == 1) {
				ARG_AS_VERSION = as_file_name.split("_")[2];
				if (versionCompare(ARG_AS_VERSION, globalValidASMap.get(ARG_VERSION)) < 0
						|| versionCompare(ARG_AS_VERSION, globalValidASMapMax.get(ARG_VERSION)) > 1){
					System.out.println("ERROR: AS Version:" + ARG_AS_VERSION + " is incompatible with the BE Version:" + ARG_VERSION);
					return false;
				}
			} else {
				System.out.println("WARNING: No AS package found in the directory : " + ARG_INSTALLER_LOCATION);
				return true; // Return true without writing anything to package_files.txt
			}
		}
		
		if (!isArgumentNull(ARG_AS_HF)) {
			int AS_HF_COUNT=0;
			String local_as_hf=ARG_AS_HF;
			if(ARG_AS_HF.length()==1)
				local_as_hf = "00"+local_as_hf;
			if(ARG_AS_HF.length()==2)
				local_as_hf = "0"+local_as_hf;
			File[] listHf = new File(ARG_INSTALLER_LOCATION).listFiles();
			for(File file : listHf){
				if(file.getName().matches("^.*activespaces.*" + ARG_AS_VERSION + "_HF-" + local_as_hf + ".*\\.zip$")){
					AS_HF_COUNT++;
				}
					
			}
			if (AS_HF_COUNT < 1) {
				System.out.println("ERROR: No package found for Activespaces HF in the installer location.");
				return false;
			}
			if(AS_HF_COUNT > 1){
				System.out.println("ERROR: More than one AS HF packages are present in the target directory.There should be only one.");
				return false;
			}
		}
		return true;
	}
	
	private boolean validateVersion() {
		String[] versionArray = ARG_VERSION.split("\\.");
		if(versionArray.length != 3)
			return false;
		String pattern = "^[1-9][0-9]*$";
		String pattern2 = "^[0-9]*$";
	    if(versionArray[0].matches(pattern) && versionArray[1].matches(pattern2) && versionArray[2].matches(pattern2))
	    	return true;
		return false;
	}
	
    private void initializeMaps(){
    	//Valid addons
    	globalValidAddons.put("process", "businessevents-process");
		globalValidAddons.put("views", "businessevents-views");
		globalValidAddons.put("datamodeling", "businessevents-datamodeling");
		globalValidAddons.put("decisionmanager", "businessevents-decisionmanager");
		globalValidAddons.put("eventstreamprocessing", "businessevents-eventstreamprocessing");
		
		//Valid Edition
		globalValidEdition.put("standard", "businessevents-standard");
		globalValidEdition.put("enterprise", "businessevents-enterprise");

		//Valid Addon For EDITION
		globalValidAddOnEdition.put("enterprise", "process views");
		globalValidAddOnEdition.put("standard", "process views datamodeling eventstreamprocessing decisionmanager");
		
		//Valid AS Version Mapping
		globalValidASMap.put("5.3.0", "2.1.6");
		globalValidASMap.put("5.4.0", "2.2.0");
		globalValidASMap.put("5.4.1", "2.2.0");
		globalValidASMap.put("5.5.0", "2.2.0");
		globalValidASMap.put("5.6.0", "2.2.0");
		
		globalValidASMapMax.put("5.3.0", "3.0.0");
		globalValidASMapMax.put("5.4.0", "3.0.0");
		globalValidASMapMax.put("5.4.1", "3.0.0");
		globalValidASMapMax.put("5.5.0", "3.0.0");
		globalValidASMapMax.put("5.6.0", "3.0.0");
    }
    
    public boolean readArguments() throws Exception{
    	ARG_INSTALLER_LOCATION = baseImageConfig.get("installers_location");
    	//ARG_EDITION = baseImageConfig.get("edition");
    	ARG_VERSION = baseImageConfig.get("version");
    	ARG_IMAGE_VERSION = baseImageConfig.get("image_version");
    	if(isArgumentNull(baseImageConfig.get("generateBaseImage")) || isArgumentNull(baseImageConfig.get("useBEHome")) ||isArgumentNull(ARG_INSTALLER_LOCATION) 
    			|| isArgumentNull(ARG_VERSION) || isArgumentNull(ARG_IMAGE_VERSION)){
    		System.out.println("Missing mandatory arguments");
    		printUsage();
    		throw new Exception("Error while building BaseImage");
    	}
    	ARG_ADDONS = baseImageConfig.get("addons");
    	ARG_HF = baseImageConfig.get("hf");
    	ARG_AS_HF = baseImageConfig.get("as_hf");
    	ARG_DOCKERFILE = baseImageConfig.get("dockerfile");
    	ARG_PLATFORM = isArgumentNull(baseImageConfig.get("platform")) ? "linux" : baseImageConfig.get("platform");
    	
    	return true;
    }
    
    public boolean isArgumentNull(String arg) {
    	if(arg=="" || arg==null)
    		return true;
    	else
    		return false;
    }
    
    public void printUsage() {
    	String usage = "\nUsage build-base-image\n"
    			+ "[generateBaseImage]    :       If want to generate base BE image [required]"
    			+ "[useBEHome]            :       If want to generate base image from already installed BE [required]"
    			+ "[installers_location]  :       Location where TIBCO BusinessEvents and TIBCO Activespaces installers are located [required]\n\n"
    			+ "[version]              :       TIBCO BusinessEvents product version (3 part number) [required]\n\n"
    			+ "[image_version]        :       Version to be given to the image ('v01' for example) [required]\n\n"
    			+ "[addons]               :       Comma separated values for required addons : process/views/datamodeling/decisionmanager/eventstreamprocessing [optional] \n\n"
    			+ "[hf]                   :       Additional TIBCO BusinessEvents hotfix version ('1' for example) [optional]\n\n"
    			+ "[as-hf]                :       Additional TIBCO ActiveSpaces hotfix version ('1' for example) [optional]\n\n"
    			+ "[dockerfile]           :       Dockerfile to be used for generating image. [optional]\n\n"
    			+ "[platform]             :       The targeted container platform (valid values - linux, win). The provided installers needs to be of same type (default linux) [optional]\n";
    	System.out.println(usage);
    }
    
    private static void runBaseImageBuildProcess(List<String> command,String dockerExecDirectory) throws Exception {
    	for (String s : command) {
    		System.out.print(s + " ");
    	}
    	System.out.println();
    	ProcessBuilder pro = new ProcessBuilder(command);
		pro.directory(new File(dockerExecDirectory));
		Process p = pro.start();
    	printLines(" stdout:", p.getInputStream());
	    printLines(" stderr:", p.getErrorStream());
	    p.waitFor();	   
	    if (p.exitValue() > 0){
	    	 System.out.println(" exitValue() " + p.exitValue());
	    	 throw new Exception("Error while building BaseImage");
	    }
	    File dockerDirectory = new File(dockerExecDirectory);
	    File fList[] = dockerDirectory.listFiles();
	    for (int i = 0; i < fList.length; i++) {
	    	File  file = fList[i];
	        if (file.getName().endsWith(".zip")) {
	            file.delete();
	        }
	    }
	}
	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(line);
	    }
	}
	public static int versionCompare(String str1, String str2) {
	    String[] vals1 = str1.split("\\.");
	    String[] vals2 = str2.split("\\.");
	    int i = 0;
	    while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
	      i++;
	    }
	    if (i < vals1.length && i < vals2.length) {
	        int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
	        return Integer.signum(diff);
	    }
	    return Integer.signum(vals1.length - vals2.length);
	}
	
	private void printOptimizedImageUsage() {
	    String OptimizedImageUsage = "\nUsage: build-base-image with useBEHome"
	    		+ "\n\n [version]              :       TIBCO BusinessEvents product version (3 part number) [required]"
				+ "\n\n [image_version]        :       Version|tag to be given to the image ('v01' for example) [required]"
				+ "\n\n [be_home]              :       BE_HOME which should be used to generate base image(default will be the one specified in plugin configuration) [optional]"
				+ "\n\n [dockerfile]           :       Dockerfile to be used for generating image.(default Dockerfile.fromtar) [optional]\n";
		System.out.println(OptimizedImageUsage);
	}
	
	public void readOptimizedImageArguments() throws Exception{
		ARG_VERSION = baseImageConfig.get("version");
		BE_PRODUCT_IMAGE_VERSION = baseImageConfig.get("image_version");
		ARG_DOCKERFILE  = baseImageConfig.get("dockerfile");
		BE_HOME = baseImageConfig.get("be_home");
		if (isArgumentNull(ARG_VERSION)
				|| isArgumentNull(BE_PRODUCT_IMAGE_VERSION)) {
			System.out.println("Missing mandatory arguments");
			printOptimizedImageUsage();
			throw new Exception("Error while building BaseImage");
		}
	}
	
	/*private void printOptimizedImageArgumentInfo(){
		String argsInfo = "INFO: Supplied Arguments :\n"
				+ "\n----------------------------------------------"
				+ "\nINFO: VERSION :"+ARG_VERSION
				+ "\nINFO: DOCKERFILE :"+ARG_DOCKERFILE
				+ "\nINFO: IMAGE VERSION :"+BE_PRODUCT_IMAGE_VERSION
				+ "\nINFO: BE_HOME :"+BE_HOME
				+ "\n----------------------------------------------";
		System.out.println(argsInfo);
	}*/
}
