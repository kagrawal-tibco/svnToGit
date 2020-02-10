package com.tibco.cep.runtime.service.time;

import com.tibco.cep.runtime.service.Service;

/*
* Author: Ashwin Jayaprakash Date: Feb 16, 2009 Time: 3:36:26 PM
*/
public interface HeartbeatService extends Service {
    long getHeartbeatIntervalMillis();

    Heartbeat getLatestHeartbeat();

    //-----------

    public static class Heartbeat {
        protected long timestamp;

        public Heartbeat(long timestamp) {
            this.timestamp = timestamp;
        }

        /**
         * @return Timestamp in milliseconds.
         */
        public long getTimestamp() {
            return timestamp;
        }
    }
}
