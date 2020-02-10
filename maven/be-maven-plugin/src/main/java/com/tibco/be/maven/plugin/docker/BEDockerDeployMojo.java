package com.tibco.be.maven.plugin.docker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.tibco.be.maven.plugin.util.BEMavenUtil;

@Mojo( name = "dockerdeploy")
public class BEDockerDeployMojo extends AbstractMojo{

	@Parameter
	private LinkedHashMap<String, String> appImageConfig;
	
	@Parameter
	private Properties dockerRegistryConfig;
	String appImage , tagImage ,repoUrl , repositoryName = "";
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			readArguments();
			String awsCommand = "" , dockerCommand="";
			if(System.getProperty("os.name").contains("Windows"))
				awsCommand="aws.cmd";
			else if(System.getProperty("os.name").toLowerCase().contains("mac"))
				awsCommand="/Users/apple/Library/Python/2.7/bin/aws";
			else
				awsCommand="aws";
			
			dockerCommand = getDockerCommand();
			String[] awsGetLoginCommand = {awsCommand, "ecr" ,"get-login","--no-include-email"};
			String[] dockerTagImageCommand = {dockerCommand, "tag" ,appImage,repoUrl+"/"+tagImage};
			String awsLoginCommand="";
			String result="";
		
			Process dockerTag = new ProcessBuilder(Arrays.asList(dockerTagImageCommand)).start();
			InputStream dockerTagIS = dockerTag.getInputStream();
			BufferedReader dockerTagIsIn = new BufferedReader(new InputStreamReader(dockerTagIS));
			while ((result = dockerTagIsIn.readLine()) != null) {
				System.out.println(result);
			}
			dockerTag.waitFor();
			
			if(repositoryName.equalsIgnoreCase("AWS")){
				Process awsGetLogin = new ProcessBuilder(Arrays.asList(awsGetLoginCommand)).start();
				InputStream awsGetLoginIs = awsGetLogin.getInputStream();
				BufferedReader awsGetLoginIn = new BufferedReader(new InputStreamReader(awsGetLoginIs));
				while ((result = awsGetLoginIn.readLine()) != null) {
					awsLoginCommand = result;
				}
				awsGetLogin.waitFor();
				if(System.getProperty("os.name").toLowerCase().contains("mac"))
					awsLoginCommand ="/usr/local/bin/"+awsLoginCommand;
				
				String[] dockerAWSLoginCommand = awsLoginCommand.split(" ");
				ProcessBuilder dockerAWSLoginBuilder = new ProcessBuilder(Arrays.asList(dockerAWSLoginCommand));				
				if(System.getProperty("os.name").toLowerCase().contains("mac")){
					Map<String,String> envVar = dockerAWSLoginBuilder.environment();
					envVar.put("PATH", "/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/bin");
				}
				Process dockerAWSLogin = dockerAWSLoginBuilder.start();
				InputStream dockerAWSLoginIs = dockerAWSLogin.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(dockerAWSLoginIs));
				while ((result = in.readLine()) != null) {
					System.out.println(result);
				}
				dockerAWSLogin.waitFor();
			 }
			
			String[] pushCommand = {dockerCommand, "push" ,repoUrl+"/"+tagImage};
			ProcessBuilder pushCommandProBuilder = new ProcessBuilder(Arrays.asList(pushCommand));
			if(System.getProperty("os.name").toLowerCase().contains("mac")){
				Map<String,String> envVar = pushCommandProBuilder.environment();
				envVar.put("PATH", "/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/bin");
			}
			Process pushCommandPro = pushCommandProBuilder.start();
			InputStream pushCommandIs = pushCommandPro.getInputStream();
			BufferedReader pushCommandIn = new BufferedReader(new InputStreamReader(pushCommandIs));
			while ((result = pushCommandIn.readLine()) != null) {
				System.out.println(result);
			}
			printLines(" stdout:", pushCommandPro.getInputStream());
			printLines(" stderr:", pushCommandPro.getErrorStream());
			pushCommandPro.waitFor();
			if (pushCommandPro.exitValue() > 0) {
				System.out.println(" exitValue() " + pushCommandPro.exitValue());
				throw new Exception("Error while pushing the docker image");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException("Error while docker push");
		}
		
	}
	public void readArguments() throws Exception{
		appImage = appImageConfig.get("appImage");
		if(dockerRegistryConfig!=null){
			tagImage = dockerRegistryConfig.getProperty("TagImage");
			repoUrl = dockerRegistryConfig.getProperty("Url");
			repositoryName =  dockerRegistryConfig.getProperty("Repository");
		}else{
			System.out.println("Missing mandatory arguments");
			printUsage();
    		throw new Exception("Error while docker push");
		}
    	if(BEMavenUtil.isArgumentNull(tagImage) || BEMavenUtil.isArgumentNull(repoUrl) || BEMavenUtil.isArgumentNull(appImage)
    			||BEMavenUtil.isArgumentNull(repositoryName)){
    		System.out.println("Missing mandatory arguments");
    		printUsage();
    		throw new Exception("Error while docker push");
    	}
    }
	
	public static String getDockerCommand() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			return "/usr/local/bin/docker";
		} else {
			return "docker";
		}
	}

	public void printUsage() {
		String usage = "Usage : be docker push \n"
				+ "[tagImage]  :        Name of tag Image (reponame:name)[required]\n\n"
				+ "[repoUrl]       :     Repository Url[required] \n\n"
				+ "[repositoryName]  :        Repository Name [required] \n\n";
		System.out.println(usage);
	}
	private static void printLines(String name, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
	}
}
