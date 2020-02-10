<%@ page import="com.casalus.ideascript.ScriptContext,
                 com.casalus.ideascript.PsiUtil,
                 java.util.Iterator,
                 com.intellij.psi.*,
                 com.intellij.psi.util.MethodSignature,
                 com.intellij.util.IncorrectOperationException"%>
<%@ taglib uri="ideascript.tld" prefix="is" %>
<%! 
    String repeatString(String str, int num) {
        StringBuffer buf = new StringBuffer();
        for(int ii = 0; ii < num; ii++) {
            buf.append(str);
        }
        return buf.toString();
    }
    
    String jniTypeStr(PsiType type) {
        int dims = type.getArrayDimensions();
        type = type.getDeepComponentType();
        String sig;
        if(type == PsiType.BOOLEAN) {
            sig = "Z";
        } else if(type == PsiType.BYTE) {
            sig = "B";
        } else if(type == PsiType.CHAR) {
            sig = "C";
        } else if(type == PsiType.SHORT) {
            sig = "S";
        } else if(type == PsiType.INT) {
            sig = "I";
        } else if(type == PsiType.LONG) {
            sig = "J";
        } else if(type == PsiType.FLOAT) {
            sig = "F";
        } else if(type == PsiType.DOUBLE) {
            sig = "D";
        } else if(type == PsiType.VOID) {
            sig = "V";
        } else {
            sig = 'L' + ((PsiClassType)type).resolve().getQualifiedName().replace('.', '/') + ";";
        }
        
        return repeatString("[", dims) + sig;
    }
    
    String makeJNISig(PsiMethod method) {
        StringBuffer buf = new StringBuffer();
        buf.append("(");
        PsiParameter[] params = method.getParameterList().getParameters();
        for(int ii = 0; ii < params.length; ii++) {
            PsiParameter param = params[ii];
            buf.append(jniTypeStr(param.getType()));
        }
        buf.append(")");
        buf.append(jniTypeStr(method.getReturnType()));
        return buf.toString();
    }
    
    String CSPrimitiveName(PsiType type, String defaultName) {
        if(type == PsiType.BOOLEAN) {
            return "bool";
        } else if(type.equalsToText("java.lang.String")) {
            return "string"; 
        } else {
            return defaultName;
        }
    }
    
    String shortName(PsiType type) {
        String longName = type.getCanonicalText();
        int lastIndexOf = longName.lastIndexOf('.');
        if(lastIndexOf > 0) {
            return longName.substring(lastIndexOf+1);
        } else {
            return longName;
        }
    }
    
    String argTypeName(PsiType type) {
        int dims = type.getArrayDimensions();
        type = type.getDeepComponentType();
        
        String prefix = "";
        if(type instanceof PsiClassType) {
            PsiClassType classType = (PsiClassType)type;
            prefix += getInterfacePrefix(classType);
        }
        String typeName = CSPrimitiveName(type, prefix + shortName(type)); 
        return typeName + repeatString("[]", dims);
    }
    
    String makeParamsList(PsiMethod method) {
        StringBuffer buf = new StringBuffer();
        PsiParameter[] params = method.getParameterList().getParameters();
        for(int ii = 0; ii < params.length; ii++) {
            PsiParameter param = params[ii];
            if(ii > 0) buf.append(", ");
            buf.append(argTypeName(param.getType()));
            buf.append(" ");
            buf.append(fixArgName(param.getName()));
        }
        return buf.toString();
    }
    String makeReturnType(PsiMethod method) {
        PsiType type = method.getReturnType();
        return argTypeName(type);
    }
    
    String makeInvokeCall(String jMethodName, PsiMethod method) {
        String str = jMethodName + ".Invoke";
        PsiType returnType = method.getReturnType();
        boolean untypedInvoke = false;
        if(returnType == PsiType.BOOLEAN) {
            str += "Boolean";
        } else if(returnType == PsiType.BYTE) {
            str += "Byte";
        } else if(returnType == PsiType.CHAR) {
            str += "Char";
        } else if(returnType == PsiType.DOUBLE) {
            str += "Double";
        } else if(returnType == PsiType.FLOAT) {
            str += "Float";
        } else if(returnType == PsiType.INT) {
            str += "Int";
        } else if(returnType == PsiType.LONG) {
            str += "Long";
        } else if(returnType == PsiType.SHORT) {
            str += "Short";
        } else if(returnType == PsiType.VOID) {
            str += "Void";
        } else if(returnType.equalsToText("java.lang.String")) {
            str += "String";
        } else {
            untypedInvoke = true;
        }
        
        String returnTypeName = argTypeName(method.getReturnType());
        if(untypedInvoke) {
            //typecast
            str = "(" + returnTypeName + ")" + str;
        }
        
        if(returnType!=PsiType.VOID) {
            str = "return " + str;
        }
        
        //args
        if(method.hasModifierProperty("static")) {
            str+= "(null";
        } else {
            str += "(target.getJObject()";
        }
        
        if(untypedInvoke) {
            String concreteTypeName = returnTypeName;
            if(returnType instanceof PsiClassType) {
                concreteTypeName = returnTypeName.replaceFirst(getInterfacePrefix((PsiClassType)returnType), "J");
            }
            if(concreteTypeName == "JEntity") {
                str += ", " + "DynamicWrappers.WrapEntity";
            } else {
                str+= ", " + concreteTypeName + ".New" + concreteTypeName;
            }
        }
       
        String argsList = makeArgsList(method, true);
        if(argsList.length() > 0) str += ", " + argsList;
        
        str+=");";
        return str;
    }
    
    String makeArgsList(PsiMethod method, boolean wrapJValue) {
        String str = "";
        PsiParameter[] params = method.getParameterList().getParameters();
        for(int ii = 0; ii < params.length; ii++) {
            if(ii > 0) str += ", ";
            PsiParameter param = params[ii];
            if(wrapJValue) str += "new JValue(";
            str += fixArgName(param.getName());
            if(wrapJValue) str += ")";
        }
        return str;
    }
    
    String makeInterfaceList(PsiClass clz) {
        String str = "";
        PsiType[] ifaces = clz.getExtendsListTypes();
        for(int ii = 0; ii < ifaces.length; ii++) {
            PsiType iface = ifaces[ii];
            if(ii > 0) str+= ", ";
            str += argTypeName(iface);
        }
        return str;
    }
    
    String fixArgName(String name) {
        if(name.equals("out")) return "outt";
        if(name.equals("namespace")) return "name_space";
        if(name.equals("event")) return "evt";
        if(name.equals("ref")) return "reff";
        return name;
    }
    
    String getInterfacePrefix(PsiClassType classType) {
        if(classType.resolve().getQualifiedName().startsWith("java.")) {
                return "IJ";
            } else if(classType.resolve().isInterface()) {
                return "I";
            }
        return "";
    }
    
    /*
    String makeField(PsiField field) {
        field = (PsiField)field.copy();
        PsiModifierList modifierList = field.getModifierList();
        if(!modifierList.hasModifierProperty(PsiModifier.STATIC)) return "";
        String str = "";
        String getJField = "clazz.GetField(\"" + field.getName() + "\", " + '"' + jniTypeStr(field.getType()) + "\")";
        if(modifierList.hasModifierProperty(PsiModifier.FINAL)) {
            try {
                modifierList.setModifierProperty(PsiModifier.FINAL, false);
            } catch(IncorrectOperationException e) {
                e.printStackTrace();
            }
            str += modifierList.getText() + " " + "readonly" + " " + argTypeName(field.getType()) + " " + field.getName();
            str += " = " + ";";
        } else {
            str += "private static readonly JField 
        }
    }
    */
