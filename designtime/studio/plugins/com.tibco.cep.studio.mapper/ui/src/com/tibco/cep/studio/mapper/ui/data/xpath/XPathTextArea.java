package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.io.Reader;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.CodeCompleteData;
import com.tibco.cep.mapper.xml.xdata.xpath.CodeCompleteResult;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibXPath20Functions;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.XPathSoftDragItem;
import com.tibco.cep.studio.mapper.ui.data.xpath.text.XPathTokenMarker;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CodeErrorFinder;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CodeErrorMessage;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CompletionProposal;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.DefaultCodeErrorMessage;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.DefaultCompletionProposal;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.ErrCheckErrorList;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JSoftDragNDropablePathTextArea;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.TextDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxStyle;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmSequenceType;

public class XPathTextArea extends JSoftDragNDropablePathTextArea {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private boolean mAllowsEmptyString;
   private ExprContext mExprContext;
   private XTypeChecker mTypeChecker;
   private NamespaceContextRegistry m_mNamespaceContextRegistry;
   private XPathTypeReport mLastTypeReport = new XPathTypeReport();
   private Expr mLastExpression;
   private XPathTokenMarker m_tokenMarker = new XPathTokenMarker();

   /**
    * If set, this then this is AttributeValueTemplate xpath mode, not straight xpath mode.
    */
   private boolean m_isAVTMode = false;

   public XPathTextArea(UIAgent uiAgent) {
      setFont(uiAgent.getScriptFont());

      setPreferredSize(new Dimension(30, 30));
      setOpaque(false);


      setTokenMarker(m_tokenMarker);
      getPainter().setStyles(new SyntaxStyle[XPathTokenMarker.AVT_STYLE + 1]);
      SyntaxStyle[] styles = getPainter().getStyles();

      // These are hardcoded XPath styles (see XPathTokenMarker for where these are set.)
      styles[Token.INVALID] = new SyntaxStyle(Color.red, false, true); // errors

      styles[Token.LITERAL1] = new SyntaxStyle(Color.blue, false, false); // strings
      styles[Token.LABEL] = new SyntaxStyle(Color.blue, false, true); // &crlf; escape strings.
      styles[Token.LITERAL2] = new SyntaxStyle(Color.blue, false, false); // numbers
      styles[Token.OPERATOR] = new SyntaxStyle(Color.blue.darker().darker(), false, true); // operators

      styles[Token.KEYWORD1] = new SyntaxStyle(Color.black, false, false); // variable reference.
      styles[Token.KEYWORD2] = new SyntaxStyle(Color.blue, false, true); // function names.
      styles[Token.KEYWORD3] = new SyntaxStyle(Color.blue, false, false); // slash and slash-slash

      styles[Token.COMMENT1] = new SyntaxStyle(Color.green, true, false); // user comments.
      styles[Token.COMMENT2] = new SyntaxStyle(Color.gray, true, false); // marker comment.

      styles[XPathTokenMarker.AVT_STYLE] = new SyntaxStyle(Color.black, false, true); // bold

      getPainter().setStyles(styles);

      TextDragNDropHandler tdd = new MyDropHandler();
      setTextDragNDropHandler(tdd);

      setCodeErrorFinder(new MyErrorFinder());
   }

   public void setExprContext(ExprContext exprContext) {
      mExprContext = exprContext;
      startErrorCheck();
   }

   public ExprContext getExprContext() {
      return mExprContext;
   }

   /**
    * For auto-adding new namespaces/prefix declarations
    */
   public void setNamespaceImporter(NamespaceContextRegistry ni) {
      m_mNamespaceContextRegistry = ni;
   }

   public NamespaceContextRegistry getNamespaceImporter() {
      return m_mNamespaceContextRegistry;
   }

   public void setAVTMode(boolean avtMode) {
      m_isAVTMode = avtMode;
      m_tokenMarker.setAVTMode(avtMode);
   }

   public boolean isAVTMode() {
      return m_isAVTMode;
   }

