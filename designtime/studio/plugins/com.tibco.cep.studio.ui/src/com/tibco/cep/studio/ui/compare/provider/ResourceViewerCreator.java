package com.tibco.cep.studio.ui.compare.provider;

import java.util.Iterator;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.StructureDiffViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.tibco.cep.studio.core.compare.util.CompareMessages;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;

public class ResourceViewerCreator implements IViewerCreator {

	public ResourceViewerCreator() {
		super();
	}

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		final Tree tree = new Tree(parent, SWT.FULL_SELECTION);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		
		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(CompareMessages.getString("ResourceViewerCreator.resource")); //$NON-NLS-1$
		column.setToolTipText(CompareMessages.getString("ResourceViewerCreator.resource")); //$NON-NLS-1$

		final TreeColumn leftColumn = new TreeColumn(tree, SWT.LEFT);
		final TreeColumn rightColumn = new TreeColumn(tree, SWT.LEFT);
		String leftLabel = config.getLeftLabel(null) == null ? "" : config.getLeftLabel(null);
		leftColumn.setText(leftLabel);
		String rightLabel = config.getRightLabel(null) == null ? "" : config.getRightLabel(null);
		rightColumn.setText(rightLabel);
		
		final StructureDiffViewer viewer = new StructureDiffViewer(tree, config) {
			
			private IPropertyChangeListener fPreferenceChangeListener;
			private Action fCopyLeftToRightAction;
			private Action fCopyRightToLeftAction;
			private Action fNextAction;
			private Action fPreviousAction;

			@Override
			protected void createToolItems(ToolBarManager toolbarManager) {
				fCopyLeftToRightAction= new Action() {
					public void run() {
						copySelected(true);
					}
				};
				fCopyLeftToRightAction.setText(CompareMessages.getString("ResourceViewerCreator.Copy.LeftToRight")); //$NON-NLS-1$
				fCopyLeftToRightAction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/comp/copycont_r_co.gif")); //$NON-NLS-1$
				fCopyLeftToRightAction.setEnabled(false);
				toolbarManager.appendToGroup("merge", fCopyLeftToRightAction); //$NON-NLS-1$

				fCopyRightToLeftAction= new Action() {
					public void run() {
						copySelected(false);
					}
				};
				fCopyRightToLeftAction.setText(CompareMessages.getString("ResourceViewerCreator.Copy.RightToLeft")); //$NON-NLS-1$
				fCopyRightToLeftAction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/comp/copycont_l_co.gif")); //$NON-NLS-1$
				fCopyRightToLeftAction.setEnabled(false);
				toolbarManager.appendToGroup("merge", fCopyRightToLeftAction); //$NON-NLS-1$
			
				fNextAction= new Action() {
					public void run() {
						navigate(true);
					}
				};
				fNextAction.setText(CompareMessages.getString("ResourceViewerCreator.Difference.Next")); //$NON-NLS-1$
				fNextAction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/comp/next_diff_nav.gif")); //$NON-NLS-1$
				toolbarManager.appendToGroup("navigation", fNextAction); //$NON-NLS-1$

				fPreviousAction= new Action() {
					public void run() {
						navigate(false);
					}
				};
				fPreviousAction.setText(CompareMessages.getString("ResourceViewerCreator.Difference.Prev")); //$NON-NLS-1$
				fPreviousAction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/comp/prev_diff_nav.gif")); //$NON-NLS-1$
				toolbarManager.appendToGroup("navigation", fPreviousAction); //$NON-NLS-1$
				addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						updateActionStates(event.getSelection());
					}
				});

			}

			private void updateActionStates(ISelection selection) {
				boolean leftToRightEnabled = true;
				boolean rightToLeftEnabled = true;
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					@SuppressWarnings("rawtypes")
					Iterator e = ss.iterator();
					while (e.hasNext()) {
						Object element = e.next();
						if (element instanceof DiffNode) {
							if (!isEditable((DiffNode)element, true)) {
								leftToRightEnabled = false;
							}
							if (!isEditable((DiffNode)element, false)) {
								rightToLeftEnabled = false;
							}
							if (!leftToRightEnabled && !rightToLeftEnabled) {
								break;
							}
						}
					}
				}
				if (fCopyLeftToRightAction != null) {
					fCopyLeftToRightAction.setEnabled(leftToRightEnabled);
				}
				if (fCopyRightToLeftAction != null) {
					fCopyRightToLeftAction.setEnabled(rightToLeftEnabled);
				}
			}

			protected boolean isEditable(DiffNode node, boolean leftToRight) {
//				if (node.getKind() == Differencer.CHANGE) {
//					return false; // For now, only allow edits for 'added' and 'removed' nodes
//				}
				Object o = getInput();
				if (o instanceof DiffNode) {
					DiffNode input = (DiffNode) o;
					ITypedElement left = input.getLeft();
					ITypedElement right = input.getRight();
					if (leftToRight && right instanceof AbstractResourceNode) {
//						EMFCompareNode emfNode = (EMFCompareNode)node.getRight();
//						boolean b = (emfNode != null && emfNode.getInput() != null && emfNode.getInput().getInput() instanceof Table);
						AbstractResourceNode dtNode = ((AbstractResourceNode)right);
						return !dtNode.isRemote() /*&& !b*/;
					} else if (!leftToRight && left instanceof AbstractResourceNode) {
//						EMFCompareNode emfNode = (EMFCompareNode)node.getLeft();
//						boolean b = (emfNode != null && emfNode.getInput() != null && emfNode.getInput().getInput() instanceof Table);
						AbstractResourceNode dtNode = ((AbstractResourceNode)left);
						return !dtNode.isRemote() /*&& !b*/;
					}
				}
				return true;
			}
			
			@Override
			protected void handleDispose(DisposeEvent event) {
				IPreferenceStore store = getCompareConfiguration().getPreferenceStore();
				if (store != null) {
					store.removePropertyChangeListener(fPreferenceChangeListener);
				}
				super.handleDispose(event);
			}

			@Override
			protected void copySelected(boolean leftToRight) {
				Object input = getInput();
				if (input instanceof DiffNode) {
					DiffNode node = (DiffNode) input;
					if (leftToRight && node.getRight() instanceof AbstractResourceNode) {
						((AbstractResourceNode)node.getRight()).setDirty(true);
					} else if (!leftToRight && node.getLeft() instanceof AbstractResourceNode) {
						((AbstractResourceNode)node.getLeft()).setDirty(true);
					}
				}
				super.copySelected(leftToRight);
				
//				Object input = getInput();
//				if (input instanceof DiffNode) {
//					DiffNode node = (DiffNode) input;
//					IDocument doc = CompareUI.getDocument(leftToRight ? node
//							.getRight() : node.getLeft());
//					if (doc != null) {
//						try {
//							// fire a dummy "document changed event" to get our
//							// dirty state updated
//							doc.replace(0, 0, ""); //$NON-NLS-1$
//						} catch (BadLocationException e) {
//							// ignore
//						}
//
//						// forget the cached document to force re-parsing
//						CompareUI.unregisterDocument(doc);
//					}
//				}
			}

			protected void handlePropertyChangeEvent(PropertyChangeEvent event) {
				refresh(true);
			}
			
			protected void inputChanged(Object in, Object oldInput) {
				super.inputChanged(in, oldInput);

				if (in != oldInput) {
					if (in instanceof ICompareInput) {
						
						// not a great place to put this, but the alternative is to pull this out into its own class and put it in the constructor
						if (fPreferenceChangeListener == null) {
							fPreferenceChangeListener = new IPropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent event) {
									handlePropertyChangeEvent(event);
								}
							};
							
							IPreferenceStore store = getCompareConfiguration().getPreferenceStore();
							if (store != null) {
								store.addPropertyChangeListener(fPreferenceChangeListener);
							}
						}
						
						TableLayout layout = new TableLayout();
						layout.addColumnData(new ColumnWeightData(1));

						ICompareInput newInput = (ICompareInput) in;
						
						String left = getCompareConfiguration().getLeftLabel(newInput.getLeft());
						if (left == null) {
							left = "";
						}
						leftColumn.setText(left);
						leftColumn.setToolTipText(leftColumn.getText());
						String right = getCompareConfiguration().getRightLabel(newInput.getRight());
						if (right == null) {
							right = "";
						}
						rightColumn.setText(right);
						
						rightColumn.setToolTipText(rightColumn.getText());

						layout.addColumnData(new ColumnWeightData(1));
						layout.addColumnData(new ColumnWeightData(1));
						getTree().setLayout(layout);
						getTree().layout();
					}

				}
			}
			
		};
		viewer.setStructureCreator(new ResourceStructureCreator(viewer));
		viewer.setLabelProvider(new ResourceDiffLabelProvider());

		return viewer;
	}

}
