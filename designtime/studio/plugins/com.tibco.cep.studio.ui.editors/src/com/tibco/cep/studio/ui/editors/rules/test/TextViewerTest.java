package com.tibco.cep.studio.ui.editors.rules.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.ui.editors.RulesFormSourceViewerConfiguration;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.text.RulesAnnotationModel;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPartitionScanner;

public class TextViewerTest {
	private final static String OVERVIEW_RULER= AbstractDecoratedTextEditorPreferenceConstants.EDITOR_OVERVIEW_RULER;
	/**
	 * Preference key for highlighting current line.
	 */
	private final static String CURRENT_LINE= AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE;
	/**
	 * Preference key for highlight color of current line.
	 */
	private final static String CURRENT_LINE_COLOR= AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR;
	/**
	 * Preference key for showing print margin ruler.
	 */
	private final static String PRINT_MARGIN= AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN;
	/**
	 * Preference key for print margin ruler color.
	 */
	private final static String PRINT_MARGIN_COLOR= AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLOR;
	private final static String PRINT_MARGIN_COLUMN= AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLUMN;

	private static final String editorFont = "1|Courier New|24.0|0|WINDOWS|1|36|19|0|0|400|0|0|0|0|3|2|1|49|Courier New";
	
	public static void main(String[] args) {
		
		Display display = new Display();
		final Shell shell = new Shell(display, SWT.WRAP | SWT.RESIZE);
		shell.setLayout(new GridLayout());
		shell.setLayoutData(new GridData(GridData.FILL_BOTH));
		shell.setText("Rules Editor");
		
		Composite composite = new Composite(shell, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = gridLayout.marginHeight = 0;

		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		SashForm form = new SashForm(composite, SWT.VERTICAL | SWT.SHADOW_ETCHED_IN);
		form.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		{
			Composite sv1comp = new Composite(form, SWT.BORDER);
			sv1comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			GridLayout compLayout = new GridLayout();
			compLayout.marginWidth = compLayout.marginHeight = 0;
			sv1comp.setLayout(compLayout);
			
			Label label = new Label(sv1comp, SWT.NULL);
			label.setText("Declarations");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			label.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			label.setFont(new Font(display, "Courier New", 10, SWT.NULL));

			Table table = new Table(sv1comp, SWT.BORDER | SWT.FULL_SELECTION);
			table.setLayout(new GridLayout());
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
			table.setFont(new Font(display, "Courier New", 10, SWT.NULL));
			
			TableColumn termColumn = new TableColumn(table, SWT.NULL);
			termColumn.setText("Term");
			termColumn.setWidth(300);

			TableColumn aliasColumn = new TableColumn(table, SWT.NULL);
			aliasColumn.setText("Alias");
			aliasColumn.setWidth(300);
			
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			
			TableItem tableItem= new TableItem(table, SWT.NONE);
			tableItem.setText(0, "/CEP/Concepts/MyConcept");
			tableItem.setText(1, "myconcept");
			
			TableItem tableItem2= new TableItem(table, SWT.NONE);
			tableItem2.setText(0, "/CEP/Concepts/HisConcept");
			tableItem2.setText(1, "his");
			
		}
		
		{
			Composite sv1comp = new Composite(form, SWT.BORDER);
			sv1comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			GridLayout compLayout = new GridLayout();
			compLayout.marginWidth = compLayout.marginHeight = 0;
			sv1comp.setLayout(compLayout);
			
			Label label = new Label(sv1comp, SWT.NULL);
			label.setText("Conditions");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			label.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			label.setFont(new Font(display, "Courier New", 10, SWT.NULL));
			
			OverviewRuler ruler = new OverviewRuler(new DefaultMarkerAnnotationAccess(), 10, EntitySharedTextColors.getInstance());
			SourceViewer viewer = new ProjectionViewer(sv1comp, new VerticalRuler(10), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

			viewer.getTextWidget().setFont(new Font(display, "Courier New", 10, SWT.NULL));
			
			viewer.configure(new RulesFormSourceViewerConfiguration(null, IRulesSourceTypes.CONDITION_SOURCE, null));
			viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
			viewer.setOverviewRulerAnnotationHover(new DefaultAnnotationHover());
			Document document = new Document(getInput("input1.txt"));
			IDocumentPartitioner partitioner = new FastPartitioner(new RulesPartitionScanner(), 
					new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.STRING, RulesPartitionScanner.HEADER, RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
			viewer.setDocument(document, new RulesAnnotationModel());
		}

		{
			Composite sv2comp = new Composite(form, SWT.BORDER);
			sv2comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			GridLayout compLayout = new GridLayout();
			compLayout.marginWidth = compLayout.marginHeight = 0;
			sv2comp.setLayout(compLayout);

			Label label = new Label(sv2comp, SWT.NULL);
			label.setText("Actions");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			label.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
			label.setFont(new Font(display, "Courier New", 10, SWT.NULL));

			DefaultMarkerAnnotationAccess access = new DefaultMarkerAnnotationAccess();
			EntitySharedTextColors sharedColors = EntitySharedTextColors.getInstance();
			OverviewRuler ruler = new OverviewRuler(access, 10, sharedColors);
			SourceViewer viewer = new SourceViewer(sv2comp, new VerticalRuler(10), ruler, true, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			viewer.getTextWidget().setFont(new Font(display, "Courier New", 10, SWT.NULL));
			viewer.configure(new RulesFormSourceViewerConfiguration(null, IRulesSourceTypes.ACTION_SOURCE, null));
			
			setupSourceViewerDecorationSupport(viewer, ruler, access, sharedColors);
			
			viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
			Document document = new Document(getInput("input2.txt"));
			IDocumentPartitioner partitioner = new FastPartitioner(new RulesPartitionScanner(), 
					new String[] { IDocument.DEFAULT_CONTENT_TYPE, RulesPartitionScanner.HEADER, RulesPartitionScanner.STRING, RulesPartitionScanner.COMMENT, RulesPartitionScanner.XML_TAG });
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
			viewer.setDocument(document, new RulesAnnotationModel());
			
			addAnnotations(viewer);
		}
		
		shell.setSize(700, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	protected static void setupSourceViewerDecorationSupport(ISourceViewer viewer, OverviewRuler ruler, DefaultMarkerAnnotationAccess access, EntitySharedTextColors sharedColors) {
		SourceViewerDecorationSupport fSourceViewerDecorationSupport= new SourceViewerDecorationSupport(viewer, ruler, access, sharedColors);
		configureSourceViewerDecorationSupport(fSourceViewerDecorationSupport);
	}

	
	private static void configureSourceViewerDecorationSupport(
			SourceViewerDecorationSupport support) {
//		Iterator e= fAnnotationPreferences.getAnnotationPreferences().iterator();
//		while (e.hasNext())
//			support.setAnnotationPreference((AnnotationPreference) e.next());

		support.setCursorLinePainterPreferenceKeys(CURRENT_LINE, CURRENT_LINE_COLOR);
		support.setMarginPainterPreferenceKeys(PRINT_MARGIN, PRINT_MARGIN_COLOR, PRINT_MARGIN_COLUMN);
//		support.setSymbolicFontName(getFontPropertyPreferenceKey());
		
	}

	private static void addAnnotations(SourceViewer viewer) {
		IAnnotationModel annotationModel = viewer.getAnnotationModel();
		Annotation ann = new Annotation("com.tibco.cep.editors.ui.problem", false, "You f'ed up");
		annotationModel.addAnnotation(ann, new Position(0, 6));
	}

	private static String getInput(String fileName) {
		try {
//			URL entry = EditorsUIPlugin.getDefault().getBundle().getEntry(fileName);
//			InputStream openStream = entry.openStream();//new FileInputStream(new File("C:\\dev\\be\\leo\\tui\\plugins\\com.tibco.cep.editors.ui\\input1.txt"));
			InputStream openStream = new FileInputStream(new File("C:\\dev\\be\\leo\\studio\\plugins\\com.tibco.cep.studio.ui.editors\\"+fileName));
			byte[] arr = new byte[openStream.available()];
			openStream.read(arr);
			return new String(arr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
