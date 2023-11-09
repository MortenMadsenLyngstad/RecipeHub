package springboot.restserver;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import file.DirectRecipeHubAccess;
import file.RecipeFilehandler;
import file.UserFilehandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Tests for the RecipeHubApplication class.
 */
@SpringBootTest(classes = { RecipeHubApplication.class,
    RecipeHubController.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RecipeHubApplicationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    private DirectRecipeHubAccess access = new DirectRecipeHubAccess();

    /**
     * This method is run before all tests.
     * @throws Exception - if an error occurs
     */
    @BeforeAll
    public static void setup() throws Exception {
        UserFilehandler.setFileName("testUserInfo.json");
        RecipeFilehandler.setFileName("testRecipes.json");

    }

    private String getUrl() {
        return "http://localhost:" + port + "/recipehub/";
    }

    @Test
    public void putRecipeTest() {
        Recipe recipe1 = new Recipe("Recipe1", 1,
                new Profile("testUser", "Password123"));
        testRestTemplate.put(getUrl() + "recipelibrary", recipe1);
        RecipeLibrary recipeLibrary = access.getRecipeLibrary();
        Assertions.assertTrue(recipeLibrary.getSize() == 1);
        Assertions.assertTrue(recipeLibrary.containsRecipe(recipe1));
    }

    @Test
    public void getRecipeLibraryTest() {
        System.out.println(RecipeFilehandler.getFileName());
        Recipe recipe1 = new Recipe("Recipe1", 1,
                new Profile("testUser", "Password123"));
        access.saveRecipe(recipe1);
        RecipeLibrary recipeLibrary = testRestTemplate.getForObject(getUrl() + "recipelibrary",
                RecipeLibrary.class);
        Assertions.assertTrue(recipeLibrary.getSize() == 1);
        Assertions.assertEquals(recipeLibrary.getRecipe(0).getName(), recipe1.getName());
        Assertions.assertEquals(recipeLibrary.getRecipe(0).getPortions(), recipe1.getPortions());
    }

    @Test
    public void removeRecipeTest() {
        Recipe recipe1 = new Recipe("Recipe1", 1, new Profile("testUser", "Password123"));
        access.saveRecipe(recipe1);
        RecipeLibrary recipeLibrary = access.getRecipeLibrary();
        Assertions.assertTrue(recipeLibrary.getSize() == 1);
        Assertions.assertTrue(recipeLibrary.containsRecipe(recipe1));

        String url = getUrl() + "recipelibrary";
        testRestTemplate.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(recipe1),
                Void.class);

        recipeLibrary = access.getRecipeLibrary();
        Assertions.assertTrue(recipeLibrary.getSize() == 0);
        Assertions.assertFalse(recipeLibrary.containsRecipe(recipe1));
    }

    @Test
    public void putProfileTest() {
        Profile profile = new Profile("Username1", "Password1");
        testRestTemplate.put(getUrl() + "profiles", profile);
        Assertions.assertTrue(access.getProfiles().size() == 1);
        Assertions.assertTrue(access.getProfiles()
                .get(0).getUsername().equals(profile.getUsername()));
    }

    @Test
    public void getProfilesTest() {
        Profile profile = new Profile("Username1", "Password1");
        access.saveProfile(profile);
        ResponseEntity<List<Profile>> responseEntity = testRestTemplate.exchange(
                getUrl() + "profiles",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Profile>>() {
                });

        List<Profile> profiles = responseEntity.getBody();
        Assertions.assertTrue(profiles.size() == 1);
        Assertions.assertEquals(profiles.get(0).getUsername(), profile.getUsername());
    }

    @Test
    public void getProfileTest() {
        Profile profile = new Profile("Username1", "Password1");
        access.saveProfile(profile);
        Profile readProfile = testRestTemplate.getForObject(getUrl() + "profiles/Username1",
                Profile.class);
        Assertions.assertEquals("Username1", readProfile.getUsername(), 
            "The username of the profile should be 'Username1'");
    }

    @AfterEach
    private void deleteTestFiles() {
        try {
            Files.delete(Path.of(System.getProperty("user.home")).resolve("testRecipes.json"));
            Files.delete(Path.of(System.getProperty("user.home")).resolve("testUserInfo.json"));
        } catch (IOException e) {
            System.out.println("Error deleting file");
            System.out.println(e.getMessage());
        }
    }
}
