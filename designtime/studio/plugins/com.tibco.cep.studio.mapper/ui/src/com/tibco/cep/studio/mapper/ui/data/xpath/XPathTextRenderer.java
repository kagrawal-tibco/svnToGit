package com.tibco.cep.studio.mapper.ui.data.xpath;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.CharStream;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.PaintUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTextRenderer;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea;

/**
 * Slated for removal, still needed indirectly in a couple places. A utility class for different types of components.
 */
public class XPathTextRenderer {
   private Color mErrorUnderlineColor = Color.pink;
   private Color mWarningUnderlineColor = Color.orange;
   private Color mLiteralStringClr = Color.blue;
   private Color mCommentStringClr = Color.green;
   private Color mOperatorColor;
   private Font mDotDotFont;
   private FontHolder mFonts;
   private final UIAgent uiAgent;
   private boolean m_avtMode;

   public XPathTextRenderer(UIAgent uiAgent) {
      this.uiAgent = uiAgent;
   }

   public final FontHolder getFontHolder() {
      return mFonts;
   }

   public Font getFont() {
      return mFonts.getNormalFont();
   }

   /**
    * If set, renders as an AVT, not a pure XPath.<br>
    * Default is <code>false</code>.
    *
    * @param avtMode
    */
   public void setAVTMode(boolean avtMode) {
      m_avtMode = avtMode;
   }

   public boolean getAVTMode() {
      return m_avtMode;
   }

   protected final Font getNormalFont() {
      return mFonts.getNormalFont();
   }

   protected final Font getBoldFont() {
      return mFonts.getBoldFont();
   }

   protected final Font getItalicFont() {
      return mFonts.getItalicFont();
   }

   public int getCharWidth() {
      return mFonts.getCharWidth();
   }

   public void initialize(Graphics g) {
      if (mFonts == null) {
         Font baseFont = uiAgent.getScriptFont();
         mFonts = new FontHolder(g.getFontMetrics(baseFont), getFontRenderContext(g), baseFont);
         mDotDotFont = uiAgent.getAppFont();
         mOperatorColor = Color.blue.darker().darker();
      }
   }

   private static FontRenderContext getFontRenderContext(Graphics g) {
      if (g instanceof Graphics2D) {
         return ((Graphics2D) g).getFontRenderContext();
      }
      throw new RuntimeException("Graphics should always be a Graphics2D!");  // LAMb: this is lame!
   }

   /**
    * Paints a syntax colored line, including block comments.
    *
    * @param y        The base-line y for the text.
    * @param selStart The start selection index RELATIVE to the start of this line.
    * @param selEnd   The end selection index RELATIVE to the start of this line.
    */
   public void drawSyntaxLine(Graphics g, char[] txt, int from, int len, int x, int y, int width, int selStart, int selEnd, boolean endsWithNewLineMarker) {
      initialize(g);
      txt = tabsToSpaces(txt, from, len);
      drawSyntaxLine1(g, txt, from, len, x, y, width, /*commentTracker,*/selStart, selEnd, endsWithNewLineMarker);
   }

   public int getFontBaselineYOffset(Graphics g) {
      initialize(g);
      g.setFont(getFont());
      FontMetrics fm = g.getFontMetrics();
      return BasicTextRenderer.computeInlineTextYBaseOffset(fm);
   }

   /**
    * Turns tabs into spaces in the char array.
    */
   private static char[] tabsToSpaces(char[] txt, int from, int len) {
      //@ ugh.
      // look for tabs, replace them: (not real efficient for tabs, but so what)
      int end = from + len;

      // DSS: Check the end
      if (end > txt.length) {
         end = txt.length;
      }

      StringBuffer sb = null;
      int lastIndex = from;
      for (int i = from; i < end; i++) {
         if (txt[i] == '\t') {
            if (sb == null) {
               sb = new StringBuffer(len + 20);
            }
            sb.append(txt, lastIndex, i - lastIndex);
            int ts = 3;//@unhardcode... get from view? getView().getTabSize();
            int l = sb.length();
            int tabChars = ts > 0 ? (ts - (l % ts)) : 0;
            if (tabChars == 0) {
               tabChars = ts;
            }
            for (int tt = 0; tt < tabChars; tt++) {
               sb.append(' ');
            }
            lastIndex = i + 1;
         }
      }
      if (sb != null) {
         // we had tabs,
         sb.append(txt, lastIndex, end - lastIndex);
         // get retabbed:
         txt = sb.toString().toCharArray();
         from = 0;
         len = txt.length;
      }
      return txt;
   }

