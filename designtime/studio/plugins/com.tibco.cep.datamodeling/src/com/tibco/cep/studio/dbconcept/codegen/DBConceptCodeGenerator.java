package com.tibco.cep.studio.dbconcept.codegen;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.IDBConceptCodeGenerator;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.SmapFileWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;

public class DBConceptCodeGenerator implements IDBConceptCodeGenerator {

	public DBConceptCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.parser.codegen.IConceptCodeGenerator#generateConcept(com.tibco.cep.designtime.model.element.Concept, com.tibco.cep.studio.parser.codegen.CodeGenContext)
	 */
	@Override
	public void generateConcept(Concept cept, CodeGenContext ctx)
			throws Exception {
		File targetDir;
		if (cept.isAScorecard()) {
			targetDir = (File) ctx
					.get(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR);
		} else {
			targetDir = (File) ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);
		}
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		// Nikhil: Aug 23 2010. Fixing defect for BE 9129. Instantiating smblbMap and passing the same instance to all methods
		Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer
		.fromConcept(cept, o);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JavaClassWriter jClassWriter = (JavaClassWriter) DBConceptClassGenerator
				.makeConceptFile(
						cept,
						(Properties) ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
						(Map<?, ?>) ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE), smblbMap
						, (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
						(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		
		boolean debug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		createDBConceptFIle(cept, 
				jClassWriter, 
				targetDir,
				debug,smblbMap);
	}
	
	@Override
	public void generateConceptStream(Concept cept, CodeGenContext ctx)
			throws Exception {
		JavaFolderLocation targetDir;
		if (cept.isAScorecard()) {
			targetDir = (JavaFolderLocation) ctx
					.get(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR);
		} else {
			targetDir = (JavaFolderLocation) ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);
		}
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		// Nikhil: Aug 23 2010. Fixing defect for BE 9129. Instantiating smblbMap and passing the same instance to all methods
		Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer
		.fromConcept(cept, o);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JavaClassWriter jClassWriter = (JavaClassWriter) DBConceptClassGenerator
				.makeConceptFile(
						cept,
						(Properties) ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS),
						(Map) ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE), smblbMap
						, (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY),
						(Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE));
		boolean debug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		createDBConceptStream(cept, 
				jClassWriter, 
				targetDir,
				debug,smblbMap);
		
	}

	private void createDBConceptStream(Concept cept,
			JavaClassWriter jClassWriter, JavaFolderLocation targetDir,
			boolean enableDebug,
			Map<String, StateMachineBlockLineBuffer> smblbMap) throws Exception {
		// Nikhil: Aug 23 2010. Fixing defect for BE 9129. Getting smblbMap from the method arguments
//		Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer
//				.fromConcept(cept);
		JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(cept
				.getName(), ModelNameUtil.modelPathToExternalForm(cept
				.getFolder().getFullPath()), targetDir,
				ModelNameUtil.RULE_SEPARATOR_CHAR);
		jfwriter.addClass(jClassWriter);
		jfwriter.writeStream();
		if (enableDebug) {
			// SMAP
			for (Map.Entry<String, StateMachineBlockLineBuffer> entry : smblbMap
					.entrySet()) {
				
				StateMachineBlockLineBuffer smblb = entry.getValue();
				String className = smblb.getClassName();
				className = className.replace('.', '$');
//				String smtext = smblb.toIndentedString(true);
				SmapFileWriter smwriter = CodeGenHelper.setupSmapStreamWriter(
						className, ModelNameUtil.modelPathToExternalForm(cept
								.getFolder().getFullPath()), targetDir,
						ModelNameUtil.RULE_SEPARATOR_CHAR);
				smwriter.writeSMapXmlStream(smblb);
				System.out.println("end of createDBConceptFIle()");
			}
		}
		
	}

	/**
	 * @param cept
	 * @param jClassWriter
	 * @param targetDir
	 * @param enableDebug
	 * @param smblbMap 
	 * @throws Exception
	 */
	private void createDBConceptFIle(Concept cept,
			JavaClassWriter jClassWriter, 
			File targetDir, 
			boolean enableDebug, Map<String, StateMachineBlockLineBuffer> smblbMap)
			throws Exception {
		// Nikhil: Aug 23 2010. Fixing defect for BE 9129. Getting smblbMap from the method arguments
//		Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer
//				.fromConcept(cept);
		JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(cept
				.getName(), ModelNameUtil.modelPathToExternalForm(cept
				.getFolder().getFullPath()), targetDir,
				ModelNameUtil.RULE_SEPARATOR_CHAR);
		jfwriter.addClass(jClassWriter);
		jfwriter.writeFile();
		if (enableDebug) {
			// SMAP
			for (Map.Entry<String, StateMachineBlockLineBuffer> entry : smblbMap
					.entrySet()) {
				
				StateMachineBlockLineBuffer smblb = entry.getValue();
				String className = smblb.getClassName();
				className = className.replace('.', '$');
//				String smtext = smblb.toIndentedString(true);
				SmapFileWriter smwriter = CodeGenHelper.setupSmapFileWriter(
						className, ModelNameUtil.modelPathToExternalForm(cept
								.getFolder().getFullPath()), targetDir,
						ModelNameUtil.RULE_SEPARATOR_CHAR);
				smwriter.writeSMapXmlFile(smblb);
				System.out.println("end of createDBConceptFIle()");
			}
		}
	}

	

}
