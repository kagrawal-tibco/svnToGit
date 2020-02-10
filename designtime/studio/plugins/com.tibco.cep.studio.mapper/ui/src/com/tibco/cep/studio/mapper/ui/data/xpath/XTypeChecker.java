package com.tibco.cep.studio.mapper.ui.data.xpath;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

/**
 * A callback style iterface for performing pluggable type-checking.
 */
public interface XTypeChecker {
   /**
    * Type check this type according to implementation specific logic.
    *
    * @param gotType               The type to check.
    * @param errorMessageTextRange The text range that should be used in the error message.
    * @return The list of error messages, it is ok to return null.
    */
   ErrorMessageList check(SmSequenceType gotType, TextRange errorMessageTextRange);

   /**
    * For drag'n'drop, need a basic type (for cardinality, etc.)
    *
    * @return The basic type, or null if not available.
    */
   SmSequenceType getBasicType();
}
