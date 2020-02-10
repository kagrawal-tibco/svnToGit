package com.tibco.cep.studio.core.index.utils;

import java.io.File;
import java.io.InputStream;

import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.java.CommonJavaParserManager;

public class BaseJavaElementCreator {

	private boolean fIncludeBodyText;

	public BaseJavaElementCreator() {
		this(false);
	}

	public BaseJavaElementCreator(boolean includeBodyText) {
		this.fIncludeBodyText = includeBodyText;
	}

	/**
	 * @param projectName
	 * @param projectPath
	 * @param file
	 * @return
	 */
	public JavaElement createJavaElement(String projectName, File projectPath, File file) {
		JavaSource javaSource = CommonJavaParserManager.parseJavaFile(projectName, file, fIncludeBodyText);
		String entityFolder = CommonIndexUtils.getFileFolder(new Path(projectPath.getPath()), new Path(file.getPath()));
		javaSource.setFolder(entityFolder);
		JavaElement element = IndexFactory.eINSTANCE.createJavaElement();
		
		String fileName = new Path(file.getPath()).removeFileExtension().lastSegment();
		javaSource.setName(fileName);
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!entityFolder.equals(element.getFolder())) {
			element.setFolder(entityFolder);
		}
		element.setEntity(javaSource);
		element.setElementType(CommonIndexUtils.getIndexType(new Path(file.getPath())));
		
		return element;
	}
	
	/**
	 * @param projectName
	 * @param projectPath
	 * @param file
	 * @return
	 */
	public JavaResourceElement createJavaResourceElement(String projectName, File projectPath, File file) {
		JavaResource javaResource = CommonJavaParserManager.getJavaResource(projectName, projectPath, file);

		String entityFolder = CommonIndexUtils.getFileFolder(new Path(projectPath.getPath()), new Path(file.getPath()));
		javaResource.setFolder(entityFolder);

		JavaResourceElement element = IndexFactory.eINSTANCE.createJavaResourceElement();

		String fileName = new Path(file.getPath()).removeFileExtension().lastSegment();
		javaResource.setName(fileName);

		element.setName(javaResource.getName());
		element.setFolder(javaResource.getFolder());
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!entityFolder.equals(element.getFolder())) {
			element.setFolder(entityFolder);
		}
		element.setEntity(javaResource);
		element.setJavaResource(javaResource);

		element.setElementType(CommonIndexUtils.getIndexType(new Path(file.getPath())));
		return element;

	}

	/**
	 * @param projectName
	 * @param rootPath
	 * @param uri
	 * @param file
	 * @return
	 */
	public JavaElement createJavaElement(String projectName, String rootPath, String uri, InputStream file) {
		JavaSource javaSource = CommonJavaParserManager.parseJavaInputStream(projectName, file);
		String entityFolder = CommonIndexUtils.getFileFolder(new Path(rootPath), new Path(uri));
		javaSource.setFolder(entityFolder);
		JavaElement element = IndexFactory.eINSTANCE.createJavaElement();
		
		String fileName = new Path(uri).removeFileExtension().lastSegment();
		javaSource.setName(fileName);
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!entityFolder.equals(element.getFolder())) {
			element.setFolder(entityFolder);
		}
		element.setEntity(javaSource);
		element.setJavaSource(javaSource);
		element.setElementType(CommonIndexUtils.getIndexType(new Path(uri)));
		return element;
	}
	
	/**
	 * @param projectName
	 * @param rootPath
	 * @param uri
	 * @param file
	 * @return
	 */
	public JavaResourceElement createJavaResourceElement(String projectName, String rootPath, String uri, InputStream file) {

		JavaResource javaResource = CommonJavaParserManager.getJavaResource(projectName, rootPath, uri, file);
		String entityFolder = CommonIndexUtils.getFileFolder(new Path(rootPath), new Path(uri));
		javaResource.setFolder(entityFolder);
		JavaResourceElement element = IndexFactory.eINSTANCE.createJavaResourceElement();
		String fileName = new Path(uri).removeFileExtension().lastSegment();
		javaResource.setName(fileName);
		element.setName(javaResource.getName());
		element.setFolder(javaResource.getFolder());
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!entityFolder.equals(element.getFolder())) {
			element.setFolder(entityFolder);
		}
		element.setEntity(javaResource);
		element.setJavaResource(javaResource);

		element.setElementType(CommonIndexUtils.getIndexType(new Path(uri)));
		return element;
		
	}

}
