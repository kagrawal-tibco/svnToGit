package com.tibco.cep.runtime.management.impl.adapter.parent;

/*
* Author: Ashwin Jayaprakash Date: Mar 11, 2009 Time: 10:05:39 AM
*/
public class RemoteProcessInfo {
    protected RemoteCoherenceClusterAdapter adapter;

    protected RemoteProcessPingerTask pingerTask;

    protected Process optionalChildProcess;

    protected RemoteProcessOutputReader optionalChildProcessOutputReader;

    protected int port;                //Broker RMI port. Set using the property be.metric.cluster.property.broker.rmi.port (RemoteClusterSpecialPropKeys.BrokerPort)

    public RemoteProcessPingerTask getPingerTask() {
        return pingerTask;
    }

    public void setPingerTask(RemoteProcessPingerTask pingerTask) {
        this.pingerTask = pingerTask;
    }

    public RemoteCoherenceClusterAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RemoteCoherenceClusterAdapter adapter) {
        this.adapter = adapter;
    }

    public Process getOptionalChildProcess() {
        return optionalChildProcess;
    }

    public void setOptionalRemoteProcess(Process optionalChildProcess) {
        this.optionalChildProcess = optionalChildProcess;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setOptionalRemoteProcessOutputReader(
            RemoteProcessOutputReader optionalChildProcessOutputReader) {
        this.optionalChildProcessOutputReader = optionalChildProcessOutputReader;
    }

    public RemoteProcessOutputReader getOptionalChildProcessOutputReader() {
        return optionalChildProcessOutputReader;
    }

    public void discard() throws Exception {
        if (adapter != null) {
            adapter.discard();
            adapter = null;
        }

        pingerTask = null;

        optionalChildProcess = null;

        if (optionalChildProcessOutputReader != null) {
            optionalChildProcessOutputReader.stop();
            optionalChildProcessOutputReader = null;
        }

        port = 0;
    }
}
