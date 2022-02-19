Feature: Deleting track from playlist

Background: 
		* url 'http://localhost:8081/'
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.playlist'+random()+'@test.com'
		* def playlistTitle = 'title'+random()

Scenario: Should register a new user, a new playlist, add a track to it and then delete it
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
    
    Given path 'playlists'
		And header Authorization = 'Bearer ' + token
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 201
    And match response contains{'id':'#notnull','title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)','tracks':[]}
    * def playlist = response
    
    * def trackId = 1280165222
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And header Authorization = 'Bearer '+token
    And request {'id': '#(trackId)'}
    When method POST
    Then status 200
    And match response contains{'id':'#notnull','title':'#notnull','duration':'#notnull','artist':'#notnull','artist':'#notnull'}
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And header Authorization = 'Bearer '+token
    And request {'id': '#(trackId)'}
    When method GET
    Then status 200
    And match response contains{'id':'#(playlist.id)','title':'#(playlistTitle)','description':'#notnull','userId':'#(user.id)','tracks':'#notnull'}
    
    * def expectedMessage = 'Track '+trackId+' deleted from playlist '+playlist.id+'.'
    
    Given path 'playlists/'+playlist.id+'/tracks/'+trackId
    And header Authorization = 'Bearer '+token
		When method DELETE
		Then status 200
		And match response contains '#(expectedMessage)'
		    
Scenario: Should not delete track without authentication
    
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
    
    Given path 'playlists'
		And header Authorization = 'Bearer ' + token
    And request {'title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)'}
    When method POST
    Then status 201
    And match response contains{'id':'#notnull','title':'#(playlistTitle)','description':'my first playlist','userId':'#(user.id)','tracks':[]}
    * def playlist = response
    
    * def trackId = 1280165222
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And header Authorization = 'Bearer ' + token
    And request {'id': '#(trackId)'}
    When method POST
    Then status 200
    And match response contains{'id':'#notnull','title':'#notnull','duration':'#notnull','artist':'#notnull','artist':'#notnull'}
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And header Authorization = 'Bearer '+token
    And request {'id': '#(trackId)'}
    When method GET
    Then status 200
    And match response contains{'id':'#(playlist.id)','title':'#(playlistTitle)','description':'#notnull','userId':'#(user.id)','tracks':'#notnull'}
    
    Given path 'playlists/'+playlist.id+'/tracks'+trackId
		When method DELETE
		Then status 401