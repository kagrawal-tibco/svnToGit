package com.tibco.cep.bpmn.model.designtime.utils;

public class Identifier {
	
	private static final String DOT_SEPARATOR = "."; //$NON-NLS-1$
	
	String id;
	
	public Identifier(String id) {
		this.id = id;
	}
	
	private Identifier() {
		id="";
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return id.substring(id.lastIndexOf(DOT_SEPARATOR)+1);
	}
	
	Identifier getParent() {
		if(id.indexOf(DOT_SEPARATOR) != -1) {
			return new Identifier(id.substring(0, id.lastIndexOf(DOT_SEPARATOR)));
		} else {
			return new Identifier();
		}
		
	}
	
	boolean isRoot() {
		return id.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Identifier)) {
			return false;
		}
		Identifier other = (Identifier) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identifier [" + (id != null ? "id=" + id : "") + "]";
	}

//	public static void main(String[] args) {
//		Identifier id = new Identifier("a.b.c");
//		System.out.println(id);
//		System.out.println(id.getParent());
//		System.out.println(id.getParent().getParent());
//		System.out.println(id.getParent().getParent().getParent().isRoot());
//		System.out.println(id.getName());
//		System.out.println(id.getParent().getName());
//		System.out.println(id.getParent().getParent().getName());
//	}

}
