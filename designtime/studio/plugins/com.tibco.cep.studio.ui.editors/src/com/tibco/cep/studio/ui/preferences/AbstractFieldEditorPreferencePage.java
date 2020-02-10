package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractFieldEditorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage,IPreferences {
	
	protected BooleanFieldEditor orthoFixNodeSizeField; 
	protected BooleanFieldEditor styleOrthoFixNodeSizeField; 
	protected BooleanFieldEditor styleUnDirectedLayoutField; 
	protected RadioGroupFieldEditor styleOriFieldEditor;
	protected RadioGroupFieldEditor clusterLayoutFieldEditor;
	protected String preference;

	public AbstractFieldEditorPreferencePage(){
		super(GRID);
	}
	
    /**
     * 
     * @param parent
     * @param preference
     */
	public void addRoutingFields(Composite parent,String preference){
		Group nodeGroup = new Group(parent, SWT.NULL);
//		nodeGroup.setText(Messages.getString("studio.preference.diagram.nodes"));
		nodeGroup.setLayout( new GridLayout(2, false));
		nodeGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		if(preference.equalsIgnoreCase(PROJECT)){
			createBooleanFieldEditors(StudioPreferenceConstants.PROJECT_ROUTING_FIX_NODE_SIZES, 
					StudioPreferenceConstants.PROJECT_ROUTING_FIX_NODE_POSITIONS, nodeGroup);
		}
		if(preference.equalsIgnoreCase(CONCEPT)){
			createBooleanFieldEditors(StudioPreferenceConstants.CONCEPT_ROUTING_FIX_NODE_SIZES, 
					StudioPreferenceConstants.CONCEPT_ROUTING_FIX_NODE_POSITIONS, nodeGroup);
		}
		if(preference.equalsIgnoreCase(EVENT)){
			createBooleanFieldEditors(StudioPreferenceConstants.EVENT_ROUTING_FIX_NODE_SIZES, 
					StudioPreferenceConstants.EVENT_ROUTING_FIX_NODE_POSITIONS, nodeGroup);
		}
		if(preference.equalsIgnoreCase(STATEMACHINE)){
			createBooleanFieldEditors(StudioPreferenceConstants.STATEMACHINE_ROUTING_FIX_NODE_SIZES, 
					StudioPreferenceConstants.STATEMACHINE_ROUTING_FIX_NODE_POSITIONS, nodeGroup);
		}
		if(preference.equalsIgnoreCase(DEPENDENCY)){
			createBooleanFieldEditors(StudioPreferenceConstants.DEPENDENCY_ROUTING_FIX_NODE_SIZES, 
					StudioPreferenceConstants.DEPENDENCY_ROUTING_FIX_NODE_POSITIONS, nodeGroup);
		}
	}
	
    /**
     * 
     * @param parent
     * @param preference
     */
	public void addRoutingOrthogonalFields(Composite parent,String preference){
		if(preference.equalsIgnoreCase(PROJECT)){
			createBooleanFieldEditor(StudioPreferenceConstants.PROJECT_ROUTING_ORTHOGONAL_FIX_NODE_SIZES, parent);
		}
		if(preference.equalsIgnoreCase(CONCEPT)){
			createBooleanFieldEditor(StudioPreferenceConstants.CONCEPT_ROUTING_ORTHOGONAL_FIX_NODE_SIZES, parent);
		}
		if(preference.equalsIgnoreCase(EVENT)){
			createBooleanFieldEditor(StudioPreferenceConstants.EVENT_ROUTING_ORTHOGONAL_FIX_NODE_SIZES, parent);
		}
		if(preference.equalsIgnoreCase(STATEMACHINE)){
			createBooleanFieldEditor(StudioPreferenceConstants.STATEMACHINE_ROUTING_ORTHOGONAL_FIX_NODE_SIZES, parent);
		}
		if(preference.equalsIgnoreCase(DEPENDENCY)){
			createBooleanFieldEditor(StudioPreferenceConstants.DEPENDENCY_ROUTING_ORTHOGONAL_FIX_NODE_SIZES, parent);
		}
	}

    /**
     * 
     * @param parent
     * @param preference
     */
	public void addStyleFields(Composite parent,String preference){
		
	//	Group oriGroup = new Group(parent, SWT.NONE);
	//	oriGroup.setText(Messages.getString("studio.preference.diagram.orientation"));
	//	GridLayout layout = new GridLayout(1, false);
	//	oriGroup.setLayout(layout);
	//	oriGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		
		String styleOrthoFixNodeSizeFieldCons = null;
		String topToBottom = null;
		String bottomToTop = null;
		String leftToRight = null;
		String rightToLeft = null;
		String heiOri = null;
		String heiOriUnLayout = null;
		
		if(preference.equalsIgnoreCase(PROJECT)){
			styleOrthoFixNodeSizeFieldCons = StudioPreferenceConstants.PROJECT_ORTHOGONAL_FIX_NODE_SIZES;
			topToBottom = StudioPreferenceConstants.PROJECT_HIERARCHICAL_ORIENTAION_TOP_TO_BOTTOM;
			bottomToTop = StudioPreferenceConstants.PROJECT_HIERARCHICAL_ORIENTAION_BOTTOM_TO_TOP;
			leftToRight = StudioPreferenceConstants.PROJECT_HIERARCHICAL_ORIENTAION_LEFT_TO_RIGHT;
			rightToLeft = StudioPreferenceConstants.PROJECT_HIERARCHICAL_ORIENTAION_RIGHT_TO_LEFT;
			heiOri = StudioPreferenceConstants.PROJECT_HIERARCHICAL_ORIENTAION;
			heiOriUnLayout = StudioPreferenceConstants.PROJECT_HIERARCHICAL_ORIENTAION_UNDIRECTED_LAYOOUT;
		}else if(preference.equalsIgnoreCase(CONCEPT)){
			styleOrthoFixNodeSizeFieldCons = StudioPreferenceConstants.CONCEPT_ORTHOGONAL_FIX_NODE_SIZES;
			topToBottom = StudioPreferenceConstants.CONCEPT_HIERARCHICAL_ORIENTAION_TOP_TO_BOTTOM;
			bottomToTop = StudioPreferenceConstants.CONCEPT_HIERARCHICAL_ORIENTAION_BOTTOM_TO_TOP;
			leftToRight = StudioPreferenceConstants.CONCEPT_HIERARCHICAL_ORIENTAION_LEFT_TO_RIGHT;
			rightToLeft = StudioPreferenceConstants.CONCEPT_HIERARCHICAL_ORIENTAION_RIGHT_TO_LEFT;
			heiOri = StudioPreferenceConstants.CONCEPT_HIERARCHICAL_ORIENTAION;
			heiOriUnLayout = StudioPreferenceConstants.CONCEPT_HIERARCHICAL_ORIENTAION_UNDIRECTED_LAYOOUT;
		}else if(preference.equalsIgnoreCase(EVENT)){
			styleOrthoFixNodeSizeFieldCons = StudioPreferenceConstants.EVENT_ORTHOGONAL_FIX_NODE_SIZES;
			topToBottom = StudioPreferenceConstants.EVENT_HIERARCHICAL_ORIENTAION_TOP_TO_BOTTOM;
			bottomToTop = StudioPreferenceConstants.EVENT_HIERARCHICAL_ORIENTAION_BOTTOM_TO_TOP;
			leftToRight = StudioPreferenceConstants.EVENT_HIERARCHICAL_ORIENTAION_LEFT_TO_RIGHT;
			rightToLeft = StudioPreferenceConstants.EVENT_HIERARCHICAL_ORIENTAION_RIGHT_TO_LEFT;
			heiOri = StudioPreferenceConstants.EVENT_HIERARCHICAL_ORIENTAION;
			heiOriUnLayout = StudioPreferenceConstants.EVENT_HIERARCHICAL_ORIENTAION_UNDIRECTED_LAYOOUT;
		}else if(preference.equalsIgnoreCase(STATEMACHINE)){
			styleOrthoFixNodeSizeFieldCons = StudioPreferenceConstants.STATEMACHINE_ORTHOGONAL_FIX_NODE_SIZES;
			topToBottom = StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL_ORIENTAION_TOP_TO_BOTTOM;
			bottomToTop = StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL_ORIENTAION_BOTTOM_TO_TOP;
			leftToRight = StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL_ORIENTAION_LEFT_TO_RIGHT;
			rightToLeft = StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL_ORIENTAION_RIGHT_TO_LEFT;
			heiOri = StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL_ORIENTAION;
			heiOriUnLayout = StudioPreferenceConstants.STATEMACHINE_HIERARCHICAL_ORIENTAION_UNDIRECTED_LAYOOUT;
		}else if(preference.equalsIgnoreCase(DEPENDENCY)){
			styleOrthoFixNodeSizeFieldCons = StudioPreferenceConstants.DEPENDENCY_ORTHOGONAL_FIX_NODE_SIZES;
			topToBottom = StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL_ORIENTAION_TOP_TO_BOTTOM;
			bottomToTop = StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL_ORIENTAION_BOTTOM_TO_TOP;
			leftToRight = StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL_ORIENTAION_LEFT_TO_RIGHT;
			rightToLeft = StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL_ORIENTAION_RIGHT_TO_LEFT;
			heiOri = StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL_ORIENTAION;
			heiOriUnLayout = StudioPreferenceConstants.DEPENDENCY_HIERARCHICAL_ORIENTAION_UNDIRECTED_LAYOOUT;
		}
		
		styleOrthoFixNodeSizeField = new BooleanFieldEditor(styleOrthoFixNodeSizeFieldCons,
				Messages.getString("studio.preference.diagram.orthogonal.fix.node.sizes"),parent);
		addField(styleOrthoFixNodeSizeField);
		
		String[][] oriLabelAndValues = {
				{ Messages.getString("studio.preference.diagram.her.ori.TopToBottom"),
					topToBottom},
					{ Messages.getString("studio.preference.diagram.her.ori.BottomToTop"),
						bottomToTop},
						{ Messages.getString("studio.preference.diagram.her.ori.LeftToRight"),
							leftToRight},
							{ Messages.getString("studio.preference.diagram.her.ori.RighttToLeft"),
								rightToLeft}};
		styleOriFieldEditor = new RadioGroupFieldEditor(
				heiOri, ""/*Messages.getString("studio.preference.diagram.orientation")*/, 8,
				oriLabelAndValues, parent, true);
		addField(styleOriFieldEditor);
		styleOriFieldEditor.setEnabled(false, parent);
		
		styleUnDirectedLayoutField = new BooleanFieldEditor(heiOriUnLayout,
				Messages.getString("studio.preference.diagram.her.ori.undirected.layout"),parent);
		addField(styleUnDirectedLayoutField);
		styleUnDirectedLayoutField.setEnabled(false, parent);

		if(preference.equalsIgnoreCase(PROJECT)){
			String[][] clusterLayoutLabelAndValues = {
					{ Messages.getString("studio.preference.diagram.circular"),
						StudioPreferenceConstants.PROJECT_CIRCULAR},
						{ Messages.getString("studio.preference.diagram.circular.symmetric"),
							StudioPreferenceConstants.PROJECT_SYMMETRIC}};
			clusterLayoutFieldEditor = new RadioGroupFieldEditor(
					StudioPreferenceConstants.PROJECT_CLUSTER_LAYOUT, Messages.getString("studio.preference.diagram.cluster.layout.style"), 4,
					clusterLayoutLabelAndValues, parent, true);
			addField(clusterLayoutFieldEditor);
			clusterLayoutFieldEditor.setEnabled(false, parent);
		}
	}
	
    /**
     * 
     * @param nodeSizePref
     * @param nodePositionPref
     * @param parent
     */
	public void createBooleanFieldEditors(String nodeSizePref,String nodePositionPref,Composite parent){
		addField(new BooleanFieldEditor(nodeSizePref,Messages.getString("studio.preference.diagram.fix.node.sizes"),parent));
		addField(new BooleanFieldEditor(nodePositionPref,Messages.getString("studio.preference.diagram.fix.node.positions"),parent));
	}
	
	/**
	 * 
	 * @param parent
	 */
	public void createScaleBooleanFieldEditors(Composite parent){
		Composite composite = new Composite(parent,SWT.NONE);
		composite.setLayout(new GridLayout(1,false));  
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		IntegerFieldEditor pColField = new IntegerFieldEditor(StudioPreferenceConstants.PRINT_PAGE_COLUMNS,
				Messages.getString("studio.print.preference.diagram.page.cols"), composite);
		addField(pColField);
		IntegerFieldEditor pRowField = new IntegerFieldEditor(StudioPreferenceConstants.PRINT_PAGE_ROWS,
				Messages.getString("studio.print.preference.diagram.page.rows"), composite);
		addField(pRowField);
	}
	
	/**
	 * 
	 * @param nodeSizePref
	 * @param parent
	 */
	public void createBooleanFieldEditor(String nodeSizePref,Composite parent){
		orthoFixNodeSizeField = new BooleanFieldEditor(nodeSizePref,Messages.getString("studio.preference.diagram.orthogonal.fix.node.sizes"),parent);
		addField(orthoFixNodeSizeField);
	}

	public BooleanFieldEditor getStyleUnDirectedLayoutField() {
		return styleUnDirectedLayoutField;
	}

	public RadioGroupFieldEditor getStyleOriFieldEditor() {
		return styleOriFieldEditor;
	}

	public RadioGroupFieldEditor getClusterLayoutFieldEditor() {
		return clusterLayoutFieldEditor;
	}

	public BooleanFieldEditor getStyleOrthoFixNodeSizeField() {
		return styleOrthoFixNodeSizeField;
	}
	public BooleanFieldEditor getOrthoFixNodeSizeField() {
		return orthoFixNodeSizeField;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}
}
