package com.tibco.cep.studio.core.grammar.event;

import java.util.List;

import org.antlr.runtime.CommonTokenStream;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.grammar.EntityCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.HeaderASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.HeaderParser;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public class EventCreatorASTVisitor extends DefaultEventNodeASTVisitor implements EntityCreatorASTVisitor {

	private Event fCreatedEvent;
	private PropertyDefinition fCurrentPropertyDef;
	private String fProjectName;
	private String fResourceURI;
	
	public EventCreatorASTVisitor(String projectName, String resourceURI) {
		this.fProjectName = projectName;
		this.fResourceURI = resourceURI;
	}

	public EventCreatorASTVisitor(String resourceURI) {
		this(null, resourceURI);
	}
	
	@Override
	public boolean preVisit(RulesASTNode node) {
		return super.preVisit(node);
	}

	@Override
	public boolean visit(RulesASTNode node) {
		return super.visit(node);
	}

	@Override
	public boolean visitDomainStatement(RulesASTNode node) {
		if (this.fCurrentPropertyDef == null) {
			return false;
		}
		List<RulesASTNode> nameNodes = node.getChildrenByFeatureId(EventParser.DOMAIN_NAMES);
		for (RulesASTNode nameNode : nameNodes) {
			String domainName = RuleGrammarUtils.getFullNameFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
			DomainInstance domain = DomainFactory.eINSTANCE.createDomainInstance();
			domain.setResourcePath(domainName);
			domain.setOwnerProperty(this.fCurrentPropertyDef);
			this.fCurrentPropertyDef.getDomainInstances().add(domain);
		}
		return false;
	}

	@Override
	public boolean visitPayloadStringStatement(RulesASTNode node) {
		RulesASTNode literalNode = node.getChildByFeatureId(EventParser.LITERALS);
		String value = literalNode.getText();
		if (value.length() > 1 && value.charAt(0) == '"') {
			value = value.substring(1, value.length()-1);
		}
		value = BEStringUtilities.unescape(value);
		this.fCreatedEvent.setPayloadString(value);
		return false;
	}

	@Override
	public boolean visitTTLStatement(RulesASTNode node) {
		RulesASTNode literalNode = node.getChildByFeatureId(EventParser.TTL);
		String text = literalNode.getText();
		this.fCreatedEvent.setTtl(text);
		
		RulesASTNode ttlUnitsNode = node.getChildByFeatureId(EventParser.UNITS);
		this.fCreatedEvent.setTtlUnits(TIMEOUT_UNITS.get(ttlUnitsNode.getText()));
		
		return false;
	}

	@Override
	public boolean visitDefaultDestinationStatement(RulesASTNode node) {
		RulesASTNode destNameNode = node.getChildByFeatureId(EventParser.NAME);
		String channelURI = RuleGrammarUtils.getQualifierFromNode(destNameNode, RuleGrammarUtils.FOLDER_FORMAT);
		if (channelURI.endsWith("/")) {
			channelURI = channelURI.substring(0, channelURI.length()-1);
		}
		String destName = RuleGrammarUtils.getSimpleNameFromNode(destNameNode);

		((SimpleEvent)this.fCreatedEvent).setChannelURI(channelURI);
		((SimpleEvent)this.fCreatedEvent).setDestinationName(destName);
		return false;
	}

	@Override
	public boolean visitRetryOnExceptionStatement(RulesASTNode node) {
		RulesASTNode literalNode = node.getChildByFeatureId(EventParser.LITERAL);
		String text = literalNode.getText();
		boolean value = Boolean.parseBoolean(text);
		this.fCreatedEvent.setRetryOnException(value);

		return false;
	}
	
	@Override
	public boolean visitThenBlockNode(RulesASTNode node) {
		RulesASTNode fRootNode = RuleGrammarUtils.getRootNode(node);
		CommonTokenStream tokens = fRootNode != null ? (CommonTokenStream) fRootNode.getData("tokens") : null;
		if (tokens != null) {
			RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
			String nodeText = RuleGrammarUtils.getNodeText(tokens, blockNode, false, false);
			// if node text is null then set it to blank
			if (nodeText == null){
				nodeText = "";
			}
			this.fCreatedEvent.getExpiryAction().setActionText(nodeText);
		}
		return false;
	}

	@Override
	public boolean visitDeclaratorNode(RulesASTNode node) {
		Rule expiryActionRule = this.fCreatedEvent.getExpiryAction();
		RuleFunctionSymbol symbol = RuleFactory.eINSTANCE.createRuleFunctionSymbol();
		
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		if (typeNode != null) {
			if (RuleGrammarUtils.isName(typeNode)) {
				symbol.setType(RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT));
			} else if (typeNode.getType() == RulesParser.PRIMITIVE_TYPE){
				symbol.setType(typeNode.getFirstChild().getText());
			}
		}

		RulesASTNode name = node.getChildByFeatureId(RulesParser.NAME);
		if (name != null) {
			symbol.setIdName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		if (expiryActionRule != null) {
			if (expiryActionRule.getSymbols() == null) {
				Symbols symbols = RuleFactory.eINSTANCE.createSymbols();
				expiryActionRule.setSymbols(symbols);
			}
			expiryActionRule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		}
		return false;
	}

	@Override
	public boolean visitDeclareBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(EventParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(EventParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitExpiryActionBlock(RulesASTNode blocksNode) {
		this.fCreatedEvent.setExpiryAction(RuleFactory.eINSTANCE.createRule());
		RulesASTNode node = blocksNode.getFirstChildWithType(EventParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(EventParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitPropertyBlock(RulesASTNode node) {
		return super.visitPropertyBlock(node);
	}

	@Override
	public boolean visitPropertyDeclaration(RulesASTNode node) {
		PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
		this.fCreatedEvent.getProperties().add(propDef);
		propDef.setOwnerPath(this.fCreatedEvent.getFullPath());
		propDef.setOwnerProjectName(getProjectName());
		RulesASTNode typeNode = node.getChildByFeatureId(EventParser.TYPE);
		if (typeNode != null) {
			if (RuleGrammarUtils.isName(typeNode)) {
				String fullName = RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT);
				if (RuleGrammarUtils.isGenericType(fullName)) {
					propDef.setType(PROPERTY_TYPES.get(fullName));
				} else {
					propDef.setConceptTypePath(fullName);
				}
			} else if (typeNode.getType() == EventParser.PRIMITIVE_TYPE){
				propDef.setType(PROPERTY_TYPES.get(typeNode.getFirstChild().getText()));
			}
			propDef.setArray(typeNode.isArray());
		}

		RulesASTNode name = node.getChildByFeatureId(EventParser.NAME);
		if (name != null) {
			propDef.setName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		
		RulesASTNode propertyBlock = node.getChildByFeatureId(EventParser.PROPERTY_BLOCK);
		if (propertyBlock != null) {
			List<RulesASTNode> attributeNodes = propertyBlock.getChildrenByFeatureId(EventParser.STATEMENTS);
			this.fCurrentPropertyDef = propDef;
			doVisitList(attributeNodes);
			this.fCurrentPropertyDef = null;
		}
		return false;
	}

	@Override
	public boolean visitEventDefinition(RulesASTNode node) {
		this.fCreatedEvent = EventFactory.eINSTANCE.createSimpleEvent();
		
		RulesASTNode nameNode = node.getChildByFeatureId(EventParser.NAME);
		if (nameNode == null) {
			System.out.println("could not find name from AST");
		}
		
		String folder = RuleGrammarUtils.getQualifierFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		String name = RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		if(folder == null || folder.equals("")) {
			folder = "/";
		}
		fCreatedEvent.setFolder(folder);
		fCreatedEvent.setNamespace(folder);
		fCreatedEvent.setName(name);
		String projectName = getProjectName();
		fCreatedEvent.setOwnerProjectName(projectName);
		RulesASTNode parentNode = node.getChildByFeatureId(EventParser.PARENT);
		if (parentNode != null) {
			String parentPath = RuleGrammarUtils.getFullNameFromNode(parentNode, RuleGrammarUtils.FOLDER_FORMAT);
			fCreatedEvent.setSuperEventPath(parentPath);
		}
		
		Object headerNode = ((RulesASTNode) node.getParent()).getData("HEADER");
		if (headerNode instanceof HeaderASTNode) {
			List<RulesASTNode> statementNodes = ((HeaderASTNode) headerNode).getChildrenByFeatureId(HeaderParser.STATEMENTS);
			for (RulesASTNode rulesASTNode : statementNodes) {
				if (rulesASTNode.getType() == HeaderParser.GUID_STATEMENT) {
					String literals = getLiterals(rulesASTNode);
					fCreatedEvent.setGUID(literals);
				} else if (rulesASTNode.getType() == HeaderParser.DESCRIPTION_STATEMENT) {
					String literals = getLiterals(rulesASTNode);
					fCreatedEvent.setDescription(literals);
				} else if (rulesASTNode.getType() == HeaderParser.AUTHOR_STATEMENT) {
					// don't care
				}
			}
		}
		
		List<RulesASTNode> blocks = node.getChildrenByFeatureId(EventParser.BLOCKS);
		doVisitList(blocks);
		
		return false;
	}

	private String getProjectName() {
		if (this.fProjectName == null) {
			Path projectPath = new Path(this.fResourceURI).removeLastSegments(1);
			Path folderPath = new Path(this.fCreatedEvent.getFolder());
			projectPath = projectPath.removeLastSegments(folderPath.segmentCount());
			this.fProjectName = projectPath.lastSegment();
		}
		return this.fProjectName;
	}

	private String getLiterals(RulesASTNode rulesASTNode) {
		List<RulesASTNode> literals = rulesASTNode.getChildrenByFeatureId(HeaderParser.LITERALS);
		StringBuffer desc = new StringBuffer();
		if (literals != null) {
			for (int i=0; i<literals.size(); i++) {
				RulesASTNode literalNode = literals.get(i);
				if (literalNode.getType() == HeaderParser.NEWLINE) {
					if (i < literals.size()-1) {
						desc.append('\r');
						desc.append('\n');
					}
				} else {
					desc.append(literalNode.getText());
				}
			}
		}
		return desc.toString();
	}

	@Override
	public boolean visitNamespaceStatement(RulesASTNode blocksNode) {
		RulesASTNode prefixNode = blocksNode.getFirstChildWithType(EventParser.PREFIX);
		RulesASTNode nsNode = blocksNode.getFirstChildWithType(EventParser.NAMESPACE);
		String prefix = prefixNode.getFirstChildWithType(EventParser.SIMPLE_NAME).getFirstChild().getText();
		String ns = nsNode.getFirstChild().getText();
		
		NamespaceEntry nsEntry = EventFactory.eINSTANCE.createNamespaceEntry();
		nsEntry.setPrefix(prefix);
		if (ns.length() > 1 && ns.charAt(0) == '"') {
			ns = ns.substring(1, ns.length()-1);
		}
		nsEntry.setNamespace(ns);
		this.fCreatedEvent.getNamespaceEntries().add(nsEntry);
		
		RulesASTNode locNode = blocksNode.getFirstChildWithType(EventParser.LOCATION);
		ImportRegistryEntry importEntry = EventFactory.eINSTANCE.createImportRegistryEntry();
		importEntry.setNamespace(ns);
		if (locNode != null) {
			String loc = locNode.getFirstChild().getText();
			if (loc.length() > 1 && loc.charAt(0) == '"') {
				loc = loc.substring(1, loc.length()-1);
			}
			importEntry.setLocation(BEStringUtilities.unescape(loc));
			this.fCreatedEvent.getRegistryImportEntries().add(importEntry);
		} else {
			importEntry.setLocation("");
		}

		return false;
	}

	@Override
	public boolean visitAttributesBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(EventParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(EventParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitPayloadBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(EventParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(EventParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitNamespacesBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(EventParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(EventParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitPropertiesBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(EventParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(EventParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	private void doVisitList(List<RulesASTNode> node) {
		if (node == null) {
			return;
		}
		for (RulesASTNode object : node) {
			visit(object);
		}
	}

	public Event getEvent() {
		return this.fCreatedEvent;
	}

	@Override
	public Entity getEntity() {
		return getEvent();
	}
	
}
