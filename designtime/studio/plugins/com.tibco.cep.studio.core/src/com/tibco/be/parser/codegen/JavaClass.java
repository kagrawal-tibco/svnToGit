package com.tibco.be.parser.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.Modifier;
import javassist.NotFoundException;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 2, 2004
 * Time: 5:54:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class JavaClass {
    private static final String BRK = CGConstants.BRK;
    protected String access;
    protected String name;
    protected String superclass = null;
    protected ArrayList fieldsAndInits = new ArrayList(5);
    protected ArrayList staticFieldsAndInits = new ArrayList<String>(0);
    protected ArrayList<String> interfaces = new ArrayList<String>(5);
    //protected ArrayList<MemberRec> members = new ArrayList<MemberRec>(5);
    protected ArrayList<MethodRec> methods = new ArrayList<MethodRec>(5);
    protected ArrayList<JavaClass> classes = new ArrayList<JavaClass>(5);
    protected JavaClass parentClass = null;
    protected String sourceText = null;
    //public CtClass ctClass = null;
    
    public JavaClass(String name) {
        this.name = name;
    }
    
    public void setAccess(String access) {
        this.access = access;
    }
        
    public void setSuperClass(String superclassName) {
        superclass = superclassName;
    }

    public void addInterface(String interfaceName) {
        interfaces.add(interfaceName);
    }

    public void addMember(String access, String type, String name, String initializer) {
        //members.add(new MemberRec(access, type, name));
        ArrayList list;
        if(access.contains("static")) list = staticFieldsAndInits;
        else list = fieldsAndInits;
        list.add(new MemberRec(access, type, name, initializer));
    }
    
    public void addMember(String access, String type, String name) {
        addMember(access, type, name, null);
    }
    
    public void addInitializer(boolean isStatic, StringBuilder body) {
        ArrayList list;
        if(isStatic) list = staticFieldsAndInits;
        else list = fieldsAndInits;
        
        list.add(new InitializerRec(isStatic, body.toString()));
    }

    public void addMethod(MethodRec method) {
        methods.add(method);
    }
    
    public void addClass(JavaClass clazz) {
        clazz.parentClass = this;
        classes.add(clazz);
    }
    
    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }
    
    public String getSourceText() {
        return sourceText;
    }

    public String getName() {
        if(parentClass != null) {
            return parentClass.getName() + "$" + name;
        } else {
            return name;
        }
    }
    
    public String getLocalName() {
        return name;
    }

    public Iterator getClasses() {
        return classes.iterator();
    }
    
    public StringBuilder toStringBuilder() {
        StringBuilder ret = new StringBuilder();
        
        if(access != null && !access.equals("")) {
            ret.append(access);
            ret.append(" ");
        }
        ret.append("class ");
        ret.append(name);
        ret.append(" ");
        if(superclass != null && !superclass.equals("")) {
            ret.append("extends ");
            ret.append(superclass);
            ret.append(" ");
        }
        for(int ii = 0; ii < interfaces.size(); ii++) {
            if(ii == 0) {
                ret.append("implements ");
            } else {
                ret.append(", ");
            }
            ret.append((String)interfaces.get(ii));
        }
        ret.append("{" + BRK+BRK);
        
        for(Object obj : staticFieldsAndInits) {
            ret.append(obj.toString());
        }
        for(Object obj : fieldsAndInits) {
            ret.append(obj.toString());
        }
        
        for(int ii = 0; ii < methods.size(); ii++) {
            ret.append(((MethodRec)methods.get(ii)).toStringBuilder());
        }
        for(int ii = 0; ii < classes.size(); ii++) {
            ret.append(((JavaClass)classes.get(ii)).toStringBuilder());
        }
        ret.append("}" + BRK);
        return ret;
    }
     
    public String toString() {
        return toStringBuilder().toString();
    }
    
    //use this to convert textual modifiers like "public" or "static" into the int value expected by javassist 
    public static HashMap<String, Integer> modifierNameToInt = null;
    public static void initModifierNameToIntMap() {
        if(modifierNameToInt != null) return;
        modifierNameToInt = new HashMap<String, Integer>(14);
        
        modifierNameToInt.put("public", Modifier.PUBLIC);
        modifierNameToInt.put("private", Modifier.PRIVATE);
        modifierNameToInt.put("protected", Modifier.PROTECTED);
        modifierNameToInt.put("static", Modifier.STATIC);
        modifierNameToInt.put("final", Modifier.FINAL);
        modifierNameToInt.put("synchronized", Modifier.SYNCHRONIZED);
        modifierNameToInt.put("volatile", Modifier.VOLATILE);
        modifierNameToInt.put("transient", Modifier.TRANSIENT);
        modifierNameToInt.put("native", Modifier.NATIVE);
        modifierNameToInt.put("interface", Modifier.INTERFACE);
        modifierNameToInt.put("abstract", Modifier.ABSTRACT);
        modifierNameToInt.put("strictfp", Modifier.STRICT);
//        modifierNameToInt.put("annotation", Modifier.ANNOTATION);
        modifierNameToInt.put("enum", Modifier.ENUM);
    }
    
    public CtClass initCtClass(String packageName, ClassPool cpool) {
        return cpool.makeClass(packageName + "." + name);
    }
    
    public void toCtClass(CtClass ctc, String packageName, ClassPool cpool) throws CannotCompileException, NotFoundException {
        initModifierNameToIntMap();
        if(access != null && access.length() > 0) {
            ctc.setModifiers(createJavassistModifiers(access));
        }
        
        
        if(superclass != null && !superclass.equals("")) {
            ctc.setSuperclass(cpool.get(superclass));

        }
        
        for(String iface : interfaces) {
            ctc.addInterface(cpool.get(iface));
        }
        
        ArrayList<String> staticInitBodies = new ArrayList<String>(5);
        for(Object obj : staticFieldsAndInits) {
            if(obj instanceof MemberRec) {
                MemberRec mr = (MemberRec)obj;
                mr.addToCtClass(ctc);
                staticInitBodies.add(mr.name + " = " + mr.initializer + ";");
            } else if(obj instanceof InitializerRec) {
                InitializerRec ir = (InitializerRec)obj;
                staticInitBodies.add(ir.body);
            }
        }

        ArrayList<String> initBodies = new ArrayList<String>(5);
        for(Object obj : fieldsAndInits) {
            if(obj instanceof MemberRec) {
                MemberRec mr = (MemberRec)obj;
                mr.addToCtClass(ctc);
                initBodies.add(mr.name + " = " + mr.initializer + ";");
            } else if(obj instanceof InitializerRec) {
                InitializerRec ir = (InitializerRec)obj;
                initBodies.add(ir.body);
            }
        }
        
        for(MethodRec mr : methods) {
            mr.addToCtClass(ctc);
        }
            
        for(JavaClass jc : classes) {
            CtClass nested = ctc.makeNestedClass(packageName + "." + jc.name, jc.access.contains("static"));
            jc.toCtClass(nested, packageName, cpool);
        }
            
        if(staticInitBodies.size() > 0) {
            StringBuilder body = new StringBuilder();
            for(String sinit : staticInitBodies) {
                body.append("{" + BRK + sinit + BRK + "}" + BRK);
            }
			//This will return the existing one or make
			//a new one if there isn't one already.
			//calling ctc.addConstructor(clinit) afterward
			//is unecessary
            CtConstructor clinit = ctc.makeClassInitializer();
            //TODO is insertAfter correct?
            clinit.insertAfter(body.toString());
        }
            
        if(initBodies.size() > 0) {
            //adding the initializers to a function will prevent them from working on final fields
            //so just always add this to the start of every constructor.  will consume more space
            //in the class file with lots of constructors unless there is a way to avoid duplicating code
            StringBuilder body = new StringBuilder();
            for(String init : initBodies) {
                body.append("{ " + init + " }" + BRK);
            }
            String bodyStr = body.toString();
            body = null;
            for(CtConstructor cons : ctc.getConstructors()) {
                cons.insertBeforeBody(bodyStr);
            }
        }
    }    
    public static int createJavassistModifiers(String accessModifiers) {
        String[] accessArr = accessModifiers.trim().split("\\s+");
        int mods = 0;
        for(String access : accessArr) {
            Integer mod = modifierNameToInt.get(access);
            if(mod != null) mods |= mod;
        }
        return mods;
    }
}
