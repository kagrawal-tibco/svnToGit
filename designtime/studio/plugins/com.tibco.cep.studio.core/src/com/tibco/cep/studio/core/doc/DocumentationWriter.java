package com.tibco.cep.studio.core.doc;

import static com.tibco.cep.studio.core.util.DocUtils.copyFile;
import static com.tibco.cep.studio.core.util.DocUtils.copyString;
import static com.tibco.cep.studio.core.util.DocUtils.getCSSString;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.DocUtils;


/*
@author ssailapp
@date Dec 9, 2011
 */

public class DocumentationWriter {

	private DocumentationDescriptor desc;
	ArrayList<IDocumentationGenerator> generators;
	String defaultCssFile;

	public DocumentationWriter(DocumentationDescriptor desc) { 
		this.desc = desc;
	}

	public boolean write() {
		if (desc == null) {
			return false;
		}

		StudioCorePlugin.log("Generating doc " + desc.project.getName() + " " + desc.location);
		generators = initializeGenerators();
		initializeDestination(desc.location);
		
		String cssString = getCSSString();
		
		try {
			// create doc directory
			new File(desc.location).mkdirs();
			copyString(cssString, new File(desc.location + "/" + "stylesheet.css"));
			defaultCssFile = new File(desc.location + "/" + "stylesheet.css").getAbsolutePath();
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		
		for (IDocumentationGenerator generator : generators) {
			try {
				final StringTemplateManager templateManager = new StringTemplateManager(generator.getStringTemplateGroup());
				boolean completed = generator.generateDocumentation(new Callback() {

					File cssFile = new File(defaultCssFile);
					
					@Override
					public void writeFile(String templateName, Object dataObject, String outFile)
							throws IOException {
						//Create directories if not exist
						File file = new File(outFile);
						file.getParentFile().mkdirs();
						Map<String, Object> attributes = new HashMap<String, Object>();
						if (dataObject != null) {
							attributes.put("dataObject", dataObject);
						}
						attributes.put("cssPath", DocUtils.computeRelativePath(file.getParentFile(),cssFile));
						
						String content = templateManager.processTemplate(templateName, attributes);
						InputStream contentStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
						copyFile(contentStream, new File(outFile));
					}
				});
			} catch (Exception e) {
				StudioCorePlugin.log(e);
				return false;
			}
		}
		return true;
	}

	private void initializeDestination(String destLocation) {
		File destDir = new File(destLocation);
		if (destDir.exists()) {	//TODO - Clean up should warn user
			deleteDir(destDir);
		}
		destDir.mkdirs();
	}
	
	private void deleteDir(File file) {
		if (file.isDirectory()) {
			for (File chdFile: file.listFiles()) {
				deleteDir(chdFile);
			}
		}
		file.delete();
	}

	private ArrayList<IDocumentationGenerator> initializeGenerators() {
		ArrayList<IDocumentationGenerator> generators = new ArrayList<IDocumentationGenerator>();
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor("com.tibco.cep.studio.core.documentationGenerator");
		try {
			for (IConfigurationElement configurationElement : config) {
				final Object generator = configurationElement.createExecutableExtension("class");
				if (generator instanceof IDocumentationGenerator) {
					((IDocumentationGenerator) generator).setDocumentationDescriptor(desc);
					generators.add((IDocumentationGenerator)generator);
				}
			}
		} catch (CoreException ex) {
			ex.printStackTrace();
			StudioCorePlugin.log(ex);
		}
		return generators;
	}
}
