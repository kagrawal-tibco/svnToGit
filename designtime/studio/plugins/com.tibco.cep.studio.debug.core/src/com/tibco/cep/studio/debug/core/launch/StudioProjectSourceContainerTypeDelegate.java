package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerTypeDelegate;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainerTypeDelegate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class StudioProjectSourceContainerTypeDelegate extends AbstractSourceContainerTypeDelegate implements
		ISourceContainerTypeDelegate {
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainerTypeDelegate#createSourceContainer(java.lang.String)
	 */
	public ISourceContainer createSourceContainer(String memento) throws CoreException {
		Node node = parseDocument(memento);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element)node;
			if ("studioProject".equals(element.getNodeName())) { //$NON-NLS-1$
				String string = element.getAttribute("name"); //$NON-NLS-1$
				if (string == null || string.length() == 0) {
					abort("Missing required <name> attribute in Studio project source container memento.", null); 
				}
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IProject project = workspace.getRoot().getProject(string);
				return new StudioProjectSourceContainer(project);
			}
			abort("Missing required <javaProject> attribute in Studio project source container memento.", null); 
		}
		abort("Invalid format for Studio project source container memento.", null); 
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceContainerTypeDelegate#getMemento(org.eclipse.debug.internal.core.sourcelookup.ISourceContainer)
	 */
	public String getMemento(ISourceContainer container) throws CoreException {
		StudioProjectSourceContainer project = (StudioProjectSourceContainer) container;
		Document document = newDocument();
		Element element = document.createElement("studioProject"); //$NON-NLS-1$
		element.setAttribute("name", project.getName()); //$NON-NLS-1$
		document.appendChild(element);
		return serializeDocument(document);
	}

}
