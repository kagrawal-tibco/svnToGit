package com.tibco.cep.studio.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class StyleGroupFieldEditor extends AbstractGroupFieldEditor{
	 
	/**
	 * @param name
	 * @param labelText
	 * @param numColumns
	 * @param labelAndValues
	 * @param parent
	 * @param useGroup
	 * @param preferencePage
	 */
	public StyleGroupFieldEditor(String name, String labelText, int numColumns,
			String[][] labelAndValues, Composite parent, boolean useGroup,AbstractFieldEditorPreferencePage preferencePage) {
		super(name, labelText, numColumns, labelAndValues, parent, useGroup, preferencePage);
	}  
 
	/**
     * Returns this field editor's radio group control.
     * @param parent The parent to create the radioBox in
     * @return the radio group control
     */
    public Composite getRadioBoxControl(Composite parent) {
        if (radioBox == null) {
            Font font = parent.getFont();
            initGroupField(parent);
            buttonsGroup = new Group(radioBox, SWT.NONE);
            buttonsGroup.setLayout( new GridLayout(8, false));
            buttonsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

            
            
            radioButtons = new Button[labelsAndValues.length];
            for (int i = 0; i < labelsAndValues.length; i++) {
                Button radio = new Button(buttonsGroup, SWT.RADIO | SWT.LEFT);
                radioButtons[i] = radio;
                String[] labelAndValue = labelsAndValues[i];
                radio.setText(labelAndValue[0]);
                radio.setData(labelAndValue[1]);
                radio.setFont(font);
                radio.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                    	Button button = (Button)event.getSource();
                    	if(button.getText().equalsIgnoreCase(Messages.getString("studio.preference.diagram.orthogonal"))
                    			&& button.getSelection()==true){
                    		preferencePage.getStyleOrthoFixNodeSizeField().setEnabled(true, radioBox);
                    		if(preferencePage.getPreference().equalsIgnoreCase(PROJECT)){
                    			preferencePage.getClusterLayoutFieldEditor().setEnabled(false, radioBox);
                    		}
                    		preferencePage.getStyleUnDirectedLayoutField().setEnabled(false, radioBox);
                    		preferencePage.getStyleOriFieldEditor().setEnabled(false, radioBox);
                    	}
                    	else if(button.getText().equalsIgnoreCase("Hierarchical") && button.getSelection()==true){
                    		preferencePage.getStyleOrthoFixNodeSizeField().setEnabled(false, radioBox);
                    		if(preferencePage.getPreference().equalsIgnoreCase(PROJECT)){
                    			preferencePage.getClusterLayoutFieldEditor().setEnabled(false, radioBox);
                    		}
                    		preferencePage.getStyleUnDirectedLayoutField().setEnabled(true, radioBox);
                    		preferencePage.getStyleOriFieldEditor().setEnabled(true, radioBox);
                    	}
                    	else if(button.getText().equalsIgnoreCase(Messages.getString("studio.preference.diagram.circular"))
                    			&& button.getSelection()==true){
                    		preferencePage.getStyleOrthoFixNodeSizeField().setEnabled(false, radioBox);
                    		if(preferencePage.getPreference().equalsIgnoreCase(PROJECT)){
                    			preferencePage.getClusterLayoutFieldEditor().setEnabled(true, radioBox);
                    		}
                    		preferencePage.getStyleUnDirectedLayoutField().setEnabled(false, radioBox);
                    		preferencePage.getStyleOriFieldEditor().setEnabled(false, radioBox);
                    	}
                    	else if(button.getText().equalsIgnoreCase(Messages.getString("studio.preference.diagram.circular.symmetric"))
                    			&& button.getSelection()==true){
                    		preferencePage.getStyleOrthoFixNodeSizeField().setEnabled(false, radioBox);
                    		if(preferencePage.getPreference().equalsIgnoreCase(PROJECT)){
                    			preferencePage.getClusterLayoutFieldEditor().setEnabled(false, radioBox);
                    		}
                    		preferencePage.getStyleUnDirectedLayoutField().setEnabled(false, radioBox);
                    		preferencePage.getStyleOriFieldEditor().setEnabled(false, radioBox);
                    	}
                    	String oldValue = value;
                    	value = (String) event.widget.getData();
                    	setPresentsDefaultValue(false);
                    	fireValueChanged(VALUE, oldValue, value);
                     }
                });
            }
            radioBox.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent event) {
                	buttonsGroup=null;
                    radioBox = null;
                    radioButtons = null;
                }
            });
            preferencePage.addStyleFields(radioBox,preferencePage.getPreference());
        } else {
            checkParent(radioBox, parent);
        }
        return radioBox;
    }
}
