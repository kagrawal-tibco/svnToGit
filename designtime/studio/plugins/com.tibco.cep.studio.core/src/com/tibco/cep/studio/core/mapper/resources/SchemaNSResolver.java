package com.tibco.cep.studio.core.mapper.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;

import com.tibco.cep.studio.core.util.ISchemaNSResolver;

public class SchemaNSResolver implements ISchemaNSResolver {

	private ResourceSet resourceSet;

	public SchemaNSResolver(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	public URI getLocation(String namespace) {
		EList<Resource> resources = resourceSet.getResources();
		for (int i = 0; i < resources.size(); i++) {
			Resource resource = resources.get(i);
			if (resource instanceof XSDResourceImpl) {
				XSDSchema schema = ((XSDResourceImpl) resource).getSchema();
				if (schema != null) {
					EList<XSDAttributeDeclaration> attributeDeclarations = schema.getAttributeDeclarations();
					for (XSDAttributeDeclaration decl : attributeDeclarations) {
						if (namespace.equals(decl.getTargetNamespace())) {
							return URI.create(resource.getURI().toPlatformString(true));
						}
					}
				}
			} else if (resource instanceof WSDLResourceImpl) {
				if (!resource.isLoaded()) {
					try {
						resource.load(new HashMap<>());
					} catch (IOException e) {
						e.printStackTrace();
					};
				}
				Map namespaces = ((WSDLResourceImpl) resource).getDefinition().getNamespaces();
				if (namespaces.containsValue(namespace)) {
					org.eclipse.emf.common.util.URI uri = resource.getURI();
					try {
						return new URI(uri.scheme(), uri.devicePath(), uri.fragment());
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
}
