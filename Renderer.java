import java.awt.*;

public class Renderer
{
    /* Initialize the images*/
    /* For JAR File*/
  /*
  Image pacmanImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacman.jpg"));
  Image pacmanUpImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmanup.jpg"));
  Image pacmanDownImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmandown.jpg"));
  Image pacmanLeftImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmanleft.jpg"));
  Image pacmanRightImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmanright.jpg"));
  Image ghost10 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost10.jpg"));
  Image ghost20 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost20.jpg"));
  Image ghost30 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost30.jpg"));
  Image ghost40 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost40.jpg"));
  Image ghost11 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost11.jpg"));
  Image ghost21 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost21.jpg"));
  Image ghost31 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost31.jpg"));
  Image ghost41 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost41.jpg"));
  Image titleScreenImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/titleScreen.jpg"));
  Image gameOverImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/gameOver.jpg"));
  Image winScreenImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/winScreen.jpg"));
  */
    /* For NOT JAR file*/
    Image pacmanImage = Toolkit.getDefaultToolkit().getImage("img/pacman.jpg");
    Image pacmanUpImage = Toolkit.getDefaultToolkit().getImage("img/pacmanup.jpg");
    Image pacmanDownImage = Toolkit.getDefaultToolkit().getImage("img/pacmandown.jpg");
    Image pacmanLeftImage = Toolkit.getDefaultToolkit().getImage("img/pacmanleft.jpg");
    Image pacmanRightImage = Toolkit.getDefaultToolkit().getImage("img/pacmanright.jpg");
    Image ghost10 = Toolkit.getDefaultToolkit().getImage("img/ghost10.jpg");
    Image ghost20 = Toolkit.getDefaultToolkit().getImage("img/ghost20.jpg");
    Image ghost30 = Toolkit.getDefaultToolkit().getImage("img/ghost30.jpg");
    Image ghost40 = Toolkit.getDefaultToolkit().getImage("img/ghost40.jpg");
    Image ghost11 = Toolkit.getDefaultToolkit().getImage("img/ghost11.jpg");
    Image ghost21 = Toolkit.getDefaultToolkit().getImage("img/ghost21.jpg");
    Image ghost31 = Toolkit.getDefaultToolkit().getImage("img/ghost31.jpg");
    Image ghost41 = Toolkit.getDefaultToolkit().getImage("img/ghost41.jpg");
    Image titleScreenImage = Toolkit.getDefaultToolkit().getImage("img/titleScreen.jpg");
    Image gameOverImage = Toolkit.getDefaultToolkit().getImage("img/gameOver.jpg");
    Image winScreenImage = Toolkit.getDefaultToolkit().getImage("img/winScreen.jpg");

    public static Graphics g;
    private static Font font = new Font("Monospaced",Font.BOLD, 12);


    public void setGraphic(Graphics gr)
    {
        g = gr;
    }

