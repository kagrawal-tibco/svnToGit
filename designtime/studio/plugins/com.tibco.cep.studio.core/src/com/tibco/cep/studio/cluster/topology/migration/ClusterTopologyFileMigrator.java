package com.tibco.cep.studio.cluster.topology.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator;

/**
 * 
 * @author smarathe
 *
 */
public class ClusterTopologyFileMigrator extends DefaultStudioProjectMigrator {

	private static Transformer transformer;
	File xmlFile;
	DOMSource domSource;
	@Override
	public int getPriority() {
		return 15;
	}
	
	
	protected void migrateFile(File parentFile, File file,
			IProgressMonitor monitor) {
		
		int idx = file.getName().lastIndexOf('.');
		String ext = null;
		if (idx > -1) {
			ext = file.getName().substring(idx+1);
		}
		if (!"st".equalsIgnoreCase(ext)) {
			return;
		}
		
		processResultDataFile(parentFile, file);
	}

	private void processResultDataFile(File parentFile, File file) {
		this.xmlFile = file;
		Transformer transformer = getTransformer();
		if (transformer == null) {
			return;
		}
		transformer.setParameter("arg1", cep_containerVersion.version);

		InputStream is = null;
		OutputStream os = null;
		File tmpFile = null;
		try {
			is = new FileInputStream(file);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			tmpFile = new File(file.getAbsolutePath()+"_tmp");
			os = new FileOutputStream(tmpFile);
			transformer.transform(domSource, new StreamResult(os));
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
	
	private Transformer getTransformer() {
	//	System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");	
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		if (transformer == null) {
			f.setNamespaceAware(false);
			InputStream xsltFile = getClass().getResourceAsStream("ClusterTopologyFileMigration.xslt");
			Source xsltSource = new StreamSource(xsltFile);
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates templates = null;
			try {
				templates = factory.newTemplates(xsltSource);
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			try {
				if (templates != null) {
					transformer = templates.newTransformer();
				}
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
		}
		Document dom = null;
		try {
			dom = f.newDocumentBuilder().parse(xmlFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		domSource = new DOMSource(dom);
		return transformer;
	}

}
