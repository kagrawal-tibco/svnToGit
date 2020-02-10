package com.tibco.be.maven.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.tibco.be.maven.plugin.util.BEMavenUtil;

@Mojo( name = "beBuildProjectLib")
public class BEBuildProjectLib extends AbstractMojo {
	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	@Parameter
	private List<String> projLibResources;
	
	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

	public void execute() throws MojoExecutionException, MojoFailureException {
		String beHomeEntry = beProjectDetails.get("beHome");
        String toolsJarDirectory = beProjectDetails.get("jdkHome");
        String projectPath ="";
        String classpathSeparator="";
        boolean isMac = false;
		if (System.getProperty("os.name").contains("Windows"))
			classpathSeparator = ";";
		else
			classpathSeparator = ":";
		if (System.getProperty("os.name").toLowerCase().contains("mac"))
			isMac = true;

		if (BEMavenUtil.isArgumentNull(toolsJarDirectory)) {
			toolsJarDirectory = new File(beHomeEntry).getParentFile().getParent();
			if (!isMac) {
				toolsJarDirectory = toolsJarDirectory + "/tibcojre64/11";
			} else {
				toolsJarDirectory = toolsJarDirectory + "/tibcojre64/11/Contents/Home";
			}
		}
        
        File checkMvnSrcFolder = new File(project.getBasedir().getPath()+"/src/main");
        if(checkMvnSrcFolder.exists()){ //check if folder structure is of type maven
        	String[] projectName = checkMvnSrcFolder.list();
        	if(projectName.length !=0)
        		projectPath = project.getBasedir().getPath()+"/src/main/" + projectName[0];
        	else
        		return;
        }
        else
        	projectPath = project.getBasedir().getPath();
        
        projectPath = projectPath.replace('\\', '/');
        
        String projectLibPath = beProjectDetails.get("projLibLocation");
        if(projectLibPath==null || projectLibPath=="")
        	projectLibPath = project.getBasedir().getPath() + "/target/"+project.getArtifactId()+"-"+project.getVersion()+".projlib";
        else{
        	File file = new File(projectLibPath);
        	if(!projectLibPath.contains(".projlib")){
        		if (file.isAbsolute()) {
        			projectLibPath = projectLibPath + "/" + project.getArtifactId()+"-"+project.getVersion()+".projlib";
            	}else{
            		projectLibPath = project.getBasedir().getPath() + "/" + projectLibPath + "/" + project.getArtifactId()+"-"+project.getVersion()+".projlib";
            	}
        	}else{
        		throw new MojoExecutionException("Invalid project lib location");
        	}
        }
        projectLibPath = projectLibPath.replace('\\', '/');
        
        String projectLibResources = "";
        for(String projLibResource:projLibResources){
        	projectLibResources = projectLibResources + projLibResource +",";
        }
       
        String eclipseLauncherJar="";
        if(isMac)
        	eclipseLauncherJar = beHomeEntry
                + "/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/org.eclipse.equinox.launcher_1.5.400.v20190515-0925.jar"+classpathSeparator;
        else
        	eclipseLauncherJar = beHomeEntry
            + "/eclipse-platform/eclipse/plugins/org.eclipse.equinox.launcher_1.5.400.v20190515-0925.jar"+classpathSeparator;
        try {
            String classPath = eclipseLauncherJar +beHomeEntry + "/studio/eclipse/plugins/*.jar"+classpathSeparator+beHomeEntry+"/eclipse-platform/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar"+classpathSeparator+beHomeEntry+"/eclipse-platform/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar"+classpathSeparator;
            String beHomeVariable = "-DBE_HOME="+beHomeEntry;
            List<String> command = new ArrayList<String>();
            command.add("java");
            command.add(beHomeVariable);
			command.add("-Xbootclasspath/p:"+toolsJarDirectory+"/lib/tools.jar");
            command.add("-cp");
            command.add(classPath);
            command.add("org.eclipse.equinox.launcher.Main");
            command.add("-application");
            command.add("com.tibco.cep.studio.cli.studio-tools");    
            command.add("-core");
            command.add("buildLibrary");
            command.add("-p");
            command.add(projectPath);
            command.add("-x");
            command.add("-n");
            command.add(projectLibPath);
            if(projectLibResources != ""){
            	command.add("-f");
                command.add(projectLibResources);
            }
            System.out.println("Build project library file "+new File(projectLibPath).getName()+" in progress..");	
            runBuildLibrarydProcess(command);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException("Error while building Project Library");
        }
	}
	private static void runBuildLibrarydProcess(List<String> command) throws Exception {
		Process pro = new ProcessBuilder(command).start();
	    printLines(" stdout:", pro.getInputStream());
	    printLines(" stderr:", pro.getErrorStream());
	    pro.waitFor();	   
	    if (pro.exitValue() > 0){
	    	 System.out.println(" exitValue() " + pro.exitValue());
	    	 throw new Exception("Error while building project Library");
	    }
	}

	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(line);
	    }
	}
}
