#!/usr/bin/env python

"""applicationsMgmt.py: CLI for Applications Management BE-TEA Operations."""

import sys
import io
import os.path
import datetime
import argparse
import tibco.tea
from utils import *

# Create the command-line ArgumentParser
def createCommandParser():
	#create the top-level parser
	commandParser = argparse.ArgumentParser(add_help = False, description = 'Applications Management Operations CLI.')
	commandParser.print_help()
	commandParser.add_argument('-ssl', required = False, default = False, dest = 'sslEnabled', help = 'SSL Enabled')
	args = commandParser.parse_known_args()[0]
	commandParser.add_argument('-t', required = False, default = getDefaultTEAServerURL(args.sslEnabled), dest = 'serverURL', help = 'TEA Server URL')
	commandParser.add_argument('-u', required = True, dest = 'userName', help = 'TEA User Name')
	commandParser.add_argument('-p', required = True, dest = 'userPwd', help = 'TEA User Password')
	commandParser.add_argument('-sc', required = False, default = '', dest = 'serverCert', help = 'Server certificate Path')
	commandParser.add_argument('-cc', required = False, default = '', dest = 'clientCert', help = 'Client certificate Path')
	commandParser.add_argument('-gt', required = False, default = 30, dest = 'gTimeout', help = 'Global timeout')
	subparsers = commandParser.add_subparsers(dest='operationName')
	
	subparsersList = []
	for operation in operationsList:
		if (operation == 'addmachine'):
			addMachineParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			addMachineParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			addMachineParser.add_argument('-i', required = True, dest = 'ipAddress', help = 'Ip Address')
			addMachineParser.add_argument('-o', required = True, dest = 'OS', help = 'OS', choices=['windows', 'unix' , 'os-x'])
			addMachineParser.add_argument('-b', required = True, dest = 'beHome', help = 'BE Home')
			addMachineParser.add_argument('-t', required = True, dest = 'beTra', help = 'BE Home TRA file')
			addMachineParser.add_argument('-v', required = False, dest = 'beVersion', help = 'BE Home Version')
			addMachineParser.add_argument('-u', required = True, dest = 'user', help = 'Machine User')
			addMachineParser.add_argument('-p', required = True, dest = 'pwd', help = 'Machine User password')
			addMachineParser.add_argument('-s', required = True, dest = 'sshPort', help = 'SSH port')
			addMachineParser.add_argument('-f', required = True, dest = 'deploymentPath', help = 'Default Deployment Path')
			addMachineParser.add_argument('-abh', required = False, dest = 'addBeHome', help = 'Add additional Be Home')
			addMachineParser.set_defaults(func = addMachine)
			subparsersList.append(addMachineParser)
			
		elif (operation == 'docker_addmachine'):
			dockerAddMachineParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			dockerAddMachineParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			dockerAddMachineParser.add_argument('-i', required = True, dest = 'ipAddress', help = 'Ip Address')
			dockerAddMachineParser.add_argument('-o', required = False, dest = 'OS', help = 'OS', choices=['windows', 'unix' , 'os-x'])
			dockerAddMachineParser.add_argument('-u', required = False, dest = 'user', help = 'Machine User')
			dockerAddMachineParser.add_argument('-p', required = False, dest = 'pwd', help = 'Machine User password')
			dockerAddMachineParser.add_argument('-s', required = False, dest = 'sshPort', help = 'SSH port')
			dockerAddMachineParser.set_defaults(func = docker_addMachine)
			subparsersList.append(dockerAddMachineParser)
		elif (operation == 'createdeployment'):
			createDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			createDeploymentParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			createDeploymentParser.add_argument('-c', required = True, dest = 'cddFile', help = 'CDD file')
			createDeploymentParser.add_argument('-e', required = True, dest = 'earFile', help = 'EAR file')
			createDeploymentParser.set_defaults(func = createDeployment)
			subparsersList.append(createDeploymentParser)
		elif (operation == 'docker_createdeployment'):
			dockerCreateDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			dockerCreateDeploymentParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			dockerCreateDeploymentParser.set_defaults(func = docker_createDeployment)
			subparsersList.append(dockerCreateDeploymentParser)
		elif (operation == 'importdeployment'):
			importDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			importDeploymentParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			importDeploymentParser.add_argument('-c', required = True, dest = 'cddFile', help = 'CDD file')
			importDeploymentParser.add_argument('-e', required = True, dest = 'earFile', help = 'EAR file')
			importDeploymentParser.add_argument('-s', required = True, dest = 'stFile', help = 'ST file')
			importDeploymentParser.set_defaults(func = importDeployment)
			subparsersList.append(importDeploymentParser)
		elif (operation == 'deletedeployment'):
			deleteDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			deleteDeploymentParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			deleteDeploymentParser.set_defaults(func = deleteDeployment)
			subparsersList.append(deleteDeploymentParser)
		elif (operation == 'createinstance'):
			createInstanceParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			createInstanceParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			createInstanceParser.add_argument('-i', required = True, dest = 'instanceName', help = 'Instance name')
			createInstanceParser.add_argument('-u', required = True, dest = 'pu', help = 'Processing Unit')
			createInstanceParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			createInstanceParser.add_argument('-p', required = True, dest = 'jmxPort', help = 'JMX Port')
			createInstanceParser.add_argument('-f', required = False, dest = 'deploymentPath', help = 'Deployment Path')
			createInstanceParser.add_argument('-ju', required = False, dest = 'jmxuser', help = 'JMX User Name')
			createInstanceParser.add_argument('-jp', required = False, dest = 'jmxpass', help = 'JMX Password')
			createInstanceParser.add_argument('-bh', required = False, dest = 'beHome', help = 'BE Home')
			createInstanceParser.set_defaults(func = createInstance)
			subparsersList.append(createInstanceParser)
		elif (operation == 'docker_createinstance'):
			dockerCreateInstanceParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			dockerCreateInstanceParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			dockerCreateInstanceParser.add_argument('-i', required = True, dest = 'instanceName', help = 'Instance name')
			dockerCreateInstanceParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			dockerCreateInstanceParser.add_argument('-p', required = True, dest = 'jmxPort', help = 'JMX Port')
			dockerCreateInstanceParser.add_argument('-ju', required = False, dest = 'jmxuser', help = 'JMX User Name')
			dockerCreateInstanceParser.add_argument('-jp', required = False, dest = 'jmxpass', help = 'JMX Password')
			dockerCreateInstanceParser.add_argument('-u', required = False, dest = 'pu', help = 'Processing Unit')
			dockerCreateInstanceParser.set_defaults(func = docker_createInstance)
			subparsersList.append(dockerCreateInstanceParser)
		elif (operation == 'copyinstance'):
			copyInstanceParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			copyInstanceParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			copyInstanceParser.add_argument('-i', required = True, dest = 'instanceName', help = 'Instance to copy')
			copyInstanceParser.add_argument('-n', required = True, dest = 'newInstanceName', help = 'New Instance name')
			copyInstanceParser.add_argument('-u', required = True, dest = 'pu', help = 'Processing Unit')
			copyInstanceParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			copyInstanceParser.add_argument('-p', required = True, dest = 'jmxPort', help = 'JMX Port')
			copyInstanceParser.add_argument('-f', required = True, dest = 'deploymentPath', help = 'Deployment Path')
			copyInstanceParser.add_argument('-ju', required = False, dest = 'jmxuser', help = 'JMX User Name')
			copyInstanceParser.add_argument('-jp', required = False, dest = 'jmxpass', help = 'JMX Password')
			copyInstanceParser.add_argument('-bh', required = False, dest = 'beHome', help = 'BE Home')
			copyInstanceParser.set_defaults(func = copyInstance)
			subparsersList.append(copyInstanceParser)
		elif (operation == 'deleteinstance'):
			deleteInstanceParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			deleteInstanceParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			deleteInstanceParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of instance names')
			deleteInstanceParser.set_defaults(func = deleteInstance)
			subparsersList.append(deleteInstanceParser)
		elif operation in ['deploy', 'undeploy', 'start', 'stop']:
			mgmtOprParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			mgmtOprParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			group = mgmtOprParser.add_mutually_exclusive_group()
			group.add_argument('-m', required = False, dest = 'machine', help = 'Machine name')
			group.add_argument('-u', required = False, dest = 'pu', help = 'Processing Unit')
			group.add_argument('-a', required = False, dest = 'agentClass', help = 'BE Agent class')
			mgmtOprParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of instance names')
			mgmtOprParser.set_defaults(func = invokeMgmtOperation)
			subparsersList.append(mgmtOprParser)
		elif (operation == 'hotdeploy'):
			#hot-deploy argument parser
			hotdeployParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			hotdeployParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			hotdeployParser.add_argument('-e', required = True, dest = 'earFile', help = 'Deployment EAR file')
			hotdeployParser.set_defaults(func = hotdeploy)
			subparsersList.append(hotdeployParser)
		elif (operation == 'downloadlogs'):
			downloadLogsParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			downloadLogsParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			downloadLogsParser.add_argument('-l', required = True, dest = 'downloadLocation', help = 'Download location path')
			downloadLogsParser.add_argument('-lt', required = True, dest = 'logType', help = 'Be/AS/ThreadDump logs', choices=['be', 'as' , 'td'])
			downloadLogsParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of instance names')
			downloadLogsParser.set_defaults(func = downloadLogs)
			subparsersList.append(downloadLogsParser)
		elif (operation == 'exportteadeployment'):
			exportTeaDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			exportTeaDeploymentParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			exportTeaDeploymentParser.add_argument('-l', required = True, dest = 'downloadLocation', help = 'Download location path')
			exportTeaDeploymentParser.set_defaults(func = exportTeaDeployment)
			subparsersList.append(exportTeaDeploymentParser)
		elif (operation == 'importteadeployment'):
			importTeaDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			importTeaDeploymentParser.add_argument('-z', required = True, dest = 'zipFile', help = 'Exported zip file')
			importTeaDeploymentParser.set_defaults(func = importTeaDeployment)
			subparsersList.append(importTeaDeploymentParser)
		elif (operation == 'hotdeploydtrt'):
			hotDeploydtrtParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			hotDeploydtrtParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			hotDeploydtrtParser.add_argument('-i', required = True, dest = 'instanceName', help = 'Instance name')
			hotDeploydtrtParser.add_argument('-o', required = True, dest = 'deployType', help = 'dt/rt', choices=['dt', 'rt'])
			hotDeploydtrtParser.add_argument('-z', required = True, dest = 'zipFile', help = 'Zip/Jar file')
			hotDeploydtrtParser.set_defaults(func = hotDeployDtRt)
			subparsersList.append(hotDeploydtrtParser)
		elif (operation == 'checkstatus'):
			checkStatusParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			group = checkStatusParser.add_mutually_exclusive_group(required=True)
			group.add_argument('-d', dest = 'applicationName', nargs='?', default='',const=' ', help = 'Application deployment name')
			group.add_argument('-m', dest = 'machineName', nargs='?', default='', const=' ', help = 'Machine name' )
			checkStatusParser.add_argument('-i', required = False, dest = 'instanceName', help = 'Instance name')
			checkStatusParser.add_argument('-an', required = False, dest = 'agentName', help = 'Agent name')
			checkStatusParser.add_argument('-da', required = False, dest = 'displayAll', action='store_true', help = 'Display all (no argument required)')
			checkStatusParser.set_defaults(func = checkStatus)
			subparsersList.append(checkStatusParser)

	return commandParser, subparsersList

