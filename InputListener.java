import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements MouseListener, KeyListener {

    Board b;

    public InputListener(Board b)
    {
        this.b = b;
        /*Set listeners for mouse actions and button clicks*/
        b.addMouseListener(this);
        b.addKeyListener(this);
    }

    /* Handles user key presses*/
    public void keyPressed(KeyEvent e)
    {
        /* Pressing a key in the title screen starts a game */
        if (b.titleScreen)
        {
            b.titleScreen = false;
            return;
        }
        /* Pressing a key in the win screen or game over screen goes to the title screen */
        else if (b.winScreen || b.overScreen)
        {
            b.titleScreen = true;
            b.winScreen = false;
            b.overScreen = false;
            return;
        }
        /* Pressing a key during a demo kills the demo mode and starts a new game */
        else if (b.demo)
        {
            b.demo=false;
            /* Stop any pacman eating sounds */
            b.sounds.nomNomStop();
            b.New=1;
            return;
        }

        /* Otherwise, key presses control the player! */
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                b.player.desiredDirection='L';
                break;
            case KeyEvent.VK_RIGHT:
                b.player.desiredDirection='R';
                break;
            case KeyEvent.VK_UP:
                b.player.desiredDirection='U';
                break;
            case KeyEvent.VK_DOWN:
                b.player.desiredDirection='D';
                break;
        }

        b.repaintChanges();
    }

    /* This function detects user clicks on the menu items on the bottom of the screen */
    public void mousePressed(MouseEvent e){
        if (b.titleScreen || b.winScreen || b.overScreen)
        {
            /* If we aren't in the game where a menu is showing, ignore clicks */
            return;
        }

        /* Get coordinates of click */
        int x = e.getX();
        int y = e.getY();
        if ( 400 <= y && y <= 460)
        {
            if ( 100 <= x && x <= 150)
            {
                /* New game has been clicked */
                b.New = 1;
            }
            else if (180 <= x && x <= 300)
            {
                /* Clear high scores has been clicked */
                b.score.clearHighScores();
            }
            else if (350 <= x && x <= 420)
            {
                /* Exit has been clicked */
                System.exit(0);
            }
        }
    }


    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

}
