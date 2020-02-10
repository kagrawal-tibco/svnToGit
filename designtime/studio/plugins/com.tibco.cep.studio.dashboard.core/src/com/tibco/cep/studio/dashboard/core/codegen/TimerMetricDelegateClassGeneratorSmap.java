package com.tibco.cep.studio.dashboard.core.codegen;

import static com.tibco.be.parser.codegen.CGConstants.BRK;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.MethodRecWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.functions.model.EMFMetricMethodModelFunction;
import com.tibco.cep.studio.core.util.mapper.MapperMetricUtils.FieldInfo;

public class TimerMetricDelegateClassGeneratorSmap extends MetricClassGeneratorSmap {

    @SuppressWarnings("rawtypes")
	protected TimerMetricDelegateClassGeneratorSmap(Concept cept, Properties oversizeStringConstants,
            Map ruleFnUsage, Map<String,StateMachineBlockLineBuffer> smblbMap, JavaClassWriter cc, Ontology o,
            Map<String, Map<String, int[]>> propInfoCache)
    {
    	super(cept, oversizeStringConstants, ruleFnUsage, smblbMap, cc, o, propInfoCache);
    }

    static void getMetricComputeInternalMethod(JavaClassWriter cc, Concept cept, boolean noPropArgs, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {

        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME);
        mr.setAccess("protected static");
        StringBuilder body = new StringBuilder();
        List<FieldInfo> groupByList = new ArrayList<FieldInfo>();

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            } else if (fi.getDependingField() != null) {
                FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
                if (tmp.isSystem() == false) {
                	body.append("boolean " + fi.getCodegenName() + IS_SET + " = false;" + BRK);
                    continue;
                }
            }
            mr.addArg(CGConstants.setArgumentTypes[fi.getType()], fi.getCodegenName());
            mr.addArg(CGConstants.setArgumentTypes[BOOLEAN_TYPE], fi.getCodegenName() + IS_SET);
            if (fi.isGroupBy() == true) {
                groupByList.add(fi);
            }
        }

        StringBuilder extId = getMetricExtId(cept, groupByList);

        // Lookup metric. If none found, create a new one
        body.append(extId);
        body.append(INIT_METHOD_NAME + "();" + BRK);
        body.append("if (lockIsEnabled == true) {" + BRK);
        body.append("int lockCurrentRetryCount = 0;" + BRK);
        body.append("boolean lockStatus = false;" + BRK);
        body.append("do {" + BRK);
        body.append("lockStatus = com.tibco.be.functions.cluster.DataGridFunctions.Lock(extId, lockTimeout, lockIsLocal);" + BRK);
        body.append("lockCurrentRetryCount++;" + BRK);
        body.append("} while (lockStatus == false && lockCurrentRetryCount < lockRetryCount);" + BRK);
        body.append("if (lockStatus == false) {" + BRK);
        //body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.WARN, \"Failed to acquire lock for metric : \" + extId);" + BRK);
        body.append("throw new java.lang.RuntimeException(\"Failed to acquire lock for metric : \" + extId);" + BRK);
        body.append("}}" + BRK); // Do not use help library at this moment
        body.append(getFSName(cept) + " instance = ");
        //body.append("(" + getFSName(cept) + ") " + "com.tibco.be.functions.object.ObjectHelper.getByExtId(extId);" + BRK);
        body.append("(" + getFSName(cept) + ") " + "com.tibco.be.functions.cluster.DataGridFunctions.CacheLoadConceptByExtIdByUri(extId.toString(), false, \""+cept.getNamespace() + cept.getName()+"\");" + BRK);
        body.append("if (instance == null) {" + BRK);

        String strId = null;
        strId = CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getIdGenerator().nextEntityId(" + getFSName(cept) + ".class)";

        body.append(" instance = new " + getFSName(cept) + "(" + strId + ", extId);" + BRK);

        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"New metric instance created for : \" + extId);" + BRK);
        body.append("}" + BRK);
        body.append("}" + BRK);

        // Id for DVM
        body.append("long dvmid = " + CGConstants.getCurrentRuleSession + ".getRuleServiceProvider().getIdGenerator().nextEntityId(" + getFSName(cept) + ".class);" + BRK);
        body.append(getFSName(cept) + "DVM dvminstance = new " + getFSName(cept) + "DVM(dvmid, extId + \"" + EXTID_SEPARATOR + "\" + dvmid);" + BRK);
        String setPidMethodName = getSetMethodName(ModelNameUtil.generatedMemberName(ConceptAdapter.METRIC_DVM_PARENT_ID_NAME));
        body.append("dvminstance." + setPidMethodName + "(instance.getId());" + BRK);

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            // populate DVM gb and user defined fields
            if (fi.isGroupBy() == true || fi.isUserDefined() == true) {
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
                addIsSetCheckEnd(fieldMap, fi, body);
                continue;
            }
            if (fi.getDependingField() != null) {
                continue;
            }
            if (fi.getAggregationType() == METRIC_AGGR_TYPE.SET_VALUE) {
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
                addIsSetCheckEnd(fieldMap, fi, body);
            }
            body.append(BRK);
        }

        // Assert metric and dvm into rete
        body.append("try {" + BRK);
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"DVM created : \" + dvminstance);" + BRK);
        body.append("}" + BRK);
        body.append(CGConstants.getCurrentRuleSession + ".assertObject(dvminstance, false);" + BRK);
        body.append("} catch (" + com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException.class.getName() + " e) {" + BRK);
        body.append("throw new java.lang.RuntimeException(e.getMessage(), e);" + BRK);
        body.append("}" + BRK);
        body.append("return null;" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }


    // Construct rolling metric specific 'compute' method
    static void getMetricComputeMethod(JavaClassWriter cc, Concept cept, boolean noPropArgs, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {
        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME);
        mr.setAccess("public static");
        mr.addArg("java.util.List<" + getFSName((Concept) cept) + "DVM" + ">", DVMS_VAR_NAME);
        StringBuilder body = new StringBuilder();

        body.append("for (" + getFSName((Concept) cept) + "DVM" + " " + DVM_VAR_NAME + " : " + DVMS_VAR_NAME + ") {" + BRK);

        body.append(COMPUTE_ROLLING_TIME_METHOD_NAME + "(");

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            } else if (fi.getDependingField() != null) {
                FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
                if (tmp.isSystem() == false)
                    continue;
            }

            switch (fi.getType()) {
            case (RDFTypes.STRING_TYPEID):
                body.append(DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().getString(), " + DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.INTEGER_TYPEID):
                body.append(DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().getInt(), " + DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.LONG_TYPEID):
                body.append(DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().getLong(), " + DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.DOUBLE_TYPEID):
                body.append(DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().getDouble(), " + DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.BOOLEAN_TYPEID):
                body.append(DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().getBoolean(), " + DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().isSet()");
                break;
            case (RDFTypes.DATETIME_TYPEID):
                body.append(DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().getDateTime(), " + DVM_VAR_NAME + ".get" + fi.getCodegenName() + "().isSet()");
                break;
            default:// TODO - Throw exception here
                break;
            }
            if (itr.hasNext()) {
                body.append(",");
            }
        }

        body.append(");" + BRK);
        body.append(" } " + BRK);

        mr.setReturnType("void");
        mr.setBody(body);
    }
}
