package com.tibco.cep.ws.util.wsdlimport;


import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.xml.ws.wsdl.WsPortType;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;

public class ServerSideWsdlImportResult
		extends DefaultWsdlImportResult
{
	
	
    private final Set<Entity> createdEntities = new HashSet<Entity>();  
    private final Set<Entity> modifiedEntities = new HashSet<Entity>();  
	private final Set<WsPortType> processedTypes = new HashSet<WsPortType>();
	private final Set<ResourceSet> sharedResources = new HashSet<ResourceSet>();
	
	
    public Set<Entity> getCreatedEntities()
    {
        return createdEntities;
    }

    
    public Set<Entity> getModifiedEntities()
    {
        return modifiedEntities;
    }

    
	public Set<WsPortType> getProcessedTypes()
	{
		return processedTypes;
	}
	

	public Set<ResourceSet> getSharedResources()
	{
		return this.sharedResources;
	}
    
}
