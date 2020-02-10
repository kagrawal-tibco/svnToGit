package com.tibco.cep.studio.mapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * A serializable container of EventListeners of all types
 */
@SuppressWarnings("rawtypes")
public class EventListenerList implements Serializable{

	private static final long serialVersionUID = 1L;
	/* Map of Listener-type and corresponding Listeners*/
	transient HashMap<Class, ArrayList<EventListener>> listenermap = new HashMap<Class, ArrayList<EventListener>>();

	public synchronized <T extends EventListener>void add(Class<T> c, T listener) {
		if (listener == null){
			return;
		}
		if(!c.isInstance(listener)){
			throw new IllegalArgumentException("Listner " + listener + "is not of type " + c);
		}
		ArrayList<EventListener> listenerslist = listenermap.get(c);
		if(listenerslist == null){
			listenerslist = new ArrayList<EventListener>();
		}
		listenerslist.add(listener);
		listenermap.put(c, listenerslist);
	}
	
	public synchronized <T extends EventListener> void remove(Class<T> c, T listener) {
		if (listener == null){
			return;
		}
		if(!c.isInstance(listener)){
			throw new IllegalArgumentException("Listner " + listener + "is not of type " + c);
		}
		ArrayList<EventListener> listenerslist = listenermap.get(c);
		if(listenerslist != null){
			listenerslist.remove(listener);
			listenermap.put(c, listenerslist);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EventListener> T[] getListeners(Class<T> c) {
		if(listenermap.get(c) != null){
			T[] temp = (T[]) Array.newInstance(c, listenermap.get(c).size());
			return listenermap.get(c).toArray(temp);
		}
		else{
			T[] temp = (T[]) Array.newInstance(c, 0);
			return temp;
		}
		
		
	}
	
	public void writeObject(ObjectOutputStream s) throws IOException {
		HashMap<Class, ArrayList<EventListener>> lMap = listenermap;
		s.defaultWriteObject();
		
		Set<Class> cSet = lMap.keySet();
		for (Iterator<Class> iterator = cSet.iterator(); iterator.hasNext();) {
			Class class1 = iterator.next();
			ArrayList<EventListener> lList = lMap.get(class1);
			if(lList == null){
				continue;
			}
			for (Iterator<EventListener> iterator2 = lList.iterator(); iterator2.hasNext();) {
				EventListener eventListener = (EventListener) iterator2.next();
				if((eventListener != null) && eventListener instanceof Serializable){
					s.writeObject(class1);
					s.writeObject(eventListener);
				}
			}
		}
		s.writeObject(null);
	}
	
	@SuppressWarnings("unchecked")
	public void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		listenermap.clear();
		s.defaultReadObject();
		Object object;
		
		while ((object = s.readObject()) != null) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			EventListener l = (EventListener)s.readObject();
			add((Class<EventListener>)Class.forName((String)object, true, cl), l);
		}
	}
}
