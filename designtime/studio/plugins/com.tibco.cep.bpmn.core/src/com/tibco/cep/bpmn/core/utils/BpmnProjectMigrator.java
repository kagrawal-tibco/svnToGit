package com.tibco.cep.bpmn.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.migration.DefaultStudioProjectMigrator;
import com.tibco.cep.studio.core.util.StudioProjectMigrationUtils;



public class BpmnProjectMigrator extends DefaultStudioProjectMigrator {

	private static String BEPROCESS="beprocess";
	private static String BEPROCESSPALETTE="beprocesspalette";
	private static Transformer TRANSFORMER;
	
	static {
		//load xslt
		ClassLoader classLoader = BpmnProjectMigrator.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("com/tibco/cep/bpmn/core/utils/BpmnMigrator.xsl");
		if (inputStream != null) {
			StreamSource streamSource = new StreamSource(inputStream);
			try {
				Templates templates = TransformerFactory.newInstance().newTemplates(streamSource);
				TRANSFORMER = templates.newTransformer();
			} catch (TransformerConfigurationException e) {
				StudioCorePlugin.log(e);
			} catch (TransformerFactoryConfigurationError e) {
				StudioCorePlugin.log(e);
			}
		}
	}
	
	
	
	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	protected void migrateFile(File parentFile, File file,
			IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		
		//Code for migrating process palette
		if(BEPROCESSPALETTE.equalsIgnoreCase(ext)){
			migrateProcessPalette(parentFile,file,monitor);
			return;
		}
		//Code for migrating process file
		if(BEPROCESS.equalsIgnoreCase(ext)){
			migrateProcess(parentFile,file,monitor);
			return;
		}
		return ;
	}

	private void migrateProcess(File parentFile, File file,
			IProgressMonitor monitor){
		
		monitor.subTask("- Converting BPMN file "+file.getName());
		EObject eObj = CommonIndexUtils.loadEObject(file.toURI(), true);
		URI uri = URI.createURI(file.toURI().toString());
		try {
			if(eObj != null){
				EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(eObj);
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(parentFile.getName());
				boolean flag=BpmnModelUtils.checkForUniqueId(wrapper);
				if(flag){
					ECoreHelper.serializeModelXMI(project,uri,eObj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void migrateProcessPalette(File parentFile, File file,
			IProgressMonitor monitor){
		
		monitor.subTask("- Converting BPMN Palette file "+file.getName());
		String fileName = file.getName();   //Get Name
		File parentDir = file.getParentFile();   //Get parent dir
		String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_modified" + "." + BEPROCESSPALETTE;

		File newFile = new File(parentDir, newFileName);
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(newFile);
			transform(file, fos);
		}catch(Exception e){
			newFile.delete();
			return;
		}
		finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		   boolean isTransformed = StudioProjectMigrationUtils.checkIfFileTransformed(newFile, file);
		    if (!isTransformed) {
		    	newFile.delete();
		    	return;
		    }
		    
		    String backupName = fileName.substring(0, fileName.lastIndexOf('.')) +"."+ BEPROCESSPALETTE + ".orig";
		    File backupFile = new File(parentDir, backupName);
		    file.renameTo(backupFile);
		    file = backupFile;

		    //Rename modified to original
		    File origFile = new File(parentDir, fileName);
		    newFile.renameTo(origFile);  
		    
		    file.delete();
		
	}

	private static void transform(File processFile,
			FileOutputStream newFileOutputStream) throws Exception {

		if (TRANSFORMER != null) {

			//Create source file source
			StreamSource xmlSource = new StreamSource(processFile);
			StreamResult outputResult = new StreamResult(newFileOutputStream);
			TRANSFORMER.transform(xmlSource, outputResult);
		}
	}
	
}
