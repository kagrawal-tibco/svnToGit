package com.tibco.cep.studio.ui.editors.domain;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractDomainSaveableEditorPart extends AbstractSaveableEntityEditorPart{
	
    protected Domain domain;
    protected IFile file = null;
    protected DomainFormEditorInput domainFormEditorInput;
    protected DomainFormViewer domainFormViewer;
  
	public Domain getDomain() {
		return (Domain)getEntity();
	}
   
    /**
	 * This is how the framework determines which interfaces we implement.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(Domain.class))
			return getDomain();
		return super.getAdapter(key);
	}
	
	public void setDomain(Domain channel) {
		this.domain = channel;
	}
	
	  public DomainFormViewer getDomainFormViewer() {
			return domainFormViewer;
		}

}