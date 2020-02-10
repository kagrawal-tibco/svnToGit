package com.tibco.cep.studio.ui.editors.archives;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.archive.SharedArchive;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ArchiveConfigurationFormViewer extends com.tibco.cep.studio.ui.forms.AbstractArchiveFormViewer{

	private Text archiveNameText;
	private Text archiveDescText;
	private Text archiveAuthorText;
    private Text archiveVersionText;
    private Text fileLocText;
    
    private Button button;
    private Button cbutton;
    
	private TableViewer barviewer;
	

	private TableViewer sarviewer;
	
	public ArchiveConfigurationFormViewer(EnterpriseArchiveEditor archiveEditor, EnterpriseArchive archive){
		this.editor = archiveEditor;
		this.archive = archive;
	}
	 
	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		/**
		 * @see com.tibco.cep.studio.ui.editors.AbstractFormViewer
		 */
		super.createPartControl(container, archive.getName() + " "+Messages.getString("ear.editor.title"),EditorsUIPlugin.getImageDescriptor("icons/enterpriseArchive16x16.gif").createImage());
	}
	
	@Override
	protected void createConfigurationPart(ScrolledForm form, FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
		
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
	
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		sectionClient.setLayout(layout);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 50;
		sectionClient.setLayoutData(layoutData);
		
		toolkit.createLabel(sectionClient, Messages.getString("Name"),SWT.NONE);
		archiveNameText =toolkit.createText(sectionClient,archive.getName()!=null?archive.getName():"",  SWT.BORDER | SWT.READ_ONLY );
		gd = new GridData(GridData.FILL_HORIZONTAL);
		archiveNameText.setLayoutData(gd);
		toolkit.createLabel(sectionClient, "",SWT.NONE);

		toolkit.createLabel(sectionClient, Messages.getString("Description"),SWT.NONE);
		archiveDescText = toolkit.createText(sectionClient,archive.getDescription()!=null?archive.getDescription():"",  SWT.BORDER);
		archiveDescText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					Command command=new SetCommand(getEditingDomain(),(EObject)archive,ArchivePackage.eINSTANCE.getArchiveResource_Description(),archiveDescText.getText()) ;			
					EditorUtils.executeCommand(editor, command);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
              }
			});

		gd = new GridData(GridData.FILL_HORIZONTAL);
		archiveDescText.setLayoutData(gd);
		toolkit.createLabel(sectionClient, "",SWT.NONE);
		
		toolkit.createLabel(sectionClient, Messages.getString("bar.editor.page.details.Author"),SWT.NONE);
		archiveAuthorText = toolkit.createText(sectionClient,archive.getAuthor()!=null?archive.getAuthor():"",  SWT.BORDER);
		archiveAuthorText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try{
					Command command=new SetCommand(getEditingDomain(),(EObject)archive,ArchivePackage.eINSTANCE.getArchiveResource_Author(),archiveAuthorText.getText()) ;			
					EditorUtils.executeCommand(editor, command);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
			});

		gd = new GridData(GridData.FILL_HORIZONTAL);
		archiveAuthorText.setLayoutData(gd);
		toolkit.createLabel(sectionClient, "",SWT.NONE);
		
		toolkit.createLabel(sectionClient, Messages.getString("ear.editor.config.ArchiveVersion"),SWT.NONE);
		archiveVersionText = toolkit.createText(sectionClient,archive.getVersion()!=null?archive.getVersion():"",  SWT.BORDER);
		archiveVersionText.setText(archive.getDescription()!=null?archive.getDescription():"");
		archiveVersionText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				//TODO
              }
			});

		gd = new GridData(GridData.FILL_HORIZONTAL);
		archiveVersionText.setLayoutData(gd);
		toolkit.createLabel(sectionClient, "",SWT.NONE);
		
		
		toolkit.createLabel(sectionClient, Messages.getString("ear.editor.config.ArchiveLocation"),SWT.NONE);
		fileLocText =toolkit.createText(sectionClient,archive.getFileLocation()!=null?archive.getFileLocation():"", SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileLocText.setLayoutData(gd);
		fileLocText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
					//TODO
				}
			});
		
		button = new Button(sectionClient, SWT.NONE);
		button.setText(Messages.getString("Browse"));
		button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						FileDialog fd = new FileDialog(editor.getSite().getWorkbenchWindow().getShell(), SWT.OPEN);
						fd.setText(Messages.getString("Open"));
						String[] filterExt = {"*.ear"};
						fd.setFilterExtensions(filterExt);
						if(new File(fileLocText.getText()).exists()){
							fd.setFilterPath(fileLocText.getText());
						}else
							fd.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toPortableString());
						String selected = fd.open();
						if(selected!=null)
							fileLocText.setText(selected+".ear");  
					}
				});
		
		toolkit.createLabel(sectionClient,  Messages.getString("ear.editor.config.GlobalVariables"),SWT.NONE);
		cbutton = toolkit.createButton(sectionClient,"",  SWT.CHECK);
		cbutton.setSelection(true);
		
		createArchiveSection(form, toolkit);
		
		toolkit.paintBordersFor(sectionClient);
	}

	@SuppressWarnings("serial")
	private void createArchiveSection(final ScrolledForm form,	final FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(),Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		section.setText(Messages.getString("ear.editor.page.archive.info.title"));
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout elayout = new GridLayout();
		elayout.verticalSpacing = 0;
		elayout.numColumns = 1;
		sectionClient.setLayout(elayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 300;
		gd.widthHint = 200;
		section.setLayoutData(gd);
		
		ImageHyperlink image = toolkit.createImageHyperlink(sectionClient,SWT.NONE);
		image.setImage(EditorsUIPlugin.getDefault().getImage("icons/beArchives16x16.png"));
		image.setText(Messages.getString("bar.editor.page.title"));
		image.addHyperlinkListener(new HyperlinkAdapter() {
		      public void linkActivated(HyperlinkEvent e) {
		    	  editor.activePage(BUSINNESS_EVENTS_ARCHIVE);
		      }
		    });
		
		Action addRowAction = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				Display.getDefault().asyncExec(new Runnable(){
					public void run() {
						BusinessEventsArchiveResource newBusinessEventsArchiveResource = ArchiveFactory.eINSTANCE.createBusinessEventsArchiveResource();
						newBusinessEventsArchiveResource.setName("BusinessEventsArchiveResource1");
						archive.getBusinessEventsArchives().add(newBusinessEventsArchiveResource);
						setBusinessEventsArchiveResource(businessEventsArchiveResource);
						barviewer.refresh();
				    	int count = barviewer.getTable().getItemCount();
				    	Object item = (BusinessEventsArchiveResource)barviewer.getElementAt(count-1);
				    	barviewer.setSelection(new StructuredSelection(item));
				    	barviewer.getTable().setFocus();
						editor.getConfigurationFormViewer().getBarviewer().refresh();
				    	editor.modified();
					}});
			}
		};
		Action removeRowAction = new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
				Display.getDefault().asyncExec(new Runnable(){
					public void run() {
				    	int index = barviewer.getTable().getSelectionIndex();
				    	archive.getBusinessEventsArchives().remove(index);
				    	barviewer.refresh();
				    	if(index-1!=-1){
				    		Object item = (BusinessEventsArchiveResource)barviewer.getElementAt(index-1);
				    		barviewer.setSelection(new StructuredSelection(item));
				    		barviewer.getTable().setFocus();
				    	}
				    	editor.getConfigurationFormViewer().getBarviewer().refresh();
				    	editor.modified();
					}});
			}
		};
