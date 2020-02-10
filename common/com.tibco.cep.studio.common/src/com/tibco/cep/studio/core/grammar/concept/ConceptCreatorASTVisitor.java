package com.tibco.cep.studio.core.grammar.concept;

import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.grammar.EntityCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.HeaderASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.HeaderParser;

public class ConceptCreatorASTVisitor extends DefaultConceptNodeASTVisitor implements EntityCreatorASTVisitor {

	private Concept fCreatedConcept;
	private PropertyDefinition fCurrentPropertyDef;
	private Entity fCurrentMetadataProperty;
	private String fProjectName;
	private String fResourceURI;
	
	public ConceptCreatorASTVisitor(String projectName, String resourceURI) {
		this.fProjectName = projectName;
		this.fResourceURI = resourceURI;
	}

	public ConceptCreatorASTVisitor(String resourceURI) {
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
	public boolean visitMetadataProperty(RulesASTNode node) {
		SimpleProperty prop = ModelFactory.eINSTANCE.createSimpleProperty();
		RulesASTNode name = node.getChildByFeatureId(ConceptParser.NAME);
		if (name != null) {
			String text = RuleGrammarUtils.unescapeSimpleString(name.getText());
			prop.setName(text);
		}
		
		RulesASTNode literalNode = node.getChildByFeatureId(ConceptParser.LITERAL);
		String value = RuleGrammarUtils.unescapeSimpleString(literalNode.getText());
		prop.setValue(value);
		if (this.fCreatedConcept.getExtendedProperties() == null) {
			this.fCreatedConcept.setExtendedProperties(ModelFactory.eINSTANCE.createPropertyMap());
		}
		Entity oldProp = this.fCurrentMetadataProperty;
		if (oldProp != null) {
			if (oldProp instanceof ObjectProperty) {
				((PropertyMap)((ObjectProperty) oldProp).getValue()).getProperties().add(prop);
			}
		} else {
			this.fCreatedConcept.getExtendedProperties().getProperties().add(prop);
		}
		return false;
	}

	@Override
	public boolean visitMetadataPropertyGroup(RulesASTNode node) {
		ObjectProperty prop = ModelFactory.eINSTANCE.createObjectProperty();
		PropertyMap map = ModelFactory.eINSTANCE.createPropertyMap();
		prop.setValue(map);
		
		RulesASTNode name = node.getChildByFeatureId(ConceptParser.NAME);
		if (name != null) {
			String text = RuleGrammarUtils.unescapeSimpleString(name.getText());
			prop.setName(text);
		}

		if (this.fCreatedConcept.getExtendedProperties() == null) {
			this.fCreatedConcept.setExtendedProperties(ModelFactory.eINSTANCE.createPropertyMap());
		}
		Entity oldProp = this.fCurrentMetadataProperty;
		if (oldProp != null) {
			if (oldProp instanceof ObjectProperty) {
				((PropertyMap)((ObjectProperty) oldProp).getValue()).getProperties().add(prop);
			}
		} else {
			this.fCreatedConcept.getExtendedProperties().getProperties().add(prop);
		}
		this.fCurrentMetadataProperty = prop;
		List<RulesASTNode> childProps = node.getChildrenByFeatureId(ConceptParser.STATEMENTS);
		doVisitList(childProps);
		this.fCurrentMetadataProperty = oldProp;
		return false;
	}

	@Override
	public boolean visitStateMachineStatement(RulesASTNode node) {
		RulesASTNode name = node.getChildByFeatureId(ConceptParser.NAME);
		if (name != null) {
			this.fCreatedConcept.getStateMachinePaths().add(RuleGrammarUtils.getFullNameFromNode(name, RuleGrammarUtils.FOLDER_FORMAT));
		}
		return false;
	}

	@Override
	public boolean visitDomainStatement(RulesASTNode node) {
		if (this.fCurrentPropertyDef == null) {
			return false;
		}
		List<RulesASTNode> nameNodes = node.getChildrenByFeatureId(ConceptParser.DOMAIN_NAMES);
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
	public boolean visitHistoryStatement(RulesASTNode node) {
		if (this.fCurrentPropertyDef == null) {
			return false;
		}
		RulesASTNode literalNode = node.getChildByFeatureId(ConceptParser.LITERAL);
		String text = literalNode.getText();
		int value = Integer.parseInt(text);
		this.fCurrentPropertyDef.setHistorySize(value);
		return false;
	}

	@Override
	public boolean visitPolicyStatement(RulesASTNode node) {
		if (this.fCurrentPropertyDef == null) {
			return false;
		}
		RulesASTNode literalNode = node.getChildByFeatureId(ConceptParser.LITERAL);
		String text = literalNode.getText();
		int value = "CHANGES_ONLY".equals(text) ? 0 : 1;
		this.fCurrentPropertyDef.setHistoryPolicy(value);
		return false;
	}

	@Override
	public boolean visitContainedStatement(RulesASTNode node) {
		if (this.fCurrentPropertyDef == null) {
			return false;
		}
		RulesASTNode literalNode = node.getChildByFeatureId(ConceptParser.LITERAL);
		String text = literalNode.getText();
		boolean value = Boolean.parseBoolean(text);
		if (value) {
			this.fCurrentPropertyDef.setType(PROPERTY_TYPES.CONCEPT);
		} else {
			this.fCurrentPropertyDef.setType(PROPERTY_TYPES.CONCEPT_REFERENCE);
		}
		return false;
	}

	@Override
	public boolean visitAutoStartStatement(RulesASTNode node) {
		RulesASTNode literalNode = node.getChildByFeatureId(ConceptParser.LITERAL);
		String text = literalNode.getText();
		boolean value = Boolean.parseBoolean(text);
		this.fCreatedConcept.setAutoStartStateMachine(value);

		return false;
	}

	@Override
	public boolean visitStateMachinesBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(ConceptParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(ConceptParser.STATEMENTS);
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
		this.fCreatedConcept.getProperties().add(propDef);
		propDef.setOwnerPath(this.fCreatedConcept.getFullPath());
		propDef.setOwnerProjectName(getProjectName());
		RulesASTNode typeNode = node.getChildByFeatureId(ConceptParser.TYPE);
		if (typeNode != null) {
			if (RuleGrammarUtils.isName(typeNode)) {
				String fullName = RuleGrammarUtils.getFullNameFromNode(typeNode, RuleGrammarUtils.FOLDER_FORMAT);
				if (RuleGrammarUtils.isGenericType(fullName)) {
					propDef.setType(PROPERTY_TYPES.get(fullName));
				} else {
					propDef.setConceptTypePath(fullName);
					propDef.setType(PROPERTY_TYPES.CONCEPT_REFERENCE); // default is concept reference
				}
			} else if (typeNode.getType() == ConceptParser.PRIMITIVE_TYPE){
				propDef.setType(PROPERTY_TYPES.get(typeNode.getFirstChild().getText()));
			}
			propDef.setArray(typeNode.isArray());
		}

		RulesASTNode name = node.getChildByFeatureId(ConceptParser.NAME);
		if (name != null) {
			propDef.setName(RuleGrammarUtils.getSimpleNameFromNode(name));
		}
		
		RulesASTNode propertyBlock = node.getChildByFeatureId(ConceptParser.PROPERTY_BLOCK);
		if (propertyBlock != null) {
			List<RulesASTNode> attributeNodes = propertyBlock.getChildrenByFeatureId(ConceptParser.STATEMENTS);
			this.fCurrentPropertyDef = propDef;
			doVisitList(attributeNodes);
			this.fCurrentPropertyDef = null;
		}
		return false;
	}

	@Override
	public boolean visitConceptDefinition(RulesASTNode node) {
		this.fCreatedConcept = ElementFactory.eINSTANCE.createConcept();
		this.fCreatedConcept.setOwnerProjectName(this.fProjectName);
		
		RulesASTNode nameNode = node.getChildByFeatureId(ConceptParser.NAME);
		if (nameNode == null) {
			System.out.println("could not find name from AST");
		}
		
		String folder = RuleGrammarUtils.getQualifierFromNode(nameNode, RuleGrammarUtils.FOLDER_FORMAT);
		String name = RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		if(folder == null || folder.equals("")) {
			folder = "/";
		}
		fCreatedConcept.setFolder(folder);
		fCreatedConcept.setNamespace(folder);
		fCreatedConcept.setName(name);
		String projectName = getProjectName();
		fCreatedConcept.setOwnerProjectName(projectName);
		RulesASTNode parentNode = node.getChildByFeatureId(ConceptParser.PARENT);
		if (parentNode != null) {
			String parentPath = RuleGrammarUtils.getFullNameFromNode(parentNode, RuleGrammarUtils.FOLDER_FORMAT);
			fCreatedConcept.setSuperConceptPath(parentPath);
		}
		
		Object headerNode = ((RulesASTNode) node.getParent()).getData("HEADER");
		if (headerNode instanceof HeaderASTNode) {
			List<RulesASTNode> statementNodes = ((HeaderASTNode) headerNode).getChildrenByFeatureId(HeaderParser.STATEMENTS);
			for (RulesASTNode rulesASTNode : statementNodes) {
				if (rulesASTNode.getType() == HeaderParser.GUID_STATEMENT) {
					String literals = getLiterals(rulesASTNode);
					fCreatedConcept.setGUID(literals);
				} else if (rulesASTNode.getType() == HeaderParser.DESCRIPTION_STATEMENT) {
					String literals = getLiterals(rulesASTNode);
					fCreatedConcept.setDescription(literals);
				} else if (rulesASTNode.getType() == HeaderParser.AUTHOR_STATEMENT) {
					// don't care
				}
			}
		}
		
		List<RulesASTNode> blocks = node.getChildrenByFeatureId(ConceptParser.BLOCKS);
		doVisitList(blocks);
		
		return false;
	}

	private String getProjectName() {
		if (this.fProjectName == null) {
			Path projectPath = new Path(this.fResourceURI).removeLastSegments(1);
			Path folderPath = new Path(this.fCreatedConcept.getFolder());
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
	public boolean visitMetadataBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(ConceptParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(ConceptParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitAttributesBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(ConceptParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(ConceptParser.STATEMENTS);
		doVisitList(declarators);
		return false;
	}

	@Override
	public boolean visitPropertiesBlock(RulesASTNode blocksNode) {
		RulesASTNode node = blocksNode.getFirstChildWithType(ConceptParser.BLOCK);
		List<RulesASTNode> declarators = node.getChildrenByFeatureId(ConceptParser.STATEMENTS);
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

	public Concept getConcept() {
		return this.fCreatedConcept;
	}
	
	public Entity getEntity() {
		return getConcept();
	}
	
}
