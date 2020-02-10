/**
 * 
 */
package com.tibco.cep.store.as.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.store.Item;
import com.tibco.cep.store.as.ASContainer;
import com.tibco.cep.store.as.ASItem;
import com.tibco.cep.store.serializer.ItemCodec;
import com.tibco.datagrid.ColumnType;
import com.tibco.datagrid.Row;
import com.tibco.datagrid.TibDateTime;
import com.tibco.datagrid.exceptions.DataGridNotFoundException;
import com.tibco.datagrid.exceptions.DataGridOutOfRangeException;

/**
 * @author vpatil
 *
 */
public class ASRowCodec implements ItemCodec {
	private Logger logger;
	
	public ASRowCodec() {
		logger = LogManagerFactory.getLogManager().getLogger(ASRowCodec.class);
	}
	
	@Override
	public void putInItem(Item item, String fieldName, FieldType fieldType, Object fieldValue) {
		ASItem dgItem = (ASItem) item;
		Row row = dgItem.getRow();
		
		try {
			ColumnType columnType = ((ASContainer) dgItem.getContainer()).getTableMetadata().getColumnType(fieldName);
			if (columnType != null) {
				switch (fieldType) {
				case INTEGER:
				case LONG: 
				case BOOLEAN:
					Long longValue = null;
					if (fieldType == FieldType.BOOLEAN) {
						longValue = ((Boolean)fieldValue) ? 1L : 0L;
					} else {
						longValue = (Long) ((fieldType == FieldType.INTEGER) ? ((Integer) fieldValue).longValue() : fieldValue);
					}
					row.setLong(fieldName, longValue);
					break;
				case DOUBLE:
					Double doubleValue = (Double) ((fieldValue instanceof Float) ? ((Float) fieldValue).doubleValue() : fieldValue);
					row.setDouble(fieldName, doubleValue);
					break;
				case STRING:
					String stringValue = (String) fieldValue;
					row.setString(fieldName, stringValue);
					break;
				case DATETIME:
					if (fieldValue instanceof Calendar) {
						TibDateTime tibDateTime = new TibDateTime();
						tibDateTime.setFromDate(((Calendar)fieldValue).getTime());
						row.setDateTime(fieldName, tibDateTime);
					}

					break;
				case BLOB:
					try {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(fieldValue);
						oos.close();
						row.setOpaque(fieldName, baos.toByteArray());
					}
					catch (IOException e) {
						throw new RuntimeException(e);
					}

					break;
				default: throw new IllegalArgumentException(String.format("No matching type [%s] found for field[%s]", fieldType, fieldValue));
				}
			}
		} catch(Exception e) {
			// case for column does not exist in the table
			if (e instanceof DataGridNotFoundException) {
				try {
					if (!isSystemField(fieldName)) logger.log(Level.ERROR, String.format("Field [%s] of type [%s] not found in container[%s].", fieldName, fieldType.toString(), ((ASContainer) dgItem.getContainer()).getTableMetadata().getName()));
				} catch (Exception exception) {
					throw new RuntimeException (exception);
				}
			} else {
				throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public Object getFromItem(Item item, String fieldName, FieldType fieldType) {
		ASItem dgItem = (ASItem) item;
		Row row = dgItem.getRow();
		
		Object value = null;
		
		try {
			ColumnType columnType = ((ASContainer) dgItem.getContainer()).getTableMetadata().getColumnType(fieldName);
			if (columnType != null) {
				if (row.isColumnSet(fieldName)) {
					switch (fieldType) {
					case INTEGER:
					case LONG:
					case BOOLEAN:
						Long longValue = row.getLong(fieldName);
						if (fieldType == FieldType.BOOLEAN) {
							value = (longValue == 1) ? Boolean.TRUE : Boolean.FALSE;
						} else {
							if (fieldType == FieldType.INTEGER) value = longValue.intValue();
							else value = longValue;
						}
						break;
					case FLOAT:
					case DOUBLE:
						value = row.getDouble(fieldName);
						break;
					case STRING:
						value = row.getString(fieldName);
						break;
					case DATETIME:
						value = row.getDateTime(fieldName);
						Calendar cal = null;
						if (value instanceof TibDateTime) {
							cal = Calendar.getInstance();
							try {
								cal.setTime(((TibDateTime)value).toDate());
							} catch (DataGridOutOfRangeException dgoutofRange) {
								throw new RuntimeException(dgoutofRange);
							}
							cal.getTimeInMillis();
						}
						value = cal;
						break;
					case BLOB:
						byte[] bytes = row.getOpaque(fieldName);
						if (bytes != null) {
							ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
							ObjectInputStream ois = new ObjectInputStream(bais);
							value = ois.readObject();
							ois.close();
						}
						break;
					default: throw new IllegalArgumentException(String.format("No matching type [%s] found for field[%s]", fieldType, fieldName));
					}
				}
			}
		} catch(Exception e) {
			// case for column does not exist in the table
			if (e instanceof DataGridNotFoundException) {
				try {
					if (!isSystemField(fieldName)) logger.log(Level.ERROR, String.format("Field [%s] of type [%s] not found in container[%s].", fieldName, fieldType.toString(), ((ASContainer) dgItem.getContainer()).getTableMetadata().getName()));
				} catch (Exception exception) {
					throw new RuntimeException (exception);
				}
				return null;
			} else {
				throw new RuntimeException(e);
			}
		}
		return value;
	}
}
