package com.tibco.cep.webstudio.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tibco.cep.webstudio.client.palette.Palette;
import com.tibco.cep.webstudio.client.palette.PaletteTool;
import com.tibco.cep.webstudio.client.palette.PaletteToolGroup;
import com.tibco.cep.webstudio.client.palette.PaletteToolbarService;
import com.tibco.cep.webstudio.server.model.palette.Palette.Groups.Group;
import com.tibco.cep.webstudio.server.model.palette.Palette.Groups.Group.Items.Item;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDataSerializer;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class PaletteToolbarServiceImpl extends RemoteServiceServlet implements PaletteToolbarService
{
	private String palette;
	public String getMessage(String msg) {
		return "";
	}

	public Palette getToolbar(String palette) {
		if(null==this.palette  || this.palette.trim().isEmpty())
		this.palette = palette;
		Palette palettetoobar = new Palette();
		try {
			com.tibco.cep.webstudio.server.model.palette.Palette palettes = (com.tibco.cep.webstudio.server.model.palette.Palette) ProcessDataSerializer.deserializePalette(this.palette);
			for (Group grp: palettes.getGroups().getGroup()) {
				if (!grp.isVisible()) {
					continue;
				}
				PaletteToolGroup paletteToolGrp = new PaletteToolGroup(grp.getTitle(), grp.getIcon(),grp.getGroupId());
				/*if (grp.getTitle().equals("General")) {
					paletteToolGrp.getPaletteTools().add(new PaletteTool("Select Tool", "Select Tool", "Select", "select.gif", "Select", null, null, grp.getTitle()));
					paletteToolGrp.getPaletteTools().add(new PaletteTool("Pan Tool", "Pan Tool", "Pan", "pan.gif", "Pan", null, null, grp.getTitle()));
				}*/
				for (Item item : grp.getItems().getItem()) {
					if (!item.isVisible()) {
						continue;
					}
			
					if ("general".equals(grp.getGroupId()) && ("general.association".equals(item.getItemId()) || "general.sequence".equals(item.getItemId()))) {
						
						paletteToolGrp.getPaletteTools().add(new PaletteTool("Create New Flow Edge", "BetweenTool", item.getTooltip(), item.getIcon(), item.getTitle(), 
								item.getEmfType(), item.getExtendedType(), grp.getTitle(),item.getItemId()));
						
					} else {
						if (!("activity.javatask".equals(item.getItemId()) || "activity.manual".equals(item.getItemId()) || "activity.subProcess"
								.equals(item.getItemId()))) {
						paletteToolGrp.getPaletteTools().add(new PaletteTool("Create New Flow Node", "AtTool", item.getTooltip(), item.getIcon(), item.getTitle(), 
								item.getEmfType(), item.getExtendedType(), grp.getTitle(),item.getItemId()));
						}
					}
				}
				palettetoobar.getPaletteToolGroup().add(paletteToolGrp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return palettetoobar;
	}
	public String getPalette() {
		return palette;
	}	
}