package com.tibco.cep.bpmn.ui.wizards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.be.model.util.EntityNameHelper;
import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonPaletteResourceUtil;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.wizards.StudioNewFileWizardPage;

/**
 * 
 * @author mgoel
 *
 */
public class NewBpmnPaletteNamePage extends StudioNewFileWizardPage{

	public NewBpmnPaletteNamePage(IStructuredSelection selection) {
		super("NewBpmnPaletteFileWizardPage",selection);
		setTitle(Messages.getString("new.bpmnpalette.wizard.title"));
        setDescription(Messages.getString("new.bpmnpalette.wizard.desc"));
        setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/palette.gif"));
        setFileExtension(BpmnCoreConstants.PALETTE_FILE_EXTN);
	}
	
	 protected boolean validatePage() {
	    	String name = getFileName().replaceAll("."+getFileExtension(), "");
	    	
	    	if(name.length() == 0){
	    		setErrorMessage(Messages.getString("Empty_Name"));
	    		return false;
	    	}
	    	
	    	//if (!EntityNameHelper.isValidSharedResourceIdentifier(name)){
	    	if (!ModelNameUtil.isValidSharedResourceIdentifier(name)) {
	    		String problemMessage = Messages.getString("BE_Resource_invalidFilename", name);
	    		setErrorMessage(problemMessage);
	    		return false;
	    	}
	    	boolean containsInvalidChar = EntityNameHelper.containsInvalidNameChars(name, false);
	    	if(containsInvalidChar){
	    		String problemMessage = Messages.getString("BE_Resource_invalidFilename", name);
	    		setErrorMessage(problemMessage);
	    		return false;
	    	}
	    	
	    	//Validation for Bad Folder
	    	if (!StudioUIUtils.isValidContainer(getContainerFullPath())){
	    		String problemMessage = null;
	    		if(getContainerFullPath() == null){
	    		problemMessage = Messages.getString("No_folder_specified");
	    		setMessage(problemMessage);
	    		setErrorMessage(null);
	    		}
	    		else if (getContainerFullPath() != null) {
	    		problemMessage = com.tibco.cep.studio.core.util.Messages.getString("Resource.folder.bad", getContainerFullPath());
	    		setErrorMessage(problemMessage);
	    		}
	    		return false;
	    	}
	    	
	    	IResource resource  = StudioResourceUtils.getResourcePathFromContainerPath(getContainerFullPath());
	    	StringBuilder duplicateFileName  = new StringBuilder("");
	    	if(isDuplicateBEResource(resource,resourceContainer.getResourceName(),duplicateFileName)){
	    		String problemMessage = Messages.getString("BE_Resource_FilenameExists", duplicateFileName ,resourceContainer.getResourceName());
	    		setErrorMessage(problemMessage);
	    		return false;
	    	}
	    	return (super.validatePage());
	    }


	    protected InputStream getInitialContents() {
			@SuppressWarnings("unused")
			String file = "";
			file = BpmnCoreConstants.DEFAULT_BEBPMNPALETTE;
			//file="bdb.cdd";
			InputStream contents = null;
			try {
				
				List<URL> urlList = new ArrayList<URL>();
				Enumeration<URL> e =BpmnCommonPaletteResourceUtil.getDefaultBePaletteSysResEnumerationList();
				while (e.hasMoreElements()) {
	        		urlList.add(e.nextElement());
	        	}
	        	e = BpmnCommonPaletteResourceUtil.getDefaultBePaletteResEnumerationList();
	        	
	        	while (e.hasMoreElements()) {
	        		urlList.add(e.nextElement());
	        	}
				contents = urlList.get(0).openStream();
				String name = getFileName();
				if (name!= null)
					name = name.replaceAll("."+getFileExtension(), "");
				else
					name = "ProcessPalette";
				/*String contentsStr = processTemplate(contents, name)
				 * ;
				contents = new ByteArrayInputStream(contentsStr.getBytes("UTF-8"));*/
			} catch (IOException e) {
		    }
			return contents;
	    }
	    
	  public boolean canFlipToNextPage() {
	    	if (getErrorMessage() != null)
	    		return false;
	    	String name = getFileName();
	    	if (name == null || name.trim().equals(""))
	    		return false;
	       	return true;
	    }
	  
	  public String getString(InputStream inputStream) throws IOException {
			StringBuilder sb = new StringBuilder();
			String line;
			if (inputStream != null) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
					while ((line = reader.readLine()) != null) {
						sb.append(line).append("\n");
					}
				} finally {
					inputStream.close();
				}
				return sb.toString();
			} else {       
				return "";
			}
		}
}
