"""utils.py: Common/Utility functions."""

import sys
import tibco.tea

##########################################################################################################################################
# Common Utility methods
##########################################################################################################################################
def getDefaultTEAServerURL(sslEnabled):
	if(sslEnabled):
		return 'https://localhost:8777'
	else:
		return 'http://localhost:8777'

# Connect to the TEA Server
def connectToServer(serverURL, userName, userPwd, sslEnabled, serverCert, clientCert, globalTimeout):
	try:
		print('Connecting to TEA Server ' + serverURL)
		if(sslEnabled):
			tea = tibco.tea.EnterpriseAdministrator(config = {'enable_class_generation':True}, url = serverURL, user = userName, pwd = userPwd, retry=10, wait=5, client_cert_path=clientCert, server_cert_path=serverCert, timeout=int(globalTimeout))
		else:
			tea = tibco.tea.EnterpriseAdministrator(config = {'enable_class_generation':True}, url = serverURL, user = userName, pwd = userPwd, retry=10, wait=5, timeout=int(globalTimeout))			
		tea.refresh_()
		print('Successfully connected to TEA Server ' + serverURL)
	except Exception as ex:
		print('Connection to TEA Server ' + serverURL + ' failed')
		details = ex.args[0]
		print(details)
		sys.exit(2)
	return tea

def printCompleteUsage(commamdParser, subparsersList):
	commamdParser.print_usage()
	for subparser in subparsersList:
		parserUsage = subparser.format_usage()
		parserUsage = parserUsage.replace("usage:", "", 1)
		print (parserUsage)

# Get the BEApplication	TEA object
def getMasterHost(beProduct, machineName):
	beMasterHosts = beProduct.MasterHosts	
	beMasterHost = None
	for beMasterHost2 in beMasterHosts.values(): 
		if (beMasterHost2.hostName == machineName):
			beMasterHost = beMasterHost2
			break
	return beMasterHost

# Get the BEApplication	TEA object
def getApplication(beProduct, applicationName):
	beApplications = beProduct.Applications
	beApplications.refresh_()
	beApplication = None
	for beApplication2 in beApplications.values(): 
		if (beApplication2.name == applicationName):
			beApplication = beApplication2
			break			
	return beApplication

# Get the BEApplicationHost	TEA object
def getApplicationHost(beApplication, hostName):
	beApplicationHosts = beApplication.Hosts
	beApplicationHosts.refresh_()
	beApplicationHost = None
	for beApplicationHost2 in beApplicationHosts.values(): 
		if (beApplicationHost2.name == hostName):
			beApplicationHost = beApplicationHost2
			break			
	return beApplicationHost	

# Get the BEProcessingUnit TEA object
def getApplicationPU(beApplication, puName):
	beApplicationPUs = beApplication.ProcessingUnits
	beApplicationPUs.refresh_()
	beApplicationPU = None
	for beApplicationPU2 in beApplicationPUs.values(): 
		if (beApplicationPU2.puId == puName):
			beApplicationPU = beApplicationPU2
			break			
	return beApplicationPU

# Get the BEProcessingUnitAgent TEA object
def getApplicationPuAgent(beApplication, agentClass):
	beApplicationPuAgents = beApplication.ApplicationAgents
	#beApplicationPuAgents.refresh_()
	beApplicationPuAgent = None
	for beApplicationPuAgent2 in beApplicationPuAgents.values(): 
		if (beApplicationPuAgent2.name == agentClass):
			beApplicationPuAgent = beApplicationPuAgent2
			break			
	return beApplicationPuAgent

# Get the BEServiceInstance TEA object	
def getServiceInstance(beTeaObjectEntity, instanceName):
	serviceInstances = beTeaObjectEntity.ServiceInstances
	serviceInstances.refresh_()
	serviceInstance = None
	for serviceInstance2 in serviceInstances.values(): 
		if (serviceInstance2.name == instanceName):
			serviceInstance = serviceInstance2
			break			
	return serviceInstance

def getAgentInstance(beTeaObjectEntity, agentName):
	agentInstances = beTeaObjectEntity.Agent
	agentInstances.refresh_()
	agentInstance = None
	for agentInstance2 in agentInstances.values(): 
		if (agentInstance2.name == agentName):
			agentInstance = agentInstance2
			break			
	return agentInstance

def exit():
	print('Exiting...')
	sys.exit(2)
