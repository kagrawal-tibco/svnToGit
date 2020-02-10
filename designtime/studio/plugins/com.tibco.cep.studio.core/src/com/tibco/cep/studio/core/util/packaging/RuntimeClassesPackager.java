package com.tibco.cep.studio.core.util.packaging;
/**
 * @author rmishra
 *
 */
public interface RuntimeClassesPackager extends Packager {

	boolean isDeleteTempFiles();

	String getPathToDirectory();
	
}
