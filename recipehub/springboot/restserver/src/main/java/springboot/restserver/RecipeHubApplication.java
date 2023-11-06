package springboot.restserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Spring application.
 */
@SpringBootApplication
public class RecipeHubApplication {

    /**
     * The Gson bean.
     * @return the Gson bean
     */
    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * The main method which runs the application.
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RecipeHubApplication.class, args);
    }    
}
