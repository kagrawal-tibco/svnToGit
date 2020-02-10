package com.tibco.cep.studio.ui.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.views.CatalogFunctionsView.CustomFunctionsCatalogNode;
import com.tibco.cep.studio.ui.views.CatalogFunctionsView.FunctionsCatalogNode;

public class CatalogFunctionsContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {

	private String fActiveProject;

	public CatalogFunctionsContentProvider(String projectName) {
		setActiveProject(projectName);
	}

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object element) {
		List<Object> children = new ArrayList<Object>(0);
		if (element instanceof FunctionsCategory) {
			Iterator all = ((FunctionsCategory) element).all();
			while (all.hasNext()) {
				children.add(all.next());
			}
		} else if (element instanceof Predicate) {
//			return ((Predicate) element).getArguments();
		} else if (element instanceof Map) {
			return ((Map) element).values().toArray();
		} else if (element instanceof FunctionsCatalog) {
			System.out.println("getting FN children");
		} else if (element instanceof CustomFunctionsCatalogNode) {
			getCustomFunctionsCatalogChildren((CustomFunctionsCatalogNode) element, children);
		} else if (element instanceof Object[]) {
			return (Object[]) element;
		} else if (element instanceof FunctionsCatalogNode) {
			FunctionsCatalog catalog = ((FunctionsCatalogNode) element).getCatalog();
			getFunctionsCatalogChildren((FunctionsCatalogNode) element, children, catalog);
		}
		return children.toArray();
	}

	private void getCustomFunctionsCatalogChildren(
			CustomFunctionsCatalogNode element, List<Object> children) {
		if (fActiveProject == null) {
			// BE-13724 : If there is no active project, do not show any custom functions 
			//     -- this could be an issue if custom functions are required for property view editing when no editor is open (can't think of a specific use case)
//			Collection<FunctionsCatalog> values = element.getCatalogs().values();
//			for (FunctionsCatalog functionsCatalog : values) {
//				Iterator catalogs = functionsCatalog.catalogs();
//				while (catalogs.hasNext()) {
//					FunctionsCategory c = (FunctionsCategory) catalogs.next();
//					children.add(c);
//				}
//			}
		} else {
			IResource res = ResourcesPlugin.getWorkspace().getRoot().findMember(fActiveProject);
			if(res != null) {
				IProject project = res.getProject();
					if(!CommonUtil.isStudioProject(project)) {
						return;
					}
			}
			FunctionsCatalog functionsCatalog = null;
			try {
				functionsCatalog = FunctionsCatalogManager.getInstance().getCustomRegistry(fActiveProject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if (functionsCatalog == null) {
	    		return;
	    	}
			Iterator catalogs = functionsCatalog.catalogs();
			while (catalogs.hasNext()) {
				FunctionsCategory c = (FunctionsCategory) catalogs.next();
				children.add(c);
			}
		}
			
	}

	private void getFunctionsCatalogChildren(FunctionsCatalogNode element,
			List<Object> children, FunctionsCatalog catalog) {
		Iterator catalogs = catalog.catalogs();
		while (catalogs.hasNext()) {
			FunctionsCategory c = (FunctionsCategory) catalogs.next();
			String catalogName = c.getName().localName;
			if ("Ontology".equals(catalogName) && element.getType() == FunctionsCatalogNode.STATIC_CATALOG) {
				continue;
			}
			if (fActiveProject != null) {
				if (element.getType() == FunctionsCatalogNode.ONTOLOGY_CATALOG 
					&& !FunctionsCatalogManager.getInstance().getOntologyCategoryName(fActiveProject).equals(catalogName)) {
					continue;
				} 
//				else if (element.getType() == FunctionsCatalogNode.CUSTOM_CATALOG
//						&& !FunctionsCatalogManager.getCustomCategoryName(fActiveProject).equals(catalogName)) {
//					continue;
//				}
			} else {
				// if the active project is not set, do not show any ontology functions
				continue;
			}
			children.add(c);
		}
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Predicate) {
			return false;
		}
		return true;
	}

	public void setActiveProject(String projectName) {
		this.fActiveProject = projectName;
		if (projectName != null) {
			// lookup the Ontology functions for this project
			new FunctionsCatalogLookup(fActiveProject);
		}
	}

	public String getActiveProject() {
		return fActiveProject;
	}

}