   /**
    * This is auto-called by drawSyntaxLine, but multi-line areas sometimes need
    * to paint this separately.
    *
    * @param lineErrorList The error list for this line where the ranges have been normalized for this line.
    */
   public void drawErrorsLine(Graphics g, char[] lineTextChars, ErrorMessageList lineErrorList, int x, int y) {
      if (lineErrorList == null || lineErrorList.getCount() == 0) {
         return;
      }
      // Draw underlines:
      initialize(g);
      int baseCharWidth = mFonts.getFontMetrics().charWidth('w');
      ErrorMessage[] mErrors = lineErrorList.getMessages();
      for (int i = 0; i < mErrors.length; i++) {
         ErrorMessage error = mErrors[i];
         TextRange loc = error.getTextRange();
         int s = loc.getStartPosition();
         int e = loc.getEndPosition();
         if (error.getType() == ErrorMessage.TYPE_ERROR) {
            g.setColor(mErrorUnderlineColor);
         }
         else {
            if (error.getType() == ErrorMessage.TYPE_WARNING) {
               g.setColor(mWarningUnderlineColor);
            }
            else {
               continue;
               // otherwise, don't show, it's just annoying.
            }
         }
         if (e >= s) {
            int waveHeight = 2;
            int yat = y + waveHeight; // waveHeight-> a few pixels space.
            int xleft;
            int xright;
            if (e - s <= 1) {
               // make 0,1 characters errors bigger:
               xleft = x + calculateCharsPixelSize(lineTextChars, 0, s) - baseCharWidth / 2;
               xright = x + calculateCharsPixelSize(lineTextChars, 0, s) + (baseCharWidth / 2) + 1;
            }
            else {
               xleft = x + calculateCharsPixelSize(lineTextChars, 0, s);
               xright = x + calculateCharsPixelSize(lineTextChars, 0, e);
            }
            PaintUtils.drawWavyLine(g, baseCharWidth / 2, waveHeight, xleft, xright, yat);
         }
         else {
            throw new RuntimeException("Error not in range?" + s + " and " + e);
         }
      }
   }

