package com.tibco.be.functions.xpath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.genxdm.processor.xpath.v20.widget.XTypeWidget;
import org.genxdm.typed.TypedContext;
import org.genxdm.typed.types.Emulation;
import org.genxdm.typed.types.Quantifier;
import org.genxdm.typed.types.TypesBridge;
import org.genxdm.xpath.v20.expressions.Expr;
import org.genxdm.xpath.v20.expressions.SequenceInfo;
import org.genxdm.xpath.v20.expressions.StaticContext;
import org.genxdm.xpath.v20.functions.FunctionImpls;
import org.genxdm.xpath.v20.functions.FunctionSign;
import org.genxdm.xpath.v20.functions.FunctionSigns;
import org.genxdm.xs.ComponentProvider;
import org.genxdm.xs.types.NativeType;
import org.genxdm.xs.types.SequenceType;
import org.genxdm.xs.types.Type;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.util.BECustomFunctionHelper;
import com.tibco.cep.mapper.xml.xdata.xpath20.InvokeJavaMethodXFunction;
import com.tibco.xml.xmodel.xpath.func.XFunction;
import com.tibco.xml.xmodel.xpath.type.XType;
import com.tibco.xml.xmodel.xpath.type.XTypeSupport;

public class BECustomFunctionImpls implements FunctionImpls, FunctionSigns {

	private class Key {
		QName fnName;
		int arity;
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Key)) {
				return false;
			}
			Key key = (Key) obj;
			
			return this.arity == key.arity && this.fnName.equals(key.fnName);
		}
		
		@Override
		public int hashCode() {
			return this.arity >> this.fnName.hashCode();
		}
	}
	
	private class BEFunctionSign implements FunctionSign {

		private XFunction func;

		public BEFunctionSign(XFunction func) {
			this.func = func;
		}

		@Override
		public SequenceType argType(final int index, int arity, final Emulation emulation, final TypesBridge metaBridge, final ComponentProvider cp) {
			XType argType = func.getArgType(index);
			String primType = XTypeSupport.getPrimitiveTypeName(argType);
			Type typeDefinition = cp.getTypeDefinition(new QName(XType.XMLSCHEMA_NAMESPACE, primType));
			return typeDefinition;
		}

		@Override
		public boolean getLastArgRepeats(int arg0) {
			return false;
		}

		@Override
		public int getMaxArity(Emulation arg0) {
			return func.getNumArgs();
		}

		@Override
		public int getMinArity(Emulation arg0) {
			return func.getMinimumNumArgs();
		}

		@Override
		public <N, A> SequenceInfo returnType(final List<SequenceInfo> args, int arity, final SequenceType focus, final StaticContext env, final TypedContext<N, A> tcx) {

	        TypesBridge metaBridge = tcx.getTypesBridge();
	        ComponentProvider cp = tcx.getSchema().getComponentProvider();
	        final SequenceType type = args.get(0).getType();
	        final SequenceType t1 = metaBridge.prime(type);
	        final SequenceType t2 = XTypeWidget.convertUntypedAtomic(t1, cp.getTypeDefinition(NativeType.DOUBLE), metaBridge, cp);
	        if (metaBridge.sameAs(t2, metaBridge.emptyType()))
	        {
	            return new SequenceInfo(metaBridge.emptyType());
	        }
	        else
	        {
	            final SequenceType[] targets = metaBridge.typeArray(5);
	            targets[0] = cp.getTypeDefinition(NativeType.DECIMAL);
	            targets[1] = cp.getTypeDefinition(NativeType.FLOAT);
	            targets[2] = cp.getTypeDefinition(NativeType.DOUBLE);
	            targets[3] = cp.getTypeDefinition(NativeType.DURATION_YEARMONTH);
	            targets[4] = cp.getTypeDefinition(NativeType.DURATION_DAYTIME);
	            final SequenceType t0 = XTypeWidget.commonPromotion(t2, metaBridge, cp, targets);
	            if (null != t0)
	            {
	                final Quantifier aggregate = metaBridge.quantifier(type).single();
	                return new SequenceInfo(metaBridge.multiply(t0, aggregate));
	            }
	            else
	            {
	                return new SequenceInfo(metaBridge.noneType());
	            }
	        }
		}
		
	}
	
	// TODO : initialize functionMap with java xfunctions
	private Map<Key, XFunction> functionMap = new HashMap<BECustomFunctionImpls.Key, XFunction>();
	
	@Override
	public FunctionSign resolveFunctionSign(QName fnName, int argLength) {
		Key key = new Key();
		key.arity = argLength;
		key.fnName = fnName;
		XFunction func = functionMap.get(key);
		if (func != null) {
			return new BEFunctionSign(func);
		}

		try {
			String cat = fnName.getNamespaceURI().substring(BECustomFunctionHelper.BE_CUSTOM_NAMESPACE.length());
			cat = cat.replaceAll("\\-", ".");
			Object lookup = FunctionsCatalog.getINSTANCE().lookup(cat+'.'+fnName.getLocalPart(), false);
			if (lookup instanceof JavaStaticFunction) {
				JavaStaticFunction fn = (JavaStaticFunction) lookup;
				InvokeJavaMethodXFunction xfunc = new InvokeJavaMethodXFunction(fnName.getNamespaceURI(), fn.getMethod());
				functionMap.put(key, xfunc);
				return new BEFunctionSign(xfunc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Expr resolveFunctionImpl(QName fnName, Expr[] args) {
		Key key = new Key();
		key.arity = args.length;
		key.fnName = fnName;
		functionMap.keySet().iterator().next().equals(key);
		InvokeJavaMethodXFunction expr = (InvokeJavaMethodXFunction) functionMap.get(key);
		if (expr != null) {
			return expr.getAsExpr(args);
		}
		return null;//new JavaMethodInvokeExpr(null, null, null);
	}

}
