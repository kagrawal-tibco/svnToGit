package com.tibco.cep.decision.table.wizard;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ISetSelectionTarget;
/**
 * 
 * @author sasahoo
 *
 */
public class AbstractNewWizard extends Wizard implements
        INewWizard {

    private IWorkbenchWindow workbenchWindow;
    private IStructuredSelection selection;

    /**
     * Perform operation when the finish button is pressed. Subclasses should
     * override this. Default implementation returns <code>false</code>.
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {
        return false;
    }

    /**
     * The <code>BasicNewBpmResourceWizard</code> implementation of this
     * <code>IWorkbenchWizard</code> method init records the current workbench
     * and selection. These values can be accessed using
     * <code>getWorkbench()</code> and <code>getSelection()</code>.
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbenchWindow = workbench.getActiveWorkbenchWindow();
        this.selection = selection;

    }

    /**
     * Get the current selection
     * 
     * @return Current <code>IStructuredSelection</code>
     */
    public IStructuredSelection getSelection() {
        return selection;
    }

    /**
     * Attempts to select and reveal the specified resource in all parts within
     * the supplied workbench window's active page. See the static method<code>selectAndReveal</code>
     * for more details.
     * 
     * @param objectToSelect
     */
    protected void selectAndReveal(Object objectToSelect) {
        selectAndReveal(objectToSelect, workbenchWindow);
    }

    /**
     * Attempts to select and reveal the specified resource in all parts within
     * the supplied workbench window's active page.
     * <p>
     * Checks all parts in the active page to see if they implement
     * <code>ISetSelectionTarget</code>, either directly or as an adapter. If
     * so, tells the part to select and reveal the specified resource.
     * </p>
     * 
     * @param Object
     *            to be selected and revealed
     * @param IWorkbenchWindow
     *            the workbench window to select and reveal the resource
     * 
     * @see ISetSelectionTarget
     */
    public static void selectAndReveal(Object objectToSelect,
            IWorkbenchWindow window) {

        // validate the input
        if (window == null || objectToSelect == null) {
            return;
        }
        IWorkbenchPage page = window.getActivePage();
        if (page == null) {
            return;
        }

        // get all the view and editor parts
        List<IWorkbenchPart> parts = new ArrayList<IWorkbenchPart>();
        IWorkbenchPartReference refs[] = page.getViewReferences();
        for (int i = 0; i < refs.length; i++) {
            IWorkbenchPart part = refs[i].getPart(false);
            if (part != null) {
                parts.add(part);
            }
        }
        refs = page.getEditorReferences();
        for (int i = 0; i < refs.length; i++) {
            if (refs[i].getPart(false) != null) {
                parts.add(refs[i].getPart(false));
            }
        }

        final ISelection selection = new StructuredSelection(objectToSelect);

        for (Iterator<IWorkbenchPart> iter = parts.iterator(); iter.hasNext();) {
            IWorkbenchPart part = iter.next();

            // get the part's ISetSelectionTarget implementation
            ISetSelectionTarget target = null;
            if (part instanceof ISetSelectionTarget) {
                target = (ISetSelectionTarget) part;
            } else {
                target = (ISetSelectionTarget) part
                        .getAdapter(ISetSelectionTarget.class);
            }

            if (target != null) {
                // select and reveal resource
                final ISetSelectionTarget finalTarget = target;
                window.getShell().getDisplay().asyncExec(new Runnable() {
                    public void run() {
                        finalTarget.selectReveal(selection);
                    }
                });
            }
        }
    }

}
