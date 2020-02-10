package com.tibco.be.parser.codegen;

import static com.tibco.be.parser.codegen.CoreCGConstants.boxUnboxed;
import static com.tibco.be.parser.codegen.CoreCGConstants.unboxBoxed;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.ModelRuleFunction;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleCompiler;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.semantic.CompilableDeclSymbolTable;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.TypeNameUtil;
import com.tibco.be.parser.tree.DeclarationNode;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.TemplatedDeclarationNode;
import com.tibco.be.parser.tree.TemplatedProductionNode;
import com.tibco.be.parser.tree.TypeNode;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;
import com.tibco.cep.runtime.service.loader.NeedsStaticInitialization;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.util.CodegenFunctions;
import com.tibco.cep.util.UnimplementedVirtualRuleFunctionException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Feb 4, 2005
 * Time: 6:05:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleFunctionCodeGeneratorSmap {
    private static final String RULE_FN_INTERFACE = com.tibco.cep.kernel.model.rule.RuleFunction.class.getName();
    private static final String OVERSIZE_ARGS_ARRAY_NAME = "$args";

    /**
     * @param fn
     * @param errorList
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @return
     */
    public static JavaClassWriter generateRuleFunctionClass(RuleFunction fn, List errorList, Properties oversizeStringConstants, Map ruleFnUsages
    		, Ontology o, Map<String, Map<String, int[]>> propInfoCache) {
        return generateRuleFunctionClass(fn, errorList, oversizeStringConstants, ruleFnUsages, false, o, propInfoCache);
    }
    /**
     * @param fn
     * @param errorList
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @param ignoreVirtual
     * @param rinfo
     * @return
     */
    public static JavaClassWriter generateRuleFunctionClassNew(RuleFunction fn, 
    															List errorList, 
    															Properties oversizeStringConstants,
                                                                Map ruleFnUsages, 
                                                                boolean ignoreVirtual, 
                                                                RuleInfo rinfo,
                                                                Ontology o,
                                                                Map<String, Map<String, int[]>> propInfoCache)  {
        JavaClassWriter jClass = new JavaClassWriter(ModelNameUtil.ruleFnClassShortName(fn),null);

        jClass.setAccess("public");

        MethodRecWriter constructor = jClass.createMethod(ModelNameUtil.ruleFnClassShortName(fn));
        constructor.setAccess("public");
        constructor.setReturnType("");
        StringBuilder body = new StringBuilder();
        body.append("");
        constructor.setBody(body);

        MethodRecWriter mr = RuleFunctionCodeGeneratorSmap.generateStaticMethodFromRuleFnNew(jClass, fn, errorList, oversizeStringConstants, ruleFnUsages,
                ModelNameUtil.endsWithOversizeRuleFnClassNameSuffix(jClass.getName()), ignoreVirtual, false, rinfo, o, propInfoCache);
        jClass.addInterface("com.tibco.cep.runtime.service.debug.SmartStepInto");

        String userURI = fn.getFullPath();
        String userName = ModelNameUtil.removeGeneratedPackagePrefix(ModelNameUtil.modelPathToExternalForm(userURI));
        jClass.addAnnotation(RuleFunctionUserNameAnnotation.class 
        		, RuleFunctionUserNameAnnotation.userName, userName
        		, RuleFunctionUserNameAnnotation.userURI, userURI);

        return jClass;        
    }

     /**
     * @param fn
     * @param errorList
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @param ignoreVirtual
     * @return
     */
    public static JavaClassWriter generateRuleFunctionClass(RuleFunction fn, List errorList, Properties oversizeStringConstants,
                                                      Map ruleFnUsages, boolean ignoreVirtual, Ontology o, Map<String, Map<String, int[]>> propInfoCache)
    {
        JavaClassWriter jClass = new JavaClassWriter(ModelNameUtil.ruleFnClassShortName(fn),null);

        jClass.setAccess("public");

        MethodRec mr = RuleFunctionCodeGeneratorSmap.generateStaticMethodFromRuleFn(jClass,fn, errorList, oversizeStringConstants, ruleFnUsages,
                ModelNameUtil.endsWithOversizeRuleFnClassNameSuffix(jClass.getName()), ignoreVirtual, false, o, propInfoCache);
        //mr will be null if there were errors during parsing or generation
//        if(mr != null) jClass.addMethod(mr);

        String userURI = fn.getFullPath();
        String userName = ModelNameUtil.removeGeneratedPackagePrefix(ModelNameUtil.modelPathToExternalForm(userURI));
        
        jClass.addAnnotation(RuleFunctionUserNameAnnotation.class 
        		, RuleFunctionUserNameAnnotation.userName, userName
        		, RuleFunctionUserNameAnnotation.userURI, userURI);

        return jClass;
    }

    /**
     * @param fn
     * @param errorList
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @return
     */
    public static JavaClassWriter generateRuleFunctionWrapperClass(RuleFunction fn, List errorList, Properties oversizeStringConstants, Map ruleFnUsages, Ontology o) {
        JavaClassWriter jClass = new JavaClassWriter(fn.getName(),null);

        jClass.setAccess("public");
        jClass.addInterface(RULE_FN_INTERFACE);

        MethodRecWriter constructor = jClass.createMethod(fn.getName());
        constructor.setAccess("public");
        constructor.setReturnType("");
        StringBuilder body = new StringBuilder();
        String className = ModelNameUtil.ruleFnClassFSName(fn);
        body.append("new ").append(className).append("();");
        constructor.setBody(body);

        MethodRecWriter mr = generateInvokeArrayMethod(jClass, fn, o);
//        jClass.addMethod(mr);

        mr = generateInvokeMapMethod(jClass, fn, o);
//        jClass.addMethod(mr);

        mr = generateSignatureMethod(jClass, fn, o);
//        jClass.addMethod(mr);

        addParameterDescriptors(fn, jClass, o);

        return jClass;

    }

    private enum ReturnType {VOID, PRIMIIVE, REFERENCE};
    /**
     * @param fn
     * @param body
     * @return
     */
    private static ReturnType appendReturnPrefix(RuleFunction fn, Appendable body, Ontology o) {
        boolean returnPrimitive = false;
        String returnType = fn.getReturnType();
        NodeType nodeType = null;
        if(returnType != null) {
        	int numBrackets = 0;
        	if (returnType.endsWith("[]")) {
        		numBrackets = 1;
        		returnType = returnType.substring(0, returnType.length()-2); 
        	}
            nodeType =TypeNameUtil.typeNameToNodeType(returnType, o, false, false, numBrackets);
            if(!nodeType.isVoid()) {
                if(nodeType.isValueType()) {
                    returnPrimitive = true;
                    try {
                        body.append("return " + boxUnboxed + "( ");
                    } catch(IOException ioe) {}
                } else {
                    try {
                        body.append("return ");
                    } catch(IOException ioe) {}
                }
            }
        }
        if(returnPrimitive) return ReturnType.PRIMIIVE;
        if(nodeType == null || nodeType.isVoid()) return ReturnType.VOID;
        return ReturnType.REFERENCE;
    }

        private static MethodRecWriter generateInvokeMapMethod(JavaClassWriter jClass, RuleFunction fn, Ontology o) {
        //generate the invoke(Object[])
        MethodRecWriter mr = jClass.createMethod("public", "java.lang.Object", "invoke");
        mr.addArg("java.util.Map", "input");
        StringBuilder body = new StringBuilder();

        ReturnType returnType = appendReturnPrefix(fn, body, o);
        String className = ModelNameUtil.ruleFnClassFSName(fn);
        boolean hasOversizeClassName = ModelNameUtil.endsWithOversizeRuleFnClassNameSuffix(className);

        body.append(className + "." + fn.getName() + "(");
        boolean prependComma = false;
        //pass calledFromCondition argumnet
        if ( (fn.getValidity() != RuleFunction.Validity.ACTION)
                && Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT)) {
            body.append("false");
            prependComma = true;
        }
        if(hasOversizeClassName) {
            if(prependComma) body.append(", ");
            prependComma = false;
            body.append("new java.lang.Object[]{");

        }

        int numArgs = fn.getArguments().getSymbolsList().size();
        if(numArgs > 0 && prependComma) {
            body.append(", ");
            prependComma = false;
        }

        for(int i = 0; i < numArgs; i++) {
            Symbol sym  = (Symbol) fn.getArguments().getSymbolsList().get(i);
            String type = sym.getType();
            String name = sym.getName();
            int numBrackets = sym.isArray() ? 1 : 0;
            NodeType argNodeType = TypeNameUtil.typeNameToNodeType(type, o, false, false, numBrackets);
            if(i != 0)
                body.append(", ");

            String argType = getArgumentTypeName(argNodeType);
            if(hasOversizeClassName) {
                body.append("input.get(\"" + name + "\")");

            } else if(argType.equals(CGUtil.convertNonboxedToBoxed(argType))) {
                body.append("(" + argType +")input.get(\"" + name + "\")");
            }
            else {
                body.append(unboxBoxed + "((" +CGUtil.convertNonboxedToBoxed(argType)+ ")input.get(\"" + name + "\"))");
            }
        }
        if(hasOversizeClassName) {
            body.append("}");
        }
        if(ReturnType.VOID.equals(returnType))
            body.append(");" + CGConstants.BRK + "return null;");
        else {
            if(ReturnType.PRIMIIVE.equals(returnType))
                body.append(") );");
            else
                body.append(");");
        }
        mr.setBody(body);
        return mr;
    }

    private static MethodRecWriter generateInvokeArrayMethod(JavaClassWriter jClass, RuleFunction fn, Ontology o) {
        MethodRecWriter mr =jClass.createMethod("public", "java.lang.Object", "invoke");
        mr.addArg("java.lang.Object[]", "args");
        StringBuilder body = new StringBuilder();

        ReturnType returnType = appendReturnPrefix(fn, body, o);
        String className = ModelNameUtil.ruleFnClassFSName(fn);
        boolean hasOversizeClassName = ModelNameUtil.endsWithOversizeRuleFnClassNameSuffix(className);
        body.append(className + "." + fn.getName() + "(");
        boolean prependComma = false;
        //pass calledFromCondition argumnet
        if ( (fn.getValidity() != RuleFunction.Validity.ACTION)
                && Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT)) {
            body.append("false");
            prependComma = true;
        }
        if(hasOversizeClassName) {
            if(prependComma) {
                body.append(", ");
                prependComma = false;
            }
            body.append("args");
        } else {
            int numArgs = fn.getArguments().getSymbolsList().size();
            if(prependComma && numArgs > 0) {
                body.append(", ");
                prependComma = false;
            }
            for(int i = 0; i < numArgs; i++) {
                Symbol sym  = (Symbol) fn.getArguments().getSymbolsList().get(i);
                String type = sym.getType();
                String name = sym.getName();
                int numBrackets = sym.isArray() ? 1 : 0;
                NodeType argNodeType = TypeNameUtil.typeNameToNodeType(type, o, false, false, numBrackets);
                if(i != 0)
                    body.append(", ");

                String argType = getArgumentTypeName(argNodeType);
               if(argType.equals(CGUtil.convertNonboxedToBoxed(argType))) {
                    body.append("(" + argType +")args[" + i + "]");
                }
                else {
                    body.append(unboxBoxed + "((" + CGUtil.convertNonboxedToBoxed(argType)+ ")args[" + i + "])");
                }
            }
        }
        if(ReturnType.VOID.equals(returnType))
            body.append(");" + CGConstants.BRK + "return null;");
        else {
            if(ReturnType.PRIMIIVE.equals(returnType))
                body.append(") );");
            else
                body.append(");");
        }
        mr.setBody(body);
        return mr;
    }

    private static MethodRecWriter generateSignatureMethod(JavaClassWriter jClass, RuleFunction fn, Ontology o) {
        MethodRecWriter mr = jClass.createMethod("public", "java.lang.String", "getSignature");
        StringBuilder body = new StringBuilder();

        boolean returnPrimitive = false;
        String returnType = fn.getReturnType();
        NodeType nodeType = null;
        if(returnType != null) {
        	int numBrackets = 0;
        	if (returnType.endsWith("[]")) {
        		numBrackets = 1;
        		returnType = returnType.substring(0, returnType.length()-2); 
        	}
            nodeType = TypeNameUtil.typeNameToNodeType(returnType, o, false, false, numBrackets);
            if(!nodeType.isVoid()) {
                body.append("return \"" + getArgumentTypeName(nodeType) + " ");
            }
            else {
                body.append("return \"void ");
            }
        }
        else {
            body.append("return \"void ");
        }

        String functionName = fn.getFullPath().substring(1);

        body.append(functionName.replace('/', '.') + "(");

        int numArgs = fn.getArguments().getSymbolsList().size();
        for(int i = 0; i < numArgs; i++) {
            Symbol sym  = (Symbol) fn.getArguments().getSymbolsList().get(i);
            String type = sym.getType();
            String name = sym.getName();
            int numBrackets = sym.isArray() ? 1 : 0;
            NodeType argNodeType = TypeNameUtil.typeNameToNodeType(type, o, false, false, numBrackets);
            if(i != 0)
                body.append(", ");

            String argType = getArgumentTypeName(argNodeType);
            body.append(argType + " " + name);
        }
        body.append(")\";");
        mr.setBody(body);
        return mr;
    }

    private static void addParameterDescriptors(RuleFunction fn, JavaClassWriter jclass, Ontology o) {
        final String parameterDescriptorsMemberName = "parameterDescriptors";
        String arrTypeName = com.tibco.cep.kernel.model.rule.RuleFunction.class.getName() + ".ParameterDescriptor[]";
        MethodRecWriter mr = jclass.createMethod("public", arrTypeName, "getParameterDescriptors");
        mr.setBody("return " + parameterDescriptorsMemberName + ";");

        String classLoader = jclass.getName() + ".class.getClassLoader()";
        StringBuilder initializer = new StringBuilder();

        initializer.append(parameterDescriptorsMemberName + " = new " + arrTypeName + "{");

        int numArgs = fn.getArguments().getSymbolsList().size();
        for(int i = 0; i < numArgs; i++) {
            Symbol sym  = (Symbol) fn.getArguments().getSymbolsList().get(i);
            String type = sym.getType();
            String name = sym.getName();
            int numBrackets = sym.isArray() ? 1 : 0;
            NodeType argNodeType = TypeNameUtil.typeNameToNodeType(type, o, false, false, numBrackets);
            if(i != 0) initializer.append(", ");
            initializer.append("new " + com.tibco.cep.kernel.model.rule.impl.ParameterDescriptorImpl.class.getName() + "(\"" + name + "\", " + makeClassExpression(classLoader, argNodeType) + ", true)");
        }
        if(numArgs != 0)
            initializer.append(", ");
        
        String returnType = fn.getReturnType();
        NodeType nodeType;
        if(returnType == null) {
        	nodeType = NodeType.VOID;
        } else {
        	int numBrackets = 0;
        	if (returnType.endsWith("[]")) {
        		numBrackets = 1;
        		returnType = returnType.substring(0, returnType.length()-2); 
        	}
        	nodeType = TypeNameUtil.typeNameToNodeType(returnType, o, false, false, numBrackets);
        }
        if(!nodeType.isVoid()) {
            initializer.append("new " + com.tibco.cep.kernel.model.rule.impl.ParameterDescriptorImpl.class.getName() + "(\"returnValue\", " + makeClassExpression(classLoader, nodeType) + ", false)");
        } else {
            initializer.append("new " + com.tibco.cep.kernel.model.rule.impl.ParameterDescriptorImpl.class.getName() + "(\"returnValue\", void.class, false)");
        }

        initializer.append("};");
        
        mr = jclass.createMethod("public static", "void", NeedsStaticInitialization.INIT_METHOD_NAME);
        mr.addArg(RuleServiceProvider.class.getName(), "rsp");
		mr.setBody(initializer);
                
        jclass.addMember("private static", arrTypeName, parameterDescriptorsMemberName);      
        jclass.addInterface(NeedsStaticInitialization.class.getName());
    }

    
    /**
     * @param jClassWriter
     * @param fn rule function with which to compile / error check / generate code
     * @param errorList RuleErrors will be added to this list as errors arise
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @param hasOversizeClassName
     * @param ignoreVirtual
     * @param isDTRF
     * @param rinfo
     * @return  null if errors were produced, a MethodRec for this function otherwise
     */
    protected static MethodRecWriter generateStaticMethodFromRuleFnNew(JavaClassWriter jClassWriter, 
    																	RuleFunction fn, 
    																	List errorList, 
    																	Properties oversizeStringConstants,
                                                                        Map ruleFnUsages, 
                                                                        boolean hasOversizeClassName, 
                                                                        boolean ignoreVirtual, 
                                                                        boolean isDTRF, 
                                                                        RuleInfo rinfo,
                                                                        Ontology o,
                                                                        Map<String, Map<String, int[]>> propInfoCache)
    {
        CompilableDeclSymbolTable symbolTable = new CompilableDeclSymbolTable(fn, o);
        MethodRecWriter mr = jClassWriter.createMethod(fn.getName(),null);
//        MethodRec mr = new MethodRec(fn.getName());
//        RuleInfo rinfo = new RuleInfo();

        mr.setAccess("public static");
        //add calledFromCondition argument to non-action-only rule function
        if ((fn.getValidity() != RuleFunction.Validity.ACTION)
                && Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT)) mr.addArg("boolean", CGConstants.calledFromCondition);
        for(Iterator entries = fn.getScope().entrySet().iterator(); entries.hasNext();) {
            Map.Entry entry = (Map.Entry)entries.next();
            String id = (String)entry.getKey();
            NodeType type = symbolTable.getDeclaredIdentifierType(id);
            rinfo.addDeclaration(type.getName(), id);

            //add argument list to mr
            if(!hasOversizeClassName)mr.addArg(getArgumentTypeName(type), ModelNameUtil.generatedScopeVariableName(id));
        }
        if(hasOversizeClassName) mr.addArg("java.lang.Object[]", OVERSIZE_ARGS_ARRAY_NAME);

        NodeType returnType = symbolTable.getReturnType();
        if(returnType == null) returnType = NodeType.VOID;
        if(hasOversizeClassName && !returnType.isVoid() && !returnType.isBoolean() && !returnType.isDouble() && !returnType.isInt() && !returnType.isLong()){
            mr.setReturnType("java.lang.Object");
        } else {
            mr.setReturnType(getArgumentTypeName(returnType));
        }

        if(fn.isVirtual() && !ignoreVirtual) {
            mr.setBody(makeVRFBody(fn, hasOversizeClassName, o));
            return mr;
        }

        List parseTrees = RuleCompiler.parseAndTypeCheckRuleFunction(fn, errorList, isDTRF, o);
        if(parseTrees == null) return null;

        //rinfo.setName("name_is_arbitrary");
        rinfo.setThenTrees(parseTrees);
        rinfo.setRuleBlockBuffer(RuleFunctionLineBuffer.fromRuleFunction(fn));
        LineBuffer body = mr.getLineBuffer();
        if(hasOversizeClassName) appendLocalVarsNew(fn.getScope().entrySet().iterator(), symbolTable, body);
        RuleClassGeneratorSmap.getActionBodyText(jClassWriter, body, rinfo, symbolTable, oversizeStringConstants, o, propInfoCache);

        //rule function usage only needed for functions usable in a condition
        if( (fn.getValidity() != RuleFunction.Validity.ACTION)
                && (ruleFnUsages != null)) {
            //if(ruleFnUsages == null) {
            //    throw new RuntimeException("ruleFnUsages was null with non-action-only rule function");
            //}

            RuleFunctionUsageVisitor visitor = new RuleFunctionUsageVisitor();
            for(Iterator it = parseTrees.iterator(); it.hasNext();) {
                ((Node)it.next()).accept(visitor);
            }
            ruleFnUsages.put(ModelNameUtil.ruleFnClassFSName(fn), visitor.usages);
        }

        return mr;
    }

    
    /**
     * @param cc
     * @param fn rule function with which to compile / error check / generate code
     * @param errorList RuleErrors will be added to this list as errors arise
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @param hasOversizeClassName
     * @param ignoreVirtual
     * @param isDTRF
     * @return  null if errors were produced, a MethodRec for this function otherwise
     */
    protected static MethodRecWriter generateStaticMethodFromRuleFn(JavaClassWriter cc,
    																RuleFunction fn, 
    																List errorList, 
    																Properties oversizeStringConstants,
    																Map ruleFnUsages, 
    																boolean hasOversizeClassName, 
    																boolean ignoreVirtual, 
    																boolean isDTRF,
    																Ontology o,
    																Map<String, Map<String, int[]>> propInfoCache)
    {
        CompilableDeclSymbolTable symbolTable = new CompilableDeclSymbolTable(fn, o);
        MethodRecWriter mr = cc.createMethod(fn.getName());
        RuleInfo rinfo = new RuleInfo();

        mr.setAccess("public static");
        //add calledFromCondition argument to non-action-only rule function
        if ((fn.getValidity() != RuleFunction.Validity.ACTION)
                && Boolean.getBoolean(CGConstants.GENERATE_NULL_CONTAINED_CONCEPT)) mr.addArg("boolean", CGConstants.calledFromCondition);
        for(Iterator entries = fn.getScope().entrySet().iterator(); entries.hasNext();) {
            Map.Entry entry = (Map.Entry)entries.next();
            String id = (String)entry.getKey();
            NodeType type = symbolTable.getDeclaredIdentifierType(id);
            rinfo.addDeclaration(type.getName(), id);

            //add argument list to mr
            if(!hasOversizeClassName)mr.addArg(getArgumentTypeName(type), ModelNameUtil.generatedScopeVariableName(id));
        }
        if (hasOversizeClassName) mr.addArg("java.lang.Object[]", OVERSIZE_ARGS_ARRAY_NAME);

        NodeType returnType = symbolTable.getReturnType();
        if(returnType == null) returnType = NodeType.VOID;
        if (hasOversizeClassName && !returnType.isVoid() && !returnType.isBoolean() && !returnType.isDouble() && !returnType.isInt() && !returnType.isLong()){
            mr.setReturnType("java.lang.Object");
        } else {
            mr.setReturnType(getArgumentTypeName(returnType));
        }

        if(fn.isVirtual() && !ignoreVirtual) {
            mr.setBody(makeVRFBody(fn, hasOversizeClassName, o));
            return mr;
        }

        List parseTrees = RuleCompiler.parseAndTypeCheckRuleFunction(fn, errorList, isDTRF, o);
        if(parseTrees == null) return null;

        //rinfo.setName("name_is_arbitrary");
        rinfo.setThenTrees(parseTrees);

        StringBuilder body = new StringBuilder();
        if(hasOversizeClassName) appendLocalVars(fn.getScope().entrySet().iterator(), symbolTable, body);
        body.append(RuleClassGeneratorSmap.getActionBodyText(rinfo, symbolTable, oversizeStringConstants, o, propInfoCache));
        mr.setBody(body);

        //rule function usage only needed for functions usable in a condition
        if( (fn.getValidity() != RuleFunction.Validity.ACTION)
                && (ruleFnUsages != null)) {
            //if(ruleFnUsages == null) {
            //    throw new RuntimeException("ruleFnUsages was null with non-action-only rule function");
            //}

            RuleFunctionUsageVisitor visitor = new RuleFunctionUsageVisitor();
            for(Iterator it = parseTrees.iterator(); it.hasNext();) {
                ((Node)it.next()).accept(visitor);
            }
            ruleFnUsages.put(ModelNameUtil.ruleFnClassFSName(fn), visitor.usages);
        }

        return mr;
    }
    
    /**
     * @param stateClass
     * @param fn
     * @param errorList
     * @param oversizeStringConstants
     * @return
     */
    public static MethodRecWriter generateStaticMethodFromRuleFn(JavaClassWriter stateClass, 
    															RuleFunction fn, 
    															List errorList, 
    															Properties oversizeStringConstants,
    															Ontology o, 
    															Map<String, Map<String, int[]>> propInfoCache) {
        return generateStaticMethodFromRuleFn(stateClass,fn, errorList, oversizeStringConstants, null, o, propInfoCache);
    }
    
    /**
     * @param cc
     * @param fn
     * @param errorList
     * @param oversizeStringConstants
     * @param ruleFnUsages
     * @return
     */
    public static MethodRecWriter generateStaticMethodFromRuleFn(JavaClassWriter cc,
    															RuleFunction fn, 
    															List errorList, 
    															Properties oversizeStringConstants, 
    															Map ruleFnUsages,
    															Ontology o,
    															Map<String, Map<String, int[]>> propInfoCache) {
        return generateStaticMethodFromRuleFn(cc,fn, errorList, oversizeStringConstants, ruleFnUsages, false, false, false, o, propInfoCache);
    }

    /**
     * @param fn
     * @param hasOversizeClassName
     * @return
     */
    public static String makeVRFBody(RuleFunction fn, boolean hasOversizeClassName, Ontology o) {
        StringBuilder sb = new StringBuilder();
        sb.append(VRFImpl.class.getCanonicalName());
        sb.append(" impl = ");
        sb.append(CodegenFunctions.class.getName() + ".getVRFDefaultImpl(");
        sb.append('"' + fn.getFullPath() + '"');
        sb.append(");\n");
        sb.append("if(impl == null) throw new ");
        sb.append(UnimplementedVirtualRuleFunctionException.class.getName());
        sb.append("(\"" + fn.getFullPath() + "\");\n");

        ReturnType returnType = appendReturnPrefix(fn, sb, o);
        sb.append(CodegenFunctions.class.getName() + ".invokeVRFImpl(impl, ");
        if(hasOversizeClassName) {
            sb.append(OVERSIZE_ARGS_ARRAY_NAME);
        } else {
            sb.append("new java.lang.Object[]{");
            Iterator it = fn.getScope().keySet().iterator();
            //VRFs should be allowed to have no args as well.
            while (it.hasNext()) {
                String argName = (String)it.next();
                //java 5 auto-boxing should take care of any primitive arguments
                sb.append(ModelNameUtil.generatedScopeVariableName(argName));
                if(it.hasNext()) sb.append(", ");
                else break;
            }
            sb.append("}");
        }
        //close paren needed due to box method call in appendReturnPrefix
        if(ReturnType.PRIMIIVE.equals(returnType)) {
            sb.append(")");
        }
        sb.append(");\n");
        return sb.toString();
    }

    /**
     * @param scope
     * @param symbolTable
     * @param body
     */
    public static void appendLocalVarsNew(Iterator scope, CompilableDeclSymbolTable symbolTable, LineBuffer body) {
        for(int ii = 0; scope.hasNext(); ii++) {
            Map.Entry entry = (Map.Entry)scope.next();
            String id = (String)entry.getKey();
            NodeType type = symbolTable.getDeclaredIdentifierType(id);
            if(type == null) continue;
            String generatedId = ModelNameUtil.generatedScopeVariableName(id);
            String typeName = getArgumentTypeName(type);
            String boxedName = CGUtil.convertNonboxedToBoxed(typeName);
            if(!typeName.equals(boxedName)) {
                body.append(typeName + " " + generatedId + " = " + unboxBoxed + "((" + boxedName + ")"
                        + OVERSIZE_ARGS_ARRAY_NAME +"[" + ii + "]);" + CGConstants.BRK);
            } else {
                //after the above tests, isPrimitiveType will only be true for object based primitives like String
                //if(type.isGeneric() || type.isPrimitiveType()) typeName = CGUtil.genericJavaTypeName(type, true, false, true);
                //else typeName = generatedClassName(type.getName());
                body.append(typeName + " " + generatedId + " = (" + typeName + ")" + OVERSIZE_ARGS_ARRAY_NAME + "[" + ii + "];" + CGConstants.BRK);
            }
        }
    }

    /**
     * @param scope
     * @param symbolTable
     * @param body
     */
    public static void appendLocalVars(Iterator scope, CompilableDeclSymbolTable symbolTable, StringBuilder body) {
        for(int ii = 0; scope.hasNext(); ii++) {
            Map.Entry entry = (Map.Entry)scope.next();
            String id = (String)entry.getKey();
            NodeType type = symbolTable.getDeclaredIdentifierType(id);
            if(type == null) continue;
            String generatedId = ModelNameUtil.generatedScopeVariableName(id);
            String typeName = getArgumentTypeName(type);
            String boxedName = CGUtil.convertNonboxedToBoxed(typeName);
            if(!typeName.equals(boxedName)) {
                body.append(typeName + " " + generatedId + " = " + unboxBoxed + "((" + boxedName + ")"
                        + OVERSIZE_ARGS_ARRAY_NAME +"[" + ii + "]);" + CGConstants.BRK);
            } else {
                //after the above tests, isPrimitiveType will only be true for object based primitives like String
                //if(type.isGeneric() || type.isPrimitiveType()) typeName = CGUtil.genericJavaTypeName(type, true, false, true);
                //else typeName = generatedClassName(type.getName());
                body.append(typeName + " " + generatedId + " = (" + typeName + ")" + OVERSIZE_ARGS_ARRAY_NAME + "[" + ii + "];" + CGConstants.BRK);
            }
        }
    }



    /**
     * @param type
     * @return
     */
    private static String getArgumentTypeName(NodeType type) {
        return getArgumentTypeName(type, true);
    }
    /**
     * @param type
     * @param appendArraySuffix
     * @return
     */
    private static String getArgumentTypeName(NodeType type, boolean appendArraySuffix) {
        String argTypeName = null;
        if(type.hasPropertyContext()) {
            assert(false);
            argTypeName = CGUtil.genericJavaTypeName(type, false, false, true);
        } else {
            if(type.isEntity() && !type.isGeneric()) {
                if(type.isArray()) {
                    argTypeName = generatedClassName(type.getComponentType(false));
                    if (appendArraySuffix) {
                    	argTypeName += "[]";
                    }
                } else {
                    argTypeName = generatedClassName(type);
                }
            } else {
                argTypeName = CGUtil.genericJavaTypeName(type, true, appendArraySuffix, false);
            }
        }
        return argTypeName;
    }

    /**
     * @param classLoader
     * @param typ
     * @return
     */
    private static String makeClassExpression(String classLoader, NodeType typ) {
        String typeName = getArgumentTypeName(typ, false);
        if(CGUtil.isJavaPrimitive(typ)) {
            if(!typ.isArray()) {
                return typeName + ".class";
            } else {
                if(typ.isInt()) typeName = "[I";
                else if (typ.isLong()) typeName = "[J";
                else if(typ.isDouble()) typeName = "[D";
                else if(typ.isBoolean()) typeName = "[Z";
            }
        } else {
            if(typ.isArray()) {
                typeName = "[L" + typeName + ";";
            }
        }
        return CodegenFunctions.class.getName() + ".classForName(" + classLoader + ",\"" + typeName + "\")";
    }

    //finds all the other rule functions this rule function uses
    private static class RuleFunctionUsageVisitor implements NodeVisitor {
        public HashSet usages = new HashSet();

        private void visitChildren(Node n) {
            for(Iterator it = n.getChildren(); it.hasNext();) {
                Node child = ((Node)it.next());
                if(child != null) child.accept(this);
            }
        }

        public Object visitFunctionNode(FunctionNode node) {
            FunctionRec fr = node.getFunctionRec();
            if(fr != null) {
                Predicate p = fr.function;
                if(p != null) {
                    if(p instanceof ModelRuleFunction) {
                        ModelRuleFunction rf = (ModelRuleFunction)p;
                        //the full name of the class that implements this rule function at run-time
                        usages.add(rf.getModelClass());
                    }
                }
            }
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object visitProductionNode(ProductionNode node) {
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object visitTemplatedProductionNode(TemplatedProductionNode node) {
            visitChildren(node);
            return null;
        }

        public Object visitProductionNodeListNode(ProductionNodeListNode node) {
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object visitDeclarationNode(DeclarationNode node) {
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object visitRootNode(RootNode node) {
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object visitNameNode(NameNode node) {
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object visitTypeNode(TypeNode node) {
            visitChildren(node);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }


        @Override
        public Object visitTemplatedDeclarationNode(TemplatedDeclarationNode node) {
            visitChildren(node);
            return null;
        }

    }
    
    public static String getArgumentTypeName(Symbol arg, Ontology ontology) {
    	int numBrackets = arg.isArray() ? 1 : 0;
        NodeType argNodeType = TypeNameUtil.typeNameToNodeType(arg.getType(), ontology, false, false, numBrackets);
        return getArgumentTypeName(argNodeType);
    }
    
    protected static String generatedClassName(NodeType type) {
    	if(type.isProcess()) return CGUtil.genericJavaTypeName(type, false, true, false);
    	return ModelNameUtil.modelPathToGeneratedClassName(type.getName());
    }
}