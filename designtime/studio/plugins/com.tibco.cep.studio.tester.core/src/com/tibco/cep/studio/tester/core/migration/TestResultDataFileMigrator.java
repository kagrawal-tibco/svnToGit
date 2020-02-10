package com.tibco.cep.studio.tester.core.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.be.util.SimpleXslTransformer;
import com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator;

/**
 * 
 * @author smarathe
 *
 */
public class TestResultDataFileMigrator extends DefaultStudioProjectMigrator {

	private static SimpleXslTransformer transformer;
	
	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!"resultdata".equalsIgnoreCase(ext)) {
			return;
		}
		
		processResultDataFile(parentFile, file);
	}

	private void processResultDataFile(File parentFile, File file) {
		SimpleXslTransformer transformer = getTransformer();
		if (transformer == null) {
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
			tmpFile = new File(file.getAbsolutePath()+"_tmp");
			// TODO : check whether this file already exists?
			os = new FileOutputStream(tmpFile);
			transformer.transform(is, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
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
	}
	
	private SimpleXslTransformer getTransformer() {
//		System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");	
		if (transformer == null) {
			InputStream inputStream = getClass().getResourceAsStream("TestResultMigrator.xslt");
			try {
				transformer = new SimpleXslTransformer(inputStream);
			} catch (TransformerConfigurationException e1) {
				e1.printStackTrace();
			}
		}
		return transformer;
	}

}
