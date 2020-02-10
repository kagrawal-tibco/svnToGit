package com.tibco.cep.studio.core.grammar.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.grammar.EntityInfo;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class EventInfo extends EntityInfo {
	
	class RuleInfo {
		Rule rule;
		
		public RuleInfo(Rule rule) {
			this.rule = rule;
		}

		public String getActionText() {
			return rule.getActionText();
		}
		
		public Set<Entry<String,String>> getDeclarations() {
			Map<String, Symbol> symbolList = rule.getSymbols().getSymbolMap();
			LinkedHashSet<Entry<String,String>> set = new LinkedHashSet<Entry<String,String>>();
			for (final Symbol symbol : symbolList.values()) {
				set.add(new Entry<String, String>() {
					
					@Override
					public String setValue(String value) {
						throw new UnsupportedOperationException();
					}
					
					@Override
					public String getValue() {
						return symbol.getIdName();
					}
					
					@Override
					public String getKey() {
						return ModelUtils.convertPathToPackage(symbol.getType());
					}
				});
			}
			return set;
		}
	}
	
	class PayloadInfo {
		public String payloadString;
		public Set<EventInfo.NamespaceInfo> namespaces = new HashSet<EventInfo.NamespaceInfo>();

		public Set<NamespaceInfo> getNamespaces() {
			return namespaces;
		}
	}
	
	class NamespaceInfo {
		public String prefix;
		public String namespace;
		public String location;
	}

	public EventInfo(Event event) {
		super(event);
	}

	public boolean isRetryOnException() {
		return getEvent().isRetryOnException();
	}
	
	public String getDefaultDestination() {
		String channelURI = ((SimpleEvent) getEvent()).getChannelURI();
		String destName = ((SimpleEvent)getEvent()).getDestinationName();
		if (destName == null) {
			return null;
		}
		return ModelUtils.convertPathToPackage(channelURI+"/"+destName);
	}
	
	public String getTtl() {
		return getEvent().getTtl();
	}
	
	public String getTtlUnits() {
		return getEvent().getTtlUnits().getLiteral();
	}
	
	private Event getEvent() {
		return (Event) getEntity();
	}
	
	public List<Property> getProperties() { 
		EList<PropertyDefinition> properties = getEvent().getProperties();
		List<Property> propWrappers = new ArrayList<EventInfo.Property>();
		for (PropertyDefinition propDef : properties) {
			propWrappers.add(new Property(propDef));
		}
		return propWrappers; 
	}
	
	public boolean isMetadata() {
		return getEvent().getExtendedProperties() != null 
				&& getEvent().getExtendedProperties().getProperties().size() > 0;
	}
	
	public RuleInfo getExpiryAction() {
		Rule expiryAction = getEvent().getExpiryAction();
		return new RuleInfo(expiryAction);
	}
	
	public PayloadInfo getPayload() {
		String plString = getEvent().getPayloadString();
		if (plString == null || plString.length() == 0) {
			return null;
		}
		plString = BEStringUtilities.escape(plString);
    
		PayloadInfo payload = new PayloadInfo();
		payload.payloadString = plString;
		EList<NamespaceEntry> namespaceEntries = getEvent().getNamespaceEntries();
		for (NamespaceEntry namespaceEntry : namespaceEntries) {
			NamespaceInfo nsInfo = new NamespaceInfo();
			nsInfo.prefix = namespaceEntry.getPrefix();
			nsInfo.namespace = namespaceEntry.getNamespace();
			nsInfo.location = getLocation(nsInfo.namespace);
			payload.namespaces.add(nsInfo);
		}
		return payload;
	}
	
	private String getLocation(String namespace) {
		EList<ImportRegistryEntry> registryImportEntries = getEvent().getRegistryImportEntries();
		for (ImportRegistryEntry importRegistryEntry : registryImportEntries) {
			if (namespace.equals(importRegistryEntry.getNamespace())) {
				String loc = importRegistryEntry.getLocation();
				return BEStringUtilities.escape(loc);
			}
		}
		return null;
	}

	public String getParent() { 
		String superPath = getEvent().getSuperEventPath();
		if (superPath == null || superPath.length() == 0) {
			return null;
		}

		return ModelUtils.convertPathToPackage(superPath); 
	}

}