    public static void drawScore(String str) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,600,18);
        g.setColor(Color.YELLOW);
        g.setFont(font);
        g.drawString(str,20,10);
    }

    /* Draws the appropriate number of lives on the bottom left of the screen.
           Also draws the menu */
    public void drawLives(Graphics gr, Board board)
    {
      g.setColor(Color.BLACK);
      /*Clear the bottom bar*/
      g.fillRect(0, board.max+5,600, board.gridSize);
      g.setColor(Color.YELLOW);
      for(int i = 0; i< board.numLives; i++)
      {
        /*Draw each life */
        g.fillOval(board.gridSize*(i+1), board.max+5, board.gridSize, board.gridSize);
      }
      /* Draw the menu items */
      g.setColor(Color.YELLOW);
      g.setFont(font);
      g.drawString("Reset",100, board.max+5+ board.gridSize);
      g.drawString("Clear High Scores",180, board.max+5+ board.gridSize);
      g.drawString("Exit",350, board.max+5+ board.gridSize);
    }

    /*  This function draws the board.  The pacman board is really complicated and can only feasibly be done
            manually.  Whenever I draw a wall, I call updateMap to invalidate those coordinates.  This way the pacman
            and ghosts know that they can't traverse this area */
    public void drawBoard(Graphics g, Board board)
    {
          g.setColor(Color.BLACK);
          g.fillRect(0,0,600,600);
          g.setColor(Color.BLACK);
          g.fillRect(0,0,420,420);

          g.setColor(Color.BLACK);
          g.fillRect(0,0,20,600);
          g.fillRect(0,0,600,20);
          g.setColor(Color.WHITE);
          g.drawRect(19,19,382,382);
          g.setColor(Color.BLUE);

          g.fillRect(40,40,60,20);
            board.updateMap(40,40,60,20);
          g.fillRect(120,40,60,20);
            board.updateMap(120,40,60,20);
          g.fillRect(200,20,20,40);
            board.updateMap(200,20,20,40);
          g.fillRect(240,40,60,20);
            board.updateMap(240,40,60,20);
          g.fillRect(320,40,60,20);
            board.updateMap(320,40,60,20);
          g.fillRect(40,80,60,20);
            board.updateMap(40,80,60,20);
          g.fillRect(160,80,100,20);
            board.updateMap(160,80,100,20);
          g.fillRect(200,80,20,60);
            board.updateMap(200,80,20,60);
          g.fillRect(320,80,60,20);
            board.updateMap(320,80,60,20);

          g.fillRect(20,120,80,60);
            board.updateMap(20,120,80,60);
          g.fillRect(320,120,80,60);
            board.updateMap(320,120,80,60);
          g.fillRect(20,200,80,60);
            board.updateMap(20,200,80,60);
          g.fillRect(320,200,80,60);
            board.updateMap(320,200,80,60);

          g.fillRect(160,160,40,20);
            board.updateMap(160,160,40,20);
          g.fillRect(220,160,40,20);
            board.updateMap(220,160,40,20);
          g.fillRect(160,180,20,20);
            board.updateMap(160,180,20,20);
          g.fillRect(160,200,100,20);
            board.updateMap(160,200,100,20);
          g.fillRect(240,180,20,20);
          board.updateMap(240,180,20,20);
          g.setColor(Color.BLUE);

          g.fillRect(120,120,60,20);
            board.updateMap(120,120,60,20);
          g.fillRect(120,80,20,100);
            board.updateMap(120,80,20,100);
          g.fillRect(280,80,20,100);
            board.updateMap(280,80,20,100);
          g.fillRect(240,120,60,20);
            board.updateMap(240,120,60,20);

          g.fillRect(280,200,20,60);
            board.updateMap(280,200,20,60);
          g.fillRect(120,200,20,60);
            board.updateMap(120,200,20,60);
          g.fillRect(160,240,100,20);
            board.updateMap(160,240,100,20);
          g.fillRect(200,260,20,40);
            board.updateMap(200,260,20,40);

          g.fillRect(120,280,60,20);
            board.updateMap(120,280,60,20);
          g.fillRect(240,280,60,20);
            board.updateMap(240,280,60,20);

          g.fillRect(40,280,60,20);
            board.updateMap(40,280,60,20);
          g.fillRect(80,280,20,60);
            board.updateMap(80,280,20,60);
          g.fillRect(320,280,60,20);
            board.updateMap(320,280,60,20);
          g.fillRect(320,280,20,60);
            board.updateMap(320,280,20,60);

          g.fillRect(20,320,40,20);
            board.updateMap(20,320,40,20);
          g.fillRect(360,320,40,20);
            board.updateMap(360,320,40,20);
          g.fillRect(160,320,100,20);
            board.updateMap(160,320,100,20);
          g.fillRect(200,320,20,60);
            board.updateMap(200,320,20,60);

          g.fillRect(40,360,140,20);
            board.updateMap(40,360,140,20);
          g.fillRect(240,360,140,20);
            board.updateMap(240,360,140,20);
          g.fillRect(280,320,20,40);
            board.updateMap(280,320,20,60);
          g.fillRect(120,320,20,60);
            board.updateMap(120,320,20,60);
          drawLives(g, board);
        String str = "";
        if (Board.demo)
            str = "DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: "+board.score.highScore;
        else
            str = "Score: "+(board.score.currScore)+"\t High Score: "+board.score.highScore;
        Renderer.drawScore(str);
    }

    /* Draws the pellets on the screen */
    public void drawPellets(Graphics g, Board board)
    {
          g.setColor(Color.YELLOW);
          for (int i=1;i<20;i++)
          {
            for (int j=1;j<20;j++)
            {
              if ( board.pellets[i-1][j-1])
              g.fillOval(i*20+8,j*20+8,4,4);
            }
          }
    }

    void drawPacman(Graphics g, Player player) {
      if (player.frameCount < 5)
      {
        /* Draw mouth closed */
        g.drawImage(pacmanImage,player.x,player.y,Color.BLACK,null);
      }
      else
      {
        /* Draw mouth open in appropriate direction */
        if (player.frameCount >=10)
          player.frameCount=0;

        switch(player.currDirection)
        {
          case 'L':
             g.drawImage(pacmanLeftImage,player.x,player.y,Color.BLACK,null);
             break;
          case 'R':
             g.drawImage(pacmanRightImage,player.x,player.y,Color.BLACK,null);
             break;
          case 'U':
             g.drawImage(pacmanUpImage,player.x,player.y,Color.BLACK,null);
             break;
          case 'D':
             g.drawImage(pacmanDownImage,player.x,player.y,Color.BLACK,null);
             break;
        }
      }
    }

    void drawGhost(Graphics g, Board board) {
      if (board.ghost1.frameCount < 5)
      {
        /* Draw first frame of ghosts */
        g.drawImage(ghost10, board.ghost1.x, board.ghost1.y,Color.BLACK,null);
        g.drawImage(ghost20, board.ghost2.x, board.ghost2.y,Color.BLACK,null);
        g.drawImage(ghost30, board.ghost3.x, board.ghost3.y,Color.BLACK,null);
        g.drawImage(ghost40, board.ghost4.x, board.ghost4.y,Color.BLACK,null);
        board.ghost1.frameCount++;
      }
      else
      {
        /* Draw second frame of ghosts */
        g.drawImage(ghost11, board.ghost1.x, board.ghost1.y,Color.BLACK,null);
        g.drawImage(ghost21, board.ghost2.x, board.ghost2.y,Color.BLACK,null);
        g.drawImage(ghost31, board.ghost3.x, board.ghost3.y,Color.BLACK,null);
        g.drawImage(ghost41, board.ghost4.x, board.ghost4.y,Color.BLACK,null);
        if (board.ghost1.frameCount >=10)
          board.ghost1.frameCount=0;
        else
          board.ghost1.frameCount++;
      }
    }

    void drawGameOverScreen(Graphics g, Board board) {
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
      g.drawImage(gameOverImage,0,0,Color.BLACK,null);
      board.New = 1;
      /* Stop any pacman eating sounds */
      board.sounds.nomNomStop();
    }

    void drawWinScreen(Graphics g, Board board) {
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
      g.drawImage(winScreenImage,0,0,Color.BLACK,null);
      board.New = 1;
      /* Stop any pacman eating sounds */
      board.sounds.nomNomStop();
    }

    void drawTitleScreen(Graphics g, Board board) {
      g.setColor(Color.BLACK);
      g.fillRect(0,0,600,600);
      g.drawImage(titleScreenImage,0,0,Color.BLACK,null);

      /* Stop any pacman eating sounds */
      board.sounds.nomNomStop();
      board.New = 1;
    }

    void drawDeath(Graphics g, Board board) {
      /* Draw the pacman */
      g.drawImage(pacmanImage, board.player.x, board.player.y,Color.BLACK,null);
      g.setColor(Color.BLACK);

      /* Kill the pacman */
      if (board.dying == 4)
        g.fillRect(board.player.x, board.player.y,20,7);
      else if ( board.dying == 3)
        g.fillRect(board.player.x, board.player.y,20,14);
      else if ( board.dying == 2)
        g.fillRect(board.player.x, board.player.y,20,20);
      else if ( board.dying == 1)
      {
        g.fillRect(board.player.x, board.player.y,20,20);
      }
    }

    /* Draws one individual pellet.  Used to redraw pellets that ghosts have run over */
    public void fillPellet(int x, int y, Graphics g)
    {
      g.setColor(Color.YELLOW);
      g.fillOval(x*20+28,y*20+28,4,4);
    }

    boolean drawGameScreens(Graphics g, Board board) {
      /* If this is the title screen, draw the title screen and return */
      if (board.titleScreen)
      {
        drawTitleScreen(g, board);
        return true;
      }
      /* If this is the win screen, draw the win screen and return */
      else if (board.winScreen)
      {
        drawWinScreen(g, board);
        return true;
      }
      /* If this is the game over screen, draw the game over screen and return */
      else if (board.overScreen)
      {
        drawGameOverScreen(g, board);
        return true;
      }
      return false;
    }

    void drawOver(Graphics g, Board board) {
      /* Delete the players and ghosts */
      g.setColor(Color.BLACK);
      g.fillRect(board.player.lastX, board.player.lastY,20,20);
      g.fillRect(board.ghost1.lastX, board.ghost1.lastY,20,20);
      g.fillRect(board.ghost2.lastX, board.ghost2.lastY,20,20);
      g.fillRect(board.ghost3.lastX, board.ghost3.lastY,20,20);
      g.fillRect(board.ghost4.lastX, board.ghost4.lastY,20,20);
    }

    void optimizeDrawCalls(Graphics g, Board board) {
      /* Drawing optimization */
      g.copyArea(board.player.x-20, board.player.y-20,80,80,0,0);
      g.copyArea(board.ghost1.x-20, board.ghost1.y-20,80,80,0,0);
      g.copyArea(board.ghost2.x-20, board.ghost2.y-20,80,80,0,0);
      g.copyArea(board.ghost3.x-20, board.ghost3.y-20,80,80,0,0);
      g.copyArea(board.ghost4.x-20, board.ghost4.y-20,80,80,0,0);
    }
}
