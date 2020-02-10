package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityUtils;
import com.tibco.cep.designtime.core.model.Entity;

public class DrilldownQuerySpec extends QuerySpec {

	private static final String DRILLDOWN_KEYWORD = "drilldown";

	private static final long serialVersionUID = -7544914402861177631L;

	private TupleSchema parentSchema;

	public DrilldownQuerySpec(TupleSchema schema) {
		super();
		Entity entity = EntityCache.getInstance().getEntity(schema.getTypeID());
		if (EntityUtils.isDVM(entity) == false){
			throw new IllegalArgumentException(schema+" does not represent a dvm");
		}
		setSchema(schema);
		parentSchema = TupleSchemaFactory.getInstance().getTupleSchema(EntityUtils.getParent(entity));
		selectKeyWord = DRILLDOWN_KEYWORD;
	}

	protected QuerySpec getInstance() {
		QuerySpec clonedQuerySpec = new DrilldownQuerySpec(this.mSchema);
		return clonedQuerySpec;
	}

	@Override
	protected void addFrom(StringBuilder buffer) {
		buffer.append(parentSchema.getScopeName());
	}

	public TupleSchema getParentSchema() {
		return parentSchema;
	}

}