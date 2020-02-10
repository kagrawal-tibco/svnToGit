package com.tibco.cep.bpmn.ui.graph.properties;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.designtime.CommonOntologyAdapter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmSchema;

/**
 * 
 * @author ggrigore
 *
 */
public class OutputPropertySection extends AbstractBpmnPropertySection  {
	
    protected Composite composite;
	private Tree tree;
	@SuppressWarnings("unused")
	private TreeItem rootNode;
	private TreeViewer treeViewer;
	private Entity entity;
	private Ontology ontology;
	
	static String [] columnTitles	= {BpmnMessages.getString("outputPropertySection_column_variable"),BpmnMessages.getString("outputPropertySection_column_type")};


	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		@SuppressWarnings("unused")
		int style = SWT.LEFT_TO_RIGHT | SWT.SINGLE| SWT.H_SCROLL| SWT.V_SCROLL| SWT.FULL_SELECTION | SWT.BORDER | SWT.FILL;
		/* Create the text tree */
		treeViewer = new CheckboxTreeViewer(parent);
//		tree = new Tree (parent, style);
		tree = treeViewer.getTree();
//		tree.setLayout(new FillLayout());
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		for (int i = 0; i < columnTitles.length; i++) {
			TreeColumn treeColumn = new TreeColumn(tree, SWT.LEFT);
			treeColumn.setText(columnTitles[i]);
			treeColumn.setToolTipText(MessageFormat.format("Tooltip:  {0}", new Object [] {columnTitles[i]}));
			treeColumn.setWidth(200);
		}
		setColumnsResizable(true);		
		tree.setSortColumn(tree.getColumn(0));
		// Attach a listener directly after the creation
		tree.addListener(SWT.Selection,new Listener() {
			
		   public void handleEvent(org.eclipse.swt.widgets.Event e) {
		       if( e.detail == SWT.CHECK ) {
		           if( !(e.item.getData() instanceof RootEntity) ) {
		              e.detail = SWT.NONE;
		              e.type   = SWT.None;
//		              e.doIt   = false;
		              try {
		                 tree.setRedraw(false);
		                 TreeItem item = (TreeItem)e.item;
		                 item.setChecked(! item.getChecked() );
		              } finally {
		                 tree.setRedraw(true);
		              }
		           }
		       }
		   }
		});

		
		
		fEditor = (BpmnEditor) tabbedPropertySheetPage.getSite().getPage().getActiveEditor();
		fProject = ((BpmnEditorInput) fEditor.getEditorInput()).getFile().getProject();
		if (fProject == null || fEditor == null) {
			return;
		}
		try {
			setOntology(new CommonOntologyAdapter(getProject().getName()));
		} catch (Exception e1) {
			BpmnUIPlugin.log(e1);
		}
		
		
		treeViewer.setContentProvider(new OutputTreeContentProvider(fProject));
		treeViewer.setLabelProvider(new OutputTreeLabelProvider());
		

