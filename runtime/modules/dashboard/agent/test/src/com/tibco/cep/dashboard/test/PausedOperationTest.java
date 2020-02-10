package com.tibco.cep.dashboard.test;

import java.io.IOException;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.tools.AuthenticationTest;

public class PausedOperationTest extends AuthenticationTest {

	public static void main(String[] args) {
		final PausedOperationTest test = new PausedOperationTest();
		try {
			test.setup(args);
			test.start();
			test.pause();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(5*DateTimeUtils.SECOND);
						test.login("admin", "admin");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}).start();
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(7*DateTimeUtils.SECOND);
						test.login("admin", "admin");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}).start();			
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(15*DateTimeUtils.SECOND);
						test.unpause();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}).start();	
			Thread.sleep(20*DateTimeUtils.SECOND);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			test.shutdown();
		}
	}

}
