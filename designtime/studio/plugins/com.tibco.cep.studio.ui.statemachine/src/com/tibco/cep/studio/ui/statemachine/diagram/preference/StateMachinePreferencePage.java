package com.tibco.cep.studio.ui.statemachine.diagram.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.AbstractFieldEditorPreferencePage;
import com.tibco.cep.studio.ui.preferences.RoutingGroupFieldEditor;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.preferences.StyleGroupFieldEditor;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachinePreferencePage  extends AbstractFieldEditorPreferencePage{

	public StateMachinePreferencePage() {
		super();
		setPreferenceStore(StateMachinePlugin.getDefault().getPreferenceStore());
	}

	public void createFieldEditors() {
		setPreference(STATEMACHINE);
		Composite parent =getFieldEditorParent();
		
//		String[][] layoutActionLabelAndValues = {
//				{ Messages.getString("studio.preference.diagram.sm.autolayout.none"),
//					StudioPreferenceConstants.STATEMACHINE_AUTOLAYOUT_NONE},
//					{ Messages.getString("studio.preference.diagram.sm.autolayout.incr"),
//						StudioPreferenceConstants.STATEMACHINE_AUTOLAYOUT_INCR},
//						{ Messages.getString("studio.preference.diagram.sm.autolayout.full"),
//							StudioPreferenceConstants.STATEMACHINE_AUTOLAYOUT_FULL}};
//		RadioGroupFieldEditor layoutActionFieldEditor = new RadioGroupFieldEditor(
//				StudioPreferenceConstants.STATEMACHINE_QAULITY, Messages.getString("studio.preference.diagram.sm.autolayout"), 6,
//				layoutActionLabelAndValues, parent, true);
//		addField(layoutActionFieldEditor);		
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.STATEMACHINE_FIX_LABELS,	
				Messages.getString("studio.preference.diagram.fix.labels"),parent));
		
		/*String[][] edgeLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.straight"),
					StudioPreferenceConstants.STATEMACHINE_LINK_TYPE_STRAIGHT},
					{ Messages.getString("studio.preference.diagram.curved"),
						StudioPreferenceConstants.STATEMACHINE_LINK_TYPE_CURVED} };

		RadioGroupFieldEditor linkFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.STATEMACHINE_LINK_TYPE, Messages.getString("studio.preference.diagram.linktypes"), 4,
				edgeLabelAndValues, parent, true);
		addField(linkFieldEditor);*/

		String[][] gridLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.none"),
					StudioPreferenceConstants.STATEMACHINE_NONE},
					{ Messages.getString("studio.preference.diagram.lines"),
						StudioPreferenceConstants.STATEMACHINE_LINES},
						{ Messages.getString("studio.preference.diagram.points"),
							StudioPreferenceConstants.STATEMACHINE_POINTS}};
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.STATEMACHINE_GRID, Messages.getString("studio.preference.diagram.grid"), 6,
				gridLabelAndValues, parent, true);
		addField(gridFieldEditor);

		addField(new BooleanFieldEditor(StudioPreferenceConstants.STATEMACHINE_SNAP_TO_GRID,	
				Messages.getString("studio.preference.diagram.snaptogrid"),parent));

		String[][] qualityLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.draft"),
					StudioPreferenceConstants.STATEMACHINE_DRAFT},
					{ Messages.getString("studio.preference.diagram.medium"),
						StudioPreferenceConstants.STATEMACHINE_MEDIUM},
						{ Messages.getString("studio.preference.diagram.proof"),
							StudioPreferenceConstants.STATEMACHINE_PROOF}};
		RadioGroupFieldEditor qualityFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.STATEMACHINE_QAULITY, Messages.getString("studio.preference.diagram.quality"), 6,
				qualityLabelAndValues, parent, true);
		addField(qualityFieldEditor);

		String[][] styleLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.STATEMACHINE_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.hierarchical"),
						StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL}};
		StyleGroupFieldEditor styleFieldEditor = new StyleGroupFieldEditor(
				StudioPreferenceConstants.STATEMACHINE_STYLE, Messages.getString("studio.preference.diagram.style"), 4,
				styleLabelAndValues, parent, true,this);
		addField(styleFieldEditor);

		String[][] ruoutingLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.STATEMACHINE_ROUTING_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.polyline"),
						StudioPreferenceConstants.STATEMACHINE_ROUTING_POLYLINE}};
		RoutingGroupFieldEditor routingFieldEditor = new RoutingGroupFieldEditor(
				StudioPreferenceConstants.STATEMACHINE_ROUTING, Messages.getString("studio.preference.diagram.routing"), 4,
				ruoutingLabelAndValues, parent, true,this);
		addField(routingFieldEditor);
	}

	public void init(IWorkbench workbench) {
	}

}
