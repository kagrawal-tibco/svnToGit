#!/usr/bin/env python

"""configurationMgmt.py: CLI for configuration Management BE-TEA Operations."""

import sys
import io
import zipfile
import os.path
import argparse
import tibco.tea
from utils import *

# Create the command-line ArgumentParser
def createCommandParser():
	#create the top-level parser
	commandParser = argparse.ArgumentParser(add_help = False, description = 'Configuration Management Operations CLI.')
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
		if (operation == 'editmachine'):
			editMachineParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			editMachineParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			editMachineParser.add_argument('-n', required = False, dest = 'newMachineName', help = 'New Machine name')
			editMachineParser.add_argument('-i', required = False, dest = 'ipAddress', help = 'Ip Address')
			editMachineParser.add_argument('-o', required = False, dest = 'OS', help = 'OS', choices=['windows', 'unix' , 'os-x'])
			editMachineParser.add_argument('-b', required = False, dest = 'beHome', help = 'BE Home')
			editMachineParser.add_argument('-t', required = False, dest = 'beTra', help = 'BE Home TRA file')
			editMachineParser.add_argument('-u', required = False, dest = 'user', help = 'Machine User')
			editMachineParser.add_argument('-p', required = False, dest = 'pwd', help = 'Machine User password')
			editMachineParser.add_argument('-s', required = False, dest = 'sshPort', help = 'SSH port')
			editMachineParser.add_argument('-f', required = False, dest = 'deploymentPath', help = 'Default Deployment Path')
			editMachineParser.set_defaults(func = editMachine)
			subparsersList.append(editMachineParser)
		elif (operation == 'deletemachine'):
			deleteMachineParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			deleteMachineParser.add_argument('-m', required = True, nargs='*', dest = 'machineNames', help = 'List of machine names')
			deleteMachineParser.set_defaults(func = deleteMachine)
			subparsersList.append(deleteMachineParser)
		elif (operation == 'discoverbehomes'):
			discoverHomeParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			discoverHomeParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			discoverHomeParser.add_argument('-s', required = False, default = False, dest = 'save', help = 'Save BE Homes')
			discoverHomeParser.set_defaults(func = discoverBEHomes)
			subparsersList.append(discoverHomeParser)
		elif (operation == 'uploadexternaljars'):
			uploadJarsParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			uploadJarsParser.add_argument('-m', required = True, dest = 'machineName', help = 'Machine name')
			uploadJarsParser.add_argument('-b', required = True, dest = 'beHome', help = 'BE Home')
			uploadJarsParser.add_argument('-z', required = True, dest = 'jarFiles', help = 'Jar or Zip file')
			uploadJarsParser.set_defaults(func = uploadExternalJars)
			subparsersList.append(uploadJarsParser)
		elif (operation == 'editdeployment'):
			editDeploymentParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			editDeploymentParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			editDeploymentParser.add_argument('-c', required = False, dest = 'cddFile', help = 'CDD file')
			editDeploymentParser.add_argument('-e', required = False, dest = 'earFile', help = 'EAR file')
			editDeploymentParser.set_defaults(func = editDeployment)
			subparsersList.append(editDeploymentParser)
		elif (operation == 'editinstance'):	
			editInstanceParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			editInstanceParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			editInstanceParser.add_argument('-i', required = True, dest = 'instanceName', help = 'Instance name')
			editInstanceParser.add_argument('-u', required = False, dest = 'pu', help = 'Processing Unit')
			editInstanceParser.add_argument('-m', required = False, dest = 'machineName', help = 'Machine name')
			editInstanceParser.add_argument('-p', required = False, dest = 'jmxPort', help = 'JMX Port')
			editInstanceParser.add_argument('-f', required = False, dest = 'deploymentPath', help = 'Deployment Path')
			editInstanceParser.add_argument('-ju', required = False, dest = 'jmxuser', help = 'JMX User Name')
			editInstanceParser.add_argument('-jp', required = False, dest = 'jmxpass', help = 'JMX Password')
			editInstanceParser.add_argument('-bh', required = False, dest = 'beHome', help = 'BE Home')
			editInstanceParser.set_defaults(func = editInstance)
			subparsersList.append(editInstanceParser)
		elif (operation == 'saveglobalvariable'):
			saveGlobalVariablesParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			saveGlobalVariablesParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			saveGlobalVariablesParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of Instances')
			saveGlobalVariablesParser.add_argument('-n', required = True, dest = 'varName', help = 'GV Name')
			saveGlobalVariablesParser.add_argument('-v', required = True, dest = 'varValue', help = 'GV Value')
			saveGlobalVariablesParser.set_defaults(func = saveGlobalVariable)
			subparsersList.append(saveGlobalVariablesParser)
		elif (operation == 'savesystemproperty'):
			saveSystemPropertiesParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			saveSystemPropertiesParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			saveSystemPropertiesParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of Instances')
			saveSystemPropertiesParser.add_argument('-n', required = True, dest = 'propName', help = 'System Property Name')
			saveSystemPropertiesParser.add_argument('-v', required = True, dest = 'propValue', help = 'System Property Value')
			saveSystemPropertiesParser.set_defaults(func = saveSystemProperty)
			subparsersList.append(saveSystemPropertiesParser)
		elif (operation == 'savejvmproperty'):
			saveJVMPropertiesParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			saveJVMPropertiesParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			saveJVMPropertiesParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of Instances')
			saveJVMPropertiesParser.add_argument('-n', required = True, dest = 'propName', help = 'JVM Property Name')
			saveJVMPropertiesParser.add_argument('-v', required = True, dest = 'propValue', help = 'JVM Property Value')
			saveJVMPropertiesParser.set_defaults(func = saveJVMProperty)
			subparsersList.append(saveJVMPropertiesParser)
		elif (operation == 'savebeproperty'):
			saveBEPropertiesParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			saveBEPropertiesParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			saveBEPropertiesParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of Instances')
			saveBEPropertiesParser.add_argument('-n', required = True, dest = 'propName', help = 'BE Property Name')
			saveBEPropertiesParser.add_argument('-v', required = True, dest = 'propValue', help = 'BE Property Value')
			saveBEPropertiesParser.set_defaults(func = saveBEProperty)
			subparsersList.append(saveBEPropertiesParser)
		elif (operation == 'savedeploymentvariables'):
			saveDeploymentVariablesParser = subparsers.add_parser(operation, prog = operation, add_help = True)
			saveDeploymentVariablesParser.add_argument('-d', required = True, dest = 'applicationName', help = 'Application deployment name')
			saveDeploymentVariablesParser.add_argument('-i', nargs='*', dest = 'instances', help = 'List of Instances')
			saveDeploymentVariablesParser.add_argument('-tp', required = True, dest = 'type', help = 'Type of variables(give either one of GV/BE/JVM/SYS)')
			saveDeploymentVariablesParser.add_argument('-p', required = True, dest = 'propFile', help = 'Property file location')
			saveDeploymentVariablesParser.set_defaults(func = saveDeploymentVariables)
			subparsersList.append(saveDeploymentVariablesParser)

	return commandParser, subparsersList

