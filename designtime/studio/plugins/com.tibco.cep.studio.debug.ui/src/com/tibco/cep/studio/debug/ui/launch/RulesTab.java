package com.tibco.cep.studio.debug.ui.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.impl.RuleElementImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

/*
@author ssailapp
@date Jun 18, 2009 4:35:51 PM
 */
@SuppressWarnings({"unchecked"})
public class RulesTab extends AbstractLaunchConfigurationTab implements ILaunchConfigurationTab {

	private final static String TAB_ID = "com.tibco.cep.studio.debug.ui.launch.rulestab";
	
	private final static String ICON_RULES_TAB = "icons/rulestab.gif";
	private final static String ICON_RULES = "icons/rules.png";
	private final static String ICON_RULES_FOLDER = "icons/rules_folder.png";
		
	private Composite parent;
	private Tree trRules;
	private WidgetListener wListener;
	
	public RulesTab() {
		wListener = new WidgetListener();
	}
	
	@Override
	public boolean canSave() {
		return validateLocal();
	}

	@Override
	public void createControl(Composite parent) {
		this.parent = parent;
		
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, false);
		comp.setLayout(gl);

		Label lRules = new Label(comp, SWT.NONE);
		lRules.setText("Rules to Deploy:");
		lRules.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		trRules = new Tree (comp, SWT.BORDER | SWT.CHECK | SWT.MULTI);
		trRules.addListener(SWT.Selection, wListener);
		trRules.setLayoutData(new GridData(GridData.FILL_BOTH));

		setControl(comp);
	}

	@Override
	public Image getImage() {
		return (StudioDebugUIPlugin.getImageDescriptor(ICON_RULES_TAB)).createImage();
	}

	@Override
	public String getMessage() {
		return ("Select the Rules to deploy");
	}

	@Override
	public String getName() {
		return ("Rules");
	}
	
	public String getId() {
		return (TAB_ID);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String projName = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if (projName == null || projName.trim().equals(""))
				return;
				
			fillRulesTree(projName, trRules);
			
			List<String> rulesList = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_RULES, new ArrayList<String>());
			for (String r: rulesList) {
				for (TreeItem ruleFolder: trRules.getItems()) {
					for (TreeItem rule: ruleFolder.getItems()) {
						if (rule.getText(0).equalsIgnoreCase(r)) {
							rule.setChecked(true);
							checkPathForGrayItems(rule.getParentItem(), true, false);
						}
					}
				}
			}
		} catch (CoreException e) {
		}
	}

	private void fillRulesTree(String projName, Tree tree) {
		trRules.removeAll();
		List<DesignerElement> rules = IndexUtils.getAllElements(projName, ELEMENT_TYPES.RULE);
		
		for (DesignerElement r: rules) {
			RuleElement re = (RuleElementImpl)r;
			String rulePath = re.getFolder() + re.getName();
			TreeItem ruleFolder = getRuleFolder(tree, re.getFolder());
			TreeItem rule = new TreeItem (ruleFolder, SWT.NONE);
			rule.setImage(StudioDebugUIPlugin.getImageDescriptor(ICON_RULES).createImage());
			rule.setText(rulePath);
		}
	}
	
	private TreeItem getRuleFolder(Tree tree, String ruleFolderName) {
		for (TreeItem ruleFolder: tree.getItems()) {
			if (ruleFolder.getText(0).equalsIgnoreCase(ruleFolderName)) {
				return ruleFolder;
			}
		}
		TreeItem newRuleFolder = new TreeItem(tree, SWT.NONE);
		newRuleFolder.setText(ruleFolderName);
		newRuleFolder.setImage(StudioDebugUIPlugin.getImageDescriptor(ICON_RULES_FOLDER).createImage());
		return newRuleFolder;
	}
	
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return validateLocal();
	}

	private boolean validateLocal() {
		if (getSelectedRules().size() == 0) {
			setErrorMessage("Atleast one rule must be selected.");
			return false;
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		List<String> rulesList = getSelectedRules();
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_RULES, rulesList);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}
	
	private class WidgetListener implements ModifyListener, SelectionListener, Listener {
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}

		@Override
		public void handleEvent(Event event) {
            TreeItem item = (TreeItem) event.item;
            boolean checked = item.getChecked();
            checkMarkChildren(item, checked);
            checkPathForGrayItems(item.getParentItem(), checked, false);
			updateLaunchConfigurationDialog();
		}
	}

	private void checkMarkChildren(TreeItem item, boolean checked) {
	    item.setGrayed(false);
	    item.setChecked(checked);
	    TreeItem[] children = item.getItems();
	    for (int i=0; i<children.length; i++) {
	        checkMarkChildren(children[i], checked);
	    }
	}
	
	private void checkPathForGrayItems(TreeItem item, boolean checked, boolean grayed) {
	    if (item == null) return;
	    if (grayed) {
	        checked = true;
	    } else {
	        int index = 0;
	        TreeItem[] children = item.getItems();
	        while (index < children.length) {
	            TreeItem child = children[index];
	            if (child.getGrayed() || checked != child.getChecked()) {
	                checked = grayed = true;
	                break;
	            }
	            index++;
	        }
	    }
	    item.setChecked(checked);
	    item.setGrayed(grayed);
	    checkPathForGrayItems(item.getParentItem(), checked, grayed);
	}

	private List<String> getSelectedRules() {
		ArrayList<String> selectedRules = new ArrayList<String>();
		for (TreeItem ruleFolder: trRules.getItems()) {
			for (TreeItem rule: ruleFolder.getItems()) {
				if (rule.getChecked())
					selectedRules.add(rule.getText(0));
			}
		}
		return selectedRules;
	}
}
