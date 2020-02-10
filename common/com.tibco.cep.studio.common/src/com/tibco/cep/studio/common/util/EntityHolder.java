/**
 * 
 */
package com.tibco.cep.studio.common.util;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * @author rmishra
 *
 */
public class EntityHolder {
	private List<Entity> entity;
	public List<Entity> getEntity() {
		if (entity == null){
			entity = new ArrayList<Entity>();
		}
		return entity;
	}
	public void setEntity(List<Entity> entity) {
		this.entity = entity;
	}
	public EntityHolder(){
		
	}
}
