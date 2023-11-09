package ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * This class centralizes access to the REST API.
 * Makes it easier to support transparent use of a REST API.
 */
public class RemoteRecipeHubAccess implements RecipeHubAccess {

    private final URI endpointBaseUri;

    private static final String APPLICATION_JSON = "application/json";

    private static final String ACCEPT_HEADER = "Accept";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private Gson gson;

    /**
     * This constructor will set the base URI and initialize Gson object.
     *
     * @param endpointBaseUri - The base URI of the remote recipehub endpoint
     */
    public RemoteRecipeHubAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Retrieves the RecipeLibrary from the remote server.
     *
     * @return RecipeLibrary containing the recipes for the app
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public RecipeLibrary getRecipeLibrary() {
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

    /**
     * Removes the given recipe from the remote server.
     *
     * @param recipe - Recipe to be removed.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public boolean removeRecipe(Recipe recipe) {
        try {
            String json = gson.toJson(recipe);
            HttpRequest request = HttpRequest.newBuilder(recipeLibraryUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                    .method("DELETE", BodyPublishers.ofString(json))
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            Boolean success = gson.fromJson(responseString, Boolean.class);
            System.out.println("Removed recipe: " + success);
            return success;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Saves the given recipe to the remote server.
     *
     * @param recipe - Recipe to be saved.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public boolean saveRecipe(Recipe recipe) {
        try {
            String json = gson.toJson(recipe);
            HttpRequest request = HttpRequest.newBuilder(recipeLibraryUri())
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                    .PUT(BodyPublishers.ofString(json))
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            System.out.println(recipeLibraryUri());
            Boolean success = gson.fromJson(responseString, Boolean.class);
            System.out.println("Saved recipe: " + success);
            return success;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Saves the given profile to the remote server.
     *
     * @param profile - Profile to be saved.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public boolean saveProfile(Profile profile) {
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
            System.out.println("Saved profile: " + success);
            return success;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Retrieves a list of all profiles from the remote server.
     *
     * @return - List of all profiles.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public List<Profile> getProfiles() {
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

        } catch (IOException | InterruptedException | IllegalStateException e) {
            return null;
        }
    }

    /**
     * Generates the URI for accessing the RecipeLibrary on the remote server.
     *
     * @return URI for the recipe library.
     */
    private URI recipeLibraryUri() {
        return endpointBaseUri.resolve("recipelibrary");
    }

    /**
     * Generates the URI for accessing the profile with the given username on the remote server
     * 
     * @param username - String with the username
     * @return URI for the profile.
     */
    private URI profileUri(String username) {
        return endpointBaseUri.resolve("profiles/" + username);
    }

    /**
     * Generates the URI for accessing profiles on the remote server.
     *
     * @return URI for profiles.
     */
    private URI profilesUri() {
        return endpointBaseUri.resolve("profiles");
    }


    /**
     * Saves a list of profiles to the remote server.
     *
     * @param profiles - List of profiles to be saved.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public boolean saveProfiles(List<Profile> profiles) {
        for (Profile profile : profiles) {
            if (saveProfile(profile) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Loads a profile from the remote server based on the provided predicate.
     *
     * @param username - String with the username of the profile to load
     * @return Profile with the given username
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public Profile loadProfile(String username) {
        try {
            HttpRequest request = HttpRequest.newBuilder(profileUri(username))
                    .header(ACCEPT_HEADER, APPLICATION_JSON)
                    .GET()
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            Profile profile = gson.fromJson(responseBody, Profile.class);

            return profile;

        } catch (IOException | InterruptedException | IllegalStateException e) {
            return null;
        }
    }
}
