HttpServer
================

This file contains information that is required to get started with HttpServer.

## Type of Application
This is a pure **Console** based simple httpserver. This server handles **POST** request type only. And can be used to upload a file.

## System and Software Requirements

 * Java 7+
 * Maven 3

## Build
Use following command to create executable jar file:
 * mvn clean package
This will create executable jar file **HttpServer.jar** under target folder.

## Execute
Build result is an executable jar file, so it can easily be triggered using following command:
 * java -jar HttpServer.jar <<PORT>> <<Server directory>>
   e.g.
   java -jar HttpServer.jar 7777 E:\Storage

 * User CURL command to upload any file as follows:
   curl -w '%{response_code}\r\n' --request POST --data-binary @<<FILE_PATH>> http://localhost:7777/<<FILE_NAME>>