package com.tibco.cep.dashboard.plugin.beviews.common.query;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryLiteralValue;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;

public class CacheQueryLiteralValue extends QueryLiteralValue {

	private static final long serialVersionUID = 8846198645178336652L;

	public CacheQueryLiteralValue(TupleSchema schema, FieldValue literalValue) {
		super(schema, literalValue);
	}

	@Override
	public String toString() {
		String value;
		if (literalValue.getDataType() == BuiltInTypes.DATETIME) {
			value = BEViewsQueryDateTypeInterpreter.DATETIME_CONVERTOR.format(literalValue.getValue());
		} else if (literalValue.getDataType() == BuiltInTypes.STRING) {
			value = "\"" + literalValue.toString() + "\"";
		} else {
			value = literalValue.toString();
			//strip away leading zero's that causes issues with com.tibco.cep.query.model.impl.NumberLiteralImpl
			//com.tibco.cep.query.model.impl.NumberLiteralImpl assumes that anything starting with 0 is octal
			//so it will fails to parse 0.0 or 000111 or 0002121.1
			//value = value.replaceFirst("^0+(?!$)", "");
			value = removeLeadingZeros(value);
		}
		return value;
	}

	private static String removeLeadingZeros(String s){
		StringBuilder sb = new StringBuilder();
		char[] charArray = s.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == '+') {
				sb.append(c);
			}
			else if (c == '-') {
				sb.append(c);
			}
			else if (c == '0') {
				if (i + 1 == charArray.length) {
					sb.append(c);
				}
			}
			else {
				sb.append(charArray, i, charArray.length-i);
				break;
			}
		}
		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		CacheQueryLiteralValue queryLiteralValue = new CacheQueryLiteralValue(super.getSchema(), literalValue);
		queryLiteralValue.setAlias(mAlias);
		return queryLiteralValue;
	}

//	public static void main(String[] args) {
//		String[] testData = new String[] {
//			"0.0",
//			"0001111",
//			"0002121.1",
//			"0002121.10000",
//			"0020.34",
//			"0020",
//			"+0.0",
//			"+0001111",
//			"+0002121.1",
//			"+0020.34",
//			"+0020",
//			"-0.0",
//			"-0001111",
//			"-0002121.1",
//			"-0020.34",
//			"-0020",
//			"0",
//			"00000"
//		};
//		for (int i = 0; i < testData.length; i++) {
//			String data = testData[i];
//			System.out.println(data +" --> "+removeLeadingZeros(data));
//		}
//	}

}
