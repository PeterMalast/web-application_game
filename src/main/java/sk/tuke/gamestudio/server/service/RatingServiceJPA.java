package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void setRating(Rating rating) throws RatingException{
        entityManager.persist(rating);

    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Query q = entityManager.createNamedQuery("rating.getAverageRating")
                .setParameter("game", game);
        List res = q.getResultList();
        if(res.isEmpty() || res.get(0) == null)
            return -1;

        return ((Double)res.get(0)).intValue();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
    Query q = entityManager.createNamedQuery("rating.getRating")
            .setParameter("player",player)
            .setParameter("game",game);
    List res = q.getResultList();
        if(res.isEmpty() || res.get(0) == null)
            return -1;
        return ((Rating)res.get(0)).getRating();

    }

}