##########################################################################################################################################
# Configuration Management operations
##########################################################################################################################################
def editMachine(args):
	try:
		print ('Executing operation - editmachine')
		beMasterHost = getMasterHost(beProduct, args.machineName)
		if (not beMasterHost):
			print('Machine ' + args.machineName + ' not found')
			exit()
		newMachineName = args.newMachineName if args.newMachineName else beMasterHost.hostName
		ipAddress = args.ipAddress if args.ipAddress else beMasterHost.hostIp
		OS = args.OS if args.OS else beMasterHost.os
		if(OS == "windows"):
			OS = "Windows Based"
		else:
			OS = "OS/X,Unix/Linux Based"
			
		beHome = args.beHome if args.beHome else beMasterHost.beHome
		beTra = args.beTra if args.beTra else beMasterHost.beTra
		user = args.user if args.user else beMasterHost.userName
		pwd = args.pwd if args.pwd else beMasterHost.password
		sshPort = args.sshPort if args.sshPort else beMasterHost.sshPort
		deploymentPath = args.deploymentPath if args.deploymentPath else beMasterHost.deploymentPath
		result = beMasterHost.editCli(newMachineName, ipAddress, OS, beHome, beTra, user, pwd, sshPort, deploymentPath)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
		
def deleteMachine(args):
	try:
		print ('Executing operation - deletemachine')
		machineNameKeys = []
		
		if (args.machineNames and len(args.machineNames) > 0):
			for machineName in args.machineNames:
				beMasterHost = getMasterHost(beProduct, machineName)
				hostInfo = None
				if (beMasterHost):
					hostInfo = beMasterHost.getHostInfo()
					machineNameKeys.append(hostInfo.hostId)
				else:
					print('Machine ' + machineName + ' not found')
		else:
			print("Provide some machine names to delete")
			exit()
					
		result = getattr(beProduct, 'groupMasterHostsDelete')(machineNameKeys)
		
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
		