def hotDeployDtRt (args):
	
	zipFileHandle = None
	
	try:
		print('Executing operation - hot deploy DT/RT')
		
		if (os.path.isfile(args.zipFile)):
			if (not os.access(args.zipFile, os.R_OK)):
				print('Cannot read Zip/Jar file ' + args.zipFile)
				exit()
		else:
			print('Zip/Jar file ' + args.zipFile + ' not found')
			exit()
		zipFileHandle = io.open(args.zipFile, 'rb')
		
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		
		serviceInstance = getServiceInstance(beApplication, args.instanceName)
		if (not serviceInstance):
			print('Instance ' + args.instanceName + ' not found for ' + ' ' + beApplication.name)
			
		if(args.deployType == 'rt'):
			result = serviceInstance.uploadRuleTemplates(zipFileHandle)
		else:
			result = serviceInstance.uploadClasses(zipFileHandle)
			
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (zipFileHandle):
			zipFileHandle.close()
	

def importTeaDeployment (args):
	
	zipFileHandle = None
	try:
		print('Executing operation - importteadeployment')
		if (os.path.isfile(args.zipFile)):
			if (not os.access(args.zipFile, os.R_OK)):
				print('Cannot read ZIP file ' + args.zipFile)
				exit()
		else:
			print('ZIP file ' + args.zipFile + ' not found')
			exit()
					
		zipFileHandle = io.open(args.zipFile, 'rb')
		result = beProduct.upload('', '', '', '', zipFileHandle, False, True)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (zipFileHandle):
			zipFileHandle.close()

