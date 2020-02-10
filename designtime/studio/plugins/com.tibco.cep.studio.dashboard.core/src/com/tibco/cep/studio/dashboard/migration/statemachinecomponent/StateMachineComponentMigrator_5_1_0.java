package com.tibco.cep.studio.dashboard.migration.statemachinecomponent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.QueryParam;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateSeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextFieldFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.migration.IDashboardResourceMigrator;

public class StateMachineComponentMigrator_5_1_0 implements IDashboardResourceMigrator {

	@Override
	public void migrate(IStudioProjectMigrationContext context, File resource, IProgressMonitor monitor) throws CoreException {
		try {
			StateMachineComponent stateMachineComponent = (StateMachineComponent) DashboardCoreResourceUtils.loadMultipleElements(resource.getAbsolutePath()).get(0);
			boolean migratedDisplayNames = migrateDisplayNames(stateMachineComponent);
			boolean migrateActionRuleBindings = migrateActionRuleBindings(stateMachineComponent);
			boolean stateRefIdsUpdated = updateStateRefIds(stateMachineComponent);
			if (migratedDisplayNames == true || migrateActionRuleBindings == true || stateRefIdsUpdated == true) {
				List<Entity> entities = new ArrayList<Entity>();
				entities.add(stateMachineComponent);
				DashboardCoreResourceUtils.persistEntities(entities, resource.getAbsolutePath(), null);
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not migrate state model component", e));
		}
	}

	private boolean migrateDisplayNames(StateMachineComponent stateMachineComponent) {
		boolean migrated = false;
		EStructuralFeature displayNameAttr = null;
		//go through each visualization in the component
		for (Visualization visualization : stateMachineComponent.getVisualization()) {
			//go through each series config in the visualization
			for (SeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				StateSeriesConfig stateSeriesConfig = (StateSeriesConfig) seriesConfig;
				if (displayNameAttr == null) {
					displayNameAttr = seriesConfig.eClass().getEStructuralFeature(BEViewsConfigurationPackage.SERIES_CONFIG__DISPLAY_NAME);
				}
				if (stateSeriesConfig.eIsSet(displayNameAttr) == false) {
					//display name has not been set
					DataFormat formatter = stateSeriesConfig.getValueDataConfig().get(0).getFormatter();
					//check for progress bar , indicator and text independently since the type hierarchy is text -> indicator -> progress bar
					if (formatter instanceof ProgressBarFieldFormat) {
						//we are dealing with content series config
						stateSeriesConfig.setDisplayName("Content Settings");
						migrated = true;
					}
					else if (formatter instanceof IndicatorFieldFormat) {
						//we are dealing with indicator series config
						stateSeriesConfig.setDisplayName("Indicator Settings");
						migrated = true;
					}
					else if (formatter instanceof TextFieldFormat) {
						//we are dealing with content series config
						stateSeriesConfig.setDisplayName("Content Settings");
						migrated = true;
					}
				}
			}
		}
		return migrated;
	}

	private boolean migrateActionRuleBindings(Component component) {
		boolean modified = false;
		List<Visualization> visualizations = component.getVisualization();
		for (Visualization visualization : visualizations) {
			List<SeriesConfig> seriesConfigs = visualization.getSeriesConfig();
			for (SeriesConfig seriesConfig : seriesConfigs) {
				if (seriesConfig instanceof TwoDimSeriesConfig) {
					TwoDimSeriesConfig twoDimSeriesConfig = (TwoDimSeriesConfig) seriesConfig;
					List<QueryParam> params = twoDimSeriesConfig.getActionRule().getParams();
					for (QueryParam queryParam : params) {
						String value = queryParam.getValue();
						PRE_DEFINED_BINDS predefinedBind = BEViewsQueryDateTypeInterpreter.getPredefinedBind(value);
						if (predefinedBind != null) {
							queryParam.setValue("${SYS:"+predefinedBind+"}");
							modified = true;
						}
					}
				}
			}
		}
		return modified;
	}

	private boolean updateStateRefIds(StateMachineComponent component) {
//		//get the state machine used
//		EObject stateMachineObj = component.getStateMachine();
//		if (stateMachineObj.eIsProxy() == true) {
//			stateMachineObj = EcoreUtil.resolve(stateMachineObj, new ResourceSetImpl());
//		}
//		StateMachine sourceStateMachine = (StateMachine) stateMachineObj;
//		//get it's path
//		String stateMachinePath = sourceStateMachine.getFullPath()+"."+IndexUtils.getFileExtension(sourceStateMachine);
//		//create the replacer
//		StateMachineComponentStateRefIDReplacer replacer = new StateMachineComponentStateRefIDReplacer(source.getProject(), target.getProject(), component, stateMachinePath);
//		//trigger the update
//		replacer.update();
		return false;
	}

}
