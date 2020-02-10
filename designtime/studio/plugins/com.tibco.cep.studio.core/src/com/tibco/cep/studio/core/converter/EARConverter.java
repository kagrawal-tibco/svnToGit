package com.tibco.cep.studio.core.converter;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;

public class EARConverter extends ArchiveResourceConverter {

	public static final String BAR_PROP = "BusinessEventsArchives";
	public static final String SAR_PROP = "sharedArchive";
	public static final String DESIGNER_PROP = "designer"; // what is this?
	
	public static final String VERSION_PROP = "versionProperty";
	public static final String FILE_LOC_PROP = "fileLocationProperty";
	public static final String GLOBAL_VARS_PROP = "addServiceSettableGvars";
	
	public EARConverter(ArchiveResource resource) {
		super(resource);
	}

	protected void processAttribute(String key, String value) {
		if (VERSION_PROP.equals(key)) {
			((EnterpriseArchive)fCurrentResource).setVersion(value);
		} else if (FILE_LOC_PROP.equals(key)) {
			((EnterpriseArchive)fCurrentResource).setFileLocation(value);
		} else if (GLOBAL_VARS_PROP.equals(key)) {
			((EnterpriseArchive)fCurrentResource).setIncludeGlobalVars(Boolean.valueOf(value));
		}
		super.processAttribute(key, value);
	}

	protected void processMap(String key, Map map) {
		if (BAR_PROP.equals(key)) {
			
			if (map.size() > 0) {
				Set keySet = map.keySet();
				for (Object archiveKey : keySet) {
					Map archiveMap = (Map) map.get(archiveKey);
					BusinessEventsArchiveResource bar = ArchiveFactory.eINSTANCE.createBusinessEventsArchiveResource();
					new BARConverter(bar).convertArchive((String)archiveKey, archiveMap);
					((EnterpriseArchive)fCurrentResource).getBusinessEventsArchives().add(bar);
				}
			}
			
		} else if (SAR_PROP.equals(key)) {

			SharedArchive sar = ArchiveFactory.eINSTANCE.createSharedArchive();
			sar.setName("Shared Archive");
			Map archiveMap = (Map) map;
			new SARConverter(sar).convertArchive((String)key, archiveMap);
			((EnterpriseArchive)fCurrentResource).getSharedArchives().add(sar);

//			if (map.size() > 0) {
//				Set keySet = map.keySet();
//				for (Object archiveKey : keySet) {
//					Object object = map.get(archiveKey);
//					if (object instanceof Map) {
//						Map archiveMap = (Map) map.get(archiveKey);
//						new SARConverter(bar).convertArchive((String)archiveKey, archiveMap);
//						((EnterpriseArchive)fCurrentResource).getSharedArchives().add(bar);
//					} else {
//						System.out.println("string attribute: "+archiveKey+":::"+object);
//					}
//				}
//			}
			
		} else if (DESIGNER_PROP.equals(key)) {
		}
	}

}
