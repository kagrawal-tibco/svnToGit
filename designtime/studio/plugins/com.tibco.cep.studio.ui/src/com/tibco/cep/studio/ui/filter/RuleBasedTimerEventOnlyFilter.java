package com.tibco.cep.studio.ui.filter;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleBasedTimerEventOnlyFilter extends FileInclusionFilter {

	/**
	 * @param inclusions
	 */
	@SuppressWarnings("rawtypes")
	public RuleBasedTimerEventOnlyFilter(Set inclusions) {
		super(inclusions);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isEntityFile(file) && isRuleBased(file);
			}
			if (res instanceof IFolder) {
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if (element instanceof SharedElementRootNode || element instanceof ElementContainer) {
			return true;
		} else if (element instanceof SharedEntityElement && ((SharedEntityElement) element).getEntity() instanceof TimeEvent) {
			TimeEvent timeEvent = (TimeEvent) ((SharedEntityElement) element).getEntity();
			EVENT_SCHEDULE_TYPE scheduleType = timeEvent.getScheduleType();
			return scheduleType == EVENT_SCHEDULE_TYPE.RULE_BASED;
		} else {
			if(!(element instanceof IResource)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param file
	 * @return
	 */
	private boolean isRuleBased(IFile file) {
		TimeEvent timeEvent = (TimeEvent) IndexUtils.getEntity(file
				.getProject().getName(), IndexUtils.getFullPath(file),
				ELEMENT_TYPES.TIME_EVENT);
		EVENT_SCHEDULE_TYPE scheduleType = timeEvent.getScheduleType();
		return scheduleType == EVENT_SCHEDULE_TYPE.RULE_BASED;
	}

	/**
	 * Ensures that the element contains a "virtual" rule function
	 * @param element
	 * @return 
	 */
	@Override
	protected boolean isVisible(Object element) {
		// TODO Auto-generated method stub

		Object[] object = CommonUtil.getResources((IFolder) element);
		
		for (Object obj : object) {
			if (obj instanceof IFolder) {
				isVisible(obj);
			}
			if (obj instanceof IFile){
				if (isEntityFile(obj) && isRuleBased((IFile)obj)) {
					visible = true;
				}
			}
		}
		
		if (visible == true) {
			return true;
		}
		return false;
	}
	
	
}
