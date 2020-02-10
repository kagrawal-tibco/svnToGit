package com.tibco.cep.dashboard.test;

import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.dashboard.tools.CompleteReadTest;
import com.tibco.cep.dashboard.tools.Launchable;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;

public class CompleteReadConcurrentUsersTest extends BasicReadTest implements Launchable {

	@Override
	public String getArgmentUsage() {
		return "remotepullrequesturl streamingportnumber username password role numofusers";
	}

	@Override
	public void launch(String[] args) throws IllegalArgumentException,Exception {
		logger = LogManagerFactory.getLogManager().getLogger(this.getClass());

		final String[] newArgs = args;
		if(newArgs.length < 6) {
			logger.log(Level.ERROR, "Insufficiant arguments, noOfUsers not specified...");
			return;
		}
		final int noOfConcurrentUsers = Integer.parseInt(newArgs[5]);
		logger.log(Level.INFO, "Number of concurrent users specified: "+ noOfConcurrentUsers);
		for(int i=0; i<noOfConcurrentUsers; i++) {
			Thread userThread = new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						CompleteReadTest test = new CompleteReadTest();
						test.launch(newArgs);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			logger.log(Level.INFO,"Starting Thread#"+i);
			userThread.start();
		}
	}

	public static void main(String[] args) {
		CompleteReadConcurrentUsersTest test = new CompleteReadConcurrentUsersTest();
		try {
			test.launch(args);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Usage : java " + CompleteReadConcurrentUsersTest.class.getName() + " [<localfilename>]/[<remotepullrequesturl> <portnumber>] <username> <password> <role> <numofusers>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
