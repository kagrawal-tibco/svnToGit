package com.tibco.cep.studio.core.grammar.java;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;

import com.tibco.cep.studio.core.utils.ModelUtils;

public class JavaPackageParserTester {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No file specified.  Exiting");
			return;
		}
		try {
			System.out.println("Attempting parse of "+args[0]);
			JavaPackageLexer lexer = new JavaPackageLexer(new ANTLRFileStream(args[0], ModelUtils.DEFAULT_ENCODING));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			List tokensList = tokens.getTokens();
			for (Object object : tokensList) {
				Token token = (Token) object;
				if (token.getType() == JavaPackageLexer.PackageStatement) {
					String packageText = token.getText();
					packageText = packageText.substring("package".length()).trim(); // trim 'package' at start
					packageText = packageText.substring(0, packageText.length()-1); // trim semicolon
					System.out.println("package is "+packageText);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
