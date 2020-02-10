package com.tibco.cep.studio.ui.providers;

import java.io.File;

import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.views.GlobalVariablesView.GlobalVariableContainerNode;

public class GlobalVariablesLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof GlobalVariableDescriptor) {
			GlobalVariableDescriptor desc = (GlobalVariableDescriptor) element;
			String type = desc.getType();
			String projectSrc = desc.getProjectSource();
			return getImageFromType(type, projectSrc.contains(File.separator));
			
		} else if (element instanceof GlobalVariableContainerNode) {
			return StudioUIPlugin.getDefault().getImage("icons/group.png");
		} 
		return super.getImage(element);
	}

	private Image getImageFromType(String type, boolean jar) {
		if ("String".equals(type)) {
			return getImage(StudioUIPlugin.getDefault().getImage("icons/iconString16.gif"), jar);
		}
		if ("Integer".equals(type)) {
			return getImage(StudioUIPlugin.getDefault().getImage("icons/iconInteger16.gif"), jar);
		}
		if ("long".equals(type)) {
			return getImage(StudioUIPlugin.getDefault().getImage("icons/iconInteger16.gif"), jar);
		}
		if ("Boolean".equals(type)) {
			return getImage(StudioUIPlugin.getDefault().getImage("icons/iconBoolean16.gif"), jar);
		}
		if ("Password".equals(type)) {
			return getImage(StudioUIPlugin.getDefault().getImage("icons/no_type.png"), jar);
		}
		
		return getImage(StudioUIPlugin.getDefault().getImage("icons/function.png"), jar);
	}
	
	@SuppressWarnings("static-access")
	private Image getImage(Image image, boolean jar) {
		if (jar) {
			return new DecorationOverlayIcon(image, StudioUIPlugin.getDefault().getImageDescriptor("icons/vars/overlay_lib.png"),
					IDecoration.BOTTOM_LEFT).createImage();
		}
		return image;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return getImage(element);
		} else if(columnIndex == 3) {
			if(element instanceof GlobalVariableDescriptor) {
				GlobalVariableDescriptor gvd = (GlobalVariableDescriptor) element;
				if(Boolean.TRUE.equals(gvd.isOverridden())) {
					return StudioUIPlugin.getDefault().getImage("icons/previousOccurence.png");
				} 
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof GlobalVariableContainerNode) {
				return ((GlobalVariableContainerNode) element).getName();
			} else if (element instanceof GlobalVariableDescriptor) {
				GlobalVariableDescriptor desc = (GlobalVariableDescriptor) element;
				return desc.getName();
			} else if (element instanceof GlobalVariablesProvider) {
				return "Global Variables";
			}
			return super.getText(element);	
		} else if (columnIndex == 1) {
			if (element instanceof GlobalVariableDescriptor) {
				GlobalVariableDescriptor desc = (GlobalVariableDescriptor) element;
				if ("Password".equals(desc.getType())) {
					return "*******";
				}
				return desc.getValueAsString();
			}
			return ("");	
		} else if (columnIndex == 2) {
			if (element instanceof GlobalVariableDescriptor) {
				GlobalVariableDescriptor desc = (GlobalVariableDescriptor) element;
				return (desc.getType());
			}
			return ("");
		} else if(columnIndex == 4) {
			if (element instanceof GlobalVariableDescriptor) {
				GlobalVariableDescriptor desc = (GlobalVariableDescriptor) element;
				return (desc.getProjectSource());
			}
			return ("");
		}
		return null;
	}

}
