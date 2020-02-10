/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.tools;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.view.drawing.TSModelDrawingView;

/**
 * @author dijadhav
 *
 */
public class ProcessToolUtils {
	/**
	 * This method is used to get the emf type of the active tool.
	 * 
	 * @param view
	 *            - Instance of the TSModelDrawingView.
	 * @return EMF type of the active tool.
	 */
	public static EClass getEmfType(TSModelDrawingView view) {
		Object emfTypeAttribute = view.getModel().getAttribute(
				"ActiveToolEmfType");
		if (emfTypeAttribute != null) {
			String emfType = emfTypeAttribute.toString();
			EClass emfTypeClass = BpmnMetaModel.getInstance().getEClass(
					ExpandedName.parse(emfType));
			return emfTypeClass;
		}
		return null;
	}
	public static String getExtendedName(TSModelDrawingView view) {
		Object[] extendedType = (Object[]) getExtendedType(view);
		String extendedName = "";
		if (null != extendedType) {
			if (extendedType.length == 1) {
				EClass extended = (EClass) extendedType[0];
				extendedName = extended.getName();
			}
		}
		return extendedName;
	}
	/**
	 * This method is used to get the extended type of the element.
	 * 
	 * @param view
	 *            - Instance of the TSModelDrawingView.
	 * @return Extended type of the element.
	 */
	public static Object getExtendedType(TSModelDrawingView view) {
		Object extendedTypeAttribute = view.getModel().getAttribute(
				"ActiveToolExtendedType");
		if (extendedTypeAttribute != null) {
			String extendedType = extendedTypeAttribute.toString();
			if (!extendedType.isEmpty()) {
				EClass extendedTypeClass = BpmnMetaModel.getInstance()
						.getEClass(ExpandedName.parse(extendedType));
				return new Object[] { extendedTypeClass };
			}
		}
		return new Object[] {};
	}
	/**
	 * This method is used to get the type of element which need to create.
	 * 
	 * @param view
	 *            - Instance of the TSModelDrawingView.
	 * @return Type of the element.
	 */
	public static String getElementType(TSModelDrawingView view) {
		Object elementTypeAttribute = view.getModel().getAttribute(
				"ActiveToolType");
		if (elementTypeAttribute != null) {
			return elementTypeAttribute.toString();
		}
		return null;
	}
}
