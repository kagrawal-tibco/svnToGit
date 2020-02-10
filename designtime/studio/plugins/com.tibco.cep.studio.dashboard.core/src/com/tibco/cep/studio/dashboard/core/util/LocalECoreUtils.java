package com.tibco.cep.studio.dashboard.core.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalECoreUtils {
	
	public static String getExtensionFor(EObject entity) {
		String extension = BEViewsElementNames.getExtension(entity.eClass().getName());
		if (extension != null){
			return extension;
		}
		return IndexUtils.getFileExtension((Entity)entity);
	}

	public static String fileToElementType(IFile file) {
		return BEViewsElementNames.getType(file.getFileExtension());
	}
	
	public static void loadFully(LocalElement localElement, boolean loadProperties, boolean loadParticles) throws Exception {
		if (loadProperties == true) {
			List<ISynXSDAttributeDeclaration> properties = localElement.getProperties();
			for (ISynXSDAttributeDeclaration property : properties) {
				localElement.getPropertyValue(property.getName());
			}
		}
		if (loadParticles == true){
			List<LocalParticle> particles = localElement.getParticles(true);
			for (LocalParticle particle : particles) {
				List<LocalElement> elements = localElement.getChildren(particle.getName());
				if (particle.isReference() == false) {
					for (LocalElement element : elements) {
						loadFully(element, loadProperties, loadParticles);
					}
				}
			}
		}
		
	}
}
