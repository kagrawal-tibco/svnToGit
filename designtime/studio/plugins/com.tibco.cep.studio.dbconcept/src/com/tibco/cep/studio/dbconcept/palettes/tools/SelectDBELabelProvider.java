package com.tibco.cep.studio.dbconcept.palettes.tools;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;
import com.tibco.cep.studio.dbconcept.utils.ImageIconsFactory;

/**
 * 
 * @author moshaikh
 *
 */
public class SelectDBELabelProvider extends LabelProvider {

	private static final Image ICON_DB = ImageIconsFactory.createImageIcon("iconDB.gif");
	private static final Image ICON_SCHEMA = ImageIconsFactory.createImageIcon("iconSchema16.gif");
	private static final Image ICON_TABLE = ImageIconsFactory.createImageIcon("iconTable.gif");
	private static final Image ICON_VIEW = ImageIconsFactory.createImageIcon("iconView.gif");

	@Override
	public String getText(Object element) {
		if (element instanceof DBEntityCatalog) {
			DBEntityCatalog dbeCatalog = (DBEntityCatalog) element;
			return dbeCatalog.getName();
		} else if (element instanceof DBSchema) {
			DBSchema dbs = (DBSchema) element;
			return dbs.getName();
		} else if (element instanceof DBEntity) {
			DBEntity dbe = (DBEntity) element;
			return dbe.getAlias();// Name();
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof DBEntityCatalog) {
			return ICON_DB;
		} else if (element instanceof DBSchema) {
			return ICON_SCHEMA;
		} else if (element instanceof DBEntity) {
			DBEntity dbe = (DBEntity) element;
			if (dbe.getEntityType() == DBEntity.VIEW) {
				return ICON_VIEW;
			} else {
				return ICON_TABLE;
			}
		}
		return null;
	}
}
