#
# This class read the properties from the qa.properties file 
#
# Author: Oswaldo Gallango
#

class QAProperties:
    
    #
    # Init Properties class to read parameters from the qa.properties file
    # Author: Oswaldo Gallango
    #
    def __init__(self):
        self.properties = {}
        with open('../../src/main/resources/qa.properties') as file:
           for line in file:
               if not line or line.startswith('#') or not '=' in line:
                   continue
               
               key, value = line.split('=')
               self.properties[key.strip()]=value.strip()
               
        file.close()
        #print(self.properties)
    
    #
    # get a propertie value by key
    # Author: Oswaldo Gallango
    #
    # Argument:
    #    Key key value
    #
    def getValue(self,key):
        return self.properties.get(key)