# Documentation for second release

This readme file aims to explain our reasoning for the decisions we have made for the second deliverable. 

## Functionality for second release

In the second deliverable, our goal was to expand the functionality of the app, while keeping all the functionality from the first release. We also prioritized having a high percentage of test-coverage. 

These were our goals of expansion: 

- Favoriting recipes
- Deleting recipes
- Search bar in main screen
- Undoing steps and ingredients in addRecipe
- Pop-up for return to mainscreen from addRecipe if recipe not completed
- Tests for all parts of teh code-base, with high coverage-percentage
- Plugins for evaluating code quality
- Hashing of passwords for security

We are pleased with being able to implement every one of our golas, except one: do to time constraints we were not able to implement hashing of passwords in this release. 

### Screengrabs of current functionality

Following are screengrabs of the current functionality of the app in use: 

### Registering a new user:

![RegisterScreen](https://i.imgur.com/s9bpp7z.png)

### Logging in:

![Login screen](https://i.imgur.com/hMHZGDD.png)

### Mainscreen:

![Mainscreen](link)

### Adding a new recipe:

![Addrecipe](link)

### Viewing a recipe:

![Recipe](link)


## Workflow and issues in second sprint

The following paragraphs aims to explain our workflow and issues during the second sprint.

### Workflow with git

During the second sprint, we had development tasks as issues in gitlab,labeled with what they were concerning, and in relevant cases, linked to a userstory. We made branches from each issue, and then merged the branch with the master after the issue was completed. We had one three week long sprint, equivalent to the duration of the second deliverable. 

### Pair programming 

One of the requirements for the second deliverable was to implement pair programming, a process where to persons work on the same issue on the same computer. We chose to use pair programming to solve some of the more difficult tasks during the second deliverable, and to secure better code quality. The main way we used pair programming to ensure good code quality was to have another student read through the implemented code, and suggesting improvement and pointing out possible weakpoints and bugs. We think that using pair programming during the second sprint has given us the ability to improve 

### Scrum workflow

Due to this beeing a course, and not a full time job, we had stand-up once a week, on monday at 8. At stand up, we presented what we had done, our plans, issues we had encountered and also the amount of work we had in other subjects the coming week, which could limit our ability to work on the project. In stand-up, we made plans of which issues each member the members should work on in the coming week, and planned who were going to pair-program what. 

### Issues encountered in second sprint

We encountered som issues in changing filehandling to the JSON format. Since we had filehandling with .csv and .ser files, we had to change the way we handled files, so that they could be saved as .json files. This lead to a lot of repeated code in the file module, breaking the DRY principle. Because of this, we made a fileUtil class, which both RecipeFileHandler and UserFileHandler could inherit from. When saving profiles to file, we encountered recursion problem, due to relations between recipes and users. We fixed this by saving the author name instead of the author object in the individual Recipe objects. 

## Choices made for second release

### Checkstyle and Spotbugs

In the second deliverable, we were supposed implement code quality tools, we chose to use the maven spotbugs plugin as a tool for revealing bugs not found by testing. We chose the maven checkstyle plugin as a tool to assess the formatting of our code. 

For spotbugs, we chose to exclude some bugs, the same as in the todo-list example, since one of these gave us a false positive.  
For checkstyle, we went with googles format, assuming it is a good starting point, and changing the indentation level from 2 to 4 due to the groups preferences. 

We chose to set failOnError and failOnViolation to false, because we wanted to be able to run the project, even with some small bugs and checkstyle violations that were not critical to the functionality of the app. 

### Testing 

In order to be well prepared for further expansion of the application in the last deliverable, we wanted to have a high percentage of test coverage already in release 2. In order to do so, we used a few different tools. In the core and file tests, we used the JUnit tests, and in the ui tests we used mockito to mock the filehandling, and TestFX to the all the different controllers. 

We tried to test every aspect of application as it is rigth now, and plan to expand the testing when implementing the rest of the functionality. 

## Other relevant documentation

There is documentation about structure, requirements and dependencies [here](../../readme.md).  
There is documentation about the the app in general, core features in the finalized product and user stories [here](../../group-project/README.md).
