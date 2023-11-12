# RecipeHub API

## Running project with Rest API

First you need to navigate to the recipehub folder and use the command `mvn clean install`.

Then you need to start the server, which you can either do by using the command `mvn spring-boot:run -f springboot/restserver/pom.xml` from the recipehub folder or navigate to the restserver folder by using `cd springboot/restserver/` and then use the command `mvn spring-boot:run`.

To now run the application with it using the API, you can use the command `mvn javafx:run -P remoteapp -f ui/pom.xml` from the recipehub folder or navigate to the ui folder by using `cd ui/` and then use the command `mvn javafx:run -P remoteapp`.

## Overview

### Base endpoint

No methods to base endpoint.

### recipelibrary

http://localhost:8080/recipehub/recipelibrary

#### Methods

- Get - retrieves the recipelibrary with all recipes from the server
  - URI: host:port/recipehub/recipelibrary - http://localhost:8080/recipehub/recipelibrary
  - Parameters: None
  - Available: Springboot
  - Returns JSON with the RecipeLibrary

```json
{
  "recipes": [
    {
      "steps": [
        "step1"
      ],
      "portions": 2,
      "name": "recipe1",
      "description": "This is a description",
      "ingredients": {
        "Ingredient 1": 5.0
      },
      "ingredientUnits": {
        "Ingredient 1": "g"
      },
      "authorUsername": "Username1",
      "isSaved": true,
      "reviewList": []
    },
    {
      "steps": [
        "Step 1"
      ],
      "portions": 1,
      "name": "recipe2",
      "description": "This is a description",
      "ingredients": {
        "Ingredient 1": 3.0
      },
      "ingredientUnits": {
        "Ingredient 1": "g"
      },
      "authorUsername": "Username2",
      "isSaved": true,
      "reviewList": [
        {
          "rating": 4.0,
          "comment": "It was really good!",
          "username": "Username1"
        }
      ]
    }
  ]
}
```

- PUT - Creates a recipe if it does not exist or updates the existing recipe otherwise
  - URI: host:port/recipehub/recipelibrary - http://localhost:8080/recipehub/recipelibrary
  - Parameters:
    - Body - application/json; charset=UTF-8
    - Recipe object

```json
{
  "steps": [
    "step1"
  ],
  "portions": 2,
  "name": "recipe3",
  "description": "This is a description",
  "ingredients": {
    "Ingredient 1": 10.0
  },
  "ingredientUnits": {
    "Ingredient 1": "g"
  },
  "authorUsername": "Username3",
  "isSaved": true,
  "reviewList": []
}
```

  - Available: Springboot
  - Returns:
    - Content-Type: application/json
    - Json with boolean true on succes, false otherwise

```json
true
```

```json
false
```

- DELETE - Deletes the given recipe
  - URI: host:port/recipehub/recipelibrary - http://localhost:8080/recipehub/recipelibrary
  - Parameters:
    - Body - application/json; charset=UTF-8
    - Recipe object

```json
{
  "steps": [
    "step1"
  ],
  "portions": 2,
  "name": "recipe3",
  "description": "This is a description",
  "ingredients": {
    "Ingredient 1": 10.0
  },
  "ingredientUnits": {
    "Ingredient 1": "g"
  },
  "authorUsername": "Username3",
  "isSaved": true,
  "reviewList": []
}
```

- Available: Springboot
- Returns:
  - Content-Type: application/json
  - Json with boolean true on success, false otherwise

```json
true
```

```json
false
```

## profiles

http://localhost:8080/recipehub/profiles

### Methods

- Get - retrieves a list of all the profiles from the server
  - URI: host:port/recipehub/prfiles - http://localhost:8080/recipehub/profiles
  - Parameters: None
  - Available: Springboot
  - Returns JSON with the List<Profile>

```json
[
  {
    "username": "Username1",
    "hashedPassword": "B3572EE8E5AA2A347BFCE79286EA396D:4239A64E84894C5AF748F24CFD4CECF94039B3C1D445D03ADE662DF93A946D49",
    "recipeLibrary": {
      "recipes": []
    },
    "favorites": {
      "recipes": []
    }
  },
  {
    "username": "Username2",
    "hashedPassword": "7259641929E2A4152F9660C3A16DF7D2:D6095565B8DA44E391D611247FE06100B2032EFF78B5F78B9A17221636AD3133",
    "recipeLibrary": {
      "recipes": []
    },
    "favorites": {
      "recipes": []
    }
  }
]
```

- PUT - Creates a profile if it does not exist or updates the existing profile otherwise
  - URI: host:port/recipehub/profiles - http://localhost:8080/recipehub/profiles
  - Parameters:
    - Body - application/json; charset=UTF-8
    - Profile object

```json
{
  "username": "Username1",
  "hashedPassword": "B3572EE8E5AA2A347BFCE79286EA396D:4239A64E84894C5AF748F24CFD4CECF94039B3C1D445D03ADE662DF93A946D49",
  "recipeLibrary": {
    "recipes": []
  },
  "favorites": {
    "recipes": []
  }
}
```

- Available: Springboot
- Returns:
  - Content-Type: application/json
  - Json with boolean true on succes, false otherwise

```json
true
```

```json
false
```

## profiles/{username}

http://localhost:8080/recipehub/profiles/{username}

- Get - retrieves a profile from the server
  - URI: host:port/recipehub/prfiles/{username} - http://localhost:8080/recipehub/profiles/Username1
  - Parameters: None
  - Available: Springboot
  - Returns JSON with the profile

```json
{
  "username": "Username1",
  "hashedPassword": "B3572EE8E5AA2A347BFCE79286EA396D:4239A64E84894C5AF748F24CFD4CECF94039B3C1D445D03ADE662DF93A946D49",
  "recipeLibrary": {
    "recipes": []
  },
  "favorites": {
    "recipes": []
  }
}
```
