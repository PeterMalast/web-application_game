package sk.tuke.gamestudio;

import sk.tuke.gamestudio.server.service.CommentServiceJDBC;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CommentServiceJDBCTest extends CommentServiceTest {
    private static final String DELETE = "DELETE FROM comment";

    private static final String URL = "jdbc:postgresql://localhost/gamestudio";
    private static final String USER = "postgres";
    private static final String PASS = "tukeKP";

    public CommentServiceJDBCTest() {
        super.commentService = new CommentServiceJDBC();
    }

    @Before
    public void setUp() throws Throwable {
        Connection c = DriverManager.getConnection(URL, USER, PASS);
        Statement s = c.createStatement();
        s.execute(DELETE);
    }

    @Test
    public void testDbInit() throws Exception {
        super.testDbInit();
    }

    @Test
    public void testAddScore() throws Exception {
        super.testAddComment();
    }

    @Test
    public void testGetComment() throws Exception {
        super.testAddComment();
    }







}
