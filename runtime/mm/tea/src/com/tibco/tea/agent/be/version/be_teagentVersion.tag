/*
 * $HeadURL: http://svn.tibco.com/be/branches/5.3/runtim/mm/tea/src/com/tibco/tea/agent/be/version/be_teagentVersion.tag $ $Revision: 1 $ $Date: 2015-12-23 02:30:01 +0530 (Fri, 23 Dec 2015) $
 *
 * Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 *
 * be-tea-agent.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "be_teagentVersion.java" is automatically generated at
 * build time from "be_teagentVersion.tag"
 *
 * Any maintenance changes MUST be applied to "be_teagentVersion.tag"
 * and an official build triggered to propagate such changes to
 * "be_teagentVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "be_teagentVersion.tag" *AND* "be_teagentVersion.java"
 *
 */

package com.tibco.tea.agent.be.version;

public final class be_teagentVersion {
        static final public String asterisks       		 = "**********************************************************************";
        static final public String copyright       		 = "@BE_COPYRIGHT@";
        static final public String line_separator 		 = System.getProperty("line.separator");
        static final public String version        		 = "@BE_VERSION@";
       	static final public String be_tea_agent_version  = "@BE_TEA_AGENT_VERSION@";
        static final public String engineMinVersion 	 = "@BE_CONTAINER_MINIMUM_VERSION@";
        static final public String build           		 = "@BE_BUILD@";
        static final public String buildDate      		 = "@BE_DATE@";
        static final private String company       		 = "@BE_COMPANY@";
        static final private String component     		 = "@BE_TEA_AGENT_PRODUCT@";
        static final private String license       		 = "@BE_LICENSE@";
        

        static public String getVersion() {
                return "Version " + version + "." + build + ", " + buildDate;
        }
         static public String getBETEVersion() {
                return "Version " + be_tea_agent_version + "." + build + ", " + buildDate;
        }
      
        static public String getCompany() {
                return company;
        }

        static public String getComponent() {
                return component;
        }

        static public void main(String[] args) {
                System.out.println(getCompany() + " - " + getComponent() + " " + getBETEVersion() + " " + getLicense());
        }

        static public String getLicense() {
                return license;
        }
       
}
