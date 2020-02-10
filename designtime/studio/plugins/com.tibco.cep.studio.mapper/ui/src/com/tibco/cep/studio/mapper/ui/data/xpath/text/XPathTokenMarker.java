package com.tibco.cep.studio.mapper.ui.data.xpath.text;

import javax.swing.text.Segment;

import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.CharStream;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TokenMarker;

/**
 * A JEdit TokenMarker for XPath.
 */
public class XPathTokenMarker extends TokenMarker {
   private boolean m_isAVTMode = false;

   public static final int AVT_STYLE = 25; // 25 -> well clear of the built-in ones.

   public boolean supportsMultilineTokens() {
      return false;
   }

   /**
    * Sets if this is in attribute value template (xpath) mode (default is off).
    *
    * @param avtMode
    */
   public void setAVTMode(boolean avtMode) {
      m_isAVTMode = avtMode;
   }

   public boolean isAVTMode() {
      return m_isAVTMode;
   }

   protected byte markTokensImpl(byte prevTokenFlag, Segment line, int lineIndex) {
      String txt = new String(line.array, line.offset, line.count);
      Token[] t;
      if (m_isAVTMode) {
         t = AVTUtilities.lexAsExpr(txt);
      }
      else {
         t = Lexer.lex(new CharStream(txt), prevTokenFlag == 1 ? true : false);
      }

      byte nullType = com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.NULL;
      for (int i = 0; i < t.length; i++) {
         Token tok = t[i];
         if (i > 0) {
            Token prevTok = t[i - 1];
            if (prevTok.getTextRange().getEndPosition() < tok.getTextRange().getStartPosition()) {
               // space:
               addToken(tok.getTextRange().getStartPosition() - prevTok.getTextRange().getEndPosition(), nullType);
            }
         }
         else {
            // leading space:
            addToken(tok.getTextRange().getStartPosition(), nullType);
         }
         byte jeditTokenType = getJEditType(tok, i == t.length - 1);
         if (tok.getType() == Token.TYPE_LITERAL_STRING || tok.getType() == Token.TYPE_LITERAL_STRING_SINGLE_QUOTE) {
            markLiteralString(tok.getValue(), jeditTokenType, true); // true-> has terminating quote.
         }
         else {
            if (jeditTokenType == com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LITERAL1) {
               // Only happens for unterminated literal strings.
               String lt = tok.getLiteralText().substring(1); // literal text contains the leading ', method below assumes that's gone.
               markLiteralString(lt, jeditTokenType, false); // false -> does not have terminating quote.
            }
            else {
               addToken(tok.getTextRange().getLength(), jeditTokenType);
            }
         }
      }
      // check for trailing space:
      if (t.length == 0) {
         if (txt.length() > 0) {
            addToken(txt.length(), nullType);
         }
      }
      else {
         int endOfLastToken = t[t.length - 1].getTextRange().getEndPosition();
         int dif = txt.length() - endOfLastToken;
         if (dif > 0) {
            addToken(dif, nullType);
         }
      }
      if (t.length == 0) {
         return prevTokenFlag;
      }
      else {
         return Lexer.isTokenIndicatingNextOperator(t[t.length - 1]) ? (byte) 1 : (byte) 0;
      }
   }

   /**
    * Allow for literal strings to contain character escapes (i.e. &amp;), etc. & mark those accordingly.
    */
   private void markLiteralString(String str, byte jeditTokenType, boolean hasTerminatingQuote) {
      // possibly break into more tokens.
      int charsSoFar = 1; // (1 counts the quote itself)
      for (int xi = 0; xi < str.length(); xi++) {
         char c = str.charAt(xi);
         if (c == '&') {
            if (charsSoFar > 0) {
               addToken(charsSoFar, jeditTokenType);
            }
            boolean foundEnd = false;
            for (int ss = xi; ss < str.length(); ss++) {
               if (str.charAt(ss) == ';') {
                  // Use label for lack of a better code:
                  int len = 1 + ss - xi;
                  addToken(len, com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LABEL);
                  xi = ss; // +1 added later.
                  charsSoFar = 0;
                  foundEnd = true;
                  break;
               }
            }
            if (!foundEnd) {
               int len = str.length() - xi;
               addToken(len, com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LABEL);
               charsSoFar = 0;
               // end of string.
               break;
            }
         }
         else {
            charsSoFar++;
         }
      }
      int remaining = charsSoFar + (hasTerminatingQuote ? 1 : 0);  // +1 => ending quote.
      if (remaining > 0) {
         addToken(remaining, jeditTokenType);
      }
   }

   private byte getJEditType(Token n, boolean isEndingToken) {
      int tokenType = n.getType();
      switch (tokenType) {
         case Token.TYPE_AVT_FRAGMENT:
            return AVT_STYLE; // use Keyword3 for AVT.
         case Token.TYPE_LITERAL_STRING:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LITERAL1;
         case Token.TYPE_LITERAL_STRING_SINGLE_QUOTE:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LITERAL1;
         case Token.TYPE_NUMBER:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LITERAL2;
         case Token.TYPE_VARIABLE_REFERENCE:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.KEYWORD1;
         case Token.TYPE_FUNCTION_NAME:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.KEYWORD2;
         case Token.TYPE_ERROR:
            {
               if (isEndingToken) {
                  // final token, error is probably just because user is still typing...
                  if (Lexer.UNTERMINATED_LITERAL_ERROR.equals(n.getValue())) {
                     // unterminated literal, use normal literal string color: (LITERAL1)
                     return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.LITERAL1;
                  }
                  if (Lexer.UNTERMINATED_COMMENT_ERROR.equals(n.getValue())) {
                     // unterminated comment, use normal comment string color: (COMMENT1)
                     return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.COMMENT1;
                  }
                  // let it go since the user is probably still typing.
                  return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.NULL;
               }
               return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.INVALID;
            }
         case Token.TYPE_COMMENT:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.COMMENT1;
         case Token.TYPE_USER_COMMENT:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.COMMENT1;
         case Token.TYPE_MARKER_STRING:
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.COMMENT2;
      }
      if (n.isOperator() || n.isAxis()) {
         if (n.getType() == Token.TYPE_SLASH || n.getType() == Token.TYPE_SLASH_SLASH) {
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.KEYWORD3;
         }
         else {
            return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.OPERATOR;
         }
      }
      return com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token.NULL;
   }
}
