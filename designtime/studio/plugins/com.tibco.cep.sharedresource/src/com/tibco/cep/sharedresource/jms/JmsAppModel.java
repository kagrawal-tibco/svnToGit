package com.tibco.cep.sharedresource.jms;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.tibco.cep.sharedresource.model.SharedResModel;

/*
@author ssailapp
@date Dec 30, 2009 11:52:14 PM
 */

public class JmsAppModel extends SharedResModel {
	ArrayList<LinkedHashMap<String, String>> appProps;

	public JmsAppModel(String name) {
		this.name = name;
		appProps = new ArrayList<LinkedHashMap<String, String>>();
	}
}
