package sk.tuke.gamestudio.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@NamedQuery( name = "rating.getRating",
        query = "SELECT r FROM Rating r WHERE r.game=:game AND r.player = :player ORDER BY r.ratedon DESC")
@NamedQuery( name = "rating.getAverageRating",
        query = "SELECT AVG (r.rating) from Rating r WHERE r.game=:game")


public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident;




    private String player;
    private String game;
    private int rating;
    private Date ratedon;

    public Rating(String player, String game, int rating, Date ratedon) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.ratedon = ratedon;
    }

    public Rating(){ }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedon() {
        return ratedon;
    }

    public int getIdent() { return ident; }

    public void setIdent(int ident) { this.ident = ident; }

    public void setRatedon(Date ratedon) {
        this.ratedon = ratedon;
    }


    @Override
    public String toString() {
        return "Rating{" +
                "ident=" + ident+
                ",player='" + player + '\'' +
                "game='" + game + '\'' +
                ",rating=" + rating +
                ",ratedon=" + ratedon +
                '}';
    }



    @Override
    public int hashCode() {
        return Objects.hash(ident, player,game, rating,ratedon);
    }
}
