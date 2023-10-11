# Documentation for second release

This readme file aims to explain our reasoning for the decisions we have made for the second deliverable. 

## Functionality for second release

In the second deliverable, our goal was to expand the functionality of the app, while keeping all the functionality from the first release. We also prioritized code quality and having a high percentage of test-coverage. 

These were our goals for expansion: 

- Functionality for favoriting recipes
- Functionality for deleting recipes
- Search bar in mainscreen
- Functionality for undoing steps and ingredients in addRecipe-screen
- Pop-up when trying to return to mainscreen from addRecipe-screen if the recipe is not completed
- Tests for all parts of the code-base, with high coverage-percentage
- Plugins for evaluating code quality
- Hashing of passwords for security

We are pleased with being able to implement every one of our golas, except one. Due to time constraints we were not able to implement hashing of passwords in this release. Therefore, this goal will be kept for the third deliverable.

### Screengrabs of current functionality

Following are screengrabs of the current functionality of the app in use: 

### Registering a new user:

![RegisterScreen](https://i.imgur.com/s9bpp7z.png)

### Logging in:

![Login screen](https://i.imgur.com/hMHZGDD.png)

### Mainscreen:

![Mainscreen](https://i.imgur.com/Ojy8xZQ.png)

### Adding a new recipe:

![Addrecipe](https://i.imgur.com/aoiwNPQ.png)

### Viewing a recipe:

![Recipe](https://i.imgur.com/7zn5XQL.png)

### Searching 

![Search](https://i.imgur.com/vhrTvLk.png)

Functionality for favorites can be found in both the mainscreen and the recipescreen.
## Workflow and issues in second sprint

The following paragraphs aims to explain our workflow and issues during the second sprint.

### Workflow with git

Our workflow during this second sprint is built higly upon the workflow we developed during the first. Firstly, we made development tasks as issues in gitlab, trying to give them suitable names and labeling them with labels explaining what they were concerning. In relevant cases, we also labled issues with an associated userstory. By labeling them, the issues get sorted in our issue boards, providing a good overview of the current state of the project. We then made branches from each issue, which we worked in locally. Here we tried to pull often, so that the brach stays up to date. When the issue was completed we always pulled from the remote repository, and then merged the master into the brach locally, so that we could resolve any potensial merge conflicts locally before pushing to gitlab. In gitlab, we then made merge request where we would describe the changes made and assigned another group member to review the changes before completing the merge.

### Pair programming 

One of the requirements for the second deliverable was to implement pair programming, a process where to persons work on the same issue on the same computer. We chose to use pair programming to solve some of the more difficult tasks during the second deliverable. This allowed us to discuss the best way to solve the problems with a partner, leading to better solutions overall. For example, when deciding what would be the optimal way to save data, being able to discuss this with a partner was essential. In addition, pair programming also ensured better code quality and more readable code. We definetly think that using pair programming during the second sprint has improved both our workflow and product.

### Scrum workflow

We had one three week long sprint, equivalent to the duration of the second deliverable.Due to this beeing a course, and not a full time job, we had stand-up once a week, on monday at 8. At stand up, we presented what we had done, our plans, issues we had encountered and also the amount of work we had in other subjects the coming week, which could limit our ability to work on the project. In stand-up, we made plans of which issues each member the members should work on in the coming week, and planned who were going to pair-program what. 

### Issues encountered in second sprint

We encountered som issues in changing filehandling to the JSON format. Since we had filehandling with .csv and .ser files, we had to change the way we handled files, so that they could be saved as .json files. This lead to a lot of repeated code in the file module, breaking the DRY principle. Because of this, we made a fileUtil class, which both RecipeFileHandler and UserFileHandler could inherit from. When saving profiles to file, we encountered recursion problem, due to relations between recipes and users. We fixed this by saving the author name instead of the author object in the individual Recipe objects. 

## Choices made for second release

### Checkstyle and Spotbugs

In the second deliverable, we were supposed to implement code quality tools. We chose to use the maven spotbugs plugin as a tool for revealing bugs not found by testing and maven checkstyle plugin as a tool to assess the formatting of our code. 

For spotbugs, we chose to exclude some bugs, the same as in the todo-list example, since one of these gave us a false positive.  
For checkstyle, we went with googles format because it is a good starting point, and changed the indentation level from 2 to 4 due to the group's preferences. 

We chose to set failOnError and failOnViolation to false, because we wanted to be able to run the project, even with some small bugs and checkstyle violations that were not critical to the functionality of the app. 

### Testing 

In order to be well prepared for further expansion of the application in the last deliverable, we wanted to have a high percentage of test coverage already in release 2. In order to do so, we used a few different tools. In the core and file tests, we used the JUnit tests, while we in the ui controller tests used TestFX. For the controllers, we also used mockito to mock other classes, for example filehandlers, so that the tests would focus strictly on the controllers.

We tried to test every aspect of application as it is rigth now, and plan to expand the testing when implementing the rest of the functionality. 

## Other relevant documentation

There is documentation about structure, requirements and dependencies [here](../../readme.md).  
There is documentation about the the app in general, core features in the finalized product and user stories with sequence diagrams [here](../../group-project/README.md).

## Example data

To have some example recipes and users to run the project with, we have run the project for a while and added the files in the [exampledata](./exampledata/) directory. Move the files from the directory to the position "user.home" on your computer, and the app can use them as example data.  
Logininfo for example users:  
**Username:** Profile1 , **Password:** Password1  
**Username:** Exampleuser1 , **Password:** Exampleuser1Pass  
