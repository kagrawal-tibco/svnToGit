package com.tibco.cep.bpmn.model.designtime.metamodel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;

/**
 * @author pdhar
 *
 */
public class BpmnMetaModelInterfaceGenerator {


	private EPackage metaModelPackage;

	private EPackage metaModelIndexPackage;

	private EPackage metaModelExtnPackage;

	public final String BPMN_COMMON_PLUGIN_ID = "com.tibco.cep.bpmn.common";//$NON-NLS-1$

//	private final String DEFAULT_PACKAGE_SEPARATOR = "."; //$NON-NLS-1$

	public BpmnMetaModelInterfaceGenerator() {
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xml", new XMLResourceFactoryImpl());

		new BpmnMetaModelInterfaceGenerator();
	}

	private void initialize() throws Exception {
		ResourceSet rset = new ResourceSetImpl();
		rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new XMLResourceFactoryImpl());

		InputStream indexUri = getClass().getResourceAsStream("/be/cep-bpmn-index.ecore");
		URI uri = URI.createURI("file:"+getClass().getClassLoader().getResource("be/cep-bpmn-index.ecore").getFile());
		EList<EObject> xmiOut = deserializeXMI(uri,rset, indexUri);
		metaModelIndexPackage = (EPackage) xmiOut.get(0);

		InputStream modelUri = getClass().getResourceAsStream("/be/cep-bpmn.ecore");
		uri = URI.createURI("file:"+getClass().getClassLoader().getResource("be/cep-bpmn.ecore").getFile());
		xmiOut = deserializeXMI(URI.createURI("/be/cep-bpmn.ecore"),rset, modelUri);
		metaModelPackage = (EPackage) xmiOut.get(0);

		InputStream extnUri = getClass().getResourceAsStream("/be/cep-bpmn-extn.ecore");
		uri = URI.createURI("file:"+getClass().getClassLoader().getResource("be/cep-bpmn-extn.ecore").getFile());
		xmiOut = deserializeXMI(URI.createURI("/be/cep-bpmn-extn.ecore"),rset, extnUri);
		metaModelExtnPackage = (EPackage) xmiOut.get(0);

		PrintWriter pw =new PrintWriter(new File("BpmnModelClass.java"));
//		StringBuilder sb = new StringBuilder();
		pw.println(MessageFormat.format("package {0};",getClass().getPackage().getName()));
		pw.println("");
		pw.println("import org.eclipse.emf.ecore.EClass;");
		pw.println("import org.eclipse.emf.ecore.EEnum;");
		pw.println("import org.eclipse.emf.ecore.EEnumLiteral;");
		String packName = getClass().getPackage().getName();
		pw.println(MessageFormat.format("import {0}.EEnumWrapper;",packName.substring(0,packName.lastIndexOf("."))+".utils"));
		pw.println("");
		genTimeStamp(pw);
		pw.println("");
		pw.println(MessageFormat.format("public interface {0} extends BpmnMetaModelExtension '{'\n","BpmnModelClass"));
		pw.println("\tpublic static Class<BpmnMetaModel> METAMODEL = "+getClass().getPackage().getName()+".BpmnMetaModel.class;");
		generateModelClassInterfaces(pw, metaModelIndexPackage);
		generateModelClassInterfaces(pw, metaModelPackage);
		pw.println("");
		pw.println("}");
		pw.flush();
		pw.close();
		
		pw = new PrintWriter(new File("BpmnMetaModelExtension.java"));
		pw.println(MessageFormat.format("package {0};",getClass().getPackage().getName()));
		pw.println("");
		pw.println("import org.eclipse.emf.ecore.EClass;");
		pw.println("import org.eclipse.emf.ecore.EEnum;");
		pw.println("import org.eclipse.emf.ecore.EEnumLiteral;");
		packName = getClass().getPackage().getName();
		pw.println(MessageFormat.format("import {0}.EEnumWrapper;",packName.substring(0,packName.lastIndexOf("."))+".utils"));
		pw.println("");
		genTimeStamp(pw);
		pw.println("");
		pw.println(MessageFormat.format("public interface {0} '{' \n","BpmnMetaModelExtension"));
		generateModelClassInterfaces(pw, metaModelExtnPackage);
		pw.println("");
		pw.println("}");
		pw.flush();
		pw.close();