def exportTeaDeployment (args):
	outFile = None
	inputfile = None
	try:
		print('Executing operation - exportteadeployment')
		
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
			
		logLocation = args.downloadLocation
		if(not os.path.exists(logLocation)):
			print("Not a valid path " + logLocation)
			exit()
			
		today = datetime.datetime.today().strftime('%Y%m%d%H%M%S')
		fileName = args.applicationName + "_" + today
		logLocation = logLocation + "/" + fileName + "_Export.zip"
		
		logFileHandle = beApplication.export()
		
		outFile = io.open(logLocation, 'wb')
		inputfile = io.open(logFileHandle, 'rb')
		outFile.write(inputfile.read())
		print("Success")
		
		
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (outFile):
			outFile.close()
		if (inputfile):
			inputfile.close()


def downloadLogs (args):
	outFile = None
	inputfile = None
	try:
		print('Executing operation - downloadLogs')
		
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
			
		logLocation = args.downloadLocation
		if(not os.path.exists(logLocation)):
			print("Not a valid path " + logLocation)
			exit()
			
		today = datetime.datetime.today().strftime('%Y%m%d%H%M%S')
		fileName = args.applicationName + "_" + today
		logLocation = logLocation + "/" + fileName
		
		instanceKeys = []
		for instanceName in args.instances:
			serviceInstance = getServiceInstance(beApplication, instanceName)
			if (serviceInstance):
				instanceKeys.append(serviceInstance.key)
			else:
				print('Instance ' + instanceName + ' not found for ' + ' ' + beApplication.name)
		
		if(args.logType == 'as'):	
			logFileHandle = beApplication.downloadASLog(instanceKeys)
			logLocation = logLocation  + "_logs.zip"
		elif(args.logType == 'be'):
			logFileHandle = beApplication.downloadLog(instanceKeys)
			logLocation = logLocation  + "_logs.zip"
		else:
			logFileHandle = beApplication.groupThreadDump(instanceKeys)
			logLocation = logLocation + "_Thread_Dump.zip"
		
		outFile = io.open(logLocation, 'wb')
		inputfile = io.open(logFileHandle, 'rb')
		outFile.write(inputfile.read())
		print("Success")
		
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (outFile):
			outFile.close()
		if (inputfile):
			inputfile.close()
