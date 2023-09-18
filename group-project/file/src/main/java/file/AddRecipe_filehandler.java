package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddRecipe_filehandler {
    private String filePath = Path.of(System.getProperty("user.home")).toString() + "/addedRecipies.csv";

    public void SaveRecipe(String name, String description, Set<String> ingredients,
            Map<String, String> ingredientUnits, List<String> steps, int portions) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(name + "," + description + "," + ingredients.toString() + "," + ingredientUnits.toString() + "," + steps.toString() + "," + String.valueOf(portions));
            FileWriter filewriter = new FileWriter(new File(filePath), true);
            filewriter.write(sb.toString() + "\n");
            filewriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            System.out.println(e.getMessage());
        }
    }
}
