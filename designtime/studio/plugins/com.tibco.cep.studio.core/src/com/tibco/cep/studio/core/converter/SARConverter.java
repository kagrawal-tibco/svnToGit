package com.tibco.cep.studio.core.converter;

import java.util.Map;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;

public class SARConverter extends ArchiveResourceConverter {

	public static final String BAR_PROP = "BusinessEventsArchives";
	public static final String SAR_PROP = "sharedArchive";
	public static final String DESIGNER_PROP = "designer"; // what is this?
	
	public static final String VERSION_PROP = "versionProperty";
	public static final String FILE_LOC_PROP = "fileLocationProperty";
	public static final String GLOBAL_VARS_PROP = "addServiceSettableGvars";
	
	
	public SARConverter(ArchiveResource resource) {
		super(resource);
	}

	protected void processAttribute(String key, String value) {
		
		super.processAttribute(key, value);
	}

	protected void processMap(String key, Map map) {
		System.out.println("found map in SAR "+map);
	}
	
}
