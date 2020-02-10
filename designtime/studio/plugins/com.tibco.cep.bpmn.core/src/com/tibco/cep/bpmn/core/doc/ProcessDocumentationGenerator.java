package com.tibco.cep.bpmn.core.doc;

import static com.tibco.cep.bpmn.core.doc.ProcessDocumentationConstants.DOCUMENTATION_PROCESSES_DIRECTORY;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATES_DIRECTORY;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATE_FILE_ALLCLASSES_FRAME;
import static com.tibco.cep.studio.core.doc.DocumentationConstants.TEMPLATE_FILE_PACKAGE_FRAME;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.doc.AbstractDocumentationGenerator;
import com.tibco.cep.studio.core.doc.Callback;
import com.tibco.cep.studio.core.doc.ClassElement;
import com.tibco.cep.studio.core.doc.PackageElement;

/*
 * 
 * @author mgoel
 * @date June 25, 2012
 */
 
public class ProcessDocumentationGenerator extends AbstractDocumentationGenerator {
	
	private static final String TEMPLATE_GROUP_FILENAME = "process_template.stg";
	
	private final Map<EObject, DefaultProcessIndex> processIndexes = new HashMap<EObject, DefaultProcessIndex>();
	private DefaultBpmnIndex bpmnIndex;
	
	public ProcessDocumentationGenerator() {
		super();
	}
	
	@Override
	public ArrayList<PackageElement> getPackageElements() {
		return null;
	}
	
	@Override
	public ArrayList<ClassElement> getClassElements() {
		return null;
	}
	
