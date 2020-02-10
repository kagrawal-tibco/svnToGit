import java.util.LinkedHashMap;

public class FunctionDocumentation {

    protected LinkedHashMap<String, FunctionParameterDocumentation> argumentNameToArgument;
    protected String cautions;
    protected String description;
    protected String domain;
    protected String example;
    protected String mapper;
    protected String name;
    protected FunctionParameterDocumentation returnParameter;
    protected String see;
    protected String signature;
    protected String synopsis;
    protected String version;

    public FunctionDocumentation(
            String name,
            FunctionParameterDocumentation returnParameter,
            String signature,
            String synopsis,
            String description,
            String domain,
            String mapper,
            String see,
            String cautions,
            String example,
            String version) {
        this.argumentNameToArgument = new LinkedHashMap<String, FunctionParameterDocumentation>();
        this.cautions = cautions;
        this.description = description;
        this.domain = domain;
        this.example = example;
        this.mapper = mapper;
        this.name = name;
        this.returnParameter = returnParameter;
        this.see = see;
        this.signature = signature;
        this.synopsis = synopsis;
        this.version = version;
    }

    public LinkedHashMap<String, FunctionParameterDocumentation> getArguments() {
        return this.argumentNameToArgument;
    }

    public String getCautions() {
        return this.cautions;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getExample() {
        return this.example;
    }

    public String getMapper() {
        return this.mapper;
    }

    public String getName() {
        return this.name;
    }

    public FunctionParameterDocumentation getReturn() {
        return this.returnParameter;
    }

    public String getSee() {
        return this.see;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public String getVersion() {
        return this.version;
    }
}
