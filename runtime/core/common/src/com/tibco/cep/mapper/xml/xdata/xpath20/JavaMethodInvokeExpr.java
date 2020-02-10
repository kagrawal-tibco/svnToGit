package com.tibco.cep.mapper.xml.xdata.xpath20;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.genxdm.processor.xpath.v20.widget.AbstractNodePullExpr;
import org.genxdm.typed.TypedContext;
import org.genxdm.xpath.v20.expressions.DynamicContext;
import org.genxdm.xpath.v20.expressions.Expr;
import org.genxdm.xpath.v20.expressions.Focus;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.xquery.ExprException;

public class JavaMethodInvokeExpr extends AbstractNodePullExpr
{
    private final Method m_method;
    private final ExpandedName m_name;
    private final Expr[] m_args;

    public JavaMethodInvokeExpr(final Method method, final ExpandedName name, final Expr[] args)
    {
        m_method = method;
        m_name = name;
        m_args = args;
    }

    /**
     * Executes a Java method as if it were a an XPath expression
     *
     * @param focus The expression focus.
     * @param host  The expression context.
     * @return A sequence of items.
     * @throws ExprException if the method invocation raises an exception.
     *                       The expression is wrapped and preserved by the ExprException. The
     *                       checked exceptions raised by the method invocation are {@link IllegalAccessException}
     *                       and {@link InvocationTargetException}. All other unchecked exceptions
     *                       are similarly wrapped.
     */
	@Override
	public <N, A> N node(Focus<N, A> arg0, DynamicContext<N, A> arg1,
			TypedContext<N, A> arg2)
			throws org.genxdm.xpath.v20.err.ExprException {
		try {
			return JavaMethodInvoker.invoke(arg0, arg1, arg2, m_method, m_args);
		} catch (org.genxdm.xpath.v10.ExprException e) {
			e.printStackTrace();
		}
		return null;
	}
}
