package com.tibco.cep.bpmn.core.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;

/**
 * Bean class to hold data needed by process' nodes.
 * 
 * @author moshaikh
 * 
 */
public class DocumentationFlowElementBean extends DocumentDataBean {

	private String name;
	private String processName;
	private String parentProcessName;
	private String description;
	private Boolean checkpoint;
	private DocumentHyperLink resource;
	private DocumentHyperLink destination;
	private Integer priority;
	private Map<String, DocumentHyperLink> inputMapScopeVariables;
	private Map<String, DocumentHyperLink> inputMapReceivingParams;
	private Map<String, DocumentHyperLink> outputMapScopeVariables;
	private Map<String, DocumentHyperLink> outputMapReceivingParams;
	private DocumentHyperLink targetRef;
	private DocumentHyperLink sourceRef;
	private List<DocumentHyperLink> rules;
	private List<DocumentHyperLink> incomingSequences;
	private List<DocumentHyperLink> outgoingSequences;
	private DocumentHyperLink defaultSequence;
	private String mergeExpression;
	private DocumentHyperLink joinRuleFunction;
	private DocumentHyperLink forkRuleFunction;
	private String keyExpression;
	private List<DocumentHyperLink> implementationURI;
	private Map<String, Integer> replyConsumeEvents;
	private Map<String, String> transportProperties;
	private EObject eobject;

