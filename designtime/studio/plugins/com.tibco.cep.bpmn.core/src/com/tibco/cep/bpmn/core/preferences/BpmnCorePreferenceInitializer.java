package com.tibco.cep.bpmn.core.preferences;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;

public class BpmnCorePreferenceInitializer extends
		AbstractPreferenceInitializer {

	public BpmnCorePreferenceInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
//		Preferences prefs = BpmnCorePlugin.getDefault().getPluginPreferences();
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_FOLDER, BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX, BpmnCommonModelUtils.BPMN_PROCESS_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX, BpmnCommonModelUtils.BPMN_RULE_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX, BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX, BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_EVENT_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX);
//		prefs.setDefault(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX, BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX);
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(BpmnCorePlugin.PLUGIN_ID);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_FOLDER, BpmnCommonModelUtils.CODE_GEN_FOLDER_PATH);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_PROCESS_PREFIX, BpmnCommonModelUtils.BPMN_PROCESS_PREFIX);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_RULE_PREFIX, BpmnCommonModelUtils.BPMN_RULE_PREFIX);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_RULE_FUNCTION_PREFIX, BpmnCommonModelUtils.BPMN_RULE_FUNCTION_PREFIX);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_CONCEPT_PREFIX, BpmnCommonModelUtils.BPMN_CONCEPT_PREFIX);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_EVENT_PREFIX);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_TIME_EVENT_PREFIX, BpmnCommonModelUtils.BPMN_TIME_EVENT_PREFIX);
		prefs.put(BpmnCoreConstants.PREF_CODEGEN_SCORECARD_PREFIX, BpmnCommonModelUtils.BPMN_SCORECARD_PREFIX);
		Collection<EClass> allClasses = BpmnMetaModel.getAllClasses();
//		Set<NamePrefix> namePrefixMap = new HashSet<NamePrefix>();
		EClass[] classFilter = new EClass[] {
			BpmnModelClass.FLOW_ELEMENT,
			BpmnModelClass.CHOREGRAPHY_SUB_PROCESS,
			BpmnModelClass.BOUNDARY_EVENT,
			BpmnModelClass.AD_HOC_SUB_PROCESS,
			BpmnModelClass.CHOREOGRAPHY_TASK,
			BpmnModelClass.TRANSACTION,
			BpmnModelClass.IMPLICIT_THROW_EVENT,			
			BpmnModelClass.TASK,
			BpmnModelClass.DATA_OBJECT,
			BpmnModelClass.CALL_CHOREOGRAPHY_ACTIVITY,
			BpmnModelClass.SCRIPT_TASK,
			BpmnModelClass.USER_TASK,
			BpmnModelClass.DATA_STORE_REFERENCE,
			BpmnModelClass.CHOREOGRAPHY_ACTIVITY,
			
		};
		for(EClass type:allClasses) {
			if(Arrays.asList(classFilter).contains(type) ||
				!BpmnModelClass.BASE_ELEMENT.isSuperTypeOf(type) || 
				type.isAbstract())
				continue;
			String name = type.getName();
			String pfx = ECoreHelper.toTitleCase(type.getName()).replace(' ', '_');
			prefs.put(BpmnCoreConstants.BPMN_NAME_PREFIX+"."+name, pfx);			
		}

	}

}
