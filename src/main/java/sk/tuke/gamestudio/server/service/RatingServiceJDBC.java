package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.Rating;

import java.sql.*;
import java.sql.Date;

public class RatingServiceJDBC implements RatingService {

    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "tukeKP";

    public static final String INSERT_RATING =
            "INSERT INTO rating (player, game, rating, ratedon) VALUES (?, ?, ?, ?)";

    public static final String SELECT_RATING =
            "SELECT player, game, rating, ratedon FROM rating WHERE game = ? AND player = ? ORDER BY rating DESC LIMIT 10;";

    public static final String SELECT_AVG_RATING = "SELECT AVG(rating) from rating WHERE game = ?;";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_RATING)) {
                ps.setString(1, rating.getGame());
                ps.setString(2, rating.getPlayer());
                ps.setDate(3, new Date(rating.getRatedon().getTime()));
                ps.setInt(4, rating.getRating());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException("Error saving rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int r = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_AVG_RATING)) {
                ps.setString(1, game);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        r = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading score", e);
        }
        return r;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int r = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_RATING)) {
                ps.setString(1, game);
                ps.setString(2, player);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        r = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading score", e);
        }
        return r;
    }

    public static void main(String[] args) throws Exception {
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
        try {
            System.out.println(ratingServiceJDBC.getAverageRating("My First play of NMM"));
        } catch (RatingException e) {
        }
    }
}

