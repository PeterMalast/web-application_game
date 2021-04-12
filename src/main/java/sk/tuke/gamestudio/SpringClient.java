package sk.tuke.gamestudio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import sk.tuke.gamestudio.game.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.core.Field;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.server.service.*;

@Configuration
@SpringBootApplication
public class SpringClient {

    public static void main(String[] args) throws Exception {
      //  SpringApplication.run(SpringClient.class, args);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);

    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) { return args -> ui.play(); }

    @Bean
    public ConsoleUI consoleUI(Field field) { return new ConsoleUI(field); }

    @Bean
    public Field field() { return new Field(9, 9); }

    @Bean
    public ScoreService scoreService() { return new ScoreServiceRestClient(); }

    @Bean
    public RatingService ratingService() { return new RatingServiceRestClient(); }

    @Bean
    public CommentService commentService() { return new CommentServiceRestClient(); }





    /*
    public static void main(String[] args) {
            Field field = new Field(9,9);
            ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }
    */
}
