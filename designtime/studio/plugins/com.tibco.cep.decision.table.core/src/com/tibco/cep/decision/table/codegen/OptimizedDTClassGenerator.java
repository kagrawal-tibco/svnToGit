package com.tibco.cep.decision.table.codegen;

import static com.tibco.cep.decision.table.language.DTRegexPatterns.RETURN_REGEX;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.CGUtil;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.MethodRec;
import com.tibco.be.parser.codegen.MethodRecWriter;
import com.tibco.be.parser.codegen.RuleClassGeneratorSmap;
import com.tibco.be.parser.codegen.RuleFunctionCodeGeneratorSmap;
import com.tibco.be.parser.semantic.CompilableDeclSymbolTable;
import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.SourceType;
import com.tibco.cep.decision.table.language.DTValidator;
import com.tibco.cep.decision.table.language.ErrorListRuleErrorRecorder;
import com.tibco.cep.decision.table.language.MergedTable;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.util.DTModelUtil;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.mutable.MutableRuleFunctionAdapter;

public class OptimizedDTClassGenerator extends DTClassGenerator {
	private static final String RESULTS_ARRAY_NAME = "conditionResults";
	private static final String ROW_COUNT_NAME = "rowCnt";
	private static final String MATCHING_ROWIDS_ARRAY_NAME = "matchingRowIds";
	private static final String COMPACT_MATCHING_ROWIDS_ARRAY_NAME = "compactMatchingRowIds";
	private static final String ACT_PRIO_ARR = "actPrioArr";
	//private static final String COND_PRIO_ARR = "condPrioArr" + OBF_STRING;
	//private static final String COND_STR = "condStr" + OBF_STRING;
	private static final String ACT_STR = "actStr";
	//TODO compute the minimum prerequisite true conditions to evaluate a given condition and use that to
	//populate the results array
	private static final String RESULTS_ARRAY_TYPE = "byte";
	private static String EVAL_CONDITION = "evalCondition";
	private static String EXEC_ACTION = "execAction";
	//TODO arbitrary
	private static int MAX_METHODS = Integer.parseInt(getConfig("bui.codegen.max_methods", "3000"));
	private static int MIN_CONDS_PER_METHOD = Integer.parseInt(getConfig("bui.codegen.min_conds_per_method", "1"));
	private static int MIN_ACTS_PER_METHOD = Integer.parseInt(getConfig("bui.codegen.min_acts_per_method", "1"));
	
	
	//array of length 2*NUM_PRIO for the strings that control condition and action execution
	//protected StringBuilder[] conditionBufs = null;
	protected StringBuilder[] actionBufs = null;
	protected ArrayList<JavaFragment> conds = null;
	protected ArrayList<JavaFragment> acts = null;
	protected String argsString = null;
	protected String[][] argsSig = null;
	
	private int rowCount;

	private static String getConfig(String key, String def) {
		//BUIConfig.getProperty
		return def;
	}
	
	protected boolean init(Table dt,
			               MutableRuleFunctionAdapter vrf,
			               List<RuleError> errorList, Ontology o, Map<String, Map<String, int[]>> propInfoCache) {
		if (super.init(dt, vrf, errorList, o, propInfoCache)) {
			argsString = makeArgsString(vrf);
			argsSig = makeArgsArray(vrf, o);
			return true;
		} else {
			return false;
		}
	}
	
	protected JavaClassWriter makeJavaClass() {
		JavaClassWriter jclass = super.makeJavaClass();
		jclass.addMember("private final", "java.lang.String[]", ACT_PRIO_ARR);
		
		int maxCondMethods = (int)(MAX_METHODS * (((double)conds.size())/((double)(conds.size() + acts.size()))));
		if(maxCondMethods <= 0) maxCondMethods = 1;
		int condsPerMethod = conds.size() / maxCondMethods;
		//round up the result of the division
		//TODO do this better, 11 conds and 10 methods max results in only 6 methods used
		if(conds.size() % maxCondMethods > 0) ++condsPerMethod;
		if(condsPerMethod < MIN_CONDS_PER_METHOD) condsPerMethod = MIN_CONDS_PER_METHOD;
		
		int maxActMethods = MAX_METHODS - maxCondMethods;
		if(maxActMethods <=0) maxActMethods = 1;
		int actsPerMethod = acts.size() / maxActMethods;
		//round up the result of the division
		//TODO do this better, 11 acts and 10 methods max results in only 6 methods used
		if(acts.size() % maxActMethods > 0) ++actsPerMethod;
		if(actsPerMethod < MIN_ACTS_PER_METHOD) actsPerMethod = MIN_ACTS_PER_METHOD;
		
		addConditionMethods(jclass, condsPerMethod);
		conds = null;
		addActionMethods(jclass, actsPerMethod);
		acts = null;
		
		return jclass;
	}
	
