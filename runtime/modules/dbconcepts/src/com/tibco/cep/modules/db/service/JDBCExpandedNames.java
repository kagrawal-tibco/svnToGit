package com.tibco.cep.modules.db.service;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 17, 2007
 * Time: 1:12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface JDBCExpandedNames {

    //1.4 JDBC Channel ExpandedNames
    ExpandedName FOLDER = ExpandedName.makeName("folder");
    ExpandedName NAME = ExpandedName.makeName("name");
    ExpandedName DRIVER = ExpandedName.makeName("jdbcDriver");
    ExpandedName URL = ExpandedName.makeName("jdbcURL");
    ExpandedName USERNAME = ExpandedName.makeName("username");
    ExpandedName PASSWORD = ExpandedName.makeName("password");
    ExpandedName POOLSIZE = ExpandedName.makeName("poolSize");

    //2.0 JDBC DataSource ExpandedNames;
    ExpandedName CONFIG = ExpandedName.makeName("config");
    ExpandedName DRIVER_2_0 = ExpandedName.makeName("driver");
    ExpandedName URL_2_0 = ExpandedName.makeName("location");
    ExpandedName USERNAME_2_0 = ExpandedName.makeName("user");
    ExpandedName PASSWORD_2_0 = ExpandedName.makeName("password");
    ExpandedName POOLSIZE_2_0 = ExpandedName.makeName("maxConnections");
    ExpandedName LOGINTIMEOUT = ExpandedName.makeName("loginTimeout");
    ExpandedName CONNECTIONTYPE = ExpandedName.makeName("connectionType");
    ExpandedName USESHAREDJNDI = ExpandedName.makeName("UseSharedJndiConfig");



}
