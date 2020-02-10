import com.tibco.be.model.functions.*;
import com.tibco.be.model.functions.impl.JavaStaticFunction;

import java.lang.reflect.Method;
import java.util.*;


/**
 * User: nprade
 * Date: 4/27/12
 * Time: 3:03 PM
 */
public class FunctionCatalogConverter {


    public FunctionCatalogDocumentation convertCatalog(
            FunctionsCategory category) {

        final FunctionCatalogDocumentation catalogDocumentation =
                new FunctionCatalogDocumentation(category.getName().localName);

        final SortedMap<String, FunctionCategoryDocumentation> nameToCategoryDoc = catalogDocumentation.getCategories();

        final Context context = new Context();

        for (final Iterator<FunctionsCategory> i = category.allSubCategories(); i.hasNext(); ) {
            final FunctionsCategory subCategory = i.next();
            final FunctionCategoryDocumentation subCategoryDoc = convertCategory(subCategory, null, context);
            nameToCategoryDoc.put(subCategory.getName().localName, subCategoryDoc);
        }

        return catalogDocumentation;
    }


    private FunctionCategoryDocumentation convertCategory(
            FunctionsCategory category,
            FunctionCategoryDocumentation parent,
            Context context) {

        final FunctionCategoryDocumentation categoryDocumentation =
                new FunctionCategoryDocumentation(category.getName().localName, null, null, parent);

        updateDescription(categoryDocumentation, null, context);

        context.setCategoryDocumentation(categoryDocumentation.getFullName(), categoryDocumentation);

        final SortedMap<String, FunctionCategoryDocumentation> nameToSubCategoryDoc =
                categoryDocumentation.getSubCategories();

        for (final Iterator<FunctionsCategory> i = category.allSubCategories(); i.hasNext(); ) {
            final FunctionsCategory subCategory = i.next();
            final FunctionCategoryDocumentation subCategoryDoc = convertCategory(subCategory, categoryDocumentation, context);
            nameToSubCategoryDoc.put(subCategory.getName().localName, subCategoryDoc);
        }

        final SortedMap<String, FunctionDocumentation> nameTofunction =
                categoryDocumentation.getFunctions();

        for (final Iterator<Predicate> i = category.allFunctions(); i.hasNext(); ) {
            final Predicate function = i.next();
            final FunctionDocumentation functionDoc = convertFunction(function, context);
            if (null != functionDoc) {
                nameTofunction.put(functionDoc.getName(), functionDoc);
            }
        }

        return categoryDocumentation;
    }


    private void convertCategoryDescription(
            Class<?> clazz,
            Context context) {

        final BEPackage annotation = clazz.getAnnotation(BEPackage.class);

        if (null != annotation) {
            final FunctionCategoryDocumentation categoryDocumentation =
                    context.getCategoryDocumentation(annotation.category());

            updateDescription(categoryDocumentation, annotation.synopsis(), context);
        }
    }


    private void updateDescription(
            FunctionCategoryDocumentation categoryDocumentation,
            String synopsys,
            Context context) {

        if (null != categoryDocumentation) {

            if ((null == synopsys)  || synopsys.isEmpty()) {
                final String description = context.getCategoryDescription(categoryDocumentation.getFullName());

                if ((null != description) && !description.trim().isEmpty()) {
                    categoryDocumentation.setDescription(description);
                }

            } else {
                final String oldDescription = categoryDocumentation.getDescription();

                if (!synopsys.equals(oldDescription)) {
                    final String categoryName = categoryDocumentation.getFullName();

                    if ((null == oldDescription) || oldDescription.trim().isEmpty()) {
                        context.setCategoryDescription(categoryName, synopsys);
                        categoryDocumentation.setDescription(synopsys);
                    }
                    else {
                        System.err.println("Warning: Conflicting descriptions for '" + categoryName
                                + "':\n    1) '" + oldDescription
                                + "'\n    2) '" + synopsys + "'");
                    }
                }
            }
        }
    }


    private String convertDomain(
            FunctionDomain[] functionDomains) {

        final StringBuilder sb = new StringBuilder();
        if (null != functionDomains) {
            boolean comma = false;
            for (final FunctionDomain name : functionDomains) {
                if (comma) {
                    sb.append(", ");
                }
                else {
                    comma = true;
                }
                sb.append(name.name());
            }
        }
        return convertString(sb.toString());
    }


    private FunctionDocumentation convertFunction(
            Predicate function,
            Context context) {

        if (function instanceof JavaStaticFunction) {
            final Method method = ((JavaStaticFunction) function).getMethod();

            convertCategoryDescription(method.getDeclaringClass(), context);

            final BEFunction annotation = method.getAnnotation(BEFunction.class);

            if (null != annotation) {
                final FunctionDocumentation functionDocumentation = new FunctionDocumentation(
                        annotation.name(),
                        convertParameter(annotation.freturn()),
                        convertString(annotation.signature()),
                        convertString(annotation.synopsis()),
                        convertString(annotation.description()),
                        convertDomain(annotation.fndomain()),
                        "" + annotation.mapper(),
                        convertString(annotation.see()),
                        convertString(annotation.cautions()),
                        convertString(annotation.example()),
                        convertString(annotation.version()));

                final LinkedHashMap<String, FunctionParameterDocumentation> args = functionDocumentation.getArguments();
                for (final FunctionParamDescriptor param : annotation.params()) {
                    args.put(param.name(), convertParameter(param));
                }
                return functionDocumentation;
            }
        }
        return null;
    }


    private String convertString(
            String text) {

        if (null != text) {
            text = text.trim();

            if (!text.isEmpty()) {
                return text;
            }
        }

        return null;
    }


    private FunctionParameterDocumentation convertParameter(
            FunctionParamDescriptor paramDescriptor) {

        if (null == paramDescriptor) {
            return null;
        }

        return new FunctionParameterDocumentation(
                paramDescriptor.name(),
                paramDescriptor.type(),
                paramDescriptor.desc());
    }


    private static class Context {


        private final Map<String, FunctionCategoryDocumentation> categoryNameToCategoryDoc =
                new HashMap<String, FunctionCategoryDocumentation>();

        private final Map<String, String> categoryNameToDescription =
                new HashMap<String, String>();


        public String getCategoryDescription(
                String name) {

            return categoryNameToDescription.get(name);
        }


        public FunctionCategoryDocumentation getCategoryDocumentation(
                String name) {

            return categoryNameToCategoryDoc.get(name);
        }


        public void setCategoryDescription(
                String name,
                String description) {

            categoryNameToDescription.put(name, description);
        }


        public void setCategoryDocumentation(
                String name,
                FunctionCategoryDocumentation categoryDocumentation) {

            categoryNameToCategoryDoc.put(name, categoryDocumentation);
        }
    }


}
