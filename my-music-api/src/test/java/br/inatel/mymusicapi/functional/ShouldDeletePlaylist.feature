Feature: Registering a new User and new Playlist
    
Background: 
		* url 'http://localhost:8081/'
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.playlist.delete'+random()+'@test.com'
		* def playlistTitle = 'title'+random()

Scenario: Should register a new user and a new playlist, then delete it
    Given path 'users'
    And request {"name": 'Lucas', "email": '#(userEmail)', "password": '12345678'}
    When method POST
    Then status 201
    And match response contains {"id": '#notnull', "name": 'Lucas', "email": '#(userEmail)'}
    * def user = response
    
		Given path 'users/'+user.id
    When method GET
    Then status 200
    
		Given path 'login'
    And request {'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 200
    And match response contains{'token': '#notnull','type':'Bearer'}
    * def token = response.token
    
    Given path 'playlists'
		And header Authorization = 'Bearer ' + token
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 201
    And match response contains{'id':'#notnull','title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)','tracks':[]}
    * def playlist = response
    
    Given path 'playlists/'+playlist.id
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 200
    And match response contains 'Playlist '+playlist.id+' deleted.'
 
 Scenario: Should not delete a new playlist if not authenticated
    Given path 'users'
    And request {"name": 'Lucas', "email": '#(userEmail)', "password": '12345678'}
    When method POST
    Then status 201
    And match response contains {"id": '#notnull', "name": 'Lucas', "email": '#(userEmail)'}
    * def user = response
    
		Given path 'users/'+user.id
    When method GET
    Then status 200
    
    Given path 'playlists'
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 403
    
Scenario: Should display error message if playlist id is invalid
    Given path 'users'
    And request {"name": 'Lucas', "email": '#(userEmail)', "password": '12345678'}
    When method POST
    Then status 201
    And match response contains {"id": '#notnull', "name": 'Lucas', "email": '#(userEmail)'}
    * def user = response
    
		Given path 'users/'+user.id
    When method GET
    Then status 200
    
		Given path 'login'
    And request {'email': '#(userEmail)', 'password': '12345678'}
    When method POST
    Then status 200
    And match response contains{'token': '#notnull','type':'Bearer'}
    * def token = response.token
    
    Given path 'playlists'
		And header Authorization = 'Bearer ' + token
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 201
    And match response contains{'id':'#notnull','title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)','tracks':[]}
    * def playlist = response
    
    * def random = random()
    * def expectedErrorMessage = 'Playlist '+random+' not found.'
    Given path 'playlists/'+random
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 404
    And match response contains{'status':404,'message':'#(expectedErrorMessage)'}
		
		
		Given path 'playlists/'+'asd'
		And header Authorization = 'Bearer ' + token
		When method DELETE
		Then status 400
