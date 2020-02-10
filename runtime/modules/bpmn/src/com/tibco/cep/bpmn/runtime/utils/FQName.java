package com.tibco.cep.bpmn.runtime.utils;

import com.tibco.be.model.util.ModelNameUtil;

/*
* Author: Suresh Subramani / Date: 12/3/11 / Time: 5:54 PM
*/
public class FQName {
	
	private  String name;
    private int version;


    private FQName(String name, int version) {
        this.name = name;
        this.version = version;
    }
	public static FQName makeName(final String name, final int revision) {

		return new FQName(name, revision);
	}

    public String getName() {
    	//process_marker is so that you can tell this is a dynamically generated process class
    	//for example during hot deploy to avoid errors
        return String.format("%s_%d_%s", name, version, ModelNameUtil.PROCESS_MARKER);
    }




}
