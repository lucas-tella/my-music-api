Feature: Login and authentication

Background:
		* url 'http://localhost:8081/'
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.login'+random()+'@test.com'
		

Scenario: Should return login token
		Given path 'users'
		And request {'name': 'Lucas', 'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 201
    And match response contains {'id': '#notnull', 'name': Lucas, 'email': '#(userEmail)'}
    
    Given path 'login'
    And request {'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 200
    And match response contains{'token': '#notnull','type':'Bearer'}
    
Scenario: Should not return login token using wrong password
		Given path 'users'
		And request {'name': 'Lucas', 'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 201
    And match response contains {'id': '#notnull', 'name': Lucas, 'email': '#(userEmail)'}
    
    Given path 'login'
    And request {'email': '#(userEmail)', 'password': '1234567'}
    When method POST
    Then status 400
    
Scenario: Should not return login token using wrong email
		Given path 'users'
		And request {'name': 'Lucas', 'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 201
    And match response contains {'id': '#notnull', 'name': Lucas, 'email': '#(userEmail)'}
    
    Given path 'login'
    And request {'email': 'email#(random())@test.com', 'password': '12345678'}
    When method POST
    Then status 400
    
