package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author hitesh
 *
 */

public class DependencyDiagramPreferencePage extends AbstractFieldEditorPreferencePage {

	public DependencyDiagramPreferencePage(){
		super();
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}
	
	protected void createFieldEditors() {
		setPreference(DEPENDENCY);
		Composite parent = getFieldEditorParent();
		/*String[][] dependencyLevels = {
				{ Messages.getString("studio.dependency.preference.diagram.one"),
					StudioPreferenceConstants.DEPENDENCY_ONE},
					{ Messages.getString("studio.dependency.preference.diagram.two"),
						StudioPreferenceConstants.DEPENDENCY_TWO},
						{ Messages.getString("studio.dependency.preference.diagram.all"),
							StudioPreferenceConstants.DEPENDENCY_ALL}};
		RadioGroupFieldEditor dependencyFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.DEPENDENCY_LEVELS, Messages.getString("studio.dependency.preference.diagram.levels"), 6,
				dependencyLevels, parent, true);
		addField(dependencyFieldEditor);*/
		
		/*String[][] edgeLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.straight"),
					StudioPreferenceConstants.DEPENDENCY_LINK_TYPE_STRAIGHT},
					{ Messages.getString("studio.preference.diagram.curved"),
						StudioPreferenceConstants.DEPENDENCY_LINK_TYPE_CURVED} };

		RadioGroupFieldEditor linkFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.DEPENDENCY_LINK_TYPE, Messages.getString("studio.preference.diagram.linktypes"), 4,
				edgeLabelAndValues, parent, true);
		addField(linkFieldEditor);*/

		String[][] gridLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.none"),
					StudioPreferenceConstants.DEPENDENCY_NONE},
					{ Messages.getString("studio.preference.diagram.lines"),
						StudioPreferenceConstants.DEPENDENCY_LINES},
						{ Messages.getString("studio.preference.diagram.points"),
							StudioPreferenceConstants.DEPENDENCY_POINTS}};
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.DEPENDENCY_GRID, Messages.getString("studio.preference.diagram.grid"), 6,
				gridLabelAndValues, parent, true);
		addField(gridFieldEditor);

		addField(new BooleanFieldEditor(StudioPreferenceConstants.DEPENDENCY_SNAP_TO_GRID,
				Messages.getString("studio.preference.diagram.snaptogrid"),parent));

		String[][] qualityLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.draft"),
					StudioPreferenceConstants.DEPENDENCY_DRAFT},
					{ Messages.getString("studio.preference.diagram.medium"),
						StudioPreferenceConstants.DEPENDENCY_MEDIUM},
						{ Messages.getString("studio.preference.diagram.proof"),
							StudioPreferenceConstants.CONCEPT_PROOF}};
		RadioGroupFieldEditor qualityFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.DEPENDENCY_QUALITY, Messages.getString("studio.preference.diagram.quality"), 6,
				qualityLabelAndValues, parent, true);
		addField(qualityFieldEditor);

		String[][] styleLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.DEPENDENCY_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.hierarchical"),
						StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL}};
		StyleGroupFieldEditor styleFieldEditor = new StyleGroupFieldEditor(
				StudioPreferenceConstants.DEPENDENCY_STYLE, Messages.getString("studio.preference.diagram.style"), 4,
				styleLabelAndValues, parent, true,this);
		addField(styleFieldEditor);

		String[][] ruoutingLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.DEPENDENCY_ROUTING_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.polyline"),
						StudioPreferenceConstants.DEPENDENCY_ROUTING_POLYLINE}};
		RoutingGroupFieldEditor routingFieldEditor = new RoutingGroupFieldEditor(
				StudioPreferenceConstants.DEPENDENCY_ROUTING, Messages.getString("studio.preference.diagram.routing"), 4,
				ruoutingLabelAndValues, parent, true,this);
		addField(routingFieldEditor);
		
	}

	public void init(IWorkbench workbench) {
	}
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
	}

}