   /**
    * Sets a type checker to be called after other (intra-expression) checking is complete.
    *
    * @param typeChecker The type checker to use, or null if none.  Defaults to null.
    */
   public void setTypeChecker(XTypeChecker typeChecker) {
      mTypeChecker = typeChecker;
   }

   public XTypeChecker getTypeChecker() {
      return mTypeChecker;
   }

   private Utilities.DropTextInfo getDropTargetRange(int xPos, int yPos, int selStart, int selEnd, boolean isCharacterRef, boolean isSurround) {
      int cpos = xyToOffset(xPos, yPos);
      if (isCharacterRef) {
         // For single characters, the logic is much simpler, just plop it between characters (or on selection)
         Utilities.DropTextInfo dti = new Utilities.DropTextInfo();
         if (cpos <= selEnd && cpos >= selStart) {
            dti.range = new TextRange(selStart, selEnd);
         }
         else {
            dti.range = new TextRange(cpos, cpos);
         }
         return dti;
      }
      if (isSurround) {
         Utilities.DropTextInfo dti = Utilities.getSurroundDropTargetRange(cpos, getText(), selStart, selEnd);
         return dti;
      }
      else {
         return Utilities.getDropTargetRange(cpos, getText(), selStart, selEnd);
      }
   }

   /**
    * For the purposes of error underlining, is nothing legal?
    * (Default is no)
    */
   public void setAllowsEmptyString(boolean allows) {
      mAllowsEmptyString = allows;
   }

   public boolean getAllowsEmptyString() {
      return mAllowsEmptyString;
   }

   public XPathTypeReport getTypeReport() {
      synchronized (this) {
         return mLastTypeReport;
      }
   }

   public Expr getExpression() {
      synchronized (this) {
         return mLastExpression;
      }
   }

   /**
    * Implements the text drag'n'drop methods for the text area.
    */
   private class MyDropHandler implements TextDragNDropHandler {

      public String computeDropString(TextRange dropRange, Object dropItem) {
         if (!(dropItem instanceof XPathSoftDragItem)) {
            return null;
         }
         XPathSoftDragItem sdi = (XPathSoftDragItem) dropItem;
         if (sdi.isCharacterReference) {
            // If we're dropping a character string, the logic is different for figuring out what actually gets dropped.
            return computeCharacterDropString(sdi.path, dropRange);
         }
         String xpath = getText();
         ExprContext context = getExprContext();
         if (context == null) {
            // Ugh. for handling non-xpath things (which this shouldn't do; WCETODO separate this)
            return sdi.path;
         }
         String insertXPath = fixNamespace(sdi);
         int start = dropRange.getStartPosition();
         String before = xpath.substring(0, Math.min(xpath.length(), start));
         SmSequenceType retType = mTypeChecker != null ? mTypeChecker.getBasicType() : null;
         String relXPath = Utilities.makeRelativeXPath(context,
                                                       before,
                                                       insertXPath,
                                                       retType);
         return relXPath;
      }

      public String[] computeSurroundDropStrings(TextRange dropRange, Object dropItem) {
         if (!(dropItem instanceof XPathSoftDragItem)) {
            return null;
         }
         XPathSoftDragItem sdi = (XPathSoftDragItem) dropItem;
         ExprContext context = getExprContext();
         if (context == null) {
            return null;
         }
         String insertXPath = fixNamespace(sdi);
         // extract the first argument: (assumes this is a function)
         int lparen = insertXPath.indexOf("(");
         if (lparen < 0) {
            return null; // shouldn't happen.
         }
         int firstComma = insertXPath.indexOf(",");
         String left = insertXPath.substring(0, lparen + 1);
         String right;
         if (firstComma > lparen) {
            // more than 1 argument, strip out the first argument, but preserve stuff after it.
            right = insertXPath.substring(firstComma);
         }
         else {
            int firstRParen = insertXPath.indexOf(")");
            if (firstRParen < 0) {
               return null; // shouldn't happen.
            }
            right = insertXPath.substring(firstRParen);
         }
         return new String[]{left, right};
      }

