package com.tibco.cep.dashboard.psvr.ogl;

import java.util.Comparator;

import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;

/**
 * @author apatil
 *
 */
public class DataRowComparator implements Comparator<DataRow> {

    private boolean ascending;

    public DataRowComparator(boolean ascending){
        this.ascending = ascending;
    }

    public int compare(DataRow dr1, DataRow dr2) {
        String dr1ID = dr1.getId();
        String dr2ID = dr2.getId();
        int result = dr1ID.compareTo(dr2ID);
        if (result == 0){
            return result;
        }
        if (ascending == true){
            return result;
        }
        return -(result);
    }

}
