import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Score {

    /* Score information */
    int currScore;
    int highScore;

    public Score()
    {
        currScore=0;
        initHighScores();
    }

    public void update()
    {
        updateScore();
    }

    /* Reads the high scores file and saves it */
    public void initHighScores()
    {
        File file = new File("highScores.txt");
        Scanner sc;
        try
        {
            sc = new Scanner(file);
            highScore = sc.nextInt();
            sc.close();
        }
        catch(Exception e)
        {
        }
    }

    /* Writes the new high score to a file and sets flag to update it on screen */
    public void updateScore()
    {
        if (currScore > highScore)
        {
            PrintWriter out;
            try
            {
                out = new PrintWriter("highScores.txt");
                out.println(currScore);
                out.close();
            }
            catch(Exception e)
            {
            }
            highScore=currScore;
        }
        String str = "";
        if (Board.demo)
            str = "DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: "+highScore;
        else
            str = "Score: "+(currScore)+"\t High Score: "+highScore;
        Renderer.drawScore(str);
    }

    /* Wipes the high scores file and sets flag to update it on screen */
    public void clearHighScores()
    {
        PrintWriter out;
        try
        {
            out = new PrintWriter("highScores.txt");
            out.println("0");
            out.close();
        }
        catch(Exception e)
        {
        }
        highScore=0;
    }
}
