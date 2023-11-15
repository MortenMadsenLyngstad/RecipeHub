# Documentation for third release

This readme file is an overview over our work and project for realse 3.

Content:

- [New implementations for the third release](#new-implementations-for-the-third-release)
- [Workflow](#workflow)
- [Choices made for third release](#choices-made-for-third-release)
- [Issues encountered for third release](#issues-encountered-for-third-release)
- [Other relevant documentation](#other-relevant-documentation)
- [Example data](#example-data)

## New implementations for the third release

In the third deliverable our goal was centered around futher expanding the functionality of the app, while also implementing the Rest API and ensuring a high level of code quality. The Rest API was naturally a priority due to it's big role in this release, but we also ensured that we implemented new functionality that we wanted for the app. While expanding, we wanted to make sure that the code quality was high, both for new and old implementations.

These were our goals for expansion:

- Make pipelines to futher ensure code quality
- Hashing of passwords to improve security
- Functionality for scaling of portions
- Improve search algorithm
- Functionality for reviews with both rating and comments
- Make filehandling static
- Make Rest API and adapt the application to use it
- Implement Jpackage and Jlink
- Make new logo
- Change the colors in the app

Looking back on these goals we can see that we reached just about every single one. The only exception is making the filehandling static. After starting on this issue, we realized this would complicate our testing in ui, which utilizes Mockito. We decided that using our previous filehandling, which worked well with Mockito and in the project in general, was better than changing it all and having to give up our worked through tests. We would have had to not use Mockito, but decided that what we had was a better testing practise. We did however make the methods in FileUtil static, to make it more dynamic.

Other than that, we reached all our goals. The Rest API was implemented, and it was made so that one can decide whether to use the api or the normal filehandling. New functionality like the reviews and scaling of portions was implemented and inserted into the application. Futhermore, we spent time improving already existing functionality, like the search algorithm and passwords with hashing, and made some asthetic modifications. All this while also writing new tests and ensuring code quality.

### Screenshots of current functionality

Below you can find screenshots of the different screens in our app with a small explination of the functionality in each screen.

### Logging in

![Login screen](https://i.imgur.com/WJkGvEx.png)

#### Login screen functionality

This is the login screen. If a user has already created a profile, the user is able to log in. The user input needs to match one of the saved profiles, with both username and password (which is hashed), and the user is notified if no such user was found. If the user does not have a profile, the user can click on the hyperlink to sign up in the register screen.

### Registering a new user

![RegisterScreen](https://i.imgur.com/MuylG7f.png)

#### Register screen functionality

This is the register screen. Here a user can make a new profile by filling in username and password. The username and password must conform to certain standards set for the username and password, and the user will be notified if either of them are invalid. If the user already has a profile, he/she can click on the hyperlink to log in on the log in screen.

### Mainscreen

![Mainscreen](https://i.imgur.com/yoJq5jO.png)

#### Main screen functionality

This is the main screen of the application. Here the user will find a feed of recipes which can be sorted by either 'My recipes', 'All Recipes' or 'Favorites'. In addition to this sorting, the user can also use the searchbar to search for specific recipes. For each recipe, one can see the average rating of the recipe, mark it as a favorite and click on read more in order to see the recipe in full. By clicking on the '+ Add Recipe'-button the user can get to the add recipe screen, where they can add a new recipe, and by clicking on the 'Log Out'-button the user can log out and go back to the login screen.

### Adding a new recipe

![Addrecipe](https://i.imgur.com/Nm41Ivs.png)

#### Add recipe screen functionality

This is the add recipe screen. Here the user will go through a step by step process for making a recipe. During this process, the elements being added will be displayed in the window to the right. After making the recipe and clicking on the 'Back'-button, the user will return to the main screeen, and the recipe will be added. If the user tries to click the 'Back'-button before completing the recipe, the user will be warned that the recipe will not be saved.

### Viewing a recipe

![Recipe](https://i.imgur.com/90kbZdM.png)

#### Recipe screen functionality

This is the recipe screen. Here the user can see the in depth information of a given recipe. There is functionality for the scaling of portions, which will update the ingredient amount. You can also give a review by clicking on the star (if the recipe is not made by the logged in profile), favorite by clicking on the heart or delete by clicking on the trash can if the recipe was made by the logged in user.

![Review](https://i.imgur.com/xuh6VOI.png)

#### Adding a review

This is the pop up which shows up if the user wants to add a review. Here the user can give the recipe a rating from 1 to 5 stars and write a comment.

![Reviews](https://i.imgur.com/qe7BWnw.png)

#### Reviews

This is the bottom of the recipe screen where the user can find the reviews of the recipe. Here he/she can see who wrote the review, how many stars each user have given and the comments.

## Workflow

While working on our project, we had some general principles we tried to follow. Amongst others, these included writing tests for new functionality when implemented, writing javadoc comments clearifying the code and using descriptive variable names. The following paragraphs aims to futher explain our workflow during this sprint leading up to the third release.

### Scrum workflow

Our scrum workflow remaind mostly the same working towards the third release as it did for the second. We had one five week long sprint, equivalent to the duration of the third deliverable. Due to this being a course, and not a full time job, we had stand-up once a week, on monday at 08:15. At stand up, we presented what we had done, our plans, issues we had encountered and also the amount of work we had in other subjects the coming week, which could limit our ability to work on the project. Futhermore, we made plans for which issues the members should work on in the coming week, and planned potential pair programming. The stand ups also became an arena for sharing new ideas for the application, so that we could all discuss it together. Therefore, the weekly meeting also ensured that we were all aligned with our ideas for the app and the road forward, reducing disagreements within the group.

### Workflow with Git

This sprint's workflow with git is a continuation and improvement of the workflow established in previous sprints. We have continued to make development tasks as issues in gitlab, giving them names and labels establishing what they are concerning, and in relevant cases, labeling them with an assosiated userstory. This sprint however, we've also made sure to write more thourogh description, to elaborate on the issue. This includes clearifying the goal, background and benefits of the issue. This makes it easier for the others in the group to know what is going on in the project, and with the labels making the issues show up on our issue boards, it is easier to get an overview of the state of the project.

As previously, we then made branches from each issue and worked on it locally. Here we tried to pull often, in order to stay up to date with the remote master branch, but also push often in case anyone else wanted to see how things were going in our branch. When making changes, commit messages were made descriptive, often with header, body and footer for a more thorough explanation for what had been done. In the commit message footer, we also related the commits to the associated issues, and noted with 'Co-authored-by' if we were pair programming during the changes.

When the issue was complete, we tried to always make sure to pull from the remote repository in order to ensure that we were up to date and then potentially resolve any conflicts locally before pushing to the remote repository. In addition, we also tried to always run the tests (note that ), chekstyle, spotbugs and just check that the app ran. If our pipelines ever failed, we made sure to fix the problems, before proceeding.

When everything was good, and the pipelines ran fine, we made merge requests. Here we wrote a description about what changes was made. The other group member assigned as reviewer then had to look over the changes made and make a merge review. Here we looked for typos, unclear code, bugs, checkstyle errors and potential for optimalization. We also pulled down the brach locally to ensure that everything worked properly. We made both comments and suggestions to the code, which the other person then looked at. This process was then repeated until they were both satisfied with the code, resulting in the merge of the branch into the master branch.

### Pair programming

This sprint we continued to utilize pair programming, where two group members work on the same issue on the same computer. While one of us wrote the code, the other looked over everything that was written and came with suggestions. This helped us solve difficult problems and find better solutions, as well as ensuring a higher level of code quality. An example where we used pair programming is for the implementation of the Rest API. This was new for the whole group, so we thought pair programming this part would be a good idea. By using pair programming, we could discuss different ideas and how things should be implemented, but also build upon each other's understanding of how the Rest API should work. We think this made the process of implementing the Rest API go much smoother, while also resulting in a higher level of code quality.

## Decisions made for third release

The following paragraphs aims to explain some of the decisions we've made for the third release.

### Rest API

When it comes to the Rest API, several choices had to be made for how we wanted to implement it. We took inspiration from the todo list example, but had to decide how it would work best for our application. Therefore, we decided to scrap our implementation of a Service class in the springboot/restserver module and the implementation of a RecipeHubModel in our core module. These are both classes we made at first, but upon futher inspection in the context of our app, appeared to be redundent. After discussions within the group, we also came to the conclusion that they complicated the code base, making it more difficult to understand the implementation of the Rest API. With these classes not really adding any real value, neither when it comes to functionality or readability, we removed them.

Other than that, we also made the choice of having the Rest API only include methods our application would use. We could have added additional methods for the Rest API, which could maybe be used at a later stage, but decided to rather prioritize having a more compact Rest API which only includes what we need at this stage. In other words, we chose to remove excess methods for the Rest API.

### New functionality

Although we added several new functionalities for this third deliverable, such as the scaling of portions or reviews, we realised that our application already had quite a lot of functionalities. Therefore, in order to follow the principle of quality over quantity, we decided to not add an excessive amount of new functionality for this third deliverable, and rather focus on making sure that the once we have, had high quality. This meant that for example adding images, which was an idea that occured during this sprint, was de-prioritized. It also meant that we spent some time refactoring functionality we had implemented earlier, like the search bar or the recipe screen. We think this was a good choice when considering the position we found ourselves in after release 2, where we already had quite a lot of functionality.

### Indexing of recipes

During the sprint, we thought of indexing the recipes to avoid having to save the same recipes several times in several files. We made such an implementation, and it worked, but we realized that the implementation also had it's disadvantages. How we implemented the indexation required us to iterate through all the recipes in the app each time we wanted to load a profile's recipes, which would have a negative impact on the app's runtime. In addition, the implementation would make the usage of the core module individually cumbersome, while also making our codebase more difficult to understand. Because of these reasons, we decided to not implement the indexing of recipes, but rather stick to the implementation we had previously, prioritizing runtime and clearity over storage space.

However, this descision was made before having all new functionality added, which added new complexity to the app. This made us realize that the implementation we had settled on also ended up requiring iteration through all the data, in order to update all the recipes who were saved several places. With this in mind, despite the other disadvantaged that came with indexation, this would probably be something we would have looked back into if we had time. At the time, the decision we made seemed right, but upon futher inspection at a later stage of the development, this is something that could've reconsidered.

## Issues encountered for third release

The following paragraphs aims to explain some of the issues encountered during the third sprint.

### Eclipse Che

We encountered some issues when it comes to eclips che, more specifically that some tests that ran perfectly locally failed in eclipse che. This caused some of frustration, because the problems were not related to the logic, but rather the enviroment they were ran in. However, we managed to fix it at the end.

### Pipelines

We decided to make pipelines for our project in order to futher ensure higher code quality by having another layer of protection from having errors making it into the master branch. These pipelines work, and have been a great addition to our project, but we've encounteres a reoccurring problem. Sometimes jobs in the pipelines fail, and have to be re-ran for them to run succesfully. This is nothing more than a minor inconvinience, but still a problem nontheless. After talking to other groups with the same problem, we think it might be related to the runners we use for the pipelines.

## Other relevant documentation

Documentation about structure, requirements and dependencies can be found [here](../../readme.md).  
Documentation about the the app in general, core features in the finalized product and user stories with sequence diagrams can be found [here](../../recipehub/README.md).

## Example data

To have some example recipes and users to run the project with, we have run the project for a while and added the files in the [exampledata](./exampledata/) directory. Move the files from the directory to the position "user.home" on your computer, and the app can use them as example data.  
Logininfo for the example users:  
**Username:** TestUser , **Password:** Test1234
**Username:** Testuser2 , **Password:** Test1234
**Username:** Testuser3 , **Password:** Test1234
