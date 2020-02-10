package com.tibco.be.parser.codegen;

import static com.tibco.be.parser.codegen.CGConstants.BRK;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.NullContainedConcept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.GeneratedStatsScorecard;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;
import com.tibco.cep.util.NullContainedConceptAccessInConditionException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: May 26, 2004
 * Time: 12:50:37 PM
 * To change this template use File | Settings | File Templates.
 */

public class ConceptClassGeneratorSmap extends BaseConceptClassGeneratorSmap {
    //public static final String ALLOW_FWD_CORRELATE = "TIBCO.BE.allowForwardCorrelation";
    //protected static boolean fwdCorrelationEnabled = Boolean.getBoolean(ALLOW_FWD_CORRELATE);
    public static String DEFAULT_SUPER_CLASS_NAME = com.tibco.cep.runtime.model.element.impl.GeneratedConceptImpl.class.getName();
    private static final String FACTORY_PRIMITIVE_TO_PROPERTY_ARRAY_FUNCTION = com.tibco.cep.util.CodegenFunctions.class.getName() + ".copyPrimitiveToPropertyArray";
    protected static final String NEXT_ID = CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getIdGenerator().nextEntityId(";

    protected Collection<PropertyDefinition> allProps;
    protected Collection<PropertyDefinition> localProps;
    protected Collection<StateMachine> allSMs;
    protected Collection<StateMachine> localSMs;
    protected final Concept cept;
    //protected final boolean hasSuper;
    protected final Concept superCept;
    protected final Map<String, StateMachineBlockLineBuffer> smblbMap;
    protected final Map<String, Map<String, int[]>> propInfoCache;
    protected final boolean isStatsScorecard;
    protected final PropertyDefinition parentPropertyDef;
    protected final boolean isImmediatelyContained;
    protected boolean forceNamedGetSet;

    protected ConceptClassGeneratorSmap(Concept cept, Properties oversizeStringConstants,
                     Map ruleFnUsage, Map<String,StateMachineBlockLineBuffer> smblbMap, JavaClassWriter cc
                     , Ontology o, Map<String, Map<String, int[]>> propInfoCache, boolean forceNamedGetSet)
    {
        super(oversizeStringConstants, ruleFnUsage, cc, o);
        //super(oversizeStringConstants, ruleFnUsage, cc, cept, o);
        this.cept = cept;
        superCept = cept.getSuperConcept();
        //hasSuper = superCept != null;
        this.smblbMap = smblbMap;
        initLists(cept, 0);

        this.propInfoCache = propInfoCache;
        this.forceNamedGetSet = forceNamedGetSet;
        
        isStatsScorecard = Boolean.parseBoolean(System.getProperty(CodeGenConstants.BE_CODEGEN_ENABLE_STATS_SCORECARD, "false"))
                && cept.isAScorecard() && cept.getDescription() != null
                && cept.getDescription().startsWith(GeneratedStatsScorecard.STATS_CONFIG_STRING);

        parentPropertyDef = getParentPropertyDef(cept, o);
        isImmediatelyContained = parentPropertyDef != null && parentPropertyDef.getConceptTypePath().equals(cept.getFullPath());
    }

    protected static PropertyDefinition getParentPropertyDef(Concept cept,Ontology o) {
        PropertyDefinition parent = cept.getParentPropertyDefinition();
        if(parent == null) {
            Collection<Entity> entities = o.getEntities(new ElementTypes[]{ElementTypes.PROCESS});
            for(Entity e:entities) {
                if(e instanceof ProcessModel) {
                    ProcessModel p = (ProcessModel) e;
                    Concept c= (Concept) p.cast(com.tibco.cep.designtime.model.element.Concept.class);
                    for(Iterator it = c.getAllPropertyDefinitions().iterator();it.hasNext();) {
                        Object obj = it.next();
                        if(obj instanceof PropertyDefinition) {
                            PropertyDefinition pd = (PropertyDefinition) obj;
                            if(pd.getType() == PROPERTY_TYPES.CONCEPT_VALUE
                                    && pd.getConceptType() != null
                                    && cept.isA(pd.getConceptType()))
                            {
                                return pd;
                            }
                        }
                    }
                }
            }
        }
        return parent;
    }

    //protected static Collection wrapCollection(Collection col) {
    //    if(col == null) return new ArrayList(0);
    //    else return col;
    //}
    //protected static Collection wrapLinkedHashSet(Collection col) {
    //    if(col == null) return new LinkedHashSet(0);
    //    else if(col instanceof LinkedHashSet) return col;
    //    else return new LinkedHashSet(col);
    //}

    public static JavaClassWriter makeConceptFile(Concept cept, Properties oversizeStringConstants,
           Map ruleFnUsage, Map<String, StateMachineBlockLineBuffer> smblbMap, Ontology o,
           Map<String, Map<String, int[]>> propInfoCache, boolean forceNamedGetSet) throws ModelException
    {
        JavaClassWriter cc = new JavaClassWriter(cept.getName(),null);
        cc.setAccess("public");
        ConceptClassGeneratorSmap classGen = new ConceptClassGeneratorSmap(cept, oversizeStringConstants, ruleFnUsage
                , smblbMap, cc, o, propInfoCache, forceNamedGetSet);
        classGen.makeConceptFile_base();
        classGen.makeConceptFile_conceptProps();
        classGen.makeConceptFile_SM();
        return classGen.cc;
    }

    protected void makeConceptFile_base() throws ModelException {
        setSuper();

        addNewInstanceMethod();
        addMetaProperties();
        addGetProperties();
        addGetLocalProperties();

        addGetParentPropertyMethods();

        addGetType(RDFTnsFlavor.BE_NAMESPACE + cept.getNamespace() + cept.getName());
        addConstructors(cept.getName());
        addGetSet(cc);
        addGetPropertyWithHashCode();

        addFactoryMethod();
        addExcludeNullProps(cc);
        addIncludeNullProps(cc);
        addExpandPropertyRefs(cc, cept);
        addNilAttribs(cc);
        addTreatNullValues(cc);
        addGetExpandedName();

        if (cept.isAScorecard()) {
            addScorecardMethods();
        }

        if (isContained() && generateNullContainedConcept) {
            addNullContainedConcept();
        }

        addGetNumProperties();
        addGetNumDirtyBits();
    }

    protected boolean isContained() {
        return parentPropertyDef != null;
    }

    protected void makeConceptFile_conceptProps() {
        addGetContainedConceptProperties();
        addGetConceptReferenceProperties();
        //uses different strings than getPropertyIndex(String name) uses
        addGetContainedConceptProperty();
        //addGetConceptReferenceProperty();
    }

    protected void makeConceptFile_SM() throws ModelException {
        addIsAutoStartupStateMachine();
        addNewSMWithIndex();
        generateStateMachines();
    }

    //parent state machines come before child properties in the ordering
    protected int initLists(Concept cept, int totalSize) {
        if(cept == null) {
            propIds = new HashMap(totalSize);
            allProps = new LinkedHashSet(totalSize);
            allSMs = new LinkedHashSet(totalSize);
            return 0;
        }

        List<PropertyDefinition> localList = (List<PropertyDefinition>)cept.getLocalPropertyDefinitions();
        List<StateMachine> localSMList = (List<StateMachine>)cept.getStateMachines();

        if(cept == this.cept) {
            localProps = new LinkedHashSet(localList.size());
            localSMs = new LinkedHashSet(localSMList.size());
            for(PropertyDefinition pd : localList) {
                localProps.add(pd);
            }
            for(StateMachine sm : localSMList) {
                localSMs.add(sm);
            }
        }

        int index = initLists(cept.getSuperConcept(), totalSize + localList.size() + localSMList.size());

        for(PropertyDefinition pd : localList) {
            String key = ModelNameUtil.generatedMemberClassName(pd.getName());
            if (propIds.put(key, index++) != null) {
                throw new RuntimeException("initPropertyIds: property name crash: " + pd.getFullPath());
            }
            allProps.add(pd);
        }

        for(StateMachine sm : localSMList) {
            String key = ModelNameUtil.generatedStateMachinePropertyClassName(cept, sm);
            if (propIds.put(key, index++) != null) {
                throw new RuntimeException("initPropertyIds: state machine name crash: " + sm.getFullPath());
            }
            allSMs.add(sm);
        }

        return index;
    }

    protected Integer getPropertyId(PropertyDefinition pd) {
        return propIds.get(ModelNameUtil.generatedMemberClassName(pd.getName()));
    }
    public Integer getPropertyId(StateMachine sm) {
        return propIds.get(ModelNameUtil.generatedStateMachinePropertyClassName(sm.getOwnerConcept(), sm));
    }
    protected Integer getPropertyId(Object pdOrSM) {
        if(pdOrSM instanceof PropertyDefinition) {
            return getPropertyId((PropertyDefinition)pdOrSM);
        } else {
            return getPropertyId((StateMachine)pdOrSM);
        }
    }

    //String name, int historyPolicy, Class type, int historySize, boolean maintainReverseRefs, int containedPropIndex
    @Override
    protected void appendMetaPropsInitBody(StringBuilder bld) {
        String[] mprops = new String[allProps.size() + allSMs.size()];
        int len = 0;

        for(PropertyDefinition pd : allProps) {
            boolean isContained = pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT;
            boolean isRef = pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE;

            String mprop;

            if(isContained || isRef) {
                boolean maintainReverseRefs = true;
                String revRef = (String) pd.getExtendedProperties().get("Reverse Ref");
                if(revRef != null && revRef.equalsIgnoreCase("FALSE")) {
                    maintainReverseRefs = false;
                }
                String typeClassName = getFSName(pd.getConceptType());
                if(METAPROP_BODY_CONSTRUCTORS) {
                    mprop = MetaPropertyEncoder.encodeAsConstructor(pd.getName(), pd.getHistoryPolicy(), typeClassName
                            ,pd.getHistorySize() , maintainReverseRefs, isContained, pd.getType(), pd.isArray()
                            , MetaPropertyEncoder.getDefaultValue(pd), isStatsScorecard);
                } else {
                    mprop = MetaPropertyEncoder.encode(pd.getName(), (byte)pd.getHistoryPolicy(), typeClassName
                            , pd.getHistorySize(), maintainReverseRefs, isContained, (byte)pd.getType(), pd.isArray()
                            , MetaPropertyEncoder.getDefaultValue(pd));
                }
            } else {
                if(METAPROP_BODY_CONSTRUCTORS) {
                    mprop = MetaPropertyEncoder.encodeAsConstructor(pd.getName(), pd.getHistoryPolicy(), pd.getHistorySize()
                            , pd.getType(), pd.isArray(), MetaPropertyEncoder.getDefaultValue(pd), isStatsScorecard);
                } else {
                    mprop = MetaPropertyEncoder.encode(pd.getName(), (byte)pd.getHistoryPolicy(), pd.getHistorySize()
                            , (byte)pd.getType(), pd.isArray(), MetaPropertyEncoder.getDefaultValue(pd));
                }
            }

            len += mprop.length();
            mprops[getPropertyId(pd)] = mprop;
        }
        for(StateMachine sm : allSMs) {
            String typeClassName;
            if(METAPROP_BODY_CONSTRUCTORS) {
                typeClassName = StateMachineClassGeneratorSmap.propClassName(sm.getOwnerConcept(), sm);
            } else {
                typeClassName = ModelNameUtil.stateMachineClassloaderClassName(sm.getOwnerConcept(), sm);
            }
            String smPropName = ModelNameUtil.generatedStateMachinePropertyName(sm.getOwnerConcept(), sm);
            String mprop;
            if(METAPROP_BODY_CONSTRUCTORS) {
                mprop = MetaPropertyEncoder.encodeAsConstructor(smPropName, PropertyDefinition.HISTORY_POLICY_ALL_VALUES
                        , typeClassName, 0, false, true, MetaProperty.SM_CONCEPT, false, null, isStatsScorecard);
            } else {
                mprop = MetaPropertyEncoder.encode(smPropName, (byte)PropertyDefinition.HISTORY_POLICY_ALL_VALUES
                        , typeClassName, 0, false, true, MetaProperty.SM_CONCEPT, false, null);
            }
            len += mprop.length();
            mprops[getPropertyId(sm)] = mprop;
        }

        appendMetaPropsInitBody(mprops, len, isStatsScorecard, bld);
    }

    private void addHasMainStateMachine() {
        StateMachine mainStateMachine = cept.getMainStateMachine();
        if (mainStateMachine != null && localSMs.contains(mainStateMachine)) {
            MethodRecWriter hasStateMachine = cc.createMethod("public", "boolean", "hasMainStateMachine");
            hasStateMachine.setBody("return true;");
        }
    }

    private void generateStateMachines() throws ModelException {
        addHasMainStateMachine();

        StateMachine mainStateMachine = cept.getMainStateMachine();
        if (localSMs.size() > 0) {
            for(StateMachine sm : localSMs) {
                StateMachineBlockLineBuffer smblb = smblbMap.get(sm.getName());
                StateMachineClassGeneratorSmap.makeStateMachineFile(cc,sm, cept, oversizeStringConstants,
                        ruleFnUsage, smblb, this, ontology, propInfoCache);
            }
        }
        addGetMainStateMachineProperty(mainStateMachine);
        addGetStateMachineProperties();
        addGetStateMachineProperty();
        addGetMainStateMachine();
        addGetStateMachines();
    }

//    private void addStateMachineProperty(StateMachine sm) {
//        PropertyDefinition pd = getStateMachinePD(cept, sm);
//        String interfaceName = ModelNameUtil.generatedStateMachinePropertyClassName(cept, sm);
//        addPropertyClass(cept, sm, pd, cc, true, propIds);
//        //TODO if(!omEnabled) // Nikunj: Only generate local property reference if OM disabled.
//        cc.addMember("public", interfaceName, ModelNameUtil.generatedStateMachinePropertyMemberName(cept, sm));
//
//    }

//    private PropertyDefinition getStateMachinePD(StateMachine sm) {
//        PropertyDefinition pd = new MutablePropertyDefinitionAdapter(null, ModelNameUtil.generatedStateMachinePropertyName(cept, sm), null, false,
//                PropertyDefinition.PROPERTY_TYPE_CONCEPT, ModelNameUtil.generatedStateMachinePropertyName(cept, sm),
//                PropertyDefinition.HISTORY_POLICY_ALL_VALUES, 0, null, -1);
//        return pd;
//    }

    //-- SS - For StateMachine - End of Merge Copy

    protected static String getPackageString(Concept concept) {
        String path = concept.getFolderPath();
        return ModelNameUtil.modelPathToExternalForm(path);
    }

    @Override protected String className() {
        return getFSName(cept);
    }

    protected static String getFSName(Concept concept) {
        String path = concept.getFullPath();
        return ModelNameUtil.modelPathToGeneratedClassName(path);
    }

//    private static void addPackage(Concept cept, JavaFile file) {
//        file.setPackage(getPackageString(cept));
//    }

    private String getSuperClassFSName() {
        Concept superConcept = superCept;
        if (superConcept != null) {
            return getFSName(superConcept);
        } else {
            if(cept.isAScorecard()) {
                if(isStatsScorecard) {
                    return GeneratedStatsScorecard.class.getName();
                }
            }
            return DEFAULT_SUPER_CLASS_NAME;
        }
    }

    protected void setSuper() {
        cc.setSuperClass(getSuperClassFSName());

        if (isImmediatelyContained) {
            cc.addInterface(ContainedConcept.class.getName());
        }
        if (cept.isAScorecard()) {
            cc.addInterface(com.tibco.cep.kernel.model.entity.NamedInstance.class.getName());
        }
    }

    private static String getPropertyClass(PropertyDefinition pd) {
        String[] arr;
        if(pd.isArray()) {
            if(pd.getHistorySize() > 0) arr = CGConstants.propertyArrayImplFSClassNames;
            else arr = CGConstants.propertyArrayImplSimpleClassNames;
        } else {
            if(pd.getHistorySize() > 0) arr = CGConstants.propertyAtomImplFSClassNames;
            else arr = CGConstants.propertyAtomImplSimpleClassNames;
        }

        return arr[pd.getType()];
    }

    protected void addGetExpandedName() {
        String nameSpaceURI = RDFTnsFlavor.BE_NAMESPACE + cept.getNamespace() + cept.getName();
        String localName = cept.getName();
        addGetExpandedName(nameSpaceURI, localName);
    }

    private void addScorecardMethods() {
        StringBuilder body = newStringBuilder();
        body.append("try {").append(BRK);
        body.append("return (").append(ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath()))
                .append(")").append(CGConstants.scorecardLookupMethod).append("(\"")
                .append(cept.getFullPath()).append("\",")
                .append(ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath()))
                .append(".class);").append(BRK);
        body.append("} catch (java.lang.Exception ex) {").append(BRK);
        body.append("throw new java.lang.RuntimeException(\"failed to look up Scorecard ")
                .append(cept.getName()).append("\", ex);").append(BRK);
        body.append("}");
        MethodRecWriter mr = cc.createMethod("public static",
                ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath()), CGConstants.scorecardInstanceGetter);
        mr.setBody(body);
    }

    private void addGetContainedConceptProperty() {
        StringBuilder body = newStringBuilder();

        for (PropertyDefinition pd : localProps) {
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                body.append("if(propertyName.equals(\"").append(pd.getName()).append("\")) {").append(BRK);
                body.append("return ").append(propertyGetterExp(pd, true)).append(";").append(BRK);
                body.append("}").append(BRK);
            }
        }

        for(StateMachine sm : localSMs) {
            body.append("if (propertyName.equals(\"")
                    .append(ModelNameUtil.generatedStateMachinePropertyName(cept, sm)).append("\")) {").append(BRK);
            body.append("return get").append(ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm))
                    .append("();").append(BRK);
            body.append("}").append(BRK);
        }

        if(body.length() > 0 || CGConstants.HDPROPS) {
            body.append("return super.getContainedConceptProperty(propertyName);");

            MethodRecWriter mr = cc.createMethod("getContainedConceptProperty");
            mr.setAccess("protected");
            mr.setReturnType(com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyContainedConcept");
            mr.addArg("String", "propertyName");

            mr.setBody(body);
        }
    }

    private void addGetConceptReferenceProperty() {
        StringBuilder body = newStringBuilder();
        for(PropertyDefinition pd  : localProps) {
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) { // ||
                body.append("if(propertyName.equals(\"").append(pd.getName()).append("\")) {").append(BRK);
                body.append("return ").append(propertyGetterExp(pd, true)).append(";").append(BRK);
                body.append("}").append(BRK);
            }
        }

        if(body.length() > 0) {
            body.append("return super.getConceptReferenceProperty(propertyName);");

            MethodRecWriter mr = cc.createMethod("getConceptReferenceProperty");
            mr.setAccess("protected");
            mr.setReturnType(com.tibco.cep.runtime.model.element.Property.class.getName() + ".PropertyConceptReference");
            mr.addArg("String", "propertyName");

            mr.setBody(body);
        }
    }

    private void addGetContainedConceptProperties() {
        StringBuilder arrayBody = newStringBuilder();

        for (PropertyDefinition pd : allProps) {
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                arrayBody.append(propertyGetterExp(pd, true));
                arrayBody.append(", ");
            }
        }
        for(StateMachine sm : allSMs) {
            arrayBody.append("get" + ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm) + "()");
            arrayBody.append(", ");
        }

        if (arrayBody.length() > 0 || CGConstants.HDPROPS) {
            String typeName = CGConstants.propertyTypeInterfaceFSClassNames[RDFTypes.CONCEPT_TYPEID] + "[]";
            MethodRecWriter mr = cc.createMethod("public", typeName, "getContainedConceptProperties");
            mr.setBody(String.format("return new %s { %s };", typeName, arrayBody));
        }
    }

    private void addGetConceptReferenceProperties() {
        StringBuilder arrayBody = newStringBuilder();

        for (PropertyDefinition pd : allProps) {
            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
                arrayBody.append(propertyGetterExp(pd, true));
                arrayBody.append(", ");
            }
        }

        if (arrayBody.length() > 0 || CGConstants.HDPROPS) {
            String typeName = CGConstants.propertyTypeInterfaceFSClassNames[RDFTypes.CONCEPT_REFERENCE_TYPEID] + "[]";
            MethodRecWriter mr = cc.createMethod("public", typeName, "getConceptReferenceProperties");
            mr.setBody(String.format("return new %s { %s };", typeName, arrayBody));
        }
    }

    protected void addGetParentPropertyMethods() {
        if (isImmediatelyContained) {
            addGetParentProperty(parentPropertyDef.getName());
        }
    }
