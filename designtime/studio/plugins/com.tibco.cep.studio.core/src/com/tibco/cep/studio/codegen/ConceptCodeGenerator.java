package com.tibco.cep.studio.codegen;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.ConceptClassGeneratorSmap;
import com.tibco.be.parser.codegen.IConceptCodeGenerator;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.SmapFileWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;

public class ConceptCodeGenerator implements IConceptCodeGenerator {

	public ConceptCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateConcept(Concept cept, CodeGenContext ctx)
			throws Exception {
        File targetDir;
        boolean isDebug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
        if(cept.isAScorecard()) {
            targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR);
        } else {
            targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);
        }
        Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer.fromConcept(cept, o);
		final JavaClassWriter jClassWriter = 
    		ConceptClassGeneratorSmap.makeConceptFile(cept,
    				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS), 
    				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE), 
    				smblbMap, o, (Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE), false);
    	
    	JavaFileWriter jfwriter = CodeGenHelper.setupJavaFileWriter(cept.getName(),
    			ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath())
    			, targetDir
    			, ModelNameUtil.RULE_SEPARATOR_CHAR);
    	
    	jfwriter.addClass(jClassWriter);
    	jfwriter.writeFile();
    	
    	if(isDebug) {
    		// SMAP
    		for(Map.Entry<String,StateMachineBlockLineBuffer> entry : smblbMap.entrySet()) {
    			StateMachineBlockLineBuffer smblb = entry.getValue();
    			String className = smblb.getClassName();
    			className = className.replace('.','$');
    			String smtext = smblb.toIndentedString(true);
    			SmapFileWriter smwriter = CodeGenHelper.setupSmapFileWriter(className,
    					ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath()),
    					targetDir,
    					ModelNameUtil.RULE_SEPARATOR_CHAR);
    			smwriter.writeSMapXmlFile(smblb);
    		}
    	}

	}

	@Override
	public void generateConceptStream(Concept cept, CodeGenContext ctx)
			throws Exception {
		JavaFolderLocation targetDir;
		boolean isDebug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
        if(cept.isAScorecard()) {
            targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR);
        } else {
            targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);
        }
        Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer.fromConcept(cept, o);
		final JavaClassWriter jClassWriter = 
    		ConceptClassGeneratorSmap.makeConceptFile(cept,
    				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS), 
    				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE), 
    				smblbMap, o, (Map)ctx.get(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE), false);
    	
    	JavaFileWriter jfwriter = CodeGenHelper.setupJavaStreamWriter(cept.getName(),
    			ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath())
    			, targetDir
    			, ModelNameUtil.RULE_SEPARATOR_CHAR);
    	
    	jfwriter.addClass(jClassWriter);
    	jfwriter.writeStream();
    	
    	if(isDebug ) {
    		// SMAP
    		for(Map.Entry<String,StateMachineBlockLineBuffer> entry : smblbMap.entrySet()) {
    			StateMachineBlockLineBuffer smblb = entry.getValue();
    			String className = smblb.getClassName();
    			className = className.replace('.','$');
    			String smtext = smblb.toIndentedString(true);
    			SmapFileWriter smwriter = CodeGenHelper.setupSmapStreamWriter(className,
    					ModelNameUtil.modelPathToExternalForm(cept.getFolder().getFullPath()),
    					targetDir,
    					ModelNameUtil.RULE_SEPARATOR_CHAR);
    			smwriter.writeSMapXmlFile(smblb);
    		}
    	}
		
	}

}
