package com.tibco.cep.studio.core.util;

import java.io.File;

/**
 * Migrate Coherence Function to AS counter parts 
 */
public class CoherenceFunctionMigrator extends FunctionMigrator {
	public static String[][] fCoherenceFunctionMappings;
	
	static {
		fCoherenceFunctionMappings = new String[20][2];
		fCoherenceFunctionMappings[0][0] = "Coherence.C_CacheGetEntityById(";
		fCoherenceFunctionMappings[0][1] = "Cluster.DataGrid.CacheGetEntityById(";
		fCoherenceFunctionMappings[1][0] = "Coherence.C_CacheLoadConceptByExtId(";
		fCoherenceFunctionMappings[1][1] = "Cluster.DataGrid.CacheLoadConceptByExtId(";
		fCoherenceFunctionMappings[2][0] = "Coherence.C_CacheLoadConceptByExtIdByUri(";
		fCoherenceFunctionMappings[2][1] = "Cluster.DataGrid.CacheLoadConceptByExtIdByUri(";
		fCoherenceFunctionMappings[3][0] = "Coherence.C_CacheLoadConceptById(";
		fCoherenceFunctionMappings[3][1] = "Cluster.DataGrid.CacheLoadConceptById(";
		fCoherenceFunctionMappings[4][0] = "Coherence.C_CacheLoadConceptIndexedByExtId(";
		fCoherenceFunctionMappings[4][1] = "Cluster.DataGrid.CacheLoadConceptIndexedByExtId(";
		fCoherenceFunctionMappings[5][0] = "Coherence.C_CacheLoadConceptsByExtId(";
		fCoherenceFunctionMappings[5][1] = "Cluster.DataGrid.CacheLoadConceptsByExtId(";
		fCoherenceFunctionMappings[6][0] = "Coherence.C_CacheLoadEntity(";
		fCoherenceFunctionMappings[6][1] = "Cluster.DataGrid.CacheLoadEntity(";
		fCoherenceFunctionMappings[7][0] = "Coherence.C_CacheLoadEventByExtId(";
		fCoherenceFunctionMappings[7][1] = "Cluster.DataGrid.CacheLoadEventByExtId(";
		fCoherenceFunctionMappings[8][0] = "Coherence.C_CacheLoadEventByExtIdByUri(";
		fCoherenceFunctionMappings[8][1] = "Cluster.DataGrid.CacheLoadEventByExtIdByUri(";
		fCoherenceFunctionMappings[9][0] = "Coherence.C_CacheLoadEventById(";
		fCoherenceFunctionMappings[9][1] = "Cluster.DataGrid.CacheLoadEventById(";
		fCoherenceFunctionMappings[10][0] = "Coherence.C_CacheLoadParent(";
		fCoherenceFunctionMappings[10][1] = "Cluster.DataGrid.CacheLoadParent(";
		fCoherenceFunctionMappings[11][0] = "Coherence.C_CacheName(";
		fCoherenceFunctionMappings[11][1] = "Cluster.DataGrid.CacheName(";
		fCoherenceFunctionMappings[12][0] = "Coherence.C_CacheReevaluate(";
		fCoherenceFunctionMappings[12][1] = "Cluster.DataGrid.CacheReevaluate(";
		fCoherenceFunctionMappings[13][0] = "Coherence.C_ClassName(";
		fCoherenceFunctionMappings[13][1] = "Cluster.DataGrid.ClassName(";
		fCoherenceFunctionMappings[14][0] = "Coherence.C_EnableCacheUpdate(";
		fCoherenceFunctionMappings[14][1] = "Cluster.DataGrid.EnableCacheUpdate(";
		fCoherenceFunctionMappings[15][0] = "Coherence.C_Flush(";
		fCoherenceFunctionMappings[15][1] = "Cluster.DataGrid.Flush(";
		fCoherenceFunctionMappings[16][0] = "Coherence.C_Index(";
		fCoherenceFunctionMappings[16][1] = "Cluster.DataGrid.Index(";
		fCoherenceFunctionMappings[17][0] = "Coherence.C_Lock(";
		fCoherenceFunctionMappings[17][1] = "Cluster.DataGrid.Lock(";
		fCoherenceFunctionMappings[18][0] = "Coherence.C_TransactionProperties(";
		fCoherenceFunctionMappings[18][1] = "Cluster.DataGrid.TransactionProperties(";
		fCoherenceFunctionMappings[19][0] = "Coherence.C_UnLock(";
		fCoherenceFunctionMappings[19][1] = "Cluster.DataGrid.UnLock(";
	}

	@Override
	protected void createReplaceEdits(StringBuilder sb, File file) {
		boolean changed = false;
		String[][] arr = CoherenceFunctionMigrator.fCoherenceFunctionMappings;
		for (String[] mapping : arr) {
			String cFunction = mapping[0];
			int offset = sb.indexOf(cFunction);
			String asFunction = mapping[1];
			while (offset > -1) {
				sb = sb.replace(offset, offset+cFunction.length(), asFunction);
				int delta = cFunction.length() - asFunction.length();
				offset += delta;
				offset = sb.indexOf(cFunction, offset+1);
				changed = true;
			}
		}
		if (changed) {
			writeFile(sb, file);
		}
	}
	
	@Override
	public int getPriority() {
		return 99; // low priority, do after all others
	}
}
