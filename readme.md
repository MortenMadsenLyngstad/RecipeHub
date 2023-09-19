# Project Description

This project is an application with both front-end and back-end. The project is structured into three main modules: Core, File, and UI.

- The Core module contains the main logic of the application.
- The File module is responsible for file handling.
  - The files are stored in user.home
- The UI module contains all the user interface related code.

## Requirements

- Java version: 17.0.5
- Maven version: 3.9.4
- TestFX version 4.0.16-alpha
- Hamcrest version 2.2
- JaCoCo version 0.8.7
- Mockito version 5.5.0

## Dependencies

- TestFX
- Maven
- JaCoCo
- Mockito
- Hamcrest
- FontAwesomeFX-FontAwesome

## Instructions

To run the project, navigate to the group-project file and use the command `mvn clean install`. After the build is successful, you can navigate to the ui-directory and run the application using the command `mvn javafx:run`. 
To run the tests, use the command `mvn test` in the group-project directory. This will run all the tests in the project. 
To generate a test coverage report, use the command `mvn jacoco:report`. The report can be found in the target/site/jacoco/index.html file.

## Directory Structure
    
```
└───group-project
    ├───core
    │   ├───src
    │       ├───main
    │       │   └───java
    │       │       └───core
    │       │           -- The Profile- Recipe- and RecipeLibrary-classes in this package are responsible for the main logic of the application.
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
                               


