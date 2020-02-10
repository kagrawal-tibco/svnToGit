/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.type;

import com.tibco.cep.mapper.xml.xdata.xpath.CharStream;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.cep.mapper.xml.xdata.xpath.TokenStream;
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Parses the 'type' part of the XQuery specification --- these are used by XQuery as a type declaration.
 * This parser can handle a superset of that, allowing things such as choices, sequences, etc. to be parsed as well.
 */
public class XQueryTypesParser
{
    public static SmSequenceType parse(PrefixToNamespaceResolver resolver,
                                       SmComponentProviderEx sp,
                                       SmSequenceType outputContext,
                                       String decl)
    {
        try
        {
            return parseUnsafe(resolver,sp,outputContext,decl);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);//WCETODO REMOVE.
            System.err.println("Err parsing xtype: " + decl + " " + e.getMessage());
            return SMDT.PREVIOUS_ERROR;
        }
    }

    public static SmSequenceType parseUnsafe(PrefixToNamespaceResolver resolver,
                                             SmComponentProviderEx sp,
                                             SmSequenceType outputContext,
                                             String decl) {
        Token[] t = Lexer.lex(new CharStream(decl),false,true);
        TokenStream ts = new TokenStream(t);
        SmSequenceType r = parseExtendedSequenceType(resolver,sp,outputContext,ts);
        if (ts.peek().getType()!=Token.TYPE_EOF) {
            throw new RuntimeException("Unexpected at end: " + ts.peek());
        }
        return r;
    }

    private static SmSequenceType parseExtendedSequenceType(PrefixToNamespaceResolver resolver,
                                                            SmComponentProviderEx sp,
                                                            SmSequenceType outputContext,
                                                            TokenStream ts) {
        SmSequenceType t = parseSequenceType(resolver,sp,outputContext,ts);
        Token tn = ts.peek();
        if (tn.getType()==Token.TYPE_COMMA) {
            ts.pop();
            SmSequenceType t2 = parseExtendedSequenceType(resolver,sp,outputContext,ts);
            return SmSequenceTypeFactory.createSequence(t,t2);
        }
        if (tn.getType()==Token.TYPE_UNION) {
            ts.pop();
            SmSequenceType t2 = parseExtendedSequenceType(resolver,sp,outputContext,ts);
            return SmSequenceTypeFactory.createChoice(t,t2);
        }
        if (tn.getType()==Token.TYPE_AMPERSAND) {
            ts.pop();
            SmSequenceType t2 = parseExtendedSequenceType(resolver,sp,outputContext,ts);
            return SmSequenceTypeFactory.createInterleave(t,t2);
        }
        return t;
    }


    /**
     * Parses the 'SequenceType' term (in XQuery spec)
     */
    private static SmSequenceType parseSequenceType(PrefixToNamespaceResolver resolver,
                                                    SmComponentProviderEx sp,
                                                    SmSequenceType outputContext,
                                                    TokenStream ts) {
        // (ItemType OccurrenceIndicator) |  "empty"
        Token first = ts.peek();

        SmSequenceType t;
        if (first.getType()==Token.TYPE_LPAREN) {
            t = parseParenExtension(resolver,sp,outputContext,ts);
        } else {
            if (first.getType()==Token.TYPE_NAME_TEST) {
                String v = first.getValue();
                if (v.equals("empty")) {
                    ts.pop();
                    return SMDT.VOID;
                }
                if (v.equals("previous-error")) {
                    ts.pop();
                    return SMDT.PREVIOUS_ERROR;
                }
                if (v.equals("none")) {
                    ts.pop();
                    return SMDT.NONE;
                }
            }
            t = parseItemType(resolver,sp,outputContext,ts);
        }
        Token n = ts.peek();
        if (n.getType()==Token.TYPE_PLUS) {
            ts.pop();
            return SmSequenceTypeFactory.createRepeats(t,SmCardinality.AT_LEAST_ONE);
        }
        if (n.getType()==Token.TYPE_QUESTION_MARK) {
            ts.pop();
            return SmSequenceTypeFactory.createRepeats(t,SmCardinality.OPTIONAL);
        }
        // hacky, but xpath lexer works this case:
        if (n.getType()==Token.TYPE_NAME_TEST && n.getValue().equals("*")) {
            ts.pop();
            return SmSequenceTypeFactory.createRepeats(t,SmCardinality.REPEATING);
        }
        // just in case...
        if (n.getType()==Token.TYPE_MULTIPLY) {
            ts.pop();
            return SmSequenceTypeFactory.createRepeats(t,SmCardinality.REPEATING);
        }
        if (n.getType()==Token.TYPE_LBRACKET)
        {
            ts.pop();
            //WCETODO make robust, error checks, add tests!
            Token lb = ts.peek();
            String lv = lb.getValue();
            int v = Integer.parseInt(lv);
            ts.pop(); // #
            ts.pop(); // ,
            Token n2 = ts.pop(); // 2nd #
            String nv = n2.getValue();
            int max;
            if (nv.equals("unbounded") || nv.equals("*"))
            {
                max = SmParticle.UNBOUNDED;
            }
            else
            {
                max = Integer.parseInt(nv);
            }
            ts.pop(); // ]
            return SmSequenceTypeFactory.createRepeats(t,SmCardinality.create(v,max));
        }
        // no suffix:
        return t;
    }

    private static SmSequenceType parseItemType(PrefixToNamespaceResolver resolver,
                                                SmComponentProviderEx sp,
                                                SmSequenceType outputContext,
                                                TokenStream ts) {
        Token t = ts.peek();
        if (t.getType()==Token.TYPE_NAME_TEST) {
            String v = t.getValue();
            if (v.equals("element")) {
                ts.pop();
                return parseElementRef(resolver,sp,outputContext,ts);
            }
            if (v.equals("attribute")) {
                ts.pop();
                return parseAttributeRef(resolver,sp,outputContext,ts);
            }
            if (v.equals("document")) {
                ts.pop();
                if (ts.peek().getValue()!=null && ts.peek().getValue().equals("of")) {
                    ts.pop();
                    SmSequenceType elType = parseSequenceType(resolver,sp,outputContext,ts);
                    return SmSequenceTypeFactory.createDocument(elType);
                } else {
                    return SmSequenceTypeFactory.createDocument();
                }
            }
            if (v.equals("text")) {
                ts.pop();
                return SMDT.TEXT_NODE;
            }
            if (v.equals("item")) {
                ts.pop();
                return SMDT.ITEM;
            }
            if (v.equals("node")) {
                ts.pop();
                return SMDT.NODE;
            }
            if (v.equals("comment")) {
                ts.pop();
                return SMDT.COMMENT_NODE;
            }
            if (v.equals("processing-instruction")) {
                ts.pop();
                return SMDT.PROCESSING_INSTRUCTION_NODE;
            }
            if (v.equals("atomic")) {
                ts.pop();
                if (!"value".equals(ts.peek().getValue())) {
                    throw new RuntimeException("Expected value after 'atomic'");
                }
                ts.pop();
                return SMDT.ATOMIC_VALUE;
            }
            // Simple type:
            QName qn = new QName(t.getValue());
            ExpandedName ename = qn.getExpandedName(resolver);
            if (ename==null) {
                throw new RuntimeException("Unresolved prefix: " + qn.getPrefix());
            }
            if (XSDL.NAMESPACE.equals(ename.getNamespaceURI())) {
                ts.pop();
                String ln = ename.getLocalName();
                SmSequenceType rt = SmSequenceTypeFactory.createSimple(ln);
                if (rt==null) {
                    throw new RuntimeException("Type not found: " + ln);
                }
                return rt;
            }
        }
        throw new RuntimeException("unknown item type: " + t);
    }

    private static SmSequenceType parseElementRef(PrefixToNamespaceResolver resolver,
                                                  SmComponentProviderEx sp,
                                                  SmSequenceType outputContext,
                                                  TokenStream ts) {
        Token t = ts.peek();
        if (t.getType()!=Token.TYPE_NAME_TEST || t.getValue().equals("*")) {
            return SMDT.ELEMENT_NODE;
        }
        // special case:
        if (t.getValue().equals("of") && "type".equals(ts.peek2().getValue())) {
            SmType st = parseOptionalOfType(resolver,sp,ts);
            return SmSequenceTypeFactory.createElement(st);
        }
        ts.pop();
        String n = t.getValue();
        QName qn = new QName(n);
        ExpandedName ename = qn.getExpandedName(resolver);
        if (ename==null) {
            try//@@@@WWW
            {
                resolver.getNamespaceURIForPrefix(qn.getPrefix());
            }
            catch (Throwable tt) {}
            throw new RuntimeException("Missing prefix " + qn.getPrefix());
        }
        SmType st = parseOptionalOfType(resolver,sp,ts);
        return SmSequenceTypeSupport.getElementInContext(ename,outputContext,sp,st);
    }

    private static SmSequenceType parseAttributeRef(PrefixToNamespaceResolver resolver,
                                                    SmComponentProviderEx sp,
                                                    SmSequenceType outputContext,
                                                    TokenStream ts) {
        Token t = ts.peek();
        if (t.getType()!=Token.TYPE_NAME_TEST || t.getValue().equals("*")) {
            return SMDT.ATTRIBUTE_NODE;
        }
        // special case:
        if (t.getValue().equals("of") && "type".equals(ts.peek2().getValue())) {
            SmType st = parseOptionalOfType(resolver,sp,ts);
            return SmSequenceTypeFactory.createAttribute(st);
        }
        ts.pop();
        String n = t.getValue();
        QName qn = new QName(n);
        ExpandedName ename = qn.getExpandedName(resolver);
        if (ename==null) {
            throw new RuntimeException("Missing prefix " + qn.getPrefix());
        }
        SmType st = parseOptionalOfType(resolver,sp,ts);
        return SmSequenceTypeSupport.getAttributeInContext(ename,outputContext,sp,st);
    }

    private static SmType parseOptionalOfType(PrefixToNamespaceResolver resolver,
                                              SmComponentProviderEx sp,
                                              TokenStream ts) {
        if ("of".equals(ts.peek().getValue())) {
            ts.pop();
            if ("type".equals(ts.peek().getValue())) {
                ts.pop();
                QName qn = new QName(ts.peek().getValue());
                ts.pop();
                ExpandedName ename = qn.getExpandedName(resolver);
                if (ename==null) {
                    throw new RuntimeException("Can't find prefix for " + qn);
                }
               try {
                  return sp.getType(ename);
               }
               catch (SmGlobalComponentNotFoundException e) {
                  throw new RuntimeWrapException(e);
               }
/*
                SmNamespace schema = sp.getNamespace(ename.getNamespaceURI());
                if (schema==null) {
                    throw new RuntimeException("Couldn't get schema " + ename.getNamespaceURI());
                } else {
                    SmType t = SmSequenceTypeSupport.getType(schema,ename.getLocalName());
                    if (t!=null) {
                        return t;
                    }
                    throw new RuntimeException("Type not found " + ename.getLocalName());
                }
*/
            } else {
                throw new RuntimeException("Expected 'type' after 'of'");
            }
        } else {
            return null;
        }
    }

    private static SmSequenceType parseParenExtension(PrefixToNamespaceResolver resolver,
                                                      SmComponentProviderEx sp,
                                                      SmSequenceType outputContext,
                                                      TokenStream ts) {
        ts.pop(); // lparen
        SmSequenceType t1 = parseExtendedSequenceType(resolver,sp,outputContext,ts);
        Token tn = ts.peek();
        if (tn.getType()==Token.TYPE_RPAREN) {
            ts.pop();
            return SmSequenceTypeFactory.createParen(t1);
        } else {
            throw new RuntimeException("Expected ), got " + tn);
        }
    }
}
