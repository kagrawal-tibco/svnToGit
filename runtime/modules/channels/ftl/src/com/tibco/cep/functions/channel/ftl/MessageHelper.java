package com.tibco.cep.functions.channel.ftl;

import com.tibco.ftl.*;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@com.tibco.be.model.functions.BEPackage(
        catalog = "FTL",
        category = "FTL.Message",
        synopsis = "Message Functions",
        enabled = @com.tibco.be.model.functions.Enabled(value=false)
        )
public class MessageHelper {

    static ConcurrentMap<String, MessageFieldRef> fieldRefTable = new ConcurrentHashMap<String, MessageFieldRef>();

    @com.tibco.be.model.functions.BEFunction(
        name = "acknowledge",
        signature = "void acknowledge (Object message)",
        params = {
        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message Object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Explicitly acknowledge a message. When a durable subscriber object specifies explicit acknowledgement, the application program must acknowledge each message to the durable by calling this method. When a durable subscriber object does not specify explicit acknowledgement, the FTL library automatically acknowledges the message when the application callback method returns. For any message that arrives through a non-durable subscriber, this call returns without error (and without action).",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void acknowledge (Object message) {
        Message msg = Message.class.cast(message);
        if (msg == null) return;
        try {
            msg.acknowledge();
        } catch (FTLException e) {
            e.printStackTrace();
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clearAllFields",
        signature = "void clearAllFields (Object message)",
        params = {
        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message Object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clear all fields in a mutable message. After clearing all fields, you can re-use the message. The message format does not change. This call is more efficient than creating a new empty message of the same format.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void clearAllFields (Object message) {
        Message msg = Message.class.cast(message);
        if (msg == null) return;
        msg.clearAllFields();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clearField",
        signature = "void clearField (Object message, String fieldName)",
        params = {
        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
        @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "field name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clear a field in a mutable message. Clearing a field clears the data from a field in the message object, and flags the field so a subsequent send call does not transmit it.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void clearField (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return;
        msg.clearField(fieldname);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "destroy",
        signature = "void destroy (Object message)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Destroy a message object. A program may destroy only mutable messages - that is, those messages that the program creates - for example, using Realm.createMessage of mutableCopy(). Inbound messages in listener callback methods belong to the FTL library, programs must not destroy them. Do not destroy a message if the program needs data that the message owns - for example, a string (from getString), an opaque pointer (from getOpaque), a sub-message (from getMessage), or an inbox (from getInbox). Destroying a message frees all resources associated with it.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void destroy (Object message) {
        Message msg = Message.class.cast(message);
        if (msg == null) return;

        try {
            msg.destroy();
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDateTime",
        signature = "Calendar getDateTime (Object message, String fieldName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldName", type = "String", desc = "field name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Calendar", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the value of a TibDateTime field from a message.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static Calendar getDateTime (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return null;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            TibDateTime tdt = null;
            if (fieldRef == null) {
                tdt = msg.getDateTime(fieldname);
            }
            else {
                tdt = msg.getDateTime(fieldRef);
            }

            Calendar gc =  new GregorianCalendar();
            gc.setTime(tdt.toDate());
            return gc;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDateTimeArray",
        signature = "Object[] getDateTimeArray (Object message, String fieldname)",
        enabled=@Enabled(property="", value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = " message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the value of a TibDateTime array field from a message.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static Object[] getDateTimeArray (Object message, String fieldname) {
        throw new RuntimeException("Not Supportted. ");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDouble",
        signature = "double getDouble (Object message, String fieldname)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the value of a double floating-point field from a message.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static double getDouble (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return Double.NaN;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getDouble(fieldname) : msg.getDouble(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDoubleArray",
        signature = "double[] getDoubleArray (Object message, String fieldname)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double[]", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the value of a double array field from a message.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static double[] getDoubleArray (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return new double[0];

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getDoubleArray(fieldname) : msg.getDoubleArray(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFieldType",
        signature = "int getFieldType (Object message, String fieldname)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the type of a field within the message.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static int getFieldType (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return -1;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getFieldType(fieldname) : msg.getFieldType(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFieldTypeString",
        signature = "String getFieldTypeString (Object message, int field)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "field", type = "int", desc = "field type ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return a string that represents a field type.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static String getFieldTypeString (Object message, int field) {

        Message msg = Message.class.cast(message);
        if (msg == null) return null;

        try {
            return msg.getFieldTypeString(field);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getInbox",
            signature = "com.tibco.ftl.Inbox getInbox (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "com.tibco.ftl.Inbox", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of an inbox field from a message. This call deserializes the inbox value, caches the result with the message object, and returns that cached inbox object. The inbox object is valid only for the lifetime of the message (though programs may copy it). Your program must not modify nor destroy the inbox object. Calling this method repeatedly returns the same cached inbox; it does not repeat the deserialization. Programs can send messages to the inbox using Publisher.sendToInbox.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object getInbox (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return null;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getInbox(fieldname) : msg.getInbox(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getLong",
            signature = "long getLong (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of a long integer field from a message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static long getLong (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) throw new RuntimeException("message is null");

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getLong(fieldname) : msg.getLong(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getLongArray",
            signature = "long[] getLongArray (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long[]", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of a long array field from a message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static long[] getLongArray (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return new long[0];

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getLongArray(fieldname) : msg.getLongArray(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getMessage",
            signature = "com.tibco.ftl.Message getMessage (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "message object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of a message field from a message.This call deserializes the sub-message value, caches the result with the message object, and returns that cached sub-message. The sub-message is valid only for the lifetime of the parent message. Your program must not modify nor destroy the sub-message.Calling this method repeatedly returns the same cached sub-message; it does not repeat the deserialization.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object getMessage (Object message, String fieldname) {

        Message msg = Message.class.cast(message);
        if (msg == null) return Double.NaN;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getMessage(fieldname) : msg.getMessage(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getMessageArray",
            signature = "Object[] getMessageArray (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "message object array"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of a message array field from a message.This call deserializes the sub-message array value, caches the result with the message object, and returns that cached array object. The array is valid only for the lifetime of the message. Your program must not modify nor destroy the sub-messages, nor modify the array.Calling this method repeatedly returns the same cached array of cached messages; it does not repeat the deserialization.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object[] getMessageArray (Object message, String fieldname) {

        Message msg = Message.class.cast(message);
        if (msg == null) return new Object[0];

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getMessageArray(fieldname) : msg.getMessageArray(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getOpaque",
            signature = "Object getOpaque (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "opaque object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of an opaque field from a message.This method copies the bytes from the message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object getOpaque (Object message, String fieldname) {

        Message msg = Message.class.cast(message);
        if (msg == null) return Double.NaN;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getOpaque(fieldname) : msg.getOpaque(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getString",
            signature = "String getString (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "5.2",
            see = "Get the value of a string field from a message.The string is valid only for the lifetime of the message. The string is part of the message object; the program must neither modify nor free it.",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static String getString (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return null;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getString(fieldname) : msg.getString(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getStringArray",
            signature = "String[] getStringArray (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the value of a string array field from a message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static String[] getStringArray (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return new String[0];

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.getStringArray(fieldname) : msg.getStringArray(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "isFieldSet",
            signature = "boolean isFieldSet (Object message, String fieldname)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Determine whether a field is set in a message.If it is not set, then getting the field value throws an exception.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static boolean isFieldSet (Object message, String fieldname) {
        Message msg = Message.class.cast(message);
        if (msg == null) return false;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
            return fieldRef == null ? msg.isFieldSet(fieldname) : msg.isFieldSet(fieldRef);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "iterator",
            signature = "com.tibco.ftl.MessageIterator iterator (Object message)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message Object")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "iterator object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Initialize an iterator for the fields of the message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object iterator (Object message) {
        Message msg = Message.class.cast(message);
        if (msg == null) return Double.NaN;

        try {
            return msg.iterator();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "mutableCopy",
            signature = "Object mutableCopy (Object message)",
            params = {

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "copied object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Create a mutable copy of a message.Programs cannot modify inbound messages that subscribers receive. However, programs can use this method to create mutable copies (which they may modify).Programs must destroy mutable copies to reclaim resources.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object mutableCopy (Object message) {
        Message msg = Message.class.cast(message);
        if (msg == null) return Double.NaN;

        try {
            return msg.mutableCopy();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setArray",
            signature = "void setArray (Object message, String fieldname, Object[] messages)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = " "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messages", type = "Object", desc = "message objects ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set a long array field in a mutable message.This method copies the array.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setArray (Object message, String fieldname, Object[] messages) {
         Message msg = Message.class.cast(messages);
         if (msg == null) throw new RuntimeException("Null message");

         try {
             msg.setArray(fieldname, (Message[])messages);
         }
         catch (Exception ex) {
             throw new RuntimeException(ex);
         }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setDateTime",
            signature = "void setDateTime (Object message, String fieldName, Calendar dateTime)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "dateTime", type = "Calendar", desc = "date time ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set a TibDateTime field in a mutable message.The method copies the value into the message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setDateTime (Object message, String fieldname, Calendar dateTime) {
         Message msg = Message.class.cast(message);
         if (msg == null) return ;

         MessageFieldRef fieldRef = fieldRefTable.get(fieldname);
         TibDateTime tdt = new TibDateTime();
         tdt.setFromMillis(dateTime.getTimeInMillis());

         try {
            if (fieldRef == null)  {
                msg.setDateTime(fieldname, tdt);
            } else {
                msg.setDateTime(fieldRef, tdt);
            }
         }
         catch (Exception ex) {
             throw new RuntimeException(ex);
         }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setDouble",
            signature = "void setDouble (Object message, String fieldname, double value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "value")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set a double floating-point field in a mutable message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setDouble (Object message, String fieldname, double value) {
         Message msg = Message.class.cast(message);
         if (msg == null) return ;

         MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

         try {
            if (fieldRef == null)  {
                msg.setDouble(fieldname, value);
            } else {
                msg.setDouble(fieldRef, value);
            }
         }
         catch (Exception ex) {
             throw new RuntimeException(ex);
         }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setInbox",
            signature = "void setInbox (Object message, String fieldname, Object inbox)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "inbox", type = "Object", desc = "inbox object")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set an inbox field in a mutable message.This call copies the inbox into a message field.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setInbox (Object message, String fieldname, Object value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return ;
        Inbox ibox = Inbox.class.cast(value);
        if (ibox == null)  return;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
        	if (fieldRef == null)  {
        		msg.setInbox(fieldname, ibox);
        	} else {
        		msg.setInbox(fieldRef, ibox);
        	}
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setLong",
            signature = "void setLong (Object message, String fieldname,, long value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "long value")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set a long integer field in a mutable message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setLong (Object message, String fieldname, long value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return ;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
        	if (fieldRef == null) {
				msg.setLong(fieldname, value);
			} else {
				msg.setLong(fieldRef, value);
			}
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setMessage",
            signature = "void setMessage (Object message, String fieldname, Object  messageValue)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageValue", type = "Object", desc = "message value object")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set a sub-message field in a mutable message.This call copies the sub-message data into the enclosing message field, but does not create a new Java message object. Programs may safely destroy the msg argument after this call returns.Do not set a message as a sub-message of itself (at any level of nesting).",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setMessage (Object message, String fieldname, Object value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return ;
        Message msgVal = Message.class.cast(value);
        if (msgVal == null)  return;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
			if (fieldRef == null) {
				msg.setMessage(fieldname, msgVal);
			} else {
				msg.setMessage(fieldRef, msgVal);
			}
		}
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setOpaque",
            signature = "void setOpaque (Object message, String fieldname, Object value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "fieldname"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "byte[] object ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set an opaque (byte-array) field in a mutable message.This method copies the entire byte-array into the field.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setOpaque (Object message, String fieldname, Object value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return ;
        byte[] msgVal = byte[].class.cast(value);
        if (msgVal == null)  return;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
			if (fieldRef == null) {
				msg.setOpaque(fieldname, msgVal);
			} else {
				msg.setOpaque(fieldRef, msgVal);
			}
		}
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setString",
            signature = "void setString (Object message, String fieldname, String value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "fieldname", type = "String", desc = "field name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "value String")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set a string field in a mutable message.This method copies the string value into the message.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setString (Object message, String fieldname, String value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return ;
        if (value == null) return;

        MessageFieldRef fieldRef = fieldRefTable.get(fieldname);

        try {
			if (fieldRef == null) {
				msg.setString(fieldname, value);
			} else {
				msg.setString(fieldRef, value);
			}
		}
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "writeToByteArray",
            signature = "int writeToByteArray (Object message, Object value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "byte[] object ")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Write a serialized representation of a message to a byte-array.Programmers estimate the required size of the byte-array buffer. If the buffer is too small, the method fails, but returns the actual required size. The program can use this information to supply a buffer of the required size in the second call.<br/>Programming Strategy:<br/>1. Create an array you think is big enough.<br/>2. Call writeToByteArray.<br/>3. If the returned size is bigger than your array, then call writeToByteArray again with a larger array (at least the returned size).<br/>For example:<br/>int length = 2048;<br/>byte[] bytes = new byte[length];<br/>length = msg.writeToByteArray(bytes);<br/>if(length > bytes.length)<br/>{<br/>bytes = new byte[length];<br/>length = msg.writeToByteArray(bytes);<br/>}<br/>",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static int writeToByteArray (Object message, Object value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return -1;
        byte[] msgVal = byte[].class.cast(value);
        if (msgVal == null)  return -1;

        try {
            int length = msgVal.length;
            byte[] bytes = new byte[length];

            length = msg.writeToByteArray(bytes);

            if (length > bytes.length) {
				msgVal = new byte[length];
				length = msg.writeToByteArray(msgVal);
				return length;
			}
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "writeToPortableByteArray",
            signature = "int writeToPortableByteArray (Object message, Object value)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "Object", desc = "message object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "byte[] value object")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Write a self-describing serialized representation of a message to a byte-array.This method writes a portable form of the message, which you can use outside the originating realm. The portable form is larger than optimized forms, because it includes all format metadata (as with a dynamic format).Programmers estimate the required size of the byte-array buffer. If the buffer is too small, the method fails, but returns the actual required size. The program can use this information to supply a buffer of the required size in the second call.<br/>Programming Strategy:<br/>1. 1.Create an array you think is big enough.<br/>2. 2.Call writeToPortableByteArray.<br/>3. 3.If the returned size is bigger than your array, then call writeToPortableByteArray again with a larger array (at least the returned size).<br/>For example: <br/>int length = 2048;<br/>byte[] bytes = new byte[length];<br/>length = msg.writeToPortableByteArray(bytes);<br/>if(length > bytes.length)<br/>{<br/>bytes = new byte[length];<br/>length = msg.writeToPortableByteArray(bytes);<br/>}<br/>",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static int writeToPortableByteArray (Object message, Object value) {
        Message msg = Message.class.cast(message);
        if (msg == null) return -1;
        byte[] msgVal = byte[].class.cast(value);
        if (msgVal == null)  return -1;

        try {
            int length = msgVal.length;
            byte[] bytes = new byte[length];

            length = msg.writeToPortableByteArray(bytes);

            if (length > bytes.length) {
				msgVal = new byte[length];
				length = msg.writeToPortableByteArray(msgVal);
				return length;
			}
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return -1;
    }

    public static MessageFieldRef getOrCreateRef(Realm realm, String propName) {
        MessageFieldRef mfr = fieldRefTable.get(propName);
        if (mfr != null) return mfr;

        mfr = realm.createMessageFieldRef(propName);
        MessageFieldRef oldmfr = fieldRefTable.putIfAbsent(propName, mfr);
        if (oldmfr != null) {
            mfr.destroy();
            return oldmfr;
        }
        return mfr;
    }
}
