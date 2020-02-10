/**
 * 
 */
package com.tibco.cep.studio.core.util.packaging;

/**
 * @author aathalye
 *
 */
public class PackagingConstants {
	
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_AUTHOR = "authorProperty";
	public static final String PROPERTY_VERSION = "versionProperty";
	public static final String PROPERTY_DEBUG_COMPILE = "compileWithDebug";
	public static final String PROPERTY_COMPILE_PATH= "compilePath";
	public static final String PROPERTY_OM_CHKPT_INTERVAL = "omCheckPtInterval";
	public static final String PROPERTY_OM_CACHE_SIZE = "omCacheSize";
	
	//Build this exclusion list from the configurator
	public static final String[] EXCLUSIONS = new String[] {".svn"};
}
