/**
 * 
 */
package com.tibco.cep.query.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.be.util.idgenerators.serial.PrefixedAlphanumericGenerator;
import com.tibco.cep.query.exception.DuplicateAliasException;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionArg;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.TypeInfo;

/**
 * @author pdhar
 */
public class FunctionImpl extends AbstractRegistryEntryContext implements Function {

    private ArrayList<Class> exceptions;

    private FunctionType fType;

    private String uri;

    private Class implClass;

    private String name;

    private ArrayList ids = new ArrayList();

    protected PrefixedAlphanumericGenerator m_prefixedGenerator;

    /**
     * @param parentContext
     * @param tree
     * @param localName
     * @param uri
     * @param implClass
     * @param functionTypeRule
     */
    public FunctionImpl(ModelContext parentContext, CommonTree tree, String localName, String uri,
                        Class implClass, FunctionType functionTypeRule)
            throws Exception {
        super(parentContext);
        this.exceptions = new ArrayList<Class>();
        this.name = localName;
        this.uri = uri;
        this.implClass = implClass;
        this.fType = functionTypeRule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#addExceptions(java.lang.Class)
     */
    public void addExceptions(Class exception) {
        exceptions.add(exception);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#addFunctionArgument(java.lang.String,
     *      com.tibco.cep.query.model.impl.TypeInfoImpl)
     */
    public FunctionArg addFunctionArgument(String name, TypeInfo type) throws Exception {
        return new FunctionArgImpl(this, null, name, type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#addFunctionArgument(java.lang.String,
     *      java.lang.Class)
     */
    public FunctionArg addFunctionArgument(String name, Class clazz) throws Exception {
        return this.addFunctionArgument(name, new TypeInfoImpl(clazz));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getArguments()
     */
    public FunctionArg[] getArguments() {
        List<FunctionArg> args = new LinkedList<FunctionArg>();

        int index = 0;
        for (Iterator it = this.getChildrenIterator(); it.hasNext(); index++) {
            Object o = it.next();
            if (o instanceof FunctionArg) {
                args.add((FunctionArg) o);
            }
        }

        return args.toArray(new FunctionArg[args.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getExceptionClassNames()
     */
    public String[] getExceptionClassNames() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getExceptions()
     */
    public Class[] getExceptions() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getImplClass()
     */
    public Class getImplClass() throws Exception {
        return this.implClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#setImplClass(java.lang.Class)
     */
    public void setImplClass(Class clazz) {
        this.implClass = clazz;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getImplClassName()
     */
    public String getImplClassName() {
        return (null == this.implClass) ? null : this.implClass.getCanonicalName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getName()
     */
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#getPath()
     */
    public String getPath() {
        return this.uri;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#hasReturnType()
     */
    public boolean hasReturnType() {
        return this.typeInfo != null;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#isFunctionType(com.tibco.cep.query.model.Function.FunctionType)
     */
    public FunctionType getFunctionType() {
        return this.fType;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#setFunctionType(com.tibco.cep.query.model.Function.FunctionType)
     */
    public void setFunctionType(FunctionType type) {
        this.fType = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.ModelContext#getContextType()
     */
    public int getContextType() {
        switch (this.getFunctionType()) {
            case FUNCTION_TYPE_AGGREGATE: return ModelContext.CTX_TYPE_AGGREGATE_FUNCTION;
            case FUNCTION_TYPE_CATALOG: return ModelContext.CTX_TYPE_CATALOG_FUNCTION;
            case FUNCTION_TYPE_RULE: return ModelContext.CTX_TYPE_RULE_FUNCTION;
        }
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.Function#setPath(java.lang.String)
     */
    public void setPath(String path) {
        this.uri = path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.IdentifiableChildContext#getIdGenPrefix()
     */
    public String getIdGenPrefix() {
        return Function.ID_GEN_PREFIX;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.IdentifiableChildContext#getIdentifierGenerator()
     */
    public IdentifierGenerator getIdentifierGenerator() {
        if (m_prefixedGenerator == null) {
            m_prefixedGenerator = new PrefixedAlphanumericGenerator(getIdGenPrefix(), false,
                    getIdGenPrefix().length() + 3);
        }
        return m_prefixedGenerator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.IdentifiableChildContext#nextIdentifier()
     */
    public Object nextIdentifier() throws DuplicateAliasException {
        return nextIdentifier(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.cep.query.model.IdentifiableChildContext#nextIdentifier(java.lang.Object)
     */
    public Object nextIdentifier(Object id) throws DuplicateAliasException {
        if (id == null) {
            id = getIdentifierGenerator().nextIdentifier();
            while (ids.contains(id)) {
                id = getIdentifierGenerator().nextIdentifier();
            }
        }
        ids.add(id);
        return id;
    }


    /**
     * Returns the number of arguments
     * 
     * @return int
     */
    public int getArgumentCount() {
        return this.getDescendantContextsByType(this, ModelContext.CTX_TYPE_FUNCTION_ARG).length;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(super.equals(o) && (o instanceof FunctionImpl))) {
            return false;
        }
        final FunctionImpl that = (FunctionImpl) o;

        return (this.fType == that.fType)
                && this.name.equals(that.name)
				&& this.uri.equals(that.uri)
                && Arrays.equals(this.getArguments(),that.getArguments())
                && this.exceptions.equals(that.exceptions)
                && ( ((this.implClass == null) && (that.implClass == null))
                    || ((this.implClass != null) && this.implClass.equals(that.implClass)) );
    }


    public int hashCode() {
        long longHash = super.hashCode();
        longHash = 29 * longHash + this.childContext.hashCode();
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + this.exceptions.hashCode();
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + this.fType.hashCode();
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + this.uri.hashCode();
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + ((null == this.implClass) ? 0 : this.implClass.hashCode());
        longHash ^= (longHash >>> 32);
        longHash = 29 * longHash + this.name.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
