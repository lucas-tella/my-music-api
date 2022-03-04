Feature: Registering a new User
    
Background: 
		* url baseUrl
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.post'+random()+'@test.com'

Scenario: Should register a new user and check if we can find it
    Given path 'users'
    And request {"name": 'Lucas', "email": '#(userEmail)', "password": '12345678'}
    When method POST
    Then status 201
    And match response contains {"id": '#notnull', "name": 'Lucas', "email": '#(userEmail)'}
    * def user = response

		Given path 'login'
    And request {'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 200
    And match response contains{'token': '#notnull','type':'Bearer'}
    * def token = response.token
		
    Given path 'users/'+user.id
		And header Authorization = 'Bearer ' + token    
    When method GET
    Then status 200
    And match response contains {"id": '#notnull', "name": 'Lucas', "email": '#(userEmail)'}
    
    
Scenario: Should not register a new user with invalid email
    Given path 'users'
    And request {"name": 'Lucas', "email": 'qatest.br', "password": '12345678'}
    When method POST
    Then status 400

Scenario: Should not register a new user without eigth-characters-password
    Given path 'users'
    And request {"name": 'Lucas', "email": '#(userEmail)', "password": '1234567'}
    When method POST
    Then status 400
    
    Given path 'users'
    And request {"name": 'Lucas', "email": '#(userEmail)', "password": '123456789'}
    When method POST
    Then status 403
    And match response contains{'status':403,'message':'The password must have 8 characters.'}