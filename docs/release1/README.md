# Documentation for first release

This readme file aims to explain our reasoning for the decisions we have made for the first deliverable

## Functionality for first release

In the first deliverable, our goal was to have a working app, with the following functionalities:   

- Logging in and registering a new user. Also logging out
- Saving user info to file
- Overview of all the recipes saved on the device, and all the users own recipes
- Adding a new recipe
- Saving recipes to file
- Viewing a single recipe
- Tests for core logic
- Test for login

We are pleased with beeing able to implement all these functionalities in the first release, and think the deliverable we have completed is a good starting point for the next iteration. 

### Screengrabs of current functionality

Following are screengrabs of the current functionality of the app in use: 

### Registering a new user:

![RegisterScreen](https://i.imgur.com/s9bpp7z.png)

### Logging in:

![Login screen](https://i.imgur.com/hMHZGDD.png)

### Mainscreen:

![Mainscreen](https://i.imgur.com/WausjGQ.png)

### Adding a new recipe:

![Addrecipe](https://i.imgur.com/2TuvtHQ.png)

### Viewing a recipe:

![Recipe](https://i.imgur.com/UGLmlRX.png)

## Workflow and issues in first sprint

The following paragraphs aims to explain our workflow and issues during the first sprint.

### Workflow with git

We started with the modular template, and changed this on a single computer to avoid conflicts. After we had removed the old files, and changed some names in the template.  
Then we created a branch for every group member. We later realized, during the lecture on git, that we should have made branches connected to issues. We changed to this approach in the second half of the sprint. At the same point, we changed the structure of our commit-messages to what we had learned in the lecture.  
For the first sprint, we had one milestone, which was the first deliverable. We connected all our issues to the milestone, made labels to mark what the issues were concerning, and added tasks to each issue. 

### Scrum workflow

Due to this beeing a course, and not a full time job, we had stand-up once a week, on monday at 8. At stand up, we presented what we had done, our plans, issues we had encountered and also the amount of work we had in other subjects the coming week, which could limit our ability to work on the project. 

### Issues encountered in first sprint

During the first sprint we encoutered some issues.  

First we had issues with making the template work as intended, but after some tinkering with the pom.xml files, and changing some names and versions, it worked as intended. 
Next we struggled with building the project from ground-up, since nearly every part we made were dependent on another class which were being created in another branch. This got easier after some time, when we could merge some branches into the master, and share teh code with the others.  
Lastly we struggled with the plugins for JaCoCo and Mockito, this took a while to fix, but we learned a lot about building with pom.xml files on the way.

All in all we would say that the issues we encountered in the first sprint gave us the opportunity to learn a lot about the development process.


## Choices made for first release

We have chosen a modular template, to give a better overview of the different parts of the project, and to simplify the process for the two other deliverables.   

In the first deliverable, we chose to make the app single device, with the possibility of making multiple users on the device. We plan to make it a multi-device app in the third deliverable. In this iteration, we save information locally in .csv and .ser files in the user.home location.  


## Other relevant documentation

There is documentation describing the project at the `root level` about structure, requirements and dependencies.  
There is documentation about the core features in the finalized product and the app in general inside `group-project`.

## Example data

To have some example recipes and users to run the project with, we have run the project for a while and added the files in the [exampledata](./exampledata/) directory. Move the files from the directory to the position "user.home" on your computer, and the app can use them as example data.  
Logininfo for example users:  
**Username:** Exampleuser01, **Password:** Password01  
**Username:** Exampleuser02, **Password:** Password02  
