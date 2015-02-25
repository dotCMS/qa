#
# Set Test Case status.
# Execution: python SetTestCaseStatus.py <nameOfRun> <caseId> <status> [<comment>]
# ex: python SetTestCaseStatus.py MyTestRun 234 PASSED
# ex: python SetTestCaseStatus.py MyTestRun 234 FAILED
#
# Author: Oswaldo Gallango
#
# Arguments:
#    nameOfRun      Name for the new run to create (Required)
#    caseId         Case Id as a string (Required)
#    status         Allowed values: PASSED, BLOCKED, UNTESTED, RETEST, FAILED (Required)
#    comment        Comment (Optional)
#

import sys
import TestRailAPIClient
import QAProperties

def main(nameOfRun,caseId,status,comment):
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
            suiteId = str(suite.get('id'))
            break
           
    #Get the milestone Id
    milestones = testrail.getMilestones(projectId)
    for milestone in milestones:
        #print("Milestone:",milestone)
        if milestone.get('name') == milestoneName:
            milestoneId = str(milestone.get('id'))
            break 
           
    #Get the run Id
    runs = testrail.getRunsFiltered(projectId,milestoneId,suiteId)
    for run in runs:
        #print("Run:",run)
        if run.get('name') == nameOfRun:
            runId = str(run.get('id'))
            break
        
    status = status.upper()       
    if status == 'PASSED': 
        statusVal = '1'
    elif status == 'BLOCKED': 
        statusVal = '2'   
    elif status == 'UNTESTED': 
        statusVal = '3'
    elif status == 'RETEST': 
        statusVal = '4'  
    elif status == 'FAILED': 
        statusVal = '5'
    else:
        statusVal = '0'     
            
    print('values: ',runId,caseId,statusVal,comment)
    results = testrail.addResultsForCase(runId,caseId,statusVal,comment)
    print("Case result status submitted")
    
if __name__ == "__main__":
    try:
        nameOfRun = str(sys.argv[1])
        caseId=str(sys.argv[2])
        status=str(sys.argv[3])
        comment=''
    
        if(len(sys.argv) > 4): 
            #print(sys.argv)
            comment = str(sys.argv[4])
          
        main(nameOfRun,caseId,status,comment)
    except Exception as e:
       print('ERROR: ')
       print(e)
       print 'Run this command to execute the script: python SetTestCaseStatus.py <Name Of Run> <Case Id> <status> [<comment>]'
       sys.exit(2)