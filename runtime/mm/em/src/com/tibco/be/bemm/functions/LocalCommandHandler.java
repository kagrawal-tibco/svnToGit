/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import com.tibco.be.util.config.cdd.MmExecuteCommandConfig;

/**
 * @author Nick Xu
 *
 */
public class LocalCommandHandler {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	private Process localProcess;
	private String command;
	
	public LocalCommandHandler(String command){
		this.command = command;
	}
	
	public LocalCommandHandler(MmExecuteCommandConfig commandConfig){
		this.command = commandConfig.getCommand();
	}
	
	private LinkedList<String> parseCommand(String command){
		if(command==null || command.length()==0)
			throw new RuntimeException("Ignoring empty command");
		String[] commands = command.split(" ");
		LinkedList<String> commandList = new LinkedList<String>();
		for(int i=0;i<commands.length;i++){
			commandList.add(commands[i]);
		}
		return commandList;
	}
		
	public void executeCommand() throws Exception{
		LinkedList<String> commandList = parseCommand(command);
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream(true);
		processBuilder.command(commandList);
		localProcess = processBuilder.start();
		InputStream is = localProcess.getInputStream();
		StreamReader outputReader = new StreamReader(is);
		outputReader.start();
	}
	
	public void destroy(){
		if(this.localProcess != null)
			this.localProcess.destroy();
	}
	
	//this may get hang if the command will be running for long time.
	private String getOutput(InputStream inputStream) throws IOException {
		StringBuffer sb = new StringBuffer();
		char c = (char) inputStream.read();
		while ((c != -1) && (c != 65535)) {
			sb.append(c);
			c = (char) inputStream.read();
			if(sb.length()==2048){
				//System.out.println(sb.toString());
				sb.setLength(0);
			}
		}
		return sb.toString();
	} 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String command = "c:/be40_82/be/4.0/bin/be-engine.exe --propFile c:/be40_82/be/4.0/bin/be-engine.tra " +
				"-c c:/be40_82/be/4.0/bin/myBEtest.cdd -u default c:/be40_82/be/4.0/bin/myBEtest.ear --propVar jmx_port=5562";
		//String command = "ipconfig";
		try{
			new LocalCommandHandler(command).executeCommand();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	class StreamReader extends Thread{
	    InputStream is;
	    
	    StreamReader(InputStream is){
	        this.is = is;
	    }
	    
	    public void run(){
	        try{
				StringBuffer sb = new StringBuffer();
				char c = (char) is.read();
				while ((c != -1) && (c != 65535)) {
					sb.append(c);
					c = (char) is.read();
					if(sb.length()==2048){
						System.out.println(sb.toString());
						sb.setLength(0);
					}
				}
	        } 
	        catch (IOException ioe){
	            ioe.printStackTrace();  
	        }
	        finally{
	        	try {
					is.close();
				} catch (Exception e) {
				}
	        }
	    }
	}

}
