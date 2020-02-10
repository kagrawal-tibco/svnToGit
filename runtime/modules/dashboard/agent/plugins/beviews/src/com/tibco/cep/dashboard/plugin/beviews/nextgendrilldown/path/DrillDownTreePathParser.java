package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.kernel.service.logging.Logger;

public class DrillDownTreePathParser {

	public static final String NULL_VALUE_IDENTIFIER = "null";

	private static Map<String, List<String>> PARENT_CHILD_MAP = new HashMap<String, List<String>>(4);

	static {
		// root can have only type id
		PARENT_CHILD_MAP.put(null, Arrays.asList(TypeIDDrillDownTreePathElement.class.getName()));
		// type id can have instanceid, field/value
		PARENT_CHILD_MAP.put(TypeIDDrillDownTreePathElement.class.getName(), Arrays.asList(InstanceIDDrillDownTreePathElement.class.getName(), FieldValueDrillDownTreePathElement.class.getName()));
		// instance id can have typeid
		PARENT_CHILD_MAP.put(InstanceIDDrillDownTreePathElement.class.getName(), Arrays.asList(TypeIDDrillDownTreePathElement.class.getName()));
		// field/value can have instanceid,field/value
		PARENT_CHILD_MAP.put(FieldValueDrillDownTreePathElement.class.getName(), Arrays.asList(InstanceIDDrillDownTreePathElement.class.getName(), FieldValueDrillDownTreePathElement.class.getName()));
	}

	@SuppressWarnings("unused")
	private Logger logger;

	@SuppressWarnings("unused")
	private ExceptionHandler exceptionHandler;

	@SuppressWarnings("unused")
	private MessageGenerator messageGenerator;

	private EntityCache entityCache;

	public DrillDownTreePathParser(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, EntityCache entityCache) {
		super();
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		this.entityCache = entityCache;
	}

	public DrillDownTreePath parse(String path) {
		if (StringUtil.isEmptyOrBlank(path) == true) {
			return null;
		}
		List<String> tokens = tokenize(path);
		DrillDownTreePathElement[] pathElements = interpret(tokens);
		return new DrillDownTreePath(path, pathElements);
	}

	private List<String> tokenize(String path) {
		path = path.replaceAll("//", "%%");
		String[] splits = path.split("/");
		List<String> tokens = new LinkedList<String>();
		for (String split : splits) {
			if (StringUtil.isEmptyOrBlank(split) == false) {
				tokens.add(split.replaceAll("%%", "/"));
			}
		}
		return tokens;
	}

	private DrillDownTreePathElement[] interpret(List<String> tokens) {
		DrillDownTreePathElement[] elements = new DrillDownTreePathElement[tokens.size()];
		int i = 0;
		TypeIDDrillDownTreePathElement prevPathElement = null;
		for (String token : tokens) {
			// first token should always be typeid
			if (prevPathElement == null) {
				// Root to be type id
				Entity entity = entityCache.getEntity(token);
				if ((entity instanceof Concept) == false) {
					throw new IllegalArgumentException(token + " is not a metric or concept");
				}
				elements[i] = new TypeIDDrillDownTreePathElement(token, entity);
			} else {
				List<String> splits = split(token);
				if (splits.size() >= 2) {
					// we have a field value pair possibly fieldname=value or fieldname=NULL_VALUE_IDENTIFIER=true
					if (splits.size() == 2) {
						elements[i] = createFieldValuePathElement(token, (Concept) prevPathElement.getEntity(), unescapeValue(splits.get(0)), unescapeValue(splits.get(1)), null);
					}
					else {
						elements[i] = createFieldValuePathElement(token, (Concept) prevPathElement.getEntity(), unescapeValue(splits.get(0)), unescapeValue(splits.get(1)), unescapeValue(splits.get(2)));
					}
				} else {
					try {
						Entity entity = entityCache.getEntity(token);
						elements[i] = new TypeIDDrillDownTreePathElement(token, entity);
					} catch (IllegalArgumentException e) {
						// we have a instance id
						elements[i] = new InstanceIDDrillDownTreePathElement(token, prevPathElement.getEntity());
					}
				}
				//validate the parent-child
				boolean valid = PARENT_CHILD_MAP.get(prevPathElement.getClass().getName()).contains(elements[i].getClass().getName());
				if (valid == false) {
					throw new IllegalArgumentException("Invalid child "+ token+" under "+prevPathElement.getToken());
				}
			}
			prevPathElement = (TypeIDDrillDownTreePathElement) elements[i];
			i++;
		}
		return elements;
	}