   private void drawSyntaxLine1(Graphics g, char[] txt, int from, int len, int x, int y, int width, int selStart, int selEnd, boolean endsWithNewLineMarker) {
      g.setFont(getNormalFont());

      int scanFrom = from;
      int scanX = x;

      // reset scanner:
      Token[] tokens;
      if (!m_avtMode) {
         CharStream charStream = new CharStream(new String(txt, from, len));
         tokens = Lexer.lex(charStream);
      }
      else {
         String s = new String(txt, from, len);
         tokens = AVTUtilities.lexAsExpr(s);
      }

      int strPixels = calculateCharsPixelSize(g, txt, from, len);
      if (endsWithNewLineMarker) {
         strPixels += 4; // give a little space for it.
      }
      int paintTo;
      if (strPixels > width) { // <= seems to work better, < should be correct...
         int availCharLen = width / (strPixels / len); // approximation
         paintTo = from + availCharLen - 3;
      }
      else {
         paintTo = from + len;
      }

      for (int i = 0; i < tokens.length; i++) {
         Token n = tokens[i];
         boolean isEndingToken = (i == tokens.length - 1);// && n.getTextRange().getEndPosition()==charStream.getEndPosition();
         int at = n.getTextRange().getStartPosition();

         // find the end of this token by looking at the start of the next one:
         int endAt;

         if ((i + 1) < tokens.length) {
            endAt = tokens[i + 1].getTextRange().getStartPosition();
         }
         else {
            endAt = len - from;
         }
         Color c;
         Font f = getNormalFont();
         switch (n.getType()) {
            case Token.TYPE_LITERAL_STRING:
            case Token.TYPE_LITERAL_STRING_SINGLE_QUOTE:
               c = mLiteralStringClr;
               break;
            case Token.TYPE_NUMBER:
               c = Color.blue;
               break;
            case Token.TYPE_FUNCTION_NAME:
               c = Color.blue;
               f = getBoldFont();
               break;
            case Token.TYPE_VARIABLE_REFERENCE:
               c = Color.black;
               break;
            case Token.TYPE_COMMENT:
               c = mCommentStringClr;
               f = getItalicFont();
               break;
            case Token.TYPE_ERROR:
               {
                  if (isEndingToken) {
                     // final token, error is probably just because user is still typing...
                     int firstChar = txt[scanFrom + at];
                     if (firstChar == '\'' || firstChar == '"') {
                        // unterminated literal, use literal color:
                        c = mLiteralStringClr;
                     }
                     else {
                        if (len > 3 && txt[scanFrom + at] == '{' && txt[scanFrom + at + 1] == '-') {
                           c = mCommentStringClr;
                           f = getItalicFont();
                        }
                        else {
                           c = Color.black;
                        }
                     }
                  }
                  else {
                     c = Color.red;
                  }
                  break;
               }
            case Token.TYPE_NAME_TEST:
               c = Color.black;
               break;
            case Token.TYPE_USER_COMMENT:
               c = Color.green;
               f = getItalicFont();
               break;
            case Token.TYPE_MARKER_STRING:
               c = Color.gray;
               f = getItalicFont();
               break;
            case Token.TYPE_AVT_FRAGMENT:
               c = Color.black;
               f = getBoldFont();
               break;
            default:
               {
                  if (n.isOperator() || n.isAxis()) {
                     c = mOperatorColor;
                     if (n.getType() == Token.TYPE_SLASH || n.getType() == Token.TYPE_SLASH_SLASH) {
                        c = Color.blue;
                     }
                     else {
                        f = getBoldFont();
                     }
                  }
                  else {
                     // otherwise.
                     c = Color.black;
                  }
               }
         }
         // use the syntax hilite color
         g.setColor(c);
         g.setFont(f);
         int relSelStart = selStart - at;
         int relSelEnd = selEnd - at;

         initialize(g);
         int offset;
         if ((at > 0) && (i > 0)) {
            // yikes, not very efficient here...
            offset = calculateTokenPixelSize(txt, tokens, scanFrom, at, 0, i - 1);
         }
         else {
            offset = n.getTextRange().getStartPosition() * g.getFontMetrics().charWidth(' ');
         }
         if (!drawSelChars(g, txt, scanFrom + at, endAt - at, scanX + offset, y, paintTo, relSelStart, relSelEnd)) {
            break;
         }
         at = endAt;
      }

      if ((paintTo != from + len) && (paintTo >= from)) {
         int offset = calculateCharsPixelSize(g, txt, from, paintTo - from);
         drawDotDots(g, x + offset, y, endsWithNewLineMarker);
      }
      else {
         if (endsWithNewLineMarker) {
            g.setColor(Color.blue);
            int sz = calculateTokenPixelSize(txt, tokens, from, paintTo - from, 0, tokens.length - 1);
            JExtendedEditTextArea.paintEOLMarker(g, x + sz, y);
         }
      }
   }

   private void drawDotDots(Graphics g, int x, int y, boolean endsWithNewLineMarker) {
      g.setFont(mDotDotFont);
      g.setColor(Color.black);
      String ddd = "...";
      g.drawString(ddd, x, y);
      int offset = g.getFontMetrics().charsWidth(ddd.toCharArray(), 0, ddd.length());
      if (endsWithNewLineMarker) {
         g.setColor(Color.blue);
         JExtendedEditTextArea.paintEOLMarker(g, x + offset, y);
      }
   }

