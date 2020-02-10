package com.tibco.cep.decision.cli;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.designtime.core.model.ModelPackage;

public class DecisionTableCLIOpsUtils {
	

	public static void registerEPackages() {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore",
				ModelPackage.eINSTANCE);
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put("http:///com/tibco/cep/decisionproject/model/decisionproject.ecore",
				DecisionProjectModelPackage.eINSTANCE);
	}
}
