
package gridwar;

public class Bullet{
    private int bX;
    private int bY;

    public int getbX() {
        return bX;
    }

    public int getbY() {
        return bY;
    }
    
    public void setbX(int x){
        this.bX = x + 44;
    }

    public void setbY(int y) {
        this.bY = y;
    }
    
}
