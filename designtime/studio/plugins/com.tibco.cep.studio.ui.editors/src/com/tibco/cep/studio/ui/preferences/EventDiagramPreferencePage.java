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
public class EventDiagramPreferencePage extends AbstractFieldEditorPreferencePage{
	
	public EventDiagramPreferencePage(){
		super();
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}
	
	@Override
	protected void createFieldEditors() {
		setPreference(EVENT);
		Composite parent =getFieldEditorParent();
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.EVENT_SHOW_ALL_PROPERTIES_IN_EVENT_NODE,
				Messages.getString("studio.event.preference.show.all.properties.in.event.node"),parent));
		
		/*String[][] edgeLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.straight"),
					StudioPreferenceConstants.EVENT_LINK_TYPE_STRAIGHT},
					{ Messages.getString("studio.preference.diagram.curved"),
						StudioPreferenceConstants.EVENT_LINK_TYPE_CURVED} };

		RadioGroupFieldEditor linkFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.EVENT_LINK_TYPE, Messages.getString("studio.preference.diagram.linktypes"), 4,
				edgeLabelAndValues, parent, true);
		addField(linkFieldEditor);*/

		String[][] gridLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.none"),
					StudioPreferenceConstants.EVENT_NONE},
					{ Messages.getString("studio.preference.diagram.lines"),
						StudioPreferenceConstants.EVENT_LINES},
						{ Messages.getString("studio.preference.diagram.points"),
							StudioPreferenceConstants.EVENT_POINTS}};
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.EVENT_GRID, Messages.getString("studio.preference.diagram.grid"), 6,
				gridLabelAndValues, parent, true);
		addField(gridFieldEditor);

		addField(new BooleanFieldEditor(StudioPreferenceConstants.EVENT_SNAP_TO_GRID,	
				Messages.getString("studio.preference.diagram.snaptogrid"),parent));

		String[][] qualityLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.draft"),
					StudioPreferenceConstants.EVENT_DRAFT},
					{ Messages.getString("studio.preference.diagram.medium"),
						StudioPreferenceConstants.EVENT_MEDIUM},
						{ Messages.getString("studio.preference.diagram.proof"),
							StudioPreferenceConstants.EVENT_PROOF}};
		RadioGroupFieldEditor qualityFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.EVENT_QAULITY, Messages.getString("studio.preference.diagram.quality"), 6,
				qualityLabelAndValues, parent, true);
		addField(qualityFieldEditor);

		String[][] styleLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.EVENT_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.hierarchical"),
						StudioPreferenceConstants.EVENT_HIERARCHICAL}};
		StyleGroupFieldEditor styleFieldEditor = new StyleGroupFieldEditor(
				StudioPreferenceConstants.EVENT_STYLE, Messages.getString("studio.preference.diagram.style"), 4,
				styleLabelAndValues, parent, true,this);
		addField(styleFieldEditor);

		String[][] ruoutingLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.EVENT_ROUTING_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.polyline"),
						StudioPreferenceConstants.EVENT_ROUTING_POLYLINE}};
		RoutingGroupFieldEditor routingFieldEditor = new RoutingGroupFieldEditor(
				StudioPreferenceConstants.EVENT_ROUTING, Messages.getString("studio.preference.diagram.routing"), 4,
				ruoutingLabelAndValues, parent, true,this);
		addField(routingFieldEditor);
	}

	public void init(IWorkbench workbench) {
	}
}