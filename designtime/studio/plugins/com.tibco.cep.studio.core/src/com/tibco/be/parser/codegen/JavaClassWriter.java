package com.tibco.be.parser.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import com.tibco.be.parser.tree.Node;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 8, 2008
 * Time: 1:51:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaClassWriter {

    protected LineBuffer lineBuffer;
    private JavaClassWriter parentClassWriter;
    private LinkedHashMap<String,JavaClassWriter> nestedClassWriters = new LinkedHashMap<String,JavaClassWriter>();
    private Node astNode;

    protected static final String BRK = CGConstants.BRK;
    protected String access;
    protected String name;
    protected String superclass = null;
    protected ArrayList annotations = null;
    protected ArrayList fieldsAndInits = new ArrayList(5);
    protected ArrayList staticFieldsAndInits = new ArrayList<String>(0);
    protected ArrayList<String> interfaces = null;
    protected ArrayList<MethodRecWriter> methods = new ArrayList<MethodRecWriter>(5);
//    protected ArrayList<JavaClassWriter> classes = new ArrayList<JavaClassWriter>(5);
    protected String sourceText = null;


    public JavaClassWriter(String clazzName, Node node) {
        this.lineBuffer = new LineBuffer(node);
        this.name = clazzName;
        this.astNode = node;
    }



    private JavaClassWriter(JavaClassWriter parentClassWriter, String nestedClazzName, Node node)  {
        this.parentClassWriter = parentClassWriter;
        this.name = nestedClazzName;
        this.lineBuffer = new LineBuffer(node);
        this.astNode = node;
    }



    public JavaClassWriter createNestedClassWriter(String clazzName,Node node) {
        JavaClassWriter cwriter = new JavaClassWriter(this, clazzName,node);
        cwriter.parentClassWriter = this;
        this.nestedClassWriters.put(clazzName, cwriter);
        return cwriter;
    }


    public boolean isNestedClass() {
        return parentClassWriter != null;
    }



    public LineBuffer getLineBuffer() {
        return lineBuffer;
    }


    public void adjustJavaLines(int offset) {
        lineBuffer.adjustJavaLines(offset);
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setSuperClass(String superclassName) {
        superclass = superclassName;
    }

    public void addInterface(String interfaceName) {
    	if(interfaces == null) interfaces = new ArrayList(2);
        interfaces.add(interfaceName);
    }

    //one value argument without name is not allowed (@attr("value"))
    //always give name and value (@attr(name="value"))
	//nested attributes or array values not supported
    public void addAnnotation(Class<? extends java.lang.annotation.Annotation> annotationClass, Object... elementValuePairs) {
    	assert elementValuePairs == null || elementValuePairs.length % 2 == 0;
    	if(annotations == null) annotations = new ArrayList(4);
        annotations.add(annotationClass);
        annotations.add(elementValuePairs);
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

    private void addMethod(MethodRecWriter method) {
        methods.add(method);
    }



    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getName() {
        if(parentClassWriter != null) {
            return parentClassWriter.getName() + "." + name;
        } else {
            return name;
        }
    }

    public String getClassName() {
        if(parentClassWriter != null) {
            return parentClassWriter.getClassName() + "$" + name;
        } else {
            return name;
        }
    }

    public String getLocalName() {
        return name;
    }

    public Iterator<JavaClassWriter> getClasses() {
        return nestedClassWriters.values().iterator();
    }

    

    public void writePreamble(LineBuffer lbuffer) {
    	if(annotations != null) {
    		for(int ii = 0; ii < annotations.size();) {
    			Class<java.lang.annotation.Annotation> annoType = (Class<java.lang.annotation.Annotation>)annotations.get(ii++);
    			Object[] elementValuePairs = (Object[]) annotations.get(ii++);
    			lbuffer.append("@").append(annoType.getName());
    			if(elementValuePairs != null) {
    				lbuffer.append("(");
    				//should always be a multiple of two to avoid errors in loop
    				int limit = elementValuePairs.length & ~1;
    				for(int jj = 0; jj < limit;) {
    					lbuffer.append(String.valueOf(elementValuePairs[jj++]));
    					lbuffer.append(" = ");
    					Object value = elementValuePairs[jj++];
    					if(value instanceof String) {
    						lbuffer.append("\"").append((String)value).append("\"");
    					} else {
    						lbuffer.append(String.valueOf(value));
    					}
    					if(jj < limit) lbuffer.append(", ");
    				}
    				lbuffer.append(")");
				}
    			lbuffer.append(BRK);
    		}
    	}
    	
        if(access != null && !access.equals("")) {
//            ret.append(access);
//            ret.append(" ");
            lbuffer.append(access + " ");

        }
//        ret.append("class ");
//        ret.append(name);
//        ret.append(" ");
        lbuffer.append("class " + name + " ");
        if(superclass != null && !superclass.equals("")) {
//            ret.append("extends ");
//            ret.append(superclass);
//            ret.append(" ");
            lbuffer.append("extends " + superclass + " ");
        }
        if(interfaces != null) {
	        for(int ii = 0; ii < interfaces.size(); ii++) {
	            if(ii == 0) {
	//                ret.append("implements ");
	                lbuffer.append("implements ");
	            } else {
	//                ret.append(", ");
	                lbuffer.append(", ");
	            }
	//            ret.append((String)interfaces.get(ii));
	            lbuffer.append((String)interfaces.get(ii));
	        }
        }

        lbuffer.append("{"+BRK+BRK);

        for(Object obj : staticFieldsAndInits) {
//            ret.append(obj.toString());
            lbuffer.append(obj.toString());
        }
        for(Object obj : fieldsAndInits) {
//            ret.append(obj.toString());
            lbuffer.append(obj.toString());
        }

    }

    public void writePostamble(LineBuffer lbuffer) {
         lbuffer.append("}"+BRK);
    }

    public void writeBuffer(LineBuffer lbuffer,boolean adjust) {
        if(adjust) {
            adjustJavaLines(lbuffer.getJavaLine());
        }
        writePreamble(lbuffer);
        for(MethodRecWriter mrw:methods) {
//            if(adjust) {
//                mrw.adjustJavaLines(lbuffer.getJavaLine()-1);
//            }
            mrw.writeBuffer(lbuffer,adjust);
        }
        for(Iterator<JavaClassWriter> it = nestedClassWriters.values().iterator();it.hasNext();) {
//            if(adjust) {
//                jcw.adjustJavaLines(lbuffer.getJavaLine()-1);
//            }
        	JavaClassWriter jcw = it.next();
            jcw.writeBuffer(lbuffer,adjust);
        }
        writePostamble(lbuffer);
    }

    public String toString() {
        LineBuffer lbuffer = new LineBuffer();
        writeBuffer(lbuffer,false);
        return lbuffer.toString();
    }

    public String toLineBuffer(LineBuffer lbuffer,boolean adjust) {
        if(adjust) {
            adjustJavaLines(lbuffer.getJavaLine());
        }
        writeBuffer(lbuffer,adjust);
        return lbuffer.toString();
    }


    public MethodRecWriter createMethod(String access, String returntype, String shortName) {
        MethodRecWriter mrec = new MethodRecWriter(shortName,access,returntype,null);
        addMethod(mrec);
        return mrec;
    }

    public MethodRecWriter createMethod(String shortName,Node node) {
        MethodRecWriter mrec = new MethodRecWriter(shortName,node);
        addMethod(mrec);
        return mrec;
    }
    public MethodRecWriter createMethod(String shortName) {
        MethodRecWriter mrec = new MethodRecWriter(shortName,null);
        addMethod(mrec);
        return mrec;
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
        
        if(annotations != null) {
        	ClassFile cf = ctc.getClassFile();
			ConstPool cp = cf.getConstPool();
			AnnotationsAttribute attr = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
    		for(int ii = 0; ii < annotations.size();) {
    			Class<java.lang.annotation.Annotation> annoType = (Class<java.lang.annotation.Annotation>) annotations.get(ii++);
    			Object[] elementValuePairs = (Object[]) annotations.get(ii++);
    			Annotation annot = new Annotation(annoType.getName(), cp);
    			if(elementValuePairs != null) {
    				//should always be a multiple of two to avoid errors in loop
    				int limit = elementValuePairs.length & ~1;
    				for(int jj = 0; jj < limit;) {
    					String elemName = String.valueOf(elementValuePairs[jj++]);
    					Object value = elementValuePairs[jj++];
    					if(value instanceof String) annot.addMemberValue(elemName, new StringMemberValue((String)value, cp));
    					else if(value instanceof Long) annot.addMemberValue(elemName, new LongMemberValue((Long)value, cp));
    					else if(value instanceof Integer) annot.addMemberValue(elemName, new IntegerMemberValue((Integer)value, cp));
    					else if(value instanceof Boolean) annot.addMemberValue(elemName, new BooleanMemberValue((Boolean)value, cp));
    					else if(value instanceof Class) annot.addMemberValue(elemName, new ClassMemberValue(((Class)value).getName(), cp));
    					else if(value instanceof Double) annot.addMemberValue(elemName, new DoubleMemberValue((Double)value, cp));
    					else if(value instanceof Float) annot.addMemberValue(elemName, new FloatMemberValue((Float)value, cp));
    					else if(value instanceof Character) annot.addMemberValue(elemName, new CharMemberValue((Character)value, cp));
    					else if(value instanceof Byte) annot.addMemberValue(elemName, new ByteMemberValue((Byte)value, cp));
    				}
				}
    			attr.addAnnotation(annot);
    		}
    		cf.addAttribute(attr);
    		cf.setVersionToJava5();
    	}
        
        if(interfaces != null) {
	        for(String iface : interfaces) {
	            ctc.addInterface(cpool.get(iface));
	        }
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
            
        for(Iterator<JavaClassWriter> it = getClasses();it.hasNext();) {
        	JavaClassWriter jc = it.next();
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
