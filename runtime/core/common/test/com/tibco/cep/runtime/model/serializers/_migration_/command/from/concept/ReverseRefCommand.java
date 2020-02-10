package com.tibco.cep.runtime.model.serializers._migration_.command.from.concept;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionKey;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.command.Command;

import java.io.DataInput;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 2:40:16 PM
*/
public class ReverseRefCommand implements Command {
    public void execute(ConversionScratchpad scratchpad) throws Exception {
        DataInput dataInput = scratchpad.getFromDataInput();

        int reverseRefSize = dataInput.readInt();

        for (int i = 0; i < reverseRefSize; i++) {
            if (dataInput.readBoolean()) {
                String propertyName = dataInput.readUTF();

                long reverseRefId = dataInput.readLong();
                Reference reverseRef = new Reference(reverseRefId);

                ConversionKey<ConceptOrReference> key = new ConversionKey<ConceptOrReference>(
                        propertyName, ConceptOrReference.class);
                scratchpad.addIntermediateDatum(key, reverseRef);
            }
        }
    }
}
