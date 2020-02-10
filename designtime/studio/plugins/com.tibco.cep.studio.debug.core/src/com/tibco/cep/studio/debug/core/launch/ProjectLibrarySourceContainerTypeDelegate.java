package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerTypeDelegate;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainerTypeDelegate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ProjectLibrarySourceContainerTypeDelegate extends AbstractSourceContainerTypeDelegate implements
		ISourceContainerTypeDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainerTypeDelegate#createSourceContainer(java.lang.String)
	 */
	public ISourceContainer createSourceContainer(String memento) throws CoreException {
		Node node = parseDocument(memento);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element)node;
			if ("projectLibrary".equals(element.getNodeName())) { //$NON-NLS-1$
				String projectName = element.getAttribute("name"); //$NON-NLS-1$
				if (projectName == null || projectName.length() == 0) {
					abort("Missing required project name in source container memento.", null); 
				}
				String path = element.getAttribute("path"); //$NON-NLS-1$
				if (projectName == null || projectName.length() == 0) {
					abort("Missing required project library path in source container memento.", null); 
				}
				return new JarEntrySourceContainer(path,projectName);
			}
			abort("Missing required project name attribute in Java project source container memento.", null); 
		}
		abort("Invalid format for project library source container memento.", null); 
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainerTypeDelegate#getMemento(org.eclipse.debug.internal.core.sourcelookup.ISourceContainer)
	 */
	public String getMemento(ISourceContainer container) throws CoreException {
		JarEntrySourceContainer source = (JarEntrySourceContainer) container;
		Document document = newDocument();
		Element element = document.createElement("projectLibrary"); //$NON-NLS-1$
		element.setAttribute("name", source.getProjectName()); //$NON-NLS-1$
		element.setAttribute("path", source.getJarPath() ); //$NON-NLS-1$
		document.appendChild(element);
		return serializeDocument(document);
	}
}
