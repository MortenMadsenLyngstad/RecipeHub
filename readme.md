# Project Description

This project is an application with both front-end and back-end. It is structured into three main modules: Core, File, and UI.

- The Core module contains the main logic of the application.
- The File module is responsible for file handling.
  - The files are stored in user.home
- The UI module contains all the user interface related code.

## Requirements

These are the requirements for the project:

- Java version: 17.0.5
- Maven version: 3.9.4
- JavaFX version: 17.0.2
- JUnit version: 5.7.2
- TestFX version 4.0.16-alpha
- Hamcrest version 2.2
- Mockito version 5.5.0
- JaCoCo version 0.8.7
- FontAwesomeFX-FontAwesome version 4.7.0-9.1.2

## Dependencies

These are the dependencies for the project:

- JavaFX
- TestFX
- JUnit
- Maven
- JaCoCo
- Mockito
- Hamcrest
- FontAwesomeFX-FontAwesome

## Instructions

- To run the project, navigate to the group-project file and use the command `mvn clean install`.  
- After the build is successful, you can navigate to the ui-directory and run the application using the command `mvn javafx:run`.  
- To run the tests, use the command `mvn test` in the group-project directory. This will run all the tests in the project.  
- To generate a test coverage report, use the command `mvn jacoco:report`. The report can be found in the target/site/jacoco/index.html file.

## Directory Structure

The following figure shows the directory structure of the project:

    
```
└───group-project
    ├───core
    │   ├───src
    │       ├───main
    │       │   └───java
    │       │       └───core
    │       │           -- The Profile- Recipe- and RecipeLibrary-classes are in this package.            
    │       │           -- They are responsible for the main logic of the application.
    │       └───test
    │           └───java
    │               └───core 
    │                   -- The testfiles for the core module are located in this file.
    │   
    ├───file
    │   ├───src
    │       └───main
    │           └───java
    │               └───file
    │                   -- The file-handling for the project is located in this file.
    │   
    └───ui
        ├───src
            ├───main
            │   ├───java
            │   │   └───ui
            │   │       └───controllers
            │   │           -- The controllers used in the project is located in this file.
            │   └───resources
            │       └───ui
            │           └───controllers
            │               -- This is where the fxml-files for the UI are located.
            └───test
               └───java
                   └───ui
                       └───controllers
                            -- The testfiles for the ui module are located in this file.
                               


