package com.tibco.cep.bpmn.codegen;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.AutoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.MetaPropertyEncoder;
import com.tibco.be.parser.codegen.RDFUtil;
import com.tibco.cep.bpmn.runtime.model.VersionInfo;
import com.tibco.cep.bpmn.runtime.templates.MapperClassTemplate;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.ProcessModel.BASE_ATTRIBUTES;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaPropertyDecoder;

public class ProcessClassGenerator {

    public static class MemberInfo {
        public boolean array = false;
        public boolean attribute = false;
        public boolean containedConcept = false;
        public boolean genNullContainedConcept = false;
        public String hashCode;
        protected String methodName,historySize,historyPolicy,name,varname,memberClass,
                        propertyId,propertyLevel,propertyIndex,type,maintainReverseRef = null;
        public boolean referencedConcept = false;
        public String typeId;
        public String getHashCode() { return hashCode;  }
        public String getHistoryPolicy() {  return historyPolicy; }
        public String getHistorySize() { return historySize; }
        public String getMaintainReverseRef() { return maintainReverseRef; }
        public String getMemberClass() { return memberClass; }
        public String getMethodName() { return methodName; }
        public String getName() { return name; }
        public String getPropertyId() { return propertyId;  }
        public String getPropertyIndex() {  return propertyIndex; }
        public String getPropertyLevel() {  return propertyLevel; }
        public String getType() { return type;  }
        public String getTypeId() { return typeId;  }
        public String getVarname() { return varname; }
        public boolean isArray() {  return array; }
        public boolean IsArray() { return array; }
        public boolean isAttribute() {  return attribute; }
        public boolean isContainedConcept() { return containedConcept; }
        public boolean isGenNullContainedConcept() { return genNullContainedConcept;}
        public boolean isReferencedConcept() { return referencedConcept; }
    }

    public static class ProcessInfo {
        List<MemberInfo> contained = new ArrayList<MemberInfo>();
        public boolean containedConcept;
        public String designTimeType;
        boolean excludeNullProps,includeNullProps,expandPropertyRefs,setNilAttribs,treatNullValues = false;
        List<String> inputMapperSnippets = new ArrayList<String>();
        List<MemberInfo> members = new ArrayList<MemberInfo>();
        public String metaProperties;
        public int numDirtyBits;
        public int numProperties;
        public int numTasks;
        List<String> outputMapperSnippets = new ArrayList<String>();
        String packageName,className,type,nsURI,name,parentClass,parentProperty = null;
        List<MemberInfo> referenced = new ArrayList<MemberInfo>();
        public VersionInfo versionInfo = null;
        public String getClassName() {  return className; }
        public List<MemberInfo> getContained() { return contained; }
        public String getDesignTimeType() { return designTimeType;  }
        public List<String> getInputMapperSnippets() { return inputMapperSnippets; }
        public List<MemberInfo> getMembers() {  return members; }
        public String getMetaProperties() { return metaProperties;  }
        public String getName() { return name;  }
        public String getNsURI() {  return nsURI; }
        public int getNumDirtyBits() {  return numDirtyBits; }
        public int getNumProperties() { return numProperties;}
        public int getNumTasks() {  return numTasks; }
        public List<String> getOutputMapperSnippets() { return outputMapperSnippets; }
        public String getPackageName() { return packageName; }
        public String getParentClass() { return parentClass; }
        public String getParentProperty() { return parentProperty;  }
        public List<MemberInfo> getReferenced() { return referenced; }
        public String getType() { return type;  }
        public VersionInfo getVersionInfo() { return versionInfo; }
        public boolean isExcludeNullProps() { return excludeNullProps; }
        public boolean isIncludeNullProps() { return includeNullProps; }
        public boolean isExpandPropertyRefs() { return expandPropertyRefs;  }
        public boolean isSetNilAttribs() {  return setNilAttribs;   }
        public boolean isTreatNullValues() { return treatNullValues; }
    }

    private static final String MEMBER_ACCESS = "protected"; //$NON-NLS-1$

    protected static final Map<String, String> PROPERTY_INDEX = new HashMap<String, String>();

    protected static String getFSName(Concept concept) {
            String path = concept.getFullPath();
            return ModelNameUtil.modelPathToGeneratedClassName(path);
    }

    private static String getMethodName(String varName) {
        return RDFUtil.firstCap(varName);
    }
    private String className;

    private StringTemplate classTemplate;

    private ByteArrayOutputStream codeStream;

    private Logger logger = LogManagerFactory.getLogManager().getLogger(ProcessClassGenerator.class);

    private List<MapperClassTemplate> mapperCodeSnippets;

    private String packageName;

    private Concept pConcept;

    private ProcessInfo processInfo;

    private ProcessModel processModel;

    private ProcessTemplate processTemplate;

    private StringTemplateGroup stgroup;

