package sk.tuke.gamestudio;

import org.junit.Test;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.ScoreService;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;



public class ScoreServiceTest {

    protected ScoreService scoreService;

    @Test
    public void testDbInit() throws Exception {
     //   assertEquals(0, scoreService.getTopScores("lihtoff").size());
    }

    @Test
    public void testAddScore() throws Exception {
        Score score = new Score("lihtoff", "miska", 15, new Date());
        scoreService.addScore(score);
      //  assertEquals(1, scoreService.getTopScores("lihtoff").size());
    }

    @Test
    public void testGestBestScores() throws Exception {
        Score s1 = new Score("lihtoff", "janko", 150, new Date());
        Score s2 = new Score("lihtoff", "hrasko", 300, new Date());

        scoreService.addScore(s1);
        scoreService.addScore(s2);

    //    List<Score> scores = scoreService.getTopScores("lihtoff");
  //      assertEquals(s2.getPoints(), scores.get(0).getPoints());
    //    assertEquals(s2.getPoints(), scores.get(0).getPoints());
    }


}