//    protected static void addPropertyClass(Concept cept, StateMachine sm, PropertyDefinition pd,
//                          JavaClassWriter cc, Map<String, Integer> propIds)
//    {
//        addPropertyClass(cept, sm, pd, cc, false, propIds);
//    }

//    private static void addPropertyClass(Concept cept, StateMachine sm, PropertyDefinition pd, JavaClassWriter cc
//            , boolean isStateMachineProperty, Map<String, Integer> propIds)
//    {
//        String propName = ModelNameUtil.generatedMemberClassName(pd.getName());
//        JavaClassWriter propClass = cc.createNestedClassWriter(propName,null);
//        propClass.setAccess("static public");
//
//
//        int historySize = pd.getHistorySize();
//        boolean isArray = pd.isArray();
//        if (isStateMachineProperty) {
//            propClass.setSuperClass(com.tibco.cep.runtime.model.element.impl.simple.PropertyStateMachineImpl.class.getName());
//        } else if (historySize == 0) {
//            if (isArray) {
//                propClass.setSuperClass(CGConstants.propertyArrayImplSimpleClassNames[pd.getType()]);
//            } else {
//                propClass.setSuperClass(CGConstants.propertyAtomImplSimpleClassNames[pd.getType()]);
//            }
//        } else {
//            if (isArray) {
//                propClass.setSuperClass(CGConstants.propertyArrayImplFSClassNames[pd.getType()]);
//            } else {
//                propClass.setSuperClass(CGConstants.propertyAtomImplFSClassNames[pd.getType()]);
//            }
//        }
//        //constructor w/o default value
//        MethodRecWriter cons = propClass.createMethod(propName);
//        cons.setAccess("public");
//        cons.setReturnType("");
//        if (historySize == 0)
//            cons.setBody(new StringBuilder("super(subject);"));
//        else {
//            cons.setBody(new StringBuilder("super(historySize, subject);"));
//            cons.addArg("int", "historySize");
//        }
//        cons.addArg(CGConstants.engineConceptInterface, "subject");
////        propClass.addMethod(cons);
//
//        //constructor w/ default value
//        if (!isArray) {
//            cons = propClass.createMethod(propName);
//            cons.setReturnType("");
//            if (historySize == 0)
//                cons.setBody(new StringBuilder("super(subject, defaultValue);"));
//            else {
//                cons.setBody(new StringBuilder("super(historySize, subject, defaultValue);"));
//                cons.addArg("int", "historySize");
//            }
//            cons.addArg(CGConstants.engineConceptInterface, "subject");
//            cons.addArg(CGConstants.setArgumentTypes[pd.getType()], "defaultValue");
////            propClass.addMethod(cons);
//        }
//        //constructor w/ initial size
//        else {
//            cons = propClass.createMethod(propName);
//            cons.setReturnType("");
//            if (historySize == 0)
//                cons.setBody("super(subject, initialSize);");
//            else {
//                cons.setBody("super(historySize, subject, initialSize);");
//                cons.addArg("int", "historySize");
//            }
//            cons.addArg(CGConstants.engineConceptInterface, "subject");
//            cons.addArg("int", "initialSize");
////            propClass.addMethod(cons);
//        }
//
//        MethodRecWriter getNameMethod = propClass.createMethod("public", "java.lang.String", "getName");
//        getNameMethod.setBody("return \"" + pd.getName() + "\";");
////        propClass.addMethod(getNameMethod);
//
//        propClass.addMember("static public", "int", "propertyIndex", "-1");
//        addGetMethod("public", "int", "propertyIndex", propClass);
//        propClass.addMember("static public", "int", "propertyLevel", "-1");
//        addGetMethod("public", "int", "propertyLevel", propClass);
//        String index = null;
//        if (isStateMachineProperty) {
//            index = propIds.get(ModelNameUtil.generatedStateMachinePropertyClassName(cept, sm));
//        } else {
//            index = propIds.get(ModelNameUtil.generatedMemberClassName(pd.getName()));
//        }
//        if (null == index) {
//            index = "-1";
//            assert false : "-1 property index";
//        }
//        propClass.addMember("static public", "int", "propertyId", index);
//        addGetMethod("public", "int", "propertyId", propClass);
//
//        MethodRecWriter staticGetIndex = propClass.createMethod("static public", "int", "getIndex");
//        staticGetIndex.setBody("return propertyIndex;");
////        propClass.addMethod(staticGetIndex);
//
//        MethodRecWriter staticGetLevel = propClass.createMethod("static public", "int", "getLevel");
//        staticGetLevel.setBody("return propertyLevel;");
////        propClass.addMethod(staticGetLevel);
//
//        propClass.addMember("static public", "int", "historyPolicy", Integer.toString(pd.getHistoryPolicy()));
//        addGetMethod("public", "int", "historyPolicy", propClass);
//
//        if (isArray) {
//            propClass.addMember("static private", "int", "historySize", Integer.toString(pd.getHistorySize()));
//            addGetMethod("public", "int", "historySize", propClass);
//        }
//
//        if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
//            MethodRec maintainReverseRef = propClass.createMethod("public", "boolean", "maintainReverseRef");
//            String revRef = (String) pd.getExtendedProperties().get("Reverse Ref");
//            if(revRef != null && revRef.equalsIgnoreCase("FALSE"))
//                maintainReverseRef.setBody("return false;");
//            else
//                maintainReverseRef.setBody("return true;");
//        }
//
//        if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT || pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
//            if (isStateMachineProperty) {
//                propClass.addMember("static private", "java.lang.Class", "type", pd.getName() + ".class");
//                MethodRecWriter startMachine = propClass.createMethod("public ", "void", "startMachine");
//                startMachine.addArg("long", "id");
//                String clzName = ModelNameUtil.modelNametoStateMachineClassName(cept, sm);
//                StringBuilder body = newStringBuilder();
//                body.append(" boolean assertSMConcept = false;").append(BRK).append(' ');
//                body.append(clzName).append(' ').append("var = (").append(clzName).append(") this.getConcept();").append(BRK);
//                body.append(" if (var == null) {" + BRK + " var = new " + clzName + "(id);").append(BRK);
//                body.append("  this.setValue(var);").append(BRK);
//                body.append("  assertSMConcept = true;").append(BRK);
//                body.append(" }").append(BRK);
//
//                body.append("  var.get" + ModelNameUtil.generatedMemberName(sm.getMachineRoot().getName()) + "().enter(new java.lang.Object[] {this.getParent()});").append(BRK);
//                /**
//                 if(assertSMConcept) {
//                 try {
//                 RuleSessionManager.getCurrentRuleSession().assertObject(cept,false);
//                 } catch (Exception e) {
//                 e.printStackTrace();
//                 throw new RuntimeException(e);
//                 }
//                 }
//                 */
//                body.append(" if(assertSMConcept) {").append(BRK);
//                body.append("  try {").append(BRK);
//                body.append("  ").append(RuleSessionManager.class.getName() + ".getCurrentRuleSession().assertObject(var,false);").append(BRK);
//                body.append("  } catch (java.lang.Exception ex) { " + BRK + "throw new java.lang.RuntimeException(ex);").append(BRK);
//                body.append(" }").append(BRK);
//                body.append("}").append(BRK);
//                startMachine.setBody(body);
////                propClass.addMethod(startMachine);
//
//                MethodRecWriter getMachine = propClass.createMethod("public ", "void", "getMachine");
//                getMachine.setReturnType(clzName);
//                body = newStringBuilder();
//                body.append(" return (" + clzName + ") this.getConcept();").append(BRK);
//                getMachine.setBody(body);
////                propClass.addMethod(getMachine);
//
//                getMachine = propClass.createMethod("public ", "void", "getStateMachineConcept");
//                getMachine.setReturnType(StateMachineConcept.class.getName());
//                body = newStringBuilder();
//                body.append(" return (" + StateMachineConcept.class.getName() + ") this.getConcept();").append(BRK);
//                getMachine.setBody(body);
////                propClass.addMethod(getMachine);
//
//            } else {
//                propClass.addMember("static private", "java.lang.Class", "type", getFSName(pd.getConceptType()) + ".class");
//            }
//
//            addGetMethod("public", "java.lang.Class", "type", propClass);
//        }
//
//        //NullContainedConcept stuff
//        if (!isStateMachineProperty && pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT && Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT)) {
//            //contained concept array properties need to define getNullContainedConcept so that
//            //the elements of their array can call it
//            if (pd.isArray()) {
//                MethodRecWriter mr = propClass.createMethod("public", NullContainedConcept.class.getName(), "getNullContainedConcept");
//                mr.setBody("return " + getFSName(pd.getConceptType()) + "." + CGConstants.nullContainedConceptInnerClassName + "." + CGConstants.nullContainedConceptInstanceName + ";");
////                propClass.addMethod(mr);
//            } else {
//                MethodRecWriter mr = propClass.createMethod("public", ContainedConcept.class.getName(), "getContainedConcept");
//                mr.addArg("boolean", "calledFromCondition");
//                StringBuilder body = newStringBuilder();
//                body.append(ContainedConcept.class.getName() + " cc = getContainedConcept();").append(BRK);
//                body.append("if(calledFromCondition && cc == null) return " + getFSName(pd.getConceptType()) + "." + CGConstants.nullContainedConceptInnerClassName + "." + CGConstants.nullContainedConceptInstanceName + ";").append(BRK);
//                body.append("else return cc;").append(BRK);
//                mr.setBody(body);
////                propClass.addMethod(mr);
//            }
//        }
//
//        //cc.addClass(propClass);
//    }
/*
    protected static void addSerializerDeserializer(Concept cept, JavaClassWriter cc) {
        MethodRecWriter mr;
        StringBuilder body;

        // Add the deserializer
        mr = cc.createMethod("readExternal");
        mr.setAccess("public");
        mr.setReturnType("void");
        mr.addArg("java.io.DataInput", "dataInput");
        body = newStringBuilder();
        body.append("try {").append(BRK);
        boolean buseOldSerialization=Boolean.valueOf(System.getProperty(SERIALIZATION_PROPERTY, "true")).booleanValue();
        if (!buseOldSerialization) {
            body.append(com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer_NoNullProps.class.getName() + " deser= new " + com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer_NoNullProps.class.getName() + "(dataInput);").append(BRK);
        } else {
            body.append(com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer.class.getName() + " deser= new " + com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer.class.getName() + "(dataInput);").append(BRK);
        }
        body.append("this.deserialize(deser);").append(BRK);
        body.append("} catch(java.lang.Exception ex) {ex.printStackTrace(); throw new java.lang.RuntimeException(ex);}").append(BRK);

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

        mr = cc.createMethod("writeExternal");
        mr.setAccess("public");
        mr.setReturnType("void");
        mr.addArg("java.io.DataOutput", "dataOutput");
        body = newStringBuilder();
        body.append("try {").append(BRK);
        if (!buseOldSerialization) {
            body.append(com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer_NoNullProps.class.getName() + " ser= new " + com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer_NoNullProps.class.getName() + "(dataOutput,this.getClass());").append(BRK);
        } else {
            body.append(com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer.class.getName() + " ser= new " + com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer.class.getName() + "(dataOutput,this.getClass());").append(BRK);
        }

        body.append("this.serialize(ser);").append(BRK);
        body.append("} catch(java.lang.Exception ex) {ex.printStackTrace(); throw new java.lang.RuntimeException(ex);}").append(BRK);
        mr.setBody(body);
//        cc.addMethod(mr);
    }
*/
    private void addIsAutoStartupStateMachine() {
        MethodRecWriter mr = cc.createMethod("public", boolean.class.getName(), "isAutoStartupStateMachine");
        mr.setBody(String.format("return %s;", cept.isAutoStartStateMachine()));
    }

    protected String getPropertyFromArray_PD_or_SM(Object pdOrSM) {
        if(pdOrSM instanceof PropertyDefinition) {
            return getPropertyFromArray((PropertyDefinition)pdOrSM);
        } else {
            return getPropertyFromArray((StateMachine) pdOrSM);
        }
    }
    protected String getPropertyFromArray(PropertyDefinition pd) {
        return String.format(PROPS_ARRAY + "[%s]", getPropertyId(pd));
    }
    protected String getPropertyFromArray(StateMachine sm) {
        return String.format(PROPS_ARRAY + "[%s]", getPropertyId(sm));
    }

    protected void addGetSet(JavaClassWriter cc) {
        if(!CGConstants.HDPROPS || forceNamedGetSet) {
            for (PropertyDefinition pd : localProps) {
                int type = pd.getType();

                String getMethodName = getGetMethodName(pd);
                MethodRecWriter mr = cc.createMethod(getMethodName);
                mr.setAccess("public");

                String returnType;
                if (pd.isArray()) {
                    returnType = CGConstants.propertyArrayInterfaceFSClassNames[type];
                } else {
                    //use this so that getContainedConcept(boolean calledFromCondition) method is exposed
                    if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT && generateNullContainedConcept) {
                        returnType = CGConstants.propertyAtomContainedConcept_CalledFromCondition;
                    } else {
                        returnType = CGConstants.propertyAtomInterfaceFSClassNames[type];
                    }
                }
                mr.setReturnType(returnType);

                mr.setBody(String.format( "return %s;", propertyGetterExp(pd, true)));

                String name = ModelNameUtil.generatedMemberName(pd.getName());
                //don't make a set method for array types
                if (!pd.isArray()) {
                    mr = cc.createMethod(CGConstants.SET_PREFIX + name);
                    mr.setAccess("public");
                    mr.addArg(CGConstants.setArgumentTypes[type], "value");
                    mr.setBody(String.format("%s().%s(value);", getMethodName, CGConstants.propAtomSetterNames[type]));
                }
            }
        }
        for(StateMachine sm : localSMs) {
            String propElem = getPropertyFromArray(sm);
            String retType = smPropType(sm);

            MethodRecWriter mr = cc.createMethod("public", retType, getGetMethodName(sm));
            mr.setBody(String.format( "return (%s)getProperty(%s);", PropertyStateMachineImpl.class.getName(), getPropertyId(sm)));

            mr = cc.createMethod("public", retType, nullOkMethodName(sm));
            mr.setBody(String.format("return (%2$s)%1$s;"
                    , propElem, PropertyStateMachineImpl.class.getName()));
        }
    }

    private void addGetMainStateMachineProperty(StateMachine sm) {
        if (sm != null && localSMs.contains(sm)) {
            MethodRecWriter mr = cc.createMethod("getMainStateMachineProperty");
            mr.setAccess("public");
            mr.setReturnType("com.tibco.cep.runtime.model.element.Property.PropertyStateMachine");
            mr.setBody(String.format("return %s();", getGetMethodName(sm)));
        }
    }

    private void addGetStateMachines() {
        for(StateMachine sm : localSMs) {
            MethodRecWriter mr = cc.createMethod("public", "com.tibco.cep.runtime.model.element.Property.PropertyStateMachine",
                    ModelNameUtil.modelNameToStateMachineVirtualMethod(sm));
            mr.setBody(String.format("return get%s();", ModelNameUtil.generatedStateMachinePropertyMemberName(cept, sm)));
        }
    }

    private void addGetStateMachineProperties() {
        if(allSMs.size() > 0) {
            MethodRecWriter mr = cc.createMethod("public", "com.tibco.cep.runtime.model.element.Property.PropertyStateMachine[]", "getStateMachineProperties");
            StringBuilder body = newStringBuilder();
            body.append("return new ")
                    .append(com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl.class.getName())
                    .append("[] {");

            for(StateMachine sm : allSMs) {
                body.append("get").append(ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm)).append("()");
                body.append(",");
            }
            body.append("};").append(BRK);
            mr.setBody(body);
        }
    }

    private void addGetStateMachineProperty() {
        if(allSMs.size() > 0) {
            MethodRecWriter mr = cc.createMethod("public", "com.tibco.cep.runtime.model.element.Property.PropertyStateMachine", "getStateMachineProperty");
            mr.addArg(String.class.getName(), "stateMachineName");
            StringBuilder body = newStringBuilder();
            if(localSMs.size() > 0) {
                for(StateMachine sm : localSMs) {
                    body.append("if (stateMachineName.equals(\"").append(sm.getOwnerConceptPath())
                            .append("/").append(sm.getName()).append("\")){ ").append(BRK);
                    body.append("return get").append(ModelNameUtil.generatedStateMachinePropertyMemberName(cept, sm)).append("();").append(BRK);
                    body.append("}").append(BRK);
                }
            }

            body.append("return super.getStateMachineProperty(stateMachineName);");
            mr.setBody(body);
        }
    }

    private void addGetMainStateMachine() {
        StateMachine mainSm = cept.getMainStateMachine();
        if (mainSm != null && localSMs.contains(mainSm)) {
            MethodRecWriter mr = cc.createMethod("public", StateMachineConcept.class.getName(), "getMainStateMachine");
            mr.setBody(String.format("return (%s) get%s().getConcept();", StateMachineConcept.class.getName()
                    , ModelNameUtil.generatedStateMachinePropertyMemberName(cept, mainSm)));
        }
    }

    /**
     * ConceptStateMachine machine= getMainStateMachine();
     * if (machine == null) {
     * PropertyStateMachine property= getMainStateMachineProperty();
     * if (property != null) {
     * property.startMachine(id);
     * return (ConceptStateMachine) property.getConcept();
     * }
     * }
     * return machine;
     *
     * @param cc
     * @param cept
     */