    /**
     * @param processTemplate
     * @param mapperCodeSnippets
     */
    public ProcessClassGenerator(ProcessTemplate processTemplate, List<MapperClassTemplate> mapperCodeSnippets) {
        this.processTemplate = processTemplate;
        this.processModel = processTemplate.getProcessModel();
        this.pConcept = processModel.cast(Concept.class);
        try {
            this.stgroup = new StringTemplateGroup(new InputStreamReader(getClass().getResourceAsStream("/com/tibco/cep/bpmn/codegen/ProcessClassTemplate.stg"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.ERROR, e.getMessage(),e);
        }
        this.classTemplate = stgroup.getInstanceOf("Process_Class");
        this.processInfo = new ProcessInfo();
        this.codeStream = new ByteArrayOutputStream();
        this.mapperCodeSnippets = mapperCodeSnippets;
        final String className = processTemplate.getProcessName().getJavaClassName();
        this.packageName = className.substring(0, className.lastIndexOf("."));
        this.className = className.substring(className.lastIndexOf(".")+1);
        this.processInfo.numTasks = processTemplate.allTasks().size();
        this.processInfo.containedConcept = this.pConcept.getParentConcept() != null;
        this.processInfo.excludeNullProps = System.getProperty("tibco.be.schema.exclude.null.props", "true").equals("true");
        this.processInfo.includeNullProps = System.getProperty("tibco.be.schema.include.null.props", "false").equals("true");
        this.processInfo.expandPropertyRefs = System.getProperty("tibco.be.schema.ref.expand", "false").equals("true");
        this.processInfo.setNilAttribs = System.getProperty("tibco.be.schema.nil.attribs", "false").equals("true");
        this.processInfo.treatNullValues = System.getProperty("tibco.be.schema.treat.null.values", "false").equals("true");
    }

    private void addDuplicateMethod() {

    }

    protected void addGetNumDirtyBits() {
        List<PropertyDefinition> propDefs = new ArrayList<PropertyDefinition>();
//      propDefs.addAll(getAttributeDefinitions(pConcept));
        propDefs.addAll(pConcept.getAllPropertyDefinitions());
        this.processInfo.numProperties = propDefs.size();
        //contained concepts have two dirty bits
        int ccCount = 0;
        for(PropertyDefinition pd : propDefs) {
            if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                ccCount++;
            }
        }
        if(ccCount > 0) {
            this.processInfo.numDirtyBits = propDefs.size() + ccCount;
        }
    }

    protected void addGetParentClass() {
        processInfo.parentClass = null;
        if (this.pConcept.getParentConcept() != null) {
            processInfo.parentClass = ModelNameUtil.modelPathToGeneratedClassName(this.pConcept.getParentConcept().getFullPath()) + ".class";
        }
    }

    protected void addGetParentPropertyMethods() {
        processInfo.parentProperty = null;
        if (this.pConcept.getParentConcept() != null) {
            for (Iterator it = this.pConcept.getParentConcept().getAllPropertyDefinitions().iterator(); it.hasNext();) {
                PropertyDefinition pd = (PropertyDefinition) it.next();
                if ((pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) && (pd.getConceptType().getFullPath().equals(this.pConcept.getFullPath()))) {
                    processInfo.parentProperty = pd.getName();
                    break;
                }
            }
        }
    }

    private void addGetProperties() {

    }
    private void addGetPropertiesNullOK() {

    }

    private void addGetType() {
        this.processInfo.designTimeType = pConcept.getFullPath();
//      final String processName = ModelNameUtil.escapeIdentifier(processTemplate.getProcessModel().getGUID().replace('.', '$'));
//      processInfo.type=RDFTnsFlavor.BE_NAMESPACE + pConcept.getNamespace() + processName+"_"+processTemplate.getVersionInfo().getRevision();
//      processInfo.nsURI=RDFTnsFlavor.BE_NAMESPACE + pConcept.getNamespace() + processName+"_"+processTemplate.getVersionInfo().getRevision();
        processInfo.type= processTemplate.getProcessName().getExpandedName().getNamespaceURI();
        processInfo.nsURI= processTemplate.getProcessName().getExpandedName().getNamespaceURI();
    }

    private void addMapperSnippets() throws UnsupportedEncodingException {
        for(MapperClassTemplate mc:mapperCodeSnippets){
            processInfo.getInputMapperSnippets().add(new String(mc.getMapperTransformationSnippet(),"UTF-8"));
        }

    }

    private void addMembers(ProcessInfo processInfo) {
        List<PropertyDefinition> propDefs = new ArrayList<PropertyDefinition>();
//      propDefs.addAll(getAttributeDefinitions(pConcept));
        propDefs.addAll(pConcept.getAllPropertyDefinitions());
        int numAttributes = 0;//getAttributeDefinitions(pConcept).size();
        int i=0;
        for (PropertyDefinition pd:propDefs) {
            MemberInfo mi = new MemberInfo();
            mi.attribute = (i <= (numAttributes-1));
            mi.name = pd.getName();
            if(mi.attribute) {
                mi.varname = getGeneratedAttributeName(pd.getName());
            } else {
                mi.varname = ModelNameUtil.generatedMemberName(pd.getName());
            }
            mi.methodName = getMethodName(pd.getName());
            mi.typeId = ""+pd.getType();
            mi.hashCode=""+pd.getName().hashCode();

            int historySize = pd.getHistorySize();
            boolean isArray = pd.isArray();
            mi.array = isArray;
            mi.historySize= historySize > 0 ?""+historySize:null;
            String propClass = null;
            if (historySize == 0) {
                if (isArray) {
                    mi.memberClass = CGConstants.propertyArrayImplSimpleClassNames[pd.getType()];
                } else {
                    mi.memberClass = CGConstants.propertyAtomImplSimpleClassNames[pd.getType()];
                }
            } else {
                if (isArray) {
                    mi.memberClass = CGConstants.propertyArrayImplFSClassNames[pd.getType()];
                } else {
                    mi.memberClass = CGConstants.propertyAtomImplFSClassNames[pd.getType()];
                }
            }
            mi.propertyIndex = "-1";
            mi.propertyLevel= "-1";
            mi.historyPolicy="0";
//          String index = PROPERTY_INDEX.get(ModelNameUtil.generatedMemberClassName(pd.getName()));
            String index = PROPERTY_INDEX.get(pd.getName());
            if (null == index) {
                index = "-1";
            }
            mi.propertyId = index;

            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT || pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
                if(mi.attribute) {
                    mi.type = processInfo.packageName+"."+processInfo.className;
                } else {

                    mi.type = getFSName(pd.getConceptType());
                }
            }

            if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
                mi.containedConcept = true;
                processInfo.getContained().add(mi);
            }

            if(pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
                mi.referencedConcept=true;
                processInfo.getReferenced().add(mi);
                String revRef = (String) pd.getExtendedProperties().get("Reverse Ref");
                if(revRef != null && revRef.equalsIgnoreCase("FALSE"))
                    mi.maintainReverseRef="false";
                else
                    mi.maintainReverseRef="true";
            } else {
                mi.maintainReverseRef=null;
            }

            processInfo.getMembers().add(mi);

            i++;
        } // end for
    }

    protected void addMetaProperties(ProcessInfo processInfo) {
        //cc.addMember("public static", META_PROP_CLASS + "[]", CGConstants.META_PROPS_ARRAY, makeMetaPropsInitBody());
        processInfo.metaProperties = makeMetaPropsInitBody(processInfo);
        //addGetMetaProperty();  // done in template
    }
    private void addPropertiesIndexToName() {

    }

    protected String className(Concept cept) {
        return getFSName(cept);
    }
    private void createPropertyIndex() {
        PROPERTY_INDEX.clear();
        List<PropertyDefinition> propDefs = new ArrayList<PropertyDefinition>();
//      propDefs.addAll(getAttributeDefinitions(pConcept));
        propDefs.addAll(pConcept.getAllPropertyDefinitions());
        int index = 0;
        for (PropertyDefinition pd : propDefs) {
//          String key = ModelNameUtil.generatedMemberClassName(pd.getName());
            String key = pd.getName();
            if (!PROPERTY_INDEX.containsKey(key)) {
                PROPERTY_INDEX.put(key, String.valueOf(index));
            }
            index++;
        }
    }

    public ByteArrayOutputStream generate() throws Exception {

        this.classTemplate.reset();
        processInfo.name = processTemplate.getProcessName().getSimpleName();
        processInfo.versionInfo = processTemplate.getVersionInfo();
        processInfo.packageName= this.packageName;
        processInfo.className=this.className;

        addGetType();
        createPropertyIndex();
        addGetParentClass();
        addGetParentPropertyMethods();
        addMembers(processInfo);
        addMapperSnippets();
        addDuplicateMethod();
        addMetaProperties(processInfo);
        addGetNumDirtyBits();
        addGetProperties();
        addGetPropertiesNullOK();
        addPropertiesIndexToName();

        this.classTemplate.setAttribute("processInfo", processInfo);
        logger.log(Level.INFO, String.format("Generating code for process:%s version:%d", processInfo.getName(),processInfo.getVersionInfo().getRevision()));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(codeStream,"UTF-8");
        this.classTemplate.write(new AutoIndentWriter(outputStreamWriter));
        outputStreamWriter.flush();
        outputStreamWriter.close();
        this.codeStream.flush();
        this.codeStream.close();

        return codeStream;
    }

    private List<PropertyDefinition> getAttributeDefinitions(Concept pConcept) {
        List<PropertyDefinition> propDefs = new ArrayList<PropertyDefinition>();
        for(Iterator it = pConcept.getAttributeDefinitions().iterator();it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition) it.next();
            if(pd.getName().equals(ProcessModel.BASE_ATTRIBUTES.id.name())||
                    pd.getName().equals(ProcessModel.BASE_ATTRIBUTES.extId.name())  ) {
                continue;
            }
            propDefs.add(pd);
        }
        return propDefs;
    }
    public String getClassName() {
        return this.className;
    }

    private String getGeneratedAttributeName(String name) {
        return BASE_ATTRIBUTES.valueOf(name).getGetter();
//      if(name.equals(ProcessModel.BASE_ATTRIBUTES.parent.name())) {
//          return "getParentAsProperty";
//      } else if(name.equals(ProcessModel.BASE_ATTRIBUTES.state.name())) {
//          return "getStatusAsProperty";
//      } else if(name.equals(ProcessModel.BASE_ATTRIBUTES.taskId.name())) {
//          return "getTaskAsProperty";
//      } else if(name.equals(ProcessModel.BASE_ATTRIBUTES.templateVersion.name())) {
//          return "getTemplateVersionAsProperty";
//      }

//      return null;
    }

    public String getPackageName() {
        return this.packageName;
    }

     //String name, int historyPolicy, Class type, int historySize, boolean maintainReverseRefs, int containedPropIndex
    protected String makeMetaPropsInitBody(ProcessInfo processInfo) {
        List<PropertyDefinition> propDefs = new ArrayList<PropertyDefinition>();
//      propDefs.addAll(getAttributeDefinitions(pConcept));
        propDefs.addAll(pConcept.getAllPropertyDefinitions());
        String[] mprops = new String[propDefs.size()];
        int len = 0;
        int numAttribs = 0;//getAttributeDefinitions(pConcept).size();
        int c = 0;
        for(PropertyDefinition pd : propDefs) {
            boolean isContained = pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT;
            boolean isRef = pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE;
            boolean isAttr = (c <= (numAttribs -1));

            String mprop;

            if(isContained || isRef) {
                boolean maintainReverseRefs = true;
                String revRef = (String) pd.getExtendedProperties().get("Reverse Ref");
                if(revRef != null && revRef.equalsIgnoreCase("FALSE")) {
                    maintainReverseRefs = false;
                }
                String typeClassName = null;
                if(isAttr) {
                    typeClassName = processInfo.packageName+"."+processInfo.className;
                } else {
                    typeClassName = getFSName(pd.getConceptType());
                }

                mprop = MetaPropertyEncoder.encode(pd.getName(), (byte)pd.getHistoryPolicy(), typeClassName
                                , pd.getHistorySize(), maintainReverseRefs, isContained, (byte)pd.getType(), pd.isArray()
                                , MetaPropertyEncoder.getDefaultValue(pd));
            } else {

                mprop = MetaPropertyEncoder.encode(pd.getName(), (byte)pd.getHistoryPolicy(), pd.getHistorySize()
                        , (byte)pd.getType(), pd.isArray(), MetaPropertyEncoder.getDefaultValue(pd));
            }

            len += mprop.length();
            mprops[Integer.valueOf(PROPERTY_INDEX.get(pd.getName()))] = mprop;
            c++;
        }

        return makeMetaPropsInitBody(mprops, len);
    }

    protected String makeMetaPropsInitBody(String[] mprops, int len) {
        String start = String.format("new %s(", MetaPropertyDecoder.class.getName());
        String end = String.format(".toString()).decode(%s.class.getClassLoader())", processTemplate.getProcessName().getJavaClassName());
        //for the leading int used to store mprops.length
        len += 2;
        int extra = 0;
        extra += start.length() + end.length();
        extra += "new ".length() + StringBuilder.class.getName().length();
        //plus 1 for the initial count
        extra += (mprops.length + 1)* (".append(\"\")").length();
        //final semicolon plus anything forgotten
        extra += 100;

        StringBuilder body = new StringBuilder(len + extra);
        //StringBuilder body = new StringBuilder(len + 2*mprops.length + 100);

        //body.append("new ").append(META_PROP_CLASS).append("[]{");
        body.append(start);
        body.append("new ").append(StringBuilder.class.getName());
        body.append("(").append(len).append(")");

        //int ccIdx = 0;
        //for(String str : mprops) {
        //    ccIdx = MetaPropertyEncoder.appendWithCCIdx(body, str, ccIdx);
        //    body.append(", ");
        //}
        //body.append("}");

        body.append(".append(\"");
        MetaPropertyEncoder.appendInt(mprops.length, body);
        body.append("\")");
        for(String s : mprops) {
            body.append(".append(\"");
            body.append(s);
            body.append("\")");
        }
        body.append(end);

        return body.toString();
    }
}
