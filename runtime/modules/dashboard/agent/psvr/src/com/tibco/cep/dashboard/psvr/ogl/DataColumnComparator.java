package com.tibco.cep.dashboard.psvr.ogl;

import java.util.Comparator;

import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;

/**
 * @author apatil
 *
 */
public class DataColumnComparator implements Comparator<DataColumn> {

    private boolean ascending;

    public DataColumnComparator(boolean ascending){
        this.ascending = ascending;
    }

    public int compare(DataColumn dc1, DataColumn dc2) {
        String dc1ID = dc1.getId();
        String dc2ID = dc2.getId();
        int result = dc1ID.compareTo(dc2ID);
        if (result == 0){
            return result;
        }
        if (ascending == true){
            return result;
        }
        return -(result);
    }

}
