package com.tibco.be.util.docker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.be.util.docker.util.BEDockerUtil;
import com.tibco.cep.cep_commonVersion;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;


public class BEDockerMain {


	private String[] args;
	private String   targetDirectory	="";
	private String   imageName			="";
	private String   maintainer			="";
	private String   email				="";
	private String   labels				="";
	private boolean overwriteDockerfile = false;
	private boolean windowsContainer = false;

	File wdFile =null;
	File cdd    =null;
	File ear    =null;

	private String BE_DOCKER_APP_HOME="/opt/tibco/be/application";
	private String BE_DOCKER_TIBCO_HOME="/opt/tibco";
	protected static final XiParser PARSER = XiParserFactory.newInstance();
	private HashMap<String, String> gvMap=new HashMap<String, String>();
	private HashSet<String> fileVolumes=new HashSet<String>();
	private HashSet<String> excludeGv=new HashSet<String>();


	ExpandedName CONFIG 		= ExpandedName.makeName("config");
	ExpandedName DRIVER_2_0 	= ExpandedName.makeName("driver");
	ExpandedName URL_2_0 		= ExpandedName.makeName("location");
	ExpandedName USERNAME_2_0 	= ExpandedName.makeName("user");
	ExpandedName PASSWORD_2_0 	= ExpandedName.makeName("password");
	ExpandedName POOLSIZE_2_0 	= ExpandedName.makeName("maxConnections");
	ExpandedName LOGINTIMEOUT 	= ExpandedName.makeName("loginTimeout");
	ExpandedName CONNECTIONTYPE = ExpandedName.makeName("connectionType");
	ExpandedName USESHAREDJNDI 	= ExpandedName.makeName("UseSharedJndiConfig");
	ExpandedName USE_SSL	   	= ExpandedName.makeName("useSsl");


	public String usage="Usage : be-docker-gen \n"
			+ "[-t|-target-dir]  :        Target directory which has CDD,EAR,external jars and custom docker files [required]\n\n"
			+ "[-i|-image]       :        Base TIBCO BusinessEvents Docker image version/tag [required] \n\n"
			+ "[-m|-maintainer]  :        Maintainer [required] \n\n"
			+ "[-e|-email]       :        Email [required]\n\n"
			+ "[-l|-label]       :        Label entries.Can be multiple [optional]\n\n"
			+ "[-o]              :        To overwrite existing docker file [optional]\n\n"
			//+ "[-w]              :        Create image for Windows containers (Ensure Base image is also for Windows) [optional]\n\n"
			+ "[-h|-help]        :        Displays this usage [optional]\n";

	public BEDockerMain(String args[]){
		this.args=args;
	}

	public static void main(String[] args) throws Exception {
		BEDockerMain beDockerMain=new BEDockerMain(args);
		boolean result=true;
		if (beDockerMain.parseArgs()) {
			if (beDockerMain.windowsContainer) {
				beDockerMain.prepareForWindowsContainer();
			}
			result=beDockerMain.start();
		}
		if(!result){
			throw new Exception("Error occurred.Aborting");
		}
	}

	private void prepareForWindowsContainer() {
		BE_DOCKER_APP_HOME = "c:\\tibco\\be\\application";
		BE_DOCKER_TIBCO_HOME = "c:\\tibco";
		BEDockerUtil.BE_DOCKERFILE_TEMPLATE = "Dockerfile_win.template";
		BEDockerUtil.BE_DOCKER_CNST_EXT_LIB_PATH = "c:\\tibco\\be\\ext";
		//BEDockerUtil.BE_DOCKER_CNST_EXT_FILE_PATH = "c:\\tibco\\be\\files";
	}

