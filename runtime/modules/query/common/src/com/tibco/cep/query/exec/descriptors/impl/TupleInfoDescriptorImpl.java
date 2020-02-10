package com.tibco.cep.query.exec.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;

import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.exec.codegen.QueryTemplateRegistry;
import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.stream.impl.expression.TupleExtractor;
import com.tibco.cep.query.utils.TypeHelper;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 16, 2008
 * Time: 3:57:37 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Describes at generation time a runtime TupleInfo.
 *
 * @see TupleInfoColumnDescriptorImpl
 */
public class TupleInfoDescriptorImpl

        implements TupleInfoDescriptor {


    protected static final QueryTemplateRegistry TEMPLATE_REGISTRY = QueryTemplateRegistry.getInstance();

    protected final StringTemplate TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE;

    protected Map<String, TupleInfoColumnDescriptor> classNameToDescriptor;
    protected LinkedHashMap<String, TupleInfoColumnDescriptor> columnNameToDescriptor;
    protected Map<ModelContext, TupleInfoColumnDescriptor> ctxToDescriptor;
    private ModelContext modelContext;
    protected String tupleClassName;
    protected String tupleInfoClassName;
    protected int typeIndex;
    protected Class tupleClass;


    public TupleInfoDescriptorImpl(ModelContext ctx) {
        try {
            TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getTupleColumnValue");
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }

        this.classNameToDescriptor = new HashMap<String, TupleInfoColumnDescriptor>();
        this.columnNameToDescriptor = new LinkedHashMap<String, TupleInfoColumnDescriptor>();
        this.ctxToDescriptor = new HashMap<ModelContext, TupleInfoColumnDescriptor>();
        this.modelContext = ctx;
        this.tupleClass = null;
        this.tupleClassName = null;
        this.tupleInfoClassName = null;
        this.typeIndex = 0;
    }


    public void addColumn(TupleInfoColumnDescriptor descriptor) {
        if (this.columnNameToDescriptor.containsKey(descriptor.getName())) {
            throw new IllegalArgumentException();
        }
        this.columnNameToDescriptor.put(descriptor.getName(), descriptor);
        this.classNameToDescriptor.put(descriptor.getClassName(), descriptor);
        final ModelContext ctx = descriptor.getModelContext();
        if (null != ctx) {
            this.ctxToDescriptor.put(ctx, descriptor);
            if (ctx instanceof Projection) {
                this.ctxToDescriptor.put(((Projection) ctx).getExpression(), descriptor);
            }
            if ((ctx instanceof Identifier) && !(ctx instanceof ProxyContext)) {
                try {
                    this.ctxToDescriptor.put(((Identifier) ctx).getIdentifiedContext(), descriptor);
                } catch (ResolveException ignored) {
                }
            }
        }
    }


    public TupleInfoColumnDescriptor getColumnByModelContext(ModelContext ctx) {
        return this.ctxToDescriptor.get(ctx);
    }


    public TupleInfoColumnDescriptor getColumnByName(String columnName) {
        return this.columnNameToDescriptor.get(columnName);
    }


    public List<String> getColumnClassGetters() {
        final List<String> classNames = new ArrayList<String>();
        for (TupleInfoColumnDescriptor column : this.columnNameToDescriptor.values()) {
            classNames.add(column.getClassGetter());
        }
        return classNames;
    }


    public List<String> getColumnClassNames() {
        final List<String> classNames = new ArrayList<String>();
        for (TupleInfoColumnDescriptor column : this.columnNameToDescriptor.values()) {
            classNames.add(column.getClassName());
        }
        return classNames;
    }

    public Map<String, TupleInfoColumnDescriptor> getClassNameToDescriptor() {
        return classNameToDescriptor;
    }

    public TupleExtractorDescriptor getColumnExtractor(TupleInfoColumnDescriptor column) {
        final int index = this.getColumnIndex(column);
        final TupleExtractor tupleExtractor = new TupleExtractor(index);
        final TupleExtractorDescriptor d = new TupleExtractorDescriptor(tupleExtractor, column.getTypeInfo());
        d.addUsedColumnName(column.getName(), column.getClassName());
        return d;
    }


    public String getColumnGetterCode(CharSequence baseTupleCode, TupleInfoColumnDescriptor column) {
        TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE.reset();
        TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE.setAttribute("tuple", baseTupleCode);
        TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE.setAttribute("valueClassName", TypeHelper.getBoxedName(column.getClassName()));
        TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE.setAttribute("escapedColumnName", this.getColumnIndex(column));

        if (TypeHelper.isJavaPrimitiveTypeOrPrimitiveArrayType(column.getClassName())) {
            return TypeHelper.class.getCanonicalName()
                    + ".getUnboxed("
                    + TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE.toString()
                    + ")";
        } else {
            return TEMPLATE_FOR_GET_TUPLE_COLUMN_VALUE.toString();
        }
    }


    public int getColumnIndex(TupleInfoColumnDescriptor column) {
        if (null != column) {
            int index = 0;
            for (TupleInfoColumnDescriptor c : this.columnNameToDescriptor.values()) {
                if (column.equals(c)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }


    public List<String> getColumnNames() {
        return new ArrayList<String>(this.columnNameToDescriptor.keySet());
    }


    public Collection<? extends TupleInfoColumnDescriptor> getColumns() {
        return this.columnNameToDescriptor.values();
    }


    public ModelContext getModelContext() {
        return this.modelContext;
    }


    public Class getTupleClass() {
        return this.tupleClass;
    }


    public String getTupleClassName() {
        return this.tupleClassName;
    }


    public String getTupleInfoClassName() {
        return this.tupleInfoClassName;
    }


    public int getTypeIndex() {
        return this.typeIndex;
    }


    public void setModelContext(ModelContext context) {
        this.modelContext = context;
    }


    public void setTupleClass(Class tupleClass) {
        this.tupleClass = tupleClass;
        this.tupleClassName = tupleClass.getCanonicalName();
    }


    public void setTupleClassName(String generatedClassName) {
        this.tupleClassName = generatedClassName;
    }


    public void setTupleInfoClassName(String tupleInfoClassName) {
        this.tupleInfoClassName = tupleInfoClassName;
    }


    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }


}
