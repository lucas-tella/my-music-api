Feature: Deleting user

Background: 
    * url 'http://localhost:8081/'
    * def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.delete'+random()+'@test.com'

Scenario: Should delete existing user if authenticated
		Given path 'users'
		And request {'name': 'Lucas', 'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 201
    And match response contains {'id': '#notnull', 'name': Lucas, 'email': '#(userEmail)'}
    * def user = response
    
    Given path 'login'
    And request {'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 200
    And match response contains{'token': '#notnull','type':'Bearer'}
    * def token = response.token

		Given path 'users/'+user.id
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 200
		And match response contains 'User '+user.id+' deleted.'
		
Scenario: Should not delete existing user if not authenticated
		Given path 'users'
		And request {'name': 'Lucas', 'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 201
    And match response contains {'id': '#notnull', 'name': Lucas, 'email': '#(userEmail)'}
    * def user = response

		Given path 'users/'+user.id
		When method DELETE
		Then status 401
		
Scenario: Should display error message if user id is invalid
		Given path 'users'
		And request {'name': 'Lucas', 'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 201
    And match response contains {'id': '#notnull', 'name': Lucas, 'email': '#(userEmail)'}
    * def user = response
    
    Given path 'login'
    And request {'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 200
    And match response contains{'token': '#notnull','type':'Bearer'}
    * def token = response.token

		Given path 'users/'+999999
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 404
		And match response contains{"status": 404,"message": "User 999999 not found."}
		
		Given path 'users/'+'asd'
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 400

		Given path 'users/'+'1a2s3d'
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 400