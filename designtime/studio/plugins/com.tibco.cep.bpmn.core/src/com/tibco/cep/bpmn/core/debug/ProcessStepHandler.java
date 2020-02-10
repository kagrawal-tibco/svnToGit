package com.tibco.cep.bpmn.core.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.ecore.EClass;

import com.sun.jdi.ObjectReference;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.IStepHandler;
import com.tibco.cep.studio.debug.core.model.IntermediateBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.core.model.impl.IMappedResourcePosition;
import com.tibco.cep.studio.debug.core.model.impl.RuleStepHandler;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;
import com.tibco.cep.studio.debug.smap.TaskRegistryMapper;
import com.tibco.xml.data.primitive.ExpandedName;

public class ProcessStepHandler extends RuleStepHandler implements IStepHandler {

	private IProcessBreakpointInfo[] getNextTaskInfo(RuleDebugThread rdt, RuleDebugStackFrame frame, IProcessBreakpointInfo pbInfo) throws DebugException {
		List<IProcessBreakpointInfo> nextTaskInfo = new ArrayList<IProcessBreakpointInfo>();
		IDebugTarget target = frame.getDebugTarget();
		TaskRegistryMapper mapper = ((AbstractDebugTarget) target).getTaskRegistryMapper();
		ObjectReference thisObject = frame.getUnderlyingThisObject();
		List<ObjectReference> tasks = DebuggerSupport.getOutgoingTasksFromBreakpoint(rdt, thisObject);
		String processUri = pbInfo.getProcessUri();
		int uniqueId = pbInfo.getUniqueId();
		for (ObjectReference task : tasks) {
			String javaSourceName = task.referenceType().name();
			String taskType = mapper.inverseMap().get(javaSourceName);
			String taskId = DebuggerSupport.getProcessTaskIdFromBreakpoint(rdt, task);
			IProcessBreakpoint.TASK_BREAKPOINT_LOCATION bploc = IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START;
//			Method executeMethod = DebuggerSupport
//					.findMethodBySignature((ClassType) task.referenceType(), "execute",
//							"(Lcom/tibco/cep/bpmn/runtime/agent/ProcessContext;Lcom/tibco/cep/bpmn/runtime/utils/Variables;)Lcom/tibco/cep/bpmn/runtime/activity/TaskResult;");
			ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(processUri, taskId, taskType, bploc, uniqueId);
			nextTaskInfo.add(bpInfo);

		}
		return nextTaskInfo.toArray(new IProcessBreakpointInfo[0]);
	}

	
	
	
	private String getRuleFunctionPath(RuleDebugThread rdt, RuleDebugStackFrame frame) throws DebugException {
//		IDebugTarget target = frame.getDebugTarget();
//		String javaSourceName = frame.getDeclaringTypeName();
		ObjectReference thisObject = frame.getUnderlyingThisObject();
		return DebuggerSupport.getRuleFunctionFromTaskBreakpoint(rdt, thisObject);
	}

	

	public String getTypeName(IResource resource) {
		IPath resourcePath = resource.getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute();
		return resourcePath.toString();
	}

	@Override
	public boolean handlesSession(String currentRuleSession, Map<String, CacheAgent.Type> targetAgentMap) {
		if (currentRuleSession != null 
				&& targetAgentMap != null 
				&& targetAgentMap.get(currentRuleSession) == CacheAgent.Type.PROCESS) {
			return true;
		}
		return false;
	}

