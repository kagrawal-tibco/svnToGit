package com.tibco.cep.bpmn.ui.editor;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.visitor.BpmnIndexUpdateVisitor;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.validation.resolution.BpmnProcessRefreshVisitor;

public class BpmnEditorInput extends PlatformObject implements IFileEditorInput, IPathEditorInput, IURIEditorInput,
IPersistableElement,IGraphEditorInput {
	
	protected EObjectWrapper<EClass, EObject> processModel;
	private BpmnEditor bpmnGraphEditor;
	private String description;
	private boolean intialized = false;
	private FileEditorInput fEditorInput;
	private boolean isPrivateProcess = false;
	
	public BpmnEditorInput(IFile file) {
		this.fEditorInput = new FileEditorInput(file);
		description = "";
	}
	
	public BpmnEditorInput(IFile file, boolean createPrivateProcess) {
		this.fEditorInput = new FileEditorInput(file);
		this.isPrivateProcess = createPrivateProcess;
		description = "";
	}
	/**
	 * @param bpmnGraphEditor 
	 * @param file
	 */
	public BpmnEditorInput(BpmnEditor bpmnGraphEditor, IFile file) {
		this(file);
		this.bpmnGraphEditor = bpmnGraphEditor;
	}
	@Override
	public boolean exists() {
		return fEditorInput.exists();
	}
	
	@Override
	public String getFactoryId() {
		return fEditorInput.getFactoryId();
	}
	
	@Override
	public IFile getFile() {
		return fEditorInput.getFile();
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return fEditorInput.getImageDescriptor();
	}
	
	@Override
	public String getName() {
		return fEditorInput.getName();
	}
	
	@Override
	public IPath getPath() {
		return fEditorInput.getPath();
	}
	
	@Override
	public IPersistableElement getPersistable() {
		return fEditorInput.getPersistable();
	}
	
	@Override
	public IStorage getStorage() throws CoreException {
		return fEditorInput.getStorage();
	}
	
	@Override
	public String getToolTipText() {
		return fEditorInput.getToolTipText();
	}
	
	@Override
	public URI getURI() {
		return fEditorInput.getURI();
	}
	
	@Override
	public void saveState(IMemento memento) {
		fEditorInput.saveState(memento);		
	}
	
	public BpmnEditor getGraphEditor() {
		return bpmnGraphEditor;
	}
	
	public void setGraphEditor(BpmnEditor editor) {
		bpmnGraphEditor=editor;
	}

	public void initialize() throws Exception {
		if (getFile().exists()) {
			// wait for the index to get loaded if this editor is loaded by the
			// eclipse after startup
			Job.getJobManager().resume();
			URIHandler uriHandler = ECoreHelper.getURIHandler(getFile().getProject());
			EList<EObject> resources = ECoreHelper.deserializeModelXMI(getFile(), true, uriHandler);
			EObject eObj = resources.get(0);
			
			if(detectMissingBinding(eObj)) {
				fixMissingBinding(eObj);
			}
			// every time latest process gets loaded and older one removes from
			// resourceset
			// so index needs to be updated for for latest resource
			EObject index = BpmnIndexUtils.getIndex(getFile());
			BpmnIndexUpdateVisitor updater = new BpmnIndexUpdateVisitor();
			updater.reindexFile(IResourceDelta.CHANGED, getFile(), index, false);
			if (BpmnModelClass.PROCESS.isSuperTypeOf(eObj.eClass())) {
				this.processModel = EObjectWrapper.wrap(eObj);
			}
			this.intialized = true;
		}
	}
	
	private boolean detectMissingBinding(EObject process) throws Exception {
		BpmnProcessRefreshVisitor visitor = new BpmnProcessRefreshVisitor(false);
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(process);
		wrapper.accept(visitor);
		return visitor.isMissingExtensionData();
	}

	private void fixMissingBinding(EObject modelObj) throws Exception {
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(modelObj);
		BpmnProcessRefreshVisitor visitor = new BpmnProcessRefreshVisitor(true);
		wrapper.accept(visitor);
	}
	
	public EObjectWrapper<EClass, EObject> getProcessModel() {
		if(!this.intialized) {
			try {
				initialize();
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
				return null;
			}
		}
		return processModel;
	}

	public void setProcessModel(EObjectWrapper<EClass, EObject> processModel) {
		this.processModel = processModel;
	}
	
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String desc){
		description = desc;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == IStorage.class){
			return getFile();
		} else if(adapter == IResource.class){
			return getFile();
		} else if(adapter == IFile.class) {
			return getFile();
		} else if(adapter == IDocument.class || IGraphDocument.class.isAssignableFrom(adapter)) {			
			return getGraphEditor().getDocumentProvider().getDocument(this);			
		} else if(adapter == IProject.class) {
			return getFile().getProject();
		}
		return super.getAdapter(adapter);
	}
	
	/**
	 * This method rebinds the extension defs to the objects when the index is rebuilt
	 * @param project
	 * @param index
	 * @throws Exception
	 */
	public void onIndexUpdate(IProject project, EObject index) throws Exception{
		if(getFile() == null || getFile().getProject() != project ) {
			return;
		}
		
		initialize();
		BpmnDiagramManager manager = getGraphEditor().getBpmnGraphDiagramManager();
		// detect if there is missing extend definitions in the process model
		// then fix it to keep consistency between index and model
		try {
			manager.setLoading(true);
			manager.reLoadModel();
			getGraphEditor().getBpmnGraphDiagramManager();
			if(detectMissingBinding(getProcessModel().getEInstance())) {
				fixMissingBinding(getProcessModel().getEInstance());
			}
		} finally {
			manager.setLoading(false);
		}

		
	}


	public boolean equals(Object obj) {
		if (obj == null || getFile() == null)
			return false;

		if (this == obj) {
			return true;
		}
		
		IFile file = null;
		if (obj instanceof BpmnEditorInput) {
			file = ((BpmnEditorInput) obj).getFile();
		}

		if (obj instanceof FileEditorInput) {
			file = ((FileEditorInput) obj).getFile();
		}

		if(file == null)
			return false;
		
		return getFile().equals(file);

	}

	
	public int hashCode() {
		return getFile().hashCode();
	}

	public boolean isPrivateProcess(){
		return isPrivateProcess;
	}
	
	public void setIsPrivateProcess(boolean privateProcess){
		isPrivateProcess = privateProcess;
	}
	
	public EObjectWrapper<EClass, EObject> getProcessModelForCallActivityTask() {
		if(!this.intialized) {
			try {
				initialize();
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
				return null;
			}
			if(processModel==null){
				try{
					if(bpmnGraphEditor!=null){
						EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap((EObject) bpmnGraphEditor.getGraphManager().getUserObject());
						return useInstance;
					}
				}
				catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
		}
		return processModel;
	}
}
