package com.tibco.cep.metric;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.functions.object.SAX2ConceptInstance;
import com.tibco.be.model.functions.MapperElementType;
import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.EngineTraxSupport;
import com.tibco.be.util.TemplatesArgumentPair;
import com.tibco.be.util.TraxSupport;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Metric;
import com.tibco.cep.runtime.model.element.MetricDVM;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.datamodel.XiNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Metric",
        category = "MetricInstance",
        synopsis = "Functions to operate on Metrics")
public class MetricHelper {
	private static boolean lockConfigured = false;
	private static long lockTimeout = 1000;
	private static int lockRetryCount = 10;
	private static boolean lockIsLocal = true;
	private static boolean lockEnabled = true;

	private static final String COMPUTE_METHOD_NAME = "compute";

	public static Concept createOrUpdateMetric2(String xslt,
			VariableList varlist) {
		return createOrUpdateMetric(xslt, xslt, varlist);
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "createOrUpdateMetric",
        synopsis = "Creates a Metric instance based on the data provided in the\nXSLT Mapper and adds it to the working memory. Adding the\ninstance to the working memory will cause any rule conditions\nthat depend on the metric to be evaluated.",
        signature = "Metric createOrUpdateMetric(String xslt-template)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt", type = "-template", desc = "String formed using the XSLT Mapper.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Metric", desc = "The newly created Metric instance."),
        version = "1.0",
        see = "",
        mapper =  @com.tibco.be.model.functions.BEMapper(
        		enabled=@com.tibco.be.model.functions.Enabled(value=true),
        		type=MapperElementType.XSLT,
        		inputelement="<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
        				"\t\t\t\t\t\t<xsd:element name=\"createOrUpdateMetric\">\n" +
        				"\t\t\t\t\t\t\t<xsd:complexType>\n" +
        				"\t\t\t\t\t\t\t\t<xsd:sequence>\n" +
        				"\t\t\t\t\t\t\t\t\t<xsd:element name=\"object\" type=\"xsd:anyType\" minOccurs=\"1\" maxOccurs=\"1\" />\n" +
        				"\t\t\t\t\t\t\t\t</xsd:sequence>\n" +
        				"\t\t\t\t\t\t\t</xsd:complexType>\n" +
        				"\t\t\t\t\t\t</xsd:element>\n" +
        				"\t\t\t\t\t</xsd:schema>"),
        description = "Creates a new Metric instance based on the data provided in\nthe XSLT Mapper and adds it to the working memory. Adding\nthe instance to the working memory will cause any rule\nconditions that depend on the concept to be evaluated.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
	public static final Concept createOrUpdateMetric(String key, String xslt,
			VariableList varlist) {
		RuleSession session = RuleSessionManager.getCurrentRuleSession();
		Concept cept = createMetric(session, xslt, xslt, varlist);
		return cept;
	}

	public static Concept createMetric(RuleSession session, String key,
			String xslt, VariableList varlist) {
		try {
			Concept rootConcept = transform2Metric(session, key, xslt, varlist);
			return rootConcept;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Concept transform2Metric(RuleSession session, String key,
			String xslt, VariableList varlist) throws Exception {
		RuleServiceProvider provider = session.getRuleServiceProvider();
		TypeManager manager = provider.getTypeManager();
		GlobalVariables gVars = provider.getGlobalVariables();

		TemplatesArgumentPair tap = EngineTraxSupport.getTemplates(key, xslt, manager);

		List<String> paramNames = tap.getParamNames();
		XiNode nodes[] = new XiNode[paramNames.size()];
		int i = 0;
		for(String paramName : paramNames) {
			if (paramName.equalsIgnoreCase(GlobalVariablesProvider.NAME)) {
				nodes[i] = gVars.toXiNode();
			} else {
				Variable v = varlist.getVariable(paramName);
				if (v != null) {
					nodes[i] = XiNodeBuilder.makeXiNode(v);
				} else {
					nodes[i] = null;
				}
			}
			++i;
		}
		Class clz = tap.getRecvParameterClass();
		Class dvmClz = tap.getDvmClz();

		MetricDVM metricDVM = null;
		if (clz != null) {
			long id = session.getRuleServiceProvider().getIdGenerator()
					.nextEntityId(dvmClz);
			Constructor cons = dvmClz
					.getConstructor(new Class[] { long.class });
			metricDVM = (MetricDVM) cons
					.newInstance(new Object[] { new Long(id) });
		}
		SAX2ConceptInstance ci = new SAX2ConceptInstance(metricDVM, session);
		TraxSupport.doTransform(tap.getTemplates(), nodes, ci);

		Method method = clz.getMethod(COMPUTE_METHOD_NAME,
				new Class[] { tap.getDvmClz() });

		Metric metric = (Metric) method.invoke(null, metricDVM); 

		return metric;

	}

	/* 
	 * Convenience methods, may be used in future
	 *  
	public static boolean acquire_C_Lock(String key) {
		initializeLockConfig();
		if (lockEnabled == false) {
			return true;
		}
		int lockCurrentRetryCount = 0;
		boolean lockStatus = false;
		do {
			lockStatus = DataGridFunctions.Lock(key, lockTimeout, lockIsLocal);
			lockCurrentRetryCount++;
		} while (lockStatus == false && lockCurrentRetryCount < lockRetryCount);
		if (lockStatus == false) {
			com.tibco.be.functions.System.SystemHelper
					.debugOut("Failed to acquire lock for metric : " + key);
		}
		return lockStatus;
	}

	private static void initializeLockConfig() {
		if (lockConfigured == false) {
			lockEnabled = ((com.tibco.be.util.BEProperties) com.tibco.cep.runtime.session.RuleSessionManager
					.getCurrentRuleSession().getRuleServiceProvider()
					.getProperties()).getBoolean(
					"be.engine.metric.lock.enable", true);
			com.tibco.be.functions.System.SystemHelper
					.debugOut("Lock is enabled : " + lockEnabled);
			lockTimeout = ((com.tibco.be.util.BEProperties) com.tibco.cep.runtime.session.RuleSessionManager
					.getCurrentRuleSession().getRuleServiceProvider()
					.getProperties()).getLong("be.engine.metric.lock.timeout",
					1000);
			com.tibco.be.functions.System.SystemHelper
					.debugOut("Lock retry timeout : " + lockTimeout);
			lockRetryCount = ((com.tibco.be.util.BEProperties) com.tibco.cep.runtime.session.RuleSessionManager
					.getCurrentRuleSession().getRuleServiceProvider()
					.getProperties()).getInt(
					"be.engine.metric.lock.retrycount", 10);
			com.tibco.be.functions.System.SystemHelper
					.debugOut("Lock retry count : " + lockRetryCount);
			lockIsLocal = ((com.tibco.be.util.BEProperties) com.tibco.cep.runtime.session.RuleSessionManager
					.getCurrentRuleSession().getRuleServiceProvider()
					.getProperties()).getBoolean(
					"be.engine.metric.lock.islocal", true);
			com.tibco.be.functions.System.SystemHelper
					.debugOut("Lock is local : " + lockIsLocal);
			lockConfigured = true;
		}
	}
	*/
}
