package com.tibco.be.parser.codegen;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleGrammar;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.semantic.CompilableDeclSymbolTable;
import com.tibco.be.parser.semantic.CompositeModelLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.NodeTypeVisitor;
import com.tibco.be.parser.semantic.RuleInfoSymbolTable;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.VariableTTLTimeEventImpl;
import com.tibco.cep.runtime.service.loader.NeedsStaticInitialization;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.studio.core.util.GvUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 13, 2004
 * Time: 4:32:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventClassGeneratorSmap implements CGConstants {
    private static final String SIMPLE_CLASS_PACKAGE = com.tibco.cep.runtime.model.event.impl.SimpleEventImpl.class.getPackage().getName();
    private static final String SIMPLE_SUPER_CLASS_NAME = "SimpleEventImpl";
    private static final String TIME_CLASS_PACKAGE = com.tibco.cep.runtime.model.event.impl.TimeEventImpl.class.getPackage().getName();
    private static final String TIME_SUPER_CLASS_NAME = "TimeEventImpl";
    private static final String RULE_BASED_CLASS_PACKAGE = com.tibco.cep.runtime.model.event.impl.VariableTTLTimeEventImpl.class.getPackage().getName();
    private static final String RULE_BASED_SUPER_CLASS_NAME = "VariableTTLTimeEventImpl";

    public static JavaClassWriter makeEventClass(Event event, Properties oversizeStringConstants, RuleInfo rinfo, Ontology o
    		, Map<String, Map<String, int[]>> propInfoCache)
    {
//        JavaFile file = new JavaFile(event.getName());
        JavaClassWriter cc = new JavaClassWriter(event.getName(),null);
        cc.setAccess("public ");
        cc.addInterface("com.tibco.cep.runtime.service.debug.SmartStepInto");
//        addPackage(event, file);
//        setSuper(event, cc, usesCache);
        setSuper(event, cc);
        addConstructors(event, cc);
//        addSerializerDeserializer(event,cc);
        getType(event, cc);
        getNamespaceLocalName(event, cc);
        //CGUtil.addRuleRef(cc);

        ArrayList localNames = new ArrayList();
        ArrayList localTypes = new ArrayList();
        ArrayList allNames = new ArrayList();
        ArrayList allTypes = new ArrayList();
        if(event.getType() != Event.TIME_EVENT) {
            addGetDestinationURI(event, cc); //Fixed in the SimpleEventImpl SR-1-8C32KG
//            getChannelDestination(event,cc);
            populateNameTypeArrays(localNames, localTypes, allNames, allTypes, event);
            addMembers(localNames, localTypes, cc);
//            addDISConstructor(event, cc);  //todo - Nikunj?
            addGetSet(localNames, localTypes, cc);
            addGetProperty(allNames, allTypes, cc);
            addGetPropertyAsXmlTypedValue(allNames, allTypes, cc);
            addSetProperty(allNames, allTypes, cc);
            addSetPropertyEx(allNames, allTypes, cc, "java.lang.String");
            addSetPropertyEx(allNames, allTypes, cc, "com.tibco.xml.data.primitive.XmlTypedValue");


           addGetPropertyNames(allNames, cc);
           addGetPropertyTypes(allTypes,cc);
//            addGetPropertyInfoSet(allNames, allTypes, cc);
            addReadDIS(localNames, localTypes, cc);    //todo - Nikunj?
            addWriteDOS(localNames, localTypes,cc);   //todo - Nikunj?
            addSer(localNames, localTypes,cc);
            addDeSer(localNames, localTypes,cc);
            addDeSerWithIndex(allNames,allTypes,cc);

//            addCreateDIS(event, cc);         //todo - Nikunj?
//            addWriteCOS(event,names,types,cc);    //todo - Nikunj?
//            addReadCIS(event,names,types,cc);     //todo - Nikunj?
            addFactoryMethod(event, cc);
//            addSerializationFormat(event,cc);
            addExpiryAction(event, cc, oversizeStringConstants,rinfo, o, propInfoCache);
//            addCreateCopy(event, cc);

            addGetRetryOnException(event, cc);
            addGetTtl(event, cc);
        } else {
            addTimeMembers(event,cc);
//            addtoTimeEventXiNode(event,cc);
        }
        //addFromXiNode(event, names, types, cc);
        //addToXiNode(event, names, types, cc);

        // For SOAPEvent add getter, setter and super interface
        if(event.getSuperEventPath() != null && RDFTypes.SOAP_EVENT.getName().equals(event.getSuperEventPath())){
        	setSOAPEventInterface(cc);
        	addMemberSoapAction(cc);
        	addGetSoapAction(cc);
        	addSetSoapAction(cc);
        }
        addStaticInit(event, cc);                
//        file.addClass(cc);
        return cc;
    }



    private static String getPackageString(Event event) {
        String path = event.getFolderPath();
        return ModelNameUtil.modelPathToExternalForm(path);
    }

//    private static void addPackage(Event event, JavaFile file) {
//        file.setPackage(getPackageString(event));
//    }

    private static void setSuper(Event event, JavaClassWriter cc) {
        if(event.getType() == Event.TIME_EVENT) {
            if(event.getSchedule() == Event.RULE_BASED) {
                cc.setSuperClass(RULE_BASED_CLASS_PACKAGE+ "." + RULE_BASED_SUPER_CLASS_NAME);
            } else {
                cc.setSuperClass(TIME_CLASS_PACKAGE + "." + TIME_SUPER_CLASS_NAME);
            }
        } else {
            cc.setSuperClass(getSuperClassFSName(event));
        }
//        if (usesCache) {
//          cc.addInterface("com.tangosol.io.ExternalizableLite");
//        }
//        if(event.getSuperEventPath() != null && RDFTypes.SOAP_EVENT.getName().equals(event.getSuperEventPath())){
//        	cc.addInterface(com.tibco.cep.runtime.model.event.SOAPEvent.class.getName());
//        }
    }



    private static String propertyFlagToJavaType(int flag) {
        switch(flag) {
            case RDFTypes.BOOLEAN_TYPEID:
                return boolean.class.getName();
            case RDFTypes.INTEGER_TYPEID:
                return int.class.getName();
            case RDFTypes.LONG_TYPEID:
                return long.class.getName();
            case RDFTypes.DOUBLE_TYPEID:
                return double.class.getName();
            case RDFTypes.DATETIME_TYPEID:
                return java.util.Calendar.class.getName();
            default:
                return String.class.getName();
        }
    }
    
    private static String getFSName(Event event) {
        return ModelNameUtil.modelPathToGeneratedClassName(event.getFullPath());
    }

    private static String getSuperClassFSName(Event event) {
        Event superEvent = event.getSuperEvent();
        if(superEvent != null) {
            return  getFSName(superEvent);
        } else {
            return SIMPLE_CLASS_PACKAGE + "." + SIMPLE_SUPER_CLASS_NAME;
        }
    }

    private static void populateNameTypeArrays(ArrayList localNames, ArrayList localTypes, ArrayList allNames, ArrayList allTypes, Event event) {

        Iterator props= event.getUserProperties();
        while(props.hasNext()) {
            EventPropertyDefinition propDefn = (EventPropertyDefinition) props.next();
            RDFPrimitiveTerm type = (RDFPrimitiveTerm)propDefn.getType();
            String name = (String)propDefn.getPropertyName();
                int typeFlag = CGUtil.getRDFTermTypeFlag(type);
                localNames.add(name);
                localTypes.add(new Integer(typeFlag));
        }

        props= event.getAllUserProperties().iterator();
        while(props.hasNext()) {
            EventPropertyDefinition propDefn = (EventPropertyDefinition) props.next();
            RDFPrimitiveTerm type = (RDFPrimitiveTerm)propDefn.getType();
            String name = (String)propDefn.getPropertyName();
                int typeFlag = CGUtil.getRDFTermTypeFlag(type);
                allNames.add(name);
                allTypes.add(new Integer(typeFlag));
        }

    }

    private static String propertyDefaultValue(int type) {
        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                return "false";
            case RDFTypes.INTEGER_TYPEID:
            case RDFTypes.LONG_TYPEID:
            case RDFTypes.DOUBLE_TYPEID:
                return "0";
            case RDFTypes.STRING_TYPEID:
                return "null";
            case RDFTypes.DATETIME_TYPEID:
                return "null";
            default:
                return "";
        }
    }

    private static void addMembers(ArrayList names, ArrayList types, JavaClassWriter cc) {
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            int type = ((Integer)types.get(ii)).intValue();
            cc.addMember("public", propertyFlagToJavaType(type), ModelNameUtil.generatedMemberName((String)names.get(ii)), propertyDefaultValue(type));
        }
    }



    private static void addConstructors(Event event, JavaClassWriter cc) {
        MethodRec mr;

//        mr = new MethodRec(event.getName());
        mr = cc.createMethod(event.getName(),null);
        mr.setAccess("public");
        mr.setReturnType("");
        mr.setBody(new StringBuilder("super();"));
//        cc.addMethod(mr);

//        mr = new MethodRec(event.getName());
        mr = cc.createMethod(event.getName(),null);
        mr.setAccess("public");
        mr.setReturnType("");
        mr.addArg("long","id");
        mr.setBody(new StringBuilder("super(id);"));
//        cc.addMethod(mr);

//        mr = new MethodRec(event.getName());
//        mr.setAccess("public");
//        mr.setReturnType("");
//        mr.addArg("java.lang.String", "extId");
//        mr.setBody(new StringBuilder("super(extId);"));
//        cc.addMethod(mr);

        //todo - need this for Huoyi?
//        if(event.getType() == Event.SIMPLE_EVENT) {
//            mr = new MethodRec(event.getName());
//            mr.setAccess("protected");
//            mr.setReturnType("");
//            mr.addArg("long", "id");
//            mr.setBody(new StringBuilder("super(id);"));
//            cc.addMethod(mr);
//        }

        //if(event.getType() == Event.SIMPLE_EVENT) {
//            mr = new MethodRec(event.getName());
            mr = cc.createMethod(event.getName(),null);
            mr.setAccess("public");
            mr.setReturnType("");
            mr.addArg("long", "_id");
            mr.addArg("java.lang.String", "_extId");
            //mr.addThrows("java.io.IOException");
            StringBuilder body = new StringBuilder();
            body.append("super(_id, _extId);" + BRK);
            //mr.addThrows("java.io.IOException");
            mr.setBody(body);
//            cc.addMethod(mr);
        //}
    }


    private static void addGetRetryOnException(
            Event event,
            JavaClassWriter cc) {
        final MethodRec mr = cc.createMethod("getRetryOnException");
        mr.setAccess("public ");
        mr.setReturnType(boolean.class.getName());
        mr.setBody("return " + event.getRetryOnException() + ";");
    }


    private static void addGetSet(ArrayList names, ArrayList types, JavaClassWriter cc) {
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
        	String userPropName = (String)names.get(ii);
            String name = ModelNameUtil.generatedMemberName(userPropName);
            Integer type = (Integer)types.get(ii);
//            MethodRec mr = new MethodRec(GET_PREFIX + name);
            MethodRec mr = cc.createMethod(GET_PREFIX + name,null);
            mr.setAccess("public");
            mr.setReturnType(propertyFlagToJavaType(type.intValue()));
            mr.setBody(new StringBuilder("return " + name + ";"));
//            cc.addMethod(mr);

//            mr = new MethodRec(SET_PREFIX + name);
            mr = cc.createMethod(SET_PREFIX + name,null);
            mr.setAccess("public");
            mr.addArg(propertyFlagToJavaType(type.intValue()), name);
            mr.setBody(String.format("this.%s = %s;%n modified(\"%s\");", name, name, userPropName));
//            cc.addMethod(mr);
        }
    }

    private static String boxPrimitive(String name, int type) {
        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                return "new java.lang.Boolean(" + name + ")";
            case RDFTypes.INTEGER_TYPEID:
                return "new java.lang.Integer(" + name + ")";
            case RDFTypes.LONG_TYPEID:
                return "new java.lang.Long(" + name + ")";
            case RDFTypes.DOUBLE_TYPEID:
                return "new java.lang.Double(" + name + ")";
            default:
                return name;
        }
    }

    private static String castAndUnboxSetValue(int type, String varName) {
        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                return "((" + Boolean.class.getName() + ")" + varName + ").booleanValue()";
            case RDFTypes.INTEGER_TYPEID:
                return "((" + Integer.class.getName() + ")" + varName + ").intValue()";
            case RDFTypes.LONG_TYPEID:
                return "((" + Long.class.getName() + ")" + varName + ").longValue()";
            case RDFTypes.DOUBLE_TYPEID:
                return "((" + Double.class.getName() + ")" + varName + ").doubleValue()";
            case RDFTypes.STRING_TYPEID:
                return "(" + String.class.getName() + ")" + varName;
            case RDFTypes.DATETIME_TYPEID:
                //TODO - SS CHANGED THIS
                // return "(" + Calendar.class.getName() + ")" + varName;
                return  com.tibco.be.model.types.Converter.class.getName() +".convertDateTimeProperty(" + varName + ")";
            default:
                return varName;
        }
    }

    private static String toJavaType(int type) {
        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                return Boolean.class.getName() + ".class";
            case RDFTypes.INTEGER_TYPEID:
                return Integer.class.getName() + ".class";
            case RDFTypes.LONG_TYPEID:
                return Long.class.getName() + ".class";
            case RDFTypes.DOUBLE_TYPEID:
                return Double.class.getName() + ".class";
            case RDFTypes.DATETIME_TYPEID:
                return Calendar.class.getName() + ".class";
            default:
                return null;
        }
    }

    private static String toXmlTypeValue(int type, String varName) {
        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                return varName+ "? " + com.tibco.xml.data.primitive.values.XsBoolean.class.getName() + ".TRUE : "
                                     +  com.tibco.xml.data.primitive.values.XsBoolean.class.getName() +".FALSE";
            case RDFTypes.DATETIME_TYPEID:
                return varName + " == null? null : com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport.convertToXsDateTime(" + varName + ")";
            case RDFTypes.LONG_TYPEID:
                return "new " + com.tibco.xml.data.primitive.values.XsLong.class.getName() + "(" + varName + ")";
            case RDFTypes.DOUBLE_TYPEID:
                return "new " + com.tibco.xml.data.primitive.values.XsDouble.class.getName() + "(" + varName + ")";
            case RDFTypes.INTEGER_TYPEID:
                return "new " + com.tibco.xml.data.primitive.values.XsInt.class.getName() + "(" + varName + ")";
            case RDFTypes.STRING_TYPEID:
                return varName + " == null? null : new " + com.tibco.xml.data.primitive.values.XsString.class.getName() + "(" + varName + ")";
            default:
                return varName;
        }
    }

    private static void addGetProperty(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder("switch(name.hashCode()) {" +BRK);
        ArrayList nameSet = new ArrayList(names);
        ArrayList typeSet = new ArrayList(types);
        while(nameSet.size() != 0) {
            String externalName = (String)nameSet.remove(0);
            String name         = ModelNameUtil.generatedMemberName(externalName);
            int pType           = ((Integer)typeSet.remove(0)).intValue();
            int hashCode        = externalName.hashCode();
//  if(externalName.equals("int1") || externalName.equals("int2") || externalName.equals("long1")) hashCode = 1;

            body.append("case ").append(hashCode).append(": ").append(BRK);

            //check if hashCode crash with another one
            boolean crashed = false;
            List toBeRemove = new LinkedList();
            for(int i = 0; i < nameSet.size(); i++) {
                String checkName  = (String) nameSet.get(i);
                int checkHashCode = checkName.hashCode();
//if(checkName.equals("int1") || checkName.equals("int2") || checkName.equals("long1")) checkHashCode = 1;

                if(hashCode == checkHashCode) {
                    //crashed
                    String varName = ModelNameUtil.generatedMemberName(checkName);
                    int varType = ((Integer)typeSet.get(i)).intValue();
                    body.append("   if(name.equals(\"").append(checkName).append("\")) { return ").append(boxPrimitive("this." + varName, varType)).append("; }" + BRK);
                    crashed = true;
                    toBeRemove.add(checkName);
                }
            }
            if (crashed) {
                body.append("   if(name.equals(\"").append(externalName).append("\")) { return ").append(boxPrimitive("this." + name, pType)).append("; }" + BRK);
                Iterator ite = toBeRemove.iterator();
                while(ite.hasNext()) {
                    String removePD = (String) ite.next();
                    int index = nameSet.indexOf(removePD);
                    nameSet.remove(index);
                    typeSet.remove(index);
                }
            }
            else
                body.append("   return ").append(boxPrimitive("this." + name, pType)).append(";"+BRK);
        }
        body.append("default: throw new java.lang.NoSuchFieldException(name); }");

//        MethodRec mr = new MethodRec("getProperty");
        MethodRec mr = cc.createMethod("getProperty");
        mr.setAccess("public");
        mr.setReturnType("java.lang.Object");
        mr.addArg("java.lang.String", "name");
        mr.addThrows("java.lang.NoSuchFieldException");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static void addGetPropertyAsXmlTypedValue(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder("switch(name.hashCode()) {" +BRK);
        ArrayList nameSet = new ArrayList(names);
        ArrayList typeSet = new ArrayList(types);
        while(nameSet.size() != 0) {
            String externalName = (String)nameSet.remove(0);
            String name         = ModelNameUtil.generatedMemberName(externalName);
            int pType           = ((Integer)typeSet.remove(0)).intValue();
            int hashCode        = externalName.hashCode();
//  if(externalName.equals("int1") || externalName.equals("int2") || externalName.equals("long1")) hashCode = 1;

            body.append("case ").append(hashCode).append(": ").append(BRK);

            //check if hashCode crash with another one
            boolean crashed = false;
            List toBeRemove = new LinkedList();
            for(int i = 0; i < nameSet.size(); i++) {
                String checkName  = (String) nameSet.get(i);
                int checkHashCode = checkName.hashCode();
//if(checkName.equals("int1") || checkName.equals("int2") || checkName.equals("long1")) checkHashCode = 1;

                if(hashCode == checkHashCode) {
                    //crashed
                    String varName = ModelNameUtil.generatedMemberName(checkName);
                    int varType = ((Integer)typeSet.get(i)).intValue();
                    body.append("   if(name.equals(\"").append(checkName).append("\")) { return ").append(toXmlTypeValue(varType, varName)).append("; }" + BRK);
                    crashed = true;
                    toBeRemove.add(checkName);
                }
            }
            if (crashed) {
                body.append("   if(name.equals(\"").append(externalName).append("\")) { return ").append(toXmlTypeValue(pType, name)).append("; }" + BRK);
                Iterator ite = toBeRemove.iterator();
                while(ite.hasNext()) {
                    String removePD = (String) ite.next();
                    int index = nameSet.indexOf(removePD);
                    nameSet.remove(index);
                    typeSet.remove(index);
                }
            }
            else
                body.append("   return ").append(toXmlTypeValue(pType, name)).append(";"+BRK);
        }
        body.append("default: throw new java.lang.NoSuchFieldException(name); }");

//        MethodRec mr = new MethodRec("getPropertyAsXMLTypedValue");
        MethodRec mr = cc.createMethod("getPropertyAsXMLTypedValue");
        mr.setAccess("public");
        mr.setReturnType("com.tibco.xml.data.primitive.XmlTypedValue");
        mr.addArg("java.lang.String", "name");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }


    private static void addSetProperty(ArrayList names, ArrayList types, JavaClassWriter cc) {

        StringBuilder body = new StringBuilder("switch(name.hashCode()) {" +BRK);
        ArrayList nameSet = new ArrayList(names);
        ArrayList typeSet = new ArrayList(types);
        boolean addModifiedCall = false;
        while(nameSet.size() != 0) {
        	addModifiedCall = true;
            String externalName = (String)nameSet.remove(0);
            String name         = ModelNameUtil.generatedMemberName(externalName);
            int pType           = ((Integer)typeSet.remove(0)).intValue();
            int hashCode        = externalName.hashCode();
//  if(externalName.equals("int1") || externalName.equals("int2") || externalName.equals("long1")) hashCode = 1;

            body.append("case ").append(hashCode).append(": ").append(BRK);

            //check if hashCode crash with another one
            boolean crashed = false;
            List toBeRemove = new LinkedList();
            for(int i = 0; i < nameSet.size(); i++) {
                String checkName  = (String) nameSet.get(i);
                int checkHashCode = checkName.hashCode();
//if(checkName.equals("int1") || checkName.equals("int2") || checkName.equals("long1")) checkHashCode = 1;

                if(hashCode == checkHashCode) {
                    //crashed
                    String varName = ModelNameUtil.generatedMemberName(checkName);
                    int varType = ((Integer)typeSet.get(i)).intValue();
                    body.append("   if(name.equals(\"").append(checkName).append("\")) { ").append("this." + varName).append(" = ").append(castAndUnboxSetValue(varType, "value")).append("; break;}" + BRK);
                    crashed = true;
                    toBeRemove.add(checkName);
                }
            }
            if (crashed) {
                body.append("   if(name.equals(\"").append(externalName).append("\")) { ").append("this." + name).append(" = ").append(castAndUnboxSetValue(pType, "value")).append("; break;}" + BRK);
                Iterator ite = toBeRemove.iterator();
                while(ite.hasNext()) {
                    String removePD = (String) ite.next();
                    int index = nameSet.indexOf(removePD);
                    nameSet.remove(index);
                    typeSet.remove(index);
                }
            }
            else
                body.append("   this." + name).append(" = ").append(castAndUnboxSetValue(pType, "value")).append("; break;" + BRK);
        }
        body.append("default: throw new java.lang.NoSuchFieldException(name);" + BRK + "}" + BRK);
        if(addModifiedCall){
        	body.append("modified(name);");
        }
        

        MethodRec mr = cc.createMethod("setProperty");
        mr.setAccess("public");
        mr.addArg("java.lang.String", "name");
        mr.addArg("java.lang.Object", "value");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static void addSer(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder();
        body.append("super._serialize(serializer);" + BRK);
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = ModelNameUtil.generatedMemberName((String)names.get(ii));
            Integer type = (Integer)types.get(ii);
            switch(type.intValue()) {
                case RDFTypes.BOOLEAN_TYPEID:
                    body.append("serializer.startProperty(\"" + names.get(ii) + "\"," + ii + ", true);" + BRK);
                    body.append("serializer.writeBooleanProperty(" + name + ");"+ BRK);
                    body.append("serializer.endProperty();" + BRK);
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append("serializer.startProperty(\"" + names.get(ii) + "\"," + ii + ", " + name + " != null);" + BRK);
                    body.append("if( " + name + " != null) serializer.writeDateTimePropertyCalendar(" + name + ");"+ BRK);
                    body.append("serializer.endProperty();" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append("serializer.startProperty(\"" + names.get(ii) + "\"," + ii + ", true);" + BRK);
                    body.append("serializer.writeLongProperty(" + name + ");"+ BRK);
                    body.append("serializer.endProperty();" + BRK);
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append("serializer.startProperty(\"" + names.get(ii) + "\"," + ii + ", true);" + BRK);
                    body.append("serializer.writeDoubleProperty(" + name + ");" + BRK);
                    body.append("serializer.endProperty();" + BRK);
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append("serializer.startProperty(\"" + names.get(ii) + "\"," + ii + ", true);" + BRK);
                    body.append("serializer.writeIntProperty(" + name + ");"+ BRK);
                    body.append("serializer.endProperty();" + BRK);
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append("serializer.startProperty(\"" + names.get(ii) + "\"," + ii + ", " + name + " != null);" + BRK);
                    body.append("if(" + name + " != null) serializer.writeStringProperty(" + name + ");"+ BRK);
                    body.append("serializer.endProperty();" + BRK);
                    break;
            }

        }
        MethodRecWriter mr = cc.createMethod("_serialize");
        mr.setAccess("public");
        mr.addArg("com.tibco.cep.runtime.model.event.EventSerializer", "serializer");
        //mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static void addDeSer(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder();
        body.append("super._deserialize(deserializer);" + BRK );

        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = ModelNameUtil.generatedMemberName((String)names.get(ii));
            Integer type = (Integer)types.get(ii);
            body.append("if (deserializer.startProperty(\"" + names.get(ii) + "\"," + ii + ")) {" + BRK);
            switch(type.intValue()) {

                case RDFTypes.BOOLEAN_TYPEID:
                    body.append(name + " = deserializer.getBooleanProperty();" + BRK);
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append(name + " = deserializer.getDateTimeProperty();" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append(name + " = deserializer.getLongProperty();"+ BRK);
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append(name + " = deserializer.getDoubleProperty();"+ BRK);
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append(name + " = deserializer.getIntProperty();"+ BRK);
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append(name + " = deserializer.getStringProperty();"+ BRK);
                    break;
            }
            body.append("}" + BRK);
            body.append("deserializer.endProperty();" + BRK);
        }
        //todo - Nikunj need this??
        MethodRecWriter mr = cc.createMethod("_deserialize");
        mr.setAccess("public");
        mr.addArg("com.tibco.cep.runtime.model.event.EventDeserializer", "deserializer");
        //mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static void addDeSerWithIndex(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder();
//        body.append("super.deserialize(deserializer);" + BRK);
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = ModelNameUtil.generatedMemberName((String)names.get(ii));
            Integer type = (Integer)types.get(ii);

            body.append("if (index == " + ii + " ) {" + BRK);
            body.append("if (deserializer.startProperty(\"" + names.get(ii) + "\"," + ii + ")) {" + BRK);
            switch(type.intValue()) {

                case RDFTypes.BOOLEAN_TYPEID:
                    body.append(name + " = deserializer.getBooleanProperty();" + BRK);
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append(name + " = deserializer.getDateTimeProperty();" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append(name + " = deserializer.getLongProperty();"+ BRK);
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append(name + " = deserializer.getDoubleProperty();"+ BRK);
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append(name + " = deserializer.getIntProperty();"+ BRK);
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append(name + " = deserializer.getStringProperty();"+ BRK);
                    break;
            }
            body.append("}" + BRK);
            body.append("deserializer.endProperty();" + BRK);
            body.append("return;" + BRK);
            body.append("}" + BRK);

        }

        //todo - Nikunj need this??
        MethodRecWriter mr = cc.createMethod("deserializeProperty");
        mr.setAccess("public");
        mr.addArg("com.tibco.cep.runtime.model.event.EventDeserializer", "deserializer");
        mr.addArg("int", "index");

        //mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static void addReadDIS(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder();
        //body.append("super.read(is);" + BRK);
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = ModelNameUtil.generatedMemberName((String)names.get(ii));
            Integer type = (Integer)types.get(ii);
            switch(type.intValue()) {

                case RDFTypes.BOOLEAN_TYPEID:
                    body.append(name + " = is.readBoolean();");
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append("if( is.readBoolean() == true ) {" + BRK);
                    body.append(name + " = new java.util.GregorianCalendar(java.util.TimeZone.getTimeZone(is.readUTF()));" + BRK);
                    body.append(name + ".setTimeInMillis(is.readLong());" + BRK);
                    //body.append(name + " = readCalendar(is.readUTF());" + BRK);
                    //body.append(name + " = new java.util.Date(is.readLong());" + BRK);
                    body.append(" } else { " + BRK);
                    body.append(name + " = null;" + BRK);
                    body.append("}" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append(name + " = is.readLong();");
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append(name + " = is.readDouble();");
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append(name + " = is.readInt();");
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append("if( is.readBoolean() == true ) {" + BRK);
                    body.append(name + " = is.readUTF();" + BRK);
                    body.append(" } else { " + BRK);
                    body.append(name + " = null;" + BRK);
                    body.append("}" + BRK);
                    break;
            }
        }

        //todo - Nikunj need this??
        MethodRecWriter mr = cc.createMethod("readFromDataInput");
        mr.setAccess("public");
        mr.addArg("java.io.DataInput", "is");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

               /*
    private static void addWriteCOS(Event event,ArrayList names, ArrayList types, JavaClass cc) {
        StringBuilder body = new StringBuilder();
        body.append("final com.tibco.be.sdk.channelapi.ChannelEventOutputStream out =(com.tibco.be.sdk.channelapi.ChannelEventOutputStream) csos;" + BRK);

        if (event.getSuperEvent() != null) {
            body.append("super.write(csos);" + BRK);
        }
        else {
            body.append("super.write(out);" + BRK);
        }

//        if(event.getSubEventPaths().size() == 0)
//        if (event.getSuperEvent() == null) {
//            body.append("out.setEntityName(com.tibco.xml.data.primitive.ExpandedName.makeName(type,\"" + event.getName() + "\"));" + BRK);
//            body.append("out.setPayload(getPayload());" + BRK);
//        }

        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String propName= (String) names.get(ii);
            String name = ModelNameUtil.generatedMemberName(propName);
            Integer type = (Integer)types.get(ii);
            switch(type.intValue()) {
                case RDFTypes.BOOLEAN_TYPEID:
                    body.append("out.setBooleanProperty(\"" + propName + "\"," + name + ");" + BRK);
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append("if (" + name + " != null) {");
                    body.append("out.setStringProperty(\"" + propName + "\", writeCalendar(" + name + "));" + BRK);
                    body.append("}" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append("out.setLongProperty(\"" + propName + "\"," + name + ");" + BRK);
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append("out.setDoubleProperty(\"" + propName + "\"," + name + ");" + BRK);
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append("out.setIntegerProperty(\"" + propName + "\"," + name + ");" + BRK);
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append("if (" + name + " != null) {");
                    body.append("out.setStringProperty(\"" + propName + "\"," + name + ");" + BRK);
                    body.append("}" + BRK);
                    break;
            }
        }

        MethodRec mr = new MethodRec("write");
        mr.setAccess("public");
        mr.addArg("com.tibco.be.sdk.channelapi.ChannelSendableOutputStream", "csos");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
        cc.addMethod(mr);
    }

    private static void addReadCIS(Event event,ArrayList names, ArrayList types, JavaClass cc) {
        StringBuilder body = new StringBuilder();
        body.append("final com.tibco.be.sdk.channelapi.ChannelEventInputStream in =(com.tibco.be.sdk.channelapi.ChannelEventInputStream) cris;" + BRK);
        body.append("java.lang.Object pv=null;" + BRK);
        body.append("super.read(in);" + BRK);
        boolean b;
        Object o;

        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String propName= (String) names.get(ii);
            String name = ModelNameUtil.generatedMemberName(propName);
            Integer type = (Integer)types.get(ii);
            switch(type.intValue()) {
                case RDFTypes.BOOLEAN_TYPEID:
                    body.append("pv=in.getProperty(\"" + propName + "\");" + BRK);
                    body.append("if (pv != null) {" + BRK);
                    body.append(name + "= ((java.lang.Boolean)pv).booleanValue();}" + BRK);
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append("pv=in.getStringProperty(\"" + propName + "\");" + BRK);
                    body.append("if (pv != null) {" + BRK);
                    body.append(name+  "=readCalendar((String) pv);}" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append("pv=in.getProperty(\"" + propName + "\");" + BRK);
                    body.append("if (pv != null) {" + BRK);
                    body.append(name + "=((java.lang.Long)pv).longValue();}" + BRK);
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append("pv=in.getProperty(\"" + propName + "\");" + BRK);
                    body.append("if (pv != null) {" + BRK);
                    body.append(name + "=((java.lang.Double)pv).doubleValue();}" + BRK);
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append("pv=in.getProperty(\"" + propName + "\");" + BRK);
                    body.append("if (pv != null) {" + BRK);
                    body.append(name + "=((java.lang.Integer)pv).intValue();}" + BRK);
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append("pv=in.getStringProperty(\"" + propName + "\");" + BRK);
                    body.append("if (pv != null) {" + BRK);
                    body.append(name + "= (java.lang.String)pv;}" + BRK);
                    break;
            }
        }

        MethodRec mr = new MethodRec("read");
        mr.setAccess("public");
        mr.addArg("com.tibco.be.sdk.channelapi.ChannelReceivableInputStream", "cris");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
        cc.addMethod(mr);
    }                 */

    /*
    private static void addtoTimeEventXiNode(Event event, JavaClass cc) {
        StringBuilder body = new StringBuilder();
        body.append("setEntityId(node);" + BRK);
        body.append("if( getClosure() != null ) node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(\"closure\"), getClosure());" + BRK);
        body.append("if( getScheduledTime() != null ) node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(\"scheduledTime\"), java2xsd_dt_conv.convertToTypedValue(getScheduledTime()).toString());" + BRK);
        body.append("node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(\"interval\"), Long.toString(getInterval()));" + BRK);
        body.append("node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName(\"ttl\"), Long.toString(getTTL()));" + BRK);
        MethodRec mr = new MethodRec("toXiNode");
        mr.setAccess("public");
        mr.addArg("com.tibco.xml.datamodel.XiNode ", "node");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
        cc.addMethod(mr);
    }*/


    /*
    private static void addtoXiNode(Event event,ArrayList names, ArrayList types, JavaClass cc) {
        StringBuilder body = new StringBuilder();
        if (event.getSuperEvent() == null) {
            body.append("setEntityId(node);" + BRK);
        }

        body.append("super.toXiNode(node);" + BRK);


        int limit = names.size() <= types.size() ? names.size() : types.size();

        for(int ii = 0; ii < limit; ii++) {
            String propName= (String) names.get(ii);
            String name = ModelNameUtil.generatedMemberName(propName);
            Integer type = (Integer)types.get(ii);

            switch(type.intValue()) {
                case RDFTypes.BOOLEAN_TYPEID:
                    body.append("node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\""+propName+"\")).");
                    body.append("appendText(getBooleanProperty(" + name+ "));" + BRK);
                    break;

                case RDFTypes.DATETIME_TYPEID:
                    body.append("if (" + name + " != null) {");
                    body.append("node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\""+propName+"\")).");
                    body.append("appendText(getDateTimeProperty(" + name+ "));" + BRK + "}" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append("node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\""+propName+"\")).");
                    body.append("appendText(getLongProperty(" + name+ "));" + BRK);
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append("node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\""+propName+"\")).");
                    body.append("appendText(getDoubleProperty(" + name+ "));" + BRK);
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append("node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\""+propName+"\")).");
                    body.append("appendText(getIntegerProperty(" + name+ "));" + BRK);
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append("if (" + name + " != null) {");
                    body.append("node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\""+propName+"\")).");
                    body.append("appendText(getStringProperty(" + name+ "));" + BRK + "}" + BRK);
                    break;
            }
        }

        body.append("com.tibco.be.model.runtime.event.EventPayload eventpayload = getPayload();");
        body.append("if (eventpayload != null) {" + BRK);
        body.append("com.tibco.xml.datamodel.XiNode payload= eventpayload.getNode();" + BRK);
        body.append("if (payload != null) {" + BRK);
        body.append("com.tibco.xml.datamodel.XiNode $pn= node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(\"payload\"));" + BRK);
        body.append("$pn.appendChild(payload);" + BRK + "}" + BRK + "}" + BRK);

        MethodRec mr = new MethodRec("toXiNode");
        mr.setAccess("public");
        mr.addArg("com.tibco.xml.datamodel.XiNode ", "node");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
        cc.addMethod(mr);
    }*/

    private static void addWriteDOS(ArrayList names, ArrayList types, JavaClassWriter cc) {
        StringBuilder body = new StringBuilder();
        //body.append("super.write(os);" + BRK);
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = ModelNameUtil.generatedMemberName((String)names.get(ii));
            Integer type = (Integer)types.get(ii);
            switch(type.intValue()) {
                case RDFTypes.BOOLEAN_TYPEID:
                    body.append("os.writeBoolean(" + name + ");");
                    break;
                case RDFTypes.DATETIME_TYPEID:
                    body.append("if(" + name + " != null) {" + BRK);
                    body.append("os.writeBoolean(true);" + BRK);
                    body.append("os.writeUTF(" + name + ".getTimeZone().getID());" + BRK);
                    body.append("os.writeLong(" + name + ".getTimeInMillis());" + BRK);
                    //body.append("os.writeUTF(writeCalendar(" + name + "));" + BRK);
                    body.append("} else {" + BRK);
                    body.append("os.writeBoolean(false);" + BRK);
                    body.append("}" + BRK);
                    break;
                case RDFTypes.LONG_TYPEID:
                    body.append("os.writeLong(" + name + ");");
                    break;
                case RDFTypes.DOUBLE_TYPEID:
                    body.append("os.writeDouble(" + name + ");");
                    break;
                case RDFTypes.INTEGER_TYPEID:
                    body.append("os.writeInt(" + name + ");");
                    break;
                case RDFTypes.STRING_TYPEID:
                    body.append("if(" + name + " != null) {" + BRK);
                    body.append("os.writeBoolean(true);" + BRK);
                    body.append("os.writeUTF(" + name + ");" + BRK);
                    body.append("} else {" + BRK);
                    body.append("os.writeBoolean(false);" + BRK);
                    body.append("}" + BRK);
                    break;
            }
        }

        MethodRecWriter mr = cc.createMethod("writeToDataOutput");
        mr.setAccess("public");
        mr.addArg("java.io.DataOutput", "os");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    /*
    private static void addCreateDIS(Event event, JavaClass cc) {
        MethodRec mr = new MethodRec("create");
        mr.setAccess("public");
        mr.setReturnType("com.tibco.cep.runtime.service.om.impl.attic.StreamCreatable");
        mr.addArg("java.io.DataInputStream", "is");
        mr.addThrows("java.io.IOException");
        mr.setBody(new StringBuilder("return new " + event.getName() + "(is);"));
        cc.addMethod(mr);
    } */

    private static void addDISConstructor(Event event, JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod(event.getName());
        mr.setAccess("public");
        mr.setReturnType("");
        mr.addArg("java.io.DataInputStream", "is");
        mr.addThrows("java.io.IOException");
        StringBuilder body = new StringBuilder();
        body.append("super(is);" + BRK);
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static MethodRecWriter genActionBody(String methodName, Rule rule, Properties oversizeStringConstants
    		, JavaClassWriter eventClass, RuleInfo rinfo, Ontology o, Map<String, Map<String, int[]>> propInfoCache)
    {
        StringReader reader = new StringReader(rule.getActionText());
        RuleGrammar parser = new RuleGrammar(reader);
        ActionOnlyParserClient client = new ActionOnlyParserClient();
        parser.setParserClient(client);

        try {
            parser.ActionBlock();
        } catch (ParseException error) {
            client.errors.add(RuleError.makeSyntaxError(error));
        }
        //todo report compile errors if client.errors.size() > 0;

        //do type checking / annotation
        NodeTypeVisitor ntv = new NodeTypeVisitor(new CompilableDeclSymbolTable(rule, o), CompositeModelLookup.getDefaultLookupSet(o), new FunctionsCatalogLookup(o.getName()));
        ntv.populateNodeTypes(client.thenTrees.iterator());

//        RuleInfo rinfo = new RuleInfo();
        rinfo.setDeclarations(rule.getDeclarations());
        rinfo.setThenTrees(client.thenTrees);

        return RuleClassGeneratorSmap.makeActionMethod(eventClass,methodName, rinfo, new RuleInfoSymbolTable(rinfo, o)
        , oversizeStringConstants, rule.getRuleSet().getOntology(), propInfoCache);
    }



    private static void addExpiryAction(Event event, JavaClassWriter eventClass, Properties oversizeStringConstants
    		,RuleInfo rinfo, Ontology o, Map<String, Map<String, int[]>> propInfoCache)
    {
        Rule r = event.getExpiryAction(false);
        if(r == null) return;
        rinfo.setPath(r.getFullPath());
        rinfo.setRuleBlockBuffer(RuleFunctionLineBuffer.fromRule(r));
        if(r == null) return;

        if(ModelUtils.IsEmptyString(r.getActionText())) return;

        MethodRecWriter hasExpiryAction = eventClass.createMethod("public", "boolean", "hasExpiryAction");
        hasExpiryAction.setBody("return true;");
//        eventClass.addMethod(hasExpiryAction);

        MethodRecWriter onExpiry = eventClass.createMethod("public", "void", "onExpiry");
        onExpiry.setBody("onExpiryInternal(new java.lang.Object[] { this });");
//        eventClass.addMethod(onExpiry);

        MethodRecWriter onExpiryInternal = genActionBody("onExpiryInternal", r, oversizeStringConstants,eventClass,rinfo, o, propInfoCache);
        onExpiryInternal.setAccess("public");
        onExpiryInternal.setReturnType("void");
//        onExpiry.addArg("java.lang.Object[]", "objects");

//        eventClass.addMethod(onExpiryInternal);
        eventClass.setSourceText(r.getActionText());
    }

    private static void getType(Event event, JavaClassWriter cc) {
        cc.addMember("public static", String.class.getName(), "type",
			'"' + RDFTnsFlavor.BE_NAMESPACE + event.getNamespace() + event.getName() + '"');
        MethodRecWriter mr = cc.createMethod("getType");
//        MethodRec mr = new MethodRec("getType");
        mr.setAccess("public");
        mr.setReturnType(String.class.getName());
        mr.setBody("return type;");
//        cc.addMethod(mr);


        cc.addMember("public static ", "int", "TYPEID" , "0");
        mr = cc.createMethod("getTypeId");
//        mr = new MethodRec("getTypeId");
        mr.setReturnType("int");
        mr.setAccess("public");
        mr.setBody("return TYPEID;");
//        cc.addMethod(mr);


    }



    private static void getNamespaceLocalName(Event event, JavaClassWriter cc) {
        /*cc.addMember("public static", String.class.getName(), "event_namespaceURI",
			'"' + RDFTnsFlavor.BE_NAMESPACE + event.getNamespace() + event.getName() + '"');
        MethodRec mr = new MethodRec("getNamespaceURI");
        mr.setAccess("public ");
        mr.setReturnType(String.class.getName());
        mr.setBody("return event_namespaceURI;" + BRK);
        cc.addMethod(mr);

        cc.addMember("public static", String.class.getName(), "event_localName",
			'"' + event.getName() + '"');
        MethodRec mr2 = new MethodRec("getLocalName");
        mr2.setAccess("public ");
        mr2.setReturnType(String.class.getName());
        mr2.setBody("return event_localName;" + BRK);
        cc.addMethod(mr2);  */

        String nameSpaceURI = RDFTnsFlavor.BE_NAMESPACE + event.getNamespace() + event.getName();
        String localName    = event.getName();


        cc.addMember("public static", com.tibco.xml.data.primitive.ExpandedName.class.getName(), "event_expandedName",
                "com.tibco.xml.data.primitive.ExpandedName.makeName(\"" + nameSpaceURI + "\",\"" + localName + "\")");
//        MethodRec mr3 = new MethodRec("getExpandedName");
        MethodRec mr3 = cc.createMethod("getExpandedName");
        mr3.setAccess("public ");
        mr3.setReturnType(com.tibco.xml.data.primitive.ExpandedName.class.getName());
        mr3.setBody("return event_expandedName;");
//        cc.addMethod(mr3);
    }



    private static void addGetDestinationURI(Event event, JavaClassWriter cc) {
//        MethodRec mr = new MethodRec("getDestinationURI");
        MethodRec mr = cc.createMethod("getDestinationURI");
        mr.setAccess("public ");
        mr.setReturnType(String.class.getName());
        final StringBuilder sb = new StringBuilder("if (this.destinationURI != null) return this.destinationURI;");
        if((event.getChannelURI() != null) && (event.getChannelURI().length() > 0)) {
            sb.append("return \"")
                    .append(event.getChannelURI())
                    .append("/")
                    .append(event.getDestinationName())
                    .append("\";")
                    .append(BRK);
        } else {
            sb.append("return super.getDestinationURI();")
                    .append(BRK);
        }
        mr.setBody(sb.toString());
//        cc.addMethod(mr);
    }


//    private static void getChannelDestination(Event event, JavaClass cc) {
//        cc.addMember("public static", ChannelDestination.class.getName(), "$destination",null);
//
//        MethodRec mr = new MethodRec("getChannelDestination");
//        mr.setAccess("public ");
//        mr.setReturnType(ChannelDestination.class.getName());
//        mr.setBody("return $destination;" + BRK);
//        cc.addMethod(mr);
//
//        MethodRec mr1 = new MethodRec("setChannelDestination");
//        mr1.addArg(ChannelDestination.class.getName(), "destinationRef");
//        mr1.setAccess("public synchronized ");
//        mr1.setReturnType("void ");
//        mr1.setBody("if ($destination == null) {" + BRK+ "$destination=destinationRef;}" + BRK);
//        cc.addMethod(mr1);
//    }


    private static void addTimeMembers(Event event, JavaClassWriter cc) {
        //cc.addMember("public static ", "long",      "specifiedTime",String.valueOf(event.getSpecifiedTime()) + "L");

//        cc.addMember("public static ", "int",       "$schedule",String.valueOf(event.getSchedule()));
//        cc.addMember("public static ", "int",       "$intervalUnit",String.valueOf(event.getIntervalUnit()));
//        cc.addMember("public static ", "long",      "$interval",String.valueOf(event.getInterval()) + "L");
//        cc.addMember("public static ", "int",      "$tecount",String.valueOf(event.getTimeEventCount()));

        boolean ruleBased = event.getSchedule() == Event.RULE_BASED;
        if (ruleBased) {
            addScheduleMethod(event, cc, false);
            if(Boolean.valueOf(System.getProperty("java.property.tibco.be.generateScheduleWithExtId", "false")).booleanValue()){
                addScheduleMethod(event, cc, true);
            }
            addCloneMethod(event, cc);
        }
        // Add methods to implement timevent interface
        MethodRec mr2 = cc.createMethod("isRepeating");
        mr2.setAccess("public ");
        mr2.setReturnType(boolean.class.getName());
        mr2.setBody("return " + (event.getSchedule() == Event.REPEAT) + ";");
//        cc.addMethod(mr2);

        mr2 = cc.createMethod("getInterval");
        mr2.setAccess("public ");
        mr2.setReturnType(long.class.getName());
        if(ruleBased) {
            mr2.setBody("return 0;");
        } else {
        	cc.addMember("public static", long.class.getName(), "INTERVAL");
        	String intervalStr = event.getInterval();
        	if(GvUtil.isGlobalVar(intervalStr)) {
        		mr2.setBody("return INTERVAL;");
        	} else {
                //Only actually use the static field when it's known to be a global variable
                //to avoid looking up a global variable every time.
                //For hotdeploy compatibility the field and NeedsStaticInitialization interface 
                //and corresponding static function must always be generated.
                //However it's better for third party users of generated classes
                //if it can still work while igoring NeedsStaticInitializaiton when not using global vars.
        		StringBuilder sb = new StringBuilder("return ");
        		addIntervalExp(event, sb);
        		sb.append(";");
        		mr2.setBody(sb.toString());
        	}
        }
        
        ///////////////////
        mr2 = cc.createMethod("getTimeEventCount");
        mr2.setAccess("public ");
        mr2.setReturnType(int.class.getName());
        if(ruleBased) {
            mr2.setBody("return 1;");
        } else {
        	cc.addMember("public static", int.class.getName(), "TIME_EVENT_COUNT");
        	String timeEventCountStr = event.getTimeEventCount();
        	if(GvUtil.isGlobalVar(timeEventCountStr)) {
        		mr2.setBody("return TIME_EVENT_COUNT;");
        	} else {
                //Only actually use the static field when it's known to be a global variable
                //to avoid looking up a global variable every time.
                //For hotdeploy compatibility the field and NeedsStaticInitialization interface 
                //and corresponding static function must always be generated.
                //However it's better for third party users of generated classes
                //if it can still work while igoring NeedsStaticInitializaiton when not using global vars.
        		StringBuilder sb = new StringBuilder("return ");
        		addTimeEventCountExp(event, sb);
        		sb.append(";");
        		mr2.setBody(sb.toString());
        	}
        }
        
        
//        cc.addMethod(mr2);
    }
    
    protected static void addIntervalExp(Event event, StringBuilder body) {
    	body.append(intervalMultiplier(event.getIntervalUnit()))
		.append("L * ");
    	
    	String interval = event.getInterval();
    	if(GvUtil.isGlobalVar(interval)) {
    		addIntGVExp(interval, body);
    	} else {
    		body.append(interval);
    	}
    }
    
    protected static void addTimeEventCountExp(Event event, StringBuilder body) {

    	String timeEventCount = event.getTimeEventCount();
    	if(GvUtil.isGlobalVar(timeEventCount)) {
    		addIntGVExp(timeEventCount, body);
    	} else {
    		body.append(timeEventCount);
    	}
    }
    
    private static void addScheduleMethod(Event event, JavaClassWriter cc, boolean useExtId) {
        String name = "schedule" + event.getName();
        if(useExtId) name += "WithExtId";
        MethodRec mr1 = cc.createMethod(name);
        if(useExtId) mr1.addArg(String.class.getName(), "extId");
            mr1.addArg(long.class.getName(), "delay");
            mr1.addArg(String.class.getName(), "closure");
            mr1.addArg(long.class.getName(), "ttl");
            mr1.setAccess("public static  ");
            mr1.setReturnType(TimeEvent.class.getName());
//            mr1.setBody("return " + CGConstants.getCurrentRuleSession + ".getTimeManager().scheduleRuleTimeEvent("+getFSName(event) + ".class, delay,key,ttl);");
            StringBuilder sb = new StringBuilder();
            sb.append("try {").append(BRK);
            sb.append("com.tibco.cep.runtime.session.RuleSession session = ").append(CGConstants.getCurrentRuleSession).append(";").append(BRK);
        	sb.append(getFSName(event)).append(" timeEvent = new ").append(getFSName(event)).append("(session.getRuleServiceProvider().getIdGenerator().nextEntityId(" + getFSName(event) + ".class)");
        if(useExtId) sb.append(", extId");
        sb.append(");").append(BRK);
            sb.append("if (closure != null) timeEvent.setClosure(closure);").append(BRK);
            sb.append("timeEvent.setTTL(ttl);").append(BRK);
            sb.append("timeEvent.setScheduledTime(System.currentTimeMillis() + delay);").append(BRK);
            sb.append("return (" + mr1.returnType + ") ((" + com.tibco.cep.kernel.core.base.BaseTimeManager.class.getName() + ")session.getTimeManager()).scheduleOnceOnlyEvent(timeEvent, delay);").append(BRK);
            sb.append("} catch(Exception ex) { throw new RuntimeException(ex);}");
            mr1.setBody(sb);
//            cc.addMethod(mr1);
    }

    private static void addCloneMethod(Event event, JavaClassWriter cc) {
        MethodRec mr1 = cc.createMethod("cloneTimeEvent");
        mr1.addArg(long.class.getName(), "id");
        mr1.setAccess("public");
        mr1.setReturnType(VariableTTLTimeEventImpl.class.getName());
        StringBuilder sb = new StringBuilder();
        sb.append(VariableTTLTimeEventImpl.class.getName()).append(" timeEvent = new ");
        sb.append(getFSName(event)).append("(id, getExtId());").append(BRK);
        sb.append("if (getClosure() != null) timeEvent.setClosure(getClosure());").append(BRK);
        sb.append("timeEvent.setTTL(getTTL());").append(BRK);
        sb.append("timeEvent.setScheduledTime(getScheduledTimeMillis());").append(BRK);
        sb.append("return timeEvent;");
        mr1.setBody(sb);
    }

    /*
    private static void addSerializationFormat(Event event, JavaClass cc) {
        cc.addMember("public static ", "int",       "$serializationFormat",String.valueOf(event.getSerializationFormat()));

        // Add methods to implement timevent interface
        MethodRec mr2 = new MethodRec("getSerializationFormat");
        mr2.setAccess("public ");
        mr2.setReturnType(int.class.getName());
        mr2.setBody("return $serializationFormat;" + BRK);
        cc.addMethod(mr2);
    } */

    private static void addGetTtl(Event event, JavaClassWriter cc) {
    	cc.addMember("public static", long.class.getName(), "TTL");
    	MethodRec mr2 = cc.createMethod("public", long.class.getName(), "getTTL");
        mr2.setReturnType(long.class.getName());
        
        //Only actually use the static field when it's known to be a global variable
        //to avoid looking up a global variable every time.
        //For hotdeploy compatibility the field and NeedsStaticInitialization interface 
        //and corresponding static function must always be generated.
        //However it's better for third party users of generated classes
        //if it can still work while igoring NeedsStaticInitializaiton when not using global vars.
        if(GvUtil.isGlobalVar(event.getTTL())) {
        	mr2.setBody("return TTL;");
        } else {
        	StringBuilder body = new StringBuilder("return ");
        	addTTLExp(event, body);
        	body.append(";");
        	mr2.setBody(body);
        }
    }
    
    private static void addTTLExp(Event event, StringBuilder body) {
        // multiply out ttl units
        int units = event.getTTLUnits();
        long multiplier = 1;
        switch(units) {
            case Event.MILLISECONDS_UNITS:
                multiplier = 1L;
                break;
            case Event.SECONDS_UNITS:
                multiplier = 1000L;
                break;
            case Event.MINUTES_UNITS:
                multiplier = 60 * 1000L;
                break;
            case Event.HOURS_UNITS:
                multiplier = 60 * 60 * 1000L;
                break;
            case Event.DAYS_UNITS:
                multiplier = 24 * 60 * 60 * 1000L;
                break;
            default:
                multiplier = 1L;
        }

        body.append(multiplier).append("L * ");
        
        String ttlString = event.getTTL();
        if(GvUtil.isGlobalVar(ttlString)) {
	        addIntGVExp(ttlString, body);
        } else {
        	body.append(ttlString);
        }
    }
    
    protected static void addIntGVExp(String gv, StringBuilder body) {
    	body.append(Integer.class.getName());
        body.append(".parseInt(rsp.getGlobalVariables().substituteVariables(\"");
        body.append(gv);
        body.append("\").toString())");
    }

    private static void addStaticInit(Event event, JavaClassWriter cc) {
    	cc.addInterface(NeedsStaticInitialization.class.getName());
    	MethodRecWriter mr = cc.createMethod("public static", "void", NeedsStaticInitialization.INIT_METHOD_NAME);
    	mr.addArg(RuleServiceProvider.class.getName(), "rsp");
    	StringBuilder body = new StringBuilder();
    	
    	if(event.getType() != Event.TIME_EVENT) {
    		body.append("{ TTL = ");
    		addTTLExp(event, body);
    		body.append("; }" + BRK);
    		
    		mr.addThrows(NumberFormatException.class.getName());
    	} else {
    		if(event.getSchedule() == Event.REPEAT) {
    			body.append("{ INTERVAL = ");
    			addIntervalExp(event, body);
    			body.append("; }" + BRK);
    			
    			body.append("{ TIME_EVENT_COUNT = ");
    			addTimeEventCountExp(event, body);
    			body.append("; }" + BRK);
    		}
    	}

    	mr.setBody(body);
    }

    static long intervalMultiplier(int intervalUnit) {
        switch (intervalUnit) {
            case TimeEvent.INTERVAL_UNIT_MILLISECONDS:
                return 1;
            case TimeEvent.INTERVAL_UNIT_SECONDS:
                return 1000L;
            case TimeEvent.INTERVAL_UNIT_MINUTES:
                return 1000L*60L;
            case TimeEvent.INTERVAL_UNIT_HOURS:
                return 1000*60*60L;
                case TimeEvent.INTERVAL_UNIT_DAYS :
                return 1000*60*60*24L;
            default:
                return 1;
        }
    }

    private static void addFactoryMethod(Event event, JavaClassWriter cc) {
//        System.out.println("adding factory method");

//        MethodRec mr = new MethodRec("new" + event.getName());
        MethodRec mr = cc.createMethod("new" + event.getName());
        mr.setReturnType(getFSName(event));
        mr.setAccess("public static");
        StringBuilder body = new StringBuilder();
        String strId = null;
    	strId = CGConstants.getCurrentRuleSession +".getRuleServiceProvider().getIdGenerator().nextEntityId(" + getFSName(event) +".class)";
        
        boolean argsAsArray = CGUtil.areEventConstructorArgsOversize(event);
        if(argsAsArray) {
            mr.addArg("java.lang.Object[]", "$args");
            extractArgFromArray("extId", "java.lang.String", body, 0);
            extractArgFromArray("payload", "java.lang.String", body, 1);
        } else {
            mr.addArg("java.lang.String", "extId");
            mr.addArg(String.class.getName(), "payload");
        }
        
        body.append(getFSName(event) + " instance = new " + getFSName(event) + "(" + strId + ", extId);" + BRK);

        Iterator allProperties = event.getAllUserProperties().iterator();
        for(int argsIdx = 2; allProperties.hasNext(); argsIdx++) {
            EventPropertyDefinition propDefn= (EventPropertyDefinition) allProperties.next();
            String propName= (String) propDefn.getPropertyName();
            String name = ModelNameUtil.generatedMemberName(propName);
            String primitiveType = propertyFlagToJavaType(RDFUtil.getRDFTermTypeFlag(propDefn.getType()));
            if(!argsAsArray) {
                mr.addArg(primitiveType, name);
            } else {
                extractArgFromArray(name, primitiveType, body, argsIdx);
            }
            body.append("instance." + SET_PREFIX + name + "(" + name + ");" + BRK);
        }
//        body.append("if (payload != null) {instance.setPayload(new com.tibco.be.model.runtime.event.XiNodePayload(payload,$serializationFormat));" + BRK + "}" + BRK);
        body.append("if (payload != null) {" + BRK);
        body.append("\ttry{" + BRK);
        body.append("\t\t" + com.tibco.cep.runtime.model.event.PayloadFactory.class.getName() + " pFactory = " + CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getTypeManager().getPayloadFactory();" + BRK);
        body.append("\t\t" + com.tibco.cep.runtime.model.event.EventPayload.class.getName() + " evtload = pFactory.createPayload(instance.getExpandedName(), payload);" + BRK);
        body.append("\t\tinstance.setPayload(evtload);"  + BRK);
        body.append("\t} catch(java.lang.Exception e) { ").append(BRK);
        body.append("\t\tif (").append(PayloadValidationHelper.class.getName()).append(".ENABLED) {").append(BRK);
        body.append("\t\t\tthrow (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);").append(BRK);
        body.append("\t\t}").append(BRK);
        body.append("\t}" + BRK);
        body.append("}" + BRK);
        body.append("return instance;" + BRK);
        mr.setBody(body);
//        cc.addMethod(mr);
    }
    
    private static void extractArgFromArray(String name, String primitiveType, StringBuilder body, int argsIdx) {
        //int flag = CodegenFunctions.unbox((java.lang.Integer) $args[1]);
        String boxedType = CGUtil.convertNonboxedToBoxed(primitiveType);
        boolean unbox = !boxedType.equals(primitiveType);
        body.append(primitiveType).append(" ").append(name).append(" = "); //int flag = 
        if(unbox) body.append(CoreCGConstants.unboxBoxed).append("(");      //CodegenFunctions.unbox(
        body.append("(").append(boxedType).append(")");     //(java.lang.Integer)
        body.append("$args[").append(argsIdx).append("]"); //$args[1]
        if(unbox) body.append(")");
        body.append(";").append(BRK);
    }

    private static void addGetPropertyNames(ArrayList names, JavaClassWriter cc) {
        //add public static String[] propertyNames
        StringBuilder init = new StringBuilder();
        init.append("new " + String.class.getName() + "[] { ");
        boolean isFirst = true;
        for(int ii = 0; ii < names.size(); ii++) {
            String propName = (String)names.get(ii);
            if(propName != null) {
                if(!isFirst || (isFirst = false)) {
                    init.append(", ");
                }
                init.append('"' + propName + '"');
            }
        }
        init.append(" }");
        cc.addMember("public static ", String.class.getName() + "[]", "propertyNames", init.toString());

        //add public static String[] getPropertyNames()
//        MethodRec mr = new MethodRec("getPropertyNames");
        MethodRec mr = cc.createMethod("getPropertyNames");
        mr.setAccess("public ");
        mr.setReturnType(String.class.getName() + "[]");
        mr.setBody(new StringBuilder("return propertyNames;"));
//        cc.addMethod(mr);
    }

    private static void addGetPropertyTypes(ArrayList types, JavaClassWriter cc) {
        //add public static String[] propertyNames
        StringBuilder init = new StringBuilder();
        init.append("new int[] { ");
        boolean isFirst = true;
        for(int ii = 0; ii < types.size(); ii++) {
            Integer propType = (Integer)types.get(ii);
            if(propType != null) {
                if(!isFirst || (isFirst = false)) {
                    init.append(", ");
                }
                init.append( propType.intValue());
            }
        }
        init.append(" }");
        cc.addMember("public static ", " int [] ", "propertyTypes", init.toString());

        //add public static String[] getPropertyNames()
//        MethodRec mr = new MethodRec("getPropertyTypes");
        MethodRec mr = cc.createMethod("getPropertyTypes");
        mr.setAccess("public ");
        mr.setReturnType("int []");
        mr.setBody(new StringBuilder("return propertyTypes;"));
//        cc.addMethod(mr);
    }

//    public static EventPropertyInfo[] propertyInfo = new EventPropertyInfo[]{
//            new EventPropertyInfo("p_String", java.lang.String.class)
//    };
//
//    public EventPropertyInfo[] getPropertyInfoSet() {
//        return propertyInfo;
//    }


//    private static void addGetPropertyInfoSet(ArrayList names, ArrayList types, JavaClass cc) {
//        StringBuilder init = new StringBuilder();
//        init.append("new EventPropertyInfo[] { ");
//        boolean isFirst = true;
//        for(int ii = 0; ii < names.size(); ii++) {
//            String propName = (String)names.get(ii);
//            Integer type = (Integer)types.get(ii);
//            if(propName != null) {
//                if(!isFirst || (isFirst = false)) {
//                    init.append(", ");
//                }
//                init.append("new EventPropertyInfo(\"" + propName + "\", " + toJavaType(type.intValue()) + ")");
//            }
//        }
//        init.append(" }");
//        cc.addMember("public static ", "EventPropertyInfo[]", "propertyInfoSet", init.toString());
//
//        //add public static String[] getPropertyNames()
//        MethodRec mr = new MethodRec("getPropertyInfoSet");
//        mr.setAccess("public ");
//        mr.setReturnType("EventPropertyInfo[]");
//        mr.setBody(new StringBuilder("return propertyInfoSet;"));
//        cc.addMethod(mr);
//    }

/*
    private static void addSerializerDeserializer(Event evt, JavaClassWriter cc) {
        MethodRec mr;
        StringBuilder body;

        // Add the deserializer
        mr = cc.createMethod("public","void","readExternal");
//        mr = new MethodRec("readExternal");
//        mr.setAccess("public");
//        mr.setReturnType("void");
        mr.addArg("java.io.DataInput", "dataInput");
        body = new StringBuilder();
        body.append("try{" + BRK);
        body.append("com.tibco.cep.runtime.model.serializers.DataInputEventDeserializer deser= new com.tibco.cep.runtime.model.serializers.DataInputEventDeserializer(dataInput,null);" + BRK);
        body.append("this.deserialize(deser);" + BRK);
        body.append("} catch(java.lang.Exception ex) {ex.printStackTrace(); throw new java.lang.RuntimeException(ex);}" + BRK);
        mr.setBody(body);

//        cc.addMethod(mr);
        // Add the serializer

//        public void writeExternal(DataOutput dataOutput) throws IOException {
//            DataOutputConceptSerializer serializer = new DataOutputConceptSerializer(dataOutput);
//            dataOutput.writeBoolean(isRecovered);
//            dataOutput.writeLong(parent.getCacheId());
//            dataOutput.writeInt(parent.currentVersion);
//            instance.serialize(serializer);
//        }
        mr = cc.createMethod("public","void","writeExternal");
//        mr = new MethodRec("writeExternal");
//        mr.setAccess("public");
//        mr.setReturnType("void");
        mr.addArg("java.io.DataOutput", "dataOutput");
        body = new StringBuilder();
        body.append("try{" + BRK);
        body.append("com.tibco.cep.runtime.model.serializers.DataOutputEventSerializer ser= new com.tibco.cep.runtime.model.serializers.DataOutputEventSerializer(dataOutput, this.getClass());" + BRK);
        body.append("this.serialize(ser);" + BRK);
        body.append("} catch(java.lang.Exception ex) {ex.printStackTrace(); throw new java.lang.RuntimeException(ex);}" + BRK);
        mr.setBody(body);
//        cc.addMethod(mr);
    }
    */



    private static void addSetPropertyEx(ArrayList names, ArrayList types, JavaClassWriter cc, String parmClass) {

        StringBuilder body = new StringBuilder("switch(propertyName.hashCode()) {" +BRK);
        ArrayList nameSet = new ArrayList(names);
        ArrayList typeSet = new ArrayList(types);
        boolean addModfiedCall = false;
        
        while(nameSet.size() != 0) {
        	addModfiedCall = true;
            String externalName = (String)nameSet.remove(0);
            String name         = ModelNameUtil.generatedMemberName(externalName);
            int pType           = ((Integer)typeSet.remove(0)).intValue();
            int hashCode        = externalName.hashCode();
//  if(externalName.equals("int1") || externalName.equals("int2") || externalName.equals("long1")) hashCode = 1;

            body.append("case ").append(hashCode).append(": ").append(BRK);

            //check if hashCode crash with another one
            boolean crashed = false;
            List toBeRemove = new LinkedList();
            for(int i = 0; i < nameSet.size(); i++) {
                String checkName  = (String) nameSet.get(i);
                int checkHashCode = checkName.hashCode();
//if(checkName.equals("int1") || checkName.equals("int2") || checkName.equals("long1")) checkHashCode = 1;

                if(hashCode == checkHashCode) {
                    //crashed
                    String varName = ModelNameUtil.generatedMemberName(checkName);
                    int varType = ((Integer)typeSet.get(i)).intValue();
                    body.append("   if(propertyName.equals(\"").append(checkName).append("\")) { ").append("this." + varName).append(" = ").append(castAndUnboxSetValue(varType, "propertyValue")).append("; break;}" + BRK);
                    crashed = true;
                    toBeRemove.add(checkName);
                }
            }
            if (crashed) {
                body.append("   if(propertyName.equals(\"").append(externalName).append("\")) { ").append("this." + name).append(" = ").append(castAndUnboxSetValue(pType, "propertyValue")).append("; break;}" + BRK);
                Iterator ite = toBeRemove.iterator();
                while(ite.hasNext()) {
                    String removePD = (String) ite.next();
                    int index = nameSet.indexOf(removePD);
                    nameSet.remove(index);
                    typeSet.remove(index);
                }
            }
            else
                body.append("   this." + name).append(" = ").append(castAndUnboxSetXMLValue(pType, "propertyValue")).append("; break;" + BRK);
        }
        body.append("default: throw new java.lang.NoSuchFieldException(propertyName);" + BRK + "}" + BRK);
        if(addModfiedCall){
        	body.append("modified(propertyName);");
        }

//        MethodRec mr = new MethodRec("setProperty");
        MethodRec mr = cc.createMethod("setProperty");
        mr.setAccess("public");
        mr.addArg("java.lang.String ", "propertyName");
        mr.addArg(parmClass, "propertyValue");
        mr.addThrows("java.lang.Exception");
        mr.setBody(body);
//        cc.addMethod(mr);
    }

    private static String castAndUnboxSetXMLValue(int type, String varName) {
        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                return com.tibco.be.model.types.Converter.class.getName() + ".convertBooleanProperty(" + varName + ")";
            case RDFTypes.INTEGER_TYPEID:
                return com.tibco.be.model.types.Converter.class.getName() + ".convertIntegerProperty(" + varName + ")";
            case RDFTypes.LONG_TYPEID:
                return com.tibco.be.model.types.Converter.class.getName() + ".convertLongProperty(" + varName + ")";
            case RDFTypes.DOUBLE_TYPEID:
                return com.tibco.be.model.types.Converter.class.getName() + ".convertDoubleProperty(" + varName + ")";
            case RDFTypes.STRING_TYPEID:
                return com.tibco.be.model.types.Converter.class.getName() + ".convertStringProperty(" + varName + ")";
            case RDFTypes.DATETIME_TYPEID:
                return com.tibco.be.model.types.Converter.class.getName() + ".convertDateTimeProperty(" + varName + ")";
            default:
                return varName;
        }
    }

    /*
    private static void addHawkGet(ArrayList names, ArrayList types, JavaClass cc) {
        MethodRec mr = new MethodRec("public", "java.util.List", "hawkGet");
        StringBuilder body = new StringBuilder();
        body.append("java.util.List attrList = super.hawkGet();" + BRK);
        body.append("com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute attrib;" + BRK);

        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String gen_name = ModelNameUtil.generatedMemberName((String)names.get(ii));
            Integer type = (Integer)types.get(ii);

            if(type.intValue() == RDFTypes.DATETIME_TYPEID ) {
                body.append("if( " + gen_name + " != null) attrib = new com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute ( \"" + names.get(ii) + "\", " + gen_name + ".getTime().toString(), com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute.PROPERTY);" + BRK);
                body.append("else attrib = new com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute ( \"" + names.get(ii) + "\", null, com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute.PROPERTY);" + BRK);
                body.append("attrList.add(attrib);" + BRK);
            }
            else if (type.intValue() == RDFTypes.STRING_TYPEID) {
                body.append("if( " + gen_name + " != null) attrib = new com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute ( \"" + names.get(ii) + "\", " + gen_name + ", com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute.PROPERTY);" + BRK);
                body.append("else attrib = new com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute ( \"" + names.get(ii) + "\", null, com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute.PROPERTY);" + BRK);
                body.append("attrList.add(attrib);" + BRK);
            }
            else {
                body.append("attrib = new com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute ( \"" + names.get(ii) + "\", java.lang.String.valueOf(" + gen_name + "), com.tibco.be.engine.model.entity.Impl.EntityHawkAttribute.PROPERTY);" + BRK);
                body.append("attrList.add(attrib);" + BRK);
            }
        }

        body.append("return attrList;");
        mr.setBody(body);
        cc.addMethod(mr);
    }*/

//    private static void addCreateCopy(Event event, JavaClass cc) {
//        StringBuilder body = new StringBuilder();
//        MethodRec mr2 = new MethodRec("createCopy");
//        mr2.setAccess("public");
//        mr2.addThrows("Exception");
//        mr2.setReturnType(Object.class.getName());
//        body.append(getSuperClassFSName(event) + " clone = new " + event.getName() + " (getId());"+ BRK);
//        body.append("this.copyTo(clone);"+ BRK);
//        body.append("return clone;"+ BRK);
//        mr2.setBody(body);
//        cc.addMethod(mr2);
//    }
//}

/*
    private static final String XI_NODE = "com.tibco.xml.datamodel.XiNode";
    private static final String XI_CHILD = "com.tibco.xml.datamodel.helpers.XiChild";
    private static final String ENTITY_NAMES = "com.tibco.be.engine.model.entity.EntityNames";
    private static final String EVENT_NAMES = "com.tibco.be.engine.model.event.EventNames";
    private static final String XML_ATOMIC_VALUE_CAST_EXCEPTION = "com.tibco.xml.data.primitive.XmlAtomicValueCastException";
    private static void addFromXiNode(Event event, ArrayList names, ArrayList types, JavaClass cc) {
        StringBuilder body = new StringBuilder();
        body.append(event.getName() + " event;" + BRK);
        body.append("java.lang.String tmpString;" + BRK);
        body.append(XI_NODE + " tmpNode;" + BRK);

        body.append("tmpNode = " + XI_CHILD + ".getChild(xiNode, " + ENTITY_NAMES + ".URI);" + BRK);
        body.append("tmpString = " + XI_CHILD + ".getString(tmpNode, " + ENTITY_NAMES + ".EXTERNAL_ID);" + BRK);
        body.append("if(tmpString != null) {" + BRK);
        body.append("event = new " + event.getName() + "(tmpString);" + BRK);
        body.append("} else {" + BRK);
        body.append("event = new " + event.getName() + "();" + BRK);
        body.append("}" + BRK);

        body.append("tmpNode = " + XI_CHILD + ".getChild(xiNode, " + EVENT_NAMES + ".CREATE_TSTAMP);" + BRK);
        body.append("try {" + BRK);
        body.append("event.setCreationTime(" + XI_CHILD + ".getLong(tmpNode, " + EVENT_NAMES + ".TIME));" + BRK);
        body.append("} catch (" + XML_ATOMIC_VALUE_CAST_EXCEPTION  + " xavce) {" + BRK);
        body.append("//the creation time is alread set to the current time in the" + BRK);
        body.append("//constructor so if this fails creation time still has a valid value" + BRK);
        body.append("}" + BRK);
        body.append("tmpString = " + XI_CHILD + ".getString(xiNode, " + EVENT_NAMES + ".TIME_ZONE);" + BRK);
        body.append("if(tmpString != null) {" + BRK);
        body.append("event.setTimeZone(java.util.TimeZone.getTimeZone(tmpString));" + BRK);
        body.append("}" + BRK);

        body.append("tmpString = " + XI_CHILD + ".getString(xiNode, " + EVENT_NAMES + ".SOURCE_URI);" + BRK);
        body.append("if(tmpString != null) {" + BRK);
        body.append("event.setSourceURI(tmpString);" + BRK);
        body.append("}" + BRK);
        body.append("tmpString = " + XI_CHILD + ".getString(xiNode, " + EVENT_NAMES + ".CHANNEL_URI);" + BRK);
        body.append("if(tmpString != null) {" + BRK);
        body.append("event.setChannelURI(tmpString);" + BRK);
        body.append("}" + BRK);
        body.append("event.setClosure(" + XI_CHILD + ".getChild(xiNode, " + EVENT_NAMES + ".CLOSURE));" + BRK);
        if(event.getType() != Event.TIME_EVENT) {
            body.append("tmpNode = " + XI_CHILD + ".getChild(xiNode, " + EVENT_NAMES + ".USER_PROPS);" + BRK);
            body.append("if(tmpNode != null) {" + BRK);
            appendFromXiNodeUserProps(body, names, types);
            body.append("}" + BRK);
        }
        body.append("return event;");

        MethodRec mr = new MethodRec("fromXiNode");
        mr.setAccess("public static");
        mr.setReturnType(event.getName());
        mr.addArg(XI_NODE, "xiNode");
        mr.setBody(body);
        cc.addMethod(mr);
    }

    private static void appendFromXiNodeUserProps(StringBuilder body, ArrayList names, ArrayList types) {
        body.append("try {" + BRK);
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = (String)names.get(ii);
            int type = ((Integer)types.get(ii)).intValue();

            body.append("event.");
            body.append((String)names.get(ii));
            body.append(" = ");
            appendXiChildGetMethod(body, name, type);
        }
        body.append("} catch(java.lang.IllegalArgumentException iae) {}" + BRK);
        body.append("  catch(" + XML_ATOMIC_VALUE_CAST_EXCEPTION + " xavce) {}" + BRK);
        body.append("  catch(java.lang.IndexOutOfBoundsException ioobe) {}" + BRK);
    }

    private static void appendXiChildGetMethod(StringBuilder body, String name, int type) {
        body.append(XI_CHILD);
        body.append(".get");

        switch(type) {
            case RDFTypes.BOOLEAN_TYPEID:
                body.append("Boolean");
                break;
            case RDFTypes.INTEGER_TYPEID:
                body.append("Int");
                break;
            case RDFTypes.LONG_TYPEID:
                body.append("Long");
                break;
            case RDFTypes.DOUBLE_TYPEID:
                body.append("Double");
                break;
            case RDFTypes.STRING_TYPEID:
                body.append("String");
                break;
                //todo add case for DateTime
        }

        body.append("(tmpNode, ");
        body.append(EXPANDED_NAME + ".makeName(" + "\"" + name + "\")");
        body.append(")");
        body.append(";" + BRK);
    }

    private static final String XI_FACTORY_FACTORY = "com.tibco.xml.datamodel.XiFactoryFactory";
    private static final String EXPANDED_NAME = "com.tibco.xml.data.primitive.ExpandedName";
    private static void addToXiNode(Event event, ArrayList names, ArrayList types, JavaClass cc) {
        StringBuilder body = new StringBuilder();
        body.append(XI_NODE + " tmpNode;" + BRK);
        body.append(XI_NODE + " xiEvent = " + XI_FACTORY_FACTORY + ".newInstance().createElement(" + EXPANDED_NAME + ".makeName(\"" + getPackageString(event) + "\", \"" + event.getName() + "\"));" + BRK);
        body.append("xiEvent.appendElement(" + ENTITY_NAMES + ".NAME).appendText(\"" + event.getName() + "\");" + BRK);
        body.append("tmpNode = xiEvent.appendElement(" + ENTITY_NAMES + ".URI);" + BRK);
        body.append("tmpNode.appendElement(" + ENTITY_NAMES + ".ID).appendText(String.valueOf(m_uri.id()));" + BRK);
        body.append("if(m_uri.extId() != null) {" + BRK);
            body.append("tmpNode.appendElement(" + ENTITY_NAMES + ".EXTERNAL_ID).appendText(m_uri.extId());" + BRK);
        body.append("}" + BRK);

        body.append("tmpNode = xiEvent.appendElement(" + EVENT_NAMES + ".CREATE_TSTAMP);" + BRK);
        body.append("tmpNode.appendElement(" + EVENT_NAMES + ".TIME_ZONE).appendText(java.util.TimeZone.getDefault().getID());" + BRK);
        body.append("tmpNode.appendElement(" + EVENT_NAMES + ".TIME).appendText(String.valueOf(System.currentTimeMillis()));" + BRK);

        body.append("xiEvent.appendElement(" + EVENT_NAMES + ".TYPE).appendText(String.valueOf(" + EVENT_NAMES + ".TYPE_SIMPLE_EVENT));" + BRK);

        body.append("if(getSourceURI() != null) {" + BRK);
            body.append("xiEvent.appendElement(" + EVENT_NAMES + ".SOURCE_URI).appendText(getSourceURI());" + BRK);
        body.append("}" + BRK);

        body.append("xiEvent.appendElement(" + EVENT_NAMES + ".CHANNEL_URI).appendText(this.getChannelURI());" + BRK);

        if(event.getType() != Event.TIME_EVENT) {
            body.append("tmpNode = xiEvent.appendElement(" + EVENT_NAMES + ".USER_PROPS);" + BRK);
            appendToXiNodeUserProps(body, names, types);
        }

        body.append("xiEvent.appendChild((" + XI_NODE + ")getClosure());" + BRK);
        body.append("return xiEvent;" + BRK);

        MethodRec mr = new MethodRec("toXiNode");
        mr.setAccess("public");
        mr.setReturnType(XI_NODE);
        mr.setBody(body);
        cc.addMethod(mr);
    }

    private static void appendToXiNodeUserProps(StringBuilder body, ArrayList names, ArrayList types) {
        int limit = names.size() <= types.size() ? names.size() : types.size();
        for(int ii = 0; ii < limit; ii++) {
            String name = (String)names.get(ii);
            body.append("tmpNode.appendElement(");
            body.append(EXPANDED_NAME);
            body.append(".makeName(");
            body.append("\"" + name + "\"");
            body.append("), String.valueOf(");
            body.append(name);
            body.append("));" + BRK);
        }
    }
    */

	private static void setSOAPEventInterface(JavaClassWriter eventClass){
		eventClass.addInterface(com.tibco.cep.runtime.model.event.SOAPEvent.class.getName());
	}
	private static void addMemberSoapAction(JavaClassWriter eventClass){
		eventClass.addMember("public", "String", "soapAction" , null);
	}

	private static void addGetSoapAction(JavaClassWriter eventClass){
//        MethodRec mr = new MethodRec("getSoapAction");
//        mr.setAccess("public ");
//        mr.setReturnType(java.lang.String.class.getName());
        MethodRecWriter mr = eventClass.createMethod("public",java.lang.String.class.getName(),"getSoapAction");
        final StringBuilder sb = new StringBuilder("return soapAction;").append(BRK);
        mr.setBody(sb.toString());
//        eventClass.addMethod(mr);
	}
	private static void addSetSoapAction(JavaClassWriter eventClass){
//        MethodRec mr = new MethodRec("setSoapAction");
//        mr.setAccess("public ");
//        mr.setReturnType("void");
        MethodRecWriter mr = eventClass.createMethod("public","void","setSoapAction");
        mr.addArg("java.lang.String", "soapAction");
        final StringBuilder sb = new StringBuilder("this.soapAction = soapAction;").append(BRK);
        mr.setBody(sb.toString());
//        eventClass.addMethod(mr);
	}
}