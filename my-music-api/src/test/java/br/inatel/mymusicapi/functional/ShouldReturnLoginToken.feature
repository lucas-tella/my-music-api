Feature: Login and authentication (security tests)

Background:
		* url baseUrl
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.login'+random()+'@test.com'
#		* def delay = setTimeout(function(){console.log('Deu bão')},1800001)
#		* def delay = function(){setTimeout(() => {console.log("this is the first message")}, 1800001)}
#		* def delay = function(){java.lang.Thread.sleep("${mymusicapi.jwt.expiration}")}
		

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
    
Scenario: Should not authenticate a user with a invalid token
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
    * def invalidToken = token+random()
    
    Given path 'users/'+user.id
		And header Authorization = 'Bearer ' + invalidToken    
    When method GET
    Then status 401
    
Scenario: Should not authenticate a user with a expired token
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

#		* call delay()
    
    Given path 'users/'+user.id
		And header Authorization = 'Bearer '+token  
		When method GET
    Then status 401
    
