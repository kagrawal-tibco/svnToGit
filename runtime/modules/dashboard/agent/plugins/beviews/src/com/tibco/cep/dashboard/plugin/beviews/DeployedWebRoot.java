package com.tibco.cep.dashboard.plugin.beviews;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;

public class DeployedWebRoot {

	private Logger logger;

	private Properties properties;

	private ExceptionHandler exceptionHandler;

	@SuppressWarnings("unused")
	private MessageGenerator messageGenerator;

	private DeployedProject project;

	private String configuredDocRootLocation;

	private Path actualWebRoot;

	DeployedWebRoot(Properties properties, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, DeployedProject project) throws IOException {
		this.properties = properties;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
		this.logger = logger;
		this.project = project;
		configuredDocRootLocation = (String) ConfigurationProperties.PROP_DOC_ROOT.getValue(this.properties);
		File configuredDocRoot = new File(configuredDocRootLocation);
		if (configuredDocRoot.exists() == false){
			throw new RuntimeException(configuredDocRootLocation+" does not exist");
		}
		if (configuredDocRoot.isDirectory() == false){
			throw new RuntimeException(configuredDocRootLocation+" is not a valid document root");
		}
		// get the project name , we will use it as the prefix for temporary web root folder name
		String tempDocRootFolderName = this.project.getName() + "_web_root_"+System.currentTimeMillis();
		this.logger.log(Level.DEBUG, "Creating temporary web root folder %s", tempDocRootFolderName);

		actualWebRoot = FileUtils.createTempFolder(tempDocRootFolderName).toPath();
		this.logger.log(Level.DEBUG, "Created temporary web root folder %s", actualWebRoot.toString());
		// start the content copying
		copyContents(configuredDocRoot.toPath(), actualWebRoot);
	}

	private void copyContents(final Path srcPath, final Path destPath) throws IOException {
		logger.log(Level.TRACE, "Copying contents of %s to %s", srcPath.toString(), destPath.toString());
		Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				if (dir.getFileName().toString().startsWith(".") == true) {
					return FileVisitResult.SKIP_SUBTREE;
				}
				Path destDir = destPath.resolve(srcPath.relativize(dir));
				if (Files.exists(destDir) == false) {
					logger.log(Level.TRACE, "Creating %s", destDir.toString());
					Files.createDirectory(destDir);
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Path destFilePath = destPath.resolve(srcPath.relativize(file));
				logger.log(Level.TRACE, "Copying %s to %s", file.toString(), destFilePath.toString());
				Files.copy(file, destFilePath);
				return FileVisitResult.CONTINUE;
			}

		});
	}

	public String getLocation(){
		return actualWebRoot.toAbsolutePath().toString();
	}

	public File createFile(String relativePath, byte[] contents) throws IOException {
		Path path = actualWebRoot.resolve(relativePath);
		if (Files.exists(path.getParent()) == false) {
			Files.createDirectories(path.getParent());
		}
		Files.write(path, contents);
		return path.toFile();
	}

	public boolean delete(){
		try {
			Files.walkFileTree(actualWebRoot, new SimpleFileVisitor<Path>(){

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					logger.log(Level.TRACE, "Deleting %s...", file.toString());
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					logger.log(Level.TRACE, "Deleting %s...", dir.toString());
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			exceptionHandler.handleException(String.format("could not delete %s", actualWebRoot.toString()), e, Level.WARN);
			return false;
		}
		return true;
	}

}