package com.tibco.cep.webstudio.client.decisiontable.model;


/**
 * 
 * @author sasahoo
 *
 */
public class ArgumentDataTransfer {

	private static ArgumentDataTransfer instance;
	private static ArgumentResource argResource;
	private static String uri;
	private static String argumentPath;

	public static ArgumentDataTransfer getInstance(){
		if(instance == null){
			instance = new ArgumentDataTransfer();
		}
		return instance;
	}
	
	public ArgumentResource getArgumentResource() {
		return argResource;
	}

	public String getURI() {
		return uri;
	}
	
	public String getArgumentPath() {
		return argumentPath;
	}
	
	public void setTransfer(ArgumentResource argResource, String uri, String argPath) {
		ArgumentDataTransfer.argumentPath = argPath;
		ArgumentDataTransfer.uri = uri;
		ArgumentDataTransfer.argResource = argResource;
	}
	
	public void clear() {
		argumentPath = null;
		uri = null;
		argResource = null;
	}
	
}