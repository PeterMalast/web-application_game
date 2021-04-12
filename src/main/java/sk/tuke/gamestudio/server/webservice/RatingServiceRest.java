package sk.tuke.gamestudio.server.webservice;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.service.RatingException;
import sk.tuke.gamestudio.server.service.RatingService;

import javax.ws.rs.*;

@Path("/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    //http://localhost:8080/rest/rating
    @POST
    @Consumes("application/json")
    public Response addRating(Rating rating) throws RatingException {
        ratingService.setRating(rating);
        return Response.ok().build();
    }

    //http://localhost:8080/rest/rating/lightoff
    @GET
    @Path("/{game}")
    @Produces("application/json")
    public int getAverageRating(@PathParam("game") String game) throws RatingException {
        return ratingService.getAverageRating(game);
    }

    @GET
    @Path("/{game}/{player}")
    @Produces("application/json")
    public int getRating(@PathParam("game") String game, @PathParam("player") String player) throws RatingException {
        return ratingService.getRating(game,player);
    }


}