package com.tibco.be.util.config.topology;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 9/19/11
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MMIoNS {
    interface Elements {                //TODO: Namespace?
        ExpandedName OPERATION = ExpandedName.makeName("operation");

        ExpandedName DATA = ExpandedName.makeName("data");

        ExpandedName TABLE= ExpandedName.makeName("table");
        ExpandedName ROW= ExpandedName.makeName("row");
        ExpandedName COLUMN= ExpandedName.makeName("column");

        ExpandedName VALUE= ExpandedName.makeName("value");

        ExpandedName ERROR= ExpandedName.makeName("error");
        ExpandedName ERROR_CODE= ExpandedName.makeName("errorcode");
        ExpandedName ERROR_MSG= ExpandedName.makeName("errormessage");

        ExpandedName EVENT= ExpandedName.makeName("event");

        ExpandedName SUCCESS= ExpandedName.makeName("success");
        ExpandedName WARNING= ExpandedName.makeName("warning");
        ExpandedName INFO= ExpandedName.makeName("info");
    }

    interface Attributes {
        ExpandedName NAME= ExpandedName.makeName("name");
        ExpandedName TYPE= ExpandedName.makeName("type");
        ExpandedName VALUE= ExpandedName.makeName("value");
        ExpandedName ID= ExpandedName.makeName("Id");       //TODO: LowerCase
    }

    interface Table {
        interface DeployDuColNames {
            String DU_NAME = "Deployment Unit name";
            String DEPLOY_STATUS = "Deployment status";
            String REMOTE_DIR= "Remote directory";
            String INFO = "Info";
        }

        interface SaveGvsColNames {
            String DU_NAME = "Deployment Unit name";
            String STATUS = "Status";
            String LOCAL_FILE_PATH = "Path to file saved locally";
            String INFO = "Info";
        }
    }

    interface OperationStatus {
        interface DeployDu {
            String SUCCESS = "SUCCESS";
            String FAIL = "FAIL";
        }

        interface SaveGvs {
            String SAVED = "SAVED";
            String NOT_SAVED = "NOT SAVED";
        }
    }
}
