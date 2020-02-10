package com.tibco.cep.runtime.model.serializers._migration_.command.from.concept;

import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.command.ConceptConstants;
import com.tibco.cep.runtime.model.serializers._migration_.command.Command;

import java.io.DataInput;
import java.io.IOException;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:34:28 PM
*/
public class MainCommand implements Command {
    public void execute(ConversionScratchpad scratchpad) throws IOException {
        DataInput dataInput = scratchpad.getFromDataInput();

        int typeId = dataInput.readInt();
        scratchpad.addIntermediateDatum(ConceptConstants.KEY_TYPE_ID, typeId);

        int version = dataInput.readInt();
        scratchpad.addIntermediateDatum(ConceptConstants.KEY_VERSION, version);

        boolean isDeleted = dataInput.readBoolean();
        scratchpad.addIntermediateDatum(ConceptConstants.KEY_IS_DELETED, isDeleted);

        long id = dataInput.readLong();
        scratchpad.addIntermediateDatum(ConceptConstants.KEY_ID, id);

        if (dataInput.readBoolean()) {
            String extId = dataInput.readUTF();
            scratchpad.addIntermediateDatum(ConceptConstants.KEY_EXT_ID, extId);
        }

        if (dataInput.readBoolean()) {
            long parentId = dataInput.readLong();

            scratchpad.addIntermediateDatum(ConceptConstants.KEY_PARENT_ID,
                    new Reference(parentId));
        }
    }
}
