package com.tibco.cep.runtime.service.ft.impl;

import java.util.Arrays;
import java.util.Collection;

import com.tibco.cep.runtime.service.ft.FTNodeAsyncMsgId;

//////////////////////////////////////////////////////////////////////////////////////////
// FTNodeControllerMsgs
public class FTNodeAsyncControllerMsgs {
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_NODE_STARTED = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_NODE_STARTED,"Node Started");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_INIT_ALL = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_INIT_ALL, "Init All");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_INIT_CHANNELS = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_INIT_CHANNELS,"Initialize Channels");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_SET_INACTIVE = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_SET_INACTIVE,"Set Inactive");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_START_CHANNELS = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_START_CHANNELS,"Start Channels");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_STOP_CHANNELS = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_STOP_CHANNELS,"Stop Channels");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_WAIT_FOR_RTC = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_WAIT_FOR_RTC,"Wait for RTC");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_STOP_NODE = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_STOP_NODE,"Stop Node");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_INIT_RULESESSION = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_INIT_RULESESSION,"Init RuleSession");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_SUSPEND_RTC = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_SUSPEND_RTC,"Suspend RTC");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_ACTIVATE_RTC = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_ACTIVATE_RTC,"Activate RTC");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_SHUTDOWN = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_SHUTDOWN,"Shutdown");
       public static final FTNodeAsyncControllerMsgs MSG_SYNC_GETDIGEST = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_SYNC_GETDIGEST,"GetDigest");
       public static final FTNodeAsyncControllerMsgs MSG_ASYNC_WAIT_FOR_ACTIVATION = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_WAIT_FOR_ACTIVATION,"WaitForActivation");
      public static final FTNodeAsyncControllerMsgs MSG_ASYNC_WAIT_BEFORE_START = new FTNodeAsyncControllerMsgs(FTNodeAsyncMsgId.MSGID_ASYNC_WAIT_BEFORE_START,"WaitBeforeStart");

       private int enumInt;
       private String msg;
       private Object payload;



    FTNodeAsyncControllerMsgs (int enumInt,String _msg) {
        this.enumInt = enumInt;
        this.msg = _msg;        
    }
        public boolean equals(Object o) {
           if(o instanceof FTNodeAsyncControllerMsgs) {
               return enumInt == ((FTNodeAsyncControllerMsgs) o).enumInt;
           } else {
               return false;
           }
       }
       public String toString() {
           return ("FTAsyncNodeControllerMsgs["+msg+"]");
       }
       public int getId() { return enumInt; }
       public void setPayload(Object o) { this.payload = o; }
       public Object getPayload() { return payload; }

       public static Collection values () {
           return Arrays.asList(new FTNodeAsyncControllerMsgs [] {
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_NODE_STARTED,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_INIT_ALL,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_INIT_CHANNELS,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_SET_INACTIVE,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_START_CHANNELS,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_STOP_CHANNELS,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_WAIT_FOR_RTC,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_STOP_NODE,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_INIT_RULESESSION,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_SUSPEND_RTC,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_ACTIVATE_RTC,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_SHUTDOWN,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_WAIT_FOR_ACTIVATION,
                   FTNodeAsyncControllerMsgs.MSG_ASYNC_WAIT_BEFORE_START
                   });
       }

   }
