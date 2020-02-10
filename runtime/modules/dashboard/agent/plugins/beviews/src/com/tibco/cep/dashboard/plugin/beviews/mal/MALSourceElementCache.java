package com.tibco.cep.dashboard.plugin.beviews.mal;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.psvr.mal.CacheController;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.xml.data.primitive.ExpandedName;

public class MALSourceElementCache extends CacheController<MALSourceElement> {

	private static MALSourceElementCache instance;

	public static final synchronized MALSourceElementCache getInstance() {
		if (instance == null) {
			instance = new MALSourceElementCache();
		}
		return instance;
	}

	protected MALSourceElementCache() {
		super("MALSourceElementCache", "MALSourceElement Cache");
	}

	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		this.logger = parentLogger;
		this.mode = mode;
		this.properties = properties;
		this.serviceContext = serviceContext;
		this.exceptionHandler = new ExceptionHandler(logger);
		this.messageGenerator = new MessageGenerator("beviews",exceptionHandler);
		super.init(parentLogger, mode, properties, serviceContext);
	}

	public MALSourceElement getMALSourceElement(String guid) {
		return getMALSourceElement(EntityCache.getInstance().getEntity(guid));
	}

	public MALSourceElement getMALSourceElementByFullPath(String fullPath) {
		return getMALSourceElement(EntityCache.getInstance().getEntityByFullPath(fullPath));
	}

	public MALSourceElement getMALSourceElement(ExpandedName name) {
		return getMALSourceElement(EntityCache.getInstance().getEntity(name));
	}

	public MALSourceElement getMALSourceElement(TypeDescriptor typeDescriptor) {
		return getMALSourceElement(EntityCache.getInstance().getEntity(typeDescriptor));
	}

	public MALSourceElement getMALSourceElement(Entity entity){
		MALSourceElement sourceElement = getObject(entity.getGUID());
		if (sourceElement == null) {
			synchronized (this) {
				sourceElement = getObject(entity.getGUID());
				if (sourceElement == null) {
					sourceElement = createSourceElement(entity);
					addObject(entity.getGUID(), sourceElement);
				}
			}
		}
		return sourceElement;
	}


	public List<MALSourceElement> searchByName(String typeName) {
		List<Entity> entities = EntityCache.getInstance().searchByName(typeName);
		List<MALSourceElement> sourceElements = new ArrayList<MALSourceElement>(entities.size());
		for (Entity entity : entities) {
			sourceElements.add(getMALSourceElement(entity));
		}
		return sourceElements;
	}

	private MALSourceElement createSourceElement(Entity entity) {
		if (EntityUtils.isDVM(entity) == true) {
			return new MetricDVMSourceElement((Metric) entity, EntityCache.getInstance().getTypeDescriptor(entity));
		}
		if (entity instanceof Metric) {
			return new MetricSourceElement((Metric) entity, EntityCache.getInstance().getTypeDescriptor(entity));
		}
		if (entity instanceof Concept) {
			return new ConceptSourceElement((Concept) entity, EntityCache.getInstance().getTypeDescriptor(entity), EntityCache.getInstance().isASuperClass(entity));
		}
		throw new IllegalArgumentException(entity.getFullPath()+" is not supported");
	}


}