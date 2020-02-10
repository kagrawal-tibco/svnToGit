package com.tibco.be.parser.codegen;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;

import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Entity;



public class RDFUtil {
	public static final String firstCap(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String getPackageString(Entity entity) {
        String path = entity.getFolder().getFullPath();
        return ModelNameUtil.modelPathToExternalForm(path);
    }

    public static String getFSName(Entity entity) {
        return getPackageString(entity) + "." + ModelNameUtil.escapeIdentifier(entity.getName());
    }

    //returns -1 if the type is unknown
    public static int getRDFTermTypeFlag(RDFPrimitiveTerm type) {
        if(type == null) return -1;
        return type.getTypeId();
    }
    
  //65534 was experimentally determined as the max number of plain ascii
    //characters allowed in a string literal before javac complains
    public static final int MAX_STRING_LITERAL_BYTES = 65534;
    //returns true if a string would be too big to store in a class as a string constant
    public static boolean isStringConstantOversize(String str) {
        if(str == null) return false;
        //max number of bytes per character in Java modified UTF-8 is 3
        if(str.length() < MAX_STRING_LITERAL_BYTES / 3) return false;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(str);
            dos.flush();
            baos.flush();
            //the first two bytes of baos are the length of the output as a short
            //but this may overflow so it can't be trusted and the actual output
            //must be checked
            boolean isOversize = baos.size() - 2 > MAX_STRING_LITERAL_BYTES;
            baos.close();
            dos.close();
            return isOversize;
        } catch (UTFDataFormatException udfe) {
            return true;
        } catch(IOException ioe) {
            return true;
        }
    }
    
    

}