	@SuppressWarnings("unchecked")
	private static String[][] makeArgsArray(RuleFunction vrf, Ontology o) {
		List<Symbol> list = (List<Symbol>)vrf.getArguments().getSymbolsList();
		String[][] args = new String[list.size()][];
		for(int ii = 0; ii < list.size(); ii++) {
			Symbol sym = list.get(ii);
			//model name
			args[ii] = new String[]{RuleFunctionCodeGeneratorSmap.getArgumentTypeName(sym, o), ModelNameUtil.generatedScopeVariableName(sym.getName())};
		}
		return args;
	}
	
	@SuppressWarnings("unchecked")
	private static String makeArgsString(RuleFunction vrf) {
		List<Symbol> list = (List<Symbol>)vrf.getArguments().getSymbolsList();
		StringBuilder args = new StringBuilder();
		for(int ii = 0; ii < list.size(); ii++) {
			Symbol sym = list.get(ii);
			if(ii > 0) args.append(", ");
			//model name
			args.append(ModelNameUtil.generatedScopeVariableName(sym.getName()));
		}
		return args.toString();
	}
	
	public static JavaClassWriter makeJavaClass(DTCodegenTableContext ctx, MutableRuleFunctionAdapter vrf, Ontology o
			, Map<String, Map<String, int[]>> propInfoCache) 
	{
		OptimizedDTClassGenerator dtGen = new OptimizedDTClassGenerator();
		if(!dtGen.init(ctx.table, vrf, ctx.errorList, o, propInfoCache)) {
			return null;
		} else {
			if(!dtGen.prepareLists(ctx.mt, ctx.projectName, o)) return null;
			ctx.mt = null;
			return dtGen.makeJavaClass();
		}
	}
	
	protected MethodRecWriter makeRuleFunctionImpl(JavaClassWriter cc) {
		MethodRecWriter mr = super.makeRuleFunctionImpl(cc);
        
        //need to use mr.getBody because it will have 
        //some boilerplate code in it after the above call
        //on the vrf with empty body
        CharSequence ruleFunctionBoilerplate = mr.getBody();
        if(ruleFunctionBoilerplate == null) ruleFunctionBoilerplate = "";
        StringBuilder body = new StringBuilder();

        body.append("int " + ROW_COUNT_NAME + "= 0;\n");
        
        body.append("int[] " + MATCHING_ROWIDS_ARRAY_NAME + " = new int[" + rowCount + "];\n");
        body.append(java.util.Arrays.class.getName());
        body.append(".fill(" + MATCHING_ROWIDS_ARRAY_NAME + ", (int)0);\n");
        
        if(acts != null && acts.size() != 0) { 
        	body.append("if(!").append(CHECK_DATE).append("()) return null;\n");
        	
	        body.append(ruleFunctionBoilerplate);

	        body.append(RESULTS_ARRAY_TYPE + "[] " + RESULTS_ARRAY_NAME + " = null;\n");
	        body.append("final boolean[] returnStatus = {false};\n");
	        
	    	String prioIdx = "prioIdx";
			//2* NUM_PRIOS because exception table priorities are separate
			body.append("for(int " + prioIdx + " = 0; " + prioIdx + " < " + NUM_PRIOS * 2 + "; " + prioIdx + "++) {\n\n");
			
			body.append("java.lang.String " + ACT_STR + " = " + ACT_PRIO_ARR + "[" + prioIdx + "];\n");		
			body.append("if(" + ACT_STR + " != null) {\n");
			
			body.append("if(" + RESULTS_ARRAY_NAME + " == null) ");
			body.append(RESULTS_ARRAY_NAME + " = new ");
			body.append(RESULTS_ARRAY_TYPE + "[").append(conds.size()).append("];\n");
			body.append("else ").append(java.util.Arrays.class.getName());
			body.append(".fill(" + RESULTS_ARRAY_NAME + ", (" + RESULTS_ARRAY_TYPE + ")0);\n");
			
			appendInterpreterLoop(body);
		
			body.append("}\n");
			body.append("}\n");
        }
        
        body.append("if (" + ROW_COUNT_NAME + " > 0 && " + ROW_COUNT_NAME + " < " + MATCHING_ROWIDS_ARRAY_NAME + ".length) {\n");
        body.append("int[] " + COMPACT_MATCHING_ROWIDS_ARRAY_NAME + " = " + java.util.Arrays.class.getName() + ".copyOf(" + MATCHING_ROWIDS_ARRAY_NAME +", " + ROW_COUNT_NAME + ");\n");
        body.append(MATCHING_ROWIDS_ARRAY_NAME + " = null;\n");
        body.append("return " + COMPACT_MATCHING_ROWIDS_ARRAY_NAME + ";\n");
        body.append("}\n");
        
        body.append("return " + MATCHING_ROWIDS_ARRAY_NAME + ";\n");
        
		mr.setBody(body.toString());
		return mr;
	}
	
	
	