//    private static void addStartMainStateMachine(JavaClassWriter cc, Concept cept) {
//        MethodRecWriter mr = cc.createMethod("public", StateMachineConcept.class.getName(), "startStateMachine");
//        mr.addArg("long", "id");
//        StringBuilder body = newStringBuilder();
//        if (cept.getMainStateMachine() != null) {
//            body.append("if (getMainStateMachine() == null){").append(BRK);
//            body.append("if (getMainStateMachineProperty() != null) {").append(BRK);
//            body.append("getMainStateMachineProperty().startMachine(id);").append(BRK);
//            body.append("return getMainStateMachineProperty().getMachine();").append(BRK);
//            body.append("}").append(BRK);
//            body.append("}").append(BRK);
//            body.append("return null;").append(BRK);
//        } else {
//            body.append("return super.startStateMachine(id);").append(BRK);
//        }
//        mr.setBody(body);
////        cc.addMethod(mr);
//    }

    protected void addFactoryMethod() {
        MethodRecWriter mr = cc.createMethod("new" + cept.getName());
        mr.setAccess("public static");
        mr.addArg("java.lang.String", "extId");
        StringBuilder varargsBody = null;
        StringBuilder body = newStringBuilder();
        String fsName = getFSName(cept);
        body.append(fsName).append(" instance = new ").append(fsName)
                .append("(").append(NEXT_ID).append(fsName)
                .append(".class)").append(", extId);").append(BRK);

        boolean noPropArgs = CGConstants.HDPROPS || ModelUtils.areConceptConstructorArgsOversize(cept);

        if (noPropArgs) {
            mr.addArg("java.lang.Object...", "args");
            varargsBody = newStringBuilder();
        }
        int argCounter = -1;
        //getPropertyDefinitions(false) returns PDs in a different order than they are in allProps
        for(PropertyDefinition pd : (List<PropertyDefinition>)cept.getPropertyDefinitions(false)) {
            argCounter++;
            String name = ModelNameUtil.generatedMemberName(pd.getName());

            int type = pd.getType();
            String arraySuffix = pd.isArray() ? "[]" : "";
            if ((type == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) || (type == PropertyDefinition.PROPERTY_TYPE_CONCEPT))
            {
                String argType;
                if (type == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
                    argType = CGConstants.engineConceptReferenceInterface + arraySuffix;
                } else {
                    argType = CGConstants.engineContainedConceptInterface + arraySuffix;
                }
                if(noPropArgs) {
                	varargsBody.append(String.format("%1$s %2$s = (args != null && args.length > %3$s) ? (%1$s)args[%3$s] : null;%n", argType, name, argCounter));
                } else {
                    mr.addArg(argType, name);
                }

                if (!pd.isArray()) {
                    body.append("instance.").append(propertyAtomSetterExp(pd, false)).append("(").append(name).append(");").append(BRK);
                } else {
                    body.append("if(").append(name).append(" != null) {").append(BRK);
                    body.append(FACTORY_PRIMITIVE_TO_PROPERTY_ARRAY_FUNCTION).append("(").append(name).append(", instance.")
                            .append(propertyGetterExp(pd, false)).append(");").append(BRK);
                    body.append("}").append(BRK);
                }
            } else {
                String argType = CGConstants.setArgumentTypes[type] + arraySuffix;
                if(noPropArgs) {
                    String cast = pd.isArray() ? argType : CGConstants.boxedPrimitivePropertyTypes[type];
                    varargsBody.append(String.format("%1$s %2$s = (args != null && args.length > %4$s) ? (%3$s)args[%4$s] : null;%n", argType, name, cast, argCounter));
                } else {
                    mr.addArg(argType, name);
                }

                if (!pd.isArray()) {
                    body.append("instance.").append(propertyAtomSetterExp(pd, false)).append("(").append(name).append(");").append(BRK);
                } else {
                    body.append("if(").append(name).append(" != null) {").append(BRK);
                    body.append(FACTORY_PRIMITIVE_TO_PROPERTY_ARRAY_FUNCTION).append("(")
                            .append(name).append(", instance.").append(propertyGetterExp(pd, false)).append(");").append(BRK);
                    body.append("}").append(BRK);
                }
            }
        }
        body.append("instance.startSMAndAssert();").append(BRK);
        body.append("return instance;").append(BRK);

        mr.setReturnType(fsName);
        if(varargsBody != null) body = varargsBody.append(body);
        mr.setBody(body);
    }

