package com.tibco.cep.studio.dbconcept.palettes.tools;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBProperty;
import com.tibco.cep.studio.dbconcept.conceptgen.DBSchema;
import com.tibco.cep.studio.dbconcept.utils.ImageIconsFactory;

/**
 * 
 * @author moshaikh
 *
 */
public class ConceptDBETableLabelProvider implements ITableLabelProvider {

	private static final Image ICON_DB = ImageIconsFactory.createImageIcon("iconDB.gif");
	private static final Image ICON_TABLE = ImageIconsFactory.createImageIcon("iconTable.gif");
	private static final Image ICON_SCHEMA = ImageIconsFactory.createImageIcon("iconSchema16.gif");
	private static final Image ICON_VIEW = ImageIconsFactory.createImageIcon("iconView.gif");
	private static final Image ICON_PROP_BOOLEAN = ImageIconsFactory.createImageIcon("iconBoolean16.gif");
	private static final Image ICON_PROP_CHAR = ImageIconsFactory.createImageIcon("iconChar16.gif");
	private static final Image ICON_PROP_DATE = ImageIconsFactory.createImageIcon("iconDate16.gif");
	private static final Image ICON_PROP_DATETIME = ImageIconsFactory.createImageIcon("iconTime16.gif");
	private static final Image ICON_PROP_DOUBLE = ImageIconsFactory.createImageIcon("iconReal16.gif");
	private static final Image ICON_PROP_INTEGER = ImageIconsFactory.createImageIcon("iconInteger16.gif");
	private static final Image ICON_PROP_LONG = ImageIconsFactory.createImageIcon("iconInteger16.gif");
	private static final Image ICON_PROP_STRING = ImageIconsFactory.createImageIcon("iconString16.gif");
	private static final Image ICON_PROP_ANY = ImageIconsFactory.createImageIcon("iconAny16.gif");

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof DBEntityCatalog) {
				return ICON_DB;
			} else if (element instanceof DBSchema) {
				return ICON_SCHEMA;
			} else if (element instanceof DBEntity) {
				DBEntity dbe = (DBEntity)element;
				if (dbe.getEntityType() == DBEntity.VIEW)
					return ICON_VIEW;
				else
					return ICON_TABLE;
			} else if (element instanceof DBProperty) {
				DBProperty dbp = (DBProperty) element;
				switch (dbp.getType()) {
				case DBProperty.BOOLEAN:
					return ICON_PROP_BOOLEAN;
				case DBProperty.CHAR:
					return ICON_PROP_CHAR;
				case DBProperty.DATE:
					return ICON_PROP_DATE;
				case DBProperty.DATETIME:
					return ICON_PROP_DATETIME;
				case DBProperty.DOUBLE:
					return ICON_PROP_DOUBLE;
				case DBProperty.INTEGER:
					return ICON_PROP_INTEGER;
				case DBProperty.LONG:
					return ICON_PROP_LONG;
				case DBProperty.STRING:
					return ICON_PROP_STRING;
				default:
					return ICON_PROP_ANY;
				}
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof DBEntityCatalog) {
				DBEntityCatalog dbeCatalog = (DBEntityCatalog) element;
				return dbeCatalog.getName();
			}
			if (element instanceof DBSchema) {
				DBSchema dbs = (DBSchema) element;
				return dbs.getName();
			} else if (element instanceof DBEntity) {
				DBEntity dbe = (DBEntity) element;
				return dbe.getName();
			} else if (element instanceof DBProperty) {
				DBProperty dbp = (DBProperty) element;
				return dbp.getName();
			}
		} else if (columnIndex == 1) {
			if (element instanceof DBEntity) {
				DBEntity dbe = (DBEntity) element;
				return dbe.getAlias();
			} else if (element instanceof DBProperty) {
				DBProperty dbp = (DBProperty) element;
				return dbp.getAlias();
			}
			return null;
		}
		return null;
	}
}
