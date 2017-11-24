# SDE 2017 Assigment 2 Client 


**Assigment 2 Client - Mattia Buffa mattia.buffa-1@studenti.unitn.it**  
**Assigment 2 Server repository: https://github.com/CommanderBuffin/introsde-2017-assignment-2-server**  
**Partner Denis Gallo**
**Assigment 2 Partner Server repository: https://github.com/DenisGallo/introsde-2017-assignment-2-server**  
**Assigment 2 Partner Client repository: https://github.com/DenisGallo/introsde-2017-assignment-2-client**  
**Assigment 2 Partner Server heroku: http://assignment2-denisgallo.herokuapp.com/sdelab**  

**Table of Contents**

- [SDE 2017 Assigment 2 Client](#sde-2017-assigment-2-Client)
	- [Project Description](#project-description)
		- [Code analysis](#code-analysis)
		- [Code tasks](#code-tasks)
		- [Execution](#execution)
		- [Additional Notes](#additional-notes)

## Project Description

### Code analysis

**RunClient.java**

This is the class that will be called by the command:

```
ant execute.client
```
This class calls all the required Steps.

**RESTClient.java**

This class contains the implementation of the HTTP class defined as requests and the aggregation of them as step.

### Code tasks

**RunClient**

The first task of the code is to call RunClient which will call the database init and all the other steps defined inside RESTClient.

**RESTClient**

This class has implementations of the required steps in the following way:

__StepInit - callInit()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/init to initialize the database.

__Step3.1 - step1()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/person to retrieve all the people, saves the first and last person id, calculates if there are at least 5 people then prints OK or ERROR using the suggested pattern.

__Step3.2 - step2()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/person/{first_person_id} then prints the prints OK or ERROR using the suggested pattern. 

__Step3.3 - step3()__

Calls the API PUT http://assignment2-denisgallo.herokuapp.com/sdelab/person/{first_person_id} sending a Person then checks if the firstname is changed and prints OK or ERROR using the suggested pattern.

__Step3.4 - step4()__

Calls the API POST http://assignment2-denisgallo.herokuapp.com/sdelab/person sending a new Person with an Activity then check if there is a PersonID, saves it and prints OK or ERROR using the suggested pattern.

__Step3.5 - step5()__

Calls the API DELETE http://assignment2-denisgallo.herokuapp.com/sdelab/person/{new_person_id} and checks if the person has been deleted using the request2 then prints OK or ERROR using the suggested pattern.

__Step3.6 - step6()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/activity_types to retrieve all the ActivityType and checks if they are more than two then prints OK or ERROR using the suggested pattern.

__Step3.7 - step7()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/person/{id}/{activity_type} using first_person_id and last_person_id and looks for an ActivityType looping all the activity_types, stores the personID and the activityID for the person that has the activity with that activity_type then prints OK or ERROR using the suggested pattern.

__Step3.8 - step8()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/person/{id}/{activity_type}/{activity_id} using the previous activityID, activityType and personID checks if it exists then prints OK or ERROR using the suggested pattern.

__Step3.9 - step9()__

Calls the request7 using an activityType saves the count of retrived activities then calls POST http://assignment2-denisgallo.herokuapp.com/sdelab/person/{first_person_id}/{activity_type} sending a new Activity then redo the request7 and checks if the count is greater than before then prints OK or ERROR using the suggested pattern.

__Step3.10 - step10()__

Calls the API PUT http://assignment2-denisgallo.herokuapp.com/sdelab/person/{id}/{activity_type}/{activity_id} sending an Activity and then calls request6 to check if the PUT changed the previous activity then prints OK or ERROR using the suggested pattern.

__Step3.11 - step11()__

Calls the API GET http://assignment2-denisgallo.herokuapp.com/sdelab/person/{id}/{activity_type}?before={beforeDate}&after={afterDate} in order to retrieve all the activity of a person in a range of date and checks if there is at least one activity then prints OK or ERROR using the suggested pattern.

**Build.xml**

The file build.xml contains all the task that will:  
- install, download and resolve ivy dependencies  
- compile classes  
- run execute.client target which will call RunClient
	
### Execution

In order to execute the assigment we need to have ant installed: open a terminal, navigate to the root project folder and run this instruction 
```
ant execute.client
```
A log file called 'client-server-log' with all the results will be created inside the root folder

### Additional Notes

Step3.11 requires dates in "yyyy-MM-dd-hh-mm" format 
  
*Bugs don't exist there are only strange features.*