##########################################################################################################################################
# Application Management operations
##########################################################################################################################################
# Add Machine operation
def addMachine (args):
	try:
		print('Executing operation - addmachine')
		OS = args.OS
		if(OS == "windows"):
			OS = "Windows Based"
		else:
			OS = "OS/X,Unix/Linux Based"
		result = beProduct.addMasterHostCli(args.machineName, args.ipAddress, OS, args.beHome, args.beTra, args.user, args.pwd, args.sshPort, args.deploymentPath, args.beVersion, args.addBeHome)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Add docker machine(container) operation
def docker_addMachine (args):
	try:
		print('Executing operation - docker_addMachine')
		masterHost = getMasterHost(beProduct, args.machineName)
		if (masterHost):
			print('MasterHost ' + args.machineName + ' is already exist')
			exit()
		OS = args.OS
		if(OS == "windows"):
			OS = "Windows Based"
		else:
			OS = "OS/X,Unix/Linux Based"
		result = beProduct.addMasterHostCli(args.machineName, args.ipAddress,OS,'','',args.user, args.pwd, args.sshPort,'','')
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Create Deployment operation
def createDeployment(args):
	cddFileHandle = None
	earFileHandle = None
	try:
		print('Executing operation - createdeployment')
		if (os.path.isfile(args.cddFile)):
			if (not os.access(args.cddFile, os.R_OK)):
				print('Cannot read CDD file ' + args.cddFile)
				exit()
		else:
			print('CDD file ' + args.cddFile + ' not found')
			exit()
		if (os.path.isfile(args.earFile)):
			if (not os.access(args.earFile, os.R_OK)):
				print('Cannot read EAR file ' + args.earFile)
				exit()
		else:
			print('EAR file ' + args.earFile + ' not found')
			exit()
					
		cddFileHandle = io.open(args.cddFile, 'rb')
		earFileHandle = io.open(args.earFile, 'rb')
		result = beProduct.upload(args.applicationName, '', cddFileHandle, earFileHandle, '', True, False)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (cddFileHandle):
			cddFileHandle.close()
		if (earFileHandle):
			earFileHandle.close()
