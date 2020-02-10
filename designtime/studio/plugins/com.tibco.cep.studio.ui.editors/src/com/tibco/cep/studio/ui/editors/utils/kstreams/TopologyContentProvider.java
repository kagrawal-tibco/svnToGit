/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.cep.designtime.core.model.SimpleProperty;

/**
 * @author shivkumarchelwa
 *
 */
public class TopologyContentProvider implements IStructuredContentProvider {

	SimpleProperty topologyProcessorProperty;

	private Topology streamsTopology = new Topology();

	public Topology getStreamsTopology() {
		return this.streamsTopology;
	}

	private void setDestinationProperty() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Writer w = new StringWriter();
			mapper.writeValue(w, streamsTopology);
			topologyProcessorProperty.setValue(w.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void replaceTransformation(int index, Transformation t) {
		streamsTopology.replaceTransformation(index, t);
		setDestinationProperty();
	}

	public void addTransformation(Transformation t) {
		streamsTopology.addTransformation(t);
		setDestinationProperty();
	}

	public void removeTransformation(Transformation t) {
		streamsTopology.removeTransformation(t);
		setDestinationProperty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getElements(Object property) {
		this.topologyProcessorProperty = (SimpleProperty) property;

		String topologyJson = this.topologyProcessorProperty.getValue();
		if (topologyJson == null || topologyJson.isEmpty()) {
			return streamsTopology.getTransformations().toArray();
		}

		try {
			ObjectMapper objMapper = new ObjectMapper();
			streamsTopology = objMapper.readValue(topologyJson, Topology.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return streamsTopology.getTransformations().toArray();
	}

}
