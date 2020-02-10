package com.tibco.cep.runtime.service.tester.beunit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import com.tibco.as.admin.Admin;
import com.tibco.as.admin.AdminException;
import com.tibco.as.space.ASException;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.RecoveryOptions;
import com.tibco.as.space.RecoveryOptions.RecoveryPolicy;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef.PersistenceType;
import com.tibco.as.space.SpaceDef.SpaceState;
import com.tibco.as.space.router.RouterActionResult;
import com.tibco.as.space.router.RouterAlterAction;
import com.tibco.as.space.router.RouterCloseAction;
import com.tibco.as.space.router.RouterOpenAction;
import com.tibco.as.space.router.RouterWriteAction;

public class ASEngine {
	Metaspace ms;
	Properties env = new Properties();
	Hashtable<String, Space> spacerefs = new Hashtable<String, Space>();

	public ASEngine(String propertyFile) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(propertyFile);
			env.load(in);
			in.close();
		} finally {
			if (in != null)
				in.close();
		}
	}

	public ASEngine(String metaspace, String discovery, String remoteListen, String dataStore, String schema) {
		env.put("as.metaspace", metaspace);
		env.put("as.data.store.path", dataStore);
		env.put("as.discovery", discovery);
		env.put("as.remote.listen", remoteListen);
		env.put("as.schema.file", schema);
	}

	public String getMetaspaceName() {
		return env.getProperty("as.metaspace");
	}

	public String getRemoteDiscoveryUrl() {
		return env.getProperty("as.remote.listen");
	}

	public String getDiscoveryUrl() {
		return env.getProperty("as.discovery");
	}

	public void start() throws ASException, IOException, AdminException {
		BufferedReader bufferedReader = null;
		try {
			MemberDef member = MemberDef.create();
			member.setDiscovery(env.getProperty("as.discovery"));
			member.setListen(env.getProperty("as.discovery"));
			member.setDataStore(env.getProperty("as.data.store.path"));
			member.setRemoteListen(env.getProperty("as.remote.listen"));
			member.setMemberName("asunittest");
			member.setWorkerThreadCount(5);

			try {
				ms = Metaspace.connect(env.getProperty("as.metaspace"), member);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			Admin admin = Admin.create();

			String cmd = "create space name 'Admin_ClusterLockSpace' " + "(field name 'key' type 'string', "
					+ "field name 'value' type 'string') " + "key (fields ('key')) "
					+ "distribution_policy 'distributed' " + "replication_count 0 "
					+ "min_seeders 1 persistence_type 'none'";
			admin.execute(this.ms, cmd);

			cmd = "create space name 'Admin_ClusterCnxResetSpace' " + "(field name 'appName' type 'string', "
					+ "field name 'clusterName' type 'string', " + "field name 'clusterVersion' type 'string', "
					+ "field name 'dataCenter' type 'string', "
					+ "field name 'newMetaspace' type 'string' nullable true,"
					+ "field name 'badMetaspace' type 'string' nullable true) "
					+ "key (fields ('appName', 'clusterName', 'clusterVersion')) "
					+ "distribution_policy 'distributed' " + "replication_count -1 "
					+ "min_seeders 1 persistence_type 'none'";
			admin.execute(ms, cmd);

			bufferedReader = new BufferedReader(new FileReader(env.getProperty("as.schema.file")));
			String input;
			while ((input = bufferedReader.readLine()) != null) {
				if (input.length() <= 0)
					continue;
				input = input.trim();
				if ((input.startsWith("#")) || (input.startsWith("connect"))) {
					continue;
				}

				admin.execute(ms, input);

				RecoveryOptions opt = RecoveryOptions.create();
				opt.setRecoveryPolicy(RecoveryPolicy.NO_DATA);
				opt.setKeepFiles(false);

				for (String sp : ms.getUserSpaceNames()) {
					Space space = ms.getSpace(sp, DistributionRole.SEEDER);
					if (space.getSpaceDef().isRouted())
						space.setRouter(new Router());

					spacerefs.put(sp, space);
					while (space.getSpaceState() != SpaceState.READY) {
						if (space.getSpaceDef().getPersistenceType() == PersistenceType.SHARE_NOTHING
								&& space.getSpaceState() == SpaceState.RECOVER) {
							ms.recoverSpace(sp, opt);
							space.waitForReady();
						}
					}
				}
			}
		} finally {
			bufferedReader.close();
		}
	}

	public void shutdown() {
		cleanup();
		try {
			ms.closeAll();
		} catch (ASException e) {
			;
		}
	}

	public void truncate(String space) throws ASException {
		this.ms.getSpace(space).clear();
	}

	public void cleanup() {
		try {
			for (String sp : ms.getUserSpaceNames()) {
				this.ms.getSpace(sp).clear();
			}
		} catch (Exception e) {
			;
		}
	}

	class Router implements com.tibco.as.space.router.Router {

		@Override
		public RouterActionResult onAlter(RouterAlterAction arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RouterActionResult onClose(RouterCloseAction arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RouterActionResult onOpen(RouterOpenAction arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RouterActionResult onWrite(RouterWriteAction arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
