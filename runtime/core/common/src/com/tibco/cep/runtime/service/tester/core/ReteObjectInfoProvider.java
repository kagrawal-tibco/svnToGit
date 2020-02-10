package com.tibco.cep.runtime.service.tester.core;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.runtime.service.tester.model.ReteObject;


/**
 * 
 * @author sasahoo
 *
 */
public class ReteObjectInfoProvider {

	private static ReteObjectInfoProvider instance;
	private List<ReteObject> reteObjectList = new ArrayList<ReteObject>();

    public List<ReteObject> getReteObjectList() {
		return reteObjectList;
	}

	static synchronized ReteObjectInfoProvider getInstance() throws RuntimeException {
        if (instance == null) {
            instance = new ReteObjectInfoProvider();
        }
        return instance;
    }
    private ReteObjectInfoProvider() throws RuntimeException {
	}
	
}
