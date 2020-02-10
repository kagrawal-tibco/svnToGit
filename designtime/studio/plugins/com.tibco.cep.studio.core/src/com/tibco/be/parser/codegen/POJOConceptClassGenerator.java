package com.tibco.be.parser.codegen;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 16, 2007
 * Time: 11:13:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class POJOConceptClassGenerator {

    private static final String DEFAULT_SUPER_CLASS_PACKAGE = com.tibco.cep.runtime.model.element.impl.EntityImpl.class.getPackage().getName();
    private static final String DEFAULT_SUPER_CLASS_NAME= "EntityImpl";

    private static final String DEFAULT_INTERFACE_NAME = com.tibco.cep.runtime.model.pojo.Pojo.class.getName();


    private static final String BRK = CGConstants.BRK;
    public static final String SET_PREFIX = "set";
    public static final String GET_PREFIX = "get";

    public static JavaFileWriter makePOJOConceptFile(Concept cept, Properties oversizeStringConstants, Map ruleFnUsage) {
        JavaFileWriter file = new JavaFileWriter(cept.getName(), ModelNameUtil.modelPathToExternalForm(cept.getFolderPath()));
        JavaClassWriter cc = new JavaClassWriter(cept.getName(), null);

        cc.setAccess("public");

        cc.setSuperClass(getSuperClassFSName(cept));
        cc.addInterface(DEFAULT_INTERFACE_NAME);

        getNamespaceLocalName(cept,cc);
        addStaticInitializer(cept,cc);
        addConstructors(cept, cc);
        addGetSet(cept, cc);
        //addCollectionMethods(cept,cc);
        addMembers(cept,cc);
        addFactoryMethod(cept,cc);
        addIdentityMethods(cept,cc);
        addSerializableLiteMethods(cept,cc);

        file.addClass(cc);

        return file;

    }

    private static void addSerializableLiteMethods(Concept cept, JavaClassWriter cc) {

        StringBuilder body = new StringBuilder();
        MethodRecWriter mr = cc.createMethod("writeExternal");
        mr.setAccess("public");
        mr.setReturnType("void");
        mr.addThrows("java.io.IOException");
        mr.addArg("java.io.ObjectOutput", "out");
        body.append("if (java.io.Externalizable.class.isAssignableFrom(implClass)) ((java.io.Externalizable)target).writeExternal(out);").append(BRK);
        //TODO - Should implement the basic property Serializer
        mr.setBody(body);

        body = new StringBuilder();
        mr = cc.createMethod("readExternal");
        mr.setAccess("public");
        mr.setReturnType("void");
        mr.addThrows("java.io.IOException");
        mr.addThrows("java.lang.ClassNotFoundException");
        mr.addArg("java.io.ObjectInput", "in");
        body.append("if (java.io.Externalizable.class.isAssignableFrom(implClass)) ((java.io.Externalizable)target).readExternal(in);").append(BRK);
        //TODO - Should implement the basic property Serializer
        mr.setBody(body);
    }

    private static void addIdentityMethods(Concept cept, JavaClassWriter cc) {

        StringBuilder body = new StringBuilder();
        MethodRecWriter mr = cc.createMethod("hashCode");
        mr.setAccess("public");
        mr.setReturnType("int");
        mr.setBody("return target.hashCode();");

        mr = cc.createMethod("equals");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        mr.addArg("java.lang.Object", "other");
        body = new StringBuilder();

        String genClass = ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath());
        body.append("if (other instanceof " + genClass + ") return target.equals((("+genClass +")other).getImpl());").append(BRK);
        body.append("return target.equals(other);");
        mr.setBody(body);

        mr = cc.createMethod("getImpl");
        mr.setAccess("public");
        mr.setReturnType(cept.getPOJOImplClassName());
        mr.setBody("return target;" + BRK);


    }

    private static void addMembers(Concept cept, JavaClassWriter cc) {

        Collection propDefs = cept.getLocalPropertyDefinitions();
        StringBuilder body;
        for(Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition)it.next();
            String name = ModelNameUtil.generatedMemberName(pd.getName());

            int type = pd.getType();
            if ((type ==  RDFTypes.CONCEPT_REFERENCE_TYPEID) || (type == RDFTypes.CONCEPT_TYPEID)) {
                if (!pd.isArray()) {
                    String referencedType = ModelNameUtil.modelPathToGeneratedClassName(pd.getConceptType().getFullPath());
                    cc.addMember("private", referencedType, name, "null");
                }
                else {
                    //cc.addMember("private", "java.util.HashMap", name, "new java.util.HashMap");
                }
            }

        }


    }

    private static void addStaticInitializer(Concept cept, JavaClassWriter cc) {
        cc.addMember("private static", "java.lang.Class", "implClass");
        cc.addMember("private", cept.getPOJOImplClassName(), "target");

        StringBuilder buf = new StringBuilder();
        buf.append("try" + BRK);
        buf.append("{" + BRK);
        buf.append("implClass = java.lang.Class.forName(\"" + cept.getPOJOImplClassName() + "\");" + BRK );
        buf.append("} catch (java.lang.ClassNotFoundException cnfe) {" + BRK);
        buf.append("throw new java.lang.RuntimeException(cnfe);" + BRK);
        buf.append("}" + BRK);


        cc.addInitializer(true, buf);

        MethodRecWriter mr = cc.createMethod("Initialize");
        mr.setAccess("public static");
        mr.setReturnType("void");

        StringBuilder strb = new StringBuilder();
        strb.append("try" + BRK);
        strb.append("{" + BRK);
        strb.append("java.lang.reflect.Method m = implClass.getDeclaredMethod(\"Initialize\", new java.lang.Class[]{});").append(BRK);
        strb.append("m.invoke(null,new java.lang.Object[0]);").append(BRK);
        strb.append("} catch (java.lang.Exception cnfe) {};" + BRK);
        mr.setBody(strb);





    }

    public static String getPackageString(Concept concept) {
        String path = concept.getFolderPath();
        return ModelNameUtil.modelPathToExternalForm(path);
    }

    private static String getSuperClassFSName(Concept cept) {
        Concept superConcept = cept.getSuperConcept();
        if(superConcept != null) {
            return  getFSName(superConcept);
        } else {
            return "java.lang.Object"; //it might be something like AbstractPojoImpl - For reasoning.
        }
    }

    private static String getFSName(Concept concept) {
        String path = concept.getFullPath();
        return ModelNameUtil.modelPathToGeneratedClassName(path);
    }

    private static void getNamespaceLocalName(Concept cept, JavaClassWriter cc) {
        String nameSpaceURI = RDFTnsFlavor.BE_NAMESPACE + cept.getNamespace() + cept.getName();
        String localName    = cept.getName();


        cc.addMember("public static", com.tibco.xml.data.primitive.ExpandedName.class.getName(), "concept_expandedName",
                "com.tibco.xml.data.primitive.ExpandedName.makeName(\"" + nameSpaceURI + "\",\"" + localName + "\")");
        MethodRecWriter mr3 = cc.createMethod("getExpandedName");
        mr3.setAccess("public ");
        mr3.setReturnType(com.tibco.xml.data.primitive.ExpandedName.class.getName());
        mr3.setBody("return concept_expandedName;");
    }

    private static void addConstructors(Concept cept, JavaClassWriter cc) {
        MethodRecWriter mr;
        StringBuilder body;

        mr = cc.createMethod(cept.getName());
        mr.setAccess("public");
        mr.addThrows("java.lang.Exception");
        mr.setReturnType("");
        body = new StringBuilder();
        body.append("target = " + "new " + cept.getPOJOImplClassName() + "();"  ).append(BRK);

        mr.setBody(body);


        mr = cc.createMethod(cept.getName());
        mr.setAccess("protected");
        mr.setReturnType("");
        mr.addArg(cept.getPOJOImplClassName(), "$other");
        body = new StringBuilder();

        body.append("target = $other;").append(BRK);
        mr.setBody(body);

    }

    private static void addFactoryMethod(Concept cept, JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod("new" + cept.getName());
        mr.setAccess("public static");
        //mr.addArg("java.lang.String", "extId");

        Collection propDefs = cept.getPropertyDefinitions(false);
        StringBuilder body = new StringBuilder();


        body.append("try {" + BRK);
        //String strId = CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getIdGenerator().nextEntityId()";
        body.append(getFSName(cept) + " instance = new " + getFSName(cept) + "();" + BRK);
       // body.append(CGConstants.getCurrentRuleSession + ".assertObject(instance, true);"+ BRK);
        body.append("return instance;" + BRK);
        body.append("} catch (java.lang.Exception e) {" + BRK);
        body.append("throw new java.lang.RuntimeException(e.getMessage(), e);" + BRK);
        body.append("}" + BRK);

        mr.setReturnType(getFSName(cept));
        mr.setBody(body);
    }



    private static void addGetSet(Concept cept, JavaClassWriter cc) {
        Collection propDefs = cept.getLocalPropertyDefinitions();
        StringBuilder body;
        for(Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition)it.next();
            if (pd.isArray())
            {
                addGetSetForArrayAccess(pd, cc);
            }
            else {
                addGetSetForPropertyOrPrimitiveAccess(pd,cc);
            }


        }
    }

    private static void addGetSetForArrayAccess(PropertyDefinition pd, JavaClassWriter cc) {

        StringBuilder body = null;
        String name = ModelNameUtil.generatedMemberName(pd.getName());
        String camelName = toCamelName(pd.getName());
        String propertyClassNm = ModelNameUtil.modelPathToGeneratedClassName(pd.getConceptTypePath());

        int type = pd.getType();

        if (!(type ==  RDFTypes.CONCEPT_REFERENCE_TYPEID) || (type == RDFTypes.CONCEPT_TYPEID)) { //Only primitive access for gets as of Now
            MethodRecWriter mr = cc.createMethod(GET_PREFIX + name);
            mr.setAccess("public");
            mr.setReturnType(propertyFlagToJavaType(pd,true)); //pro
            mr.setBody(new StringBuilder("return target." + GET_PREFIX + camelName + "();"));

            mr = cc.createMethod(SET_PREFIX + name);
            mr.setAccess("public");
            mr.addArg("int", "index");
            mr.addArg(propertyFlagToJavaType(pd,false), "val");
            mr.setBody(new StringBuilder("target." + GET_PREFIX + camelName + "()[index] = val;"));

        }
        else {
            body = new StringBuilder();
            body.append(pd.getConceptType().getPOJOImplClassName() + " $ret = " + "target." + GET_PREFIX + camelName + "(key);");
            body.append("if ($ret != null) return new " + propertyFlagToJavaType(pd,false) + "($ret);").append(BRK);
            body.append("return null;").append(BRK);

            MethodRecWriter mr = cc.createMethod(GET_PREFIX + name);
            mr.setAccess("public");
            mr.addArg("int", "key");
            mr.setReturnType(propertyFlagToJavaType(pd,false)); //pro

            //mr.setBody(new StringBuilder("return (" + propertyClassNm + ")" + name + ".get(index);"));
            mr.setBody(body);

            mr = cc.createMethod(GET_PREFIX + name);
            mr.setAccess("public");
            mr.addArg("java.lang.String", "key");
            mr.setReturnType(propertyFlagToJavaType(pd,false)); //pro
            //mr.setBody(new StringBuilder("return (" + propertyClassNm + ")" + name + ".get(key);"));
            mr.setBody(body);

            body = new StringBuilder();
            body.append("target." + SET_PREFIX + camelName + "(key,val.getImpl());");

            mr = cc.createMethod(SET_PREFIX + name);
            mr.setAccess("public");
            mr.addArg("int", "key");
            mr.addArg(propertyFlagToJavaType(pd,false), "val");

            mr.setBody(body);

            mr = cc.createMethod(SET_PREFIX + name);
            mr.setAccess("public");
            mr.addArg("java.lang.String", "key");
            mr.addArg(propertyFlagToJavaType(pd,false), "val");

            mr.setBody(body);


        }





    }

    private static void addGetSetForPropertyOrPrimitiveAccess(PropertyDefinition pd, JavaClassWriter cc) {
        StringBuilder body = null;
        String name = ModelNameUtil.generatedMemberName(pd.getName());
        String camelName = toCamelName(pd.getName());

        int type = pd.getType();

        MethodRecWriter mr = cc.createMethod(GET_PREFIX + name);
        mr.setAccess("public");
        mr.setReturnType(propertyFlagToJavaType(pd,false));
        if ((type ==  RDFTypes.CONCEPT_REFERENCE_TYPEID) || (type == RDFTypes.CONCEPT_TYPEID)) {
            mr.setBody(new StringBuilder("return new " + propertyFlagToJavaType(pd,false) + " ( target." + GET_PREFIX + camelName + "());"));
        }
        else {
            mr.setBody(new StringBuilder("return target." + GET_PREFIX + camelName + "();"));
        }

        mr = cc.createMethod(SET_PREFIX + name);
        mr.setAccess("public");
        mr.addArg(propertyFlagToJavaType(pd,false), name);

        if ((type ==  RDFTypes.CONCEPT_REFERENCE_TYPEID) || (type == RDFTypes.CONCEPT_TYPEID)) {

            body = new StringBuilder();
            //body.append("this." + name + "=" + name + ";").append(BRK);
            body.append("target." + SET_PREFIX + camelName + "( " + name + ".getImpl() ); ").append(BRK);
            mr.setBody(body);

        }
        else {
            mr.setBody(new StringBuilder("target." + SET_PREFIX + camelName + "( " + name + ");"));
        }
    }


    private static String toCamelName(String name) {
        char c = Character.toUpperCase(name.charAt(0));
        StringBuilder buf = new StringBuilder().append(c).append(name.substring(1));
        return buf.toString();

    }

    private static String getGetMethodName(String varName) {
        return CGConstants.GET_PREFIX + CGUtil.firstCap(varName);
    }

    private static String getGetMethodName(PropertyDefinition pd) {
        return getGetMethodName(ModelNameUtil.generatedMemberName(pd.getName()));
    }

    private static String getSetMethodName(String varName) {
        return CGConstants.SET_PREFIX + CGUtil.firstCap(varName);
    }

    private static String getSetMethodName(PropertyDefinition pd) {
        return getSetMethodName(ModelNameUtil.generatedMemberName(pd.getName()));
    }

    private static String propertyFlagToJavaType(PropertyDefinition pd, boolean checkArray) {

        int flag = pd.getType();
        StringBuilder buf = new StringBuilder();
        switch(flag) {
            case RDFTypes.STRING_TYPEID:
                buf.append(String.class.getName()); break;
            case RDFTypes.BOOLEAN_TYPEID:
                buf.append(Boolean.TYPE.getName()); break;
            case RDFTypes.INTEGER_TYPEID:
                buf.append(Integer.TYPE.getName()); break;
            case RDFTypes.LONG_TYPEID:
                buf.append(Long.TYPE.getName()); break;
            case RDFTypes.DOUBLE_TYPEID:
                buf.append(Double.TYPE.getName());break;
            case RDFTypes.DATETIME_TYPEID:
                buf.append(java.util.Calendar.class.getName());break;
            case RDFTypes.CONCEPT_REFERENCE_TYPEID:
            case RDFTypes.CONCEPT_TYPEID:
                buf.append(ModelNameUtil.modelPathToGeneratedClassName(pd.getConceptTypePath()));break;
            default:
                buf.append(Object.class.getName());
        }
        if (checkArray)
            if (pd.isArray()) buf.append("[]");
        return buf.toString();
    }

}
