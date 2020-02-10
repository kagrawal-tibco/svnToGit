package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;

/**
 * @author vpatil
 *
 */
public class EntitySelectionDialog extends ConfigElementSelectionDialog {

	private String selectedEntity;

	public EntitySelectionDialog(Shell parent) {
		super(parent);
		selectedEntity = "";
	}

	public void open(ArrayList<?> entities, ArrayList<?> filterGrp) {
		initDialog("Entity Selector", false);

		for (Object entity: entities) {
			if (entity instanceof EntityElement) {
				EntityElement entityElement = (EntityElement) entity;
				if (!ifCurrentEntity(entityElement, filterGrp)) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setImage(getEntityImage(entityElement));
					String entityName = getEntityName(entityElement);
					item.setText(entityName);
					item.setData(entityName);
				}
			}
		}

		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) 
						selectedEntity = item.getText(0);
				}
				dialog.dispose();
			}
		});

		openDialog();
	}

	private boolean ifCurrentEntity(EntityElement entity, ArrayList<?> filterGrp) {
		String entityPath = getEntityName(entity);
		if (entityPath != null) {
			String curEntity = (String)filterGrp.get(0);
			if (entityPath.equals(curEntity.trim()))
				return true;
		}
		return false;
	}
	
	public String getSelectedEntity() {
		return selectedEntity;
	}
	
	private String getEntityName(EntityElement entity) {
		return entity.getEntity().getFullPath();
	}
	
	private Image getEntityImage(EntityElement entity) {
		if (entity != null) {
			if (entity.getElementType() == ELEMENT_TYPES.CONCEPT) 
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_CONCEPT));
			else if (entity.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT)
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_SIMPLEEVENT));	
			else if (entity.getElementType() == ELEMENT_TYPES.METRIC)
				return (ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_METRIC));	
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
}
