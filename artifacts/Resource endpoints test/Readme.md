

# How to run the REST endpoints tests 

This is a set of automated tests to validate every workflow use case. This will validate the response, headers, and return codes for all endpoints. 


### *__Requirements__*
1- To run this set locally,  you need to have the npn installed in your computer, if you don't have this installed, follow this guide to install via homebrew. 
```
- brew update
- brew install node
- brew upgrade node
```
2- Now, make sure you have installed **"node"** and **"npm"** by running those commands in your terminal 
```
- node -v
- npm -v
```

3- Once you have npm, you should be able to install **"newman"**. **This is the library to interact with postman.** Run this command to install via npm.
```
- npm install -g newman
```
4- Make sure you have installed newman
```
- newman -v 
```
5- Finally,  you have all the required libraries to run test from terminal. 

### *__Running tests__*

To run this you only need to download the postman collection with all the test cases from this url: https://github.com/dotCMS/qa/blob/master/artifacts/Resource%20endpoints%20test/Workflow%20Resource%20Tests.postman_collection.json, this is a json file. 

To execute this test use: 
``` 
- newman run ($postmanCollectionPath)
```

**Also you can import that collection to postman and run each of them individually.** 

Here a doc to see how works every endpoint: https://goo.gl/UFw6qw
