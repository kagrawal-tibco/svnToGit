package com.tibco.cep.query.exec.descriptors;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.query.exec.descriptors.impl.TupleExtractorDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 8, 2008
 * Time: 8:17:06 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Describes at generation time a runtime TupleInfo.
 */
public interface TupleInfoDescriptor {

    void addColumn(TupleInfoColumnDescriptor descriptor);

    TupleInfoColumnDescriptor getColumnByModelContext(ModelContext ctx);

    TupleInfoColumnDescriptor getColumnByName(String columnName);

    List<String> getColumnClassGetters();

    List<String> getColumnClassNames();

    TupleExtractorDescriptor getColumnExtractor(TupleInfoColumnDescriptor column);

    String getColumnGetterCode(CharSequence baseTupleCode, TupleInfoColumnDescriptor column);

    int getColumnIndex(TupleInfoColumnDescriptor column);

    List<String> getColumnNames();

    Collection<? extends TupleInfoColumnDescriptor> getColumns();

    ModelContext getModelContext();

    Class getTupleClass();

    String getTupleClassName();

    String getTupleInfoClassName();

    int getTypeIndex();

    void setTupleClass(Class tupleClass);

    void setTupleClassName(String generatedClassName);

    void setTupleInfoClassName(String tupleInfoClassName);

    void setTypeIndex(int index);

}
