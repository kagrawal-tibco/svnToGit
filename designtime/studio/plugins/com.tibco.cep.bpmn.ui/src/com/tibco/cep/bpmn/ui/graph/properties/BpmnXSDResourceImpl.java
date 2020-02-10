/**
 * 
 */
package com.tibco.cep.bpmn.ui.graph.properties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.xml.sax.InputSource;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.util.coll.Iterators;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Document;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.util.XSDWriter;

/**
 * @author sshekhar
 *
 */
public class BpmnXSDResourceImpl extends XSDResourceImpl {
	private static final ExpandedName IS_NILLABLE_NAME = ExpandedName.makeName("isNillable");
	private static final ExpandedName NILLABLE_NAME = ExpandedName.makeName("nillable");
	
	public SmSchema smElementSchema;
	public Entity ent;
	
	public Entity getEnt() {
		return ent;
	}

	public void setEnt(Entity ent) {
		this.ent = ent;
	}

	public BpmnXSDResourceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BpmnXSDResourceImpl(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public XSDSchema getSchema() {
		// TODO Auto-generated method stub
		return super.getSchema();
	}
	
	@Override
	protected void doLoad(InputSource inputSource, Map<?, ?> options)
			throws IOException {
		generateXsdSchema(smElementSchema);
	}
	public void generateXsdSchema(SmSchema smElementSchema){
		
		InputSource inputSource;
		HashMap<Object, Object> options = new HashMap<>();
		try {
//			
			String schemaString = generateSchemaString(smElementSchema);
//			String schemaString = (String) schemas.get(ent.getFullPath());
			System.out.println("Loaded string");
			System.out.println(schemaString);
			InputStream byteStream = new ByteArrayInputStream(schemaString.getBytes());
		    inputSource = new InputSource(byteStream);
		
		    super.doLoad(inputSource, options);
		    System.out.println(getSchema().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateXsdSchemaForWsdl(String schemaString) {
		InputSource inputSource;
		HashMap<Object, Object> options = new HashMap<>();
		try {
//			
//			String schemaString = generateSchemaString(smElementSchema);
//			String schemaString = (String) schemas.get(ent.getFullPath());
			System.out.println("Loaded string");
			System.out.println(schemaString);
			InputStream byteStream = new ByteArrayInputStream(schemaString.getBytes());
		    inputSource = new InputSource(byteStream);
		
		    super.doLoad(inputSource, options);
		    System.out.println(getSchema().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private Map<String, String> generateSchema(MutableElement smElement ){
//		Map<String, String> m = new HashMap<String, String>();
//		try {
//		SchemaGenerator generator = new SchemaGenerator(ent.getOwnerProjectName(), new SchemaNSResolver(getResourceSet()));
//		
//		MutableSchema schema = (MutableSchema) smElement.getSchema();
//		String xsdNamespace = ent.getNamespace();
//		String xsdName = ent.getName();
//		String xsd = generator.generateSchema(schema, xsdNamespace, xsdName);
//		m.put(xsdNamespace + xsdName, xsd);
//		} catch ( Exception e ) {
//			return null;
//		}
//		return m;
//	}
	
	public static String generateSchemaString(SmSchema schema) {
		StringWriter stringWriter = new StringWriter();
		XSDWriter xw = new XSDWriter();
//		xw.setProperty(XSDWriter.IMPORT, importTns);

		List<?> imports = new LinkedList<Object>();
//		imports.add(BE_BASE_CONCEPT_NS);
		String xsd ="";
		try {
		xw.write(schema, stringWriter, imports.listIterator());
		 xsd = stringWriter.toString();

	
			xsd = normalizeXsd(xsd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xsd;
	}
	
	public static String normalizeXsd(String xsd) throws Exception {
		XiNode rootNode;
		XiParser parser;

		parser = XiParserFactory.newInstance();

		rootNode = parser.parse(new InputSource(new ByteArrayInputStream(xsd.getBytes(ModelUtils.DEFAULT_ENCODING))));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName
				.makeName(XSDL.NAMESPACE, "schema"));
		
		int countComplexTypeNodes = XiChild.getChildCount(schemaNode,
				ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
		if(countComplexTypeNodes > 1){
			Iterator<?> ComplexTypeIter = XiChild.getIterator(schemaNode,
					ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
			while (true) {
				XiNode complexTypeNode = (XiNode) ComplexTypeIter.next();
				if (!ComplexTypeIter.hasNext()) {
					schemaNode.removeChild(complexTypeNode);
					break;
				}
			}
		}
		removeAttributeRecursively(rootNode, IS_NILLABLE_NAME);
		removeAttributeRecursively(rootNode, NILLABLE_NAME);
		StringWriter w = new StringWriter();
		XiSerializer.serialize(rootNode, w);
		w.flush();
		xsd = w.toString();
		return xsd;
	}
	
	public static void removeAttributeRecursively(XiNode node, ExpandedName attributeExpandedName){
		if(node == null )
			return;
		if (!(node instanceof Document)) {
			try{
				Iterator<?> attributes = node.getAttributes();
				if ((attributes != Iterators.EMPTY)) {
					XiNode attribute = node.getAttribute(attributeExpandedName);
					if (attribute != null)
						node.removeAttribute(attributeExpandedName);
				}
			}catch (Exception e){
				
			}

		}
		Iterator<?> children = node.getChildren();
		while(children.hasNext()){
			XiNode next = (XiNode)children.next();
			removeAttributeRecursively(next, attributeExpandedName);
		}
	}
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		super.doLoad(inputStream, options);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		// TODO Auto-generated method stub
		super.load(options);
	}
	
}
