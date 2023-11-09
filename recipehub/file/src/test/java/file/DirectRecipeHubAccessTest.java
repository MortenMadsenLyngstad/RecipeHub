package file;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.Profile;
import core.Recipe;
import core.RecipeLibrary;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This is a test class for DirectRecipeHubAccess.
 * This class mainly uses the filehandlers, which are tested seperatly
 * This class will not repeat this testing, but rather check if they are utilized properly
 */
public class DirectRecipeHubAccessTest {

    private UserFilehandler mockUserFilehandler = mock(UserFilehandler.class);
    private RecipeFilehandler mockRecipeFilehandler = mock(RecipeFilehandler.class);
    private DirectRecipeHubAccess directRecipeHubAccess = new DirectRecipeHubAccess(
        mockUserFilehandler, mockRecipeFilehandler);

    /**
     * This method tests if the empty contructor works properly.
     */
    @Test
    @DisplayName("Empty contructor test")
    public void testEmptyContructor() {
        Assertions.assertDoesNotThrow(() -> new DirectRecipeHubAccess(),
            "Initializing a DirectRecipeHubAcess without filehandlers should not throw exception");
    }

    /**
     * This method tests if the contructor whcih takes in two filehandlers works properly.
     * The filehandlers should never be null
     */
    @Test
    @DisplayName("Non-empty contructor test")
    public void testContructorWithFilehandlers() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new DirectRecipeHubAccess(null, mock(RecipeFilehandler.class)),
                "Should not be able to send in null for the RecipeFilehandler in the constructor.");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new DirectRecipeHubAccess(mock(UserFilehandler.class), null),
                "Should not be able to send in null for the UserFilehandler in the constructor.");
        Assertions.assertThrows(IllegalArgumentException.class, 
                () -> new DirectRecipeHubAccess(null, null),
                "Should not be able to send in null for both filehandlers in constructor.");
    }

    /**
     * This method tests if you can get all recipes properly, using getRecipeLibrary.
     */
    @Test
    @DisplayName("getRecipeLibrary() test")
    public void testgetRecipeLibrary() {
        when(mockRecipeFilehandler.readRecipeLibrary()).thenReturn(new RecipeLibrary());
        Assertions.assertNotNull(directRecipeHubAccess.getRecipeLibrary(), 
            "The ReipeLibrary should not be null");
        Assertions.assertEquals(0, directRecipeHubAccess.getRecipeLibrary().getSize(), 
            "An empty RecipeLibrary should be reeturned");
    }

    /**
     * This method tests if you can remove a recipe properly, using removeRecipe().
     */
    @Test
    @DisplayName("removeRecipe() test")
    public void testRemoveRecipe() {
        Recipe recipe = new Recipe("Pasta Carbonara", 2, new Profile("Username1", "Password1"));
        when(mockRecipeFilehandler.removeRecipe(recipe)).thenReturn(true);
        directRecipeHubAccess.removeRecipe(recipe);
        verify(mockRecipeFilehandler).removeRecipe(recipe);
    }

    /**
     * This method tests if you can save a recipe properly, using saveRecipe().
     */
    @Test
    @DisplayName("saveRecipe() test")
    public void testSaveRecipe() {
        Profile profile = new Profile("Username1", "Password1");
        when(mockUserFilehandler.writeProfile(profile)).thenReturn(true);
        directRecipeHubAccess.saveProfile(profile);
        verify(mockUserFilehandler).writeProfile(profile);
    }

    /**
     * This method tests if you can get all saved profiles properly, using getProfiles().
     */
    @Test
    @DisplayName("getProfiles() test")
    public void testGetProfiles() {
        when(mockUserFilehandler.readProfiles()).thenReturn(
            new ArrayList<>(List.of(new Profile("Username1", "Password1"))));
        Assertions.assertNotNull(directRecipeHubAccess.getProfiles(), "Should not return null.");
        Assertions.assertEquals(1, directRecipeHubAccess.getProfiles().size(), 
            "The list returnes should have length 1.");
        Assertions.assertEquals("Username1", 
            directRecipeHubAccess.getProfiles().get(0).getUsername(), 
            "The username of the first profile in the list should be 'Username1'");
    }

    /**
     * This method tests if you can load a profile from file properly, using loadProfile().
     * The method checks for the two predicates used in the application:
     * - Checking username and password
     * - Only checking username
     */
    @Test
    @DisplayName("loadProfile() test")
    public void testLoadProfile() {
        when(mockUserFilehandler.loadProfile("Username1"))
            .thenReturn(new Profile("Username1", "Password1"));

        Profile readProfile = directRecipeHubAccess.loadProfile("Username1");

        Assertions.assertEquals("Username1", readProfile.getUsername(), 
            "The username of the loaded profile should be 'Username1.");
        
        // Checks if it works when no match is found
        when(mockUserFilehandler.loadProfile("Username2")).thenReturn(null);

        Assertions.assertNull(directRecipeHubAccess.loadProfile("Username2"), 
            "The method should return null if the profile is not found.");
    }

    /**
     * This method tests you can save a list of profiles to file properly, using saveProfiles().
     */
    @Test
    @DisplayName("saveProfiles() test")
    public void testSaveProfiles() {
        List<Profile> profiles = new ArrayList<>(List.of(new Profile("Username1", "Password1")));
        when(mockUserFilehandler.writeAllProfiles(profiles)).thenReturn(true);
        directRecipeHubAccess.saveProfiles(profiles);
        verify(mockUserFilehandler).writeAllProfiles(profiles);
    }

    /**
     * This method tests if you can save a recipe properly, using saveRecipe().
     */
    @Test
    @DisplayName("saveRecipe() test")
    public void testRecipe() {
        Recipe recipe = new Recipe("Pasta Carbonara", 2, new Profile("Username1", "Password1"));
        when(mockRecipeFilehandler.writeRecipe(recipe)).thenReturn(true);
        directRecipeHubAccess.saveRecipe(recipe);
        verify(mockRecipeFilehandler).writeRecipe(recipe);
    }
}