def discoverBEHomes(args):
	try:
		print ('Executing operation - discoverbehomes')
		
		beMasterHost = getMasterHost(beProduct, args.machineName)
		if (not beMasterHost):
			print('Machine ' + args.machineName + ' not found')
			exit()
		
		if(args.save):
			machineNames = []
			machineNames.append(beMasterHost.hostId)
			beProduct.discoverBEHome(machineNames)
			print("Successfully Saved")
		else :
			result = beProduct.getBeHomes(beMasterHost.hostName, beMasterHost.hostIp, beMasterHost.os, beMasterHost.userName, beMasterHost.password, beMasterHost.sshPort)
			for be in result:
				print("Home=" + be.beHome + " TRA=" + be.beTra + " Version=" + be.version)
			
	except Exception as ex:
		print("exception caught")
		details = ex.args[0]
		print(details)	
		
def uploadExternalJars(args):
	jarFilesHandle = None
	beId = None
	
	try:
		print ('Executing operation - uploadexternaljars')
		
		beMasterHost = getMasterHost(beProduct, args.machineName)
		if (not beMasterHost):
			print('Machine ' + args.machineName + ' not found')
			exit()
		
		hostInfo = beMasterHost.getHostInfo()
		
		for be in hostInfo.be:
			if(be.beHome == args.beHome):
				beId = be.id
		
		if (os.path.isfile(args.jarFiles)):
			if (not os.access(args.jarFiles, os.R_OK)):
				print('Cannot read Jar/Zip file ' + args.jarFiles)
				exit()
		else:
			print('Jar/Zip file ' + args.exportedZipFile + ' not found')
			exit()
			
		jarFilesHandle = io.open(args.jarFiles, 'rb')
		
		
		result = beMasterHost.uploadExternalJars(jarFilesHandle, beId)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (jarFilesHandle):
			jarFilesHandle.close()


def editDeployment(args):
	cddFileHandle = None
	earFileHandle = None
	try:
		print ('Executing operation - editdeployment')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		if (args.cddFile):
			if (os.path.isfile(args.cddFile)):
				if (os.access(args.cddFile, os.R_OK)):
					cddFileHandle = io.open(args.cddFile, 'rb')	
				else:
					print('Cannot read CDD file ' + args.cddFile)
					exit()
			else:
				print('CDD file ' + args.cddFile + ' not found')
				exit()
		if (args.earFile):
			if (os.path.isfile(args.earFile)):
				if (os.access(args.earFile, os.R_OK)):
					earFileHandle = io.open(args.earFile, 'rb')
				else:
					print('Cannot read EAR file ' + args.earFile)
					exit()
			else:
				print('EAR file ' + args.earFile + ' not found')
				exit()
		if(cddFileHandle is None):
			cddFileHandle = ''
		if(earFileHandle is None):
			earFileHandle = ''
		result = beApplication.upload('', cddFileHandle, earFileHandle, (args.cddFile != None), (args.earFile != None))
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

def editInstance(args):
	try:
		print ('Executing operation - editinstance')
		beApplication = getApplication(beProduct, args.applicationName)	
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		serviceInstance = getServiceInstance(beApplication, args.instanceName)
		if (not serviceInstance):
			print('Instance ' + args.instanceName + ' not found')
			exit()
		pu = args.pu if args.pu else serviceInstance.Instance.puId
		machineName = args.machineName if args.machineName else serviceInstance.host['hostName']
		jmxPort = args.jmxPort if args.jmxPort else serviceInstance.jmxPort
		deploymentPath = args.deploymentPath if args.deploymentPath else serviceInstance.deploymentPath
		jmxuser = args.jmxuser if args.jmxuser else ''
		jmxpass = args.jmxpass if args.jmxpass else ''
		masterHost = getMasterHost(beProduct, machineName)
		if (not masterHost):
			print('MasterHost ' + machineName + ' not found')
			exit()
		beId = masterHost.getBeId(args.beHome);
		if ((not args.beHome is None)  and (beId is None)):
			print('Invalid Be Home')
			exit()
		result = serviceInstance.edit(pu, machineName, jmxPort, deploymentPath, jmxuser, jmxpass, beId)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
		
