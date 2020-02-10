package com.tibco.cep.studio.debug.core.model.var;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ClassObjectReference;
import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StringReference;
import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class RuleDebugVariableFactory {

	// This constants are used to identify the types for common rows such as
	// "type", "properties" etc
	public static final int ROWTYPE_CONCEPT = 1;
	public static final int ROWTYPE_EVENT = 2;

	public RuleDebugVariableFactory() {
	}

	/**
	 * main factory method to get top level local variables
	 * @param frame
	 * @param tinfo
	 * @param localVar
	 * @return
	 */
	public static AbstractDebugVariable getVarRow(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, LocalVariable localVar) throws DebugException {
		final Value jdiValue = frame.getFrame().getValue(localVar);
		final String varName = localVar.name();
		AbstractDebugVariable var = null;
		if (jdiValue instanceof ObjectReference) {
			if (frame.getVarMap().containsKey(jdiValue)) {
				AbstractDebugVariable mapVar = frame.getVarMap().get(jdiValue);
				if (varName.equals(mapVar.getName())) {
					return mapVar;
				}
			}
			List<IRuleDebugVariableProvider> providers = getVariableProviders();
			for(IRuleDebugVariableProvider provider:providers) {
				if(provider.canProvide((ObjectReference) jdiValue)) {
					var = provider.getVariable(frame, tinfo, localVar, (ObjectReference) jdiValue, varName);
					if(var != null){
						break;
					}
				}
			}
			if(var == null) {
				// Default
				StudioDebugCorePlugin.debug("Returning the default VarRow - " + varName);
				var = new RuleDebugLocalVariable(frame, tinfo, localVar);
			}
			
//			if (DebuggerSupport.isConcept((ObjectReference) jdiValue)) {
//				if (DebuggerSupport.isScorecard((ObjectReference) jdiValue)) {
//					StudioDebugCorePlugin.debug("Returning the Scorecard Var - "
//							+ varName);
//					var = new ScorecardVariable(frame, tinfo, localVar);
//				} else {
//					StudioDebugCorePlugin.debug("Returning the Concept Var - " + varName);
//					var = new ConceptVariable(frame, tinfo, localVar);
//				}
//			} else if (DebuggerSupport.isEvent((ObjectReference) jdiValue)) {
//				StudioDebugCorePlugin.debug("Returning the Event Var - " + varName);
//				var = new EventVariable(frame, tinfo, localVar);
//			} else {
//				// Default
//				StudioDebugCorePlugin.debug("Returning the default VarRow - " + varName);
//				var = new RuleDebugLocalVariable(frame, tinfo, localVar);
//			}
		} else {
			// Default
			StudioDebugCorePlugin.debug("Returning the default VarRow - " + varName);
			var = new RuleDebugLocalVariable(frame, tinfo, localVar);
		}

		return var;
	}
	
	public static List<IRuleDebugVariableProvider> getVariableProviders() throws DebugException {
		List<IRuleDebugVariableProvider> providers = new ArrayList<IRuleDebugVariableProvider>();
		try {

			IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(StudioDebugCorePlugin.PLUGIN_ID, "debugVariableProvider"); //$NON-NLS-1$
			if(extensionPoint != null) {
				IConfigurationElement[] configs = extensionPoint.getConfigurationElements();
				for (int i= 0; i < configs.length; i++) {
					IRuleDebugVariableProvider provider = (IRuleDebugVariableProvider) configs[i].createExecutableExtension("class"); //$NON-NLS-1$
					if(!providers.contains(provider)) {
						providers.add(provider); 
					}
				}				
			}
		}catch (CoreException e) {
			throw new DebugException(new Status(IStatus.ERROR,StudioDebugCorePlugin.PLUGIN_ID,"Failed to find variable providers",e));
		}
		return providers;
		
	}
	

	/**
	 * main factory method to get variables
	 * @param frame
	 * @param tinfo
	 * @param varName
	 * @param jdiValue
	 * @return
	 */
	public static AbstractDebugVariable getVarRow(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, String varName, Value jdiValue) throws DebugException {
		AbstractDebugVariable var = null;
		if (jdiValue instanceof ObjectReference) {
			if (frame.getVarMap().containsKey(jdiValue)) {
				AbstractDebugVariable mapVar = frame.getVarMap().get(jdiValue);
				if (varName.equals(mapVar.getName())) {
					return mapVar;
				}
			}
			List<IRuleDebugVariableProvider> providers = getVariableProviders();
			for(IRuleDebugVariableProvider provider:providers) {
				if(provider.canProvide((ObjectReference) jdiValue)) {
					var = provider.getVariable(frame, tinfo, (ObjectReference) jdiValue, varName);
					if(var != null){
						break;
					}
				}
			}
			if(var == null) {
				// Default
				StudioDebugCorePlugin.debug("Returning the default VarRow - " + varName);
				var = new  RuleDebugLocalVariable(frame, tinfo, varName,jdiValue);
			}
//			if (DebuggerSupport.isConcept((ObjectReference) jdiValue)) {
//				if (DebuggerSupport.isScorecard((ObjectReference) jdiValue)) {
//					StudioDebugCorePlugin.debug("Returning the Scorecard Var - "
//							+ varName);
//					var = new ScorecardVariable(frame, tinfo,
//							(ObjectReference) jdiValue, varName, jdiValue);
//				} else {
//					System.out
//							.println("Returning the Concept Var - " + varName);
//					var = new ConceptVariable(frame, tinfo,
//							(ObjectReference) jdiValue, varName, jdiValue);
//				}
//			} else if (DebuggerSupport.isEvent((ObjectReference) jdiValue)) {
//				StudioDebugCorePlugin.debug("Returning the Event Var - " + varName);
//				var = new EventVariable(frame, tinfo, varName, jdiValue);
//			} else {
//				// Default
//				StudioDebugCorePlugin.debug("Returning the default VarRow - " + varName);
//				var = new RuleDebugLocalVariable(frame, tinfo, varName,
//						jdiValue);
//			}
		} else {
			// Default
			StudioDebugCorePlugin.debug("Returning the default VarRow - " + varName);
			var = new RuleDebugVariable(frame, tinfo, varName, jdiValue);
		}
		return var;
	}

	/**
	 * returns an array of child variables for this array
	 * @param frame
	 * @param tinfo
	 * @param arRef
	 * @return
	 */
	public static IVariable[] getChildrenFromArrayReference(
			RuleDebugStackFrame frame, RuleDebugThread tinfo,
			ArrayReference arRef) throws DebugException {
		int arrLength = arRef.length();
		List<IVariable> vars = new ArrayList<IVariable>();
		if (arrLength > 0) {
			final List<Value> values = arRef.getValues();
			int i = 0;
			for (Value v : values) {
				String s = "[" + i + "]";
				IVariable var = RuleDebugVariableFactory.getVarRow(frame, tinfo, s,
						v);
				vars.add(var);
				i++;
			}
		}	
		return vars.toArray(new IVariable[vars.size()]);
	}

	/**
	 * returns an array of child variables of this object 
	 * @param frame
	 * @param tinfo
	 * @param obj
	 * @return
	 */
	public static IVariable[] getChildrenFromObjectReference(
			RuleDebugStackFrame frame, RuleDebugThread tinfo,
			ObjectReference obj) throws DebugException {
		List<IVariable> vars = new ArrayList<IVariable>();
		List<Field> fields = obj.referenceType().visibleFields();
		for (Field f : fields) {
			if (f.isStatic())
				continue;
			Value v = obj.getValue(f);
			IVariable var = RuleDebugVariableFactory.getVarRow(frame, tinfo, f
					.name(), v);
			vars.add(var);
		}
		return vars.toArray(new IVariable[vars.size()]);
	}

	/**
	 * returns a list of scorecard variables
	 * @param frame
	 * @param tinfo
	 * @return
	 */
	public static List<IVariable> getScorecardVariables(RuleDebugStackFrame frame,
			RuleDebugThread tinfo) throws DebugException {
		List<IVariable> scorecardrows = new ArrayList<IVariable>();
		//try {
			StudioDebugCorePlugin.debug("Getting all scorecards");
			Set<Entry<String, ObjectReference>> scorecards = getScorecards(frame, tinfo).entrySet();
			for (Entry<String, ObjectReference> sc : scorecards) {
				StudioDebugCorePlugin.debug("creating scorecard variable:"+sc.getKey());
				AbstractDebugVariable row = getVarRow(frame, tinfo,
						sc.getKey(), sc.getValue());
				scorecardrows.add(row);
			}

		//} catch (Exception e) {
		//	StudioDebugCorePlugin.log(e);
		//}
		return scorecardrows;
	}
	
	public static Map<String, ObjectReference> getScorecards(RuleDebugStackFrame frame,
			RuleDebugThread tinfo) throws DebugException {
		synchronized (tinfo) {
			Map<String,ObjectReference> scorecards = new HashMap<String,ObjectReference>();
			StudioDebugCorePlugin.debug("Returning the Scorecard VarRow");
			//try {
				ObjectReference ruleSessionRef = DebuggerSupport
				.getCurrentRuleSession(tinfo);
				if (ruleSessionRef != null) {
					ObjectReference omRef = DebuggerSupport.getObjectManager(tinfo,
							ruleSessionRef);
					if (omRef != null) {
						ObjectReference rspRef = DebuggerSupport
						.getRuleServiceProvider(tinfo, ruleSessionRef);
						if (rspRef != null) {
							ArrayReference arrRef = DebuggerSupport
							.getTypeDescriptorArrayForScorecards(tinfo,
									rspRef);
							try {
								List<Value> values = arrRef.getValues();
								arrRef.disableCollection();
								if (values != null) {
									for (Value v : values) {
										StringReference uriRef = DebuggerSupport.getUri(
												tinfo, (ObjectReference) v);
										ClassObjectReference classRef = DebuggerSupport
												.getImplClass(tinfo, (ObjectReference) v);
										ObjectReference scorecardRef = DebuggerSupport
												.getNamedInstance(tinfo, omRef, uriRef,
														classRef);

										StringReference nameRef = DebuggerSupport.getEntityExtId(tinfo,
												scorecardRef); 
										String name = nameRef.toString();

										scorecards.put(name,scorecardRef);
									}
								} 
							} catch (Exception e) {
//								StudioDebugCorePlugin.log(e);
							}
							arrRef.enableCollection();
						}
					}
				}
				
			//} catch (Exception e) {
			//	StudioDebugCorePlugin.log(e);
			//}
			return scorecards;
		}
	}

	/**
	 * returns a list of agenda Item values
	 * @param frame
	 * @param tinfo
	 * @return
	 * @throws DebugException
	 */
	@SuppressWarnings("unchecked")
	public static List<Value> getRuleAgendaItems(RuleDebugStackFrame frame,
			RuleDebugThread tinfo) throws DebugException {
		ObjectReference ruleSessionMirror = null;
		ObjectReference wmMirror = null;
		ObjectReference resolverMirror;
		RuleDebugThread eventThread = tinfo;
		//VirtualMachine vm = tinfo.getVM();
		synchronized (eventThread) {
			ruleSessionMirror = DebuggerSupport.getCurrentRuleSession(eventThread);
			if (ruleSessionMirror != null) {
				wmMirror = DebuggerSupport.getWorkingMemory(eventThread,
						ruleSessionMirror);
				if (wmMirror != null) {
					resolverMirror = DebuggerSupport.getResolver(eventThread,
							wmMirror);
					if (resolverMirror != null) {
						ArrayReference agendaItems = DebuggerSupport
						.getAgendaItems(eventThread, resolverMirror);
						if (agendaItems != null) {
							try {
								agendaItems.disableCollection();
								return agendaItems.getValues();
							} finally {
								agendaItems.enableCollection();
							}
						}
					}
				}
			}
		}
		return Collections.EMPTY_LIST;
	}

	
	/**
	 * creates a rule agenda variable
	 * @param frame
	 * @param tinfo
	 * @param agendaItemVal
	 * @return
	 * @throws DebugException
	 */
	public static RuleAgendaVariable createRuleAgendaVariable(RuleDebugStackFrame frame,
			RuleDebugThread tinfo, ObjectReference agendaItemVal)
			throws DebugException {

		ObjectReference ruleMirror = DebuggerSupport.getAgendaRule(tinfo,
				agendaItemVal);
		String rName = DebuggerSupport.getRuleName(tinfo, ruleMirror);
		if (rName.startsWith(DebuggerConstants.CODE_GEN_PREFIX)) {
			rName = rName
					.substring(DebuggerConstants.CODE_GEN_PREFIX.length() + 1);
		}
		return new RuleAgendaVariable(frame, tinfo, rName, agendaItemVal);
	}

}
