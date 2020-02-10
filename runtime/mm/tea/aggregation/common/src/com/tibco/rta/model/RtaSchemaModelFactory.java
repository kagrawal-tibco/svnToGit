package com.tibco.rta.model;

import com.tibco.rta.model.impl.RtaSchemaImpl;
import com.tibco.rta.model.mutable.MutableRtaSchema;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import org.xml.sax.InputSource;

import java.io.File;


/**
 * A factory for creating {@link RtaSchema} model objects.
 */
public class RtaSchemaModelFactory {

	private static RtaSchemaModelFactory factory;

	private RtaSchemaModelFactory() {
	}

	synchronized public static RtaSchemaModelFactory getInstance() {
		if (factory == null) {
			factory = new RtaSchemaModelFactory();
		}
		return factory;
	}
	
	/**
	 * Returns a new schema instance.
	 *
	 * @param name the name
	 */
	public MutableRtaSchema newSchema(String name) {
		return new RtaSchemaImpl(name);
	}
	
	/**
	 * Create an {@link RtaSchema} instance from the provided input source.
	 * 
	 * @param inputSource input source to use to create the schema
	 * @return the schema
	 * @throws Exception
	 */
	public RtaSchema createSchema(InputSource inputSource) throws Exception {
		return SerializationUtils.deserializeSchema(inputSource);
	}

	/**
	 * Create an {@link RtaSchema} instance from the provided file
	 * 
	 * @param file Should be the XML representation of the RtaSchema
	 * @return the schema
	 * @throws Exception
	 */
	public RtaSchema createSchema(File file) throws Exception {
		return SerializationUtils.deserialize(file);
	}
	
}