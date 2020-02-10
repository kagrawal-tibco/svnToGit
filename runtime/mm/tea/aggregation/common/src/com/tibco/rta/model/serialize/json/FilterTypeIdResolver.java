package com.tibco.rta.model.serialize.json;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/*
 * @author:vasharma
 * 
 */

public class FilterTypeIdResolver implements TypeIdResolver
{
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());
	
    private JavaType mBaseType;

    @Override
    public void init(JavaType baseType)
    {
        mBaseType = baseType;
    }

    @Override
    public Id getMechanism()
    {
        return Id.CUSTOM;
    }

    @Override
    public String idFromValue(Object obj)
    {
        return idFromValueAndType(obj, obj.getClass());
    }

    @Override
    public String idFromBaseType()
    {
        return idFromValueAndType(null, mBaseType.getRawClass());
    }

    @Override
    public String idFromValueAndType(Object obj, Class<?> clazz)
    {    		
        return getFilterTypeByClass(obj.getClass().getSimpleName());
    }
    @Override
	public JavaType typeFromId(DatabindContext context, String type) {
		Class<?> clazz = null;
		try {
			String classPath = getFilterTypeById(type);
			clazz = TypeFactory.defaultInstance().findClass(classPath);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.ERROR, "Error while deserializing.", e);
		}
		return TypeFactory.defaultInstance().constructSpecializedType(mBaseType, clazz);
	}

	
	public static String getFilterTypeByClass(String simpleName) {
		if(simpleName!=null&&!simpleName.isEmpty())
		{
			if(simpleName.equals("AndFilterImpl"))
			{
				return "AND";
			}
			else if(simpleName.equals("OrFilterImpl"))
			{
				return "OR";
			}
			else if(simpleName.equals("NotFilterImpl"))
			{
				return "NOT";
			}
			else if(simpleName.equals("GtFilterImpl"))
			{
				return "GT";
			}
			else if(simpleName.equals("GEFilterImpl"))
			{
				return "GE";
			}
			else if(simpleName.equals("LtFilterImpl"))
			{
				return "LT";
			}
			else if(simpleName.equals("LEFilterImpl"))
			{
				return "LE";
			}
			else if(simpleName.equals("LikeFilterImpl"))
			{
				return "LIKE";
			}
			else if(simpleName.equals("EqFilterImpl"))
			{
				return "EQ";
			}
			else if(simpleName.equals("NEqFilterImpl"))
			{
				return "NEQ";
			}
			else if(simpleName.equals("InFilterImpl"))
			{
				return "IN";
			}
			
		}
		return simpleName;
	}

	public static String getFilterTypeById(String type) {
		if(type!=null&&!type.isEmpty())
		{
			if(type.equals("AND"))
			{
				return "com.tibco.rta.query.filter.impl.AndFilterImpl";
			}
			else if(type.equals("OR"))
			{
				return "com.tibco.rta.query.filter.impl.OrFilterImpl";
			}
			else if(type.equals("NOT"))
			{
				return "com.tibco.rta.query.filter.impl.NotFilterImpl";
			}
			else if(type.equals("GT"))
			{
				return "com.tibco.rta.query.filter.impl.GtFilterImpl";
			}
			else if(type.equals("GE"))
			{
				return "com.tibco.rta.query.filter.impl.GEFilterImpl";
			}
			else if(type.equals("LT"))
			{
				return "com.tibco.rta.query.filter.impl.LtFilterImpl";
			}
			else if(type.equals("LE"))
			{
				return "com.tibco.rta.query.filter.impl.LEFilterImpl";
			}
			else if(type.equals("LIKE"))
			{
				return "com.tibco.rta.query.filter.impl.LikeFilterImpl";
			}
			else if(type.equals("EQ"))
			{
				return "com.tibco.rta.query.filter.impl.EqFilterImpl";
			}
			else if(type.equals("NEQ"))
			{
				return "com.tibco.rta.query.filter.impl.NEqFilterImpl";
			}
			else if(type.equals("IN"))
			{
				return "com.tibco.rta.query.filter.impl.InFilterImpl";
			}
			
		}
		return type;

	}
	@Override
	public String getDescForKnownTypeIds() {
		return "Class name used as type id";
	}
}