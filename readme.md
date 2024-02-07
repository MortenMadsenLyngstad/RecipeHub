# Project Description

[![wakatime](https://wakatime.com/badge/user/2dd907da-de6a-4666-be49-93df180978c4/project/ccb900b2-aceb-41eb-8ff6-22c27b3823fc.svg)](https://wakatime.com/badge/user/2dd907da-de6a-4666-be49-93df180978c4/project/ccb900b2-aceb-41eb-8ff6-22c27b3823fc)

This project is an application with both front-end and back-end. It is structured into four main modules: core, file, springboot and ui.

- The core module contains the main logic of the application.
- The file module is responsible for file handling.
    - The files are stored in user.home
- The springboot module is responsible for the Rest API.
- The ui module contains all the user interface related code.


## Package diagram for RecipeHub project

![Diagram](https://i.imgur.com/eoW6Ppw.png)

## Class diagram for core module

![Diagram](https://i.imgur.com/083smoE.png)

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
- Checkstyle version 3.2.0
- Spotbugs 4.7.3.6

## Dependencies

These are the dependencies for the project:

- JavaFX
- TestFX
- JUnit
- GSON
- Maven
- JaCoCo
- Mockito
- Hamcrest
- FontAwesomeFX-FontAwesome
- Checkstyle
- Spotbugs
- Log4j
- Springboot framework


## Instructions

- To run the project, navigate to the recipehub directory and use the command `mvn clean install`.
- After the build is successful, you can navigate to the ui-directory and run the application using the command `mvn javafx:run`.
- To run the tests, use the command `mvn test` in the recipehub directory. This will run all the tests in the project.
- To generate a test coverage report, use the command `mvn jacoco:report`. The report can be found in the target/site/jacoco/index.html file.
- To pack the project as an app use the command `mvn javafx:jlink -f ui/pom.xml`. Then use the command `mvn jpackage:jpackage -f ui/pom.xml`. Follow the instructions from your computer, and the application will appear in your applications.
- For instructions on how to run the project with the Rest API, see the [restapi.md](./docs/release3/restapi.md) file.

## Directory Structure

The following figure shows the directory structure of the project:


```
└───recipehub
    ├───core
    │   └───src
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
    │   └───src
    │       │───main
    │       │   └───java
    │       │       └───file
    │       │           -- The file-handling for the project is located in this file.
    │       └───test
    │           └───java
    │               └───file
    │                    -- The testfiles for the file module are located in this file
    │
    ├───springboot
    │   └───restserver
    │       └───src
    │           │───main
    │           │   └───java
    │           │       └───springboot
    │           │           └───restserver
    │           │               -- The Rest API for the project is located in this file.
    │           └───test
    │               └───java
    │                   └───springboot
    │                       └───restserver
    │                           -- Tests for the restserver module are located in this file.
    └───ui
        └───src
            ├───main
            │   ├───java
            │   │   └───ui
            │   │       -- The controllers used in the project is located in this file.
            │   └───resources
            │       ├───ui
            │       │    -- This is where the fxml-files and style.css for the UI are located.
            │       │
            │       └───images
            │            -- This is where the images used in the UI are located.
            └───test
                └───java
                    └───ui
                        -- The testfiles for the ui module are located in this file.
```