	protected MethodRecWriter makeConstructor(JavaClassWriter jclass) {
		MethodRecWriter mr = super.makeConstructor(jclass);
		StringBuilder body = null;
		if(mr.getBody() == null) {
			body = new StringBuilder();
		} else {
			body = new StringBuilder(mr.getBody());
		}
		appendActionStrComment(actionBufs, body);
		body.append("\n");
		body.append(ACT_PRIO_ARR + " = ");
		appendStringLiteralArray(actionBufs, body);
		body.append(";\n");
		actionBufs = null;
		
		//body.append("System.out.println(\"*$*********** static init " + dt.getName() + "***************$*\");\n");
		
		mr.setBody(body.toString());
		return mr;
	}
	
//	protected MethodRec makeStaticUninitializer() {
//		MethodRec mr = super.makeStaticUninitializer();
//		StringBuilder body = null;
//		if(mr.getBody() == null) {
//			body = new StringBuilder();
//		} else {
//			body = new StringBuilder(mr.getBody());
//		}
//		body.append(ACT_PRIO_ARR).append(" = null;\n");
//		//body.append("System.out.println(\"*$*********** static uninit " + dt.getName() + "***************$*\");\n");
//		mr.setBody(body.toString());
//		return mr;
//	}
	
	private boolean prepareLists(MergedTable mergedTable, String projectName, Ontology o)
	{
		//merges the table based on the contents of the cells in the decision table editor
		//to optimize the validation process
		if(mergedTable == null) {
			mergedTable = new MergedTable();
			mergedTable.mergeTable(tableEModel, null, null, reorderers, projectName);
		}
		
		ArrayList<DTRow> rows = mergedTable.getRows();
		if (rows != null) rowCount = rows.size();
		if(o == null) return false;
		DTValidator dtv = DTValidator.newDTValidator(tableEModel, o, new ErrorListRuleErrorRecorder(errorList));
		if(dtv == null) return false;

		//Performs additional optimizations by transforming the parse tree stored in the 
		//RootNodes property of DTCell.  May create or remove DTCells.
		//Optimized RootNodes are converted into Java code and stored as body of DTCell.
		//After this method all DTCell bodies will be java code (not rules language)
		//and the cells will be merged in terms of their java bodies.
		populateRootNodes(mergedTable, errorList, dtv);
		 
		conds = new ArrayList<JavaFragment>();
		acts = new ArrayList<JavaFragment>();

		//all the functions that need to code generate something can put their RootNode in this list
		//and then pass ri to RuleClassGenerator, sine riRootNode is contained in ri.
		ArrayList<RootNode> riRootNodes = new ArrayList<RootNode>();
		RuleInfo ri = new RuleInfo();
		ri.setThenTrees(riRootNodes);
		ri.setDeclarations((Symbols)vrf.getScope());
		CompilableDeclSymbolTable symbolTable = new CompilableDeclSymbolTable(vrf, o);
		
		generateAndMergeOptimizedJava(mergedTable, ri, riRootNodes, o, symbolTable, conds, acts);

		doSorts(rows, conds, acts);
		
		//conditionBufs = createPerPriorityConditionStrings(rows);
		actionBufs = createPerPriorityRowActionStrings(rows);
		return true;
	}
	
