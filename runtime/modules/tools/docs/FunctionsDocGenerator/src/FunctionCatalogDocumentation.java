import java.util.SortedMap;
import java.util.TreeMap;


public class FunctionCatalogDocumentation {


    protected SortedMap<String,FunctionCategoryDocumentation> categories;
    protected String name;


    public FunctionCatalogDocumentation(String name) {
        this.categories = new TreeMap<String,FunctionCategoryDocumentation>();
        this.name = name;
    }


    public SortedMap<String, FunctionCategoryDocumentation> getCategories() {
        return this.categories;
    }


    public String getName() {
        return this.name;
    }


    public String getDescription() {
        return "Function catalog.";
    }

}
