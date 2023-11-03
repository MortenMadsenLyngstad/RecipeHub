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

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    public static void main(String[] args) {
        SpringApplication.run(RecipeHubApplication.class, args);
    }    
    
}
