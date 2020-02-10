package com.tibco.cep.studio.cli.studiotools;

import java.io.IOException;
import java.io.InputStream;

/*
@author ssailapp
@date Feb 16, 2010 4:33:00 PM
 */

public class StudioCommandLineMain {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	private static Process pr = null;

	public static void main(String args[]) {
		String beHome = System.getProperty("BE_HOME"); 
		StringBuffer cmdLine = new StringBuffer();
		cmdLine.append(beHome + "/eclipse-platform/eclipse/eclipsec");
		cmdLine.append(" -nosplash");
		cmdLine.append(" -application com.tibco.cep.studio.cli.studio-tools");
		for (int i=0; i<args.length; i++) {
			cmdLine.append(" ");
			cmdLine.append(args[i]);
		}
		cmdLine.append(" -vmargs");
		cmdLine.append(" -DBE_HOME=" + beHome);
		Runtime rt = Runtime.getRuntime();
		System.out.println(cmdLine.toString());

		rt.addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutting down...");
				InputStream is = pr.getInputStream();
				try {
					int cnt = is.available();
					byte[] buf = new byte[cnt];
					is.read(buf, 0, cnt);
					System.out.println(new String(buf));
				} catch (IOException ioe) {
				}
				//consoleProcessor.stop();
			}
		});

		try {
			pr = rt.exec(cmdLine.toString());
			System.out.println("Process running");
			int exitVal = pr.waitFor();
			System.out.println("Exit code=" + exitVal);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
