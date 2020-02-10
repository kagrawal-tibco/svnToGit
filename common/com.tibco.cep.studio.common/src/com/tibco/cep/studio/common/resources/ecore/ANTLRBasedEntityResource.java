package com.tibco.cep.studio.common.resources.ecore;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.stringtemplate.AutoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.grammar.EntityCreatorASTVisitor;
import com.tibco.cep.studio.core.grammar.EntityInfo;
import com.tibco.cep.studio.core.grammar.concept.ConceptCreatorASTVisitor;
import com.tibco.cep.studio.core.grammar.concept.ConceptInfo;
import com.tibco.cep.studio.core.grammar.concept.ConceptLexer;
import com.tibco.cep.studio.core.grammar.concept.ConceptParser;
import com.tibco.cep.studio.core.grammar.concept.ConceptTreeAdaptor;
import com.tibco.cep.studio.core.grammar.event.EventCreatorASTVisitor;
import com.tibco.cep.studio.core.grammar.event.EventInfo;
import com.tibco.cep.studio.core.grammar.event.EventLexer;
import com.tibco.cep.studio.core.grammar.event.EventParser;
import com.tibco.cep.studio.core.grammar.event.EventTreeAdaptor;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.rules.BaseRulesParser;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class ANTLRBasedEntityResource extends ResourceImpl {

	class ResourceInfo {
		
		ELEMENT_TYPES resourceType;
		Lexer lexer;
		BaseRulesParser parser;
		TreeAdaptor adaptor;
		EntityCreatorASTVisitor creatorVisitor;
		
		public void init(ANTLRInputStream stream, String projectName) {
			String path = getURI().path();
			int idx = path.lastIndexOf('.');
			if (idx == -1) {
				return;
			}
			String ext = path.substring(idx+1);
			if ("event".equalsIgnoreCase(ext)) {
				resourceType = ELEMENT_TYPES.SIMPLE_EVENT;
			} else if ("concept".equalsIgnoreCase(ext)) {
				resourceType = ELEMENT_TYPES.CONCEPT;
			} 
			switch (resourceType) {
			case SIMPLE_EVENT:
			{
				lexer = new EventLexer(stream);
				CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);
				parser = new EventParser(tokens);
				parser.setTreeAdaptor(new EventTreeAdaptor());
				creatorVisitor = new EventCreatorASTVisitor(projectName, getURI().path());
				break;
			}
			
			case CONCEPT:
			{
				lexer = new ConceptLexer(stream);
				CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);
				parser = new ConceptParser(tokens);
				parser.setTreeAdaptor(new ConceptTreeAdaptor());
				creatorVisitor = new ConceptCreatorASTVisitor(projectName, getURI().path());

				break;
			}

			default:
				break;
			}
		}
		
	}

	private String projectName;
	
	public ANTLRBasedEntityResource(URI uri, String projectName) {
		super(uri);
		this.projectName = projectName;
	}

	public ANTLRBasedEntityResource(URI uri) {
		this(uri, null);
	}
	
	public ANTLRBasedEntityResource() {
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		if (!ANTLRResourceFactory.SOURCE_BASED_PERSISTENCE) {
			XMIResourceImpl impl = new XMIResourceImpl();
			impl.getContents().addAll(this.getContents());
			impl.doSave(outputStream, options);
			this.getContents().addAll(impl.getContents());
			return;
		}
		// persist the contents in source form
		EList<EObject> contents = getContents();
		for (EObject eObject : contents) {
			// write out the concept
			writeEObject(outputStream, eObject);
		}
		outputStream.flush();
	}

	private void writeEObject(OutputStream outputStream, EObject eObject) 
			throws IOException {
		if (eObject instanceof Entity) {
			Entity entity = (Entity) eObject;
			writeEntity(outputStream, entity);
		}
	}

	private void writeEntity(OutputStream outputStream, Entity entity) 
			throws IOException {
		if (entity instanceof Concept) {
			writeConcept(outputStream, (Concept)entity);
		} else if (entity instanceof Event) {
			writeEvent(outputStream, (Event)entity);
		}
	}

	private void writeEvent(OutputStream outputStream, Event entity) 
			throws IOException {
		writeEntityToStream("/com/tibco/cep/studio/core/grammar/event/EventTemplate.stg", 
				"Event_Definition", outputStream, new EventInfo(entity));
	}

	private void writeConcept(OutputStream outputStream, Concept entity) 
			throws IOException {
		writeEntityToStream("/com/tibco/cep/studio/core/grammar/concept/ConceptTemplate.stg", 
				"Concept_Definition", outputStream, new ConceptInfo(entity));
	}

	public void writeEntityToStream(String templatePath, String templateName, OutputStream outputStream, EntityInfo entityInfo) throws IOException {
		StringTemplateGroup stgroup = new StringTemplateGroup(new InputStreamReader(getClass().getResourceAsStream(templatePath)));

		StringTemplate entityTemplate = stgroup.getInstanceOf(templateName);
		entityTemplate.reset();
		
    	entityTemplate.setAttribute("elementInfo", entityInfo);
    	System.out.println(entityTemplate.toString());
    	
    	OutputStreamWriter mapperStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
    	entityTemplate.write(new AutoIndentWriter(mapperStreamWriter));
    	mapperStreamWriter.flush();
    	mapperStreamWriter.close();
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		BufferedInputStream stream = new BufferedInputStream(inputStream);
		stream.mark(2);
		int read = stream.read();
		stream.reset();
		// Check if the first char is '<', if so, assume the format is xmi.  Otherwise, read with ANTLR parser
		if (read == '<') {
			XMIResourceImpl impl = new XMIResourceImpl();
			impl.doLoad(stream, options);
			this.getContents().addAll(impl.getContents());
		} else {
			ANTLRInputStream antlrStream = new ANTLRInputStream(stream);
			parseInputStream(antlrStream, options);
		}
	}

	private void parseInputStream(ANTLRInputStream stream, Map<?, ?> options) {

		ResourceInfo info = getResourceInfo(stream);
//		Date start = new Date();
		BaseRulesParser parser = info.parser;
		ParserRuleReturnScope startRule = null;
		try {
			startRule = parser.startRule();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
//		Date end = new Date();
//		printDebug("Parse finished in "+(end.getTime() - start.getTime()));
		if (startRule != null) {
			RulesASTNode tree = (RulesASTNode) startRule.getTree();
			if (tree == null) {
				printDebug("AST is null.  Perhaps source is empty?");
				return;
			}
			if (parser.getHeaderNode() != null) {
				tree.setData("HEADER", parser.getHeaderNode());
			}
			EntityCreatorASTVisitor visitor = info.creatorVisitor;//getCreatorVisitor();
	    	tree.accept(visitor);
	    	Entity createdEntity = visitor.getEntity();
			this.getContents().add(createdEntity);
		} else {
			printDebug("Return value is null");
			return;
		}
			
	}

	private ResourceInfo getResourceInfo(ANTLRInputStream stream) {
		ResourceInfo info = new ResourceInfo();
		info.init(stream, projectName);
		return info;
	}

	private void printDebug(String string) {
		System.out.println(string);
	}
	
}
