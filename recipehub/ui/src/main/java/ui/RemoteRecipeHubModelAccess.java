package ui;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import core.Profile;
import core.Recipe;
import core.RecipeHubModel;
import core.RecipeLibrary;

public class RemoteRecipeHubModelAccess implements RecipeHubModelAccess {

    private final URI endpointBaseUri;

    private static final String APPLICATION_JSON = "application/json";

    private static final String ACCEPT_HEADER = "Accept";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private Gson gson;

    private RecipeHubModel recipeHubModel;

    public RemoteRecipeHubModelAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.recipeHubModel = getRecipeHubModel();
        
    }

    private RecipeHubModel getRecipeHubModel() {
        if (recipeHubModel == null) {
            HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .GET()
                    .build();
            try {
                final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                        HttpResponse.BodyHandlers.ofString());
                this.recipeHubModel = gson.fromJson(response.body(), RecipeHubModel.class);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return recipeHubModel;
    }

    @Override
    public Recipe getRecipe(String name) {
        System.out.println("getRecipe(String name) :" + recipeUri(name));

        Recipe oldRecipe = this.recipeHubModel.getRecipe(name);
        if (oldRecipe == null || (!(oldRecipe instanceof Recipe))) {
            HttpRequest request = HttpRequest.newBuilder(recipeUri(name))
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .GET()
                    .build();
            try {
                final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                        HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                System.out.println("getRecipe(" + name + ") response:" + responseBody);

                Recipe recipe = gson.fromJson(responseBody, Recipe.class);
                this.recipeHubModel.addRecipe(recipe);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return oldRecipe;
    }

    @Override
    public RecipeLibrary getRecipeLibrary() {
        System.out.println("getRecipeLirary() :" + recipeLibraryUri());

        try {
            HttpRequest request = HttpRequest.newBuilder(recipeLibraryUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .GET()
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            RecipeLibrary recipeLibrary = gson.fromJson(responseBody, RecipeLibrary.class);

            return recipeLibrary;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        try {
            HttpRequest request = HttpRequest.newBuilder(recipeLibraryUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .DELETE()
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            System.out.println("removeRecipe(Recipe recipe) response:" + responseString);
            Boolean success = gson.fromJson(responseString, Boolean.class);
            if (success) {
                recipeHubModel.removeRecipe(recipeHubModel.getRecipe(recipe.getName()));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addRecipe(Recipe recipe) {
        try {
            String json = gson.toJson(recipe);
            HttpRequest request = HttpRequest.newBuilder(recipeLibraryUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                    .POST(BodyPublishers.ofString(json))
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            System.out.println("addRecipe(Recipe recipe) response:" + responseString);
            System.out.println(recipeLibraryUri());
            Boolean success = gson.fromJson(responseString, Boolean.class);
            if (success) {
                recipeHubModel.addRecipe(recipe);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsRecipe(Recipe recipe) {
        return getRecipeHubModel().containsRecipe(recipe);
    }

    @Override
    public Profile getProfile(String username) {
        System.out.println("getProfile(String username) :" + profileUri(username));

        Profile oldProfile = this.recipeHubModel.getProfile(username);
        if (oldProfile == null || (!(oldProfile instanceof Profile))) {
            HttpRequest request = HttpRequest.newBuilder(profileUri(username))
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .GET()
                    .build();
            try {
                final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                        HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                System.out.println("getProfile(" + username + ") response:" + responseBody);

                Profile profile = gson.fromJson(responseBody, Profile.class);
                this.recipeHubModel.addProfile(profile);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return oldProfile;
    }

    @Override
    public void addProfile(Profile profile) {
        System.out.println("addProfile(Profile profile) :" + profilesUri());
        try {
            String json = gson.toJson(profile);
            HttpRequest request = HttpRequest.newBuilder(profilesUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                    .PUT(BodyPublishers.ofString(json))
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            Boolean success = gson.fromJson(responseString, Boolean.class);
            if (success) {
                recipeHubModel.putProfile(profile);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Profile> getProfiles() {
        System.out.println("getProfiles() :" + profilesUri());

        try {
            HttpRequest request = HttpRequest.newBuilder(profilesUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .GET()
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            Type listType = new TypeToken<List<Profile>>() {
            }.getType();
            List<Profile> profiles = gson.fromJson(responseBody, listType);

            return profiles;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsProfile(Profile profile) {
        return getRecipeHubModel().containsProfile(profile);
    }

    private String uriParam(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private URI recipeUri(String name) {
        return endpointBaseUri.resolve("recipelibrary/").resolve(uriParam(name));
    }

    private URI recipeLibraryUri() {
        return endpointBaseUri.resolve("recipelibrary");
    }

    private URI profileUri(String username) {
        return endpointBaseUri.resolve("profiles/").resolve(uriParam(username));
    }

    private URI profilesUri() {
        return endpointBaseUri.resolve("profiles");
    }

    @Override
    public Hashtable<String, String> getUserInfo() {
        List<Profile> profiles = getProfiles();
        Hashtable<String, String> userInfo = new Hashtable<>();
        for (Profile profile : profiles) {
            userInfo.put(profile.getUsername(), profile.getHashedPassword());
        }
        return userInfo;
    }

    @Override
    public void writeProfiles(List<Profile> profiles) {
        for (Profile profile : profiles) {
            recipeHubModel.putProfile(profile);
        }
    }

    @Override
    public void writeProfile(Profile profile) {
        this.addProfile(profile);
    }

    @Override
    public void writeRecipe(Recipe recipe) {
        recipeHubModel.addRecipe(recipe);
    }

    public static void main(String[] args) {
        RecipeHubModelAccess recipeHubModelAccess;
        try {
            recipeHubModelAccess = new RemoteRecipeHubModelAccess(new URI("http://localhost:8080/recipehub"));
            System.out.println(recipeHubModelAccess.getRecipeLibrary());
            System.out.println(recipeHubModelAccess.getProfiles());
            Profile profile = new Profile("Username3", "Password3");

            recipeHubModelAccess.addProfile(profile);
            System.out.println(recipeHubModelAccess.getProfiles());

            Recipe recipe = new Recipe("Recipe3", 2, profile);
            recipeHubModelAccess.addRecipe(recipe);
            System.out.println(recipeHubModelAccess.getRecipeLibrary());
            recipeHubModelAccess.removeRecipe(recipeHubModelAccess.getRecipe("recipe2"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