   private boolean drawSelChars(Graphics g, char[] txt, int from, int length, int x, int y, int paintTo, int selStart, int selEnd) {
      if (paintTo < from + length) {
         if (from >= paintTo) {
            return false;
         }
         // draw as many as we can.
         drawChars(g, txt, from, paintTo - from, x, y); // ok
         return false;
      }
      boolean isLiteralString = true;
      if (isLiteralString) { // underline the entity refs inside strings.
         boolean inRef = false;
         int lastFrom = 0;
         Font startingFont = g.getFont();
         for (int xi = 0; xi < length; xi++) {
            int p = xi + from;
            if (txt[p] == '&') {
               if (!inRef) {
                  g.setFont(startingFont);
//                        drawChars(g,txt,lastFrom+from,xi-lastFrom,x+lastFrom*mCharWidth,y);
                  drawCharsWithOffset(g, txt, from, lastFrom, xi - lastFrom, x, y);
                  lastFrom = xi;
               }
               inRef = true;
            }
            if (txt[p] == ';') {
               if (inRef) {
                  g.setFont(getBoldFont());
//                        drawChars(g,txt,lastFrom+from,1+xi-lastFrom,x+lastFrom*mCharWidth,y);
                  drawCharsWithOffset(g, txt, from, lastFrom, 1 + xi - lastFrom, x, y);
                  lastFrom = xi + 1;
               }
               inRef = false;
            }
         }
         if (inRef) {
            g.setFont(getBoldFont());
         }
         else {
            g.setFont(startingFont);
         }
//            drawChars(g,txt,lastFrom+from,length-lastFrom,x+lastFrom*mCharWidth,y);
         drawCharsWithOffset(g, txt, from, lastFrom, length - lastFrom, x, y);
      }
      else {
         // First paint in whatever color:
         drawChars(g, txt, from, length, x, y); //ok
      }

      // Then overpaint.
      if (selStart > selEnd) {
         // reversed, swap 'em:
         int t = selStart;
         selStart = selEnd;
         selEnd = t;
      }
      if (selStart < length && selEnd >= 0 && selStart != selEnd) {
         // limit:
         selStart = Math.max(selStart, 0);
         selEnd = Math.min(length, selEnd);
         // overdraw selection text in white always:
         g.setColor(Color.white);
         //int startSelPos = from+selStart;
         int selLength = selEnd - selStart;
//            drawChars(g,txt,startSelPos,selLength,x+selStart*mCharWidth,y);
         drawCharsWithOffset(g, txt, from, selStart, selLength, x, y);
      }
      return true;
   }

   public void setErrorUnderlineColor(Color c) {
      mErrorUnderlineColor = c;
   }

   public void setWarningUnderlineColor(Color c) {
      mWarningUnderlineColor = c;
   }

   /*
   private void drawSelected(Graphics g, char[] txt, int lineNo, int from, int len, int x, int y, int selStart, int selEnd) {
       if (selStart <= len && selEnd > 0 && selEnd>selStart) {
           g.setColor(Color.white); // when selected, paint white.
           int st = Math.max(0,selStart);
           int end = Math.min(selEnd,len);
           if (end>st) { // can be <= on end of line stuff...
//                drawChars(g,txt,from+st,end-st,x+mCharWidth*st,y);
               drawCharsWithOffset(g,txt,from,st,end-st,x,y);
           }
       }
   }*/

   public Rectangle getDropTargetHintBounds(String text, TextRange range, int baseX, int y, int height, boolean isMultiline) {
      int lsub = 0;
      int rsub = 0;
      if (range.getLength() == 0) {
         if (range.getStartPosition() == text.length()) {
            // at end:
            lsub = 0;
            rsub = mFonts.getCharWidth() * 2;
         }
         else {
            if (range.getStartPosition() == 0) {
               // at start:
               rsub = mFonts.getCharWidth() / 2;
            }
            else {
               // somewhere in the middle:
               rsub = lsub = mFonts.getCharWidth() / 2;
            }
         }
      }
      int xchar;
      if (isMultiline) {
         int line = getLineNumber(text, range.getStartPosition());
         xchar = getLineOffset(text, range.getStartPosition());
         y += line * mFonts.getTotalCharHeight();
      }
      else {
         xchar = 0;
      }
      char[] txt = text.toCharArray();
      //DSS: FIX -- Line starts at character 'xchar' and not '0'
      int left = calculateCharsPixelSize(txt, xchar, range.getStartPosition() - xchar) - lsub;
      int right = calculateCharsPixelSize(txt, xchar, range.getEndPosition() - xchar) + rsub;
      return new Rectangle(baseX + left, y, right - left, height);
   }

