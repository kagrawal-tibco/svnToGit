Custom Repository integration

WebStudio/RMS server out of box supports 2 repositories, File and SVN. For another other repository support, the end user will have to implement a set of methods specific to the target repository. Below are the steps involved.

1. Need to create a java class for the custom repository integration and extend the base repository class namely 'AbstractRepositoryIntegration' implementing all its abstract methods. This class is available under 'cep-rms.jar' located under BE_HOME/rms/lib. Add this jar to the classpath of the custom integration class for resolving dependencies.

2. All the abstract methods have detailed javadocs attached, explaining what each method should do, what every parameters means, expected return types etc. Refer sample 'svn' implementation under '/svn/src'.

3. The internal custom implementation can vary, so long as it honors the contract of each of these methods. The sample 'svn' implementation uses command line execution for interacting with the SVN repository. Same strategy can be applied to other repositories as well, like CVS/Perforce/TSF/etc. All that would be needed is to install their command line executables.

4. Once the implementation is complete. Compile the source using JDK and bundle the class/s into a jar file. 

4. Copy the JAR file to BE_HOME/rms/lib.

5. Edit the RMS.cdd located under BE_HOME/rms/bin, to point to the custom implementation. Set the property 'ws.scs.impl.type' to the fully qualified path of the custom implementation class. E.g. for above svn sample it would be com.tibco.be.ws.scs.impl.repo.svn.SVNIntegration

6. Additionally if the implementation is similar to 'svn' sample i.e. using command line, edit RMS.cdd to point to the installed executable. Set the property 'ws.scs.command.path' to the absolute path of the executable.


