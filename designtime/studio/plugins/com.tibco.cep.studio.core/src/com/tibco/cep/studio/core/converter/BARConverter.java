package com.tibco.cep.studio.core.converter;


import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BE_ARCHIVE_TYPE;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.InputDestination;


public class BARConverter extends ArchiveResourceConverter {


    public BARConverter(ArchiveResource resource) {
		super(resource);
	}

	protected void processArrayAttribute(String key, Object[] value) {
		BusinessEventsArchiveResource bar = (BusinessEventsArchiveResource) fCurrentResource;
		if (Constants.PROPERTY_NAME_DESTINATIONS_INPUT_ENABLED.equals(key)) {
			for (Object object : value) {
				InputDestination dest = ArchiveFactory.eINSTANCE.createInputDestination();
				dest.setDestinationURI((String)object);
				bar.getInputDestinations().add(dest);
			}
		} else if (Constants.PROPERTY_NAME_STARTUP.equals(key)) {
			for (Object object : value) {
				bar.getStartupActions().add((String) object);
			}
		} else if (Constants.PROPERTY_NAME_SHUTDOWN.equals(key)) {
			for (Object object : value) {
				bar.getShutdownActions().add((String) object);
			}
		} else if (Constants.PROPERTY_NAME_RULESETS.equals(key)) {
			for (Object object : value) {
				bar.getIncludedRuleSets().add((String) object);
			}
		} else {
			System.out.println("not handled " + key + Arrays.toString(value));
		}
	}
	
	protected void processAttribute(String key, String value) {
		BusinessEventsArchiveResource bar = (BusinessEventsArchiveResource) fCurrentResource;
		if (Constants.PROPERTY_NAME_ARCHIVE_TYPE.equals(key)) {
			bar.setArchiveType(BE_ARCHIVE_TYPE.get(value));
		} else if (Constants.PROPERTY_NAME_ALL_DESTINATIONS_INPUT_ENABLED.equals(key)) {
			bar.setAllDestinations(Boolean.valueOf(value));
		} else if (Constants.PROPERTY_NAME_COMPILE_WITH_DEBUG.equals(key)) {
			bar.setCompileWithDebug(Boolean.valueOf(value));
		} else if (Constants.PROPERTY_NAME_EXTRA_CLASSPATH.equals(key)) {
			bar.setExtraClassPath(value);
		} else if (Constants.PROPERTY_NAME_INCLUDE_ALL_RULESETS.equals(key)) {
			bar.setAllRuleSets(Boolean.valueOf(value));
		} else if (Constants.PROPERTY_NAME_COMPILE_PATH.equals(key)) {
			bar.setCompilePath(value);
		} else if (Constants.PROPERTY_NAME_OM_TANGOSOL_CACHECONFIGFILE.equals(key)) {
			bar.setOmtgCacheConfigFile(value);
		} else if (Constants.PROPERTY_NAME_OM_BDB_DELETE_POLICY.equals(key)) {
			bar.setOmDeletePolicy(Boolean.valueOf(value));
		} else if (Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME.equals(key)) {
			bar.setOmtgGlobalCache(value);
		} else if (Constants.PROPERTY_NAME_OM_BDB_PROPERTY_CACHE_SIZE.equals(key)) {
			bar.setOmCacheSize(Integer.parseInt(value));
		} else if (Constants.PROPERTY_NAME_OM_BDB_CHECKPOINT_INTERVAL.equals(key)) {
			bar.setOmCheckPtInterval(Integer.parseInt(value));
		} else if (Constants.PROPERTY_NAME_OM_ENABLE.equals(key)) {
			System.out.println(key+"::"+value);
//			bar.setOmCheckPtInterval(Integer.parseInt(value));
		} else {
			super.processAttribute(key, value);
		}
	}

	protected void processMap(String key, Map map) {
		System.out.println("found map in BAR: "+key+"::"+map);
		if (Constants.PROPERTY_NAME_DESTINATIONS_WORKERS.equals(key)) {
			Collection values = map.values();
		} else if (Constants.PROPERTY_NAME_DESTINATIONS_QUEUE_SIZE.equals(key)) {
			Collection values = map.values();
		} 
//		else if (SAR_PROP.equals(key)) {
//			
//		} else if (DESIGNER_PROP.equals(key)) {
//			
//		}
	}
	
}