   public int getCharacterPosition(String str, int xPos, int yPos) {
      int x = 0;
      char[] txt = tabsToSpaces(str.toCharArray(), 0, str.length());
      int len = txt.length;
      String text = new String(txt, 0, len);
      CharStream charStream = new CharStream(text);
      Token[] tokens = Lexer.lex(charStream);
      int[] tokPos = generateTokenPositionTable(tokens, txt);

      int i = 0;
      if (yPos > 0) {
         i = getVerticleCharPosition(txt, yPos / mFonts.getTotalCharHeight());
      }

      for (; i < len; i++) {
         if (x > xPos) {
            //System.out.println("Char position @"+xPos + "<" + x + " is [" + (i-1) + "] " + txt[i-1] + " {"+str+"}");
            return i - 1;
         }

         //DSS: Check for the existance of the next char
         if ((i + 1) < len) {
            x += calculateCharPixelSize(txt, tokens, tokPos, i, 1);
         }
      }
      //System.out.println("Charposition @"+xPos+ " is " + (i) + txt[i] + " ["+str+"]");
      return len;
   }

   public Utilities.DropTextInfo getDropTargetRange(String text, int xPos, int yPos, int selStart, int selEnd, boolean isCharacterRef) {
      int cpos = getCharacterPosition(text, xPos, yPos);
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
      return Utilities.getDropTargetRange(cpos, text, selStart, selEnd, true); // When used by mapper tree (as this is), only consider drops on markers.
   }

   private static int getLineNumber(String text, int cpos) {
      int mm = Math.min(cpos, text.length());
      int line = 0;
      for (int i = 0; i < mm; i++) {
         if (text.charAt(i) == '\n') {
            line++;
         }
      }
      return line;
   }

   private static int getLineOffset(String text, int cpos) {
      int mm = Math.min(cpos, text.length());
      int lastLineAt = 0;
      for (int i = 0; i < mm; i++) {
         if (text.charAt(i) == '\n') {
            lastLineAt = i + 1;
         }
      }
      return lastLineAt;
   }

   private static int getVerticleCharPosition(char[] txt, int row) {
      int charAt = 0;
      while (row-- > 0) {
         boolean found = false;
         for (; charAt < txt.length; charAt++) {
            char c = txt[charAt];
            if (c == '\n') {
               charAt++;
               found = true;
               break;
            }
         }
         if (!found) {
            return txt.length;
         }
      }
      return charAt;
      // see how long this line goes...
//        for (int i=charAt;i<txt.length;i++) {
//            char c = txt[i];
//            if (c=='\n') {
//                return Math.min(charAt+col,i);
//            }
//        }
//        return charAt+col;
   }

   private final void drawChars(Graphics g, char[] txt, int from, int length, int x, int y) {
      g.drawChars(txt, from, length, x, y);
   }

   private final void drawCharsWithOffset(Graphics g, char[] txt, int first, int from, int length, int firstX, int y) {
      int x = calculateCharsPixelSize(g, txt, first, from);
      x += firstX;
      g.drawChars(txt, first + from, length, x, y);
   }

   private int getCharsPixelSize(Font currentFont, char[] txt, int from, int len) {
      len = Math.abs(len);
      FontRenderContext frc = mFonts.getFontRenderContext();
      Rectangle2D rect = currentFont.getStringBounds(txt, from, from + len, frc);
      int width = (int) rect.getWidth();
      return width;
   }