%>
<%
    ScriptContext context = ScriptContext.getInstance();
    PsiUtil putil = context.getPsiUtil();
%>

<%
    PsiClass currClass = (PsiClass)context.getCurrentClass().copy();
    {
    String className = currClass.getName();
    String qualifiedName = currClass.getQualifiedName();
 %>
using System;
using java.util;
using TIBCO.JNI.NET;

namespace <%= qualifiedName.substring(0, qualifiedName.lastIndexOf("." + className))%>
{

<%
     
     String interfaceList = makeInterfaceList(currClass);
     if(interfaceList.length() > 0) interfaceList = ", " + interfaceList;
    
    PsiMethod[] methodArr = currClass.getMethods();
    //for(int ii = 0; ii < methodArr.length; ii++) {
        //PsiMethod method = methodArr[ii];
        //method.getModifierList().delete();
        //PsiReferenceList throwslist = method.getThrowsList();
        //if(throwslist.getReferencedTypes().length > 0) {
        //    //throwslist.replace(putil.getPsi().getElementFactory().createCommentFromText("//" + throwslist.getText(), throwslist));
        //    throwslist.delete();
        //    method.getLastChild().
        //    method.add(putil.getPsi().getElementFactory().createCommentFromText("//" + throwslist.getText(), throwslist));
        //}
    //}
%>
public interface <%= getInterfacePrefix((PsiClassType)putil.type(currClass)) + className%> : IJObject <%= interfaceList%>{
    <%
        for(int ii = 0; ii < methodArr.length; ii++) {
            //String text;
            String throwsComment = "";
            PsiMethod method = methodArr[ii];
            if(method.getReturnType() == null) {
                method.delete();
                continue;
            }
            if(method.findSuperMethods().length > 0) {
                method.delete();
                continue;
            }
            if(!method.hasModifierProperty("public")) {
                method.delete();
                continue;
            }
            PsiReferenceList throwsList = method.getThrowsList();
            if(throwsList.getReferencedTypes().length > 0) {
                throwsList.delete();
                throwsComment = " //" + throwsList.getText();
            }
            
            //don't print this method in the interface, but don't
            //delete it from the list of methods so that it can
            //be included in the implementation of the interface
            if(method.hasModifierProperty("static")) continue;
            
            StringBuffer buf = new StringBuffer();
            PsiElement[] elems = method.getChildren();
            PsiTypeElement retType = method.getReturnTypeElement();
            for(int jj = 0; jj < elems.length; jj++) {
                PsiElement elem = elems[jj];
                if(!(elem instanceof PsiModifierList)) {
                    if(elem == retType) {
                        buf.append(argTypeName(method.getReturnType()));
                    } else if(elem instanceof PsiParameterList) {
                        PsiParameter[] params = ((PsiParameterList)elem).getParameters();
                        buf.append("(");
                        for(int kk = 0; kk < params.length; kk++) {
                            if(kk > 0) buf.append(", ");
                            PsiParameter param = params[kk];
                            buf.append(argTypeName(param.getType()));
                            buf.append(" ");
                            buf.append(fixArgName(param.getName()));
                        }
                        buf.append(")");
                    } else if(elem == method.getBody()) {
                        buf.append(";");
                    } else {
                        buf.append(elem.getText());
                    }
                }
            }
            buf.append(throwsComment);
    %>
    <%=buf.toString()%>
    <%
        }
     %>
}
<%
}
%>

<%
{
    //PsiClass currClass = context.getCurrentClass();
    String className = currClass.getName();
    String qualifiedName = currClass.getQualifiedName();
    %>
public class J<%= className%> : JObject, <%= getInterfacePrefix((PsiClassType)putil.type(currClass)) + className%> {
    public J<%= className%>(IntPtr obj) : base(obj){}
    public J<%= className%>(JObject reWrap) : base(reWrap){}
    public J<%= className%>(IJObject iobj) : base(iobj.getJObject()){} 
    
    public readonly static NewJObject NewJ<%= className%> = new NewJObject(newJ<%= className%>);
    private static JObject newJ<%= className%>(IntPtr obj) {
        return new J<%= className%>(obj);
    }
    
    
    <% PsiMethod[] methodArr = currClass.getMethods();
       PsiField[] fieldArr = currClass.getFields();
        
        for(int ii = 0; ii < methodArr.length; ii++) {
            PsiMethod method = methodArr[ii];
            if(method.getReturnType() == null) continue;
            boolean isStatic = method.hasModifierProperty("static");
            String access = "public" + (isStatic ? " static" : "");
            String methodName = method.getName();
            String paramsList = makeParamsList(method);
            String returnType = makeReturnType(method);
            String argsList = makeArgsList(method, false);
            //add the initial this parameter
            if(!isStatic) {
                if(argsList.length() > 0) argsList = "this , " + argsList;
                else argsList = "this";
            }
            
    %>
    <%=access%> <%= returnType%> <%= methodName%>(<%= paramsList%>) {
        <%= method.getReturnType()==PsiType.VOID ? "" : "return " %> <%= getInterfacePrefix((PsiClassType)putil.type(currClass)) + className%>_Methods.<%= methodName%>(<%= argsList%>);
    }
    <%
        }
}
    %>
}


<%
{
    //PsiClass currClass = (PsiClass)context.getCurrentClass().copy();
    String className = currClass.getName();
    String qualifiedName = currClass.getQualifiedName();
    
    PsiField[] fields = currClass.getFields();
    if(fields.length > 0){
    %>
public class <%= getInterfacePrefix((PsiClassType)putil.type(currClass)) + className%>_Fields
{
    <%
    for(int ii = 0; ii < fields.length; ii++) {
        PsiField fld = fields[ii];
        if(fld.hasModifierProperty("static") && fld.hasModifierProperty("public") && fld.hasModifierProperty("final")) {
            String str = fields[ii].getText();
        
    %>
    <%= str%>
    <%
        }
    }
    %>
}
<%
    }
}
%>

<%
{
    //PsiClass currClass = context.getCurrentClass();
    String className = currClass.getName();
    String qualifiedName = currClass.getQualifiedName();
%>
public class <%= getInterfacePrefix((PsiClassType)putil.type(currClass)) + className%>_Methods
{
    private static readonly JClass clazz = JClass.ForName("<%= currClass.getQualifiedName().replace('.', '/')%>");
    <%
        PsiMethod[] methodArr = currClass.getMethods();
        for(int ii = 0; ii < methodArr.length; ii++) {
            PsiMethod method = methodArr[ii];
            if(method.getReturnType() == null) continue;
            String methodName = method.getName();
            String JNISignature = makeJNISig(method);
            String paramsList = makeParamsList(method);
            boolean isStatic = method.hasModifierProperty("static");
            if(!isStatic) {
                String targetParm = getInterfacePrefix((PsiClassType)putil.type(currClass)) + className + " " + "target"; 
                if(paramsList.length() > 0) paramsList = targetParm + ", " + paramsList;
                else paramsList = targetParm;
            }
            String returnType = makeReturnType(method);
            String invokeCall = makeInvokeCall("_"+methodName, method);
            
    %>
    private static readonly JMethod _<%= methodName%> = clazz.GetMethod("<%= methodName%>", "<%= JNISignature%>", <%= "" + method.hasModifierProperty("static")%>);
    public static <%= returnType%> <%= methodName%>(<%= paramsList%>) {
        <%= invokeCall%>
    }
    <%
        }
}
    %>
}
}
