package com.tibco.be.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.LocalRepository;

import com.google.common.io.Files;

 

 @Mojo( name = "beinstall", defaultPhase = LifecyclePhase.INSTALL, requiresDependencyResolution = ResolutionScope.TEST )
 public class BEInstallMojo extends AbstractMojo
  {
	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;
	
	@Parameter(defaultValue = "${repositorySystemSession}" , readonly = true, required = true)
	private RepositorySystemSession repoSession;
	
    public void execute() throws MojoExecutionException
    {
    	LocalRepository localRepo = repoSession.getLocalRepository();
		String mavenRepositoryPath= localRepo.getBasedir().getAbsolutePath();
		
    	String[] groupId = project.getGroupId().split("\\.");
    	String artifactId = project.getArtifactId();
    	String version = project.getVersion();
    	String earBuildPath = beProjectDetails.get("earLocation");
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
       	  File earFileFromTarget = new File(earBuildPath);
       	  String earFileName = earFileFromTarget.getName();
       	  File pomFileFromProject = new File(project.getBasedir().getPath()+"/pom.xml");
       	  File earFileToCopy = new File(mavenRepositoryPath+"/"+groupIdDirectoryStructure+"/"+artifactId+"/"+version+"/"+earFileName);
       	  File pomFileToCopy = new File(mavenRepositoryPath+"/"+groupIdDirectoryStructure+"/"+artifactId+"/"+version+"/"+artifactId+"-"+version+".pom");
       	  try {
			Files.copy(earFileFromTarget, earFileToCopy);
			Files.copy(pomFileFromProject, pomFileToCopy);
			getLog().info( "Installing " + earFileFromTarget.getPath() +" to " +earFileToCopy.getPath());
			getLog().info( "Installing " + pomFileFromProject.getPath() +" to " +pomFileToCopy.getPath());
       	  } catch (IOException e) {
			e.printStackTrace();
			throw new MojoExecutionException("Error while installing ear file");
		  }
        }
        	 
    }
  }