	public boolean parseArgs() {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-t") || args[i].equals("-target-dir")) {
				if (args.length > (i + 1) && !args[i + 1].isEmpty() && !args[i + 1].startsWith("-"))
					targetDirectory = args[i + 1];
				else {
					printIllegalArg("[-t|-target-dir]");
					return false;
				}
			}
			if (args[i].equals("-i") || args[i].equals("-image")) {
				if (args.length > (i + 1) && !args[i + 1].isEmpty() && !args[i + 1].startsWith("-"))
					imageName = args[i + 1];
				else {
					printIllegalArg("[-i|-image]");
					return false;
				}
			}
			if (args[i].equals("-m") || args[i].equals("-maintainer")) {
				if (args.length > (i + 1) && !args[i + 1].isEmpty() && !args[i + 1].startsWith("-"))
					maintainer = args[i + 1];
				else {
					printIllegalArg("[-m|-maintainer]");
					return false;
				}
			}
			if (args[i].equals("-e") || args[i].equals("-email")) {
				if (args.length > (i + 1) && !args[i + 1].isEmpty() && !args[i + 1].startsWith("-")) {
					email = args[i + 1];
					if (!email.matches("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$")) {
						System.out.println("Invalid email-Id \n Error occurred.Aborting");
						return false;
					}
				} else {
					printIllegalArg("[-e|-email]");
					return false;
				}
			}
			if (args[i].equals("-l") || args[i].equals("-label")) {
				if (args.length > (i + 1) && !args[i + 1].isEmpty() && !args[i + 1].startsWith("-"))
					labels = labels + args[i + 1] + ",";
			}
			if (args[i].equals("-help") || args[i].equals("-h")) {
				printUsage();
				return false;
			}
			if (args[i].equals("-o")) {
				overwriteDockerfile = true;
			}
			if (args[i].equals("-w") || args[i].equals("-win")) {
				windowsContainer = true;
			}
		}
		String missingArg = "";
		int first = 1;
		if (targetDirectory == null || targetDirectory.isEmpty()) {
			if (first == 1) {
				missingArg = missingArg + "[-t|-target-dir]";
				first = 0;
			} else {
				missingArg = missingArg + ", [-t|-target-dir]";
			}
		}

		if (imageName == null || imageName.isEmpty()) {
			if (first == 1) {
				missingArg = missingArg + "[-i|-image]";
				first = 0;
			} else {
				missingArg = missingArg + ", [-i|-image]";
			}
		}

		if (maintainer == null || maintainer.isEmpty()) {
			if (first == 1) {
				missingArg = missingArg + "[-m|-maintainer]";
				first = 0;
			} else {
				missingArg = missingArg + ", [-m|-maintainer]";
			}
		}

		if (email == null || email.isEmpty()) {
			if (first == 1) {
				missingArg = missingArg + "[-e|-email]";
				first = 0;
			} else {
				missingArg = missingArg + ", [-e|-email]";
			}
		}

		if (!missingArg.equals("")) {
			printMissingArg(missingArg);
			return false;
		}
		
		try {
			if (!windowsContainer) {// '-w' not specified, try to identify from image.
				String baseImageOs = identifyImageOs(imageName);
				windowsContainer = baseImageOs != null && baseImageOs.toLowerCase().contains("windows");
			}
		} catch (Exception e) {
			windowsContainer = false;
		}

		return true;
	}

	public static String getDockerCommand() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			return "/usr/local/bin/docker";
		} else {
			return "docker";
		}
	}

	/**
	 * Inspects the image and identifies its Os name.
	 * @param imageName
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static String identifyImageOs(String imageName) throws InterruptedException, IOException {
		String dockerCommand = getDockerCommand();
		String[] imageOsCommand = { dockerCommand, "inspect", "--format={{.Os}}", "com.tibco.be:" + imageName };
		Process imageOsCommanPro = new ProcessBuilder(Arrays.asList(imageOsCommand)).start();
		InputStream is = imageOsCommanPro.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = in.readLine();
		imageOsCommanPro.waitFor();
		return line;
	}

	private void printIllegalArg(String string) {
		System.out.println("Illegal argument found for switch ::  "+string);
		printUsage();
	}

	private void printMissingArg(String string) {
		System.out.println("ERROR: Missing mandatory argument(s) :  "+string);
		printUsage();
	}

	public boolean start(){
		//start processing
		printStart();

		//perform validation
		boolean validatioResult=validate();
		if(!validatioResult)
			return false;
		
		if (windowsContainer) {
			System.out.println("Generating Docker file for Windows Container ... ");
		} else {
			System.out.println("Generating Docker file ... ");
		}
		
		String dockerCommand = getDockerCommand();
		String[] listDockerImagesCommand = {dockerCommand, "images"};
		try {
			Process listDockerImagesCommanPro;
			boolean imageExists=false;
			listDockerImagesCommanPro = new ProcessBuilder(Arrays.asList(listDockerImagesCommand)).start();
			InputStream is = listDockerImagesCommanPro.getInputStream();
			String line = null;
		    BufferedReader in = new BufferedReader(new InputStreamReader(is));
		    while ((line = in.readLine()) != null) {
		    	if(line.contains(imageName)){
		    		imageExists = true;
					break;
				}
		    }
		    listDockerImagesCommanPro.waitFor();
		    if(!imageExists)
		    	throw new Exception("Base Image does not exists");
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		

		//read template file and replace tokens
		StringBuilder dockerfileText = BEDockerUtil.getDockerFileText(imageName, maintainer, email, cdd.getName(),
				ear.getName(), BE_DOCKER_APP_HOME, BE_DOCKER_TIBCO_HOME);

		//Adding labels
		addLabelsToDockerfile(labels,dockerfileText);
		//Put entries for adding custom jars in dockerfile
		updateCustomJarEntries(dockerfileText);
		
		updateCustomSoFilesEntries(dockerfileText);

		//Get global variables from EAR File
		try {
			System.out.println("Fetching GVs from Ear ...");
			gvMap=BEDockerUtil.getGlobalVariables(ear.getAbsolutePath());


			//read CDD file
			ClusterConfig cddConfig=getCddConfig(cdd.getAbsolutePath());

			//Updating JDBC Shared resource
			updateDBSharedResource(cddConfig,dockerfileText);

			HashMap<String,String> gvClone=(HashMap<String, String>) gvMap.clone();
			if(cddConfig==null){
				System.out.println("Unable to read cdd file");
				return false;
			}
			Properties cddProperties=(Properties) cddConfig.getPropertyGroup().toProperties();

			for(Entry<String,String> entry:gvClone.entrySet()){
				Object cddValue=cddProperties.getProperty("tibco.clientVar."+entry.getKey());
				if(cddValue!=null && cddValue instanceof String && !((String)cddValue).isEmpty()){
					gvMap.put(entry.getKey(), ((String)cddValue));
				}
			}

			//Update files
			StringBuilder fileText=updateFileVariables();

			ArrayList<String> ports=new ArrayList<String>();
			ArrayList<String> volumes=new ArrayList<String>();
			System.out.println("Updating Environment entries in Dockerfile ...");
			dockerfileText.append("\n");
			dockerfileText.append("\n#Application's environment variables. Uncomment it if a value needs to be changed.");
			for (Entry<String, String> entry : gvMap.entrySet()) {
				if (entry.getValue() != null && !excludeGv.contains(entry.getKey())) {
					if (entry.getValue().isEmpty()) {
						dockerfileText.append("\n# ENV " + entry.getKey() + " \"\"");
					} else {
						dockerfileText.append("\n# ENV " + entry.getKey() + " " + entry.getValue());
					}
					if (entry.getKey().toUpperCase().matches("(.*)_PORT") && BEDockerUtil.isNumeric(entry.getValue())) {
						ports.add(entry.getValue());
					}
					if (entry.getKey().toUpperCase().matches("(.*)_PATH") && !entry.getValue().isEmpty()) {
						volumes.add(entry.getValue());
					}
				}
			}

			//Adding ports entry
			String portsStr="";
			for(String port : ports){
				portsStr=portsStr+port+" ";
			}
			if(!portsStr.isEmpty()){
				dockerfileText.append("\n\n#Exposing ports");
				dockerfileText.append("\nEXPOSE "+portsStr);
			}

			int first=1;
			//Adding mkdir entries
			for(String path : fileVolumes){
				if(first==1){
					dockerfileText.append("\n\n#Making directories");
					first=0;
				}
				dockerfileText.append("\nRUN mkdir -p "+path);
			}

			//Add file entries
			dockerfileText.append(fileText);

			//Adding volume entry
			String volumeStr="";
			for(String path : volumes){
				volumeStr=volumeStr+path+" ";
			}
			//Adding file volumes
			for(String path : fileVolumes){
				volumeStr=volumeStr+path+" ";
			}
			if(!volumeStr.isEmpty()){
				dockerfileText.append("\n\n#Exposing volumes");
				dockerfileText.append("\nVOLUME "+volumeStr);
			}


		} catch (Exception e) {
			System.out.println("ERROR:Exception occurred while fetching Gloable Variables from EAR");
			e.printStackTrace();
		}

		updateDockerWithCustomFile(dockerfileText);

		writeDockerfile(dockerfileText);

		return true;
	}


	private StringBuilder updateFileVariables() {
		int first=1;
		StringBuilder fileText=new StringBuilder("");
		for(Entry<String,String> entry:gvMap.entrySet()){
			if(entry.getKey().toUpperCase().matches("(.*)_FILE")){

				if(first==1){
					fileText.append("\n\n#Adding external files");
					first=0;
				}
				File externalFile=new File(entry.getValue());
				File filesFolderFile=new File(targetDirectory+File.separator+BEDockerUtil.BE_DOCKER_CNST_FILES_FOLDER+File.separator+externalFile.getName());
				if(filesFolderFile.exists()){
					String path = externalFile.getParent();
					if(path!=null){
						path=BEDockerUtil.transformPath(externalFile.getAbsolutePath());
						path=path.trim().equals("")?BEDockerUtil.BE_DOCKER_CNST_EXT_FILE_PATH:path.substring(0,path.lastIndexOf("/"));
					}
					else{
						path=BEDockerUtil.BE_DOCKER_CNST_EXT_FILE_PATH;
					}

					fileVolumes.add(path);

					fileText.append("\nADD "+BEDockerUtil.BE_DOCKER_CNST_FILES_FOLDER+"/"+filesFolderFile.getName()+" "+path);
					//updating GV value
					gvMap.put(entry.getKey(), path+"/"+externalFile.getName());
				}
				else{
					System.out.println("ERROR: The file : " + externalFile.getName() + " as referenced in the Global Variables not found in the files directory");
				}
			}
		}
		return fileText;
	}

	private void addLabelsToDockerfile(String labels, StringBuilder dockerfileText) {
		if(labels.isEmpty())
			return;
		String[] labelsArray=labels.split(",");
		String sum="";
		for(String label:labelsArray){
			sum=sum+label+" ";	
		}
		dockerfileText.append("\n\n"+"#Application's Labels");
		dockerfileText.append("\n"+"LABEL "+sum);
	}

	private void updateCustomJarEntries(StringBuilder dockerfileText) {
		File[] customJars=BEDockerUtil.getMatchingFiles(targetDirectory, "[^\\s]+\\.jar");
		if(customJars!=null&&customJars.length>0){
			dockerfileText.append("\n");
			dockerfileText.append("\n#External Jars");
			for(File jarFile : customJars){	
				dockerfileText.append("\n" + "ADD " + jarFile.getName() + " \"" + BEDockerUtil.BE_DOCKER_CNST_EXT_LIB_PATH + "\"");
			}
		}
	}
	
	private void updateCustomSoFilesEntries(StringBuilder dockerfileText) {
		File[] customJars1=BEDockerUtil.getMatchingFiles(targetDirectory, "[^\\s]+\\.so");
		File[] customJars2=BEDockerUtil.getMatchingFiles(targetDirectory, "[^\\s]+\\.so\\..*");
		File[] customJars=BEDockerUtil.concatArrays(customJars1, customJars2);
		if(customJars!=null&&customJars.length>0){
			dockerfileText.append("\n");
			dockerfileText.append("\n#External Libraries");
			for(File jarFile : customJars){	
				dockerfileText.append("\n" + "ADD " + jarFile.getName() + " \"" + BEDockerUtil.BE_DOCKER_CNST_EXT_LIB_PATH + "\"");
			}
		}
	}

	private void updateDBSharedResource(ClusterConfig cddConfig, StringBuilder dockerfileText) {

		if(cddConfig==null)
			return;

		if(cddConfig.getObjectManagement().getCacheManager()==null)
			return;

		String backingStoreOption=(String) cddConfig.getObjectManagement().getCacheManager().getBackingStore().getPersistenceOption().getMixed().get(0).getValue();
		if(BEDockerUtil.BE_DOCKER_CNST_SHARED_ALL.equals(backingStoreOption)){
			String uri=cddConfig.getObjectManagement().getCacheManager().getBackingStore().getPrimaryConnection().getUri();
			uri=BEDockerUtil.substituteGlobalVariables(gvMap, uri);
			updateSharedResource(uri,dockerfileText);
		}
	}

	private void updateSharedResource(String uri, StringBuilder dockerfileText) {

		try{
			String unzipLoc=targetDirectory+File.separator+BEDockerUtil.getFileName(ear);
			
			//backing up ear
			Files.copy(ear.toPath(),new File(ear.getAbsolutePath()+".bkp").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			
			//Unzip Enterprise archive
			BEDockerUtil.unzip(ear,new File(unzipLoc));

			//Update Shared Resource
			updateSharedResourceWithGVs(unzipLoc+File.separator+"Shared Archive",uri,dockerfileText);

			//Zip Shared Archive
			BEDockerUtil.zipArchive(new File(unzipLoc+File.separator+"Shared Archive"), new File(unzipLoc+File.separator+"Shared Archive2.sar"));


			//perform cleanup
			BEDockerUtil.deleteDirectory(new File(unzipLoc+File.separator+"Shared Archive"));

			Files.move(new File(unzipLoc+File.separator+"Shared Archive2.sar").toPath(), new File(unzipLoc+File.separator+"Shared Archive.sar").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			BEDockerUtil.zipArchive(new File(unzipLoc), new File(ear.getAbsolutePath()+"2"));
			Files.move(new File(ear.getAbsolutePath()+"2").toPath(), ear.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			BEDockerUtil.deleteDirectory(new File(unzipLoc));
		}
		catch(Exception e){
			System.out.println("ERROR:Exception occurred while reading ear file"+ear.getAbsolutePath());
			e.printStackTrace();
		}
	} 	

	private void updateSharedResourceWithGVs(String sharedArchivePath, String uri, StringBuilder dockerfileText) {

		String sharedResourcePath=sharedArchivePath+File.separator+uri;
		String sharedResourcePathOrig=sharedResourcePath;
		

		File file=new File(sharedArchivePath+File.separator+uri);
		File orignalFile=new File(sharedResourcePath+".bkp.orig");
		
		try {
			if(orignalFile.exists()){
				Files.copy(file.toPath(), new File(sharedResourcePath+".bkp").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				sharedResourcePath=sharedResourcePath+".bkp.orig";
			}else{
				Files.copy(file.toPath(), new File(sharedResourcePath+".bkp.orig").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
			
		} catch (IOException e) {
			System.out.println("ERROR:Unable to rename shared resource");
			e.printStackTrace();
		}

		byte[] bytes=BEDockerUtil.getResourceAsBytes(sharedResourcePath);
		InputStream is =null;
		InputSource source=null;
		XiNode node=null;
		try{
		is=new ByteArrayInputStream(bytes);
		source = new InputSource(is);
		node = PARSER.parse(source);
		} catch (SAXException | IOException e) {
			System.out.println("ERROR:Unable to parse shared resource");
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		XiNode jdbcChannelNode = XiChild.getChild(node.getFirstChild(), CONFIG);
		
		String driver      = XiChild.getString(jdbcChannelNode,DRIVER_2_0),
			   url         = XiChild.getString(jdbcChannelNode,URL_2_0),
		       username    = XiChild.getString(jdbcChannelNode,USERNAME_2_0),
		       password    = XiChild.getString(jdbcChannelNode,PASSWORD_2_0),
		       poolsize    = XiChild.getString(jdbcChannelNode,POOLSIZE_2_0),
		       logintimeout= XiChild.getString(jdbcChannelNode,LOGINTIMEOUT),
		       useSsl      = XiChild.getString(jdbcChannelNode,USE_SSL);
		    		   
		
		addToExcludes(driver,url,username,password,poolsize,logintimeout,useSsl);
		
		String jdbcDriverParsed = BEDockerUtil.substituteGlobalVariables(gvMap,driver).toString();
		String jdbcurlParsed = BEDockerUtil.substituteGlobalVariables(gvMap,url).toString();
		String usernameParsed = BEDockerUtil.substituteGlobalVariables(gvMap,username).toString();
		String passwordParsed = BEDockerUtil.substituteGlobalVariables(gvMap,password).toString();
		String poolSizeParsed = BEDockerUtil.substituteGlobalVariables(gvMap,poolsize).toString();
		String loginTimeoutParsed = BEDockerUtil.substituteGlobalVariables(gvMap,logintimeout).toString();
		String useSslParsed = BEDockerUtil.substituteGlobalVariables(gvMap,useSsl).toString();

		dockerfileText.append("\n\n#Shared Resource Environment Variables");

		if(jdbcDriverParsed!=null&&!jdbcDriverParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_DRIVER+" "+jdbcDriverParsed);
		if(jdbcurlParsed!=null&&!jdbcurlParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_URL+" "+jdbcurlParsed);
		if(usernameParsed!=null&&!usernameParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_USERNAME+" "+usernameParsed);
		if(passwordParsed!=null&&!passwordParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_PASSWORD+" "+passwordParsed);
		if(poolSizeParsed!=null&&!poolSizeParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_POOL_SIZE+" "+poolSizeParsed);
		if(loginTimeoutParsed!=null&&!loginTimeoutParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_LOGIN_TIMEOUT+" "+loginTimeoutParsed);
		if(useSslParsed!=null&&!useSslParsed.isEmpty())
			dockerfileText.append("\nENV "+BEDockerUtil.BE_DOCKER_ENV_JDBC_USE_SSL+" "+useSslParsed);


		XiChild.setString(jdbcChannelNode, DRIVER_2_0, "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_DRIVER+"%%");
		XiChild.setString(jdbcChannelNode, URL_2_0, "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_URL+"%%");
		XiChild.setString(jdbcChannelNode, USERNAME_2_0, "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_USERNAME+"%%");
		XiChild.setString(jdbcChannelNode, PASSWORD_2_0, "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_PASSWORD+"%%");
		XiChild.setString(jdbcChannelNode, POOLSIZE_2_0, "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_POOL_SIZE+"%%");
		XiChild.setString(jdbcChannelNode, LOGINTIMEOUT, "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_LOGIN_TIMEOUT+"%%");
		XiChild.setString(jdbcChannelNode, ExpandedName.makeName("useSsl"), "%%"+BEDockerUtil.BE_DOCKER_ENV_JDBC_USE_SSL+"%%");

		if(node!=null){
			FileOutputStream fos = null;
			BufferedWriter bufWriter = null;

			try {
				fos = new FileOutputStream(sharedResourcePathOrig);

				bufWriter = new BufferedWriter(new OutputStreamWriter(fos,
						"UTF-8"));
				DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(bufWriter, "UTF-8");
				node.serialize(handler);

			} catch (FileNotFoundException e) {
				System.out.println("ERROR:Exception occurred while updating Shared Resource");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				System.out.println("ERROR:Exception occurred while updating Shared Resource");
				e.printStackTrace();
			} catch (SAXException e) {
				System.out.println("ERROR:Exception occurred while updating Shared Resource");
				e.printStackTrace();
			}
			finally{
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void addToExcludes(String... values){
		for(String value:values)
		if(BEDockerUtil.isGlobalVariable(value)){
			excludeGv.add(BEDockerUtil.getGlobalVariableName(value));
		}
	}



	private void writeDockerfile(StringBuilder dockerfileText) {

		//Backup any Dockerfile if it already exist in Target Directory
		if(!overwriteDockerfile)
			backupIfExists(targetDirectory,targetDirectory+File.separator+BEDockerUtil.BE_DOCKERFILE,BEDockerUtil.BE_DOCKERFILE);

		System.out.println("Writing Dockerfile ...");

		File dockerFile=new File(targetDirectory+File.separator+BEDockerUtil.BE_DOCKERFILE);

		try {
			dockerFile.createNewFile();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(dockerFile))) {
				writer.append(dockerfileText);
			}

		} catch (IOException e) {
			System.out.println("ERROR:Exception occurred while writing Dcokerfile");
			e.printStackTrace();
		}

		System.out.println("Dockerfile created at location: " + dockerFile.getAbsolutePath());

		System.out.println("Processing finished.");

	}

	private void backupIfExists(String targetDirectory,String fileName, String nameOfFile) {
		File file=new File(fileName);
		int number=1;
		if(file.exists()){
			System.out.println("Backingup existing file ...");
			File[] files=BEDockerUtil.getMatchingFiles(targetDirectory, nameOfFile+".([0-9])*");
			if(files.length>0){
				files=BEDockerUtil.sortFiles(files);
				String name=files[files.length-1].getName();
				number = Integer.parseInt(name.substring((name.indexOf('.')+1), name.length()));
			}
			File renamedFile=new File(targetDirectory,nameOfFile+"."+(++number));
			try {
				Files.move(file.toPath(), renamedFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {

			}
		}
	}


	private void updateDockerWithCustomFile(StringBuilder dockerfileText) {
		File[] customFiles=BEDockerUtil.getMatchingFiles(targetDirectory, "[^\\s]+\\.custom");
		System.out.println("Checking if Custom docker file present ...");
		for(File file : customFiles){
			StringBuilder fileText=BEDockerUtil.readFile(file);
			dockerfileText.append("\n\n"+fileText);
		}
	}

	public ClusterConfig getCddConfig(String cddPath) {
		File cddFile = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			cddFile = File.createTempFile(BEDockerUtil.BE_DOCKER_CNST_TEMP_CDD, ".cdd");
			String version = cep_commonVersion.version.replaceAll(".[0-9]$", "");
			br = new BufferedReader(new FileReader(cddPath));
			bw = new BufferedWriter(new FileWriter(cddFile));
			String line = null;
			while (null != (line = br.readLine())) {
				if (line.trim().startsWith("<cluster")) {
					line = line.substring(0, line.lastIndexOf("/") + 1);
					line += version + "\">";
				}
				bw.write(line);
			}
		} catch (IOException e1) {

		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}

			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}

		ClusterConfig clusterConfig = null;
		if (null != cddFile) {

			ClusterConfigFactory factory = new ClusterConfigFactory();

			try {
				clusterConfig = factory.newConfig(cddFile.getAbsolutePath());
				return clusterConfig;
			} catch (IllegalArgumentException | IOException e) {
			}
			cddFile.delete();
		}
		return null;

	}



	private boolean validate() {

		File wd=new File(targetDirectory);

		//Validating working directory
		if(!wd.exists() || !wd.isDirectory()){
			System.out.println("The target directory does not exist or not a directory");
			return false;
		}
		else
			wdFile=wd;

		//Validating presence of EAR File
		File[] ears=BEDockerUtil.getMatchingFiles(targetDirectory, "[^\\s]+\\.ear");

		if(ears==null||ears.length==0){
			System.out.println("No EAR file found in the target directory.");
			return false;
		}
		else if(ears.length>1){
			System.out.println("Multiple EARs found.Make sure there is only single EAR file present in the target directory");
			return false;
		}
		else if(ears.length==1){
			ear=ears[0];
		}

		//Validating presence of CDD File
		File[] cdds=BEDockerUtil.getMatchingFiles(targetDirectory, "[^\\s]+\\.cdd");

		if(cdds==null||cdds.length==0){
			System.out.println("No CDD file found in the target directory.");
			return false;
		}
		else if(cdds.length>1){
			System.out.println("Multiple CDDs found.Make sure there is only single CDD file present in the target directory");
			return false;
		}
		else if(cdds.length==1){
			cdd=cdds[0];
		}

		return true;
	}

	private void printStart() {
		System.out.println("------------------------------------------\nGenerating Dockerfile with following arguments : \n"
				+ "Target Directory " + targetDirectory + "\n"
				+ "Image Name : " + imageName + "\n"
				+ "Maintainer : " + maintainer + "\n"
				+ "Email : " + email + "\n"
				+ "------------------------------------------");

	}

	public void printUsage(){
		System.out.println(usage);
	}


	public void stop(){
		System.out.println("Shutting down BE Docker");
	}
}