def saveDeploymentVariables(args):
	
	try:
		print ('Executing operation - savedeploymentvariables')
		propFileHandle = None
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
					print('Instance ' + instanceName + ' not found.')
		else:
			for serviceInstance in beApplication.ServiceInstances.values():
				instanceKeys.append(serviceInstance.key)
		propFileHandle = io.open(args.propFile, 'rb')
		
		result = beApplication.saveDeploymentVariables(propFileHandle, instanceKeys, args.type)
		print(result)
		
	except Exception as ex:
		details = ex.args[0]
		print(ex)
	finally:
		if (propFileHandle):
			propFileHandle.close()

def saveGlobalVariable(args):
	try:
		print ('Executing operation - saveglobalvariable')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		if (args.instances and len(args.instances) == 1):
			serviceInstance = getServiceInstance(beApplication, args.instances[0])
			if (not serviceInstance):
				print('Instance ' + args.instanceName + ' not found')
				exit()
			mod = serviceInstance.module_
			nameValuePair = mod.NameValuePair(type=None, hasDefaultValue=None, isDeleted=None, deployedValue=None, name=args.varName, defaultValue=None, description=None, value=args.varValue)
			nameValuePairsList = []
			nameValuePairsList.append(nameValuePair)
			nameValuePairs = mod.NameValuePairs(nameValuePairsList)
			deploymentVariableType = serviceInstance.getDepVarType('GLOBAL_VARIABLES')			
			deploymentVariables = mod.DeploymentVariables(nameValuePairs=nameValuePairs, name=args.applicationName, type=deploymentVariableType, version=0, key=None)
			result = serviceInstance.saveGlobalVariables(deploymentVariables)
			print(result)
		else:
			instanceKeys = []
			if (args.instances and len(args.instances) > 0):
				for instanceName in args.instances:
					serviceInstance = getServiceInstance(beApplication, instanceName)
					if (serviceInstance):
						instanceKeys.append(serviceInstance.key)
					else:
						print('Instance ' + instanceName + ' not found.')
			else:
				for serviceInstance in beApplication.ServiceInstances.values():
					instanceKeys.append(serviceInstance.key)
			mod = beApplication.module_
			groupDeploymentVariable = mod.GroupDeploymentVariable(deploymentVariableType=None, deleted=None, effectiveValue=None, hasEqualValue=None, deployedValue=None,name=args.varName,description=None,isNew=None,type=None,variablesVersion=None,selectedInstances=instanceKeys,value=args.varValue)
			groupDeploymentVariableList = []
			groupDeploymentVariableList.append(groupDeploymentVariable)
			result = beApplication.saveGroupGlobalVariables(groupDeploymentVariableList)
			print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

def saveSystemProperty(args):
	try:
		print ('Executing operation - savesystemproperty')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		if (args.instances and len(args.instances) == 1):
			serviceInstance = getServiceInstance(beApplication, args.instances[0])
			if (not serviceInstance):
				print('Instance ' + args.instances[0] + ' not found')
				exit()
			
			mod = serviceInstance.module_
			nameValuePair = mod.NameValuePair(type=None, hasDefaultValue=None, isDeleted=None, deployedValue=None, name=args.propName, defaultValue=None, description=None, value=args.propValue)
			nameValuePairsList = []
			nameValuePairsList.append(nameValuePair)
			nameValuePairs = mod.NameValuePairs(nameValuePairsList)
			deploymentVariableType = serviceInstance.getDepVarType('SYSTEM_VARIABLES')
			deploymentVariables = mod.DeploymentVariables(nameValuePairs=nameValuePairs, name=args.applicationName, type=deploymentVariableType, version=0, key=None)			
			result = serviceInstance.saveSystemVariables(deploymentVariables)
			print(result)
		else:
			instanceKeys = []
			if (args.instances and len(args.instances) > 0):
				for instanceName in args.instances:
					serviceInstance = getServiceInstance(beApplication, instanceName)
					if (serviceInstance):
						instanceKeys.append(serviceInstance.key)
					else:
						print('Instance ' + instanceName + ' not found for')
			else:
				for serviceInstance in beApplication.ServiceInstances.values():
					instanceKeys.append(serviceInstance.key)
			mod = beApplication.module_
			groupDeploymentVariable = mod.GroupDeploymentVariable(deploymentVariableType=None, deleted=None, effectiveValue=None, hasEqualValue=None, deployedValue=None,name=args.propName,description=None,isNew=None,type=None,variablesVersion=None,selectedInstances=instanceKeys,value=args.propValue)
			groupDeploymentVariableList = []
			groupDeploymentVariableList.append(groupDeploymentVariable)
			result = beApplication.saveGroupSystemVariables(groupDeploymentVariableList)
			print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