      private String fixNamespace(XPathSoftDragItem dropItem) {
         String path = dropItem.path;
         if (dropItem.namespace != null) {
            //ExprContext exprContext = getExprContext();
            int colonIndex = path.indexOf(':');
            if (colonIndex != -1 && path.length() > 2) {
               String suggestedPrefix = path.substring(0, colonIndex);
               String newPrefix = m_mNamespaceContextRegistry.getOrAddPrefixForNamespaceURI(dropItem.namespace, suggestedPrefix);
               if (!suggestedPrefix.equals(newPrefix)) {
                  path = newPrefix + ':' + dropItem.path.substring(colonIndex + 1);
               }
            }
            else {
               String newPrefix = m_mNamespaceContextRegistry.getOrAddPrefixForNamespaceURI(dropItem.namespace);
               path = newPrefix + ":" + path;
            }
         }
         return path;
      }

      private String computeCharacterDropString(String character, TextRange dropRange) {
         // When dropping a literal string (character) need to see if we're already inside a string,
         // if not, put quotes around it:
         String xpath = getText();
         com.tibco.cep.mapper.xml.xdata.xpath.Token[] tokens = Lexer.lex(xpath);
         // Find token we're dropping inside:
         for (int i = 0; i < tokens.length; i++) {
            com.tibco.cep.mapper.xml.xdata.xpath.Token t = tokens[i];
            if (t.getTextRange().containsPosition(dropRange.getStartPosition())) {
               if (t.getTextRange().containsPosition(dropRange.getEndPosition() + 1)) {
                  if (isLiteralStringToken(t)) {
                     // inside quotes already, don't need more quotes:
                     return character;
                  }
               }
            }
         }
         // not inside quotes already, quote it:
         return "\"" + character + "\"";
      }

      private boolean isLiteralStringToken(com.tibco.cep.mapper.xml.xdata.xpath.Token token) {
         int tt = token.getType();
         if (tt == com.tibco.cep.mapper.xml.xdata.xpath.Token.TYPE_LITERAL_STRING) {
            return true;
         }
         if (tt == com.tibco.cep.mapper.xml.xdata.xpath.Token.TYPE_LITERAL_STRING_SINGLE_QUOTE) {
            return true;
         }
         // not a literal string, so no.
         return false;
      }

      public TextRange computeSurroundDropTextRange(Object dragObject, Point mouseAt) {
         if (!(dragObject instanceof XPathSoftDragItem)) {
            return null;
         }
         XPathSoftDragItem sdi = (XPathSoftDragItem) dragObject;
         if (sdi.isCharacterReference) {
            return null;
         }
         if (!sdi.getIsFunction()) // cheap test for a function.
         {
            // if not a function, can't insert around.
            return null;
         }
         Utilities.DropTextInfo dti = getDropTargetRange(mouseAt.x,
                                                         mouseAt.y,
                                                         getSelectionStart(),
                                                         getSelectionEnd(),
                                                         sdi.isCharacterReference,
                                                         true);
         if (dti == null) {
            return null;
         }
         return dti.range;
      }

      public TextRange computeDropTextRange(Object dragObject, Point mouseAt) {
         if (!(dragObject instanceof XPathSoftDragItem)) {
            return null;
         }
         XPathSoftDragItem sdi = (XPathSoftDragItem) dragObject;
         Utilities.DropTextInfo dti = getDropTargetRange(mouseAt.x,
                                                         mouseAt.y,
                                                         getSelectionStart(),
                                                         getSelectionEnd(),
                                                         sdi.isCharacterReference,
                                                         false);
         return dti.range;
      }
   }

