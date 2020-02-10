package com.tibco.cep.studio.core.migration;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.be.util.SimpleXslTransformer;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;


public class CddMigrator extends DefaultStudioProjectMigrator {

	private static SimpleXslTransformer transformer;

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!"cdd".equalsIgnoreCase(ext)) {
			return;
		}
		monitor.subTask("- Converting CDD file "+file.getName());
		transformCDDFile(parentFile, file);
		migrateCDDFile(file);
	}

	private void transformCDDFile(File parentFile, File file) {
		SimpleXslTransformer transformer = getTransformer();
		if (transformer == null) {
			System.err.println("Could not initialize CDD transformer, CDDs not converted");
			return;
		}

		FileInputStream xmlInStream = null;
		File tmpFile = null;
		BufferedWriter out = null;
		ByteArrayOutputStream bos = null;
		StreamResult sr = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			tmpFile = new File(file.getAbsolutePath()+"_tmp");
			
			//BE-24348 multibyte character support in migration. @rjain			
			xmlInStream = new FileInputStream(file);
			
	        bos = new ByteArrayOutputStream();
	        sr = new StreamResult(new OutputStreamWriter(bos, "UTF-8"));
	        
	        transformer.transform(new StreamSource(new InputStreamReader(xmlInStream,StandardCharsets.UTF_8)), sr);
	        
	        byte[] outputBytes = bos.toByteArray();
	        String strContentString = new String(outputBytes, "UTF-8");
	        
	        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8"));
			out.write(strContentString);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(xmlInStream != null){
					xmlInStream.close();
				}
				if(out != null){
					out.close();
				}
				if(bos !=null){
					bos.close();
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
			InputStream inputStream = SimpleXslTransformer.class.getResourceAsStream("Cdd4Or50Or51OrCdd52OrCdd53OrCdd54OrCdd55ToCdd56.xslt");
			try {
				transformer = new SimpleXslTransformer(inputStream);
			} catch (TransformerConfigurationException e1) {
				e1.printStackTrace();
			}
		}
		return transformer;
	}

	private void migrateCDDFile(File file) {
		ClusterConfigModelMgr modelmgr = new ClusterConfigModelMgr(null, file.getAbsolutePath());
		modelmgr.parseModel();
		boolean updated = modelmgr.forceEnableCacheStorageForProcessingUnits();
		updated |= modelmgr.addSecurityAuthProperties();
		updated |= modelmgr.migrateBackingStoreTo510();
		updated |= modelmgr.migrateLoadBalancer();
		updated |= modelmgr.migrateASPropertiesTo512();
		if (updated) {
			String clusterConfigText = modelmgr.saveModel(true);
			try {
				//FileWriter writer = new FileWriter(file);
				//writer.write(clusterConfigText);
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				out.write(clusterConfigText);
				out.close();
				//writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int getPriority() {
		return 1; // high priority, run before others
	}

}