def saveJVMProperty(args):
	try:
		print ('Executing operation - savejvmproperty')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		if (args.instances and len(args.instances) == 1):
			serviceInstance = getServiceInstance(beApplication, args.instances[0])
			if (not serviceInstance):
				print('Instance ' + args.instanceName + ' not found')
				exit()
			mod = serviceInstance.module_
			nameValuePair = mod.NameValuePair(type=None, hasDefaultValue=None, isDeleted=None, deployedValue=None, name=args.propName, defaultValue=None, description=None, value=args.propValue)
			nameValuePairsList = []
			nameValuePairsList.append(nameValuePair)
			nameValuePairs = mod.NameValuePairs(nameValuePairsList)
			deploymentVariableType = serviceInstance.getDepVarType('JVM_PROPERTIES')
			deploymentVariables = mod.DeploymentVariables(nameValuePairs=nameValuePairs, name=args.applicationName, type=deploymentVariableType, version=0, key=None)
			result = serviceInstance.saveJVMProperties(deploymentVariables)
			print(result)
		else:
			instanceKeys = []
			if (args.instances and len(args.instances) > 0):
				for instanceName in args.instances:
					serviceInstance = getServiceInstance(beApplication, instanceName)
					if (serviceInstance):
						instanceKeys.append(serviceInstance.key)
					else:
						print('Instance ' + instanceName + ' not found.')
			else:
				for serviceInstance in beApplication.ServiceInstances.values():
					instanceKeys.append(serviceInstance.key)
			mod = beApplication.module_
			groupDeploymentVariable = mod.GroupDeploymentVariable(deploymentVariableType=None, deleted=None, effectiveValue=None, hasEqualValue=None, deployedValue=None,name=args.propName,description=None,isNew=None,type=None,variablesVersion=None,selectedInstances=instanceKeys,value=args.propValue)
			groupDeploymentVariableList = []
			groupDeploymentVariableList.append(groupDeploymentVariable)
			result = beApplication.saveGroupJVMproperties(groupDeploymentVariableList)
			print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
		
def saveBEProperty(args):
	try:
		print ('Executing operation - savebeproperty')
		beApplication = getApplication(beProduct, args.applicationName)
		if (not beApplication):
			print('Application ' + args.applicationName + ' not found')
			exit()
		if (args.instances and len(args.instances) == 1):
			serviceInstance = getServiceInstance(beApplication, args.instances[0])
			if (not serviceInstance):
				print('Instance ' + args.instanceName + ' not found')
				exit()
			mod = serviceInstance.module_
			nameValuePair = mod.NameValuePair(type=None, hasDefaultValue=None, isDeleted=None, deployedValue=None, name=args.propName, defaultValue=None, description=None, value=args.propValue)
			nameValuePairsList = []
			nameValuePairsList.append(nameValuePair)
			nameValuePairs = mod.NameValuePairs(nameValuePairsList)
			deploymentVariableType = serviceInstance.getDepVarType('BE_PROPERTIES')
			deploymentVariables = mod.DeploymentVariables(nameValuePairs=nameValuePairs, name=args.applicationName, type=deploymentVariableType, version=0, key=None)
			result = serviceInstance.saveBEProperties(deploymentVariables)
			print(result)
		else:
			instanceKeys = []
			if (args.instances and len(args.instances) > 0):
				for instanceName in args.instances:
					serviceInstance = getServiceInstance(beApplication, instanceName)
					if (serviceInstance):
						instanceKeys.append(serviceInstance.key)
					else:
						print('Instance ' + instanceName + ' not found.')
			else:
				for serviceInstance in beApplication.ServiceInstances.values():
					instanceKeys.append(serviceInstance.key)
			mod = beApplication.module_
			groupDeploymentVariable = mod.GroupDeploymentVariable(deploymentVariableType=None, deleted=None, effectiveValue=None, hasEqualValue=None, deployedValue=None,name=args.propName,description=None,isNew=None,type=None,variablesVersion=None,selectedInstances=instanceKeys,value=args.propValue)
			groupDeploymentVariableList = []
			groupDeploymentVariableList.append(groupDeploymentVariable)
			result = beApplication.saveGroupBEProperties(groupDeploymentVariableList)
			print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)

##########################################################################################################################################
# Startup code
##########################################################################################################################################
operationsList = ['editmachine', 'deletemachine', 'discoverbehomes', 'uploadexternaljars', 'editdeployment', 'editinstance', 'saveglobalvariable', 'savesystemproperty', 'savejvmproperty', 'savebeproperty', 'savedeploymentvariables']			
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
	exit()
