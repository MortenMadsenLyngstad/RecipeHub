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
import java.util.function.Predicate;

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

    /**
     * Removes the given recipe from the remote server.
     *
     * @param recipe - Recipe to be removed.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public void removeRecipe(Recipe recipe) {
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
            System.out.println("removeRecipe(Recipe recipe) response:" + responseString);
            Boolean success = gson.fromJson(responseString, Boolean.class);
            System.out.println("Removed recipe: " + success);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the given recipe to the remote server.
     *
     * @param recipe - Recipe to be saved.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public void saveRecipe(Recipe recipe) {
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
            System.out.println("putRecipe(Recipe recipe) response:" + responseString);
            System.out.println(recipeLibraryUri());
            Boolean success = gson.fromJson(responseString, Boolean.class);
            System.out.println("Saved recipe: " + success);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the given profile to the remote server.
     *
     * @param profile - Profile to be saved.
     * @throws RuntimeException if an error occurs during the HTTP request.
     */
    @Override
    public void saveProfile(Profile profile) {
        System.out.println("saveProfile(Profile profile) :" + profilesUri());
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
            System.out.println(responseString);
            Boolean success = gson.fromJson(responseString, Boolean.class);
            System.out.println("Saved profile: " + success);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
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

    /**
     * Generates the URI for accessing the RecipeLibrary on the remote server.
     *
     * @return URI for the recipe library.
     */
    private URI recipeLibraryUri() {
        return endpointBaseUri.resolve("recipelibrary");
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
    public void saveProfiles(List<Profile> profiles) {
        for (Profile profile : profiles) {
            saveProfile(profile);
        }
    }

    /**
     * Loads a profile from the remote server based on the provided predicate.
     *
     * @param predicate - Predicate that determines which profile to load.
     * @return First Profile that matches the predicate, or null if not found.
     */
    @Override
    public Profile loadProfile(Predicate<Profile> predicate) {
        List<Profile> profiles = getProfiles();
        return profiles.stream().filter(predicate).findFirst().orElse(null);
    }
}
