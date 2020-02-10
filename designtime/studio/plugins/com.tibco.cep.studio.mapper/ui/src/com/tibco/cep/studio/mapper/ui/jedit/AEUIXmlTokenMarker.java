package com.tibco.cep.studio.mapper.ui.jedit;

/*
 * Copyright 2000 TIBCO Software, Inc. All rights reserved
 *
 * Date Modified               Author            Description
 * =============================================================================
 * (11/8/2000)                 Alberto Ricart        Original
 */

import java.awt.Color;

import javax.swing.text.Segment;

import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.JEditTextArea;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxStyle;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TextAreaDefaults;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.Token;
import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.TokenMarker;

/**
 * Class responsible for performing the syntax coloring on the editor
 */
public class AEUIXmlTokenMarker extends TokenMarker
{
  int lastOffset;

  public byte markTokensImpl(byte token, Segment line, int lineIndex)
  {
    char[] array = line.array;
    int offset = line.offset;
    int length = line.count + offset;

    lastOffset = offset;
/*
    if (length > 0)
    {
      System.out.println("offset: " + offset + " length: " + length
                         + " lastoffset: " + lastOffset);
      System.out.println(array);
    }
*/
    for (int i = offset; i < length; i++)
    {
      char c = array[i];

      switch (token)
      {

        // start an element
        case Token.NULL:
          switch (c)
          {

            case '<':
              token = Token.KEYWORD1;

              addToken(1, token);

              break;

            default:
              addToken(1, token);
              //System.out.println(c);

              break;
          }

          break;

        case Token.KEYWORD1:
          switch (c)
          {

            case '=':
            case '/':
              addToken(1, token);
              break;

            case '>':
              addToken(1, token);
              token = Token.NULL;
              break;

            case '"':
              addToken(1, token);
              token = Token.LITERAL1;
			  break;

            default:
              addToken(1, Token.LITERAL2);
              break;
          }

          break;


        case Token.LITERAL1:
          switch (c)
          {
            case '"':
              addToken(1, Token.KEYWORD1);
			  token = Token.KEYWORD1;
              break;

            default:
              token = Token.LITERAL1;
              addToken(1, token);
              break;
          }

          break;
      }
    }

    return token;
  }


  /**
   * Returns a JEditTextArea properly ininitalized for displaying XML.
   */
  public static JEditTextArea getAEUIXMLView()
  {
	TextAreaDefaults defaults = TextAreaDefaults.getDefaults();

	defaults.caretColor = Color.black;
	defaults.selectionColor = new Color(0xccccff);
	defaults.lineHighlightColor = new Color(0xe0e0e0);
	defaults.lineHighlight = false;
	defaults.bracketHighlightColor = Color.black;
	defaults.bracketHighlight = false;
	defaults.eolMarkerColor = new Color(0x009999);
	defaults.eolMarkers = false;
	defaults.paintInvalid = false;
	defaults.cols=20;
	defaults.rows=3;

	// coloring control
	SyntaxStyle[] styles = defaults.styles;
	styles[Token.KEYWORD1] = new SyntaxStyle(Color.blue,false,false);
	styles[Token.LITERAL2] = new SyntaxStyle(new Color(153,0,153),false,false);
	styles[Token.LITERAL1] = new SyntaxStyle(Color.black, false, true);


	JEditTextArea text = new JEditTextArea(defaults);
	text.setTokenMarker(new AEUIXmlTokenMarker());
	text.setEditable(false);

	return text;
  }


}

