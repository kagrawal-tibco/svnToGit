package com.tibco.cep.studio.mapper.ui.data.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxDocument;

/**
 * A subclass of PlainDocument that only allows legal validated name characters where the
 * validation is specific by the {@link DocumentNameValidator}.
 */
public class NameValidatingDocument extends SyntaxDocument
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DocumentNameValidator mValidator;

    /**
     * Creates the document with no validator,
     * call {@link #setDocumentNameValidator(com.tibco.ui.data.utils.DocumentNameValidator)}
     * to set it.
     */
    public NameValidatingDocument() {
    }

    /**
     * Creates the document with the validator.<br>
     * Equivalent to the empty ctr. and calling {@link #setDocumentNameValidator(com.tibco.ui.data.utils.DocumentNameValidator)}.
     */
    public NameValidatingDocument(DocumentNameValidator nameValidator) {
        mValidator = nameValidator;
    }

    public void remove(int offs, int len) throws BadLocationException
    {
        super.remove(offs, len);
        // (Since call to replace ends up calling remove, we want to avoid coming in again...)
        if (offs==0)
        {
            // check for newly illegal first character.
            if (getLength()>0)
            {
                char c = getText(0,1).charAt(0);
                if (!mValidator.isValidNameCharacter(c,true))
                {
                    String r = mValidator.getReplacementString(c,true);
                    // Instead of calling replace, call insert/remove so that we insert before remove
                    // (which avoids nasty cursor issues with delete, etc. & also avoids needing to add a 'changing' re-entry guard for this routine)
                    super.insertString(1,r,null); // null allowed in replace for AttributeSet;
                    super.remove(0,1);
                }
            }
        }
    }

    public void insertString(int pos, String content, AttributeSet set) throws BadLocationException{
        if (content != null)
        {
            char[] ccontent = content.toCharArray();
            StringBuffer ret = new StringBuffer();
            for (int i=0;i<ccontent.length;i++) {
                boolean isFirst = i+pos==0;
                char c = ccontent[i];
                if (mValidator==null || !mValidator.isValidNameCharacter(c,isFirst)) {
                    ret.append(mValidator.getReplacementString(c,isFirst));
                } else {
                    ret.append(c);
                }
            }
            String newContent = ret.toString();
            super.insertString(pos,newContent,set);
        }
        else
            super.insertString(pos,content,set);
    }

    /**
     * Sets the name validator to use.<br>
     * This will not retro-activiely fix the existing document, though.
     */
    public void setDocumentNameValidator(DocumentNameValidator nameValidator) {
        mValidator = nameValidator;
    }

    /**
     * Gets the name validator in use.
     */
    public DocumentNameValidator getDocumentNameValidator() {
        return mValidator;
    }

    /**
     * A public service utility function that fixes a name by applying the validator's character changes.
     */
    public static String makeValidName(String name, DocumentNameValidator validator) {
        char[] ccontent = name.toCharArray();
        StringBuffer ret = new StringBuffer();
        for (int i=0;i<ccontent.length;i++) {
            boolean isFirst = i==0;
            char c = ccontent[i];
            if (validator==null || !validator.isValidNameCharacter(c,isFirst)) {
                ret.append(validator.getReplacementString(c,isFirst));
            } else {
                ret.append(c);
            }
        }
        return ret.toString();
    }
}
