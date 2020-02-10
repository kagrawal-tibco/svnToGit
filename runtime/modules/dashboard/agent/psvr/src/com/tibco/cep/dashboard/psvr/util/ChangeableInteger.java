package com.tibco.cep.dashboard.psvr.util;

/**
 * @author apatil
 *
 */
public class ChangeableInteger implements Comparable<ChangeableInteger>{
    
	int value = 0;
	
	public ChangeableInteger(int value){
		this.value = value;
	}
	
	public void increment(){
		value++;
	}
	
	public void decrement(){
		value--;
	}
	
	public int intValue(){
		return value;
	}
	
	public String toString(){
		return ""+value;
	}

    public void setValue(int value) {
        this.value = value;
    }

    public int compareTo(ChangeableInteger o) {
        if (this == o || this.value == o.value){
            return 0;
        }
        if (this.value > o.value){
            return 1;
        }
        return -1;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if ((obj instanceof ChangeableInteger) == false){
            return false;
        }
        return (this.value == ((ChangeableInteger)obj).value);
    }
}