package uk.ac.aber.cs221.gp05.buccaneergame.game.board;

public class Position {
    private int x;
    private int y;

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Position() {
        new Position(0,0);
    }

    public Position(int xCoord, int yCoord) {
        x = xCoord;
        y = yCoord;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
