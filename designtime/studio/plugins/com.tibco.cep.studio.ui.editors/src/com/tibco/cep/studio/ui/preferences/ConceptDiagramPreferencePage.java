package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ConceptDiagramPreferencePage extends AbstractFieldEditorPreferencePage{
	
	public ConceptDiagramPreferencePage(){
		super();
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}	
	
	@Override
	protected void createFieldEditors() {
		setPreference(CONCEPT);
		Composite parent =getFieldEditorParent();
		addField(new BooleanFieldEditor(StudioPreferenceConstants.CONCEPT_SHOW_ALL_PROPERTIES_IN_CONCEPT_NODE,
				Messages.getString("studio.concept.preference.show.all.properties.in.concept.node"),parent));

		/*String[][] edgeLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.straight"),
					StudioPreferenceConstants.CONCEPT_LINK_TYPE_STRAIGHT},
					{ Messages.getString("studio.preference.diagram.curved"),
						StudioPreferenceConstants.CONCEPT_LINK_TYPE_CURVED} };

		RadioGroupFieldEditor linkFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.CONCEPT_LINK_TYPE, Messages.getString("studio.preference.diagram.linktypes"), 4,
				edgeLabelAndValues, parent, true);
		addField(linkFieldEditor);*/

		String[][] gridLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.none"),
					StudioPreferenceConstants.CONCEPT_NONE},
					{ Messages.getString("studio.preference.diagram.lines"),
						StudioPreferenceConstants.CONCEPT_LINES},
						{ Messages.getString("studio.preference.diagram.points"),
							StudioPreferenceConstants.CONCEPT_POINTS}};
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.CONCEPT_GRID, Messages.getString("studio.preference.diagram.grid"), 6,
				gridLabelAndValues, parent, true);
		addField(gridFieldEditor);

		addField(new BooleanFieldEditor(StudioPreferenceConstants.CONCEPT_SNAP_TO_GRID,
				Messages.getString("studio.preference.diagram.snaptogrid"),parent));

		String[][] qualityLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.draft"),
					StudioPreferenceConstants.CONCEPT_DRAFT},
					{ Messages.getString("studio.preference.diagram.medium"),
						StudioPreferenceConstants.CONCEPT_MEDIUM},
						{ Messages.getString("studio.preference.diagram.proof"),
							StudioPreferenceConstants.CONCEPT_PROOF}};
		RadioGroupFieldEditor qualityFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.CONCEPT_QUALITY, Messages.getString("studio.preference.diagram.quality"), 6,
				qualityLabelAndValues, parent, true);
		addField(qualityFieldEditor);

		String[][] styleLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.CONCEPT_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.hierarchical"),
						StudioPreferenceConstants.CONCEPT_HIERARCHICAL}};
		StyleGroupFieldEditor styleFieldEditor = new StyleGroupFieldEditor(
				StudioPreferenceConstants.CONCEPT_STYLE, Messages.getString("studio.preference.diagram.style"), 4,
				styleLabelAndValues, parent, true,this);
		addField(styleFieldEditor);

		String[][] ruoutingLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.CONCEPT_ROUTING_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.polyline"),
						StudioPreferenceConstants.CONCEPT_ROUTING_POLYLINE}};
		RoutingGroupFieldEditor routingFieldEditor = new RoutingGroupFieldEditor(
				StudioPreferenceConstants.CONCEPT_ROUTING, Messages.getString("studio.preference.diagram.routing"), 4,
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
