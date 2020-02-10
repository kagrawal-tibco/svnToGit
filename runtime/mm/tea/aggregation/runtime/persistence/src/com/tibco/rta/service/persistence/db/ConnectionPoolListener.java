package com.tibco.rta.service.persistence.db;

public interface ConnectionPoolListener {
	void reconnected();

	void switched(boolean toSecondary);
}