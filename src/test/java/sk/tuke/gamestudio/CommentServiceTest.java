package sk.tuke.gamestudio;

import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.service.CommentService;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class CommentServiceTest {
    protected CommentService commentService;


    @Test
    public void testDbInit() throws Exception {

        assertEquals(0, commentService.getComments("lightoff").size());

    }

    @Test
    public void testAddComment() throws Exception {
        Comment comment = new Comment("prvy-hrac", "lightoff","Dobra hra", new Date());
        commentService.addComment(comment);
    }

    @Test
    public void testGetComment() throws Exception {
        Comment s2 = new Comment("prvy-hrac", "lightoff","Ide to", new Date());

        commentService.addComment(s2);

  List<Comment> comments = commentService.getComments("lightoff");
       TestCase.assertEquals(0,comments.size());
    }
}

