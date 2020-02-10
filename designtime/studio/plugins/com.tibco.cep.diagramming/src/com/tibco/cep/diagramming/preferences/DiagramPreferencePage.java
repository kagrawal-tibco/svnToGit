package com.tibco.cep.diagramming.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.Messages;


/**
 * 
 * @author sasahoo
 *
 */
public class DiagramPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	private static String[] animationGroupLabels = {
			Messages.getString("designer.diagram.preference.Animation"),
			Messages.getString("designer.diagram.preference.Animation.Options"),
			Messages.getString("designer.diagram.preference.Animation.Duration")};
	private static String[][] singleCheckLabelAndValues = {
			{Messages.getString("designer.diagram.preference.Animation.Allow"), DiagramPreferenceConstants.ANIMATION_LAYOUT_ALLOW }};
	private static String[][] animationCheckLabelAndValues = {
			{Messages.getString("designer.diagram.preference.Animation.Options.Fade"), DiagramPreferenceConstants.ANIMATION_LAYOUT_FADE },
			{Messages.getString("designer.diagram.preference.Animation.Options.Interpolation"), DiagramPreferenceConstants.ANIMATION_LAYOUT_INTERPOLATION },
			{Messages.getString("designer.diagram.preference.Animation.Options.Viewport"), DiagramPreferenceConstants.ANIMATION_LAYOUT_VIEWPORT_CHANGE }};
	private static String[][] animationTextLabelAndValues = {      
			{Messages.getString("designer.diagram.preference.Animation.Duration.Layout"), DiagramPreferenceConstants.LAYOUT_ANIMATION_DURATION },
			{Messages.getString("designer.diagram.preference.Animation.Duration.Viewport"), DiagramPreferenceConstants.VIEWPORT_CHANGE_DURATION },};
    private static String[][] labelAndValues = {
			{Messages.getString("designer.diagram.preference.RunLayoutOnChanges.none"),
				DiagramPreferenceConstants.NONE },
				{ Messages.getString("designer.diagram.preference.RunLayoutOnChanges.incremental"),
					DiagramPreferenceConstants.INCREMENTAL },
					{ Messages.getString("designer.diagram.preference.RunLayoutOnChanges.full"),
						DiagramPreferenceConstants.FULL } };
    private static String[][] edgeLabelAndValues = {
			{ Messages.getString("designer.diagram.preference.diagram.straight"),
				DiagramPreferenceConstants.LINK_TYPE_STRAIGHT},
				{ Messages.getString("designer.diagram.preference.diagram.curved"),
					DiagramPreferenceConstants.LINK_TYPE_CURVED} };

	

	public DiagramPreferencePage(){
	super(GRID);
	setPreferenceStore(DiagrammingPlugin.getDefault().getPreferenceStore());
	}
	
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		addField(new BooleanFieldEditor(DiagramPreferenceConstants.RESET_TOOL_AFTER_CHANGES,	Messages.getString("designer.diagram.preference.ResetToolAfterChange"),parent));
		addField(new BooleanFieldEditor(DiagramPreferenceConstants.AUTO_HIDE_SCROLL_BARS,	Messages.getString("designer.diagram.preference.AutoHideScrollBars"),parent));
		addField(new BooleanFieldEditor(DiagramPreferenceConstants.SHOW_TOOLTIPS,Messages.getString("designer.diagram.preference.ShowTooltips"),parent));
    	addField(new IntegerFieldEditor(DiagramPreferenceConstants.UNDO_LIMIT,	Messages.getString("designer.diagram.preference.UndoLimit"),parent));
		
		RadioGroupFieldEditor linkFieldEditor = new RadioGroupFieldEditor(
				DiagramPreferenceConstants.LINK_TYPE, Messages.getString("designer.diagram.preference.diagram.linktypes"), 4,
				edgeLabelAndValues, parent, true);
		addField(linkFieldEditor);
		RadioGroupFieldEditor runlayoutFieldEditor = new RadioGroupFieldEditor(
				DiagramPreferenceConstants.RUN_LAYOUT_ON_CHANGES, Messages.getString("designer.diagram.preference.RunLayoutOnChanges"), 6,
				labelAndValues, parent, true);
		addField(runlayoutFieldEditor);
		
		//Animation Layout.
        AnimationGroupFieldEditor animationGrouplayoutFieldEditor = new AnimationGroupFieldEditor(this, 
                                 animationGroupLabels, 
                                 singleCheckLabelAndValues,
                                 animationCheckLabelAndValues, 
                                 animationTextLabelAndValues,
                                 parent);
        addField(animationGrouplayoutFieldEditor);

        addField(new BooleanFieldEditor(DiagramPreferenceConstants.OPAQUE_MOVEMENT,Messages.getString("designer.diagram.preference.OpaqueMovement"), parent));
		DoubleFieldEditor iField = new DoubleFieldEditor(DiagramPreferenceConstants.INTERACTIVE_ZOOM_SENSITIVITY,
				Messages.getString("designer.diagram.preference.InteractiveZoomSensitivity"), parent);
		addField(iField);
		DoubleFieldEditor pField = new DoubleFieldEditor(DiagramPreferenceConstants.PAN_SENSITIVITY,
				Messages.getString("designer.diagram.preference.PanSensitivity"), parent);
		addField(pField);
		
		Group nodeGroup = new Group(parent, SWT.NULL);
		// nodeGroup.setLayout(new GridLayout(2, false));
		// nodeGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		nodeGroup.setText("Magnify Tool");
		IntegerFieldEditor magLevelField = new IntegerFieldEditor(DiagramPreferenceConstants.MAGNIFY_SIZE,
				Messages.getString("designer.diagram.preference.MagnifySize"), nodeGroup);
		addField(magLevelField);
		IntegerFieldEditor maxSizeField = new IntegerFieldEditor(DiagramPreferenceConstants.MAGNIFY_ZOOM,
				Messages.getString("designer.diagram.preference.MagnifyZoom"), nodeGroup);
		addField(maxSizeField);
	}
	
	public void init(IWorkbench workbench) {
	}
	/**
	 * Make addition of field available outside as it is protected
	 * @param editor
	 */
	public void addPrferenceFieldeditor(FieldEditor editor) {
		addField(editor);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
	}
}