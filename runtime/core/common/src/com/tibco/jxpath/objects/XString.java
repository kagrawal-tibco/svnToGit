package com.tibco.jxpath.objects;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:01 PM
*/
public class XString extends XObjectWrapper {

    String val;
    public XString(String val)
    {
        super(val);
        this.val = val;
    }

    public boolean bool() {
        if ((val != null) && (val.length() > 0)) return false;
        return true;
    }

    public double num() {
        return Double.parseDouble(val);
    }

    public String str() {
        return val;
    }





    public boolean equals(XObject other) {
        if (!(other instanceof XString)) return false;

        XString xstr = (XString) other;

        if (xstr.val.equals(this.val)) return true;

        return false;
    }

    public static boolean isWhiteSpace(char ch)
    {
        return (ch == 0x20) || (ch == 0x09) || (ch == 0xD) || (ch == 0xA);
    }

    public XString fixWhiteSpace(boolean trimHead, boolean trimTail, boolean doublePunctuationSpaces)
    {

        // %OPT% !!!!!!!
        int len = val.length();
        char[] buf = new char[len];

        val.getChars(0, len, buf, 0);

        boolean edit = false;
        int s;

        for (s = 0; s < len; s++)
        {
            if (isWhiteSpace(buf[s]))
            {
                break;
            }
        }

        /* replace S to ' '. and ' '+ -> single ' '. */
        int d = s;
        boolean pres = false;

        for (; s < len; s++)
        {
            char c = buf[s];

            if (isWhiteSpace(c))
            {
                if (!pres)
                {
                    if (' ' != c)
                    {
                        edit = true;
                    }

                    buf[d++] = ' ';

                    if (doublePunctuationSpaces && (s != 0))
                    {
                        char prevChar = buf[s - 1];

                        if (!((prevChar == '.') || (prevChar == '!')
                                || (prevChar == '?')))
                        {
                            pres = true;
                        }
                    }
                    else
                    {
                        pres = true;
                    }
                }
                else
                {
                    edit = true;
                    pres = true;
                }
            }
            else
            {
                buf[d++] = c;
                pres = false;
            }
        }

        if (trimTail && 1 <= d && ' ' == buf[d - 1])
        {
            edit = true;

            d--;
        }

        int start = 0;

        if (trimHead && 0 < d && ' ' == buf[0])
        {
            edit = true;

            start++;
        }



        return new XString(new String(buf, start, d - start));
    }
}
