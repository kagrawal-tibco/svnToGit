package com.tibco.cep.webstudio.client.merge;

import com.smartgwt.client.widgets.events.BrowserEvent;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.diff.ModificationEntry;

public abstract class AbstractEditorMergeHandler implements IEditorMergeHandler {
		
	@SuppressWarnings("rawtypes")
	public abstract void setMergeMenu(BrowserEvent event);

	@Override
	public final void onClick(MenuItemClickEvent event) {
		String title = ((MenuItem)event.getSource()).getTitle();
		ModificationEntry modificationEntry = (ModificationEntry)((MenuItem) event.getSource()).getAttributeAsObject(MOD_ENTRY_ATTR);
		if(MergeType.USE_LOCAL.getLiteral().equals(title)) {
			useLocal(event);
			onMerge(event);
		} else if(MergeType.USE_BASE.getLiteral().equals(title)) {
			useBase(event);
			onMerge(event);
		} else if(title.startsWith(MergeType.ADD.getLiteral())) {
			switch(modificationEntry.getModificationType()) {
    		case ADDED:
    		case DELETED:
    			add(event);
    			onMerge(event);
    			break;
			}
		} else if(title.startsWith(MergeType.REMOVE.getLiteral())) {
			switch(modificationEntry.getModificationType()) {
			case ADDED:
			case DELETED:
				remove(event);
				onMerge(event);
				break;
			}
		}
	}
			
}