//		createButtons(toolkit, sectionClient,addRowAction,removeRowAction);
		
		Table barTable = toolkit.createTable(sectionClient, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		barTable.setLayoutData(gd);
		barviewer = new TableViewer(barTable);
		final ArchiveAction barAction = new ArchiveAction(editor,barviewer);
		barviewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				barAction.run();
			}
		});
		barviewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
			   //TODO	
			}
		});
		barviewer.setContentProvider(new BusinessEventsArchivesContentProvider());
		barviewer.setLabelProvider(new BusinessEventsArchivesLabelProvider());
		barviewer.setInput(archive);
		
		
		image = toolkit.createImageHyperlink(sectionClient, SWT.NONE);
		image.setImage(EditorsUIPlugin.getDefault().getImage("icons/sharedArchives16x16.png"));
		image.setText(Messages.getString("sar.editor.page.title"));
		image.addHyperlinkListener(new HyperlinkAdapter() {
		      public void linkActivated(HyperlinkEvent e) {
		    	  editor.activePage(SHARED_ARCHIVE);
		      }
		    });

		
		Action addSARRowAction = new AbstractAction("Add") {
			public void actionPerformed(ActionEvent e) {
				Display.getDefault().asyncExec(new Runnable(){
					public void run() {
						SharedArchive newSharedArchive = ArchiveFactory.eINSTANCE.createSharedArchive();
						newSharedArchive.setName("SharedArchiveResource_1");
						archive.getSharedArchives().add(newSharedArchive);
						setSharedArchive(newSharedArchive);
						sarviewer.refresh();
						int count = sarviewer.getTable().getItemCount();
						Object item = (SharedArchive)sarviewer.getElementAt(count-1);
						sarviewer.setSelection(new StructuredSelection(item));
						sarviewer.getTable().setFocus();
						editor.getConfigurationFormViewer().getSarviewer().refresh();
						editor.modified();
					}});
			}
		};
		Action removeSARRowAction = new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
				Display.getDefault().asyncExec(new Runnable(){
					public void run() {
						if(sarviewer.getSelection() instanceof IStructuredSelection){
							int index = sarviewer.getTable().getSelectionIndex();
							archive.getSharedArchives().remove(index);
							sarviewer.refresh();
							if(index-1!=-1){
								Object item = (SharedArchive)sarviewer.getElementAt(index-1);
								sarviewer.setSelection(new StructuredSelection(item));
								sarviewer.getTable().setFocus();
							}
							editor.getConfigurationFormViewer().getSarviewer().refresh();
							editor.modified();
						}
					}});
			}
		};
//		createButtons(toolkit, sectionClient,addSARRowAction,removeSARRowAction);
		
		Table sarTable = toolkit.createTable(sectionClient, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		sarTable.setLayoutData(gd);
		sarviewer = new TableViewer(sarTable);
		final ArchiveAction sarAction = new ArchiveAction(editor,sarviewer);
		sarviewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				sarAction.run();
			}
		});
		sarviewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				//TODO
			}
		});
		sarviewer.setContentProvider(new SharedArchivesContentProvider());
		sarviewer.setLabelProvider(new SharedArchivesLabelProvider());
		sarviewer.setInput(archive);
		
		toolkit.paintBordersFor(sectionClient);
	}
	
	public TableViewer getBarviewer() {
		return barviewer;
	}

	public TableViewer getSarviewer() {
		return sarviewer;
	}
}
