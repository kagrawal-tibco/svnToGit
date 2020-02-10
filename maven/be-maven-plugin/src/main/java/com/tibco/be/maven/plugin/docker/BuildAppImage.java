package com.tibco.be.maven.plugin.docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

@Mojo(name = "buildAppImage")
public class BuildAppImage extends AbstractMojo {
	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;

	@Parameter
	private LinkedHashMap<String, String> appImageConfig;

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	@Parameter(defaultValue = "${repositorySystemSession}", readonly = true, required = true)
	private RepositorySystemSession repoSession;
	private String appImage, targetDir, installerLocation, dockerfile, useBEHome = "", beHome, cddFileLocation;

	public void execute() throws MojoExecutionException {

		try {
			// Get BE_HOME
			String beHomeEntry = beProjectDetails.get("beHome");

			// Read home
			readArguments();
			// Directory from where the docker command will be executed
			String dockerExecDirectory = beHomeEntry + "/cloud/docker/bin";
			List<String> createAppImage = new ArrayList<>();
			String location=installerLocation;
			
			if (Boolean.parseBoolean(useBEHome)) {
				if (BEMavenUtil.isArgumentNull(beHome))
					beHome = beHomeEntry;
				dockerExecDirectory = beHomeEntry + "/cloud/docker/frominstall";
				location =beHome;
			
			}
			// Run the process
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				createAppImage.add(dockerExecDirectory + "/build_app_image.bat");
			} else {
				createAppImage.add(dockerExecDirectory + "/build_app_image.sh");
			}
			
			createAppImage.add("-a");
			createAppImage.add(targetDir);
			createAppImage.add("-r");
			createAppImage.add(appImage);
			if (!BEMavenUtil.isArgumentNull(dockerfile)) {
				createAppImage.add("-d");
				createAppImage.add(dockerfile);
			}
			createAppImage.add("-l");
			createAppImage.add(location);
			
			ProcessBuilder createOptimizedImageProcess = new ProcessBuilder(createAppImage);
			createOptimizedImageProcess.directory(new File(dockerExecDirectory));
			Process createOptimizedImagePro = createOptimizedImageProcess.start();

			// Print the output
			printLines(" stdout:", createOptimizedImagePro.getInputStream());
			printLines(" stderr:", createOptimizedImagePro.getErrorStream());
			createOptimizedImagePro.waitFor();
			if (createOptimizedImagePro.exitValue() > 0) {
				System.out.println(" exitValue() " + createOptimizedImagePro.exitValue());
				throw new Exception("Error while building application image");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException("Error while building app image");
		}
	}

	private static void printLines(String name, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
	}

	public void readArguments() throws Exception {
		beHome = appImageConfig.get("be_home");
		useBEHome = appImageConfig.get("useBEHome");
		cddFileLocation = appImageConfig.get("cddFileLocation");
		targetDir = appImageConfig.get("targetDir");
		appImage = appImageConfig.get("appImage");
		installerLocation = appImageConfig.get("installers_location");
		dockerfile = appImageConfig.get("dockerfile");
		String earLocation = beProjectDetails.get("earLocation");
		if (BEMavenUtil.isArgumentNull(earLocation)) {
			printUsage();
			throw new Exception("Ear location is missing");
		} else if (BEMavenUtil.isArgumentNull(cddFileLocation) || BEMavenUtil.isArgumentNull(targetDir)
				|| BEMavenUtil.isArgumentNull(appImage))

		{
			System.out.println("Missing mandatory arguments");
			printUsage();
			throw new Exception("Error while building dockerfile");
		}
		if (!Boolean.parseBoolean(useBEHome) && BEMavenUtil.isArgumentNull(installerLocation)) {
			printUsage();
			throw new Exception("Error while building dockerfile");
		}
		
		File targetDirectoryPath = new File(targetDir);
		File[] cddFiles = targetDirectoryPath.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cdd");
			}
		});
		// Remove previous cdd files
		if (null != cddFiles && cddFiles.length > 0) {
			for (int i = 0; i < cddFiles.length; i++) {
				cddFiles[i].delete();
			}
		}

		// Copy EAR file to target file
		File cddFileLocationFile = new File(cddFileLocation);
		FileUtils.copyFileToDirectory(cddFileLocationFile, targetDirectoryPath);

		File[] targetEars = targetDirectoryPath.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ear");
			}
		});
		// Remove previous ear files
		if (null != targetEars && targetEars.length > 0) {
			for (int i = 0; i < targetEars.length; i++) {
				targetEars[i].delete();
			}
		}
		String projectPath = null;
		File checkMvnSrcFolder = new File(project.getBasedir().getPath() + "/src/main");
		if (checkMvnSrcFolder.exists()) { // check if folder structure is of
											// type maven
			String[] projectName = checkMvnSrcFolder.list();
			if (projectName.length != 0)
				projectPath = project.getBasedir().getPath() + "/src/main/" + projectName[0];
			else
				return;
		} else
			projectPath = project.getBasedir().getPath();

		projectPath = projectPath.replace('\\', '/');
		String earBuildPath = beProjectDetails.get("earLocation");
		if (earBuildPath == null || earBuildPath == "")
			earBuildPath = project.getBasedir().getPath() + "/target/" + project.getArtifactId() + "-"
					+ project.getVersion() + ".ear";
		else {
			File file = new File(earBuildPath);
			if (!earBuildPath.contains(".ear")) {
				if (file.isAbsolute()) {
					earBuildPath = earBuildPath + "/" + project.getArtifactId() + "-" + project.getVersion() + ".ear";
				} else {
					earBuildPath = project.getBasedir().getPath() + "/" + earBuildPath + "/" + project.getArtifactId()
							+ "-" + project.getVersion() + ".ear";
				}
			} else {
				throw new MojoExecutionException("Invalid EAR location");
			}
		}
		// Copy All EAR files
		FileUtils.copyFileToDirectory(new File(earBuildPath), targetDirectoryPath);
	}

	public void printUsage() {
		String usage = "\n\n [targetDir]      :     Location where the application ear, cdd and other files are located [required]\n";
		usage += "\n\n [installers_location]  :     Location where TIBCO BusinessEvents and TIBCO Activespaces installers are located [required]\n";
		usage += "\n\n [appImage]             :     The app image Repository (example - fdc:latest) [required]";
		usage += "\n\n [cddFileLocation]      :     Location of cdd file [required]";
		usage += "\n\n [dockerFile]           :     Dockerfile to be used for generating image (default - Dockerfile.win for windows container, Dockerfile for others) [optional]\n";
		usage += "\n\n [behome]               :     BE Home directory [optional]\n";

		System.out.println(usage);
	}
}