	public DocumentationFlowElementBean() {
		super("FlowElementTemplate");
		String.valueOf(true);
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns true if checkpoint is defined.
	 * 
	 * @return
	 */
	public boolean isCheckpointDefined() {
		return checkpoint != null;
	}

	/**
	 * Returns the checkpoint value.
	 * 
	 * @return
	 */
	public Boolean isCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(Boolean checkpoint) {
		this.checkpoint = checkpoint;
	}

	public DocumentHyperLink getResource() {
		computeHyperLink(resource);
		return resource;
	}

	public void setResource(DocumentHyperLink resource) {
		this.resource = resource;
	}

	public DocumentHyperLink getDestination() {
		computeHyperLink(destination);
		return destination;
	}

	public void setDestination(DocumentHyperLink destination) {
		this.destination = destination;
	}

	public Map<String, DocumentHyperLink> getInputMapScopeVariables() {
		if (inputMapScopeVariables != null) {
			for (DocumentHyperLink link : inputMapScopeVariables.values()) {
				computeHyperLink(link);
			}
		}
		return inputMapScopeVariables;
	}

	public void addInputMapScopeVariable(String name, DocumentHyperLink value) {
		if (this.inputMapScopeVariables == null) {
			this.inputMapScopeVariables = new LinkedHashMap<String, DocumentHyperLink>();
		}
		this.inputMapScopeVariables.put(name, value);
	}

	public Map<String, DocumentHyperLink> getInputMapReceivingParams() {
		if (inputMapReceivingParams != null) {
			for (DocumentHyperLink link : inputMapReceivingParams.values()) {
				computeHyperLink(link);
			}
		}
		return inputMapReceivingParams;
	}

	public void addInputMapReceivingParam(String name, DocumentHyperLink value) {
		if (this.inputMapReceivingParams == null) {
			this.inputMapReceivingParams = new LinkedHashMap<String, DocumentHyperLink>();
		}
		this.inputMapReceivingParams.put(name, value);
	}

	public Map<String, DocumentHyperLink> getOutputMapScopeVariables() {
		if (outputMapScopeVariables != null) {
			for (DocumentHyperLink link : outputMapScopeVariables.values()) {
				computeHyperLink(link);
			}
		}
		return outputMapScopeVariables;
	}

	public void addOutputMapScopeVariable(String name, DocumentHyperLink value) {
		if (this.outputMapScopeVariables == null) {
			this.outputMapScopeVariables = new LinkedHashMap<String, DocumentHyperLink>();
		}
		this.outputMapScopeVariables.put(name, value);
	}

	public Map<String, DocumentHyperLink> getOutputMapReceivingParams() {
		if (outputMapReceivingParams != null) {
			for (DocumentHyperLink link : outputMapReceivingParams.values()) {
				computeHyperLink(link);
			}
		}
		return outputMapReceivingParams;
	}

	public void addOutputMapReceivingParam(String name, DocumentHyperLink value) {
		if(this.outputMapReceivingParams == null) {
			this.outputMapReceivingParams = new LinkedHashMap<String, DocumentHyperLink>();
		}
		this.outputMapReceivingParams.put(name, value);
	}

	public String getParentProcessName() {
		return parentProcessName;
	}

	public void setParentProcessName(String parentProcessName) {
		this.parentProcessName = parentProcessName;
	}

	public DocumentHyperLink getTargetRef() {
		computeHyperLink(targetRef);
		return targetRef;
	}

	public void setTargetRef(DocumentHyperLink targetRef) {
		this.targetRef = targetRef;
	}

	public DocumentHyperLink getSourceRef() {
		computeHyperLink(sourceRef);
		return sourceRef;
	}

	public void setSourceRef(DocumentHyperLink sourceRef) {
		this.sourceRef = sourceRef;
	}

	public List<DocumentHyperLink> getRules() {
		if (rules != null) {
			for (DocumentHyperLink rule : rules) {
				computeHyperLink(rule);
			}
		}
		return rules;
	}

	public void setRules(List<DocumentHyperLink> rules) {
		this.rules = rules;
	}

	public List<DocumentHyperLink> getIncomingSequences() {
		if (incomingSequences != null) {
			for (DocumentHyperLink seq : incomingSequences) {
				computeHyperLink(seq);
			}
		}
		return incomingSequences;
	}

	public void addIncomingSequence(DocumentHyperLink incomingSequence) {
		if (this.incomingSequences == null) {
			this.incomingSequences = new ArrayList<DocumentHyperLink>();
		}
		this.incomingSequences.add(incomingSequence);
	}

	public List<DocumentHyperLink> getOutgoingSequences() {
		// Default sequence is also an outgoing sequence, remove entry from
		// outgoing sequences to avoid duplicate link on UI
		if (defaultSequence != null && outgoingSequences != null) {
			this.outgoingSequences.remove(defaultSequence);
		}
		if (outgoingSequences != null) {
			for (DocumentHyperLink seq : outgoingSequences) {
				computeHyperLink(seq);
			}
		}
		return outgoingSequences;
	}

	public void addOutgoingSequence(DocumentHyperLink outgoingSequences) {
		if (this.outgoingSequences == null) {
			this.outgoingSequences = new ArrayList<DocumentHyperLink>();
		}
		this.outgoingSequences.add(outgoingSequences);
	}

	public boolean isShowOutgoingSequences() {
		return (outgoingSequences != null || defaultSequence != null);
	}

	public DocumentHyperLink getDefaultSequence() {
		computeHyperLink(defaultSequence);
		return defaultSequence;
	}

	public void setDefaultSequence(DocumentHyperLink defaultSequence) {
		this.defaultSequence = defaultSequence;
	}

	public String getMergeExpression() {
		return getExpression(mergeExpression);
	}

	public void setMergeExpression(String mergeExpression) {
		this.mergeExpression = mergeExpression;
	}

	public DocumentHyperLink getJoinRuleFunction() {
		return joinRuleFunction;
	}

	public void setJoinRuleFunction(DocumentHyperLink joinRuleFunction) {
		this.joinRuleFunction = joinRuleFunction;
	}

	public DocumentHyperLink getForkRuleFunction() {
		return forkRuleFunction;
	}

	public void setForkRuleFunction(DocumentHyperLink forkRuleFunction) {
		this.forkRuleFunction = forkRuleFunction;
	}

	public String getKeyExpression() {
		return getExpression(keyExpression);
	}

	public List<DocumentHyperLink> getImplementationURI() {
		return implementationURI;
	}

	public Map<String, ReplyEvent> getReplyConsumeEvents() {
		if (replyConsumeEvents != null) {
			Map<String, ReplyEvent> results = new HashMap<String, ReplyEvent>();
			for (Entry<String, Integer> e : replyConsumeEvents.entrySet()) {
				results.put(e.getKey(), new ReplyEvent(e.getValue()));
			}
			return results;
		}
		return null;
	}

	public void setReplyConsumeEvents(Map<String, Integer> replyConsumeEvents) {
		this.replyConsumeEvents = replyConsumeEvents;
	}

	public Map<String, String> getTransportProperties() {
		return transportProperties;
	}

	public void setTransportProperties(Map<String, String> transportProperties) {
		this.transportProperties = transportProperties;
	}

	public void addImplementationURI(DocumentHyperLink implementationURI) {
		if (this.implementationURI == null) {
			this.implementationURI = new ArrayList<DocumentHyperLink>();
		}
		this.implementationURI.add(implementationURI);
	}

	public void setKeyExpression(String keyExpression) {
		this.keyExpression = keyExpression;
	}

	public EObject getEobject() {
		return eobject;
	}

	public void setEobject(EObject eObject) {
		this.eobject = eObject;
	}

	/**
	 * Determines whether to show properties table on UI.
	 * 
	 * @return
	 */
	public boolean isShowProperties() {
		return (checkpoint != null || priority != null || resource != null
				|| destination != null || targetRef != null
				|| sourceRef != null || isShowOutgoingSequences()
				|| incomingSequences != null || rules != null
				|| implementationURI != null || joinRuleFunction != null
				|| forkRuleFunction != null || keyExpression != null);
	}

	private static String getExpression(String str) {
		if (str != null && !"".equals(str.trim())) {
			Pattern p = Pattern.compile("<expr>.*</expr>");
			Matcher m = p.matcher(str);
			if (m.find()) {
				return m.group().replace("<expr>", "").replace("</expr>", "");
			}
		}
		return null;
	}

	/**
	 * This class is for packing replyEvents details. Used by StringTemplate.
	 * 
	 * @author moshaikh
	 * 
	 */
	private static class ReplyEvent {
		// Used only by StringTemplate via reflection hence kept private.
		private boolean replyTo;
		private boolean consume;

		public ReplyEvent(int mask) {
			replyTo = (mask & BpmnDocHelper.REPLY) == 1;
			consume = (mask & BpmnDocHelper.CONSUME) == 1;
		}

		@SuppressWarnings("unused")
		public boolean isReplyTo() {
			return replyTo;
		}

		@SuppressWarnings("unused")
		public boolean isConsume() {
			return consume;
		}
	}
}