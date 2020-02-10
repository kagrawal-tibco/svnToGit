package com.tibco.cep.studio.codegen;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.IPojoConceptCodeGenerator;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.POJOConceptClassGenerator;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.element.Concept;

public class PojoConceptCodeGenerator implements IPojoConceptCodeGenerator {

	public PojoConceptCodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateConcept(Concept cept, CodeGenContext ctx)
			throws Exception {
		File targetDir;
        if(cept.isAScorecard()) {
            targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR);
        } else {
            targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);
        }
		JavaFileWriter jFile = POJOConceptClassGenerator.makePOJOConceptFile(cept,
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS), 
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE));
		jFile.setTargetDir(targetDir);
		jFile.writeFile();
	}

	@Override
	public void generateConceptStream(Concept cept, CodeGenContext ctx)
			throws Exception {
		JavaFolderLocation targetDir;
        if(cept.isAScorecard()) {
            targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR);
        } else {
            targetDir = (JavaFolderLocation)ctx.get(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR);
        }
		JavaFileWriter jFile = POJOConceptClassGenerator.makePOJOConceptFile(cept,
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS), 
				(Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE));
		jFile.setTargetFolder(targetDir);
		jFile.writeStream();
	}

}
