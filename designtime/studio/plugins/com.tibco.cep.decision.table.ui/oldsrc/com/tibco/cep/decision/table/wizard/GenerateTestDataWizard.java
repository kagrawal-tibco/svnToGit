package com.tibco.cep.decision.table.wizard;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;

import org.eclipse.jface.wizard.Wizard;

import com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerView;
//import com.tibco.cep.decision.table.constraintpane.GenerateTestDataAction;

/**
 * 
 * @author smarathe
 *
 */
public class GenerateTestDataWizard extends Wizard {

	GenerateTestDataWizardPage generateTestDataPage;
	Map<String, Component> components;
	Map<String, Component> selectedComponents;
	DecisionTableAnalyzerView analyzerView;
	private Map<String, Integer> intervalMap;
	
	public GenerateTestDataWizard(Map<String, Component> components, DecisionTableAnalyzerView analyzerView) {
		this.components = components;
		this.analyzerView = analyzerView;
	}
	
	public void init() {
		setWindowTitle("Generate Test Data Wizard");
		generateTestDataPage = new GenerateTestDataWizardPage("Generate Test Data", components);
		addPage(generateTestDataPage);
	}
	@Override
	public boolean performFinish() {
		
		selectedComponents = new HashMap<String, Component>();
		intervalMap = new HashMap<String, Integer>();
		int index = 0;
		for(JCheckBox checkBox : generateTestDataPage.getCheckBoxArray()) {
			if(checkBox.isSelected()) {
				selectedComponents.put(checkBox.getName(), generateTestDataPage.getComponentArray()[index]);
				intervalMap.put(checkBox.getName(), new Integer(generateTestDataPage.getTextFieldArray()[index].getText()));
			}
			index++;
		}
	//	GenerateTestDataAction generateAction = analyzerView.getGenTestDataAction();
		
		
		//generateAction.setFComponents(selectedComponents);
	//	try {
	//		generateAction.generateTestData();
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}
		return true;
	}

	public Map<String, Component> getSelectedComponents() {
		return selectedComponents;
	}

	public void setSelectedComponents(Map<String, Component> selectedComponents) {
		this.selectedComponents = selectedComponents;
	}
	
}