# Create Deployment operation
def docker_createDeployment(args):	
	try:
		print('Executing operation - docker_createDeployment')	
		beApplication = getApplication(beProduct, args.applicationName)	
		if (beApplication):
			print('Application ' + args.applicationName + ' is already exist')
			exit()
			
		result = beProduct.upload(args.applicationName, '', '', '', '', True, False)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)	

# Import Deployment operation
def importDeployment(args):
	cddFileHandle = None
	earFileHandle = None
	stFileHandle = None
	try:
		print('Executing operation - importdeployment')
		if (os.path.isfile(args.cddFile)):
			if (not os.access(args.cddFile, os.R_OK)):
				print('Cannot read CDD file ' + args.cddFile)
				exit()
		else:
			print('CDD file ' + args.cddFile + ' not found')
			exit()
		if (os.path.isfile(args.earFile)):
			if (not os.access(args.earFile, os.R_OK)):
				print('Cannot read EAR file ' + args.earFile)
				exit()
		else:
			print('EAR file ' + args.earFile + ' not found')
			exit()
		if (os.path.isfile(args.stFile)):
			if (not os.access(args.stFile, os.R_OK)):
				print('Cannot read Site Topology file ' + args.stFile)
				exit()
		else:
			print('Site Topology file ' + args.stFile + ' not found')
			exit()
		cddFileHandle = io.open(args.cddFile, 'rb')
		earFileHandle = io.open(args.earFile, 'rb')
		stFileHandle = io.open(args.stFile, 'rb')
		result = beProduct.upload(args.applicationName, '', cddFileHandle, earFileHandle, stFileHandle, False, False)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (cddFileHandle):
			cddFileHandle.close()
		if (earFileHandle):
			earFileHandle.close()

# Delete Deployment operation
def deleteDeployment(args):
	try:
		print ('Executing operation - deletedeployment')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		result = beApplication.delete()
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Create Instance operation
def createInstance(args):
	try:
		print ('Executing operation - createinstance')
		beApplication = getApplication(beProduct, args.applicationName)	
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		masterHost = getMasterHost(beProduct, args.machineName)
		if (not masterHost):
			print('MasterHost ' + args.machineName + ' not found')
			exit()
		beId = masterHost.getBeId(args.beHome);
		if ((not args.beHome is None)  and (beId is None)):
			print('Invalid Be Home')
			exit()
		result = beApplication.createServiceInstance(args.instanceName, args.pu, args.machineName, args.jmxPort, args.deploymentPath, args.jmxuser, args.jmxpass, beId)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Create docker Instance operation
