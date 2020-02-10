package com.tibco.cep.studio.dashboard.ui.editors;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @author ssrinivasan
 *
 * @deprecated	
 */
public interface IGenericCellModificationCallback {

	public static final int ACTION_CHECKED=1;
	public static final int ACTION_UNCHECKED=2;
	public static final int ACTION_SELECTED=3;
    public static final int ACTION_UNSELECTED=4;
    
    public void modified(int action, LocalElement element);
}
