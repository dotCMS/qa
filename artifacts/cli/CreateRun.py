#
# Create a new test Run.
# Execution: python CreateRun.py <Run Name> [<description>]
# ex: python CreateRun.py MyTestRun
# ex: python CreateRun.py MyTestRun 'this is a test'
#
# Author: Oswaldo Gallango
#
# Arguments:
#    nameOfRun        Name for the new run to create (Required)
#    description    Description of the run (optional)
#

import sys
import TestRailAPIClient
import QAProperties

def main(nameOfRun,description):
    projectId= ''
    suiteId= ''
    milestoneId= ''
    assignesUserId= ''
    includeAll= ''
   
    properties = QAProperties.QAProperties()
    projectName = properties.getValue('testrail.Project')
    suiteName = properties.getValue('testrail.Suite')
    milestoneName = properties.getValue('testrail.Milestone')
    testrailURL = properties.getValue('testrail.URL')
    testRailUser = properties.getValue('testrail.User')
    testRailPassword = properties.getValue('testrail.Password')
    testrail = TestRailAPIClient.TestRailAPIClient(testrailURL,testRailUser,testRailPassword)
       
    #get the user id
    users = testrail.getUsers()
    #print('Users: ',users)
    for user in users:
        #print("User:",user)
        if user.get('email') == testRailUser:
            userassignesUserId = user.get('id')
            break 
           
    #Get the project Id
    projects = testrail.getProjects()
    for project in projects:
        #print("Projects:",project)
        if project.get('name') == projectName:
            projectId = str(project.get('id'))
            break
       
    #Get the suite Id
    suites = testrail.getSuites(projectId)
    for suite in suites:
        #print("Suite:",suite)
        if suite.get('name') == suiteName:
            suiteId = suite.get('id')
            break
           
    #Get the milestone Id
    milestones = testrail.getMilestones(projectId)
    for milestone in milestones:
        #print("Milestone:",milestone)
        if milestone.get('name') == milestoneName:
            milestoneId = milestone.get('id')
            break   
            
    #print('values: ',projectId,suiteId,milestoneId,userassignesUserId)
    results = testrail.addRun(projectId, suiteId, milestoneId, nameOfRun, description, userassignesUserId, 'true')
    print("Results: ",results)
   
   
if __name__ == "__main__":
    try:
        nameOfRun = str(sys.argv[1])
        description=''
    
        if(len(sys.argv) > 2): 
            #print(sys.argv)
            description = str(sys.argv[2])
      
        main(nameOfRun,description)
    except Exception as e:
       print('ERROR: ')
       print(e)
       print 'To run execute: python CreateRun.py <Name Of Run> [<description>]'
       sys.exit(2)