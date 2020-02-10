package com.tibco.cep.releases.bom;

import com.tibco.xml.data.primitive.ExpandedName;

public class XNames {


    public static final String NAMESPACE = "http://tibco.com/businessevents/releases/bom/5.1";


    private XNames() {}


    public static class Attributes {

        private Attributes() {}

        public static final ExpandedName NAME = ExpandedName.makeName("name");
        public static final ExpandedName PORT_SPECIFIC = ExpandedName.makeName("port-specific");
        public static final ExpandedName REF = ExpandedName.makeName("ref");
        public static final ExpandedName TYPE = ExpandedName.makeName("type");
    }


    public static class Nodes {

        private Nodes() {}

        public static final ExpandedName ADD_ON = ExpandedName.makeName(NAMESPACE, "add-on");
        public static final ExpandedName ADD_ONS = ExpandedName.makeName(NAMESPACE, "add-ons");
        public static final ExpandedName ASSEMBLIES = ExpandedName.makeName(NAMESPACE, "assemblies");
        public static final ExpandedName ASSEMBLY = ExpandedName.makeName(NAMESPACE, "assembly");
        public static final ExpandedName ASSEMBLY_REF = ExpandedName.makeName(NAMESPACE, "assembly-ref");
        public static final ExpandedName BOM = ExpandedName.makeName(NAMESPACE, "bom");
        public static final ExpandedName BUILD = ExpandedName.makeName(NAMESPACE, "build");
        public static final ExpandedName FILE_SET = ExpandedName.makeName(NAMESPACE, "file-set");
        public static final ExpandedName FILE_SET_REF = ExpandedName.makeName(NAMESPACE, "file-set-ref");
        public static final ExpandedName FILE_SETS = ExpandedName.makeName(NAMESPACE, "file-sets");
        public static final ExpandedName INSTALLED_GA = ExpandedName.makeName(NAMESPACE, "installed-ga");
        public static final ExpandedName INSTALLED_HF = ExpandedName.makeName(NAMESPACE, "installed-hf");
        public static final ExpandedName SOURCE = ExpandedName.makeName(NAMESPACE, "source");
    }

}
