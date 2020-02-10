package com.tibco.cep.runtime.plugin.lv;

import com.streambase.liveview.client.LiveViewException;
import com.streambase.liveview.client.LiveViewExceptionType;
import com.streambase.sb.Schema;
import com.streambase.sb.Schema.Field;
import com.streambase.sb.Timestamp;
import com.streambase.sb.Tuple;
import com.streambase.sb.TupleException;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.query.api.Row;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 10/7/14
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class BETupleCopier {

    /**
     * @param args
     */
    private List<BETupleFieldCopier> fieldCopiers;
    private Schema resultSchema;

    public BETupleCopier(Schema resultSchema, Object eveConcept) throws LiveViewException, NoSuchFieldException {

        this.resultSchema = resultSchema;
        List<BETupleFieldCopier> copiers = new ArrayList<BETupleFieldCopier>();

        for (Schema.Field field : resultSchema) {
            final String asFieldName = field.getName();
            PropertyDefinition fieldDef = null;
            if(eveConcept instanceof SimpleEvent)
                fieldDef = (PropertyDefinition) ((SimpleEvent)eveConcept).getProperty(asFieldName);
            else if(eveConcept instanceof Concept)
                fieldDef = (PropertyDefinition) ((SimpleEvent)eveConcept).getProperty(asFieldName);
            BETupleFieldCopier copier;
            switch (fieldDef.getType()) {
//            case BLOB:
//                copier = new ASTFC_Blob(field, asFieldName);
//                break;
                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                    copier = new BETFC_Boolean(field, asFieldName);
                    break;
//            case CHAR:
//                copier = new ASTFC_Char2Int(field, asFieldName);
//                break;
                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                    copier = new BETFC_Datetime(field, asFieldName);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_REAL:
                    copier = new BETFC_Double(field, asFieldName);
                    break;
//            case FLOAT:
//                copier = new BETFC_Float2Double(field, asFieldName);
//                break;
                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                    copier = new BETFC_Int(field, asFieldName);
                    break;
                case PropertyDefinition.PROPERTY_TYPE_LONG:
                    copier = new BETFC_Long(field, asFieldName);
                    break;
//            case SHORT:
//                copier = new ASTFC_Short2Int(field, asFieldName);
//                break;
                case PropertyDefinition.PROPERTY_TYPE_STRING:
                    copier = new BETFC_String(field, asFieldName);
                    break;
                default:
                    throw LiveViewExceptionType.UNEXPECTED.error("Unrecognized field type");
            }
            copiers.add(copier);
        }
        fieldCopiers = copiers;
    }

    public Schema getSchema() {
        return resultSchema;
    }

    public Tuple copy(Row row) throws TupleException, NoSuchFieldException {
        Tuple destination = resultSchema.createTuple();
        for (BETupleFieldCopier copier : fieldCopiers) {
            copier.copy(row, destination);
        }
        return destination;
    }

//    public Tuple copy(Concept source) throws TupleException, NoSuchFieldException {
//        Tuple destination = resultSchema.createTuple();
//        for (BETupleFieldCopier copier : fieldCopiers) {
//            copier.copy(source, destination);
//        }
//        return destination;
//    }
//
//    public SimpleEvent copy(Tuple source) throws TupleException {
//        SimpleEvent result = null;
//        for (BETupleFieldCopier copier : fieldCopiers) {
//            copier.copy(source, result);
//        }
//
//        return result;
//    }

//    public Concept copy(Tuple source) throws TupleException {
//        Concept result = null;
//        for (BETupleFieldCopier copier : fieldCopiers) {
//            copier.copy(source, result);
//        }
//
//        return result;
//    }

    private static abstract class BETupleFieldCopier {
        protected Schema.Field field;
        protected String asFieldName;
        public BETupleFieldCopier(Schema.Field field, String asFieldName) {
            this.field = field;
            this.asFieldName = asFieldName;
        }

        abstract public void copy(Row row, Tuple destination) throws TupleException, NoSuchFieldException;
//        abstract public void copy(Concept source, Tuple destination) throws TupleException, NoSuchFieldException;
//        abstract public void copy(Tuple source, SimpleEvent destination) throws TupleException;
//        abstract public void copy(Tuple source, Concept destination) throws TupleException;

        public String toString() {
            return field.toString();
        }
    }

    private static class BETFC_Boolean extends BETupleFieldCopier {

        public BETFC_Boolean(Field field, String asFieldName) {
            super(field, asFieldName);
        }

        @Override
        public void copy(Row source, Tuple destination)
                throws TupleException, NoSuchFieldException {
            Boolean val = (Boolean) source.getColumn(asFieldName);
            if (val == null) {
                destination.setNull(field);
            }
            else {
                destination.setBoolean(field, val);
            }

        }

//		@Override
//		public void copy(Concept source, Tuple destination)
//				throws TupleException {
//			Property valProp = source.getProperty(asFieldName);
//			PropertyAtom val = (PropertyAtom) valProp;
//            if (val == null) {
//                destination.setNull(field);
//            }
//            else {
//                destination.setBoolean(field, ((PropertyAtomBoolean)val).getBoolean());
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, SimpleEvent destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setProperty(asFieldName, source.getBoolean(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, Concept destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setPropertyValue(asFieldName, source.getBoolean(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}

    }

    private static class BETFC_Datetime extends BETupleFieldCopier {

        public BETFC_Datetime(Field field, String asFieldName) {
            super(field, asFieldName);
        }

        @Override
        public void copy(Row source, Tuple destination)
                throws TupleException, NoSuchFieldException {
            Calendar dateTime = (Calendar) source.getColumn(asFieldName);
            if (dateTime == null) {
                destination.setNull(field);
            }
            else {
                Timestamp value = Timestamp.msecs(Timestamp.TIMESTAMP, dateTime.getTimeInMillis());
                destination.setTimestamp(field, value);
            }

        }

//		@Override
//		public void copy(Concept source, Tuple destination)
//				throws TupleException {
//			Property valProp = source.getProperty(asFieldName);
//			PropertyAtomDateTime val = (PropertyAtomDateTime) valProp;
//			Calendar dateTime = val.getDateTime();
//            if (dateTime == null) {
//                destination.setNull(field);
//            }
//            else {
//                Timestamp value = Timestamp.msecs(Timestamp.TIMESTAMP, dateTime.getTimeInMillis());
//                destination.setTimestamp(field, value);
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, SimpleEvent destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                Timestamp timestamp = source.getTimestamp(field);
//                long timeInMillis = timestamp.toMsecs();
//                Calendar dateTime = null;
//                dateTime.setTimeInMillis(timeInMillis);
//                try {
//					destination.setProperty(asFieldName, dateTime);
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, Concept destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                Timestamp timestamp = source.getTimestamp(field);
//                long timeInMillis = timestamp.toMsecs();
//                Calendar dateTime = null;
//                dateTime.setTimeInMillis(timeInMillis);
//                try {
//					destination.setPropertyValue(asFieldName, dateTime);
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}



    }

    private static class BETFC_Int extends BETupleFieldCopier {

        public BETFC_Int(Field field, String asFieldName) {
            super(field, asFieldName);
        }

        @Override
        public void copy(Row source, Tuple destination)
                throws TupleException, NoSuchFieldException {
            Integer val = (Integer) source.getColumn(asFieldName);
            if (val == null) {
                destination.setNull(field);
            }
            else {
                destination.setInt(field, val);
            }

        }

//		@Override
//		public void copy(Concept source, Tuple destination)
//				throws TupleException {
//			Property valProp = source.getProperty(asFieldName);
//			PropertyAtom val = (PropertyAtom) valProp;
//            if (val == null) {
//                destination.setNull(field);
//            }
//            else {
//                destination.setInt(field, ((PropertyAtomInt) val).getInt());
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, SimpleEvent destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setProperty(asFieldName, (Object)source.getInt(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, Concept destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setPropertyValue(asFieldName, source.getInt(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}

    }

    private static class BETFC_String extends BETupleFieldCopier {

        public BETFC_String(Field field, String asFieldName) {
            super(field, asFieldName);
        }

        @Override
        public void copy(Row source, Tuple destination)
                throws TupleException, NoSuchFieldException {

            destination.setString(field, (String) source.getColumn(asFieldName));


        }

//		@Override
//		public void copy(Concept source, Tuple destination)
//				throws TupleException {
//			Property valProp = source.getProperty(asFieldName);
//			PropertyAtom val = (PropertyAtom) valProp;
//			destination.setString(field, ((PropertyAtomString) val).getString());
//
//		}
//
//		@Override
//		public void copy(Tuple source, SimpleEvent destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setProperty(asFieldName, source.getString(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, Concept destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setPropertyValue(asFieldName, source.getString(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}

    }

    private static class BETFC_Long extends BETupleFieldCopier {

        public BETFC_Long(Field field, String asFieldName) {
            super(field, asFieldName);
        }

        @Override
        public void copy(Row source, Tuple destination)
                throws TupleException, NoSuchFieldException {
            Long val = (Long) source.getColumn(asFieldName);
            if (val == null) {
                destination.setNull(field);
            }
            else {
                destination.setLong(field, val);
            }

        }

//		@Override
//		public void copy(Concept source, Tuple destination)
//				throws TupleException {
//			Property valProp = source.getProperty(asFieldName);
//			PropertyAtom val = (PropertyAtom) valProp;
//            if (val == null) {
//                destination.setNull(field);
//            }
//            else {
//                destination.setLong(field, ((PropertyAtomLong)val).getLong());
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, SimpleEvent destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setProperty(asFieldName, source.getLong(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, Concept destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setPropertyValue(asFieldName, source.getLong(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
    }

    private static class BETFC_Double extends BETupleFieldCopier {

        public BETFC_Double(Field field, String asFieldName) {
            super(field, asFieldName);
        }

        @Override
        public void copy(Row source, Tuple destination)
                throws TupleException, NoSuchFieldException {
            Double val = (Double) source.getColumn(asFieldName);
            if (val == null) {
                destination.setNull(field);
            }
            else {
                destination.setDouble(field, val);
            }

        }

//		@Override
//		public void copy(Concept source, Tuple destination)
//				throws TupleException {
//
//			Property valProp = source.getProperty(asFieldName);
//			PropertyAtom val = (PropertyAtom) valProp;
//			if (val == null) {
//                destination.setNull(field);
//            }
//            else {
//                destination.setDouble(field, ((PropertyAtomDouble)val).getDouble());
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, SimpleEvent destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setProperty(asFieldName, source.getDouble(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}
//
//		@Override
//		public void copy(Tuple source, Concept destination)
//				throws TupleException {
//			if (!source.isNull(field)) {
//                try {
//					destination.setPropertyValue(asFieldName, source.getDouble(field));
//				} catch (Exception e) {
//					throw new TupleException(e);
//				}
//            }
//
//		}

    }
}