	private List<String> split(String token) {
		List<String> splits = new ArrayList<String>();
		char[] tokenAsChars = token.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : tokenAsChars) {
			if (c == '=') {
				//check if previous char is '\'
				if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\\') {
					sb.setCharAt(sb.length() - 1, c);
				}
				else {
					splits.add(sb.toString());
					sb.setLength(0);
				}
			}
			else {
				sb.append(c);
			}
		}
		if (sb.length() > 0) {
			splits.add(sb.toString());
		}
		return splits;
	}

	private DrillDownTreePathElement createFieldValuePathElement(String token, Concept concept, String fieldName, String fieldValueOrNullIdentifier, String isNull) {
		TupleSchema schema = TupleSchemaFactory.getInstance().getDynamicBaseTupleSchema(concept.getGUID());
		TupleSchemaField selectedField = schema.getFieldByName(fieldName);
		if (selectedField == null) {
			throw new IllegalArgumentException(fieldName + " is not a field in " + concept.getFullPath());
		}
		FieldValue value = null;
		if (isNull != null && fieldValueOrNullIdentifier.equals(NULL_VALUE_IDENTIFIER) == true) {
			boolean isNullFlag = Boolean.parseBoolean(isNull);
			if (isNullFlag == false) {
				throw new IllegalArgumentException("Invalid null field value "+fieldValueOrNullIdentifier+"="+isNull + " for " + fieldName + " in " + concept.getFullPath());
			}
			value = new FieldValue(selectedField.getFieldDataType(),isNullFlag);
		}
		else {
			// validate the value
			try {
				value = new FieldValue(selectedField.getFieldDataType(), selectedField.getFieldDataType().valueOf(fieldValueOrNullIdentifier));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(fieldValueOrNullIdentifier + " is not a valid value for " + fieldName + " in " + concept.getFullPath());
			}
		}
		return new FieldValueDrillDownTreePathElement(token, concept, fieldName, selectedField.getFieldDataType(), value);
	}

	public static final String escapeValue(String value) {
		//escape = in fieldvalue if any
		value = value.replace("\\", "\\\\");
		value = value.replace("=", "\\=");
		value = value.replaceAll("\r", "#xD");
		value = value.replaceAll("\n", "#xA");
		return value;
	}

	static final String unescapeValue(String value){
		value = value.replace("\\\\", "\\");
		value = value.replaceAll("#xD", "\r");
		value = value.replaceAll("#xA", "\n");
		return value;
	}

	public static void main(String[] args) {
		DrillDownTreePathParser parser = new DrillDownTreePathParser(null, null, null, null);
//		String[] testargs = new String[] { "C1/C2", "C1/C//2", "C1/C///2", "/", "//", "C1/C2", "//C1/C2"};
		String[] testargs = new String[] {"F17B0CD4-DF72-1549-63A4-85C315D9215A/3113981/F17B0CD4-DF72-1549-63A4-85C315D9215A@dvm/EmpId=1023/FName=Rohit/LName=Pegallapati/joiningdate=2009-11-16T09:30:15.000+0530/City=PA\n"};
		for (String test : testargs) {
			System.out.println(test + " -> " + parser.tokenize(test));
		}
//		String[] testargs = new String[] { "foo=bar", "foo=isnull=true", "foo=barvalue=", "foo=barvalue\\=", "foo=bar\\=value", "foo=bar\\\\=value", "foo=bar\\=\\=value", "foo=bar\\\\\\=value"};
//		for (String test : testargs) {
//			System.out.println(test + " -> " + parser.split(test));
//		}
	}
}
