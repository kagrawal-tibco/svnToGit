package com.tibco.cep.webstudio.client.domain;

import java.util.Iterator;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.domain.model.Domain;
import com.tibco.cep.webstudio.client.domain.model.DomainEntry;
import com.tibco.cep.webstudio.client.domain.model.RangeEntry;
import com.tibco.cep.webstudio.client.domain.model.SingleEntry;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

public class DomainDataItem implements IRequestDataItem {
	private Domain domain = null;
	
	public DomainDataItem(Domain domain) {
		this.domain = domain;
	}
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		Node artifactItemElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT);
		rootNode.appendChild(artifactItemElement);

		String domainPath = domain.getFolder() + domain.getName();
		if (domainPath.indexOf(".") != -1) {
			domainPath = domainPath.substring(0, domainPath.indexOf("."));
		}

		Node artifactPathElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_PATH_ELEMENT);
		Text artifactPathText = rootDocument.createTextNode(domainPath);
		artifactPathElement.appendChild(artifactPathText);
		artifactItemElement.appendChild(artifactPathElement);

		Node artifactTypeElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_TYPE_ELEMENT);
		Text artifactTypeText = rootDocument.createTextNode("domain");
		artifactTypeElement.appendChild(artifactTypeText);
		artifactItemElement.appendChild(artifactTypeElement);

		Node artifactExtnElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_FILE_EXTN_ELEMENT);
		Text artifactExtnText = rootDocument.createTextNode("domain");
		artifactExtnElement.appendChild(artifactExtnText);
		artifactItemElement.appendChild(artifactExtnElement);

		StringBuffer artifactContentData = new StringBuffer();
		// Create a root builder element
		Node domainElement = rootDocument.createElement("domain");
		
		Node domainNameElement = rootDocument.createElement("name");
		Text domainNameText = rootDocument.createTextNode(domain.getName());
		domainNameElement.appendChild(domainNameText);
		domainElement.appendChild(domainNameElement);

		Node domainFolderElement = rootDocument.createElement("folder");
		Text domainFolderText = rootDocument.createTextNode(domain.getFolder());
		domainFolderElement.appendChild(domainFolderText);
		domainElement.appendChild(domainFolderElement);

		if (domain.getDescription() != null) {
			Node domainDescElement = rootDocument.createElement("description");
			Text domainDescText = rootDocument.createTextNode(domain.getDescription());
			domainDescElement.appendChild(domainDescText);
			domainElement.appendChild(domainDescElement);
		}
		
		Node projectElement = rootDocument.createElement("ownerProjectName");
		Text projectText = rootDocument.createTextNode(domain.getOwnerProjectName());
		projectElement.appendChild(projectText);
		domainElement.appendChild(projectElement);

		if (domain.getSuperDomainPath() != null) {
			Node superDomainElement = rootDocument.createElement("superDomainPath");
			Text superDomainText = rootDocument.createTextNode(domain.getSuperDomainPath());
			superDomainElement.appendChild(superDomainText);
			domainElement.appendChild(superDomainElement);
		}
		
		Node dataTypeElement = rootDocument.createElement("dataType");
		Text dataTypeText = rootDocument.createTextNode(domain.getDataType());
		dataTypeElement.appendChild(dataTypeText);
		domainElement.appendChild(dataTypeElement);

		if (domain.getNamespace() != null) {
			Node namespaceElement = rootDocument.createElement("namespace");
			Text namespaceText = rootDocument.createTextNode(domain.getNamespace());
			namespaceElement.appendChild(namespaceText);
			domainElement.appendChild(namespaceElement);
		}
		
		serializeDomainEntries(rootDocument, domainElement, domain);
		
		artifactContentData.append(domainElement.toString());
	
		Node artifactContentElement = rootDocument.createElement(XMLRequestBuilderConstants.ARTIFACT_CONTENTS_ELEMENT);
		Text artifactContentsText = rootDocument.createTextNode(artifactContentData.toString());
		artifactContentElement.appendChild(artifactContentsText);
		artifactItemElement.appendChild(artifactContentElement);
	}

	private void serializeDomainEntries(Document rootDocument, Node domainElement, Domain domain) {
		Iterator<DomainEntry> domainEntriesItr = domain.getDomainEntries().iterator();
		while (domainEntriesItr.hasNext()) {
			DomainEntry domainEntry = domainEntriesItr.next();
			
			if (domainEntry instanceof SingleEntry) {
				Node domainEntryElement = rootDocument.createElement("singleEntry");
				domainElement.appendChild(domainEntryElement);

				if (domainEntry.getDescription() != null) {
					Node entryDescElement = rootDocument.createElement("description");
					Text entryDescText = rootDocument.createTextNode(domainEntry.getDescription());
					entryDescElement.appendChild(entryDescText);
					domainEntryElement.appendChild(entryDescElement);
				}			

				SingleEntry singleEntry = (SingleEntry) domainEntry;
				Node entryValueElement = rootDocument.createElement("value");
				Text entryValueText = rootDocument.createTextNode(singleEntry.getValue());
				entryValueElement.appendChild(entryValueText);
				domainEntryElement.appendChild(entryValueElement);

			} else if (domainEntry instanceof RangeEntry) {
				Node domainEntryElement = rootDocument.createElement("rangeEntry");
				domainElement.appendChild(domainEntryElement);

				if (domainEntry.getDescription() != null) {
					Node entryDescElement = rootDocument.createElement("description");
					Text entryDescText = rootDocument.createTextNode(domainEntry.getDescription());
					entryDescElement.appendChild(entryDescText);
					domainEntryElement.appendChild(entryDescElement);
				}			

				RangeEntry rangeEntry = (RangeEntry) domainEntry;				
				Node lowerElement = rootDocument.createElement("lower");
				Text lowerText = rootDocument.createTextNode(rangeEntry.getLower());
				lowerElement.appendChild(lowerText);
				domainEntryElement.appendChild(lowerElement);

				Node upperElement = rootDocument.createElement("upper");
				Text upperText = rootDocument.createTextNode(rangeEntry.getUpper());
				upperElement.appendChild(upperText);
				domainEntryElement.appendChild(upperElement);

				Node lowerInclusiveElement = rootDocument.createElement("lowerInclusive");
				Text lowerInclusiveText = rootDocument.createTextNode(Boolean.toString(rangeEntry.isLowerInclusive()));
				lowerInclusiveElement.appendChild(lowerInclusiveText);
				domainEntryElement.appendChild(lowerInclusiveElement);

				Node upperInclusiveElement = rootDocument.createElement("upperInclusive");
				Text upperInclusiveText = rootDocument.createTextNode(Boolean.toString(rangeEntry.isUpperInclusive()));
				upperInclusiveElement.appendChild(upperInclusiveText);
				domainEntryElement.appendChild(upperInclusiveElement);
			}
		}		
	}
}
