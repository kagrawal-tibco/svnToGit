package com.tibco.cep.bpmn.core.doc;

import static com.tibco.cep.studio.core.doc.DocumentationConstants.KEY_CLASS_DOCUMENTATION;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.KEY_CLASS_NAME;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.KEY_PACKAGE_CLASS_ELEMENTS;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.KEY_PACKAGE_NAME;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATE_FILE_CLASS;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATE_FILE_PACKAGE_FRAME;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATE_LOCATION_PKG;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.DEFAULT_CSS;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.doc.AbstractDocumentationGenerator;
import com.tibco.cep.studio.core.doc.Callback;
import com.tibco.cep.studio.core.doc.ClassElement;
import com.tibco.cep.studio.core.doc.DocumentationDescriptor;
import com.tibco.cep.studio.core.doc.DocumentationWriter;
import com.tibco.cep.studio.core.doc.PackageElement;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;

/*
@author ssailapp
@date Dec 8, 2011
 */

public class BpmnDocumentationGenerator extends AbstractDocumentationGenerator {
	
	String defaultCss ;
	public BpmnDocumentationGenerator() {
		super();
		
	}

	public boolean generate(DocumentationDescriptor docDesc) {
		EObject bpmnIndex = BpmnModelCache.getInstance().getIndex(desc.project.getName());
		ProcessIndex index = new DefaultBpmnIndex(bpmnIndex);
		Collection<EObject> processes = index.getAllElementsByType(BpmnModelClass.PROCESS);
		for (EObject process: processes) { 
			generate(process);
		}
		return true;
	}

