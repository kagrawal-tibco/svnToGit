package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

public interface IDependencyCalculatorExtension extends IDependencyCalculator {

	public List<File> calculateDependencies(File projectDir, File resource);
	
}