	@Override
	public StringTemplateGroup getStringTemplateGroup() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(TEMPLATES_DIRECTORY + "/" + TEMPLATE_GROUP_FILENAME);
        InputStreamReader reader = new InputStreamReader(is);
        return new StringTemplateGroup(reader, DefaultTemplateLexer.class);
	}
	
	@Override
	public boolean generateDocumentation(Callback callback) {
		try {
			//Create the main index file - index.html
			callback.writeFile("indexFrameTemplate", null, desc.location + "/" + "index.html");
			
			//Create blank file, that shows up in the content frame at the start.
			callback.writeFile("homeFrameTemplate", null, desc.location + "/" + "class-frame.html");
			
			List<DocumentDataBean> dataBeans = new ArrayList<DocumentDataBean>();
			//Create the overview file. Appears in the top-left frame.
			dataBeans.add(getOverviewFileData());
			
			//generate package-summary files for processes
			dataBeans.addAll(getAllProcessSummaryBeans());
			
			//generate package-summary files for subprocesses
			dataBeans.addAll(getAllSubProcessSummaryBeans());
			
			//Create individual class files.
			dataBeans.addAll(getAllFlowBeans(desc.project));
			
			//creates all package-files
			dataBeans.addAll(getPackageFilesData());
			
			for (DocumentDataBean data : dataBeans) {
				callback.writeFile(data.getTemplateName(), data, data.getDocFilePath());
			}
			return true;
		} catch (Exception e) {
			BpmnCorePlugin.log(e);
			return false;
		} finally {
			DocumentationBeansStore.clear();
			processIndexes.clear();
			bpmnIndex = null;
		}
	}
	
	/**
	 * The frame that appears at lower left hand side.
	 * @param callback
	 * @return
	 * @throws IOException 
	 */
	private List<DocumentDataBean> getPackageFilesData() throws IOException {
		List<DocumentDataBean> dataBeans = new ArrayList<DocumentDataBean>();
		
		List<DocumentHyperLink> allEvents = new ArrayList<DocumentHyperLink>();
		List<DocumentHyperLink> allElements = new ArrayList<DocumentHyperLink>();
		List<DocumentHyperLink> allSequences = new ArrayList<DocumentHyperLink>();
		List<DocumentHyperLink> allSubProcesses = new ArrayList<DocumentHyperLink>();
		
		String targetFrame = "classFrame";
		Collection<EObject> processes = getBpmnIndex().getAllElementsByType(BpmnModelClass.PROCESS);
		for (EObject process : processes) {
			List<DocumentHyperLink> events = new ArrayList<DocumentHyperLink>();
			List<DocumentHyperLink> elements = new ArrayList<DocumentHyperLink>();
			List<DocumentHyperLink> sequences = new ArrayList<DocumentHyperLink>();
			List<DocumentHyperLink> subProcesses = new ArrayList<DocumentHyperLink>();
			
			List<DocumentDataBean> flowBeans = new ArrayList<DocumentDataBean>();
			flowBeans.addAll(getFlowNodeBeans(process, null));
			flowBeans.addAll(getSequenceFlowBeans(process, null));
			
			for (DocumentDataBean d : flowBeans) {
				if (!(d instanceof DocumentationFlowElementBean)) {
					continue;
				}
				DocumentationFlowElementBean dataBean = (DocumentationFlowElementBean)d;
				if (StringUtils.isEmpty(dataBean.getName())) {
					continue;
				}
				
				List<DocumentHyperLink> outListAlias = null;
				List<DocumentHyperLink> outAllListAlias = null;
				if (BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(dataBean.getEobject().eClass())) {
					outListAlias = sequences;
					outAllListAlias = allSequences;
				}
				else if (BpmnModelClass.EVENT.isSuperTypeOf(dataBean.getEobject().eClass())) {
					outListAlias = events;
					outAllListAlias = allEvents;
				}
				else {
					outListAlias = elements;
					outAllListAlias = allElements;
				}
				
				//name as shown in the generated documentation.
				String elementName = null;
				if (StringUtils.isEmpty(dataBean.getParentProcessName())) {
					elementName = dataBean.getName();
				} else {
					elementName = dataBean.getProcessName() + "." + dataBean.getName(); 
				}
				DocumentHyperLink link = new DocumentHyperLink(elementName, null, targetFrame, "");
				link.setTargetId(d.getEntityId());
				outListAlias.add(link);
				link = new DocumentHyperLink(elementName, null, targetFrame, "");
				link.setName((StringUtils.isEmpty(dataBean.getParentProcessName()) ? "" : dataBean.getParentProcessName() +".")
						+ dataBean.getProcessName() + "." + dataBean.getName());
				link.setTargetId(d.getEntityId());
				outAllListAlias.add(link);
			}
			
			PackagesFrameBean packagesFrameBean = new PackagesFrameBean();
			packagesFrameBean.setPackageName(getProcessIndex(process).getName());
			packagesFrameBean.setElements(elements);
			packagesFrameBean.setEvents(events);
			packagesFrameBean.setSequences(sequences);
			packagesFrameBean.setSubProcesses(subProcesses);
			packagesFrameBean.setDocFilePath(desc.location + "/" + DOCUMENTATION_PROCESSES_DIRECTORY + "/" + getProcessIndex(process).getName() + "/"+ TEMPLATE_FILE_PACKAGE_FRAME);
			
			for (DocumentDataBean subProcess : getSubProcessSummaryBeans(process)) {
				if (subProcess instanceof DocumentationProcessBean) {
					DocumentationProcessBean sp = (DocumentationProcessBean)subProcess;
					DocumentHyperLink link = new DocumentHyperLink(sp.getName(), null, "classFrame", "");
					link.setTargetId(sp.getEntityId());
					subProcesses.add(link);
					link = new DocumentHyperLink(null, null, "classFrame", "");
					link.setName(sp.getParentProcessName() + "." + sp.getName());
					link.setTargetId(sp.getEntityId());
					allSubProcesses.add(link);
				}
			}
			
			//Add the packages file for each process
			dataBeans.add(packagesFrameBean);
		}
		
		PackagesFrameBean packagesFrameBean = new PackagesFrameBean();
		packagesFrameBean.setElements(allElements);
		packagesFrameBean.setEvents(allEvents);
		packagesFrameBean.setSequences(allSequences);
		packagesFrameBean.setSubProcesses(allSubProcesses);
		packagesFrameBean.setDocFilePath(desc.location + "/" + TEMPLATE_FILE_ALLCLASSES_FRAME);
		
		//Write the all-packages file.
		dataBeans.add(packagesFrameBean);
		
		return dataBeans;
	}
	
	/**
	 * The frame that appears at upper left hand side.
	 * @param callback
	 * @return
	 * @throws IOException 
	 */
	private DocumentDataBean getOverviewFileData() throws IOException {
		OverviewFrameBean overviewFrameBean = new OverviewFrameBean();
		
		String targetFrame = "packageFrame";
		List<DocumentHyperLink> overviewEntries = new ArrayList<DocumentHyperLink>();
		Collection<EObject> processes = getBpmnIndex().getAllElementsByType(BpmnModelClass.PROCESS);
		for (EObject process: processes) {
			String name = getProcessIndex(process).getName();
			String link = DOCUMENTATION_PROCESSES_DIRECTORY + "/" + getProcessIndex(process).getName() + "/" + TEMPLATE_FILE_PACKAGE_FRAME ;
			overviewEntries.add(new DocumentHyperLink(name, link, targetFrame, ""));
		}
		overviewFrameBean.setEntries(overviewEntries);
		overviewFrameBean.setDocFilePath(desc.location + "/" + "overview-frame.html");
		return overviewFrameBean;
	}
	
	/**
	 * Returns beans for all nodes, sequences in all the BPMN process for the project.
	 * @param project
	 * @return
	 */
	private List<DocumentDataBean> getAllFlowBeans(IProject project) {
		Collection<EObject> processes = getBpmnIndex().getAllElementsByType(BpmnModelClass.PROCESS);
		List<DocumentDataBean> flowBeans = new ArrayList<DocumentDataBean>();
		for (EObject process: processes) {
			flowBeans.addAll(getFlowNodeBeans(process, null));
			flowBeans.addAll(getSequenceFlowBeans(process, null));
		}
		return flowBeans;
	}
	
	/**
	 * Returns beans for all process in the project (excluding subProcesses).
	 * @return
	 */
	private List<DocumentDataBean> getAllProcessSummaryBeans()  {
		Collection<EObject> processes = getBpmnIndex().getAllElementsByType(BpmnModelClass.PROCESS);
		List<DocumentDataBean> dataBeans = new ArrayList<DocumentDataBean>();
		for (EObject process: processes) {
			dataBeans.add(DocumentationBeanUtils.createProcessSummaryBean(process, desc));
		}
		return dataBeans;
	}
	
	/**
	 * Returns beans for all subProcesses in the project.
	 * @return
	 */
	private List<DocumentDataBean> getAllSubProcessSummaryBeans() {
		Collection<EObject> processes = getBpmnIndex().getAllElementsByType(BpmnModelClass.PROCESS);
		List<DocumentDataBean> dataBeans = new ArrayList<DocumentDataBean>();
		for (EObject process: processes) {
			dataBeans.addAll(getSubProcessSummaryBeans(process));
		}
		return dataBeans;
	}
	
	/**
	 * Returns beans for all subProcesses in the specified process.
	 * @param process
	 * @return
	 */
	private List<DocumentDataBean> getSubProcessSummaryBeans(EObject process) {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
		List<DocumentDataBean> subProcessBeans = new ArrayList<DocumentDataBean>();
		String processId = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		for (EObject subProcess : getBpmnIndex().getAllSubProcesses()) {
			String subProcessId = DocumentationBeanUtils.getElementId(subProcess);
			if (subProcessId != null && subProcessId.startsWith(processId + ".")) {
				DocumentDataBean bean = DocumentationBeanUtils.createSubProcessSummaryBean(subProcess, desc, process);
				subProcessBeans.add(bean);
			}
		}
		return subProcessBeans;
	}
	
	/**
	 * Returns beans for all the nodes in the specified process.
	 * @param process
	 * @param parentProcess
	 * @return
	 */
	private List<DocumentDataBean> getFlowNodeBeans(EObject process, EObject parentProcess) {
		List<DocumentDataBean> nodeBeans = new ArrayList<DocumentDataBean>();
		ArrayList<EObject> elements = new ArrayList<EObject>();
		elements.addAll(getProcessIndex(process).getAllFlowNodes());
		for (EObject subProcess : getProcessIndex(process).getAllSubProcesses()) {
			elements.remove(subProcess);
			elements.removeAll(getProcessIndex(subProcess).getAllFlowNodes());
			for (DocumentDataBean d : getFlowNodeBeans(subProcess, process)) {
				nodeBeans.add(d);
			}
		}
		for (EObject element : elements) {
			DocumentDataBean bean = DocumentationBeanUtils.createNodeBean(element, desc, process, parentProcess);
			if (bean != null) {
				nodeBeans.add(bean);
			}
		}
		return nodeBeans;
	}
	
	/**
	 * Returns beans for all sequences in specified process.
	 * @param process
	 * @param parentProcess
	 * @return
	 */
	private List<DocumentDataBean> getSequenceFlowBeans(EObject process, EObject parentProcess) {
		List<DocumentDataBean> sequenceFlowBeans = new ArrayList<DocumentDataBean>();
		ArrayList<EObject> elements = new ArrayList<EObject>();
		elements.addAll(getProcessIndex(process).getAllSequenceFlows());
		
		for (EObject subProcess : getProcessIndex(process).getAllSubProcesses()) {
			elements.remove(subProcess);
			elements.removeAll(getProcessIndex(subProcess).getAllSequenceFlows());
			for (DocumentDataBean d : getSequenceFlowBeans(subProcess, process)) {
				sequenceFlowBeans.add(d);
			}
		}
		for (EObject element : elements) {
			DocumentDataBean bean = DocumentationBeanUtils.sequenceFlowBean(element, desc, process, parentProcess);
			sequenceFlowBeans.add(bean);
		}
		return sequenceFlowBeans;
	}
	
	private DefaultProcessIndex getProcessIndex(EObject process) {
		if (processIndexes.get(process) == null) {
			DefaultProcessIndex processIndex = new DefaultProcessIndex(process);
			processIndexes.put(process, processIndex);
		}
		return processIndexes.get(process);
	}
	
	private DefaultBpmnIndex getBpmnIndex() {
		if (bpmnIndex == null) {
			EObject index = BpmnModelCache.getInstance().getIndex(desc.project.getName());
			bpmnIndex = new DefaultBpmnIndex(index);
		}
		return bpmnIndex;
	}
}
