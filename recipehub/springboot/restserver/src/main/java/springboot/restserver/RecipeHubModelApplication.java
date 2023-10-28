package springboot.restserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Spring application.
 */
@SpringBootApplication
public class RecipeHubModelApplication {

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    public static void main(String[] args) {
        SpringApplication.run(RecipeHubModelApplication.class, args);
    }    
    
}
