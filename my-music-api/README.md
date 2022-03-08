**My Music API**

My Music API is a SpringBoot/Java application that consumes Deezer open API and lets you create and edit your music playlists with your desired tracks.

To run My Music API, you'll need to have Docker installed on your machine, version 17.03.1-ce-rc1-win3 (10625) or superior.
Prior to run the application container, you will need a mysql database container running on your machine. Use the follow command in you desired command prompt to run the mysql container with the right configuration:

*docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql:8*

Once the mysql database container is up and running, you can run the My Music API docker container to run the application on your machine with the command: 

*docker run lucastellaroli/mymusicapi:latest*

For information about API's basic use and requests, you can reffer to the swagger documentation: 
open swagger UI live demo (*https://petstore.swagger.io/?_ga=2.221756827.2018432814.1644948099-1563697491.1631281809*), change the document to "https://lucas-tella.github.io" and hit EXPLORE. The My Music API swagger documentation is now ready to use.

For further information about this API, reffer to the extended documentation file **my-music-api.documentation.pdf** inside the documentation folder in this repository.
