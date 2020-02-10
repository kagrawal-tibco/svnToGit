package com.tibco.be.model.functions.impl;

import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * @author ishaan
 * @version Jan 7, 2005, 7:45:01 PM
 */
public class DestinationModelFunctionCategory extends ModelFunctionCategory {

    public DestinationModelFunctionCategory(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates, Destination destination) {
        super(categoryName, allowSubCategories, allowPredicates, destination);
    }

    public DestinationModelFunctionCategory(ExpandedName categoryName, Destination destination) {
        this(categoryName, false, true, destination);
    }

    public String toString() {
        Destination dest = (Destination) getEntity();
        DriverConfig driver = dest.getDriverConfig();
        Channel channel = driver.getChannel();
        String channelPath = channel.getFullPath();
        String fullPath = '"' + channelPath + Folder.FOLDER_SEPARATOR_CHAR + dest.getName() + '"';

        return fullPath;
    }


}
