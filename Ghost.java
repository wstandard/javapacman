/* Ghost class controls the ghost. */
class Ghost extends Mover
{

    /*Constructor places ghost and updates states*/
    public Ghost(int x, int y)
    {
        direction='L';
        pelletX=x/gridSize-1;
        pelletY=x/gridSize-1;
        lastPelletX=pelletX;
        lastPelletY=pelletY;
        this.lastX = x;
        this.lastY = y;
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        move();
        updatePellet();
    }

    public boolean collidedWithPlayer(Player player)
    {
        /* oops is set to true when pacman has lost a life */
        boolean oops=false;
        /* Detect collisions */
        if (player.x == x && Math.abs(player.y-y) < 10)
            oops=true;
        else if (player.y == y && Math.abs(player.x-x) < 10)
            oops=true;
        return oops;
    }



}
