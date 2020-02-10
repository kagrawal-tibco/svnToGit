package com.tibco.cep.bpmn.runtime.model;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 8:02:07 AM
 * To change this template use File | Settings | File Templates.
 */


import java.util.Calendar;

/**
 * This interface provides VersionInfo info for the JobImpl Template.
 */
public interface VersionInfo {

    int getRevision();

    String getOriginalAuthor();

    Calendar getDeployedDate();

    Calendar getLastModifiedDate();

    String getLastModifiedAuthor();


}