   /**
    * Creates a lookup table to be used to map character positions to token positions.
    */
   protected static int[] generateTokenPositionTable(Token[] tokens, char[] txt) {
      int[] tokPos = new int[txt.length];
      for (int pos = 0; pos < txt.length; pos++) {
         tokPos[pos] = -1;
      }

      // Create the table.
      for (int i = 0; i < tokens.length; i++) {
         Token token = tokens[i];
         int start = token.getTextRange().getStartPosition();

         // DSS: End position for current token = start of the next
         //int end = token.getTextRange().getEndPosition();
         int end = start;
         if ((i + 1) < tokens.length) {
            end = tokens[i + 1].getTextRange().getStartPosition();
         }
         else {
            end = txt.length - 1;
         }

         //System.out.println("generateTokenPositionTable: textLen="+txt.length+ " ["+start+","+end+"]");

         for (int j = start; j < end; j++) {
            tokPos[j] = i;
         }
      }

      // Make all the white space part of the next token.
      int currentToken = 0;
      for (int pos = 0; pos < txt.length; pos++) {
         if (tokPos[pos] == -1) {
            tokPos[pos] = currentToken;
         }
         else {
            currentToken = tokPos[pos];
         }
      }
      return tokPos;
   }

   public int calculateCharsPixelSize(Graphics g, char[] txt, int from, int len) {
      initialize(g);
      if (g instanceof Graphics2D) {
         return calculateCharsPixelSize(txt, from, len);
      }
      return len * getCharWidth();
   }

   private int calculateCharsPixelSize(char[] txt, int from, int len) {
      if ((from + len) > txt.length) {
         len = txt.length - from;
      }
      if (len < 0) {
         //System.out.println("Length is negative: [offset, length, array] = "+from+", "+len+", "+txt.length );
         return 0;
      }

      txt = tabsToSpaces(txt, from, len);
      // Create a token scanner so we can get the tokens from the txt string.

      CharStream charStream = new CharStream(new String(txt, from, len));
      Token[] tokens = Lexer.lex(charStream);

      return calculateTokenPixelSize(txt, tokens, from, len, -1, -1);
   }

   // Incremental pixel sizer.
   private int calculateCharPixelSize(char[] txt, Token[] tokens, int[] tokPos, int from, int len) {
      Font currentFont = mFonts.getNormalFont();
      int totalSize = 0;
      int i = 0;
      int lastToken = tokens.length - 1;

      int at = from;
      if (tokPos != null) {
         i = tokPos[from];
         lastToken = tokPos[Math.min(from + len, tokPos.length - 1)];
      }

      for (; i <= lastToken; i++) {
         Token n = tokens[i];
         int endAt;
         if (i < lastToken) {
            endAt = tokens[i + 1].getTextRange().getStartPosition();
         }
         else {
            endAt = Math.min(from + len, txt.length - 1);
         }

         // Get the font for this kind of token.
         currentFont = getFontForToken(n);
         totalSize += getCharsPixelSize(currentFont, txt, at, endAt - at);
         at = endAt;
      }
      return totalSize;
   }

   /**
    * Dump Tokens
    * <p/>
    * private static void dumpTokens(String hint, Token[] tokens)
    * {
    * // Create the table.
    * for(int i=0; i < tokens.length; i++)
    * {
    * Token token = tokens[i];
    * int start = token.getTextRange().getStartPosition();
    * int end = token.getTextRange().getEndPosition();
    * System.out.println( hint + ": " + token.getType() + ", " + token.getValue() + ", ["+ start + ", " + end + "]" );
    * }
    * }
    */

   private Font getFontForToken(Token n) {
      Font currentFont = mFonts.getNormalFont();
      switch (n.getType()) {
         case Token.TYPE_LITERAL_STRING:
         case Token.TYPE_VARIABLE_REFERENCE:
         case Token.TYPE_LITERAL_STRING_SINGLE_QUOTE:
         case Token.TYPE_ERROR:
            break;
         case Token.TYPE_FUNCTION_NAME:
            currentFont = mFonts.getBoldFont();
            break;
         case Token.TYPE_USER_COMMENT:
         case Token.TYPE_MARKER_STRING:
            currentFont = mFonts.getItalicFont();
            break;
         default:
            {
               if (n.isOperator() || n.isAxis()) {
                  if (!(n.getType() == Token.TYPE_SLASH || n.getType() == Token.TYPE_SLASH_SLASH)) {
                     currentFont = mFonts.getBoldFont();
                  }
               }
            }
      }
      return currentFont;
   }