	private static void appendActionStrComment(StringBuilder[] bufs, StringBuilder out) {
		out.append("/* { ");
		for(int ii = 0; ii < bufs.length; ii++) {
			StringBuilder buf = bufs[ii];
			if(ii > 0) out.append(" || ");
			if(buf == null) {
				out.append("null");
			} else {
				for(int jj = 0; jj < buf.length(); jj++) {
					if(jj > 0) out.append(",");
					char chr = buf.charAt(jj);
					if(chr == '\\') {
						chr = unescapeChar(buf.charAt(++jj));
					} 
						out.append((int)chr);
				}
			}
		}
		out.append(" } */");
	}
	
	private static void appendStringLiteralArray(StringBuilder[] bufs, StringBuilder body) {
		//just assume worst case 3 bytes per char (unverified)
		int limit = CGUtil.MAX_STRING_LITERAL_BYTES / 3;
		body.append("new java.lang.String[]{");
		for(int ii = 0; ii < bufs.length; ii++) {
			StringBuilder buf = bufs[ii];
			if(ii > 0) body.append(", ");
			if(buf == null) body.append("null");
			else {
				body.append("(new ").append(java.lang.StringBuilder.class.getName());
				body.append("(").append(buf.length()).append("))");
				int start = 0;
				while(start < buf.length()) {
					body.append(".append(");
					int end = adjustSubstringEnd(buf, start + limit);
					body.append('"');
					body.append(buf.substring(start, end));
					body.append('"');
					body.append(")");
					start = end;
				}
				body.append(".toString()");
			}
		}
		body.append("}");
		
	}
	
	//don't separate an escape sequence
	//like \n into ...\" + "n...
	//have to handle long sequences of \\
	private static int adjustSubstringEnd(StringBuilder buf, int end) {
		if(end >= buf.length()) return buf.length();
		if(buf.charAt(end - 1) != '\\') return end;
		
		int backslashCount = 0;
		for(int ii = end -1; ii >= 0; ii--) {
			if(buf.charAt(ii) == '\\') ++backslashCount;
			else break;
		}
		//even number of backslashes are "self contained"
		//if odd number, the final backslash is escaping the following character
		if(backslashCount % 2 == 0) {
			return end;
		} else {
			return end - 1;
		}
	}
	
	private void appendInterpreterLoop(StringBuilder body) {
		final String strPos = "strPos";
		final String condsEnd = "condsEnd";
		final String actsEnd = "actsEnd";
		
		/* evaluate conditions and populate results array */
		appendOuterLoopPrefix(body, strPos, condsEnd, actsEnd, false);

		body.append("while(" + strPos + " < " + condsEnd + ") {\n");
		final String result = "result";
		final String condIdx = "condIdx";
		
		body.append("boolean " + result + " = false;\n");
		body.append("final int " + condIdx + " = " + ACT_STR + ".charAt(" + strPos + "++);\n");
		body.append(result + " = " + RESULTS_ARRAY_NAME + "[" + condIdx + "] > 0;\n");
		body.append("if(" + RESULTS_ARRAY_NAME + "[" + condIdx + "] == 0) {\n");
		body.append(result + " = " + EVAL_CONDITION + "(" + condIdx);
		if(argsString.length() > 0) {
			body.append(", ");
			body.append(argsString);
		}
		body.append(");\n");
		body.append(RESULTS_ARRAY_NAME);
		body.append("[" + condIdx + "]");
		body.append(" = " + result + " ? (" + RESULTS_ARRAY_TYPE + ")1 : (" + RESULTS_ARRAY_TYPE + ")-1;\n");
		body.append("}\n");
		
		body.append("if(!" + result + ") {\n");
		body.append("break;\n");
		body.append("}\n");
		body.append("}\n");
		
		//after done with conditions move strPos to the start of next row
		body.append(strPos + " = " + actsEnd + ";\n");
		body.append("}\n\n");
		
		/* after all conditions are checked, check results array and execute row actions for rows with all conditions true*/
		//label for labeled break below
		body.append("outerLoop:\n");
		appendOuterLoopPrefix(body, strPos, condsEnd, actsEnd, true);
		
		//check that all conditions for this row are true
		body.append("while(" + strPos + " < " + condsEnd + ") {\n");
		body.append("final int " + condIdx + " = " + ACT_STR + ".charAt(" + strPos + "++);\n");
		//if any conditions are false, break and move strPos to start of next row
		//use a labeled break to skip the return statement after the action exec loop
		//that is generated when singleRowExecution is true;
		body.append("if(" + RESULTS_ARRAY_NAME + "[" + condIdx + "] <= 0) {\n");
		body.append(strPos + " = " + actsEnd + ";\n");
		body.append("continue outerLoop;\n}\n");
		body.append("}\n\n");

		body.append("returnStatus[0] = false;\n");
		body.append("if (" + strPos + " < " + actsEnd + ") {\n");
		body.append(MATCHING_ROWIDS_ARRAY_NAME + "[" + ROW_COUNT_NAME  + "++] = rowId;\n");
		body.append("while(" + strPos + " < " + actsEnd + ") {\n");
		body.append(EXEC_ACTION + "(" + ACT_STR + ".charAt(" + strPos + "++)");
		body.append(", returnStatus");
		if(argsString.length() > 0) {
			body.append(", ");
			body.append(argsString);
		}
		body.append(");\n");
		body.append("if(returnStatus[0]) return " + MATCHING_ROWIDS_ARRAY_NAME + ";\n");
		body.append("}\n");
		body.append("}\n");
		
		if(DTModelUtil.getOneRowExec(tableEModel)) {
			//even if a row has 0 actions, if the labeled break caused by condition failure is not taken
			//this return will be reached.
			body.append("return " + MATCHING_ROWIDS_ARRAY_NAME + ";\n");
		}
		
		body.append("}\n");
	}
	