		Map<String,String> attrMap = new HashMap<String,String>();
		pw = new PrintWriter(new File("BpmnMetaModelConstants.java"));
		pw.println(MessageFormat.format("package {0};",getClass().getPackage().getName()));
		pw.println("");
		pw.println("import com.tibco.xml.data.primitive.ExpandedName;");
		pw.println("");
		genTimeStamp(pw);
		pw.println("");
		pw.println(MessageFormat.format("public interface {0} extends BpmnMetaModelExtensionConstants '{' \n","BpmnMetaModelConstants"));
		generateModelConstantInterfaces(pw, metaModelIndexPackage, attrMap);
		generateModelConstantInterfaces(pw, metaModelPackage, attrMap);
		CommonECoreHelper.toTitleCase("childLaneSet");
		generateAttrConstants(pw, attrMap);
		pw.println("");
		pw.println("}");
		pw.flush();
		pw.close();
		
		Map<String,String> xattrMap = new HashMap<String,String>();
		pw = new PrintWriter(new File("BpmnMetaModelExtensionConstants.java"));
		pw.println(MessageFormat.format("package {0};",getClass().getPackage().getName()));
		pw.println("");
		pw.println("import com.tibco.xml.data.primitive.ExpandedName;");
		pw.println("");	
		genTimeStamp(pw);
		pw.println("");		
		pw.println(MessageFormat.format("public interface {0} '{' \n","BpmnMetaModelExtensionConstants"));
		pw.println("\tpublic static final String EXTENSION_ATTRIBUTE_NAME = \"data\"; //$NON-NLS-1$");
		generateModelConstantInterfaces(pw, metaModelExtnPackage, xattrMap);
		generateAttrConstants(pw, xattrMap);
		pw.println("");
		pw.println("}");
		pw.flush();
		pw.close();
	}

	private void generateAttrConstants(PrintWriter pw,
			Map<String, String> attrMap) {
		List<Entry<String, String>> list = new LinkedList<Entry<String, String>>(attrMap.entrySet());
	    Collections.sort(list, new Comparator<Entry<String, String>>() {
	          public int compare(Entry<String, String> o1, Entry<String, String> o2) {
	               return ((Comparable<String>) ((Map.Entry<String, String>) (o1)).getKey())
	              .compareTo(((Map.Entry<String, String>) (o2)).getKey());
	          }
	     });

		for(Entry<String, String> entry:list) {
			pw.println("\tpublic static final String "+entry.getValue()+" = \""+entry.getKey()+"\"; 	//$NON-NLS-1$");
		}
	}
	
	private void genTimeStamp(PrintWriter pw) {
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		 String date = sdf.format(cal.getTime());

		pw.println("/**");
		pw.println("**\tGenerated on "+date);
		pw.println("**\tby BpmnMetaModelInterfaceGenerator");
		pw.println("**");
		pw.println("**/");
		
	}

	/**
	 * @param uri
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeXMI(URI uri,ResourceSet resourceSet,InputStream is) throws Exception {
		Resource resource = null;
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF,
				XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		resource = resourceSet.createResource(uri);
		resource.load(is,options);		
		EList<EObject> contents = resource.getContents();
		if (contents != null && contents.size() > 0) {
			return contents;
		}
		return null;
	}

	/**
	 * @param pw
	 *            TODO
	 * @param ePackage
	 */
	private static void generateModelClassInterfaces(PrintWriter pw,
			EPackage ePackage) {
		for (Iterator<EClassifier> iter = ePackage.getEClassifiers().iterator(); iter
				.hasNext();) {
			EClassifier clazz = iter.next();
			CommonECoreHelper.generateMetaModelClassInterface(pw, ePackage,
					clazz);
		}

		for (EPackage esp : ePackage.getESubpackages()) {
			generateModelClassInterfaces(pw, esp);
		}

	}

	/**
	 * @param pw
	 *            TODO
	 * @param ePackage
	 * @param attrSet TODO
	 */
	private static void generateModelConstantInterfaces(PrintWriter pw,
			EPackage ePackage, Map<String,String> attrMap) {
		
		for (Iterator<EClassifier> iter = ePackage.getEClassifiers().iterator(); iter
				.hasNext();) {
			EClassifier clazz = iter.next();
			attrMap.putAll(
					CommonECoreHelper.generateMetaModelConstantsInterface(pw,
					ePackage, clazz));
		}

		for (EPackage esp : ePackage.getESubpackages()) {
			generateModelConstantInterfaces(pw, esp, attrMap);
		}

	}

}
