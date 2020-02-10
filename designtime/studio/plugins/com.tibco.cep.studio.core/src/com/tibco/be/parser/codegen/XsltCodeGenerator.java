package com.tibco.be.parser.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.mapper.codegen.IVariableType;
import com.tibco.cep.mapper.codegen.IVariableTypeResolver;
import com.tibco.cep.mapper.codegen.UnsupportedXsltMappingException;
import com.tibco.cep.mapper.codegen.VariableType;
import com.tibco.cep.mapper.codegen.XsltCodegenContext;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;

public class XsltCodeGenerator extends CommonXsltCodeGenerator implements IVariableTypeResolver {

    private static int mapperFnSuffixCtr = 0;
    protected LineBuffer currentLinebuffer;
    protected RLSymbolTable symbolTable;
    private IVariableTypeResolver resolver;

    public XsltCodeGenerator(Ontology ontology, RLSymbolTable symbolTable) {
    	super(ontology, null);
    	this.typeResolver = this;
    	this.symbolTable = symbolTable;
    }

	protected Object transformXSLTToJava(LineBuffer buffer, FunctionRec fr, FunctionNode node, Node oldPrevNode, boolean isRuleFunction, 
			JavaClassWriter currClass, Symbols identifierMap, HashSet additionalDependentScorecards) {
		
		String mapperFnName = "_mapperCreateInstance"+mapperFnSuffixCtr++;
		writeCreateInstanceMethodBody(node, mapperFnName, null, true); // validate that we can process this mapping

		this.currentLinebuffer = new LineBuffer();
		currentLinebuffer.append(mapperFnName);
        currentLinebuffer.append("(");

        MethodRecWriter mr = null;
        if (isRuleFunction) {
        	mr = currClass.createMethod("private static", fr.function.getReturnClass().getName(), mapperFnName);
        } else {
        	mr = currClass.createMethod("private", fr.function.getReturnClass().getName(), mapperFnName);
        }
        appendFunctionArgs(this.symbolTable.visibleIds(), mr, identifierMap, additionalDependentScorecards);
        LineBuffer oldBuf = currentLinebuffer;
        writeCreateInstanceMethodBody(node, mapperFnName, mr, false);
        currentLinebuffer = oldBuf;
        currentLinebuffer.append(")");
        buffer.append(currentLinebuffer);
        return null;
	}

	private void writeCreateInstanceMethodBody(FunctionNode node, String mapperFnName, MethodRecWriter mr, boolean validate) {
		String xmlStringLiteral = ((ProductionNode)node.getChild(0)).getToken().image;
		String xmlString = xmlStringLiteral.substring(1, xmlStringLiteral.length() -1);
		if (validate) {
	        if (xmlString.startsWith("xslt")) {
	    		TemplateBinding binding = CommonMapperCoreUtils.getBinding(xmlString, new ArrayList());
	    		ArrayList receivingParms = XSTemplateSerializer.getReceivingParms(xmlString);
	    		String entityPath = (String) receivingParms.get(0);
	    		Entity entity = ontology.getEntity(entityPath);
	    		
	    		String tempVarName = "$temp";
	    		// first validate that we can process this binding
	    		processBindings(binding, receivingParms, entity, tempVarName, true, null, false);
	        }
	        return;
		}
        currentLinebuffer = mr.getLineBuffer();
        node.setBeginJavaLine(currentLinebuffer.getJavaLine());
        
        if (xmlString.startsWith("xslt")) {
        	StringBuilder builder = new StringBuilder();
        	try {
        		processXsltString(xmlString, new XsltCodegenContext(true, true, false), builder);
			} catch (UnsupportedXsltMappingException e) {
				currentLinebuffer.append("return null;"); // return null to avoid compilation error, even though this method will not be used
				throw e;
			}
        	currentLinebuffer.append(builder.toString());
        }
	}

    private void appendFunctionArgs(Iterator ids, MethodRecWriter mr, Symbols identifierMap, HashSet additionalDependentScorecards) {
    	for(int ii = 0; ids.hasNext(); ii++) {
    		String id = (String)ids.next();
    		String modelPathName = identifierMap.getType(id);
    		NodeType type = symbolTable.getDeclaredIdentifierType(id);
    		if(!(modelPathName == null && additionalDependentScorecards.contains(id))) {
    			String typeClassName;
    			if(type != null && (type.isGeneric() || type.isPrimitiveType())) {
    				typeClassName = CGUtil.genericJavaTypeName(type, true, type.isArray(), true);
    			} else {
    				if (modelPathName == null && type.isEntity()) {
    					modelPathName = type.getName();
    				}
    				typeClassName = ModelNameUtil.modelPathToGeneratedClassName(modelPathName);
    				if (type != null && type.isArray()) {
    					typeClassName += "[]";
    				}
    			}
    			String argText = ModelNameUtil.generatedScopeVariableName(id); 
    			mr.addArg(typeClassName, argText);
//    			currBody.append(argText);
    			currentLinebuffer.append(argText);
    			if (ids.hasNext()) {
//    				currBody.append(", ");
    				currentLinebuffer.append(", ");
    			}
    		}
    	}
    }

	@Override
	public IVariableType getDeclaredIdentifierType(String variable) {
		NodeType type = symbolTable.getDeclaredIdentifierType(variable);
		return new VariableType(type.getName(), type.isConcept(), type.isEvent(), type.isTimeEvent(), type.isProcess(), type.isArray());
	}
    
}
