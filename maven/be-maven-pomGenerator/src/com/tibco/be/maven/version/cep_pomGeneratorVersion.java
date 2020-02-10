/*
 * $HeadURL:  $ $Revision:  $ $Date: $
 *
 * Copyright(c) 2004-2019 TIBCO Software Inc. All rights reserved.
 *
 * cep-be-maven.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "cep_pomGeneratorVersion.java" is automatically generated at
 * build time from "cep_pomGeneratorVersion.tag"
 *
 * Any maintenance changes MUST be applied to "cep_pomGeneratorVersion.tag"
 * and an official build triggered to propagate such changes to
 * "cep_pomGeneratorVersion.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "cep_pomGeneratorVersion.tag" *AND* "cep_pomGeneratorVersion.java"
 *
 */

package com.tibco.be.maven.version;

public final class cep_pomGeneratorVersion {
        static final public String asterisks       = "**********************************************************************";
        static final public String copyright       = "Copyright(c) 2004-2019 TIBCO Software Inc. All rights reserved.";
        static final public String line_separator  = System.getProperty("line.separator");
        static final public String version         = "5.6.1";
        static final public String build           = "070";
        static final public String buildDate       = "2019-11-13";
        static final public String container_id    = "be-engine";
        static final private String company        = "TIBCO Software Inc.";
        static final private String component      = "TIBCO BusinessEvents";
        static final private String license        = "";

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
