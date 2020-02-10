package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * This class should be run after column order has been migrated.
 *
 */
public class BEProjectConfigurationMigrator extends DefaultStudioProjectMigrator {

	private static Transformer transformer;

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		if (".beproject".equalsIgnoreCase(file.getName())) {
			monitor.subTask("- Converting Project Configuration file " + file.getName());
			processBEConfigFile(parentFile, file);
			return;
		}
	}

	private void processBEConfigFile(File parentFile, File file) {
		Transformer transformer = getTransformer();
		if (transformer == null) {
			StudioCorePlugin.log("Could not initialize project configuration transformer, .beproject not converted");
			return;
		}

		InputStream is = null;
		OutputStream os = null;
		File tmpFile = null;
		try {
			is = new FileInputStream(file);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			tmpFile = new File(file.getAbsolutePath() + "_tmp");
			// TODO : check whether this file already exists?
			os = new FileOutputStream(tmpFile);
			transformer.transform(new StreamSource(is), new StreamResult(os));
		} catch (FileNotFoundException e) {
			StudioCorePlugin.log(e);
		} catch (TransformerConfigurationException e) {
			StudioCorePlugin.log(e);
		} catch (TransformerException e) {
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
			}
		}
		if (tmpFile != null) {
			if (file.exists()) {
				file.delete();
			}
			tmpFile.renameTo(file);
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
		}
		updateVersionInfo(file);
	}

	private void updateVersionInfo(File file) {
		try {
			EObject eObj = IndexUtils.loadEObject(file.toURI());
			if (eObj instanceof StudioProjectConfiguration) {
				StudioProjectConfiguration config = (StudioProjectConfiguration) eObj;
				config.setVersion(cep_commonVersion.version);
				config.setBuild(cep_commonVersion.build);
				if (xpathVersion != null) {
					config.setXpathVersion(xpathVersion);
				}
				eObj.eResource().save(ModelUtils.getPersistenceOptions());
			}
		} catch (Exception e) {
		}
		
	}

	private Transformer getTransformer() {
//		System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");	
		if (transformer == null) {
			ClassLoader classLoader = BEProjectConfigurationMigrator.class.getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("com/tibco/cep/studio/core/migration/MigrateBEProject.xsl");
			try {
				if (inputStream == null) {
					throw new RuntimeException("Could not load MigrateBEProject.xsl");
				}
				StreamSource streamSource = new StreamSource(inputStream);
				Templates templates = TransformerFactory.newInstance().newTemplates(streamSource);
				transformer = templates.newTransformer();
			} catch (TransformerConfigurationException e) {
				StudioCorePlugin.log(e);
			}
		}
		return transformer;
	}

	@Override
	public int getPriority() {
		return 1; 
	}
}
