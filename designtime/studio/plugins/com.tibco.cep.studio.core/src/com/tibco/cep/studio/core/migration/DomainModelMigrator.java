package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * Migrate Single entry values of all types of Domains from 4.x format (EJavaObject - HEX Strings representation) 
 * to 5.1 (plain String representation)   
 * 
 * @author vdhumal
 *
 */
public class DomainModelMigrator extends DefaultStudioProjectMigrator {

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if ("domain".equalsIgnoreCase(ext)) {
			monitor.subTask(" Attempting Domain Model migration " + file.getName());
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				Domain domain = (Domain) CommonIndexUtils.deserializeEObject(fileInputStream);
				if (migrateDomain(file, domain)) {
					saveDomain(file, domain);
				}
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
	}

	/**
	 * @param resourceFile DomainModel file
	 * @param domain 
	 * @return boolean true if migrated else false 
	 */
	public boolean migrateDomain(File resourceFile, Domain domain) {
		
		EList<DomainEntry> domainEntries = domain.getEntries();
		boolean migrate = false;
		for (DomainEntry domainEntry : domainEntries) {
			if (domainEntry instanceof Single) {				
				Single singleEntry = (Single) domainEntry;
				String value = singleEntry.getValue();				
				if (value != null && value.length() > 0) {  										
					try {
						//try converting the Domain from HEX String representation to plain String representation
						EcorePackage ecorePackage = EcoreFactory.eINSTANCE.getEcorePackage();
						Object obj = EcoreUtil.createFromString(ecorePackage.getEJavaObject(), value);
						if (obj != null && (obj instanceof String)) {
							singleEntry.setValue((String) obj);
							migrate = true;
						}	
					} catch (Exception ex) {
						//Domain is actually in plain String representation, Do nothing
					}
				}	
			}
		}

		return migrate;	
	}

	/**
	 * Saves the Domain Model
	 * 
	 * @param resourceFile
	 *            File
	 * @param Domain
	 *            Domain Model to be saved to File
	 * @throws IOException
	 */
	private void saveDomain(File resourceFile, Domain domain) throws IOException {
		String resourceLocation = resourceFile.getAbsolutePath();
		XMIResource resource = new XMIResourceImpl(URI.createFileURI(resourceLocation));
		resource.getContents().add(domain);
		InputStream is = null;
		OutputStream os = null;
		File tmpFile = null;
		try {
			is = new FileInputStream(resourceFile);
			if (!resourceFile.getParentFile().exists()) {
				resourceFile.getParentFile().mkdirs();
			}
			tmpFile = new File(resourceFile.getAbsolutePath() + "_tmp");
			os = new FileOutputStream(tmpFile);
			resource.save(os, null);
		} catch (FileNotFoundException e) {
			StudioCorePlugin.log(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e2) {
				// Do Nothing
			}
		}
		if (tmpFile != null) {
			if (resourceFile.exists()) {
				resourceFile.delete();
			}
			tmpFile.renameTo(resourceFile);
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
		}
	}
		
}