   // Incremental pixel sizer.  Needs refactoring --- txt, tokens redundant information, from/len & vbegin/vend are also redundant.
   private int calculateTokenPixelSize(char[] txt, Token[] tokens, int from, int len, int vbegin, int vend) {
      try {
         return calculateTokenPixelSizeInternal(txt, tokens, from, len, vbegin, vend);
      }
      catch (Exception e) {
         // code above is so messed up that it sometimes gets array index out of bounds, until it is rewritten,
         // just deal with it this way.
         return mFonts.getCharWidth() * len;
      }
   }

   // Incremental pixel sizer.  Needs refactoring --- txt, tokens redundant information, from/len & vbegin/vend are also redundant.
   // This code is way ****ed.
   private int calculateTokenPixelSizeInternal(char[] txt, Token[] tokens, int from, int len, int vbegin, int vend) {
      Font currentFont = mFonts.getNormalFont();
      int totalSize = 0;
      int i = 0;
      int lastToken;

      if (vend == -1) {
         lastToken = tokens.length - 1;
      }
      else {
         lastToken = vend;
      }

      if (vbegin != -1) {
         i = vbegin;
      }

      int at = 0, endAt = 0;

      for (; i <= lastToken; i++) {
         Token n = tokens[i];
         at = tokens[i].getTextRange().getStartPosition();

         if (i < lastToken) {
            endAt = tokens[i + 1].getTextRange().getStartPosition();
         }
         else {
            endAt = Math.min(len, txt.length - from);
         }

         currentFont = getFontForToken(n);
         int ffrom = Math.min(from + at, txt.length);
         int toklen = Math.min(endAt - at, txt.length);
         if (i == 0) {
            FontRenderContext frc = mFonts.getFontRenderContext();
            int ct = tokens[i].getTextRange().getStartPosition();
            if (ct > 0) {
               Rectangle2D rect = currentFont.getStringBounds(" ", frc);
               totalSize += ct * rect.getWidth();
            }
         }
         totalSize += getCharsPixelSize(currentFont, txt, ffrom, toklen);
         at = endAt;
      }
      return totalSize;
   }

}

// Don't ever make these.... either make it inner or a separate file.

final class FontHolder {
   private final FontMetrics mFontMetrics;
   private final FontRenderContext mFontRenderContext;

   private final Font mNormalFont;
   private final Font mBoldFont;
   private final Font mItalicFont;

   private final int mCharWidth;
   private final int mCharHeight;
   private final int mCharDescent;

   public FontHolder(FontMetrics fontMetrics, FontRenderContext frc, Font normalFont, Font boldFont, Font italicFont) {
      mFontMetrics = fontMetrics;
      mFontRenderContext = frc;

      mNormalFont = normalFont;
      mBoldFont = boldFont;
      mItalicFont = italicFont;

      mCharWidth = mFontMetrics.charWidth('X'); // Assumes fixed character size
      mCharHeight = mFontMetrics.getMaxAscent();
      mCharDescent = mFontMetrics.getMaxDescent();
   }

   public FontHolder(FontMetrics fontMetrics, FontRenderContext frc, Font normalFont) {
      this(fontMetrics, frc,
           normalFont,
           normalFont.deriveFont(Font.BOLD),
           normalFont.deriveFont(Font.ITALIC));
   }

   public final FontMetrics getFontMetrics() {
      return mFontMetrics;
   }

   public final FontRenderContext getFontRenderContext() {
      return mFontRenderContext;
   }

   public final Font getNormalFont() {
      return mNormalFont;
   }

   public final Font getItalicFont() {
      return mItalicFont;
   }

   public final Font getBoldFont() {
      return mBoldFont;
   }

   public final int getCharDescent() {
      return mCharDescent;
   }

   public final int getCharHeight() {
      return mCharHeight;
   }

   public final int getCharWidth() {
      return mCharWidth;
   }

   public final int getTotalCharHeight() {
      return mCharHeight + mCharDescent;
   }
}