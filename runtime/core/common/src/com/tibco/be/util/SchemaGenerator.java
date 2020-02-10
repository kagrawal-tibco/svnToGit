package com.tibco.be.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.BETargetNamespaceCache;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.mutable.MutableBEProject;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.util.XSDWriter;

/**
 * 
 * @author bgokhale
 * Generate XSD schemas for a BE project.
 * 
 */
public class SchemaGenerator {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	public final static String BE_BASE_CONCEPT_NS = "www.tibco.com/be/ontology/_BaseConcept";
	public final static String BE_BASE_EVENT_NS = "www.tibco.com/be/ontology/_BaseEvent";
	public final static String BE_BASE_TIME_EVENT_NS = "www.tibco.com/be/ontology/_BaseTimeEvent";
	public final static String BE_BASE_SOAP_EVENT_NS = "www.tibco.com/be/ontology/_BaseSOAPEvent";
	public static final String BE_BASE_PROCESS_NS = "www.tibco.com/be/ontology/_BaseProcess";
	public static final String BE_BASE_EXCEPTION_NS = "www.tibco.com/be/ontology/_BaseException";

	Project project;
	Ontology ontology;
	String projectPath;
	String generateLocation;
	
	/**
	 * This base namespace will be used so as to avoid namespace clashes with BE namespaces
	 * For example if a namespace is provided like www.my-org.com/schemas 
	 * then the namespace like www.tibco.com/be/ontology/cepts/MyCept.xsd 
	 * will be converted to
	 *  
	 * www.my-org.com/schemas/cepts/MyCept.xsd
	 * 
	 */
	String baseNamespace = "www.tibco.com/be/ontology";

	// *****************************************************************
	// Main.
	// *****************************************************************      
	public static void main(String[] args) {
		try {

            SchemaGenerator st = new SchemaGenerator(args);
			//st.setNsFragment("/test/path\\");
			//st.setBaseNamespace("http://www.my.org/ns/");
			st.generateConceptSchemas();
			st.generateEventSchemas();
			System.out.println("Done!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SchemaGenerator(Project project) throws Exception {
		this.project = project;
		this.ontology = this.project.getOntology();
		if (this.projectPath == null && this.project.getTnsCache() != null) {
			this.projectPath = ((BETargetNamespaceCache)this.project.getTnsCache()).getRootURI();
		}
	}

	public void setBaseNamespace(String baseNamespace) {
		this.baseNamespace = trim(baseNamespace);
	}
	
	public SchemaGenerator(String projectPath) throws Exception {
		this.projectPath = projectPath;
		final MutableBEProject project = new MutableBEProject(projectPath);
		project.load();
		boolean validProject = project.isValidDesignerProject();
		if(!validProject){
			throw new Exception("Invalid designer project");
		}
		this.project = project;
		this.ontology = this.project.getOntology();
	}

	public SchemaGenerator(String[] args) throws Exception {
		parseArgs(args);
		this.project = new MutableBEProject(this.projectPath);
		this.project.load();
		ontology = project.getOntology();
	}
	
	public Map generateSchemas(List entityList) throws Exception {
		
		System.out.println("Generating XSDs...... ");
		Map m = new HashMap();
		
		for (Iterator itr = entityList.iterator(); itr.hasNext();) {
			Entity ee = (Entity) itr.next();
			MutableElement smElement = (MutableElement) project.getSmElement(ee);
			MutableSchema schema = (MutableSchema) smElement.getSchema();
			String xsdNamespace = ee.getNamespace();
			String xsdName = ee.getName();
			String xsd = generateSchema(schema, xsdNamespace, xsdName);
			m.put(xsdNamespace + xsdName, xsd);
		}
		return m;

	}

	public void generateConceptSchemas () throws Exception {
		Map m = new HashMap();
		
		if(ontology.getConcepts().size() > 0){
			//generate base concept schema only when concepts exist in project
			String baseConceptXsd = generateSchema(RDFTnsFlavor.getBaseConceptType().getSchema(),"",RDFTnsFlavor.getBaseConceptType().getName());
			m.put("/"+RDFTnsFlavor.getBaseConceptType().getName(),baseConceptXsd);
		}
		
		List ceptList = new ArrayList(ontology.getConcepts());
		Map ceptsMap = generateSchemas(ceptList);
		m.putAll(ceptsMap);

		m = addOverridenNamespace(m);
		writeToFile(m, generateLocation);
		
		System.out.println("Concept Schema Generation Done!");
	}
	
	public void generateEventSchemas() throws Exception {
		Map m = new HashMap();

		if(ontology.getEvents().size() > 0) {
			//generate base event schema only when events exist in project
			String baseEventXsd = generateSchema(RDFTnsFlavor.getBaseEventType().getSchema(),"",RDFTnsFlavor.getBaseEventType().getName());
			m.put("/"+RDFTnsFlavor.getBaseEventType().getName(),baseEventXsd);
		}
		
		List eventList = new ArrayList(ontology.getEvents());
		Map eventsMap = generateSchemas(eventList);
		m.putAll(eventsMap);
		
		m = addOverridenNamespace(m);
		writeToFile(m, generateLocation);
		
		System.out.println("Event Schema Generation Done!");
	}
	
	public static void writeToFile(Map m, String rootDir) throws Exception, IOException {
		Iterator i = m.entrySet().iterator();
		
		while (i.hasNext()) {
			
			Map.Entry e = (Entry) i.next();
			
			
			String xsdNamespace = (String) e.getKey();
			String xsd = (String) e.getValue();
			String dirName = rootDir;
			if (xsdNamespace.indexOf("/") != -1) {
				dirName = rootDir + xsdNamespace.substring(0, xsdNamespace.lastIndexOf("/"));
			}
			dirName = dirName.replace('/', File.separatorChar).replace('\\',
					File.separatorChar);
			String xsdFileName = xsdNamespace.substring(xsdNamespace.lastIndexOf("/")+1) +".xsd";
			
			writeToFile(dirName, xsdFileName, xsd);
			
		}
	}
	
    private static Logger logger;

    // Initialize this class //
    static {
        logger = LogManagerFactory.getLogManager().getLogger(SchemaGenerator.class);
    }

	public String generateSchema(SmSchema schema, String xsdNamespace, String xsdName) throws Exception {

		StringWriter stringWriter = new StringWriter();
		XSDWriter xw = new XSDWriter();
		logger.log(Level.DEBUG, "Generating schema for '%s' [namespace '%s']", xsdName, xsdNamespace);
		//System.out.println("NS gen = " + xsdNamespace + " xsd = " + xsdName);
		ImportSchemaLocator importTns = new ImportSchemaLocator(xsdNamespace);

//		MutableNamespaceContext nsCtx = new DefaultNamespaceContext();
//		nsCtx.add("", XSDL.NAMESPACE);
//		nsCtx.add("bc", BE_BASE_CONCEPT_NS);
////		xw.setProperty(XSDWriter.PREFERRED_NAMESPACECTX, nsCtx);
////		xw.setProperty(XSDWriter.NAMESPACECTX, nsCtx);
		xw.setProperty(XSDWriter.IMPORT, importTns);

		List imports = new LinkedList();
		imports.add(BE_BASE_CONCEPT_NS);

		xw.write(schema, stringWriter, imports.listIterator());
		String xsd = stringWriter.toString();

		xsd = normalizeXsd(xsd);
		return xsd;
	}


	public static void writeToFile( String dirName, String fileName, String xsd)
			throws Exception, IOException {
		File dir = new File(dirName);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new Exception("Can't create schema folder: " + dirName);
			}
		}

		File file = new File(dir, fileName);
		FileWriter fw = new FileWriter(file);
		fw.write(xsd);
		fw.flush();
		fw.close();
	}

