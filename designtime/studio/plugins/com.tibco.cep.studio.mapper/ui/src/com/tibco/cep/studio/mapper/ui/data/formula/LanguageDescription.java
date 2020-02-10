package com.tibco.cep.studio.mapper.ui.data.formula;

import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CodeErrorFinder;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxStyle;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TokenMarker;

/**
 * A set of properties describing a language for use in {@link TextBasedFormulaWindow}.
 */
public final class LanguageDescription {
   private TokenMarker m_tokenMarker;
   private SyntaxStyle[] m_syntaxStyles;
   private CodeErrorFinder m_codeErrorFinder;

   public TokenMarker getTokenMarker() {
      return m_tokenMarker;
   }

   public void setTokenMarker(TokenMarker tokenMarker) {
      m_tokenMarker = tokenMarker;
   }

   public void setSyntaxStyles(SyntaxStyle[] overrideStyles) {
      m_syntaxStyles = overrideStyles;
   }

   public SyntaxStyle[] getSyntaxStyles() {
      return m_syntaxStyles;
   }

   public void setCodeErrorFinder(CodeErrorFinder codeErrorFinder) {
      m_codeErrorFinder = codeErrorFinder;
   }

   public CodeErrorFinder getCodeErrorFinder() {
      return m_codeErrorFinder;
   }
}
