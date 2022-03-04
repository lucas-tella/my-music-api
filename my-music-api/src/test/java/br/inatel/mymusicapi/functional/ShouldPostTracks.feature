Feature: Adding Track to Playlist

Background: 
		* url baseUrl
		* def random = function(){return java.lang.System.currentTimeMillis()}
		* def userEmail = 'qa.playlist'+random()+'@test.com'
		* def playlistTitle = 'title'+random()

Scenario: Should register a new user and a new playlist and add a track to it
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
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 200
    And match response contains{'id':'#notnull','title':'#notnull','duration':'#notnull','artist':'#notnull','artist':'#notnull'}
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method GET
    Then status 200
    And match response contains{'id':'#(playlist.id)','title':'#(playlistTitle)','description':'#notnull','userId':'#(user.id)','tracks':'#notnull'}
    
Scenario: Should not add a track to a playlist without authentication
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
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 200
    And match response contains{'id':'#notnull','title':'#notnull','duration':'#notnull','artist':'#notnull','artist':'#notnull'}
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And request {'id': '1280165222'}
    When method GET
    Then status 401

Scenario: Should not add a track with invalid playlist id
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
    
    Given path 'playlists/'+0+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 404
    And match response contains {'status':404,'message':'Playlist 0 not found.'}
    
    Given path 'playlists/'+'9999999'+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 404
    And match response contains {'status':404,'message':'Playlist 9999999 not found.'}
    
    Given path 'playlists/'+'asd'+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 400
    
    Given path 'playlists/'+'123asd456'+'/tracks'
    And request {'id': '1280165222'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 400

Scenario: Should not add a track with invalid track id
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
    * def playlistId = playlist.id
    
    * def expectedErrorMessage = 'Track 123asd456 not found or already added to playlist '+ playlistId + '.'
    
    Given path 'playlists/'+playlist.id+'/tracks'
    And request {'id': '123asd456'}
    And header Authorization = 'Bearer ' + token
    When method POST
    Then status 400
    And match response contains {'status':400,'message':'#(expectedErrorMessage)'}
    