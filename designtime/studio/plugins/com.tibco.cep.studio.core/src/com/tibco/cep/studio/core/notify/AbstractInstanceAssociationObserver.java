/**
 * 
 */
package com.tibco.cep.studio.core.notify;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

/**
 * @author aathalye
 *
 */
public abstract class AbstractInstanceAssociationObserver extends AdapterImpl {
	
	/**
	 * Maintain the base index to update
	 */
	protected DesignerProject index;
	
	/**
	 * The file in which the update is persisted (concept/event etc.)
	 */
	protected IFile targetFile;
	
	protected AbstractInstanceAssociationObserver(final DesignerProject index,
			                                      final IFile targetFile) {
		this.index = index;
		this.targetFile = targetFile;
	}
	
	protected void insertElement(DesignerElement entry) {
		ElementContainer folder = 
			IndexUtils.getFolderForFile(index, targetFile, true);
		folder.getEntries().add(entry);
	}

	protected void removeElement(DesignerElement entry) {
		ElementContainer folder = IndexUtils.getFolderForFile(index, targetFile);
		folder.getEntries().remove(entry);
	}
	
	protected <T extends BaseInstance> boolean contains(List<InstanceElement<T>> list,
			                                            InstanceElement<T> instanceElement,
			                                            ELEMENT_TYPES elementType) {
		
		for (InstanceElement<T> element : list) {
			if (element.equals(instanceElement)) {
				return true;
			}
		}
		return false;
	}
	
	
}
