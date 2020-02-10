package com.tibco.cep.studio.core.index.utils;

import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;

public class JavaElementCreator {
	
	private JavaElementCreator() {
		
	}

	/**
	 * Creates the Element type holder for the index from the JavaSource entity
	 * @param file
	 * @param entity
	 * @param shared
	 * @return
	 */
	public static JavaElement createJavaElement(IFile file,JavaSource entity,boolean shared) {
		JavaElement element;
		if (shared) {
			element = IndexFactory.eINSTANCE.createSharedJavaElement();
			try {
				((SharedEntityElement) element).setArchivePath(CommonIndexUtils.getLinkedResourcePath(file.getLocationURI()));
				((SharedEntityElement) element).setSharedEntity(entity);
			} catch (URISyntaxException e) {
				StudioCorePlugin.log(e);
			}
		} else {
			element = IndexFactory.eINSTANCE.createJavaElement();
			element.setEntity(entity);
		}
		element.setJavaSource(entity);
		element.setName(entity.getName());
		element.setFolder(entity.getFolder());
		element.setElementType(IndexUtils.getElementType(entity));
		
		return element;
	}
	public static JavaResourceElement createJavaResourceElement(IFile file,JavaResource entity,boolean shared) {
		JavaResourceElement element;
		if (shared) {
			element = IndexFactory.eINSTANCE.createSharedJavaResourceElement();
			try {
				((SharedEntityElement) element).setArchivePath(CommonIndexUtils.getLinkedResourcePath(file.getLocationURI()));
				((SharedEntityElement) element).setSharedEntity(entity);
			} catch (URISyntaxException e) {
				StudioCorePlugin.log(e);
			}
		} else {
			element = IndexFactory.eINSTANCE.createJavaResourceElement();
			element.setEntity(entity);
		}
		element.setJavaResource(entity);
		element.setName(entity.getName());
		element.setFolder(entity.getFolder());
		element.setElementType(IndexUtils.getElementType(entity));
		
		return element;
	}

}
