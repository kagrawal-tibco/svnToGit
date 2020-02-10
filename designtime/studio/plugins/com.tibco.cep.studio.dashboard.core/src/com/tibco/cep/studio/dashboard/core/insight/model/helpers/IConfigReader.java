package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;

public interface IConfigReader {

	/**
	 * Returns the list of properties associated with the configtype passed in.
	 * The list also includes the properties defined in the super class.
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public abstract List<SynProperty> getPropertyList(String type);

	/**
	 * Returns the property for the given type and propertyname.
	 * @param typeName
	 * @param propertyName
	 * @return
	 */
	public abstract SynProperty getProperty(String typeName, String propertyName);

	/**
	 * Returns the propertyHelper to work with property config.
	 * @param type
	 * @param property
	 * @return
	 */
	public abstract LocalPropertyConfig getPropertyHelper(String type, String property);

	/**
	 * Returns the list of particles associated with the configtype passed in.
	 * The list also includes the particles defined in the super class.
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public abstract List<LocalParticle> getParticleList(String type);

	/**
	 * Returns the particle helper for the type and particle name passed.
	 * @param typeName
	 * @param particleName
	 * @return
	 * @throws Exception
	 */
	public abstract LocalParticleConfig getParticleHelper(String typeName, String particleName);

	/**
	 * Returns the super class for the type passed.
	 * @param particleName
	 * @return
	 */
	public String getSuperParticleName(String particleName);

	/**
	 * Returns the complete map of all elements configured in this instance.
	 * @return
	 */
	public Map<String, Map<String, LocalParticleConfig>> getParticleMaps();

}