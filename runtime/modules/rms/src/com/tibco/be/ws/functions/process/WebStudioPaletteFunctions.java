/**
 * 
 */
package com.tibco.be.ws.functions.process;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.ws.process.PaletteHelper;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteModel;
import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteItem;

/**
 * Catalog Functions to work with Process Palette.
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Process.Palette",
        synopsis = "Functions to work with Process Palette Model.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Process.Palette", value=true))

public class WebStudioPaletteFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getPaletteModel",
        signature = "Object getPaletteModel()",
        params = {},
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Palette Model."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Palette Model.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getPaletteModel() {
		return PaletteHelper.getPaletteModel();
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getPaletteGroups",
        signature = "Object[] getPaletteGroups(Object bpmnCommonPaletteModel)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "bpmnCommonPaletteModel", type = "Object", desc = "Palette Model Object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Palette Groups."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Palette Groups",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getPaletteGroups(Object bpmnCommonPaletteModel) {
		if (bpmnCommonPaletteModel instanceof BpmnCommonPaletteModel) {
			return PaletteHelper.getPaletteGroups((BpmnCommonPaletteModel)bpmnCommonPaletteModel);
		}
		
		return null;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getGroupDetails",
        signature = "Object getGroupDetails(Object paletteGroup)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "paletteGroup", type = "Object", desc = "Palette Group Object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Palette Groups details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Palette Group details",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getGroupDetails(Object paletteGroup) {
		if (paletteGroup instanceof PaletteGroup) {
			return PaletteHelper.getGroupDetails((PaletteGroup)paletteGroup);
		}
		
		return null;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getPaletteItems",
        signature = "Object[] getPaletteItems(Object paletteGroup)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "paletteGroup", type = "Object", desc = "Palette Group Object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Palette Items."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Palette Items",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getPaletteItems(Object paletteGroup) {
		if (paletteGroup instanceof PaletteGroup) {
			return PaletteHelper.getPaletteItems((PaletteGroup)paletteGroup);
		}
		
		return null;
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getItemDetails",
		signature = "Object getItemDetails(Object paletteItem)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "paletteItem", type = "Object", desc = "Palette Item Object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Palette Item details."),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrieves Palette Item details",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object getItemDetails(Object paletteItem) {
		if (paletteItem instanceof PaletteItem) {
			return PaletteHelper.getItemDetails((PaletteItem)paletteItem);
		}
		
		return null;
	}
}
