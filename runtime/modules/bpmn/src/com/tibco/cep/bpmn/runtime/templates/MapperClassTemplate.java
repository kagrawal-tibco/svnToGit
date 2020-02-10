package com.tibco.cep.bpmn.runtime.templates;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.antlr.stringtemplate.AutoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.runtime.activity.EventApplicable;
import com.tibco.cep.bpmn.runtime.activity.Gateway;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.events.AbstractTriggerEvent;
import com.tibco.cep.bpmn.runtime.activity.events.EndEvent;
import com.tibco.cep.bpmn.runtime.activity.tasks.CallActivityTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.DecisionTableTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.InferenceTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.LoopTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.LoopTask.LoopCharacteristics;
import com.tibco.cep.bpmn.runtime.activity.tasks.LoopTask.LoopVarType;
import com.tibco.cep.bpmn.runtime.activity.tasks.LoopTask.MultiInstanceLoopCharacteristics;
import com.tibco.cep.bpmn.runtime.activity.tasks.ProcessJavaTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.RuleFunctionTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.SendTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.ServiceTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.SubProcessTask;
import com.tibco.cep.bpmn.runtime.utils.TaskFunctionModel;
import com.tibco.cep.bpmn.runtime.utils.TaskFunctionModel.PROPERTY_TYPE;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.mapper.codegen.CommonXsltCodeGenerator;
import com.tibco.cep.mapper.codegen.IVariableTypeResolver;
import com.tibco.cep.mapper.codegen.VariableType;
import com.tibco.cep.mapper.codegen.XiNodeVariableType;
import com.tibco.cep.mapper.codegen.XsltCodegenContext;

/*
* Author: Suresh Subramani / Date: 1/4/12 / Time: 4:52 PM
*/
public class MapperClassTemplate {
	
	/**
	 * @author pdhar
	 * Bean class to hold mapper codegen data
	 *
	 */
	public class MapperInfo { 
		String name;
		public String inputCode;
		public String outputCode;
		public Map<String,VariableType> outputSymbolMap;
		public Map<String,VariableType> inputSymbolMap;
		public boolean isLoop = false;
		public String getInputCode() { return inputCode; }
		public Set<Entry<String, VariableType>> getInputEntries() { return inputSymbolMap.entrySet(); }
		public Map<String,VariableType> getInputSymbolMap() { return inputSymbolMap; }
		public String getName() { return name; }		
		public String getOutputCode() { return outputCode; }		
		public Set<Entry<String, VariableType>> getOutputEntries() { return outputSymbolMap.entrySet(); }
		public Map<String,VariableType> getOutputSymbolMap() {	return outputSymbolMap; }
		//// Gateway
		public void setLoop() { isLoop = true;}
		public boolean isLoop() { return isLoop; }
		
	}
	private class SymbolMap implements IVariableTypeResolver {

		LinkedHashMap<String,VariableType> map = new LinkedHashMap<String,VariableType>();

		public SymbolMap() {
			// TODO Auto-generated constructor stub
		}
		
		public void add(String var, VariableType type) {
			map.put(var, type);
		}

		@Override
		public VariableType getDeclaredIdentifierType(String id) {
			return map.get(id);
		}
		
		public LinkedHashMap<String,VariableType> getSymbolMap() {
			return map;
		}
		
		public String toString() {
			return map.toString();
		}

	}
	
	
	protected Task contextTask;
	protected InitContext initContext;
	protected ByteArrayOutputStream mapperTransformStream;
	protected ByteArrayOutputStream xsltOutputTransformStream;
	private StringTemplateGroup stgroup;
	
	private StringTemplate mapperClassTemplate;
    private ProcessModel processModel;



    private static final String[] PRIMITIVE_NAMES = { "int", "long", "double", "boolean", "String", "DateTime", "null", "void", "Object" };
	
    private static Logger logger = LogManagerFactory.getLogManager().getLogger(MapperClassTemplate.class);



    public MapperClassTemplate(ProcessModel m) {
    	this.processModel = m;
    }
    
    public Task getTask() {
		return contextTask;
	}
    
    public ProcessModel getProcessModel() {
		return processModel;
	}
    /**
     * @throws Exception
     */
    public void generate() throws Exception {
    	Task t = getTask();
        if (!(t instanceof Gateway)) {
            generateTask();
        }
    }

   



