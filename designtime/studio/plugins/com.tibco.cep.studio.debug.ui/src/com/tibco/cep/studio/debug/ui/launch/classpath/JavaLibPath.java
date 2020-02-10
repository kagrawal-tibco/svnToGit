package com.tibco.cep.studio.debug.ui.launch.classpath;

public abstract class JavaLibPath {
	
	private String libPath;
	
	public JavaLibPath(String libPath) {
		this.libPath = libPath;
	}
	
	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}

	public String getLibPath() {
		return this.libPath;
	}
	
	public boolean equals(Object o) {
		if(this.libPath == null || !(o instanceof JavaLibPath)){
			return false;
		}
		JavaLibPath lib = (JavaLibPath) o;
		return this.libPath.equals(lib.getLibPath());
	}
	
	public int hashCode(){
		return this.libPath.hashCode();
	}
	
	public String toString() {
		return this.libPath;
	}
	
	abstract public boolean isDefault();
}
