/* Drew Schuster */
import java.awt.*;
import javax.swing.JPanel;

/*This board class contains the player, ghosts, pellets, and most of the game logic.*/
public class Board extends JPanel
{

  /* Initialize the player and ghosts */
  Player player = new Player(200,300);
  Ghost ghost1 = new Ghost(180,180);
  Ghost ghost2 = new Ghost(200,180);
  Ghost ghost3 = new Ghost(220,180);
  Ghost ghost4 = new Ghost(220,180);
  Renderer renderer = new Renderer();
  Score score;
  /* Timer is used for playing sound effects and animations */
  long timer = System.currentTimeMillis();

  /* Dying is used to count frames in the dying animation.  If it's non-zero,
     pacman is in the process of dying */
  int dying=0;
 


  int numLives=2;

  /*Contains the game map, passed to player and ghosts */
  boolean[][] state;

  /* Contains the state of all pellets*/
  boolean[][] pellets;

  /* Game dimensions */
  int gridSize;
  int max;

  /* State flags*/
  boolean stopped;
  boolean titleScreen;
  boolean winScreen = false;
  boolean overScreen = false;
  static boolean demo = false;
  int New;

  /* Used to call sound effects */
  GameSounds sounds;

  int lastPelletEatenX = 0;
  int lastPelletEatenY=0;

  /* Constructor initializes state flags etc.*/
  public Board() 
  {
    score = new Score();
    sounds = new GameSounds();
    stopped=false;
    max=400;
    gridSize=20;
    New=0;
    titleScreen = true;
  }


  /* Reset occurs on a new game*/
  public void reset()
  {
    numLives=2;
    state = new boolean[20][20];
    pellets = new boolean[20][20];

    /* Clear state and pellets arrays */
    for(int i=0;i<20;i++)
    {
      for(int j=0;j<20;j++)
      {
        state[i][j]=true;
        pellets[i][j]=true;
      }
    }

    /* Handle the weird spots with no pellets*/
    for(int i = 5;i<14;i++)
    {
      for(int j = 5;j<12;j++)
      {
        pellets[i][j]=false;
      }
    }
    pellets[9][7] = false;
    pellets[8][8] = false;
    pellets[9][8] = false;
    pellets[10][8] = false;

  }


  /* Function is called during drawing of the map.
     Whenever the a portion of the map is covered up with a barrier,
     the map and pellets arrays are updated accordingly to note
     that those are invalid locations to travel or put pellets
  */
  public void updateMap(int x,int y, int width, int height)
  {
    for (int i =x/gridSize; i<x/gridSize+width/gridSize;i++)
    {
      for (int j=y/gridSize;j<y/gridSize+height/gridSize;j++)
      {
        state[i-1][j-1]=false;
        pellets[i-1][j-1]=false;
      }
    }
  }


  /* This is the main function that draws one entire frame of the game */
  @Override
  public void paint(Graphics g)
  {
    renderer.setGraphic(g);
    /* If we're playing the dying animation, don't update the entire screen.
       Just kill the pacman*/ 
    if (dying > 0)
    {
      die(g);
      return;
    }

    if (renderer.drawGameScreens(g, this)) return;

    /* Game initialization */
    if (startNewGame(g)) return;

    /* Kill the pacman */
    if (collidedWithGhost())
    {
      killPacman(g);
    }

    renderer.optimizeDrawCalls(g, this);
    renderer.drawOver(g, this);

    /* Eat pellets */
    if ( pellets[player.pelletX][player.pelletY] && New!=2 && New !=3)
    {
      if (eatPellet()) return;
    }

    /* If we moved to a location without pellets, stop the sounds */
    else if ( (player.pelletX != lastPelletEatenX || player.pelletY != lastPelletEatenY ) || player.stopped)
    {
      /* Stop any pacman eating sounds */
      sounds.nomNomStop();
    }


    /* Replace pellets that have been run over by ghosts */
    if ( pellets[ghost1.lastPelletX][ghost1.lastPelletY])
      renderer.fillPellet(ghost1.lastPelletX,ghost1.lastPelletY,g);
    if ( pellets[ghost2.lastPelletX][ghost2.lastPelletY])
      renderer.fillPellet(ghost2.lastPelletX,ghost2.lastPelletY,g);
    if ( pellets[ghost3.lastPelletX][ghost3.lastPelletY])
      renderer.fillPellet(ghost3.lastPelletX,ghost3.lastPelletY,g);
    if ( pellets[ghost4.lastPelletX][ghost4.lastPelletY])
      renderer.fillPellet(ghost4.lastPelletX,ghost4.lastPelletY,g);


    /*Draw the ghosts */
    renderer.drawGhost(g, this);

    /* Draw the pacman */
    renderer.drawPacman(g, player);

    /* Draw the border around the game in case it was overwritten by ghost movement or something */
    g.setColor(Color.WHITE);
    g.drawRect(19,19,382,382);

  }

