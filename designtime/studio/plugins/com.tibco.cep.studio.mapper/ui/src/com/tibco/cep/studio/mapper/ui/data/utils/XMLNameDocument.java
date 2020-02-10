package com.tibco.cep.studio.mapper.ui.data.utils;

import com.tibco.io.xml.XMLStringUtilities;

/**
 * A subclass of PlainDocument that only allows legal XML names (with the exception
 * that it does allow an empty string)
 */
public class XMLNameDocument extends NameValidatingDocument {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XMLNameDocument() {
        super(VALIDATOR);
    }

    /**
     * The {@link DocumentNameValidator} used; it validates XML names.
     */
    public static final DocumentNameValidator VALIDATOR = new XMLDocumentNameValidator();

    private static final class XMLDocumentNameValidator implements DocumentNameValidator {
        public boolean isValidNameCharacter(char character, boolean isFirstCharacter) {
            if (isFirstCharacter)
            {
                return XMLStringUtilities.isNameStartChar(character);
            }
            return XMLStringUtilities.isNameChar(character);
        }

        public String getReplacementString(char character, boolean isFirstCharacter) {
            return isFirstCharacter ? "_" : "-";
        }

        public int getMaximumLength() {
            return -1; // no limit.
        }

        public String isReservedName(String fullName) {
            // never reserved.
            return null;
        }

        public String makeValidName(String startingName) {
            return startingName;
        }
    }
}
