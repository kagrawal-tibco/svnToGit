import com.sun.javadoc.*;
import com.sun.tools.doclets.standard.Standard;
import com.sun.tools.javadoc.Main;

import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Doclet which filters out all the {@link MemberDoc MemberDoc},
 * {@link ClassDoc ClassDoc}, {@link PackageDoc PackageDoc} items that are not
 * tagged with a valid category name.
 * <dl>
 * <dt>Usage:</dt>
 * <dd>
 * <dl>
 * <dt>Javadoc tag:</dt>
 * <dd><code>/<!---->**<br/>
 * &nbsp;* @.category <var>name1</var><br/>
 * &nbsp;* @.category <var>name2</var><br/>
 * &nbsp;* @.category <var>...</var><br/>
 * &nbsp;* @.category <var>nameN</var><br/>
 * &nbsp;*<!---->/<br/>
 * &nbsp;</code></dd>
 * <dt>Javadoc command line:</dt>
 * <dd>
 * <code>javadoc <b>{</b> -category&nbsp;<var>name</var> <b>}</b> -tag .category:X -doclet FilterDoclet -docletpath&nbsp;<var>pathToFilterDoclet</var> <b>[</b>&nbsp;otherJavadocOptions&nbsp;<b>] ...</b></code>
 * </dd>
 * </dl>
 * </dd>
 * </dl>
 */
public class FilterDoclet {

	/**
	 * Name of the custom Javadoc tag which associates a category name to a
	 * <doc>{@link Doc Doc}</code>.
	 */
	public static final String CATEGORY_TAG_NAME = ".category";

	/**
	 * Name of the option used to specify one category name on the command line.
	 */
	public static final String CATEGORY_ARG_NAME = "-category";

	private static final FilterDoclet SINGLETON = new FilterDoclet();

	protected Set categoryNames;

	protected FilterDoclet() {
		this.categoryNames = new HashSet();
	}

	/**
	 * Provides access to the internal set of category names accepted by this
	 * <code>FilterDoclet</code>. Add a String to this set to enable Javadoc for
	 * all <code>DocMember</doc>'s that are tagged with that name.
	 *
	 * @return a Set of String objects.
	 */
	public Set categoryNames() {
		return this.categoryNames;
	}

	protected boolean isValid(Object o) {
		//System.out.println("OBJ:" + o.toString() + " class: "
		//		+ o.getClass().getName());
		if ((o instanceof AnnotationDesc.ElementValuePair)
				|| (o instanceof SeeTag) || (o instanceof Tag)
				|| (o instanceof com.sun.javadoc.ParameterizedType)
				|| (o instanceof AnnotationTypeDoc)
				|| (o instanceof AnnotationTypeElementDoc)
				|| (o instanceof String) || (o instanceof String[])
				|| (o instanceof AnnotationDesc)
				|| (o instanceof AnnotationValue)) {
			
			return true;

		}
		if (!((o instanceof MemberDoc) || (o instanceof ClassDoc)
				|| (o instanceof PackageDoc) || (o instanceof FieldDoc)

		)) {
			//System.out.println("NOT A FILTERED DOC " + o.getClass().getName());
			return true;
		}
		Tag[] categoryTags = null;
		if (o instanceof AnnotationDesc || o instanceof AnnotationValue
				|| o instanceof AnnotationDesc.ElementValuePair
				|| o instanceof SeeTag || o instanceof Tag
				|| o instanceof String || o instanceof String[]
				|| o instanceof com.sun.javadoc.ParameterizedType) {
			return true;
		} else {
			categoryTags = ((Doc) o).tags(CATEGORY_TAG_NAME);
		}
		for (int i = 0; i < categoryTags.length; i++) {
			// System.out.print("TAG " + categoryTags[i]);
			if (this.categoryNames.contains(categoryTags[i].text())) {
				// System.out.print(" IS VALID");
				return true;
			}
			// System.out.println();
		}

		// System.out.println("INVALID " + o);
		return false;
	}

