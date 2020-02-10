package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

/**
 * The <code>SearchPath</code> represents one path from a top level element to a 
 * target element.
 * 
 * @author anpatil
 *
 */
public final class SearchPath implements Cloneable {

	//what is the type of the target element?
	private String targetType;

	//the path to the element referencing the target element 
	private LinkedList<PathElement> path;

	//the location of the target element in the referencing element
	//can be the name of a particle or a property
	private String targetLocation;

	//the name by which the target element is referenced
	private String referencedAs;

	//The particle referencing the target element
	private LocalParticle referencingParticle;

	//The property referencing the target element
	private SynProperty referencingProperty;

	public SearchPath() {
		path = new LinkedList<PathElement>();
	}

	/**
	 * Adds path element to the bottom of the path list
	 * @param name The name of the particle
	 * @param type The type of the particle
	 */
	void addPathElement(String name, String type) {
		addPathElement(name, type, false);
	}

	/**
	 * Adds path element to the bottom of the path list
	 * @param name The name of the particle
	 * @param type The type of the particle
	 * @param repeatitive Is the path element repeatitive?
	 */
	void addPathElement(String name, String type, boolean repeatitive) {
		path.addLast(new PathElement(name, type, repeatitive));
	}

	/**
	 * Returns the path
	 * @return
	 */
	public List<PathElement> getPath() {
		return path;
	}

	/**
	 * Returns the first element in the path
	 * @return
	 */
	public PathElement getFirstPathElement() {
		return path.getFirst();
	}
	
	/**
	 * Returns the element after the path element in the path
	 * @param pathElement
	 * @return
	 */
	public PathElement getNextPathElement(PathElement pathElement) {
		int i = path.indexOf(pathElement);
		if (i == -1) {
			throw new IllegalArgumentException(pathElement + " does not exist in " + StringUtil.fromList(path, "", "/", ""));
		}
		int nextIdx = i + 1;
		if (nextIdx == path.size()) {
			return null;
		}
		return path.get(nextIdx);
	}

	/**
	 * Returns the last element in the path
	 * @return
	 */
	public PathElement getLastPathElement() {
		return path.getLast();
	}

	/**
	 * Returns the element before the path element in the path
	 * @param pathElement
	 * @return
	 */
	public PathElement getPrevPathElement(PathElement pathElement) {
		int i = path.indexOf(pathElement);
		if (i == -1) {
			throw new IllegalArgumentException(pathElement + " does not exist in " + StringUtil.fromList(path, "", "/", ""));
		}
		int prevIdx = i - 1;
		if (prevIdx == -1) {
			return null;
		}
		return path.get(prevIdx);
	}

	/**
	 * Returns the target type 
	 * @return
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * Returns the target location
	 * @return
	 */
	public String getTargetLocation() {
		return targetLocation;
	}

	/**
	 * Returns the particle referencing the target element
	 * @return
	 */
	public LocalParticle getReferencingParticle() {
		return referencingParticle;
	}

	/**
	 * Sets the particle referencing the target element
	 * @param referencingParticle
	 */
	final void setReferencingParticle(LocalParticle referencingParticle) {
		this.referencingParticle = referencingParticle;
		this.referencedAs = this.referencingParticle.getName();
		this.targetLocation = referencingParticle.getName();
		this.targetType = referencingParticle.getTypeName();
	}

	/**
	 * Returns the property referencing the target element
	 * @return
	 */
	public SynProperty getReferencingProperty() {
		return referencingProperty;
	}

	/**
	 * Sets the property referencing the target element
	 * @param referencingProperty
	 * @param targetType the target type of the target element
	 */
	final void setReferencingProperty(SynProperty referencingProperty, String targetType) {
		this.referencingProperty = referencingProperty;
		this.referencedAs = referencingProperty.getName();
		this.targetLocation = referencingProperty.getName();
		this.targetType = targetType;
	}

	/**
	 * Returns how the target element is referenced
	 * @return
	 */
	public String getReferencedAs() {
		return referencedAs;
	}

	/**
	 * Is target element referenced in a attribute
	 * @return
	 */
	public boolean isAttribute() {
		return referencingProperty != null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((referencedAs == null) ? 0 : referencedAs.hashCode());
		result = prime * result + ((targetLocation == null) ? 0 : targetLocation.hashCode());
		result = prime * result + ((targetType == null) ? 0 : targetType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchPath other = (SearchPath) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (referencedAs == null) {
			if (other.referencedAs != null)
				return false;
		} else if (!referencedAs.equals(other.referencedAs))
			return false;
		if (targetLocation == null) {
			if (other.targetLocation != null)
				return false;
		} else if (!targetLocation.equals(other.targetLocation))
			return false;
		if (targetType == null) {
			if (other.targetType != null)
				return false;
		} else if (!targetType.equals(other.targetType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SearchPath[");
		sb.append("targetType=" + targetType);
		sb.append(",path=" + StringUtil.fromList(path, "", "/", ""));
		if (referencingProperty == null) {
			sb.append(",targetLocation=" + targetLocation);
		} else {
			sb.append(",targetLocation=@" + targetLocation);
		}
		sb.append(",referencedAs=" + referencedAs);
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		SearchPath clone = new SearchPath();
		clone.path = new LinkedList<PathElement>(this.path);
		clone.referencedAs = this.referencedAs;
		clone.referencingParticle = this.referencingParticle;
		clone.referencingProperty = this.referencingProperty;
		clone.targetLocation = this.targetLocation;
		clone.targetType = this.targetType;
		return clone;
		
	}

	class PathElement {

		private String name;

		private boolean repetitive;

		private String type;

		PathElement(String name, String type) {
			this(name, type, false);
		}

		PathElement(String name, String type, boolean repetitive) {
			this.name = name;
			this.type = type;
			this.repetitive = repetitive;
		}
		
		public final String getName() {
			return name;
		}

		public final boolean isRepetitive() {
			return repetitive;
		}
		
		final void setRepetitive() {
			this.repetitive = true;
		}

		public final String getType() {
			return type;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + (repetitive ? 1231 : 1237);
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PathElement other = (PathElement) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (repetitive != other.repetitive)
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append("[");
			sb.append(type);
			if (repetitive == true) {
				sb.append(",R");
			}
			sb.append("]");
			return sb.toString();
		}

	}

}