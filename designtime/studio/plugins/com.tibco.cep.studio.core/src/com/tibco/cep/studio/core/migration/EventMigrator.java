package com.tibco.cep.studio.core.migration;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.tools.ChannelMigrator;

/**
 * 
 * @author moshaikh
 *
 */
public class EventMigrator extends EntityMigrator {
	
	private static Transformer eventMigratorTransformer;

	public EventMigrator() {
		extension = CommonIndexUtils.EVENT_EXTENSION;
	}
	
	@Override
	protected void migrateFile(File parentFile, File file,
			IProgressMonitor monitor) {
		super.migrateFile(parentFile, file, monitor);
		
		String outContent = transform_addPropOwner(file);
		if(outContent != null) {
			BufferedWriter out;
			try {
				//out = new BufferedWriter(new FileWriter(file));										//BE-24348 multibyte character support in migration. @rjain
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				out.write(outContent);
				out.close();
			} catch (IOException e) {
				StudioCorePlugin.log(e);
			}
		}
	}
	
	/**
	 * Transforms the event xml to fix properties with missing ownerPath and ownerProjectName. (BE-21005)
	 * @param file
	 * @return
	 */
	private static String transform_addPropOwner(File file)  {
		try {
			String ext = fileExtension(file);
			if (!"event".equalsIgnoreCase(ext)) {
				return null;
			}
			if (eventMigratorTransformer == null) {
				initializeEventTransformer();
			}
			FileInputStream xmlInStream = new FileInputStream(file);
			
			//BE-24348 multibyte character support in migration. @rjain
			//StringWriter writer = new StringWriter();
			//eventMigratorTransformer.transform(new StreamSource(new InputStreamReader(xmlInStream)), new StreamResult(writer));
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        StreamResult sr = new StreamResult(new OutputStreamWriter(bos, "UTF-8"));
			eventMigratorTransformer.transform(new StreamSource(new InputStreamReader(xmlInStream,StandardCharsets.UTF_8)), sr);
			byte[] outputBytes = bos.toByteArray();
	        String strRepeatString = new String(outputBytes, "UTF-8");
	        return strRepeatString;
			//writer.flush();
			//return writer.toString();
		}
		catch(Exception e){
			StudioCorePlugin.log(e);
		}
       	return null;
	}
	
	/**
	 * Initializes the xsl transformer for event.
	 * @throws Exception
	 */
	private static void initializeEventTransformer() throws Exception {
		ClassLoader classLoader = ChannelMigrator.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("com/tibco/cep/studio/core/migration/EventMigration.xslt");
		TransformerFactory tFactory = TransformerFactory.newInstance();
		eventMigratorTransformer = tFactory.newTransformer(new StreamSource(inputStream));
	}
	
	private static String fileExtension(File file) {
		int idx = file.getName().lastIndexOf('.');
		String ext = null;
		if (idx > -1) {
			ext = file.getName().substring(idx+1);
		}
		return ext;
	}
}
