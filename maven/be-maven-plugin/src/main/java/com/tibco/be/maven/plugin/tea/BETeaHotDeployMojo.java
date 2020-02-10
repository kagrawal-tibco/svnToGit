package com.tibco.be.maven.plugin.tea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.LinkedHashMap;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.tibco.be.maven.plugin.util.BEMavenUtil;

@Mojo( name = "behotdeploy")
public class BETeaHotDeployMojo extends AbstractMojo{
	@Parameter
	private LinkedHashMap<String, String> teaConfig;
	
	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;
	
	private String earBuildPath,teaUrl,teaAppName,username,password;
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try{
		readArguements();
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
        String encoding = Base64.getEncoder().encodeToString((username+":"+password).getBytes());
        String uploadedFileName="";
        URL serverUrl;
			 	serverUrl =  new URL(teaUrl+"/teas/fileupload/");
				HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
				String boundaryString =  Long.toHexString(System.currentTimeMillis());
				File logFileToUpload = new File(earBuildPath);
					 
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
					
					
				urlConnection.setRequestProperty  ("Authorization", "Basic " + encoding);
					 
				urlConnection.setDoOutput(true);
					 
				OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
				BufferedWriter httpRequestBodyWriter =
					    new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));
					 
					
					 
				httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
				httpRequestBodyWriter.write("Content-Disposition: form-data;"
					        + "name=\"file\";"
					        + "filename=\""+ logFileToUpload.getName() +"\""
					        + "\nContent-Type: application/octet-stream\n\n");
				httpRequestBodyWriter.flush();
					 
				FileInputStream inputStreamToLogFile = new FileInputStream(logFileToUpload);
					 
				int bytesRead;
				byte[] dataBuffer = new byte[1024];
				while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
					outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
				}
					 
				outputStreamToRequestBody.flush();
					 
				httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
				httpRequestBodyWriter.flush();
					 
				outputStreamToRequestBody.close();
				httpRequestBodyWriter.close();
					
			    if(urlConnection.getErrorStream()!=null){
			        	 BufferedReader br2 = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
					        String line2;
					        while ((line2 = br2.readLine()) != null) {
					                System.out.println(line2);		        
					        }
					        br2.close();
					        throw new MojoExecutionException("Error While uploading ear file");
			    }else if(urlConnection.getInputStream()!=null){
			        	BufferedReader br2 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				        String line2;
				        while ((line2 = br2.readLine()) != null) {
				                uploadedFileName = line2.replaceAll("\\[", "").replaceAll("\\]","");
				        }
				        br2.close();
				        if(!uploadedFileName.contains(".tmp"))
				        	throw new MojoExecutionException("Error While uploading ear file");
			    }
		
			URL url = new URL (teaUrl+"/teas/task");
            String payload="{\"methodType\":\"UPDATE\",\"objectId\":\":BusinessEvents::Application:"+teaAppName +"\",\"operation\":\"hotdeployAll\",\"params\":{\"earpath\":" +uploadedFileName+ "}}";
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
    		connection.setRequestMethod("PUT");
    		connection.setRequestProperty("Accept", "application/json,text/plain,*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
		 
            InputStream content = (InputStream)connection.getInputStream();
            if(content!=null){
            	BufferedReader in   =  new BufferedReader (new InputStreamReader (content));
                String result;
                boolean status = false;
		        while ((result = in.readLine()) != null) {
		           if(result.contains("\"status\":\"DONE\""))
		        	   status=true;
		        }
		        in.close();
		        if(status)
		        	System.out.println("Hot Deployed EAR Successfully");
		        else
		        	throw new MojoExecutionException("Error while hot deploying Ear");
            }else{
            	InputStream contentError = (InputStream)connection.getErrorStream();
            	if(contentError!=null){
            		BufferedReader in   =  new BufferedReader (new InputStreamReader (content));
                    String result;
                    boolean status = false;
    		        while ((result = in.readLine()) != null) {
    		        	System.out.println(result);
    		        }
    		        in.close();
            	}
            	 throw new MojoExecutionException("Error while hot deploying Ear");
             }
		  }catch (Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException("Error while hot deploying Ear");
		}
	}
    private void readArguements() throws Exception{
    	earBuildPath = beProjectDetails.get("earLocation");
		teaUrl = teaConfig.get("teaUrl");
		teaAppName = teaConfig.get("applicationName");
		username = teaConfig.get("username");
		password = teaConfig.get("password");
		if(BEMavenUtil.isArgumentNull(earBuildPath)||BEMavenUtil.isArgumentNull(teaUrl)||BEMavenUtil.isArgumentNull(teaAppName)
				||BEMavenUtil.isArgumentNull(username)||BEMavenUtil.isArgumentNull(password)){
			printUsage();
			throw new Exception("Invalid arguments");
		}
    }
    private void printUsage(){
    	String usage="Usage : be maven hot deploy \n"
    			+ "[earBuildPath]  :        EAR file path[required]\n\n"
    			+ "[teaUrl]       :         TEA Server url [required] \n\n"
    			+ "[teaAppName]  :          Name of the application deployed on TEA[required] \n\n"
    			+ "[username]       :       TEA Server username[required]\n\n"
    			+ "[password]       :       TEA Server password[required]";
    	System.out.println(usage);
    }
}
