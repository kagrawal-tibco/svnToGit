package com.tibco.cep.studio.ui.editors.rules.utils;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.rules.actions.OpenNodeAction;
import com.tibco.cep.studio.ui.util.StudioUIUtils;


public class RulesEditorUtils extends StudioUIUtils {
	
	public static final String XSLT_TYPE = "xslt";
	public static final String XPATH_TYPE = "xpath";
	
	public static final String XSLT_PREFIX = "\"xslt://";
	public static final String XPATH_PREFIX = "\"xpath://";

	public static final String CREATE_INSTANCE_FN_QN = "{Instance}createInstance";
	public static final String CREATE_EVENT_FN_QN = "{Event}createEvent";
	public static final String CREATE_INSTANCE_FN = "createInstance(\"xslt://\");";
	public static final String CREATE_EVENT_FN = "createEvent(\"xslt://\");";
	
	public static final String XPATH_FUNCTION_PREFIX = "{XPath}evalAs";
	


	/**
	 * @param sourceViewer
	 * @param contextProvider
	 * @param document
	 * @param sourceType
	 * @param projectName
	 */
	public static void openElement (SourceViewer sourceViewer,
											   IResolutionContextProvider contextProvider,	
			                                   IDocument document,
			                                   int sourceType, 
			                                   String projectName) {
		if(sourceViewer ==  null){
			return;
		}
//		int offset= getCurrentTextOffset(sourceViewer);
//		if (offset == -1)
//			return;
//		IRegion region= new Region(offset, 0);
//		IRegion region = sourceViewer.getVisibleRegion();
//		if(region == null) { 
//			return;
//		}
		
		int offset = sourceViewer.getSelectedRange().x;
		
		RulesASTNodeFinder finder = new RulesASTNodeFinder(offset/*region.getOffset()*/);
		RulesASTNode rulesAST = null;
		rulesAST = RulesParserManager.getTree(document, sourceType, projectName);
		if (rulesAST == null) {
			return;
		}
		rulesAST.accept(finder);
		RulesASTNode node = finder.getFoundNode();
		if(node!=null){
			while (node.getType() != RulesParser.SIMPLE_NAME && node.getParent() != null) {
				node = (RulesASTNode) node.getParent();
			}
			if (node.getType() != RulesParser.SIMPLE_NAME) {
				return;
			}
		}
		OpenNodeAction openAction = new OpenNodeAction(sourceViewer, IRulesSourceTypes.ACTION_SOURCE, 
				node,  projectName, contextProvider);
		openAction.run();
	}
	
	/**
	 * @param fTextViewer
	 * @return
	 */
	private static int getCurrentTextOffset(SourceViewer fTextViewer) {
		try {
			StyledText text= fTextViewer.getTextWidget();
			if (text == null || text.isDisposed())
				return -1;

			Display display= text.getDisplay();
			Point absolutePosition= display.getCursorLocation();
			Point relativePosition= text.toControl(absolutePosition);

			int widgetOffset= text.getOffsetAtLocation(relativePosition);
			Point p= text.getLocationAtOffset(widgetOffset);
			if (p.x > relativePosition.x)
				widgetOffset--;
			
			if (fTextViewer instanceof ITextViewerExtension5) {
				ITextViewerExtension5 extension= (ITextViewerExtension5)fTextViewer;
				return extension.widgetOffset2ModelOffset(widgetOffset);
			}

			return widgetOffset + fTextViewer.getVisibleRegion().getOffset();

		} catch (IllegalArgumentException e) {
			return -1;
		}
	}
}
