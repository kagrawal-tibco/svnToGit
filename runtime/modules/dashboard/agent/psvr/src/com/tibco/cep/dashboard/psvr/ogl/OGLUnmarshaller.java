package com.tibco.cep.dashboard.psvr.ogl;

import java.io.StringReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.ServiceDependent;

/**
 * @author apatil
 *
 */
public final class OGLUnmarshaller extends ServiceDependent {

    private static OGLUnmarshaller instance = null;

    public static final synchronized OGLUnmarshaller getInstance() {
        if (instance == null) {
            instance = new OGLUnmarshaller();
        }
        return instance;
    }

    private boolean debug;

    private OGLUnmarshaller() {
    	super("oglunmarshaller","OGL Unmarshaller");
        debug = false;
    }

    public Object unmarshall(Class<?> clazz,String input) throws OGLException{
        try {
            StringReader reader = new StringReader(input);
            Unmarshaller unmarshaller = new Unmarshaller(clazz);
            unmarshaller.setValidation(debug);
            unmarshaller.setDebug(debug);
            return unmarshaller.unmarshal(reader);
        } catch (MarshalException e) {
            String message = messageGenerator.getMessage("oglunmarshaller.unmarshall.failure",new MessageGeneratorArgs(e,clazz.getName(),input));
            throw new OGLException(message,e);
        } catch (ValidationException e) {
            String message = messageGenerator.getMessage("oglunmarshaller.validation.failure",new MessageGeneratorArgs(e,clazz.getName(),input));
            throw new OGLException(message,e);
        }
    }

    /**
     * @return Returns the debug.
     */
    boolean isDebug() {
        return debug;
    }
    /**
     * @param debug The debug to set.
     */
    void setDebug(boolean debug) {
        this.debug = debug;
    }

}