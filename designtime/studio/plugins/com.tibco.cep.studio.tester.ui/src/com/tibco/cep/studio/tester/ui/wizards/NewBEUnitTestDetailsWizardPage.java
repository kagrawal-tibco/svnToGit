package com.tibco.cep.studio.tester.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class NewBEUnitTestDetailsWizardPage extends WizardPage {

	protected boolean testRuleExecution;
	protected boolean testRuleOrderExecution;
	protected boolean testEventFired;
	protected boolean testConceptModification;
	protected boolean testWorkingMemory;
	protected boolean selectDeselectAll;

	protected NewBEUnitTestDetailsWizardPage(String pageName) {
		super(pageName);
		setTitle("BusinessEvents Unit Test Suite Details");
		setMessage("Select from the following common options what you would like to test. You may add other tests after this wizard completes.");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayout(layout);
		composite.setLayoutData(data);
		
		Label l = new Label(composite, SWT.NULL);
		l.setText("What would you like to test?");
		
		final Button ruleExecutionButton = new Button(composite, SWT.CHECK);
		ruleExecutionButton.setText("Test whether a particular Rule fired");
		
		final Button ruleExecutionOrderButton = new Button(composite, SWT.CHECK);
		ruleExecutionOrderButton.setText("Test the order of Rule Execution");
		
		final Button eventFiredButton = new Button(composite, SWT.CHECK);
		eventFiredButton.setText("Test whether an Event fired");
		
		final Button conceptModifiedButton = new Button(composite, SWT.CHECK);
		conceptModifiedButton.setText("Test whether a Concept was modified");
		
		final Button workingMemoryButton = new Button(composite, SWT.CHECK);
		workingMemoryButton.setText("Test whether a Concept or Event or Scorecard is in working memory");
		
		
		Composite selectComposite = new Composite(composite, SWT.NULL);
		GridLayout layout1 = new GridLayout();
		GridData data1 = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		selectComposite.setLayout(layout1);
		selectComposite.setLayoutData(data1);
		
		final Button selectDeselectButton = new Button(selectComposite, SWT.CHECK);
		selectDeselectButton.setText("Select/Deselect All");
				
		
		SelectionListener listener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// just reset all flags for simplicity
				testRuleExecution = ruleExecutionButton.getSelection();
				testRuleOrderExecution = ruleExecutionOrderButton.getSelection();
				testEventFired = eventFiredButton.getSelection();
				testConceptModification = conceptModifiedButton.getSelection();
				testWorkingMemory = workingMemoryButton.getSelection();
				if(e.getSource().equals(selectDeselectButton)){
					selectDeselectAll=selectDeselectButton.getSelection();
					if(selectDeselectAll == true){
						ruleExecutionButton.setSelection(true);
						testRuleExecution=true;
						ruleExecutionOrderButton.setSelection(true);
						testRuleOrderExecution=true;
						eventFiredButton.setSelection(true);
						testEventFired=true;
						conceptModifiedButton.setSelection(true);
						testConceptModification=true;
						workingMemoryButton.setSelection(true);
						testWorkingMemory=true;
					}else{
						ruleExecutionButton.setSelection(false);
						testRuleExecution=false;
						ruleExecutionOrderButton.setSelection(false);
						testRuleOrderExecution=false;
						eventFiredButton.setSelection(false);
						testEventFired=false;
						conceptModifiedButton.setSelection(false);
						testConceptModification=false;
						workingMemoryButton.setSelection(false);
						testWorkingMemory=false;
					}
				}
				
				if(testRuleExecution && testRuleOrderExecution && testEventFired && testConceptModification && testWorkingMemory){
					selectDeselectAll = true;
					selectDeselectButton.setSelection(true);
				}else if((testRuleExecution && testRuleOrderExecution && testEventFired && testConceptModification && testWorkingMemory) == false){
					selectDeselectAll = false;
					selectDeselectButton.setSelection(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
		ruleExecutionButton.addSelectionListener(listener);
		eventFiredButton.addSelectionListener(listener);
		conceptModifiedButton.addSelectionListener(listener);
		selectDeselectButton.addSelectionListener(listener);
		workingMemoryButton.addSelectionListener(listener);
		ruleExecutionOrderButton.addSelectionListener(listener);
		setControl(composite);
	}

	public boolean shouldTestConceptModification() {
		return testConceptModification;
	}
	
	public boolean shouldTestEventFired() {
		return testEventFired;
	}
	
	public boolean shouldTestRuleExecution() {
		return testRuleExecution;
	}
	
	public boolean shouldTestWorkingMemory() {
		return testWorkingMemory;
	}
	
	public boolean shouldTestRuleOrderExecution() {
		return testRuleOrderExecution;
	}
}
