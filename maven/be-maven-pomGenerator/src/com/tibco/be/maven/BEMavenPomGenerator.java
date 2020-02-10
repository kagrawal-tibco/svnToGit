package com.tibco.be.maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.tibco.be.maven.version.cep_pomGeneratorVersion;
public class BEMavenPomGenerator {
	public static void main(String[] args) {
		BEMavenPomGenerator BEPomGenerator = new BEMavenPomGenerator();
		if(args.length < 8 || args.length > 8){
			BEPomGenerator.printUsage();
			return;
    	}
		Map<String,String> projectArgs = BEPomGenerator.parseArguments(args);
		if(projectArgs==null)
			return;
		String pomFilePath = projectArgs.get("projectPath") + "/pom.xml";
		boolean overwritePOMFile = false;
		if (new File(pomFilePath).exists()) {
			if (!overwritePOMFile) {
				overwritePOMFile =readYesNo("The POM file already exists.\nDo you want to overwrite?(y/n)");
				if(!overwritePOMFile) {
					return;
				}
			}
		}
		BEPomGenerator.generatePOM(projectArgs);
	}
	public void printUsage(){
		System.out.println("\nUsage: be-maven-pom-generator -p <projectPath> -g <groupId> -a <artifactId>  -v <version>");
		System.out.println("\nWhere:\n");
		System.out.println("-p <projectPath> :\t Path of a project for which POM will be generated\n");
		System.out.println("-g <groupId>     :\t Group Id for the project \n");
		System.out.println("-a <artifactId>  :\t Artifact Id for the project \n");
		System.out.println("-v <version>     :\t Version for the EAR file  \n");
	}
	public Map<String,String> parseArguments(String[] args){
		 Map<String,String> projectArgs = new HashMap<String,String>();
		 List<String> argsList = new ArrayList<>();
		 int i=0;
	     while(i < args.length){
	         if (args[i].compareTo("-p")==0 && !argsList.contains("-p")){
	             projectArgs.put("projectPath", args[i+1]);
	             argsList.add("-p");
	             i += 2;
	         }else if (args[i].compareTo("-g")==0 && !argsList.contains("-g")){
	        	 projectArgs.put("groupId", args[i+1]);
	        	 argsList.add("-g");
	             i += 2;
	         }else if (args[i].compareTo("-a")==0 && !argsList.contains("-a")){
	        	 projectArgs.put("artifactId", args[i+1]);
	        	 argsList.add("-a");
	             i += 2;
	         }else if (args[i].compareTo("-v")==0 && !argsList.contains("-v")){
	        	 projectArgs.put("version", args[i+1]);
	        	 argsList.add("-v");
	             i += 2;
	         }else{
	        	 printUsage();
	        	 return null;
	         } 
	      }
		return projectArgs;
	}
    public void generatePOM(Map<String,String> args){
    	
    	String be_home = "";
    	if( System.getProperty("BE_HOME") != "" && System.getProperty("BE_HOME")!= null){
    		be_home = System.getProperty("BE_HOME");
    	}else
    		be_home = System.getenv("BE_HOME");
    	
    	String projectPath = args.get("projectPath");
		projectPath = projectPath.replace('\\', '/');
		List<String> projectLibList = new ArrayList<String>();
		List<String> thirdPartyJarList = new ArrayList<String>();
		
		/***** Check if project contains projectLib dependencies and add them to POM.xml file *****/
		try{
			
			File fXmlFile = new File(projectPath+"/.project");
			if(!fXmlFile.exists()){
				System.out.println("ERROR: .project not found at :" + projectPath+"/.project");
				return;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			try {
				Document projectFile = dBuilder.parse(fXmlFile);
				projectFile.getDocumentElement().normalize();
				NodeList projectLibNodeList = projectFile.getElementsByTagName("link");
				for (int temp = 0; temp < projectLibNodeList.getLength(); temp++) {

					Node nNode = projectLibNodeList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String[] projectLibNameSplit = eElement.getElementsByTagName("locationURI").item(0).getTextContent().split("projlib:/file:/");
						projectLibList.add(projectLibNameSplit[1]);
					}
				}
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/***** Check if project contains third party jar dependencies and add them to POM.xml file *****/
			File fXmlProjectFile = new File(projectPath+"/.beproject");
			if(!fXmlProjectFile.exists()){
				System.out.println("ERROR: .beproject not found at :" + projectPath+"/.beproject");
				return;
			}
			DocumentBuilderFactory dbProjectFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dProjectBuilder = dbProjectFactory.newDocumentBuilder();
			try {
				Document projectFile = dProjectBuilder.parse(fXmlProjectFile);
				projectFile.getDocumentElement().normalize();
				NodeList projectLibNodeList = projectFile.getElementsByTagName("thirdpartyLibEntries");
				for (int temp = 0; temp < projectLibNodeList.getLength(); temp++) {
					Node nNode = projectLibNodeList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String thirdPartyJarPath = eElement.getAttribute("path");
						thirdPartyJarList.add(new File(thirdPartyJarPath).getName().substring(0,new File(thirdPartyJarPath).getName().lastIndexOf(".")));
					}
				}
				NodeList customJarList = projectFile.getElementsByTagName("customFunctionLibEntries");
				for (int temp = 0; temp < customJarList.getLength(); temp++) {
					Node nNode = customJarList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String customJarPath = eElement.getAttribute("path");
						thirdPartyJarList.add(new File(customJarPath).getName().substring(0,new File(customJarPath).getName().lastIndexOf(".")));
					}
				}
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			File file = new File(projectPath+"/target");
			if (!file.exists()) 
	            file.mkdir();
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("project");
			doc.appendChild(rootElement);
			
			Element modelVersion = doc.createElement("modelVersion");
			modelVersion.appendChild(doc.createTextNode("4.0.0"));
			rootElement.appendChild(modelVersion);
			
			Element groupId = doc.createElement("groupId");
			groupId.appendChild(doc.createTextNode(args.get("groupId")));
			rootElement.appendChild(groupId);
			
			Element artifactId = doc.createElement("artifactId");
			artifactId.appendChild(doc.createTextNode(args.get("artifactId")));
			rootElement.appendChild(artifactId);
			
			
			Element version = doc.createElement("version");
			version.appendChild(doc.createTextNode(args.get("version")));
			rootElement.appendChild(version);
			
			
			Element packaging = doc.createElement("packaging");
			packaging.appendChild(doc.createTextNode("ear"));
			rootElement.appendChild(packaging);
			
			Element build = doc.createElement("build");
			rootElement.appendChild(build);
			
			Element plugins = doc.createElement("plugins");
			build.appendChild(plugins);
			
			Element plugin = doc.createElement("plugin");
			plugins.appendChild(plugin);
			Element pluginGroupId = doc.createElement("groupId");
			pluginGroupId.appendChild(doc.createTextNode("com.tibco.be.maven.plugin"));
			plugin.appendChild(pluginGroupId);
			Element pluginArtifactId = doc.createElement("artifactId");
			pluginArtifactId.appendChild(doc.createTextNode("be-maven-plugin"));
			plugin.appendChild(pluginArtifactId);
			Element pluginVersion = doc.createElement("version");
			pluginVersion.appendChild(doc.createTextNode(cep_pomGeneratorVersion.version));
			plugin.appendChild(pluginVersion);
			Element extensions = doc.createElement("extensions");
			extensions.appendChild(doc.createTextNode("true"));
			plugin.appendChild(extensions);
			Element configuration = doc.createElement("configuration");
			plugin.appendChild(configuration);
			Element beProjectDetails = doc.createElement("beProjectDetails");
			configuration.appendChild(beProjectDetails);
			Element beHome = doc.createElement("beHome");
			beHome.appendChild(doc.createTextNode(be_home));
			beProjectDetails.appendChild(beHome);
			Element jdkHome = doc.createElement("jdkHome");
			jdkHome.appendChild(doc.createTextNode(""));
			beProjectDetails.appendChild(jdkHome);
			Element earLocation = doc.createElement("earLocation");
			earLocation.appendChild(doc.createTextNode("target"));
			beProjectDetails.appendChild(earLocation);
			Element projLibLocation = doc.createElement("projLibLocation");
			projLibLocation.appendChild(doc.createTextNode("target"));
			beProjectDetails.appendChild(projLibLocation);
			
			/*Element projectLibs = doc.createElement("projectLibs");
			projectLibs.appendChild(doc.createTextNode(""));
			configuration.appendChild(projectLibs);
			
			if(projectLibList.size()>0){
				for(int i=0 ; i< projectLibList.size() ; i++){
					Element projectLib = doc.createElement("projectLib");
					projectLib.appendChild(doc.createTextNode(projectLibList.get(i)));
					projectLibs.appendChild(projectLib);
				}
			}*/
			
			Element projLibResources = doc.createElement("projLibResources");
			projLibResources.appendChild(doc.createTextNode(""));
			configuration.appendChild(projLibResources);
			
			Element teaConfig = doc.createElement("teaConfig");
			configuration.appendChild(teaConfig);
			Element teaUrl = doc.createElement("teaUrl");
			teaUrl.appendChild(doc.createTextNode("http://localhost:8777"));
			teaConfig.appendChild(teaUrl);
			Element applicationName = doc.createElement("applicationName");
			applicationName.appendChild(doc.createTextNode(""));
			teaConfig.appendChild(applicationName);
			Element username = doc.createElement("username");
			username.appendChild(doc.createTextNode("admin"));
			teaConfig.appendChild(username);
			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode("admin"));
			teaConfig.appendChild(password);
			
			//Configuration to generate the application image
			
			Element appImageConfig = doc.createElement("appImageConfig");//Base tag
			configuration.appendChild(appImageConfig);
			
			Element useBEHome = doc.createElement("useBEHome");
      		useBEHome.appendChild(doc.createTextNode("true"));//Flag to use  given be home
      		appImageConfig.appendChild(useBEHome);
      		Element installationLocation = doc.createElement("be_home");//BE home to be used
      		installationLocation.appendChild(doc.createTextNode(""));
      		appImageConfig.appendChild(installationLocation);   	
      		
			Element cddFileLocation = doc.createElement("cddFileLocation");
			cddFileLocation.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(cddFileLocation);
			
			Element installerLocation = doc.createElement("installers_location");//installers_location 
			installerLocation.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(installerLocation);
			
			
			Element appLocation = doc.createElement("targetDir");//Location of application files
			appLocation.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(appLocation);			
			
			
			Element repo = doc.createElement("appImage");//The application image repo name like repo:tag. If 
			repo.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(repo);
			
			Element dockerFile = doc.createElement("dockerfile");// Path of the docker file to be used
			dockerFile.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(dockerFile);
			
			
			
			/*Element baseBEImage = doc.createElement("baseBEImage");
			baseBEImage.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(baseBEImage);
			Element maintainer = doc.createElement("maintainer");
			maintainer.appendChild(doc.createTextNode(args.get("artifactId")));
			appImageConfig.appendChild(maintainer);
			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(email);
			Element labels = doc.createElement("labels");
			labels.appendChild(doc.createTextNode("ApplicationName="+args.get("artifactId")));
			appImageConfig.appendChild(labels);
			Element appImage = doc.createElement("appImage");
			appImage.appendChild(doc.createTextNode(""));
			appImageConfig.appendChild(appImage);
			Element overwriteDockerfile = doc.createElement("overwriteDockerfile");
			overwriteDockerfile.appendChild(doc.createTextNode("false"));
			appImageConfig.appendChild(overwriteDockerfile);*/
			
			Element dockerRegistryConfig = doc.createElement("dockerRegistryConfig");
      		configuration.appendChild(dockerRegistryConfig);
      		Element prop1 = doc.createElement("property");
      		dockerRegistryConfig.appendChild(prop1);
      		Element key1 = doc.createElement("name");
      		key1.appendChild(doc.createTextNode("Repository"));
      		prop1.appendChild(key1);
      		Element value1 = doc.createElement("value");
      		value1.appendChild(doc.createTextNode(""));
      		prop1.appendChild(value1);
      		
      		Element prop2 = doc.createElement("property");
      		dockerRegistryConfig.appendChild(prop2);
      		Element key2 = doc.createElement("name");
      		key2.appendChild(doc.createTextNode("Url"));
      		prop2.appendChild(key2);
      		Element value2 = doc.createElement("value");
      		value2.appendChild(doc.createTextNode(""));
      		prop2.appendChild(value2);
      		
      		Element prop3 = doc.createElement("property");
      		dockerRegistryConfig.appendChild(prop3);
      		Element key3 = doc.createElement("name");
      		key3.appendChild(doc.createTextNode("TagImage"));
      		prop3.appendChild(key3);
      		Element value3 = doc.createElement("value");
      		value3.appendChild(doc.createTextNode(""));
      		prop3.appendChild(value3);
			
      		/*
      		Element baseImageConfig = doc.createElement("baseImageConfig");
      		configuration.appendChild(baseImageConfig);
      		Element generateBaseImage = doc.createElement("generateBaseImage");
      		generateBaseImage.appendChild(doc.createTextNode("false"));
      		baseImageConfig.appendChild(generateBaseImage);
      		Element useBEHome = doc.createElement("useBEHome");
      		useBEHome.appendChild(doc.createTextNode("true"));
      		baseImageConfig.appendChild(useBEHome);
      		Element installationLocation = doc.createElement("be_home");
      		installationLocation.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(installationLocation);   		
      		Element installers_location = doc.createElement("installers_location");
      		installers_location.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(installers_location);
      		Element beversion = doc.createElement("version");
      		beversion.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(beversion);
      		Element image_version = doc.createElement("image_version");
      		image_version.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(image_version);
      		Element addons = doc.createElement("addons");
      		addons.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(addons);
      		Element hf = doc.createElement("hf");
      		hf.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(hf);
      		Element as_hf = doc.createElement("as_hf");
      		as_hf.appendChild(doc.createTextNode(""));
      		baseImageConfig.appendChild(as_hf);
      		Element dockerfile = doc.createElement("dockerfile");
      		dockerfile.appendChild(doc.createTextNode("")); //Leave dockerfile tag blank, so that default is picked depending on 'useBEHome' and execution env.
      		baseImageConfig.appendChild(dockerfile);
      		Element platform = doc.createElement("platform");
      		platform.appendChild(doc.createTextNode("linux"));
      		baseImageConfig.appendChild(platform);*/
//      		Comment comment = doc.createComment("valid values for platform - win, linux");
//      		baseImageConfig.appendChild(comment);
			
			Element projectDependencies = doc.createElement("dependencies");
			rootElement.appendChild(projectDependencies);
			if(thirdPartyJarList.size() > 0){
				for(int i=0;i<thirdPartyJarList.size();i++){
					Element thirdPartyJar = doc.createElement("dependency");
					thirdPartyJar.appendChild(doc.createTextNode(""));
					
					Element thirdPartyJarGroupId = doc.createElement("groupId");
					thirdPartyJarGroupId.appendChild(doc.createTextNode("com.tibco.be"));
					thirdPartyJar.appendChild(thirdPartyJarGroupId);
					
					Element thirdPartyJarArtifactId = doc.createElement("artifactId");
					thirdPartyJarArtifactId.appendChild(doc.createTextNode(thirdPartyJarList.get(i)));
					thirdPartyJar.appendChild(thirdPartyJarArtifactId);
					
					Element thirdPartyJarVersion = doc.createElement("version");
					thirdPartyJarVersion.appendChild(doc.createTextNode("1.0.0"));
					thirdPartyJar.appendChild(thirdPartyJarVersion);
					
					projectDependencies.appendChild(thirdPartyJar);
				}
			}
			if(projectLibList.size() > 0){
				for(int i=0;i<projectLibList.size();i++){
					String projectLibFile = projectLibList.get(i);
					Element projLib = doc.createElement("dependency");
					projLib.appendChild(doc.createTextNode(""));
					
					Element projLibGroupId = doc.createElement("groupId");
					projLibGroupId.appendChild(doc.createTextNode("com.tibco.be"));
					projLib.appendChild(projLibGroupId);
					
					Element projLibArtifactId = doc.createElement("artifactId");
					projLibArtifactId.appendChild(doc.createTextNode(new File(projectLibFile).getName().substring(0,new File(projectLibFile).getName().lastIndexOf("."))));
					projLib.appendChild(projLibArtifactId);
					
					Element projLibVersion = doc.createElement("version");
					projLibVersion.appendChild(doc.createTextNode("1.0.0"));
					projLib.appendChild(projLibVersion);
					
					Element projLibType = doc.createElement("type");
					projLibType.appendChild(doc.createTextNode("projlib"));
					projLib.appendChild(projLibType);
					
					projectDependencies.appendChild(projLib);
				}
			}
			
			Element properties = doc.createElement("properties");
			properties.appendChild(doc.createTextNode(""));
			rootElement.appendChild(properties);
			
			Element reuseForks = doc.createElement("reuseForks");
			reuseForks.appendChild(doc.createTextNode("false"));
			properties.appendChild(reuseForks);
			
			//Add maven compiler version
			Element sourceCompiler = doc.createElement("maven.compiler.source");
			sourceCompiler.appendChild(doc.createTextNode("11"));
			properties.appendChild(sourceCompiler);
			
			Element targetCompiler = doc.createElement("maven.compiler.target");
			targetCompiler.appendChild(doc.createTextNode("11"));
			properties.appendChild(targetCompiler);
			
			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			
			String fileNm = null;
			if (projectPath.endsWith("/")) {
				fileNm = projectPath + "pom.xml";
			} else {
				fileNm = projectPath + "/" + "pom.xml";
			}

			
			StreamResult result = new StreamResult(new File(fileNm));
			
			transformer.transform(source, result);
			
			String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
			prefix += "<!--\n  Created by TIBCO BusinessEvents POM generation utility be-maven-pom-generator\n  Creation datetime: "+new Date().toString() + "\n-->\n";
			String xml = readFile (fileNm);
			xml = prefix + xml;
			stringToFile(fileNm, xml);
			

			System.out.println("SUCCESS: Created POM file at : " + fileNm);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
    
    private void stringToFile (String filename, String text) {
    	try {
    		PrintWriter out = new PrintWriter(filename);
    	    out.println(text);
    	    out.close();
    	} catch (Exception e) {
    		
    	}
    }
    public static final boolean readYesNo(String prompt) {
		String input = readLine(prompt).toLowerCase().trim();
		if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
			return true;
		} 
		if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
			return false;
		} 
		if(!input.toLowerCase().startsWith("y")&& !input.toLowerCase().startsWith("n")){
			System.out.println("\n");
			return readYesNo(prompt);
		}
		return false;
	}

	private static String readLine(String prompt) {
		System.out.print(prompt);
		StringBuilder sb = new StringBuilder();
		while (true) {
			try {
				char c = (char) System.in.read();
				sb.append(c);
				if (c == '\n') {
					return sb.toString().trim();
				} else if (c == '\r') {
				} // ignore cr
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
