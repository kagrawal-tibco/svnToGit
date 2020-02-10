package com.tibco.cep.studio.codegen;

import java.io.File;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.IJavaResourceGenerator;
import com.tibco.be.parser.codegen.JavaResourceWriter;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.java.JavaResource;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

/**
 * @author Pranab Dhar
 *
 */
public class JavaResourceGenerator implements IJavaResourceGenerator {

	public JavaResourceGenerator() {
	}

	@Override
	public void generateJavaResource(JavaResource javaSource, CodeGenContext ctx) throws Exception {
		File targetDir = (File) ctx.get(CodeGenConstants.BE_CODEGEN_JAVA_SRC_DIR);
		String packageName = javaSource.getPackageName()==null?"":javaSource.getPackageName();
		String entityPath = javaSource.getFullPath();
		String extension = javaSource.getExtension();
		String fullName = javaSource.getName()+'.'+extension;
		JavaResourceWriter jswriter = CodeGenHelper.setupJavaResourceWriter(fullName,
				packageName, targetDir,entityPath, ModelNameUtil.RULE_SEPARATOR_CHAR);
		jswriter.addSource(javaSource.getContent());
		jswriter.writeFile();
		
	}

	@Override
	public void generateJavaResourceStream(JavaResource javaSource, CodeGenContext ctx) throws Exception {
		JavaFolderLocation targetDir = (JavaFolderLocation) ctx.get(CodeGenConstants.BE_CODEGEN_JAVA_SRC_DIR);
		String packageName = javaSource.getPackageName() == null ? "" : javaSource.getPackageName();
		String extension = javaSource.getExtension();
		String entityPath = javaSource.getFullPath();
		String extn = CommonIndexUtils.DOT + extension;
		if (!entityPath.endsWith(extn)) {
			entityPath = entityPath + extn;
		}
		String javaSourceName = javaSource.getName(); 
		if (!javaSourceName.endsWith(extn)) {
			javaSourceName = javaSourceName + extn;
		}
		if (packageName.isEmpty() || packageName.equals("null")) {
			String folder = javaSource.getFolderPath();
			String projectName = javaSource.getOntology().getName();
			String path = "/" + projectName + folder;
			String javaSrcUri = StudioJavaUtil.getJavaSourceFolderPath(path, javaSource.getOntology().getName());
			if (!javaSrcUri.isEmpty()) {
				String _packageName = path.replace(javaSrcUri, "");
				packageName = _packageName.replace("/", CommonIndexUtils.DOT).substring(0);
				packageName = packageName.substring(1, packageName.length() - 1);
			}
		}
		
		JavaResourceWriter jsWriter = CodeGenHelper.setupJavaResourceWriter(javaSourceName,
				packageName, targetDir, entityPath, ModelNameUtil.RULE_SEPARATOR_CHAR);

		jsWriter.addSource(javaSource.getContent());
		jsWriter.writeStream();
		
	}

}