	private void generate(EObject process) {
		try {
			DefaultProcessIndex index = new DefaultProcessIndex(process);
			Collection<EObject> flowNodes = index.getAllFlowNodes();
			for (EObject flowNode: flowNodes) {
				ROEObjectWrapper<EClass, EObject> wrapper = ROEObjectWrapper.wrap(flowNode);
				EObject docObject = (EObject) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
				ROEObjectWrapper<EClass, EObject> docWrapper = ROEObjectWrapper.wrap(docObject);
				@SuppressWarnings("unused")
				String docString = (String) docWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<PackageElement> getPackageElements() {
		defaultCss = Platform.getPreferencesService().getString(StudioCorePlugin.PLUGIN_ID,
				StudioCorePreferenceConstants.DOC_GEN_CSS_LOCATION,	"", null);
		defaultCss = defaultCss.replace("\\","/");
		ArrayList<PackageElement> elements = new ArrayList<PackageElement>();
		EObject bpmnIndex = BpmnModelCache.getInstance().getIndex(desc.project.getName());
		ProcessIndex index = new DefaultBpmnIndex(bpmnIndex);
		Collection<EObject> processes = index.getAllElementsByType(BpmnModelClass.PROCESS);
		for (EObject process: processes) { 
			PackageElement pkg = new PackageElement();
			DefaultProcessIndex processIndex = new DefaultProcessIndex(process);
			String name = processIndex.getName();
			pkg.setName(name);
			String link = createPackageClassesFile(processIndex);
			pkg.setLink(link);
			
			/*
			ROEObjectWrapper<EClass, EObject> wrapper = ROEObjectWrapper.wrap(process);
			String name1 = (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			*/
			
			elements.add(pkg);
		}
		return elements;
	}

	@SuppressWarnings("unchecked")
	private String createPackageClassesFile(DefaultProcessIndex processIndex) {
		String processName = processIndex.getName();
		String linkDirName = desc.location + "/" + processName;
		new File(linkDirName).mkdirs();
		String linkFileName = TEMPLATE_FILE_PACKAGE_FRAME;
		DocumentationWriter.copyTemplates(TEMPLATE_LOCATION_PKG, linkDirName, linkFileName);
		String link = linkDirName + "/" + linkFileName; 
		
		String text = "";
		ArrayList<EObject> elements = new ArrayList<EObject>();
		elements.addAll(processIndex.getAllFlowNodes());
		elements.addAll(processIndex.getAllSequenceFlows());
		
		for (EObject element: elements) {
			ROEObjectWrapper<EClass, EObject> wElement = ROEObjectWrapper.wrap(element);
			String elementName = (String) wElement.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			EList<EObject> docList = (EList<EObject>) wElement.getAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			
			for (EObject docObject: docList) {
				String classFileLinkName = createClassFile(processName, elementName, docObject);
				text += getClassesText(classFileLinkName, elementName);
			}
		}
		
		DocumentationWriter.replaceContents(link, KEY_PACKAGE_NAME, processName);
		DocumentationWriter.replaceContents(link, KEY_PACKAGE_CLASS_ELEMENTS, text);
		DocumentationWriter.replaceContents(link, DEFAULT_CSS,"\""+ defaultCss +"\"");
		
		return (processName + "/" + linkFileName);
	}
	
	private String getClassFileLinkName(String parentDirName, String elementName) {
		String link = desc.location + "/" + parentDirName + "/" + elementName+ ".html";
		return link;
	}
	
	private String createClassFile(String resourceName, String elementName, EObject docObject) {
		String linkFileName = TEMPLATE_FILE_CLASS;
		DocumentationWriter.copyTemplates(TEMPLATE_LOCATION_PKG, desc.location + "/" + resourceName, linkFileName);
		String link = getClassFileLinkName(resourceName, elementName);
		File oldFile = new File(desc.location + "/" + resourceName + "/" + linkFileName);
		File linkFile = new File(link);
		oldFile.renameTo(linkFile);
		
		ROEObjectWrapper<EClass, EObject> wDoc = ROEObjectWrapper.wrap(docObject);
		String docString = (String) wDoc.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
		
		DocumentationWriter.replaceContents(link, KEY_PACKAGE_NAME, resourceName);
		DocumentationWriter.replaceContents(link, KEY_CLASS_NAME, elementName);
		DocumentationWriter.replaceContents(link, KEY_CLASS_DOCUMENTATION, docString);
		DocumentationWriter.replaceContents(link, DEFAULT_CSS,"\""+ defaultCss +"\"");
		return link;
	}

	private String getClassesText(String classFileLinkName, String className) { 
		String text = "";
		text += "<BR><A HREF=\"";
		text += classFileLinkName; 
		text += "\" target=\"classFrame\">";
		text += className; 
		text += "</A>\n";
		return text;
	}
	
	@Override
	public ArrayList<ClassElement> getClassElements() {
		ArrayList<ClassElement> clsElements = new ArrayList<ClassElement>();
		EObject bpmnIndex = BpmnModelCache.getInstance().getIndex(desc.project.getName());
		ProcessIndex index = new DefaultBpmnIndex(bpmnIndex);
		Collection<EObject> processes = index.getAllElementsByType(BpmnModelClass.PROCESS);
		for (EObject process: processes) { 
			DefaultProcessIndex processIndex = new DefaultProcessIndex(process);
			String processName = processIndex.getName();
			
			ArrayList<EObject> elements = new ArrayList<EObject>();
			elements.addAll(processIndex.getAllFlowNodes());
			elements.addAll(processIndex.getAllSequenceFlows());
	
			for (EObject element: elements) {
				ClassElement cls = new ClassElement();
				ROEObjectWrapper<EClass, EObject> wElement = ROEObjectWrapper.wrap(element);
				String elementName = (String) wElement.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				cls.setName(elementName);
				String link = getClassFileLinkName(processName, elementName);
				cls.setLink(link);
				clsElements.add(cls);
			}
		}
		return clsElements;
	}

	@Override
	public boolean generateDocumentation(Callback callback) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public StringTemplateGroup getStringTemplateGroup() {
		// TODO Auto-generated method stub
		return null;
	}
}
