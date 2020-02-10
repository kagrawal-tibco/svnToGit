/*
 * $HeadURL: http://svn.tibco.com/be/branches/5.1/runtime/modules/interpreter/src/com/tibco/cep/interpreter/cep_interpreterVersion.tag $ $Revision: 48199 $ $Date:
 2012-02-16 19:27:01 -0800 (Thu, 16 Feb 2012) $
 *
 * Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 *
 * cep-interpreter.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "cep_interpreterVersion.java" is automatically generated at
 * build time from "cep_interpreterVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_interpreterVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_interpreterVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_interpreterVersion.tag" *AND* "cep_interpreterVersion.java"
 *
 */

package com.tibco.cep.interpreter;

public final class cep_interpreterVersion {
        static final public String asterisks       = "****************************************************************************";
        static final public String copyright       = "@BE_COPYRIGHT@";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "@BE_VERSION@";
        static final public String build           = "@BE_BUILD@";
        static final public String buildDate       = "@BE_DATE@";
        static final public String container_id    = "@BE_CONTAINER_ID@";
        static final public String readme	   = "@BE_ENGG_README@";
        static final private String company        = "@BE_COMPANY@";
        static final private String component      = "@BE_PRODUCT@";
        static final private String license        = "@BE_LICENSE@";


        static public String getVersion() {
                return "Version " + version + "." + build + ", " + buildDate;
        }

        static public String getCompany() {
                return company;
        }

        static public String getComponent() {
                return component;
        }

        static public void main(String[] args) {
                System.out.println(getCompany() + " - " + getComponent() + " " + getVersion() + " " + getLicense());
        }

        static public String getLicense() {
                return license;
        }
}