   // Refactor as separate class!!!
   private class MyErrorFinder implements CodeErrorFinder {
      public ErrCheckErrorList getErrors(Reader documentReader) throws IOException {
         String xpath = getAsString(documentReader);
         ErrorMessageList syntaxErrors;
         Expr expr;
         if (!m_isAVTMode) {
            Parser ret = new Parser(Lexer.lex(xpath));

            syntaxErrors = ret.getErrorMessageList();
            expr = ret.getExpression();
         }
         else {
            syntaxErrors = ErrorMessageList.EMPTY_LIST; //WCETODO get syntax errors out.
            expr = AVTUtilities.parseAsExpr(xpath);
         }
         XPathTypeReport retTypeReport;
         if (mAllowsEmptyString && xpath.length() == 0) {
            retTypeReport = new XPathTypeReport();
         }
         else {
            // now do semantic check:
            if (mExprContext != null) { // if null, don't do any semantic checking.
               ExprContext context = mExprContext;
               retTypeReport = expr.evalType(context);
               if (syntaxErrors.size() > 0) {
                  // combine the syntax & type errors:
                  retTypeReport = new XPathTypeReport(retTypeReport.xtype, syntaxErrors.addMessages(retTypeReport.errors));
               }
               if (retTypeReport.errors.size() == 0) {
                  // now do a return type check:
                  XTypeChecker checker = mTypeChecker;
                  if (checker != null) // If we have a type checker...
                  {
                     ErrorMessageList typeError = checker.check(retTypeReport.xtype, new TextRange(0, xpath.length()));
                     if (typeError != null && typeError.size() > 0) {
                        // update type report:
                        retTypeReport = new XPathTypeReport(retTypeReport.xtype,
                                                            typeError);
                     }
                  }
               }
            }
            else {
               // no syntax errors, nothing to check.
               retTypeReport = new XPathTypeReport();
            }
         }
         final XPathTypeReport ftypeReport = retTypeReport;
         synchronized (XPathTextArea.this) {
            mLastExpression = expr;
            mLastTypeReport = ftypeReport;
         }

         // Copy the errors:
         ErrCheckErrorList el = new ErrCheckErrorList();
         ErrorMessage[] em = ftypeReport.errors.getMessages();
         for (int i = 0; i < em.length; i++) {
            final ErrorMessage msg = em[i];
            int t = msg.getType();
            if (t == ErrorMessage.TYPE_MARKER || t == ErrorMessage.TYPE_AUTOCAST) {
               // don't show these.
               continue;
            }
            int uitype = ErrorMessage.TYPE_ERROR;
            switch (t) {
               case ErrorMessage.TYPE_ERROR:
                  uitype = CodeErrorMessage.TYPE_ERROR;
                  break;
               case ErrorMessage.TYPE_UNCHECKED:
                  uitype = CodeErrorMessage.TYPE_UNCHECKED;
                  break;
               case ErrorMessage.TYPE_WARNING:
                  uitype = CodeErrorMessage.TYPE_WARNING;
                  break;
            }
            int code = msg.getCode();
            CompletionProposal[] proposals;
            if (code == ErrorMessage.CODE_MISSING_PREFIX && getExprContext() != null) {
               DefaultCompletionProposal proposal = new DefaultCompletionProposal("Add namespace prefix  (Alt+Ent)", msg.getTextRange(), "") {
                  public void apply(Document document) {
                     String ns = msg.getSupplementalText();
                     String suggestedPrefix = msg.getSupplementalText2();
                     String pfx = m_mNamespaceContextRegistry.getOrAddPrefixForNamespaceURI(ns, suggestedPrefix);
                     try {
                        String original = document.getText(mTextRange.getStartPosition(), mTextRange.getLength());
                        String ln;
                        if (original.indexOf("(") >= 0) {
                           ln = original;
                        }
                        else {
                           QName oqname = new QName(original);
                           ln = oqname.getLocalName();
                        }
                        String replacementText = pfx + ":" + ln;
                        document.remove(mTextRange.getStartPosition(), mTextRange.getLength());
                        document.insertString(mTextRange.getStartPosition(), replacementText, document.getDefaultRootElement().getAttributes());
                     }
                     catch (BadLocationException ble) {
                        // ignore this.
                     }
                  }
               };
               proposals = new CompletionProposal[]{proposal};
            }
            else {
               proposals = new CompletionProposal[0];
            }

            DefaultCodeErrorMessage uimsg = new DefaultCodeErrorMessage(msg.getMessage(),
                                                                        msg.getTextRange(),
                                                                        uitype,
                                                                        proposals);
            el.addMessage(uimsg);
         }
         return el;
      }

