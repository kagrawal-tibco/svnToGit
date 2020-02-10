package com.tibco.cep.studio.codegen;

import java.io.File;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.IJavaCodeGenerator;
import com.tibco.be.parser.codegen.JavaSourceWriter;
import com.tibco.be.parser.codegen.SmapFileWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.java.JavaSource;

/**
 * @author Pranab Dhar
 *
 */
public class JavaSourceCodeGenerator implements IJavaCodeGenerator {

	public JavaSourceCodeGenerator() {
	}

	@Override
	public void generateJava(JavaSource javaSource, CodeGenContext ctx) throws Exception {
		boolean isDebug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		File targetDir = (File) ctx.get(CodeGenConstants.BE_CODEGEN_JAVA_SRC_DIR);
		
		String packageName = "";
		
		if (javaSource.getPackageName() != null && !javaSource.getPackageName().equals("null")) {
			packageName = javaSource.getPackageName();
		}
		
		String entityPath = javaSource.getFullPath();
		JavaSourceWriter jswriter = CodeGenHelper.setupJavaSourceWriter(javaSource.getName(),
				packageName, targetDir,entityPath, ModelNameUtil.RULE_SEPARATOR_CHAR);

		jswriter.addSource(javaSource.getSource());
		jswriter.writeFile();
		if(isDebug) {
    		// SMAP
			SmapFileWriter smwriter = CodeGenHelper.setupSmapFileWriter(javaSource.getName(),
					packageName,
					targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			smwriter.writeSMapXmlFile(javaSource.getSource(),entityPath);

    	}
	}

	@Override
	public void generateJavaStream(JavaSource javaSource, CodeGenContext ctx) throws Exception {
		boolean isDebug = (Boolean) ctx.get(CodeGenConstants.BE_CODEGEN_DEBUG);
		JavaFolderLocation targetDir = (JavaFolderLocation) ctx.get(CodeGenConstants.BE_CODEGEN_JAVA_SRC_DIR);
		String packageName = javaSource.getPackageName()==null?"":javaSource.getPackageName();
		String entityPath = javaSource.getFullPath();
		JavaSourceWriter jsWriter = CodeGenHelper.setupJavaSourceWriter(javaSource.getName(),
				packageName, targetDir,entityPath, ModelNameUtil.RULE_SEPARATOR_CHAR);

		jsWriter.addSource(javaSource.getSource());
		jsWriter.writeStream();
		if(isDebug) {
    		// SMAP
			SmapFileWriter smwriter = CodeGenHelper.setupSmapStreamWriter(javaSource.getName(),
					packageName,
					targetDir,
					ModelNameUtil.RULE_SEPARATOR_CHAR);
			smwriter.writeSMapXmlStream(javaSource.getSource(),entityPath);

    	}
	}

}
