package com.tibco.cep.driver.soap;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 19, 2009
 * Time: 3:12:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum MimeTypes {

    PLAIN_TEXT("text/plain"),
    XML_TEXT("text/xml"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private String literal;

    private MimeTypes(String literal) {
        this.literal = literal;
    }

     private static final MimeTypes[] VALUES_ARRAY =
            new MimeTypes[] {
                 PLAIN_TEXT,
                 XML_TEXT,
                 APPLICATION_OCTET_STREAM
            };

    public static MimeTypes getByLiteral(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MimeTypes result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

    public String getLiteral() {
        return literal;
    }

    public String toString() {
        return literal;
    }
}
