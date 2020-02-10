package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:56 PM
*/
public class TranslateFunc implements Function {

    public static final Function FUNCTION = new TranslateFunc();
    public static final QName FUNCTION_NAME = new QName("translate");

    private TranslateFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {

        if (args.size() != 3) throw new XPathFunctionException("Expecting of 3 args");

        String theFirstString = args.get(0).str();
        String theSecondString = args.get(1).str();
        String theThirdString = args.get(2).str();
        int theFirstStringLength = theFirstString.length();
        int theThirdStringLength = theThirdString.length();


        StringBuilder sbuffer = new StringBuilder();

        for (int i = 0; i < theFirstStringLength; i++)
        {
            char theCurrentChar = theFirstString.charAt(i);
            int theIndex = theSecondString.indexOf(theCurrentChar);

            if (theIndex < 0)
            {

                // Didn't find the character in the second string, so it
                // is not translated.
                sbuffer.append(theCurrentChar);
            }
            else if (theIndex < theThirdStringLength)
            {

                // OK, there's a corresponding character in the
                // third string, so do the translation...
                sbuffer.append(theThirdString.charAt(theIndex));
            }
            else
            {

                // There's no corresponding character in the
                // third string, since it's shorter than the
                // second string.  In this case, the character
                // is removed from the output string, so don't
                // do anything.
            }
        }

        return new XString(sbuffer.toString());


    }
}
