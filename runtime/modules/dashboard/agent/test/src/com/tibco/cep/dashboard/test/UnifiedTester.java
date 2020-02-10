package com.tibco.cep.dashboard.test;

import com.tibco.cep.dashboard.tools.BasicReadTest;
import com.tibco.cep.dashboard.tools.SubscriptionTest;

public class UnifiedTester {
	
	public static void main(String[] args) {
		try {
			BasicReadTest.main(args);
			SubscriptionTest.main(args);
			ComponentGalleryUsageTest.main(args);
			SaveLayoutTest.main(args);
			RemoveComponentFromPanelTest.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
