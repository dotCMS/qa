#
# TestRail API binding for Python 2.x (API v2, available since 
# TestRail 3.0)
#
# Learn more:
# http://docs.gurock.com/testrail-api2/bindings-python
# http://docs.gurock.com/testrail-api2/start
# http://docs.gurock.com/testrail-api2/accessing
#
# Copyright Gurock Software GmbH. See license.md for details.
# modified By Oswaldo Gallango
#

import urllib2, json, base64

class TestRailAPIClient:
    #
    # Init test rail API instance
    # Author: Oswaldo Gallango
    #
    # Arguments: 
    #
    # base_url      Test rail base url
    # user          Test rail user
    # password      Test rail pasword
    #
    def __init__(self, base_url,user,password):
        self.user = user
        self.password = password
        if not base_url.endswith('/'):
            base_url += '/'
        self.__url = base_url + 'index.php?/api/v2/'

    #
    # Send Get
    #
    # Issues a GET request (read) against the API and returns the result
    # (as Python dict).
    #
    # Arguments:
    #
    # uri                 The API method to call including parameters
    #                     (e.g. get_case/1)
    #
    def send_get(self, uri):
        return self.__send_request('GET', uri, None)

    #
    # Send POST
    #
    # Issues a POST request (write) against the API and returns the result
    # (as Python dict).
    #
    # Arguments:
    #
    # uri                 The API method to call including parameters
    #                     (e.g. add_case/1)
    # data                The data to submit as part of the request (as
    #                     Python dict, strings must be UTF-8 encoded)
    #
    def send_post(self, uri, data):
        return self.__send_request('POST', uri, data)

    def __send_request(self, method, uri, data):
        url = self.__url + uri
        request = urllib2.Request(url)
        if (method == 'POST'):
            request.add_data(json.dumps(data))
        auth = base64.encodestring('%s:%s' % (self.user, self.password)).strip()
        request.add_header('Authorization', 'Basic %s' % auth)
        request.add_header('Content-Type', 'application/json')

        e = None
        try:
            response = urllib2.urlopen(request).read()
        except urllib2.HTTPError as e:
            response = e.read()

        if response:
            result = json.loads(response)
        else:
            result = {}

        if e != None:
            if result and 'error' in result:
                error = '"' + result['error'] + '"'
            else:
                error = 'No additional error message received'
            raise APIError('TestRail API returned HTTP %s (%s)' % 
                (e.code, error))

        return result
    
    # Send test status to test rail API
    # Author: Oswaldo Gallango
    #
    # Arguments: 
    #
    # runId      Test Run Identifier as a string
    # testCaseId Test case number as a string
    # status     Integer value, use: 1 for Passed, 2 for Blocked, 3 for Untested, 4 for RestTest and 5 for failed
    # comment    String If you want to send any comment about the test 
    #
    def addResultsForCase(self,runId,testCaseId,status,comment):
        data = { 'status_id': status, 'comment': comment }
        self.send_post("add_result_for_case/" + runId + "/" + testCaseId, data)
    
    #
    # Add a Run 
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # projectId        Project id as a string
    # suiteId          Suite Id integer number
    # milestoneId      Milestone Id integer number
    # name             Name of the Run
    # description      Run description
    # assignesUserId   Id of the user integer number
    # includeAll       Include all test boolean
    #    
    def addRun(self, projectId, suiteId, milestoneId, name, description, assignesUserId, includeAll):
        data ={'suite_id': suiteId, 'milestone_id': milestoneId, 'name': name,'description': description,
               'assignedto_id':assignesUserId, 'include_all':bool(includeAll)}
        return self.send_post("add_run/" + projectId, data)
        
    #
    # Delete a Run. 
    # Return 200 if was succesfull, 400 invalid or unknow project, 403 no permission 
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # runId        Run id as a string
    #    
    def deleteRun(self, runId):
        data={}
        return self.send_post("delete_run/" +runId,data)   
    
    #
    # Return a jsonObject with the project runs list
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # projectId         Project id as a string
    #    
    def getRuns(self, projectId):
        return self.send_get("get_runs/" + projectId)   
    
    #
    # Return a jsonObject with the project runs list filtered by milestoneid and suite id
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # projectId         Project id as a string
    # milestoneIdId     Milestone id as a string
    # suiteId           Suite id as a string
    #
    def getRunsFiltered(self, projectId, milestoneId, suiteId):
        return self.send_get("get_runs/" + projectId+"&milestone_id="+milestoneId+"&suite_id="+suiteId) 
    
    #
    # Return a jsonObject with the project milestones list
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # projectId         Project id as a string
    # 
    def getMilestones(self, projectId):
        return self.send_get("get_milestones/" + projectId)
    
    #
    # Return a jsonObject with the list of projects
    # Author: Oswaldo Gallango
    # 
    def getProjects(self):
        return self.send_get("get_projects/")
    
    #
    # Return a jsonObject with the project suites list
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # projectId         Project id as a string
    # 
    def getSuites(self,projectId):
        return self.send_get("get_suites/"+projectId)
    
    #
    # Return a jsonObject with the list of users
    # Author: Oswaldo Gallango
    # 
    def getUsers(self):
        return self.send_get("get_users")
    
    #
    # Return a jsonObject with the specified user
    # Author: Oswaldo Gallango
    #
    # Arguments:
    #  userId   User Id as a string
    # 
    def getUser(self,userId):
        return self.send_get("get_user/"+userId)
    #
    # Return a jsonObject with the run tests list
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # runId         Run id as a string
    # 
    def getTests(self,runId):
        return self.send_get("get_tests/"+runId)
    
    #
    # Return a jsonObject with the test details
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # testId         Test id as a string
    # 
    def getTest(self,testId):
        return self.send_get("get_test/"+testId)
    
    #
    # Return a jsonObject with the test results
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # testId         Test id as a string
    #
    #def getResults(self, testId):
    #    return self.send_get("get_results/"+testId)
    
    #
    # Return a jsonObject with the test results from a particular run
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # runtId         Run id as a string
    #
    #def getRunResults(self, runId):
    #    return self.send_get("get_results_for_run/"+runId)
    
    #
    # Return a jsonObject with the test results from a particular run
    # Author: Oswaldo Gallango
    #
    # Arguments:
    # runtId         Run id as a string
    # caseId         Test case id as a string
    #
    def getResultsForCase(self,runId,caseId):
        return self.send_get("get_results_for_case/"+runId+"/"+caseId)

class APIError(Exception):
    pass
