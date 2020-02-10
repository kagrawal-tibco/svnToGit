package com.tibco.cep.decision.table.wizard;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.jidesoft.combobox.CheckBoxListComboBox;
import com.jidesoft.swing.RangeSlider;
import com.tibco.cep.decision.table.ui.utils.Messages;

/**
 * 
 * @author smarathe
 *
 */
public class GenerateTestDataWizardPage extends WizardPage implements ActionListener, DocumentListener{

	private Map<String, Component> components;
	private Map<String, Component> selectedComponents;
	private Component[] componentArray;
	private JCheckBox[] checkBoxArray;
	private JTextField[] textFieldArray;
	private JLabel propertyLabel, valueLabel, intervalLabel;
	protected GenerateTestDataWizardPage(String pageName, Map<String, Component> components) {
		super(pageName);
		setTitle(pageName);
		this.components = components;
		componentArray = new Component[components.keySet().size()];
		checkBoxArray = new JCheckBox[components.keySet().size()];
		textFieldArray = new JTextField[components.keySet().size()];
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		Frame frame = SWT_AWT.new_Frame(composite);
		Panel jContentPane = null;
		jContentPane = new Panel(new GridBagLayout());
		jContentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jContentPane.setBackground(new Color(236, 233, 216));
		GridBagConstraints constraints;
		Iterator<String> iterator = components.keySet().iterator();
		int index = 0;
		propertyLabel = new JLabel();
		propertyLabel.setText(Messages.getString("Generate_Test_Data_Property_Name"));
		valueLabel = new JLabel(Messages.getString("Generate_Test_Data_Value"));
		intervalLabel = new JLabel(Messages.getString("Generate_Test_Data_Interval"));
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 20, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.gridx = 0;
		constraints.gridy = 0;
		jContentPane.add(propertyLabel, constraints);
		constraints.gridx = 1;
		jContentPane.add(valueLabel, constraints);
		constraints.gridx = 2;
		jContentPane.add(intervalLabel, constraints);
		while(iterator.hasNext()) {
			String name = iterator.next();
			Component component = components.get(name);
			if(component instanceof RangeSlider) {
				constraints = new GridBagConstraints();
				RangeSlider originalRS = (RangeSlider)component;
				RangeSlider rs = new RangeSlider(originalRS.getMinimum(), originalRS.getMaximum());
				componentArray[index] = rs;
				rs.setHighValue(originalRS.getMaximum());
				rs.setLowValue(originalRS.getMinimum());
				rs.setPaintTicks(true);
				rs.setPaintLabels(true);
				int delta = originalRS.getMaximum() - originalRS.getMinimum();
				int numTicks = originalRS.getMaximum() < 1000 ? 4 : 2;
				int tickSpacing = delta > numTicks ? delta / numTicks : 1;
				rs.setMajorTickSpacing(tickSpacing); 
				checkBoxArray[index] = new JCheckBox();
				checkBoxArray[index].setText(name);
				checkBoxArray[index].setName(name);
				textFieldArray[index] = new JTextField("5");
				textFieldArray[index].getDocument().addDocumentListener(this);
				checkBoxArray[index].setSelected(true);
				checkBoxArray[index].addActionListener(this);
				int topInset = 10;
				if(index == 0) {
					constraints.anchor = GridBagConstraints.PAGE_START;
					topInset = 0;
				} else {
					constraints.anchor = GridBagConstraints.LINE_START;
				}
				constraints.weightx = 0;
				constraints.gridx = 0;
				constraints.gridy = index + 1;
				constraints.insets = new Insets(topInset, 0, 0, 0);
				jContentPane.add(checkBoxArray[index], constraints);
				constraints.insets = new Insets(topInset, 15, 0, 0);
				constraints.gridx = 1;
				constraints.gridy = index + 1;
				jContentPane.add(rs, constraints);
				constraints.insets = new Insets(topInset, 15, 0, 0);
				constraints.gridx = 2;
				constraints.gridy = index + 1;
				constraints.ipadx = 30;
				jContentPane.add(textFieldArray[index], constraints);
				index++;
			} else if(component instanceof CheckBoxListComboBox) {
				constraints = new GridBagConstraints();
				CheckBoxListComboBox originalList  = (CheckBoxListComboBox)component;
				CheckBoxListComboBox cbList = new CheckBoxListComboBox(originalList.getModel());
				componentArray[index] = cbList;
				checkBoxArray[index] = new JCheckBox();
				checkBoxArray[index].setText(name);
				checkBoxArray[index].setName(name);
				textFieldArray[index] = new JTextField("5");
				textFieldArray[index].getDocument().addDocumentListener(this);
				checkBoxArray[index].setSelected(true);
				checkBoxArray[index].addActionListener(this);
				int topInset = 10;
				if(index == 0) {
					constraints.anchor = GridBagConstraints.PAGE_START;
					topInset = 0;
				} else {
					constraints.anchor = GridBagConstraints.LINE_START;
				}
				constraints.weightx = 0;
				constraints.gridx = 0;
				constraints.gridy = index + 1;
				constraints.insets = new Insets(topInset, 0, 0, 0);
				jContentPane.add(checkBoxArray[index], constraints);
				constraints.insets = new Insets(topInset, 15, 0, 0);
				constraints.gridx = 1;
				constraints.gridy = index + 1;
				jContentPane.add(cbList, constraints);
				index++;
			}
		}
		frame.add(jContentPane);
		setControl(composite);	

	}

