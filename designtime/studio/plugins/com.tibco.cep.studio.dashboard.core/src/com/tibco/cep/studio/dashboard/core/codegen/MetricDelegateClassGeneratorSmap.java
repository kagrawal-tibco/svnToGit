package com.tibco.cep.studio.dashboard.core.codegen;

import static com.tibco.be.parser.codegen.CGConstants.BRK;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.tibco.cep.studio.core.util.mapper.MapperMetricUtils.DepFieldInfo;
import com.tibco.cep.studio.core.util.mapper.MapperMetricUtils.FieldInfo;

public class MetricDelegateClassGeneratorSmap extends MetricClassGeneratorSmap {

    @SuppressWarnings("rawtypes")
	protected MetricDelegateClassGeneratorSmap(Concept cept,
			Properties oversizeStringConstants, Map ruleFnUsage,
			Map<String, StateMachineBlockLineBuffer> smblbMap, JavaClassWriter cc, Ontology o
			, Map<String, Map<String, int[]>> propInfoCache)
    {
		super(cept, oversizeStringConstants, ruleFnUsage, smblbMap, cc, o, propInfoCache);
	}

	static void getMetricComputeInternalMethod(JavaClassWriter cc, Concept cept, boolean isRollingTimeMetric, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {

    	MethodRecWriter mr = null;
    	//TODO - Needs to be enabled after Timer Metric code is complete
//    	if (!isRollingTimeMetric) {
            mr = cc.createMethod(EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME);
//    	} else {
//            mr = cc.createMethod(COMPUTE_ROLLING_TIME_METHOD_NAME);
//    	}

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

        // populate the GB fields of the newly created metric
        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isGroupBy() == true) {
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
            }
        }
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
            // generate compute logic
            // Need to process no dependent aggregation field first
            // Do not process isSystem field, do it as part of aggregation
            // field with dependents code gen logic
            if (fi.getDependingField() != null) {
                continue;
            }
            if (fi.getAggregationType() == METRIC_AGGR_TYPE.SET_VALUE) {
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
                addIsSetCheckEnd(fieldMap, fi, body);
            } else if (fi.getAggregationType() == METRIC_AGGR_TYPE.MAXIMUM_VALUE
                    || fi.getAggregationType() == METRIC_AGGR_TYPE.MINIMUM_VALUE) {
                String operator = (fi.getAggregationType() == METRIC_AGGR_TYPE.MAXIMUM_VALUE ? " <" : " >");
                addIsSetCheckStart(fieldMap, fi, body);
                // always store the incoming(intermediate) value into dvm
                body.append("dvminstance." + getDVMSetAuditMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
                // If this is a new instance, store the incoming value regardless
                // Retrieve existing property isSet value
                body.append("boolean p_" + fi.getCodegenName() + "_isSet = instance."
                        + getGetMethodName(fi.getPropertyDefinition()) + "().isSet();" + BRK);
            	body.append("if (!p_" + fi.getCodegenName() + "_isSet) {" + BRK);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(" +
                	fi.getCodegenName() + ");" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(" +
                	fi.getCodegenName() + ");" + BRK);
                // else look at existing values to determine the min/max value
                body.append("} else {" + BRK);
                body.append(CGConstants.setArgumentTypes[fi.getType()] + " p_" + fi.getCodegenName() + " = instance."
                        + getGetMethodName(fi.getPropertyDefinition()) + "()."
                        + CGConstants.jdbcGetterNames[fi.getType()] + "();"
                        + BRK);
                body.append("if (p_" + fi.getCodegenName() + operator + " (" + CGConstants.setArgumentTypes[fi.getType()] + ") " + fi.getCodegenName() + ") {" + BRK);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(" + fi.getCodegenName() + ");" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(" +
                		fi.getCodegenName() + ");} else {" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");}" + BRK);
                body.append("}" + BRK);
                addIsSetCheckEnd(fieldMap, fi, body);
            } else if (fi.getAggregationType() == METRIC_AGGR_TYPE.COUNT_VALUE
                    || fi.getAggregationType() == METRIC_AGGR_TYPE.SUM_VALUE) {
                if (fi.isSystem() == true) {
                    if (fi.getAggregationType() == METRIC_AGGR_TYPE.COUNT_VALUE) {
                        addIsSetCheckStart(fieldMap, fi, body);
                        body.append("dvminstance." + getDVMSetAuditMethodName(fi.getPropertyDefinition()) + "((" + CGConstants.setArgumentTypes[fi.getType()] + ") 1);" + BRK);
                        addIsSetCheckEnd(fieldMap, fi, body);
                        body.append(CGConstants.setArgumentTypes[fi.getType()]
                                + " p_" + fi.getCodegenName() + " = instance."
                                + getGetMethodName(fi.getPropertyDefinition())
                                + "()."
                                + CGConstants.jdbcGetterNames[fi.getType()]
                                + "() + 1;" + BRK);
                    } else {
                        FieldInfo fiTarget = fieldMap.get(fi .getTargetPropertyName());
                        addIsSetCheckStart(fieldMap, fi, body);
                        body.append("dvminstance." + getDVMSetAuditMethodName(fi.getPropertyDefinition()) + "((" + CGConstants.setArgumentTypes[fi.getType()] + ")" + fiTarget.getCodegenName() + ");" + BRK);
                        addIsSetCheckEnd(fieldMap, fi, body);
                        body.append(CGConstants.setArgumentTypes[fi.getType()]
                                + " p_" + fi.getCodegenName() + " = instance."
                                + getGetMethodName(fi.getPropertyDefinition())
                                + "()."
                                + CGConstants.jdbcGetterNames[fi.getType()]
                                + "() + ("
                                + CGConstants.setArgumentTypes[fi.getType()]
                                + ")" + fiTarget.getCodegenName() + ";" + BRK);
                    }
                } else {
                    addIsSetCheckStart(fieldMap, fi, body);
                    body.append("dvminstance." + getDVMSetAuditMethodName(fi.getPropertyDefinition()) + "((" + CGConstants.setArgumentTypes[fi.getType()] + ")" + fi.getCodegenName() + ");" + BRK);
                    addIsSetCheckEnd(fieldMap, fi, body);
                    body.append(CGConstants.setArgumentTypes[fi.getType()]
                            + " p_" + fi.getCodegenName() + " = instance."
                            + getGetMethodName(fi.getPropertyDefinition())
                            + "()." + CGConstants.jdbcGetterNames[fi.getType()]
                            + "() + " + fi.getCodegenName() + ";" + BRK);
                }
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
                if (fi.isSystem() == false)
                    body.append(fi.getCodegenName() + IS_SET + " = true;");
                addIsSetCheckEnd(fieldMap, fi, body);
            } else if (fi.getAggregationType() == METRIC_AGGR_TYPE.SUM_OF_SQUARES_VALUE) {
                // Always is system field
                FieldInfo fiTarget = fieldMap.get(fi.getTargetPropertyName());
                body.append(CGConstants.setArgumentTypes[fi.getType()] + " pi_" + fi.getCodegenName() + " = (" + CGConstants.setArgumentTypes[fi.getType()] + ")(" + fiTarget.getCodegenName() + " * " + fiTarget.getCodegenName() + ");" + BRK);
                body.append(CGConstants.setArgumentTypes[fi.getType()] + " p_" + fi.getCodegenName() + " = instance." + getGetMethodName(fi.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[fi.getType()] + "() + pi_" + fi.getCodegenName() + ";" + BRK);
                // set the incoming value(sq of incoming value in this case)
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("dvminstance." + getDVMSetAuditMethodName(fi.getPropertyDefinition()) + "(pi_" + fi.getCodegenName() + ");" + BRK);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
                if (fi.isSystem() == false)
                    body.append(fi.getCodegenName() + IS_SET + " = true;");
                addIsSetCheckEnd(fieldMap, fi, body);
            }
            body.append(BRK);
        }

        // If the aggregation fields have depending fields which are user
        // defined
        // Do the aggregation after all the dependent field values are computed
        // already
        // Use the computeOrderList to ensure the order of evaluation follows
        // the
        // dependency graph
        for (Iterator<FieldInfo> itr = computeOrderList.iterator(); itr
                .hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.getAggregationType() == METRIC_AGGR_TYPE.AVERAGE_VALUE) {
                DepFieldInfo[] depInfo = fi.getDependingField();
                FieldInfo depSum = fieldMap.get(depInfo[0].getPropertyName());
                FieldInfo depCount = fieldMap.get(depInfo[1].getPropertyName());
                StringBuilder sumExpression = new StringBuilder();
                StringBuilder countExpression = new StringBuilder();
                sumExpression.append("instance." + getGetMethodName(depSum.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depSum.getType()] + "()");
                countExpression.append("instance." + getGetMethodName(depCount.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depCount.getType()] + "()");
                body.append(CGConstants.setArgumentTypes[fi.getType()] + " p_" + fi.getCodegenName() + " = (" + CGConstants.setArgumentTypes[fi.getType()] + ") (" + sumExpression.toString() + "/" + countExpression.toString() + ");" + BRK);
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
				body.append(fi.getCodegenName() + IS_SET + " = true;" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
                addIsSetCheckEnd(fieldMap, fi, body);
            } else if (fi.getAggregationType() == METRIC_AGGR_TYPE.VARIANCE_VALUE
                    || fi.getAggregationType() == METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE) {
                DepFieldInfo[] depInfo = fi.getDependingField();
                FieldInfo depSum = fieldMap.get(depInfo[0].getPropertyName());
                FieldInfo depCount = fieldMap.get(depInfo[1].getPropertyName());
                FieldInfo depSumSq = fieldMap.get(depInfo[2].getPropertyName());
                StringBuilder sumSqExpression = new StringBuilder();
                StringBuilder avgExpression = new StringBuilder();

                // Need to handle type casting here
                sumSqExpression.append("(instance." + getGetMethodName(depSumSq.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depSumSq.getType()] + "() / instance." + getGetMethodName(depCount.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depCount.getType()] + "())");

                avgExpression.append("((instance." + getGetMethodName(depSum.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depSum.getType()] + "() / instance." + getGetMethodName(depCount.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depCount.getType()] + "())");
                avgExpression.append(" * ");
                avgExpression.append("(instance." + getGetMethodName(depSum.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depSum.getType()] + "() / instance." + getGetMethodName(depCount.getPropertyDefinition()) + "()." + CGConstants.jdbcGetterNames[depCount.getType()] + "()))");

                // variance
                if (fi.getAggregationType() == METRIC_AGGR_TYPE.VARIANCE_VALUE) {
                    body.append(CGConstants.setArgumentTypes[fi.getType()] + " p_" + fi.getCodegenName() + " = ");
                    body.append("(" + CGConstants.setArgumentTypes[fi.getType()] + ")");
                    body.append("(");
                    body.append(sumSqExpression);
                    body.append(" - ");
                    body.append(avgExpression);
                    body.append(")");
                    body.append(";" + BRK);
                } else {
                    body.append(CGConstants.setArgumentTypes[fi.getType()] + " p_" + fi.getCodegenName() + " = ");
                    body.append("(" + CGConstants.setArgumentTypes[fi.getType()] + ")");
                    body.append("java.lang.Math.sqrt(");
                    body.append(sumSqExpression);
                    body.append(" - ");
                    body.append(avgExpression);
                    body.append(");" + BRK);
                }
                addIsSetCheckStart(fieldMap, fi, body);
                body.append("instance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
				body.append(fi.getCodegenName() + IS_SET + " = true;" + BRK);
                body.append("dvminstance." + getSetMethodName(fi.getPropertyDefinition()) + "(p_" + fi.getCodegenName() + ");" + BRK);
                addIsSetCheckEnd(fieldMap, fi, body);
            }
        }

        // Assert metric and dvm into rete
        body.append("try {" + BRK);
        body.append("if (logger.isEnabledFor(com.tibco.cep.kernel.service.logging.Level.DEBUG)) {" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"Metric updated : \" + instance);" + BRK);
        body.append("logger.log(com.tibco.cep.kernel.service.logging.Level.DEBUG, \"DVM created : \" + dvminstance);" + BRK);
        body.append("}" + BRK);
        // body.append("com.tibco.be.functions.System.SystemHelper.debugOut(\"Metric updated : \" + instance);"
        // + BRK);
        // body.append("com.tibco.be.functions.System.SystemHelper.debugOut(\"DVM created : \" + dvminstance);"
        // + BRK);
        // FIX THIS - Should we use 'true' here for metric? What is the rule
        // here?
        body.append(CGConstants.getCurrentRuleSession + ".assertObject(instance, false);" + BRK);
        body.append("if (dvmIsEnabled == true) {" + BRK);
        body.append(CGConstants.getCurrentRuleSession + ".assertObject(dvminstance, false);" + BRK);
        body.append("}" + BRK);
        body.append("} catch (" + com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException.class.getName() + " e) {" + BRK);
        body.append("throw new java.lang.RuntimeException(e.getMessage(), e);" + BRK);
        body.append("}" + BRK);
        body.append("return instance;" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }

    // Construct metric specific 'compute' method
    // For DVM I_XXX field, it's used to store the incoming or internally
    // generated intermediate value during each compute call
    static void getMetricComputeMethod(JavaClassWriter cc, Concept cept, Map<String, FieldInfo> fieldMap, List<FieldInfo> computeOrderList) {
        MethodRecWriter mr = cc.createMethod(EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME);
        mr.setAccess("public static");

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            } else if (fi.getDependingField() != null) {
                FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
                if (tmp.isSystem() == false) {
                    continue;
                }
            }
            mr.addArg(CGConstants.setArgumentTypes[fi.getType()], fi.getCodegenName());
        }

        StringBuilder body = new StringBuilder();
        body.append("return " + EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME + "(");

        boolean addNextField = false;

        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem() == true) {
                continue;
            } else if (fi.getDependingField() != null) {
                FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
                if (tmp.isSystem() == false)
                    continue;
            }

            if (addNextField) {
            	body.append(",");
            }

            body.append(fi.getCodegenName() + ", true");

            addNextField = true;
        }

        body.append(");" + BRK);
        mr.setReturnType(getFSName((Concept) cept));
        mr.setBody(body);
    }
}
