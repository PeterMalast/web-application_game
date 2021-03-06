package sk.tuke.gamestudio.server.entity;

        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.Id;
        import javax.persistence.NamedQuery;
        import java.io.Serializable;
        import java.util.Date;
        import java.util.Objects;

@Entity
@NamedQuery( name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")

@NamedQuery( name = "Score.getScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")





public class Score implements Comparable<Score>, Serializable {
    @Id
    @GeneratedValue
    private int ident; //identifikator

    public int getIdent() { return ident; }

    public void setIdent(int ident) { this.ident = ident; }

    private String game;

    private String player;

    private int points;

    private Date playedOn;

    public Score() {}

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedOn +
                "ident=" + ident+
                '}';
    }

    @Override
    public int compareTo(Score o) {
        if(o == null) return -1;
        return this.getPoints() - o.getPoints();
    }

    @Override
    public int hashCode() {
        return Objects.hash(ident, game, playedOn, player, points);
    }
}
