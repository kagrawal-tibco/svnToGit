package com.tibco.cep.bpmn.ui.utils;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;

public class BpmnXSLTutils {

    
	public static void getloopMapperVar( IProject fproj , Map<Object,Object> map,  EObject userObject ) {
		try {
			EObjectWrapper<EClass, EObject> objectWrapper = EObjectWrapper
					.wrap(userObject);
			String mode =BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE;
			
			EObject lcObj = (EObject)objectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
	    	if(lcObj != null) {
	    		ROEObjectWrapper<EClass, EObject> lcw = EObjectWrapper.wrap(lcObj);
	    		if(lcw.isInstanceOf(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS)) {
	    			mode =BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP;
	    		}else if(lcw.isInstanceOf(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS)) {
	    			mode =BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE;
	    		}
	    	}
    		

			if (!mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_NONE)) {
				map.put(BpmnXSLTutils.mapcontainsLoop, true);
			} else {
				return;
			}

			Object attribute = objectWrapper
					.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
			EObjectWrapper<EClass, EObject> loopWrapper = EObjectWrapper
					.wrap((EObject) attribute);
			if (mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP)) {
				attribute = loopWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM);
			
			} else if (mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE)) {
				attribute = loopWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT);
			}

			if (mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP)) {
				map.put(BpmnXSLTutils.maplooptype, MapperConstants.LOOP_COUNTER);
			} else if (mode.equals(BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE)) {
				map.put(BpmnXSLTutils.maplooptype, MapperConstants.LOOP_VAR);
			}
			
			EObject eobj = loopWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_DATA_TYPE);
			if (eobj != null) {
				EObjectWrapper<EClass, EObject> eobjWrap = EObjectWrapper
						.wrap(eobj);
				attribute = eobjWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_VARIABLE_TYPE);
				if (attribute != null) {
					map.put(BpmnXSLTutils.maploopVartype,
							(String) attribute);
				}
				attribute = eobjWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_VARIABLE_NAME);
				if (attribute != null) {
					map.put(BpmnXSLTutils.maploopVarname,
							(String) attribute);
				}
				attribute = eobjWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_VARIABLE_PATH);
				if (attribute != null) {
					map.put(BpmnXSLTutils.maploopVarpath,
							(String) attribute);
				}
				attribute = eobjWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_MULTIPLE);
				if (attribute != null) {
					map.put(BpmnXSLTutils.maploopvarisArray,
							(Boolean) attribute);
				}
			}
		} catch (Exception e) {

		}
		
	}

	/*
		 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
		 */
	//	private boolean containsLoop ;
	//	private String loopVartype ;
	//	private String loopVarpath ;
		public static String mapcontainsLoop = "containsLoop";
		public static String maploopVartype = "loopVartype";
		public static String maploopVarpath = "loopVarpath";
		public static String maploopVarname= "loopVarname";
		public static String maplooptype = "looptype" ;
		public static String maploopvarisArray = "loopVarisArray" ;
	
}
