package com.tibco.be.parser.codegen;

import static com.tibco.be.parser.codegen.CGConstants.BRK;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;
import com.tibco.cep.runtime.service.loader.NeedsStaticInitialization;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 1/27/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseConceptClassGeneratorSmap {
    public static String GET_METAPROP_STATIC = "getMetaProperty_static";
    public static String GET_PROP_INDEX_STATIC = "getPropertyIndex_static";
    protected static final String MEMBER_ACCESS = "protected";
    protected static final String PROPS_ARRAY = "m_props";
    protected static final String META_PROP_CLASS = MetaProperty.class.getName();

    protected static final boolean METAPROP_BODY_CONSTRUCTORS = Boolean.parseBoolean(System.getProperty("be.codegen.debug.gen.metaprop.constructors", "false"));

    protected Map<String, Integer> propIds;
    //the container of a contained concept
    protected final Properties oversizeStringConstants;
    protected final Map ruleFnUsage;
    protected final boolean generateNullContainedConcept = Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT);
    protected final JavaClassWriter cc;
    protected final Ontology ontology;

    public BaseConceptClassGeneratorSmap(Properties oversizeStringConstants, Map ruleFnUsage, JavaClassWriter cc, Ontology o) {
        this.oversizeStringConstants = oversizeStringConstants;
        this.ruleFnUsage = ruleFnUsage;
        this.cc = cc;
        ontology = o;
    }

    protected static StringBuilder newStringBuilder() {
        return new StringBuilder(1024);
    }
    
    abstract protected String className();
    
    protected static void addGetMethod(String access, String type, String varName, JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod(access, type, getGetMethodName(varName));
        mr.setBody("return " + varName + ";");
    }

    protected static String getGetMethodName(String varName) {
        return CGConstants.GET_PREFIX + CGUtil.firstCap(varName);
    }

    protected void addGetType(String type) {
        cc.addMember("public static", String.class.getName(), "type",
                '"' + type + '"');
        MethodRecWriter mr = cc.createMethod("getType");
        mr.setAccess("public");
        mr.setReturnType(String.class.getName());
        mr.setBody("return type;");
    }

    protected void addGetExpandedName(String nameSpaceURI, String localName) {
        cc.addMember("public static", com.tibco.xml.data.primitive.ExpandedName.class.getName(), "concept_expandedName",
                "com.tibco.xml.data.primitive.ExpandedName.makeName(\"" + nameSpaceURI + "\",\"" + localName + "\")");
        MethodRecWriter mr3 = cc.createMethod("getExpandedName");
        mr3.setAccess("public ");
        mr3.setReturnType(com.tibco.xml.data.primitive.ExpandedName.class.getName());
        mr3.setBody("return concept_expandedName;");
    }

    protected void addConstructors(String classShortName) {
        MethodRecWriter mr;

        mr = cc.createMethod(classShortName);
        mr.setAccess("public");
        mr.setReturnType("");
        mr.setBody("super();");

        mr = cc.createMethod(classShortName);
        mr.setAccess("public");
        mr.setReturnType("");
        mr.addArg("long", "_id");
        mr.setBody("super(_id);");

        mr = cc.createMethod(classShortName);
        mr.setAccess("public");
        mr.setReturnType("");
        mr.addArg("long", "_id");
        mr.addArg("java.lang.String", "_extId");
        mr.setBody("super(_id, _extId);");
    }

    protected void addGetNumProperties(int numProperties) {
        MethodRecWriter mr = cc.createMethod("protected", "int", "_getNumProperties_constructor_only");
        mr.setBody(String.format("return %s;", numProperties));
    }

    protected void addGetNumDirtyBits(int numDirtyBits) {
        MethodRecWriter mr = cc.createMethod("protected", "int", "_getNumDirtyBits_constructor_only");
        mr.setBody(String.format("return %s;", numDirtyBits));
    }

    protected void addGetParentProperty(String parentPropName) {
        cc.addMember("public static", String.class.getName(), CGConstants.PARENT_PROPERTY_NAME, String.format("\"%s\"", parentPropName));

        MethodRecWriter mr = cc.createMethod("public", "java.lang.String", getGetMethodName(CGConstants.PARENT_PROPERTY_NAME));
        mr.setBody("return " + CGConstants.PARENT_PROPERTY_NAME + ";");
    }

    //Map is hashcode of prop name -> {prop name, get method name, propId, null, or if there is a hashcode collision {prop name, get method name, propId, ...}}
    protected void addGetPropertyWithHashCode(HashMap<Integer, Object[]> map) {
        //MethodRecWriter mr = cc.createMethod("getProperty");
        MethodRecWriter idxMr = cc.createMethod(GET_PROP_INDEX_STATIC);
        //mr.setAccess("public");
        idxMr.setAccess("static public");
        //mr.setReturnType(CGConstants.propertyGenericInterface);
        idxMr.setReturnType("int");
        //mr.addArg("java.lang.String", "name");
        idxMr.addArg("java.lang.String", "name");
        //StringBuilder body = newStringBuilder();
        StringBuilder idxBody = newStringBuilder();

        //body.append("switch(name.hashCode()) {").append(BRK);
        idxBody.append("switch(name.hashCode()) {").append(BRK);

        for(Map.Entry<Integer, Object[]> entry : map.entrySet()) {
            int hashCode = entry.getKey();
            Object[] arr = entry.getValue();

            String propName = (String) arr[0];
            idxBody.append("case ").append(hashCode).append(": //").append(propName).append(BRK);

            //check if hashCode crash with another one
            if (arr[2] != null) {
                do {
                    propName = (String) arr[0];
                    Integer propId = (Integer) arr[1];

                    idxBody.append("   if(name.equals(\"").append(propName).append("\")) { return ").append(propId).append("; }").append(BRK);

                    arr = (Object[])arr[2];
                } while(arr != null);
            } else {
                Integer propId = (Integer) arr[1];

                idxBody.append("   return ").append(propId).append(";").append(BRK);
            }
        }
        idxBody.append("default: return -1;").append(BRK).append("}");

        idxMr.setBody(idxBody);

        //make a non-static method as well
        idxMr = cc.createMethod("getPropertyIndex");
        idxMr.setAccess("public");
        idxMr.setReturnType("int");
        idxMr.addArg("java.lang.String", "name");
        idxMr.setBody(String.format("return %s(name);", GET_PROP_INDEX_STATIC));
    }

    protected void addMetaProperties() {
    	if(CGConstants.HDPROPS) {
    		cc.addInterface(NeedsStaticInitialization.class.getName());
    		cc.addMember("public static", META_PROP_CLASS + "[]", CGConstants.META_PROPS_ARRAY);
    		MethodRecWriter mr = cc.createMethod("public static", "void", NeedsStaticInitialization.INIT_METHOD_NAME);
    		mr.addArg(RuleServiceProvider.class.getName(), "rsp");
    		StringBuilder body = new StringBuilder(CGConstants.META_PROPS_ARRAY + " = ");
    		appendMetaPropsInitBody(body);
    		body.append(";").append(BRK);
    		
    		body.append(CGConstants.CONCEPT_PROP_IDXS_ARRAY).append(" = ")
    			.append("makeConceptPropIdxs(").append(CGConstants.META_PROPS_ARRAY).append(");");
    		
    		mr.setBody(body);
    
        	cc.addMember("public static", "int[]", CGConstants.CONCEPT_PROP_IDXS_ARRAY);
        	
    	} else {
	    	StringBuilder initBody = new StringBuilder();
	    	appendMetaPropsInitBody(initBody);
	        cc.addMember("public static final", META_PROP_CLASS + "[]", CGConstants.META_PROPS_ARRAY, initBody.toString());
	    	cc.addMember("public static final", "int[]", CGConstants.CONCEPT_PROP_IDXS_ARRAY
	    			, "makeConceptPropIdxs(" + CGConstants.META_PROPS_ARRAY + ")");
    	}
    	
    	MethodRecWriter rec = cc.createMethod("protected", "int[]", CGConstants.CONCEPT_PROP_IDXS_METHOD);
        rec.setBody("return " + CGConstants.CONCEPT_PROP_IDXS_ARRAY + ";");

    	addGetMetaProperty();
    }
    
    protected void addGetMetaProperty() {
        MethodRecWriter rec = cc.createMethod("public", META_PROP_CLASS, "getMetaProperty");
        rec.addArg("int", "propIndex");
        rec.setBody(String.format("return %s(propIndex);", GET_METAPROP_STATIC));

        rec = cc.createMethod("static public", META_PROP_CLASS, GET_METAPROP_STATIC);
        rec.addArg("int", "propIndex");
        rec.setBody(String.format("return %s[propIndex];", CGConstants.META_PROPS_ARRAY));
    }

    abstract protected void appendMetaPropsInitBody(StringBuilder bld);

    protected void appendMetaPropsInitBody(String[] mprops, int len, boolean isAtomic, StringBuilder bld) {
        if(METAPROP_BODY_CONSTRUCTORS) {
        	appendMetaPropsInitBody_constructors(mprops, len, bld);
        } else {
        	appendMetaPropsInitBody_string(mprops, len, isAtomic, bld);
        }
    }
    protected void appendMetaPropsInitBody_constructors(String[] mprops, int len, StringBuilder bld) {
    	bld.ensureCapacity(bld.length() + len + 2*mprops.length + 100);
        bld.append("new ").append(META_PROP_CLASS).append("[]{");
        int ccIdx = 0;
        for(String str : mprops) {
            ccIdx = MetaPropertyEncoder.appendWithCCIdx(bld, str, ccIdx);
            bld.append(", ");
        }
        bld.append("}");
    }
    protected void appendMetaPropsInitBody_string(String[] mprops, int len, boolean isAtomic, StringBuilder bld) {
    	String start = String.format("new %s(", MetaPropertyDecoder.class.getName());
    	String end = String.format(".toString()).decode(%s.class.getClassLoader(), %s)", className(), isAtomic);
        //for the leading int used to store mprops.length
        len += 2;
        int extra = 0;
        extra += start.length() + end.length();
        extra += "new ".length() + StringBuilder.class.getName().length();
        //plus 1 for the initial count
        extra += (mprops.length + 1) * (".append(\"\")").length();
        //final semicolon plus anything forgotten
        extra += 100;

        bld.ensureCapacity(bld.length() + len + extra);

        bld.append(start);
        bld.append("new ").append(StringBuilder.class.getName());
        bld.append("(").append(len).append(")");

        bld.append(".append(\"");
        MetaPropertyEncoder.appendInt(mprops.length, bld);
        bld.append("\")");
        for(String s : mprops) {
            bld.append(".append(\"");
            bld.append(s);
            bld.append("\")");
        }
        bld.append(end);
    }

    protected String nullOkMethodName(StateMachine sm) {
        return "get_NullOK_" + ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm);
    }

    protected String smPropType(StateMachine sm) {
        return String.format("%s<%s>"
                , PropertyStateMachineImpl.class.getName(), ModelNameUtil.modelNametoStateMachineClassName(sm.getOwnerConcept(), sm));
    }
}