	public void generateTask() throws Exception
    {
		Task task = null;
		if(contextTask instanceof LoopTask) {
			task = ((LoopTask)contextTask).getLoopTask();
		} else {
			task = contextTask;
		}
    	String inputXslt = task.getInputMapperString();
    	String outputXslt = task.getOutputMapperString();
    	
    	MapperInfo mapperInfo = new MapperInfo();
    	
    	if(contextTask instanceof LoopTask){
    		mapperInfo.setLoop();
    	}
    	
    	String name  = getMapperClassName();
    	SymbolMap symTable = new SymbolMap();
    	Entity c = processModel.cast(Concept.class);
    	
    	String[] vars = new String[0];
    	VariableType[] types = new VariableType[0];
    	boolean isCreate = false;
    	boolean createRoot = false;

    	if(contextTask instanceof LoopTask) {
    		addLoopVars(symTable);
    	}

		String tempVarName = MapperConstants.TEMP_VAR_JOB;
        if (task instanceof EventApplicable) {        	
			Event event = ((EventApplicable)task).getInputEvent();
			if(event != null) {
				c = event;
				String varName = event.getName();
				VariableType varType = new VariableType(event.getFullPath(), false, true, event.getType() == EVENT_TYPE.TIME_EVENT_VALUE, false, false);
				vars = new String[]	 { varName };
				types = new VariableType[] { varType };
				if (task instanceof EndEvent) {
					isCreate = true;
					createRoot = true;
				}
			}
        } else if(task instanceof ProcessJavaTask ){
        	TaskFunctionModel fm = ((ProcessJavaTask)task).getFunctionModel();
			for (TaskFunctionModel.TypeSymbol argType : fm.getArgTypes()) {

				boolean isPrimitive = argType.isPrimitive();
				boolean isArray = argType.isArray();
				VariableType v = new VariableType(getPrimitiveType(argType.getPropertyType()), false, false, false, false, isArray);
				symTable.add(argType.getName(), v);
			}
        	
        } else if (task instanceof RuleFunctionTask) {
			RuleFunction rulefunction = ((RuleFunctionTask) task).getRulefunction();
			c = rulefunction;
		} else if (task instanceof DecisionTableTask) {
			RuleFunction rulefunction = ((DecisionTableTask) task).getVrfFunction();
			c = rulefunction;
		} else if(task instanceof SubProcessTask) {
			SubProcessTask subprocess = (SubProcessTask) task;
			SubProcessModel spm = processModel.getSubProcessById(subprocess.getName());
			c = spm.cast(Concept.class);
			tempVarName = "$"+spm.getName();
			symTable.add(tempVarName, new VariableType(spm.getFullPath(), true, false, false, true, false));
			isCreate = true;
		} else if(task instanceof CallActivityTask) {
			CallActivityTask ctask = (CallActivityTask) task;
			ProcessModel pm = ctask.getCalledProcess();
			c = pm.cast(Concept.class);
			symTable.add(MapperConstants.PROCESS_URI, new VariableType(RDFTypes.STRING.getName(),false,false, false, false, false));
			symTable.add(MapperConstants.PROCESS, new VariableType(pm.getFullPath(), true, false, false, true, false));
			tempVarName = "$"+MapperConstants.PROCESS;
			isCreate = true;
		} else if ( task instanceof InferenceTask ) {
			InferenceTask itask = (InferenceTask) task;
			// rule uri's are separated by ';'
			String ruleUris = itask.getRuleUris();
			symTable.add(MapperConstants.RULES, new VariableType(RDFTypes.STRING.getName(),false,false, false, false, false));	
			tempVarName = MapperConstants.RULES;
		}
        
       
		for(int i=0; i < vars.length;i++) {
			symTable.add(vars[i], types[i]);
		}
		
		symTable.add(MapperConstants.JOB, new VariableType(processModel.getFullPath(), true, false, false, true, false));
        
		CommonXsltCodeGenerator generator = new CommonXsltCodeGenerator(processModel.getOntology(), symTable);
		generator.setBaseEntity(c);
		generator.setTempVarName(tempVarName);
		
        ////////////////////////////////////INPUT MAP////////////////////////////////////////
		String xsltString = inputXslt;
		if (xsltString == null && task instanceof SendTask) {
			// BE-19926 : If there is no mapping for the SendEvent task, create an empty one so that the (empty) event is still created and sent
			Event event = ((EventApplicable)task).getInputEvent();
			if(event != null) {
				ArrayList<String> params = new ArrayList<String>();
				params.add(c.getFullPath());
				xsltString = XSTemplateSerializer.serialize(null, params, new ArrayList());
			}
		}
		if (xsltString != null) {
			StringBuilder buffer = new StringBuilder();
			try {
    			generator.setLoopTask(contextTask instanceof LoopTask);
    			generator.setJavaTask(task instanceof ProcessJavaTask);
				generator.setInputTransformation(true);
				if (task instanceof EventApplicable) {
					generator.setTempVarName(ModelNameUtil.SCOPE_IDENTIFIER_PREFIX+((EventApplicable)task).getInputEvent().getName());
					generator.setInputTransformation(false);
					generator.setPsuedoOutputTransformation(true);
				}
				if (task instanceof SendTask) {
					isCreate = true;
					createRoot = true;
				}
				if (task instanceof CallActivityTask || task instanceof SubProcessTask) {
					generator.setInputTransformation(false);
					generator.setPsuedoOutputTransformation(true);
					// declare default process template and process variables
					String procURI = null;
					if (task instanceof CallActivityTask) {
						procURI = ((CallActivityTask) task).getCalledProcess().getFullPath();
					} else if (task instanceof SubProcessTask) {
						procURI = c.getFullPath();
					}
					String nl = String.format("%n");
	    			buffer.append("com.tibco.cep.bpmn.runtime.templates.ProcessTemplate $template = ").append(nl)
    				.append("com.tibco.cep.bpmn.runtime.templates.ProcessTemplateRegistry.getInstance().getProcessTemplateFromURI(").append(nl)
    				.append("com.tibco.cep.runtime.session.RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProject().getOntology(), ").append(nl)
    				.append("\"")
    				.append(procURI)
    				.append("\");").append(nl);
	    			
	    			buffer.append("com.tibco.cep.bpmn.runtime.model.JobContext ")
	    			.append(tempVarName)
	    			.append(" = $template.newProcessData();").append(nl);
				}
				generator.processXsltString(xsltString, new XsltCodegenContext(isCreate, createRoot, true), buffer);
				if (task instanceof CallActivityTask || task instanceof SubProcessTask) {
					String nl = String.format("%n");
					String varName =  MapperConstants.PROCESS;
					buffer.append("$vars.setVariable(\"").append(varName).append("\", ").append(tempVarName).append(");").append(nl);
				}
				logger.log(Level.DEBUG, String.format("*************Generated code for input %s *************", task.getClass().getName()));
				logger.log(Level.DEBUG, buffer.toString());
				logger.log(Level.DEBUG, "*************End generated code for input*************");
    			mapperInfo.inputCode = buffer.toString();
    			mapperInfo.inputSymbolMap = symTable.getSymbolMap();
			} catch (Exception e) {
				mapperInfo.inputSymbolMap = symTable.getSymbolMap();
				logger.log(Level.ERROR, e, "Error while generating input transformation code");
			}
		}

		tempVarName = MapperConstants.TEMP_VAR_JOB;
		generator.setTempVarName(tempVarName);
		//////////////////////////////////// OUTPUT MAP////////////////////////////////////////
		symTable.getSymbolMap().clear(); // need to clear this (do not recreate), output transformation needs its own variables 
		// re-add the event applicable args
		for(int i=0; i < vars.length;i++) {
			symTable.add(vars[i], types[i]);
		}

		// re-add loop vars
		if(contextTask instanceof LoopTask) {
			addLoopVars(symTable);
		}

    	xsltString = outputXslt;
    	if (xsltString != null) {
    		c = processModel.cast(Concept.class);
    		
    		if(task instanceof ProcessJavaTask) {
    			TaskFunctionModel fm = ((ProcessJavaTask)task).getFunctionModel();
    			TaskFunctionModel.TypeSymbol retType = fm.getReturnType();
    			vars = new String[]	 { MapperConstants.RETURN };
				if (retType != null) {
					boolean isPrimitive = retType.isPrimitive();
					boolean isArray = retType.isArray();
					if(isPrimitive || retType.getUri()== null) {
						VariableType v = new VariableType(getPrimitiveType(retType.getPropertyType()), false, false, false, false, isArray);
						v.setTypeRequiresBox(!isArray);
						types = new VariableType[] { v };
					} else {
						Entity entity = processModel.getOntology().getEntity(retType.getUri());
        				generator.setBaseEntity(entity);
        				boolean isEvent = entity instanceof Event;
        				boolean isTimeEvent = false;
        				if (isEvent) {
        					isTimeEvent = ((Event)entity).getType() == EVENT_TYPE.TIME_EVENT_VALUE;
        				}
        				boolean isConcept = entity instanceof Concept;
        				boolean isProcess = entity instanceof ProcessModel;
            			VariableType varType = new VariableType(retType.getUri(), isConcept, isEvent, isTimeEvent, isProcess, isArray);
            			types = new VariableType[] { varType };
					}
					
					for(int i=0; i < vars.length;i++) {
        				symTable.add(vars[i], types[i]);
        			}

				}
    			
    		} else if (task instanceof RuleFunctionTask) {
    			RuleFunction rulefunction = ((RuleFunctionTask) task).getRulefunction();
    			String ret = rulefunction.getReturnType();
    			if(ret != null){
    				boolean isArray = ret.endsWith("[]");
    				if (isArray) {
    					ret = ret.substring(0, ret.length()-2);
    				}
    				vars = new String[]	 { MapperConstants.RETURN };
        			if (isPrimitiveType(ret)) {
            			VariableType varType = new VariableType(ret, false, false, false, false, isArray);
            			varType.setTypeRequiresBox(!isArray || "DateTime".equalsIgnoreCase(ret));
            			types = new VariableType[] { varType };
        			} else {
        				Entity entity = processModel.getOntology().getEntity(ret);
        				generator.setBaseEntity(entity);
        				boolean isEvent = entity instanceof Event;
        				boolean isTimeEvent = false;
        				if (isEvent) {
        					isTimeEvent = ((Event)entity).getType() == EVENT_TYPE.TIME_EVENT_VALUE;
        				}
        				boolean isConcept = entity instanceof Concept;
        				boolean isProcess = entity instanceof ProcessModel;
            			VariableType varType = new VariableType(ret, isConcept, isEvent, isTimeEvent, isProcess, isArray);
            			types = new VariableType[] { varType };
        			}
        			for(int i=0; i < vars.length;i++) {
        				symTable.add(vars[i], types[i]);
        			}
    			}
				generator.setTypeResolver(symTable);
    		} else	if(task instanceof SubProcessTask) {
    			SubProcessTask subprocess = (SubProcessTask) task;
    			SubProcessModel spm = processModel.getSubProcessById(subprocess.getName());
    			symTable.add(spm.getName(), new VariableType(spm.getFullPath(), true, false, false, true, false));
    			symTable.add(MapperConstants.RETURN, new VariableType(spm.getFullPath(), true, false, false, true, false));
    		} else if(task instanceof CallActivityTask) {
    			CallActivityTask ctask = (CallActivityTask) task;
    			ProcessModel pm = ctask.getCalledProcess();    			
    			symTable.add(pm.getName(), new VariableType(pm.getFullPath(),true,false, false, true, false));
    			VariableType retVarType = new VariableType(pm.getFullPath(), true, false, false, true, false);
    			retVarType.setTypeRequiresBox(!retVarType.isArray());
    			symTable.add(MapperConstants.RETURN, retVarType);
    		} else if (task instanceof ServiceTask) {
    			symTable.add(ServiceTask.Message_Node_Name, new XiNodeVariableType());
    		}
    		
    		generator.setBaseEntity(c);
    		StringBuilder buffer = new StringBuilder();
    		isCreate = task instanceof AbstractTriggerEvent || task instanceof RuleFunctionTask || task instanceof ProcessJavaTask;
    		createRoot = false;
    		try {
    			generator.setLoopTask(contextTask instanceof LoopTask);
    			generator.setJavaTask(contextTask instanceof ProcessJavaTask);
    			generator.setInputTransformation(false);
				generator.setPsuedoOutputTransformation(false);
    			generator.processXsltString(xsltString, new XsltCodegenContext(isCreate, createRoot, true), buffer);
    			logger.log(Level.DEBUG, String.format("*************Generated code for output %s *************", task.getClass().getName()));
				logger.log(Level.DEBUG, buffer.toString());
				logger.log(Level.DEBUG, "*************End generated code for output*************");
    			mapperInfo.outputCode = buffer.toString();
    			mapperInfo.outputSymbolMap = symTable.getSymbolMap();
    			
			} catch (Exception e) {
				mapperInfo.outputSymbolMap = symTable.getSymbolMap();
				logger.log(Level.ERROR, e, "Error while generating output transformation code");
			}
    	}
		
    	this.mapperClassTemplate.reset();
    	mapperInfo.name = name;
    	this.mapperClassTemplate.setAttribute("mapperInfo", mapperInfo);
//    	System.out.println("template:\n"+this.mapperClassTemplate.toString());
    	OutputStreamWriter mapperStreamWriter = new OutputStreamWriter(mapperTransformStream,MapperConstants.ENCODING);
    	this.mapperClassTemplate.write(new AutoIndentWriter(mapperStreamWriter));
    	mapperStreamWriter.flush();
    	mapperStreamWriter.close();
    	this.mapperTransformStream.flush();
    	this.mapperTransformStream.close();
    }

