/**
 * 
 */
package com.tibco.cep.studio.common.legacy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ParentResource;


/**
 * @author aathalye
 *
 */
public class AbstractResourceVisitor<A extends AbstractResource> {
	
	private A wrapped;
	
	private List<AbstractResourceVisitor<A>> list = 
				new ArrayList<AbstractResourceVisitor<A>>();
	
	private boolean visited;
	
	@SuppressWarnings("unchecked")
	public AbstractResourceVisitor(final A abstractResource) {
		this.wrapped = abstractResource;
		if (abstractResource instanceof ParentResource) {
			Iterator<? extends AbstractResource> children = 
				((ParentResource)abstractResource).getChildren();
			while (children.hasNext()) {
				AbstractResource ar = children.next();
				list.add(new AbstractResourceVisitor(ar));
			}
		}
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(final boolean visited) {
		this.visited = visited;
	}
	
	public boolean hasUnvisitedChildren() {
		if (!(wrapped instanceof ParentResource)) {
			return false;
		}
		for (AbstractResourceVisitor<A> arw : list) {
			//If a single child is also unvisited return true
			if (!arw.isVisited()) {
				return true;
			}
		}
		return false;
	}
	
	public Iterator<AbstractResourceVisitor<A>> getUnvisitedChildren() {
		Iterator<AbstractResourceVisitor<A>> iter;
		if (!(wrapped instanceof ParentResource)) {
			return null;
		}
		//ParentResource pr = (ParentResource)wrapped;
		List<AbstractResourceVisitor<A>> unvisited = 
				new ArrayList<AbstractResourceVisitor<A>>();
		if (!list.isEmpty()) {
			for (int loop = 0; loop < list.size(); loop++) {
				AbstractResourceVisitor<A> wrapper = list.get(loop);
				if (!wrapper.isVisited()) {
					unvisited.add(wrapper);
				}
			}
			iter = unvisited.iterator();
		} 
		iter = list.iterator();
		return iter;
	}
	
	public A getWrappedResource() {
		return wrapped;
	}
}
