package sk.tuke.gamestudio;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.service.RatingService;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RatingServiceTest {
    protected RatingService ratingService;

    @Test
    public void testDbInit() throws  Exception{
        assertEquals(0 , ratingService.getAverageRating("lightoff"));
    }

    @Test
    public void testAddRating() throws Exception{
        Rating rating = new Rating("Prvy","lightoff",8,new Date());
        ratingService.setRating(rating);
        TestCase.assertEquals(rating.getRating(), ratingService.getRating("lightoff", "Prvy"));
    }

    @Test
    public void testAverageRating() throws Exception{
        Rating s2 = new Rating("Prvy","lightoff", 8, new Date());

        ratingService.setRating(s2);
        TestCase.assertEquals(1,ratingService.getAverageRating("lightoff"));

    }

}
