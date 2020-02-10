package com.tibco.cep.studio.core.java;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

import com.tibco.cep.designtime.core.model.java.JavaFactory;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.grammar.java.JavaPackageLexer;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class CommonJavaParserManager {

	private static boolean fDebug = false;

	public static JavaSource parseJavaFile(String projectName, String fullFilePath) {
		try {
			printDebug("Attempting parse of " + fullFilePath);
			JavaPackageLexer lexer = new JavaPackageLexer(new ANTLRFileStream(fullFilePath, ModelUtils.DEFAULT_ENCODING));
			CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);

			String packageName = getPackageName(tokens);
			JavaSource javaSrc = JavaFactory.eINSTANCE.createJavaSource();
			javaSrc.setPackageName(packageName);
			javaSrc.setOwnerProjectName(projectName);
			return javaSrc;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JavaSource parseJavaInputStream(String projectName, InputStream is) {
		try {

			ANTLRInputStream antlrStream = new ANTLRInputStream(is, ModelUtils.DEFAULT_ENCODING);
			return parseJavaInputStream(projectName, antlrStream, true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getPackageName(CommonTokenStream tokens) {
		List<?> tokensList = tokens.getTokens();
		for (Object object : tokensList) {
			Token token = (Token) object;
			if (token.getType() == JavaPackageLexer.PackageStatement) {
				String packageText = token.getText();
				packageText = packageText.substring("package".length()).trim(); // trim
				// 'package'
				// at
				// start
				packageText = packageText.substring(0, packageText.length() - 1); // trim
				// semicolon
				// System.out.println("package is "+packageText);
				return packageText;
			}
		}
		return "";
	}

	public static JavaSource parseJavaCharArray(String projectName, char[] charArray, boolean includeTokensInTree) {
		try {
			ANTLRStringStream antlrStream = new ANTLRStringStream(charArray, charArray.length);
			return parseJavaInputStream(projectName, antlrStream, includeTokensInTree);
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static JavaSource parseJavaInputStream(String projectName, ANTLRStringStream antlrStream, boolean includeTokens) throws RecognitionException {
		JavaPackageLexer lexer = new JavaPackageLexer(antlrStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);
		String packageName = getPackageName(tokens);
		JavaSource javaSrc = JavaFactory.eINSTANCE.createJavaSource();
		javaSrc.setPackageName(packageName);
		javaSrc.setOwnerProjectName(projectName);
		javaSrc.setFullSourceText(tokens.toString().getBytes());
		return javaSrc;

	}

	private static void printDebug(String message) {
		if (fDebug) {
			System.out.println(message);
		}
	}

	public static JavaSource parseJavaFile(String projectName, File file, boolean includeTokensInTree) {
		return parseJavaFile(projectName, file.getAbsolutePath());
	}

	public static JavaResource getJavaResource(String projectName, File projectPath, File file) {
		JavaResource javaResource = JavaFactory.eINSTANCE.createJavaResource();
		String entityFolder = CommonIndexUtils.getFileFolder(new Path(projectPath.getPath()), new Path(file.getPath()));
		String extension = file.getName().substring(file.getName().indexOf(CommonIndexUtils.DOT) + 1);

		String fileName = new Path(file.getPath()).removeFileExtension().lastSegment();
		javaResource.setFolder(entityFolder);
		javaResource.setOwnerProjectName(projectName);
		javaResource.setName(fileName);
		javaResource.setExtension(extension);
		try {
			InputStream is = new FileInputStream(file); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				baos.write(data, 0, nRead);
			}
			baos.flush();
			javaResource.setContent(baos.toByteArray());
			is.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} 
		return javaResource;
	}

	public static JavaResource getJavaResource(String projectName, String rootPath, String uri, InputStream is) {
		JavaResource javaResource = JavaFactory.eINSTANCE.createJavaResource();
		String entityFolder = CommonIndexUtils.getFileFolder(new Path(rootPath), new Path(uri));
		String extension = uri.substring(uri.lastIndexOf(".")  + 1);
		String fileName = new Path(uri).removeFileExtension().lastSegment();
		javaResource.setFolder(entityFolder);
		javaResource.setOwnerProjectName(projectName);
		javaResource.setName(fileName);
		javaResource.setExtension(extension);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				baos.write(data, 0, nRead);
			}
			baos.flush();
			javaResource.setContent(baos.toByteArray());
			is.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} 
		return javaResource;
	}

}