  private void killPacman(Graphics g) {
    if (!stopped){
      /* 4 frames of death*/
      dying=4;

      /* Play death sound effect */
      sounds.death();
      /* Stop any pacman eating sounds */
      sounds.nomNomStop();

      /*Decrement lives, update screen to reflect that.  And set appropriate flags and timers */
      numLives--;
      stopped=true;
      renderer.drawLives(g, this);
      timer = System.currentTimeMillis();
    }
  }

  private boolean eatPellet() {
    lastPelletEatenX = player.pelletX;
    lastPelletEatenY = player.pelletY;

    /* Play eating sound */
    sounds.nomNom();

    /* Increment pellets eaten value to track for end game */
    player.pelletsEaten++;

    /* Delete the pellet*/
    pellets[player.pelletX][player.pelletY]=false;

    /* Increment the score */
    score.currScore += 50;
    score.update();
    /* If this was the last pellet */
    if (player.pelletsEaten == 173)
    {
      /*Demo mode can't get a high score */
      if (!demo)
      {
        score.updateScore();
        winScreen = true;
      }
      else
      {
        titleScreen = true;
      }
      return true;
    }
    return false;
  }

  private boolean collidedWithGhost() {
    /* oops is set to true when pacman has lost a life */
    boolean oops=false;
    if (ghost1.collidedWithPlayer(player))
      oops = true;
    else if (ghost2.collidedWithPlayer(player))
      oops = true;
    else if (ghost3.collidedWithPlayer(player))
      oops = true;
    else if (ghost4.collidedWithPlayer(player))
      oops = true;
    return oops;
  }

  private boolean startNewGame(Graphics g) {
    if (New==1)
    {
      initGame(g);
    }
    /* Second frame of new game */
    else if (New == 2)
    {
      New++;
    }
    /* Third frame of new game */
    else if (New == 3)
    {
      New++;
      /* Play the newGame sound effect */
      sounds.newGame();
      timer = System.currentTimeMillis();
      return true;
    }
    /* Fourth frame of new game */
    else if (New == 4)
    {
      /* Stay in this state until the sound effect is over */
      long currTime = System.currentTimeMillis();
      if (currTime - timer >= 5000)
      {
        New=0;
      }
      else
        return true;
    }
    return false;
  }

  private void initGame(Graphics g) {
    reset();
    score.currScore = 0;
    player = new Player(200,300);
    ghost1 = new Ghost(180,180);
    ghost2 = new Ghost(200,180);
    ghost3 = new Ghost(220,180);
    ghost4 = new Ghost(220,180);
    renderer.drawBoard(g, this);
    renderer.drawPellets(g, this);
    renderer.drawLives(g, this);
    /* Send the game map to player and all ghosts */
    player.updateState(state);
    /* Don't let the player go in the ghost box*/
    player.state[9][7]=false;
    ghost1.updateState(state);
    ghost2.updateState(state);
    ghost3.updateState(state);
    ghost4.updateState(state);

    New++;
  }

  private void die(Graphics g) {
    /* Stop any pacman eating sounds */
    sounds.nomNomStop();

    renderer.drawDeath(g, this);

      /* Take .1 seconds on each frame of death, and then take 2 seconds
         for the final frame to allow for the sound effect to end */
    long currTime = System.currentTimeMillis();
    long temp;
    if (dying != 1)
      temp = 100;
    else
      temp = 2000;
    /* If it's time to draw a new death frame... */
    if (currTime - timer >= temp)
    {
      dying--;
      timer = currTime;
      /* If this was the last death frame...*/
      if (dying == 0)
      {
        if (numLives==-1)
        {
          /* Demo mode has infinite lives, just give it more lives*/
          if (demo)
            numLives=2;
          else
          {
          /* Game over for player.  If relevant, update high score.  Set gameOver flag*/
            score.updateScore();
            overScreen=true;
          }
        }
      }
    }
  }

  /* This repaint function repaints only the parts of the screen that may have changed.
       Namely the area around every player ghost and the menu bars
    */
  public void repaintChanges()
  {
    if (player.teleport)
    {
      repaint(player.lastX-20, player.lastY-20,80,80);
      player.teleport=false;
    }
    repaint(0,0,600,20);
    repaint(0,420,600,40);
    repaint(player.x-20, player.y-20,80,80);
    repaint(ghost1.x-20, ghost1.y-20,80,80);
    repaint(ghost2.x-20, ghost2.y-20,80,80);
    repaint(ghost3.x-20, ghost3.y-20,80,80);
    repaint(ghost4.x-20, ghost4.y-20,80,80);
  }

  void resetMovers() {
    /* Move all game elements back to starting positions and orientations */
    player.currDirection='L';
    player.direction='L';
    player.desiredDirection='L';
    player.x = 200;
    player.y = 300;
    ghost1.x = 180;
    ghost1.y = 180;
    ghost2.x = 200;
    ghost2.y = 180;
    ghost3.x = 220;
    ghost3.y = 180;
    ghost4.x = 220;
    ghost4.y = 180;
  }
}
