package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Score;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/*
    CREATE TABLE score (
        player VARCHAR(64) NOT NULL,
        game VARCHAR(64) NOT NULL,
        points INTEGER NOT NULL,
        playedon TIMESTAMP NOT NULL
    );
     */

//INSERT INTO score (player, game, points, playedon) VALUES ('jaro', 'mines', 200, '2017-03-02 14:30')

//SELECT player, game, points, playedon FROM score WHERE game = 'mines' ORDER BY points DESC LIMIT 10;

public class ScoreServiceJDBC implements ScoreService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "tukeKP";

    public static final String INSERT_SCORE =
            "INSERT INTO score (game, player, points, playedon) VALUES (?, ?, ?, ?)";

    public static final String SELECT_SCORE =
            "SELECT game, player, points, playedon FROM score WHERE game = ? ORDER BY points DESC LIMIT 10;";


    @Override
    public void addScore(Score score) throws ScoreException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_SCORE)) {
                ps.setString(1, score.getGame());
                ps.setString(2, score.getPlayer());
                ps.setInt(3, score.getPoints());
                ps.setDate(4, new Date(score.getPlayedOn().getTime()));

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ScoreException("Error saving score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String gameName) throws ScoreException {
        List<Score> scores = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = c.prepareStatement(SELECT_SCORE)) {
                ps.setString(1, gameName);

                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    Score s = new Score(
                            rs.getString(1),

                            rs.getString(2),
                            rs.getInt(3),

                            rs.getTimestamp(4)
                    );
                    scores.add(s);
                }

            } catch (SQLException e) {
                throw new ScoreException("Error saving score", e);
            }
        } catch (SQLException e) {
            throw new ScoreException("Error connecting to database", e);
        }

        return scores;
    }

    public static void main(String[] args) throws Exception {
        ScoreService scoreService = new ScoreServiceJDBC() {
        };
        Score score = new Score("prvy ", "lightoff", 8, new java.util.Date());
        scoreService.addScore(score);
    }

}