	class ImportSchemaLocator implements XSDWriter.DeclareImportsTns {
		String namespace;
		String [] myns = new String[0];
		public ImportSchemaLocator (String namespace) {
			if (!namespace.equals("/")) {
				if (namespace.startsWith("/")) {
					namespace = namespace.substring(1);
				}
				this.namespace = namespace;
				myns = namespace.split("/");
			}
		}
		
		public String getLocation(String ns, String loc) {

			if (BE_BASE_CONCEPT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_EVENT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_SOAP_EVENT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_PROCESS_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			} else if (BE_BASE_TIME_EVENT_NS.equals(ns)) {
				return null;//getRelativeLocation(ns, "xsd");
			}
			String relative_location = null;
			if(loc != null) {
				loc = loc.replaceAll("\\\\" ,"/");
				if (!loc.endsWith("xsd")) {
					int idx = loc.lastIndexOf('.');
					String extension = null;
					if (idx > -1) {
						extension = loc.substring(idx+1);
					} else {
						extension = "xsd";
					}
					relative_location = getRelativeLocation(ns, extension);
				} else {
					try {
						String rootURI = projectPath;//cache.getRootURI();
						if (rootURI == null) {
							projectPath = ((BETargetNamespaceCache)project.getTnsCache()).getRootURI();
						}

						URI locURI = new URI(loc);
						if (locURI.isAbsolute()) {
							if ("mem".equals(locURI.getScheme())) {
								relative_location = "";
								String part = locURI.getSchemeSpecificPart();
								part = part.replaceAll("/[/]+", "/");
								for (int i = 0; i < myns.length; i++) {
									relative_location += "..";
									if (i < myns.length - 1) {
										relative_location += "/";
									}
								} 

								return relative_location + part;
							}
							if (ns == null) {
								relative_location = "";
								for (int i = 0; i < myns.length; i++) {
									relative_location += "..";
									if (i < myns.length - 1) {
										relative_location += "/";
									}
								}
								String path = locURI.toString();
								while (path.startsWith("/")) {
									path = path.substring(1);
								}
								relative_location += path.substring(rootURI.length());
								return relative_location;
							}
							if ("jar".equals(locURI.getScheme())) {
								return locURI.toString();
							}
							if ("mem".equals(locURI.getScheme())) {
								relative_location = "";
								String part = locURI.getSchemeSpecificPart();
								part = part.replaceAll("/[/]+", "/");
								for (int i = 0; i < myns.length; i++) {
									relative_location += "..";
									if (i < myns.length - 1) {
										relative_location += "/";
									}
								} 

								return relative_location + part;
							}
							File file = new File(locURI.toURL().getFile());
//							if (!file.exists() && nsResolver != null) {
//								URI location = nsResolver.getLocation(ns);
//								locURI = location;
//							}
						}
						if (locURI == null) {
							return null;
						}
						String path = locURI.toURL().getPath();
						while (path.startsWith("/")) {
							path = path.substring(1);
						}
						if (rootURI != null && path.startsWith(rootURI)) {
							relative_location = "";
							for (int i = 0; i < myns.length; i++) {
								relative_location += "..";
								if (i < myns.length - 1) {
									relative_location += "/";
								}
							} 
							relative_location += path.substring(rootURI.length());
						}
					} catch (URISyntaxException e) {
						logger.log(Level.INFO, "Error while locating schema: "+e.getMessage());
						e.printStackTrace();
					} catch (MalformedURLException e) {
						logger.log(Level.INFO, "Error while locating schema: "+e.getMessage());
						e.printStackTrace();
					}
				}
			}

			return relative_location;
		
		}
		
//		public String getLocation(String ns, String loc) {
//			if (BE_BASE_CONCEPT_NS.equals(ns)
//					|| BE_BASE_EVENT_NS.equals(ns)) {
//				return null;
//			}
////			if (true) return null;
//			if (ns.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
//				String relative_location = getRelativeLocation(ns);
//				return relative_location;
//			} else if (loc != null) {
//				
//			}
//			return null;
//		}

