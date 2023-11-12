package springboot.restserver;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import core.Review;
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
import org.junit.jupiter.api.BeforeEach;
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
    private Recipe recipe1;
    private Profile profile;

    /**
     * This method is run before all tests.
     * 
     * @throws Exception - if an error occurs
     */
    @BeforeAll
    public static void setUp() throws Exception {
        UserFilehandler.setFileName("testUserInfo.json");
        RecipeFilehandler.setFileName("testRecipes.json");
    }

    @BeforeEach
    public void beforeEachSetUp() {
        profile = new Profile("testUser", "Password123");
        recipe1 = new Recipe("Recipe1", 1, profile);
    }

    private String getUrl() {
        return "http://localhost:" + port + "/recipehub/";
    }

    @Test
    public void testPutRecipe() {
        testRestTemplate.put(getUrl() + "recipelibrary", recipe1);
        RecipeLibrary recipeLibrary = access.getRecipeLibrary();
        Assertions.assertTrue(recipeLibrary.getSize() == 1,
                "Should have size 1 after added one recipe");
        Assertions.assertTrue(recipeLibrary.containsRecipe(recipe1), "Should contain recipe1");
        Review review = new Review(5.0, "Good", "testUser");
        recipe1.addReview(review);
        testRestTemplate.put(getUrl() + "recipelibrary", recipe1);
        recipeLibrary = access.getRecipeLibrary();
        Assertions.assertTrue(recipeLibrary.getSize() == 1,
                "Should have size 1 after updating a recipe that already exists");
        Assertions.assertTrue(recipeLibrary.containsRecipe(recipe1),
                "Should contain recipe1 after update");
        Assertions.assertEquals(review.getReviewer(),
                recipeLibrary.getRecipe(0).getReviews().get(0).getReviewer(),
                "Should have a review after adding review");
    }

    @Test
    public void testGetRecipeLibrary() {
        Assertions.assertNotNull(testRestTemplate.getForObject(getUrl() + "recipelibrary",
                RecipeLibrary.class), "Should return empty recipe library");
        access.saveRecipe(recipe1);
        access.saveRecipe(new Recipe("Recipe2", 2, profile));
        RecipeLibrary recipeLibrary = testRestTemplate.getForObject(getUrl() + "recipelibrary",
                RecipeLibrary.class);
        Assertions.assertTrue(recipeLibrary.getSize() == 2, "Should have size 2");
        Assertions.assertEquals("Recipe1", recipeLibrary.getRecipe(0).getName(),
                "Should have recipe with name Recipe1");
        Assertions.assertEquals("Recipe2", recipeLibrary.getRecipe(1).getName(),
                "Should have recipe with name Recipe2");
    }

    @Test
    public void testRemoveRecipe() {
        access.saveRecipe(recipe1);
        RecipeLibrary recipeLibrary = access.getRecipeLibrary();

        String url = getUrl() + "recipelibrary";

        testRestTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(recipe1), Void.class);

        recipeLibrary = access.getRecipeLibrary();
        Assertions.assertTrue(recipeLibrary.getSize() == 0, "Should be empty");
        // Tests that removing recipe that doesn't exist doesn't cause error
        Assertions.assertTrue(testRestTemplate.exchange(url, HttpMethod.DELETE,
                new HttpEntity<>(recipe1), Boolean.class).getBody(), "Should return true");
    }

    @Test
    public void testPutProfile() {
        // Tests if putProfile adds new profile
        Assertions.assertEquals(0, access.getProfiles().size(), "Should be empty");
        testRestTemplate.put(getUrl() + "profiles", profile);
        Assertions.assertEquals(1, access.getProfiles().size(), "Should have size 1");
        Assertions.assertEquals("testUser", access.loadProfile("testUser").getUsername(),
                "Should have username testUser");
        Assertions.assertFalse(
                access.loadProfile("testUser").getFavorites().containsRecipe(recipe1),
                "Should not contain recipe1");
        // Tests if putProfile updates existing profile
        profile.addFavorite(recipe1);
        testRestTemplate.put(getUrl() + "profiles", profile);
        Assertions.assertEquals(1, access.getProfiles().size(), "Should have size 1");
        Assertions.assertTrue(
                access.loadProfile("testUser").getFavorites().containsRecipe(recipe1),
                "Should contain recipe1");
    }

    @Test
    public void testGetProfiles() {
        Profile profile2 = new Profile("testUser2", "Password123");
        access.saveProfile(profile);
        access.saveProfile(profile2);
        ResponseEntity<List<Profile>> responseEntity = testRestTemplate.exchange(
                getUrl() + "profiles",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Profile>>() {
                });

        List<Profile> profiles = responseEntity.getBody();
        Assertions.assertTrue(profiles.size() == 2, "Should have size 2");
        Assertions.assertEquals(profiles.get(0).getUsername(), profile.getUsername(),
                "Should have username testUser");
        Assertions.assertEquals(profiles.get(1).getUsername(), profile2.getUsername(),
                "Should have username testUser2");
    }

    @Test
    public void testGetProfile() {
        access.saveProfile(profile);
        Profile readProfile = testRestTemplate.getForObject(getUrl() + "profiles/testUser",
                Profile.class);
        Assertions.assertEquals("testUser", readProfile.getUsername(),
                "The username of the profile should be 'testUser'");
        Assertions.assertNull(testRestTemplate.getForObject(getUrl() + "profiles/Username2",
                Profile.class), "Should return null");
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
