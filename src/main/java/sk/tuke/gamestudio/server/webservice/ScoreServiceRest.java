package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.ScoreException;
import sk.tuke.gamestudio.server.service.ScoreService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/score")
public class ScoreServiceRest {
    @Autowired
    private ScoreService scoreService;

    @GET
    @Path("/{game}")
    @Produces("application/json")
    public List<Score> getTopScores(@PathParam("game") String game ) {
        return scoreService.getTopScores(game);
    }


    @POST
    @Consumes("application/json")
    public Response addScore(Score score) {
        scoreService.addScore(score);
        return Response.ok().build();
    }


}
