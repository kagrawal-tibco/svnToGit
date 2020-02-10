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
public class PrintPreferencePage extends AbstractFieldEditorPreferencePage{
	
	public PrintPreferencePage(){
		super();
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		String[][] printLabelAndValues = {
				{Messages.getString("studio.print.preference.diagram.entire.drawing")
					,StudioPreferenceConstants.PRINT_ENTIRE_DRAWING },
					{ Messages.getString("studio.print.preference.diagram.current.window")
						,StudioPreferenceConstants.PRINT_CURRENT_WINDOW },
						{ Messages.getString("studio.print.preference.diagram.current.selection"),
							StudioPreferenceConstants.PRINT_CURRENT_SELECTION } };

		RadioGroupFieldEditor printFieldEditor = new RadioGroupFieldEditor(
				StudioPreferenceConstants.PRINT, "", 6,printLabelAndValues, parent, true);
		addField(printFieldEditor);

		String[][] scaleByLabelAndValues = {
				{Messages.getString("studio.print.preference.diagram.pages")
					,StudioPreferenceConstants.PRINT_PAGES },
					{ Messages.getString("studio.print.preference.diagram.page.actual.size")
						,StudioPreferenceConstants.PRINT_ACTUAL_SIZE },
						{ Messages.getString("studio.print.preference.diagram.page.zoom.level"),
							StudioPreferenceConstants.PRINT_ZOOM_LEVEL } };

		ScaleGroupFieldEditor scaleFieldEditor = new ScaleGroupFieldEditor(
				StudioPreferenceConstants.PRINT,  Messages.getString("studio.print.preference.diagram.scale.by"), 
				1,scaleByLabelAndValues, parent, true,this);
		addField(scaleFieldEditor);
	
		Group multiPageGroup = new Group(parent, SWT.NONE);
		multiPageGroup.setText(Messages.getString("studio.print.preference.diagram.multipage"));
		multiPageGroup.setLayout(new GridLayout(1,false));
		multiPageGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		Composite container = new Composite(multiPageGroup, SWT.NONE);
		container.setLayout(new GridLayout(1,false));
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PRINT_MULTIPAGE_NUMBERS,
				Messages.getString("studio.print.preference.diagram.multipage.nos"),container));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PRINT_MULTIPAGE_CROP_MARKS,
				Messages.getString("studio.print.preference.diagram.multipage.crop.marks"),container));
	}
	public void init(IWorkbench workbench) {
	}
}