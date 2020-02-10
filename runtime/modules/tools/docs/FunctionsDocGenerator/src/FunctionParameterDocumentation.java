public class FunctionParameterDocumentation {

    protected String name;
    protected String type;
    protected String description;

    public FunctionParameterDocumentation(
            String name,
            String type,
            String description) {
        this.description = description;
        this.name = name;
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}