		public String getLocation(String location) {
			return location;
		}

		public boolean declareImport(String s) {
			return true;
		}

		private String getRelativeLocation(String ns, String extension) {
			if (ns.contains(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
				ns = ns
						.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI
								.length() + 1);
			}

			String[] st = ns.split("/");

			String relNs = "";
			if (myns.length <= st.length - 1) {

				int len_limit = myns.length;

				int j=0;
				for (j=0; j<len_limit; j++) {
					if (!myns[j].equals(st[j])) {
						break;
					}
				}
				int k=0;
				for (k=len_limit-1; k>=j; k--) {
					relNs += "../";
				}

				for ( k=j; k<st.length - 1; k++) {
					relNs = relNs + st[k] + "/";
				}
				relNs += st[st.length - 1];
			} else  {

				for (int i=myns.length; i>st.length - 1; i--) {
					relNs += "../";
				}

				int len_limit = st.length - 1;
				int j=0;
				for (j=0; j<len_limit; j++) {
					if (!myns[j].equals(st[j])) {
						break;
					}
				}
				int k=0;
				for (k=len_limit-1; k>=j; k--) {
					relNs += "../";
				}

				for ( k=j; k<st.length - 1; k++) {
					relNs = relNs + st[k] + "/";
				}
				relNs += st[st.length - 1];

			}
			return relNs + '.' + extension;
		}

		private String getRelativeLocation(String ns) {
			ns = ns.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length()+1);
			String[] st = ns.split("/");