def docker_createInstance(args):
	try:
		print ('Executing operation - docker_createInstance')
		beApplication = getApplication(beProduct, args.applicationName)	
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		masterHost = getMasterHost(beProduct, args.machineName)
		if (not masterHost):
			print('MasterHost ' + args.machineName + ' not found')
			exit()
		beServiceInstance = getServiceInstance(beApplication, args.instanceName)
		if (beServiceInstance):
			print('Instance ' + args.instanceName + ' is already exist')
			exit()		
		result = beApplication.createServiceInstance(args.instanceName, '', args.machineName, args.jmxPort,'', args.jmxuser, args.jmxpass, '')
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Copy Instance operation
def copyInstance(args):
	try:
		print ('Executing operation - copyinstance')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		masterHost = getMasterHost(beProduct, args.machineName)
		if (not masterHost):
			print('MasterHost ' + args.machineName + ' not found')
			exit()
		beId = masterHost.getBeId(args.beHome);
		if ((not args.beHome is None)  and (beId is None)):
			print('Invalid Be Home')
			exit()
		beServiceInstance = getServiceInstance(beApplication, args.instanceName)
		if (not beServiceInstance):
			print('Instance ' + args.instanceName + ' not found')
			exit()
		result = beServiceInstance.copyInstance(args.newInstanceName, args.pu, args.machineName, args.jmxPort, args.deploymentPath, args.jmxuser, args.jmxpass, beId)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Delete Instance operation
def deleteInstance(args):
	try:
		print ('Executing operation - deleteinstance')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
			
		instanceKeys = []
		if (args.instances and len(args.instances) > 0):
			for instanceName in args.instances:
				serviceInstance = getServiceInstance(beApplication, instanceName)
				if (serviceInstance):
					instanceKeys.append(serviceInstance.key)
				else:
					print('Instance ' + instanceName + ' not found for ' + ' ' + beApplication.name)
			if(len(instanceKeys) == 0):
				raise Exception('No instance(s) found to delete')
			result = getattr(beApplication, 'groupDelete')(instanceKeys)	
			print(result)
		else:
			for serviceInstance in beApplication.ServiceInstances.values():
				instanceKeys.append(serviceInstance.key)
			if(len(instanceKeys) == 0):
				raise Exception('No instance(s) found to delete')
			result = getattr(beApplication, 'groupDelete')(instanceKeys)		
			print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# deploy/undeploy/start/stop instance(s) operations
def invokeMgmtOperation (args):
	try:
		teaObjectType = None
		print ('Executing operation - ' + args.operationName)
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			print('Exiting...')
			exit()
		if (args.machine):
			teaObjectType = 'Machine'
			beTeaObject = getApplicationHost(beApplication, args.machine)
		elif (args.pu):
			teaObjectType = 'PU'
			beTeaObject = getApplicationPU(beApplication, args.pu)
		elif (args.agentClass):
			teaObjectType = 'AgentClass'
			beTeaObject = getApplicationPuAgent(beApplication, args.agentClass)
		else:
			teaObjectType = 'Application'
			beTeaObject = beApplication
		
		instanceKeys = []
		if (args.instances and len(args.instances) > 0):
			for instanceName in args.instances:
				serviceInstance = getServiceInstance(beTeaObject, instanceName)
				if (serviceInstance):
					instanceKeys.append(serviceInstance.key)
				else:
					print('Instance ' + instanceName + ' not found for ' + teaObjectType + ' ' + beTeaObject.name)
			if(len(instanceKeys) == 0):
				raise Exception('No instance(s) found to ' + args.operationName)
			result = getattr(beTeaObject, args.operationName)(instanceKeys)	
			print(result)
		else:
			for serviceInstance in beTeaObject.ServiceInstances.values():
				instanceKeys.append(serviceInstance.key)
			result = getattr(beTeaObject, args.operationName)(instanceKeys)		
			print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