	public void addLoopVars(SymbolMap symTable) {
		LoopTask lTask = (LoopTask) contextTask;
		symTable.add(MapperConstants.LOOP_COUNTER, new VariableType(RDFTypes.INTEGER.getName(),false,false,false,false,false));
		LoopCharacteristics lc = lTask.getLoopCharacteristics();
		symTable.add(MapperConstants.LOOP_MAX, new VariableType(RDFTypes.INTEGER.getName(),false,false,false,false,false));
		if(lc instanceof MultiInstanceLoopCharacteristics){
			MultiInstanceLoopCharacteristics mlc = (MultiInstanceLoopCharacteristics) lc;
			LoopVarType loopVarType = mlc.getLoopVarType(); //fix this with actual Type object
			String type = loopVarType.getType();
			boolean isConcept = loopVarType.isConcept();
			symTable.add(MapperConstants.LOOP_VAR, new VariableType(type,isConcept,false,false,false,false));
		}
	}

	

	private String getPrimitiveType(PROPERTY_TYPE propertyType) {
		switch (propertyType) {
		case BOOLEAN:
		case BOOLEAN_WRAP:
			return "boolean";

		case DOUBLE:
		case DOUBLE_WRAP:
			return "double";
			
		case INTEGER:
		case INTEGER_WRAP:
			return "int";
			
		case LONG:
		case LONG_WRAP:
			return "long";
			
		case STRING:
			return "String";
			
		case DATE_TIME:
			return Calendar.class.getName();
			
		case CONCEPT:
		case CONCEPT_REFERENCE:
			return "Concept";
		default:
			break;
		}
		return null;
	}

