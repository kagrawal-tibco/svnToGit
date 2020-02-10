package com.tibco.cep.dashboard.psvr.data;

import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;

/**
 * @author anpatil
 *
 */
public interface Updateable {

	enum UpdateType {

		CREATE,
		UPDATE,
		DELETE,
		UNUSABLE

	}

//	public String getIdentifier();

    public void updateData(UpdateType updateType, List<Tuple> data);

    public void resetData(boolean purge) throws DataException;

    //public abstract void regenerateQuery() throws DataException;
}