package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ProjectDiagramPreferencePage extends AbstractFieldEditorPreferencePage{
	
	private Group optionsGroup;
	private Composite filterGroup1;
	private Composite filterGroup2;
	private Composite filterGroup3;
	private Composite filterGroup4;

	public ProjectDiagramPreferencePage(){
		super();
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}
	
	@Override
	protected void createFieldEditors() {
		setPreference(PROJECT);
		Composite parent =getFieldEditorParent();
	/*	String[][] edgeLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.straight"),
					StudioPreferenceConstants.PROJECT_LINK_TYPE_STRAIGHT},
					{ Messages.getString("studio.preference.diagram.curved"),
						StudioPreferenceConstants.PROJECT_LINK_TYPE_CURVED} };

		RadioGroupFieldEditor linkFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.PROJECT_LINK_TYPE, Messages.getString("studio.preference.diagram.linktypes"), 4,
				edgeLabelAndValues, parent, true);
		addField(linkFieldEditor);
*/
		String[][] gridLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.none"),
					StudioPreferenceConstants.PROJECT_NONE},
					{ Messages.getString("studio.preference.diagram.lines"),
						StudioPreferenceConstants.PROJECT_LINES},
						{ Messages.getString("studio.preference.diagram.points"),
							StudioPreferenceConstants.PROJECT_POINTS}};
		RadioGroupFieldEditor gridFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.PROJECT_GRID, Messages.getString("studio.preference.diagram.grid"), 6,
				gridLabelAndValues, parent, true);
		addField(gridFieldEditor);

		String[][] dependencyLevels = {
				{ Messages.getString("studio.dependency.preference.diagram.one"),
					StudioPreferenceConstants.PROJECT_ONE},
					{ Messages.getString("studio.dependency.preference.diagram.two"),
						StudioPreferenceConstants.PROJECT_TWO},
						{ Messages.getString("studio.dependency.preference.diagram.all"),
							StudioPreferenceConstants.PROJECT_ALL}};
		RadioGroupFieldEditor dependencyFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.PROJECT_LEVELS, Messages.getString("studio.dependency.preference.diagram.levels"), 6,
				dependencyLevels, parent, true);
		addField(dependencyFieldEditor);
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SNAP_TO_GRID,
				Messages.getString("studio.preference.diagram.snaptogrid"),parent));

		addField(new BooleanFieldEditor(StudioPreferenceConstants.CREATE_VIEW,
				Messages.getString("studio.preference.diagram.create.view"),parent));
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.RUN_ANALYSIS,
				Messages.getString("studio.preference.diagram.run.analysis"),parent));
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.FAST_LAYOUT,	
				Messages.getString("studio.preference.diagram.fast.Layout"),parent));
		
		optionsGroup = new Group(parent, SWT.NONE);
		optionsGroup.setText(Messages.getString("studio.project.preference.diagram.filters"));
		optionsGroup.setLayout( new GridLayout(4,false));
		optionsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		filterGroup1 = new Composite(optionsGroup, SWT.NONE);
		filterGroup1.setLayout( new GridLayout(1,false));
		filterGroup1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		filterGroup2 = new Composite(optionsGroup, SWT.NONE);
		filterGroup2.setLayout( new GridLayout(1,false));
		filterGroup2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		filterGroup3 = new Composite(optionsGroup, SWT.NONE);
		filterGroup3.setLayout( new GridLayout(1,false));
		filterGroup3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		filterGroup4 = new Composite(optionsGroup, SWT.NONE);
		filterGroup4.setLayout( new GridLayout(1,false));
		filterGroup4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_CONCEPTS,
				Messages.getString("studio.project.preference.diagram.show.concepts"),filterGroup1));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_METRICS,
				Messages.getString("studio.project.preference.diagram.show.metrics"),filterGroup1));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_EVENTS,
				Messages.getString("studio.project.preference.diagram.show.events"),filterGroup1));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_DECISIONTABLES,
				Messages.getString("studio.project.preference.diagram.show.decision.tables"),filterGroup1));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_DOMAINMODEL,
				Messages.getString("studio.project.preference.diagram.show.domain.model"),filterGroup1));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_STATEMACHIES,
				Messages.getString("studio.project.preference.diagram.show.statemachines"),filterGroup1));
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVES,
				Messages.getString("studio.project.preference.diagram.show.aerchives"),filterGroup2));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_RULES,
				Messages.getString("studio.project.preference.diagram.show.rules"),filterGroup2));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_RULES_FUNCTIONS,
				Messages.getString("studio.project.preference.diagram.show.rule.functions"),filterGroup2));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_SCORECARDS,
				Messages.getString("studio.project.preference.diagram.show.scorecard"),filterGroup2));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_CHANNELS,
				Messages.getString("studio.project.preference.diagram.show.channels"),filterGroup2));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_PROCESSES,
				Messages.getString("studio.project.preference.diagram.show.processes"),filterGroup2));
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_SCOPE_LINKS,
				Messages.getString("studio.project.preference.diagram.show.scope.links"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_USAGE_LINKS,
				Messages.getString("studio.project.preference.diagram.show.usage.links"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_PROCESS_LINKS,
				Messages.getString("studio.project.preference.diagram.show.process.links"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_PROCESS_EXPANDED,
				Messages.getString("studio.project.preference.diagram.show.process.expanded"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVED_DESTINATIONS,
				Messages.getString("studio.project.preference.diagram.show.archived.destinations"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVED_RULES,
				Messages.getString("studio.project.preference.diagram.show.archived.rules"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_ARCHIVED_RULES_ALL,
				Messages.getString("studio.project.preference.diagram.show.archived.rules.all"),filterGroup3));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_RULES_FOLDERS,
				Messages.getString("studio.project.preference.diagram.show.rules.in.folders"),filterGroup4));
		
//		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_SHOW_TOOLTIPS,
//				Messages.getString("studio.project.preference.diagram.show.tooltips"),filterGroup4));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_GROUP_CONCEPTS,
				Messages.getString("studio.project.preference.diagram.group.concepts"),filterGroup4));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_GROUP_EVENTS,
				Messages.getString("studio.project.preference.diagram.group.events"),filterGroup4));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_GROUP_RULES,
				Messages.getString("studio.project.preference.diagram.group.rules"),filterGroup4));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PROJECT_GROUP_RULE_FUNCTIONS,
				Messages.getString("studio.project.preference.diagram.group.rule.functions"),filterGroup4));
		

		String[][] qualityLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.draft"),
					StudioPreferenceConstants.PROJECT_DRAFT},
					{ Messages.getString("studio.preference.diagram.medium"),
						StudioPreferenceConstants.PROJECT_MEDIUM},
						{ Messages.getString("studio.preference.diagram.proof"),
							StudioPreferenceConstants.PROJECT_PROOF}};
		RadioGroupFieldEditor qualityFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.PROJECT_QAULITY, Messages.getString("studio.preference.diagram.quality"), 6,
				qualityLabelAndValues, parent, true);
		addField(qualityFieldEditor);

		String[][] styleLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.PROJECT_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.hierarchical"),
						StudioPreferenceConstants.PROJECT_HIERARCHICAL},
						{ Messages.getString("studio.preference.diagram.circular"),
							StudioPreferenceConstants.PROJECT_CIRCULAR},
							{ Messages.getString("studio.preference.diagram.circular.symmetric"),
								StudioPreferenceConstants.PROJECT_SYMMETRIC}};
		StyleGroupFieldEditor styleFieldEditor = new StyleGroupFieldEditor(
				StudioPreferenceConstants.PROJECT_STYLE, Messages.getString("studio.preference.diagram.style"), 1,
				styleLabelAndValues, parent, true,this);
		addField(styleFieldEditor);
	
		String[][] ruoutingLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.orthogonal"),
					StudioPreferenceConstants.PROJECT_ROUTING_ORTHOGONAL},
					{ Messages.getString("studio.preference.diagram.polyline"),
						StudioPreferenceConstants.PROJECT_ROUTING_POLYLINE}};
		RoutingGroupFieldEditor routingFieldEditor = new RoutingGroupFieldEditor(
				StudioPreferenceConstants.PROJECT_ROUTING, Messages.getString("studio.preference.diagram.routing"),1,
				ruoutingLabelAndValues, parent, true,this);
		addField(routingFieldEditor);
	}
	
	public void init(IWorkbench workbench) {
		
	}
}