	@Override
	public void stepInto(RuleDebugThread rdt, RuleDebugStackFrame frame) throws DebugException {
		IRuleRunTarget target = (IRuleRunTarget) frame.getDebugTarget().getAdapter(IRuleRunTarget.class);
		IProcessBreakpointInfo pbInfo = ProcessDebugModel.getProcessBreakPointInfo(frame);
//		IProject project = (IProject)rdt.getDebugTarget().getAdapter(IProject.class);
		if(pbInfo != null) {
			EClass nodeClass = BpmnMetaModel.INSTANCE.getEClass(ExpandedName.parse(pbInfo.getTaskType()));
			if(BpmnModelClass.RULE_FUNCTION_TASK.isSuperTypeOf(nodeClass)) {
				String ruleFunctionUri = getRuleFunctionPath(rdt,frame);
				RuleFunction ruleFunction = target.getBEProject().getOntology().getRuleFunction(ruleFunctionUri);
				int start = ruleFunction.getBodyCodeBlock().getStart();
				int end = ruleFunction.getBodyCodeBlock().getEnd();
				IMappedResourcePosition pos = target.getSourceMapper().getJavaPosition(ruleFunctionUri, start);
				int newStart = start ;
				while (pos == null && newStart <= end ){
					newStart= newStart +1;
					pos = target.getSourceMapper().getJavaPosition(ruleFunctionUri, newStart);
					if (pos != null ){
						break ;
					}
				}
//				IResource resource = project.findMember(new Path(pos.getResourceName()));
				IResource resource = ((AbstractDebugTarget)target).getEntityResource(ruleFunctionUri);
				Map<String,Object> attributes = new HashMap<String,Object>(10);
				attributes.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_STEPOUT);
				try {
					if (pos != null) {
						IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(resource, pos.getResourceName(), pos.getLineNumber(), -1, -1, 0, false, attributes);
						target.breakpointAdded(bp);
					}
					rdt.setRunning(true);
					rdt.preserveStackFrames();
					rdt.fireResumeEvent(DebugEvent.STEP_INTO);
					rdt.invokeThread();
				} catch (CoreException e) {
					rdt.abort("Failed to create rule function breakpoint", e);
				}
				//String className = target.getTaskRegistryMapper().get(pbInfo.getTaskType());
				
			} else if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(nodeClass)) {
				
			}
		} else {
			//rdt.abort("Failed to get process breakpoint information", new Exception());
			super.stepInto(rdt, frame);
		}
	}

	@Override
	public void stepOver(RuleDebugThread rdt, RuleDebugStackFrame frame) throws DebugException {
		IRuleRunTarget target = (IRuleRunTarget) frame.getDebugTarget().getAdapter(IRuleRunTarget.class);
		IProcessBreakpointInfo pbInfo = ProcessDebugModel.getProcessBreakPointInfo(frame);
		if(pbInfo != null) {
			IProject project = (IProject)target.getAdapter(IProject.class);
			IPath processPath = new Path(pbInfo.getProcessUri()).addFileExtension(CommonIndexUtils.PROCESS_EXTENSION);
			IResource resource = project.findMember(processPath);
			Map<String,Object> attributes = new HashMap<String,Object>(10);
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE, IProcessBreakpoint.PROCESS_INTERMEDIATE_BREAKPOINT_STEP_OVER);
			try {
				IProcessBreakpointInfo[] nextTaskInfo = getNextTaskInfo(rdt,frame,pbInfo);
				if(nextTaskInfo.length > 0) {
					for(IProcessBreakpointInfo tinfo:nextTaskInfo) {
						String typeName = pbInfo.getProcessUri();
						IProcessBreakpoint pbp = ProcessBreakpoint.graphBreakpointExists(pbInfo.getProcessUri(), tinfo);
						if(pbp == null) {
							IProcessBreakpoint bp = ProcessDebugModel.createProcessIntermediateBreakpoint(resource, typeName, tinfo, attributes);
							target.breakpointAdded(bp);
						}

					}
					rdt.setRunning(true);
					rdt.preserveStackFrames();
					rdt.fireResumeEvent(DebugEvent.STEP_OVER);
					rdt.invokeThread();

				} else {
					/**
					 * There are no tasks ahead, therefore resume thread
					 */
					rdt.resumedByVM();
				}
			} catch (CoreException e) {
				rdt.abort("Failed to create step intermediate breakpoint", e);
			}

		} else {
			//rdt.abort("Failed to get process breakpoint information", new Exception());
			super.stepOver(rdt, frame);
		}

	}

	@Override
	public void stepReturn(RuleDebugThread ruleDebugThread, RuleDebugStackFrame frame) throws DebugException {
		if(DebuggerSupport.isProcessJavaTaskContext(ruleDebugThread)){
			super.stepReturn(ruleDebugThread, frame);
		}
	}

}