	public Map<String, Component> getComponents() {
		return components;
	}

	public Map<String, Component> getSelectedComponents() {
		return selectedComponents;
	}

	public void setSelectedComponents(Map<String, Component> selectedComponents) {
		this.selectedComponents = selectedComponents;
	}

	public void setComponents(Map<String, Component> components) {
		this.components = components;
	}



	public Component[] getComponentArray() {
		return componentArray;
	}



	public void setComponentArray(Component[] componentArray) {
		this.componentArray = componentArray;
	}



	public JCheckBox[] getCheckBoxArray() {
		return checkBoxArray;
	}



	public void setCheckBoxArray(JCheckBox[] checkBoxArray) {
		this.checkBoxArray = checkBoxArray;
	}



	public JTextField[] getTextFieldArray() {
		return textFieldArray;
	}



	public void setTextFieldArray(JTextField[] textFieldArray) {
		this.textFieldArray = textFieldArray;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JCheckBox) {
			JCheckBox checkBox = (JCheckBox)e.getSource();
			int index = 0;
			for (JCheckBox checkBoxItem : checkBoxArray) {
				if(checkBox == checkBoxItem) {
					if(!checkBox.isSelected()) {
						textFieldArray[index].setEnabled(false);
						componentArray[index].setEnabled(false);
						return;
					} else {
						textFieldArray[index].setEnabled(true);
						componentArray[index].setEnabled(true);
						return;
					}
				}else {
					index++;
				}
			}
		} else if(e.getSource() instanceof JTextField){
			try {
				Integer.parseInt(((JTextField)e.getSource()).getText());
				setPageComplete(true);
			} catch(Exception excep) {
				setPageComplete(false);
			}
		}
	}

	public boolean canFlipToNextPage() {
		selectedComponents = new HashMap<String, Component>();
		int index = 0;
		for(JCheckBox checkBox : checkBoxArray) {
			if(checkBox.isSelected()) {
				selectedComponents.put(checkBox.getName(), componentArray[index]);
			}
			index++;
		}
		return true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		validateInterval(e);
	}

	private void validateInterval(DocumentEvent e) {
		Document doc = e.getDocument();
		String text = "";
		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		try {
			Integer.parseInt(text);
			new Thread(new Runnable() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							setPageComplete(true); 
							setErrorMessage(null);
						}
					});
				}
			}).start();
		}  catch (Exception e1) {
			new Thread(new Runnable() {
				public void run() {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							setPageComplete(false); 
							setErrorMessage(Messages.getString("Invalid_Interval"));
						}
					});
				}
			}).start();

		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		validateInterval(e);
	}


}

