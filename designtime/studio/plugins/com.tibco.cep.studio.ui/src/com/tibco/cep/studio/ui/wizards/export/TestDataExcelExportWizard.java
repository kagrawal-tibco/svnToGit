/**
 * 
 */
package com.tibco.cep.studio.ui.wizards.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.testdata.exportHandler.TestDataExportHandler;
import com.tibco.cep.studio.ui.decorators.TestDataLabelDecorator;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;


/**
 * @author mgujrath
 *
 */
public class TestDataExcelExportWizard extends ExportExcelWizard<TestDataExcelExportWizard,TestDataLabelDecorator>{
	
	@Override
	public void addPages() {
		if (selection.isEmpty()) {
			MessageDialog.openError(workbenchWindow.getShell(), "Export", "Cannot Export without selecting a resource");
			dispose();
			return;
		} else {
			addPage(page = new TestDataExportWizardPage(Messages.getString("Export.title"),workbenchWindow, selection));
		}
	}
	
	
	@Override
	public boolean performFinish() {
		String filePath = page.getExportFilePath();
		if(new File(filePath).exists()){
			if(!MessageDialog.openConfirm(workbenchWindow.getShell(),"Export", "The file already exists. Do you want to overwrite?")){
				return false;
			}
		}
		boolean success=export(page.selectedFile, filePath);
		if(success){
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					"Export", Messages.getString("Export.Excel.Success", "Test Data"));
		}
		return true;
	}
	
	public boolean export(IResource resource,String targetPath){
		String resourcePath = resource.getFullPath().lastSegment();
		String [] splits=resourcePath.split("\\.");
		String entityName=splits[0];
		
		String projectName = StudioResourceUtils.getCurrentProject(selection).getName();
		List<Entity> enlist=CommonIndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		enlist.addAll(CommonIndexUtils.getAllEntities(projectName, ELEMENT_TYPES.SCORECARD));
		enlist.addAll(CommonIndexUtils.getAllEntities(projectName, ELEMENT_TYPES.SIMPLE_EVENT));
	
		Entity entity=null;
		String relatedEntityName="";
		
		String relatedEntityPath=getEntityInfo(resource.getLocation().toString());
		
		if(relatedEntityPath!=null && relatedEntityPath.trim().length()!=0)
			relatedEntityName = relatedEntityPath.substring(relatedEntityPath.lastIndexOf("/")+1, relatedEntityPath.length());

		
		String excelFilePath=targetPath;
		for(Entity en:enlist){
			if(en.getName().equalsIgnoreCase(relatedEntityName)){
				entity=en;
				break;
			}
		}
		
		TestDataExportHandler exportHandler=new TestDataExportHandler(projectName,excelFilePath,null,null, entity,resource.getLocation().toString());
		try {
			exportHandler.exportTestData(true);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				MessageDialog.openError(workbenchWindow.getShell(), 
						"Export", Messages.getString("Export.Excel.Failure.File.Open",resource.getLocation().toString() ));
			}
		}
		return true;
	}

	public static String getEntityInfo(String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			fileName=fileName.replaceFirst("/TestData", "");
			file = new File(fileName);
		}
		if (file.exists() && file.length() > 0) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				XiNode rootNode = XiParserFactory.newInstance().parse(
						new InputSource(fis));
				XiNode mainNode = rootNode.getFirstChild();
				XiNode entityPathNode=mainNode.getAttribute(ExpandedName.makeName("entityPath"));
				if(entityPathNode!=null){
					return entityPathNode.getStringValue();
				}
				Iterator<XiNode> subNodeIterator = mainNode.getChildren();
				while (subNodeIterator.hasNext()) {
					XiNode childNode = subNodeIterator.next();
					String splits[]=childNode.getName().toString().split("www.tibco.com/be/ontology");
					return (splits[1].split("}"))[0];
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return fileName;
	}
}