//    protected com.tibco.cep.runtime.model.element.Property getProperty(String name, int hashCode) {
//        switch(hashCode) {
//            case 0: return get$2zp1();  //"p1"
//            case 1: return get$2zp2();
//            default: return null;
//        }
//    }

    protected void addGetPropertyWithHashCode() {
        HashMap<Integer, Object[]> map = new HashMap(allProps.size() + allSMs.size());
        for(PropertyDefinition pd : allProps) {
            String name = pd.getName();
            Integer hashCode = name.hashCode();
            //last element implements linked list of hashcode collisions
            Object[] arr = new Object[]{name, getPropertyId(pd), map.get(hashCode)};
            map.put(hashCode, arr);
        }
        {
            HashSet smNameCrash = new HashSet(allSMs.size());
            for(StateMachine sm : allSMs) {
                String name = sm.getOwnerConceptPath() + "." + sm.getName();
                if(!smNameCrash.add(name))
                    throw new RuntimeException("addGetPropertyWithHashCode: Name crashed: " + name);

                Integer hashCode = name.hashCode();
                Object[] arr = new Object[]{name, getPropertyId(sm), map.get(hashCode)};
                map.put(hashCode, arr);
            }
        }

        addGetPropertyWithHashCode(map);
    }

//    private static List<StateMachine> getAllStateMachines(Concept cept) {
//        List<List> allMachines = new ArrayList();
//        Concept tmp = cept;
//        int count = 0;
//        while (tmp != null) {
//            List list = tmp.getStateMachines();
//            if (list != null) {
//                allMachines.add(list);
//                count += list.size();
//            }
//            tmp = tmp.getSuperConcept();
//        }
//
//        List ret = new ArrayList(count);
//        for(List l : allMachines) {
//            ret.addAll(l);
//        }
//        return ret;
//    }

    protected void addNewInstanceMethod() {
        MethodRecWriter mr = cc.createMethod("newInstance");
        mr.setAccess("public");
        mr.setReturnType(CGConstants.engineConceptImpl);
        mr.setBody(String.format("return new %s();", cept.getName()));
    }

    protected void addGetProperties() {
        if(allProps.size() > 0 || allSMs.size() > 0) {
            StringBuilder body = new StringBuilder("return new " + CGConstants.propertyGenericInterface + "[] {");
            for (PropertyDefinition pd : allProps) {
                body.append(propertyGetterExp(pd, true));
                body.append(", ");
            }

            for(StateMachine sm : allSMs) {
                body.append("get" + ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm) + "()");
                body.append(", ");
            }
            body.append("};");

            MethodRecWriter mr = cc.createMethod("getProperties") ;
            mr.setAccess("public");
            mr.setReturnType(CGConstants.propertyGenericInterface + "[]");
            mr.setBody(body);
        }
    }

    protected void addGetLocalProperties() {
        if(localProps.size() > 0 || localSMs.size() > 0) {
            StringBuilder body = newStringBuilder();
            body.append("return new " + CGConstants.propertyGenericInterface + "[] {");
            for (PropertyDefinition pd : localProps) {
                body.append(propertyGetterExp(pd, true));
                body.append(", ");
            }
            for(StateMachine sm : localSMs) {
                body.append("get" + ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm) + "()");
                body.append(", ");
            }

            body.append("};");

            MethodRecWriter mr = cc.createMethod("getLocalProperties");
            mr.setAccess("public");
            mr.setReturnType(CGConstants.propertyGenericInterface + "[]");
            mr.setBody(body);
        }
    }

    protected static String getGetMethodName(PropertyDefinition pd) {
        return getGetMethodName(ModelNameUtil.generatedMemberName(pd.getName()));
    }

    protected String propertyGetterExp(PropertyDefinition pd, boolean noPfx) {
        if(pd.isArray()) {
            return CGUtil.conceptPropertyArrayAccessExp(getPropertyId(pd), pd.getType(), noPfx);
        } else {
            return CGUtil.conceptPropertyAtomAccessExp(getPropertyId(pd), pd.getType(), generateNullContainedConcept, noPfx);
        }
    }
    protected static String getGetMethodName(StateMachine sm) {
        return "get" + ModelNameUtil.generatedStateMachinePropertyMemberName(sm.getOwnerConcept(), sm);
    }
