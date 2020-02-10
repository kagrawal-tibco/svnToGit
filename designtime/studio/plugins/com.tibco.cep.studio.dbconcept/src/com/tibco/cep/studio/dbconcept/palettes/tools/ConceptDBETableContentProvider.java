package com.tibco.cep.studio.dbconcept.palettes.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBProperty;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;

/**
 * 
 * @author moshaikh
 * 
 */
public class ConceptDBETableContentProvider implements ITreeContentProvider {

	ConceptDBETableModel dbeTableModel;

	public ConceptDBETableContentProvider(ConceptDBETableModel dbeTableModel) {
		this.dbeTableModel = dbeTableModel;
	}
	
	/**
	 * Retrieves all elements that come under the specified parent in hierarchy (includes children, grand children and so on).<br/>
	 * Pass <code>null</code> to get all the elements.
	 * @param parent
	 * @return
	 */
	public Collection<Object> getAllSubElements(Object parentElement) {
		List<Object> elements = new ArrayList<Object>();
		if (parentElement == null) {
			Object[] rootElements = getElements(dbeTableModel);
			if (rootElements != null && rootElements.length > 0) {
				for(Object rootElement : rootElements) {
					elements.add(rootElement);
					elements.addAll(getAllSubElements(rootElement));
				}
			}
		}
		else {
			Object[] children = getChildren(parentElement);
			for (Object child : children) {
				elements.add(child);
				elements.addAll(getAllSubElements(child));
			}
		}
		return elements;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput != null && newInput instanceof ConceptDBETableModel) {
			dbeTableModel = (ConceptDBETableModel) newInput;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<Object> elements = new ArrayList<Object>();
		if (this.dbeTableModel != null) {
			if (DBDataSource.MSSQL.equals(this.dbeTableModel.getDatabaseType())) {
				elements.add(this.dbeTableModel.getDbeCatalog());
			} else {
				elements.addAll(this.dbeTableModel.getSelectedSchemas());
			}
		}
		return elements.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> children = new ArrayList<Object>();
		if (parentElement instanceof DBEntityCatalog) {
			DBEntityCatalog dbeCatalog = (DBEntityCatalog) parentElement;
			Iterator<DBSchema> dbSchemaIter = dbeCatalog.getDBSchemas()
					.values().iterator();
			while (dbSchemaIter.hasNext()) {
				DBSchema dbs = dbSchemaIter.next();
				if (dbeTableModel.getSelectedSchemas().contains(dbs)) {
					children.add(dbs);
				}
			}
		} else if (parentElement instanceof DBSchema) {
			DBSchema dbs = (DBSchema) parentElement;
			Iterator<DBEntity> dbEntityIter = dbs.getEntities().values()
					.iterator();
			while (dbEntityIter.hasNext()) {
				DBEntity dbe = dbEntityIter.next();
				if (dbeTableModel.getSelectedEntities().contains(dbe)) {
					children.add(dbe);
				}
			}
		} else if (parentElement instanceof DBEntity) {
			DBEntity dbe = (DBEntity) parentElement;
			children.addAll(dbe.getProperties());
		}
		return children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof DBEntityCatalog) {
			return null;
		} else if (element instanceof DBSchema) {
			return this.dbeTableModel.getDbeCatalog();
		} else if (element instanceof DBEntity) {
			DBEntity dbe = (DBEntity) element;
			return this.dbeTableModel.getDbeCatalog().getDBSchema(dbe.getSchema());
		} else if (element instanceof DBProperty) {
			Collection<DBSchema> schemas = this.dbeTableModel.getSelectedSchemas();
			for (DBSchema schema : schemas) {
				Map<String, DBEntity> entities = schema.getEntities();
				for (DBEntity entity : entities.values()) {
					if (entity.getProperties().contains(element)) {
						return entity;
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof DBEntityCatalog) {
			DBEntityCatalog dbeCatalog = (DBEntityCatalog) element;
			return dbeCatalog.getDBSchemas().values().size() > 0;
		} else if (element instanceof DBSchema) {
			DBSchema dbs = (DBSchema) element;
			return dbs.getEntities().values().size() > 0;
		} else if (element instanceof DBEntity) {
			DBEntity dbe = (DBEntity) element;
			return !dbe.getProperties().isEmpty();
		}
		return false;
	}
}
