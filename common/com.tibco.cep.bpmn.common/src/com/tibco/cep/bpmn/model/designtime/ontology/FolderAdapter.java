package com.tibco.cep.bpmn.model.designtime.ontology;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class FolderAdapter implements Folder {
	
	protected ProcessOntologyAdapter ontology;
	protected CoreOntologyAdapter coreOntology;
	protected com.tibco.cep.studio.core.adapters.FolderAdapter delegate;
	
	public FolderAdapter(ProcessOntologyAdapter processOntologyAdapter, EObject element) {
		this.ontology = processOntologyAdapter;
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(element);
		String folderPath = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		ElementContainer ec = CommonIndexUtils.getFolderForFile(processOntologyAdapter.getName(), folderPath);
		this.coreOntology = new CoreOntologyAdapter(StudioProjectCache.getInstance().getIndex(processOntologyAdapter.getName()));
		this.delegate = new com.tibco.cep.studio.core.adapters.FolderAdapter(coreOntology, ec);
	}

	@Override
	public Icon getIcon() {
		return delegate.getIcon();
	}

	@Override
	public Ontology getOntology() {		
		return delegate.getOntology();
	}

	@Override
	public String getGUID() {
		return delegate.getGUID();
	}

	@Override
	public String getNamespace() {
		return delegate.getNamespace();
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public String getDescription() {
		return delegate.getDescription();
	}

	@Override
	public void serialize(OutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFullPath() {
		return delegate.getFullPath();
	}

	@Override
	public String getFolderPath() {
		return delegate.getFolderPath();
	}

	@Override
	public Folder getFolder() {
		return delegate.getFolder();
	}

	@Override
	public String getIconPath() {
		return delegate.getIconPath();
	}

	@Override
	public Object getTransientProperty(String key) {
		return delegate.getTransientProperty(key);
	}

	@Override
	public Map<?, ?> getTransientProperties() {
		return delegate.getTransientProperties();
	}

	@Override
	public String getHiddenProperty(String key) {
		return delegate.getHiddenProperty(key);
	}

	@Override
	public Map<?, ?> getHiddenProperties() {
		return delegate.getHiddenProperties();
	}

	@Override
	public Map<?, ?> getExtendedProperties() {
		return delegate.getExtendedProperties();
	}

	@Override
	public String getBindingString() {
		return delegate.getBindingString();
	}

	@Override
	public String getLastModified() {
		return delegate.getLastModified();
	}

	@Override
	public String getAlias() {
		return delegate.getAlias();
	}

	@Override
	public Folder getParent() {
		return delegate.getParent();
	}

	@Override
	public Collection<?> getSubFolders() {
		return delegate.getSubFolders();
	}

	@Override
	public Folder getSubFolder(String shortName) {
		return delegate.getSubFolder(shortName);
	}

	@Override
	public boolean hasPredecessor(Folder f) {
		return delegate.hasPredecessor(f);
	}

	@Override
	public boolean hasSibling(Folder f) {
		return delegate.hasSibling(f);
	}

	@Override
	public boolean hasDescendant(Folder f) {
		return delegate.hasDescendant(f);
	}

	@Override
	public boolean hasChild(Folder f) {
		return delegate.hasChild(f);
	}

	@Override
	public Collection<?> getFolderList() {
		return delegate.getFolderList();
	}

	@Override
	public List<?> getEntities(boolean includeSubFolders) {
		return delegate.getEntities(includeSubFolders);
	}

	@Override
	public Entity getEntity(String name, boolean includeSubFolders) {
		return delegate.getEntity(name, includeSubFolders);
	}

}

