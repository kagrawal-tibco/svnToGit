package com.tibco.cep.studio.ui.util;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.SharedResourceSelectionDialog;

/*
@author ssailapp
@date Nov 28, 2009 10:22:20 AM
 */

public class PanelUiUtil {

	public static final int TEXT_FIELD_SIZE_HINT = 120;
	
	public static Composite getTextButtonComposite(Composite parent, int parentSpan, int compositeSpan) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(getCompactWidthGridLayout(compositeSpan, false));
		comp.setLayoutData(getGridData(parentSpan));
		return comp;
	}
	
	public static GridLayout getCompactGridLayout(int numColumns, boolean makeColumnsEqualWidth) {
		GridLayout gridLayout = new GridLayout(numColumns, makeColumnsEqualWidth);
        gridLayout.horizontalSpacing = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        return gridLayout;
	}

	public static GridLayout getCompactWidthGridLayout(int numColumns, boolean makeColumnsEqualWidth) {
		GridLayout gridLayout = new GridLayout(numColumns, makeColumnsEqualWidth);
        gridLayout.horizontalSpacing = 0;
		gridLayout.marginWidth = 0;
        return gridLayout;
	}
	
	public static GridLayout getCompactHeightGridLayout(int numColumns, boolean makeColumnsEqualWidth) {
		GridLayout gridLayout = new GridLayout(numColumns, makeColumnsEqualWidth);
        gridLayout.verticalSpacing = 0;
        gridLayout.marginHeight = 0;
        return gridLayout;
	}
	
	public static GridData getGridData(int horizontalSpan) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = horizontalSpan;
		return gd;
	}
	
	public static Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData());
		return label;
	}

	public static Label createLabelFiller(Composite parent) {
		return createLabelFiller(parent, 1);
	}
	
	public static Label createLabelFiller(Composite parent, int horzontalSpan) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(" ");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = horzontalSpan;
		label.setLayoutData(gd);
		return label;
	}
	
	public static Composite createSeparator(Composite parent, String text) {
		Composite sepComp = new Composite(parent, SWT.NONE);
		sepComp.setLayout(getCompactGridLayout(3, false));
		sepComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label sep1 = new Label(sepComp, SWT.SEPARATOR | SWT.HORIZONTAL);
		sep1.setLayoutData(new GridData(SWT.LEFT));
		createLabel(sepComp, text);
	
		Label sep2 = new Label(sepComp, SWT.SEPARATOR | SWT.HORIZONTAL);
		sep2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		return sepComp;
	}
	
	public static Composite createSeparator(Composite parent) {
		Composite sepComp = new Composite(parent, SWT.NONE);
		sepComp.setLayout(getCompactGridLayout(1, false));
		sepComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label sep = new Label(sepComp, SWT.SEPARATOR | SWT.HORIZONTAL);
		sep.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return sepComp;
	}
	
	public static Text createText(Composite parent) {
		return createText(parent, TEXT_FIELD_SIZE_HINT);
	}

	public static Text createText(Composite parent, int widthHint) {
		return createText(parent, widthHint, 1);
	}
	
	public static Text createTextSpan(Composite parent, int horizontalSpan) {
		return createText(parent, TEXT_FIELD_SIZE_HINT, horizontalSpan);
	}
	
	public static Text createText(Composite parent, int widthHint, int horizontalSpan) {
		final Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		//TODO - Use validators with cell editors
		//TextCellEditor tce = new TextCellEditor(parent, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = widthHint;
		gd.horizontalSpan = horizontalSpan;
		text.setLayoutData(gd);
		return text;
	}
	
	public static Text createTextPassword(Composite parent) {
		final Text text = new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}
	
	public static Text createTextMultiLine(Composite parent) {
		return createTextMultiLine(parent, 50);
	}
	
	public static Text createTextMultiLine(Composite parent, int heightHint) {
		final Text text = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = heightHint;
		gd.widthHint = TEXT_FIELD_SIZE_HINT;
		text.setLayoutData(gd);
		return text;
	}

	public static Button createPushButton(Composite parent, String text) {
		return createPushButton(parent, text, -1);
	}
	
	public static Button createPushButton(Composite parent, String text, int widthHint) {
		Button pushBtn = new Button(parent, SWT.PUSH | SWT.NONE);
		pushBtn.setText(text);
		GridData gd = new GridData(SWT.LEFT);
		if (widthHint != -1)
			gd.widthHint = widthHint;
		pushBtn.setLayoutData(gd);
		return pushBtn;
	}

	public static Button createGlobalVarPushButton(Composite parent, Text tField) {
		Button button = createImageButton(parent, GvUiUtil.getGvImage(GvUiUtil.ICON_GLOBAL_VAR_OFF), tField.getLineHeight());
		button.setToolTipText("Toggles the display of this field to permit setting values with global variables.");
		return button;
	}

	public static Button createBrowsePushButton(Composite parent, Text tField) {
		Button button = createImageButton(parent, StudioUIPlugin.getDefault().getImage("icons/browse_file_system.gif"), tField.getLineHeight());
		button.setToolTipText("Browse...");
		return button;
	}
		
	public static Button createImageButton(Composite parent, Image image, int height) {
		return createImageButton(parent, image, height, SWT.LEFT);
	}
	
	public static Button createImageButton(Composite parent, Image image, int height, int align) {
		Button pushBtn = new Button(parent, SWT.PUSH);
		pushBtn.setImage(image);
//		int buttonHeight = 9 + (int)((height - 9)*2.5);
		GridData gd = new GridData(align);
//		gd.heightHint = buttonHeight;
//		gd.widthHint = buttonHeight;
		gd.horizontalAlignment = align;
		pushBtn.setLayoutData(gd);
		return pushBtn;	
	}
	
	public static Button createCheckBox(Composite parent, String text) {
		Button chkBox = new Button(parent, SWT.CHECK | SWT.NONE | SWT.NO_BACKGROUND);
		chkBox.setText(text);
		chkBox.setLayoutData(new GridData(SWT.LEFT));
		chkBox.setBackground(parent.getBackground());
		return chkBox;
	}

	public static Button createRadioButton(Composite parent, String text) {
		Button radioBtn = new Button(parent, SWT.RADIO | SWT.NONE | SWT.NO_BACKGROUND);
		radioBtn.setText(text);
		radioBtn.setLayoutData(new GridData(SWT.LEFT));
		radioBtn.setBackground(parent.getBackground());
		return radioBtn;
	}
	
	public static Combo createComboBox(Composite parent, String items[], int style) {
		Combo combo = new Combo(parent, style);
		combo.setItems(items);
		if (items.length > 0)
			combo.setText(items[0]);
		combo.setLayoutData(new GridData(SWT.BEGINNING));
		return combo;
	}
	
	public static Combo createComboBox(Composite parent, String items[]) {
		return (createComboBox(parent, items, SWT.DROP_DOWN | SWT.READ_ONLY));
	}

	public static void setLabelBold(Shell shell, Label label) {
		FontData fds[] = label.getFont().getFontData();
		FontData fd = fds[0];
		fd.setStyle(SWT.BOLD);
		Font font = new Font(shell.getDisplay(), fd);
		label.setFont(font);
		font.dispose();
	}
	
	public static void replaceListItem(List list, String oldName, String newName) {
		String items[] = list.getItems();
		for (int i=0; i<items.length; i++) {
			if (items[i].equals(oldName)) {
				items[i] = newName;
				list.setItems(items);
				list.select(i);
				break;
			}
		}
	}
	
	public static void createSpacer(FormToolkit toolkit, Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, "");
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}
	
	public static void showErrorMessage(final String msg) {
    	showMessageBox(msg, "Error", SWT.ICON_ERROR | SWT.OK);
	}

    public static void showInfoMessage(final String msg) {
    	showMessageBox(msg, "Info", SWT.ICON_INFORMATION | SWT.OK);
    }

    public static void showWarningMessage(final String msg) {
    	showMessageBox(msg, "Warning", SWT.ICON_WARNING | SWT.OK);
    }
    
    private static void showMessageBox(final String msg, final String title, final int icon) {
    	final Shell shell = new Shell(Display.getDefault().getActiveShell());
    	Display.getDefault().asyncExec(new Runnable() {
            public void run() {
		        MessageBox messageBox = new MessageBox(shell, icon);
		        messageBox.setText(title);
		        messageBox.setMessage(msg);
		        messageBox.open();
            }
        });
    }
    
    public static DropTarget setDropTarget(final Text tField) {
    	DropTarget dt = new DropTarget(tField, DND.DROP_MOVE | DND.DROP_COPY);
    	dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
    	dt.addDropListener(new DropTargetAdapter() {
    		public void drop(DropTargetEvent event) {
    			String curText = tField.getText();
    			curText += (String)event.data;
    			tField.setText(curText);
    		}
    	});
    	return dt;
    }
    
    public static boolean validateTextField(final Text tField, boolean isRequired, boolean isInt) {
    	return true;
    }
    
    public static boolean validateTextField_Old(final Text tField, boolean isRequired, boolean isInt) {
    	Color COLOR_VALID = new Color(null, 255, 255, 255);
    	Color COLOR_INVALID = new Color(null, 255, 255, 0);
    	
    	if (tField == null)
    		return false;
    	if (!tField.getEnabled()) {
    		Color COLOR_DISABLED = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
    		tField.setBackground(COLOR_DISABLED);
    		return true;
    	}
    	if (isRequired) {
    		if (tField.getText().trim().equals("")) {
				tField.setBackground(COLOR_INVALID);
				return false;
    		}
    	}
    	if (isInt) {
	    	try {
				int value = new Integer(tField.getText()).intValue();
				if (value < 0) {
					tField.setBackground(COLOR_INVALID);
					return false;
				}
			} catch (NumberFormatException nfe) {
				tField.setBackground(COLOR_INVALID);
				return false;
			}
    	}
    	tField.setBackground(COLOR_VALID);
    	return true;
    }
    

    
    public static Listener getFileBrowseListener(final IProject project, final Composite comp, final String[] filterExt, final Text targetTextField) {
    	Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog = new FileDialog(comp.getShell(), SWT.OPEN);
				fileDialog.setText("Select File");
				fileDialog.setFilterPath(project.getLocation().toOSString());
				if (filterExt != null)
					fileDialog.setFilterExtensions(filterExt);
				String selected = fileDialog.open();
				if (targetTextField != null && selected != null) {
					selected = selected.replace('\\', '/');
					targetTextField.setText(selected);
				}
			}
    	};
    	return listener;
    }
    
    public static Listener getFolderBrowseListener(final IProject project, final Composite comp, final Text targetTextField) {
    	Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				DirectoryDialog dirDialog = new DirectoryDialog(comp.getShell(), SWT.OPEN);
				dirDialog.setText("Select Directory");
				if (project != null) {
					dirDialog.setFilterPath(project.getLocation().toOSString());
				}
				String selected = dirDialog.open();
				if (targetTextField != null && selected != null) {
					selected = selected.replace('\\', '/');
					targetTextField.setText(selected);
				}
			}
    	};
    	return listener;
    }

    public static Listener getFolderResourceSelectionListener(final Composite comp, final IProject project, final Text targetTextField) {
    	Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				SharedResourceSelectionDialog dialog = new SharedResourceSelectionDialog(comp.getShell(), new WorkbenchLabelProvider(), new ProjectFolderContentProvider(), project, false);
				if (dialog.open() == Dialog.OK) {
					Object selObj = dialog.getFirstResult();
					if (selObj != null && selObj instanceof IFolder) {
						String selected = "/" + ((IFolder)selObj).getProjectRelativePath().toString() + "/.folder";
						if (targetTextField != null)
							targetTextField.setText(selected);
					}
				}
			}
    	};
    	return listener;
    }
    
    public static Listener getFileResourceSelectionListener(final Composite comp, final IProject project, final String[] filters, final Text targetTextField) {
    	Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String selected = getFileResourceSelectionListener(comp, project, filters);
				if (selected != null && targetTextField != null){
					targetTextField.setText(selected);
				}
			}
    	};
    	return listener;
    }
    
    public static String getFileResourceSelectionListener(final Composite comp, final IProject project, final String[] filters) {
		SharedResourceSelectionDialog dialog = new SharedResourceSelectionDialog(comp.getShell(), new WorkbenchLabelProvider(), new ProjectFileContentProvider(filters), project, true); 	
		if (dialog.open() == Dialog.OK) {
			Object selObj = dialog.getFirstResult();
			if (selObj != null && selObj instanceof IFile) {
				String selected = "/" + ((IFile)selObj).getProjectRelativePath().toString();
				if(selected.contains(".projlib")){
					selected=selected.split(".projlib")[1];
				}
				return selected;
			}
		}
		return null;
    }

	public static String generateSequenceId(String prefix, ArrayList<String> curList) {
		String genStr = prefix;
        ArrayList<Integer> numbers = getNameIndexArray(curList);
        int enumIndex = 0;
        enumIndex = getIndexofElement(numbers);
		return genStr + "_" + enumIndex ;
	}
	
    private static ArrayList<Integer> getNameIndexArray(ArrayList<String> curList) {
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
    	for (String str: curList) {
    		int index = str.lastIndexOf("_");
    		if (index != -1) {
    			String strLastCount = str.substring(index+1);
                Integer num;
                try {
                    num = new Integer(strLastCount);
                } catch (NumberFormatException nfe) {
                    num = new Integer(0);
                }
                numbers.add(num);
    		}
    	}
        return numbers;
    }

    private static int getIndexofElement(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        int index = 1;
        while (true) {
        	int loc = Collections.binarySearch(numbers, index);
        	if (loc >= 0)
        		index++;
        	else
        		break;
        }
        return index;
    }

	private static int getCount(String str, String substr) {
		int lastIndex = -(substr.length());
		int count =0;

		while (lastIndex != -1) {
			lastIndex = str.indexOf(substr, lastIndex+substr.length());
			if( lastIndex != -1){
				count ++;
			}
		}
		return count;
	}
}
