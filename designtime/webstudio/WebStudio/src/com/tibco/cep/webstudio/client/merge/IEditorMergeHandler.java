package com.tibco.cep.webstudio.client.merge;

import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public interface IEditorMergeHandler extends ClickHandler {

	public static final String MOD_ENTRY_ATTR = "modEntry";
	
	public enum MergeType {
		USE_LOCAL("Use local"), USE_BASE("Use base"), ADD ("Add"), REMOVE("Remove");
		
		private String literal;

		private MergeType(String literal) {
			this.literal = literal;
		}
		
		public String getLiteral() {
			return literal;
		}
	}

	public abstract void useLocal(MenuItemClickEvent event);
	
	public abstract void useBase(MenuItemClickEvent event);
	
	public abstract void add(MenuItemClickEvent event);
	
	public abstract void remove(MenuItemClickEvent event);
	
	public abstract void onMerge(MenuItemClickEvent event);

}
