Feature: Registering a new User and new Playlist

Background: 
		* url baseUrl
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.playlist'+random()+'@test.com'
		* def playlistTitle = 'title'+random()

Scenario: Should register a new user and a new playlist and check if we can find them
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
    
    Given path 'playlists'
		And header Authorization = 'Bearer ' + token
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 201
    And match response contains{'id':'#notnull','title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)','tracks':[]}
    * def playlist = response
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And header Authorization = 'Bearer ' + token
    When method GET
    Then status 200
    And match response contains{'id':'#notnull','title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)','tracks':[]}
    
Scenario: Should not register a new playlist if not authenticated
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
    
    Given path 'playlists'
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 401
 
Scenario: Should not register a new playlist with invalid title
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
    
    Given path 'playlists'
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 201
    * def newPlaylist = response
    
    Given path 'playlists'
    And request {'title':'#(newPlaylist.title)','description':'my first playlist','userId':'#(user.id)'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 403
    And match response contains{'status':403,'message':'This title is already being used in another Playlist.'}
    
Scenario: Should not register a new playlist with invalid user id
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
    
    Given path 'playlists'
		And header Authorization = 'Bearer ' + token
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(random())'}
    When method POST
    Then status 404
    