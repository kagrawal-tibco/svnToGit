package com.tibco.cep.mapper.codegen;

import static com.tibco.be.parser.codegen.CGConstants.BRK;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.mapper.xml.xdata.bind.AttributeBinding;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo.NamespaceDeclaration;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ForEachGroupBinding;
import com.tibco.cep.mapper.xml.xdata.bind.IfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.OtherwiseBinding;
import com.tibco.cep.mapper.xml.xdata.bind.SetVariableBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.ValueOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.WhenBinding;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

public class CommonXsltCodeGenerator {

	private class ForLoopInfo {
		
		private String propertyPrimitiveType;
		private String propertyArrayType;
		private String propertyArrayName;
		private String localVarName; // the local variable name inside the 'for' loop
		private String localVarType;
		private boolean xiNodeLoop = false; // whether we're iterating over XiNodes (i.e. payload nodes)
		private String loopCounterVarName;
		
		public String getLoopCounterVarName() {
			return loopCounterVarName;
		}

		public void setLoopCounterVarName(String loopCounterVarName) {
			this.loopCounterVarName = loopCounterVarName;
		}

		public String getLocalVarName() {
			return localVarName;
		}

		public void setLocalVarName(String localVarName) {
			this.localVarName = localVarName;
		}

		public String getPropertyPrimitiveType() {
			return propertyPrimitiveType;
		}

		public void setPropertyPrimitiveType(String propertyPrimitiveType) {
			this.propertyPrimitiveType = propertyPrimitiveType;
		}

		public String getPropertyArrayType() {
			return propertyArrayType;
		}

		public void setPropertyArrayType(String propertyArrayType) {
			this.propertyArrayType = propertyArrayType;
		}

		public String getPropertyArrayName() {
			return propertyArrayName;
		}

		public void setPropertyArrayName(String propertyArrayName) {
			this.propertyArrayName = propertyArrayName;
		}

		public String getLocalVarType() {
			return localVarType;
		}

		public void setLocalVarType(String localVarType) {
			this.localVarType = localVarType;
		}

		public void setXiNodeLoop(boolean inXiNodeLoop) {
			this.xiNodeLoop  = inXiNodeLoop;
		}

		public boolean isXiNodeLoop() {
			return xiNodeLoop;
		}
		
	}
	
	private class PropertyTypeInfo {
		
		String type;
		boolean isProperty;
		
		public PropertyTypeInfo(String type, boolean isProperty) {
			this.type = type;
			this.isProperty = isProperty;
		}
		
	}
	
	class CodegenContext {
		
		public boolean multilineFormula = false;
		public String multilineVariable;
		
		public void reset() {
			multilineFormula = false;
			multilineVariable = null;
		}
		
		public boolean isMultilineFormula() {
			return multilineFormula;
		}
		public void setMultilineFormula(boolean multilineFormula) {
			this.multilineFormula = multilineFormula;
		}
		public String getMultilineVariable() {
			return multilineVariable;
		}
		public void setMultilineVariable(String multilineVariable) {
			this.multilineVariable = multilineVariable;
		}
		
	}
	
	private class BindingContext {
		
		Stack<Entity> sourceEntityStack = new Stack<Entity>();
		Binding binding;
		PropertyTypeInfo propertyType;
		StringBuilder buffer;
		boolean inputTransformation;
		boolean psuedoOutputTransformation; // this is an input transformation, but it behaves as an output transformation for codegen purposes
		private boolean create;
		private boolean checkBinding = false;
		private boolean xiNodeTarget = false; // whether we're processing an XiNode element
		private boolean inForLoop = false; // whether we're inside of a 'for' loop - has meaning for '.' in XPath expressions
		private boolean requiresAssert = false; // whether we still need to assert a newly created contained concept
		private String assertVarName;
		private ForLoopInfo forLoopInfo;
		private CodegenContext codegenContext;
		
		public BindingContext(StringBuilder buffer) {
			this.buffer = buffer;
		}

		protected void pushEntity(Entity entity) {
			sourceEntityStack.push(entity);
		}
		
		public CodegenContext getCodegenContext() {
			if (codegenContext == null) {
				codegenContext = new CodegenContext();
			}
			return codegenContext;
		}
		
		protected Entity getEntity() {
			if (sourceEntityStack.size() == 0) {
				return null;
			}
			return sourceEntityStack.lastElement();
		}
		
		protected Object getPropertyDefinition(String propName, boolean localOnly) {
			Concept con = getEntityAsConcept();
			if (con != null) {
				return con.getPropertyDefinition(propName, localOnly);
			} 
			Entity entity = getEntity();
			if (entity instanceof Event) {
				EventPropertyDefinition definition = ((Event) entity).getPropertyDefinition(propName, !localOnly);
				return definition;
			} else if (entity instanceof RuleFunction) {
				RuleFunction rf = (RuleFunction) entity;
				Symbol symbol = rf.getScope().getSymbol(propName);
				return symbol;
			}
			return null;
		}
		
		protected Concept getEntityAsConcept() {
			if (sourceEntityStack.size() == 0) {
				return null;
			}
			Entity el = sourceEntityStack.lastElement();
			if (el instanceof Concept) {
				return (Concept) el;
			} else if (el instanceof PropertyDefinition) {
				int propType = ((PropertyDefinition) el).getType();
				 if (propType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
							|| propType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
					 return ((PropertyDefinition) el).getConceptType();
				 }
			}
			return null;
		}
		
		protected Entity popEntity() {
			return sourceEntityStack.pop();
		}

		public boolean isFunctionCall() {
			if (getEntity() instanceof RuleFunction) {
				return true;
			}
			return isInputTransformation() && javaTask;
		}

		public boolean isInputTransformation() {
			return inputTransformation;
		}

		public void setInputTransformation(boolean inputTransformation) {
			this.inputTransformation = inputTransformation;
		}

		public boolean isPsuedoOutputTransformation() {
			return psuedoOutputTransformation;
		}

		public void setPsuedoOutputTransformation(boolean psuedoOutputTransformation) {
			this.psuedoOutputTransformation = psuedoOutputTransformation;
		}

		public void setCreate(boolean create) {
			this.create = create;
		}

		public boolean isCreate() {
			return create;
		}

		public String getAssertVarName() {
			return assertVarName;
		}

		public void setAssertVarName(String assertVarName) {
			this.assertVarName = assertVarName;
		}

		public boolean isRequiresAssert() {
			return requiresAssert;
		}

		public void setRequiresAssert(boolean requiresAssert) {
			this.requiresAssert = requiresAssert;
		}

		public void setCheckBinding(boolean b) {
			this.checkBinding  = b;
		}

		public void setXiNode(boolean xiNode) {
			this.xiNodeTarget  = xiNode;
		}

		public boolean isInForLoop() {
			return inForLoop;
		}

		public void setInForLoop(boolean inForLoop) {
			this.inForLoop = inForLoop;
		}

		public ForLoopInfo getForLoopInfo() {
			if (forLoopInfo == null) {
				forLoopInfo = new ForLoopInfo();
			}
			return forLoopInfo;
		}

		public void setForLoopInfo(ForLoopInfo forLoopInfo) {
			this.forLoopInfo = forLoopInfo;
		}

		public void reset(boolean resetPropType) {
			getCodegenContext().reset();
			if (resetPropType) {
				this.propertyType = null;
			}
		}

	}

	private static final String PROCESS_URI = "process_uri"; //$NON-NLS-1$
//    private static String XPATH_TEMPLATE = "\"xpath://<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><xpath>    <expr>{0}</expr>    <namespaces>{1}</namespaces>    <variables>{2}</variables></xpath>\"";
    public static final String boxUnboxed = com.tibco.cep.util.CodegenFunctions.class.getName()+ ".box";
//    public static final String copyOf = JXPathHelper.class.getName()+ ".copyOf";
    public static final String copyOf = "com.tibco.be.functions.xpath.JXPathHelper"+ ".copyOf";

    private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Object TEMP_VAR_JOB = "$job";

    protected Ontology ontology;
	protected IVariableTypeResolver typeResolver;

	private Entity baseEntity;

	private String tempVarName;

	private boolean inputTransformation = false;
	private boolean psuedoOutputTransformation = false;
	private boolean loopTask = false;
	private boolean javaTask = false;

	private int counter = 0;
	
	public CommonXsltCodeGenerator(Ontology ontology, IVariableTypeResolver typeResolver) {
		this.ontology = ontology;
		this.typeResolver = typeResolver;
	}

	public void processXsltString(String xmlString, XsltCodegenContext genContext, StringBuilder buffer) {

        if ((xmlString == null) || (xmlString.isEmpty())) return;
		TemplateBinding binding = CommonMapperCoreUtils.getBinding(xmlString, new ArrayList());
		ArrayList receivingParms = XSTemplateSerializer.getReceivingParms(xmlString);


		String entityPath = (String) receivingParms.get(0);
		Entity entity = null;
		if (this.baseEntity != null) {
			entity = this.baseEntity;
		} else {
			entity = ontology.getEntity(entityPath);
		}
		
		
		String tempVarName = getTempVarName();
		// first validate that we can process this binding
		processBindings(binding, receivingParms, entity, tempVarName, true, buffer, genContext.isCreate());
		if (genContext.isCreate()) {
			// get current rule session
			buffer.append("com.tibco.cep.runtime.session.RuleSession session = com.tibco.cep.runtime.session.RuleSessionManager.getCurrentRuleSession();");
			buffer.append(BRK);
			buffer.append("com.tibco.cep.kernel.service.ObjectManager objectManager = session.getObjectManager();");
			buffer.append(BRK);
			
			if (genContext.isCreateRoot()) {
				// create new empty entity
				if (entity instanceof Event) {
					// fix for using $job in mappings for this context (TODO : think of a better way to handle EndEvent tasks)
					if (genContext.isBPMN()) {
//						buffer.append("com.tibco.cep.bpmn.runtime.model.JobContext $3zjob = ").append(TEMP_VAR_JOB).append(";").append(BRK); // no longer needed, see BE-17342
					}
				}
				String entPath = ModelUtils.convertPathToPackage(entity.getFullPath());
				String decl = ModelNameUtil.GENERATED_PACKAGE_PREFIX+"."+entPath + " " + tempVarName + "= null;";
				buffer.append(decl).append(BRK);
				buffer.append("try {").append(BRK);
				buffer.append("java.lang.Class clz = ").append(ModelNameUtil.GENERATED_PACKAGE_PREFIX).append(".").append(entPath).append(".class;").append(BRK);
				buffer.append("long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);").append(BRK);
				String constructor = tempVarName + "= new "+ModelNameUtil.GENERATED_PACKAGE_PREFIX+"."+entPath+"(id);";
				buffer.append(constructor).append(BRK);
			} 
		} else if (genContext.isBPMN() && this.inputTransformation) {
			// fix for using $job in mappings for this context (TODO : think of a better way to handle RuleFunction tasks)
//			buffer.append("com.tibco.cep.bpmn.runtime.model.JobContext $3zjob = ").append(TEMP_VAR_JOB).append(";").append(BRK); // no longer needed, see BE-17342
		}
		
		processBindings(binding, receivingParms, entity, tempVarName, false, buffer, genContext.isCreate());
		
		if (genContext.isCreate() && genContext.isCreateRoot()) {
			if (entity instanceof Concept) {
				// create state machine
				buffer.append("com.tibco.be.functions.object.ObjectHelper.createStateMachine(session, (com.tibco.cep.runtime.model.element.impl.ConceptImpl) ").append(tempVarName).append(", true, false);");
				buffer.append(BRK);
				
				// assert new concept
				buffer.append("session.assertObject(").append(tempVarName).append(", false);");
				buffer.append(BRK);
			}
			
			buffer.append("} catch (Exception e) {").append(BRK);
			buffer.append("throw new RuntimeException(e);").append(BRK);
			buffer.append("}").append(BRK);
			
			if (entity instanceof Concept) {
				buffer.append("return ").append(tempVarName).append(";");
			} else if (entity instanceof Event) {
				if (genContext.isBPMN()) {
					buffer.append("$vars.setVariable(\"").append(entity.getName()).append("\",").append(tempVarName).append(");");
				} else {
					buffer.append("return ").append(tempVarName).append(";");
				}
			}
			buffer.append(BRK);
		} else if (entity instanceof RuleFunction) {
			// this is called from the "execute" method
//			String fnCall = ModelNameUtil.GENERATED_PACKAGE_PREFIX + "." + ModelUtils.convertPathToPackage(entity.getFullPath());
//			
//			buffer.append("return ").append(fnCall).append("(");
//			Symbols scope = ((RuleFunction) entity).getScope();
//			List symbolsList = scope.getSymbolsList();
//			for (int i=0; i<symbolsList.size(); i++) {
//				Symbol s = (Symbol) symbolsList.get(i);
//				String param = ModelNameUtil.SCOPE_IDENTIFIER_PREFIX + s.getName();
//				buffer.append(param);
//				if (i < symbolsList.size()-1) {
//					buffer.append(", ");
//				}
//			}
//			buffer.append(");");
//			buffer.append(BRK);
		}
	}

