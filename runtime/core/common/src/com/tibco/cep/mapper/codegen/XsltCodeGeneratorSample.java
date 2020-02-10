package com.tibco.cep.mapper.codegen;


public class XsltCodeGeneratorSample {

	private class SymbolTable implements IVariableTypeResolver {

		private String[] vars;
		private VariableType[] types;

		public SymbolTable(String[] vars, VariableType[] types) {
			this.vars = vars;
			this.types = types;
		}

		@Override
		public VariableType getDeclaredIdentifierType(String id) {
			for (int i=0; i<vars.length; i++) {
				if (vars[i].equals(id)) {
					return types[i];
				}
			}
			return null;
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String projectPath = "C:\\temp\\XPath";
//		IndexBuilder builder = new IndexBuilder(new File(projectPath));
//		DesignerProject index = builder.loadProject();
//		StudioProjectCache.getInstance().putIndex("XPath", index);
//		CoreOntologyAdapter ontology = new CoreOntologyAdapter(index);
//		String[] vars = { "limit", "var2" };
//		VariableType[] types = { new VariableType("", false, false), new VariableType("", false, false)};
//		SymbolTable symbolTable = new XsltCodeGeneratorSample().new SymbolTable(vars, types);
//		CommonXsltCodeGenerator generator = new CommonXsltCodeGenerator(ontology, symbolTable);
//		
//		StringBuilder buffer = new StringBuilder();
//		String xsltString = "xslt://{{/Concepts/Address}}<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsl xsd\">\n    <xsl:output method=\"xml\"/>\n    <xsl:param name=\"limit\"/>\n    <xsl:template match=\"/\">\n        <createObject>\n            <object>\n                <xsl:choose>\n                    <xsl:when test=\"$limit >3\">\n                        <zipCode>\n                            <xsl:value-of select=\"12345\"/>\n                        </zipCode>\n                    </xsl:when>\n                    <xsl:when test=\"$limit >2\">\n                        <zipCode>\n                            <xsl:value-of select=\"23456\"/>\n                        </zipCode>\n                    </xsl:when>\n                    <xsl:when test=\"$limit>1\">\n                        <zipCode>\n                            <xsl:value-of select=\"34567\"/>\n                        </zipCode>\n                    </xsl:when>\n                    <xsl:otherwise>\n                        <zipCode>\n                            <xsl:value-of select=\"99999\"/>\n                        </zipCode>\n                    </xsl:otherwise>\n                </xsl:choose>\n            </object>\n        </createObject>\n    </xsl:template>\n</xsl:stylesheet>";
//		boolean isCreate = true;
//		generator.processXsltString(xsltString, isCreate, buffer);
//		System.out.println("*************Generated code for create:*************");
//		System.out.println(buffer.toString());
//		System.out.println("*************End generated code for create*************");
//		
//		buffer = new StringBuilder();
//		isCreate = false;
//		generator.processXsltString(xsltString, isCreate, buffer);
//		System.out.println("\n\n*************Generated code (method body only):*************");
//		System.out.println(buffer.toString());
//		System.out.println("*************End generated code (method body only)*************");
//		
//		String forEachString = "xslt://{{/Concepts/Address}}<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xsl:stylesheet xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\" exclude-result-prefixes=\"xsl xsd\">\n    <xsl:output method=\"xml\"/>\n    <xsl:param name=\"addy2\"/>\n    <xsl:template match=\"/\">\n        <createObject>\n            <object>\n                <xsl:for-each select=\"$addy2/addressType\">\n                    <zipCode>\n                        <xsl:value-of select=\".\"/>\n                    </zipCode>\n                </xsl:for-each>\n            </object>\n        </createObject>\n    </xsl:template>\n</xsl:stylesheet>";
//		isCreate = false;
//		// Will throw an UnsupportedXsltMappingException
//		generator.processXsltString(forEachString, isCreate, buffer);
//		System.out.println("\n\n*************Generated code (for each):*************");
//		System.out.println(buffer.toString());
//		System.out.println("*************End generated code (for each)*************");
	}

}
