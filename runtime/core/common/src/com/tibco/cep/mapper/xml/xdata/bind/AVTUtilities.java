/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.xpath.CharStream;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;

/**
 * A set of utility methods for dealing with Attribute Value Templates.
 */
public class AVTUtilities
{
    /**
     * Indicates if the string is an AVT (by checking for leading {)
     */
    public static boolean isAVTString(String str)
    {
        return str.indexOf('{')>=0;
    }

    /**
     * Turns the AVT string into its equivalent expression, using the concat() function to simulate the string nature of it.<br>
     * For example, the AVT
     * <code>alpha{'beta'}gamma</code> would become
     * <code>concat('alpha',('beta'),'gamma')</code> (where the paren is used to indicate AVT-ness)
     * and paren expressions to indicate {}.
     * This is the reverse of {@link #renderAsAVT(com.tibco.cep.mapper.xml.xdata.xpath.Expr)}
     * @param avtStr A string that contains (perhaps) AVTs
     * @return An expression
     */
    public static Expr parseAsExpr(String avtStr)
    {
        ArrayList t = new ArrayList();
        parseAsExpr(avtStr,t);
        Expr[] c = (Expr[]) t.toArray(new Expr[t.size()]);
        if (c.length==1)
        {
            return c[0];
        }
        return Expr.create(ExprTypeCode.EXPR_FUNCTION_CALL,c,"concat","","");
    }

    /**
     * Turns the AVT string into its equivalent expression, using the concat() function to simulate the string nature of it.<br>
     * For example, the AVT
     * <code>alpha{'beta'}gamma</code> would become
     * <code>concat('alpha',('beta'),'gamma')</code> (where the paren is used to indicate AVT-ness)
     * and paren expressions to indicate {}.
     * This is the reverse of {@link #renderAsAVT(com.tibco.cep.mapper.xml.xdata.xpath.Expr)}
     * @param avtStr A string that contains (perhaps) AVTs
     * @return An expression
     */
    public static Token[] lexAsExpr(String avtStr)
    {
        ArrayList tokens = new ArrayList();
        lexAsExpr(avtStr,tokens,0);
        return (Token[]) tokens.toArray(new Token[tokens.size()]);
    }

    private static void lexAsExpr(String avtStr, ArrayList tokens, int basePos)
    {
        if (avtStr.length()==basePos)
        {
            return;
        }
        int i = avtStr.indexOf('{');
        int i2 = avtStr.indexOf("}");
        if (i>=0 && i2>i)
        {
            if (i>0)
            {
                String c1 = avtStr.substring(0,i);
                tokens.add(new Token(Token.TYPE_AVT_FRAGMENT,"",new TextRange(basePos,basePos+i),c1));
            }
            tokens.add(new Token(Token.TYPE_LBRACKET,"",new TextRange(basePos+i,basePos+i+1)));

            String avt = avtStr.substring(i+1,i2);
            Token[] t = Lexer.lex(new CharStream(avt,i+1));
            for (int xi=0;xi<t.length;xi++)
            {
                tokens.add(t[xi]);
            }
            int offset = i2+1;
            tokens.add(new Token(Token.TYPE_RBRACKET,"",new TextRange(basePos+i2,basePos+i2+1)));

            lexAsExpr(avtStr.substring(offset),tokens,basePos+offset);
        }
        else
        {
            if (i>=0)
            {
                // Incomplete avt (no closing {)
                if (i>0)
                {
                    String c1 = avtStr.substring(0,i);
                    tokens.add(new Token(Token.TYPE_AVT_FRAGMENT,"",new TextRange(basePos,basePos+i),c1));
                }
                tokens.add(new Token(Token.TYPE_LBRACKET,"",new TextRange(basePos+i,basePos+i+1)));

                String avt = avtStr.substring(i+1,avtStr.length());
                Token[] t = Lexer.lex(new CharStream(avt,i+1));
                for (int xi=0;xi<t.length;xi++)
                {
                    tokens.add(t[xi]);
                }
            }
            else
            {
                int end = basePos+avtStr.length();
                if (end!=basePos)
                {
                    tokens.add(new Token(Token.TYPE_AVT_FRAGMENT,"",new TextRange(basePos,basePos+avtStr.length()),avtStr));
                }
            }
        }
    }

    public static String renderAsAVT(Expr expr)
    {
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            if (expr.getExprValue().equals("concat"))
            {
                // ok, good.
                Expr[] c = expr.getChildren();
                StringBuffer sb = new StringBuffer();
                for (int i=0;i<c.length;i++)
                {
                    Expr e = c[i];
                    if (e.getExprTypeCode()==ExprTypeCode.EXPR_LITERAL_STRING)
                    {
                        String str = e.getExprValue();
                        sb.append(str);
                    }
                    else
                    {
                        // strip paren:
                        if (e.getExprTypeCode()==ExprTypeCode.EXPR_PAREN)
                        {
                            e = e.getChildren()[0];
                        }
                        sb.append("{");
                        sb.append(e.toExactString());
                        sb.append("}");
                    }
                }
                return sb.toString();
            }
        }
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_LITERAL_STRING)
        {
            String str = expr.getExprValue();
            return str;
        }
        // strip paren:
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_PAREN)
        {
            expr = expr.getChildren()[0];
        }
        return "{" + expr.toExactString() + "}";
    }

    private static void parseAsExpr(String avtStr, ArrayList components)
    {
        int i = avtStr.indexOf('{');
        int i2 = avtStr.indexOf("}");
        if (i>=0 && i2>i)
        {
            if (i>0)
            {
                String c1 = avtStr.substring(0,i);
                escapeQuotes(c1,components);
            }

            String avt = avtStr.substring(i+1,i2);
            Expr e = Parser.parse(new CharStream(avt,i+1));
            Expr paren = Expr.create(ExprTypeCode.EXPR_PAREN,new Expr[] {e},null,"","");
            components.add(paren);
            parseAsExpr(avtStr.substring(i2+1),components);
        }
        else
        {
            escapeQuotes(avtStr,components);
        }
    }

    private static void escapeQuotes(String str, ArrayList components)
    {
        if (str.length()==0)
        {
            return;
        }
        int i = str.indexOf("\"");
        if (i>=0)
        {
            if (i>0)
            {
                String s = str.substring(0,i);
                components.add(Parser.parse("\"" + s + "\""));
            }
            components.add(Parser.parse("'\"'"));
            escapeQuotes(str.substring(i+1),components);
        }
        else
        {
            components.add(Parser.parse("\"" + str + "\""));
        }
    }
}
