package com.tibco.cep.decision.table.codegen;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.MethodRecWriter;
import com.tibco.cep.decision.table.language.RowReorderer;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.decisionproject.util.DTModelUtil;
import com.tibco.cep.decisionproject.util.DecisionProjectUtil;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.runtime.service.decision.impl.DTImpl;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.adapters.mutable.MutableRuleFunctionAdapter;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public abstract class DTClassGenerator {
	protected static final String DATE_TIME_IMPL = CGConstants.setArgumentTypes[RDFTypes.DATETIME_TYPEID];
	protected static final String EFFECTIVE = "effective";
	protected static final String EXPIRY = "expiry";
	protected static final String CHECK_DATE = "checkDate";
	protected static final String IMPL_METHOD_NAME = "_invoke";
		
	//priority is 0 thru 10
	protected static final int NUM_PRIOS = 11;
	
	protected Table tableEModel;
	protected MutableRuleFunctionAdapter vrf;
	protected List<RuleError> errorList;
	protected JavaClassWriter jclass;
	protected RowReorderer[] reorderers;
	protected Ontology o;
	
	protected Map<String, Map<String, int[]>> propInfoCache;
	
	protected DTClassGenerator(){}
	
	protected boolean init(Table dt,
			               MutableRuleFunctionAdapter vrf,
			               List<RuleError> errorList, Ontology ontology, Map<String, Map<String, int[]>> propInfoCache) {
		if (dt == null) return false;
		this.tableEModel = dt;		
		if (vrf == null) return false;
		this.vrf = vrf;
		reorderers = setupOrientation();
		this.errorList = errorList;
		o = ontology;
		this.propInfoCache = propInfoCache;
		return true;
	}
	
	private RowReorderer[] setupOrientation() {
		RowReorderer[] reorderArr = new RowReorderer[]{null, null};
		if (tableEModel.getDecisionTable() != null)	reorderArr[0] = new RowReorderer(tableEModel.getDecisionTable().getColumns());
		if (tableEModel.getExceptionTable() != null) reorderArr[1] = new RowReorderer(tableEModel.getExceptionTable().getColumns());
		return reorderArr;
	}
		
	protected JavaClassWriter makeJavaClass() {
		jclass = new JavaClassWriter(className(),null);
		jclass.setAccess("public");
        //jclass.addInterface(VRFImpl.class.getName());
        jclass.addInterface(DTImpl.class.getName());
//        jclass.addInterface(NeedsStaticInitialization.class.getName());
//        jclass.addInterface(NeedsStaticUninitialization.class.getName());

		makeConstructor(jclass);
//        jclass.addMethod(makeStaticInitializer());
//        jclass.addMethod(makeStaticUninitializer());
        makeDateCheckMethod(jclass);

        makeIsVoid(jclass);
        makeInvoke(jclass);
        makePriority(jclass);
        makeIsOneRowOnly(jclass);
        makeInvokeExplicitReturn(jclass);
        makeRuleFunctionImpl(jclass);
        
        return jclass;
	}

	protected MethodRecWriter makeRuleFunctionImpl(JavaClassWriter cc) {
		vrf.setBody("");
        vrf.setVirtual(false);
        //always pass true for is oversize since the generated unboxing code is always needed
        //since VRF impls will always get their arguments as an Object[]
        MethodRecWriter mr = DecisionTableCodeGenerator.generateStaticMethodFromRuleFn(cc,vrf, null, 
        		null, null, true, o, propInfoCache);
        vrf.setVirtual(true);
        
        mr.setReturnType("Object");
        mr.setAccess("private final");
        mr.setName(IMPL_METHOD_NAME);
        
        return mr;
	}
	
	private MethodRecWriter makeIsVoid(JavaClassWriter cc) {
//		MethodRec mr = new MethodRec("public", "boolean", VRFImpl.ISVOID_METHOD_NAME);
		MethodRecWriter mr = cc.createMethod("public", "boolean", VRFImpl.ISVOID_METHOD_NAME);
		mr.setBody("return true;");
		return mr;
	}
	
	private MethodRecWriter makePriority(JavaClassWriter cc) {
//		MethodRec mr = new MethodRec("public", "int", DTImpl.PRIORITY_METHOD_NAME);
		MethodRecWriter mr = cc.createMethod("public", "int", DTImpl.PRIORITY_METHOD_NAME);
		mr.setBody("return " + DTModelUtil.getTablePriority(tableEModel) + ";");
		return mr;
	}
	
	private MethodRecWriter makeIsOneRowOnly(JavaClassWriter cc) {
//		MethodRec mr = new MethodRec("public", "boolean", DTImpl.ONE_ROW_ONLY_METHOD_NAME);
		MethodRecWriter mr = cc.createMethod("public", "boolean", DTImpl.ONE_ROW_ONLY_METHOD_NAME);
		mr.setBody("return " + DTModelUtil.getOneRowExec(tableEModel) + ";");
		return mr;
	}
	
	private MethodRecWriter makeInvoke(JavaClassWriter cc) {
		MethodRecWriter mr = cc.createMethod("public", "Object", VRFImpl.INVOKE_METHOD_NAME);
		mr.addArg(Object.class.getName() + "[]", "args");
		
		mr.setBody("return " + IMPL_METHOD_NAME + "(args);");
		
		return mr;
	}
	
	private MethodRecWriter makeInvokeExplicitReturn(JavaClassWriter cc) {
		MethodRecWriter mr = cc.createMethod("public", "boolean", DTImpl.INVOKE_EXPLICIT_RETURN_METHOD_NAME);
		mr.addArg(Object.class.getName() + "[]", "args");
		
		mr.setBody(IMPL_METHOD_NAME + "(args);\nreturn false;");
		
		return mr;
	}
	
	protected MethodRecWriter makeConstructor(JavaClassWriter jclass) {
		MethodRecWriter mr = jclass.createMethod("public", "", jclass.getLocalName());
//		MethodRec mr = new MethodRec("public", "", jclass.getLocalName());
		StringBuilder body = new StringBuilder();
		
		String effectiveString = DecisionProjectUtil.getMetaDataValue(tableEModel.getMd(), DTConstants.TABLE_EFFECTIVE_DATE);
		String expiryString = DecisionProjectUtil.getMetaDataValue(tableEModel.getMd(), DTConstants.TABLE_EXPIRY_DATE);
		appendDateCheckInit(jclass, body, effectiveString, expiryString);
		
		mr.setBody(body.toString());
		return mr;
	}
	
//	protected MethodRec makeStaticUninitializer() {
//		MethodRec mr = new MethodRec("public static", "void", NeedsStaticUninitialization.UNINIT_METHOD_NAME);
//		
//		StringBuilder body = new StringBuilder();
//		body.append(EFFECTIVE + " = null;\n");
//		body.append(EXPIRY + " = null;\n");
//		
//		mr.setBody(body.toString());
//		return mr; 
//	}
	
	private void appendDateCheckInit(JavaClassWriter jclass, StringBuilder body, String effectiveString, String expiryString) {
		String format = "formatString";
		if((effectiveString != null && effectiveString.length() > 0) 
				|| (expiryString != null && expiryString.length() > 0))
		{
			body.append("final java.lang.String ").append(format).append(" = \"");
			body.append(DTCodegenUtil.CODEGEN_EFFECTIVE_EXPIRY_DATE_FORMAT);
			body.append("\";\n");
		}

		if(effectiveString != null && effectiveString.length() > 0) {
			jclass.addMember("private final", DATE_TIME_IMPL, EFFECTIVE);
			
			body.append(EFFECTIVE).append(" = ");
			body.append(com.tibco.be.functions.java.datetime.JavaFunctions.class.getName()).append(".parseString(\"");
			body.append(effectiveString);
			body.append("\", ").append(format).append(");\n");
		}
		
		if(expiryString != null && expiryString.length() > 0) {
			body.append(EXPIRY).append(" = ");
			
			jclass.addMember("private final", DATE_TIME_IMPL, EXPIRY);
			body.append(com.tibco.be.functions.java.datetime.JavaFunctions.class.getName()).append(".parseString(\"");
			body.append(expiryString);
			body.append("\", ").append(format).append(");\n");
		}
	}

	protected MethodRecWriter makeDateCheckMethod(JavaClassWriter cc) {
//		MethodRec mr = new MethodRec("private final", "boolean", CHECK_DATE);
		MethodRecWriter mr = cc.createMethod("private final", "boolean", CHECK_DATE);
		
		String effectiveString = DecisionProjectUtil.getMetaDataValue(tableEModel.getMd(), DTConstants.TABLE_EFFECTIVE_DATE);
		String expiryString = DecisionProjectUtil.getMetaDataValue(tableEModel.getMd(), DTConstants.TABLE_EXPIRY_DATE);
		boolean hasEffective = effectiveString != null && effectiveString.length() > 0;
		boolean hasExpiry = expiryString != null && expiryString.length() > 0;
		
		if(!hasEffective && !hasExpiry) {
			mr.setBody("return true;\n");
		} else {
			StringBuilder body = new StringBuilder();
			String javaFunctions = com.tibco.be.functions.java.datetime.JavaFunctions.class.getName();
			body.append("return ");
			if(hasEffective) {
				body.append("!").append(javaFunctions).append(".before(");
				body.append(javaFunctions).append(".now(), ");
				body.append(EFFECTIVE).append(")");
				
				if(hasExpiry) {
					body.append(" && ");
				}
			}
			if(hasExpiry) {
				body.append(javaFunctions).append(".before(");
				body.append(javaFunctions).append(".now(), ");
				body.append(EXPIRY).append(")");
			}
			body.append(";\n");
			mr.setBody(body.toString());
		}
		
		return mr;
	}
	
	protected String className() {
		return ModelNameUtil.vrfImplClassShortName(tableEModel.getName());
	}
	
	protected String classFQN() {
		return ModelNameUtil.vrfImplClassFSName(vrf, tableEModel.getName());
	}
	
    public static void generateDecisionTableJavaFiles(DTCodegenGlobalContext ctx) throws IOException {
    	List<RuleError> ruleErrorList = ctx.errors;
    	
    	List<DesignerElement> tables = IndexUtils.getAllElements(ctx.o.getName(), ELEMENT_TYPES.DECISION_TABLE);

        if(!ctx.targetDir.exists()) {
            ctx.targetDir.mkdirs();
        }

        if(tables != null && tables.size() > 0) {
        	StudioProjectConfiguration spc = DTCodegenUtil.getSPC(ctx.o);
		    for(DesignerElement dte : tables) {
		    	if(dte instanceof DecisionTableElement) {
		    		EObject impl = ((DecisionTableElement)dte).getImplementation();
		    		if(impl instanceof Table) {
		    			Table dt = (Table) impl; 
			    		int nextError = ruleErrorList.size();
			    		String path = dt.getPath();
			    		DTCodegenTableContext tableCtx = 
			    			new DTCodegenTableContext(dt, spc == null ? null : spc.getName()
			    				, null, ruleErrorList);
			    		DTCodegenUtil.generateDecisionTableJavaFile(ctx, tableCtx);
			    		if(ruleErrorList.size() > nextError) {
			    			for (; nextError < ruleErrorList.size(); nextError++) {
			    				RuleError ruleError = ruleErrorList.get(nextError);
			    				ruleError.setName(path);
			    			}
			    		}
		    		}
		    	}
		    }
        }
    }
    
    public static void generateDecisionTableJavaStream(DTCodegenGlobalContext ctx) throws IOException {
    	List<RuleError> ruleErrorList = ctx.errors;
    	
    	List<DesignerElement> tables = IndexUtils.getAllElements(ctx.o.getName(), ELEMENT_TYPES.DECISION_TABLE);
 
        if(tables != null && tables.size() > 0) {
        	StudioProjectConfiguration spc = DTCodegenUtil.getSPC(ctx.o);
		    for(DesignerElement dte : tables) {
		    	if(dte instanceof DecisionTableElement) {
		    		EObject impl = ((DecisionTableElement)dte).getImplementation();
		    		if(impl instanceof Table) {
		    			Table dt = (Table) impl;
		    			
			    		int nextError = ruleErrorList.size();
			    		
			    		String path = dt.getPath();
			    		DTCodegenTableContext tableCtx = 
			    			new DTCodegenTableContext(dt, spc == null ? null : spc.getName()
			    				, null, ruleErrorList);
			    		DTCodegenUtil.generateDecisionTableJavaStream(ctx, tableCtx);
			    		if(ruleErrorList.size() > nextError) {
			    			for (; nextError < ruleErrorList.size(); nextError++) {
			    				RuleError ruleError = ruleErrorList.get(nextError);
			    				ruleError.setName(path);
			    			}
			    		}
		    		}
		    	}
		    }
        }
    }
}