	//returns number of closing braces required
	private int appendOuterLoopPrefix(StringBuilder body, String strPos, String condsEnd, String actsEnd, boolean addRowId) {
		body.append("for(int " + strPos + " = 0; " + strPos + " < " + ACT_STR + ".length();) {\n");
		
		// rowId indicates the row being processed
		//condsEnd = strPos + 2 + <number of conditions to check, encoded in the string>
		//the + 2 is because before the conditions and actions are two characters which encode the number
		//of conditions and actions respectively
		if (addRowId) {
			body.append("int rowId = " + ACT_STR + ".charAt(" + strPos + "++);\n");
		} else {
			body.append(strPos + "++;\n");
		}
		body.append("int " + condsEnd + " = " + strPos + " + 2 + " + ACT_STR + ".charAt(" + strPos + "++);\n");
		body.append("int " + actsEnd + " = " + condsEnd + " + " + ACT_STR + ".charAt(" + strPos + "++);\n");
		
		return 1;
	}
	
	private void addConditionMethods(JavaClassWriter jclass, int condsPerMethod) {
		StringBuilder outerBody = new StringBuilder();
		
		if(condsPerMethod > 1) {
			outerBody.append("switch(condIdx / "+condsPerMethod+") {\n");
		} else {
			outerBody.append("switch(condIdx) {\n");			
		}
		
		int totalMethods = conds.size() / condsPerMethod;
		if(conds.size() % condsPerMethod > 0) ++totalMethods;
		int condIndex = 0;
		for(int numMethods=0; numMethods < totalMethods; numMethods++) {
			String innerName = EVAL_CONDITION + numMethods;
			outerBody.append("case " + numMethods + " :\n");
			outerBody.append("return " + innerName+ "(");
			if(condsPerMethod > 1) {
				outerBody.append("condIdx");
				if(argsString.length() > 0) {
					outerBody.append(", ");
				}
			}
			outerBody.append(argsString);

			outerBody.append(");\n");
			
			StringBuilder body = new StringBuilder();
			if(condsPerMethod > 1) body.append("switch(condIdx) {\n");
			
			int end = condIndex + condsPerMethod;
			if(end > conds.size()) end = conds.size();
			for(; condIndex < end; condIndex++) {
				JavaFragment cond = conds.get(condIndex);
				if(condIndex != cond.getId()) throw new RuntimeException("condIndex mismatch");
				if(condsPerMethod > 1) {
					body.append("case " + condIndex + " :\n");
				}
				body.append("{ return ( ");
				body.append(cond.getBody());
				if(body.charAt(body.length()-1) == '\n') body.deleteCharAt(body.length()-1);
				body.append(" ); }\n");
			
			}
			if(condsPerMethod > 1) {
				body.append("default:\n");
				body.append("return false;\n");
				body.append("}");
			}
			
			addConditionMethod(jclass, innerName, body, condsPerMethod > 1);
		}
		outerBody.append("default:\n");
		outerBody.append("return false;\n");
		outerBody.append("}\n");
		
		addConditionMethod(jclass, EVAL_CONDITION, outerBody, true);
	}
	