# Application Deployment hotdeploy operation
def hotdeploy (args):
	fileHandle = None
	try:
		print ('Executing operation - hotdeploy')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		if (os.path.isfile(args.earFile)):
			if (not os.access(args.earFile, os.R_OK)):
				print('Cannot read EAR file ' + args.earFile)
				exit()
		else:
			print('EAR file ' + args.earFile + ' not found')
			print('Exiting...')
			exit()
					
		fileHandle = io.open(args.earFile, 'rb')
		result = beApplication.hotdeployAll(fileHandle)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (fileHandle):
			fileHandle.close()
			
def checkStatus(args):	
	try:
		print('Executing operation - checkStatus')
		
		if (args.applicationName):
			if (args.displayAll):
				for beApplication in beProduct.Applications.values():
					if (not beApplication):
						print('Application not found')
						exit()
					else:
						print('Application[' + beApplication.name + '] Status[' + beApplication.status + ']')
					
					for serviceInstance in beApplication.ServiceInstances.values():
						print('    ' + 'Instance[' + serviceInstance.name + '] Status[' + serviceInstance.status + ']')
						for beAgent in serviceInstance.Agent.values():
							print('      ' + 'Agent[' + beAgent.name + '] Status[' + beAgent.status + ']')
			else:
				if (args.applicationName):
					beApplication = getApplication(beProduct, args.applicationName)
					if (not beApplication):
						print('Application not found')
						exit()
					if (args.instanceName):
						serviceInstance = getServiceInstance(beApplication, args.instanceName)
						if (not serviceInstance):
							print('Instance not found')
							exit()
						if(args.agentName):
							agentInstance = getAgentInstance(serviceInstance, args.agentName)
							if (not agentInstance):
								print('Agent not found')
								exit()
							print('Agent[' + args.agentName + '] Status[' + agentInstance.status + ']')
						else:
							print('Instance[' + args.instanceName + '] Status[' + serviceInstance.status + ']')	
					else:				
						print('Application[' + args.applicationName + '] Status[' + beApplication.status + ']')
			
						
		if (args.machineName):
			if (args.displayAll):
				for beMasterHost in beProduct.MasterHosts.values():
					print('Machine[' + beMasterHost.machineName + '] Status[' + beMasterHost.status + ']')
			else:
				beMasterHost = getMasterHost(beProduct, args.machineName)
				if (not beMasterHost):
					print('Machine not found')
					exit()
				else:
					print('Machine[' + args.machineName + '] Status[' + beMasterHost.status + ']')
				
	except Exception as ex:
		details = ex.args[0]
		print(details)	

##########################################################################################################################################
# Startup code
##########################################################################################################################################
operationsList = ['deploy', 'undeploy', 'start', 'stop', 'hotdeploy', 'addmachine', 'createdeployment', 'createinstance', 'copyinstance', 'deleteinstance', 'deletedeployment', 'downloadlogs', 'exportteadeployment', 'importteadeployment', 'hotdeploydtrt','docker_createdeployment','docker_addmachine','docker_createinstance', 'checkstatus']
# Create Command parser
commamdParser, subparsersList = createCommandParser()
if (len(sys.argv) == 1): # Print complete usage & exit
	printCompleteUsage(commamdParser, subparsersList)
	exit()
#Parse the command arguments
command = commamdParser.parse_args()
if (command.operationName): #Execute operation
	tea = connectToServer(command.serverURL, command.userName, command.userPwd, command.sslEnabled, command.serverCert, command.clientCert, command.gTimeout)
	beProduct = tea.products['BusinessEvents']
	if (beProduct.status == 'Running'): # BE TEA Agent is not running
		command.func(command)
	else:
		print('TEA Server returned - '+ beProduct.__name__ + ' TEA Agent not reachable')
		exit()
else: #Print operation usage
	commamdParser.print_usage()
	print('Exiting...')
	exit()