			String relNs = "";
			 if (myns.length <= st.length - 1) {
				 
				int len_limit = myns.length;

				int j=0;
				for (j=0; j<len_limit; j++) {
					if (!myns[j].equals(st[j])) {
						break;
					}
				}
				int k=0;
				for (k=len_limit-1; k>=j; k--) {
					relNs += "../";
				}
				
				for ( k=j; k<st.length - 1; k++) {
					relNs = relNs + st[k] + "/";
				}
				relNs += st[st.length - 1];
			} else  {
				
				for (int i=myns.length; i>st.length - 1; i--) {
					relNs += "../";
				}
				
				int len_limit = st.length - 1;
				int j=0;
				for (j=0; j<len_limit; j++) {
					if (!myns[j].equals(st[j])) {
						break;
					}
				}
				int k=0;
				for (k=len_limit-1; k>=j; k--) {
					relNs += "../";
				}
				
				for ( k=j; k<st.length - 1; k++) {
					relNs = relNs + st[k] + "/";
				}
				relNs += st[st.length - 1];
				
			}
			return relNs + ".xsd";
		}
	}

	private String normalizeXsd(String xsd) throws Exception {
		XiNode rootNode;
		XiParser parser;

		parser = XiParserFactory.newInstance();
		XiFactory factory = XiFactoryFactory.newInstance();
		rootNode = parser.parse(new InputSource(new ByteArrayInputStream(xsd.getBytes())));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName
				.makeName(XSDL.NAMESPACE, "schema"));
		
		int countComplexTypeNodes = XiChild.getChildCount(schemaNode,
				ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
		if(countComplexTypeNodes > 1){
			Iterator ComplexTypeIter = XiChild.getIterator(schemaNode,
					ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
			while (true) {
				XiNode complexTypeNode = (XiNode) ComplexTypeIter.next();
				stripNillableAttr(complexTypeNode);
				if (!ComplexTypeIter.hasNext()) {
					schemaNode.removeChild(complexTypeNode);
					break;
				}
			}
		}
		int countElTypeNodes = XiChild.getChildCount(schemaNode,
				ExpandedName.makeName(XSDL.NAMESPACE, "element"));
		if(countElTypeNodes > 0){
			Iterator ComplexTypeIter = XiChild.getIterator(schemaNode,
					ExpandedName.makeName(XSDL.NAMESPACE, "element"));
			while (ComplexTypeIter.hasNext()) {
				XiNode complexTypeNode = (XiNode) ComplexTypeIter.next();
				stripNillableAttr(complexTypeNode);
			}
		}
		
		StringWriter w = new StringWriter();
		XiSerializer.serialize(rootNode, w);
		w.flush();
		xsd = w.toString();
		return xsd;
	}
	
	protected static final ExpandedName NILLABLE_NAME = ExpandedName.makeName("isNillable");

	private void stripNillableAttr(XiNode typeNode) {
		Iterator children = typeNode.getChildren();
		while (children.hasNext()) {
			XiNode child = (XiNode) children.next();
			if (child instanceof Element) {
				XiNode attribute = child.getAttribute(NILLABLE_NAME);
				if (attribute != null) {
					child.removeAttribute(NILLABLE_NAME);
				}
				stripNillableAttr(child);
			}
		}
	}

	private Map addOverridenNamespace(Map xsdMap) {
		if (baseNamespace.equals("")) {
			return xsdMap;
		}
		Map newMap = new LinkedHashMap();
		Iterator i = xsdMap.entrySet().iterator();
		
		while (i.hasNext()) {
			Map.Entry e = (Entry) i.next();
			String xsdNamespace = (String) e.getKey();
			String xsd = (String) e.getValue();

			xsd = xsd.replaceAll(TypeManager.DEFAULT_BE_NAMESPACE_URI, baseNamespace);
			i.remove();
			newMap.put(xsdNamespace, xsd);
		}

		return newMap;
	}

	void parseArgs(String[] args) {
		int i = 0;

		while (i < args.length) {
			if (args[i].equalsIgnoreCase("-projectPath")) {
				if ((i + 1) >= args.length)
					usage();
				projectPath = args[i + 1];
				i += 2;
			} else if (args[i].equalsIgnoreCase("-rootDir")) {
				if ((i + 1) >= args.length)
					usage();
				generateLocation = args[i + 1];
				i += 2;
			} else if (args[i].equalsIgnoreCase("-baseNamespace")) {
				if ((i + 1) >= args.length)
					usage();
				baseNamespace = args[i + 1];
				i += 2;
			}else if (args[i].equalsIgnoreCase("-help")) {
				usage();
			}
			// continue in this manner
			else {
				System.err.println("Unrecognized parameter: " + args[i]);
				usage();
			}
		}
		if (projectPath.equals("") || generateLocation.equals("")) {
			usage();
		}
	}

	/*-----------------------------------------------------------------------
	 * usage
	 *----------------------------------------------------------------------*/
	void usage() {
		System.err.println("\nUsage: java SchemaGenerator [options] ");
		System.err.println("");
		System.err.println("   where options are:");
		System.err.println("");
		System.err.println(" -projectPath /path/to/project -rootDir /path/to/generated/xsds/ [-basenamespace namespace]");
		System.err.println(" -help : help message\n");
		System.exit(0);
	}

	public String getGenerateLocation() {
		return generateLocation;
	}

	public void setGenerateLocation(String generateLocation) {
		this.generateLocation = trim(generateLocation);
	}
	
	private static String trim (String str) {
		if (str == null) {
			return "";
		}
		if (str != null && !str.equals("")) {
			//strip trailing "/" or "\"
			if (str.endsWith("/") || str.endsWith("\\")) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return str;
	}
}