	private void addConditionMethod(JavaClassWriter jclass, String name, CharSequence body, boolean includeCondIdx) {
//		MethodRec mr = new MethodRec("private final", "boolean", name);
		MethodRecWriter mr = jclass.createMethod("private final", "boolean", name);
		mr.setBody(body.toString());
		if(includeCondIdx) mr.addArg("final int", "condIdx");
		for(String[] arg : argsSig) {
			mr.addArg("final " + arg[0], arg[1]);
		}
//		jclass.addMethod(mr);
	}

	private void addActionMethods(JavaClassWriter jclass, int actsPerMethod) {
		StringBuilder outerBody = new StringBuilder();
		if(actsPerMethod > 1) {
			outerBody.append("switch(actIdx / "+actsPerMethod+") {\n");
		} else {
			outerBody.append("switch(actIdx) {\n");
		}

		
		int totalMethods = acts.size() / actsPerMethod;
		if(acts.size() % actsPerMethod > 0) ++totalMethods;
		int actIndex = 0;
		for(int numMethods=0; numMethods < totalMethods; numMethods++) {
			String innerName = EXEC_ACTION + numMethods;
			outerBody.append("case " + numMethods + " :\n");
			outerBody.append(innerName + "(");
			if(actsPerMethod > 1) outerBody.append("actIdx, ");
			outerBody.append("returnStatus");
			if(argsString.length() > 0) {
				outerBody.append(", ");
				outerBody.append(argsString);
			}
			outerBody.append(");\n");
			outerBody.append("break;\n");
			
			StringBuilder body = new StringBuilder();

			int end = actIndex + actsPerMethod;
			if(end > acts.size()) end = acts.size();
			
			//checking for the string "return" is lame but sufficient because it will detect the return keyword
			boolean mightReturn = false;
			for(int ii = actIndex; ii < end; ii++) {
				String fragBody = acts.get(ii).getBody();
				if(RETURN_REGEX.matcher(fragBody).matches()) {
					mightReturn = true;
					break;
				}
			}
			
			
			if(mightReturn) body.append("returnStatus[0] = true;\n");
			if(actsPerMethod > 1) body.append("switch(actIdx) {\n");
			

			for(; actIndex < end; actIndex++) {
				JavaFragment act = acts.get(actIndex);
				if(actIndex != act.getId()) throw new RuntimeException("actIndex mismatch");
				if(actsPerMethod > 1) body.append("case " + actIndex + " :\n");
				//only need to check return status for actions that might return
				if(mightReturn) body.append("if (0==0) {\n");
				body.append(act.getBody());
				if(!(body.charAt(body.length()-1) == '\n')) body.append("\n");
				if(mightReturn) body.append("}\n");
				if(actsPerMethod > 1) body.append("break;\n");
			}
			if(actsPerMethod > 1) body.append("}");
			if(mightReturn) body.append("returnStatus[0] = false;\n");
			
			addInnerActionMethod(jclass, innerName, body, actsPerMethod > 1);
		}
		outerBody.append("}\n");
		
//		MethodRec mr = new MethodRec("private final", null, EXEC_ACTION);
		MethodRec mr = jclass.createMethod("private final", null, EXEC_ACTION);
		mr.setBody(outerBody.toString());
		mr.addArg("final int", "actIdx");
		mr.addArg("final boolean[]", "returnStatus");
		for(String[] arg : argsSig) {
			mr.addArg("final " + arg[0], arg[1]);
		}
//		jclass.addMethod(mr);
	}

	private void addInnerActionMethod(JavaClassWriter jclass, String name, CharSequence body, boolean includeActIdx) {
//		MethodRec mr = new MethodRec("private final", "void", name);
		MethodRec mr = jclass.createMethod("private final", "void", name);
		mr.setBody(body.toString());
		if(includeActIdx) mr.addArg("final int", "actIdx");
		mr.addArg("final boolean[]", "returnStatus");
		for(String[] arg : argsSig) {
			mr.addArg("final " + arg[0], arg[1]);
		}
//		jclass.addMethod(mr);
	}
	
