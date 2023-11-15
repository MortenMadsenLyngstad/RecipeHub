# Our app: RecipeHub

## What is RecipeHub?

RecipeHub is a social media platform where you can make and share food recipes, while also exploring recipes posted by others. When you make a recipe, it will be saved in your profile, making RecipeHub a helpful tool for those who enjoy making food. A feed of all recipes posted on the app also gives the user the oppertunity to explore others new and exciting recipes. All in all, RecipeHub is applicable in several ways by having the oppertunity for saving, sharing and exploring new recipes, which makes it the perfect app for those with an interest for cooking.

![Mainscreen](https://i.imgur.com/yoJq5jO.png)

## Core feratures for final finished product

- **Login and registration**
  - When you start the app you have to log in, or sign up if you don't already have an account
  - For the first deliverable, this prosess will just require a username and a password
- **Recipe feed with sorting functionality**
  - On the main page of the app, there is a feed of recipes that have been posted on the app
  - You can choose to show all recipes ever posted, the ones posted by yourself or your favorites. Something you can change between with simple buttons
  - The recipe feed is also interactive, in the sense of having the possibility to read more about a given recipe by clicking on 'Read More'
- **In depth information about each recipe**
  - By clicking on 'Read More' for a given recipe, you will be taken to a new page where you get an in depth description of the recipe
  - The information given is for example who made the recipe, ingredients, preperation steps and more
- **Functionality for adding recipes**
  - The app takes you to a step for step prosess of making a recipe
  - You will have to give the nessecary information about a recipe, which includes name, ingredients, preperation steps, etc.
  - When the recipe is made, it will be saved in you profile and show up in the feed of recipes, both as a part of all recipes and the profiles recipes
- **Functionality for changing between different pages**
  - Naturally you will always be able to go back and fouth through the different pages of the app
- **Favorites**
  - You can choose recipes you want to mark as your favorites
  - The main recipe feed can be sorted so that it only shows your favorites
- **Reviews**
  - You can review a recipe by giving it a rating
  - These reviews shows up when viewing the recipe
- **Searh bar**
  - You can search for a given recipe based on it's name
  - The feed updates as you search

## User Stories

### User story 1: Saving recipe

As a home cook, you want to save a new recipe that you've made so that you can look at it at a later occation.

The user is in need of an application which can store their new recipe for a later date. Information about the recipe which needs to be stored inclues a name, description, ingredients with amounts, preperation steps and the number of portions the ingredient amounts is ments for. The process of writing the recipe should be easy and result in a recipe written in typical format, which the user can view when they wish.


#### Important to be able to see

- How the recipe is turning out while writing
- How the recipe turned out when done
- Where you can find the recipe

#### Important to be able to do

- Register or log in, so that the recipe can be saved in the profile
- Write a new recipe with all its information, which fits a typical recipe format
- Read a previously made recipe


#### Sequence diagram for user story 1:
![User story 1 sequence diagram](https://i.imgur.com/v9f7zCa.png)


### User story 2: Finding new recipes

As a student, you want to explore different recipes so that you can get inspiration for what you should have for dinner.

The user is in need of an application where they can easily find new recipes to make. It should be easy to scroll through the recipes, so that the user can find something they want to make. However, one should also be able to get in depth information about each recipe, so that one could just follow the recipe.


#### Important to be able to see

- Several recipes listed for the user to explore
- In depth information about each recipe (ingredients, preperation steps, etc.)

#### Important to be able to do

- Scroll through a list/feed of recipes
- Click on a recipee to get in depth information about it (ingredients, preperation steps, etc.)


#### Sequence diagram for user story 2:
![User story 2 sequence diagram](https://i.imgur.com/B1JCTbn.png)



### User story 3: Collecting your favorite recipes

As a home cook, you want to mark a recipe you discovered as one of your favorites so that you can go back to it at a later occasion.

The user is in need of an application where they can easily mark a recipe as a favorite. It should be easy to find this recipe at a later date, and the user should be able to see all their favorites. In addition, it should be visible which recipes are marked as favorites.


#### Important to be able to see

- All of the user's favorite recipes listed for the user to explore
- Which recipes are marked as favorites
- Where you can find your favorite recipes

#### Important to be able to do

- Scroll through a list/feed of your favorite recipes
- Search for specific recipes among you favorites
- Mark a recipe as a favorite



#### Sequence diagram for user story 3:
![User story 3 sequence diagram](https://i.imgur.com/CcpTG1k.png)


### User story 4: Removing a recipe

As a food enthusiast, you want to remove a recipe you've made so that you can make a new improved one.

The user is in need of an application where they can easily remove a recipe they've made. It should be obvious how to remove a recipe, and they should be asked if they are sure they want to remove it. Other users should not be able to remove a recipe that is not theirs.

#### Important to be able to see

- The button that removes a recipe
- A confirmation message when removing a recipe
- That the recipe is removed

#### Important to be able to do

- Remove a recipe
- Confirm the removal of a recipe
- Cancel the removal of a recipe


#### Sequence diagram for user story 4:
![User story 4 sequence diagram](https://i.imgur.com/BmLDxPz.png)


### User story 5: Scaling a recipe

You have found the recipe you want to make, it has a lot of ingredients and you want to make it for more people than the recipe is made for. You want to scale the recipe so that you can make it for the amount of people you want, without having to calculate the amount of ingredients yourself.

#### Important to be able to see

- Buttons to add or subtract the amount of portions
- Field for writing the amount of portions you want to make
- The amount of portions the recipe is made for
- The amount of ingredients needed for the amount of portions you want to make

#### Important to be able to do

- Add and remove portions
- Type in the amount of portions
- Rate and favourite the recipe without making scaled versions of it

#### Sequence diagram for user story 5:
![User story 5 sequence diagram](https://i.imgur.com/t3QXj1S.png)

### User Story 6: Reviewing a recipe

As a food enthusiat, you have tried to make one of the recipes you found at recipehub. You are really impressed and wants to share your experiece with other users.

The user is in need of an application where they can easily give reviews as well as reading other reviews. It should be obvious how to add a review and the users should get feedback that shows that if they click on the rating stars they will add a review. To give the reviews credibility, the author of a recipe should not be able to review its own recipe and a user should not be able to give review more than one time.

#### Important to be able to see

- The rating stars and average rating that shows how other user has reviewed this recipe
- Read the reviews other users have written

#### Important to be able to do

- Create a review
- Read other reviews

#### Sequence diagram for user story 6:
![User story 6 sequence diagram] (https://i.imgur.com/KFvzoZg.png)
