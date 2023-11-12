package file;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;
import com.google.gson.Gson;
import core.PasswordHasher;
import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is a test class for RemoteRecipeHubAccess.java
 */
public class RemoteRecipeHubAccessTest {

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;
    private RemoteRecipeHubAccess remoteRecipeHubAccess;

    /**
     * This method starts the WireMockServer and sets up the RemoteRecipeHubAccess.
     * 
     * @throws URISyntaxException if there's an issue with the URI construction.
     */
    @BeforeEach
    public void startWireMockServerAndSetup() throws URISyntaxException {
        config = WireMockConfiguration.wireMockConfig().port(8089);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        remoteRecipeHubAccess = new RemoteRecipeHubAccess(
                new URI("http://localhost:" + wireMockServer.port() + "/recipehub"));
    }

    /**
     * Tests the retrieval of the RecipeLibrary from the remote server.
     */
    @Test
    public void testGetRecipeLibrary() {
        Recipe recipe1 = new Recipe("recipe1", 1, new Profile("Username1", "Password1"));
        Recipe recipe2 = new Recipe("recipe2", 1, new Profile("Username2", "Password2"));
        String savedRecipeLibrary = new Gson().toJson(
                new RecipeLibrary(new ArrayList<>(List.of(recipe1, recipe2))));
        stubFor(get(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(savedRecipeLibrary)));
                        
        RecipeLibrary recipeLibrary = remoteRecipeHubAccess.getRecipeLibrary();
        assertEquals(2, recipeLibrary.getSize());
        Assertions.assertEquals("recipe1", recipeLibrary.getRecipe(0).getName(),
                "The first recipe should be 'recipe1'");
        Assertions.assertEquals("recipe2", recipeLibrary.getRecipe(1).getName(),
                "The second recipe should be 'recipe2'");

        // Checks if exception handling works properly
        stubFor(get(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        Assertions.assertThrows(RuntimeException.class,
                () -> remoteRecipeHubAccess.getRecipeLibrary(),
                "Runtime exception should be thrown if the load fails.");
    }

    /**
     * Tests the removal of a recipe from the remote server.
     */
    @Test
    public void testRemoveRecipe() {
        stubFor(delete(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));

        Profile profile = new Profile("Username1", "Password1");
        remoteRecipeHubAccess.removeRecipe(new Recipe("Pasta Carbonara", 1, profile));
        verify(deleteRequestedFor(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json")));

        // Checks if exception handling works properly
        stubFor(delete(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        Assertions.assertFalse(remoteRecipeHubAccess.removeRecipe(
                new Recipe("Pizza", 1, profile)),
                "Should return false if the remove fails.");

    }

    /**
     * Tests the saving of a recipe to the remote server.
     */
    @Test
    public void testSaveRecipe() {
        stubFor(put(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
        Profile profile = new Profile("Username1", "Password1");
        remoteRecipeHubAccess.saveRecipe(new Recipe("Pasta Carbonara", 1, profile));
        verify(putRequestedFor(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json")));

        // Checks if exception handling works properly
        stubFor(put(urlEqualTo("/recipelibrary"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        Assertions.assertFalse(remoteRecipeHubAccess.saveRecipe(new Recipe("Pizza", 1, profile)),
                "Should return false if the save fails.");
    }

    /**
     * Tests the saving of a profile to the remote server.
     */
    @Test
    public void testSaveProfile() {
        stubFor(put(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
        Profile profile = new Profile("Username1", "Password1");
        remoteRecipeHubAccess.saveProfile(profile);
        verify(putRequestedFor(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json")));

        // Checks if exception handling works properly
        stubFor(put(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        Assertions.assertFalse(remoteRecipeHubAccess.saveProfile(profile),
                "Should return false if the remove fails.");
    }

    /**
     * Tests the retrieval of a list of all profiles from the remote server.
     */
    @Test
    public void testGetProfiles() {
        Profile profile1 = new Profile("Username1", "Password1");
        Profile profile2 = new Profile("Username2", "Password2");
        String userinfo = new Gson().toJson(Arrays.asList(profile1, profile2));
        stubFor(get(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(userinfo)));
        List<Profile> profiles = remoteRecipeHubAccess.getProfiles();
        Assertions.assertEquals(2, profiles.size(), "The size of the list of profiles should be 2");
        Assertions.assertEquals("Username1", profiles.get(0).getUsername(),
                "The first profile's username should be 'Username1'");
        Assertions.assertEquals("Username2", profiles.get(1).getUsername(),
                "The second profile's username should be 'Username2'");

        // Checks if exception handling works properly
        stubFor(get(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        Assertions.assertNull(remoteRecipeHubAccess.getProfiles(),
                "Should return null if getProfiles fails.");
    }

    /**
     * Tests the saving of a list of profiles to the remote server.
     */
    @Test
    public void testSaveProfiles() {
        stubFor(put(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
        Profile profile1 = new Profile("Username1", "Password1");
        Profile profile2 = new Profile("Username2", "Password2");
        List<Profile> profiles = new ArrayList<>(List.of(profile1, profile2));
        remoteRecipeHubAccess.saveProfiles(profiles);

        verify(profiles.size(), putRequestedFor(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json")));

        stubFor(put(urlEqualTo("/profiles"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));
        Assertions.assertFalse(remoteRecipeHubAccess.saveProfiles(profiles));

    }

    /**
     * Tests the loading of a profile from the remote server.
     */
    @Test
    public void testLoadProfile() {
        String userinfo = new Gson().toJson(new Profile("Username1", "Password1"));

        stubFor(get(urlEqualTo("/profiles/Username1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(userinfo)));

        Profile readProfile = remoteRecipeHubAccess.loadProfile("Username1");
        Assertions.assertEquals("Username1", readProfile.getUsername(),
                "The username should be 'Username1");
        Assertions.assertTrue(PasswordHasher.verifyPassword("Password1",
                readProfile.getHashedPassword()),
                "The password should be 'Password1");
    }

    /**
     * Stops the WireMockServer after each test.
     */
    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}
