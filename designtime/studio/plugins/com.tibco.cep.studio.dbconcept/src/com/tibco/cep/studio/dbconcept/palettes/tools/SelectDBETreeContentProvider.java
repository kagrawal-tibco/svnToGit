package com.tibco.cep.studio.dbconcept.palettes.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;

/**
 * 
 * @author moshaikh
 *
 */
public class SelectDBETreeContentProvider implements ITreeContentProvider {

	private SelectDBETreeModel selectDbeTreeModel;

	public SelectDBETreeContentProvider(SelectDBETreeModel selectDbeTreeModel) {
		this.selectDbeTreeModel = selectDbeTreeModel;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput != null && newInput instanceof SelectDBETreeModel) {
			selectDbeTreeModel = (SelectDBETreeModel) newInput;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<Object> elements = new ArrayList<Object>();
		if (this.selectDbeTreeModel != null) {
			if (DBDataSource.MSSQL.equals(this.selectDbeTreeModel.getDataSourceType())) {
				elements.add(this.selectDbeTreeModel.getDbeCatalog());
			}
			else {
				Iterator<DBSchema> dbSchemaIter = this.selectDbeTreeModel.getDbeCatalog().getDBSchemas()
						.values().iterator();
				while (dbSchemaIter.hasNext()) {
					DBSchema dbs = dbSchemaIter.next();
					if (dbs.getEntities().entrySet().size() <= 0)
						continue;
					elements.add(dbs);
				}
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
				children.add(dbs);
			}
		} else if (parentElement instanceof DBSchema) {
			DBSchema dbs = (DBSchema) parentElement;
			Iterator<DBEntity> dbEntityIter = dbs.getEntities().values()
					.iterator();
			while (dbEntityIter.hasNext()) {
				DBEntity dbe = dbEntityIter.next();
				children.add(dbe);
			}
		} else if (parentElement instanceof DBEntity) {
		}
		return children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof DBEntityCatalog) {
			return null;
		} else if (element instanceof DBSchema) {
			return this.selectDbeTreeModel.getDbeCatalog();
		} else if (element instanceof DBEntity) {
			DBEntity dbe = (DBEntity) element;
			return this.selectDbeTreeModel.getDbeCatalog().getDBSchema(dbe.getSchema());
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof DBEntityCatalog) {
			DBEntityCatalog dbeCatalog = (DBEntityCatalog) element;
			return dbeCatalog.getDBSchemas().size() > 0;
		} else if (element instanceof DBSchema) {
			DBSchema dbs = (DBSchema) element;
			return dbs.getEntities().size() > 0;
		} else if (element instanceof DBEntity) {
			return false;
		}
		return false;
	}
}
