package com.tibco.cep.diagramming.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.tibco.cep.diagramming.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class AnimationGroupFieldEditor extends FieldEditor {

	 /**
     * List of Check Field Editor entries of the form [label{Display},value{Preference}].
     */
    private String[][] checkLabelAndValues;
    
    /**
     * Boolean Field Editor entries of the form [label{Display},value{Preference}] for ALLOW.
     */
    private String[][] singleCheckValue;
    
    /**
     * List of Text Field Editor entries of the form [label{Display},value{Preference}] for Text.
     */
    private String[][] textLabelAndValues;

    /**
     * List of Group Labels.
     */
    private String[] groupLabels;

    private Composite composite;

    private Group durationGroup;
    
    /**
     * Check Button Group Field Editor
     */
    private  CheckButtonGroupFieldEditor checkFieldEditor;
    
    /**
     * Diagram Preference Page
     */
    private DiagramPreferencePage diagramPreferencePage;
    
    /**
     * Animation Layout duration Integer field Editor
     */
    private IntegerFieldEditor layoutAnimationDurationField;
    /**
     * Viewport Change duration Integer field Editor
     */
    private IntegerFieldEditor viewportChangeDurationField;
    
    /**
     * Default preference for animation to allow 
     */
    private String allowAnimationPreference;

    /**
     * AnimationGroupFieldEditor to set all properties
     * @param diagramPreferencePage
     * @param groupLabels
     * @param singleCheckValue
     * @param checkLabelAndValues
     * @param textLabelAndValues
     * @param parent
     * @param allowAnimationPreference
     */
    public AnimationGroupFieldEditor(DiagramPreferencePage diagramPreferencePage,
    		                         String[] groupLabels, 
    		                         String[][] singleCheckValue,
    		                         String[][] checkLabelAndValues,
    		                         String[][] textLabelAndValues,  
    		                         Composite parent) {
        init( groupLabels[0], groupLabels[0]);
        this.singleCheckValue = singleCheckValue;
        this.checkLabelAndValues = checkLabelAndValues;
        this.textLabelAndValues = textLabelAndValues;
        this.groupLabels = groupLabels;
        this.diagramPreferencePage = diagramPreferencePage;
        this.allowAnimationPreference = singleCheckValue[0][1];
        createControl(parent);
    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void adjustForNumColumns(int numColumns) {
        Control control = getLabelControl();
        if (control != null) {
            ((GridData) control.getLayoutData()).horizontalSpan = numColumns;
        }
        ((GridData) composite.getLayoutData()).horizontalSpan = numColumns;
    }


    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doFillIntoGrid(Composite parent, int numColumns) {
    	Control control = getControl(parent);
    	GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    	control.setLayoutData(gd);
    }
    
    public Composite getControl(Composite parent) {
        if (composite == null) {
            Font font = parent.getFont();
            Group group = new Group(parent, SWT.NONE);
            group.setFont(font);
            String text = getLabelText();
            if (text != null) {
            	group.setText(text);
            }
            
            composite = group;
            GridLayout layout = new GridLayout();
            layout.horizontalSpacing = HORIZONTAL_GAP;

            composite.setLayout(layout);
            composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            BooleanFieldEditor allowFieldEditor =  new BooleanFieldEditor(singleCheckValue[0][1], singleCheckValue[0][0], composite){
            	 /* (non-Javadoc)
            	 * @see org.eclipse.jface.preference.BooleanFieldEditor#valueChanged(boolean, boolean)
            	 */
            	protected void valueChanged(boolean oldValue, boolean newValue) {
            	        setPresentsDefaultValue(false);
            	        if (oldValue != newValue) {
            				fireStateChanged(VALUE, oldValue, newValue);
             	        	checkFieldEditor.setEnabled(newValue, composite);
            	        	durationGroup.setEnabled(newValue);
            	        	layoutAnimationDurationField.setEnabled(newValue, durationGroup);
            	        	viewportChangeDurationField.setEnabled(newValue, durationGroup);
            			}
            	    }
            };
            
            diagramPreferencePage.addPrferenceFieldeditor( allowFieldEditor);
           
            checkFieldEditor = new CheckButtonGroupFieldEditor( groupLabels[1], groupLabels[1], 6,
            														    				  checkLabelAndValues, composite, true) {
            	@Override
            	public void widgetSelected(SelectionEvent event) {
            		super.widgetSelected(event);
            		Button button = (Button)event.widget;
            		String text = button.getText();
            		if (text.equals(Messages.getString("designer.diagram.preference.Animation.Options.Viewport"))) {
            			boolean val = button.getSelection();
            			if (!val) {
            				viewportChangeDurationField.setStringValue("0");
            			}
            			viewportChangeDurationField.setEnabled(val, durationGroup);
            		}
            	}
            };
            
            diagramPreferencePage.addPrferenceFieldeditor(checkFieldEditor);
            durationGroup = new Group(composite, SWT.NONE);
            durationGroup.setFont(font);
            durationGroup.setText(groupLabels[2]);
            
            layout = new GridLayout();
            layout.horizontalSpacing = HORIZONTAL_GAP;
            durationGroup.setLayout(layout);
            durationGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            
    		layoutAnimationDurationField = new IntegerFieldEditor(textLabelAndValues[0][1],
    				textLabelAndValues[0][0], durationGroup);
    		diagramPreferencePage.addPrferenceFieldeditor(layoutAnimationDurationField);
    		viewportChangeDurationField = new IntegerFieldEditor(textLabelAndValues[1][1],
    				textLabelAndValues[1][0], durationGroup);
    		diagramPreferencePage.addPrferenceFieldeditor(viewportChangeDurationField);
            
            composite.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent event) {
                    composite = null;
                    durationGroup = null;
                }
            });
        } else {
            checkParent(composite, parent);
        }
        return composite;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	@Override
	protected void doLoad() {
		load( getPreferenceStore().getBoolean(allowAnimationPreference));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	@Override
	protected void doLoadDefault() {
		load(getPreferenceStore().getDefaultBoolean(allowAnimationPreference));
	}
	
	/**
	 * @param allow
	 */
	private void load(boolean allow) {
		checkFieldEditor.setEnabled(allow, composite);
    	durationGroup.setEnabled(allow);
    	layoutAnimationDurationField.setEnabled(allow, durationGroup);
    	viewportChangeDurationField.setEnabled(allow, durationGroup);
    	if (allow) {
    		boolean isViewportChange = diagramPreferencePage.getPreferenceStore().getBoolean(DiagramPreferenceConstants.ANIMATION_LAYOUT_VIEWPORT_CHANGE);
    		if (!isViewportChange) {
    			viewportChangeDurationField.setEnabled(false, durationGroup);
    		}
    	}
	}

	@Override
	protected void doStore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfControls() {
		return 1;
	}
}