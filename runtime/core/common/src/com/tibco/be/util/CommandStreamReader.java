/**
 * 
 */
package com.tibco.be.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Thread to read command error/output stream generated via ProcessBuilder/Runtime.exec
 * @author vpatil
 */
public class CommandStreamReader extends Thread {
	private InputStream iStream;
    private String commandOutput;
    
    /**
     * Default Constructor
     * @param is
     */
	public CommandStreamReader(InputStream is) {
		this.iStream = is;
	}
	
	@Override
	public void run() {
		try	{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			InputStreamReader isr = new InputStreamReader(iStream);
			BufferedReader br = new BufferedReader(isr);
			
			String cmdOutput = null;
			while ( (cmdOutput = br.readLine()) != null) {
				outputStream.write((cmdOutput+"\n").getBytes());
			}
			
			commandOutput = outputStream.toString();
		} catch (IOException ioException) {
			ioException.printStackTrace();  
		}
	}

	public String getCommandOutput() {
		return commandOutput;
	}
}
