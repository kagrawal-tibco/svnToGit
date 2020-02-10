package com.tibco.cep.webstudio.client;

import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.portal.WebStudioPortlet;

public class PortalLayout extends HLayout {
	  
    public PortalLayout(int numColumns) {  
    	setWidth100();  
        setHeight100();
        setMembersMargin(10);
        setStyleName("ws-portallayout");

        for (int i = 0; i < numColumns; i++) {  
        	PortalColumn col = new PortalColumn();
        	col.setWidth(getColWidth(numColumns, i == numColumns-1));
            addMember(col);  
        }  
        setHPolicy(LayoutPolicy.NONE);
    }  

    public PortalColumn addPortlet(Canvas portlet) {  
        // find the column with the fewest portlets  
        int fewestPortlets = Integer.MAX_VALUE;  
        PortalColumn fewestPortletsColumn = null;  
        for (int i = 0; i < getMembers().length; i++) {  
        	Canvas[] members = ((PortalColumn) getMember(i)).getMembers();
        	int numPortlets = members.length;  
        	for (Canvas canvas : members) {
				if (canvas instanceof WebStudioPortlet && ((WebStudioPortlet)canvas).isHiddenInPortal()) {
					numPortlets--; // hidden portlets shouldn't count in calculation
				}
			}
            if (numPortlets < fewestPortlets) {  
                fewestPortlets = numPortlets;  
                fewestPortletsColumn = (PortalColumn) getMember(i);  
            }  
        }  
        fewestPortletsColumn.addMember(portlet);  
        return fewestPortletsColumn;  
    }  

    public boolean removePortlet(Canvas portlet) {  
    	for (int i = 0; i < getMembers().length; i++) {  
    		if (((PortalColumn) getMember(i)).hasMember(portlet)) {
    			((PortalColumn) getMember(i)).removeChild(portlet);
    			return true;
    		}
    	}
    	return false;  
    }  
    
    public boolean hasPortlet(Canvas portlet) {  
    	for (int i = 0; i < getMembers().length; i++) {  
    		if (((PortalColumn) getMember(i)).hasMember(portlet)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public static String getColWidth(int numColumns, boolean lastCol) {
		double delta = lastCol ? 1.5 : 1;
		String val = String.valueOf(100/numColumns - delta);
		return val+"%";
	}
}
