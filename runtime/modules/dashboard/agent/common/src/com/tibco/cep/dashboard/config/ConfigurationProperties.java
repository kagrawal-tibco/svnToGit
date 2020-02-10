package com.tibco.cep.dashboard.config;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;


public interface ConfigurationProperties extends PropertyKeys {

	static final PropertyKey FILE_CHANGE_OBSERVATION_FREQUENCY = new PropertyKey("changenotification.frequency","Defines the frequency of file changes monitoring",PropertyKey.DATA_TYPE.Long,new Long(30 * DateTimeUtils.SECOND));

	static final PropertyKey LOCALE_ID = new PropertyKey("locale","The locale to use",PropertyKey.DATA_TYPE.String,null);

	static final PropertyKey TIMEZONE_ID = new PropertyKey("timezone","The timezone to use",PropertyKey.DATA_TYPE.String,null);

	static final PropertyKey FORMAT_DELIMITER = new PropertyKey("format.delimiter","The character to be used as the seperator for multiple date/time formats",PropertyKey.DATA_TYPE.String,"$");

	static final PropertyKey DATE_FORMATS = new PropertyKey("dateformats","The date format(s) to use",PropertyKey.DATA_TYPE.String,"yyyy-MM-dd");

	static final PropertyKey TIME_FORMATS = new PropertyKey("timeformats","The time format(s) to use",PropertyKey.DATA_TYPE.String,"HH:mm:ss.SSS");

	static final PropertyKey DATE_TIME_FORMATS = new PropertyKey("datetimeformats","The datetime format(s) to use",PropertyKey.DATA_TYPE.String,"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	static final PropertyKey HOST_NAME = new PropertyKey("hostname","The name of the host running the agent",PropertyKey.DATA_TYPE.String,new HostName(false).getName());

	static final PropertyKey PULL_REQUEST_PORT = new PropertyKey("pullrequestport","The port on which the agent will listen for pull requests",PropertyKey.DATA_TYPE.Integer,new Integer(8181));

	static final PropertyKey PULL_REQUEST_BASE_URL = new PropertyKey("pullrequestbaseurl","The url which the agent will use for pull requests",PropertyKey.DATA_TYPE.String,"dashboard/controller");

	//static final PropertyKey DOC_ROOT = new PropertyKey("be.http.docRoot","The directory from which the static resources are served",PropertyKey.DATA_TYPE.String,GlobalConfiguration.getInstance().getInstallationHome()+ "/views/web-root");

	//static final PropertyKey DOC_PAGE = new PropertyKey("be.http.docPage","The file served as default",PropertyKey.DATA_TYPE.String, "index.html");

	static final PropertyKey PROP_DOC_ROOT = new PropertyKey("http.docroot","User defined property for the directory from which the static resources are served",PropertyKey.DATA_TYPE.String,GlobalConfiguration.getInstance().getInstallationHome()+ "/views/web-root");

	static final PropertyKey PROP_DOC_PAGE = new PropertyKey("http.docpage","User defined property for the file served as default",PropertyKey.DATA_TYPE.String, "index.html");

	static final PropertyKey CONFIG_DIR = new PropertyKey("configdir","The absolute location of config folder",PropertyKey.DATA_TYPE.String,GlobalConfiguration.getInstance().getInstallationHome()+"/views/config");

}