		tree.pack(true);

	}

	
	void packColumns (Tree tree) {
		int columnCount = tree.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			TreeColumn treeColumn = tree.getColumn(i);
			treeColumn.pack();
		}
	}
	
	/**
	 * Sets the moveable columns state of the "Example" widgets.
	 */
	void setColumnsMoveable (boolean selection) {
		TreeColumn[] columns1 = tree.getColumns();
		for (int i = 0; i < columns1.length; i++) {
			columns1[i].setMoveable(selection);
		}		
	}
	
	/**
	 * Sets the resizable columns state of the "Example" widgets.
	 */
	void setColumnsResizable (boolean selection) {
		TreeColumn[] columns1 = tree.getColumns();
		for (int i = 0; i < columns1.length; i++) {
			columns1[i].setResizable(selection);
		}
	}
	
	
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
	 * shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (fTSENode != null) { 
			Object obj = fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_ACTION);
			if(obj == null) {
				getTreeViewer().setInput(getProject());
				return;
			}
			if (obj instanceof String) {
				getTreeViewer().setInput(obj);
				
			}
			
		}
		if (fTSEEdge != null) {
			Object obj = fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_ACTION);
			if(obj == null) {
				getTreeViewer().setInput(getProject());
				return;
			}
		}
		if (fTSEGraph != null) {
			Object obj = fTSEGraph.getAttributeValue(BpmnUIConstants.NODE_ATTR_TASK_ACTION);
			if(obj == null) {
				getTreeViewer().setInput(getProject());
				return;
			} else if(obj instanceof String) {
				getTreeViewer().setInput(obj);
			}
		}
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 */
	@SuppressWarnings("unused")
	private void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private Ontology getOntology() {
		return ontology;
	}

	/**
	 * @param ontology
	 */
	private void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}
	
	/**
	 * @return the treeViewer
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * @param treeViewer the treeViewer to set
	 */
	public void setTreeViewer(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	
	public static class RootEntity extends TypeEntity {
		
		public RootEntity(Entity e) {
			super(null,e);
		}		
	}
	
	public static class TypeEntity {
		TypeEntity parent;
		Object typedEntity;
		/**
		 * @param parent
		 * @param entity
		 */
		public TypeEntity(TypeEntity parent, Object entity) {
			super();
			this.parent = parent;
			typedEntity = entity;
		}
		/**
		 * @return the parent
		 */
		public TypeEntity getParent() {
			return parent;
		}
		/**
		 * @param parent the parent to set
		 */
		public void setParent(TypeEntity parent) {
			this.parent = parent;
		}
		/**
		 * @return the tEntity
		 */
		public Object getTypedEntity() {
			return typedEntity;
		}
		/**
		 * @param entity the tEntity to set
		 */
		public void setTypedEntity(Object entity) {
			typedEntity = entity;
		}
		
		public String getName() {
			if(typedEntity instanceof Entity) {
				return ((Entity)typedEntity).getName();
			} else if( typedEntity instanceof PropertyDefinition) {
				return ((PropertyDefinition)typedEntity).getName();
			} else if( typedEntity instanceof EventPropertyDefinition) {
				return ((EventPropertyDefinition)typedEntity).getPropertyName();
			} else {
				return typedEntity.toString();
			}
		}
		
		public String getType() {
			if( typedEntity instanceof PropertyDefinition) {
				final int type =  ((PropertyDefinition)typedEntity).getType();
				return RDFTypes.getTypeString(type);
			} else if( typedEntity instanceof EventPropertyDefinition) {
				return ((EventPropertyDefinition)typedEntity).getType().getName();
			} else if(typedEntity instanceof Entity) {
				return ((Entity)typedEntity).getFullPath();
			} else {
				return typedEntity.toString();
			}
		}
		
		boolean hasChildren() {
			return getChildren().size() > 0;
		}
		
		@SuppressWarnings("rawtypes")
		List<TypeEntity> getChildren() {
			ArrayList<TypeEntity> children = new ArrayList<TypeEntity>();
			if(typedEntity instanceof Concept) {
				Concept c = (Concept) typedEntity;
				List pdefs = c.getAllPropertyDefinitions();
				for(java.util.Iterator<?> it = pdefs.iterator(); it.hasNext();){
					children.add(new TypeEntity(this,it.next()));
				}
			} else if (typedEntity instanceof Event) {
				Event e = (Event) typedEntity;
				List pdefs = e.getAllUserProperties();
				for(java.util.Iterator<?> it = pdefs.iterator(); it.hasNext();){
					children.add(new TypeEntity(this,it.next()));
				}
			}
			return children;
		}
		
		
	}



	public static class OutputTreeContentProvider implements ITreeContentProvider {
		
		IProject project;
		BEProject beProject;
		String entityURI;
		
		

		/**
		 * @param project2
		 */
		public OutputTreeContentProvider(IProject project) {
			super();
			this.project = project;
			
		}
		
		
		
		/**
		 * @return the project
		 */
		public IProject getProject() {
			return project;
		}



		/**
		 * @param project the project to set
		 */
		public void setProject(IProject project) {
			this.project = project;
		}



		BEProject getBEProject() {
			if(beProject == null) {
				this.beProject = new StudioEMFProject(project.getName());
				try {
					this.beProject.load();
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}
			}
			return beProject;
		}
		
		

		/**
		 * @return the Entity
		 */
		public String getEntityURI() {
			return entityURI;
		}



		/**
		 * @param rootEntity the rootEntity to set
		 */
		public void setEntityURI(String uri) {
			this.entityURI = uri;
		}



		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object parentElement) {
			if(beProject != null) {
				GlobalVariables gvars = getBEProject().getGlobalVariables();
				SmElement gvElement = gvars.toSmElement();
				@SuppressWarnings("unused")
				SmSchema gvSchema = gvElement.getSchema();
			}
			if(parentElement != null) {
				if(parentElement instanceof String) {
					Entity entity = getBEProject().getOntology().getEntity((String) parentElement);
					if(entity != null) {
						return new Object[]  { new RootEntity(entity) };
					}
				}
				if(parentElement instanceof TypeEntity) {
					TypeEntity tEntity = (TypeEntity) parentElement;
					return tEntity.getChildren().toArray();
				} else if(parentElement == null || parentElement instanceof IProject) {
					List<Concept> scoreCards = getScorecards();
					ArrayList<TypeEntity> children = new ArrayList<TypeEntity>();
					for(java.util.Iterator<?> it = scoreCards.iterator(); it.hasNext();){
						children.add(new RootEntity((Entity) it.next()));
					}
					return children.toArray();
				}
			} else {
				List<Concept> scoreCards = getScorecards();
				ArrayList<TypeEntity> children = new ArrayList<TypeEntity>();
				for(java.util.Iterator<?> it = scoreCards.iterator(); it.hasNext();){
					children.add(new RootEntity((Entity) it.next()));
				}
				return children.toArray();
			}
			return new Object[0];
		}

		List<Concept> getScorecards() {
			ArrayList<Concept> scoreCards = new ArrayList<Concept>();
			Collection<?> concepts = getBEProject().getOntology().getConcepts();
			for(Iterator<?> it =  concepts.iterator(); it.hasNext();){
				Concept c = (Concept) it.next();
				if(c.isAScorecard()) {
					scoreCards.add(c);
				}
			}
			return scoreCards;
		}
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		@Override
		public Object getParent(Object element) {
			if(element instanceof RootEntity) {
				return null;
			} else if( element instanceof TypeEntity) {
				((TypeEntity)element).getParent();
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		@Override
		public boolean hasChildren(Object element) {
			if(element instanceof String) {
				return getChildren(element).length > 0;
			} else if( element instanceof TypeEntity) {
				return ((TypeEntity)element).hasChildren();
			} else if( element instanceof Concept) {
				return ((Concept)element).getAllPropertyDefinitions().size() > 0;
			} else if ( element instanceof Event) {
				return ((Event)element).getAllUserProperties().size() > 0;
			} else if ( element instanceof IProject) {
				return getScorecards().size() > 0;
			} else if( element == null) {
				return getScorecards().size() > 0;
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if(newInput instanceof IProject ) {
				setProject((IProject) newInput);
			} else if( newInput instanceof String) {
				setEntityURI((String) newInput);
			}
			
		}
		
	}
	
	public static class OutputTreeLabelProvider extends LabelProvider implements ITableLabelProvider {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if(element instanceof TypeEntity) {
				return ((TypeEntity)element).getName();
			}
			return super.getText(element);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
		 */
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return super.getImage(element);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 */
		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof TypeEntity) {
				switch(columnIndex) {
				case 0:
					return ((TypeEntity)element).getName();
				case 1:
					return ((TypeEntity)element).getType();
				}
			}
			return super.getText(element);
		}
		
		
		
	}

	@Override
	protected void updatePropertySection(Map<String, Object> updateList) {
		// TODO Auto-generated method stub
		
	}

}