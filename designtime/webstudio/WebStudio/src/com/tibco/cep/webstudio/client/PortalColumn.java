package com.tibco.cep.webstudio.client;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VStack;

public class PortalColumn extends VStack {  
	  
    public PortalColumn() {  
        // leave some space between portlets  
        setMembersMargin(20);  

        // enable predefined component animation  
        setAnimateMembers(true);  
        setAnimateMemberTime(300);  

        // enable drop handling  
        setCanAcceptDrop(true);  

        // change appearance of drag placeholder and drop indicator  
        setDropLineThickness(4);  

        Canvas dropLineProperties = new Canvas();  
        dropLineProperties.setBackgroundColor("aqua");  
        setDropLineProperties(dropLineProperties);  

        setShowDragPlaceHolder(true);  

        Canvas placeHolderProperties = new Canvas();  
        placeHolderProperties.setBorder("2px solid #8289A6");  
        setPlaceHolderProperties(placeHolderProperties);  
    }  
}  
