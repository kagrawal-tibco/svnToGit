package com.tibco.cep.runtime.service.loader;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ClassLoaderReference;
import com.sun.jdi.Field;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.tibco.be.model.util.ModelNameUtil;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Dec 19, 2005
 * Time: 2:35:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClassManager extends ClassLoader {

    private static final String BRK = System.getProperty("line.separator", "\n");


    String id;  //don't change the name of this field
    int jdiAddress;
    Map active;
    Map inactive;
    Map resourceMap;
    static Object instrument;
    static Class  instumentClass;

    static {
        try {
            instumentClass = Class.forName("java.lang.instrument.Instrumentation");
            //JDK 1.5 is used
            Class c = Class.forName("com.tibco.cep.runtime.service.loader.InstrumentLoader");
            java.lang.reflect.Method m = c.getMethod("getInstrumentation");
            instrument = m.invoke(null);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (java.lang.SecurityException e) {
//            e.printStackTrace();
        }
    }

    public ClassManager(ClassLoader parent, int jdiPort) {
        super(parent);
        id       = Math.random() + "." + System.currentTimeMillis() + "." + this.toString();  //assume this is unique in a machine
        active   = new HashMap ();
        inactive = new HashMap ();
        resourceMap = new HashMap();
        jdiAddress = jdiPort;
    }

    public ClassManager(int jdiPort) {
        super();
        id         = Math.random() + "." + System.currentTimeMillis() + "." + this.toString();  //assume this is unique in a machine
        active     = new HashMap ();
        inactive   = new HashMap ();
        resourceMap = new HashMap();
        jdiAddress = jdiPort;
    }

    protected void declarePackage(String classname) {
        //todo: check for seal package??
        String packageName = null;
        int pos = classname.lastIndexOf('.');
        if (pos != -1)
            packageName = classname.substring(0, pos);

        if (packageName != null) {
            Package pkg = getPackage(packageName);
            if (pkg == null) {
                definePackage(packageName, null, null, null, null, null, null, null);
            }
        }
    }

    protected Class findClass(String name) throws ClassNotFoundException {
        declarePackage(name);
        ClassInfo info = (ClassInfo) active.get(name);
        if(info != null) {
            if(info.jclass == null) {
                final byte[] convertedByteCode;
                try {
                    convertedByteCode = this.getConvertedByteCode(info);
                } catch (Exception e) {
                    throw new ClassNotFoundException(e.getClass().getName() + ": " + e.getMessage());
                }
                info.jclass = this.defineClass(info.className, convertedByteCode, 0, convertedByteCode.length);
            }
            return info.jclass;
        }
        info = (ClassInfo) inactive.get(name);
        if(info != null) {
            //class have been deactivated
            //todo - should I throw new ClassNotFoundException(name);
            return info.jclass;
        }
        else {
            return super.findClass(name);
        }

    }

    
    protected byte[] getConvertedByteCode(ClassInfo classInfo) throws Exception {
        return classInfo.byteCode;
    }


    public Class loadClass(String name) throws ClassNotFoundException {
        ClassInfo info = (ClassInfo) active.get(name);
        if(info != null) {
            if(info.jclass == null) {
                info.jclass = super.loadClass(name,true);
            }
            return info.jclass;
        }
        info = (ClassInfo) inactive.get(name);
        if(info != null) {
            //class have been deactivated
            //todo - should I throw new ClassNotFoundException(name);
            return info.jclass;
        }
        else {
            return super.loadClass(name,true);
        }
    }

    /**
     * Return a list of AnalyzeResult for the input ByteCode
     * @param nameToByteCode Map of Class name and Byte Code
     * @return a list of AnalyzeResult
     */
    public Map analyzeByteCodes(Map nameToByteCode) {
        return analyzeByteCodes(nameToByteCode, false);
    }
    //when incremental is true, something like DecisionTable hotdeploy is happening
    //where only the new decision tables are available instead of an entire EAR
	public Map analyzeByteCodes(Map nameToByteCode, boolean incremental) {
		Map retMap = new HashMap();
		if (nameToByteCode == null)
			return retMap;
		Iterator ite = nameToByteCode.entrySet().iterator();
		Map tempAllActive = new HashMap(active);
		while (ite.hasNext()) {
			Map.Entry e = (Map.Entry) ite.next();
			String className = (String) e.getKey();
			byte[] byteCode = (byte[]) e.getValue();
			if (isJavaClass(byteCode)) {
				// check if it's a VRF
				if (ModelNameUtil.isVRFImplClass(className)) {
					retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.VRF_ADD));
				} else {
					// check if it is in active map
					ClassInfo info = (ClassInfo) active.get(className);
					if (info == null) { // not active
						info = (ClassInfo) inactive.get(className);
						if (info == null) { // new class
							retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.NEW));
						} else { // (reactive)
							if (different(info.byteCode, byteCode)) { // reactivate
																		// changed
								retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.REACTIVATE_CHANGED));
							} else { // reactivate no change
								retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.REACTIVATE_NOCHANGE));
							}
						}
					} else { // active class
						if (different(info.byteCode, byteCode)) { // changed
							retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.CHANGED));
						} else { // no change
							// don't do anything
							retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.NOCHANGE));
						}
					}
				}

			} else {
				// Resource files
				retMap.put(className, new ClassLoadingResult(className, byteCode, ClassLoadingResult.RESOURCE));
			}
			// remove from all temp active list
			tempAllActive.remove(className);
		}

		// for now incremental mode cannot delete or deactivate classes
		if (!incremental) {
			// need to find out deleted one
			ite = tempAllActive.entrySet().iterator();
			while (ite.hasNext()) {
				Map.Entry e = (Map.Entry) ite.next();
				String className = (String) e.getKey();
				ClassInfo info = (ClassInfo) e.getValue();
				int result;
				if (ModelNameUtil.isVRFImplClass(className)) {
					result = ClassLoadingResult.VRF_REMOVE;
				} else if (ModelNameUtil.isProcessClass(className)) {
					result = ClassLoadingResult.NOCHANGE;
				} else {
					result = ClassLoadingResult.DEACTIVATE;
				}
				retMap.put(className, new ClassLoadingResult(className, info.byteCode, result));
			}
		}
		return retMap;
	}
    
	public static boolean isJavaClass(byte[] bytecode) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytecode);
			DataInputStream dis = new DataInputStream(bais);
			int magicNumber = 0;;
			try {
				magicNumber = dis.readInt();
			} finally {
				dis.close();
				bais.close();
			}
			
			return magicNumber == 0xCAFEBABE;
		} catch (IOException e) {
			return false;
		}

	}
    
    protected Map<String, ClassLoadingResult> deactivate(Collection<String> classNames) {
        HashMap<String, ClassLoadingResult> retMap = new HashMap<String, ClassLoadingResult>();
        if(classNames != null && active != null) {
            for(String className : classNames) {
                ClassInfo cinfo = (ClassInfo)active.get(className);
                if(cinfo != null) {
                    int result;
                    if(ModelNameUtil.isVRFImplClass(className)) {
                        result = ClassLoadingResult.VRF_REMOVE;
                    } else {
                        result = ClassLoadingResult.DEACTIVATE;
                    }
                    retMap.put(className, new ClassLoadingResult(className, cinfo.byteCode, result));
                }
            }
        }
        return retMap;
    }

    public String ResultListToString(Map resultMap) {
        Set sortedSet = new TreeSet(new ClassLoadingResult.ResultComparator());
        sortedSet.addAll(resultMap.values());
        StringBuffer buf = new StringBuffer();
        Iterator ite = sortedSet.iterator();
        while(ite.hasNext()) {
            ClassLoadingResult result = (ClassLoadingResult) ite.next();
            switch(result.status) {
               case ClassLoadingResult.NEW:
                   buf = buf.append("\t[new class " + result.className + "]" + BRK);
                   break;
               case ClassLoadingResult.CHANGED:
                   buf = buf.append("\t[chg class " + result.className + "]" + BRK);
                   break;
               case ClassLoadingResult.DEACTIVATE:
                   buf = buf.append("\t[del class " + result.className + "]" + BRK);
                   break;
               case ClassLoadingResult.REACTIVATE_NOCHANGE:
                   buf = buf.append("\t[reactivate class " + result.className + "]" + BRK);
                   break;
                case ClassLoadingResult.REACTIVATE_CHANGED:
                    buf = buf.append("\t[react&chg class " + result.className + "]" + BRK);
                    break;
                case ClassLoadingResult.NOCHANGE:
                    buf = buf.append("\t[nochange class " + result.className + "]" + BRK);
                    break;
                case ClassLoadingResult.VRF_ADD:
                    buf = buf.append("\t[add VRF class " + result.className + "]" + BRK);
                    break;
                case ClassLoadingResult.VRF_REMOVE:
                    buf = buf.append("\t[remove VRF class " + result.className + "]" + BRK);
                    break;
           }
        }
        return buf.toString();
    }

    public boolean hasChange(List resultList) {
        Iterator ite = resultList.iterator();
        while(ite.hasNext()) {
            ClassLoadingResult result = (ClassLoadingResult) ite.next();
            if(result.status != ClassLoadingResult.NOCHANGE) {
                return true;
            }
        }
        return false;
    }


    synchronized public void commitChanges(Map resultMap) throws Exception {
        Map  newActive   = new HashMap (active);
        Map  newInactive = new HashMap (inactive);
        Map  newResource = new HashMap (resourceMap);
        Map  redefineMap    = new HashMap ();  //className to byte[] map

        Iterator i = resultMap.values().iterator();
        while (i.hasNext()) {
            ClassLoadingResult result = (ClassLoadingResult) i.next();
            switch (result.status) {
                case ClassLoadingResult.NEW:
                    activateClass(result.className, result.byteCode, newActive);
                    break;
                case ClassLoadingResult.CHANGED:
                    modifyClass(result.className, result.byteCode, newActive, redefineMap);
                    break;
                case ClassLoadingResult.DEACTIVATE:
                    deactivateClass(result.className, newActive, newInactive);
                    break;
                case ClassLoadingResult.REACTIVATE_NOCHANGE:
                    reactivateClass(result.className, newActive, newInactive);
                    break;
                case ClassLoadingResult.REACTIVATE_CHANGED:
                    reactivateModifyClass(result.className, result.byteCode, newActive, newInactive, redefineMap);
                    break;
                case ClassLoadingResult.RESOURCE:
                	activateResource(result.className, result.byteCode, newResource);
                	break;
//               case AnalyzeResult.NOCHANGE:  //don't do anything for this case
            }
        }
        redefineClasses(redefineMap);
        active   = newActive;
        inactive = newInactive;
        resourceMap = newResource;
    }

    public boolean activateClass(String name, byte[] byteCode) {
        ClassInfo info = (ClassInfo) active.remove(name);
        if (info != null) {
            //ClassInfo info    = new ClassInfo(name, byteCode);
            info.byteCode=byteCode;
            info.lastLoadTime=new Date();
            active.put(name, info);
            return true;
        } else {
            return false;
        }
    }
    
    //this is for loading VRF from CacheCluster.redefineClasses
    public void addNewToActive(String className, byte[] classBytes) {
        if(active.get(className) == null) {
            ClassInfo info = new ClassInfo(className, classBytes);
            info.lastLoadTime=new Date();
            active.put(className, info);
        }
    }
    
    protected void activateResource(String name, byte[] byteCode, Map newActiveMap) {
        ClassInfo info    = new ClassInfo(name, byteCode);
        newActiveMap.put(name, info);
    }

    protected void activateClass(String name, byte[] byteCode, Map newActiveMap) {
        ClassInfo info    = new ClassInfo(name, byteCode);
        newActiveMap.put(name, info);
    }

    protected void modifyClass(String name, byte[] byteCode, Map newActiveMap, Map redefineMap) {
        ClassInfo info = (ClassInfo) newActiveMap.remove(name);
        ClassInfo newInfo = new ClassInfo(info);
        newInfo.byteCode  = byteCode;
        newInfo.lastLoadTime = new Date();
        newActiveMap.put(name, newInfo);
        redefineMap.put(name, byteCode);
    }

    protected void deactivateClass(String name, Map newActiveMap, Map newInactiveMap) {
        ClassInfo info = (ClassInfo) newActiveMap.remove(name);
        ClassInfo newInfo = new ClassInfo(info);
        newInfo.unloaded  = true;
        newInactiveMap.put(name, newInfo);
    }

    protected void reactivateClass(String name, Map newActiveMap, Map newInactiveMap) {
        ClassInfo info       = (ClassInfo) newInactiveMap.remove(name);
        ClassInfo newInfo    = new ClassInfo(info);
        newInfo.lastLoadTime = new Date();
        newInfo.unloaded     = false;
        newActiveMap.put(name, info);
    }

    protected void reactivateModifyClass(String name, byte[] byteCode, Map newActiveMap, Map newInactiveMap, Map redefineMap) {
        ClassInfo info       = (ClassInfo) newInactiveMap.remove(name);
        ClassInfo newInfo    = new ClassInfo(info);
        newInfo.lastLoadTime = new Date();
        newInfo.unloaded     = false;
        newInfo.byteCode     = byteCode;
        newActiveMap.put(name, newInfo);
        redefineMap.put(name, byteCode);
    }

    public void redefineClasses(Map redefineMap) throws Exception {
        if(redefineMap.size() == 0 ) return ;

        if(instrument != null) {
            Class classDefinationClass = Class.forName("java.lang.instrument.ClassDefinition");
            Object[] arr = (Object[]) java.lang.reflect.Array.newInstance(classDefinationClass, redefineMap.size());
            Constructor cc = classDefinationClass.getConstructor(new Class[] {Class.class, byte[].class});

//            java.lang.instrument.ClassDefinition[] arr = new java.lang.instrument.ClassDefinition[redefineMap.size()];
            int i = 0;

            Iterator ite = redefineMap.entrySet().iterator();
            while(ite.hasNext()) {
                Map.Entry e = (Map.Entry) ite.next();
                String name = (String) e.getKey();
                byte[] code = (byte[]) e.getValue();
                Class  clzz = findClass(name);
                arr[i] = cc.newInstance(new Object[] {clzz, code});
//                arr[i] = new java.lang.instrument.ClassDefinition(clzz, code);
                i++;
            }
            java.lang.reflect.Method m = instumentClass.getMethod("redefineClasses", new Class[] {arr.getClass()});
            try {
                m.invoke(instrument, new Object[] {arr});
            } catch(InvocationTargetException e) {
                throw new IllegalArgumentException("Failed to redefine existing Ontology classes.", e.getCause());
            }

//            defaultInstrument.redefineClasses(arr);
        }
        else {
            Map redefineClassRefMap    = new HashMap();
            VirtualMachine vm          = getDebuggerVM(jdiAddress);
            ClassLoaderReference clRef = getClassLoaderReference(vm);

    //System.out.println("redefining classes...");
    //System.out.println("class loader Ref = " + clRef);

            Iterator ite = redefineMap.entrySet().iterator();
            while(ite.hasNext()) {
                Map.Entry e = (Map.Entry) ite.next();
                String name = (String) e.getKey();
    //System.out.println("className = " + name);

                byte[] code = (byte[]) e.getValue();
                List l = vm.classesByName(name);

    //System.out.println("number of class ref in VM = " + l.size());

                boolean found = false;
                for(int j = 0; j < l.size(); j++) {
                    ReferenceType classRef = (ReferenceType) l.get(j);
    //System.out.println("\tclass ref[" + j+ "] = " + classRef + ", class loader ref = " + classRef.classLoader());
                    if (classRef.classLoader().equals(clRef)) {
    //System.out.println("\tsame class loader ref");
                        redefineClassRefMap.put(classRef, code);
                        found = true;
                        break;
                    }
                    else {
    //System.out.println("\tdifferent class loader ref");
                    }
                }
                if(found == false) {
    //System.out.println("\tclass " + name + " was not loaded earlier");
                }
            }
    //System.out.println("redefineMap.size = " + redefineMap.size() + ", redefineClassRefMap.size = " + redefineClassRefMap.size());

    //        if(redefineMap.size() != redefineClassRefMap.size()) {
    //            throw new RuntimeException("ProgramError: Input classNameMap is different from classRefMap");
    //        }
            try {
                vm.redefineClasses(redefineClassRefMap);
            } catch(Throwable t) {
                throw new IllegalArgumentException("Failed to redefine existing Ontology classes.", t);
            }

            vm.dispose();
            vm = null;
        }
    }

    private ClassLoaderReference getClassLoaderReference(VirtualMachine vm) {
//System.out.println("getClassLoaderReference start");
        List aa = vm.classesByName(this.getClass().getName());
        ReferenceType classLoaderClassRef = (ReferenceType) aa.get(0);
        Field tempField = classLoaderClassRef.fieldByName("id");

        String className = null;

        if(active.size() > 0) {
            className = (String) active.keySet().iterator().next();
//System.out.println("getClassLoaderReference className = " + className);
        }
        else if (inactive.size() > 0) {
            className = (String) inactive.keySet().iterator().next();
        }
        else {
            return null;
        }

        List l = (List) vm.classesByName(className);
        for(int j = 0; j < l.size(); j++) {
            ReferenceType classRef = (ReferenceType) l.get(j);
            ClassLoaderReference clRef = classRef.classLoader();
            Value v = clRef.getValue(tempField);
//    System.out.println("************************************** v.toString = " + v.toString());
//    System.out.println("************************************** id = " + id);
            if(v.toString().equals("\"" + id + "\"")) {
                return clRef;
            }
        }

//System.out.println("Can't find the ClassLoaderReference");

        throw new RuntimeException("ProgramError: Can't find the ClassLoaderReference");
    }

    public boolean useJDIforHotdeploy() {
        return instrument == null;
    }

    public void checkJVM() throws Exception {
        //Check min version of JVM
        if(System.getProperty("java.vendor").startsWith("Sun Microsystems")) {
            String jvmVersion = System.getProperty("java.version");
            if(jvmVersion.startsWith("1.5.0")) {
                String strUpdate = jvmVersion.substring(jvmVersion.indexOf("_")+1);
                try{
                    int iUpdate = Integer.parseInt(strUpdate);
                    if(iUpdate < 8)
                        throw new Exception();
                }
                catch(Exception ex) {
                    throw new Exception("Min version of " + System.getProperty("java.vendor") + " JVM required for Hot deployment is 1.5.0_08, currently running version " + jvmVersion);
                }
            }
        }
        else if(System.getProperty("java.vendor").startsWith("Hewlett-Packard")) {
            String jvmVersion = System.getProperty("java.version");
            if(jvmVersion.startsWith("1.5.0")) {
                String strUpdate = jvmVersion.substring(jvmVersion.lastIndexOf(".")+1);
                try{
                    int iUpdate = Integer.parseInt(strUpdate);
                    if(iUpdate < 5)
                        throw new Exception();
                }
                catch(Exception ex) {
                    throw new Exception("Min version of " + System.getProperty("java.vendor") + " JVM required for Hot deployment is 1.5.0.05, currently running version " + jvmVersion);
                }
            }
        }
    }

    public String hotdeployMethod() {
        if (useJDIforHotdeploy()) {
            return "JDI Address: " + jdiAddress;
        }
        else {
            return "Java Instrumentation: " + instrument.getClass().getName();
        }
    }

    public String classesNotRedefineableMsg() throws Exception {
        if(instrument != null) {
            java.lang.reflect.Method m = instumentClass.getMethod("isRedefineClassesSupported");

            if ((Boolean) m.invoke(instrument)) {
                checkJVM();
                return null;
            }
            else
                return "This JVM doesn't support redefination of classes. java.lang.Instrumentation=" + instrument;
        }
        else {
            VirtualMachine vm = null;
            try {
                 vm = getDebuggerVM(jdiAddress);
                if(vm != null) {
                    if(getClassLoaderReference(vm) != null)
                        return null;
                    else
                        return "Unable to use JDI service to redefine classes, JDI address = " + Integer.toString(jdiAddress);
                }
                else {
                    return "Unable to use JDI service to redefine classes, JDI address = " + Integer.toString(jdiAddress);
                }
            }
            catch(Exception ex) {
                return "Unable to use JDI service to redefine classes, JDI address = " + Integer.toString(jdiAddress) + ". " + ex.getMessage();
            }
            finally {
                if(vm != null) {
                    vm.dispose();
                    vm = null;
                }
            }
        }
    }

    /**
     * Returns the bytecode of the specified class name from the active list
     * @param classname
     * @return byte[]
     */
    protected byte[] getByteCode(String classname) {
    	ClassInfo info = (ClassInfo) active.get(classname);
    	if (info != null) {
    		return info.byteCode;
    	}
    	return null;
    }

    /**
     * Returns true if the active list contains the class
     * @param classname
     * @return boolean
     */
    protected boolean containsClass(String classname) {
        return active.containsKey(classname);
    }

    /**
     * returns the number of classes in the active list
     * @return int
     */
    protected int size() {
        return active.size();
    }

    protected Collection getActiveClassNames() {
        if(null != active && active.size() > 0) {
            return Arrays.asList(active.keySet().toArray(new String[0]));
        } else  {
            return new ArrayList();
        }
    }

    private static VirtualMachine getDebuggerVM(int listenerPort) throws IOException, IllegalConnectorArgumentsException {
        AttachingConnector _connector = null;
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
        List attachingConnectors = vmm.attachingConnectors();
        Iterator it = attachingConnectors.iterator();
        while(it.hasNext()) {
            Connector connector = (Connector) it.next();
            String name = connector.transport().name();
            if(name.equals("dt_socket")) {
                _connector = (AttachingConnector) connector;
                break;
            }
        }
        Map connectorArgs = _connector.defaultArguments();
        Connector.Argument port = (Connector.Argument) connectorArgs.get("port");
        port.setValue(String.valueOf(listenerPort));
        return _connector.attach(connectorArgs);
    }

    private static boolean different(byte[] b1, byte[] b2) {
        if(b1 == b2) return false;
        if(b1.length != b2.length) return true;
        for(int i = 0; i < b1.length; i++) {
            if(b1[i] != b2[i]) return true;
        }
        return false;
    }

    static public class ClassInfo {
        final String  className;
        byte[]  byteCode;
        Date    initLoadTime;
        Date    lastLoadTime;
        boolean unloaded;
        Class   jclass;

        ClassInfo(String className, byte[] byteCode) {
            this.className    = className;
            this.byteCode     = byteCode;
            this.initLoadTime = new Date();
            this.lastLoadTime = this.initLoadTime;
            this.unloaded     = false;
        }

        ClassInfo(ClassInfo classInfo) {
            className    = classInfo.className;
            byteCode     = classInfo.byteCode;
            initLoadTime = classInfo.initLoadTime;
            lastLoadTime = classInfo.lastLoadTime;
            unloaded     = classInfo.unloaded;
            jclass       = classInfo.jclass;
        }
    }

    static public class ClassLoadingResult {
        public final String className;
        public final byte[] byteCode;
        public final int    status;
        
        public ClassLoadingResult(String name, byte[] code, int type) {
            className = name;
            byteCode  = code;
            status    = type;
        }
        
        static public final int NEW                 = 0;
        static public final int CHANGED             = 1;
        static public final int DEACTIVATE          = 2;
        static public final int REACTIVATE_NOCHANGE = 3;
        static public final int REACTIVATE_CHANGED  = 4;
        static public final int NOCHANGE            = 5;
        static public final int VRF_ADD             = 6;
        static public final int VRF_REMOVE          = 7;
        static public final int RESOURCE            = 8;

        static private class ResultComparator implements Comparator {
            public int compare(Object o1, Object o2) {
                int ret = ((ClassLoadingResult)o1).className.compareTo(((ClassLoadingResult)o2).className);
                if(ret==0) {
                    return ((ClassLoadingResult)o1).status - ((ClassLoadingResult)o2).status;
                }
                else {
                    return ret;
                }
            }
        }
        
        public String toString() {
        	if (this.className != null) {
        		return this.className;
        	}
        	else {
        		return super.toString();
        	}
        }
    }
}