	protected Object filter(Object o, Class expectedClass) {
		if (null == o) {
			return null;
		}

		final Class clazz = o.getClass();
		if (clazz.getName().startsWith("com.sun.")) {
			return Proxy.newProxyInstance(clazz.getClassLoader(),
					clazz.getInterfaces(), new FilterInvocationHandler(o));
		} else if (o instanceof Object[]) {

			Class componentType = expectedClass.getComponentType();
			if (componentType == null)
				componentType = java.lang.Object.class;
			final Object[] array = (Object[]) o;
			final List list = new ArrayList(array.length);
			for (int i = 0; i < array.length; i++) {
				final Object entry = array[i];
				if (this.isValid(entry)) {
					list.add(filter(entry, componentType));
				}
			}

			return list.toArray((Object[]) Array.newInstance(componentType,
					list.size()));
		} else {
			return o;
		}
	}

	/**
	 * Gets an instance of this class.
	 *
	 * @return the FilterDoclet singleton.
	 */
	public FilterDoclet getInstance() {
		return SINGLETON;
	}

	public static void main(String[] args) {

		final String className = FilterDoclet.class.getName();
		Main.execute(className, className, args);
	}

	/**
	 * Returns the number of items expected by the given option found in the
	 * command line. Called by the Standard doclet.
	 *
	 * @param option
	 *            String found in the command line.
	 * @return an int >= 1, indicating how many parts the option has.
	 */
	public static int optionLength(String option) {
		if (CATEGORY_ARG_NAME.equals(option)) {
			return 2;
		}
		return Standard.optionLength(option);
	}

	/**
	 * Starts processing rootDoc and generating the associated files. Called by
	 * the Standard doclet.
	 *
	 * @param rootDoc
	 *            root of the program structure information for one run of
	 *            javadoc.
	 * @return a boolean used by the Standard doclet.
	 */
	public static boolean start(RootDoc rootDoc) throws IOException {
		return SINGLETON.startWithFilter(rootDoc);
	}

	/**
	 * Starts processing rootDoc and generating the associated files, filtering
	 * out the unwanted <code>MemberDoc</code>.
	 *
	 * @param rootDoc
	 *            root of the program structure information for one run of
	 *            javadoc.
	 * @return a boolean used by the Standard doclet.
	 * @throws IOException
	 */
	public boolean startWithFilter(RootDoc rootDoc) throws IOException {
		if (categoryNames.isEmpty()) {
			// System.out.println("START WITHOUT FILTER");
			return Standard.start(rootDoc);
		} else {
			// System.out.println("START WITH FILTER");
			return Standard.start((RootDoc) SINGLETON.filter(rootDoc,
					RootDoc.class));
		}
	}

	/**
	 * Validates the options provided to the command line. Called by the
	 * Standard doclet.
	 *
	 * @param options
	 *            the options provided to the command line.
	 * @param docErrorReporter
	 *            used to report issues.
	 * @return a boolean used by the Standard doclet.
	 * @throws IOException
	 */
	public static boolean validOptions(String[][] options,
			DocErrorReporter docErrorReporter) throws IOException {
		final List optionsList = new ArrayList();
		for (int i = 0; i < options.length; i++) {
			if (CATEGORY_ARG_NAME.equals(options[i][0])) {
				if (options[i].length < 2) {
					docErrorReporter.printWarning(CATEGORY_ARG_NAME
							+ " option with no value");
				} else {
					SINGLETON.categoryNames().add(options[i][1]);
					// System.out.println("ADDED " + options[i][1]);
				}
			} else {
				optionsList.add(options[i]);
			}
		}
		return Standard.validOptions((String[][]) optionsList
				.toArray(new String[optionsList.size()][]), docErrorReporter);
	}

	protected static class FilterInvocationHandler implements InvocationHandler {

		private Object target;

		public FilterInvocationHandler(Object target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] arguments)
				throws Throwable {
			if (null != arguments) {
				final String methodName = method.getName();
				if (methodName.equals("compareTo")
						|| methodName.equals("equals")
						|| methodName.equals("overrides")
						|| methodName.equals("subclassOf")) {
					arguments[0] = unwrap(arguments[0]);
				}
			}
			try {
				return SINGLETON.filter(method.invoke(this.target, arguments),
						method.getReturnType());
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}

		private Object unwrap(Object proxy) {
			if (proxy instanceof Proxy) {
				return ((FilterInvocationHandler) Proxy
						.getInvocationHandler(proxy)).target;
			}
			return proxy;
		}

	}
}
