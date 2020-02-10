package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;

/*
@author ssailapp
@date Feb 25, 2011
 */

public class CddDomainObjectMigrator extends DefaultStudioProjectMigrator {

	private String entityPath;
	private LinkedHashMap<String, String> entityProps;
	private LinkedHashMap<String,LinkedHashMap<String, String>> propProps;

	public CddDomainObjectMigrator(String entityPath, LinkedHashMap<String, String> entityProps, LinkedHashMap<String,LinkedHashMap<String, String>> propProps) {
		this.entityPath = entityPath;
		this.entityProps = entityProps;
		this.propProps = propProps;
	}

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!"cdd".equalsIgnoreCase(ext)) {
			return;
		}
		monitor.subTask("- Updating CDD file "+file.getName());
		processCddFile(parentFile, file, monitor);
	}

	private void processCddFile(File parentFile, File file, IProgressMonitor monitor) {
		String filePath = file.getAbsolutePath();
		ClusterConfigModelMgr modelmgr = new ClusterConfigModelMgr(null, filePath);
		modelmgr.parseModel();

		CacheOm cacheOm = modelmgr.getModel().om.cacheOm;

		ArrayList<DomainObject> list = cacheOm.domainObjects.domainObjOverrides.overrides;
		if (list.size() == 0) {
			return;
		}
		
		boolean isDomainOverridePresent = false;
		
		for (DomainObject obj: list) {
			if (obj.entity == null || obj.entity.getEntity() == null) {
				continue;
			}
			if (obj.entity.getEntity().getFullPath().equals(entityPath)) {
				isDomainOverridePresent = true;
				break;
			}
		}
		
		if (isDomainOverridePresent) {
			modelmgr.updateDomainObject(entityPath, entityProps, propProps);
			String clusterConfigText = modelmgr.saveModel(true);
			try {
				FileWriter writer = new FileWriter(file);
				writer.write(clusterConfigText);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public int getPriority() {
		return 10;
	}
}
