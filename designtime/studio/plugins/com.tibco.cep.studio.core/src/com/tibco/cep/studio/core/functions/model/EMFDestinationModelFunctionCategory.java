package com.tibco.cep.studio.core.functions.model;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFDestinationModelFunctionCategory extends
		EMFModelFunctionCategory {

    public EMFDestinationModelFunctionCategory(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates, Destination destination) {
        super(categoryName, allowSubCategories, allowPredicates, destination);
    }

    public EMFDestinationModelFunctionCategory(ExpandedName categoryName, Destination destination) {
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