	private static void doSorts(List<DTRow> rows, List<JavaFragment> conds, List<JavaFragment> acts) {
		Collections.sort(rows, new Comparator<DTRow>() {
			public int compare(DTRow a, DTRow b) {
				if(a.getPriority() == b.getPriority()) return 0;
				if(a.getPriority() < b.getPriority()) return -1;
				return 1;
			}
		});
	
		//purpose of this sort is simply to make the switch statement cases for actions and conditions 
		//count up from lowest to highest
		//to ensure that the compiler generates a tableswitch instead of a lookupswitch 
		Comparator<JavaFragment> comp = new Comparator<JavaFragment>(){
			public int compare(JavaFragment o1, JavaFragment o2) {
				if(o1 == o2) return 0;
				if(o1 == null) return -1;
				if(o2 == null) return 1;
				if(o1.getId() < o2.getId()) return -1;
				if(o1.getId() == o2.getId()) return 0;
				return 1;
			}
		};
		Collections.sort(conds, comp);
		Collections.sort(acts, comp);
	}
	
	private static StringBuilder[] createPerPriorityRowActionStrings(List<DTRow> rows) {
		StringBuilder[] actionBufArr = new StringBuilder[NUM_PRIOS*2];
		for(DTRow row : rows) {
			StringBuilder buf = actionBufArr[row.getPriority()];
			if(buf == null) {
				buf = new StringBuilder();
				actionBufArr[row.getPriority()] = buf;
			}
			appendRowString(row, buf);
		}
		return actionBufArr;
	}
	
	//structure is <rowId><num condition ids><num action ids><list of condition ids (one char = one id)><list of action ids (one char = one id)>
	private static void appendRowString(DTRow row, StringBuilder buf) {
		if (row.getRowId() > 0) {
			checkIdRange(row.getRowId());
			appendChar(buf, (char)row.getRowId());
		}
		
		int numConds = 0;
		for(DTCell cond : row.getConditions()) {
			numConds += cond.getJavaBodies().length;
		}
		checkIdRange(numConds);
		appendChar(buf, (char)numConds);

		int numActs = 0;
		for(DTCell act : row.getActions()) {
			numActs += act.getJavaBodies().length;
		}
		checkIdRange(numActs);
		appendChar(buf, (char)numActs);
		
		for(DTCell cond : row.getConditions()) {
			for(JavaFragment frag : cond.getJavaBodies()) {
				checkIdRange(frag.getId());
				appendChar(buf, (char)frag.getId());
			}
		}
		for(DTCell action : row.getActions()) {
			for(JavaFragment frag : action.getJavaBodies()) {
				checkIdRange(frag.getId());
				appendChar(buf, (char)frag.getId());
			}
		}
	}
	
	private static void appendChar(final StringBuilder bldr, final char chr) {
		switch(chr) {
			case '\b' :
				bldr.append("\\b");
				break;
			case '\t' :
				bldr.append("\\t");
				break;
			case '\n' :
				bldr.append("\\n");
				break;
			case '\f' :
				bldr.append("\\f");
				break;
			case '\r' :
				bldr.append("\\r");
				break;
			case '"' :
				bldr.append("\\\"");
				break;
			case '\\' :
				bldr.append("\\\\");
				break;
			default:
				bldr.append(chr);
				break;
		}
	}
	
	private static char unescapeChar(char escCode) {
		switch(escCode) {
		case 'b' :
			return'\b';
		case 't' :
			return '\t';
		case 'n' :
			return '\n';
		case 'f' :
			return '\f';
		case 'r' :
			return '\r';
		case '"' :
			return '"';
		case '\\' :
			return '\\';
		default:
			return Character.MAX_VALUE;
		}
	}
	
	private static void checkIdRange(int id) {
		if(id > Character.MAX_VALUE) {
			throw new RuntimeException(id + " exceeds range of char");
		}
	}

	private static void populateRootNodes(MergedTable mt, List<RuleError> errorList, DTValidator dtv) {
		//ensure all cells have rootNodes
		for(Map.Entry<String, DTCell> entry : mt.getConditions().entrySet()) {
			String body = entry.getKey();
			DTCell cond = entry.getValue();
			if(cond.getRootNodes() == null) {
				RootNode rn = dtv.compileStandaloneExpression(new StringReader(body), errorList, SourceType.DT_CONDITION_CELL);
				ArrayList<RootNode> rootNodes = new ArrayList<RootNode>(1);
				if(rn != null) {
					rootNodes.add(rn);
				}
				cond.setRootNodes(rootNodes);
			}
		}
		for(Map.Entry<String, DTCell> entry : mt.getActions().entrySet()) {
			String body = entry.getKey();
			DTCell act = entry.getValue();
			if(act.getRootNodes() == null) {
				List<RootNode> rns = dtv.compileActionBlock(new StringReader(body), errorList, true);
				if(rns == null) {
					rns = new ArrayList<RootNode>(0);
				}
				act.setRootNodes(rns);
			}
		}
	}
	