//    protected String getGetMethodName_PD_or_SM(Object pdOrSm) {
//        if(pdOrSm instanceof PropertyDefinition) {
//            return propertyGetterExp((PropertyDefinition)pdOrSm);
//        } else {
//            return getGetMethodName((StateMachine)pdOrSm);
//        }
//    }

    protected String propertyAtomSetterExp(PropertyDefinition pd, boolean noPfx) {
        return String.format("%s.%s", propertyGetterExp(pd, noPfx), CGConstants.propAtomSetterNames[pd.getType()]);
    }

    private void addNullContainedConcept() {
        JavaClassWriter nullContainedConcept = cc.createNestedClassWriter(CGConstants.nullContainedConceptInnerClassName,null);
        nullContainedConcept.setAccess("static public");
        nullContainedConcept.setSuperClass(getFSName(cept));
        nullContainedConcept.addInterface(NullContainedConcept.class.getName());

        nullContainedConcept.addMember("public static final", CGConstants.nullContainedConceptInnerClassName, CGConstants.nullContainedConceptInstanceName, "new " + CGConstants.nullContainedConceptInnerClassName + "()");
        addGetSet(nullContainedConcept);
        nullContainedConcept.createMethod("public", "long", "getId");
        nullContainedConcept.createMethod("public", String.class.getName(), "getExtId");
        nullContainedConcept.createMethod("public", com.tibco.cep.runtime.model.element.Concept.class.getName(), "getParent");

        for(MethodRecWriter mr : nullContainedConcept.methods) {
            mr.setBody(String.format("throw new %s();", NullContainedConceptAccessInConditionException.class.getName()));
        }

        MethodRecWriter mr;
        //add constructor now so that its body doesn't get overwritten by the above step
        mr = nullContainedConcept.createMethod("private", "", CGConstants.nullContainedConceptInnerClassName);
        mr.setBody("super(0);");

        mr = cc.createMethod("public", NullContainedConcept.class.getName(), "getNullContainedConcept");
        mr.setBody(String.format("return %s.%s;",
                CGConstants.nullContainedConceptInnerClassName, CGConstants.nullContainedConceptInstanceName));
    }

    protected static void addExcludeNullProps(JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod("excludeNullProps");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        if (System.getProperty("tibco.be.schema.exclude.null.props", "true").equals("true")) {
            mr.setBody("return true;");
        } else {
            mr.setBody("return false;");
        }
    }

    protected static void addIncludeNullProps(JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod("includeNullProps");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        if (System.getProperty("tibco.be.schema.include.null.props", "false").equals("true")) {
            mr.setBody("return true;");
        } else {
            mr.setBody("return false;");
        }
    }

    @SuppressWarnings("unchecked")
    protected static void addExpandPropertyRefs(JavaClassWriter cc, Concept cept) {
        MethodRecWriter mr = cc.createMethod("expandPropertyRefs");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        String methodBody = "return false;";
        Map<String, Object> extendedProperties = cept.getExtendedProperties();
        if (extendedProperties != null && !extendedProperties.isEmpty()) {
            if (extendedProperties.containsKey("tibco.be.schema.ref.expand")) {
                String shouldExpandRefsPropertyVal = (String)extendedProperties.get("tibco.be.schema.ref.expand");
        //        if (System.getProperty("tibco.be.schema.ref.expand", "false").equals("true")) {
        //          mr.setBody("return true;");
        //        }
                if (Boolean.parseBoolean(shouldExpandRefsPropertyVal)) {
                    methodBody = "return true;";
                }
            }
        }
        mr.setBody(methodBody);
//        cc.addMethod(mr);
    }
    protected static void addNilAttribs(JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod("setNilAttribs");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        if (System.getProperty("tibco.be.schema.nil.attribs", "false").equals("true")) {
            mr.setBody("return true;");
        } else {
            mr.setBody("return false;");
        }
//        cc.addMethod(mr);
    }
    protected static void addTreatNullValues(JavaClassWriter cc) {
        MethodRecWriter mr = cc.createMethod("treatNullValues");
        mr.setAccess("public");
        mr.setReturnType("boolean");
        if (System.getProperty("tibco.be.schema.treat.null.values", "false").equals("true")) {
            mr.setBody("return true;");
        } else {
            mr.setBody("return false;");
        }
//        cc.addMethod(mr);
    }

    protected void addNewSMWithIndex() {
        if(localSMs.size() > 0) {
            MethodRecWriter mr = cc.createMethod("public", StateMachineConcept.class.getName(), "newSMWithIndex");
            mr.addArg("int", "index");

            StringBuilder body = newStringBuilder();
            body.append("switch(index) {").append(BRK);
            for(StateMachine sm : localSMs) {
                String clzName = ModelNameUtil.modelNametoStateMachineClassName(cept, sm);
                body.append(String.format("case %1$s: return new %2$s(%3$s%2$s.class));%n", getPropertyId(sm), clzName, NEXT_ID));
            }
            body.append("}").append(BRK);

            body.append("return super.newSMWithIndex(index);");
            mr.setBody(body);
        }
    }

    protected void addGetNumProperties() {
        addGetNumProperties(allProps.size() + allSMs.size());
    }

    protected void addGetNumDirtyBits() {
        //contained concepts have two dirty bits
        int ccCount = 0;
        for(PropertyDefinition pd : allProps) {
            if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                ccCount++;
            }
        }
        ccCount += allSMs.size();

        if(ccCount > 0 || CGConstants.HDPROPS) {
            addGetNumDirtyBits(allProps.size() + allSMs.size() + ccCount);
        }
    }
}