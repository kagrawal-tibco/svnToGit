package com.tibco.cep.driver.http.serializer.soap;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 21, 2009
 * Time: 4:05:39 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SOAPFaultCodes implements ISOAPFaultCodes {

    SOAP_MUSTUNDERSTAND("MustUnderstand"),
    SOAP_SERVER_ERROR("Server"),
    SOAP_CLIENT_ERROR("Client"),
    SOAP_VERSION_MISMATCH_ERROR("VersionMismatch"),
    SOAP_SENDER_ERROR("Sender"),
    SOAP_RECEIVER_ERROR("Receiver"),
    SOAP_DATA_ENCODING_UNKNOWN("DataEncodingUnknown");

    private String faultCode;

    private SOAPFaultCodes(String faultCode) {
        this.faultCode = faultCode;
    }

    public static boolean validateLiteral(String faultCodeString) {
        //The fault string can contain prefixes
        //Tokenize the string with DELIMITER
        String[] splits = faultCodeString.split(NS_PREFIX_DELIMITER);
        //Get last part
        String faultCodePart = splits[splits.length - 1];
        //Split using the Dot to allow chaining fault code values
        String[] splits1 = faultCodePart.split(FAULT_CODE_DELIMITER);
        /**
         * Here we always validate the most generic part
         * We will leave more specific validations to be
         * handled by implmentations of {@link ISOAPFaultCodes}
         */
        String genericCode = (splits1.length == 0) ? faultCodePart : splits1[0];
        return SOAPFaultCodes.getByLiteral(genericCode) != null;
    }

   private static final SOAPFaultCodes[] VALUES_ARRAY =
            new SOAPFaultCodes[] {
                 SOAP_MUSTUNDERSTAND,
                 SOAP_SERVER_ERROR,
                 SOAP_CLIENT_ERROR,
                 SOAP_VERSION_MISMATCH_ERROR,
                 SOAP_SENDER_ERROR,
                 SOAP_RECEIVER_ERROR,
                 SOAP_DATA_ENCODING_UNKNOWN   
            };

    public static SOAPFaultCodes getByLiteral(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SOAPFaultCodes result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

    public String toString() {
        return faultCode;
    }
}
