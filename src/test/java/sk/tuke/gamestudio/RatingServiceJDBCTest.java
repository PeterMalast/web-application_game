package sk.tuke.gamestudio;

import org.junit.Before;
import org.junit.Test;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.service.RatingException;
import sk.tuke.gamestudio.server.service.RatingService;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import static org.junit.Assert.*;

public class RatingServiceJDBCTest {

    private static final String DELETE = "DELETE FROM rating";

    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String USER = "postgres";
    private static final String PASS = "tukeKP";

    @Before
    public void setUp() throws Exception {
        Connection c = DriverManager.getConnection(URL, USER, PASS);
        Statement s = c.createStatement();
        s.execute(DELETE);
    }

    @Test
    public void setRating() throws RatingException {
        RatingService ratingService = new RatingServiceJDBC();
        Rating rating = new Rating("Juraj","lightoff",14,new Date());
        ratingService.setRating(rating);

        assertEquals("Juraj",rating.getPlayer());
        assertEquals("lightoff",rating.getGame());
        assertEquals(14,rating.getRating());
    }

    @Test
    public void getAverageRating() throws RatingException {
        RatingService ratingService = new RatingServiceJDBC();
        Rating rating = new Rating("Juraj","lightoff",14,new Date());
        Rating rating1 = new Rating("Benjur","lightoff",12,new Date());
        ratingService.setRating(rating);
        ratingService.setRating(rating1);

    }

    @Test
    public void getRating() throws RatingException {
        RatingService ratingService = new RatingServiceJDBC();
        Rating rating = new Rating("Juraj","lightoff",14,new Date());
        Rating rating1 = new Rating("Benjur","lightoff",12,new Date());
        ratingService.setRating(rating);
        ratingService.setRating(rating1);

      //  assertEquals(12,ratingService.getRating("lightoff","Benjur"));
    }
}