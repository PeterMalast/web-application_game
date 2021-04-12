package sk.tuke.gamestudio.game.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.game.core.GameState;
import sk.tuke.gamestudio.game.core.Tile;
import sk.tuke.gamestudio.server.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleUI {
    private static final String GAME_NAME = "lightoff";

    private final Field field;
    private int scores,ratings;
    private String name;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    private final Pattern RATING_PATTERN = Pattern.compile("([0-5])");

    private String game = "lightoff";
    private String comments;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleUI(Field field) {
        this.field = field;
    }

    /*

    public void play() {
        do {
            run();
            printField();
            processInput();
        } while (field.getState() == GameState.PLAYING);
        printField();
        if (field.getState() == GameState.SOLVED) {
            saveScore();
            setRating();

          //  setComment();


        }
    }
    */

    public void play() throws CommentException, RatingException, ScoreException, SQLException {
        if (field != null) {
            do {
                printField();
                processInput();

            } while (field.getState() == GameState.PLAYING);
            printField();
            saveScore();
            rate();
            comment();
            run();
        }
    }

    private void printField() {
        printFieldHeader();
        printFieldBody();
    }

    private void printFieldHeader() {
        System.out.print(" ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.println();
    }

    private void printFieldBody() {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) ('A' + row));
            for (int column = 0; column < field.getColumnCount(); column++) {
                System.out.print(" ");
                printTile(row, column);
            }
            System.out.println();
        }
    }

    private void printTile(int row, int column) {
        final Tile tile = field.getTile(row, column);
        switch (tile.getState()) {
            case OPENED:
                    System.out.print("1");
                break;
            case CLOSED:
                System.out.print("-");
                break;
        }
    }

    /*
    private void printFinalMessage() {
        if (field.getState() == GameState.SOLVED) {
            System.out.printf("Your score was: %d%n", field.getScore());
            try {
                scoreService.addScore(new Score(
                        GAME_NAME,
                        System.getProperty(name),
                        field.getScore(),
                        new Date()
                ));
            } catch (ScoreException e) {
            }
        }
    }
    */



    private void processInput() {
        while (true) {
            System.out.print("Enter input (e.g. SE5): ");
            String input = new Scanner(System.in).nextLine().trim().toUpperCase();
            if ("X".equals(input))
                System.exit(0);
            if (input.length() == 3) {
                try {
                    int row = input.charAt(1) - 'A';
                    int column = Integer.parseInt(input.substring(2)) - 1;
                    if (row >= 0 && row < field.getRowCount() && column >= 0 && column < field.getColumnCount()) {
                        if (input.charAt(0) == 'S') {
                            field.openCloseTile(row, column);
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
    }


    private String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            System.err.println("Nepodarilo sa nacitat vstup, skus znova");
            return "";
        }
    }


    private void setRating() {
        System.out.println("Your rating (0-5) stars :");
        String rating = readLine();
        Matcher ratingMatcher = RATING_PATTERN.matcher(rating);
        if (ratingMatcher.matches()) {
            try {
                ratingService.setRating(new Rating(
                        name,
                        GAME_NAME,
                        Integer.parseInt(ratingMatcher.group(1)),
                        new Date()
                ));
                System.out.println("Your rating was saved!");
//
            } catch (RatingException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Wrong format of rating!");
            setRating();
        }
    }




/*
    private void setRating(){
        Rating rating = new Rating(name,game,ratings,new Date());
        System.out.printf("Rating <0-10>:");
        Scanner scannerRating = new Scanner(System.in);
        ratings = scannerRating.nextInt();

        RatingService rs = new RatingServiceJPA();
        try{
            rs.setRating(rating);
        }catch (RatingException e){
            e.printStackTrace();
        }
    }
    */


    private void setComment(){
        System.out.printf("Comments :");
        Scanner scannerComment = new Scanner(System.in);
        comments = scannerComment.nextLine();
        Comment comment = new Comment(name,game,comments,new Date());
        CommentService rs = new CommentServiceJPA();
        try{
            rs.addComment(comment);
        }catch (CommentException e){
            e.printStackTrace();
        }
    }


    private void saveScore()  {
        System.out.println("YOU WON!");
        int points = field.getScore();
        System.out.printf("Your score was: %d%n", points);
        System.out.println("Enter your name :");
        String player = readLine();
        setName(player);
        Score score = new Score(GAME_NAME, name, points, new Date());
        scoreService.addScore(score);


    }



    private void printMenu() {
        System.out.println("[P] Play");
        System.out.println("[R] Rate");
        System.out.println("[A] Show average rating");
        System.out.println("[T] Top 10");
        System.out.println("[S] Show comments");
        System.out.println("[W] Write a comment");
        System.out.println("[X] Exit");
    }


    public void run() throws CommentException, RatingException, SQLException {
        printMenu();

        String line = readLine();
        while (!line.equalsIgnoreCase("X")) {
            if (line.equalsIgnoreCase("w")) {
                comment();
            } else if (line.equalsIgnoreCase("r")) {
                rate();

            } else if (line.equalsIgnoreCase("p")) {
                this.play();
            } else if (line.equalsIgnoreCase("t")) {
                printHighscore();
            } else if (line.equalsIgnoreCase("s")) {
                showComments();
            } else if (line.equalsIgnoreCase("a")) {
                showAverageRating();
            }
            printMenu();
            line = readLine();
        }
        System.out.println("Bye Bye");
        System.exit(0);
    }


    private void comment() throws CommentException {
        System.out.println("Your comment:");
        String commentString = readLine();
        Comment comment = new Comment(name, GAME_NAME, commentString, new Date());
        commentService.addComment(comment);
        System.out.println("Your comment was added to database");
    }

    private void rate() throws RatingException {
        System.out.println("Your rating (0-5) stars");
        String rate = readLine();
        Matcher m = RATING_PATTERN.matcher(rate);
        if(m.matches()) {
            if (Integer.parseInt(rate) <= 5 && Integer.parseInt(rate) >= 0 ) {
                Rating rating = new Rating(name, GAME_NAME, Integer.parseInt(rate), new Date());
                ratingService.setRating(rating);
            }
        }else {
            System.out.println("Wrong rating");
            rate();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void printHighscore() {
        System.out.println("Score \t Date \t \t \t\t\tPlayer ");
        List<Score> scores = scoreService.getTopScores(GAME_NAME);
        if(scores.isEmpty()){
            System.out.println("No score to show");
            return;
        }
        for (Score s : scores) {
            System.out.println(s.toString());
        }

    }

    private void showComments() throws CommentException {
        List<Comment> comments = commentService.getComments(GAME_NAME);
        if(comments.isEmpty()){
            System.out.println("No comments to show");
            return;
        }
        for (Comment c : comments) {
            System.out.println(c.toString());
            System.out.println();
        }
    }

    private void showAverageRating() throws RatingException {
        System.out.println("The average rating is: " + ratingService.getAverageRating(GAME_NAME));
    }










}
