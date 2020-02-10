package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.container.standalone.BEMain;
import com.tibco.cep.studio.cli.StudioCLIPlugin;
import com.tibco.cep.studio.core.StudioCore;

/*
@author ssailapp
@date Aug 21, 2009 5:05:26 PM
 */

public class StudioCommandLineInterpreter {
	
	static BEUrlClassLoader sysloader;

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    public static final Integer CLI_SUCCESS = new Integer(0);
    public static final Integer CLI_ERROR = new Integer(-1);
    
	private static List<ICommandLineInterpreter> interpreters;
	private static Map<String, String> argsMap =  null;
	
	static {
		interpreters = new ArrayList<ICommandLineInterpreter>();
		initializeInterpreters();
	}
	
	public static int main(String[] args, Map<String, String> argMap) throws Exception {
		argsMap = argMap;
		return executeCommandLine(args);
	}
	
//	public static int main(String[] args) throws Exception {
//		return executeCommandLine(args);
//	}
	
	public static int executeCommandLine(String[] args) throws Exception {
		processClassPath();
		//setXmlParserEnv();	//This is done in the Wrapper tra
//		ArgumentParser argParser = new ArgumentParser();
//		boolean success = argParser.parseArguments(args);
//		if (!success) {
//			printPrologue(args);
//			printUsage();
//			return CLI_ERROR;
//		}
//
//		Map<String, String> argsMap = argParser.getArgsMap();
		/*String op = argParser.getHelpOperation();
				
		if (op == null || op.trim().endsWith("help")) {
			printUsage();
			return (-1);
		}*/
		if (argsMap.size() == 0) {
			printPrologue(args);
			printUsage();
			return CLI_ERROR;
		}
		
		processPathVariables(argsMap);
		
		boolean opExecuted = false;
		for (ICommandLineInterpreter interpreter : interpreters) {
			try {
				if (!interpreter.checkIfExcludeOperation(argsMap)) {
					opExecuted = true;
					if(!(interpreter instanceof EncryptCLI)){
						printPrologue(args);
					}
					StudioCLIPlugin.log("Running: studio-tools " + args);
					boolean opStatus = interpreter.runOperation(argsMap);
					if (!opStatus) {
						if(interpreter instanceof EncryptCLI){
							printPrologue(args);
						}
						String helpMessage = interpreter.getHelp();
						if (helpMessage.isEmpty()) {
							printUsage();
						} else { 
							System.out.println(helpMessage);
						}
						
						StudioCLIPlugin.logErrorMessage("Error Running : studio-tools " + args);
						return CLI_ERROR;
					}
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				System.out.println("\n" + interpreter.getHelp());
				throw new Exception(e);
				//return (-1);
			} 
		}
		if (!opExecuted) {
			printPrologue(args);
			printUsage();
			StudioCLIPlugin.logErrorMessage("Error Running : studio-tools " + args);
			return CLI_ERROR;
		}
		StudioCLIPlugin.logErrorMessage("Successfully Completed : studio-tools " + args);
		return CLI_SUCCESS;
	}
	


	private static void initializeInterpreters() {
		
//		interpreters.add(new ImportDesignerCLI());
//		interpreters.add(new BuildEarCLI());
		
		try {
			IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor("com.tibco.cep.studio.cli");
			for (IConfigurationElement configurationElement : config) {
				final Object cliClazz = configurationElement.createExecutableExtension("class");
				if (cliClazz instanceof ICommandLineInterpreter) {
					interpreters.add((ICommandLineInterpreter)cliClazz);
				}
			}
		} catch (CoreException ex) {
			ex.printStackTrace();
		}
		
		interpreters.add(new HelpCLI());
	}
	
	public static void printUsage() {
		String usageStr = 	"Usage: studio-tools <operation_category> <operation> [-options] \n" + 
							"\n" +
							"where options for the various operations are as follows:\n"; 
		
		char ch = 'a';
		for (ICommandLineInterpreter interpreter : interpreters) {
				usageStr += "\t" + ch + ") " + interpreter.getUsage() + "\n\n";
				ch = (char)((int)ch+1);
		}
		usageStr += "\n";
		System.out.println(usageStr);
	}
	
	private static void processClassPath() {
		BEProperties.getInstance().addNewProperties(System.getProperties());
		BEProperties tibrenv = BEProperties.getInstance();
		for(Map.Entry<Object, Object> entry : tibrenv.entrySet()){
				StudioCore.setClasspathVariable(entry.getKey().toString(), new Path(entry.getValue().toString()), null);
		}
		String classpath = System.getProperty("java.class.path");
		
		String classpathEntries[] = classpath.split(File.pathSeparator);
		for (String classpathEntry: classpathEntries) {
			if (classpathEntry.indexOf("*") != -1) {
				String elements[] = classpathEntry.split("/");
				String parent = "";
				for (String element: elements) {
					if (element.indexOf("*") == -1) {
						parent += element + "/";
					} else {
						File dir = new File(parent);
						FilenameFilter jarFileFilter = new FilenameFilter(){
							@Override
							public boolean accept(File dir, String name) {
								return (name.endsWith(".jar"));
							}
						};
						String files[] = dir.list(jarFileFilter);
						if (files == null) {
							continue;
						}
						for (String file: files) {
							addURL(parent + file);
						}
					}
				}
			} else {
				// Don't do anything, as this is already in the classpath
				//Had to do this
				addURL(classpathEntry);
			}
		}
	}
	
	public static void addURL(String file) {
		
		URL url = null;
		
		try {
			url = new File(file).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(sysloader == null) {
			sysloader = new BEUrlClassLoader(new URL[] {url}, ClassLoader.getSystemClassLoader());
		}else {
			Class[] parameters = new Class[]{URL.class}; 
			Class<BEUrlClassLoader> sysclass = BEUrlClassLoader.class;
			  
			  try { 
				  Method method = sysclass.getDeclaredMethod("addURL", parameters);
				  method.setAccessible(true); 
				  URL u = new File(file).toURI().toURL();
				  method.invoke(sysloader,new Object[]{ u }); 
			  } catch (Throwable t) {
				  t.printStackTrace(); 
			  }
		}
	}
	
	public static void addURL_(String file) {
		@SuppressWarnings("rawtypes")
		Class[] parameters = new Class[]{URL.class};
		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> sysclass = URLClassLoader.class;
	 
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			URL u = new File(file).toURI().toURL();
			method.invoke(sysloader,new Object[]{ u });
		} catch (Throwable t) {
			t.printStackTrace();
			//throw new IOException("Error, could not add URL to system classloader");
		}
	}
	
	public static void printPrologue(String args[]) {
		String prologue = BEMain.printPrologue(args);
	    System.out.println(prologue);
	}
	
	private static void processPathVariables(Map<String, String> argsMap){
		Pattern varPattern = Pattern.compile("-D.*");
		Set<String> options = argsMap.keySet();
		Iterator<String> itr = options.iterator();
		while(itr.hasNext()){
			String var = itr.next();
			Matcher matcher = varPattern.matcher(var);
			if(matcher.matches()){
				if(var.length()>2){
					String pair = var.substring(2, var.length());
					int index = pair.indexOf("=");
					if(index>0){
						String variable = pair.substring(0, index);
						String value = pair.substring(index+1, pair.length());
						System.setProperty(variable, value);
					}
				}
			}
		}
		
	}
}
