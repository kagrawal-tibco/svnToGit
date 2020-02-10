import java.util.SortedMap;
import java.util.TreeMap;

public class FunctionCategoryDocumentation {

    protected String name;
    protected String className;
    protected String description;
    protected SortedMap<String, FunctionDocumentation> functionNameToFunction = new TreeMap<String, FunctionDocumentation>();
    protected FunctionCategoryDocumentation parent;
    protected SortedMap<String, FunctionCategoryDocumentation> subCategoryNameTosubCategory = new TreeMap<String, FunctionCategoryDocumentation>();

    public FunctionCategoryDocumentation(
            String name,
            String className,
            String description,
            FunctionCategoryDocumentation parent) {
        this.className = className;
        this.description = description;
        this.functionNameToFunction = new TreeMap<String, FunctionDocumentation>();
        this.name = name;
        this.parent = parent;
        this.subCategoryNameTosubCategory = new TreeMap<String, FunctionCategoryDocumentation>();
    }

    public String getClassName() {
        return this.className;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFullName() {
        if (null == this.parent) {
            return this.name;
        }
        return this.parent.getFullName() + "." + this.name;
    }

    public SortedMap<String, FunctionDocumentation> getFunctions() {
        return this.functionNameToFunction;
    }

    public String getName() {
        return this.name;
    }

    public FunctionCategoryDocumentation getParent() {
        return this.parent;
    }

    public SortedMap<String, FunctionCategoryDocumentation> getSubCategories() {
        return this.subCategoryNameTosubCategory;
    }


    public void setClassName(String className) {
        this.className = className;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