	public Entity getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(Entity baseEntity) {
		this.baseEntity = baseEntity;
	}

	private String getTempVarName() {
		if (this.tempVarName == null) {
			return "$temp";
		}
		return this.tempVarName;
	}

	public void setTempVarName(String tempVarName) {
		this.tempVarName = tempVarName;
	}

	protected void processBindings(TemplateBinding binding,
			ArrayList receivingParms, Entity entity, String tempVarName, boolean validateOnly, StringBuilder buffer, boolean isCreate) {
		if (receivingParms.size() == 0) {
			// no target entity, cannot process bindings
		}

        Binding[] children = binding.getChildren();
        for (Binding b : children) {
        	BindingContext context = new BindingContext(buffer);
        	context.setInputTransformation(this.inputTransformation);
        	context.setPsuedoOutputTransformation(this.psuedoOutputTransformation);
        	context.setCreate(isCreate);
        	context.binding = b;
        	context.pushEntity(entity);
			processBinding(context, tempVarName, validateOnly);
		}

	}

	private void processBinding(BindingContext context, String tempVarName, boolean validateOnly) {
		Binding binding = context.binding;
		if (binding instanceof ChooseBinding) {
			processChooseBinding((ChooseBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof AttributeBinding) {
			processAttributeBinding((AttributeBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof ElementBinding) {
			processElementBinding((ElementBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof ValueOfBinding) {
			processValueOfBinding((ValueOfBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof IfBinding) {
			processIfBinding((IfBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof ForEachBinding) {
			processForEachBinding((ForEachBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof ForEachGroupBinding) {
			processForEachGroupBinding((ForEachGroupBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof WhenBinding) {
			processWhenBinding((WhenBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof OtherwiseBinding) {
			processOtherwiseBinding((OtherwiseBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof CopyOfBinding) {
			processCopyOfBinding((CopyOfBinding)binding, context, tempVarName, validateOnly);
		} else if (binding instanceof SetVariableBinding) {
			processSetVariableBinding((SetVariableBinding)binding, context, tempVarName, validateOnly);
		} else {
			processChildBindings(context, tempVarName, validateOnly);
		}
		postProcessBinding(binding);
	}
	
	private void postProcessBinding(Binding binding) {
		HashMap<String,IVariableType> remove = localMapperVarMap.remove(binding);
		if (remove != null) {
			System.out.println("Found scope");
		}
	}

	private void processChildBindings(BindingContext context, String tempVarName, boolean validateOnly) {
		if (context.binding.getChildCount() > 0) {
			Binding[] children = context.binding.getChildren();
			for (Binding childBinding : children) {
				context.binding = childBinding;
				processBinding(context, tempVarName, validateOnly);
			}
		}
	}

	private void processSetVariableBinding(SetVariableBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		ExpandedName name = binding.getVariableName();
		String variableName = name.localName;
		StringBuilder setBuffer = new StringBuilder(); 
		String formula = binding.getFormula();
		if (context.propertyType == null) {
			context.propertyType = getPropertyTypeFromJXPath(context, formula);
		}
		String javaType = context.propertyType == null ? "Object" : getJavaType(context.propertyType.type);
		if (javaType == null) {
			javaType = "Object";
		}
		IVariableType localVarType = new VariableType(javaType, false, false, false, false, false);
		pushLocalMapperVar(context, variableName, localVarType);
		setBuffer.append(javaType).append(" ");
		setBuffer.append(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX).append(variableName).append(" = (").append(javaType).append(")");
		if (formula != null) {
        	String formulaString = processFormulaString(context.propertyType == null ? javaType : context.propertyType.type, context, formula, true);
        	setBuffer.append(formulaString);
		} else {
			setBuffer.append("null");
		}
		setBuffer.append(';');
		context.buffer.append(setBuffer);
		processChildBindings(context, tempVarName, validateOnly);

	}

	protected HashMap<Binding, HashMap<String, IVariableType>> localMapperVarMap = new HashMap<>();
    private void pushLocalMapperVar(BindingContext context,
			String variableName, IVariableType localVarType) {
    	HashMap<String,IVariableType> hashMap = localMapperVarMap.get(context.binding.getParent());
    	if (hashMap == null) {
    		hashMap = new HashMap<>();
    		localMapperVarMap.put(context.binding.getParent(), hashMap);
    	}
    	hashMap.put(variableName, localVarType);
	}

	private void processValueOfBinding(ValueOfBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		String formula = binding.getFormula();
		if (formula != null) {
			processFormula(context, formula, tempVarName, validateOnly);
		}
		processChildBindings(context, tempVarName, validateOnly);
	}

	private void processAttributeBinding(AttributeBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		String formula = binding.getFormula();
		if (formula != null) {
			processFormula(context, formula, tempVarName, validateOnly);
		}
		processChildBindings(context, tempVarName, validateOnly);
	}

	private void processElementBinding(ElementBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
//			if ("payload".equals(binding.getName().localName)) {
//				if ("event".equals(binding.getParent().getName().localName)) {
//					throw new UnsupportedXsltMappingException("Cannot process payload element : "+context.getEntity().getName());
//				}
//			}
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
//		check for payload element binding here - bail if found?
		if ("payload".equals(binding.getName().localName)) {
			if ("event".equals(binding.getParent().getName().localName) || (binding.getParent().getParent() instanceof TemplateBinding/*for BPMN?*/)) {
				context.setXiNode(true);
				StringBuilder payloadBuffer = new StringBuilder(); 
				StringBuilder tmpBuilder = context.buffer;
				context.buffer = payloadBuffer;
				payloadBuffer.append("// create payload for event").append(BRK);
				payloadBuffer.append("{").append(BRK);
				if (binding.getChildCount() > 1) {
					System.err.println("Ahh!");
				}
				payloadBuffer.append("com.tibco.cep.runtime.model.event.PayloadFactory $payloadFactory = session.getRuleServiceProvider().getTypeManager().getPayloadFactory();").append(BRK);
				payloadBuffer.append("com.tibco.xml.datamodel.XiFactory $xiFactory = com.tibco.xml.datamodel.XiFactoryFactory.newInstance();").append(BRK);
				Binding child = binding.getChild(0);
				context.binding = child;
				String uri = context.binding.getName().namespaceURI;
				String name = context.binding.getName().localName;
				context.buffer.append("// create payload element for {").append(uri).append("}").append(name).append(BRK);
				if (uri == null) {
					payloadBuffer.append("com.tibco.xml.datamodel.XiNode $payloadXiNode = $xiFactory.createElement(new com.tibco.xml.data.primitive.ExpandedName(\"").append(name).append("\"));").append(BRK);
				} else {
					payloadBuffer.append("com.tibco.xml.datamodel.XiNode $payloadXiNode = $xiFactory.createElement(new com.tibco.xml.data.primitive.ExpandedName(\"").append(uri).append("\", \"").append(name).append("\"));").append(BRK);
				}
				payloadBuffer.append("com.tibco.xml.datamodel.XiNode $currXiNode = $payloadXiNode;").append(BRK);
				
//				fill out the xinode by visiting children
				processChildBindings(context, tempVarName, validateOnly);

				payloadBuffer.append(tempVarName).append(".setPayload(").append("$payloadFactory.createPayload(").append(tempVarName).append(".getExpandedName(), $payloadXiNode));").append(BRK);		
				payloadBuffer.append("}").append(BRK).append(BRK);
				context.buffer = tmpBuilder;
				context.buffer.append(payloadBuffer);
				context.setXiNode(false);
				return;
//				throw new UnsupportedXsltMappingException("Cannot process payload element : "+context.getEntity().getName());
			}
		} else if ("message".equals(binding.getName().localName) && "http://schemas.xmlsoap.org/soap/envelope/".equals(binding.getName().namespaceURI) && binding.getParent() instanceof TemplateBinding) {
			context.setXiNode(true);
			StringBuilder payloadBuffer = new StringBuilder(); 
			StringBuilder tmpBuilder = context.buffer;
			context.buffer = payloadBuffer;
			payloadBuffer.append("// create message for web services").append(BRK);
			payloadBuffer.append("{").append(BRK);
			if (binding.getChildCount() > 1) {
				System.err.println("Ahh!");
			}
			payloadBuffer.append("com.tibco.xml.datamodel.XiFactory $xiFactory = com.tibco.xml.datamodel.XiFactoryFactory.newInstance();").append(BRK);
			Binding child = binding.getChild(0);
			context.binding = child;
			String uri = context.binding.getName().namespaceURI;
			String name = context.binding.getName().localName;
			context.buffer.append("// create message element for {").append(uri).append("}").append(name).append(BRK);
			if (uri == null) {
				payloadBuffer.append("com.tibco.xml.datamodel.XiNode $messageXiNode = $xiFactory.createElement(new com.tibco.xml.data.primitive.ExpandedName(\"").append(name).append("\"));").append(BRK);
			} else {
				payloadBuffer.append("com.tibco.xml.datamodel.XiNode $messageXiNode = $xiFactory.createElement(new com.tibco.xml.data.primitive.ExpandedName(\"").append(uri).append("\", \"").append(name).append("\"));").append(BRK);
			}
			payloadBuffer.append("com.tibco.xml.datamodel.XiNode $currXiNode = $messageXiNode;").append(BRK);
			
//			fill out the xinode by visiting children
			processChildBindings(context, tempVarName, validateOnly);

			payloadBuffer.append("$vars.setVariable(\"message\", $messageXiNode);").append(BRK);		
			payloadBuffer.append("}").append(BRK).append(BRK);
			context.buffer = tmpBuilder;
			context.buffer.append(payloadBuffer);
			context.setXiNode(false);
			return;
		}
		String formula = binding.getFormula();
		if (context.xiNodeTarget) {
			if (formula != null) {
				// set the current payload element with the formula val
				String uri = context.binding.getName().namespaceURI;
				String name = context.binding.getName().localName;
				context.buffer.append("// set XiNode value for {").append(uri).append("}").append(name).append(BRK);
				context.buffer.append("$currXiNode.setAtomicValue(\"").append(formula).append("\");").append(BRK);
			} else {
				// append a new payload element with the given element binding name
				String newNodeName = "$newElXiNode"+counter++;
				String tmpNodeName = "$tmpXiNode"+counter++;
				context.buffer.append("com.tibco.xml.datamodel.XiNode ").append(tmpNodeName).append(" = $currXiNode;").append(BRK);
				String uri = context.binding.getName().namespaceURI;
				String name = context.binding.getName().localName;
				context.buffer.append("// create XiNode element for {").append(uri).append("}").append(name).append(BRK);
				if (uri == null) {
					context.buffer.append("com.tibco.xml.datamodel.XiNode ").append(newNodeName).append(" = $xiFactory.createElement(new com.tibco.xml.data.primitive.ExpandedName(\"").append(name).append("\"));").append(BRK);
				} else {
					context.buffer.append("com.tibco.xml.datamodel.XiNode ").append(newNodeName).append(" = $xiFactory.createElement(new com.tibco.xml.data.primitive.ExpandedName(\"").append(uri).append("\", \"").append(name).append("\"));").append(BRK);
				}
				context.buffer.append("$currXiNode.appendChild(").append(newNodeName).append(");").append(BRK);
				context.buffer.append("$currXiNode = ").append(newNodeName).append(";").append(BRK);
				processChildBindings(context, tempVarName, validateOnly);
				context.buffer.append("$currXiNode = ").append(tmpNodeName).append(";").append(BRK);
			}
			return;
		}
		boolean requiresPop = false;
		String oldVarName = context.getAssertVarName();
		boolean requiresAssert = false;
		if (formula != null) {
			processFormula(context, formula, tempVarName, validateOnly);
		} else if (context.getEntityAsConcept() != null) {
			Concept con = context.getEntityAsConcept();
			ExpandedName name = binding.getName();
			String locName = name.localName;
			// locName is the property name
			PropertyDefinition propertyDefinition = con.getPropertyDefinition(locName, false);
			if (propertyDefinition != null) {
				int propType = propertyDefinition.getType();
				if (propType == PropertyDefinition.PROPERTY_TYPE_CONCEPT
						|| propType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
//					Concept conceptType = propertyDefinition.getConceptType();
					if (context.isCreate() && propType == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
						requiresAssert = true;
						ensureConceptCreation(context, tempVarName, locName,
								propertyDefinition);
					}
					context.pushEntity(propertyDefinition);
					requiresPop = true;
				}
			}
		}
		processChildBindings(context, tempVarName, validateOnly);
//		if (requiresAssert) {
//			// assert new concept
//			context.buffer.append("session.assertObject(").append(context.getAssertVarName()).append(", false);");
//			context.buffer.append(BRK);
//		}
		context.setAssertVarName(oldVarName);
		if (requiresPop) {
			context.popEntity();
		}
	}

	protected void ensureConceptCreation(BindingContext context,
			String tempVarName, String locName,
			PropertyDefinition propertyDefinition) {
		StringBuilder buffer = new StringBuilder();
//		buffer.append("{").append(BRK);
		int size = context.sourceEntityStack.size();
		if (size > 1) {
			ListIterator<Entity> els = context.sourceEntityStack.listIterator(0);
			{
				// temp code to add comments for readability
				buffer.append("// ensure that the concept from the property '");
				while (els.hasNext()) {
					buffer.append(els.next().getName());
					buffer.append("->");
				}
				buffer.append(propertyDefinition.getName());
				buffer.append("' is created if necessary").append(BRK);
				els = context.sourceEntityStack.listIterator(0);
				buffer.append("// assume that the parent concept from the property '");
				while (els.hasNext()) {
					buffer.append(els.next().getName());
					if (els.hasNext()) {
						buffer.append("->");
					}
				}
				buffer.append("' was already created").append(BRK);
			}
			ListIterator<Entity> elements = context.sourceEntityStack.listIterator(1);
			String parentVarName = "$parent"+counter++;
			
			buffer.append("com.tibco.cep.runtime.model.element.PropertyAtomConcept ").append(parentVarName).append(" = ");
			StringBuilder tmpBuffer = new StringBuilder();
			tmpBuffer.append(getTempVarName());
    		while (elements.hasNext()) {
    			Entity nextElement = elements.next();
    			tmpBuffer.append(".getProperty(\"");
    			tmpBuffer.append(nextElement.getName());
    			tmpBuffer.append("\")");
        		if (nextElement instanceof PropertyDefinition && ((PropertyDefinition) nextElement).getConceptTypePath() != null) {
    				String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
    				tmpBuffer.insert(0, "(("+cast+")");
    				tmpBuffer.append(")");
//    				if (elements.hasNext()) {
    					tmpBuffer.append(".getConcept()");
//    				}
        		}
    		}
			tmpBuffer.append(".getProperty(\"");
			tmpBuffer.append(propertyDefinition.getName());
			tmpBuffer.append("\")");
			String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
			tmpBuffer.insert(0, "(("+cast+")");
			tmpBuffer.append(")");
    		tmpBuffer.append(";").append(BRK);
    		buffer.append(tmpBuffer);
    		writeCreateConcept(context, propertyDefinition, buffer, parentVarName);
		} else {
			String parentVarName = "$parent"+counter++;
			buffer.append("// ensure that the concept from the property '").append(propertyDefinition.getName()).append("' is created").append(BRK);
			if (propertyDefinition.isArray()) {
				buffer.append("com.tibco.cep.runtime.model.element.PropertyArrayConcept ").append(parentVarName).append(" = ");
				buffer.append("((com.tibco.cep.runtime.model.element.PropertyArrayConcept)").append(getTempVarName()).append(".getProperty(\"");
				buffer.append(locName).append("\"));").append(BRK);
			} else {
				buffer.append("com.tibco.cep.runtime.model.element.PropertyAtomConcept ").append(parentVarName).append(" = ");
				buffer.append("((com.tibco.cep.runtime.model.element.PropertyAtomConcept)").append(getTempVarName()).append(".getProperty(\"");
				buffer.append(locName).append("\"));").append(BRK);
				
			}
			writeCreateConcept(context, propertyDefinition, buffer, parentVarName);
		}
//		buffer.append("}").append(BRK);
		context.buffer.append(buffer);
	}

	protected void writeCreateConcept(BindingContext context,
			PropertyDefinition propertyDefinition, StringBuilder buffer,
			String parentVarName) {
		String conceptVarId = getTempVarName() + "_" + counter ++;
		String entPath = ModelUtils.convertPathToPackage(propertyDefinition.getConceptTypePath());
		String contConceptVar = propertyDefinition.isArray() ? "null" : parentVarName + ".getConcept()";
		String conceptFullPath = ModelNameUtil.GENERATED_PACKAGE_PREFIX+"."+entPath;
		String decl = conceptFullPath + " " + conceptVarId + "= (" +conceptFullPath + ")" + contConceptVar + ";";
		buffer.append(decl).append(BRK);
		buffer.append("if (").append(conceptVarId).append(" == null) {").append(BRK);
		createConcept(conceptVarId, buffer, entPath, false);
		context.setAssertVarName(conceptVarId);
		buffer.append(BRK).append(parentVarName);
		if (propertyDefinition.isArray()) {
			buffer.append(".add(");
		} else {
			buffer.append(".setValue(");
		}
		buffer.append(context.getAssertVarName()).append(");");
		buffer.append("}").append(BRK);
	}

	private boolean createConcept(String conceptVarId, StringBuilder buffer, String entPath, boolean assertConcept) {
		buffer.append("try {").append(BRK);
		String clzVarName = "clz_"+counter;
		String idVarName = "id_"+counter++;
		buffer.append("java.lang.Class ").append(clzVarName).append(" = ").append(ModelNameUtil.GENERATED_PACKAGE_PREFIX).append(".").append(entPath).append(".class;").append(BRK);
		buffer.append("long ").append(idVarName).append(" = session.getRuleServiceProvider().getIdGenerator().nextEntityId(").append(clzVarName).append(");").append(BRK);
		String constructor = conceptVarId + "= new "+ModelNameUtil.GENERATED_PACKAGE_PREFIX+"."+entPath+"("+idVarName+");";
		buffer.append(constructor).append(BRK);
		
		if (assertConcept) {
			// create state machine
			buffer.append("com.tibco.be.functions.object.ObjectHelper.createStateMachine(session, (com.tibco.cep.runtime.model.element.impl.ConceptImpl) ").append(conceptVarId).append(", true, false);");
			buffer.append(BRK);
			
			// assert new concept
			buffer.append("session.assertObject(").append(conceptVarId).append(", false);");
			buffer.append(BRK);
		} else {
		}
		
		buffer.append("} catch (Exception e) {").append(BRK);
		buffer.append("throw new RuntimeException(e);").append(BRK);
		buffer.append("}").append(BRK);
		
		return !assertConcept;
	}

	private void processOtherwiseBinding(OtherwiseBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		// can otherwise bindings ever have a formula?
		context.buffer.append("{ // otherwise binding");
		context.buffer.append(BRK);
		processChildBindings(context, tempVarName, validateOnly);
		context.buffer.append("} // end otherwise binding");
		context.buffer.append(BRK);
	}

	private void processWhenBinding(WhenBinding binding, BindingContext context,
			String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		// same as if binding?
		String formula = binding.getFormula();
		if (formula != null) {
			// process test
			String formulaString = processFormulaString("Boolean", context, formula, false);
			context.buffer.append("if ("+formulaString+") {");
			context.buffer.append("// when binding");
			context.buffer.append(BRK);
		}
		processChildBindings(context, tempVarName, validateOnly);
		if (formula != null) {
			context.buffer.append("} // end if");
			context.buffer.append(BRK);
		}
		context.buffer.append("// end when binding");
		context.buffer.append(BRK);
	}

	private void processForEachGroupBinding(ForEachGroupBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		String formula = binding.getFormula();
		if (validateOnly) {
			throw new UnsupportedXsltMappingException("Cannot process for each binding : "+formula);
		}
		if (!isConstantFormula(formula.toCharArray())) {
			System.err.println("Cannot process for each : "+formula);
			return;
		} else {
			System.err.println("Got constant formula in for each : "+formula);
		}
		System.err.println("Got for each group binding");
	}

	private void processCopyOfBinding(CopyOfBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			String formula = binding.getFormula();
			if (formula != null && formula.contains("ancestor-or-self::*/namespace::node()")) {
				// cannot support copy-contents-of bindings at this point
				throw new UnsupportedXsltMappingException("Cannot process copy (contents) of binding : "+formula);
			}
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		/*
		 * The assumption here is that the target and source types are the same,
		 * i.e. Concepts.Person.  Otherwise, if they are different, the binding
		 * is a copy-contents-of binding.
		 */
		String formula = binding.getFormula();
		
		// determine the type of the source/target
		String[] split = formula.split("/");
		String propName = split[split.length-1];
		String targetPropType = getPropertyType(context, propName);
		String processedFormulaString = processFormulaString(targetPropType, context, formula, true);
		StringBuilder newFormula = new StringBuilder();
		boolean requiresNullCheck = true; // always check?
		if (context.codegenContext.isMultilineFormula()) {
			newFormula.append(processedFormulaString);
			processedFormulaString = context.codegenContext.getMultilineVariable();
		}
    	String varName = "_$tempVar"+counter++;
    	newFormula.append("Object");
    	newFormula.append(" ");
    	newFormula.append(varName).append(" = ").append(copyOf).append("(").append(processedFormulaString).append(");");
    	newFormula.append(BRK);
    	if (requiresNullCheck) {
    		newFormula.append("if (").append(varName).append(" != null) {").append(BRK);
    	}
    	StringBuilder setBuffer = processElementStack(context, tempVarName, propName,
    			varName);
		newFormula.append(setBuffer);
		
    	if (requiresNullCheck) {
    		newFormula.append("}").append(BRK);
    	}
    	context.buffer.append(newFormula);
	}

	protected StringBuilder processElementStack(BindingContext context,
			String tempVarName, String propName, String varName) {
		//		process element stack...
		StringBuilder setBuffer = new StringBuilder();
//		if (!context.isInputTransformation()) { // do I need this check?  Doesn't work for sub-process calls
		setBuffer.append(tempVarName);
//		}
		ListIterator<Entity> elements = context.sourceEntityStack.listIterator(1);
		while (elements.hasNext()) {
			Entity nextElement = elements.next();
			setBuffer.append(".getProperty(\"");
			setBuffer.append(nextElement.getName());
			setBuffer.append("\")");
			if (nextElement instanceof PropertyDefinition && ((PropertyDefinition) nextElement).getConceptTypePath() != null) {
				String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
				setBuffer.insert(0, "(("+cast+")");
				setBuffer.append(").getConcept()");
			}
		}
		setBuffer.append(".setPropertyValue(\"");
		setBuffer.append(propName);
		setBuffer.append("\", ");
		setBuffer.append(varName);
		setBuffer.append(");").append(BRK);
		return setBuffer;
	}
	
	private void processForEachBinding(ForEachBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		String formula = binding.getFormula();
		PropertyTypeInfo temppropType= null;
		if (validateOnly) {
//			if (!isConstantFormula(formula.toCharArray())) {
//				System.err.println("Cannot process for each : "+formula);
//				throw new UnsupportedXsltMappingException("Cannot process for each binding : "+formula);
//			}
			return;
		}
//		if (!isConstantFormula(formula.toCharArray())) {
//			System.err.println("Cannot process for each : "+formula);
//			throw new UnsupportedXsltMappingException("Cannot process for each binding : "+formula);
//			//				return;
//		} else 
		if (context.isInForLoop()) {
			throw new UnsupportedXsltMappingException("Cannot process for each binding (for-each is within another for-each): "+formula);
		} else
		{
			String arrayVarName = "_$tempVar"+counter++;
			PropertyTypeInfo tmpPropType = context.propertyType;
			if (context.propertyType == null) {
				temppropType = getPropertyTypeFromJXPath(context, formula);;
				String tempproptypeStr = temppropType == null ? "Object" : getJavaType(temppropType.type);
				if (tempproptypeStr == null) {
					tempproptypeStr = "Object";
				}
				temppropType = new PropertyTypeInfo(tempproptypeStr, true);
				context.propertyType = temppropType;
			}
			if (isConstantFormula(formula.toCharArray())) {
				context.setInForLoop(true);
			}
			String formulaString = processFormulaString("List", context, formula, true, arrayVarName);
			if (!isConstantFormula(formula.toCharArray())) {
				if (context.codegenContext.isMultilineFormula()) {
					context.buffer.append(formulaString);
				} else {
					// try to ascertain the type by removing each node test and traversing the tree
					context.propertyType = getPropertyTypeFromJXPath(context, formula);
					String listVarName = "_$tempVar"+counter++;
					context.buffer.append("java.util.List<Object> ").append(listVarName).append(" = "); 
					context.buffer.append(formulaString);
					context.buffer.append(";");
					context.buffer.append(BRK);
					String propType = context.propertyType.type;
					if ("Concept".equals(propType)) {
						if (context.propertyType.isProperty) {
							propType = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
						} else {
							propType = "com.tibco.cep.runtime.model.element.Concept";
						}
					} else if ("XiNode".equals(propType)) {
						propType = "com.tibco.xml.datamodel.XiNode";
					}
					context.buffer.append(propType).append("[] ").append(arrayVarName).append(" = ");
					context.buffer.append("(").append(propType).append("[]").append(")").append(listVarName)
					.append(".toArray(new ").append(propType).append("[").append(listVarName).append(".size()])");
				}
			} else {
				if (context.codegenContext.isMultilineFormula()) {
					context.buffer.append(formulaString);
				} else {
					String listVarName = "_$tempVar"+counter++;
					context.buffer.append("java.util.List<Object> ").append(listVarName).append(" = "); 
					context.buffer.append(formulaString);
					context.buffer.append(";");
					context.buffer.append(BRK);
					String propType ="";
					if(context.propertyType != null){
						propType= context.propertyType.type;
					} else {
						propType = temppropType.type;
						context.propertyType = temppropType;
					}
						
					if ("Concept".equals(propType)) {
						if (context.propertyType.isProperty) {
							propType = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
						} else {
							propType = "com.tibco.cep.runtime.model.element.Concept";
						}
					} else if ("XiNode".equals(propType)) {
						propType = "com.tibco.xml.datamodel.XiNode";
					}
					context.buffer.append(propType).append("[] ").append(arrayVarName).append(" = ");
					context.buffer.append("(").append(propType).append("[]").append(")").append(listVarName)
					.append(".toArray(new ").append(propType).append("[").append(listVarName).append(".size()])");
				}
//				context.buffer.append(formulaString);
			}
			context.buffer.append(";");
			context.buffer.append(BRK);
			processForEachLoop(context, arrayVarName, validateOnly);
			context.propertyType = tmpPropType;
		}
	}

	private PropertyTypeInfo getPropertyTypeFromJXPath(BindingContext context,
			String formula) {
		String[] split = formula.split("/");
		Object ownerEntity = null;
		if (split.length > 0) {
			String lookupVar = split[0];
			if (lookupVar.startsWith(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX)) {
				lookupVar = lookupVar.substring(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX.length());
			} else if (lookupVar.startsWith("$")) {
				lookupVar = lookupVar.substring(1);
			}
			IVariableType type = typeResolver.getDeclaredIdentifierType(lookupVar);
			String path = type.getTypeName();
			if (XiNode.class.getCanonicalName().equals(path)) {
				ForLoopInfo forLoopInfo = context.getForLoopInfo();
				forLoopInfo.setLocalVarType("com.tibco.xml.datamodel.XiNode");
				forLoopInfo.setXiNodeLoop(true);
				return new PropertyTypeInfo("XiNode", false);
			}
			if (type.isConcept()) {
				ownerEntity = ontology.getConcept(path);
			} else if (type.isEvent()) {
				ownerEntity = ontology.getEvent(path);
			} 
			if (ownerEntity == null) {
				// workaround for processes
				ownerEntity = ontology.getEntity(path);
			}
		}
//		if (ownerEntity == null) {
//			return null; // throw new unsupported mapping?  return Object type here?
//		}
		for (int j = 1; j < split.length; j++) {
			String segment = split[j];
			if ("payload".equals(segment)) {
				ForLoopInfo forLoopInfo = context.getForLoopInfo();
				forLoopInfo.setLocalVarType("com.tibco.xml.datamodel.XiNode");
				forLoopInfo.setXiNodeLoop(true);
				return new PropertyTypeInfo("XiNode", false);
			}
			int idx = segment.indexOf('[');
			if (idx != -1) {
				segment = segment.substring(0, idx);
			}
			if ("elements".equals(segment)) {
				continue;
			}
			if (ownerEntity instanceof Concept) {
				ownerEntity = ((Concept) ownerEntity).getPropertyDefinition(segment, false);
			} else if (ownerEntity instanceof Event) {
				ownerEntity = ((Event) ownerEntity).getPropertyDefinition(segment, true);
			}
		}
		if (ownerEntity instanceof Concept) {
			return new PropertyTypeInfo("Concept", false);
		}
		if (ownerEntity instanceof PropertyDefinition) {
			if (((PropertyDefinition) ownerEntity).getConceptTypePath() != null) {
				return new PropertyTypeInfo("Concept", true);
			}
			return new PropertyTypeInfo(getConceptPropertyType((PropertyDefinition) ownerEntity), true);
		}
		if (ownerEntity instanceof EventPropertyDefinition) {
			return new PropertyTypeInfo(getEventPropertyType((EventPropertyDefinition) ownerEntity), true);
		}
		return null;
	}

	private void processForEachLoop(BindingContext context, String arrayVarName, boolean validateOnly) {
		ForLoopInfo forLoopInfo = context.getForLoopInfo();
		String loopVarType = context.propertyType.type;
		boolean isConcept = "Concept".equals(loopVarType);
		if (isConcept) {
//			loopVarType = "ConceptReference";
		}
		String javaType = null;
		if (!isConcept) {
			javaType = getJavaType(loopVarType);
		}
		if (javaType == null) {
			javaType = loopVarType;
		}
		StringBuilder forLoopBuffer = new StringBuilder();
		String propArrPrefix = "com.tibco.cep.runtime.model.element.PropertyArray";
		String propAtomPrefix = "com.tibco.cep.runtime.model.element.PropertyAtom";
		Concept con = context.getEntityAsConcept();
		if (!context.isInputTransformation() && context.binding.getChildCount() == 1 && con != null) {
			// BE-21916 : When mapping to an array in an output transformation, always clear the array first, rather than append to the end
			Binding childBinding = context.binding.getChild(0);
			if (childBinding instanceof ElementBinding) {
				ExpandedName name = childBinding.getName();
				String locName = name.localName;
				// locName is the property name
				PropertyDefinition propertyDefinition = con.getPropertyDefinition(locName, false);
				if (propertyDefinition != null && propertyDefinition.isArray()) {
					// it is an array, clear it
					forLoopBuffer.append("// clear the array before mapping the output\n");
			    	ListIterator<Entity> elements = context.sourceEntityStack.listIterator(1);
			    	boolean hasElements = elements.hasNext();// && !isFuncCall;
			    	boolean useAssertVar = hasElements && context.getAssertVarName() != null;
			    	String varName = useAssertVar ? context.getAssertVarName() : getTempVarName();
					forLoopBuffer.append("((").append(propArrPrefix).append(')').append(varName).append(".getProperty(\"").append(propertyDefinition.getName()).append("\")).clear();").append('\n');
				}
			}
		}
//		String propAtomPrefix = "com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtom";
		// for input transformations, need to declare an array/list to hold the calculated values to be set in $vars
		String propertyArrayName = arrayVarName + "_arr";
		String propertyArrayType = context.isInputTransformation() ? javaType : propArrPrefix;
		if (context.isInputTransformation()) {
			forLoopBuffer.append(propertyArrayType);
			forLoopBuffer.append("[] ");
			forLoopBuffer.append(propertyArrayName);
			forLoopBuffer.append(" = new ");
			forLoopBuffer.append(propertyArrayType);
			forLoopBuffer.append("[");
			forLoopBuffer.append(arrayVarName).append(".length");
			forLoopBuffer.append("];").append(BRK);
		} 
//		else {
//			forLoopBuffer.append(propertyArrayType);
//			forLoopBuffer.append(" ");
//			forLoopBuffer.append(propertyArrayName);
//			forLoopBuffer.append(" = new ");
//			forLoopBuffer.append(propertyArrayType);
//			forLoopBuffer.append("((com.tibco.cep.runtime.model.element.impl.ConceptImpl)");
//			forLoopBuffer.append(tempVarName).append(", 0");
//			forLoopBuffer.append(");").append(BRK);
//			
//		}
		String loopCounterVar = "$_i"+counter++;
		context.getForLoopInfo().setLoopCounterVarName(loopCounterVar);
		forLoopBuffer.append("for (int ").append(loopCounterVar).append("=0; ").append(loopCounterVar).append("<").append(arrayVarName).append(".length; ").append(loopCounterVar).append("++) {").append(BRK);
		
		String localTmpVar = "_$tempVar"+counter++;
		
		if (!isConcept && !context.propertyType.isProperty) {
			loopVarType = getJavaType(loopVarType);
		}
		String localVarType = context.propertyType.isProperty ? propAtomPrefix + loopVarType : loopVarType;
		if (isConcept && !context.propertyType.isProperty) {
			localVarType = context.propertyType.type;
		}
		if ("Concept".equals(localVarType)) {
			localVarType = "com.tibco.cep.runtime.model.element.Concept";
		}
		forLoopBuffer.append(localVarType).append(" ").append(localTmpVar).append(" = ");
		forLoopBuffer.append("(").append(localVarType)
		.append(")").append(arrayVarName).append("[").append(loopCounterVar).append("];").append(BRK);
		
		context.setInForLoop(true);
		forLoopInfo.setLocalVarType(localVarType);
		forLoopInfo.setPropertyPrimitiveType(context.propertyType.type);
		forLoopInfo.setLocalVarName(localTmpVar);
		forLoopInfo.setPropertyArrayName(propertyArrayName);
		forLoopInfo.setPropertyArrayType(propertyArrayType);
		StringBuilder oldBuilder = context.buffer;
		context.buffer = forLoopBuffer;
		if (context.isFunctionCall() && context.binding.getChildCount() == 1 && context.binding.getChild(0).getChildCount() == 0) {
			// treat it like a '.' formula -- this seems to apply only to Concept mappings
			forLoopBuffer.append(forLoopInfo.propertyArrayName).append("[").append(forLoopInfo.loopCounterVarName).append("]");
			forLoopBuffer.append(" = ");
			if (isConcept && context.propertyType.isProperty) {
				forLoopBuffer.append(forLoopInfo.localVarName);
				forLoopBuffer.append(".getConcept()");
			} else if (context.propertyType.isProperty) {
				// cast property value
				forLoopBuffer.append("(");
				forLoopBuffer.append(forLoopInfo.propertyArrayType);
				forLoopBuffer.append(")");
				forLoopBuffer.append(forLoopInfo.localVarName);
				forLoopBuffer.append(".getValue()");
			}
			forLoopBuffer.append(";\n");
			context.binding = context.binding.getChild(0);
		} else {
			processChildBindings(context, getTempVarName(), validateOnly);
		}
		context.setInForLoop(false);
		context.setForLoopInfo(null);
		
		forLoopBuffer.append("}").append(BRK);
		if (context.isInputTransformation()) {
			if (context.isFunctionCall()) {
				Binding binding = context.binding;
				String varName;
				if (context.binding.getChildCount() == 0 && !(context.binding instanceof ValueOfBinding)) {
					varName = binding.getName().getLocalName();
				} else {
					varName = binding.getParent().getName().getLocalName();
				}
				forLoopBuffer.append("$vars.setVariable(\"").append(varName).append("\", ").append(propertyArrayName).append(");").append(BRK);
			} else {
				forLoopBuffer.append("$vars.setVariable(\"").append(tempVarName).append("\", ").append(propertyArrayName).append(");").append(BRK);
			}
		} else {
			// set the property value
//			Binding binding = context.binding;
//			Binding parentBinding = binding.getParent();
//			if (parentBinding instanceof ElementBinding) {
//				String propName = parentBinding.getName().localName;
//				StringBuilder setBuffer = processElementStack(context, tempVarName, propName, propertyArrayName);
//				forLoopBuffer.append(setBuffer.toString());
//			}
		}
		context.buffer = oldBuilder;
		context.buffer.append(forLoopBuffer);
	}

	private void processIfBinding(IfBinding binding, BindingContext context,
			String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		// same as when binding?
		String formula = binding.getFormula();
		if (formula != null && !context.isInputTransformation()) {
			// process test
//			what is the proper way to handle this?
			if (isElementBindingCheck(context, formula)) {
				context.setCheckBinding(true);
			} else {
				String formulaString = processFormulaString("Boolean", context, formula, false);
				context.buffer.append("if ("+formulaString+") {");
				context.buffer.append("// if binding");
				context.buffer.append(BRK);
			}
		}
		processChildBindings(context, tempVarName, validateOnly);
		if (formula != null && !context.isInputTransformation()) {
			if (context.checkBinding) {
				context.buffer.append("// end element binding check").append(BRK);
				context.checkBinding = false;
			} else {
				context.buffer.append("} // end if");
				context.buffer.append(BRK);
			}
		}
		context.buffer.append("// end if binding");
		context.buffer.append(BRK);
	}

	private boolean isElementBindingCheck(BindingContext context, String formula) {
		if (context.binding.getChildCount() == 1 && context.binding.getChild(0) instanceof ElementBinding) {
			ElementBinding binding = (ElementBinding) context.binding.getChild(0);
			if (binding.getChildCount() == 1 && binding.getChild(0) instanceof ValueOfBinding) {
				ValueOfBinding vob = (ValueOfBinding) binding.getChild(0);
				return formula.equals(vob.getFormula());
			}
		} else if (context.binding.getChildCount() == 1 && context.binding.getChild(0) instanceof AttributeBinding) {
			AttributeBinding binding = (AttributeBinding) context.binding.getChild(0);
			if (binding.getChildCount() == 1 && binding.getChild(0) instanceof ValueOfBinding) {
				ValueOfBinding vob = (ValueOfBinding) binding.getChild(0);
				return formula.equals(vob.getFormula());
			}
		}
		return false;
	}

	private void processChooseBinding(ChooseBinding binding,
			BindingContext context, String tempVarName, boolean validateOnly) {
		if (validateOnly) {
			processChildBindings(context, tempVarName, validateOnly);
			return; // no issues processing this binding
		}
		context.buffer.append("{ // choose binding");
		context.buffer.append(BRK);
		Binding[] children = binding.getChildren();
		for (int i = 0; i < children.length; i++) {
			Binding childBinding = children[i];
			// could be 'when' or 'otherwise' binding
			context.binding = childBinding;
			processBinding(context, tempVarName, validateOnly);
			if (i < children.length-1) {
				if (i == children.length-1 && binding.hasOtherwise()) {
					context.buffer.append("else ");
				} else {
					context.buffer.append("else ");
				}
			}
		}
		context.buffer.append("} // end choose binding");
		context.buffer.append(BRK);
	}

	private void processFormula(BindingContext context, String formula, String tempVarName, boolean validateOnly) {
    	if (context.binding instanceof IfBinding) {
    		return;
    	}
    	Binding parent = context.binding.getParent();
    	if (context.xiNodeTarget) {
    		String type = "String"; // TODO : get the type of the payload element
        	String formulaString = processFormulaString(type, context, formula, true);
        	if (context.codegenContext.isMultilineFormula()) {
        		context.buffer.append(formulaString);
        		formulaString = context.codegenContext.getMultilineVariable();
        	}
        	context.buffer.append("com.tibco.be.model.rdf.XiNodeBuilder.setXiNodeValue($currXiNode, ").append(formulaString).append(");").append(BRK);
//			context.buffer.append("$currXiNode.setAtomicValue(com.tibco.xml.data.primitive.values.Xs").append(type).append("(").append(formulaString).append("));").append(BRK);
    		return;
    	}
    	String propName = parent.getName().localName;
    	if (parent instanceof AttributeBinding) {
    		propName = ((AttributeBinding) parent).getExplicitNameAVT();
    		if ("Id".equals(propName)) {
				// The Id cannot be set explicitly via the mapper, it is always generated automatically to avoid conflict.  
				// Ignore this mapping, but do not throw an error.  This is consistent with the standard mapper behavior
				return;
			}
    		if (context.isInputTransformation()) {
    			IVariableType type = this.typeResolver.getDeclaredIdentifierType(propName);
    			if (type != null) {
    				// this attribute is 'declared' in the symbol map - special case for input transformations
    				type.getTypeName();
    			}
    		}
    	}
    	StringBuilder tempLinebuffer = new StringBuilder();
    	boolean isFuncCall = context.isFunctionCall();
    	boolean check = context.checkBinding;
    	String propertyType = null;
    	IVariableType type = this.typeResolver.getDeclaredIdentifierType(propName);
    	boolean isDeclaredSymbol = (parent instanceof AttributeBinding || parent instanceof ElementBinding) && (context.isInputTransformation() || context.isPsuedoOutputTransformation()) && type != null;
    	if (isDeclaredSymbol) {
    		// this attribute is 'declared' in the symbol map - special case for input transformations
    		if (type.isConcept()) {
    			propertyType = "Concept";
    		} else if (type.isEvent()) {
    			propertyType = "Event";
				//Temporary fix for wrapper objects of  primitive types 
    		} else if(this.javaTask && RDFTypes.getType(type.getTypeName()) != null){
    			propertyType = RDFTypes.getType(type.getTypeName()).getRuntimeTerm();
    			if(propertyType != null && propertyType.indexOf("java.lang.") != -1) {
    				propertyType = propertyType.substring("java.lang.".length(),propertyType.length());
    			} else {
    				propertyType = type.getTypeName();
    			}
    		} else {
    			propertyType = type.getTypeName();
    		}
    	} else {
    		propertyType = getPropertyType(context, propName);
    	}
    	String javaType = getJavaType(propertyType);
    	boolean requiresNullCheck = requiresNullCheck(javaType);
    	String formulaString = processFormulaString(propertyType, context, formula, true);
    	if (context.getCodegenContext().isMultilineFormula()) {
    		tempLinebuffer.append(formulaString);
    		formulaString = context.getCodegenContext().getMultilineVariable();
    	}
    	if (isProcessURIBinding(parent, propName)) {
    		// need to declare the '$process' variable here rather than through the mapper string template
    		if (formula == null || formula.trim().length() == 0) {
    			// default process - already declared from MapperClassTemplate
    		} else {
    			// get the process through the template registry
    			tempLinebuffer.append("$template = ")
    				.append("com.tibco.cep.bpmn.runtime.templates.ProcessTemplateRegistry.getInstance().getProcessTemplateFromURI(").append(BRK)
    				.append("com.tibco.cep.runtime.session.RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProject().getOntology(),").append(BRK)
    				.append(formulaString)
    				.append(");");
    			tempLinebuffer.append(BRK);
    		}
    		tempLinebuffer.append(tempVarName)
    		.append(" = ").append("$template.newProcessData();").append(BRK);
    	} else if (context.isInForLoop()) {
    		ForLoopInfo forLoopInfo = context.getForLoopInfo();
    		if (context.isInputTransformation()) {
    			// add the property to the property array
    			tempLinebuffer.append(forLoopInfo.getPropertyArrayName());
    			tempLinebuffer.append("[").append(forLoopInfo.getLoopCounterVarName()).append("] = ");
    			tempLinebuffer.append(formulaString);
    			tempLinebuffer.append(";").append(BRK);
    		} else {
    			// add the property to the property array
    	    	StringBuilder setBuffer = new StringBuilder();
    	    	StringBuilder propBuffer = new StringBuilder();
    	    	ListIterator<Entity> elements = context.sourceEntityStack.listIterator(1);
    	    	if (!elements.hasNext()) {
    	    		propBuffer.append("(");
//    	    		propBuffer.append("((");
//    	    		propBuffer.append(forLoopInfo.getPropertyArrayType());
//    	    		propBuffer.append(")");
    	    	}
    	    	propBuffer.append(tempVarName);
    	    	boolean unbalanced = false;
//				process element stack...
				while (elements.hasNext()) {
					Entity nextElement = elements.next();
					propBuffer.append(".getProperty(\"");
					propBuffer.append(nextElement.getName());
					
					propBuffer.append("\")"); 
					if (nextElement instanceof PropertyDefinition) {
						PropertyDefinition propDef = (PropertyDefinition) nextElement;
						if (propDef.isArray()/* && !elements.hasNext()*/) {
							// need to access array element
							if ("ref".equals(propName)) {
								// special case where we're mapping to a concept ref array, append to array rather than getting an index
								if (unbalanced) {
									propBuffer.append(")");
									unbalanced = false;
								}
								if (elements.hasNext()) {
									propBuffer.append(")");
								}
							} else {
								String cast = "com.tibco.cep.runtime.model.element.PropertyArrayConcept";
								propBuffer.insert(0, "(("+cast+")");
								propBuffer.append(").get(").append(forLoopInfo.getLoopCounterVarName()).append(")");
							}
							if (elements.hasNext()) {
								propBuffer.append(")");
							}
						}
						if (propDef.getConceptTypePath() != null) {
							if ("ref".equals(propName) && propDef.isArray() && !elements.hasNext()) {
								// special case where we're mapping to a concept ref array, don't cast it
							} else {
								if (unbalanced) {
									propBuffer.append(")");
									unbalanced = false;
								}
								String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
								propBuffer.insert(0, "((("+cast+")");
								propBuffer.append(").getConcept()");
								unbalanced = true;
							}
						}
					}
				}
		    	boolean isPropArray = false;
				Object propertyDefinition = context.getPropertyDefinition(propName, false);
				if ("ref".equals(propName) && context.binding.getParent() instanceof AttributeBinding) {
					propertyDefinition = context.sourceEntityStack.lastElement();
				}
				if (propertyDefinition instanceof PropertyDefinition) {
					isPropArray = ((PropertyDefinition) propertyDefinition).isArray();
				}
				if ("ref".equals(propName)) {
					propBuffer.append(")");
				} else {
					propBuffer.append(".getProperty(\"");
					propBuffer.append(propName);
					propBuffer.append("\")))");
				}
				setBuffer.append(propBuffer);
				if (isPropArray) {
					setBuffer.append(".add(");
					String cast = "com.tibco.cep.runtime.model.element.PropertyArray";
					setBuffer.insert(0, "(("+cast+")");
				} else {
					setBuffer.append(".setValue(");
					String cast = "com.tibco.cep.runtime.model.element.PropertyAtom";
					setBuffer.insert(0, "(("+cast+")");
				}
				setBuffer.append(formulaString);
    			if (context.propertyType.isProperty) {
    				if (!isPropArray && isConstantFormula(formula.toCharArray())) { // don't call 'copy' if we are using JXPath (?)
    					setBuffer.append(".copy(");
    					setBuffer.append(propBuffer); 
//    					setBuffer.append(")"); // propBuffer has an extra ')' at the end already
    				}
    			}
    			setBuffer.append(");").append(BRK);
    			tempLinebuffer.append(setBuffer);
    		}
    		context.buffer.append(tempLinebuffer);
    		return;
    	}
    	String varName = "_$tempVar"+counter++;
    	if (check) {
//    		tempLinebuffer.append(javaType); // use generic object to avoid casting issues
    		tempLinebuffer.append("Object");
    		tempLinebuffer.append(" ");
    		tempLinebuffer.append(varName).append(" = ").append(formulaString).append(";");
    		tempLinebuffer.append(BRK);
    		if (requiresNullCheck) {
    			tempLinebuffer.append("if (").append(varName).append(" != null) {").append(BRK);
    		}
    	}
    	StringBuilder setBuffer = new StringBuilder();
//		process element stack...
    	ListIterator<Entity> elements = context.sourceEntityStack.listIterator(1);
    	boolean hasElements = elements.hasNext();// && !isFuncCall;
    	boolean useAssertVar = hasElements && context.getAssertVarName() != null;
		if (!context.isInputTransformation() || (!isFuncCall && context.isInputTransformation() && context.sourceEntityStack.size() > 1)) {
			if (!isProcessURIBinding(parent, propName) && !useAssertVar) {
				setBuffer.append(tempVarName);
			}
		}
		boolean usePropInSet = true;
		if (useAssertVar) {
			setBuffer.append(context.getAssertVarName());
		} else if (!isFuncCall) {
			while (elements.hasNext()) {
				Entity nextElement = elements.next();
				if (parent instanceof AttributeBinding && nextElement instanceof PropertyDefinition && ((PropertyDefinition) nextElement).getConceptTypePath() != null) {
		    		propName = ((AttributeBinding) parent).getExplicitNameAVT();
		    		if ("ref".equals(propName)) {
		    			if (formula.contains("@Id")) {
		    				formula = nextElement.getName();
		    				break;
		    			}
		    		}
				}

				setBuffer.append(".getProperty(\"");
				setBuffer.append(nextElement.getName());
				setBuffer.append("\")");
				if (nextElement instanceof PropertyDefinition && ((PropertyDefinition) nextElement).getConceptTypePath() != null) {
					String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
					setBuffer.insert(0, "(("+cast+")");
					setBuffer.append(")");
					if (!elements.hasNext() && "ref".equals(propName)) {
						usePropInSet = false;
						continue;
					}
					setBuffer.append(".getConcept()");
				}
			}
		}

		if (parent instanceof AttributeBinding) {
    		propName = ((AttributeBinding) parent).getExplicitNameAVT();
    		if ("ref".equals(propName)) {
    			// need to set by the property name
    			formula = formula.replaceAll("/@ref", "");
    			formula = formula.replaceAll("/@Id", "");
    			String[] split = formula.split("/");
    			propName = split[split.length-1];
    			setBuffer.append(".set");
    			if (usePropInSet) {
    				setBuffer.append("PropertyValue(\"");
    				setBuffer.append(propName);
    				setBuffer.append("\",");
    			} else {
    				setBuffer.append("Value(");
    			}
    		} else if (!isDeclaredSymbol) {
    			setBuffer.append(".set");
    			char[] charArray = propName.toCharArray();
    			charArray[0] = Character.toUpperCase(charArray[0]);
    			setBuffer.append(new String(charArray));
    			setBuffer.append("(");
    		}
    		else {
    			// input transformation & declared attribute, no setting required
    		}
    	} else {
    		if (!context.isInputTransformation() || (!isFuncCall && context.isInputTransformation() && hasElements)) {
    			setBuffer.append(".setPropertyValue(\"");
    			setBuffer.append(propName);
    			setBuffer.append("\", ");
//    			setBuffer.append(".set"+ModelNameUtil.MEMBER_PREFIX);
//    			setBuffer.append(propName);
    		}
    	}
    	boolean requiresCast = true;
    	if (/*isFuncCall && */context.isInputTransformation() && (parent instanceof AttributeBinding || isFuncCall) || isProcessURIBinding(parent, propName)) {
    		requiresCast = false;
    		setBuffer.append("$vars.setVariable(\"");
    		setBuffer.append(propName);
    		setBuffer.append("\",");
    	}
    	if (javaType != null) {
    		if (isFuncCall) {
//    			setBuffer.append(javaType);
    			if (!context.isInputTransformation()) {
    				setBuffer.append(" ");
    				setBuffer.append(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX);
    				setBuffer.append(propName);
    				setBuffer.append(" = ");
    			}
    		} else {
//    			setBuffer.append(javaType);
//    			setBuffer.append(".");
//    			setBuffer.append("valueOf(");
    		}
    	}
    	if (check) {
    		if (requiresCast && parent instanceof AttributeBinding && javaType != null) {
    			setBuffer.append("(");
    			setBuffer.append(javaType);
    			setBuffer.append(")");
    		}
    		if (context.propertyType != null && "XiNode".equals(context.propertyType.type)) {
    			if ("DateTime".equals(propertyType)) {
    				// the format of the date time in the payload is different than the format expected in PropertyAtomDateTimeSimple.  
    				// Need to use parseDate to convert (see BE-21529)
    				setBuffer.append("com.tibco.be.functions.trax.util.CalendarUtil.parseDate(");
    			}
    			setBuffer.append("((com.tibco.xml.datamodel.XiNode)");
    			setBuffer.append(varName);
    			setBuffer.append(").getStringValue()");
    			if ("DateTime".equals(propertyType)) {
    				setBuffer.append(")");
    			}
    		} else {
    			setBuffer.append(varName);
    		}
    	} else {
    		if (requiresCast && context.propertyType != null && context.propertyType.type != null) {
    			// always cast to target type?
    			if (javaType != null) {
    				setBuffer.append("(").append(javaType).append(")");
    			} else {
    				// not sure this would ever get called - I think we want to use the javaType here -- even collapse this entire check into one append of the javaType
    				setBuffer.append("(").append(context.propertyType.type).append(")");
    			}
    		} else if (requiresCast && javaType != null && loopTask) { // TODO : might want to do this always, not just for loopTasks
    			setBuffer.append("(").append(javaType).append(")");
    		}
    		setBuffer.append(formulaString);
    	}
    	if (!context.isFunctionCall()) {
    		if (javaType != null) {
//    			setBuffer.append(")");
    		}
    		setBuffer.append(")");
    	} else if (context.isInputTransformation()) {
    		setBuffer.append(")");
    	}
    	setBuffer.append(";");
    	setBuffer.append(BRK);
    	tempLinebuffer.append(setBuffer.toString());
    	if (check && requiresNullCheck) {
    		tempLinebuffer.append("}").append(BRK);
    	}
    	context.buffer.append(tempLinebuffer);
    }

	protected boolean isProcessURIBinding(Binding parent, String propName) {
		return propName.equals(PROCESS_URI) 
				&& parent instanceof AttributeBinding 
				&& parent.getParent() != null
				&& "Input".equals(parent.getParent().getName().localName);
	}

	private boolean requiresNullCheck(String javaType) {
    	if ("String".equals(javaType)) { // any other types?
    		return true;
    	}

    	return false;
	}

	private String processFormulaString(String propertyType, BindingContext context, String formula, boolean processConstantFormula) {
		return processFormulaString(propertyType, context, formula, processConstantFormula, null);
	}
	
	private String processFormulaString(String propertyType, BindingContext context, String formula, boolean processConstantFormula, String declVarName) {
		char[] chars = formula.toCharArray();
		boolean resetPropType = chars.length > 0 && chars[0] == '$';
		context.reset(resetPropType);
		if (formula.equals("$return/return")) {
			return ModelNameUtil.SCOPE_IDENTIFIER_PREFIX+"return";
		}
		StringBuilder newFormula = new StringBuilder();
		if (formula.indexOf("&#xA") != -1 || formula.indexOf('\n') != -1) {
			formula = formula.replaceAll("&#xA", "");
			formula = formula.replaceAll("\n", "");
		}
		if (context.isInForLoop()) {
			ForLoopInfo forLoopInfo = context.getForLoopInfo();
			if (forLoopInfo.isXiNodeLoop()) {
				if (formula.equals(".")) {
					newFormula.append(forLoopInfo.getLocalVarName()+".getStringValue()");
					return newFormula.toString();
				}
				if (!isConstantFormula(chars) || formula.indexOf('/') >= 0) {
					return processJXPathFormula(propertyType, formula, newFormula, chars, declVarName, context);
				}
				if (formula.indexOf(':') >= 0) {
					String[] nameSplit = formula.split(":");
					String ns = getNamespace(context, nameSplit[0]);
					String locName = nameSplit[1];
					newFormula.append("com.tibco.xml.XiNodeUtilities.getChildNode("+forLoopInfo.getLocalVarName()+", \""+ns+"\", \""+locName+"\")");
				} else {
					newFormula.append("com.tibco.xml.XiNodeUtilities.getChildNode("+forLoopInfo.getLocalVarName()+", \"com.tibco.xml.XiNodeUtilities.NO_NAMESPACE\", \""+formula+"\")");
				}
				if (context.binding instanceof IfBinding) {
					newFormula.append(" != null");
				} else {
					newFormula.append(".getStringValue()");
				}
				return newFormula.toString();
			}
			if (formula.contains(".")) {
				String forLoopVar = forLoopInfo.getLocalVarName();
//				if (forLoopVar.startsWith("_$")) {
//					forLoopVar = forLoopVar.substring(2);
//					formula = formula.replaceAll("\\.", "\\_\\$"+forLoopVar);
//				} else {
//					formula = formula.replaceAll("\\.", forLoopVar);
//				}
				chars = formula.toCharArray();
			} else if (formula.equals("@Id")) {
				newFormula.append(forLoopInfo.getLocalVarName());
				return newFormula.toString();
			} else if (formula.startsWith("@")) {
				newFormula.append(forLoopInfo.getLocalVarName());
				newFormula.append('.');
				newFormula.append("get");
				char[] charArray = formula.substring(1).toCharArray();
				charArray[0] = Character.toUpperCase(charArray[0]);
				newFormula.append(new String(charArray));
				newFormula.append("()");
				return newFormula.toString();
			} else if ("Concept".equals(forLoopInfo.getLocalVarType()) || "com.tibco.cep.runtime.model.element.Concept".equals(forLoopInfo.getLocalVarType())) {
				if (isConstantFormula(chars) && ModelNameUtil.isValidIdentifier(formula)) {
					newFormula.append(forLoopInfo.getLocalVarName());
					newFormula.append('.');
					newFormula.append("getPropertyValue(\"");
					newFormula.append(formula);
					newFormula.append("\")");
					return newFormula.toString();
				}
			} else if ("com.tibco.xml.datamodel.XiNode".equals(forLoopInfo.getLocalVarType())) {
				if (isConstantFormula(chars)) {
					// process the formula based on the context/loop XiNode
					String[] split = formula.split("/");
					if (split.length == 1) {
						newFormula.append("com.tibco.xml.XiNodeUtilities.getChildNode(");
						newFormula.append(forLoopInfo.getLocalVarName());
						newFormula.append(", \"\", \"");
						newFormula.append(formula);
						newFormula.append("\").getStringValue()");
					} else {
						String xiNodeName = "$currXiNode"+counter++;
						String strVal = xiNodeName+"_strVal";
						context.getCodegenContext().setMultilineVariable(strVal);
						newFormula.insert(0, "com.tibco.xml.datamodel.XiNode "+xiNodeName+" = null;\n");
						for (int j = 0; j < split.length; j++) {
							String segment = split[j];
							context.getCodegenContext().setMultilineFormula(true);
							processSegment(context, newFormula, declVarName,
									split, xiNodeName, j, segment);
						}
						newFormula.append("String ").append(strVal).append(" = ").append(xiNodeName).append(".getStringValue();\n");
					}
					
					return newFormula.toString();
				} else {
					// process with JXPath, setting the proper eval context node
					return processJXPathFormula(propertyType, formula, newFormula, chars, declVarName, context);
				}
			} else if (context.propertyType != null && context.propertyType.isProperty && "com.tibco.cep.runtime.model.element.PropertyAtomConcept".equals(forLoopInfo.getLocalVarType())) {
				if (isConstantFormula(chars) && ModelNameUtil.isValidIdentifier(formula)) {
					newFormula.append(forLoopInfo.getLocalVarName());
					newFormula.append('.');
					newFormula.append("getConcept().getPropertyValue(\"");
					newFormula.append(formula);
					newFormula.append("\")");
					return newFormula.toString();
				}
			}
			if (isConstantFormula(chars)) {
//				return formula; // ?
				if (processConstantFormula) {
					if (!context.isInputTransformation() && !context.isFunctionCall() && !context.isPsuedoOutputTransformation()) {
						formula = formula.replaceAll("\\$", "\\"+ModelNameUtil.SCOPE_IDENTIFIER_PREFIX);
					}
					chars = formula.toCharArray();
					return processConstantFormula(context, formula, newFormula, chars, propertyType, declVarName);
				} else {
					return formula; // ?
				}
			} else {
				return processJXPathFormula(propertyType, formula, newFormula, chars, declVarName, context);
			}
		} else if (isConstantFormula(chars) && processConstantFormula) {
			if (!context.isInputTransformation() && !context.isFunctionCall() && !context.isPsuedoOutputTransformation()) {
				formula = formula.replaceAll("\\$", "\\"+ModelNameUtil.SCOPE_IDENTIFIER_PREFIX);
			}
			formula = formula.replaceAll("&#xA", "");
			chars = formula.toCharArray();
			int idx = formula.indexOf('/');
			if (idx == -1) {
				//	why was this code here? -- check CRs as well
				String varName = formula;
				if (varName.startsWith(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX)) {
					varName = varName.substring(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX.length());
				}
				IVariableType type = typeResolver.getDeclaredIdentifierType(varName);
				if (type != null && declVarName != null) {
					String declStr = setPropertyType(context, declVarName, type);
					if (declStr != null && !loopTask) {
						newFormula.insert(0, declStr);
						newFormula.append(formula);
						//							test whether for-each loops for concepts can be done with non-arrays
						return newFormula.toString();
					}
				} if(type== null && RDFTypes.LONG.toString().equalsIgnoreCase(propertyType)){
					newFormula.append(formula);
					try {
						Long.valueOf(formula);
						// if we don't get a NFE, this is a constant, append 'L' to avoid conversion errors
						newFormula.append("L");
					} catch (NumberFormatException e) {
						// ignore
					}
					return newFormula.toString();
				}
				return formula;
			}
			//    			System.err.println("Found constant formula: "+formula);
			return processConstantFormula(context, formula, newFormula, chars, propertyType, declVarName);
		} else {//if (isConceptString){
			//    			System.err.println("Found xpath formula: "+formula);
			return processJXPathFormula(propertyType, formula, newFormula, chars, declVarName, context);
		}
	}

	private String getJXPathType(String propertyType) {
    	if ("String".equals(propertyType) || "Long".equalsIgnoreCase(propertyType) 
    			|| "Boolean".equalsIgnoreCase(propertyType) || "Double".equalsIgnoreCase(propertyType)
    			|| "DateTime".equalsIgnoreCase(propertyType)) {
    		if (Character.isLowerCase(propertyType.charAt(0))) {
    	        return Character.toUpperCase(propertyType.charAt(0)) + propertyType.substring(1);
    		}
    		return propertyType;
    	}
    	if ("Integer".equalsIgnoreCase(propertyType) || "int".equalsIgnoreCase(propertyType)) {
    		return "Int";
    	}
    	if ("XiNode".equalsIgnoreCase(propertyType)) {
    		return "Object";
    	}
    	if ("List".equalsIgnoreCase(propertyType)) {
    		return "List";
    	}
    	if ("DateTime".equalsIgnoreCase(propertyType) || Calendar.class.getName().equals(propertyType)) {
    		return "DateTime";
    	}
    	if (propertyType != null) {
    		if (propertyType.startsWith("java.util.") || propertyType.startsWith("java.lang.")) {
    			return getJXPathType(propertyType.substring("java.util.".length()));
    		}
    	}
    	return "Object";
	}
	
    private String getJavaType(String propertyType) {
    	if ("String".equals(propertyType) || "Long".equalsIgnoreCase(propertyType) || "Boolean".equalsIgnoreCase(propertyType) || "Double".equalsIgnoreCase(propertyType)) {
    		return propertyType;
    	}
    	if ("Int".equalsIgnoreCase(propertyType)) {
    		return "Integer";
    	}
    	if ("XiNode".equalsIgnoreCase(propertyType)) {
    		return XiNode.class.getCanonicalName();
    	}
    	if ("DateTime".equalsIgnoreCase(propertyType)) {
    		return Calendar.class.getCanonicalName();
    	}
    	if (propertyType != null) {
    		if (propertyType.startsWith("java.lang.") || propertyType.startsWith("java.util.")) {
    			return propertyType; // already is the java type, just return it
    		}
    	}
    	return null;
	}

	private String getPropertyType(BindingContext context, String propName) {
		if (context.xiNodeTarget) { // set to payload/xinode for message/ServicesTask?
			return "String"; // always String?
		}
    	if ("ref".equals(propName)) {
    		return "Concept";
    	}
    	if ("extId".equals(propName) || "ref".equals(propName)) {
    		return "String";
    	}
    	if ("Id".equals(propName)) {
    		return "Long";
    	}
    	if ("soapAction".equals(propName)) {
    		return "String";
    	}
    	Concept con = context.getEntityAsConcept();
		if (con != null) {
			if (context.getEntity().getName().equals(propName)) {
				return "Concept"; // it is a contained concept or concept reference
			}
			PropertyDefinition definition = con.getPropertyDefinition(propName, false);
			if (definition == null && context.isInputTransformation() && context.isInForLoop()) {
				return context.getForLoopInfo().getPropertyPrimitiveType();
			}
			return getConceptPropertyType(definition);
		} else if (context.getEntity() instanceof Event) {
			if ("payload".equals(propName)) {
				return "String";
			}
			EventPropertyDefinition propertyDefinition = ((Event)context.getEntity()).getPropertyDefinition(propName, true);
			return getEventPropertyType(propertyDefinition);
		} else if (context.getEntity() instanceof RuleFunction) {
			RuleFunction rf = (RuleFunction) context.getEntity();
			Symbol symbol = rf.getScope().getSymbol(propName);
			return symbol.getType();
		}
		return "";
	}

	private String getConceptPropertyType(PropertyDefinition definition) {
		int propType = definition.getType();
		String attrtypeStr = "";
		if (propType != -1) {
			if (propType == PropertyDefinition.PROPERTY_TYPE_INTEGER) {
				attrtypeStr = "Int";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
					|| propType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
				attrtypeStr = "Concept";
				Concept conceptType = definition.getConceptType();
				String cast = ModelNameUtil.GENERATED_PACKAGE_PREFIX + "." + ModelUtils.convertPathToPackage(conceptType.getFullPath());
				return cast;
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_LONG) {
				attrtypeStr = "Long";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_BOOLEAN) {
				attrtypeStr = "Boolean";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_DATETIME) {
				attrtypeStr = "DateTime";
			}else if (propType == PropertyDefinition.PROPERTY_TYPE_STRING) {
				attrtypeStr = "String";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_REAL) {
				attrtypeStr = "Double";
			} else {
				attrtypeStr = "Value";
			}
			return attrtypeStr;
		}
		return null;
	}

	private String getEventPropertyType(
			EventPropertyDefinition propertyDefinition) {
		RDFPrimitiveTerm type = propertyDefinition.getType();
		int propType = type.getTypeId();
		String attrtypeStr = "";
		if (propType == RDFTypes.LONG_TYPEID) {
			attrtypeStr = "Long";
		} else if (propType == RDFTypes.INTEGER_TYPEID) {
			attrtypeStr = "Int";
		} else if (propType == RDFTypes.BOOLEAN_TYPEID) {
			attrtypeStr = "Boolean";
		} else if (propType == RDFTypes.DATETIME_TYPEID) {
			attrtypeStr = "DateTime";
		} else if (propType == RDFTypes.STRING_TYPEID) {
			attrtypeStr = "String";
		} else if (propType == RDFTypes.DOUBLE_TYPEID) {
			attrtypeStr = "Double";
		} else if (propType == RDFTypes.CONCEPT_TYPEID 
				|| propType == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
			attrtypeStr = "Concept";
//				ownerEntity = null;//definition.getConceptType();
////				String cast = ModelNameUtil.GENERATED_PACKAGE_PREFIX + "." + ModelUtils.convertPathToPackage(ownerEntity.getFullPath());
//				String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
//				requiresCast = true;
//				newFormula.insert(0, "(("+cast+")");
		} else {
			attrtypeStr = "Value";
		}
		return attrtypeStr;
	}

	private boolean isConstantFormula(char[] chars) {
		for (int i=0; i<chars.length; i++) {
			char b = chars[i];
			if (!(Character.isLetterOrDigit(b) || b == '$' || b == '/' || b == '@' || b == '_' || b == ':')) {
				return false;
			}
		}
		return true;
	}

	private String processConstantFormula(BindingContext context, String formula,
			StringBuilder newFormula, char[] chars, String propertyType, String declVarName) {
		int start = -1;
		int end = -1;
		boolean processingXiNode = false;
		boolean foundStart = false;
		boolean foundVar = false;
		String currentVar = null;
		for (int i=0; i<chars.length; i++) {
			char b = chars[i];
			if (foundStart) {
				if (Character.isLetterOrDigit(b) || b == '/' || b == '@' || b == '_' || b == ':') {
//    					buf.append((char)b);
				} else {
					end = i;
					break;
				}
				if (b == '(') {
					
				}
				if (b == '/') {
					end = i;
					String variable = formula.substring(start, i);
					currentVar = "$"+variable;
					if ("$3zglobalVariables".equals(currentVar) || "$globalVariables".equals(currentVar)) {
						newFormula.append("com.tibco.be.functions.System.SystemHelper.getGlobalVariableAs");
						newFormula.append(propertyType);
						newFormula.append("(");
					} else {
						newFormula.append('$');
						newFormula.append(variable);
						processVariable(variable, formula, start, end, chars);
					}
					foundStart = false;
					foundVar = true;
					start = -1;
				} else if (!Character.isLetterOrDigit(b)) {
					// never found a '/', no properties to be found
				}
			}
			if (foundVar) {
				StringBuilder buf = new StringBuilder();
				start = end;
				for (int j=end; j<chars.length; j++) {
					char bt= chars[j];
					if (Character.isLetterOrDigit((char)bt) || bt == '/' || bt == '@' || bt == '_' || bt == ':') {
						buf.append((char)bt);
					} else {
						end = j;
						break;
					}
				}
				if (end <= start) {
					end = chars.length;
				}
				if ("$3zglobalVariables".equals(currentVar) || "$globalVariables".equals(currentVar)) {
					if (buf.charAt(0) == '/') {
						buf.deleteCharAt(0);
						start++;
					}
					newFormula.append('\"');
					newFormula.append(buf);
					newFormula.append("\", ");
					newFormula.append(getDefaultValue(propertyType));
					newFormula.append(")");
				} else {
					if (buf.charAt(0) == '/') {
						buf.deleteCharAt(0);
						start++;
					}
					Entity ownerEntity = null;
					String lookupVar = currentVar;
					if (lookupVar.startsWith(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX)) {
						lookupVar = currentVar.substring(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX.length());
					} else if (lookupVar.startsWith("$")) {
						lookupVar = currentVar.substring(1);
					}
					String xiNodeName = null;
					IVariableType type = typeResolver.getDeclaredIdentifierType(lookupVar);
					String path = type.getTypeName();
					if (type.isConcept()) {
						ownerEntity = ontology.getConcept(path);
					} else if (type.isEvent()) {
						ownerEntity = ontology.getEvent(path);
					} else if (XiNode.class.getCanonicalName().equals(type.getTypeName())) {
						if (!processingXiNode) {
							processingXiNode = true;
							xiNodeName = "$currXiNode"+counter++;
							newFormula.insert(0, "com.tibco.xml.datamodel.XiNode "+xiNodeName+" = (com.tibco.xml.datamodel.XiNode)");
							newFormula.append(';');
							newFormula.append('\n');
							context.getCodegenContext().setMultilineFormula(true);
							context.getCodegenContext().setMultilineVariable(xiNodeName);

						}
					}
					if (ownerEntity == null) {
						// workaround for processes
						ownerEntity = ontology.getEntity(path);
					}
					if (ownerEntity != null && "loopVar".equals(lookupVar)) {
						newFormula.insert(0, "((com.tibco.cep.runtime.model.element.Concept)");
						newFormula.append(")");
					}
					String propXslt = buf.toString();
					String[] split = propXslt.split("/");
					for (int j = 0; j < split.length; j++) {
						String segment = split[j];
						if ("@ref".equals(segment)) {
							continue;
						}
						if ("@Id".equals(segment)) {
					    	Binding parent = context.binding.getParent();
							if (parent instanceof AttributeBinding) {
					    		String propName = ((AttributeBinding) parent).getExplicitNameAVT();
					    		if ("ref".equals(propName)) {
					    			// mapping a concept reference via the "Id" attribute, continue
					    			continue;
					    		}
							}
						}
						if (type.isArray() && "elements".equals(segment)) {
							String declStr = setPropertyType(context, declVarName,
									type);
							if (declStr != null) {
								newFormula.insert(0, declStr);
								context.codegenContext.setMultilineFormula(true);
								context.codegenContext.setMultilineVariable(declVarName);
							}
							return newFormula.toString();
						}
						if (segment.charAt(0) == '@') {
							newFormula.append(".");
							newFormula.append("get");
							char[] charArray = segment.substring(1).toCharArray();
							charArray[0] = Character.toUpperCase(charArray[0]);
							newFormula.append(new String(charArray));
							newFormula.append("()");
							continue;
						} else if (segment.contains(":Envelope")) {
							context.propertyType = new PropertyTypeInfo("XiNode", false);
							processingXiNode = true;
							if (!context.getCodegenContext().isMultilineFormula()) {
								xiNodeName = "$currXiNode"+counter++;
								newFormula.insert(0, "com.tibco.xml.datamodel.XiNode "+xiNodeName+" = (com.tibco.xml.datamodel.XiNode)");
								newFormula.append(';');
								newFormula.append('\n');
								context.getCodegenContext().setMultilineFormula(true);
								context.getCodegenContext().setMultilineVariable(xiNodeName);
							}
							processSegment(context, newFormula, declVarName,
									split, xiNodeName, j, segment);
							continue;
						}
						else if ("payload".equals(segment)) {
							context.propertyType = new PropertyTypeInfo("XiNode", false);
							processingXiNode = true;
							if (context.isInForLoop()) {
								ForLoopInfo forLoopInfo = context.getForLoopInfo();
								forLoopInfo.setLocalVarType("com.tibco.xml.datamodel.XiNode");
								forLoopInfo.setXiNodeLoop(true);
							}
							newFormula.append(".");
							newFormula.insert(0, "((com.tibco.cep.runtime.model.event.EventPayload)");
							newFormula.append("getPayload()).getObject();");
							xiNodeName = "$currXiNode"+counter++;
							newFormula.insert(0, "com.tibco.xml.datamodel.XiNode "+xiNodeName+" = (com.tibco.xml.datamodel.XiNode)");
							newFormula.append('\n');
							context.getCodegenContext().setMultilineFormula(true);
							context.getCodegenContext().setMultilineVariable(xiNodeName);
							j++; // $currXiNode already is the 'root' payload object, skip the next segment
							continue;
						}
						if (processingXiNode) {
							processSegment(context, newFormula, declVarName,
									split, xiNodeName, j, segment);
							continue;
						}
						newFormula.append(".");
						newFormula.append("getProperty(\"");
						newFormula.append(segment);
						newFormula.append("\")");
						
//						newFormula.append(ModelNameUtil.MEMBER_PREFIX);
//						newFormula.append(segment);
//						newFormula.append("()");
						if (ownerEntity instanceof ProcessModel) {
							ownerEntity = ((ProcessModel) ownerEntity).cast(Concept.class);
						}
						if (ownerEntity instanceof Concept) {
							PropertyDefinition definition = ((Concept)ownerEntity).getPropertyDefinition(segment, false);
							Entity newOwner = processPropertyDefinition(context, newFormula, definition, declVarName);
							if (newOwner != null) {
								ownerEntity = newOwner;
							}
						} else if (ownerEntity instanceof Event) {
							EventPropertyDefinition definition = ((Event)ownerEntity).getPropertyDefinition(segment, true);
							processEventPropertyDefinition(context, newFormula, definition, declVarName);
						} else {
							throw new UnsupportedXsltMappingException("Cannot process constant formula (null entity, possibly an undefined variable reference): "+formula);
						}
					}
				}
				
				foundVar = false;
				currentVar = null;
			}
			
			if (b == '$') {
				start = i+1;
				foundStart = true;
			}
		}
		if (foundStart) {
			// never found a '/', no properties to be found, set to a variable value
		}

		return newFormula.toString();
	}

	protected void processSegment(BindingContext context,
			StringBuilder newFormula, String declVarName, String[] split,
			String xiNodeName, int j, String segment) {
		int idx = segment.indexOf(':');
		if (idx >= 0) {
			String[] nameSplit = segment.split(":");
			String ns = getNamespace(context, nameSplit[0]);
			String locName = nameSplit[1];
			if (j == split.length-1 && declVarName != null) {
				newFormula.append("com.tibco.xml.datamodel.XiNode[] ");
				newFormula.append(declVarName);
				newFormula.append(" = com.tibco.xml.XiNodeUtilities.getChildNodes("+xiNodeName+", \""+ns+"\", \""+locName+"\");");
			} else {
				newFormula.append(xiNodeName+" = com.tibco.xml.XiNodeUtilities.getChildNode("+xiNodeName+", \""+ns+"\", \""+locName+"\");");
			}
		} else {
			if (j == split.length-1 && declVarName != null) {
				newFormula.append("com.tibco.xml.datamodel.XiNode[] ");
				newFormula.append(declVarName);
				newFormula.append(" = com.tibco.xml.XiNodeUtilities.getChildNodes("+xiNodeName+", \"com.tibco.xml.XiNodeUtilities.NO_NAMESPACE\", \""+segment+"\");");
			} else {
				newFormula.append(xiNodeName+" = com.tibco.xml.XiNodeUtilities.getChildNode("+xiNodeName+", \"com.tibco.xml.XiNodeUtilities.NO_NAMESPACE\", \""+segment+"\");");
			}
		}
		newFormula.append('\n');
	}

	private String getNamespace(BindingContext context, String prefix) {
		Binding binding = context.binding;
		while (binding != null) {
			NamespaceDeclaration[] namespaceDeclarations = binding.getElementInfo().getNamespaceDeclarations();
			for (NamespaceDeclaration nsDecl : namespaceDeclarations) {
				if (prefix.equals(nsDecl.getPrefix())) {
					return nsDecl.getNamespace();
				}
			}
			binding = binding.getParent();
		}
		return XiNodeUtilities.NO_NAMESPACE;
	}

	protected String setPropertyType(BindingContext context, String declVarName, IVariableType type) {
		if (type.isConcept()) {
			context.propertyType = new PropertyTypeInfo("Concept", false);
			return "com.tibco.cep.runtime.model.element.Concept[] " + declVarName + " = ";
		} else if (type.isEvent()) {
			// TODO
			return "";
		} else if (type.isPrimitive()) {
			String javaType = type.getTypeName();
			if (type.isTypeRequiresBox()) {
				javaType = getJavaType(type.getTypeName());
			}
			char[] charArr = type.getTypeName().toCharArray();
			if (type.getTypeName().indexOf('.') == -1) {
				// the type is fully qualified, no need to upper case
				charArr[0] = Character.toUpperCase(charArr[0]);
			}
			context.propertyType = new PropertyTypeInfo(new String(charArr), false);
			return javaType + "[] " + declVarName + " = ";
		}
		return null;
	}

	private String getDefaultValue(String propertyType) {
		if ("String".equals(propertyType)) {
			return "\"\"";
		} else if ("Long".equals(propertyType)) {
			return "0";
		} else if ("Int".equals(propertyType)) {
			return "0";
		} else if ("Double".equals(propertyType)) {
			return "0";
		} else if ("Boolean".equals(propertyType)) {
			return "false";
		}
		return "\"\"";
	}

	private Entity processPropertyDefinition(BindingContext context, StringBuilder newFormula,
			PropertyDefinition definition, String declVarName) {
		Entity ownerEntity = null;
		int propType = definition.getType();
		String attrtypeStr = "";
		boolean requiresCast = true;
		String cast = definition.isArray() ? "com.tibco.cep.runtime.model.element.PropertyArray" : "com.tibco.cep.runtime.model.element.PropertyAtom";
		if (propType != -1) {
			if (propType == PropertyDefinition.PROPERTY_TYPE_INTEGER) {
				attrtypeStr = "Int";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
					|| propType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
				attrtypeStr = "Concept";
				ownerEntity = definition.getConceptType();
//				String cast = ModelNameUtil.GENERATED_PACKAGE_PREFIX + "." + ModelUtils.convertPathToPackage(ownerEntity.getFullPath());
//				requiresCast = true;
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_LONG) {
				attrtypeStr = "Long";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_BOOLEAN) {
				attrtypeStr = "Boolean";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_DATETIME) {
				attrtypeStr = "DateTime";
			}else if (propType == PropertyDefinition.PROPERTY_TYPE_STRING) {
				attrtypeStr = "String";
			} else if (propType == PropertyDefinition.PROPERTY_TYPE_REAL) {
				attrtypeStr = "Double";
			} else {
				attrtypeStr = "Value";
			}
		}
		if (requiresCast) {
			cast += attrtypeStr;
			newFormula.insert(0, "(("+cast+")");
			newFormula.append(")");
			requiresCast = false;
		}
		if (definition.isArray()) {
			if (declVarName != null) {
				context.propertyType = new PropertyTypeInfo(attrtypeStr, true);
				StringBuilder declBuffer = new StringBuilder();
				declBuffer.append("com.tibco.cep.runtime.model.element.PropertyAtom[] ");
				declBuffer.append(declVarName);
				declBuffer.append(" = ");
				newFormula.insert(0, declBuffer);
				context.codegenContext.setMultilineFormula(true);
				context.codegenContext.setMultilineVariable(declVarName);
			}
			newFormula.append(".toArray()"); // will convert PropertyArray to PropertyAtom[]
		} else {
			if (declVarName != null) {
				context.propertyType = new PropertyTypeInfo(attrtypeStr, true);
				// not sure why this is here, but it would never work as 'cast' is not surrounded by parens.  Still, keep it here commented out in case of a regression...
//				StringBuilder declBuffer = new StringBuilder();
//				declBuffer.append(cast);
//				declBuffer.append(declVarName);
//				declBuffer.append(" = ");
//				newFormula.insert(0, declBuffer);
			}
			newFormula.append(".get");
			newFormula.append(attrtypeStr);
			newFormula.append("()");
		}
		return ownerEntity;
	}

	private Entity processEventPropertyDefinition(BindingContext context, StringBuilder newFormula,
			EventPropertyDefinition definition, String declVarName) {
		Entity ownerEntity = null;
		RDFPrimitiveTerm propertyType = definition.getType();
		int propType = propertyType.getTypeId();
		String attrtypeStr = "";
		boolean requiresCast = false;
		if (propType == RDFTypes.LONG_TYPEID) {
			attrtypeStr = "Long";
		} else if (propType == RDFTypes.INTEGER_TYPEID) {
			attrtypeStr = "Int";
		} else if (propType == RDFTypes.BOOLEAN_TYPEID) {
			attrtypeStr = "Boolean";
		} else if (propType == RDFTypes.DATETIME_TYPEID) {
			attrtypeStr = "DateTime";
		} else if (propType == RDFTypes.STRING_TYPEID) {
			attrtypeStr = "String";
		} else if (propType == RDFTypes.DOUBLE_TYPEID) {
			attrtypeStr = "Double";
		} else if (propType == RDFTypes.CONCEPT_TYPEID 
				|| propType == RDFTypes.CONCEPT_REFERENCE_TYPEID) {
			attrtypeStr = "Concept";
			ownerEntity = null;//definition.getConceptType();
//			String cast = ModelNameUtil.GENERATED_PACKAGE_PREFIX + "." + ModelUtils.convertPathToPackage(ownerEntity.getFullPath());
			String cast = "com.tibco.cep.runtime.model.element.PropertyAtomConcept";
			requiresCast = true;
			newFormula.insert(0, "(("+cast+")");
		} else {
			attrtypeStr = "Value";
		}
		if (requiresCast) {
			newFormula.append(")");
			requiresCast = false;
		}
//		newFormula.append(".get");
//		newFormula.append(attrtypeStr);
//		newFormula.append("()");
		return ownerEntity;
	}
	
	private void processVariable(String variable, String formula, int start,
			int end, char[] chars) {
		
	}

    private String processJXPathFormula(String propertyType, String formula, StringBuilder newFormula,
    		char[] chars, String declVarName, BindingContext context) {
		newFormula.append(BRK);
		
//		String namespaces = getNamespaces();
		List<String> vars = getVariables(chars);
//		StringBuilder variables = new StringBuilder();
//		for (String var : vars) {
//			variables.append("<variable>");
//			variables.append(var);
//			variables.append("</variable>");
//		}
		if (context.isInForLoop() && context.propertyType == null) {
			context.propertyType = getPropertyTypeFromJXPath(context, formula);
		}
		newFormula.append("com.tibco.be.functions.xpath.JXPathHelper.evalXPathAs");
		String primType = getJXPathType(propertyType);
		newFormula.append(primType);
		newFormula.append("(");
		formula = formula.replaceAll("\"", "\\\\\"");
		String xpath = "\""+formula+"\"";
		newFormula.append(xpath);
		boolean emptyArr = false;
		if (context.isInForLoop()) {
			// always add $current to args, so it is not an empty array
		} else {
			emptyArr = vars.size() == 0 || vars.size() == 1 && "globalVariables".equals(vars.get(0));
		}
		if (emptyArr) {
			newFormula.append(", new String[0]");
		} else {
			newFormula.append(", new String[] {");
			for (int i=0; i<vars.size(); i++) {
				if ("globalVariables".equals(vars.get(i))) {
					if (i == vars.size()-1) {
						if (newFormula.charAt(newFormula.length()-1) == ',') {
							newFormula.deleteCharAt(newFormula.length()-1); // remove trailing comma
						}
					}
					continue;
				}
				newFormula.append("\"");
				newFormula.append(vars.get(i));
				newFormula.append("\"");
				if (i<vars.size()-1) {
					newFormula.append(',');
				}
			}
		}
		if (context.isInForLoop()) {
			if (vars.size() > 0) {
				newFormula.append(',');
			}
			newFormula.append("\"");
			newFormula.append("$current"); // pick a well known var name?
			newFormula.append("\"");
		}
		if (!emptyArr) {
			newFormula.append("}");
		}
		newFormula.append(",");
		if (emptyArr) {
			newFormula.append("new Object[0] ");
		} else {
			newFormula.append("new Object[] {");
			for (int i=0; i<vars.size(); i++) {
				if ("globalVariables".equals(vars.get(i))) {
					if (i == vars.size()-1) {
						if (newFormula.charAt(newFormula.length()-1) == ',') {
							newFormula.deleteCharAt(newFormula.length()-1); // remove trailing comma
						}
					}
					continue;
				}
				newFormula.append(boxUnboxed + "(");
				String varName = vars.get(i);
				if (("$"+varName).equals(tempVarName)) {
					// FIXME : Don't like special casing this
					newFormula.append("$");
				} else {
					if (context.isInForLoop()) {
						String loopVarName = context.getForLoopInfo().getLocalVarName();
						if (("_$"+varName).equals(loopVarName)) {
							newFormula.append("_$");
						} else {
							newFormula.append(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX);
						}
					} else {
						newFormula.append(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX);
					}
				}
				newFormula.append(varName);
				newFormula.append(")");
				if (i<vars.size()-1) {
					newFormula.append(",");
				}
			}
		}
		if (context.isInForLoop()) {
			if (vars.size() > 0) {
				newFormula.append(',');
			}
			newFormula.append(boxUnboxed + "(");
			newFormula.append(context.getForLoopInfo().getLocalVarName());
			newFormula.append(")");
		}
		if (!emptyArr) {
			newFormula.append("}");
		}
		newFormula.append(")");
		return newFormula.toString();
	}
    
//	private String processXPathFormula(String propertyType, String formula, StringBuilder newFormula,
//			byte[] bytes) {
//		newFormula.append(BRK);
//		
//		String namespaces = getNamespaces();
//		List<String> vars = getVariables(bytes);
//		StringBuilder variables = new StringBuilder();
//		for (String var : vars) {
//			variables.append("<variable>");
//			variables.append(var);
//			variables.append("</variable>");
//		}
//
//		newFormula.append("com.tibco.be.functions.xpath.XPathHelper.evalAs");
//		newFormula.append(propertyType);
//		newFormula.append("2(");
//		formula = formula.replaceAll("\"", "\\\\\"");
//		String xpath = MessageFormat.format(XPATH_TEMPLATE, formula, namespaces, variables.toString());
//		newFormula.append(xpath);
//		newFormula.append(", com.tibco.be.model.functions.VariableListImpl.newVariableListImpl(");
//		newFormula.append("new String[] {");
//		for (int i=0; i<vars.size(); i++) {
//			newFormula.append("\"");
//			newFormula.append(vars.get(i));
//			newFormula.append("\"");
//			if (i<vars.size()-1) {
//				newFormula.append(',');
//			}
//		}
//		
//		newFormula.append("},");
//		newFormula.append("new Object[] {");
//		for (int i=0; i<vars.size(); i++) {
//			newFormula.append(boxUnboxed + "(");
//			newFormula.append(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX);
//			newFormula.append(vars.get(i));
//			newFormula.append(")");
//			if (i<vars.size()-1) {
//				newFormula.append(",");
//			}
//		}
//		newFormula.append("}))");
//		return newFormula.toString();
//	}

	private List<String> getVariables(char[] chars) {
		List<String> vars = new ArrayList<String>();
		boolean foundVar = false;
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<chars.length; i++) {
			int b = chars[i];
			if (b == '$') {
				foundVar = true;
				continue;
			}
			if (!(Character.isLetterOrDigit((int)b) || b == '_')) {
				if (foundVar) {
					foundVar = false;
					String newVar = buf.toString();
//					if (!vars.contains(newVar)) {
						vars.add(newVar);
//					}
					buf = new StringBuilder();
					continue;
				}
			}
			if (foundVar) {
				buf.append((char)b);
			}
		}
		
		return vars;
	}

	private String getNamespaces() {
		return "";
	}

	public void setTypeResolver(IVariableTypeResolver typeResolver) {
		this.typeResolver = typeResolver;
	}

	public void setInputTransformation(boolean b) {
		this.inputTransformation  = b;
	}

	public void setPsuedoOutputTransformation(boolean psuedoOutputTransformation) {
		this.psuedoOutputTransformation = psuedoOutputTransformation;
	}

	public void setLoopTask(boolean loopTask) {
		this.loopTask = loopTask;
	}

	public void setJavaTask(boolean javaTask) {
		this.javaTask = javaTask;
	}

}
