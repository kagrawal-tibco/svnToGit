package com.tibco.be.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.LocalRepository;

import com.google.common.io.Files;

@Mojo( name = "beInstallProjectLib")
public class BEInstallProjectLib extends AbstractMojo{
	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;
	
	@Parameter(defaultValue = "${repositorySystemSession}" , readonly = true, required = true)
	private RepositorySystemSession repoSession;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
    	LocalRepository localRepo = repoSession.getLocalRepository();
		String mavenRepositoryPath= localRepo.getBasedir().getAbsolutePath();
		
    	String[] groupId = project.getGroupId().split("\\.");
    	String artifactId = project.getArtifactId();
    	String version = project.getVersion();
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
        
    	String groupIdDirectoryStructure="";
    	for(String dir : groupId)
    		groupIdDirectoryStructure = groupIdDirectoryStructure + "/" + dir;
    	File m2RepoFileLocation = new File(mavenRepositoryPath+"/"+groupIdDirectoryStructure+"/"+artifactId+"/"+version);
        if (!m2RepoFileLocation.exists()) {
            if (m2RepoFileLocation.mkdirs()) {
            } else {
                System.out.println("failed to create sub directories");
                return;
            }
        }
        if(m2RepoFileLocation.exists()){
       	  File projLibFileFromTarget = new File(projectLibPath);
       	  String projLibFileName = projLibFileFromTarget.getName();
       	  File projLibFileToCopy = new File(mavenRepositoryPath+"/"+groupIdDirectoryStructure+"/"+artifactId+"/"+version+"/"+projLibFileName);
       	  try {
			Files.copy(projLibFileFromTarget, projLibFileToCopy);
			getLog().info( "Installing " + projLibFileFromTarget.getPath() +" to " +projLibFileToCopy.getPath());
       	  } catch (IOException e) {
			e.printStackTrace();
			throw new MojoExecutionException("Error while installing projLib file");
		  }
        }
        	 
    }

}
