package com.tibco.cep.studio.ui.compare.provider;

import static com.tibco.cep.studio.ui.compare.util.CompareUtils.getBytesForEObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.compare.structuremergeviewer.IStructureCreator;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.xml.sax.InputSource;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;
import com.tibco.cep.studio.ui.compare.model.EMFResourceNode;
import com.tibco.cep.studio.ui.compare.model.RuleResourceNode;

public class ResourceStructureCreator implements IStructureCreator {

	private StructureDiffViewer fViewer;

	public ResourceStructureCreator(StructureDiffViewer viewer) {
		super();
		this.fViewer = viewer;
	}

	public String getName() {
		return "Structure Compare";
	}

	public String getContents(Object node, boolean ignoreWhitespace) {
		return node.toString();
	}

	protected static String read(InputStream in, String encoding) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(in, encoding);
			char[] buf = new char[4096];
			int c;
			while ((c = reader.read(buf)) != -1) {
				stringBuffer.append(buf, 0, c);
			}
			
			return stringBuffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public IStructureComparator getStructure(final Object input) {
		if (input instanceof AbstractResourceNode) {
			AbstractResourceNode node = (AbstractResourceNode) input;
			if (node.getInput() instanceof EObject) {
				return node;
			}
		}
		// instead, try to load the xmi into a object
		InputStream in = null;
		if (input instanceof IStreamContentAccessor) {
			try {
				in = ((IStreamContentAccessor) input).getContents();
			} catch (CoreException e) {
				StudioUIPlugin.getDefault().getLog().log(e.getStatus());
			}
		}

		if (in == null)
			return null;

		URI uri = URI.createURI("");
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);

		if (resource instanceof XMLResource) {

			((XMLResource) resource).setEncoding("UTF-8");

			String text = null;
			Reader reader = null;
			try {
				text = read(in, "UTF-8");
				reader = new StringReader(text);
				((XMLResource) resource).load(new InputSource(reader), new HashMap());
			} catch (IOException e) {
				StudioUIPlugin.log("Could not parse input.", e);
				return null;
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
					}
				}
			}
			TreeIterator<EObject> allContents = resource.getAllContents();
			if (allContents.hasNext()) {
				EMFResourceNode node = new EMFResourceNode(allContents.next(), "", false, false);
				return node;
			}
			EMFResourceNode node = new EMFResourceNode(text, "", false, false);
			return node;
		}

		EMFResourceNode node = new EMFResourceNode(input, "", false, false);
		return node;
	}

	public IStructureComparator locate(Object path, Object input) {
		return null;
	}

	public void save(IStructureComparator node, Object input) {
		AbstractResourceNode rNode = null;
		if (input instanceof EMFResourceNode) {
			rNode = (EMFResourceNode) input;
		} else if (node instanceof EMFResourceNode) {
			rNode = (EMFResourceNode) node;
		}
		else if (node instanceof RuleResourceNode) {
			rNode = (RuleResourceNode) node;
		}
		if (rNode == null) {
			return;
		}
		Object eObject = rNode.getInput();
		try {
			byte[] bytes = null;
			if (eObject instanceof Table) {
				Table table = (Table) eObject;
				bytes = getBytesForEObject(table);
			} 
			if (eObject instanceof Entity) {
				Entity entity = (Entity)eObject;
				entity = (Entity) IndexUtils.getRootContainer(entity);
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
				IPath projectLocation = project.getLocation();
				IPath path = projectLocation.append(entity.getFolder());
				bytes = IndexUtils.getEObjectContents(path.toString(),entity);
			}
			if (input instanceof IEditableContent) {
				((IEditableContent)input).setContent(bytes);
			}
			rNode.setContent(bytes);
			((CompareEditorInput)fViewer.getCompareConfiguration().getContainer()).setDirty(true);
			fViewer.refresh(); // needed?
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}