	private static void generateAndMergeOptimizedJava(MergedTable mt, RuleInfo ri, List<RootNode> riRootNode
			, Ontology ontology, RLSymbolTable rlst, List<JavaFragment> conds, List<JavaFragment> acts) {
		Map<String, Map<String, int[]>> propInfoCache = new HashMap<String, Map<String, int[]>>();
		generateAndMergeJavaConditions(mt, ri, riRootNode, ontology, rlst, conds, propInfoCache);
		generateAndMergeJavaActions(mt, ri, riRootNode, ontology, rlst, acts, propInfoCache);
	}
	
	//can not split up root nodes for Actions, because custom actions can have many lines where one is related to the next.
	//example:  int i = 1; i = 2;
	private static void generateAndMergeJavaActions(MergedTable mt, RuleInfo ri, List<RootNode> riRootNode
			, Ontology ontology, RLSymbolTable rlst, List<JavaFragment> acts, Map<String, Map<String, int[]>> propInfoCache)
	{
		HashMap<String, JavaFragment> javaFragMap = new HashMap<String, JavaFragment>();
		int id = 0;
		for(DTCell act : mt.getActions().values()) {
			List<RootNode> nodes = act.getRootNodes();
			if(nodes != null && nodes.size() > 0) {
				JavaFragment[] javaFrags = new JavaFragment[1];
				act.setJavaBodies(javaFrags);
				act.setRootNodes(null);
				
				StringBuilder javaBody = new StringBuilder();
				for(int ii = 0; ii < nodes.size(); ii++) {
					if(ii > 0) javaBody.append("\n");
					javaBody.append(rootNodeToJava(nodes.get(ii), ri, riRootNode, ontology, rlst, propInfoCache));
				}
				String javaString = javaBody.toString();
				javaBody = null;
				javaFrags[0] = javaFragMap.get(javaString);
				if(javaFrags[0] == null) {
					javaFrags[0] = new JavaFragment(javaString, id++);
					javaFragMap.put(javaString, javaFrags[0]);
					acts.add(javaFrags[0]);
				}
			}
		}
	}
	
	private static void generateAndMergeJavaConditions(MergedTable mt, RuleInfo ri, List<RootNode> riRootNode, Ontology ontology
			, RLSymbolTable rlst, List<JavaFragment> conds, Map<String, Map<String, int[]>> propInfoCache)
	{
		HashMap<String, JavaFragment> javaFragMap = new HashMap<String, JavaFragment>();
		ConditionSplitNodeVisitor split = new ConditionSplitNodeVisitor();
		int id = 0;
		for(DTCell cond : mt.getConditions().values()) {
			List<RootNode> nodes = cond.getRootNodes();
			if(nodes != null && nodes.size() > 0) {
				//TODO make sure the left to right order is preserved
				//so asdf != null && asdf.prop prevents an NPE
				split.splitConditions(nodes);
				JavaFragment[] javaFrags = new JavaFragment[nodes.size()];
				cond.setJavaBodies(javaFrags);
				cond.setRootNodes(null);
				
				for(int ii = 0; ii < nodes.size(); ii++) {
					String javaBody = rootNodeToJava(nodes.get(ii), ri, riRootNode, ontology, rlst, propInfoCache);
					javaFrags[ii] = javaFragMap.get(javaBody);
					if(javaFrags[ii] == null) {
						javaFrags[ii] = new JavaFragment(javaBody, id++);
						javaFragMap.put(javaBody, javaFrags[ii]);
						conds.add(javaFrags[ii]);
					}
				}
			}
		}
	}
	
	private static String rootNodeToJava(RootNode node, RuleInfo ri, List<RootNode> riRootNode, Ontology ontology
			, RLSymbolTable rlst, Map<String, Map<String, int[]>> propInfoCache)
	{
		riRootNode.clear();
		riRootNode.add(node);
		return RuleClassGeneratorSmap.getActionBodyText(ri, rlst, null, ontology, propInfoCache).toString().trim();
	}
}