	private String getSubProcessName(String name) {
		String subProcessName = name;
		final int index = name.lastIndexOf("."); //$NON-NLS-1$
		if(index != -1) {
			subProcessName = name.substring(index+1);
		}
		return subProcessName;
	}

	public Class getMapperClass() {
        String className = String.format(MapperConstants.MAPPER_NAME_FORMAT, initContext.getProcessTemplate().getProcessName().getJavaClassName(), getMapperClassName()) ;
        ClassLoader classLoader = initContext.getProcessAgent().getRuleServiceProvider().getClassLoader();

        try {
            Class klazz = Class.forName(className, true, classLoader);
            return klazz;
        }
        catch (Throwable t) {
            logger.log(Level.ERROR, t, "Class not found");
        }
        return null;
    }

	public String getMapperClassName() {
		Task task = null;
		if(contextTask instanceof LoopTask) {
			task = ((LoopTask)contextTask).getLoopTask();
		} else {
			task = contextTask;
		}
    	String qualifiedName = task.getName();
        return qualifiedName.substring(qualifiedName.lastIndexOf(".")+1).replaceAll("\\.", "_");
    }

    public byte[] getMapperTransformationSnippet() {
        return mapperTransformStream.toByteArray();
    }

    public void init(Task task) {
    	this.contextTask = task;
        this.initContext = task.getInitContext();
        this.stgroup = new StringTemplateGroup(new InputStreamReader(getClass().getResourceAsStream(MapperConstants.MAPPER_STRING_TEMPLATE_URI)));

        //TODO : When mapper comes in for Gateways - then we can use a different template
        if (task instanceof Gateway) {
            this.mapperClassTemplate = stgroup.getInstanceOf(MapperConstants.MAPPER_CLASS);
        }
        else {
            this.mapperClassTemplate = stgroup.getInstanceOf(MapperConstants.MAPPER_CLASS);
        }

		mapperTransformStream = new ByteArrayOutputStream();
		xsltOutputTransformStream = new ByteArrayOutputStream();

    }



	private boolean isPrimitiveType(String symbolType) {
		for (int i=0; i<PRIMITIVE_NAMES.length; i++) {
			if (symbolType.equals(PRIMITIVE_NAMES[i])) {
				return true;
			}
		}
		return false;
	}
	


}
