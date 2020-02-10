package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.mal.ConceptSourceElement;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.xml.data.primitive.ExpandedName;

public class TupleSchemaFactory {

	private static TupleSchemaFactory instance;

	public static final synchronized TupleSchemaFactory getInstance() {
		if (instance == null) {
			instance = new TupleSchemaFactory();
		}
		return instance;
	}

	private ConcurrentHashMap<String, TupleSchema> gUIdToTupleSchemaMap;

	private TupleSchemaFactory() {
		gUIdToTupleSchemaMap = new ConcurrentHashMap<String, TupleSchema>();
	}

	public TupleSchema getTupleSchema(String guid) {
		if (StringUtil.isEmptyOrBlank(guid) == true) {
			throw new IllegalArgumentException("invalid guid " + guid);
		}
		Entity entity = EntityCache.getInstance().getEntity(guid);
		if (entity == null) {
			throw new IllegalArgumentException("could not find an entity with id as " + guid);
		}
		return getTupleSchema(entity);
	}

	public TupleSchema getTupleSchema(ExpandedName name) {
		if (name == null) {
			throw new IllegalArgumentException("invalid expanded name " + name);
		}
		Entity entity = EntityCache.getInstance().getEntity(name);
		if (entity == null) {
			throw new IllegalArgumentException("could not find an entity with expanded name as " + name);
		}
		return getTupleSchema(entity);
	}

	public TupleSchema getTupleSchema(Entity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("invalid entity " + entity);
		}
		TupleSchema tupleSchema = gUIdToTupleSchemaMap.get(entity.getGUID());
		if (tupleSchema == null) {
			if (entity instanceof Concept) {
				ConceptSourceElement sourceElement = (ConceptSourceElement) MALSourceElementCache.getInstance().getMALSourceElement(entity);
				tupleSchema = createTupleSchema(new ConceptTupleSchemaSource(sourceElement), false);
				gUIdToTupleSchemaMap.put(entity.getGUID(), tupleSchema);
			}
		}
		return tupleSchema;
	}

	public TupleSchema getDynamicBaseTupleSchema(String guid) {
		if (StringUtil.isEmptyOrBlank(guid) == true) {
			throw new IllegalArgumentException("invalid guid " + guid);
		}
		Entity entity = EntityCache.getInstance().getEntity(guid);
		if (entity == null) {
			throw new IllegalArgumentException("could not find an entity with id as " + guid);
		}
		ConceptSourceElement sourceElement = (ConceptSourceElement) MALSourceElementCache.getInstance().getMALSourceElement(entity);
		return createTupleSchema(new ConceptDynamicTupleSchemaSource(sourceElement), true);
	}

	private TupleSchema createTupleSchema(ConceptTupleSchemaSource schemaSource, boolean dynamic) {
		return new ConceptTupleSchema(schemaSource, dynamic);
	}

}