      public CompletionProposal[] computeCompletionProposals(Reader documentReader, int offset) throws IOException {
         String xpath = getAsString(documentReader);
         String xpathUpToOffset = xpath.substring(0, offset);
         com.tibco.cep.mapper.xml.xdata.xpath.Token[] tokens = Lexer.lexCodeComplete(xpathUpToOffset);
         com.tibco.cep.mapper.xml.xdata.xpath.Token[] origTokens = Lexer.lexCodeComplete(xpath);
         final Parser ret = new Parser(tokens);
         if (mExprContext != null) { // if null, don't do any type checking.
            Expr expr = ret.getExpression();
            ExprContext context = mExprContext;
            EvalTypeInfo eti = new EvalTypeInfo();
            expr.evalType(context, eti);
            CodeCompleteData ccd = eti.getCodeComplete();
            if (ccd == null) {
               return new CompletionProposal[0];
            }
            CodeCompleteResult[] strs = ccd.getPossibleStrings();
            CompletionProposal[] cp = new CompletionProposal[strs.length];
            TextRange range = null;
            for (int i = 0; i < origTokens.length; i++) {
               if (origTokens[i].getTextRange().containsPosition(offset + 1)) { // +1 -> otherwise / doesn't work.
                  int tt = origTokens[i].getType();
                  boolean replace = false;
                  switch (tt) {
                     case com.tibco.cep.mapper.xml.xdata.xpath.Token.TYPE_NAME_TEST: // fall through.
                     case com.tibco.cep.mapper.xml.xdata.xpath.Token.TYPE_CODE_COMPLETE: // fall through.
                     case com.tibco.cep.mapper.xml.xdata.xpath.Token.TYPE_FUNCTION_NAME:
                        replace = true;
                        break;
                  }
                  if (replace) {
                     if (i > 0 && origTokens[i - 1].getType() == com.tibco.cep.mapper.xml.xdata.xpath.Token.TYPE_ATTRIBUTE) {
                        range = new TextRange(origTokens[i - 1].getTextRange().getStartPosition(), origTokens[i].getTextRange().getEndPosition());
                     }
                     else {
                        range = origTokens[i].getTextRange();
                     }
                     if (range.getEndPosition() > xpath.length()) {
                        // hacky fix, the last token sometimes seems to have a 2 characters extra length on it...
                        range = new TextRange(range.getStartPosition(), xpath.length());
                     }
                  }
                  break;
               }
            }
            if (range == null) {
               // won't overwrite any text, just one spot:
               range = new TextRange(offset, offset);
            }
            for (int i = 0; i < strs.length; i++) {
               final CodeCompleteResult ccr = strs[i];
               final String dropStr = ccr.getDropString();
               cp[i] = new DefaultCompletionProposal(ccr.getDisplayString(), range, dropStr) {
                  public void apply(Document document) {
                     if (ccr.getOptionalNamespace().length() > 0) {
                        // if it's one of the two built-in namespaces, hide the prefix in the editor:
                        if (!ccr.getOptionalNamespace().equals(TibExtFunctions.NAMESPACE) && !ccr.getOptionalNamespace().equals(TibXPath20Functions.NAMESPACE)) {
                           // insert the prefix
                           String pfx = m_mNamespaceContextRegistry.getOrAddPrefixForNamespaceURI(ccr.getOptionalNamespace());
                           String nns = pfx + ":" + dropStr;
                           super.performReplacement(document, mTextRange, nns);
                        }
                        else {
                           super.apply(document);
                        }
                     }
                     else {
                        super.apply(document);
                     }
                     if (dropStr.endsWith("()")) {
                        // place caret back one.
                        try {
                           setCaretPosition(getCaretPosition() - 1);
                        }
                        catch (Exception e) {
                           // ignore.
                        }
                     }
                  }
               };
            }
            return cp;
         }
         return new CompletionProposal[0];
      }

   }

   private static String getAsString(Reader reader) throws IOException {
      StringBuffer sb = new StringBuffer();
      char[] t = new char[1024];
      for (; ;) {
         int len = reader.read(t);
         if (len <= 0) {
            break;
         }
         sb.append(t, 0, len);
      }
      return sb.toString();
   }
}
