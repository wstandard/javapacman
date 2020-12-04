/* Drew Schuster */
import java.awt.event.*;
import javax.swing.JFrame;
import java.awt.*;
import java.lang.*;

/* This class contains the entire game... most of the game logic is in the Board class but this
   creates the gui and captures mouse and keyboard input, as well as controls the game states */
public class Pacman
{

  /* These timers are used to kill title, game over, and victory screens after a set idle period (5 seconds)*/
  long titleTimer = -1;
  long timer = -1;

  /* Create a new board */
  Board b=new Board();
  InputListener input = new InputListener(b);

  /* This timer is used to do request new frames be drawn*/
  javax.swing.Timer frameTimer;
 

  /* This constructor creates the entire game essentially */   
  public Pacman()
  {
    b.requestFocus();

    /* Create and set up window frame*/
    JFrame f=new JFrame(); 
    f.setSize(420,460);

    /* Add the board to the frame */
    f.add(b,BorderLayout.CENTER);

    /* Make frame visible, disable resizing */
    f.setVisible(true);
    f.setResizable(false);

    /* Set the New flag to 1 because this is a new game */
    b.New=1;

    /* Manually call the first frameStep to initialize the game. */
    update(true);

    /* Create a timer that calls stepFrame every 30 milliseconds */
    frameTimer = new javax.swing.Timer(30,new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          update(false);
        }
      });

    /* Start the timer */
    frameTimer.start();

    b.requestFocus();
  }

  /* Steps the screen forward one frame */
  public void update(boolean New)
  {
    /* If we aren't on a special screen than the timers can be set to -1 to disable them */
    if (!b.titleScreen && !b.winScreen && !b.overScreen)
    {
      timer = -1;
      titleTimer = -1;
    }

    /* If we are playing the dying animation, keep advancing frames until the animation is complete */
    if (b.dying>0)
    {
      b.repaint();
      return;
    }

    /* New can either be specified by the New parameter in stepFrame function call or by the state
       of b.New.  Update New accordingly */ 
    New = New || (b.New !=0) ;

    /* If this is the title screen, make sure to only stay on the title screen for 5 seconds.
       If after 5 seconds the user hasn't started a game, start up demo mode */
    if (b.titleScreen)
    {
      if (titleTimer == -1)
      {
        titleTimer = System.currentTimeMillis();
      }

      long currTime = System.currentTimeMillis();
      if (currTime - titleTimer >= 5000)
      {
        b.titleScreen = false;
        b.demo = true;
        titleTimer = -1;
      }
      b.repaint();
      return;
    }
 
    /* If this is the win screen or game over screen, make sure to only stay on the screen for 5 seconds.
       If after 5 seconds the user hasn't pressed a key, go to title screen */
    else if (b.winScreen || b.overScreen)
    {
      if (timer == -1)
      {
        timer = System.currentTimeMillis();
      }

      long currTime = System.currentTimeMillis();
      if (currTime - timer >= 5000)
      {
        b.winScreen = false;
        b.overScreen = false;
        b.titleScreen = true;
        timer = -1;
      }
      b.repaint();
      return;
    }


    /* If we have a normal game state, move all pieces and update pellet status */
    if (!New)
    {
      /* Also move the ghosts, and update the pellet states */
      b.ghost1.update();
      b.ghost2.update();
      b.ghost3.update();
      b.ghost4.update();
      b.player.update();
    }

    /* We either have a new game or the user has died, either way we have to reset the board */
    if (b.stopped || New)
    {
      reset();
    }
    /* Otherwise we're in a normal state, advance one frame*/
    else
    {
      b.repaintChanges();
    }
  }

  private void reset() {
    /*Temporarily stop advancing frames */
    frameTimer.stop();

    /* If user is dying ... */
    while (b.dying >0)
    {
      /* Play dying animation. */
      update(false);
    }

    b.resetMovers();

    /* Advance a frame to display main state*/
    b.repaint(0,0,600,600);

    /*Start advancing frames once again*/
    b.stopped=false;
    frameTimer.start();
  }


  /* Main function simply creates a new pacman instance*/
  public static void main(String [] args)
  {
      Pacman c = new Pacman();
  } 
}
