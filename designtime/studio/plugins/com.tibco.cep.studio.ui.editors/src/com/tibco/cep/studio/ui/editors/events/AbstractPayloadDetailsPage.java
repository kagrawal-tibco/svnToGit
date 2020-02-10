/**
 * 
 */
package com.tibco.cep.studio.ui.editors.events;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormFeederDelegate;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormViewer;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.AdvancedEventPayloadTree;

/**
 * @author mgujrath
 *
 */
public abstract class AbstractPayloadDetailsPage implements IDetailsPage{
	
	protected IManagedForm managedForm;
	protected AdvancedEventPayloadTree m_tree;
	protected String type;
	protected ScrolledPageBook payloadDetailsPageBook;
	protected IProject project;
	protected ChannelFormFeederDelegate delegate;
	protected ChannelFormViewer formViewer;
	protected String[] driversArray; 
	protected HashMap<Object,Object> controls;
	protected Composite details;

	protected PropertyMap instance;

	protected static final int HEIGHTHINTnormal = 200;
	protected static final int HEIGHTHINTmin = 3;
	protected static final int SIZEaugend = 5;
	protected static final int LAYOUTmultiplier = 30;

	@Override
	public void createContents(Composite parent) {

		controls = new HashMap<Object, Object>();
		TableWrapLayout layout1 = new TableWrapLayout();
		parent.setLayout(layout1);
		FormToolkit toolkit = managedForm.getToolkit();
		Section section  = toolkit.createSection(parent, Section.NO_TITLE);
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		section.setLayoutData(td);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		details = toolkit.createComposite(sectionClient);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 380;
		details.setLayoutData(data);
		details.setLayout(new FillLayout());
		payloadDetailsPageBook = toolkit.createPageBook(details, SWT.NONE);
		createPayloadDetailsPage(toolkit, type);	

		payloadDetailsPageBook.showEmptyPage();
		toolkit.paintBordersFor(sectionClient);

		
	}
	
	protected void createPayloadDetailsPage(FormToolkit toolkit,String type){
		
		if(type.equalsIgnoreCase("root")){
			
			Composite root = payloadDetailsPageBook.createPage(type);
			root.setLayout(new GridLayout(3, false));
			
			toolkit.createLabel(root, "Content:");
			Combo contextCombo = new Combo(root, SWT.None);
			contextCombo.setItems(PayloadConstants.getContxtComboItems());
			contextCombo.select(0);
			contextCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			toolkit.createLabel(root, "");
		//	controls.put(type + CommonIndexUtils.DOT + Messages.getString("Name"), name);
			
			toolkit.createLabel(root, "Name:");
			final Text name = toolkit.createText(root, "");
			controls.put(type + CommonIndexUtils.DOT + Messages.getString("Name"), name);
			name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			toolkit.createLabel(root, "");
						
			toolkit.createLabel(root, "Cardinality:");
			Combo cardinalityCombo = new Combo(root, SWT.None);
			cardinalityCombo.setItems(PayloadConstants.getCardinalityComboItems());
			cardinalityCombo.select(0);
			cardinalityCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			
			
		//	controls.put(type + CommonIndexUtils.DOT + Messages.getString("Name"), name);
			
			
		}
		if(type.equalsIgnoreCase("param")){
			
			Composite root = payloadDetailsPageBook.createPage(type);
			root.setLayout(new GridLayout(3, false));
			
			toolkit.createLabel(root, "Content:");
			Combo contextCombo = new Combo(root, SWT.None);
			contextCombo.setItems(PayloadConstants.getContxtComboItems());
			contextCombo.select(1);
			toolkit.createLabel(root, "");
			
			toolkit.createLabel(root, "Name:");
			final Text name = toolkit.createText(root, "");
			controls.put(type + CommonIndexUtils.DOT + Messages.getString("Name"), name);
			name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			toolkit.createLabel(root, "");
			
			toolkit.createLabel(root, "Cardinality:");
			Combo cardinalityCombo = new Combo(root, SWT.None);
			cardinalityCombo.setItems(PayloadConstants.getCardinalityComboItems());
			cardinalityCombo.select(0);
			cardinalityCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			toolkit.createLabel(root, "");
			
			toolkit.createLabel(root, "Type:");
			Combo typecombo = new Combo(root, SWT.None);
			typecombo.setItems(PayloadConstants.getTypeComboItems());
			typecombo.select(0);
			typecombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			
			
		//	controls.put(type + CommonIndexUtils.DOT + Messages.getString("Name"), name);
			
			
		}
	}
}
