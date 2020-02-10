/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.StoreConnection;

/**
 * @author vpatil
 *
 */
@BEPackage(
        catalog = "CEP Store",
        category = "Store.Transactions",
        synopsis = "Store Transaction functions")
public class TransactionFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "enableTransactions",
		signature = "void enableTransactions (String url)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Enables transactional behavior. By default transactions are disabled. All operations Store.Open/Put/Get/Delete, post this call will be executed under a transaction. The transaction will be applied once Store.Transactions.commit/rollback is executed, post which a new transaction begins.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void enableTransactions(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.enableTransactions();
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error enabling transaction for Store with url [%s]", url), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "disableTransactions",
		signature = "void disableTransactions (String url)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Disables transactional behavior. All operations i.e. Store.Open/Put/Get/Delete post this call will be independent operations being applied immediately.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void disableTransactions(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.disableTransactions();
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error disabling transaction for Store with url [%s]", url), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "commit",
		signature = "void commit (String url)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Commits the current transaction. Current transaction covers all operations i.e. Store.open/put/get/delete between the first Store.Transactions.enableTransactions and Store.Transactions.commit/rollback or operations between subsequent Store.Transactions.commit/rollback calls.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void commit(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.commit();
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error committing the transaction for Store with url [%s]", url), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
		
	@com.tibco.be.model.functions.BEFunction(
		name = "rollback",
		signature = "void rollback (String url)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Roll's back the current transaction. Current transaction covers all operations Store.open/put/get/delete between the first Store.Transactions.enableTransactions and Store.Transactions.commit/rollback or operations between subsequent Store.Transactions.commit/rollback calls.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void rollback(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.rollback();
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error rolling back the transaction for Store with url [%s]", url), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}

}
