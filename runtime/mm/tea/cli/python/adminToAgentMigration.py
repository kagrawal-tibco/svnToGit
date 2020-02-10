#!/usr/bin/env python

"""adminToAgentMigration.py: CLI for Applications Management BE-TEA Operations."""

import sys
import io
import os.path
import argparse
import tibco.tea
from utils import *

# Create the command-line ArgumentParser
def createCommandParser():
	#create the top-level parser
	commandParser = argparse.ArgumentParser(add_help = False, description = 'Administrator to BE Agent Migration Operation CLI.')
	commandParser.add_argument('-ssl', required = False, default = False, dest = 'sslEnabled', help = 'SSL Enabled')
	args = commandParser.parse_known_args()[0]
	commandParser.add_argument('-t', required = False, default = getDefaultTEAServerURL(args.sslEnabled), dest = 'serverURL', help = 'TEA Server URL')
	commandParser.add_argument('-u', required = True, dest = 'userName', help = 'TEA User Name')
	commandParser.add_argument('-p', required = True, dest = 'userPwd', help = 'TEA User Password')
	commandParser.add_argument('-sc', required = False, default = '', dest = 'serverCert', help = 'Server certificate Path')
	commandParser.add_argument('-cc', required = False, default = '', dest = 'clientCert', help = 'Client certificate Path')
	subparsers = commandParser.add_subparsers(dest='operationName')
	
	subparsersList = []
	for operation in operationsList:
		if (operation == 'migrateapplications'):
			migrateApplicationParser = subparsers.add_parser(operation, prog = operation, add_help = False)
			migrateApplicationParser.add_argument('-z', required = True, dest = 'exportedZipFile', help = 'Exported Zip File Location')
			migrateApplicationParser.set_defaults(func = migrateapplications)
			subparsersList.append(migrateApplicationParser)
		elif (operation == 'migrateuserandroles'):
			migrateUserRolesParser = subparsers.add_parser(operation, prog = operation, add_help = False)
			migrateUserRolesParser.add_argument('-x', required = True, dest = 'exportedXmlFile', help = 'Exported Xml File Location')
			migrateUserRolesParser.set_defaults(func = migrateuserandroles)
			subparsersList.append(migrateUserRolesParser)
	
		

	return commandParser, subparsersList

##########################################################################################################################################
# Administrator to Be Agent Migration
##########################################################################################################################################

def migrateapplications(args):
	zipFileHandle = None
	try:
		print('Executing operation - migrateapplications')
		if (os.path.isfile(args.exportedZipFile)):
			if (not os.access(args.exportedZipFile, os.R_OK)):
				print('Cannot read Zip file ' + args.exportedZipFile)
				exit()
		else:
			print('Zip file ' + args.exportedZipFile + ' not found')
			exit()
							
		zipFileHandle = io.open(args.exportedZipFile, 'rb')
		with tea.timeout(30):
			result = beProduct.migrateCli(zipFileHandle)
		print(result)
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (zipFileHandle):
			zipFileHandle.close()
			
			
def migrateuserandroles(args):
	xmlFileHandle = None
	try:
		print('Executing operation - migrateuserandroles')
		if (os.path.isfile(args.exportedXmlFile)):
			if (not os.access(args.exportedXmlFile, os.R_OK)):
				print('Cannot read xml file ' + args.exportedXmlFile)
				exit()
		else:
			print('xml file ' + args.exportedXmlFile + ' not found')
			exit()
							
		xmlFileHandle = io.open(args.exportedXmlFile, 'rb')
		with tea.timeout(30):
			result = beProduct.migrateUsersAndRoles(xmlFileHandle)
			users = result[0]
			roles = result[1]
			
			for user in users:
				u = user.split(":")
				try:
					res = tea.users.createUser(name=u[0], password=u[1])
					print(res)
				except Exception as exu:
					details = exu.args[0]
					print("Error creating user - " + details)	
				
			for role in roles:
				r = role.split(":")
				rname = r[0];
				rusers = r[1].split(",")
				try:
					res = tea.roles.createRole(name=rname, description=rname, users=rusers, productName='BusinessEvents')
					print(res)
				except Exception as exr:
					details = exr.args[0]
					print("Error creating role - " + details)
					
	except Exception as ex:
		details = ex.args[0]
		print(details)
	finally:
		if (xmlFileHandle):
			xmlFileHandle.close()
			

##########################################################################################################################################
# Startup code
##########################################################################################################################################
operationsList = ['migrateapplications', 'migrateuserandroles']			
# Create Command parser
commamdParser, subparsersList = createCommandParser()
if (len(sys.argv) == 1): # Print complete usage & exit
	printCompleteUsage(commamdParser, subparsersList)
	exit()
#Parse the command arguments
command = commamdParser.parse_args()
if (command.operationName): #Execute operation
	tea = connectToServer(command.serverURL, command.userName, command.userPwd, command.sslEnabled, command.serverCert, command.clientCert)
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
