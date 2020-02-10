package com.tibco.cep.studio.ui.wizards;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.container.cep_containerVersion;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;


/*
@author ssailapp
@date Sep 21, 2009 2:30:13 PM
 */

public class NewClusterConfigNamePage extends StudioNewFileWizardPage {

	private String omType;
	
    public NewClusterConfigNamePage(IStructuredSelection selection) {
        super("NewClusterConfigFileWizardPage", selection);
        setTitle(Messages.getString("new.clusterconfiguration.wizard.title"));
        setDescription(Messages.getString("new.clusterconfiguration.wizard.desc"));
        setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/cdd_48x48.gif"));
        setFileExtension("cdd");
    }

    protected boolean validatePage() {
    	return (validateSharedResourceFileName() && super.validatePage());
    }
    
    //@Override
    protected InputStream getInitialContents() {
		String file = "";
		if (omType.equalsIgnoreCase(NewClusterConfigTemplatePage.OM_TYPE_IN_MEMORY))
			file = "memory.cdd";
		else if (omType.equalsIgnoreCase(NewClusterConfigTemplatePage.OM_TYPE_CACHE))
			file = "cache.cdd";
//		else if (omType.equalsIgnoreCase(NewClusterConfigTemplatePage.OM_TYPE_BDB))
//			file = "bdb.cdd";
		
		InputStream contents = null;
		try {
			contents = StudioUIPlugin.getDefault().getBundle().getEntry("/templates/" + file).openStream();
			String name = getFileName();
			if (name!= null){
				name = name.replaceAll("\\."+getFileExtension(), "");
			//	name= URLEncoder.encode(name, "UTF-8");
			}
			else
				name = "ClusterName";
			String contentsStr = processTemplate(contents, name);
			contents = new ByteArrayInputStream(contentsStr.getBytes("UTF-8"));
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

	public void setOmType(String omType) {
		this.omType = omType;
	}
	
	private String processTemplate(InputStream inputStream, String clusterName) {
		String contentsStr = "";
		try {
			contentsStr = getString(inputStream);
			String userName = System.getProperty("user.name");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String dateTime = dateFormat.format(date);			
			String beHome = System.getProperty("BE_HOME");
			String beVersionShort = cep_containerVersion.version.substring(0, 3);
			
			contentsStr = contentsStr.replaceAll("%%UserName%%", userName);
			contentsStr = contentsStr.replaceAll("%%ClusterName%%", clusterName);
			contentsStr = contentsStr.replaceAll("%%DateTime%%", dateTime);
			contentsStr = contentsStr.replaceAll("%%BeVersionShort%%", beVersionShort);			
			
			if (beHome != null && !beHome.trim().equals(""))
				contentsStr = contentsStr.replaceAll("%%BE_HOME%%", beHome);
			
		} catch (IOException ioe) {
		}
		return contentsStr;
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