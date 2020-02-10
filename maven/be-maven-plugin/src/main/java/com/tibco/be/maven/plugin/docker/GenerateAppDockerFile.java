package com.tibco.be.maven.plugin.docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystemSession;

import com.tibco.be.maven.plugin.util.BEMavenUtil;

@Mojo( name = "generateAppDockerFile")
public class GenerateAppDockerFile extends AbstractMojo {

	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
	@Parameter
	private LinkedHashMap<String, String> appImageConfig;
	
	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;
	
	@Parameter(defaultValue = "${repositorySystemSession}" , readonly = true, required = true)
	private RepositorySystemSession repoSession;
	private String classpathSeparator,beHomeEntry,projectPath,earBuildPath,eclipsePlatfromJars,classPath,targetDir,baseBEImage,maintainer,email,label,cddFileLocation;
    private boolean overwriteDockerfile = false;
	public void execute() throws MojoExecutionException
    {
    	try {
    		boolean isMac = false;
    		if(System.getProperty("os.name").contains("Windows"))
    			classpathSeparator=";";
    		else
    			classpathSeparator=":";
    		if(System.getProperty("os.name").toLowerCase().contains("mac"))
    			isMac = true;
    		beHomeEntry = beProjectDetails.get("beHome");
    		projectPath = project.getBasedir().getPath();
    		projectPath = projectPath.replace('\\', '/');
        
    		earBuildPath = beProjectDetails.get("earLocation");
    		if(earBuildPath==null || earBuildPath=="")
    			earBuildPath = project.getBasedir().getPath() + "/target/"+project.getArtifactId()+"-"+project.getVersion()+".ear";
    		else{
    			File file = new File(earBuildPath);
    			if(!earBuildPath.contains(".ear")){
    				if (file.isAbsolute()) {
            			earBuildPath = earBuildPath + "/" + project.getArtifactId()+"-"+project.getVersion()+".ear";
    				}else{
            			earBuildPath = project.getBasedir().getPath() + "/" + earBuildPath + "/" + project.getArtifactId()+"-"+project.getVersion()+".ear";
    				}
    			}else{
    				throw new MojoExecutionException("Invalid EAR location");
    			}
    		}
    		earBuildPath = earBuildPath.replace('\\', '/');
    		if(isMac)
    			eclipsePlatfromJars = beHomeEntry+ "/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/*"+classpathSeparator;
    		else
    			eclipsePlatfromJars = beHomeEntry+"/eclipse-platform/eclipse/plugins/*"+classpathSeparator;
    		readArguments();
    		cddFileLocation = cddFileLocation.replace('\\', '/');
    		File cddFile = new File(cddFileLocation);
    		File earFile = new File(earBuildPath);
    		File targetDirectory = new File(targetDir);
    		FileUtils.copyFileToDirectory(cddFile, targetDirectory);
    		FileUtils.copyFileToDirectory(earFile, targetDirectory);
        
            classPath = beHomeEntry+"/lib/*"+classpathSeparator+beHomeEntry+"/studio/eclipse/plugins/*"+classpathSeparator+eclipsePlatfromJars+beHomeEntry+"/lib/ext/tibco/*";
            List<String> command = new ArrayList<String>();
            command.add("java");
            command.add("-cp");
            command.add(classPath);
            command.add("com.tibco.be.util.docker.BEDockerMain");
            command.add("-t");
            command.add(targetDir);
            command.add("-i");
            command.add(baseBEImage);
            command.add("-m");
            command.add(maintainer);
            command.add("-e");
            command.add(email);
            if (label!=null || label!="") {
            	command.add("-l");
                command.add(label);
            }
            if (overwriteDockerfile) {
            	command.add("-o");
            }
            
            runDockerfileBuildProcess(command);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException("Error while building docker file");
        }
    }
    private static void runDockerfileBuildProcess(List<String> command) throws Exception {
    	Process pro = new ProcessBuilder(command).start();
	    printLines(" stdout:", pro.getInputStream());
	    printLines(" stderr:", pro.getErrorStream());
	    pro.waitFor();	   
	    if (pro.exitValue() > 0){
	    	 System.out.println(" exitValue() " + pro.exitValue());
	    	 throw new Exception("Error while building docker file");
	    }
	}
	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(line);
	    }
	}
	public void readArguments() throws Exception{
		targetDir = appImageConfig.get("targetDir");
        baseBEImage = appImageConfig.get("baseBEImage");
        maintainer = appImageConfig.get("maintainer");
        email = appImageConfig.get("email");
        label = appImageConfig.get("labels");
        cddFileLocation = appImageConfig.get("cddFileLocation");
        overwriteDockerfile = Boolean.parseBoolean(appImageConfig.get("overwriteDockerfile"));
    	if(BEMavenUtil.isArgumentNull(targetDir) || BEMavenUtil.isArgumentNull(baseBEImage) || BEMavenUtil.isArgumentNull(maintainer)
    			||BEMavenUtil.isArgumentNull(email) || BEMavenUtil.isArgumentNull(cddFileLocation)){
    		System.out.println("Missing mandatory arguments");
    		printUsage();
    		throw new Exception("Error while building dockerfile");
    	}
    	if(!email.matches("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$")){
    		throw new Exception("Invalid email-Id \n Error occurred.Aborting");
		}
    }
    public void printUsage(){
    	String usage="Usage : be docker generation \n"
    			+ "[target-dir]  :        Target directory where CDD,EAR,external jars and custom docker files will be present [required]\n\n"
    			+ "[image]       :        Base TIBCO BusinessEvents Docker image version/tag [required] \n\n"
    			+ "[maintainer]  :        Maintainer [required] \n\n"
    			+ "[email]       :        Email [required]\n\n"
    			+ "[label]       :        Label entries.Can be multiple [optional]\n\n"
    			+ "[cddFileLocation]        :        Location of cdd file [required]\n"
    			+ "[overwriteDockerfile]    :        To overwrite existing docker file [optional]";
    	System.out.println(usage);
    }
}
